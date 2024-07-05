package in.pms.master.controller;


import in.pms.global.service.EncryptionService;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ManpowerRequirementModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.service.ManpowerRequirementService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.validator.ManpowerRequirementValidator;
import in.pms.transaction.model.DesignationForClientModel;
import in.pms.transaction.service.DesignationForClientService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class ManpowerRequirementController {

	@Autowired
	ProjectMasterService projectMasterService;	
	@Autowired
	ManpowerRequirementService manpowerRequirementService;
	
	
	@Autowired
	DesignationForClientService designationForClientService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping("/ManpowerRequirementMaster")
	public String getAllManpowerRequirement(HttpServletRequest request, ManpowerRequirementModel manpowerRequirementModel,Model map){		
		Map md = map.asMap();
		int projectId=0;	
		
		if(null != request.getParameter("encProjectId")){
			try{
			String encProjectId = request.getParameter("encProjectId");
			String strProjectId = encryptionService.dcrypt(encProjectId);
			projectId = Integer.parseInt(strProjectId);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		      if(md.get("projectId")!=null){
		    	  projectId=Integer.parseInt(md.get("projectId").toString());
		      }
		      if(projectId>0){
		    	  ProjectMasterDomain domain=projectMasterService.getProjectMasterDataById(projectId);
		  			request.setAttribute("projectDetail", domain);
		  			List<ProjectMasterForm> projectRoleDetails=projectMasterService.getProjectRolesDetails();
		  			request.setAttribute("projectRoleDetails", projectRoleDetails);
		  			int categoryId = domain.getApplication().getCategoryMaster().getNumId();
		  			request.setAttribute("categoryId", categoryId);
		  			List<DesignationForClientModel> designations = designationForClientService.getDesignationByCategory(categoryId);
		  			request.setAttribute("designations", designations);
		    	 	request.setAttribute("projectId", projectId);
		    	 	request.setAttribute("encProjectId", encryptionService.encrypt(""+projectId));
		      }else{		
		    	  request.setAttribute("projectId", 0);
		    	  return "redirect:/accessDenied"; 
		      }						
		

		
		return "ManpowerRequirement";
	}
	
	@RequestMapping(value="/saveManpowerRequirementMaster",method=RequestMethod.POST)	
	public ModelAndView saveManpowerRequirement(HttpServletRequest request, ManpowerRequirementModel manpowerRequirementModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new ManpowerRequirementValidator().validate(manpowerRequirementModel, bindingResult);
	      if (bindingResult.hasErrors()) {
		/*List<ProjectMasterModel> projectData = projectMasterService.getAllActiveProjectMasterData();
		request.setAttribute("projectData", projectData);
		List<RoleMasterModel> list = roleMasterService.getAllActiveRoleMasterDomain();
		request.setAttribute("data", list);	*/
	    	  ProjectMasterDomain domain=projectMasterService.getProjectMasterDataById(manpowerRequirementModel.getProjectId());
	  			request.setAttribute("projectDetail", domain);
	  			
	  			int categoryId = domain.getApplication().getCategoryMaster().getNumId();
	  			List<DesignationForClientModel> designations = designationForClientService.getDesignationByCategory(categoryId);
	  			request.setAttribute("categoryId", categoryId);
	  			request.setAttribute("designations", designations);
		mav.setViewName("ManpowerRequirement");
        return mav;
	      }
		long id = manpowerRequirementService.saveManpowerRequirementData(manpowerRequirementModel);
		if(id>0){
			if(manpowerRequirementModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Manpower Requirement details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Manpower Requirement details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
  		redirectAttributes.addFlashAttribute("projectId",manpowerRequirementModel.getProjectId());

		mav.setViewName("redirect:/mst/ManpowerRequirementMaster");
		return mav;
		
		}
	
	@RequestMapping(value="/ManpowerData",method=RequestMethod.POST)
	public @ResponseBody List<ManpowerRequirementModel> getdata(@RequestParam("projectId") long projectId, HttpServletRequest request){
		List<ManpowerRequirementModel> data = manpowerRequirementService.getAllManpowerRequirement(projectId);
		return data;
	}
	
	@RequestMapping(value="/deleteManpowerReq",method= RequestMethod.POST)
	public @ResponseBody int deleteManpowerReq(@RequestParam("numId") long numId,HttpServletRequest request){
		
		return  manpowerRequirementService.deleteManpowerReq(numId);
		
	}
	@RequestMapping(value="/saveAdditionalManpowerRequirement",method=RequestMethod.POST)	
	public @ResponseBody long saveAdditionalManpowerRequirement(HttpServletRequest request, ManpowerRequirementModel manpowerRequirementModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		return manpowerRequirementService.saveManpowerRequirementData(manpowerRequirementModel);
	}
}