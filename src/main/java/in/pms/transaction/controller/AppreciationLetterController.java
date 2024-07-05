package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.AppreciationLetterModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.AppreciationLetterService;
import in.pms.transaction.service.MonthlyProgressService;

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
public class AppreciationLetterController {

	@Autowired
	AppreciationLetterService appreciationLetterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@RequestMapping(value="/appreciationLetter")	
	public String appreciationLetter(@ModelAttribute AppreciationLetterModel appreciationLetterModel,HttpServletRequest request){		
		String encMonthlyProgressId="";
		monthlyProgressService.writeProgressReportAuthorityCheck();

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

				encMonthlyProgressId = appreciationLetterModel.getEncMonthlyProgressId();
				encCategoryId = appreciationLetterModel.getEncCategoryId();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			
			List<AppreciationLetterModel> modelList= appreciationLetterService.loadAppreciationDetail(monthlyProgressId,categoryID);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("categoryId", categoryID);
			request.setAttribute("encCategoryId", encCategoryId);
			request.setAttribute("data", modelList);
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		return "appreciationLetter";
	}

	@RequestMapping(value="/saveUpdateAppreciation",method=RequestMethod.POST)	
	public ModelAndView saveUpdateAppreciation(AppreciationLetterModel appreciationLetterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
	//	String strDuplicateCheck = appreciationLetterService.checkDuplicateAppreciationLetter(appreciationLetterModel);
		/*if(null == strDuplicateCheck){*/
		AppreciationLetterModel appreciationLetterModels = appreciationLetterService.saveUpdateAppreciationLetter(appreciationLetterModel);
		if(appreciationLetterModels!=null){
			if(appreciationLetterModel.getNumAppreciationID()==0){
				redirectAttributes.addFlashAttribute("message",  "Appreciation Letter Details saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Appreciation Letter  Details updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		/*}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}*/
		redirectAttributes.addFlashAttribute("encMonthlyProgressId", appreciationLetterModel.getEncMonthlyProgressId());
		redirectAttributes.addFlashAttribute("encCategoryId", appreciationLetterModel.getEncCategoryId());
		mav.setViewName("redirect:/appreciationLetter");
		return mav;
	}


	@RequestMapping(value = "/deleteAppreciation",  method = RequestMethod.POST)
	public String deleteDesignation(AppreciationLetterModel appreciationLetterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  AppreciationLetterModel appreciationLetterModels= appreciationLetterService.deleteApprciationLetter(appreciationLetterModel);
			    		if(appreciationLetterModels.getNumAppreciationID()!=0){
			    			redirectAttributes.addFlashAttribute("message",  "Appreciation Letter record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    	
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
				  redirectAttributes.addFlashAttribute("encMonthlyProgressId", appreciationLetterModel.getEncMonthlyProgressId());
					redirectAttributes.addFlashAttribute("encCategoryId", appreciationLetterModel.getEncCategoryId());
		return "redirect:/appreciationLetter";
    }
	

}
