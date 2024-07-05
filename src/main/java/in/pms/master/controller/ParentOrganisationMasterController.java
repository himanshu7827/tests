package in.pms.master.controller;

import in.pms.master.model.ParentOrganisationMasterModel;
import in.pms.master.service.ParentOrganisationMasterService;
import in.pms.master.validator.OrganisationMasterValidator;
import in.pms.master.validator.ParentOrganisationMasterValidator;

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
	public class ParentOrganisationMasterController {
		
		@Autowired
		ParentOrganisationMasterService parentOrganisationMasterService;
		
		@RequestMapping("/parentOrganisationMaster")
		public String getAllOrganisationMaster(HttpServletRequest request, ParentOrganisationMasterModel parentOrganisationMasterModel){		
			List<ParentOrganisationMasterModel> list = parentOrganisationMasterService.getAllParentOrganisation();
			request.setAttribute("data", list);	
			return "parentOrganisationMaster";
		}
		
		@RequestMapping(value="/saveUpdateParentOrganisationMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateOrganisationMaster(HttpServletRequest request, ParentOrganisationMasterModel parentOrganisationMasterModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){	
		
			ModelAndView mav = new ModelAndView();
			new ParentOrganisationMasterValidator().validate(parentOrganisationMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  List<ParentOrganisationMasterModel> list = parentOrganisationMasterService.getAllParentOrganisation();
					request.setAttribute("data", list);	
					mav.setViewName("parentOrganisationMaster");
					return mav;  
		    	  
		      }
			String strDuplicateCheck = parentOrganisationMasterService.checkDuplicateParentOrganisationName(parentOrganisationMasterModel);
			if(null == strDuplicateCheck){
			long id = parentOrganisationMasterService.saveUpdateParentOrganisationMaster(parentOrganisationMasterModel);
			if(id>0){
				if(parentOrganisationMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Organisation record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Organisation record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			}else{
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
			mav.setViewName("redirect:/mst/parentOrganisationMaster");
			return mav;
		}		
		
	
	}
