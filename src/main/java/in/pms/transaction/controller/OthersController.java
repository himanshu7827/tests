package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.OthersModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.OthersService;

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
public class OthersController {

	@Autowired
	OthersService othersService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	
	@RequestMapping(value="/others")	
	public String others(@ModelAttribute OthersModel othersModel,HttpServletRequest request){		
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

				encMonthlyProgressId = othersModel.getEncMonthlyProgressId();
				encCategoryId = othersModel.getEncCategoryId();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			
			List<OthersModel> modelList= othersService.loadOthersDetail(monthlyProgressId,categoryID);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("categoryId", categoryID);
			request.setAttribute("encCategoryId", encCategoryId);
			request.setAttribute("data", modelList);
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
	
		return "Others";
		
	}

	@RequestMapping(value="/saveUpdateOthers",method=RequestMethod.POST)	
	public ModelAndView saveUpdateOthers(OthersModel othersModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
	//	String strDuplicateCheck = othersService.checkDuplicateOthers(othersModel);
		/*if(null == strDuplicateCheck){*/
		OthersModel othersModels = othersService.saveUpdateOthers(othersModel);
		if(othersModels!=null){
			if(othersModels.getNumOthersID()==0){
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
		redirectAttributes.addFlashAttribute("encMonthlyProgressId", othersModel.getEncMonthlyProgressId());
		redirectAttributes.addFlashAttribute("encCategoryId", othersModel.getEncCategoryId());
		
		mav.setViewName("redirect:/others");
		return mav;
	}


	@RequestMapping(value = "/deleteOthers",  method = RequestMethod.POST)
	public String deleteOthers(OthersModel othersModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  OthersModel othersModels= othersService.deleteOthers(othersModel);
			    		if(othersModels.getNumOthersID() !=0){
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
				  redirectAttributes.addFlashAttribute("encMonthlyProgressId", othersModel.getEncMonthlyProgressId());
					redirectAttributes.addFlashAttribute("encCategoryId", othersModel.getEncCategoryId());
		
		return "redirect:/others";
    }
	

}
