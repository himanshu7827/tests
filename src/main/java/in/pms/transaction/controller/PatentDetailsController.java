package in.pms.transaction.controller;

import java.util.List;
import java.util.Map;

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.PatentDetailsService;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


@Controller
	public class PatentDetailsController {
 
		@Autowired PatentDetailsService patentDetailsService;
		
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		
		
		
		@RequestMapping(value="/PatentDetails",method=RequestMethod.GET)
		public String projectPublicationFromConference(HttpServletRequest request,@ModelAttribute PatentDetailsModel patentDetailsModel ) {
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

					encMonthlyProgressId = patentDetailsModel.getEncMonthlyProgressId();
					encCategoryId = patentDetailsModel.getEncCategoryId();
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(null != encMonthlyProgressId){
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
				request.setAttribute("monthlyProgressModel", monthlyProgressModel);
				long categoryId =Long.parseLong(encryptionService.dcrypt(encCategoryId));
				List<PatentDetailsModel> modelList= patentDetailsService.loadPatentDetail(monthlyProgressId,categoryId);
				request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
				request.setAttribute("categoryId", encCategoryId);
				request.setAttribute("encCategoryId",encCategoryId);
				request.setAttribute("modelList", modelList);
			}
			else{
				return "redirect:/mst/ViewAllProjects";
			}
			return "PatentDetails";
		}
	
		@RequestMapping(value = "/SavePatentDetails", method = RequestMethod.POST)
	    public String SavePatentDetails(PatentDetailsModel patentDetailsModel, HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			monthlyProgressService.writeProgressReportAuthorityCheck();
			PatentDetailsModel  savePatentDetailsModel=patentDetailsService.SaveProjectPatentDetails(patentDetailsModel);
	    	if(savePatentDetailsModel !=null){
				if(patentDetailsModel.getProjectPatentId()==0){
					redirectAttributes.addFlashAttribute("message",  "Details saved Successfully !");	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Details updated Successfully !");	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", patentDetailsModel.getEncMonthlyProgressId());
			redirectAttributes.addFlashAttribute("encCategoryId", patentDetailsModel.getEncCategoryId());
			
			return "redirect:/PatentDetails?encMonthlyProgressId="+patentDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+patentDetailsModel.getEncCategoryId();
			 
	 }

		@RequestMapping(value="/deletePatentDetails",method=RequestMethod.POST)
		public String deletePatentDetails(PatentDetailsModel patentDetailsModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
			{
				 
			if(patentDetailsModel.getProjectPatentIds()==null || patentDetailsModel.getProjectPatentIds().length==0){

				
				redirectAttributes.addFlashAttribute("message",  "There is some problem in delete");
				redirectAttributes.addFlashAttribute("status",  "error");
				redirectAttributes.addFlashAttribute("encMonthlyProgressId", patentDetailsModel.getEncMonthlyProgressId());
				redirectAttributes.addFlashAttribute("encCategoryId", patentDetailsModel.getEncCategoryId());
				return "redirect:/PatentDetails?encMonthlyProgressId="+patentDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+patentDetailsModel.getEncCategoryId();
			}
			else{
				    	try
				    	{ 
						
				    		patentDetailsService.deletePatentDetails(patentDetailsModel);
				    		redirectAttributes.addFlashAttribute("message",  "Patent details deleted Successfully");	
				    		redirectAttributes.addFlashAttribute("status",  "success");
				    		redirectAttributes.addFlashAttribute("encMonthlyProgressId", patentDetailsModel.getEncMonthlyProgressId());
							redirectAttributes.addFlashAttribute("encCategoryId", patentDetailsModel.getEncCategoryId());
				    		
		                    
		                    
				    	}
				    	catch(Exception e)
				    	{
			          	System.out.println(e);

				    	}
				    	return "redirect:/PatentDetails?encMonthlyProgressId="+patentDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+patentDetailsModel.getEncCategoryId();
			}
				    }
	 
	
	    }