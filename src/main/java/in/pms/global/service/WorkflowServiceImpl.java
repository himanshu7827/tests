package in.pms.global.service;

import in.pms.global.dao.ActionMasterDao;
import in.pms.global.dao.RoleActionMappingDao;
import in.pms.global.dao.TransactionDao;
import in.pms.global.dao.WorkflowDao;
import in.pms.global.domain.ActionMasterDomain;
import in.pms.global.domain.ApprovalMasterDomain;
import in.pms.global.domain.RoleActionMappingDomain;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.model.TransactionMasterModel;
import in.pms.global.model.WorkflowModel;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.mail.dao.SendMail;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.ProjectDocumentMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.TempEmployeeRoleMasterDomain;
import in.pms.master.domain.TempProjectDocumentDetailsDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.master.domain.TempProjectMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.service.NewsLetterService;
import in.pms.transaction.dao.MonthlyProgressDao;
import in.pms.transaction.domain.Application;
import in.pms.transaction.domain.MonthlyProgressDomain;
import in.pms.transaction.model.MonthlyProgressModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowService{
	
	@Autowired
	WorkflowDao workflowDao;
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	ActionMasterDao actionMasterDao;
	@Autowired
	RoleActionMappingDao roleActionMappingDao;
	@Autowired
	MonthlyProgressDao monthlyProgressDao;
	@Autowired
	GlobalDao globalDao;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	ProjectDocumentMasterDao projectDocumentMasterDao;
	
	@Autowired
	NewsLetterService newsLetterService;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;

	@Override
	public WorkflowModel getTransactionDetails(int numMonthlyProgressId) {
	
		WorkflowModel workflowModel=null;
		List<TransactionMasterDomain> transactionMasterDomainList=transactionDao.getTransactionDetails(numMonthlyProgressId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		if(transactionMasterDomainList.size()>0)
		{
			workflowModel=new WorkflowModel();
			workflowModel.setNumCurrentRoleId(userInfo.getSelectedEmployeeRole().getNumRoleId());
			workflowModel.setNumLastRoleId(transactionMasterDomainList.get(0).getRoleMasterDomain().getNumId());
			workflowModel.setNumLastActionId(transactionMasterDomainList.get(0).getActionMasterDomain().getNumActionId());
			workflowModel.setNumWorkflowId(transactionMasterDomainList.get(0).getWorkflowMasterDomain().getNumWorkflowId());
			
		}
			
		return workflowModel;
	}


	@Override
	public List<WorkflowModel> getNextRoleActionDetails(WorkflowModel workflowModel) {
		int numMonthlyProgressId=0;
		long numWorkflowId=0l;
		int numPageId=0;
		List<WorkflowModel> woList=new ArrayList<WorkflowModel>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		List<EmployeeRoleMasterModel> allAssignedRoles = userInfo.getEmployeeRoleList();
		
		
		if(workflowModel.getEncMonthlyProgressId()!=null && workflowModel.getEncWorkflowId()!=null){	
			String strDecPageId="1";
			WorkflowModel workflowModel2=new WorkflowModel();
			if(null != workflowModel.getEncMonthlyProgressId() && !workflowModel.getEncMonthlyProgressId().equals("")){
				String strDecMonthlyProgressId=encryptionService.dcrypt(workflowModel.getEncMonthlyProgressId());
				numMonthlyProgressId=Integer.parseInt(strDecMonthlyProgressId);
			}
			if(numMonthlyProgressId != 0){			
			MonthlyProgressDomain monthlyProgressDomain =monthlyProgressDao.getOne(numMonthlyProgressId);
			List<EmployeeRoleMasterModel> filteredPMRoles = new ArrayList<EmployeeRoleMasterModel>();
			List<EmployeeRoleMasterModel> filteredPIRoles = new ArrayList<EmployeeRoleMasterModel>();
			Boolean show=false;  // variable for store hodStatus [21-05-2024]
			if(null != monthlyProgressDomain) {
				ProjectMasterDomain projectMasterDomain =monthlyProgressDomain.getProjectMasterDomain();
				if(null != projectMasterDomain) {
					long projectId = projectMasterDomain.getNumId();
					if(null != allAssignedRoles && allAssignedRoles.size()>0){
						filteredPMRoles = allAssignedRoles.stream()			      
						        .filter(x -> 3 == x.getNumRoleId() && projectId == x.getNumProjectId()).collect(Collectors.toList());		
						filteredPIRoles = allAssignedRoles.stream()			      
						        .filter(x -> 4 == x.getNumRoleId() && projectId == x.getNumProjectId()).collect(Collectors.toList());		
						
					}
					/*------- Get Project Team and Check HOD exists or not [ 21-05-2024 ] -------*/
					List<Object[]> allocatedEmployees = employeeRoleMasterDao.projectTeamWiseEmployees(projectId,0);
					for(Object[] a:allocatedEmployees){
						if(a[8].equals("Head of Department")){
							show=true;
							break;
						}
					}
				}
			}
			
			String strDecWorkflowId=encryptionService.dcrypt(workflowModel.getEncWorkflowId());
			if(workflowModel.getEncPageId()!=null&&!workflowModel.getEncPageId().equals(""))
			strDecPageId=encryptionService.dcrypt(workflowModel.getEncPageId());
			
			numWorkflowId=Long.parseLong(strDecWorkflowId);
			numPageId=Integer.parseInt(strDecPageId);
			
			workflowModel2=getTransactionDetails(numMonthlyProgressId);
			String[] arrActionIds=null;
			List<Long> newList=new ArrayList<Long>();
			List<ActionMasterDomain> actionList=new ArrayList<ActionMasterDomain>();
			
			if(workflowModel2!=null){
				List<ApprovalMasterDomain> approvalList=getApprovalDetails(numWorkflowId,workflowModel2.getNumLastRoleId(),workflowModel2.getNumLastActionId(),workflowModel2.getNumCurrentRoleId());
				if(numPageId==1 && approvalList.size()>0){
					arrActionIds=approvalList.get(0).getStrFirstPageActionIds().split(",");
					try {
				    	 List<String> list = Arrays.asList(arrActionIds);

							newList = list.stream().map(s -> Long.parseLong(s))
														.collect(Collectors.toList());
					}catch (Exception e) {
						e.printStackTrace();
					}
					actionList=actionMasterDao.getActionDetails(newList);
				}else if(numPageId==2 && approvalList.size()>0){
					try {
						arrActionIds=approvalList.get(0).getStrSecondPageActionIds().split(",");
				    	 List<String> list = Arrays.asList(arrActionIds);

							newList = list.stream().map(s -> Long.parseLong(s))
														.collect(Collectors.toList());
					        /*----- If HOD not exists than remove all HOD Actions [21-05-2024] ---*/
							if (!show) {
					            newList.remove(Long.valueOf(26));
					            newList.remove(Long.valueOf(27));
					        }
				            if (newList.contains(26L)) {
				                newList.remove(Long.valueOf(4));
				            }
							 actionList=actionMasterDao.getActionDetails(newList);
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}else if(numPageId==1&& approvalList.size()==0){
					int selectedRole = userInfo.getSelectedEmployeeRole().getNumRoleId();
					if( selectedRole==5){ 
						newList.add(2L);
						actionList=actionMasterDao.getActionDetails(newList);
					}else if(selectedRole != 5 && selectedRole!= 6 && selectedRole!= 9) {
						//load Project Id						
						if((null != filteredPMRoles && filteredPMRoles.size()>0) || (null != filteredPIRoles && filteredPIRoles.size()>0)) {
							newList.add(2L);
							actionList=actionMasterDao.getActionDetails(newList);
						}
					}
				}
			}
			else if((null != filteredPMRoles && filteredPMRoles.size()>0 && workflowModel2==null))	{
				
				if(numPageId==1)
				{
					 newList.add(1L);
					 newList.add(3L);
					 actionList=actionMasterDao.getActionDetails(newList);
				}
				else if(numPageId==2 ){
					/*----- If HOD exists than show HOD else GC [21-05-2024] ---*/
					if(show){
						newList.add(26L);
					}else{
						newList.add(4L);
					}
					actionList=actionMasterDao.getActionDetails(newList);					
				}
			}
			else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5 && workflowModel2==null){
				if(numPageId==1){
					 newList.add(1L);
					 newList.add(3L);
					 actionList=actionMasterDao.getActionDetails(newList);
				}
				else if(numPageId==2 ){					
					 newList.add(6L);
					 actionList=actionMasterDao.getActionDetails(newList);					
				}
			}
			if(actionList.size()>0){
				
				for(ActionMasterDomain amd:actionList)
				{
					WorkflowModel workflowNewModel=new WorkflowModel();
					workflowNewModel.setStrActionName(amd.getStrName());
					String strActionId=String.valueOf(amd.getNumActionId());
					workflowNewModel.setStrEncActionId(encryptionService.encrypt(strActionId));
					List<RoleActionMappingDomain> roleActionMappingList=getRoleActionMappingDetails(amd.getNumActionId(), userInfo.getSelectedEmployeeRole().getNumRoleId(),numWorkflowId);
					if(roleActionMappingList.size()>0 && roleActionMappingList.get(0).getStrToolTip()!=null && !roleActionMappingList.get(0).getStrToolTip().equals(""))
						workflowNewModel.setStrToolTip(roleActionMappingList.get(0).getStrToolTip());
					else
						workflowNewModel.setStrToolTip(amd.getStrName());
					if(roleActionMappingList.size()>0 && roleActionMappingList.get(0).getStrSuccessMsg()!=null && !roleActionMappingList.get(0).getStrSuccessMsg().equals(""))
						workflowNewModel.setStrSuccessMsg(roleActionMappingList.get(0).getStrSuccessMsg());
					else
						workflowNewModel.setStrSuccessMsg(amd.getStrName());
					if(roleActionMappingList.size()>0)
						workflowNewModel.setIsRemarksReq(roleActionMappingList.get(0).getIsRemarksReq());
					else
						workflowNewModel.setIsRemarksReq(-1);
					woList.add(workflowNewModel);
					
				}
			}
		}
		}else if(workflowModel.getEncWorkflowId()!=null){
			String strWorkflowId = encryptionService.dcrypt(workflowModel.getEncWorkflowId());
			String strCustomId = "";
			if(null != workflowModel.getEncCustomId()){
				strCustomId = encryptionService.dcrypt(workflowModel.getEncCustomId());
			}
			WorkflowModel model = getLatestTransactionDetails(Integer.parseInt(strCustomId), Long.parseLong(strWorkflowId));
			if(null != model){
				List<ApprovalMasterDomain> approvalList=getApprovalDetails(Long.parseLong(strWorkflowId),model.getNumLastRoleId(),model.getNumLastActionId(),model.getNumCurrentRoleId());			
				if(null != approvalList && approvalList.size()>0){
				String[] arrActionIds=approvalList.get(0).getStrFirstPageActionIds().split(",");
				List<Long> newList=new ArrayList<Long>();
				List<ActionMasterDomain> actionList=new ArrayList<ActionMasterDomain>();
				try {
			    	 List<String> list = Arrays.asList(arrActionIds);
			    	 newList = list.stream().map(s -> Long.parseLong(s))
													.collect(Collectors.toList());
				}catch (Exception e) {
					e.printStackTrace();
				}
				actionList=actionMasterDao.getActionDetails(newList);
				
				if(actionList.size()>0){				
					for(ActionMasterDomain amd:actionList){
						WorkflowModel workflowNewModel=new WorkflowModel();
						workflowNewModel.setStrActionName(amd.getStrName());
						String strActionId=String.valueOf(amd.getNumActionId());
						workflowNewModel.setStrEncActionId(encryptionService.encrypt(strActionId));
						List<RoleActionMappingDomain> roleActionMappingList=getRoleActionMappingDetails(amd.getNumActionId(), userInfo.getSelectedEmployeeRole().getNumRoleId(),Long.parseLong(strWorkflowId));
						if(null != roleActionMappingList && roleActionMappingList.size() > 0){
							RoleActionMappingDomain roleActionMappingDomain = roleActionMappingList.get(0);
						
							if(roleActionMappingDomain.getStrToolTip()!=null && !roleActionMappingDomain.getStrToolTip().equals("")){
								workflowNewModel.setStrToolTip(roleActionMappingDomain.getStrToolTip());
							}else{
								workflowNewModel.setStrToolTip(amd.getStrName());
							}
							
							if(roleActionMappingDomain.getStrSuccessMsg()!=null && !roleActionMappingDomain.getStrSuccessMsg().equals("")){
								workflowNewModel.setStrSuccessMsg(roleActionMappingDomain.getStrSuccessMsg());
							}else{
								workflowNewModel.setStrSuccessMsg(amd.getStrName());
							}
							workflowNewModel.setIsRemarksReq(roleActionMappingDomain.getIsRemarksReq());
						}else{
							workflowNewModel.setIsRemarksReq(-1);						
						}
						
						workflowNewModel.setNumActionId(amd.getNumActionId());
						workflowNewModel.setCustomId(strCustomId);
						
					woList.add(workflowNewModel);
						
					}
				}
				
			 }
			}
		}
		return woList;
	}
	
	
	private List<ApprovalMasterDomain> getApprovalDetails(long numWorkflowId, long numLastRoleId, long numLastActionId,
			long numCurrentRoleId) {
		// TODO Auto-generated method stub
		List<ApprovalMasterDomain> approvalMasterDomainList=workflowDao.getApprovalDetails(numWorkflowId,numLastRoleId,numLastActionId,numCurrentRoleId);
		return approvalMasterDomainList;
	}


	@Override
	public WorkflowModel doWorkAccrodingToAction(WorkflowModel workflowModel) {
		WorkflowModel workflowModel2=new WorkflowModel();
		SendMail smail = new SendMail();
		beanFactory.autowireBean(smail);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		boolean isUserValidToTakeAction=true;
		TransactionMasterDomain transactionMasterDomain=null;
		boolean isStatusUpdatedInMainTable=false;
		boolean isDataInsertedInTransaction=false;
		String strDecPageId="1";
		int numMonthlyProgressId=0;
		long numWorkflowId=0l;
		long numActionId=0;
		String strCopyCreated="";
		try {	
			String strDecWorkflowId=encryptionService.dcrypt(workflowModel.getEncWorkflowId());
			numWorkflowId=Integer.parseInt(strDecWorkflowId);
			String strDecActionnId=encryptionService.dcrypt(workflowModel.getStrEncActionId());	
			numActionId=Long.parseLong(strDecActionnId);
			
			if(numWorkflowId == 1 || numWorkflowId==2){
				String strDecMonthlyProgressId=encryptionService.dcrypt(workflowModel.getEncMonthlyProgressId());
				if(workflowModel.getEncPageId()!=null&&!workflowModel.getEncPageId().equals("")){
					strDecPageId=encryptionService.dcrypt(workflowModel.getEncPageId());
				}
						
			numMonthlyProgressId=Integer.parseInt(strDecMonthlyProgressId);
			MonthlyProgressDomain monthlyProgressDomain = monthlyProgressDao.getOne(numMonthlyProgressId);
			long projectId = monthlyProgressDomain.getProjectMasterDomain().getNumId();
			int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
			int numRoleId =0;
			List<RoleActionMappingDomain> roleActionMappingList=null;
			if(!(selectedRoleId == 5 || selectedRoleId == 6 || selectedRoleId==9)){
				//Get Role in Project
				List<EmployeeRoleMasterModel> allAssignedRoles = userInfo.getEmployeeRoleList();
				 if(null != allAssignedRoles && allAssignedRoles.size()>0){
					 List<EmployeeRoleMasterModel>	filteredRoles = allAssignedRoles.stream()			      
						        .filter(x -> (3 == x.getNumRoleId() || 4 == x.getNumRoleId() || 15 == x.getNumRoleId())  && projectId == x.getNumProjectId()).collect(Collectors.toList());		
					 for(EmployeeRoleMasterModel employeeRole : filteredRoles){
						 roleActionMappingList=getRoleActionMappingDetails(numActionId,employeeRole.getNumRoleId(),numWorkflowId);
						 if(null != roleActionMappingList && roleActionMappingList.size()>0){
							 numRoleId = employeeRole.getNumRoleId();
							 break;
						 }
					 }
				 }
				
			}else{
				numRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
				roleActionMappingList=getRoleActionMappingDetails(numActionId,numRoleId,numWorkflowId);
			}
				if(null == roleActionMappingList){
					roleActionMappingList = new ArrayList<RoleActionMappingDomain>();
				}
			
			if(roleActionMappingList.size()>0 && numActionId == 9){				
				int roleId = 4;
				List<EmployeeRoleMasterDomain> roles = employeeRoleMasterDao.checkRoleInProject(roleId,projectId);
				if(!(null != roles && roles.size()>0)){
					workflowModel2.setStrSuccessMsg(ResourceBundleFile.getValueFromKey("progressReport.PIerror"));
					return workflowModel2;
				}				
			}
			
			if(isUserValidToTakeAction && roleActionMappingList.size()>0){				
				if(roleActionMappingList.get(0).getNumTransactionImpact()==0 || roleActionMappingList.get(0).getNumTransactionImpact()==1){
					TransactionMasterModel trMasterModel=new TransactionMasterModel();
					trMasterModel.setDtTrDate(new Date());
					trMasterModel.setNumActionId(numActionId);
					trMasterModel.setNumRoleId(numRoleId);
					trMasterModel.setNumIsValid(roleActionMappingList.get(0).getNumTransactionImpact());
					
					trMasterModel.setNumMonthlyProgressId(numMonthlyProgressId);
					trMasterModel.setNumWorkflowId(numWorkflowId);
					trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
					if(workflowModel.getStrRemarks()!=null && !workflowModel.getStrRemarks().equals(""))
					trMasterModel.setStrRemarks(workflowModel.getStrRemarks());
					transactionMasterDomain=insertIntoTransaction(trMasterModel);
					
					if(transactionMasterDomain!=null)
					isDataInsertedInTransaction=true;
					else
					isDataInsertedInTransaction=true;	
					
				}
				else
					isDataInsertedInTransaction=true;	
				
				if(roleActionMappingList.get(0).getNumIsCopyCreate()==1){
					if(transactionMasterDomain!=null){
						strCopyCreated=globalDao.createMonthlyProgressReportCopy(transactionMasterDomain.getNumTransactionId(),numMonthlyProgressId);
					}else{
						strCopyCreated=globalDao.createMonthlyProgressReportCopy(0,numMonthlyProgressId);
					}
					
					
				}
				else
				{
					strCopyCreated="success";
				}
				if(roleActionMappingList.get(0).getStrStatus()!=null && !roleActionMappingList.get(0).getStrStatus().equals(""))
				{
					MonthlyProgressModel monthlyProgressModel=new MonthlyProgressModel();
					monthlyProgressModel.setNumId(numMonthlyProgressId);
					monthlyProgressModel.setDtTrDate(new Date());
					monthlyProgressModel.setSubmissionStatus(roleActionMappingList.get(0).getStrStatus());
					monthlyProgressModel.setStrRemarks(workflowModel.getStrRemarks());
					isStatusUpdatedInMainTable=updateMonhlyProgressReport(monthlyProgressModel);
				
				}
				else
					isStatusUpdatedInMainTable=true;
				
				if(!isStatusUpdatedInMainTable || !isDataInsertedInTransaction || strCopyCreated.equals("error"))
				workflowModel2.setStrSuccessMsg("error");
				else if(roleActionMappingList.size()>0 && roleActionMappingList.get(0).getStrSuccessMsg()!=null && !roleActionMappingList.get(0).getStrSuccessMsg().equals("") && isStatusUpdatedInMainTable && isDataInsertedInTransaction && strCopyCreated.equals("success"))
				workflowModel2.setStrSuccessMsg(roleActionMappingList.get(0).getStrSuccessMsg());
				else
				workflowModel2.setStrSuccessMsg("");	
				
			}
			}else{

			String strCustomId=encryptionService.dcrypt(workflowModel.getEncCustomId());										
			int numCustomId=Integer.parseInt(strCustomId);			
			
			int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
			List<RoleActionMappingDomain> roleActionMappingList=null;
			long roleId = 0;
			if(!(selectedRoleId == 5 || selectedRoleId == 6 || selectedRoleId==9)){
				//Get Role in Project
				List<EmployeeRoleMasterDomain> allAssignedRoles = employeeRoleMasterDao.getEmployeeRoleMasterDomain(userInfo.getEmployeeId());
				if(null != allAssignedRoles && allAssignedRoles.size()>0){
					 List<EmployeeRoleMasterDomain>	filteredRoles = allAssignedRoles.stream()			      
						        .filter(x -> numCustomId == x.getNumProjectId()).collect(Collectors.toList());		
					 for(EmployeeRoleMasterDomain employeeRole : filteredRoles){						 
						 roleActionMappingList=getRoleActionMappingDetails(numActionId,employeeRole.getRoleMasterDomain().getNumId(),numWorkflowId);
						 if(null != roleActionMappingList && roleActionMappingList.size()>0){
							 roleId = employeeRole.getRoleMasterDomain().getNumId();
							 break;
						 }
					 }
				 }
				
			}else{
				roleId = selectedRoleId;
				roleActionMappingList=getRoleActionMappingDetails(numActionId,selectedRoleId,numWorkflowId);
			}
			
			if(null == roleActionMappingList){
				roleActionMappingList = new ArrayList<RoleActionMappingDomain>();
			}
			if(numWorkflowId == 4 && numActionId == 20 && selectedRoleId==7){
				TransactionMasterModel transactionMasterModel=new TransactionMasterModel();
				transactionMasterModel.setDtTrDate(new Date());
				transactionMasterModel.setNumActionId(numActionId);
				transactionMasterModel.setNumRoleId(7);
				transactionMasterModel.setNumIsValid(1);										
				transactionMasterModel.setNumWorkflowId(numWorkflowId);
				transactionMasterModel.setNumTrUserId(userInfo.getEmployeeId());
				if(workflowModel.getStrRemarks()!=null && !workflowModel.getStrRemarks().equals(""))
					transactionMasterModel.setStrRemarks(workflowModel.getStrRemarks());
				transactionMasterModel.setCustomId(numCustomId);					
				transactionMasterDomain=insertIntoTransaction(transactionMasterModel);
				workflowModel2.setStrSuccessMsg("Project Closure request has been sent to concerned GC ");
			}
			//Bhavesh(25-04-23) for reviewd project for closure at GC finance
			else if(numWorkflowId == 4 && numActionId == 29 && selectedRoleId==7){
				TransactionMasterModel transactionMasterModel=new TransactionMasterModel();
				transactionMasterModel.setDtTrDate(new Date());
				transactionMasterModel.setNumActionId(numActionId);
				transactionMasterModel.setNumRoleId(7);
				transactionMasterModel.setNumIsValid(1);										
				transactionMasterModel.setNumWorkflowId(numWorkflowId);
				transactionMasterModel.setNumTrUserId(userInfo.getEmployeeId());
				if(workflowModel.getStrRemarks()!=null && !workflowModel.getStrRemarks().equals(""))
					transactionMasterModel.setStrRemarks(workflowModel.getStrRemarks());
				transactionMasterModel.setCustomId(numCustomId);					
				transactionMasterDomain=insertIntoTransaction(transactionMasterModel);
				workflowModel2.setStrSuccessMsg("Project Financially Reconciled ");
			}
			else if(numWorkflowId == 4 && numActionId == 23 && selectedRoleId==7){
				TransactionMasterModel transactionMasterModel=new TransactionMasterModel();
				transactionMasterModel.setDtTrDate(new Date());
				transactionMasterModel.setNumActionId(numActionId);
				transactionMasterModel.setNumRoleId(7);
				transactionMasterModel.setNumIsValid(1);										
				transactionMasterModel.setNumWorkflowId(numWorkflowId);
				transactionMasterModel.setNumTrUserId(userInfo.getEmployeeId());
				if(workflowModel.getStrRemarks()!=null && !workflowModel.getStrRemarks().equals(""))
					transactionMasterModel.setStrRemarks(workflowModel.getStrRemarks());
				transactionMasterModel.setCustomId(numCustomId);					
				transactionMasterDomain=insertIntoTransaction(transactionMasterModel);
				
				ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(numCustomId);									
				projectMasterDomain.setClosureRemarks(workflowModel.getStrRemarks());
				projectMasterDomain.setClosureStatus("Financial");
				projectMasterDomain.setProjectClosureDate(DateUtils.dateStrToDate(workflowModel.getFinClosureDate()));
				projectMasterDomain.setFinancialClosureDate(DateUtils.dateStrToDate(workflowModel.getFinClosureDate()));								
				projectMasterDao.mergeProjectMaster(projectMasterDomain);
				//Added by devesh on 17-10-23 to set end date after Financial Closure of Projects
				List<TempEmployeeRoleMasterDomain> teamMem=employeeRoleMasterDao.getDetailsOfTempEmpRole(numCustomId);
				
				if(teamMem.size()>0){
					for(int i=0;i<teamMem.size();i++){
						TempEmployeeRoleMasterDomain tempEmpDetails=teamMem.get(i);
						EmployeeRoleMasterDomain roleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterById(tempEmpDetails.getNumEmpRoleId());
						
						if(roleMasterDomain.getRoleMasterDomain().getNumId()==3||roleMasterDomain.getRoleMasterDomain().getNumId()==15||roleMasterDomain.getRoleMasterDomain().getNumId()==4){
							roleMasterDomain.setDtEndDate(DateUtils.dateStrToDate(workflowModel.getFinClosureDate()));
						}
						employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(roleMasterDomain);
					}
				}
				//End of Setting Date
				workflowModel2.setStrSuccessMsg("Project is now Financially Closed");
			}
			
			if(roleActionMappingList.size()>0){				
				if(roleActionMappingList.get(0).getNumTransactionImpact()==0 || roleActionMappingList.get(0).getNumTransactionImpact()==1){
					TransactionMasterModel trMasterModel=new TransactionMasterModel();
					trMasterModel.setDtTrDate(new Date());
					trMasterModel.setNumActionId(numActionId);
					trMasterModel.setNumRoleId(roleId);
					trMasterModel.setNumIsValid(roleActionMappingList.get(0).getNumTransactionImpact());										
					trMasterModel.setNumWorkflowId(numWorkflowId);
					trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
					if(workflowModel.getStrRemarks()!=null && !workflowModel.getStrRemarks().equals(""))
					trMasterModel.setStrRemarks(workflowModel.getStrRemarks());
					trMasterModel.setCustomId(numCustomId);					
					transactionMasterDomain=insertIntoTransaction(trMasterModel);
					if(transactionMasterDomain!=null){
						isDataInsertedInTransaction=true;
						if(numWorkflowId == 3 && numActionId == 12){
							//Update Project Status
							String ccRoles="5,6,7,10";
							String cCMailIds="";
							ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(numCustomId);	
							
							projectMasterDomain.setStrProjectStatus("Ongoing");								
							projectMasterDao.mergeProjectMaster(projectMasterDomain);
							String[] roleCc = ccRoles.split(",");	
							if(roleCc.length>0){
							for(int i =0; i < roleCc.length; i++){
								if(!roleCc[i].equals("")){
								int roleToCC = Integer.parseInt(roleCc[i]);
								 String ccMailId = newsLetterService.getDetailsOfEmplApplLevel(roleToCC,numCustomId) ;
								 if(ccMailId!=null && !ccMailId.equals("")){
									 cCMailIds=  ccMailId +","+cCMailIds;
								 		}
						
									}				
								}
							 String toMailIds = newsLetterService.getDetailsOfEmplApplLevel(3,numCustomId) ;
							try {
								String mailContent=ResourceBundleFile.getValueFromKey("PROJECT_APPROVAL"); 
								 mailContent = mailContent.replaceAll("\\$PROJECTS_NAME\\$", projectMasterDomain.getStrProjectName()+"");
								 mailContent = mailContent.replaceAll("\\$CDAC_OUTLAY\\$", CurrencyUtils.convertToINR(projectMasterDomain.getProjectCost())+"");
								 mailContent = mailContent.replaceAll("\\$PROJECT_CODE\\$", projectMasterDomain.getStrProjectRefNo()+"");
								 mailContent = mailContent.replaceAll("\\$GROUP_NAME\\$",  projectMasterDomain.getApplication().getGroupMaster().getGroupName()+"");
								 mailContent = mailContent.replaceAll("\\$DURATION\\$",  DateUtils.getDurationInMonths((projectMasterDomain.getDtProjectStartDate()), (projectMasterDomain.getDtProjectEndDate()))+"");
								 
								 if(null!=projectMasterDomain.getApplication()){
										Application application = projectMasterDomain.getApplication();
										mailContent = mailContent.replaceAll("\\$TOTAL_OUTLAY\\$", CurrencyUtils.convertToINR(application.getTotalProposalCost())+"");
										}
								 String subject=ResourceBundleFile.getValueFromKey("PROJECT_APPROVAL_SUB"); 
								 subject=subject.replaceAll("\\$PROJECTS_NAME\\$", projectMasterDomain.getStrProjectName()+"");
								 subject=subject.replaceAll("\\$GROUP_NAME\\$",  projectMasterDomain.getApplication().getGroupMaster().getGroupName()+"");
								 smail.TransferToMailServerwithCC(toMailIds, subject,mailContent,cCMailIds,numCustomId);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						}
						
						else if(numWorkflowId == 3 && numActionId == 12){
							//Update Project Status
							ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(numCustomId);									
							projectMasterDomain.setStrProjectStatus("Ongoing");								
							projectMasterDao.mergeProjectMaster(projectMasterDomain);
						}
						else if(numWorkflowId == 4 && numActionId == 18){
							//Update Project Status
							ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(numCustomId);
							TempProjectMasterDomain tempProjectMasterDomain =projectMasterDao.getTempProjectMasterDataById(numCustomId);
							projectMasterDomain.setStrProjectStatus("Completed");	
							projectMasterDomain.setProjectClosureDate(tempProjectMasterDomain.getProjectClosureDate());
							projectMasterDomain.setClosureRemarks(tempProjectMasterDomain.getClosureRemarks());
							projectMasterDomain.setClosureStatus("Technical");
							projectMasterDomain.setTechnicalClosureDate(tempProjectMasterDomain.getProjectClosureDate());
							projectMasterDao.mergeProjectMaster(projectMasterDomain);
							List<TempProjectDocumentMasterDomain> dataList=projectDocumentMasterDao.uploadedTempDocumentByProjectId(numCustomId);
							//List<EmployeeRoleMasterDomain> teamMem=employeeRoleMasterDao.getEmpOfTheProject(numCustomId,tempProjectMasterDomain.getProjectClosureDate());
							List<TempEmployeeRoleMasterDomain> teamMem=employeeRoleMasterDao.getDetailsOfTempEmpRole(numCustomId);
							
							if(teamMem.size()>0){
								for(int i=0;i<teamMem.size();i++){
									TempEmployeeRoleMasterDomain tempEmpDetails=teamMem.get(i);
									EmployeeRoleMasterDomain roleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterById(tempEmpDetails.getNumEmpRoleId());
									
									/*roleMasterDomain.setDtEndDate(tempEmpDetails.getDtEndDate());*/
									//Added by devesh on 17-10-23 to set involvement to zero after technical closure
									if(roleMasterDomain.getRoleMasterDomain().getNumId()==3 || roleMasterDomain.getRoleMasterDomain().getNumId()==15 || roleMasterDomain.getRoleMasterDomain().getNumId()==4){
										roleMasterDomain.setUtilization(0);
									}
									else {
										if (tempEmpDetails.getDtEndDate().compareTo(tempProjectMasterDomain.getProjectClosureDate()) > 0) {
							                roleMasterDomain.setDtEndDate(tempProjectMasterDomain.getProjectClosureDate());
							            }
										else{
											roleMasterDomain.setDtEndDate(tempEmpDetails.getDtEndDate());
										}										
										roleMasterDomain.setUtilization(0);
									}
									//End of condition
									employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(roleMasterDomain);
								}
							}
							
							if(null != dataList && dataList.size()>0){
								for(TempProjectDocumentMasterDomain master : dataList){
								ProjectDocumentMasterDomain projectDocumentMasterDomain = new ProjectDocumentMasterDomain();
								//parentTable.setNumId(master.getNumId());
								/*model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));*/
								projectDocumentMasterDomain.setProjectId(master.getProjectId());
								projectDocumentMasterDomain.setDtTrDate(new Date());
								projectDocumentMasterDomain.setNumIsValid(1);
								projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
								projectDocumentMasterDomain.setDocumentTypeMasterDomain(master.getDocumentTypeMasterDomain());
								projectDocumentMasterDomain.setDescription(master.getDescription());
								if(null != master.getDocumentDate() && !master.getDocumentDate().equals("")){
									
									projectDocumentMasterDomain.setDocumentDate(master.getDocumentDate());
								}else{
									projectDocumentMasterDomain.setDocumentDate(new Date());
								}
								
								if(null != master.getPeriodFrom() && !master.getPeriodFrom().equals("")){
									projectDocumentMasterDomain.setPeriodFrom(master.getPeriodFrom());
								}
								
								if(null != master.getPeriodTo() && !master.getPeriodTo().equals("")){
									projectDocumentMasterDomain.setPeriodTo(master.getPeriodTo());
								}
								
								projectDocumentMasterDomain.setDocumentVersion(master.getDocumentVersion());
								List<ProjectDocumentDetailsDomain> childDomainList = new ArrayList<ProjectDocumentDetailsDomain>();
								
								List<TempProjectDocumentDetailsDomain> childList = master.getProjectDocumentDetailsDomainList();
								for(int j=0;j<childList.size();j++){
									TempProjectDocumentDetailsDomain childDomain = childList.get(j);
									ProjectDocumentDetailsDomain childPDomain = new ProjectDocumentDetailsDomain();
									childPDomain.setDocumentFormatMaster(childDomain.getDocumentFormatMaster());
									childPDomain.setDocumentName(childDomain.getDocumentName());
									childPDomain.setOriginalDocumentName(childDomain.getOriginalDocumentName());
									//childPDomain.setDocumentFormatMaster(childDomain.getDocumentFormatMaster());
									childPDomain.setDtTrDate(new Date());
									childPDomain.setNumIsValid(1);
									childPDomain.setNumTrUserId(userInfo.getEmployeeId());
									childPDomain.setProjectDocumentMasterDomain(projectDocumentMasterDomain);
									childDomainList.add(childPDomain);
									/*projectDocumentMasterDao.updateProjectDocDetails(childPDomain);*/
								}
								
								projectDocumentMasterDomain.setProjectDocumentDetailsDomainList(childDomainList);
								try{
								projectDocumentMasterDao.merge(projectDocumentMasterDomain);	
								}
								catch(Exception e){
									e.printStackTrace();
								}
								}
						}}
					}else{
						isDataInsertedInTransaction=true;	
					}
				}
				else
					isDataInsertedInTransaction=true;	
				
				
				if( !isDataInsertedInTransaction){
					workflowModel2.setStrSuccessMsg("error");
					return workflowModel2;
				}else if(roleActionMappingList.size()>0 && roleActionMappingList.get(0).getStrSuccessMsg()!=null && !roleActionMappingList.get(0).getStrSuccessMsg().equals("")){
					workflowModel2.setStrSuccessMsg(roleActionMappingList.get(0).getStrSuccessMsg());
					return workflowModel2;
				}else
				workflowModel2.setStrSuccessMsg("");	
				
			}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			workflowModel2.setStrSuccessMsg("error");
		}
		
		
		return workflowModel2;
	}


	@Override
	public TransactionMasterDomain insertIntoTransaction(TransactionMasterModel trMasterModel) {
		
		TransactionMasterDomain transactionMasterDomain=null;
			TransactionMasterDomain transMstDomain=new TransactionMasterDomain();
			transMstDomain.setDtTrDate(trMasterModel.getDtTrDate());
			ActionMasterDomain actionMasterDomain=new ActionMasterDomain();
			RoleMasterDomain roleMasterDomain=new RoleMasterDomain();
			if(trMasterModel.getNumMonthlyProgressId() != 0){
				MonthlyProgressDomain monthlyProgressDomain=new MonthlyProgressDomain();
				monthlyProgressDomain.setNumId(trMasterModel.getNumMonthlyProgressId());
				transMstDomain.setMonthlyProgressDomain(monthlyProgressDomain);
			}
			
			WorkflowMasterDomain workflowDomain=new WorkflowMasterDomain();
			EmployeeMasterDomain employeeMasterDomain=new EmployeeMasterDomain();
			actionMasterDomain.setNumActionId(trMasterModel.getNumActionId());
			roleMasterDomain.setNumId(trMasterModel.getNumRoleId());
			transMstDomain.setCustomId(trMasterModel.getCustomId());
			employeeMasterDomain.setNumId(trMasterModel.getNumTrUserId());
			workflowDomain.setNumWorkflowId(trMasterModel.getNumWorkflowId());
			transMstDomain.setActionMasterDomain(actionMasterDomain);
			transMstDomain.setRoleMasterDomain(roleMasterDomain);
			
			transMstDomain.setWorkflowMasterDomain(workflowDomain);
			transMstDomain.setEmployeeMasterDomain(employeeMasterDomain);
			
			if(trMasterModel.getNumIsValid()==1){
				if(trMasterModel.getNumMonthlyProgressId() != 0){
					transactionDao.updateAllRowsWithNumIsValidZero(trMasterModel.getNumMonthlyProgressId());
				}else if(trMasterModel.getCustomId() != 0){
					transactionDao.updateAllRowsWithNumIsValidZero(trMasterModel.getCustomId(),trMasterModel.getNumWorkflowId());
				}
				
			}	
			transMstDomain.setNumIsValid(trMasterModel.getNumIsValid());
			
			if(trMasterModel.getStrRemarks()!=null && !trMasterModel.getStrRemarks().equals(""))
				transMstDomain.setStrRemarks(trMasterModel.getStrRemarks());
			transactionMasterDomain=transactionDao.save(transMstDomain);
			
		return transactionMasterDomain;				
	}


	@Override
	public List<RoleActionMappingDomain> getRoleActionMappingDetails(long numActionId, long numRoleId,
			long numWorkflowId) {
		
		return roleActionMappingDao.getRoleActionMappingDetails(numActionId, numRoleId, numWorkflowId);
	}


	@Override
	public boolean updateMonhlyProgressReport(MonthlyProgressModel monthlyProgressModel)
	{
		MonthlyProgressDomain monthlyProgressDomain=new MonthlyProgressDomain();
		boolean flag=false;
		
				 MonthlyProgressDomain monthlyProgressDomain2=null;
				 monthlyProgressDomain= monthlyProgressDao.getOne(monthlyProgressModel.getNumId());
				 monthlyProgressDomain.setStrRemarks(monthlyProgressModel.getStrRemarks());
				 monthlyProgressDomain.setDtTrDate(monthlyProgressModel.getDtTrDate());
				 monthlyProgressDomain.setSubmissionStatus(monthlyProgressModel.getSubmissionStatus());
				 monthlyProgressDomain2=monthlyProgressDao.save(monthlyProgressDomain);
				 if(monthlyProgressDomain2!=null)
				 flag=true;	 
				 
		
		return flag;
	}
	
	@Override
	public  boolean allowPreviewEdit(long numWorkflowId, long numRoleId, long numActionId) {
		
		boolean allow=false;
		List<ApprovalMasterDomain> approvalMasterDomainList=workflowDao.getPreviewApprovalDetails(numWorkflowId, numRoleId, numActionId);
		if(approvalMasterDomainList.size()>0){
			String actionIds=approvalMasterDomainList.get(0).getStrSecondPageActionIds()+","+approvalMasterDomainList.get(0).getStrFirstPageActionIds();
			//actionIds.concat(approvalMasterDomainList.get(0).getStrFirstPageActionIds());
			if(!actionIds.equalsIgnoreCase(null)){
				List<Integer> newList=new ArrayList<Integer>();
				String[] ids=actionIds.split(",");
				try {
			    	 List<String> list = Arrays.asList(ids);

						newList = list.stream().map(s -> Integer.parseInt(s))
													.collect(Collectors.toList());
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				allow=newList.contains(1);
			}
			
		}
		return allow;	
	
	}
	@Override
	public List<ApprovalMasterDomain> getProposedActions(long numWorkflowTypeId,long numRoleId, long numActionId,long numNextRoleId){
		return workflowDao.getProposedActions(numWorkflowTypeId, numRoleId, numActionId, numNextRoleId);
	}
	
	@Override
	public WorkflowModel getLatestTransactionDetails(int customId,long workflowId) {	
		WorkflowModel workflowModel=null;
		List<TransactionMasterDomain> transactionMasterDomainList=transactionDao.getLatestTransactionDetails(customId,workflowId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		if(null != transactionMasterDomainList && transactionMasterDomainList.size()>0){
			workflowModel=new WorkflowModel();
			TransactionMasterDomain domain = transactionMasterDomainList.get(0);
			workflowModel.setNumCurrentRoleId(userInfo.getSelectedEmployeeRole().getNumRoleId());
			workflowModel.setNumLastRoleId(domain.getRoleMasterDomain().getNumId());
			workflowModel.setNumLastActionId(domain.getActionMasterDomain().getNumActionId());
			workflowModel.setNumWorkflowId(workflowId);			
		}			
		return workflowModel;
	}
	
	@Override
	public WorkflowModel getLatestTransactionDetail(int customId,int workflowId){
		WorkflowModel model = new WorkflowModel();
		List<Object[]> list = workflowDao.getLatestTransactionDetail(customId, workflowId);
		if(null != list && list.size()>0){
			Object[] obj = list.get(0);
			model.setTransactionAt(DateUtils.dateToDateTimeString((Date) obj[0]));
			model.setStrRemarks((String) obj[1]);
			/*BigInteger b = (BigInteger) obj[2];
			model.setNumActionId(b.longValue());*/
			model.setEmployeeName((String) obj[3]);
			model.setStrActionName((String) obj[4]);
			model.setStrActionPerformed((String) obj[5]);
			/*-------------- add the last role id who performed the last action [26-09-2023] ---------------------------*/
			BigInteger b = (BigInteger) obj[6];
			model.setNumLastRoleId(b.longValue());
		}
		return model;
	}
	@Override
	public WorkflowModel getLatestTransactionDetailWithoutReject(int customId,int workflowId){
		WorkflowModel model = new WorkflowModel();
		List<Object[]> list = workflowDao.getLatestTransactionDetailWithoutReject(customId, workflowId);
		if(null != list && list.size()>0){
			Object[] obj = list.get(0);
			model.setTransactionAt(DateUtils.dateToDateTimeString((Date) obj[0]));
			model.setStrRemarks((String) obj[1]);
			/*BigInteger b = (BigInteger) obj[2];
			model.setNumActionId(b.longValue());*/
			model.setEmployeeName((String) obj[3]);
			model.setStrActionName((String) obj[4]);
			model.setStrActionPerformed((String) obj[5]);
		}
		return model;
	}
	
	@Override
	public List<WorkflowModel> getTransactionDetails(int customId,int workflowId){
		List<WorkflowModel> dataList  = new ArrayList<WorkflowModel>();
		List<Object[]> list = transactionDao.getTransactionDetails(customId, workflowId);
		for( int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
			WorkflowModel model = new WorkflowModel();
			model.setTransactionAt(DateUtils.dateToDateTimeString((Date) obj[0]));
			model.setStrRemarks((String) obj[1]);			
			model.setEmployeeName((String) obj[3]);
			model.setStrActionName((String) obj[4]);
			model.setStrActionPerformed((String) obj[5]);
			//Bhavesh(21-02-23) get the action id for milstone date edit
			BigInteger b = (BigInteger) obj[2];
			model.setNumActionId(b.longValue());
			dataList.add(model);			
		}
		return dataList;
	}
	
}
