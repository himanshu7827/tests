 package in.pms.transaction.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import groovy.util.logging.Slf4j;
import in.pms.transaction.model.PatchTrackerModel;
import in.pms.transaction.service.PatchTrackerService;
import in.pms.transaction.validator.PatchTrackerValidator;

import java.util.List;


@Controller
@RequestMapping("/mst")
@Slf4j

public class PatchTrackerController {
	 

		
	@Autowired
	PatchTrackerService patchTrackerService;
	
	@PreAuthorize("hasAuthority('WRITE_PATCH_TRACKER')")
	@RequestMapping("/patchTracker")
	public String patchTracker(PatchTrackerModel patchTrackerModel, HttpServletRequest request){	
	
		//request.setAttribute("patchTrackerModel",new PatchTrackerModel());
		return "patchTracker";
               }
	
	

	@RequestMapping(value="/addpatchdetails",method=RequestMethod.POST)	
	public String savePatch(PatchTrackerModel patchTrackerModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){
	   
		new PatchTrackerValidator().validate(patchTrackerModel, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println("in error log");
			return "patchTracker";
		}
		
		long t = patchTrackerService.addpatchdetails(patchTrackerModel);
		
	   if(t>0){
				redirectAttributes.addFlashAttribute("message",  "Patch details saved Successfully with Id "+t);	
				redirectAttributes.addFlashAttribute("status", "success");
	   }
		
	return "redirect:/mst/patchTracker";
	}	
	
	@RequestMapping(value="getRequesterName",method=RequestMethod.POST)
	@ResponseBody
	public List<String> getRequesterName(@RequestParam String searchString){
		return patchTrackerService.getRequesterName(searchString);
	}
	
	@RequestMapping(value="getNameOfFiles",method=RequestMethod.POST)
	@ResponseBody
	public List<String> getNameOfFiles(@RequestParam String searchString){
		return patchTrackerService.getNameOfFiles(searchString);
	}
	@RequestMapping(value="getTeamMembers",method=RequestMethod.POST)
	@ResponseBody
	public List<String> getTeamMembers(@RequestParam String searchString){
		return patchTrackerService.getTeamMembers(searchString);
	}
	
	@RequestMapping(value="getModules",method=RequestMethod.POST)
	@ResponseBody
	public List<String> getModules(@RequestParam String searchString){
		return patchTrackerService.getModules(searchString);
	}
}		
		
	
	
	
