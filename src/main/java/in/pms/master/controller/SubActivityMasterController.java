package in.pms.master.controller;

import in.pms.master.dao.SubActivityMasterDao;
import in.pms.master.model.ActivityMasterModel;
import in.pms.master.model.ProjectExpenditureDetailsModel;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.model.SubActivityMasterModel;
import in.pms.master.service.ActivityMasterService;
import in.pms.master.service.SubActivityMasterService;
import in.pms.master.validator.ActivityMasterValidator;
import in.pms.master.validator.ProjectExpenditureDetailsValidator;
import in.pms.master.validator.SubActivityMasterValidator;

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
public class SubActivityMasterController {


	@Autowired
	SubActivityMasterService subActivityMasterService;
	
	@Autowired
	ActivityMasterService activityMasterService;
	
	@Autowired
	SubActivityMasterDao subActivityMasterDao;
	
	@RequestMapping("/subActivityMaster")
	public String subActivityMaster(SubActivityMasterModel subActivityMasterModel,HttpServletRequest request){		
		List<SubActivityMasterModel> list = subActivityMasterService.getAllSubActivityMasterDomain();
		/*List<ActivityMasterModel> activityList = activityMasterService.getAllActiveActivityMasterDomain();*/
		request.setAttribute("data", list);	
		/*request.setAttribute("activityList", activityList);*/
		return "subActivityMaster";
	}

	@RequestMapping(value="/saveUpdateSubActivityMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateSubActivityMaster(SubActivityMasterModel subActivityMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
			
		/*new SubActivityMasterValidator().validate(subActivityMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<SubActivityMasterModel> list = subActivityMasterService.getAllActiveSubActivityMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("subActivityMaster");
	          return mav;
	      }
		
		
		
		long id = subActivityMasterService.saveUpdateSubActivityMaster(subActivityMasterModel);
		if(id>0){
			if(subActivityMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "SubActivity record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "SubActivity record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		
		List<SubActivityMasterModel> list = subActivityMasterService.getAllSubActivityMasterDomain();
		
		mav.setViewName("redirect:/mst/subActivityMaster");
		return mav;
	}
		*/
		
		
		
		
		new SubActivityMasterValidator().validate(subActivityMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<SubActivityMasterModel> list = subActivityMasterService.getAllActiveSubActivityMasterDomain();

	  		request.setAttribute("data", list);
	    	  mav.setViewName("subActivityMaster");
	          return mav;
	      }
		
		String strDuplicateCheck = subActivityMasterService.checkDuplicateSubActivityName(subActivityMasterModel);
		if(null == strDuplicateCheck){
		long id = subActivityMasterService.saveUpdateSubActivityMaster(subActivityMasterModel);
		if(id>0){
			if(subActivityMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "SubActivity record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "SubActivity record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			
			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<SubActivityMasterModel> list = subActivityMasterService.getAllSubActivityMasterDomain();
		request.setAttribute("subActivityMasterModel",new SubActivityMasterModel());
		request.setAttribute("data", list);	
		mav.setViewName("redirect:/mst/subActivityMaster");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/deletesubActivity",  method = RequestMethod.POST)
	public String deletesubActivity(SubActivityMasterModel subActivityMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
			    		long result= subActivityMasterService.deleteSubActivity(subActivityMasterModel);
			    		if(result !=-1){
			    			redirectAttributes.addFlashAttribute("message",  "subActivity Head record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		List<SubActivityMasterModel> list = subActivityMasterService.getAllSubActivityMasterDomain();
						request.setAttribute("data", list);        
	                    
	                    
			    	}
			    	catch(Exception e){
		          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/mst/subActivityMaster";
    }
	
}
