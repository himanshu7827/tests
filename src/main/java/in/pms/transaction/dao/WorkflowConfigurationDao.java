package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.transaction.domain.WorkflowTypeMaster;
import in.pms.transaction.domain.WorkflowTypeProcessMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WorkflowConfigurationDao {
	@Autowired
	DaoHelper daoHelper;
	
	public WorkflowTypeMaster mergeApplication(WorkflowTypeMaster application){
		return daoHelper.merge(WorkflowTypeMaster.class, application);
	}

	public List<WorkflowTypeProcessMaster> getProcessForWorkflow(int workflowTypeId) {
		String query="From WorkflowTypeProcessMaster a where a.workflowTypeMaster= "+workflowTypeId+" and a.numIsValid=1 order by stepId ";
		return daoHelper.findByQuery(query);
	}

	public int getCurrentStepId(String path, int workflowTypeId) {
		String query="from WorkflowTypeProcessMaster where controllerName ="+path+" and workflowTypeMaster="+workflowTypeId;
		WorkflowTypeProcessMaster wtp= (WorkflowTypeProcessMaster) daoHelper.findByQuery(query).get(0);
		if(wtp!=null)
			return wtp.getStepId();
		else return 0;
	}

	public String getNextRedirectPageURL(int workflowTypeId, int currentStepId) {
		String query="from WorkflowTypeProcessMaster where numIsValid=1 and stepId="+(currentStepId+1)+" and workflowTypeMaster="+workflowTypeId;
		WorkflowTypeProcessMaster wtp= (WorkflowTypeProcessMaster) daoHelper.findByQuery(query).get(0);
		if(wtp!=null)
			return wtp.getControllerName();
		else return null;
	}
	
	public String getPrevRedirectPageURL(int workflowTypeId, int currentStepId) {
		String query="from WorkflowTypeProcessMaster where stepId="+(currentStepId-1)+" and workflowTypeMaster="+workflowTypeId;
		WorkflowTypeProcessMaster wtp= (WorkflowTypeProcessMaster) daoHelper.findByQuery(query).get(0);
		if(wtp!=null)
			return wtp.getControllerName();
		else return null;
	}

	public List<WorkflowTypeMaster> getAllTypes() {
		
		String query="select p from WorkflowTypeMaster p where p.numIsValid=1 order by numId";
		return daoHelper.findByQuery(query);	
	}

	public int getWorkflowTypeId(int moduleTypeId, long businessTypeId,
			int projectTypeId, int categoryId) {
		if(moduleTypeId==1){
			String query="from WorkflowTypeMaster where numIsValid=1  and moduleTypeMaster="+moduleTypeId+" and projectCategoryMaster="+categoryId;
			List<WorkflowTypeMaster> list=daoHelper.findByQuery(query);
			if(list.size()>0)
				return list.get(0).getNumId();
			else return 0;
		}else{
		String query="from WorkflowTypeMaster where numIsValid=1 and projectTypeMaster="+projectTypeId+"  and moduleTypeMaster="+moduleTypeId+" and projectCategoryMaster="+categoryId;
		List<WorkflowTypeMaster> list=daoHelper.findByQuery(query);
		if(list.size()>0)
			return list.get(0).getNumId();
		else return 0;
		}
	}
	
		public List<WorkflowMasterDomain> getAllWorkflow() {
		
		String query="select p from WorkflowMasterDomain p where p.numIsValid=1 order by numWorkflowId";
		return daoHelper.findByQuery(query);	
	}

}
