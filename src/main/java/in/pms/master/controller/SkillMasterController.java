package in.pms.master.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.master.domain.SkillMasterDomain;
import in.pms.master.model.SkillMasterModel;
import in.pms.master.service.SkillMasterService;
import in.pms.master.validator.SkillMasterValidator;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mst")
@Slf4j
public class SkillMasterController {

	@Autowired
	SkillMasterService skillMasterService;
	
	@RequestMapping("/SkillMaster")
	public String getAllThrustMaster(HttpServletRequest request, SkillMasterModel skillMasterModel){		
		List<SkillMasterModel> list = skillMasterService.getAllSkillData();
		request.setAttribute("data", list);		
		return "SkillMaster";
	}
	
	@RequestMapping(value="/saveSkillMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateSkillMaster(HttpServletRequest request, SkillMasterModel skillMasterModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new SkillMasterValidator().validate(skillMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	  		List<SkillMasterModel> list = skillMasterService.getAllSkillData();
	  		request.setAttribute("data", list);		
	    	  mav.setViewName("SkillMaster");
	          return mav;
	      }
		String strDuplicateCheck = skillMasterService.checkDuplicateSkillData(skillMasterModel);
		if(null == strDuplicateCheck){
			SkillMasterDomain skillMasterDomain = skillMasterService.saveSkillData(skillMasterModel);
		if(skillMasterDomain.getNumId()>0){
			if(skillMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Skill details saved Successfully with Id "+skillMasterDomain.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Skill details updated Successfully with Id "+skillMasterDomain.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/SkillMaster");
		return mav;
		
	}
	
	
}
