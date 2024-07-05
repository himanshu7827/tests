package in.pms.master.service;

import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.transaction.model.MiscDataModel;
import in.pms.transaction.model.MonthlyProgressModel;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeMasterService {

	@Transactional
	public long saveUpdateEmployeeMaster(EmployeeMasterModel employeeMasterModel);
	
	public String checkDuplicateCorporateId(EmployeeMasterModel employeeMasterModel);
	
	public EmployeeMasterModel getEmployeeMasterDomainById(long numId);
	
	//public EmployeeMasterDomain getEmployeeMasterById(long numId);

	
	public EmployeeMasterModel getEmployeeMasterDomainByIdForUpdate(long numId);
	
	public List<EmployeeMasterModel> getAllEmployeeMasterDomain();
	
	public List<EmployeeMasterModel> getAllActiveEmployeeMasterDomain();
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	public EmployeeMasterDomain findByEmail(String email);
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	public EmployeeMasterDomain findAnyByEmail(String email);
	
	public boolean checkIfValidOldPassword(EmployeeMasterDomain user, String oldPassword);

	@Transactional
	public void changeUserPassword(EmployeeMasterDomain user, String newPassword);
	
	@Transactional
	public EmployeeMasterModel getEmployeeDetails(long empId);
	
	public String checkDuplicateEmployeeEmail(EmployeeMasterModel employeeMasterModel);

	public boolean getEmployeeEmail(long empId,String email);
	
	@Transactional
	public long mergeRoleInEmployeeMaster(EmployeeMasterModel employeeMasterModel);
	
	public JSONArray getEmployeeCountByEmployementType();
	
	@Transactional
	public  Map<String,List>  getEmployeeCountByGroupandDesignation();
	
	//added to show detail of support group
	@Transactional
	public  Map<String,List>  getEmployeeCountByGroupandDesignationSupport();
	
	//added to show detail of Technical group
	@Transactional
	public  Map<String,List>  getEmployeeCountByGroupandDesignationTechnical();
	
	public List<EmployeeMasterModel> getThirdPartyById(long employeeTypeId,EmployeeMasterModel employeeMasterModel);

	@Transactional
	public long saveUpdateEmployeeMasterProfile(EmployeeMasterModel employeeMasterModel);
	
	public List<String> getdistinctEmpGender() ;

	@Transactional
	public boolean sendMail(EmployeeMasterModel model);
	
	public JSONArray getEmployeeCountByGroup();
	
	//Added for HR Dashboard(Graph showing employees count based on their category)
	public JSONArray getEmployeeCountByCategory();


	//Added for HR Dashboard(Graph showing total new joining and resignations year-wise)
	public JSONArray getYearWiseEmployeeCount();
	//Employee Details by group and employment type for HR Dashboard
	@Transactional
	   public List<EmployeeMasterModel> getEmployeesByGroupByEmployementType();
	
	@Transactional
	public JSONObject getEmployeeBasicDetails(String encEmployeeId);
	
	public List<Object[]> getAllActiveEmployeeWithCount();

	public List<EmployeeMasterModel> getEmpbyDesignationForGroup(String groupName,String strDesignation);
	
	public long getEmployeeByPostId(long id);
	
	@Transactional
	public List<String> saveEmployeeDetails(EmployeeMasterModel employeeMasterModel);
	
	List<EmployeeMasterModel> getContractDetails(String date);
	
	public void releaseEmployeeAuthorityCheck();
	
	public EmployeeMasterModel getEmployeeBasicDetail(EmployeeMasterModel employeeMasterModel);
	
	@Transactional
	public boolean releaseEmployee(EmployeeMasterModel employeeMasterModel);

	@Transactional
	public List<EmployeeMasterModel> viewEmployeeOnGroupID(Long encGroupId);
	
	@Transactional
	public List<EmployeeMasterModel> loadEmployeeDetails();
	
	@Transactional
	public List<EmployeeMasterModel> loadJoinedEmployeeDetails(String startRange, String endRange);
	
	@Transactional
	public List<EmployeeMasterModel> loadResignedEmployeeDetails(String startRange, String endRange);

	public List<EmployeeMasterModel> employeeDetailsWithInvolvements();
	
	@Transactional
	public Map<String, MiscDataModel> getDetails(String startRange, String endRange);
	
	@Transactional
	public List<EmployeeMasterModel> getNewJoinedEmployeeByGroupDetails(String startRange, String endRange,String encGroupId);
	
	@Transactional
	public List<EmployeeMasterModel> getResignedEmployeeByGroupDetails(String startRange, String endRange,String encGroupId);
	
	public JSONArray getEmployeeCountByEmployementTypeNew();
	
	public List<EmployeeMasterModel> employeeDetailsWithInvolvements(ProjectMasterModel projectMastermodel);
	
	@Transactional
	public List<EmployeeMasterModel> searchEmpDataByName(EmployeeMasterModel employeeMasterModel);
	
	@Transactional
	public EmployeeMasterModel getRelievedEmployeeBasicDetail(EmployeeMasterModel employeeMasterModel);
	
	@Transactional
	public List<EmployeeMasterModel> searchRelievedEmpDataByName(EmployeeMasterModel employeeMasterModel);
	
	@Transactional
	public EmployeeMasterModel getEmployeeDetailsByEmailId(String empEmailId);
	
	@Transactional
	public List<EmployeeMasterModel> loadUnderUtilizationEmployees();
	
	@Transactional
	public List<EmployeeMasterModel> getEmployeesByEmploymentType(String employmentTypeName);
	
	@Transactional
	public List<EmployeeMasterModel> employeeDetailsWithInvolvementsGroupWise(String groupName);
	
	@Transactional
	public List<EmployeeMasterModel> employeeDetailsByCategory(String category);
	
	public JSONArray getEmployeeCountByGroupwise(String groupName);
	
	public  List<DesignationMasterModel>  getEmployeeCountBySingleGroupandDesignation(String group);
	
	@Transactional
	public List<EmployeeMasterModel> loadRejoinEmployeeDetails(String startRange, String endRange);
	
	@Transactional
	public List<EmployeeMasterModel> getPreviousHistoryOfEmp(String empName, String dob);
	
	@Transactional
	public List<EmployeeMasterModel> loadRejoinRelievedEmployeeDetails(String startRange, String endRange,String status);
	
	@Transactional
	public List<EmployeeMasterModel> loadResignedEmployeeCustomizedDetails(String startRange, String endRange);
    
	@Transactional
	public EmployeeMasterModel getCountRejoinEmployeeDetails(String startRange, String endRange);

	JSONArray getdeputedwiseEmployeeCount();//added by devesh on 9/6/23
}