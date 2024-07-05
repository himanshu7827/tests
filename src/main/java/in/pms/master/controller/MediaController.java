package in.pms.master.controller;
import in.pms.global.service.EncryptionService;
import in.pms.master.domain.MediaDomain;
import in.pms.master.model.AwardWonModel;
import in.pms.master.model.MediaModel;
import in.pms.master.service.MediaService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.PublicationDetailsModel;
import in.pms.transaction.service.MonthlyProgressService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/mst")
@Slf4j
public class MediaController {

	@Autowired
	MediaService mediaService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping(value="/Media",method = { RequestMethod.GET, RequestMethod.POST })
	public String Media(HttpServletRequest request, @ModelAttribute MediaModel mediaModel){		
		//String encMonthlyProgressId = mediaModel.getEncMonthlyProgressId();
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

		encMonthlyProgressId = mediaModel.getEncMonthlyProgressId();
		encCategoryId = mediaModel.getEncCategoryId();

		}

		} catch (Exception e) {
		// TODO: handle exception
		}
		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long catId = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			//long categoryId = mediaModel.getNumCateoryId();
	//	List<MediaModel> list = mediaService.getAllMediaDetails();
		List<MediaModel> list= mediaService.loadMediaDetail(monthlyProgressId,catId);
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
		return "MediaDetails";
	}
	
	
	@RequestMapping(value="/saveMedia",method=RequestMethod.POST)	
	public String saveMedia(HttpServletRequest request, MediaModel mediaModel, RedirectAttributes redirectAttributes,BindingResult bindingResult){		

		MediaModel mediaModel2= mediaService.saveUpdateMedia(mediaModel);
		
		 if(mediaModel2!=null){
			if(mediaModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Media details saved Successfully ");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Media details updated Successfully");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		redirectAttributes.addFlashAttribute("encMonthlyProgressId", mediaModel2.getEncMonthlyProgressId());
		redirectAttributes.addFlashAttribute("encCategoryId", mediaModel2.getEncCategoryId());
		return "redirect:/mst/Media";
	}
	
@RequestMapping(value="/deleteMedia",method=RequestMethod.POST)
	public String deleteMedia( MediaModel mediaModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
	
			    	try
			    	{					
			    		MediaModel mediaModel2=mediaService.deleteMedia(mediaModel);
			    		 if(mediaModel2!=null){
			    		redirectAttributes.addFlashAttribute("message",  "Media details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		redirectAttributes.addFlashAttribute("encMonthlyProgressId", mediaModel2.getEncMonthlyProgressId());
			    		redirectAttributes.addFlashAttribute("encCategoryId", mediaModel2.getEncCategoryId());	
			    		 }
			    	}
			    	catch(Exception e)
			    	{
			    		redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    		redirectAttributes.addFlashAttribute("status", "error");
			    		log.error(e.getCause().getMessage());
			    		log.error(e.getMessage());

			    	}
			    	return "redirect:/mst/Media";
		}
			  
	
	
}
