package in.pms.master.controller;



import in.pms.master.dao.ActivityMasterDao;
import in.pms.master.model.ActivityMasterModel;
import in.pms.master.model.SubActivityMasterModel;
import in.pms.master.service.ActivityMasterService;
import in.pms.master.service.SubActivityMasterService;
import in.pms.master.validator.ActivityMasterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j

public class ActivityMasterController {

	@Autowired
	ActivityMasterService activityMasterService;
	
	
	@Autowired
	SubActivityMasterService subActivityMasterService;
	
	@Autowired
	ActivityMasterDao activityMasterDao;
	
	@RequestMapping("/activityMaster")
	public String activityMaster(ActivityMasterModel activityMasterModel,HttpServletRequest request){		
		List<ActivityMasterModel> list = activityMasterService.getAllActivityMasterDomain();
		
		List<SubActivityMasterModel> subActivityList = subActivityMasterService.getAllActiveSubActivityMasterDomain();
		request.setAttribute("subActivityList", subActivityList);
		
		request.setAttribute("data", list);	
		return "activityMaster";
	}

	@RequestMapping(value="/saveUpdateActivityMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateActivityMaster(ActivityMasterModel activityMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
		
		new ActivityMasterValidator().validate(activityMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<ActivityMasterModel> list = activityMasterService.getAllActiveActivityMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("activityMaster");
	          return mav;
	      }
		
		
		String strDuplicateCheck = activityMasterService.checkDuplicateActivityName(activityMasterModel);
		if(null == strDuplicateCheck){
		long id = activityMasterService.saveUpdateActivityMaster(activityMasterModel);
		if(id>0){
			if(activityMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Activity record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Activity record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			
			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<ActivityMasterModel> list = activityMasterService.getAllActivityMasterDomain();
		request.setAttribute("activityMasterModel",new ActivityMasterModel());
		request.setAttribute("data", list);	
		mav.setViewName("redirect:/mst/activityMaster");
		return mav;
	}

	@RequestMapping(value = "/deleteActivity",  method = RequestMethod.POST)
	public String deleteActivity(ActivityMasterModel activityMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
			    		long result= activityMasterService.deleteActivity(activityMasterModel);
			    		if(result !=-1){
			    			redirectAttributes.addFlashAttribute("message",  "activity record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		List<ActivityMasterModel> list = activityMasterService.getAllActivityMasterDomain();
						request.setAttribute("data", list);        
	                    
	                    
			    	}
			    	catch(Exception e){
		          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/mst/activityMaster";
    }
	
}
