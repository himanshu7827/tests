package in.pms.master.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.model.WorkflowModel;

import java.util.List;

import javax.transaction.Transactional;

public interface WorkflowMasterService {

	@Transactional
	public WorkflowModel saveUpdateWorkflow(WorkflowModel actionModel);

	@Transactional
	public WorkflowModel deleteWorkflow(WorkflowModel actionModel);

	@Transactional
	public List<WorkflowModel> loadAllWorkFLow();

	public String checkDuplicateWorkflow(WorkflowModel workflowModel);

}
