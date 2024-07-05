package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.RoleMasterService;
import in.pms.transaction.model.DesignationForClientModel;
import in.pms.transaction.model.ManpowerUtilizationModel;
import in.pms.transaction.service.DesignationForClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/mst")
public class EmployeeRoleMasterController {

	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	OrganisationMasterService organisationMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	DesignationForClientService designationForClientService;
	
	@Autowired
	@Qualifier("EmployeeRoleMasterValidator")
	Validator validator;
	
	@RequestMapping("/employeeRoleMaster")
	public String employeeRoleMaster(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		if(userInfo.getSelectedEmployeeRole()!=null)
		{
			int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
			request.setAttribute("userRole", selectedRoleId);
		}
	List<EmployeeMasterModel> employeeList = employeeMasterService.getAllActiveEmployeeMasterDomain();
	List<DesignationForClientModel> designations = designationForClientService.getActiveDesignationForClient();
	request.setAttribute("designations", designations);
	List<RoleMasterModel> roleList = roleMasterService.getAllActiveRoleMasterDomain();
	
	List<OrganisationMasterModel> organisationList = organisationMasterService.getAllActiveOrganisationMasterDomain();
	
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfoo = (UserInfo) auth.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfoo.getSelectedEmployeeRole();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	
	
	if(roleId == 3 || roleId == 4 || roleId == 2) {
		List<EmployeeMasterModel> teamEmployeeList = new ArrayList<EmployeeMasterModel>();
		//load Team Details
		List<Object[]> empList= employeeRoleMasterService.getTeamMIdsName();
		
		for(int i=0; i<empList.size();i++)		{
			Object[] obj = empList.get(i);			
			EmployeeMasterModel tempEmpModel = new EmployeeMasterModel();	
			tempEmpModel.setNumId(Long.parseLong(obj[0]+""));
			tempEmpModel.setEmployeeName(obj[1]+"");
			tempEmpModel.setStrDesignation(obj[3]+"");
			
			teamEmployeeList.add(tempEmpModel);
			
			employeeList = employeeList.stream()
					  .filter(e -> e.getNumId() != tempEmpModel.getNumId())
					  .collect(Collectors.toList());
			
		}
		
		if(teamEmployeeList.size()>0) {
			//Collections.reverse(teamEmployeeList);
			//Collections.reverse(employeeList);
			employeeList.addAll(0,teamEmployeeList);
			//employeeList.addAll(teamEmployeeList)
		}
	}
	
		request.setAttribute("employeeList", employeeList);
		request.setAttribute("roleList", roleList);
		List<ProjectMasterForm> projectRoleDetails=projectMasterService.getProjectRolesDetails();
			request.setAttribute("projectRoleDetails", projectRoleDetails);
		request.setAttribute("organisationList", organisationList);
		if(organisationList.size()==1){
		List<GroupMasterModel> listGroup = groupMasterService.getAllActiveGrpMasterDomain(organisationList.get(0).getNumId());
		request.setAttribute("grouplist", listGroup);
		}
		return "employeeRoleMaster";
	}
	

	
	@RequestMapping(value="/saveUpdateEmployeeRoleMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateEmployeeRoleMaster(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request , RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
													
			validator.validate(employeeRoleMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	 
		    	  List<EmployeeMasterModel> employeeList = employeeMasterService.getAllActiveEmployeeMasterDomain();
		    		List<RoleMasterModel> roleList = roleMasterService.getAllActiveRoleMasterDomainForMapping();
		    		List<OrganisationMasterModel> organisationList = organisationMasterService.getAllActiveOrganisationMasterDomain();
		    			request.setAttribute("employeeList", employeeList);
		    			request.setAttribute("roleList", roleList);
		    			request.setAttribute("organisationList", organisationList);
		    	  mav.setViewName("employeeRoleMaster");
		          return mav;
		      }
		      String strDuplicateCheck = null;
		      if(employeeRoleMasterModel.getNumProjectId() != 0){
		    	  strDuplicateCheck = employeeRoleMasterService.checkDuplicateEmpRoleMstDetailsData(employeeRoleMasterModel);			
		      }
		  	 if(null == strDuplicateCheck){
		  		long id = employeeRoleMasterService.saveUpdateEmployeeRoleMaster(employeeRoleMasterModel);
				if(id>0){					
					if(employeeRoleMasterModel.getNumId()==0){
						
						redirectAttributes.addFlashAttribute("message",  "Employee Role record saved Successfully with Id "+id);	
						redirectAttributes.addFlashAttribute("status", "success");
					}else{
						redirectAttributes.addFlashAttribute("message",  "Employee Role record updated Successfully with Id "+id);	
						redirectAttributes.addFlashAttribute("status", "success");
					}					
				}
			}else{
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}				
		  		 mav.setViewName("redirect:/mst/employeeRoleMaster");
		          return mav;
	}
	
