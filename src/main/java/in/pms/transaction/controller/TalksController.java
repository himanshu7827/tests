package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */


import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.TalksModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.TalksService;

import java.util.List;
import java.util.Map;

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
public class TalksController {

	@Autowired
	TalksService talksService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	
	@RequestMapping(value="/talks")	
	public String talks(@ModelAttribute TalksModel talksModel,HttpServletRequest request){		
		//List<TalksModel> list = talksService.getAllTalks();
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

				encMonthlyProgressId = talksModel.getEncMonthlyProgressId();
				encCategoryId = talksModel.getEncCategoryId();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			
			List<TalksModel> modelList= talksService.loadTalksDetail(monthlyProgressId,categoryID);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("categoryId", categoryID);
			request.setAttribute("encCategoryId", encCategoryId);
			request.setAttribute("data", modelList);
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
	
		return "talks";
	}

	@RequestMapping(value="/saveUpdateTalks",method=RequestMethod.POST)	
	public ModelAndView saveUpdateTalks(TalksModel talksModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
	//	String strDuplicateCheck = talksService.checkDuplicateOthers(othersModel);
		/*if(null == strDuplicateCheck){*/
		TalksModel talksModels = talksService.saveUpdateTalks(talksModel);
		if(talksModels !=null){
			if(talksModel.getNumTalkID()==0){
				redirectAttributes.addFlashAttribute("message",  "Details saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Details updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		/*}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}*/
		
		redirectAttributes.addFlashAttribute("encMonthlyProgressId", talksModel.getEncMonthlyProgressId());
		redirectAttributes.addFlashAttribute("encCategoryId", talksModel.getEncCategoryId());
		
		mav.setViewName("redirect:/talks");
		return mav;
	}


	@RequestMapping(value = "/deleteTalks",  method = RequestMethod.POST)
	public String deleteTalks(TalksModel talksModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  TalksModel talksModels= talksService.deleteTalks(talksModel);
			    		if(talksModels.getNumTalkID()!=0){
			    			redirectAttributes.addFlashAttribute("message",  "Record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    		
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
				  redirectAttributes.addFlashAttribute("encMonthlyProgressId", talksModel.getEncMonthlyProgressId());
					redirectAttributes.addFlashAttribute("encCategoryId", talksModel.getEncCategoryId());
					
		return "redirect:/talks";
    }
	

}
