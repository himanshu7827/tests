package in.pms.master.controller;

import in.pms.master.model.ActionModel;
import in.pms.master.model.ApprovalMasterModel;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.ActionService;
import in.pms.master.service.ApprovalMasterService;
import in.pms.master.service.RoleMasterService;
import in.pms.transaction.model.WorkflowConfigurationModel;
import in.pms.transaction.service.WorkflowConfigurationService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/mst")
@Slf4j
public class ApprovalMasterController {


	@Autowired
	WorkflowConfigurationService workflowConfigurationService;
	
	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	ActionService actionService;
	
	@Autowired
	ApprovalMasterService approvalMasterService;
	
	
	@RequestMapping(value="/ApprovalMaster",method = { RequestMethod.GET, RequestMethod.POST })
	public String ApprovalMaster(HttpServletRequest request, @ModelAttribute ApprovalMasterModel approvalMasterModel){		

		List<WorkflowConfigurationModel> list = workflowConfigurationService.getAllWorkflow();
		request.setAttribute("data", list);	
		
		List<RoleMasterModel> roleList = roleMasterService.getAllRoleMaster();
		request.setAttribute("roleList", roleList);	
		
		List<ActionModel> actionList= actionService.loadAllAction();
		request.setAttribute("actionList", actionList);
	
		return "ApprovalMaster";
	}
	
	
	@RequestMapping(value="/getApprovalDetails",method=RequestMethod.POST)	
	@ResponseBody
	public ApprovalMasterModel getApprovedJobDetailsCreated(@ModelAttribute("approvalMasterModel") ApprovalMasterModel approvalMasterModel ,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		ApprovalMasterModel list = approvalMasterService.getApprovalDetails(approvalMasterModel.getWorkshopId(), approvalMasterModel.getRoleId(), approvalMasterModel.getActionId());		
		return list;
		
	}
	
	@RequestMapping(value="/saveApprovalMaster",method=RequestMethod.POST)	
	public String saveApprovalMaster(HttpServletRequest request, ApprovalMasterModel approvalMasterModel, RedirectAttributes redirectAttributes,BindingResult bindingResult){		

		ApprovalMasterModel approvalMasterModel2= approvalMasterService.saveApprovalDetails(approvalMasterModel);
		
			if(approvalMasterModel2!=null){
				if(approvalMasterModel2.getNumApprovalId()==0){
				redirectAttributes.addFlashAttribute("message",  "Approval details saved Successfully ");	
				redirectAttributes.addFlashAttribute("status", "success");
				}else{
				redirectAttributes.addFlashAttribute("message",  "Approval details updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}	}				
		
		return "redirect:/mst/ApprovalMaster";
	}

			  
	
	
}
