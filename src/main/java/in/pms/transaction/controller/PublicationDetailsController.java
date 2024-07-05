package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.PublicationDetailsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProjectPublicationDetailsService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


@Controller
	public class PublicationDetailsController {
 
		@Autowired ProjectPublicationDetailsService projectPublicationDetailsService;
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		
		@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
		@RequestMapping(value="/PublicationDetails",method =RequestMethod.GET)
		public String projectPublicationFromConference(HttpServletRequest request,@ModelAttribute PublicationDetailsModel publicationDetailsModel ) {
			monthlyProgressService.writeProgressReportAuthorityCheck();
			String encMonthlyProgressId="";
			String encCategoryId="";
			try {
				Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
				if(flashMap!= null){
				String monthlyProgressId=(String) flashMap.get("encMonthlyProgressId");
				String categoryIdsave=(String) flashMap.get("encCategoryId");
				encMonthlyProgressId= monthlyProgressId;
				encCategoryId =categoryIdsave;
				}
				else{

					encMonthlyProgressId = publicationDetailsModel.getEncMonthlyProgressId();
					encCategoryId = publicationDetailsModel.getEncCategoryId();
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if(null != encMonthlyProgressId){
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
				request.setAttribute("monthlyProgressModel", monthlyProgressModel);
				long categoryId =Long.parseLong(encryptionService.dcrypt(encCategoryId));
				List<PublicationDetailsModel> modelList= projectPublicationDetailsService.loadPublicationDetail(monthlyProgressId,categoryId);
				request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
				request.setAttribute("encCategoryId", encCategoryId);
				request.setAttribute("modelList", modelList);
			}
			else{
				return "redirect:/mst/ViewAllProjects";
			}
			return "ProjectPublicationDetails";
	    }
		
		 @RequestMapping(value = "/SavePublicationDetails", method = RequestMethod.POST)
		    public String  SavePublicationDetails(PublicationDetailsModel publicationDetailsModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			PublicationDetailsModel  savePublicationDetailsModel=projectPublicationDetailsService.saveProjectPublicationDetails(publicationDetailsModel);
		    	if(savePublicationDetailsModel !=null){
					if(publicationDetailsModel.getNumProjectPublicationId()==0){
						redirectAttributes.addFlashAttribute("message",  "Details saved Successfully !");	
						redirectAttributes.addFlashAttribute("status", "success");
					}else{
						redirectAttributes.addFlashAttribute("message",  "Details updated Successfully !");	
						redirectAttributes.addFlashAttribute("status", "success");
					}					
				}
				redirectAttributes.addFlashAttribute("encMonthlyProgressId", publicationDetailsModel.getEncMonthlyProgressId());
				redirectAttributes.addFlashAttribute("encCategoryId", publicationDetailsModel.getEncCategoryId());
				return "redirect:/PublicationDetails?encMonthlyProgressId="+publicationDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+publicationDetailsModel.getEncCategoryId();
				
		 }
		 }
		 
	
		    		
		