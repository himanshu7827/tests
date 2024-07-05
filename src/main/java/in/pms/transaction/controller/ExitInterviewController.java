package in.pms.transaction.controller;


import in.pms.login.util.UserInfo;
import in.pms.master.domain.ExitInterviewMainDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.QuestionMasterModel;
import in.pms.master.model.ResponseModel;
import in.pms.master.model.TaskAssignmentModel;
import in.pms.master.model.TaskDetailsModel;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.QuestionMasterService;
import in.pms.transaction.dao.ExitInterviewDao;
import in.pms.transaction.model.ExitInterviewModel;
import in.pms.transaction.service.ExitInterviewService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//@RequestMapping("/mst")
@Slf4j
public class ExitInterviewController {

	@Autowired
	QuestionMasterService questionMasterService;
	
	@Autowired
	ExitInterviewService exitInterviewService;

	@Autowired
	ExitInterviewDao exitInterviewDao;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@RequestMapping("/ExitInterview")
	public String getExitInterviewMaster(HttpServletRequest request, ExitInterviewModel exitInterviewModel,QuestionMasterModel questionMasterModel,RedirectAttributes redirectAttributes){		
		String result = "";
		ExitInterviewMainDomain exitInterviewMainDomain=exitInterviewService.getExitInterviewMainDomainDataByEmpId();
		if(exitInterviewMainDomain!=null){
			 result="Employee already submitted his/her experience.";
			 request.setAttribute("result",result);
		}
		else{
		List<QuestionMasterModel> list = questionMasterService.getActiveQuestionData();
		request.setAttribute("data", list);
		}
		return "ExitInterviewMaster";
	}
	
	@RequestMapping(value="saveExitInterview",method= RequestMethod.POST)
	@ResponseBody
	public long saveExitInterviewMaster(ExitInterviewModel exitInterviewModel,@RequestParam("flaEmail") String flaEmail,@RequestParam("slaEmail") String slaEmail,@RequestParam("employeeRemarks") String empRemarks,HttpServletRequest request){	
		long id = exitInterviewService.saveExitInterviewData(exitInterviewModel,flaEmail,slaEmail,empRemarks);
		return id; 		
	}
	
	@RequestMapping("/InterviewExit")
	public String getInterviewExitByFla(HttpServletRequest request, ExitInterviewModel exitInterviewModel,RedirectAttributes redirectAttributes){		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String officeEmail=userInfo.getOfficeEmail();	
		List<ExitInterviewMainDomain> exitInterviewMainDomain= exitInterviewDao.getExitInterviewMainDomainDataByFlaEmail(officeEmail);
		if(exitInterviewMainDomain!=null){
			List<ExitInterviewModel> list=exitInterviewService.getActiveExitInterviewDataByFla();
			request.setAttribute("data", list);	

		}
		List<ExitInterviewMainDomain> exitInterviewMainDomain1= exitInterviewDao.getExitInterviewMainDomainDataBySlaEmail(officeEmail);

		if(exitInterviewMainDomain1!=null){
			List<ExitInterviewModel> list1=exitInterviewService.getActiveExitInterviewDataBySla();
			request.setAttribute("data1", list1);	
		}
		
		return "ExitInterview";
	}
	
	@RequestMapping(value="/saveFlaNdSlaRemarks",method = RequestMethod.POST)	
	public @ResponseBody ResponseModel AssignedData( HttpServletRequest request,ExitInterviewModel exitInterviewModel){
		ResponseModel responseModel=new ResponseModel();
		    long numId = exitInterviewModel.getExitInterviewId();
		    int approvalId=exitInterviewModel.getApprovalId();
		    int statusValue=exitInterviewModel.getStatusValue();
		    String remarks=exitInterviewModel.getRemarks();
		    if(approvalId==1 && statusValue==1){
		    exitInterviewService.updateFlaNdSlaStatus(numId,"FLA Approved",approvalId,remarks);
			responseModel.setStatus(true);
			
			}
		    else if(approvalId==2 && statusValue==1){
			    exitInterviewService.updateFlaNdSlaStatus(numId,"SLA Approved",approvalId,remarks);
				responseModel.setStatus(true);
				
				}
		    else{
			    exitInterviewService.updateFlaNdSlaStatus(numId,"",approvalId,remarks);
				responseModel.setStatus(false);
			}		
			return responseModel;
}
	
	@RequestMapping("/InterviewExitByHr")
	public String getInterviewExitByHr(HttpServletRequest request, ExitInterviewModel exitInterviewModel,RedirectAttributes redirectAttributes){		
		List<ExitInterviewModel> list=exitInterviewService.getActiveExitInterviewDataApprovedBySla();
		request.setAttribute("data", list);	
				
		return "ExitInterviewByHr";
	}
	
	
	@RequestMapping(value="/rolesByEmpId",method= RequestMethod.POST)		
	public @ResponseBody List<EmployeeRoleMasterModel> getActiveRolesByEmpId(EmployeeRoleMasterModel employeeRoleMasterModel,@RequestParam("empId") long empId,HttpServletRequest request){		
		return employeeRoleMasterService.getActiveEmployeeRoleByEmpId(empId);
	}
	
	@RequestMapping(value="saveExitInterviewByHr",method= RequestMethod.POST)
	@ResponseBody
	public long saveExitInterviewByHr(ExitInterviewModel exitInterviewModel,@RequestParam("employeeId")long employeeId,HttpServletRequest request){	
		long id = exitInterviewService.saveExitInterviewByHr(exitInterviewModel,employeeId);
		return id; 		
	}


}
