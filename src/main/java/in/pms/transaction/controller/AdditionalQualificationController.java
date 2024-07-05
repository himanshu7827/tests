package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.AdditionalQualificationModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.MouCollaborationModel;
import in.pms.transaction.model.PublicationDetailsModel;
import in.pms.transaction.service.AdditionalQualificationService;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.MouCollaborationService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


@Controller
public class AdditionalQualificationController {
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@Autowired
	AdditionalQualificationService additionalQualificationService;
	

	@RequestMapping(value="/additionalQualification")
		public String getAllEmpRoleMaster(HttpServletRequest request, AdditionalQualificationModel additionalQualificationModel){		
			//List<AdditionalQualificationModel> data = additionalQualificationService.getAllAdditionalcollab();
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId="";
		String encCategoryId="";
		//long categoryId=0;
		try {
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if(flashMap!= null){
			String monthlyProgressId=(String) flashMap.get("encMonthlyProgressId");
			String categoryIdsave=(String) flashMap.get("encCategoryId");
			encMonthlyProgressId= monthlyProgressId;
			encCategoryId=categoryIdsave;
			//categoryId =Long.parseLong(encryptionService.dcrypt(categoryIdsave));
			}
			else{

				encMonthlyProgressId = additionalQualificationModel.getEncMonthlyProgressId();
				//categoryId = Long.parseLong(encryptionService.dcrypt(additionalQualificationModel.getEncCategoryId()));
				encCategoryId=additionalQualificationModel.getEncCategoryId();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//String encMonthlyProgressId = additionalQualificationModel.getEncMonthlyProgressId();

		if(null != encMonthlyProgressId){
			//System.out.println(" encrypted monthly progressid"+encMonthlyProgressId);
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long catId = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			//System.out.println(" decrypted monthly progressid"+monthlyProgressId);
			//Load Group, Project Id based on encMonthlyProgressId
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			//long categoryId = additionalQualificationModel.getNumCategoryId();
			List<AdditionalQualificationModel> data= additionalQualificationService.loadAdditionalqualificationDetail(monthlyProgressId,catId);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("encCategoryId", encCategoryId);
		//	request.setAttribute("encCategoryId", encryptionService.encrypt(""+categoryId));
			request.setAttribute("data", data);
		
			//request.setAttribute("data", data);	
			
			
			List<AdditionalQualificationModel> list = additionalQualificationService.getAllActiveEmployeeMasterDomain();
			request.setAttribute("List", list);	
			request.setAttribute("numGroupCategoryId", 2);	}
		else{
			return "redirect:/mst/ViewAllProjects";
			}
			return "additionalQualification";
		}
	
	
	

	@RequestMapping(value="/saveUpdateAdditionalQualifications",method=RequestMethod.POST)	
	public ModelAndView saveUpdateAdditionalQualifications(AdditionalQualificationModel additionalQualificationModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
		/*String strDuplicateCheck = additionalQualificationService.checkDuplicateAdditionalQual(additionalQualificationModel);
		if(null == strDuplicateCheck){*/
			AdditionalQualificationModel id = additionalQualificationService.saveUpdateAdditionalQual(additionalQualificationModel);
		if(id!=null){
			if(additionalQualificationModel.getNumQualID()==0){
				redirectAttributes.addFlashAttribute("message",  "Details saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Details updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}	
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", id.getEncMonthlyProgressId());
			redirectAttributes.addFlashAttribute("encCategoryId", id.getEncCategoryId());
		}
		
		/*}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}*/
		//List<AdditionalQualificationModel> list = additionalQualificationService.getAllAdditionalcollab();
		//request.setAttribute("additionalQualificationModel",new AdditionalQualificationModel());
		//request.setAttribute("data", list);	
		
		mav.setViewName("redirect:/additionalQualification");
		return mav;
	}


	@RequestMapping(value = "/deleteAddQual",  method = RequestMethod.POST)
	public String deleteAddQual(AdditionalQualificationModel additionalQualificationModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  AdditionalQualificationModel result= additionalQualificationService.deleteAddQual(additionalQualificationModel);
			    		if(result !=null){
			    			redirectAttributes.addFlashAttribute("message",  "Record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
			    	//	List<AdditionalQualificationModel> list = additionalQualificationService.getAllAdditionalcollab();
						// request.setAttribute("data", list);        
	                    
			    		redirectAttributes.addFlashAttribute("encMonthlyProgressId", result.getEncMonthlyProgressId());
			    		redirectAttributes.addFlashAttribute("encCategoryId", result.getEncCategoryId());
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/additionalQualification";
    }
	

}
