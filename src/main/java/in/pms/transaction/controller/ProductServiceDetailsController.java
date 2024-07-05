package in.pms.transaction.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.global.service.EncryptionService;
import in.pms.master.domain.States;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ProductServiceDetailsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProductServiceDetailsService;
import in.pms.transaction.service.ProgressReportService;

@Controller
public class ProductServiceDetailsController {

	@Autowired
	ProductServiceDetailsService productServiceDetailsService;
	@Autowired
	ProgressReportService progressReportService;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@RequestMapping(value="productServiceDetails",method=RequestMethod.GET)
	public String productServiceDetails(HttpServletRequest request,ProductServiceDetailsModel productServiceDetailsModel){
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId = productServiceDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=productServiceDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("encCategoryId", productServiceDetailsModel.getEncCategoryId());
			
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		
		List<States> listState = new ArrayList<States>();
		
		List<ProductServiceDetailsModel> productServiceDetailsList=productServiceDetailsService.getProductServiceDetails(monthlyProgressId,numCategoryId);
		listState = progressReportService.getStateList();
		//System.out.println("after");
		request.setAttribute("StateList", listState);
		request.setAttribute("productDetailsList",productServiceDetailsList);
		
		return "productServiceDetails";
	}
	
	@RequestMapping(value="saveUpdateProductServiceDetails",method=RequestMethod.POST)
	public String saveUpdateProductServiceDetails(HttpServletRequest request,ProductServiceDetailsModel productServiceDetailsModel,RedirectAttributes redirectAttributes){
	
		boolean flag=productServiceDetailsService.saveUpdateProductServiceDetails(request,productServiceDetailsModel);
		if(flag && productServiceDetailsModel.getNumProductServiceDetailsId()!=0)
		{
			redirectAttributes.addFlashAttribute("message",  "Product details updated Successfully");
			redirectAttributes.addFlashAttribute("status",  "success");
		
		}else if(flag && productServiceDetailsModel.getNumProductServiceDetailsId()==0)
		{
			redirectAttributes.addFlashAttribute("message",  "Product details saved Successfully");
			redirectAttributes.addFlashAttribute("status",  "success");
		}
		else
		{
			redirectAttributes.addFlashAttribute("message",  "There is some problem in data save");	
			redirectAttributes.addFlashAttribute("status",  "error");
		}
		return "redirect:/productServiceDetails?encMonthlyProgressId="+productServiceDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+productServiceDetailsModel.getEncCategoryId();
		
	}
	
	
	@RequestMapping(value="/deleteProductServiceDetails",method=RequestMethod.POST)
	public String deleteDeployementDetails(ProductServiceDetailsModel productServiceDetailsModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			 
		if(productServiceDetailsModel.getNumProductServiceDetailsIds()==null || productServiceDetailsModel.getNumProductServiceDetailsIds().length==0){

			
			redirectAttributes.addFlashAttribute("message",  "There is some problem in delete");
			redirectAttributes.addFlashAttribute("status",  "error");
			return "redirect:/productServiceDetails?encMonthlyProgressId="+productServiceDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+productServiceDetailsModel.getEncCategoryId();
		}
		else{
			    	try
			    	{ 
					
			    		productServiceDetailsService.deleteProductServiceDetails(productServiceDetailsModel);
			    		redirectAttributes.addFlashAttribute("message",  "Product service details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status",  "success");
			    		
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/productServiceDetails?encMonthlyProgressId="+productServiceDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+productServiceDetailsModel.getEncCategoryId();
		}
			    }

	
	
}
