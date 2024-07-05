package in.pms.master.controller;

import in.pms.master.model.TimeSheetMasterModel;

import in.pms.master.service.TimeSheetMasterService;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class TimeSheetMasterController {
	
	@Autowired
	TimeSheetMasterService timesheetMasterService;
	
	//Method to get all Task list by Himanshu on 10-05-2024
	@RequestMapping("/TimeSheetMaster")
	public String getAllTaskMaster(HttpServletRequest request, @ModelAttribute("timesheetMasterModel") TimeSheetMasterModel timesheetMasterModel){		
		List<TimeSheetMasterModel> list = timesheetMasterService.getAllActiveDescriptionMasterDomain();
		List<TimeSheetMasterModel> taskList = timesheetMasterService.getAllTaskMasterDomain();
		request.setAttribute("taskList", taskList);
		request.setAttribute("data", list);
		
		System.out.println(list);
		return "TimeSheetMaster";
	}
	
	
	//Method to save/update the record by Himanshu on 10-05-2024
	@RequestMapping(value="/saveUpdateDescMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateDescriptionMaster(HttpServletRequest request, @ModelAttribute("timesheetMasterModel") TimeSheetMasterModel timesheetMasterModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		long id = timesheetMasterService.saveUpdateDescMaster(timesheetMasterModel);
		if(id>0){
			if(timesheetMasterModel.getDescId()==0){
				redirectAttributes.addFlashAttribute("message",  "Task record saved Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Task record updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
			
		mav.setViewName("redirect:/mst/TimeSheetMaster");
		return mav;
	}
	
	//Method to save/update the record by Himanshu on 10-05-2024
	@RequestMapping(value="/saveUpdateDescMasterTest",method=RequestMethod.POST)	
	public ModelAndView saveUpdateDescriptionMasterTest(HttpServletRequest request, @ModelAttribute("timesheetMasterModel") TimeSheetMasterModel timesheetMasterModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		long id = timesheetMasterService.saveUpdateDescMasterTest(timesheetMasterModel);
		if(id>0){
			if(timesheetMasterModel.getDescId()==0){
				redirectAttributes.addFlashAttribute("message",  "Task record saved Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Task record updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
			
		mav.setViewName("redirect:/mst/TimeSheetMaster");
		return mav;
	}
	
	
	//Method to get Subtask by Task Id by Himanshu on 10-05-2024
	@RequestMapping(value="/getSubTaskByTaskId",method=RequestMethod.POST)
	@ResponseBody
	public List<TimeSheetMasterModel> getSubtaskByTaskId(@RequestParam("taskId") long taskId){
		return timesheetMasterService.getSubTaskMasterById(taskId);
	}
}
