package in.pms.master.controller;

import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentWithoutInvoiceMasterModel;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectPaymentReceivedService;
import in.pms.transaction.service.DashboardService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/mst")
@Slf4j
public class ProjectPaymentReceivedController {
	
	@Autowired
	ProjectPaymentReceivedService projectPaymentReceivedService;
	
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	@Qualifier("ProjectPaymentReceivedValidator")
	Validator validator;
	
	@RequestMapping({"/projectPaymentReceived","/projectPaymentReceived/{encProjId}"})
	public String projectPaymentReceived(@PathVariable(name="encProjId", required=false) String encProjId,ProjectPaymentReceivedModel projectPaymentReceivedModel,HttpServletRequest request,Model map){		
		Map md = map.asMap();
		int projectId=0;	
		/*	//making changes here
		List<ProjectMasterModel> projectList =new ArrayList<ProjectMasterModel>();
		*/
	List<ProjectMasterForm> projectList = new  ArrayList<ProjectMasterForm>();

			List<ProjectInvoiceMasterModel> invList=new ArrayList<ProjectInvoiceMasterModel>();
			List<ProjectPaymentWithoutInvoiceMasterModel> paymList=new ArrayList<ProjectPaymentWithoutInvoiceMasterModel>();
			if(encProjId!=null){
				String projId=encryptionService.dcrypt(encProjId);
				request.setAttribute("projectId", projId.trim());
			}  
			
			if(md.get("projectId")!=null){
		    	  projectId=Integer.parseInt(md.get("projectId").toString());

		    	  ProjectMasterDomain model=projectMasterService.getProjectMasterDataById(projectId);
		    	  List<ProjectMasterDomain> domainList=new ArrayList<ProjectMasterDomain>();
		    	  domainList.add(model);
		    	//changed convertProjectMasterDomainToModelList to convertBudgetMasterDomainToModelList
		    	  //projectList=projectMasterService.convertProjectMasterDomainToModelList(domainList);
					projectList=projectMasterService.convertBudgetMasterDomainToModelList(domainList);
					invList=projectInvoiceMasterService.getProjectInvoiceRefNoByProjectId(projectId);
					paymList= projectPaymentReceivedService.getProjectPaymentWithoutInvoice(projectId);
		      }else{

		    	  //changed getAllActiveProjectDetailsData() to getAllProjectDetailsData()
		    	  //projectList = projectMasterService.getAllActiveProjectDetailsData();
		projectList = projectMasterService.getAllProjectDetailsData();

		}						
		request.setAttribute("projectList", projectList);	
		request.setAttribute("invList", invList);	
		request.setAttribute("paymentList",paymList );	
		return "projectPaymentReceived";

	}
	@RequestMapping(value="/saveUpdatePaymentReceived",method=RequestMethod.POST)	
		public ModelAndView saveUpdatePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			//new ProjectPaymentReceivedValidator().validate(projectPaymentReceivedModel, bindingResult);
			/*--------------------------- Other Payments deduct and add the excess payment in the numReceived Amount [05-12-2023] ---------------------------*/ 
			Double otherPaymentReceived = (projectPaymentReceivedModel.getItTDS() != null ? projectPaymentReceivedModel.getItTDS() : 0.0)
			        + (projectPaymentReceivedModel.getGstTDS() != null ? projectPaymentReceivedModel.getGstTDS() : 0.0)
			        + (projectPaymentReceivedModel.getShortPayment() != null ? projectPaymentReceivedModel.getShortPayment() : 0.0)
			        + (projectPaymentReceivedModel.getLdPayment() != null ? projectPaymentReceivedModel.getLdPayment() : 0.0)
			        + (projectPaymentReceivedModel.getOtherRecovery() != null ? projectPaymentReceivedModel.getOtherRecovery() : 0.0);
			Double excessPaymentAmount = (projectPaymentReceivedModel.getExcessPaymentAmount() != null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0.0);
			projectPaymentReceivedModel.setNumReceivedAmount(
			        (projectPaymentReceivedModel.getNumReceivedAmount() != null ? projectPaymentReceivedModel.getNumReceivedAmount() : 0.0)
			                - otherPaymentReceived
			                + excessPaymentAmount
			);
			/*--------------------------- End of Other Payments deduct and add the excess payment in the numReceived Amount [05-12-2023] -------------------*/ 			
			validator.validate(projectPaymentReceivedModel, bindingResult);
			
