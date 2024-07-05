package in.pms.transaction.service;

import in.pms.transaction.model.ProjectBudgetForm;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;




public interface ProjectBudgetService {	
	
	@Transactional
	public long saveProjectDetailsData(ProjectBudgetForm projectMasterForm);	
	
	public List<ProjectBudgetForm> getBudgetDetailByProjectIdYear(long projectId,int year);
	
	public Map<String,Map<String,Float>> getProjectBudget(long projectId);
	
	@Transactional
	public boolean saveProjectBudgetDetails(ProjectBudgetForm projectMasterForm);

}
