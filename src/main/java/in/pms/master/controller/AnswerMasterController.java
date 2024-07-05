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

import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.model.AnswerMasterModel;
import in.pms.master.service.AnswerMasterService;
import in.pms.master.validator.AnswerMasterValidator;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mst")
@Slf4j
public class AnswerMasterController {

	@Autowired
	AnswerMasterService answerMasterService;
	
	@RequestMapping("/AnswerMaster")
	public String getAllAnswerMaster(HttpServletRequest request, AnswerMasterModel answerMasterModel){		
		List<AnswerMasterModel> list = answerMasterService.getActiveAnswerData();
		request.setAttribute("data", list);		
		return "AnswerMaster";
	}
	
	@RequestMapping(value="/saveAnswerMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateAnswerMaster(HttpServletRequest request, AnswerMasterModel answerMasterModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new AnswerMasterValidator().validate(answerMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	  		List<AnswerMasterModel> list = answerMasterService.getActiveAnswerData();
	  		request.setAttribute("data", list);		
	    	  mav.setViewName("AnswerMaster");
	          return mav;
	      }
		String strDuplicateCheck = answerMasterService.checkDuplicateAnswerData(answerMasterModel);
		if(null == strDuplicateCheck){
			AnswerMasterDomain answerMasterDomain = answerMasterService.saveAnswerData(answerMasterModel);
		if(answerMasterDomain.getNumId()>0){
			if(answerMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Answer details saved Successfully with Id "+answerMasterDomain.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Answer details updated Successfully with Id "+answerMasterDomain.getNumId());	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/AnswerMaster");
		return mav;
		
	}
	
	
}
