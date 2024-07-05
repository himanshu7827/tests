package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.validator.ProjectBudgetHeadValidator;
import in.pms.transaction.model.ProjectBudgetForm;
import in.pms.transaction.service.BudgetHeadMasterService;
import in.pms.transaction.service.ProjectBudgetService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
	public class ProjectBudgetHeadController {

		@Autowired
		ProjectBudgetService projectbudgetService;
		
		@Autowired
		BudgetHeadMasterService budgetHeadMasterService;
		@Autowired
		ProjectBudgetService projectBudgetService;
		
		@Autowired
		ProjectMasterService projectMasterService;
		
		@Autowired
		EncryptionService encryptionService;
		
		@RequestMapping("/ProjectBudgetHeadController")
		public String getProjectMaster(@ModelAttribute("projectbudgethead") ProjectBudgetForm projectbudgethead,HttpServletRequest request,Model map){	
		
			Map md = map.asMap();
			int projectId=0;	
			
			if(null != request.getParameter("encProjectId")){
				try{
				String encProjectId = request.getParameter("encProjectId");
				String strProjectId = encryptionService.dcrypt(encProjectId);
				projectId = Integer.parseInt(strProjectId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			if(md.get("projectId")!=null){
			    projectId=Integer.parseInt(md.get("projectId").toString());
			}
			if(projectId !=0){
				Map<String,Map<String,Float>> dataMap = projectBudgetService.getProjectBudget(projectId);
				request.setAttribute("dataMap", dataMap);
				request.setAttribute("projectId", projectId);
				request.setAttribute("encProjectId", encryptionService.encrypt(""+projectId));
				return "ProjectBudgetMaster";
			  }else{
				 return "redirect:/accessDenied"; 
			  }
		}
		
		@RequestMapping(value="/saveProjectBudgetDetailsMaster",method=RequestMethod.POST)	
		@ResponseBody
		public String saveUpdateProjectDetailsMaster(@ModelAttribute("projectbudgethead") ProjectBudgetForm projectMasterForm ,HttpServletRequest request,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
			new ProjectBudgetHeadValidator().validate(projectMasterForm, bindingResult);
		      if (bindingResult.hasErrors()) {					
		          return bindingResult.getAllErrors().toString();
		      }
			long id = projectbudgetService.saveProjectDetailsData(projectMasterForm);			
			return ""+id;
		}
		
		@RequestMapping(value="/BudgetDetailByProjectIdYear",method=RequestMethod.POST)
		public @ResponseBody List<ProjectBudgetForm> getdata(@RequestParam("numProjectId") long projectId,@RequestParam("year") int year, HttpServletRequest request){
			List<ProjectBudgetForm> data=projectbudgetService.getBudgetDetailByProjectIdYear(projectId, year);
					return data;
		}
		
		@RequestMapping(value="/saveProjectBudgetDetails",method=RequestMethod.POST)	
		@ResponseBody
		public boolean saveProjectBudgetDetails(@ModelAttribute("projectbudgethead") ProjectBudgetForm projectMasterForm ,HttpServletRequest request){		
			boolean  flag = projectbudgetService.saveProjectBudgetDetails(projectMasterForm);			
			return flag;
		}
}