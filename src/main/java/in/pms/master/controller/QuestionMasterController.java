package in.pms.master.controller;


import in.pms.master.model.AnswerMasterModel;
import in.pms.master.model.QuestionMasterModel;
import in.pms.master.service.AnswerMasterService;
import in.pms.master.service.QuestionMasterService;
import in.pms.master.validator.QuestionMasterValidator;
import lombok.extern.slf4j.Slf4j;

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
public class QuestionMasterController {

	@Autowired
	QuestionMasterService questionMasterService;
	
	@Autowired
	AnswerMasterService answerMasterService;
	
	@RequestMapping("/QuestionMaster")
	public String getAllQuestionMaster(HttpServletRequest request, QuestionMasterModel questionMasterModel){		
		List<QuestionMasterModel> list = questionMasterService.getActiveQuestionData();
		request.setAttribute("data", list);		
		List<AnswerMasterModel> list1 = answerMasterService.getActiveAnswerData();
		request.setAttribute("data1", list1);
		return "QuestionMaster";
	}
	
	@RequestMapping(value="/saveQuestionMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateQuestionMaster(HttpServletRequest request, QuestionMasterModel questionMasterModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new QuestionMasterValidator().validate(questionMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	List<QuestionMasterModel> list = questionMasterService.getActiveQuestionData();
	  		request.setAttribute("data", list);		
	  		List<AnswerMasterModel> list1 = answerMasterService.getActiveAnswerData();
	  		request.setAttribute("data1", list1);		
	    	  mav.setViewName("QuestionMaster");
	          return mav;
	      }
		String strDuplicateCheck = questionMasterService.checkDuplicateQuestionData(questionMasterModel);
		if(null == strDuplicateCheck){
		long id = questionMasterService.saveQuestionData(questionMasterModel);
		if(id>0){
			if(questionMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Question details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Question details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/QuestionMaster");
		return mav;
		
	}
	
	
	
}
