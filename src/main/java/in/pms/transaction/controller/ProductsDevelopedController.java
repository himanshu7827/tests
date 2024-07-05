package in.pms.transaction.controller;

import java.util.List;
import java.util.Map;

import in.pms.global.service.EncryptionService;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ProductsDevelopedModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProductsDevelopedService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public class ProductsDevelopedController {
 
		@Autowired 
		ProductsDevelopedService ProductsDevelopedService;
		
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		@RequestMapping(value="/ProductsDeveloped",method=RequestMethod.GET)
		public String projectPublicationFromConference(HttpServletRequest request,@ModelAttribute ProductsDevelopedModel productsDevelopedModel ) {
			ProductsDevelopedService.readProductsDevelopedAuthorityCheck();
			
	//String encMonthlyProgressId = productsDevelopedModel.getEncMonthlyProgressId();
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

					encMonthlyProgressId = productsDevelopedModel.getEncMonthlyProgressId();
					encCategoryId = productsDevelopedModel.getEncCategoryId();
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}		
	monthlyProgressService.writeProgressReportAuthorityCheck();
		
	if(null != encMonthlyProgressId){
		int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		//Load Group, Project Id based on encMonthlyProgressId
		MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
		request.setAttribute("monthlyProgressModel", monthlyProgressModel);
		long categoryId =Long.parseLong(encryptionService.dcrypt(encCategoryId));
		List<ProductsDevelopedModel> descr= ProductsDevelopedService.loadProductsDevelopedDetail(monthlyProgressId,categoryId);
		
		request.setAttribute("descrip",descr);
		request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
		request.setAttribute("categoryId", encCategoryId);
		}
			return "ProductsDeveloped";
		}
		
		@RequestMapping(value = "/SaveProductsDeveloped", method = RequestMethod.POST)
	    public String SaveProductsDeveloped(ProductsDevelopedModel productsDevelopedModel, HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
	       ProductsDevelopedService.writeProductsDevelopedAuthorityCheck();
	        String str= ProductsDevelopedService.SaveProductsDeveloped(productsDevelopedModel);
	        if(str.equalsIgnoreCase("success")){
	        	if(productsDevelopedModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Details saved Successfully !");	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Details updated Successfully !");	
					redirectAttributes.addFlashAttribute("status", "success");
				}
	        }
	        else{
	        	redirectAttributes.addFlashAttribute("message",  "Details Not Saved !");	
				redirectAttributes.addFlashAttribute("status", "error");
	        }
	        ModelAndView mav = new ModelAndView();
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", productsDevelopedModel.getEncMonthlyProgressId());
				redirectAttributes.addFlashAttribute("encCategoryId", productsDevelopedModel.getEncCategoryId());
				return 	"redirect:/ProductsDeveloped?encMonthlyProgressId="+productsDevelopedModel.getEncMonthlyProgressId()+"&encCategoryId="+productsDevelopedModel.getEncCategoryId();

		}
		
		@RequestMapping(value="/deleteProductsDeveloped",method=RequestMethod.POST)
		public String deleteTotDetails(ProductsDevelopedModel productsDevelopedModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
			{
				 
			if(productsDevelopedModel.getNumIds()==null || productsDevelopedModel.getNumIds().length==0){

				
				redirectAttributes.addFlashAttribute("message",  "There is some problem in delete!");
				redirectAttributes.addFlashAttribute("status",  "error");
				redirectAttributes.addFlashAttribute("encMonthlyProgressId", productsDevelopedModel.getEncMonthlyProgressId());
				redirectAttributes.addFlashAttribute("encCategoryId", productsDevelopedModel.getEncCategoryId());
				
				return 	"redirect:/ProductsDeveloped?encMonthlyProgressId="+productsDevelopedModel.getEncMonthlyProgressId()+"&encCategoryId="+productsDevelopedModel.getEncCategoryId();
			}
			else{
				    	try
				    	{ 
						
				    		ProductsDevelopedService.deleteProductsDeveloped(productsDevelopedModel);
				    		redirectAttributes.addFlashAttribute("message",  "Details deleted Successfully!");	
				    		redirectAttributes.addFlashAttribute("status",  "success");
				    		
		                    
		                    
				    	}
				    	catch(Exception e)
				    	{
			          	System.out.println(e);

				    	}
				    	redirectAttributes.addFlashAttribute("encMonthlyProgressId", productsDevelopedModel.getEncMonthlyProgressId());
						redirectAttributes.addFlashAttribute("encCategoryId", productsDevelopedModel.getEncCategoryId());
						
						return 	"redirect:/ProductsDeveloped?encMonthlyProgressId="+productsDevelopedModel.getEncMonthlyProgressId()+"&encCategoryId="+productsDevelopedModel.getEncCategoryId();
			}
				    }
		}