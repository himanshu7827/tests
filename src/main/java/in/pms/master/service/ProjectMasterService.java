package in.pms.master.service;

import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.model.TransactionMasterModel;
import in.pms.global.model.WorkflowModel;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ProjectClosureModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectMasterService {

	public List<ProjectMasterModel> getAllActiveProjectMasterData();

	public ProjectMasterModel getProjectMasterModelById(long projectId);
	
	public List<ProjectMasterForm> getAllProjectDetailsData();
	
	public List<ProjectMasterForm> getviewAllProjectDetailsData(String groupIds,String startRange, String endRange);

	public String checkDuplicateProjectDetailsData(ProjectMasterForm projectMasterForm);

	@Transactional
	public ProjectMasterDomain saveProjectDetailsData(ProjectMasterForm projectMasterForm);
	
	@Transactional
	public ProjectMasterDomain mergeProjectMaster(ProjectMasterDomain projectMasterDomain);

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProjectDetailsData(ProjectMasterForm budgetHeadMasterForm);
	
	public ProjectMasterDomain getProjectMasterDataById(long projectId);

	List<ProjectMasterForm> getAllProjectMasterData(String assignedProjects);
	
	public List<ProjectMasterForm> convertBudgetMasterDomainToModelList(List<ProjectMasterDomain> projectMasterList);
	
	@Transactional
	public List<ProjectMasterModel> getProjectDataByGroupId(long groupId);
	

	public List<ProjectMasterForm> viewProjectDetailsData(String encGroupId);
	
	public List<ProjectMasterForm> convertProjectDetailsToModelList(List<Object[]>projectDetailsList);

	public List<ProjectMasterModel> convertProjectMasterDomainToModelList(
			List<ProjectMasterDomain> domainList);

	@Transactional
	public List<ProjectMasterForm> getAllProjectDetails();
	
	public ProjectMasterForm getProjectDetailsByApplicationId(long applicationId);
	
	@Transactional
	public ProjectMasterForm getProjectDetailsFromProposalByApplicationId(long applicationId);

	public ProjectMasterForm getProjectDetailByProjectId(long projectId);

	public ProjectMasterDomain getProjectMasterDataWithClientById(long projectId);

	@Transactional
	public ProjectMasterDomain mergeClientContactDetails(ProjectMasterDomain domain);
	
	@Transactional
	public JSONArray getProjectTypeWiseProjects();
	
	@Transactional
	public boolean projectClosure(ProjectClosureModel projectClosureModel);

	public List<ProjectMasterForm> getAllCompletedProject(String strStartDate, String strEndDate);
	@Transactional
	public List<ProjectMasterModel> getProjectDataByGroupIds(String groupIds);

	public List<ProjectMasterForm> getCompletedProjectByGroup(String groupId, String strStartDate, String strEndDate);
	
	@Transactional
	public List<ProjectMasterForm> getProjectRolesDetails();
	/*public String  getNewProjectCount(Date startRange1,Date endRange1);*/
	public long  getNewProjectCount(Date startRange1,Date endRange1);
	
	@Transactional
	   public List<ProjectMasterModel> getNewProjectsDetail(ProjectMasterModel projectMasterModel);
	
	public long  getClosedProjectCount(Date startRange,Date endRange);
	
	@Transactional
	public List<ProjectMasterModel> getClosedProjectsDetail(ProjectMasterModel projectMasterModel);
	
	@Transactional
	String getProjectIdByApplicationId(ProjectMasterForm projectMasterForm);
	
	@Transactional
	public int checkProjectDate(ProjectMasterForm projectMasterForm);
	
	@Transactional
	public List<ProjectMasterForm> getPendingClosureDetail();
	
	@Transactional
	public List<ProjectMasterModel> getAllClosedProjectByGroup(ProjectMasterModel projectMasterModel);
	
	@Transactional
	public List<ProjectMasterModel> getNewProjectsByGroupDetail(ProjectMasterModel projectMasterModel);
	
	public List<ProjectMasterForm> viewValidatedProjectDetailsData(String s,String e);
	
	@Transactional
	public List<ProjectMasterForm> getPendingClosureDetailByDate(String closureDate, String symbol);
	
	@Transactional
	public ProjectMasterDomain saveProjectDetails(String encProjId,int selectedVal);

	@Transactional
	public List<ProjectMasterForm> getUnderApprovalProjects();
	
	@Transactional	
	public List<String> validateProjectDetails(String encProjectId);
	
	@Transactional
	public String submitProjectDetails(long projectId);
	
	@Transactional
	public String getDetailOfTransaction(long projectId);
	
	@Transactional
	public String submitProjectClosureInitiateByPL(long projectId);
	
	@Transactional
	public List<ProjectMasterForm> underClosureProjects();
	
	public ProjectClosureModel getTempProjectMasterModelById(long projectId);
	
	public List<ProjectMasterForm> getAllPendingCompletedProject();
	
	@Transactional
	public List<ProjectMasterForm> getUnderClosure ();
	
	@Transactional
	public List<ProjectMasterForm> getUnderClosureforPMO();
	
	@Transactional
	public List<ProjectMasterForm> getUnderClosureforGCFin();
	
	/*------------------------------ Get Project Details For Excel Generate in Ongoing Projects [ added by Anuj ] ---------------------------------------------*/
	public List<ProjectMasterForm> viewProjectDetailsForExcel(String encGroupId);
	public List<ProjectMasterForm> convertProjectDetailsForExcelToModelList(List<Object[]> projectDetailsList);
	
	/*---------------------------------------  Handle Request By HOD [ 21/07/2023 added by_Anuj ]    ---------------------------------------------------------------------------*/
	@Transactional
	public String submitProjectDetailsbyHOD(long projectId);
	@Transactional
	public String ProjectReferenceNumberbyHOD(long projectId);
	/*---------------------------------------  Handle Request By GC [ 21/07/2023 added by_Anuj ]    ---------------------------------------------------------------------------*/
	@Transactional
	public String submitProjectDetailsbyGC(long projectId);
	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	public List<ProjectMasterForm> getPendingClosureDetailforOngoing();//Added new service for Pending Closure Tile without closure initialized details by devesh on 17/8/23

	//Added new service for Pending Closure Tile without closure initialized details by devesh on 17/8/23
	List<ProjectMasterForm> getPendingClosureDetailByDateforOngoing(String closureDate, String symbol);
	
/*---------- Get Under closure projects list without role ids  [ 29-08-2023 ]-----------------------------------------------------------*/	
	public List<ProjectMasterForm> underClosureProjectsList();
	
	/*------------------------- Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/	
	@Transactional
	public List<ProjectMasterForm> getAllFinancialClosurePendingProjectByDate(ProjectMasterForm projectMasterModel);

	public List<ProjectMasterModel> getProjectvalidationModelById(long projectId);

	@Transactional
	public int checkProjectDateByRole(ProjectMasterForm projectMasterForm, int roleId); //Function added by devesh on 19-02-24 to map HODs and PIs to project
	
	/*--------- Service function for get Projects Details By Group Id [18-03-2024] -------------*/
	@Transactional
	public List<ProjectMasterModel> getProjectNameByGroupId(long groupId);
}