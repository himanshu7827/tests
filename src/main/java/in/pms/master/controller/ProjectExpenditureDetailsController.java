package in.pms.master.controller;

import in.pms.transaction.model.BudgetHeadMasterForm;
import in.pms.transaction.model.DashboardModel;
import in.pms.global.util.DateUtils;
/*import in.pms.master.model.EmployeeRoleMasterModel;*/
import in.pms.master.model.ExpenditureHeadModel;
import in.pms.master.model.ProjectExpenditureDetailsModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.transaction.service.BudgetHeadMasterService;
import in.pms.transaction.service.DashboardService;
import in.pms.master.service.ExpenditureHeadService;
import in.pms.master.service.ProjectExpenditureDetailsService;
import in.pms.master.service.ProjectMasterService;
/*import in.pms.master.validator.EmployeeRoleMasterValidator;*/
import in.pms.master.validator.ProjectExpenditureDetailsValidator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

public class ProjectExpenditureDetailsController {
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	BudgetHeadMasterService budgetHeadMasterService;
	
	@Autowired
	ExpenditureHeadService expenditureHeadService;
	
	@Autowired
	ProjectExpenditureDetailsService projectExpenditureDetailsService;
	
	@Autowired
	DashboardService dashboardService;
	
	@RequestMapping("/projectExpenditureDetails")
	public String projectExpenditureDetails(ProjectExpenditureDetailsModel ProjectExpenditureDetailsModel,HttpServletRequest request){	
		
		List<ProjectMasterModel> projectList = projectMasterService.getAllActiveProjectMasterData();
		List<BudgetHeadMasterForm> budgetHeadList = budgetHeadMasterService.getAllBugdetHeadData();
		List<ExpenditureHeadModel> expenditureHeadList = expenditureHeadService.getAllActiveExpenditureHeadMasterDomain();
		
		request.setAttribute("projectList", projectList);
		request.setAttribute("budgetHeadList", budgetHeadList);
		request.setAttribute("expenditureHeadList", expenditureHeadList);
		
		return "projectExpenditureDetails";
	}
	

	
	@RequestMapping(value="/saveUpdateProjectExpenditureDetails",method=RequestMethod.POST)	
		public ModelAndView saveUpdateProjectExpenditureDetails(ProjectExpenditureDetailsModel projectExpenditureDetailsModel,HttpServletRequest request , RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			
			
			
			new ProjectExpenditureDetailsValidator().validate(projectExpenditureDetailsModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	 // request.setAttribute("showForm", 1);
		    	  List<ProjectExpenditureDetailsModel> list = projectExpenditureDetailsService.getAllActiveProjectExpenditureDetailsDomain();

		  		request.setAttribute("data", list);
		    	  mav.setViewName("projectExpenditureDetails");
		          return mav;
		      }
			
			
			
			long id = projectExpenditureDetailsService.saveUpdateProjectExpenditureDetails(projectExpenditureDetailsModel);
			if(id>0){
				if(projectExpenditureDetailsModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Project Expenditure Details record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Project Expenditure Details record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
						
			mav.setViewName("redirect:/mst/projectExpenditureDetails");
			return mav;
		}

		
	
	
	
	
	
		
		//ajax call get Project Expenditure Details using projectId
		@RequestMapping(value="/getProjectExpenditureDetailsByProjectId",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectExpenditureDetailsModel> getProjectExpenditureDetailsByProjectId(ProjectExpenditureDetailsModel projectExpenditureDetailsModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			
			//System.out.println("Project Id "+projectExpenditureDetailsModel.getProjectId());
			List<ProjectExpenditureDetailsModel> datalist = new ArrayList<ProjectExpenditureDetailsModel>();
		
			
			datalist = projectExpenditureDetailsService.getExpenditureDetailsByProjectId(projectExpenditureDetailsModel.getNumProjectId());
			request.setAttribute("data1", datalist);	
		return datalist; 
	}
		
		@RequestMapping(value="/getExpenditureByDate", method=RequestMethod.POST)
		public @ResponseBody  String  getExpenditureByDate(ProjectExpenditureDetailsModel projectExpenditureDetailsModel ,HttpServletRequest request){
		
		Date startDate = null;
		String strStartDate = projectExpenditureDetailsModel.getStartDate();
		//System.out.println("strStartDate"+strStartDate);
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startDate = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dashboardService.getExpenditure(startDate);	
		}	
		
		
		@RequestMapping(value="/getExpenditureByDateGraph", method=RequestMethod.POST)
		public @ResponseBody  JSONArray  getExpenditureByDateGraph(ProjectExpenditureDetailsModel projectExpenditureDetailsModel ,HttpServletRequest request){
			Date startDate = null;
			String strStartDate = projectExpenditureDetailsModel.getStartDate();
			//System.out.println(strStartDate);
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startDate = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			return dashboardService.getExpenditureByGroup(startDate);
		}
		
		//Table to fetch group name, project name, and pyment received in that project
				@RequestMapping(value="/getExpenditureByGroupByProject", method=RequestMethod.POST)
				public @ResponseBody List<DashboardModel> getExpenditureByGroupByProject(DashboardModel dashboardModel ,HttpServletRequest request){
					List<DashboardModel> expenditureList = dashboardService.getExpenditureGroupwiseProjectwise();
					request.setAttribute("expenditureList", expenditureList);
				//System.out.print("expenditureList"+expenditureList);
					return expenditureList;
			
					
				}
				
}
