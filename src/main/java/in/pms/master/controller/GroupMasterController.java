package in.pms.master.controller;

import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.validator.GroupMasterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class GroupMasterController {
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	OrganisationMasterService organisationMasterService;
	
	@RequestMapping("/groupMaster")
	public String getAllGroupMaster(HttpServletRequest request, GroupMasterModel groupMasterModel){		
		List<GroupMasterModel> list = groupMasterService.getAllGroupMasterDomain();
		List<OrganisationMasterModel> organisationList = organisationMasterService.getAllActiveOrganisationMasterDomain();
		request.setAttribute("organisationList", organisationList);
		request.setAttribute("data", list);		
		return "groupMaster";
	}
	
	@RequestMapping(value="/saveUpdateGroupMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateOrganisationMaster(HttpServletRequest request, GroupMasterModel groupMasterModel, BindingResult bindingResult,RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new GroupMasterValidator().validate(groupMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<GroupMasterModel> list = groupMasterService.getAllGroupMasterDomain();
	  		List<OrganisationMasterModel> organisationList = organisationMasterService.getAllActiveOrganisationMasterDomain();
	  		request.setAttribute("organisationList", organisationList);
	  		request.setAttribute("data", list);
	    	  mav.setViewName("groupMaster");
	          return mav;
	      }
		String strDuplicateCheck = groupMasterService.checkDuplicateGroupName(groupMasterModel);
		if(null == strDuplicateCheck){
		long id = groupMasterService.saveUpdateGroupMaster(groupMasterModel);
		if(id>0){
			if(groupMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Group record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Group record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}		
		mav.setViewName("redirect:/mst/groupMaster");
		return mav;
	}
	
	@RequestMapping(value="/getGroupByOrganisationId",method=RequestMethod.POST)
	@ResponseBody
	public List<GroupMasterModel> getGroupByOrganisationId(@RequestParam("organisationId") long organisationId){
		return groupMasterService.getAllActiveGrpMasterDomain(organisationId);
	}
}
