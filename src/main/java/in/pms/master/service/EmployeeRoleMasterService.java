package in.pms.master.service;

import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.domain.TempEmployeeRoleMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterModel;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

	
public interface EmployeeRoleMasterService {

	@Transactional	
	 public long saveUpdateEmployeeRoleMaster(EmployeeRoleMasterModel employeeRoleMasterModel);	 
	 
	 public EmployeeRoleMasterModel getEmployeeRoleMasterDomainById(long numId);
	
	 public List<EmployeeRoleMasterModel> getAllEmployeeRoleMasterDomain();
	
	 public List<EmployeeRoleMasterModel> getAllActiveEmployeeRoleMaster();	
	
	public List<EmployeeRoleMasterModel> getEmployeeRoleMasterByEmpId(long numEmpId);
	
	public List<EmployeeRoleMasterModel> getActiveEmployeeRoleByEmpId(long employeeId);
	
	public int getEmployeeHighestRoleHierarchy(long employeeId);
	
	public String getDistinctOrganisationsForEmployee(long employeeId);
	
	public String getDistinctGroupsForEmployee(long employeeId);
	
	public String getDistinctProjectsForEmployee(long employeeId);
	
	public JSONObject getProjectTeamDetails(String encProjectId);
	
	public List<Object[]> getEmployeeFromProject(int month,int Year);
	
	public List<EmployeeRoleMasterModel> getEmployeeRoleMasterDomain(long empId);

	public EmployeeRoleMasterModel getDefaultEmployeeRoleByEmpId(long numId);

	public String checkDuplicateEmpRoleMstDetailsData(EmployeeRoleMasterModel employeeRoleMasterModel);

	public JSONArray projectTeamWiseEmployees(long projectId,long roleId);
	
	@Transactional
	public long saveprojectTeamMapping(EmployeeRoleMasterModel employeeRoleMasterModel);
	
	@Transactional
	public String getEmployeeRoleMasterDomainByEmpId(long empId,long projectId,long roleId) ;

	@Transactional
	public boolean uncheckPrimaryRole(long empId,long projectId,long roleId) ;
	
	public List<EmployeeRoleMasterModel> getAllTeamDetailsByProject(String strProjectId);

	public JSONArray getManpowerRequiermentDetails(long numProjectId);
	
	public List<EmployeeRoleMasterModel> getAllTeamMembers();
	
	public List<Object[]> getTeamMIdsName();
	
	public List<EmployeeRoleMasterModel> getActiveEmployeeRoleByEmpIdNew(long employeeId);

	
	public List<EmployeeRoleMasterModel> getClosedProjectEmpListService(long projectId);
	
	public List<EmployeeRoleMasterModel> getAllTempTeamDetailsByProject(String strProjectId);

	//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
	public List<EmployeeRoleMasterModel> getAllTeamDetailsByProjectForAllEndDate(String strProjectId);

	public List<EmployeeRoleMasterModel> getAllTeamMembersProjectWise(); //Added by devesh on 30-11-23 to get team members according to projects
	
}
