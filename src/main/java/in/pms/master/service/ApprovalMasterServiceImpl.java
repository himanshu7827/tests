package in.pms.master.service;

import in.pms.global.dao.RoleActionMappingDao;
import in.pms.global.dao.WorkflowDao;
import in.pms.global.domain.ActionMasterDomain;
import in.pms.global.domain.ApprovalMasterDomain;
import in.pms.global.domain.RoleActionMappingDomain;
import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.ApprovalMasterModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class ApprovalMasterServiceImpl implements ApprovalMasterService {
	
	@Autowired
	WorkflowDao workflowDao;
	
	@Autowired
	RoleActionMappingDao roleActionMappingDao;
	

	@Override
	public ApprovalMasterModel getApprovalDetails(long numWorkflowId, long numLastRoleId, long numLastActionId){
		List<ApprovalMasterDomain> list=workflowDao.getApprovalDetailsbyIds(numWorkflowId,numLastRoleId,numLastActionId);
		List<RoleActionMappingDomain> list1=roleActionMappingDao.getRoleActionMappingDetails(numLastActionId,numLastRoleId,numWorkflowId);
		ApprovalMasterModel approvalMasterModel = new ApprovalMasterModel();
		if(list.size()>0){
		ApprovalMasterDomain approvalMasterDomain = list.get(0);

		int[] firstPageActionIds = null;
		int[] secondPageActionIds = null;

		try {
	    	
			String[] arrFirstPageActionIds=approvalMasterDomain.getStrFirstPageActionIds().split(",");
			String[] arrSecondPageActionIds=approvalMasterDomain.getStrSecondPageActionIds().split(",");
			    
			firstPageActionIds=Arrays.stream(arrFirstPageActionIds).mapToInt(Integer::parseInt).toArray();
			secondPageActionIds=Arrays.stream(arrSecondPageActionIds).mapToInt(Integer::parseInt).toArray();
				
		}catch (Exception e) {
			// TODO: handle exception
		}
		approvalMasterModel.setFirstPageAction(firstPageActionIds);
		approvalMasterModel.setSecondPageAction(secondPageActionIds);
		approvalMasterModel.setNextRoleId(approvalMasterDomain.getNumNextRoleId());
		approvalMasterModel.setNumApprovalId(approvalMasterDomain.getNumApprovalId());
		}
		if(list1.size()>0){
		RoleActionMappingDomain roleActionMappingDomain = list1.get(0);
		approvalMasterModel.setCopyTobeCreated(roleActionMappingDomain.getNumIsCopyCreate());
		approvalMasterModel.setTransactionImpact(roleActionMappingDomain.getNumTransactionImpact());
		approvalMasterModel.setStatusTobeUpdated(roleActionMappingDomain.getStrStatus());
		approvalMasterModel.setNumRoleActionId(roleActionMappingDomain.getNumRoleActionMapId());
		}
		
		return approvalMasterModel;
	}

	public List<ApprovalMasterModel> convertDomainToModelList(List<ApprovalMasterDomain> appList){
		List<ApprovalMasterModel> list = new ArrayList<ApprovalMasterModel>();
		
		for(int i=0;i<appList.size();i++){
				ApprovalMasterDomain approvalMasterDomain = appList.get(i);
				ApprovalMasterModel approvalMasterModel = new ApprovalMasterModel();
		
				int[] firstPageActionIds = null;
				int[] secondPageActionIds = null;

				try {
			    	
					String[] arrFirstPageActionIds=approvalMasterDomain.getStrFirstPageActionIds().split(",");
					String[] arrSecondPageActionIds=approvalMasterDomain.getStrSecondPageActionIds().split(",");
					    
					firstPageActionIds=Arrays.stream(arrFirstPageActionIds).mapToInt(Integer::parseInt).toArray();
					secondPageActionIds=Arrays.stream(arrSecondPageActionIds).mapToInt(Integer::parseInt).toArray();
						
				}catch (Exception e) {
					// TODO: handle exception
				}
				approvalMasterModel.setFirstPageAction(firstPageActionIds);
				approvalMasterModel.setSecondPageAction(secondPageActionIds);
				approvalMasterModel.setNextRoleId(approvalMasterDomain.getNumNextRoleId());
				list.add(approvalMasterModel);
	}
		return list;
	}
	
	public List<ApprovalMasterModel> convertRoleActionDomainToModelList(List<RoleActionMappingDomain> roleList){
		List<ApprovalMasterModel> list = new ArrayList<ApprovalMasterModel>();
			for(int i=0;i<roleList.size();i++){
				RoleActionMappingDomain roleActionMappingDomain = roleList.get(i);
				ApprovalMasterModel approvalMasterModel = new ApprovalMasterModel();
				approvalMasterModel.setCopyTobeCreated(roleActionMappingDomain.getNumIsCopyCreate());
				approvalMasterModel.setTransactionImpact(roleActionMappingDomain.getNumTransactionImpact());
				approvalMasterModel.setStatusTobeUpdated(roleActionMappingDomain.getStrStatus());
				list.add(approvalMasterModel);
	}
		return list;
	}
	
	

	@Override
	public ApprovalMasterModel saveApprovalDetails(ApprovalMasterModel approvalMasterModel){
		ApprovalMasterDomain approvalMasterDomain = convertModelToDomain(approvalMasterModel);
		RoleActionMappingDomain roleActionMappingDomain = convertRoleActionModelToDomain(approvalMasterModel);
		workflowDao.save(approvalMasterDomain).getNumApprovalId();
		roleActionMappingDao.save(roleActionMappingDomain);
		return approvalMasterModel;
	}
	
	public ApprovalMasterDomain convertModelToDomain(ApprovalMasterModel approvalMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ApprovalMasterDomain approvalMasterDomain = new ApprovalMasterDomain();
		if(approvalMasterModel.getNumApprovalId()!=0){		
			approvalMasterDomain =  workflowDao.getOne(approvalMasterModel.getNumApprovalId());
		}
		//approvalMasterDomain.setNumApprovalId(approvalMasterModel.getNumId());
		RoleMasterDomain roleMasterDomain=new RoleMasterDomain();
		roleMasterDomain.setNumId(approvalMasterModel.getRoleId());
		approvalMasterDomain.setRoleMasterDomain(roleMasterDomain);
		ActionMasterDomain actionMasterDomain=new ActionMasterDomain();
		actionMasterDomain.setNumActionId(approvalMasterModel.getActionId());
		approvalMasterDomain.setActionMasterDomain(actionMasterDomain);
		WorkflowMasterDomain workflowMasterDomain=new WorkflowMasterDomain();
		workflowMasterDomain.setNumWorkflowId(approvalMasterModel.getWorkshopId());
		approvalMasterDomain.setWorkflowMasterDomain(workflowMasterDomain);
		
		int[] actions = approvalMasterModel.getFirstPageAction();
		String nxtFirstRoleActions ="";
		
		for(int i=0;i<actions.length;i++)
		{	
			nxtFirstRoleActions += actions[i]+",";
		}
		
		String strFirstPageActionIds = nxtFirstRoleActions.substring(0, nxtFirstRoleActions.lastIndexOf(","));
		approvalMasterDomain.setStrFirstPageActionIds(strFirstPageActionIds);
		
		int[] action = approvalMasterModel.getSecondPageAction();
		String nxtSecondRoleActions ="";
		
		for(int i=0;i<action.length;i++)
		{
			nxtSecondRoleActions += action[i]+",";
		}
		
		String strSecondPageActionIds = nxtSecondRoleActions.substring(0, nxtSecondRoleActions.lastIndexOf(","));
		approvalMasterDomain.setStrSecondPageActionIds(strSecondPageActionIds);
		approvalMasterDomain.setNumNextRoleId(approvalMasterModel.getNextRoleId());
		approvalMasterDomain.setNumIsValid(1);
		approvalMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		approvalMasterDomain.setDtTrDate(new Date());

		return approvalMasterDomain;
	}
	
	public RoleActionMappingDomain convertRoleActionModelToDomain(ApprovalMasterModel approvalMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		RoleActionMappingDomain roleActionMappingDomain = new RoleActionMappingDomain();
		if(approvalMasterModel.getNumRoleActionId()!=0){		
			roleActionMappingDomain =  roleActionMappingDao.getOne(approvalMasterModel.getNumRoleActionId());
		}
		//roleActionMappingDomain.setNumRoleActionMapId(approvalMasterModel.getNumId());
		RoleMasterDomain roleMasterDomain=new RoleMasterDomain();
		roleMasterDomain.setNumId(approvalMasterModel.getRoleId());
		roleActionMappingDomain.setRoleMasterDomain(roleMasterDomain);
		ActionMasterDomain actionMasterDomain=new ActionMasterDomain();
		actionMasterDomain.setNumActionId(approvalMasterModel.getActionId());
		roleActionMappingDomain.setActionMasterDomain(actionMasterDomain);
		WorkflowMasterDomain workflowMasterDomain=new WorkflowMasterDomain();
		workflowMasterDomain.setNumWorkflowId(approvalMasterModel.getWorkshopId());
		roleActionMappingDomain.setWorkflowMasterDomain(workflowMasterDomain);
		roleActionMappingDomain.setNumTransactionImpact(approvalMasterModel.getTransactionImpact());
		roleActionMappingDomain.setStrStatus(approvalMasterModel.getStatusTobeUpdated());
		roleActionMappingDomain.setNumIsCopyCreate(approvalMasterModel.getCopyTobeCreated());
		roleActionMappingDomain.setNumIsValid(1);
		roleActionMappingDomain.setNumTrUserId(userInfo.getEmployeeId());
		roleActionMappingDomain.setDtTrDate(new Date());

		return roleActionMappingDomain;
	}
	
}
