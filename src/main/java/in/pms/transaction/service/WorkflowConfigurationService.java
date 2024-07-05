package in.pms.transaction.service;

import java.util.List;

import javax.transaction.Transactional;

import in.pms.master.model.ProcessMasterModel;
import in.pms.transaction.model.WorkflowConfigurationModel;

public interface WorkflowConfigurationService {

	@Transactional
	public WorkflowConfigurationModel saveUpdateWorkflowType(WorkflowConfigurationModel model);

	public List<ProcessMasterModel> getProcessForWorkflow(int workflowTypeId);
	
	public int getCurrentStepId(String path,int workflowTypeId);
	
	public String getNextRedirectPageURL(int workflowTypeId,int  currentStepId);
	
	public String getPrevRedirectPageURL(int workflowTypeId, int currentStepId);

	public List<WorkflowConfigurationModel> getAllTypes();

	public int getWorkflowTypeId(int moduleTypeId, long businessTypeId,int projectTypeId, int categoryId);
	
	public List<WorkflowConfigurationModel> getAllWorkflow();
}
