package in.pms.master.controller;

import in.pms.master.dao.EmpTypeMasterDao;
import in.pms.master.model.DocStageMasterModel;
import in.pms.master.model.EmpTypeMasterModel;
import in.pms.master.service.EmpTypeMasterService;
import in.pms.master.validator.DocStageMasterValidator;
import in.pms.master.validator.EmpTypeMasterValidator;

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
public class EmpTypeMasterController {

	
	@Autowired
	EmpTypeMasterService empTypeMasterService;

	
	@RequestMapping("/empTypeMaster")
	public String empTypeMaster(EmpTypeMasterModel empTypeMasterModel,HttpServletRequest request){		
		List<EmpTypeMasterModel> list = empTypeMasterService.getAllEmpTypeMasterDomain();
		request.setAttribute("data", list);	
		return "empTypeMaster";
	}

	@RequestMapping(value="/saveUpdateEmpTypeMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateEmpTypeMaster(EmpTypeMasterModel empTypeMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
		
		new EmpTypeMasterValidator().validate(empTypeMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<EmpTypeMasterModel> list = empTypeMasterService.getAllActiveEmpTypeMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("empTypeMaster");
	          return mav;
	      }
		
		
		String strDuplicateCheck = empTypeMasterService.checkDuplicateEmpTypeName(empTypeMasterModel);
		if(null == strDuplicateCheck){
		long id = empTypeMasterService.saveUpdateEmpTypeMaster(empTypeMasterModel);
		if(id>0){
			if(empTypeMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Employee Type record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Employee Type record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			
			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<EmpTypeMasterModel> list = empTypeMasterService.getAllEmpTypeMasterDomain();
		request.setAttribute("empTypeMasterModel",new EmpTypeMasterModel());
		request.setAttribute("data", list);	
		mav.setViewName("redirect:/mst/empTypeMaster");
		return mav;
	}

	@RequestMapping(value = "/deleteEmpType",  method = RequestMethod.POST)
	public String deleteEmpType(EmpTypeMasterModel empTypeMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
			    		long result= empTypeMasterService.deleteEmpType(empTypeMasterModel);
			    		if(result !=-1){
			    			redirectAttributes.addFlashAttribute("message",  "Employee Type record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		List<EmpTypeMasterModel> list = empTypeMasterService.getAllEmpTypeMasterDomain();
						request.setAttribute("data", list);        
	                    
	                    
			    	}
			    	catch(Exception e){
		          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/mst/empTypeMaster";
    }
	
}
