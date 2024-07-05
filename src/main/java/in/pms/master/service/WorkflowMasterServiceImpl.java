package in.pms.master.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.global.model.WorkflowModel;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.WorkflowMasterDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WorkflowMasterServiceImpl implements WorkflowMasterService{

	@Autowired
	WorkflowMasterDao workflowMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<WorkflowModel> loadAllWorkFLow(){
		List<WorkflowMasterDomain> domainList = workflowMasterDao.getAlWorkflowDetails();
		if(null != domainList){
			return convertDomainToModelList(domainList);
		}
		return null;
		
	}

	public List<WorkflowModel> convertDomainToModelList(List<WorkflowMasterDomain> actionList){
		List<WorkflowModel> list = new ArrayList<WorkflowModel>();
			for(int i=0;i<actionList.size();i++){
				WorkflowMasterDomain workflowMasterDomain = actionList.get(i);
				WorkflowModel workflowModel = new WorkflowModel();
				workflowModel.setNumWorkflowId(workflowMasterDomain.getNumWorkflowId());
				workflowModel.setStrType(workflowMasterDomain.getStrType());
				if(workflowMasterDomain.getNumIsValid() ==1){
					workflowModel.setValid(true);
				}else{
					workflowModel.setValid(false);
				}
				list.add(workflowModel);
	}
		return list;
	}

	
	@Override
	public String checkDuplicateWorkflow(WorkflowModel workflowModel) {
		String result=	"";
		WorkflowMasterDomain workflowMasterDomain =  workflowMasterDao.getworkflow(workflowModel.getStrType());
			
		 if(null == workflowMasterDomain){
					return null; 
				 }else if(workflowModel.getNumWorkflowId()!= 0){
					 if(workflowMasterDomain.getStrType().equals(workflowModel.getStrType()))
					 {
						 return null; 
					 }
					 else{
						
						 result = "Workflow already exist!";
					}
				 }else{
					 if(workflowMasterDomain.getNumIsValid()==2)
					 {
						 result = " Workflow already exist in Delete Stage. Please Contact to Administrator! ";
					 }
					 else{
						 result = " Workflow already exist ! ";
					 }
						
					}
		
				return result;	
		}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public WorkflowModel saveUpdateWorkflow(WorkflowModel workflowModel){
		WorkflowMasterDomain workflowMasterDomain = convertModelToDomain(workflowModel);
		workflowMasterDao.save(workflowMasterDomain);
		return workflowModel;
	}

	private WorkflowMasterDomain convertModelToDomain(WorkflowModel workflowModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			WorkflowMasterDomain workflowMasterDomain = new WorkflowMasterDomain();
			if(workflowModel.getNumWorkflowId()!=0){				
				workflowMasterDomain =  workflowMasterDao.getOne(workflowModel.getNumWorkflowId());
			}
			if(workflowModel.isValid()){
				workflowMasterDomain.setNumIsValid(1);
			}else{
				workflowMasterDomain.setNumIsValid(0);
			}
			  
			workflowMasterDomain.setDtTrDate(new Date());
			workflowMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			//workflowMasterDomain.setNumIsValid(1);
			workflowMasterDomain.setStrType(workflowModel.getStrType());
			return workflowMasterDomain;
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public WorkflowModel deleteWorkflow(WorkflowModel workflowModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		WorkflowMasterDomain workflowMasterDomain = workflowMasterDao.getOne(workflowModel.getNumWorkflowId());
		if(workflowMasterDomain != null)
			workflowMasterDomain.setNumIsValid(2);
			workflowMasterDomain.setDtTrDate(new Date());
			workflowMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		workflowMasterDao.save(workflowMasterDomain).getNumWorkflowId();
		
		return workflowModel;
	}

	

}
