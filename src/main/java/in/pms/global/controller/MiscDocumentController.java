package in.pms.global.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.pms.global.service.MiscDocumentService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.service.ProjectMasterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MiscDocumentController {
	
	@Autowired
	MiscDocumentService miscDocumentService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectMasterDao projectMasterDao;

	@RequestMapping(value="/downloadTemplate",method = RequestMethod.POST)
	public void downloadTemplate(HttpServletRequest request, ProjectDocumentMasterModel projectDocumentMasterModel,HttpServletResponse response){	
		
		if(null != projectDocumentMasterModel.getTemplate()){			
			miscDocumentService.downloadTemplate(projectDocumentMasterModel, response);
		}
		
	}
	
	/*----------------- Handle Excel Sheet download Request [ added by Anuj ]  -------------------------------------*/
	@RequestMapping(value="/downloadOngoingProjects",method=RequestMethod.POST)	
	@ResponseBody
	public Boolean downloadOngoingProjects(@ModelAttribute("projectMasterForm") ProjectMasterForm projectMasterForm ,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
			Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();

			List<ProjectMasterForm> list = new ArrayList<>();
			if (!userInfo.getEmployeeRoleList().isEmpty()) {
			    int numRoleID = userInfo.getEmployeeRoleList().get(0).getNumRoleId();
			    if(numRoleID==9 || numRoleID==7 || numRoleID==6 ){
					String encGroupId = projectMasterForm.getEncGroupId();
					list = projectMasterService.viewProjectDetailsForExcel(encGroupId);
			    }
			    if(numRoleID==5){
			    	String groupId = ""+userInfo.getGroupId();
			    	list = projectMasterService.convertProjectDetailsForExcelToModelList(projectMasterDao.viewProjectDetailsForExcel(groupId));
			    }
			    
			    if(numRoleID==3){
			    	System.out.println(userInfo.getAssignedProjects());
			    	List<EmployeeRoleMasterModel> employeeRoleList = userInfo.getEmployeeRoleList();
			    	for (int i = 0; i < employeeRoleList.size(); i++) {
			    	    EmployeeRoleMasterModel employeeRole = employeeRoleList.get(i);
			    	    //System.out.println(employeeRole.getNumProjectId()+" "+employeeRole.isValid());
			    	 
			    	}
			    }
			}
			boolean download = miscDocumentService.downloadOngoingProjects(list,response);
			//boolean download = miscDocumentService.downloadWeeklyReport(list, response);
			return download;
	}
	
	
	/*----------------- Handle Excel Sheet WeeklyReport [ added by Bhavesh ]  -------------------------------------*/
	@RequestMapping(value="/downloadWeeklyReport",method=RequestMethod.POST)	
	@ResponseBody
	public Boolean downloadWeeklyReport(@ModelAttribute("projectMasterForm") ProjectMasterForm projectMasterForm ,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
			Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();

			List<ProjectMasterForm> list = new ArrayList<>();
			
			//boolean download = miscDocumentService.downloadOngoingProjects(list,response);
			boolean download = miscDocumentService.downloadWeeklyReport(list, response);
			return download;
	}
	
	@RequestMapping(value="/fetchWeeklyGraphData",method=RequestMethod.POST)	
	@ResponseBody
	public Map<String,Map<String,List<List<String>>>> fetchWeeklyGraphData(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
			
			return  miscDocumentService.fetchWeeklyGraphData();
	}
}