		      if (bindingResult.hasErrors()) {
		    	  request.setAttribute("showForm", 1);
		    	  List<ProjectPaymentReceivedModel> list = projectPaymentReceivedService.getAllPaymentReceivedDomain();
		  		request.setAttribute("data", list);
				request.setAttribute("projectPaymentReceivedModel",projectPaymentReceivedModel);
				ProjectMasterDomain model=projectMasterService.getProjectMasterDataById(projectPaymentReceivedModel.getProjectId());
		    	  List<ProjectMasterDomain> domainList=new ArrayList<ProjectMasterDomain>();
		    	  domainList.add(model);
		    	  // changed List<ProjectMasterModel> projectList
		    	  // List<ProjectMasterModel> projectList = projectMasterService..convertProjectMasterDomainToModelList(domainList);

		    	  List<ProjectMasterForm> projectList = projectMasterService.convertBudgetMasterDomainToModelList(domainList);

					request.setAttribute("projectList", projectList);	
		    	  mav.setViewName("projectPaymentReceived");
		          return mav;
		      }
			
			
	/*		String strDuplicateCheck = projectPaymentReceivedService.checkDuplicatePaymentReceivedNo(projectPaymentReceivedModel);
			if(null == strDuplicateCheck){*/
			long id = projectPaymentReceivedService.saveUpdatePaymentReceived(projectPaymentReceivedModel);
			if(id>0){
				if(projectPaymentReceivedModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Project Payment Received record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Project Payment Received record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			/*
			 * }else{ redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			 * redirectAttributes.addFlashAttribute("status", "error"); }
			 */
			//List<ProjectPaymentReceivedModel> list = projectPaymentReceivedService.getAllPaymentReceivedDomain();
			//request.setAttribute("projectPaymentReceivedModel",new ProjectPaymentReceivedModel());
			//request.setAttribute("data", list);	
	  		//redirectAttributes.addFlashAttribute("projectId",projectPaymentReceivedModel.getProjectId());
			/*------------------------ After save return the project id [05-12-2023] ------------------*/
			redirectAttributes.addFlashAttribute("currentProjectID", projectPaymentReceivedModel.getProjectId());
			mav.setViewName("redirect:/mst/projectPaymentReceived");
			return mav;
		}

		@RequestMapping(value = "/deletePaymentReceived",  method = RequestMethod.POST)
		public String deletePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
	    {	
					  
					  try
				    	{				
				    		long result= projectPaymentReceivedService.deletePaymentReceived(projectPaymentReceivedModel);
				    		if(result !=-1){
				    			redirectAttributes.addFlashAttribute("message",  "Project Payment Received record deleted Successfully");	
				    			redirectAttributes.addFlashAttribute("status", "success");
				    		}else{
				    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
				    			redirectAttributes.addFlashAttribute("status", "error");
				    		}
				    		List<ProjectPaymentReceivedModel> list = projectPaymentReceivedService.getAllPaymentReceivedDomain();
							request.setAttribute("data", list);        
		                    
		                    
				    	}
				    	catch(Exception e){
			          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

				    	}		    								
		
			
			return "redirect:/mst/projectPaymentReceived";
	    }
		
		//ajax call get projectPaymentReceived Details using projectId
		@RequestMapping(value="/getProjectPaymentReceivedByProjectId",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectPaymentReceivedModel> getProjectPaymentReceivedByProjectId(ProjectPaymentReceivedModel projectPaymentReceivedModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			
			//System.out.println("Project Id "+projectPaymentReceivedModel.getProjectId());
			List<ProjectPaymentReceivedModel> datalist = new ArrayList<ProjectPaymentReceivedModel>();
		
			if(null != projectPaymentReceivedModel.getEncProjectId()){
				String strProjectId = encryptionService.dcrypt(projectPaymentReceivedModel.getEncProjectId());
				long projectId = Long.parseLong(strProjectId);
				datalist = projectPaymentReceivedService.getProjectPaymentReceivedByProjectId(projectId);

			}else{
				datalist = projectPaymentReceivedService.getProjectPaymentReceivedByProjectId(projectPaymentReceivedModel.getProjectId());

			}
			
			request.setAttribute("data1", datalist);	
		return datalist; 
	}
		
		
		//ajax call get Project Invoice using ProjectId
				@RequestMapping(value="/getProjectInvoiceRefNoByProjectId",method=RequestMethod.POST,produces="application/json")
				public @ResponseBody List<ProjectInvoiceMasterModel> getProjectInvoiceByProjectId(@RequestParam("projectId") long projectId,ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
						List<ProjectInvoiceMasterModel> datalist  = projectInvoiceMasterService.getProjectInvoiceRefNoByProjectId(projectId);
						request.setAttribute("data1", datalist);	
					return datalist; 
				}
				
		//For dashboard income
		@RequestMapping(value="/getIncomeByDate", method=RequestMethod.POST)
		public @ResponseBody  String  getIncomeByDate(ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
		
		Date startDate = null;
		Date endDate = null;
		String strStartDate = projectPaymentReceivedModel.getStartDate();
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startDate = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		String strEndDate = projectPaymentReceivedModel.getEndDate();
		if(null != strEndDate && !strEndDate.equals("")){
			try {
				endDate = DateUtils.dateStrToDate(strEndDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}		
		return projectPaymentReceivedService.getIncome(startDate,endDate);	
		}
		
		@RequestMapping(value="/getIncomeByDateGraph", method=RequestMethod.POST)
		public @ResponseBody  JSONArray  getIncomeByDateGraph(ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
			Date startDate = null;
			Date endDate = null;
			String strStartDate = projectPaymentReceivedModel.getStartDate();
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startDate = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			String strEndDate = projectPaymentReceivedModel.getEndDate();
			if(null != strEndDate && !strEndDate.equals("")){
				try {
					endDate = DateUtils.dateStrToDate(strEndDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				endDate = new Date(); 
			}
		
		return projectPaymentReceivedService.getIncomeByGroup(startDate,endDate);
		}
		@RequestMapping(value="/getIncomeByGroupByProject", method=RequestMethod.POST)
		public @ResponseBody List<ProjectPaymentReceivedModel> getIncomeByGroupByProject(ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
			List<ProjectPaymentReceivedModel> incomeList = projectPaymentReceivedService.getIncomeByGroupwiseProject(projectPaymentReceivedModel);
			request.setAttribute("incomeData", incomeList);
			//System.out.print("income"+incomeList);
			return incomeList;			
		}
		
		//ajax call get projectPaymentReceived Details and invoice ref no using projectId
		@RequestMapping(value="/getProjectPaymentReceivedWithInvoiceByProject",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectPaymentReceivedModel> getProjectPaymentReceivedWithInvoiceByProject(ProjectPaymentReceivedModel projectPaymentReceivedModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			
			//System.out.println("Project Id "+projectPaymentReceivedModel.getProjectId());
			List<ProjectPaymentReceivedModel> datalist = new ArrayList<ProjectPaymentReceivedModel>();
		
			if(null != projectPaymentReceivedModel.getEncProjectId()){
				
				String strProjectId = encryptionService.dcrypt(projectPaymentReceivedModel.getEncProjectId());
				long projectId = Long.parseLong(strProjectId);
				//System.out.println(projectId);
				datalist = projectPaymentReceivedService.getTotalProjectPaymentReceivedWithInvoiceByProject(projectId);

			}else{
				
				datalist = projectPaymentReceivedService.getTotalProjectPaymentReceivedWithInvoiceByProject(projectPaymentReceivedModel.getProjectId());

			}
			
			request.setAttribute("data1", datalist);	
		return datalist; 
	}	
		

						
		@RequestMapping(value="/getProjectPaymentWithoutInvoice",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectPaymentWithoutInvoiceMasterModel> getProjectPaymentWithoutInvoice(@RequestParam("projectId") long projectId,ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
				List<ProjectPaymentWithoutInvoiceMasterModel> datalist  = projectPaymentReceivedService.getProjectPaymentWithoutInvoice(projectId);
				request.setAttribute("data1", datalist);	
			return datalist; 
		}
		
		//ajax call get Invoice Detail using InvoiceId
		@RequestMapping(value="/getInvoiceDetailsByInvoiceId",method=RequestMethod.POST)
		public @ResponseBody List<ProjectInvoiceMasterModel> getInvoiceDetailsByInvoiceId(@RequestParam("invoiceId") long invoiceId,ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
				/*System.out.print(invoiceId);*/
			List<ProjectInvoiceMasterModel> datalist  = projectInvoiceMasterService.getProjectInvoiceMstDomainById(invoiceId);
				request.setAttribute("invoiceData", datalist);	
				/*System.out.println(datalist);*/
			return datalist; 
		}
		
		@RequestMapping(value="/getPaymentReceivedDetails", method=RequestMethod.POST)
		public @ResponseBody List<ProjectPaymentReceivedModel> getPaymentReceivedDetails(ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
			List<ProjectPaymentReceivedModel> incomeList = projectPaymentReceivedService.getPaymentReceivedDetails(projectPaymentReceivedModel);
			request.setAttribute("incomeData", incomeList);			
			return incomeList;		
		}
}