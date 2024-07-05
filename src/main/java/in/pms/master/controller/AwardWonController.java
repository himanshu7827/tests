package in.pms.master.controller;
import in.pms.global.service.EncryptionService;
import in.pms.master.domain.AwardWonDomain;
import in.pms.master.model.AwardWonModel;
import in.pms.master.model.MediaModel;
import in.pms.master.service.AwardWonService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.MonthlyProgressService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/mst")
@Slf4j
public class AwardWonController {

	@Autowired
	AwardWonService awardWonService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping(value="/AwardWon",method = { RequestMethod.GET, RequestMethod.POST })
	public String AwardWon(HttpServletRequest request, AwardWonModel awardWonModel){
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId="";
		String encCategoryId="";
		String message="";
		try {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!= null){
		String monthlyProgressId=(String) flashMap.get("encMonthlyProgressId");
		String categoryIdsave=(String) flashMap.get("encCategoryId");
		 message=(String) flashMap.get("message");
		encMonthlyProgressId= monthlyProgressId;
		encCategoryId =categoryIdsave;
		}
		else{

		encMonthlyProgressId = awardWonModel.getEncMonthlyProgressId();
		encCategoryId = awardWonModel.getEncCategoryId();

		}

		} catch (Exception e) {
		// TODO: handle exception
		}

		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long catId = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			//long categoryId = awardWonModel.getNumCategoryId();
	//	List<MediaModel> list = mediaService.getAllMediaDetails();
		List<AwardWonModel> list= awardWonService.loadAwardDetails(monthlyProgressId,catId);
		request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
		request.setAttribute("categoryId", encCategoryId);
		//request.setAttribute("encCategoryId", encryptionService.encrypt(""+categoryId));
		//request.setAttribute("modelList", modelList);
		request.setAttribute("data", list);	
		request.setAttribute("message",message);
		
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		return "AwardWon";
	}
	
	@RequestMapping(value="/saveAwardWon",method=RequestMethod.POST)	
	public String awardWonModel(HttpServletRequest request, AwardWonModel awardWonModel, RedirectAttributes redirectAttributes,BindingResult bindingResult){		

		AwardWonModel awardWonModel2 = awardWonService.saveUpdateAwardWonDetails(awardWonModel);
		
		 if(awardWonModel2!=null){
			 if(awardWonModel2.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Award Won details saved Successfully ");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Award Won details updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}				
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", awardWonModel2.getEncMonthlyProgressId());
    		redirectAttributes.addFlashAttribute("encCategoryId", awardWonModel2.getEncCategoryId());
		 }
		
		return "redirect:/mst/AwardWon";
	}
	
@RequestMapping(value="/deleteAwardWon",method=RequestMethod.POST)
	public String deleteAwardWon(AwardWonModel awardWonModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
	
			    	try
			    	{					
			    		AwardWonModel awardWonModel2 =awardWonService.deleteAwardWonDetails(awardWonModel);
			    		 if(awardWonModel2!=null){
			    		redirectAttributes.addFlashAttribute("message",  "Award details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		redirectAttributes.addFlashAttribute("encMonthlyProgressId", awardWonModel2.getEncMonthlyProgressId());
			    		redirectAttributes.addFlashAttribute("encCategoryId", awardWonModel2.getEncCategoryId());
			    		 }
			    	}
			    	catch(Exception e)
			    	{
			    		redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    		redirectAttributes.addFlashAttribute("status", "error");
			    		log.error(e.getCause().getMessage());
			    		log.error(e.getMessage());

			    	}
			    	return "redirect:/mst/AwardWon";
		}
			  
	
	
}
