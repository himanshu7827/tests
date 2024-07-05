package in.pms.transaction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.login.util.UserInfo;
import in.pms.master.model.ProcessMasterModel;
import in.pms.master.service.BusinessTypeService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectTypeService;
import in.pms.transaction.dao.ApplicationDao;
import in.pms.transaction.dao.WorkflowConfigurationDao;
import in.pms.transaction.domain.WorkflowTypeMaster;
import in.pms.transaction.domain.WorkflowTypeProcessMaster;
import in.pms.transaction.model.WorkflowConfigurationModel;

@Service
public class WorkflowConfiguationServiceImpl implements WorkflowConfigurationService {
	
	@Autowired
	BusinessTypeService businessTypeService;
	@Autowired
	ProjectTypeService projectTypeService;
	@Autowired
	ProjectCategoryService projectCategoryService;
	@Autowired
	OrganisationMasterService organisationMasterService;
	@Autowired
	GroupMasterService groupMasterService;	
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	ApplicationDao applicationDao;
	
	@Autowired
	WorkflowConfigurationDao workflowDao;

	@Override
	public WorkflowConfigurationModel saveUpdateWorkflowType(WorkflowConfigurationModel model) {

		WorkflowTypeMaster wfm = convertWorkflowModelToDomain(model);
		wfm = workflowDao.mergeApplication(wfm);
		model.setNumId(wfm.getNumId());
		return model;
	}
	
	
	
	
	public WorkflowTypeMaster convertWorkflowModelToDomain(WorkflowConfigurationModel model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		WorkflowTypeMaster domain  = new WorkflowTypeMaster();
		
		domain.setModuleTypeMaster(moduleService.getModuleById(model.getModuleTypeId()));	
		domain.setBusinessTypeMastertm(businessTypeService.getBusinessTypeById(model.getBusinessTypeId()));
		domain.setProjectCategoryMaster(projectCategoryService.getProjectCategoryById(model.getCategoryId()));
		domain.setProjectTypeMaster(projectTypeService.getProjectTypeById(model.getProjectTypeId()));
		domain.setNumTrUserId(userInfo.getEmployeeId());
		domain.setNumIsValid(1);
		domain.setDtTrDate(new Date());
		return domain;
	}


	public List<ProcessMasterModel> getProcessForWorkflow(int workflowTypeId) {
		List <WorkflowTypeProcessMaster> list= workflowDao.getProcessForWorkflow(workflowTypeId);
		return convertToProcessModel(list);
		
	}




	private List<ProcessMasterModel> convertToProcessModel(
			List<WorkflowTypeProcessMaster> list) {
		List<ProcessMasterModel> pmm=new ArrayList<ProcessMasterModel>();
		for (WorkflowTypeProcessMaster domain : list) {
			ProcessMasterModel model=new ProcessMasterModel();
			model.setStrProcessPath(domain.getControllerName());
			model.setStrProcessDescription(domain.getDescription());
			model.setStrProcessEcode(domain.getEcode());
			model.setNumProcessId(domain.getStepId());
			pmm.add(model);
		}
		return pmm;
	}


	public int getCurrentStepId(String path, int workflowTypeId) {
		return workflowDao.getCurrentStepId(path,workflowTypeId);
	}


	public String getNextRedirectPageURL(int workflowTypeId, int currentStepId) {
		return workflowDao.getNextRedirectPageURL(workflowTypeId,currentStepId);
	}

	public String getPrevRedirectPageURL(int workflowTypeId, int currentStepId) {
		return workflowDao.getPrevRedirectPageURL(workflowTypeId,currentStepId);
	}

	public List<WorkflowConfigurationModel> getAllTypes() {
		List<WorkflowTypeMaster> list=workflowDao.getAllTypes();
		return convertDomainTomodel(list);
	}

	private List<WorkflowConfigurationModel> convertDomainTomodel(
			List<WorkflowTypeMaster> list) {

		List<WorkflowConfigurationModel> wkm=new ArrayList<WorkflowConfigurationModel>();
		for (WorkflowTypeMaster domain : list) {
			WorkflowConfigurationModel model=new WorkflowConfigurationModel();
				model.setNumId(domain.getNumId());
				model.setBusinessTypeName(domain.getBusinessTypeMastertm().getBusinessTypeName());
				model.setProjectTypeName(domain.getProjectTypeMaster().getProjectTypeName());
				model.setCategoryName(domain.getProjectCategoryMaster().getCategoryName());
				model.setModuleTypeName(domain.getModuleTypeMaster().getModuleName());
				model.setValid(domain.getNumIsValid()==1?true:false);
				wkm.add(model);
				
		}
		return wkm;
	}

	public int getWorkflowTypeId(int moduleTypeId, long businessTypeId,
			int projectTypeId, int categoryId) {
		return workflowDao.getWorkflowTypeId(moduleTypeId,businessTypeId,projectTypeId,categoryId);
	}
	
	@PreAuthorize("hasAuthority('READ_APPROVAL_MST')")
	public List<WorkflowConfigurationModel> getAllWorkflow(){
		return convertWorkFlowDomainToModelList(workflowDao.getAllWorkflow());			
	}
	
	private List<WorkflowConfigurationModel> convertWorkFlowDomainToModelList(
			List<WorkflowMasterDomain> list) {

		List<WorkflowConfigurationModel> wkm=new ArrayList<WorkflowConfigurationModel>();
		for (WorkflowMasterDomain domain : list) {
			WorkflowConfigurationModel model=new WorkflowConfigurationModel();
				model.setNumId(domain.getNumWorkflowId());
				model.setStrWorkflowType(domain.getStrType());
				wkm.add(model);
				
		}
		return wkm;
	}
	

}
