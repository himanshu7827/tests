package in.pms.master.controller;

import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.model.ParentOrganisationMasterModel;
import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ParentOrganisationMasterService;
import in.pms.master.service.ThrustAreaMasterService;
import in.pms.master.validator.OrganisationMasterValidator;

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
	public class OrganisationMasterController {
		
		@Autowired
		OrganisationMasterService organisationMasterService;
		
		@Autowired
		ParentOrganisationMasterService parentOrganisationMasterService;
		
		@Autowired
		ThrustAreaMasterService thrustAreaMasterService;
		
		
		
		
		@RequestMapping("/organisationMaster")
		public String getAllOrganisationMaster(HttpServletRequest request, OrganisationMasterModel organisationMasterModel){		
			List<OrganisationMasterModel> list = organisationMasterService.getAllOrganisationMasterDomain();
			request.setAttribute("data", list);	
			List<ThrustAreaMasterForm> talist=thrustAreaMasterService.getActiveThrustAreaData();
			request.setAttribute("tAList", talist);	
			List<ParentOrganisationMasterModel> parentOrganisations = parentOrganisationMasterService.getAllActiveParentOrganisation();
			request.setAttribute("parentOrganisations", parentOrganisations);
			return "organisationMaster";
		}
		
		@RequestMapping(value="/saveUpdateOrganisationMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateOrganisationMaster(HttpServletRequest request, OrganisationMasterModel organisationMasterModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){	
		
			ModelAndView mav = new ModelAndView();
			new OrganisationMasterValidator().validate(organisationMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  List<OrganisationMasterModel> list = organisationMasterService.getAllOrganisationMasterDomain();
					request.setAttribute("organisationMasterModel",new OrganisationMasterModel());
					request.setAttribute("data", list);	
					List<ThrustAreaMasterForm> talist=thrustAreaMasterService.getActiveThrustAreaData();
					request.setAttribute("tAList", talist);	
					List<ParentOrganisationMasterModel> parentOrganisations = parentOrganisationMasterService.getAllActiveParentOrganisation();
					request.setAttribute("parentOrganisations", parentOrganisations);
					mav.setViewName("organisationMaster");
					return mav;  
		    	  
		      }
			String strDuplicateCheck = organisationMasterService.checkDuplicateOrganisationName(organisationMasterModel);
			if(null == strDuplicateCheck){
			long id = organisationMasterService.saveUpdateOrganisationMaster(organisationMasterModel);
			if(id>0){
				if(organisationMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Organisation saved Successfully with Id "+id);	
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
			mav.setViewName("redirect:/mst/organisationMaster");
			return mav;
		}
		
		@RequestMapping(value="/deleteOrganisationMaster",method=RequestMethod.POST)
		public @ResponseBody String deleteOrganisationMaster(OrganisationMasterModel organisationMasterModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
			{
			if(organisationMasterModel.getNumIds()==null || organisationMasterModel.getNumIds().length==0){
				List<OrganisationMasterModel> list = organisationMasterService.getAllOrganisationMasterDomain();
				request.setAttribute("data", list);	
				return "organisationMaster";
			}
			else{
				    	try
				    	{
						
				    		organisationMasterService.deleteOrganisationData(organisationMasterModel);
				    		redirectAttributes.addFlashAttribute("message",  "Organisation details deleted Successfully");	
				    		redirectAttributes.addFlashAttribute("status", "success");
							List<OrganisationMasterModel> list = organisationMasterService.getAllOrganisationMasterDomain();
							request.setAttribute("data", list);		
		                   
		                    
		                    
				    	}
				    	catch(Exception e)
				    	{
				    		log.error(e.getCause().getMessage());
				          	log.error(e.getMessage());

				    	}
				    	return "redirect:/mst/organisationMaster";
			}
				    }
	
	}
