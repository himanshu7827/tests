package in.pms.master.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.global.service.EncryptionService;
import in.pms.master.domain.ThirdPartyMasterDomain;
import in.pms.master.model.ThirdPartyMasterModel;
import in.pms.master.service.ThirdPartyMasterService;
import in.pms.transaction.model.BudgetHeadMasterForm;

@Controller
@RequestMapping("/mst")
public class ThirdPartyMasterController {
	
	@Autowired
	ThirdPartyMasterService thirdPartyMasterService;
	@Autowired
	EncryptionService encryptionService;

	
	@RequestMapping("/thirdPartyMaster")
	public String getAllThirdPartyMaster(HttpServletRequest request, ThirdPartyMasterModel thirdPartyMasterModel){		
		List<ThirdPartyMasterModel> list = thirdPartyMasterService.getAllActiveThirdPartyMasterDomain();
		request.setAttribute("data", list);		
		return "thirdPartyMaster";
	}
	
	@RequestMapping(value="/saveUpdateThirdPartyMaster",method=RequestMethod.POST)	
	public String saveUpdateThirdPartyMaster(HttpServletRequest request, ThirdPartyMasterModel thirdPartyMasterModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = thirdPartyMasterService.checkDuplicateThirdPartyName(thirdPartyMasterModel);
		if(null == strDuplicateCheck){
		long id = thirdPartyMasterService.saveUpdateThirdPartyMaster(thirdPartyMasterModel);
		if(id>0){
			if(thirdPartyMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Third Party record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Third Party record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<ThirdPartyMasterModel> list = thirdPartyMasterService.getAllThirdPartyMasterDomain();
		request.setAttribute("thirdPartyMasterModel",new ThirdPartyMasterModel());
		request.setAttribute("data", list);	
		return "redirect:/mst/thirdPartyMaster";
	}
	
	@RequestMapping(value="/deleteThirdPartyMaster",method=RequestMethod.POST)
	public String deleteThirdPartyMaster(ThirdPartyMasterModel thirdPartyMasterModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			  
			if(thirdPartyMasterModel.getNumIds() == null || thirdPartyMasterModel.getNumIds().length==0){
				List<ThirdPartyMasterModel> list = thirdPartyMasterService.getAllThirdPartyMasterDomain();
				//System.out .println(list);
				request.setAttribute("data", list);	
				return "thirdPartyMaster";
			}
			else{
			    	try
			    		{
			    			thirdPartyMasterService.deleteThirdPartyMaster(thirdPartyMasterModel);
			    			redirectAttributes.addFlashAttribute("message",  "Record details deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    			List<ThirdPartyMasterModel> list = thirdPartyMasterService.getAllThirdPartyMasterDomain();
			    			request.setAttribute("data", list);		
			    		}
			    	catch(Exception e)
			    	{
		          	//System.out.println(e);

			    	}
			    	return "redirect:/mst/thirdPartyMaster";
				}
			 }

	@RequestMapping(value="getAllThirdParty",method=RequestMethod.POST)
	public @ResponseBody List<ThirdPartyMasterModel> getAllActiveThirdPartyMaster(){		
		return thirdPartyMasterService.getAllActiveThirdPartyMasterDomain();
	}
	
}
