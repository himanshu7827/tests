package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.MouCollaborationModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.MouCollaborationService;

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
public class MouCollaborationController {

	@Autowired
	MouCollaborationService mouCollaborationService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	
	@RequestMapping(value="/mouCollaboration",method = { RequestMethod.GET, RequestMethod.POST })
	public String mouCollaboration(@ModelAttribute MouCollaborationModel MouCollaborationModel,HttpServletRequest request){		
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

				encMonthlyProgressId = MouCollaborationModel.getEncMonthlyProgressId();
				encCategoryId = MouCollaborationModel.getEncCategoryId();
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if(null != encMonthlyProgressId){
			
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			
			List<MouCollaborationModel> modelList= mouCollaborationService.loadCollabDetail(monthlyProgressId,categoryID);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("categoryId", categoryID);
			request.setAttribute("encCategoryId", encCategoryId);
			request.setAttribute("data", modelList);
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		
	
		return "mouCollaboration";
	}

	@RequestMapping(value="/saveUpdateMouCollaboration",method=RequestMethod.POST)	
	public ModelAndView saveUpdateMouCollaboration(MouCollaborationModel mouCollaborationModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
	   
	//	String strDuplicateCheck = mouCollaborationService.checkDuplicateOthers(othersModel);
		/*if(null == strDuplicateCheck){*/
		MouCollaborationModel mouCollaborationModels = mouCollaborationService.saveUpdateMouCollaboration(mouCollaborationModel);
		if(mouCollaborationModels!=null){
			if(mouCollaborationModel.getNumCollabID()==0){
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
		redirectAttributes.addFlashAttribute("encMonthlyProgressId", mouCollaborationModel.getEncMonthlyProgressId());
		redirectAttributes.addFlashAttribute("encCategoryId", mouCollaborationModel.getEncCategoryId());
		mav.setViewName("redirect:/mouCollaboration");
		return mav;
	}


	@RequestMapping(value = "/deleteMouCollaboration",  method = RequestMethod.POST)
	public String deleteMouCollaboration(MouCollaborationModel mouCollaborationModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  MouCollaborationModel mouCollaborationModels= mouCollaborationService.deleteMouCollaboration(mouCollaborationModel);
			    		if(mouCollaborationModels.getNumCollabID() !=0){
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
				  redirectAttributes.addFlashAttribute("encMonthlyProgressId", mouCollaborationModel.getEncMonthlyProgressId());
					redirectAttributes.addFlashAttribute("encCategoryId", mouCollaborationModel.getEncCategoryId());
		
		return "redirect:/mouCollaboration";
    }
	

}
