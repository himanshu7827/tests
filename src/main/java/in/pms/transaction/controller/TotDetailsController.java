package in.pms.transaction.controller;

import in.pms.global.dao.TransactionDao;
import in.pms.global.service.EncryptionService;
import in.pms.login.service.TransactionActivityService;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.TotdetailsService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TotDetailsController {

	@Autowired
	TotdetailsService totdetailsService;

	@Autowired
	TransactionDao transactionDao;
	@Autowired
	TransactionActivityService transactionActivityService;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	
	//===========================TOT Details==================================================
	
	@RequestMapping(value="totDetails",method=RequestMethod.GET)
	public String totDetails(HttpServletRequest request,DeploymentTotDetailsModel deploymentTotDetailsModel){
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=deploymentTotDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("encCategoryId", deploymentTotDetailsModel.getEncCategoryId());
			
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		//List<States> listState = new ArrayList<States>();		
		
		List<DeploymentTotDetailsModel> totDetailsList=totdetailsService.getTotDetails(monthlyProgressId,numCategoryId);
		/*listState = progressReportService.getStateList();
		//System.out.println("after");
		request.setAttribute("StateList", listState);*/
		request.setAttribute("deploymentList", totDetailsList);
		return "totDetails";
	}
	@RequestMapping(value="saveUpdateTotDetails",method=RequestMethod.POST)
	public String saveUpdateTotDetails(HttpServletRequest request,DeploymentTotDetailsModel deploymentTotDetailsModel,RedirectAttributes redirectAttributes){
	
		boolean flag=totdetailsService.saveUpdateDTotDetails(request,deploymentTotDetailsModel);
		if(flag && deploymentTotDetailsModel.getNumTotId()!=0)
		{
			redirectAttributes.addFlashAttribute("message",  "TOT details updated Successfully!");
			redirectAttributes.addFlashAttribute("status",  "success");
		
		}else if(flag && deploymentTotDetailsModel.getNumTotId()==0)
		{
			redirectAttributes.addFlashAttribute("message",  "TOT details saved Successfully!");
			redirectAttributes.addFlashAttribute("status",  "success");
		}
		else
		{
			redirectAttributes.addFlashAttribute("message",  "There is some problem in data save");	
			redirectAttributes.addFlashAttribute("status",  "error");
		}
		/*redirectAttributes.addFlashAttribute("encCategoryId",deploymentTotDetailsModel.getEncCategoryId());
		redirectAttributes.addFlashAttribute("encMonthlyProgressId",deploymentTotDetailsModel.getEncMonthlyProgressId());
		*/
		return "redirect:/totDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
	}
	
	@RequestMapping(value="/deleteTotDetails",method=RequestMethod.POST)
	public String deleteTotDetails(DeploymentTotDetailsModel deploymentTotDetailsModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			 
		if(deploymentTotDetailsModel.getNumTotIds()==null || deploymentTotDetailsModel.getNumTotIds().length==0){

			
			redirectAttributes.addFlashAttribute("message",  "There is some problem in delete!");
			redirectAttributes.addFlashAttribute("status",  "error");
			return "redirect:/totDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
		}
		else{
			    	try
			    	{ 
					
			    		totdetailsService.deleteTotDetails(deploymentTotDetailsModel);
			    		redirectAttributes.addFlashAttribute("message",  "TOT details deleted Successfully!");	
			    		redirectAttributes.addFlashAttribute("status",  "success");
			    		
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/totDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
		}
			    }
}
