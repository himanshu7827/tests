package in.pms.master.controller;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.master.model.ActionModel;
import in.pms.master.service.ActionService;

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
public class ActionController {

	@Autowired
	ActionService actionService;
	
	@RequestMapping(value="/action")	
	public String others(@ModelAttribute ActionModel actionModel,HttpServletRequest request){		
	
			List<ActionModel> modelList= actionService.loadAllAction();
			request.setAttribute("data", modelList);
		
		return "Action";
		
	}

	@RequestMapping(value="/saveUpdateAction",method=RequestMethod.POST)	
	public ModelAndView saveUpdateAction(ActionModel actionModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
		String strDuplicateCheck = actionService.checkDuplicateAction(actionModel);
		if(null == strDuplicateCheck){
		ActionModel actionModels = actionService.saveUpdateOthers(actionModel);
		if(actionModels!=null){
			if(actionModels.getNumActionId()==0){
				redirectAttributes.addFlashAttribute("message",  "Action saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Action updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
	
		mav.setViewName("redirect:/action");
		return mav;
	}


	@RequestMapping(value = "/deleteAction",  method = RequestMethod.POST)
	public String deleteOthers(ActionModel actionModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  ActionModel actionModels= actionService.deleteOthers(actionModel);
			    		if(actionModels.getNumActionId() !=0){
			    			redirectAttributes.addFlashAttribute("message",  "Record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			       
	                    
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
		return "redirect:/action";
    }
	

}
