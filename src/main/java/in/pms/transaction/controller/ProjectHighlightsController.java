package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ProjectHighlightsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProjectHighlightsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
	public class ProjectHighlightsController {
 
		@Autowired 
		ProjectHighlightsService highlightService;
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		
		@RequestMapping(value="/Highlights",method=RequestMethod.GET)
		public String Highlights(HttpServletRequest request,@ModelAttribute ProjectHighlightsModel projectHighlightsModel ) {
			monthlyProgressService.writeProgressReportAuthorityCheck();
			String encMonthlyProgressId = projectHighlightsModel.getEncMonthlyProgressId();
			if(null != encMonthlyProgressId){
				//System.out.println(" encrypted monthly progressid"+encMonthlyProgressId);
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				//System.out.println(" decrypted monthly progressid"+monthlyProgressId);
				//Load Group, Project Id based on encMonthlyProgressId
				MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
				request.setAttribute("monthlyProgressModel", monthlyProgressModel);
				long categoryId =Long.parseLong(encryptionService.dcrypt(projectHighlightsModel.getEncCategoryId()));
				ProjectHighlightsModel model= highlightService.loadHighlightsDetail(monthlyProgressId,categoryId);
				
				if(model!=null){
					String descrCompleted=model.getStrCompActivityHtml();
					String descrOngoing=model.getStrOngoingActivityHtml();
					
					if(descrCompleted!=null){
					String newCompleted=descrCompleted.replaceAll("\"", "(?*");

					request.setAttribute("newCompleted",newCompleted);
					}
					if(descrOngoing!=null){
						String newOngoing=descrOngoing.replaceAll("\"", "(?*");
						request.setAttribute("newOngoing",newOngoing);
						}
					}
				request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
				request.setAttribute("categoryId", projectHighlightsModel.getEncCategoryId());
				request.setAttribute("encCategoryId", encryptionService.encrypt(""+categoryId));
				//request.setAttribute("modelList", modelList);
			}
			//request.setAttribute("reportId", projectHighlightsModel.getNumReportId());
			
			/*ProjectHighlightsModel model=highlightService.getSavedHighlights(projectHighlightsModel.getNumReportId());
			if(model!=null){
			String descrCompleted=model.getStrCompActivityHtml();
			String descrOngoing=model.getStrOngoingActivityHtml();
			
			if(descrCompleted!=null){
			String newCompleted=descrCompleted.replaceAll("\"", "(?*");

			request.setAttribute("newCompleted",newCompleted);
			}
			if(descrOngoing!=null){
				String newOngoing=descrOngoing.replaceAll("\"", "(?*");
				request.setAttribute("newOngoing",newOngoing);
				}
			}*/
			return "HighlightDetails";
		}
		
		@RequestMapping(value = "/SaveHighlights", method = RequestMethod.POST)
	    public @ResponseBody
	    ProjectHighlightsModel SaveProjectInnovations(ProjectHighlightsModel projectHighlightsModel, HttpServletRequest request,HttpServletResponse response) {
			monthlyProgressService.writeProgressReportAuthorityCheck();
			return highlightService.SaveHighlights(projectHighlightsModel);
	        
	 }
		}