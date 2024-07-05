package in.pms.master.controller;


import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.master.service.ThrustAreaMasterService;
import in.pms.master.validator.GroupMasterValidator;
import in.pms.master.validator.ThrustAreaMasterValidator;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class ThrustAreaMasterController {

	@Autowired
	ThrustAreaMasterService thrustAreaMasterService;
	
	@RequestMapping("/ThrustAreaMaster")
	public String getAllThrustMaster(HttpServletRequest request, ThrustAreaMasterForm thrustAreaMasterForm){		
		List<ThrustAreaMasterForm> list = thrustAreaMasterService.getAllThrustAreaData();
		request.setAttribute("data", list);		
		return "ThrustAreaMaster";
	}
	
	@RequestMapping(value="/saveThrustAreaMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateThrustAreaMaster(HttpServletRequest request, ThrustAreaMasterForm thrustAreaMasterForm,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new ThrustAreaMasterValidator().validate(thrustAreaMasterForm, bindingResult);
	      if (bindingResult.hasErrors()) {
	  		List<ThrustAreaMasterForm> list = thrustAreaMasterService.getAllThrustAreaData();
	  		request.setAttribute("data", list);		
	    	  mav.setViewName("ThrustAreaMaster");
	          return mav;
	      }
		String strDuplicateCheck = thrustAreaMasterService.checkDuplicateThrustAreaData(thrustAreaMasterForm);
		if(null == strDuplicateCheck){
		long id = thrustAreaMasterService.saveThrustAreaData(thrustAreaMasterForm);
		if(id>0){
			if(thrustAreaMasterForm.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Thrust Area details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Thrust Area details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/ThrustAreaMaster");
		return mav;
		
	}
	
	@RequestMapping(value="/deleteThrustAreaMaster",method=RequestMethod.POST)
	public String deleteThrustAreaMaster(ThrustAreaMasterForm thrustAreaMasterForm, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
		if(thrustAreaMasterForm.getNumIds()==null || thrustAreaMasterForm.getNumIds().length==0){
			List<ThrustAreaMasterForm> list = thrustAreaMasterService.getAllThrustAreaData();
			request.setAttribute("data", list);	
			return "ThrustAreaMaster";
		}
		else{
			    	try
			    	{
					
			    		thrustAreaMasterService.deleteThrustAreaData(thrustAreaMasterForm);
			    		redirectAttributes.addFlashAttribute("message",  "Thrust Area details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		List<ThrustAreaMasterForm> list = thrustAreaMasterService.getAllThrustAreaData();
						request.setAttribute("data", list);		
	                   
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
			    	log.error(e.getCause().getMessage());
		          	log.error(e.getMessage());

			    	}
			    	return "redirect:/mst/ThrustAreaMaster";
		}
			    }
	
	
}
