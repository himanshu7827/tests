package in.pms.transaction.controller;

import java.util.Map;

import in.pms.transaction.model.CollaborativeOrgDetailsModel;
import in.pms.transaction.service.CollaborativeOrgDetailsService;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class CollaborativeOrgDetailsController {
	
	@Autowired
	CollaborativeOrgDetailsService collaborativeOrgDetailsService;
	
	@RequestMapping("/collaborativeOrgDetails")
	public String getAllCollaborativeOrgDetails(HttpServletRequest request, CollaborativeOrgDetailsModel collaborativeOrgDetailsModel,Model model){		
		Map md = model.asMap();
		long applicationId=0;
	  if(md.get("applicationId")!=null){
	       applicationId=Long.parseLong(md.get("applicationId").toString());
			JSONArray outputArray = collaborativeOrgDetailsService.getAllActiveCollaborativeOrgDetailsDomain(applicationId);
			request.setAttribute("outputArray", outputArray);
			request.setAttribute("applicationId", applicationId);
			return "collaborativeOrgDetails";
	   }else{
	    	return "redirect:/accessDenied"; 
	    }	
	}
	
	
	@RequestMapping(value="/saveUpdateCollaborativeOrgDetails",method=RequestMethod.POST)
	@ResponseBody
	public String saveUpdateCollaborativeOrgDetails(HttpServletRequest request, CollaborativeOrgDetailsModel collaborativeOrgDetailsModel, RedirectAttributes redirectAttributes, BindingResult bindingResult){
		return ""+collaborativeOrgDetailsService.saveUpdateCollaborativeOrgDetails(collaborativeOrgDetailsModel);
		
	}

	@RequestMapping(value="/deleteCollaborativeOrgDetails",method=RequestMethod.POST)
	@ResponseBody
	public long deleteCollaborativeOrgDetails(HttpServletRequest request, CollaborativeOrgDetailsModel collaborativeOrgDetailsModel, RedirectAttributes redirectAttributes, BindingResult bindingResult){
		return collaborativeOrgDetailsService.deleteCollaborativeOrgDetails(collaborativeOrgDetailsModel);
		
	}
	
}
