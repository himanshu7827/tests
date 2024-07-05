package in.pms.global.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.pms.global.model.WorkflowModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.WorkflowService;


@Controller
public class WorkflowController {
	
	@Autowired
	WorkflowService workflowService;
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping(value = "/getNextRoleActionDetails", method = RequestMethod.POST)
	public @ResponseBody List<WorkflowModel> getNextRoleActionDetails(WorkflowModel workflowModel,HttpServletRequest request) {
		List<WorkflowModel> workflowModelList=workflowService.getNextRoleActionDetails(workflowModel);
		return workflowModelList;
	}
	
	@RequestMapping(value = "/doWorkAccrodingToAction", method = RequestMethod.POST)
	public @ResponseBody WorkflowModel doWorkAccrodingToAction(WorkflowModel workflowModel,HttpServletRequest request) {
		int numActionId=0;
		String strDecActionnId=encryptionService.dcrypt(workflowModel.getStrEncActionId());
		numActionId=Integer.parseInt(strDecActionnId);
		WorkflowModel workflowModel2=new WorkflowModel();
		switch(numActionId) 
        { 
		    case 1:
		    	workflowModel2.setNumActionId(numActionId);
		    	break;
			case 2:
				workflowModel2.setNumActionId(numActionId);	
	    	break;
	        case 3: 
	        	workflowModel2.setNumActionId(numActionId);
	        break;	
	        default :
	        workflowModel2=workflowService.doWorkAccrodingToAction(workflowModel);
	        break;	
        }
		
    
        
		
		return workflowModel2;
	}
	
	@RequestMapping(value="/loadTransactionDetails",method=RequestMethod.POST)	
	@ResponseBody
	public List<WorkflowModel> getTransactionDetails(WorkflowModel workflowModel,HttpServletRequest request){		
		
		int customId =0;
		int workflowId=0;
		String encCustomId = workflowModel.getEncCustomId();
		if(null != encCustomId && !encCustomId.equals("")){
			customId = Integer.parseInt(encryptionService.dcrypt(encCustomId));
		}
		if(null != workflowModel.getEncWorkflowId() ){
			workflowId = Integer.parseInt(encryptionService.dcrypt(workflowModel.getEncWorkflowId()));
		}
		
		return workflowService.getTransactionDetails(customId,workflowId);
	}
}
