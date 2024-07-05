package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.service.ManpowerExpensesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ManpowerExpensesController {
	
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
	ManpowerExpensesService manpowerExpensesService;
	@Autowired
	ProjectCategoryService projectCategoryService;
	
	
	
	@Autowired
	EncryptionService encryptionService;
	
	
	@RequestMapping(value="/manpowerExpensesForProjects")
	public String monthlyProgressForProjects(HttpServletRequest request){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedRole.getNumRoleId();
		if(roleId==15) {
			List<EmployeeRoleMasterModel> allRolesofEmplyee = userInfo.getEmployeeRoleList();
			Set<EmployeeRoleMasterModel> filteredRoles = new HashSet<>();
			
			if(null != allRolesofEmplyee && !allRolesofEmplyee.isEmpty()){
				filteredRoles = allRolesofEmplyee.stream()
			            .filter(x -> {
			                // Check role IDs first to see if the filtering is necessary
			                if (3 == x.getNumRoleId() || 4 == x.getNumRoleId() || 15 == x.getNumRoleId()) {
			                    ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(x.getNumProjectId());
			                    // Further filter by checking the Project Reference Number is not null
								/*return projectMasterForm != null && projectMasterForm.getProjectRefrenceNo() != null && projectMasterForm.getProjectCategory().equals("Business Project");*/
			                    return projectMasterForm != null && projectMasterForm.getProjectRefrenceNo() != null;
			                }
			                return false;
			            })
			            .collect(Collectors.toSet());
			}
			
			/*if(null != allRolesofEmplyee && allRolesofEmplyee.size()>0){
				filteredRoles = allRolesofEmplyee.stream()			      
				        .filter(x -> (3 == x.getNumRoleId() || 4== x.getNumRoleId() || 15== x.getNumRoleId())).collect(Collectors.toSet());		
			}*/
			
			if(!filteredRoles.isEmpty()){
				request.setAttribute("projects", filteredRoles);
			}
			
			return "manpowerExpenses";
		}
		else {
			return "accessDenied";
		}
			
	}
	
	@PostMapping(value="/getProjectStartAndEndDate")
	public @ResponseBody List<String> getProjectStartDate(ProjectMasterModel projectMasterModel ,HttpServletRequest request){
		long projectId =0;
		List<String> dateList = new ArrayList<>();
		String strProjectId = encryptionService.dcrypt(projectMasterModel.getEncProjectId());
		try{
			projectId = Long.parseLong(strProjectId);
		}catch(Exception e){
			e.printStackTrace();
		}
		ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(projectId);
		dateList.add(projectMasterForm.getStartDate());
		dateList.add(projectMasterForm.getEndDate());
		return dateList;		
	}

	@PostMapping(value="/downloadManpowerExpensesExcel")
	public @ResponseBody void downloadManpowerExpensesExcel(ProjectMasterForm projectMastermodel ,HttpServletRequest request, HttpServletResponse response){
		manpowerExpensesService.downloadManpowerExpensesExcel(projectMastermodel, response);
	}
	
}