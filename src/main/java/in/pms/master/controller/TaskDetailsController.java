package in.pms.master.controller;


import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ResponseModel;
import in.pms.master.model.TaskAssignmentModel;
import in.pms.master.model.TaskDetailsModel;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.TaskAssignmentService;
import in.pms.master.service.TaskDetailsService;
import in.pms.master.validator.TaskDetailValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class TaskDetailsController {

    @Autowired
	ProjectMasterService projectMasterService;
    @Autowired
    TaskDetailsService taskDetailsService;
    @Autowired
	FileUploadService fileUploadService;
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    TaskAssignmentService taskAssignmentService;
	
	@RequestMapping("/TaskDetailsMaster")
	public String getAllTaskDetailsMaster(HttpServletRequest request, TaskDetailsModel taskDetailsModel){		
		List<ProjectMasterModel> projectData = projectMasterService.getAllActiveProjectMasterData();
		request.setAttribute("projectData", projectData);	
		List<TaskDetailsModel> list = taskDetailsService.getAllTaskDetailsData();
		request.setAttribute("list", list);
		List<ProjectMilestoneDomain> milestonelist = taskDetailsService.getMilestoneWithoutActualEndDt();
		request.setAttribute("milestonelist", milestonelist);
		
		return "TaskDetailsMaster";
	}
	
	/*@RequestMapping(value="/activityData",method=RequestMethod.POST)
	public @ResponseBody List<MilestoneActivityModel> getdata(@RequestParam("milestoneId") long id, HttpServletRequest request){
		List<MilestoneActivityModel> data = taskDetailsService.getActivityData(id);
		return data;
	}*/
	
	@RequestMapping(value="/saveTaskDetailsMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateTaskDetailsMaster(HttpServletRequest request, TaskDetailsModel taskDetailsModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new TaskDetailValidator().validate(taskDetailsModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<ProjectMasterModel> projectData = projectMasterService.getAllActiveProjectMasterData();
	  		  request.setAttribute("projectData", projectData);	
	  		  List<TaskDetailsModel> list = taskDetailsService.getAllTaskDetailsData();
	  		  request.setAttribute("list", list);
	  		  List<ProjectMilestoneDomain> milestonelist = taskDetailsService.getMilestoneWithoutActualEndDt();
			  request.setAttribute("milestonelist", milestonelist);
	    	  mav.setViewName("TaskDetailsMaster");
	          return mav;
	      }
		long id = taskDetailsService.saveTaskDetailsData(taskDetailsModel);
		if(id>0){
			if(taskDetailsModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Task details saved successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Task details updated successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		
		mav.setViewName("redirect:/mst/TaskDetailsMaster");
		return mav;
		
	}
	
	

	@RequestMapping(value="downloadTaskFile",method = RequestMethod.POST)	
	public void downloadTaskFile(TaskDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		fileUploadService.downloadTaskFile(model.getEncDocumentId(),response);
	}
	
	@RequestMapping(value="/getTaskDetailsData",method=RequestMethod.POST)
	public @ResponseBody TaskDetailsModel getTaskdata(@RequestParam("encTaskId") String encTaskId, HttpServletRequest request){
		String encId=encryptionService.dcrypt(encTaskId);
		long numTaskId = Long.parseLong(encId);
		TaskDetailsModel data = taskDetailsService.getTaskDetailsData(numTaskId);
		return data;
	}

	@RequestMapping(value="/assignedTask",method = RequestMethod.POST)	
	public @ResponseBody ResponseModel AssignedData( HttpServletRequest request,TaskAssignmentModel taskAssignmentModel){
		ResponseModel responseModel=new ResponseModel();
		long id = taskAssignmentService.saveTaskAssignmentData(taskAssignmentModel);
		
		if(id>0){
			String encId=encryptionService.dcrypt(taskAssignmentModel.getEncTaskId());
			long numTaskId = Long.parseLong(encId);
			taskDetailsService.updateTaskDetailStatus(numTaskId, "Assigned");
			responseModel.setStatus(true);
			responseModel.setSuccessMsg("Task Assigned Successfully");
			
			List<TaskDetailsModel> list = taskDetailsService.getAllActiveTaskDetailsData("New,Withdraw");
			responseModel.setTaskDetailsModelList(list);
			}else{
				responseModel.setStatus(false);
				responseModel.setFailureMsg("Task is not assigned");
			}		
			return responseModel;
		}
	
	@RequestMapping(value="/ongoingTask",method = RequestMethod.POST)	
	public @ResponseBody ResponseModel ongoingData( HttpServletRequest request,@RequestParam("encTaskId") String encTaskId){
		ResponseModel responseModel=new ResponseModel();
		
			String encId=encryptionService.dcrypt(encTaskId);
			long numTaskId = Long.parseLong(encId);
			taskDetailsService.updateTaskDetailStatus(numTaskId, "WIP");
			responseModel.setStatus(true);
			
			List<TaskDetailsModel> list = taskDetailsService.getAllActiveTaskDetailsData("Assigned");
			responseModel.setTaskDetailsModelList(list);
			
			return responseModel;
		}
	@RequestMapping(value="/ongoingWithdrawTask",method = RequestMethod.POST)	
	public @ResponseBody ResponseModel ongoingWithdrawData( HttpServletRequest request,@RequestParam("encTaskId") String encTaskId){
		ResponseModel responseModel=new ResponseModel();
		
			String encId=encryptionService.dcrypt(encTaskId);
			long numTaskId = Long.parseLong(encId);
			taskDetailsService.updateTaskDetailStatus(numTaskId, "Withdraw");
			responseModel.setStatus(true);
			
			List<TaskDetailsModel> list = taskDetailsService.getAllActiveTaskDetailsData("Assigned");
			responseModel.setTaskDetailsModelList(list);
			
			return responseModel;
		}
	@RequestMapping(value="/workInProgressTask",method = RequestMethod.POST)	
	public @ResponseBody ResponseModel workInProgressData( HttpServletRequest request,@RequestParam("encTaskId") String encTaskId){
		ResponseModel responseModel=new ResponseModel();
		
			String encId=encryptionService.dcrypt(encTaskId);
			long numTaskId = Long.parseLong(encId);
			taskDetailsService.updateTaskDetailStatus(numTaskId, "Completed");
			responseModel.setStatus(true);
			
			List<TaskDetailsModel> list = taskDetailsService.getAllActiveTaskDetailsData("WIP");
			responseModel.setTaskDetailsModelList(list);
			
			return responseModel;
		}
	
}
