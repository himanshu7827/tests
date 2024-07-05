package in.pms.transaction.controller;

import java.util.List;

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.model.ProjectInnovationsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProjectInnovationsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
	public class ProjectInnovationsController {
 
		@Autowired 
		ProjectInnovationsService projectInnovationsService;
		
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		
		@RequestMapping(value="/Innovations",method=RequestMethod.GET)
		public String projectPublicationFromConference(HttpServletRequest request,@ModelAttribute ProjectInnovationsModel projectInnovationsModel) {
			monthlyProgressService.writeProgressReportAuthorityCheck();

	String encMonthlyProgressId = projectInnovationsModel.getEncMonthlyProgressId();
			
			if(null != encMonthlyProgressId){
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				//Load Group, Project Id based on encMonthlyProgressId
				MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
				request.setAttribute("monthlyProgressModel", monthlyProgressModel);
				long categoryId =Long.parseLong(encryptionService.dcrypt(projectInnovationsModel.getEncCategoryId()));
				List<ProjectInnovationsModel> modelList = projectInnovationsService.loadInnovationDetail(monthlyProgressId,categoryId);
				request.setAttribute("modelList", modelList);
				request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
				request.setAttribute("encCategoryId", projectInnovationsModel.getEncCategoryId());
			
			}
			return "ProjectInnovations";
		}
		
		@RequestMapping(value = "/SaveInnovations", method = RequestMethod.POST)
	    public @ResponseBody
	    ProjectInnovationsModel SaveProjectInnovations(ProjectInnovationsModel projectInnovationsModel, HttpServletRequest request,HttpServletResponse response) {
			monthlyProgressService.writeProgressReportAuthorityCheck();

	       return projectInnovationsService.SaveProjectInnovations(projectInnovationsModel);
	        
	 }
		
		@RequestMapping(value="/deleteInnovationDetails",method=RequestMethod.POST)
		public String deleteInnovationDetails(ProjectInnovationsModel projectInnovationsModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
			{
				 
			if(projectInnovationsModel.getNumIds()==null || projectInnovationsModel.getNumIds().length==0){

				
				redirectAttributes.addFlashAttribute("message",  "There is some problem in delete");
				redirectAttributes.addFlashAttribute("status",  "error");
				return "redirect:/Innovations?encMonthlyProgressId="+projectInnovationsModel.getEncMonthlyProgressId()+"&encCategoryId="+projectInnovationsModel.getEncCategoryId();
			}
			else{
				    	try
				    	{ 
						
				    		projectInnovationsService.deleteInnovationDetails(projectInnovationsModel);
				    		redirectAttributes.addFlashAttribute("message",  "Innovation details deleted Successfully");	
				    		redirectAttributes.addFlashAttribute("status",  "success");
				    		
		                    
		                    
				    	}
				    	catch(Exception e)
				    	{
			          	System.out.println(e);

				    	}
				    	return "redirect:/Innovations?encMonthlyProgressId="+projectInnovationsModel.getEncMonthlyProgressId()+"&encCategoryId="+projectInnovationsModel.getEncCategoryId();
			}
				    }
		
		
		
		
		}