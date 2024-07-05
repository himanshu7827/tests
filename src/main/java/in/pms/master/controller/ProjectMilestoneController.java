package in.pms.master.controller;


import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.model.WorkflowModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.WorkflowService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.MilestoneTypeMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProjectModuleMasterModel;
import in.pms.master.service.MilestoneTypeMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectMilestoneService;
import in.pms.master.service.ProjectModuleMasterService;
import in.pms.master.validator.MilestoneMasterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
@Slf4j
public class ProjectMilestoneController {

	@Autowired
	ProjectMilestoneService projectMilestoneService;
	@Autowired
	ProjectModuleMasterService projectModuleMasterService;
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
    EncryptionService encryptionService;
	@Autowired
	MilestoneTypeMasterService milestoneTypeMasterService;
	
	@Autowired
	WorkflowService workflowService;
	
	
	@RequestMapping("/ProjectMilestoneMaster")
	public String getProjectMilestoneMaster(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm ,MilestoneActivityModel milestoneModuleModel,Model map){		
		Map md = map.asMap();
		String strProjectId = "";
		if(null != md.get("projectId")){
			strProjectId = ""+md.get("projectId");
		}
		String encProjectId = projectMilestoneForm.getEncProjectId();
		if(null != encProjectId && !encProjectId.equals("")){
			strProjectId = encryptionService.dcrypt(encProjectId);
			String referer = request.getHeader("referer");
			if(null != referer && referer.contains("projectDetails")){
				request.setAttribute("referer", referer);
			}
		}
		int projectId=0;
		if(null != strProjectId && !strProjectId.equals("")){
			projectId = Integer.parseInt(strProjectId);
		}
		
		String allowedRoles = ","+ResourceBundleFile.getValueFromKey("EditBaselineDateForMilestone")+",";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		List<EmployeeRoleMasterModel> roles = userInfo.getEmployeeRoleList();	
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		if(null != selectedRole){
			String strSelectedRole = ","+selectedRole.getNumRoleId()+",";
			if(allowedRoles.contains(strSelectedRole)){
				request.setAttribute("allowDateEdit", "1");
			}else{
				request.setAttribute("allowDateEdit", "0");
			}
		}else{
			request.setAttribute("allowDateEdit", "0");
		}
	
		if(null != request.getParameter("encProjectId")){
			try{
			String tempProjectId = request.getParameter("encProjectId");
			String strProject = encryptionService.dcrypt(tempProjectId);
			projectId = Integer.parseInt(strProject);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		List<MilestoneTypeMasterForm> list = milestoneTypeMasterService.getAllMilestoneTypeData();
		request.setAttribute("milestoneTypeData", list);		
			List<ProjectMasterModel> projectList =new ArrayList<ProjectMasterModel>();
		      if(projectId  > 0){
		    	  ProjectMasterDomain model=projectMasterService.getProjectMasterDataById(projectId);
		    	  if(model!=null){
		  		  request.setAttribute("projectDetail", model);
		  		 try{
		  			 if(model.getDtProjectStartDate()!=null){
		  			request.setAttribute("projectStartDate",DateUtils.dateToString(model.getDtProjectStartDate()));
		  			 }
		  			 if(model.getDtProjectEndDate()!=null){
		  			request.setAttribute("projectEndDate",DateUtils.dateToString(model.getDtProjectEndDate()));
		  			 }
		  		 }
		  		 catch(Exception e){
		  			 e.printStackTrace();
		  		 }
		    	  }
		    	  List<ProjectMasterDomain> domainList=new ArrayList<ProjectMasterDomain>();		    	 
					projectList=projectMasterService.convertProjectMasterDomainToModelList(domainList);
					request.setAttribute("projectId", projectId);
					request.setAttribute("encProjectId", encryptionService.encrypt(""+projectId));
		      }else{
					//projectList = projectMasterService.getAllActiveProjectMasterData();
			  		request.setAttribute("projectId", 0);			  
			  		return "redirect:/accessDenied"; 
		  
		      }
		  	List<ProjectMilestoneForm> data = projectMilestoneService.getMilestoneByProjectId(projectId);
			request.setAttribute("data", data);
			
			request.setAttribute("projectData", projectList);
			
			//Bhavesh(21-02-23) get the action id for edit date
			List<WorkflowModel> workflowModelList=workflowService.getTransactionDetails(projectId,3);
			
			if (workflowModelList.size() > 0) {
			    long actionId = workflowModelList.get(0).getNumActionId();
			    int roleId=selectedRole.getNumRoleId();
			    boolean isActionId = ((actionId == 5 && roleId==3 )|| (actionId == 13 && roleId==3) || (actionId == 17 && roleId==3));
			    request.setAttribute("actionId", isActionId);
			} else {
			    request.setAttribute("actionId", false);
			}

		
			
		return "ProjectMilestoneMaster";
	}
	
	@RequestMapping(value="/saveProjectMilestone",method=RequestMethod.POST)	
	public ModelAndView saveUpdateProjectMilestone(HttpServletRequest request, ProjectMilestoneForm projectMilestoneForm,BindingResult bindingResult,RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new MilestoneMasterValidator().validate(projectMilestoneForm, bindingResult);
	      if (bindingResult.hasErrors()) {
	  		mav.setViewName("ProjectMilestoneMaster");
	          return mav;
	      }
	  	List<MilestoneTypeMasterForm> list = milestoneTypeMasterService.getAllMilestoneTypeData();
		request.setAttribute("milestoneTypeData", list);	
		String strDuplicateCheck = projectMilestoneService.checkDuplicateProjectMilestoneData(projectMilestoneForm);
		if(null == strDuplicateCheck){
		long id = projectMilestoneService.saveProjectMilestoneData(projectMilestoneForm);
		if(id>0){
			if(projectMilestoneForm.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Project Milestone details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Project Milestone details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
  		redirectAttributes.addFlashAttribute("projectId",projectMilestoneForm.getProjectId());

		mav.setViewName("redirect:/mst/ProjectMilestoneMaster");
		return mav; 
	}
	
	
	
	@RequestMapping(value="/moduleDetailsByProjectId",method=RequestMethod.POST)
	public @ResponseBody List<ProjectModuleMasterModel> getmodule(@RequestParam("numId") long projectId, HttpServletRequest request){
		List<ProjectModuleMasterModel> data = projectModuleMasterService.getProjectModuleByProjectId(projectId);
		return data;
	}

	@RequestMapping(value="/milestoneDataByProjectId",method=RequestMethod.POST)
	public @ResponseBody List<ProjectMilestoneForm> getdata(@RequestParam("projectId") long projectId, HttpServletRequest request){
		//List<ProjectMilestoneDomain> milestoneData = projectMilestoneService.getMilestoneDataByProjectId(projectId);
		
		List<ProjectMilestoneForm> milestoneData = projectMilestoneService.getMilestoneByProjectId(projectId);
		return milestoneData;
	}
	
	@RequestMapping(value="/milestoneDetailsByProjectId",method=RequestMethod.POST)
	public @ResponseBody List<ProjectMilestoneForm> getdetail(@RequestParam("encProjectId") String encprojectId, HttpServletRequest request){
		String encPrjId=encryptionService.dcrypt(encprojectId);
		long projectId=Long.parseLong(encPrjId);
		List<ProjectMilestoneForm> milestoneData = projectMilestoneService.getMilestoneByProjectId(projectId);
		return milestoneData;
	}
	
	@RequestMapping(value="/getMilestoneDetail",method=RequestMethod.POST)
	public @ResponseBody ProjectMilestoneForm getActivityDetail(HttpServletRequest request,ProjectMilestoneForm projectMilestoneForm){
		String strMilestoneId = encryptionService.dcrypt(projectMilestoneForm.getEncMilestsoneId());		
		long milestoneId = Long.parseLong(strMilestoneId);
		return projectMilestoneService.getMilestoneDataByMilestoneId(milestoneId);		 
	}
	
	/*---------------------- Delete Milestone Controller [23-10-2023] --------------------*/
	@RequestMapping(value="/deleteMilestoneDetail",method=RequestMethod.POST)
	public @ResponseBody long deleteMilestoneDetail(HttpServletRequest request,ProjectMilestoneForm projectMilestoneForm){
		ProjectMilestoneForm milestoneData= projectMilestoneService.getMilestoneDataByMilestoneId(projectMilestoneForm.getMilestoneId());
		milestoneData.setValid(true);
		long id = projectMilestoneService.saveProjectMilestoneData(milestoneData);
		return id;
	}

}
