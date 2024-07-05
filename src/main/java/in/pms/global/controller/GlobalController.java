package in.pms.global.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.pms.global.model.RedirectModel;
import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.EmployeeDefaultRoleMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProcessMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.GlobalService;
import in.pms.transaction.model.ApplicationModel;
import in.pms.transaction.model.WorkflowConfigurationModel;
import in.pms.transaction.service.ApplicationService;
import in.pms.transaction.service.WorkflowConfigurationService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GlobalController {

	@Autowired
	public WorkflowConfigurationService workflowConfigurationService;
	
	@Autowired
	public GlobalService globalService;
	
	@Autowired
	public ApplicationService applicationService;
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	 
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping(value="/nextRedirectPage")
	public String getNextRedirectPage(HttpServletRequest request,@ModelAttribute("model") RedirectModel model,RedirectAttributes redir){	
		if(model.getModuleTypeId()==1){
			ApplicationModel appModel=	applicationService.getApplicationById(model.getUniqueId());
			int numCollaborative=1;
			if(appModel!=null && appModel.isCollaborative())
				numCollaborative=2;
				int workflowTypeId=workflowConfigurationService.getWorkflowTypeId(model.getModuleTypeId(),appModel.getBusinessTypeId(),appModel.getProjectTypeId(),numCollaborative);
				model.setWorkflowTypeId(workflowTypeId);
			}	
		int currentstepId=workflowConfigurationService.getCurrentStepId(model.getPath(), model.getWorkflowTypeId());
		String url=workflowConfigurationService.getNextRedirectPageURL(model.getWorkflowTypeId(), currentstepId);
		if(model.getModuleTypeId()==1){
	    redir.addFlashAttribute("applicationId",model.getUniqueId());
		}else if(model.getWorkflowTypeId()==3)
		    redir.addFlashAttribute("projectId",model.getUniqueId());

		url=url.replaceAll("/PMS/", "");
		//Added by devesh to fix access denied page on reload
		if(model.getWorkflowTypeId()==3 && model.getModuleTypeId()!=1){
			String encProjectId = encryptionService.encrypt(""+model.getUniqueId());
			url += "?encProjectId="+encProjectId;
		}
		 //End of Code
		return "redirect:"+url;
	}
	
	@RequestMapping(value="/prevRedirectPage")
	public String getPrevRedirectPage(HttpServletRequest request,@ModelAttribute("model") RedirectModel model,RedirectAttributes redir){
		String url="";
		if(model.getModuleTypeId()==1){
			ApplicationModel appModel=	applicationService.getApplicationById(model.getUniqueId());
			int numCollaborative=1;
			if(appModel!=null && appModel.isCollaborative())
				numCollaborative=2;
				int workflowTypeId=workflowConfigurationService.getWorkflowTypeId(model.getModuleTypeId(),appModel.getBusinessTypeId(),appModel.getProjectTypeId(),numCollaborative);
				model.setWorkflowTypeId(workflowTypeId);
			}
		int currentstepId=workflowConfigurationService.getCurrentStepId(model.getPath(), model.getWorkflowTypeId());
		if(currentstepId==1 && model.getModuleTypeId()==1){
		 url="/applicationBasicDetails";
		 redir.addFlashAttribute("applicationId",model.getUniqueId());
		}else{
		url=workflowConfigurationService.getPrevRedirectPageURL(model.getWorkflowTypeId(), currentstepId);
		if(model.getModuleTypeId()==1){
		    redir.addFlashAttribute("applicationId",model.getUniqueId());
			}else if(model.getWorkflowTypeId()==3)
			    redir.addFlashAttribute("projectId",model.getUniqueId());
		url=url.replaceAll("/PMS/", "");
		//Added by devesh to fix access denied page on reload
		if(model.getWorkflowTypeId()==3 && model.getModuleTypeId()!=1){
			String encProjectId = encryptionService.encrypt(""+model.getUniqueId());
			url += "?encProjectId="+encProjectId;
		}
		 //End of Code
		}
		return "redirect:"+url;
	}
	
	
	@RequestMapping(value="/startWorkflow")
	public String startWorkflow(HttpServletRequest request,WorkflowConfigurationModel workflowConfigurationModel,@ModelAttribute("model") RedirectModel model,RedirectAttributes redir){

		int workflowTypeId=workflowConfigurationService.getWorkflowTypeId(workflowConfigurationModel.getModuleTypeId(),workflowConfigurationModel.getBusinessTypeId(),workflowConfigurationModel.getProjectTypeId(),workflowConfigurationModel.getCategoryId());		
		model.setUniqueId((int) workflowConfigurationModel.getNumId());
		List<ProcessMasterModel> list=workflowConfigurationService.getProcessForWorkflow(workflowTypeId);
			redir.addFlashAttribute("applicationId",model.getUniqueId());
		 String url=list.get(0).getStrProcessPath();
		 url=url.replaceAll("/PMS/", "");
			return "redirect:"+url;
		
	}
	
	@RequestMapping(value="/startProjectWorkflow")
	public String startProjectWorkflow(HttpServletRequest request,WorkflowConfigurationModel workflowConfigurationModel,@ModelAttribute("model") RedirectModel model,RedirectAttributes redir){

		int workflowTypeId=workflowConfigurationService.getWorkflowTypeId(workflowConfigurationModel.getModuleTypeId(),workflowConfigurationModel.getBusinessTypeId(),workflowConfigurationModel.getProjectTypeId(),workflowConfigurationModel.getCategoryId());		
		model.setUniqueId((int) workflowConfigurationModel.getNumId());
		List<ProcessMasterModel> list=workflowConfigurationService.getProcessForWorkflow(workflowTypeId);
			redir.addFlashAttribute("projectId",model.getUniqueId());
		 String url=list.get(0).getStrProcessPath();
		 url=url.replaceAll("/PMS/", "");
		 //Added by devesh to fix access denied page on reload
		 String encProjectId = encryptionService.encrypt(""+model.getUniqueId());
		 url += "?encProjectId="+encProjectId;
		 //End of Code
			return "redirect:"+url;
		
	}
	
	@RequestMapping(value="/getWorkflowSteps")
	public @ResponseBody List<ProcessMasterModel> getWorkflowSteps(HttpServletRequest request,@RequestParam("moduleTypeId") int moduleTypeId,@RequestParam("applicationId") long applicationId){
		ApplicationModel model=new ApplicationModel();
		int workflowTypeId=0;
		if(applicationId >0 && moduleTypeId == 1 ){
			model=	applicationService.getApplicationById(applicationId);
			workflowTypeId=workflowConfigurationService.getWorkflowTypeId(moduleTypeId,model.getBusinessTypeId(),model.getProjectTypeId(),model.getCategoryId());

		}else{
		 workflowTypeId=workflowConfigurationService.getWorkflowTypeId(moduleTypeId,0,0,0);
		}
		List<ProcessMasterModel> list=workflowConfigurationService.getProcessForWorkflow(workflowTypeId);

		return list;		

	}
	
	@RequestMapping(value="/updateRoles")
	public @ResponseBody String updateRoles(HttpServletRequest request,String ids, String flag) {
		// System.out.println("ids::"+ids);
		// System.out.println("Flag::"+flag);
		String returnString = "";
		UserInfo userInfo =((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		String idlist[] = ids.split("##");
		int empId = Integer.parseInt(idlist[0]);
		int roleId= Integer.parseInt(idlist[1]);
		int projectId= Integer.parseInt(idlist[2]);
		/*int GrpId= Integer.parseInt(idlist[3]);*/
		//Modified by devesh on 14-03-24 to set group id directly from employee master
		String assignedGroupsStr = userInfo.getAssignedGroups();
		int GrpId= 0;
        boolean containsZero = Arrays.stream(assignedGroupsStr.split(",")).map(String::trim).anyMatch("0"::equals);
        if (containsZero) {
        	GrpId= Integer.parseInt(idlist[3]);
        } else {
        	EmployeeMasterModel empmodel = employeeMasterService.getEmployeeMasterDomainById(empId);
    		GrpId= (int) empmodel.getGroupId();
        }
		
		//End of code
		int organizationId= Integer.parseInt(idlist[4]);
		EmployeeRoleMasterModel empDomain=new EmployeeRoleMasterModel();
		empDomain.setNumGroupId(GrpId);
		empDomain.setNumOrganisationId(organizationId);
		empDomain.setNumProjectId(projectId);
		empDomain.setNumRoleId(roleId);
		userInfo.setSelectedEmployeeRole(empDomain);
		
		//userInfo.setAssignedGroups(GrpId+"");
		//userInfo.setAssignedOrganisation(organizationId+"");
		//userInfo.setAssignedProjects(projectId+"");
		if (flag.equals("2")) {
			if (!globalService.checkDuplicateDefaultRole(empId)) {
				// System.out.println("New Insert");
				globalService.setDefaultRole(empId,
						roleId,
						projectId,
						GrpId, organizationId);
			} else {
				// System.out.println("Update");
				globalService.updateDefaultRole(empId,
						roleId,
						projectId,
						GrpId, organizationId);
			}
			userInfo.setDefaultEmployeeRole(empDomain);

		}
			returnString = "1";
		return returnString;

	}
	
	//Bhavesh (10-10-23) request method for getting default project  value
	@RequestMapping(value = "/popUpRolesForDefault", method=RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> showDefault(HttpServletRequest request) {
        
        
        String returnString3 = "";
        

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
		
		EmployeeRoleMasterModel defaultEmpRole = employeeRoleMasterService.getDefaultEmployeeRoleByEmpId(usrInfo.getEmployeeId());
		
		List<EmployeeRoleMasterModel> employeeRoles = employeeRoleMasterService
				.getActiveEmployeeRoleByEmpId(usrInfo.getEmployeeId());
		
		if(defaultEmpRole!=null){
	
		returnString3=defaultEmpRole.getStrProjectName();
					
		}

        Map<String,Object> test1 = new HashMap<>();
        try{
        	
        		 	
        test1.put("empRoles",employeeRoles);
       
        test1.put("defaultProjectName",returnString3);
        test1.put("numProjectId", defaultEmpRole.getNumProjectId());
        }
        
        catch (Exception e){};
        return test1;
    }
   
    
	
}
