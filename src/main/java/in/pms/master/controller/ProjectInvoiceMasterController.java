package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectPaymentScheduleMasterService;
import in.pms.master.validator.ProjectInvoiceMasterValidator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class ProjectInvoiceMasterController {
	
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectPaymentScheduleMasterService projectPaymentScheduleMasterService;
	
	
	@RequestMapping({"/projectInvoiceMaster","/projectInvoiceMaster/{encProjId}"})
	public String projectInvoiceMaster(@PathVariable(name="encProjId", required=false) String encProjId,ProjectInvoiceMasterModel projectInvoiceMasterModel,HttpServletRequest request){		
		/*	//changed method from  active project to all projects
		 *
		 *  List<ProjectMasterModel> projectList = projectMasterService.getAllActiveProjectMasterData();
			 */
			 
			 List<ProjectMasterForm> projectList = projectMasterService.getAllProjectDetailsData();

	if(encProjId!=null){
		String projectId=encryptionService.dcrypt(encProjId);
		request.setAttribute("projectId", projectId.trim());
	}else{
		request.setAttribute("projectId", null);
	}
		request.setAttribute("projectList", projectList);
		
		return "projectInvoiceMaster";
	}
	

	
	@RequestMapping(value="/saveUpdateProjectInvoiceMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateProjectInvoiceMaster(ProjectInvoiceMasterModel projectInvoiceMasterModel,HttpServletRequest request , RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			new ProjectInvoiceMasterValidator().validate(projectInvoiceMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  request.setAttribute("showForm", 1);
		    	/*changed getAllActiveProjectInvoiceMasterDomain() to getAllProjectInvoiceMasterDomain()
		    	 *List<ProjectInvoiceMasterModel> list = projectInvoiceMasterService.getAllActiveProjectInvoiceMasterDomain(); 
		    	 *  */
			    	 List<ProjectInvoiceMasterModel> list = projectInvoiceMasterService.getAllProjectInvoiceMasterDomain();

		  		request.setAttribute("data", list);
		    	  mav.setViewName("projectInvoiceMaster");
		          return mav;
		      }
			
			String strDuplicateCheck = projectInvoiceMasterService.checkDuplicateProjectInvoiceRefNo(projectInvoiceMasterModel);
			if(null == strDuplicateCheck){
			long id = projectInvoiceMasterService.saveUpdateProjectInvoiceMaster(projectInvoiceMasterModel);
			if(id>0){
				if(projectInvoiceMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Project Invoice record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Project Invoice record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			}else{				
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
	
			mav.setViewName("redirect:/mst/projectInvoiceMaster");
			return mav;
		}

		@RequestMapping(value = "/deletetProjectInvoice",  method = RequestMethod.POST)
		public String deleteProjectInvoice(ProjectInvoiceMasterModel projectInvoiceMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
	    {	
					  
					  try
				    	{				
				    		long result= projectInvoiceMasterService.deleteProjectInvoice(projectInvoiceMasterModel);
				    		if(result !=-1){
				    			redirectAttributes.addFlashAttribute("message",  "Project Invoice record deleted Successfully");	
				    			redirectAttributes.addFlashAttribute("status", "success");
				    		}else{
				    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
				    			redirectAttributes.addFlashAttribute("status", "error");
				    		}
				    		List<ProjectInvoiceMasterModel> list = projectInvoiceMasterService.getAllProjectInvoiceMasterDomain();
							request.setAttribute("data", list);        
		                    
		                    
				    	}
				    	catch(Exception e){
			          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

				    	}		    								
		
			
			return "redirect:/mst/projectInvoiceMaster";
	    }
		
		//ajax call get Project Invoice using ProjectId
		@RequestMapping(value="/getProjectInvoiceByProjectId",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectInvoiceMasterModel> getProjectInvoiceByProjectId(@RequestParam("projectId") long projectId,ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		
			
				List<ProjectInvoiceMasterModel> datalist = new ArrayList<ProjectInvoiceMasterModel>();				
				datalist = projectInvoiceMasterService.getProjectInvoiceByProjectId(projectId);
				request.setAttribute("data1", datalist);	
			return datalist; 
		}

		//ajax call get projectInvoice Details and invoice status using projectId
				@RequestMapping(value="/getProjectInvoiceDetailsByProjectId",method=RequestMethod.POST,produces="application/json")
				public @ResponseBody List<ProjectInvoiceMasterModel> getProjectInvoiceDetailsByProjectId(ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
					
					//System.out.println("Project Id "+projectInvoiceMasterModel.getProjectId());
					List<ProjectInvoiceMasterModel> datalist = new ArrayList<ProjectInvoiceMasterModel>();
				
					if(null != projectInvoiceMasterModel.getEncProjectId()){
						
						String strProjectId = encryptionService.dcrypt(projectInvoiceMasterModel.getEncProjectId());
						long projectId = Long.parseLong(strProjectId);
						//System.out.println(projectId);
						datalist = projectInvoiceMasterService.getProjectInvoiceDetailsByProjectId(projectId);

					}else{
						
						datalist = projectInvoiceMasterService.getProjectInvoiceDetailsByProjectId(projectInvoiceMasterModel.getProjectId());

					}
					
					request.setAttribute("data1", datalist);	
				return datalist; 
			}		

				@RequestMapping(value="/getInviceDetailsData",method=RequestMethod.POST)
				public @ResponseBody List<ProjectInvoiceMasterModel>getInvoicedata(@RequestParam("encInvoiceId") String encInvoiceId, HttpServletRequest request){
					String encId=encryptionService.dcrypt(encInvoiceId);
					long numInvoiceId = Long.parseLong(encId);
					List<ProjectInvoiceMasterModel> data = projectInvoiceMasterService.getProjectInvoiceMstDomainById(numInvoiceId);
/*					ProjectInvoiceMasterDomain data =new ProjectInvoiceMasterDomain();
*/					return data;
				}
				
				//ajax call get Scheduled Payment using ProjectId
				@RequestMapping(value="/getScheduledPayment",method=RequestMethod.POST,produces="application/json")
				public @ResponseBody List<ProjectPaymentScheduleMasterModel> getScheduledPayment(@RequestParam("projectId") long projectId,ProjectInvoiceMasterModel projectInvoiceMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
					List<ProjectPaymentScheduleMasterModel> scheduledPayementList = projectPaymentScheduleMasterService.getScheduledPaymentByProjectId(projectId);
						
					return scheduledPayementList; 
				}
				
				
				@RequestMapping(value="/getPendingPaymentsInvoiceDetail", method=RequestMethod.POST)
				public @ResponseBody List<ProjectInvoiceMasterModel> getPendingPaymentsInvoiceDetail(ProjectInvoiceMasterModel projectInvoiceMasterModel ,HttpServletRequest request){
					List<ProjectInvoiceMasterModel> pendingPaymentsList = projectInvoiceMasterService.getPendingPaymentsInvoiceDetail(projectInvoiceMasterModel);
					
					request.setAttribute("pendingPaymentsList", pendingPaymentsList);	
					
					return pendingPaymentsList;		
				}
				
				@RequestMapping(value="/getPendingInvoiceDetail", method=RequestMethod.POST)
				public @ResponseBody List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetail(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel ,HttpServletRequest request){
					List<ProjectPaymentScheduleMasterModel> pendingInvoiceList = projectInvoiceMasterService.getPendingInvoiceDetail();
					request.setAttribute("pendingInvoiceList", pendingInvoiceList);	
					
					return pendingInvoiceList;		
				}
				
				@RequestMapping(value="/getPendingPaymentsDetailsByInvoiceDt", method=RequestMethod.POST)
				public @ResponseBody List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceDt(@RequestParam("invoiceDate") String invoiceDate,@RequestParam("symbol") String symbol,ProjectInvoiceMasterModel projectInvoiceMasterModel ,HttpServletRequest request){
					List<ProjectInvoiceMasterModel> pendingPaymentsList = projectInvoiceMasterService.getPendingPaymentsDetailsByInvoiceDt(invoiceDate,symbol);
					
					request.setAttribute("pendingPaymentsList", pendingPaymentsList);	
					
					return pendingPaymentsList;		
				}
				
				@RequestMapping(value="/getPendingInvoiceDetailbyDate", method=RequestMethod.POST)
				public @ResponseBody List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyDate(@RequestParam("dueDate") String dueDate,@RequestParam("symbol") String symbol,ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel ,HttpServletRequest request){
					List<ProjectPaymentScheduleMasterModel> pendingInvoiceList = projectInvoiceMasterService.getPendingInvoiceDetailbyDate(dueDate,symbol);
					request.setAttribute("pendingInvoiceList", pendingInvoiceList);	
					
					return pendingInvoiceList;		
				}
				
				//Bhavesh(01-03-2024) getting the pending payment details year wise
				@RequestMapping(value="/getPendingPaymentsDetailsByInvoiceYear", method=RequestMethod.POST)
				public @ResponseBody List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceYear(@RequestParam("invoiceFromDate") String invoiceFromDate,@RequestParam("invoiceToDate") String invoiceToDate,ProjectInvoiceMasterModel projectInvoiceMasterModel ,HttpServletRequest request){
					List<ProjectInvoiceMasterModel> pendingPaymentsListByYear = projectInvoiceMasterService.getPendingPaymentsDetailsByInvoiceYear(invoiceFromDate,invoiceToDate);
					
					/*request.setAttribute("pendingPaymentsList", pendingPaymentsList);*/	
					
					return pendingPaymentsListByYear;		
				}
				//Bhavesh(01-03-2024) getting the pending invoice details year wise
				@RequestMapping(value="/getPendingInvoiceDetailbyYear", method=RequestMethod.POST)
				public @ResponseBody List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyYear(@RequestParam("dueFromDate") String dueFromDate,@RequestParam("dueToDate") String dueToDate,ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel ,HttpServletRequest request){
					List<ProjectPaymentScheduleMasterModel> pendingInvoiceListByYear = projectInvoiceMasterService.getPendingInvoiceDetailbyYear(dueFromDate,dueToDate);
					request.setAttribute("pendingInvoiceListByYear", pendingInvoiceListByYear);	
					
					return pendingInvoiceListByYear;		
				}
}