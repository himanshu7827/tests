package in.pms.master.controller;
import in.pms.master.model.ProjectRoleMasterForm;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.ProjectRoleMasterService;
import in.pms.master.service.RoleMasterService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class ProjectRoleMasterController {

	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	ProjectRoleMasterService projectRoleMasterService;


	@RequestMapping("/ProjectRoleMaster")
	public String ProjectRoleMaster(HttpServletRequest request, ProjectRoleMasterForm projectRoleMasterForm){		
		List<ProjectRoleMasterForm> list = projectRoleMasterService.getAllProjectRole();
		request.setAttribute("data", list);		
		return "ProjectRoleMaster";
	}
	
	@RequestMapping(value="/saveProjectRoleMaster",method=RequestMethod.POST)	
	public String saveUpdateRoleMaster(HttpServletRequest request, ProjectRoleMasterForm projectRoleMasterForm, RedirectAttributes redirectAttributes,BindingResult bindingResult){		
		int strDuplicateCheck = projectRoleMasterService.checkDuplicateProjectRoleName(projectRoleMasterForm);
		if(strDuplicateCheck==0){
		long id = projectRoleMasterService.saveUpdateProjectRoleMaster(projectRoleMasterForm);
		if(id>0){
			if(projectRoleMasterForm.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Project Role details saved Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  " Project Role details updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}
		else{
			redirectAttributes.addFlashAttribute("message","Project Based Roles is already exist");
			redirectAttributes.addFlashAttribute("status", "error");
		}	
				
		return "redirect:/mst/ProjectRoleMaster";
	}
	
	@RequestMapping(value="/deleteProjectRoleMaster",method=RequestMethod.POST)
	public String deleteProjectRoleMaster(ProjectRoleMasterForm projectRoleMasterForm, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
		if(projectRoleMasterForm.getNumIds()==null || projectRoleMasterForm.getNumIds().length==0){
			List<ProjectRoleMasterForm> list = projectRoleMasterService.getAllProjectRole();
			request.setAttribute("data", list);		
			return "ProjectRoleMaster";
		}
		else{
			    	try
			    	{					
			    		projectRoleMasterService.deleteProjectRoleData(projectRoleMasterForm);
			    		redirectAttributes.addFlashAttribute("message",  " Project Role details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		List<ProjectRoleMasterForm> list = projectRoleMasterService.getAllProjectRole();
						request.setAttribute("data", list);		
	                    
			    	}
			    	catch(Exception e)
			    	{
			    		redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    		redirectAttributes.addFlashAttribute("status", "error");
			    		log.error(e.getCause().getMessage());
			    		log.error(e.getMessage());

			    	}
			    	return "redirect:/mst/ProjectRoleMaster";
		}
			    }
	
	
}
