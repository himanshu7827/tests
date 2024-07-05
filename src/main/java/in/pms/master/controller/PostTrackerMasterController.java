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
import in.pms.master.domain.PostTrackerMasterDomain;
import in.pms.master.model.PostTrackerMasterModel;
import in.pms.master.service.PostTrackerMasterService;
import in.pms.master.validator.PostTrackerMasterValidator;
import in.pms.transaction.model.BudgetHeadMasterForm;

@Controller
@RequestMapping("/mst")
public class PostTrackerMasterController {
	
	@Autowired
	PostTrackerMasterService postTrackerMasterService;
	@Autowired
	EncryptionService encryptionService;

	
	@RequestMapping("/postTrackerMaster")
	public String getAllPostTrackerMaster(HttpServletRequest request, PostTrackerMasterModel postTrackerMasterModel){		
		List<PostTrackerMasterModel> list = postTrackerMasterService.getAllPostTrackerMasterDomain();
		request.setAttribute("data", list);	
		return "postTrackerMaster";
	}
	
	@RequestMapping(value="/saveUpdatePostTrackerMaster",method=RequestMethod.POST)	
	public String saveUpdatePostTrackerMaster(HttpServletRequest request, PostTrackerMasterModel postTrackerMasterModel, RedirectAttributes redirectAttributes,BindingResult bindingResult){		
	
		new PostTrackerMasterValidator().validate(postTrackerMasterModel, bindingResult); 
		if(bindingResult.hasErrors()){
			List<PostTrackerMasterModel> list = postTrackerMasterService.getAllPostTrackerMasterDomain();
			request.setAttribute("data", list);
			request.setAttribute("postTrackerMasterModel",postTrackerMasterModel);
			return "postTrackerMaster";
		}
		
		long id = postTrackerMasterService.saveUpdatePostTrackerMaster(postTrackerMasterModel);
		if(id>0){
			if(postTrackerMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "PostTracker record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "PostTracker record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}else{
			redirectAttributes.addFlashAttribute("message",  "Something went Wrong");	
			redirectAttributes.addFlashAttribute("status", "error");
		}
		
		return "redirect:/mst/postTrackerMaster";
	}
	
	
	@RequestMapping(value="getAllPostTracker",method=RequestMethod.POST)
	public @ResponseBody List<PostTrackerMasterModel> getAllActivePostTrackerMaster(){		
		return postTrackerMasterService.getAllPostTrackerMasterDomain();
	}
	
}