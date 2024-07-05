package in.pms.global.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.RandomGenerator;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ManpowerRequirementModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;
import in.pms.master.model.TaskAssignmentModel;
import in.pms.master.model.TaskDetailsModel;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.ManpowerRequirementService;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectMilestoneService;
import in.pms.master.service.ProjectPaymentScheduleMasterService;
import in.pms.master.service.TaskDetailsService;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.service.ProgressReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticPageAction {
	
	@Autowired 
	EncryptionService encryptionService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	TaskDetailsService taskDetailsService;
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;

	@Autowired
	ManpowerRequirementService manpowerRequirementService;
	
	@Autowired
	ProjectPaymentScheduleMasterService projectPaymentScheduleMasterService;
	
	@Autowired
	ProjectMilestoneService projectMilestoneService;
	
	@Autowired
	ProgressReportService progressReportService;
	
	
	
	
	
	@RequestMapping(value="/Homepage")
	public String homepage(HttpServletRequest request,Authentication authentication){		
		if(null != authentication){		
			return "redirect:/dashboard";
		}		
		if(null != request.getParameter("error")){
			request.setAttribute("loginModal", 1);
		}
		String prefixRandom = RandomGenerator.generateRandom(5, 4);
		String suffixRandom = RandomGenerator.generateRandom(6, 4);
		
		HttpSession session = request.getSession();
		session.setAttribute("prefixRandom", prefixRandom);
		session.setAttribute("suffixRandom", suffixRandom);
		return "Homepage";
	}

	@RequestMapping(value="/accessDenied")
	public String accessDenied(HttpServletRequest request,Authentication authentication){		
		return "accessDenied";
	}
	
	@RequestMapping(value={"/projectDetails/{encId}","/projectDetails/{encId}/{encCategoryId}" })
	
	public String projectDetails(HttpServletRequest request,@PathVariable("encId") String encId,@PathVariable(name="encCategoryId", required=false) String encCategoryId){	
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);
		if(projectId==0){
			return "redirect:/GroupMonthlyReport";
		}
		long categoryId =0;
		if(null != encCategoryId){
			categoryId = Long.parseLong(encryptionService.dcrypt(encCategoryId));
		}
		
		request.setAttribute("categoryIdback", categoryId);
		
		ProjectMasterForm projectMasterForm =projectMasterService.getProjectDetailByProjectId(projectId);
		request.setAttribute("projectMaster", projectMasterForm);
		
		List<ProjectDocumentMasterModel> projectDocument = projectDocumentMasterService.uploadedDocumentByProjectId(projectId);		
		request.setAttribute("projectDocument", projectDocument);
			
		List<ManpowerRequirementModel> manpowerRequirement = manpowerRequirementService.getAllManpowerRequirement(projectId);
		request.setAttribute("manpowerRequirement", manpowerRequirement);
		
		List<ProjectPaymentScheduleMasterModel> paymentScheduleList=	projectPaymentScheduleMasterService.getProjectPaymentScheduleByProjectId(projectId);
		request.setAttribute("paymentScheduleList", paymentScheduleList);		
		
		List<ProjectMasterModel> validatedloglist = projectMasterService.getProjectvalidationModelById(projectId);
		request.setAttribute("validatedloglist", validatedloglist);	
		//System.out.println("log list" + validatedloglist);
		
		List<ProjectPaymentScheduleMasterModel> paymentReceivedWithScheduleList= projectPaymentScheduleMasterService.getPaymentScheduleWithReceivedAmount(projectId);
		request.setAttribute("paymentReceivedWithSchedules", paymentReceivedWithScheduleList);
		
		List<ProjectMilestoneForm> milestoneDetails = projectMilestoneService.getMilestoneByProjectId(projectId);
		request.setAttribute("milestoneDetails", milestoneDetails);
		
		/*Map<String,List<EmployeeRoleMasterModel>> assignedManpower = manpowerRequirementService.getManpowerRequirementWithAssignedRole(projectId);
		request.setAttribute("assignedManpower", assignedManpower);*/
		//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
		Map<String,List<EmployeeRoleMasterModel>> assignedManpower = manpowerRequirementService.getManpowerRequirementWithAllAssignedRole(projectId);
		request.setAttribute("assignedManpower", assignedManpower);
		//End of list
		request.setAttribute("encProjectId", encId);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		if(userInfo.getSelectedEmployeeRole()!=null)
		{
			int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
			request.setAttribute("userRole", selectedRoleId);
		}
		
		List<CategoryMasterModel> categoryMasterList=new ArrayList<CategoryMasterModel>();
		List<EmployeeRoleMasterModel> allRolesofEmplyee = userInfo.getEmployeeRoleList();
		boolean isSamePM = false;
		boolean isSamePI = false;
		if(null != allRolesofEmplyee && allRolesofEmplyee.size()>0){
			EmployeeRoleMasterModel samePL = allRolesofEmplyee.stream()
					.filter(x -> projectId == x.getNumProjectId() && 3 == x.getNumRoleId()).findAny()
					.orElse(null);
			if(null != samePL){
				isSamePM= true;			
			}
			
			if(isSamePM == false){
				EmployeeRoleMasterModel samePI = allRolesofEmplyee.stream()
						.filter(x -> projectId == x.getNumProjectId() && 4 == x.getNumRoleId()).findAny()
						.orElse(null);
				if(null != samePI){
					isSamePI= true;			
				}
			}
		}
		if(userInfo.getSelectedEmployeeRole()!=null){
			
			if(isSamePM == true || isSamePI == true || userInfo.getSelectedEmployeeRole().getNumRoleId()==5 || userInfo.getSelectedEmployeeRole().getNumRoleId()==6 || userInfo.getSelectedEmployeeRole().getNumRoleId()==9 )
			{				
				categoryMasterList=progressReportService.getAllCategoryList("P");
				request.setAttribute("categoryList", categoryMasterList);
				request.setAttribute("role", 1);			
			}
		
		}else if(isSamePM == true){
			categoryMasterList=progressReportService.getAllCategoryList("P");
			request.setAttribute("categoryList", categoryMasterList);
			request.setAttribute("role", 1);
		}
		request.setAttribute("Startdate", projectMasterForm.getStartDate());
		if(projectMasterForm.getStrProjectStatus().equalsIgnoreCase("Completed")||projectMasterForm.getStrProjectStatus().equalsIgnoreCase("Terminated"))
		{
		List<EmployeeRoleMasterModel> closedProjectEmplist = employeeRoleMasterService.getClosedProjectEmpListService(projectId);
		request.setAttribute("closedProjectEmployeeList", closedProjectEmplist);
		request.setAttribute("seenFlag", 1);
		}
		else
		request.setAttribute("seenFlag", 0);	
			
		return "projectDetails";
	}
	
	@RequestMapping(value="/employeeDashboard")
	public String employeeDashboard(HttpServletRequest request,TaskAssignmentModel taskAssignmentModel){	
		List<TaskDetailsModel> list = taskDetailsService.getAllActiveTaskDetailsData("New,Withdraw");
		request.setAttribute("list", list);
		List<TaskDetailsModel> list1 = taskDetailsService.getAllActiveTaskDetailsData("Assigned");
		request.setAttribute("list1", list1);
		List<TaskDetailsModel> list2 = taskDetailsService.getAllActiveTaskDetailsData("WIP");
		request.setAttribute("list2", list2);
		List<TaskDetailsModel> list3 = taskDetailsService.getAllActiveTaskDetailsData("Completed");
		request.setAttribute("list3", list3);
		return "employeeDashboard";
	}

	@RequestMapping(value="/admin/switchUser")
	public String switchUser(HttpServletRequest request){		
		return "InternalLogin";
	}
}
