package in.pms.master.controller;

import in.pms.master.dao.ExpenditureHeadDao;
import in.pms.master.model.ExpenditureHeadModel;
import in.pms.master.service.ExpenditureHeadService;
import in.pms.master.validator.ExpenditureHeadValidator;

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

public class ExpenditureHeadController {


	@Autowired
	ExpenditureHeadService expenditureHeadService;
	
	
	@Autowired
	ExpenditureHeadDao expenditureHeadDao;
	
	@RequestMapping("/expenditureHeadMaster")
	public String expenditureHeadMaster(ExpenditureHeadModel expenditureHeadModel,HttpServletRequest request){		
		List<ExpenditureHeadModel> list = expenditureHeadService.getAllExpenditureHeadMasterDomain();
		request.setAttribute("data", list);	
		return "expenditureHeadMaster";
	}

	@RequestMapping(value="/saveUpdateExpenditureHead",method=RequestMethod.POST)	
	public ModelAndView saveUpdateExpenditureHead(ExpenditureHeadModel expenditureHeadModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
		
		new ExpenditureHeadValidator().validate(expenditureHeadModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<ExpenditureHeadModel> list = expenditureHeadService.getAllActiveExpenditureHeadMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("expenditureHeadMaster");
	          return mav;
	      }
		
		
		String strDuplicateCheck = expenditureHeadService.checkDuplicateExpenditureHeadName(expenditureHeadModel);
		if(null == strDuplicateCheck){
		long id = expenditureHeadService.saveUpdateExpenditureHead(expenditureHeadModel);
		if(id>0){
			if(expenditureHeadModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Expenditure Head record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Expenditure Head record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			
			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<ExpenditureHeadModel> list = expenditureHeadService.getAllExpenditureHeadMasterDomain();
		/*request.setAttribute("expenditureHeadModel",new ExpenditureHeadModel());
		request.setAttribute("data", list);	*/
		mav.setViewName("redirect:/mst/expenditureHeadMaster");
		return mav;
	}

	@RequestMapping(value = "/deleteExpenditureHead",  method = RequestMethod.POST)
	public String deleteExpenditureHead(ExpenditureHeadModel expenditureHeadModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
			    		long result= expenditureHeadService.deleteExpenditureHead(expenditureHeadModel);
			    		if(result !=-1){
			    			redirectAttributes.addFlashAttribute("message",  "Expenditure Head record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		List<ExpenditureHeadModel> list = expenditureHeadService.getAllExpenditureHeadMasterDomain();
						request.setAttribute("data", list);        
	                    
	                    
			    	}
			    	catch(Exception e){
		          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/mst/expenditureHeadMaster";
    }
	
}
