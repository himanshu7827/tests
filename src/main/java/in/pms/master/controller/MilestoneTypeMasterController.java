package in.pms.master.controller;


import in.pms.master.domain.MilestoneTypeMaster;
import in.pms.master.model.MilestoneTypeMasterForm;
import in.pms.master.service.MilestoneTypeMasterService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

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
public class MilestoneTypeMasterController {

	@Autowired
	MilestoneTypeMasterService milestoneTypeMasterService;
	
	@RequestMapping("/MilestoneType")
	public String getAllThrustMaster(HttpServletRequest request, MilestoneTypeMasterForm milestoneTypeMasterForm){		
		List<MilestoneTypeMasterForm> list = milestoneTypeMasterService.getAllMilestoneTypeData();
		request.setAttribute("data", list);		
		return "MilestoneType";
	}
	
	@RequestMapping(value="/saveMilestoneType",method=RequestMethod.POST)	
	public ModelAndView saveUpdateSkillMaster(HttpServletRequest request, MilestoneTypeMasterForm milestoneTypeMasterForm,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
	
		String strDuplicateCheck = milestoneTypeMasterService.checkDuplicateMilestoneTypeData(milestoneTypeMasterForm);
		if(null == strDuplicateCheck){
			MilestoneTypeMaster milestoneTypeMaster = milestoneTypeMasterService.saveMilestoneTypeData(milestoneTypeMasterForm);
		if(milestoneTypeMaster.getNumId()>0){
			if(milestoneTypeMasterForm.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Milestone Type details saved Successfully with Id "+milestoneTypeMaster.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Milestone Type details updated Successfully with Id "+milestoneTypeMaster.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/MilestoneType");
		return mav;
		
	}
	
	
}
