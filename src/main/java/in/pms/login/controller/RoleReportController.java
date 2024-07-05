package in.pms.login.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.login.model.RoleReportModel;
import in.pms.master.domain.ReportMaster;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.ReportMasterService;
import in.pms.master.service.RoleMasterService;

@Controller
public class RoleReportController {
	
	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	ReportMasterService reportMasterService;
	
	@Autowired
	GroupMasterService groupMasterService;

	@RequestMapping(value ="/roleReport", method=RequestMethod.GET)
	public String getAllRoleReport(HttpServletRequest request, RoleReportModel roleReportModel,RedirectAttributes redirectAttributes){		
		List<RoleReportModel> roleList = roleMasterService.getAllRoleDetails();	
		request.setAttribute("roleList", roleList);		
		List<ReportMaster> reportList = reportMasterService.reportName();
		request.setAttribute("reportList", reportList);
		return "roleReport";
	}
	
	// added below controller by varun to get technical group 
/*	@RequestMapping(value="/ViewAllProjectDetailReport",method = { RequestMethod.GET, RequestMethod.POST })
	public String generateProjectDetails(HttpServletRequest request, HttpServletResponse reponse){
		List<GroupMasterModel> groupList =groupMasterService.getAllTechnicalActiveGroupMasterDomain();
		request.setAttribute("groupList", groupList);
	return "projectDetailsReport";	
		}*/
	
    @RequestMapping(value="/ViewAllProjectDetailReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String generateProjectDetails(HttpServletRequest request, HttpServletResponse response) {
	    List<GroupMasterModel> groupList = groupMasterService.getAllTechnicalActiveGroupMasterDomain();
	    
	    // Sort the groupList alphabetically by the name property
	    Collections.sort(groupList, new Comparator<GroupMasterModel>() {
	        @Override
	        public int compare(GroupMasterModel o1, GroupMasterModel o2) {
	        	return o1.getGroupName().compareToIgnoreCase(o2.getGroupName());
	        }
	    });

	    request.setAttribute("groupList", groupList);
	    return "projectDetailsReport";
	}
	
	@RequestMapping("/saveRoleReport")
	public String saveRoleReports(HttpServletRequest request, RoleReportModel roleReportModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = roleMasterService.checkDuplicateRoleData(roleReportModel);
		if(null == strDuplicateCheck){
		long id = roleMasterService.saveRoleData(roleReportModel);
		if(id>0){
			if(roleReportModel.getRoleId()==0){
				redirectAttributes.addFlashAttribute("message",  "RoleReport details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "RoleReport details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					  
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
				
		return "redirect:/roleReport";
	}
	  
      @RequestMapping("/getReportByRoleId") 
      public @ResponseBody String getReportByRoleId(HttpServletRequest request){ 
    	  long roleId = 0; if(null!=request.getParameter("roleId")) { 
    		  roleId =Long.parseLong(request.getParameter("roleId")); 
    		  String report=roleMasterService.getReportByRoleId(roleId); 
    		  return report;
	  } 
	  return ""; 
	  }
	 
	
}



