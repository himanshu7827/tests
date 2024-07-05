package in.pms.master.controller;


import in.pms.global.service.EncryptionService;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.transaction.model.BudgetHeadMasterForm;
import in.pms.transaction.service.BudgetHeadMasterService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class BudgetHeadMasterController {

	@Autowired
	BudgetHeadMasterService budgetHeadMasterService;

	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping("/BudgetHeadMaster")
	public String getAllClientMaster(HttpServletRequest request, BudgetHeadMasterForm budgetHeadMasterForm){		
		List<BudgetHeadMasterForm> list = budgetHeadMasterService.getAllBugdetHeadData();
		request.setAttribute("data", list);		
		return "BudgetHeadMaster";
	}
	
	@RequestMapping(value="/saveBudgetHeadMaster",method=RequestMethod.POST)	
	public String saveUpdateBudgetHeadMaster(HttpServletRequest request, BudgetHeadMasterForm budgetHeadMasterForm, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = budgetHeadMasterService.checkDuplicateBugdetHeadData(budgetHeadMasterForm);
		if(null == strDuplicateCheck){
		long id = budgetHeadMasterService.saveBudgetHeadData(budgetHeadMasterForm);
		if(id>0){
			if(budgetHeadMasterForm.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Budget details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Budget details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
				
		return "redirect:/mst/BudgetHeadMaster";
		
	}
	
	@RequestMapping(value="/deleteBudgetHeadMaster",method=RequestMethod.POST)
	public String deleteBudgetHeadMaster(BudgetHeadMasterForm budgetHeadMasterForm, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
		if(budgetHeadMasterForm.getNumIds()==null || budgetHeadMasterForm.getNumIds().length==0){
			List<BudgetHeadMasterForm> list = budgetHeadMasterService.getAllBugdetHeadData();
			request.setAttribute("data", list);	
			return "BudgetHeadMaster";
		}
		else{
			    	try
			    	{
					
			    		budgetHeadMasterService.deleteBudgetHeadData(budgetHeadMasterForm);
			    		redirectAttributes.addFlashAttribute("message",  "Budget details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		List<BudgetHeadMasterForm> list = budgetHeadMasterService.getAllBugdetHeadData();
						request.setAttribute("data", list);		
	                   
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/mst/BudgetHeadMaster";
		}
			    }
	
	

			@RequestMapping(value="/getAmountAgainstBudgetHeadByProjectId",method=RequestMethod.POST)
			public @ResponseBody JSONArray getAmountAgainstBudgetHeadByProjectId(BudgetHeadMasterForm budgetHeadMasterForm , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
				
				//System.out.println("Project Id "+budgetHeadMasterForm.getProjectId());
				/*List<BudgetHeadMasterForm> datalist = new ArrayList<BudgetHeadMasterForm>();*/
				JSONArray datalist= new JSONArray();
				if(null != budgetHeadMasterForm.getEncProjectId()){
					
					String strProjectId = encryptionService.dcrypt(budgetHeadMasterForm.getEncProjectId());
					long projectId = Long.parseLong(strProjectId);
					//System.out.println(projectId);
					datalist = budgetHeadMasterService.getAmountAgainstBudgetHeadByProjectId(projectId);

				}else{
					
					datalist = budgetHeadMasterService.getAmountAgainstBudgetHeadByProjectId(budgetHeadMasterForm.getProjectId());

				}
				
				request.setAttribute("data1", datalist);	
			return datalist; 
		}	
	
	
}
