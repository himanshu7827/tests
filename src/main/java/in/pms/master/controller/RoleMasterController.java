package in.pms.master.controller;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.RoleMasterService;
import in.pms.master.validator.RoleMasterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class RoleMasterController {

	@Autowired
	RoleMasterService roleMasterService;
	
	@RequestMapping("/RoleMaster")
	public String getAllRoleMaster(HttpServletRequest request, RoleMasterModel roleMasterModel){		
		List<RoleMasterModel> list = roleMasterService.getAllRoleMaster();
		request.setAttribute("data", list);		
		return "RoleMaster";
	}
	
	@RequestMapping(value="/saveRoleMaster",method=RequestMethod.POST)	
	public String saveUpdateRoleMaster(HttpServletRequest request, RoleMasterModel roleMasterModel, RedirectAttributes redirectAttributes,BindingResult bindingResult){		
		new RoleMasterValidator().validate(roleMasterModel, bindingResult);
		  if (bindingResult.hasErrors()) {
				List<RoleMasterModel> list = roleMasterService.getAllRoleMaster();
				request.setAttribute("data", list);		
				return "RoleMaster";
	      }
		
		String strDuplicateCheck = roleMasterService.checkDuplicateRoleName(roleMasterModel);
		if(null == strDuplicateCheck){
		long id = roleMasterService.saveUpdateRoleMaster(roleMasterModel);
		if(id>0){
			if(roleMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Role details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Role details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}			
		return "redirect:/mst/RoleMaster";
	}
	
	@RequestMapping(value="/deleteRoleMaster",method=RequestMethod.POST)
	public String deleteRoleMaster(RoleMasterModel roleMasterModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
		if(roleMasterModel.getNumIds()==null || roleMasterModel.getNumIds().length==0){
			List<RoleMasterModel> list = roleMasterService.getAllRoleMaster();
			request.setAttribute("data", list);	
			return "RoleMaster";
		}
		else{
			    	try
			    	{					
			    		roleMasterService.deleteRoleData(roleMasterModel);
			    		redirectAttributes.addFlashAttribute("message",  "Role details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
						List<RoleMasterModel> list = roleMasterService.getAllRoleMaster();
						request.setAttribute("data", list);
	                    
			    	}
			    	catch(Exception e)
			    	{
			    		redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    		redirectAttributes.addFlashAttribute("status", "error");
			    		log.error(e.getCause().getMessage());
			    		log.error(e.getMessage());

			    	}
			    	return "redirect:/mst/RoleMaster";
		}
			    }
	
	
}
