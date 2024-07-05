package in.pms.global.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ManpowerRequirementModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;
import in.pms.master.model.TaskAssignmentModel;
import in.pms.master.model.TaskDetailsModel;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.ManpowerRequirementService;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectMilestoneService;
import in.pms.master.service.ProjectPaymentScheduleMasterService;
import in.pms.master.service.TaskDetailsService;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.service.ProgressReportService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class GroupMonthlyReport {
	
	@Autowired 
	EncryptionService encryptionService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	TaskDetailsService taskDetailsService;
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;

	@Autowired
	ManpowerRequirementService manpowerRequirementService;
	
	@Autowired
	ProjectPaymentScheduleMasterService projectPaymentScheduleMasterService;
	
	@Autowired
	ProjectMilestoneService projectMilestoneService;
	
	@Autowired
	ProgressReportService progressReportService;
	
	
@PreAuthorize("hasAuthority('WRITE_GROUP_MONTHLY_REPORT')")
@RequestMapping(value="/GroupMonthlyReport",method = {RequestMethod.GET, RequestMethod.POST })
	
	public String MonthlyReport(HttpServletRequest request, @ModelAttribute ProjectMasterForm projectMasterForm){	
		
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		//String roleCheck="";
		List<CategoryMasterModel> categoryMasterList=new ArrayList<CategoryMasterModel>();
		//System.out.println("role id "+userInfo.getSelectedEmployeeRole().getNumRoleId());
		

					categoryMasterList=progressReportService.getAllCategoryByFlag("G");
					request.setAttribute("categoryList", categoryMasterList);
				
				request.setAttribute("group", userInfo.getGroupId());
			
		
		

		
		
		
		return "GroupMonthlyReport";
	}
	
	
}
