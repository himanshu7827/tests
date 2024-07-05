package in.pms.transaction.controller;

import in.pms.master.model.BusinessTypeModel;
import in.pms.master.model.ProcessMasterModel;
import in.pms.master.model.ProjectCategoryModel;
import in.pms.master.model.ProjectTypeModel;
import in.pms.master.service.BusinessTypeService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectTypeService;
import in.pms.transaction.model.ModuleTypeModel;
import in.pms.transaction.model.WorkflowConfigurationModel;
import in.pms.transaction.service.ApplicationService;
import in.pms.transaction.service.ModuleService;
import in.pms.transaction.service.WorkflowConfigurationService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WorkflowConfigurationController {

	@Autowired
	BusinessTypeService businessTypeService;
	@Autowired
	ProjectTypeService projectTypeService;
	@Autowired
	ProjectCategoryService projectCategoryService;
	@Autowired
	OrganisationMasterService organisationMasterService;
	@Autowired
	GroupMasterService groupMasterService;	
	@Autowired
	ModuleService moduleTypeService;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	WorkflowConfigurationService workflowConfigurationService;
	
	@RequestMapping(value = "/ShowWorkflow")
	public @ResponseBody  List<ProcessMasterModel> ShowWorkFlow(HttpServletRequest request,@ModelAttribute WorkflowConfigurationModel workflowTypeModel) 
	{
	  int workflowTypeId= 2;
	  List<ProcessMasterModel> list=workflowConfigurationService.getProcessForWorkflow(workflowTypeId);
		request.setAttribute("processList", list);
		return list;
	}
	
	@RequestMapping("workflowConfigurationMaster")
	private String applicationBasicDetails(WorkflowConfigurationModel workflowConfigurationModel,HttpServletRequest request){
		
		List<ModuleTypeModel> moduleTypesList = moduleTypeService.getAllActiveModules();
		request.setAttribute("moduleTypesList", moduleTypesList);	
		
		List<BusinessTypeModel> businessTypeModelList = businessTypeService.getActiveBusinessType();
		request.setAttribute("businessTypeModelList", businessTypeModelList);
		
		List<ProjectTypeModel> projectTypeModelList = projectTypeService.getActiveProjectType();
		request.setAttribute("projectTypeModelList", projectTypeModelList);
		
		List<ProjectCategoryModel> projectCategoryModelList =projectCategoryService.getActiveProjectCategory();
		request.setAttribute("projectCategoryModelList", projectCategoryModelList);
		
		
		List<WorkflowConfigurationModel> list = workflowConfigurationService.getAllTypes();
		request.setAttribute("data", list);		
		
		
		return "workflowConfigurationMaster";
	}
	
	@RequestMapping(value="/saveUpdateWorkflowTypeDetails",method=RequestMethod.POST,produces="application/json")
	private @ResponseBody WorkflowConfigurationModel saveApplicationBasicDetails(@ModelAttribute WorkflowConfigurationModel workflowTypeModel,HttpServletRequest request){
		
/*		int moduleTypeId= Integer.parseInt(request.getParameter("moduleTypeId"));
		int businessTypeId  = Integer.parseInt(request.getParameter("businessTypeId"));
		int projectTypeId  = Integer.parseInt(request.getParameter("projectTypeId"));
		int projCategoryId = Integer.parseInt(request.getParameter("categoryId"));*/
		workflowConfigurationService.saveUpdateWorkflowType(workflowTypeModel);		
		
		
		return workflowTypeModel;
		
		
	}
	
	@RequestMapping(value="/getWorkflowIdWithModuleName")
	private int getWorkflowIdWithModuleName(@RequestParam("moduleName")String name,HttpServletRequest request){
		int moduleId=(int) moduleTypeService.getModuleByName(name).getNumId();
		return workflowConfigurationService.getWorkflowTypeId(moduleId, 0, 0, 0);
	}
}
