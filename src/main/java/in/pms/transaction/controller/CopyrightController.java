package in.pms.transaction.controller;
/**
 * @author amitkumarsaini
 *
 */

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

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.CopyRightModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.CopyrightService;
import in.pms.transaction.service.MonthlyProgressService;


@Controller
public class CopyrightController {

	@Autowired
	CopyrightService copyrightService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping(value="/copyRight" ,method = { RequestMethod.GET, RequestMethod.POST })
	public String copyRight(CopyRightModel copyRightModel,HttpServletRequest request){		
		/*List<CopyRightModel> list = copyrightService.getAllCopyRight();
		request.setAttribute("numGroupCategoryId", 2);
		request.setAttribute("data", list);	*/
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

		encMonthlyProgressId = copyRightModel.getEncMonthlyProgressId();
		encCategoryId = copyRightModel.getEncCategoryId();

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
		List<CopyRightModel> list= copyrightService.loadCopyrightDetail(monthlyProgressId,catId);
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
		return "copyRight";
	}

	@RequestMapping(value="/saveUpdateCopyRight",method=RequestMethod.POST)	
	public ModelAndView saveUpdateCopyRight(CopyRightModel copyRightModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
		ModelAndView mav = new ModelAndView();
		
		
			CopyRightModel copyRightModel2 = copyrightService.saveUpdateCopyrightMaster(copyRightModel);
			if(copyRightModel2!=null){
			if(copyRightModel.getNumCopyRightID()==0){
				redirectAttributes.addFlashAttribute("message",  "Copyright Details saved Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Copyright Details updated Successfully !");	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
			}
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", copyRightModel2.getEncMonthlyProgressId());
			redirectAttributes.addFlashAttribute("encCategoryId", copyRightModel2.getEncCategoryId());
		/*}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}*/
		/*List<CopyRightModel> list = copyrightService.getAllCopyRight();
		request.setAttribute("copyRightModel",new CopyRightModel());
		request.setAttribute("data", list);	*/
		mav.setViewName("redirect:/copyRight");
		return mav;
	}


	@RequestMapping(value = "/deleteCopyright",  method = RequestMethod.POST)
	public String deleteDesignation(CopyRightModel copyRightModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
    {	
				  
				  try
			    	{				
					  CopyRightModel copyRightModel2 = copyrightService.deleteCopyright(copyRightModel);
					  if(copyRightModel2!=null){
			    			redirectAttributes.addFlashAttribute("message",  "Copyright record deleted Successfully");	
			    			redirectAttributes.addFlashAttribute("status", "success");
			    		}else{
			    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			    			redirectAttributes.addFlashAttribute("status", "error");
			    		}
					  redirectAttributes.addFlashAttribute("encMonthlyProgressId", copyRightModel2.getEncMonthlyProgressId());
						redirectAttributes.addFlashAttribute("encCategoryId", copyRightModel2.getEncCategoryId());    
	                    
	                    
			    	}
			    	catch(Exception e){
		          	//log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

			    	}		    								
	
		
		return "redirect:/copyRight";
    }
	
	
	
	
	

}
