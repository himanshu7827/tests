package in.pms.transaction.service;

import in.pms.global.dao.RoleActionMappingDao;
import in.pms.global.dao.TransactionDao;
import in.pms.global.domain.ApprovalMasterDomain;
import in.pms.global.domain.RoleActionMappingDomain;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.model.ProceedingModel;
import in.pms.global.model.TransactionMasterModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.WorkflowService;
import in.pms.global.util.DateUtils;
import in.pms.login.service.TransactionActivityService;
import in.pms.login.util.UserInfo;
import in.pms.mail.dao.SendMail;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.ProjectCategoryDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.NewsLetterFilterMapping;
import in.pms.master.domain.NewsLetterFilterMaster;
import in.pms.master.domain.NewsLetterMaster;
import in.pms.master.domain.NewsLetterRoleMapping;
import in.pms.master.domain.NewsletterDocuments;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.NewsLetterFilterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.dao.MonthlyProgressDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.domain.CategoryMaster;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.MonthlyProgressDomain;
import in.pms.transaction.model.ApprovedJobModel;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.OthersModel;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MonthlyProgressServiceImpl implements MonthlyProgressService {

	@Autowired
	MonthlyProgressDao monthlyProgressDao;

	@Autowired
	EmployeeMasterDao employeeMasterDao;

	@Autowired
	ProjectMasterService projectMasterService;

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	GroupMasterDao groupMasterDao;

	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	ProjectCategoryDao projectCategoryDao;
		
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	RoleActionMappingDao roleActionMappingDao;
	
	@Autowired
	GlobalDao globalDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	TransactionActivityService transactionActivityService;
	
	@Override
	public List<OthersModel> getAllOthers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private @Autowired AutowireCapableBeanFactory beanFactory;

	@Override
	public String  MonthlyProgress(int month, int year, long projectId, long groupId) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
		List<Long> ids=new ArrayList<Long>();
		int parentkey=0;
		MonthlyProgressDomain keydomain=monthlyProgressDao.getdetail(year, month, groupId, projectId);
		int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
		if(selectedRoleId!=5){
			//Check for PM Role
			List<EmployeeRoleMasterModel> allAssignedRoles = userInfo.getEmployeeRoleList();			 
			 if(null != allAssignedRoles && allAssignedRoles.size()>0){
				 List<EmployeeRoleMasterModel>	filteredRoles = allAssignedRoles.stream()			      
									        .filter(x -> 3 == x.getNumRoleId() && projectId == x.getNumProjectId()).collect(Collectors.toList());		
				 if(null != filteredRoles && filteredRoles.size()>0){
					 selectedRoleId = 3;
				 }
			 }			
		}
		if(keydomain==null && (selectedRoleId==3 || selectedRoleId ==5)){
			MonthlyProgressDomain domain= new MonthlyProgressDomain();
			domain.setNumIsValid(1);
			domain.setDtTrDate(new Date());
			domain.setNumTrUserId(userInfo.getEmployeeId());
			
			
			domain.setMonth(month);
			domain.setYear(year);
			domain.setProjectMasterDomain(projectMasterService.getProjectMasterDataById(projectId));
			domain.setGroupMaster(groupMasterDao.getGroupMasterById(groupId));
			//parentkey=monthlyProgressDao.save(domain).getNumId();
			//get cateory ids/parent id /parent seq with type P
			List<Object[]> categoryList= new ArrayList<Object[]>();
			categoryList=monthlyProgressDao.getCateoryDetail("P");
			List<MonthlyProgressDetails> childList = new ArrayList<MonthlyProgressDetails>();
			//save in child with primarykey
			if(categoryList.size()>0){
				for(int i=0;i<categoryList.size();i++){
					MonthlyProgressDetails childdomain=new MonthlyProgressDetails();
					Object[] obj=categoryList.get(i);
					childdomain.setDtTrDate(new Date());
					childdomain.setNumIsValid(1);
					childdomain.setNumTrUserId(userInfo.getEmployeeId());
					
					childdomain.setNumCateoryId(((Number)obj[0]).longValue());
					ids.add(childdomain.getNumCateoryId());
					childdomain.setNumParentCateory((int)obj[1]);
					childdomain.setNumCategorySequence((int)obj[2]);
					childdomain.setMonthlyProgressDomain(domain);
					//int childPrimaryKey=monthlyProgressDetailsDao.save(childdomain).getNumId();
					childList.add(childdomain);
				}
				domain.setMonthlyProgress(childList);
				
			}
			int parentId=monthlyProgressDao.save(domain).getNumId();
			return(encryptionService.encrypt(String.valueOf(parentId)));
		}
		else if(keydomain!=null){
			parentkey =keydomain.getNumId();

			return(encryptionService.encrypt(String.valueOf(parentkey)));
		}
		else
		{
			return "";
		}
		
	}
	
	@Override
	public MonthlyProgressModel getById(int numId){
		MonthlyProgressDomain domain = monthlyProgressDao.getOne(numId);
		if(null != domain){
			return convertDomainToModel(domain);
		}
		return null;
	}
	
	public MonthlyProgressModel convertDomainToModel(MonthlyProgressDomain domain){
		String[] arr=new String[]{"Jan","Feb","Mar","Apr","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		MonthlyProgressModel model = new MonthlyProgressModel();
		model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
		model.setGroupId(domain.getGroupMaster().getNumId());
		model.setStrGroupName(domain.getGroupMaster().getGroupName());
	//	System.out.println("dfcdfcd"+domain.getProjectMasterDomain().getNumId());
		model.setEncProjectId(encryptionService.encrypt(""+domain.getProjectMasterDomain().getNumId()));
		model.setStrProjectName(domain.getProjectMasterDomain().getStrProjectName());
		model.setStrProjectReferenceNo(domain.getProjectMasterDomain().getStrProjectRefNo());
		model.setProjectId(domain.getProjectMasterDomain().getNumId());
		model.setStrMonth(arr[domain.getMonth()-1]);
		model.setYear(domain.getYear());
		model.setEncGroupId(encryptionService.encrypt(""+domain.getGroupMaster().getNumId()));
		model.setMonth(domain.getMonth());
		return model;
	}
	
	public List<MonthlyProgressModel> getMonthlyProgressDetailsByPId(int monthlyProgressId){
		List<MonthlyProgressDetails> domain=monthlyProgressDetailsDao.getMonthlyProgressDetailsByPid(monthlyProgressId);
		return convertMonthlyProgressDetailsDomainToModel(domain);
	}
	
	private List<MonthlyProgressModel> convertMonthlyProgressDetailsDomainToModel(List<MonthlyProgressDetails> list)
	{
		MonthlyProgressDetails monthlyProgressDetails= new MonthlyProgressDetails();
		Iterator<MonthlyProgressDetails> itr= list.iterator();
		List<MonthlyProgressModel> result_list= new ArrayList<MonthlyProgressModel>();
		
		while(itr.hasNext())
		{
			MonthlyProgressModel monthlyProgressModel= new MonthlyProgressModel();
			monthlyProgressDetails= itr.next();
			int parentCategoryId = monthlyProgressDetails.getNumParentCateory();
			monthlyProgressModel.setNumParentCateory(monthlyProgressDetails.getNumParentCateory());
			monthlyProgressModel.setMonthlyProgressId(monthlyProgressDetails.getNumId());
			monthlyProgressModel.setEncNumId(encryptionService.encrypt(""+monthlyProgressDetails.getNumId()));
			monthlyProgressModel.setNumCateoryId(monthlyProgressDetails.getNumCateoryId());
			monthlyProgressModel.setEncCategoryId(encryptionService.encrypt(""+monthlyProgressDetails.getNumCateoryId()));
			monthlyProgressModel.setNumCategorySequence(monthlyProgressDetails.getNumCategorySequence());
			try{
			List<CategoryMaster> details=projectCategoryDao.getProjectCategoryByCatId(monthlyProgressDetails.getNumCateoryId());
		   if(details.size()>0){
			monthlyProgressModel.setStrCategoryName(details.get(0).getStrCategoryName());
		   }
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			if(monthlyProgressDetails.getNumParentCateory() == 0){
				result_list.add(monthlyProgressModel);
			}else{
				MonthlyProgressModel tempMonthlyProgressModel = result_list.stream()
						  .filter(model -> parentCategoryId== model.getNumCateoryId())
						  .findAny()
						  .orElse(null);
				
				if(null == tempMonthlyProgressModel){
					result_list.add(monthlyProgressModel);
				}else{
					
					int itemIndex = result_list.indexOf(tempMonthlyProgressModel);
					tempMonthlyProgressModel.getObjects().add(monthlyProgressModel);
					result_list.remove(itemIndex);
					result_list.add(itemIndex, tempMonthlyProgressModel);
				}
			}		
		}
		
		return result_list;
	}
	@Override
	public String  MonthlyProgressByGroup(int month, int year, long groupId) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
		List<Long> ids=new ArrayList<Long>();
		int parentkey=0;
		MonthlyProgressDomain keydomain=monthlyProgressDao.getdetail(year, month, groupId,0);
		if(keydomain==null){
			MonthlyProgressDomain domain= new MonthlyProgressDomain();
			domain.setNumIsValid(1);
			domain.setDtTrDate(new Date());
			domain.setNumTrUserId(userInfo.getEmployeeId());
			
			
			domain.setMonth(month);
			domain.setYear(year);
			domain.setProjectMasterDomain(projectMasterService.getProjectMasterDataById(0));
			domain.setGroupMaster(groupMasterDao.getGroupMasterById(groupId));
			//parentkey=monthlyProgressDao.save(domain).getNumId();
			//get cateory ids/parent id /parent seq with type P
			List<Object[]> categoryList= new ArrayList<Object[]>();
			categoryList=monthlyProgressDao.getCateoryDetail("G");
			List<MonthlyProgressDetails> childList = new ArrayList<MonthlyProgressDetails>();
			//save in child with primarykey
			if(categoryList.size()>0){
				for(int i=0;i<categoryList.size();i++){
					MonthlyProgressDetails childdomain=new MonthlyProgressDetails();
					Object[] obj=categoryList.get(i);
					childdomain.setDtTrDate(new Date());
					childdomain.setNumIsValid(1);
					childdomain.setNumTrUserId(userInfo.getEmployeeId());
					
					childdomain.setNumCateoryId(((Number)obj[0]).longValue());
					ids.add(childdomain.getNumCateoryId());
					childdomain.setNumParentCateory((int)obj[1]);
					childdomain.setNumCategorySequence((int)obj[2]);
					childdomain.setMonthlyProgressDomain(domain);
					//int childPrimaryKey=monthlyProgressDetailsDao.save(childdomain).getNumId();
					childList.add(childdomain);
				}
				domain.setMonthlyProgress(childList);
				
			}
			int parentId=monthlyProgressDao.save(domain).getNumId();
			return(encryptionService.encrypt(String.valueOf(parentId)));
		}
		else{
			parentkey =keydomain.getNumId();

			return(encryptionService.encrypt(String.valueOf(parentkey)));
		}
		
	
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public void writeProgressReportAuthorityCheck(){
		
	}
	
	@Override
	public List<ProjectMasterModel>  getProjectIdsForReport(int month, int year, String projectIds,int selectedProjectType){
		List<Long> newList=new ArrayList<Long>();
		List<MonthlyProgressDomain> getProjectIdsForReport=new ArrayList<MonthlyProgressDomain>();
		if(!projectIds.equalsIgnoreCase(null)){}
			
			String[] ids=projectIds.split(",");
			try {
		    	 List<String> list = Arrays.asList(ids);

					newList = list.stream().map(s -> Long.parseLong(s))
												.collect(Collectors.toList());
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		if(selectedProjectType==2){
			 getProjectIdsForReport=monthlyProgressDao.getProjectIdsForReport(year, month, newList);	
		}else{
		  getProjectIdsForReport=monthlyProgressDao.getProjectIdsForReportWithSelectedType(year, month, newList,selectedProjectType);
		}
		List<ProjectMasterModel> list=new ArrayList<ProjectMasterModel>();
		if(getProjectIdsForReport.size()>0){
			for(int i=0;i<getProjectIdsForReport.size();i++){
			ProjectMasterModel model=new ProjectMasterModel();
			model.setNumId(getProjectIdsForReport.get(i).getProjectMasterDomain().getNumId());
			model.setStrProjectName(getProjectIdsForReport.get(i).getProjectMasterDomain().getStrProjectName());
			list.add(model);
			}
			System.out.println("the size"+getProjectIdsForReport.size());
			return list;	
		}
		else{
			return null;
		}
		
	}
	
	@Override
	public List<CategoryMasterModel> getCategoryByParentId(String parentId){
		List<CategoryMasterModel> categories= new ArrayList<CategoryMasterModel>();
		long id = Long.parseLong(parentId);
		List<Object[]> dataList = monthlyProgressDao.getCategoryByParentId(id);
		for(Object[] obj : dataList){
			CategoryMasterModel model = new CategoryMasterModel();
			model.setEncCategoryId(encryptionService.encrypt(""+obj[0]));
			model.setStrCategoryName(""+obj[1]);
			model.setStrCategoryController(""+obj[2]);
			categories.add(model);
		}
		return categories;
	}
	
	@Override
	public List<CategoryMasterModel> getChildCategoryByParentId(String encParentId,String encCategoryId){
		List<CategoryMasterModel> categories= new ArrayList<CategoryMasterModel>();
		String strParentId = encryptionService.dcrypt(encParentId);
		String strCategoryId = encryptionService.dcrypt(encCategoryId);
		long parentId = Long.parseLong(strParentId);
		long categoryId = Long.parseLong(strCategoryId);
		List<Object[]> dataList = monthlyProgressDao.getChildCategoryByParentId(parentId,categoryId);
		for(Object[] obj : dataList){
			CategoryMasterModel model = new CategoryMasterModel();
			model.setEncCategoryId(encryptionService.encrypt(""+obj[0]));
			model.setStrCategoryName(""+obj[1]);
			model.setStrCategoryController(""+obj[2]);
			categories.add(model);
		}
		return categories;
	}
	
	@Override
	public int activeProgressReportsWithGCCount(long actionId){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo=  (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel employeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		int selectedRole = employeeRoleMasterModel.getNumRoleId();
		int selectedGroup= employeeRoleMasterModel.getNumGroupId();
		if(selectedRole == 5){
			return monthlyProgressDao.activeProgressReportsWithGCCount(selectedGroup,actionId);
		}		
		return 0;
	}
	
	@Override
	public List<MonthlyProgressModel> activeProgressReportsWithGCDetails(long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo=  (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel employeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		int selectedRole = employeeRoleMasterModel.getNumRoleId();
		int selectedGroup= employeeRoleMasterModel.getNumGroupId();
		if(selectedRole == 5){
			List<TransactionMasterDomain> transactionList =  monthlyProgressDao.activeProgressReportsWithGCDetails(selectedGroup,actionId);
			for(TransactionMasterDomain domain : transactionList){
				MonthlyProgressModel model = new MonthlyProgressModel();
				MonthlyProgressDomain monthlyProgressDomain = domain.getMonthlyProgressDomain();
				ProjectMasterDomain projectMasterDomain = monthlyProgressDomain.getProjectMasterDomain();
				model.setStrProjectName(projectMasterDomain.getStrProjectName());
				model.setMonth(monthlyProgressDomain.getMonth());
				model.setYear(monthlyProgressDomain.getYear());
				model.setStrProjectReferenceNo(projectMasterDomain.getStrProjectRefNo());
				model.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(monthlyProgressDomain.getNumId());
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+monthlyProgressDomain.getNumId()));
				model.setTransactionDate(DateUtils.dateToDateTimeString(domain.getDtTrDate()));
				dataList.add(model);
			}
		}		
		return dataList;
	}
	@Override
	public String getMinCategoryByPId(int monthlyProgressId){
		int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(monthlyProgressId);
		String strCategoryId=encryptionService.encrypt(""+categoryId);
		return strCategoryId;
	}
	
	@Override
	public List<String> sendToPMO(MonthlyProgressModel monthlyProgressModel){	
		List<String> outputList = new ArrayList<String>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		
		int proposedRoleId = selectedEmployeeRole.getNumRoleId();
		int proposedActionId = 6;
		RoleActionMappingDomain roleActionMapping = null;
		List<RoleActionMappingDomain> roleActionMappingDomains =  roleActionMappingDao.getRoleActionMappingDetails(proposedActionId, proposedRoleId, 1);
		if(null != roleActionMappingDomains && roleActionMappingDomains.size()>0){
			roleActionMapping = roleActionMappingDomains.get(0);
		}
		
		
		String encProgressDetailsIds = monthlyProgressModel.getEncProgressDetailsIds();
		
		if(null != encProgressDetailsIds && encProgressDetailsIds.length()>0){
			String[] tempEncProgressDetails = encProgressDetailsIds.split(",");
			for(String encProgressDetailsId : tempEncProgressDetails){
				String strProgressDetailId = encryptionService.dcrypt(encProgressDetailsId);
				int progressDetailId = Integer.parseInt(strProgressDetailId);
				List<TransactionMasterDomain> transactions = transactionDao.getTransactionDetails(progressDetailId);
				if(null != transactions){
					TransactionMasterDomain transactionDomain = transactions.get(0);
					long actionId = transactionDomain.getActionMasterDomain().getNumActionId();
					long roleId = transactionDomain.getRoleMasterDomain().getNumId();
					long workFlowTypeId = transactionDomain.getWorkflowMasterDomain().getNumWorkflowId();
					List<ApprovalMasterDomain> approvalList =  workflowService.getProposedActions(workFlowTypeId, roleId,actionId,proposedRoleId);
					if(null != approvalList && approvalList.size()>0){
						ApprovalMasterDomain approvalDomain = approvalList.get(0);
						String proposedActions = ","+approvalDomain.getStrFirstPageActionIds()+","+approvalDomain.getStrSecondPageActionIds()+",";
						TransactionMasterDomain currentTransaction = null;						
						if(proposedActions.contains(",6,")){
							try{
							if(null != roleActionMapping){
								TransactionMasterModel trMasterModel=new TransactionMasterModel();
								trMasterModel.setDtTrDate(new Date());
								trMasterModel.setNumActionId(proposedActionId);
								trMasterModel.setNumRoleId(proposedRoleId);
								trMasterModel.setNumIsValid(roleActionMapping.getNumTransactionImpact());								
								trMasterModel.setNumMonthlyProgressId(progressDetailId);
								trMasterModel.setNumWorkflowId(1);
								trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
								trMasterModel.setStrRemarks("Bulk Approval and forward to PMO");
								currentTransaction = workflowService.insertIntoTransaction(trMasterModel);
								
								if(roleActionMapping.getNumIsCopyCreate()==1){
									if(currentTransaction!=null){
										globalDao.createMonthlyProgressReportCopy(currentTransaction.getNumTransactionId(),progressDetailId);
									}else{
										globalDao.createMonthlyProgressReportCopy(0,progressDetailId);
									}							
								}
								
								if(roleActionMapping.getStrStatus()!=null && !roleActionMapping.getStrStatus().equals("")){
									MonthlyProgressModel progressModel=new MonthlyProgressModel();
									progressModel.setNumId(progressDetailId);
									progressModel.setDtTrDate(new Date());
									progressModel.setSubmissionStatus(roleActionMapping.getStrStatus());									
									workflowService.updateMonhlyProgressReport(progressModel);
								}
								MonthlyProgressModel monthlyProgressModel2 =getById(progressDetailId);
								outputList.add("Request saved for Project  "+monthlyProgressModel2.getStrProjectName() +" ("+monthlyProgressModel2.getStrProjectReferenceNo()+") of Month "+monthlyProgressModel2.getStrMonth()+"/"+monthlyProgressModel2.getYear());
							
								
							}else{
								outputList.add("No Action configured");
								return outputList;
							}
						}catch(Exception e){
							MonthlyProgressModel monthlyProgressModel2 =getById(progressDetailId);
							outputList.add("Something was not right with Project  "+monthlyProgressModel2.getStrProjectName() +" ("+monthlyProgressModel2.getStrProjectReferenceNo()+") of Month "+monthlyProgressModel2.getStrMonth()+"/"+monthlyProgressModel2.getYear());						
							e.printStackTrace();
						}
						}else{
							MonthlyProgressModel monthlyProgressModel2 =getById(progressDetailId);
							outputList.add("Selected Option is not allowed on Project  "+monthlyProgressModel2.getStrProjectName() +" ("+monthlyProgressModel2.getStrProjectReferenceNo()+") for Month "+monthlyProgressModel2.getStrMonth()+"/"+monthlyProgressModel2.getYear());
						
						}
					}else{
						MonthlyProgressModel monthlyProgressModel2 =getById(progressDetailId);
						outputList.add("Selected Option is not allowed on Project  "+monthlyProgressModel2.getStrProjectName() +" ("+monthlyProgressModel2.getStrProjectReferenceNo()+") for Month "+monthlyProgressModel2.getStrMonth()+"/"+monthlyProgressModel2.getYear());
					}
				}
			}
		}else{
			outputList.add("Please select Progress report(s)");
		}
		return outputList;
	}
	
	@Override
	public int allActiveProgressReportsbyGCCount(int year, int month,long actionId){

			return monthlyProgressDao.allActiveProgressReportsbyGCCount(year,month,actionId);
				
	}
	
	@Override
	public List<MonthlyProgressModel> activeProgressReportsDetailsbyGCforCurrentMonth(int year,int month,long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();

			List<Object[]> newDataList =  projectMasterDao.activeProgressReportsDetailsbyGCforCurrentMonth(year,month,actionId);
			for(Object[] obj : newDataList){
				MonthlyProgressModel model = new MonthlyProgressModel();
				
				BigInteger projectId=new BigInteger(""+obj[1]);
				BigInteger groupId=new BigInteger(""+obj[5]);
				GroupMasterDomain groupDetails=groupMasterDao.getGroupMasterById(groupId.longValue());
				if(groupDetails!=null){
					model.setStrGroupName(groupDetails.getGroupName());
				}
				ProjectMasterDomain datails=projectMasterDao.getProjectMasterDataById(projectId.longValue());
				model.setStrProjectName(datails.getStrProjectName());
				model.setMonth(new Integer(""+obj[2]));
				model.setYear(new Integer(""+obj[3]));
				model.setStrProjectReferenceNo(datails.getStrProjectRefNo());
				model.setEncProjectId(encryptionService.encrypt(""+datails.getNumId()));
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(new Integer(""+obj[0]));
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				model.setTransactionDate(DateUtils.dateToDateTimeString( (Date)obj[4]));
				model.setStrActionName((""+obj[6]));
				dataList.add(model);
			}
		return dataList;
	}
	
	@Override
	public List<MonthlyProgressModel> PendingProgressReportsAtPL(int year,int month,long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();
		String strMonth=Integer.toString(month);
		String strYear=Integer.toString(year);
		String startDate="01"+"/"+strMonth+"/"+strYear;
			//String lastDate=DateUtils.getLastDateOfMonth(startDate);
			String strLastDate=DateUtils.getLastDateOfMonth(startDate);
			String lastDate=strLastDate+"/"+strMonth+"/"+strYear;
			Date dtLastdate = null;
			if(null !=strLastDate  && !strLastDate.equals("")){
				try {
					dtLastdate = DateUtils.dateStrToDate(lastDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			List<Object[]> newDataList =  projectMasterDao.PendingProgressReportsAtPL(year,month,dtLastdate);
			for(Object[] obj : newDataList){
				MonthlyProgressModel model = new MonthlyProgressModel();
		
				BigInteger projectId=new BigInteger(""+obj[1]);
				if(obj[5]!=null){
				
					model.setStrGroupName(""+obj[5]);
				
				}
				else{
					model.setStrGroupName("");
				}
				/*try{*/
				ProjectMasterDomain datails=projectMasterDao.getProjectMasterDataById(projectId.longValue());
				if(datails!=null){
				if(datails.getStrProjectName()!=null || !datails.getStrProjectName().equals("") ){
				model.setStrProjectName(datails.getStrProjectName());
				}
				else{
					model.setStrProjectName("");
				}
				if(datails.getStrProjectRefNo()!=null && !datails.getStrProjectRefNo().equals("")){
					model.setStrProjectReferenceNo(datails.getStrProjectRefNo());
					}
					else{
						model.setStrProjectReferenceNo("");
					}
				
				model.setEncProjectId(encryptionService.encrypt(""+datails.getNumId()));
				}
				/*}
				catch(Exception e){
					e.printStackTrace();
				}*/
				if(obj[2]!=null){
				model.setMonth(new Integer(""+obj[2]));
				}
				else{
					model.setMonth(0);
				}
				if(obj[3]!=null){
				model.setYear(new Integer(""+obj[3]));
				}
				else{
					model.setYear(0);
				}
			
				
				if(obj[0]!=null){
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(new Integer(""+obj[0]));
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				}
				else{
					model.setEncCategoryId("");
					model.setEncNumId("");
				}
				if(obj[6]!=null){
				model.setStrActionName((""+obj[6]));
				}
				else{
					model.setStrActionName("Not yet Submitted");
				}
				if(obj[4]!=null){
				model.setTransactionDate(DateUtils.dateToDateTimeString( (Date)obj[4]));
				}
				else{
					model.setTransactionDate("");
				}
				dataList.add(model);
			}
		
		return dataList;
	}
	
	@Override
	public List<MonthlyProgressModel> PendingProgressReportsAtGC(int year,int month,long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();
		String strMonth=Integer.toString(month);
		String strYear=Integer.toString(year);
		String startDate="01"+"/"+strMonth+"/"+strYear;
		String strLastDate=DateUtils.getLastDateOfMonth(startDate);
		String lastDate=strLastDate+"/"+strMonth+"/"+strYear;
		Date dtLastdate = null;
		if(null !=strLastDate  && !strLastDate.equals("")){
			try {
				dtLastdate = DateUtils.dateStrToDate(lastDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
			List<Object[]> newDataList =  projectMasterDao.PendingProgressReportsAtGC(year,month,dtLastdate);
			for(Object[] obj : newDataList){
				MonthlyProgressModel model = new MonthlyProgressModel();
		
				BigInteger projectId=new BigInteger(""+obj[1]);
				/*BigInteger groupId=new BigInteger(""+obj[5]);
				GroupMasterDomain groupDetails=groupMasterDao.getGroupMasterById(groupId.longValue());
				if(groupDetails!=null){*/
					model.setStrGroupName(""+obj[5]);
				/*}*/
				ProjectMasterDomain datails=projectMasterDao.getProjectMasterDataById(projectId.longValue());
				model.setStrProjectName(datails.getStrProjectName());
				model.setMonth(new Integer(""+obj[2]));
				model.setYear(new Integer(""+obj[3]));
				model.setStrProjectReferenceNo(datails.getStrProjectRefNo());
				model.setEncProjectId(encryptionService.encrypt(""+datails.getNumId()));
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(new Integer(""+obj[0]));
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				model.setTransactionDate(DateUtils.dateToDateTimeString( (Date)obj[4]));
				model.setStrActionName((""+obj[6]));
				dataList.add(model);
			}
		
		return dataList;
	}

	@Override
	public List<MonthlyProgressModel> revisedReportAtPL(long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();

			List<Object[]> newDataList =  projectMasterDao.revisedReportAtPL(actionId);
			for(Object[] obj : newDataList){
				MonthlyProgressModel model = new MonthlyProgressModel();
		
				BigInteger projectId=new BigInteger(""+obj[1]);
				/*BigInteger groupId=new BigInteger(""+obj[5]);
				GroupMasterDomain groupDetails=groupMasterDao.getGroupMasterById(groupId.longValue());
				if(groupDetails!=null){*/
					model.setStrGroupName(""+obj[5]);
				/*}*/
				ProjectMasterDomain datails=projectMasterDao.getProjectMasterDataById(projectId.longValue());
				model.setStrProjectName(datails.getStrProjectName());
				model.setMonth(new Integer(""+obj[2]));
				model.setYear(new Integer(""+obj[3]));
				model.setStrProjectReferenceNo(datails.getStrProjectRefNo());
				model.setEncProjectId(encryptionService.encrypt(""+datails.getNumId()));
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(new Integer(""+obj[0]));
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				model.setTransactionDate(DateUtils.dateToDateTimeString( (Date)obj[4]));
				model.setStrActionName((""+obj[6]));
				dataList.add(model);
			}
		
		return dataList;
	}
	
	public List<MonthlyProgressModel> pendingProgressReportsAtPL(){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();
		String date = ResourceBundleFile.getValueFromKey("dateForPendingReportsAtPL").trim();
		String fixStrPRDate = ResourceBundleFile.getValueFromKey("dateForProgressReport").trim();
		Date fixDtPRDate=null;
		int startMonth=0;
		String strLastDate="";
		String lastDate="";
	/*	try {
			 fixDtPRDate = DateUtils.dateStrToDate(fixStrPRDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
			try {
				fixDtPRDate = dateFormat.parse(fixStrPRDate);
		       
			} catch (ParseException e) {
				e.printStackTrace();				
			}
		
		int fixDay= Integer.parseInt(date);
		Date currentdate=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fixDtPRDate);
		startMonth=calendar.get(Calendar.MONTH) + 1;
		LocalDate now = LocalDate.now(); 
		int month=now.getMonthOfYear();
		int year=now.getYear();
		int lastMonth=0;
		int currentDay=now.getDayOfMonth();
		
		Date dtLastdate = null;
		String currentDate=DateUtils.dateToString(now.toDate());
		if(currentDay>=fixDay){
			 strLastDate=DateUtils.getLastDateOfMonth(currentDate);
			 lastDate=strLastDate+"/"+month+"/"+year;
			
			if(null !=strLastDate  && !strLastDate.equals("")){
				try {
					dtLastdate = dateFormat.parse(lastDate);
					
					Calendar cal = Calendar.getInstance();
					calendar.setTime(dtLastdate);
					lastMonth=calendar.get(cal.MONTH) + 1;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}		else{
			now =now.minusMonths(1);
			int yr=now.getYear();
			lastMonth=now.getMonthOfYear();
			currentDate=DateUtils.dateToString(now.toDate());
			
				 strLastDate=DateUtils.getLastDateOfMonth(currentDate);
				 lastDate=strLastDate+"/"+lastMonth+"/"+yr;
				
				if(null !=strLastDate  && !strLastDate.equals("")){
					
					
					try {
						dtLastdate = dateFormat.parse(lastDate);
				       
					} catch (ParseException e) {
						e.printStackTrace();				
					}
				}
		}
		List<Object[]> newDataList =  projectMasterDao.pendingProgressReportsAtPL(fixStrPRDate,lastDate, fixDay);
		for(Object[] obj : newDataList){
			MonthlyProgressModel model = new MonthlyProgressModel();
			model.setStrProjectName(""+obj[1]);
			model.setStrProjectReferenceNo(""+obj[6]);
			/*----- If MonthlyProgress Id exists than set last action and transaction date [21-05-2024] -------*/
			if(obj[0]!=null){
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				List<ProceedingModel> proceedingList=transactionActivityService.getProceedings(Integer.parseInt(""+obj[0]));
				if(proceedingList!=null){
					model.setStrActionName(proceedingList.get(0).getStrAction());
					model.setTransactionDate(proceedingList.get(0).getStrDateTime());
					//setStrName
				}
			}
			/*---- End of Code -------*/
			Long projectId = Long.parseLong(""+obj[7]);
			model.setProjectId(projectId);
			/*------- Check Project Manager for that Project and If exists set True else False [21-05-2024] -------*/
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			List<EmployeeRoleMasterModel> allAssignedRoles = userInfo.getEmployeeRoleList();
			List<EmployeeRoleMasterModel> filteredPMRoles = new ArrayList<EmployeeRoleMasterModel>();
			if(null != allAssignedRoles && allAssignedRoles.size()>0){
				filteredPMRoles = allAssignedRoles.stream()			      
				        .filter(x -> 3 == x.getNumRoleId() && projectId == x.getNumProjectId()).collect(Collectors.toList());
			}
			if(null != filteredPMRoles && filteredPMRoles.size()>0)	{
				model.setStrRemarks("True");
			}else{
				model.setStrRemarks("False");
			}
			/*------- End of Check Project Manager for that Project and If exists set True else False [21-05-2024] -------*/			
			model.setEncProjectId(encryptionService.encrypt(""+projectId.longValue()));
			String encProjectId = encryptionService.encrypt(""+projectId.longValue());
			model.setStrGroupName(""+obj[5]);
			if(obj[2]!=null){
			double dbmonth=new Double(""+obj[2]);
			model.setMonth((int) dbmonth);
			}
			else{
				model.setMonth(0);
			}
			if(obj[3]!=null){
				double dbyear=new Double(""+obj[3]);
				model.setYear((int) dbyear);
			}
			else{
				model.setYear(0);
			}
	
			dataList.add(model);
		}
	return dataList;

	}
	
	@Override
	public int SentForRevisionCount(long actionId){
		int count=0;
		List<Object[]> newDataList =  projectMasterDao.revisedReportAtPL(actionId);
		if(newDataList!=null && !newDataList.isEmpty()){
		count=newDataList.size();
		}
		else{
			count=0;
		}
		return count;
	}
	
	@Override
	public List<MonthlyProgressModel> PendingProgressReportsAtPI(int year,int month,long actionId){
		List<MonthlyProgressModel> dataList = new ArrayList<MonthlyProgressModel>();
		String strMonth=Integer.toString(month);
		String strYear=Integer.toString(year);
		String startDate="01"+"/"+strMonth+"/"+strYear;
		String strLastDate=DateUtils.getLastDateOfMonth(startDate);
		String lastDate=strLastDate+"/"+strMonth+"/"+strYear;
		Date dtLastdate = null;
		if(null !=strLastDate  && !strLastDate.equals("")){
			try {
				dtLastdate = DateUtils.dateStrToDate(lastDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
			List<Object[]> newDataList =  projectMasterDao.PendingProgressReportsAtPI(year,month,dtLastdate,actionId);
			for(Object[] obj : newDataList){
				MonthlyProgressModel model = new MonthlyProgressModel();
		
				BigInteger projectId=new BigInteger(""+obj[1]);
			
				model.setStrGroupName(""+obj[5]);
				ProjectMasterDomain datails=projectMasterDao.getProjectMasterDataById(projectId.longValue());
				model.setStrProjectName(datails.getStrProjectName());
				model.setMonth(new Integer(""+obj[2]));
				model.setYear(new Integer(""+obj[3]));
				model.setStrProjectReferenceNo(datails.getStrProjectRefNo());
				model.setEncProjectId(encryptionService.encrypt(""+datails.getNumId()));
				int categoryId=monthlyProgressDetailsDao.getMinCategoryByPId(new Integer(""+obj[0]));
				model.setEncCategoryId(encryptionService.encrypt(""+categoryId));
				model.setEncNumId(encryptionService.encrypt(""+obj[0]));
				model.setTransactionDate(DateUtils.dateToDateTimeString( (Date)obj[4]));
				model.setStrActionName((""+obj[6]));
				dataList.add(model);
			}
		
		return dataList;
	}
	
	@Override
	public void getPendingMonthlyProgReportAtGC() {

		
		Calendar calendar = GregorianCalendar.getInstance();
		String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
		int currentMonth=calendar.get(Calendar.MONTH)+1;
		int numMonth=calendar.get(Calendar.MONTH);
		String month = monthName[numMonth];
		int currentYear=calendar.get(Calendar.YEAR);
		String startDate="01"+"/"+currentMonth+"/"+currentYear;
		String strLastDate=DateUtils.getLastDateOfMonth(startDate);
		String lastDate=strLastDate+"/"+currentMonth+"/"+currentYear;
		Date dtLastdate = null;
		SendMail smail = new SendMail();
		beanFactory.autowireBean(smail);
		if(null !=strLastDate  && !strLastDate.equals("")){
			try {
				dtLastdate = DateUtils.dateStrToDate(lastDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		Map<String, List<Long>> dataMap = new LinkedHashMap<String, List<Long>>();
		List<Object[]> details = projectMasterDao.getPendingMonthlyProgReportAtGC(currentMonth,currentYear,dtLastdate);
		for(Object[] obj : details){
			String groupName=""+obj[1];
			int projectId=new Integer(""+obj[2]);
			
			if(dataMap.containsKey(groupName)){
				List<Long> pIds = dataMap.get(groupName);
				pIds.add((long) projectId);
				dataMap.put(groupName, pIds);
			}else{
				List<Long> pIds =  new ArrayList<Long>();
				pIds.add((long) projectId);
				dataMap.put(groupName, pIds);
			}
		}
		for (Map.Entry<String,List<Long>> entry : dataMap.entrySet()){
				String groupName=entry.getKey();
				List<Long> projectIds =  entry.getValue();	
			
				StringBuilder projectName = new StringBuilder();
				List<String> projectNames=monthlyProgressDao.getProjectNameById(projectIds);
								if(projectNames.size()!=0){
									projectName.append("<ol>");
									for(int i=0;i<projectNames.size();i++){
										String pName=projectNames.get(i);
										projectName.append(" <li> "+pName+"</li>");
									}
									projectName.append("</ol>");
								}
				//String projects=projectNames.replaceAll(",","\n");
				String mailId=projectMasterDao.getGCName(groupName);
				EmployeeMasterDomain detail=employeeMasterDao.findByEmail(mailId);
				try {
					String mailContent=ResourceBundleFile.getValueFromKey("ProgressReport_Content"); 
					 mailContent = mailContent.replaceAll("\\$USER_NAME\\$", detail.getEmployeeName());
					 mailContent = mailContent.replaceAll("\\$PROJECTS_NAME\\$", projectName+"");
					 mailContent = mailContent.replaceAll("\\$MONTH\\$", month+"");
					 mailContent = mailContent.replaceAll("\\$YEAR\\$", currentYear+"");
					smail.TransferToMailServer(mailId, "Pending Monthly Progress Report",mailContent);
				} catch (Exception e) {					
					e.printStackTrace();
				}
		}	
	}
	
}