	//ajax call get Employee Role Details using EmpId
	@RequestMapping(value="/getEmployeeRoleMasterByEmpId",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody List<EmployeeRoleMasterModel> getEmployeeRoleMasterByEmpId(EmployeeRoleMasterModel employeeRoleMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			List<EmployeeRoleMasterModel> datalist = new ArrayList<EmployeeRoleMasterModel>();
			datalist = employeeRoleMasterService.getEmployeeRoleMasterByEmpId(employeeRoleMasterModel.getNumEmpId());
			request.setAttribute("data1", datalist);	
		return datalist; 
	}

	
	
	
	
		//ajax call get employeeRole using roleID
		@RequestMapping(value="/getRoleMasterById",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody RoleMasterModel getRoleMasterById(@RequestParam("numRoleId") long numRoleId,HttpServletRequest request,HttpServletResponse response){
			return roleMasterService.getRoleById(numRoleId);
		}

				
		//ajax call get ProjectId using GroupId
		@RequestMapping(value="/getProjectNameByGroupId",method=RequestMethod.POST)
		public @ResponseBody List<ProjectMasterModel> getProjectNameByGroupId(@RequestParam("numGroupId") long numGroupId,HttpServletRequest request,HttpServletResponse response){
		
			return projectMasterService.getProjectDataByGroupId(numGroupId);
		}
		
		
		@RequestMapping(value="/getProjectTeamDetails/{encProjectId}",method=RequestMethod.GET)
		public @ResponseBody String getProjectTeamDetails(@PathVariable("encProjectId") String encProjectId,HttpServletRequest request,HttpServletResponse response){			
			return employeeRoleMasterService.getProjectTeamDetails(encProjectId).toString();
		}
				
		@RequestMapping(value="/getEmployeeFromProject",method=RequestMethod.POST)
		public @ResponseBody List<Object[]> getEmployeeFromProject(ManpowerUtilizationModel manpowerUtilizationModel,HttpServletRequest request,HttpServletResponse response){			
			return employeeRoleMasterService.getEmployeeFromProject(manpowerUtilizationModel.getMonth(),manpowerUtilizationModel.getYear());
		}
		
		@RequestMapping("/projectTeamMapping")
		public String projectTeamMapping(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){			
			List<RoleMasterModel> roleList = roleMasterService.getApplicationSpecificRoles();		
			request.setAttribute("roleList", roleList);
			String referrer = request.getHeader("referer");
			if(null != referrer && referrer.contains("projectDetails")){
				request.setAttribute("referrer", referrer);
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			if(userInfo.getSelectedEmployeeRole()!=null)
			{
				int selectedRoleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
				request.setAttribute("userRole", selectedRoleId);
			}
			if(null != employeeRoleMasterModel.getEncProjectId()){
				String tempProjectId = encryptionService.dcrypt(employeeRoleMasterModel.getEncProjectId());
				long projectId = Long.parseLong(tempProjectId);
				ProjectMasterModel projectMaster= projectMasterService.getProjectMasterModelById(projectId);				
				request.setAttribute("projectMaster", projectMaster);
			}else{
				List<ProjectMasterModel> projectsList =projectMasterService.getAllActiveProjectMasterData();
				request.setAttribute("projectsList", projectsList);
			}
			List<ProjectMasterForm> projectRoleDetails=projectMasterService.getProjectRolesDetails();
			request.setAttribute("projectRoleDetails", projectRoleDetails);
			List<DesignationForClientModel> designations = designationForClientService.getActiveDesignationForClient();
			request.setAttribute("designations", designations);
			return "projectTeamMapping";
		}
		
		@RequestMapping(value="saveprojectTeamMapping",method= RequestMethod.POST)
		@ResponseBody
		public String saveprojectTeamMapping(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){	
		    String strDuplicateCheck = null;
		      if(employeeRoleMasterModel.getNumProjectId() != 0){
		    	  strDuplicateCheck = employeeRoleMasterService.checkDuplicateEmpRoleMstDetailsData(employeeRoleMasterModel);			
		      }
		  	 if(null == strDuplicateCheck){
		  		 try{
		  			 employeeRoleMasterService.saveprojectTeamMapping(employeeRoleMasterModel);
		  			 return "success";	
		  		 }catch(Exception e){
		  			 return "Fail";
		  		 }
			}else{
				return strDuplicateCheck;				
			}			
		  	 	
		}
		
		@RequestMapping("/projectTeamWiseEmployees")		
		public @ResponseBody String projectTeamWiseEmployees(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){		
			return ""+employeeRoleMasterService.projectTeamWiseEmployees(employeeRoleMasterModel.getNumProjectId(), employeeRoleMasterModel.getNumRoleId());
		}
		
		@RequestMapping(value="/activeRolesByEmpId",method= RequestMethod.POST)		
		public @ResponseBody List<EmployeeRoleMasterModel> getActiveRolesByEmpId(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){		
			String strEployeeId= encryptionService.dcrypt(employeeRoleMasterModel.getEncEmpId());
			long employeeId = Long.parseLong(strEployeeId);
			return employeeRoleMasterService.getActiveEmployeeRoleByEmpId(employeeId);
		}
		
		@RequestMapping("/primaryRoleDetail")		
		public @ResponseBody String primaryRoleDetail(@RequestParam("empId") long empId,@RequestParam("projectId") long projectId,@RequestParam("roleId") long roleId,HttpServletRequest request,RedirectAttributes redirectAttributes){		
			String data=employeeRoleMasterService.getEmployeeRoleMasterDomainByEmpId(empId,projectId,roleId) ;
			return data;
		}
		
		@RequestMapping("/uncheckPrimaryRole")		
		public @ResponseBody boolean uncheckPrimaryRole(@RequestParam("empId") long empId,@RequestParam("projectId") long projectId,@RequestParam("roleId") long roleId,HttpServletRequest request){		
			boolean data=employeeRoleMasterService.uncheckPrimaryRole(empId,projectId,roleId) ;
			return data;
		}
		
		@RequestMapping(value="/getManpowerRequiermentDetails",method=RequestMethod.POST)
		public @ResponseBody String getManpowerRequiermentDetails(@RequestParam("numProjectId") long numProjectId,HttpServletRequest request,HttpServletResponse response){
		
			return  ""+employeeRoleMasterService.getManpowerRequiermentDetails(numProjectId);
		}
		
		@RequestMapping(value="/getAllTeamMembers",method=RequestMethod.POST)
		public @ResponseBody List<EmployeeRoleMasterModel> getAllTeamMembers(){
			return employeeRoleMasterService.getAllTeamMembers();
		}
		
		//Added by devesh on 30-11-23 to get team members according to projects
		@RequestMapping(value="/getAllTeamMembersProjectWise",method=RequestMethod.POST)
		public @ResponseBody List<EmployeeRoleMasterModel> getAllTeamMembersProjectWise(){
			return employeeRoleMasterService.getAllTeamMembersProjectWise();
		}
		//End of Function
		
		/*@RequestMapping(value="/getProjectDesignationDetails",method=RequestMethod.POST)
		public @ResponseBody List<DesignationForClientModel> getProjectDesignationDetails(@RequestParam("numProjectId") long numProjectId,HttpServletRequest request,HttpServletResponse response){
	    	  //ProjectMasterDomain domain=projectMasterService.getProjectMasterDataById(numProjectId);
		  		List<DesignationForClientModel> designations = designationForClientService.getActiveDesignationForClient();
		  		return designations;
		}*/
		@RequestMapping("/projectCategory")		
		public @ResponseBody int projectCategory(EmployeeRoleMasterModel employeeRoleMasterModel,HttpServletRequest request){		
			  ProjectMasterDomain domain=projectMasterService.getProjectMasterDataById(employeeRoleMasterModel.getNumProjectId());
	  			return domain.getApplication().getCategoryMaster().getNumId();
	  		}
		
}
