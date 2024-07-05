package in.pms.master.controller;


import in.pms.master.dao.DesignationMasterDao;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.service.DesignationMasterService;
import in.pms.master.validator.DesignationMasterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class DesignationMasterController {
	
	@Autowired
	DesignationMasterService designationMasterService;
	
	
	@Autowired
	DesignationMasterDao designationMasterDao;

	@RequestMapping("/designationMaster")
	public String designationMaster(DesignationMasterModel designationMasterModel,HttpServletRequest request){		
		List<DesignationMasterModel> list = designationMasterService.getAllDesignationMasterDomain();
		request.setAttribute("data", list);	
		return "designationMaster";
	}

	
	@RequestMapping("/getSpecificDesignations")
	public @ResponseBody List<DesignationMasterModel> getSpecificDesignations(DesignationMasterModel designationMasterModel,HttpServletRequest request){		
		List<DesignationMasterModel> list = designationMasterService.getSpecificDesigantion(designationMasterModel.getIsThirdPartySpecific());
		return list;
	}

	
	@RequestMapping(value="/saveUpdateDesignationMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateDesignationMaster(DesignationMasterModel designationMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
		
		
		new DesignationMasterValidator().validate(designationMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<DesignationMasterModel> list = designationMasterService.getAllActiveDesignationMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("designationMaster");
	          return mav;
	      }
		
		
		
		String strDuplicateCheck = designationMasterService.checkDuplicateDesignationName(designationMasterModel);
		if(null == strDuplicateCheck){
		long id = designationMasterService.saveUpdateDesignationMaster(designationMasterModel);
		if(id>0){
			if(designationMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Designation record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Designation record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			
			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<DesignationMasterModel> list = designationMasterService.getAllDesignationMasterDomain();
		request.setAttribute("designationMasterModel",new DesignationMasterModel());
		request.setAttribute("data", list);	
		mav.setViewName("redirect:/mst/designationMaster");
		return mav;
	}

	@RequestMapping(value = "/deleteDesignation",  method = RequestMethod.POST)
	public String deleteDesignation(DesignationMasterModel designationMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
			    		long result= designationMasterService.deleteDesignation(designationMasterModel);
			    		if(result !=-1){
			    			redirectAttributes.addFlashAttribute("message",  "Designation record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		List<DesignationMasterModel> list = designationMasterService.getAllDesignationMasterDomain();
						request.setAttribute("data", list);        
	                    
	                    
			    	}
			    	catch(Exception e){
		          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/mst/designationMaster";
    }
	
	@RequestMapping(value="saveDesignationData",method= RequestMethod.POST)
	@ResponseBody
	public long saveExitInterviewMaster(DesignationMasterModel designationMasterModel,HttpServletRequest request){	
		long id = designationMasterService.saveDesignationData(designationMasterModel);
		return id; 		
	}
	
	
}
