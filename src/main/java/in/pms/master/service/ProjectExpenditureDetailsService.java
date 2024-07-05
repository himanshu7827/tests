package in.pms.master.service;

import in.pms.master.model.ProjectExpenditureDetailsModel;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectExpenditureDetailsService {

	@Transactional	
	 public long saveUpdateProjectExpenditureDetails(ProjectExpenditureDetailsModel projectExpenditureDetailsModel);		
	
	// public String checkDuplicateProjectExpenditureDetailsId(ProjectExpenditureDetailsModel projectExpenditureDetailsModel);
	 
	 public ProjectExpenditureDetailsModel getProjectExpenditureDetailsDomainById(long numId);
	
	 public List<ProjectExpenditureDetailsModel> getAllProjectExpenditureDetailsDomain();
	
	 public List<ProjectExpenditureDetailsModel> getAllActiveProjectExpenditureDetailsDomain();
	
	/*@Transactional
	public long deleteProjectPaymentSchedule(EmployeeRoleMasterModel projectPaymentScheduleMasterModel);
*/

	public List<ProjectExpenditureDetailsModel> getProjectExpenditureDetailsByProjectId(long numProjectId);
	
	public List<ProjectExpenditureDetailsModel> getExpenditureDetailsByProjectId(long numProjectId);
	
}
