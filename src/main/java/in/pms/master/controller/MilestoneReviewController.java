package in.pms.master.controller;


import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.master.model.MilestoneReviewModel;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.service.MilestoneReviewService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectMilestoneService;
import in.pms.master.validator.MilestoneReviewValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class MilestoneReviewController {

	@Autowired
	ProjectMilestoneService projectMilestoneService;
	@Autowired
	MilestoneReviewService milestoneReviewService;
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
    EncryptionService encryptionService;
	
	
	@RequestMapping("/MilestoneReviewMaster/{encId}")
	public String getMilestoneReviewMaster(HttpServletRequest request,@PathVariable("encId") String encId, MilestoneReviewModel milestoneReviewModel){		
		String strMilestoneId= encryptionService.dcrypt(encId);
		long milestoneId= Long.parseLong(strMilestoneId);
		//long milestoneId = 20;
		
		  	List<MilestoneReviewModel> list = milestoneReviewService.getMilestoneReviewDataByMilestoneId(milestoneId);
			request.setAttribute("data", list);	
			ProjectMilestoneForm projectMilestoneForm=projectMilestoneService.getMilestoneDataByMilestoneId(milestoneId);
			request.setAttribute("data1", projectMilestoneForm);	
			if(null != list && list.size()>=2){
				request.setAttribute("thisDate", list.get(0).getCompletionDate());
				request.setAttribute("lastDate", list.get(1).getCompletionDate());

			}
			else if(null != list && list.size()==1){
				request.setAttribute("thisDate", list.get(0).getCompletionDate());
				request.setAttribute("lastDate", projectMilestoneForm.getExpectedStartDate());
			}
			String referrer = request.getHeader("referer");
			if(null != referrer && referrer.contains("projectDetails")){
				request.setAttribute("referrer", referrer);
			}
			

		    return "MilestoneReviewMaster";
	}
	
	@RequestMapping(value="/saveMilestoneReview",method=RequestMethod.POST)	
	public ModelAndView saveUpdateMilestoneReview(HttpServletRequest request, MilestoneReviewModel milestoneReviewModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new MilestoneReviewValidator().validate(milestoneReviewModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  /*-------- After Server-Side Error re-direct the milestone details [05-04-2024] ---------*/
	    	  ProjectMilestoneForm projectMilestoneForm=projectMilestoneService.getMilestoneDataByMilestoneId(milestoneReviewModel.getMilestoneId());
			  request.setAttribute("data1", projectMilestoneForm);
			  /*-------- End of After Server-Side Error re-direct the milestone details [05-04-2024] ---------*/
	  		mav.setViewName("MilestoneReviewMaster");
	          return mav;
	      }
		long id = milestoneReviewService.saveMilestoneReviewData(milestoneReviewModel);
		if(id>0){
			if(milestoneReviewModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Milestone Review details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Milestone Review details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		if(null != milestoneReviewModel.getReferrer()&& milestoneReviewModel.getReferrer().contains("projectDetails")){
			mav.setViewName("redirect:"+milestoneReviewModel.getReferrer());
		}
		else{
			mav.setViewName("redirect:/mst/MilestoneReviewDetailMaster");
		}
	
		
		return mav; 
	}
	
	@RequestMapping("/MilestoneReviewDetailMaster")
	public String getMilestoneReviewDetail(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm){	
		 	String date=ResourceBundleFile.getValueFromKey("NoOfDaysForReview");
			int days=Integer.parseInt(date);
			
				String referer = request.getHeader("referer");
				if(null != referer && referer.contains("projectDetails")){
					request.setAttribute("referer", referer);
				}
			
			List<ProjectMilestoneForm> list1 = projectMilestoneService.getMilestoneReviewDetail(days);
			List<ProjectMilestoneForm> list2 = projectMilestoneService.getMilestoneReviewDetail();			
			request.setAttribute("data1", list1);	
			request.setAttribute("data2", list2);	
			request.setAttribute("days", days);	
	
		    return "MilestoneReviewDetailMaster";
	}
	
	@RequestMapping("/MilestoneDueInOneMonthDetailMaster")
	public String getMilestoneDueInOneMonthDetail(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm){	
		 	//String date=ResourceBundleFile.getValueFromKey("NoOfDaysForReview");
			int days= 30;
			
				String referer = request.getHeader("referer");
				if(null != referer && referer.contains("projectDetails")){
					request.setAttribute("referer", referer);
				}
			
			List<ProjectMilestoneForm> list1 = projectMilestoneService.getMilestoneReviewDetail(days);
					//System.out.println(list1);
			request.setAttribute("data1", list1);	
			
	
		    return "MilestoneDueInOneMonthDetailMaster";
	}
	
	@RequestMapping("/MilestoneExceededDeadlineDetailMaster")
	public String getMilestoneExceededDeadlineDetail(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm){	
		 	String date=ResourceBundleFile.getValueFromKey("NoOfDaysForReview");
			int days=Integer.parseInt(date);
			
				String referer = request.getHeader("referer");
				if(null != referer && referer.contains("projectDetails")){
					request.setAttribute("referer", referer);
				}
			List<ProjectMilestoneForm> list2 = projectMilestoneService.getMilestoneReviewDetail();			
			
			request.setAttribute("data2", list2);	
			request.setAttribute("days", days);	
	
		    return "MilestoneExceededDeadlineDetailMaster";
	}
	
	@RequestMapping(value="/ReviewDetailByNoOfDays",method=RequestMethod.POST)
	public @ResponseBody List<ProjectMilestoneForm> getData(@RequestParam("days") int days, HttpServletRequest request){
	  	
		List<ProjectMilestoneForm> list = projectMilestoneService.getMilestoneReviewDetail(days);
				
		return list;
	}
	
	@RequestMapping(value="/milestoneReviewMasterHistory",method=RequestMethod.POST)
	public @ResponseBody List<MilestoneReviewModel> milestoneReviewMasterHistory(@RequestParam("encId") String encId, HttpServletRequest request){
		String strMilestoneId= encryptionService.dcrypt(encId);
		long milestoneId= Long.parseLong(strMilestoneId);
		List<MilestoneReviewModel> list = milestoneReviewService.getMilestoneReviewHistory(milestoneId);
			return list;
	}
	
	@RequestMapping("/MilestoneExceededDeadlineForEDDashboard")
	public String MilestoneExceededDeadlineForEDDashboard(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm){	
		 	String date=ResourceBundleFile.getValueFromKey("NoOfDaysForReview");
			int days=Integer.parseInt(date);
			
				String referer = request.getHeader("referer");
				if(null != referer && referer.contains("projectDetails")){
					request.setAttribute("referer", referer);
				}
			List<ProjectMilestoneForm> list2 = projectMilestoneService.getMilestoneReviewDetail();			
			
			request.setAttribute("data2", list2);	
			request.setAttribute("days", days);	
	
		    return "MilestoneExceededDeadlineForEDDashboard";
	}
	
	@RequestMapping("/MilestoneDueInOneMonthForED")
	public String MilestoneDueInOneMonthForED(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm){	
		 	//String date=ResourceBundleFile.getValueFromKey("NoOfDaysForReview");
			int days= 30;
			
				String referer = request.getHeader("referer");
				if(null != referer && referer.contains("projectDetails")){
					request.setAttribute("referer", referer);
				}
			
			List<ProjectMilestoneForm> list1 = projectMilestoneService.getMilestoneReviewDetail(days);
					//System.out.println(list1);
			request.setAttribute("data1", list1);	
			
	
		    return "MilestoneDueInOneMonthForED";
	}
	
	@RequestMapping(value="/MilestoneExceededDeadlineDetailByDates", method=RequestMethod.POST)
	public @ResponseBody List<ProjectMilestoneForm> MilestoneExceededDeadlineDetailByDates(@RequestParam("compDate") String compDate,@RequestParam("symbol") String symbol,ProjectInvoiceMasterModel projectInvoiceMasterModel ,HttpServletRequest request){
		List<ProjectMilestoneForm> list2 = projectMilestoneService.MilestoneExceededDeadlineDetailByDates(compDate,symbol);	
		
		return list2;		
	}
	
	//-------------------  Get All Milestone Exceeded Deadline Details [21-08-2023] -----------------------------------------------
		@RequestMapping(value="/getAllMilestoneExceededDeadlineDetailMaster", method=RequestMethod.POST)
		public @ResponseBody List<ProjectMilestoneForm> getAllMilestoneExceededDeadlineDetail(HttpServletRequest request){
			List<ProjectMilestoneForm> list2 = projectMilestoneService.getMilestoneReviewDetail();	
			return list2;		
		}
}
