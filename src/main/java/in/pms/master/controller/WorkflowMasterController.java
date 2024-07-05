package in.pms.master.controller;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.global.model.WorkflowModel;
import in.pms.master.service.WorkflowMasterService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class WorkflowMasterController {

	@Autowired
	WorkflowMasterService workflowMasterService;
	
	@RequestMapping(value="/workFlowMaster")	
	public String others(@ModelAttribute WorkflowModel workflowModel,HttpServletRequest request){		
	
			List<WorkflowModel> modelList= workflowMasterService.loadAllWorkFLow();
			request.setAttribute("data", modelList);
		
		return "workFlow";
		
	}

	@RequestMapping(value="/saveUpdateWorkflow",method=RequestMethod.POST)	
	public ModelAndView saveUpdateAction(WorkflowModel workflowModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
		String strDuplicateCheck = workflowMasterService.checkDuplicateWorkflow(workflowModel);
		if(null == strDuplicateCheck){
		WorkflowModel workflowModels = workflowMasterService.saveUpdateWorkflow(workflowModel);
		if(workflowModels!=null){
			if(workflowModels.getNumWorkflowId()==0){
				redirectAttributes.addFlashAttribute("message",  "Workflow saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Workflow updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}
		else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
	
		mav.setViewName("redirect:/workFlowMaster");
		return mav;
	}


	@RequestMapping(value = "/deleteWorkflow",  method = RequestMethod.POST)
	public String deleteOthers(WorkflowModel workflowModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  WorkflowModel workflowModels= workflowMasterService.deleteWorkflow(workflowModel);
			    		if(workflowModels.getNumWorkflowId() !=0){
			    			redirectAttributes.addFlashAttribute("message",  "Workflow deleted Successfully !");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			       
	                    
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
		return "redirect:/workFlowMaster";
    }
	

}
