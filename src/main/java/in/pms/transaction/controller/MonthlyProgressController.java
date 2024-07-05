package in.pms.transaction.controller;

import in.pms.global.model.WorkflowModel;
import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.domain.CategoryMaster;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.MonthlyProgressDetailsModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.MonthlyProgressService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class MonthlyProgressController {
	
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
	MonthlyProgressService monthlyProgressService;
	@Autowired
	ProjectCategoryService projectCategoryService;
	
	
	
	@Autowired
	EncryptionService encryptionService;
	
	
	@RequestMapping(value="/getMonthlyProgress",method=RequestMethod.POST)
	public @ResponseBody WorkflowModel getMonthlyProgress(MonthlyProgressModel monthlyProgressModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		int month= monthlyProgressModel.getMonth();
		int year = monthlyProgressModel.getYear();		
		if(month==0){
			Calendar calendar= GregorianCalendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			year= calendar.get(Calendar.YEAR);
			if(month==0){
				month= 12;
				year=year-1;
			}			
		}
		String ecrId=null;
		WorkflowModel workflowModel=new WorkflowModel();
		if(null != monthlyProgressModel.getEncProjectId()){			
			String strProjectId = encryptionService.dcrypt(monthlyProgressModel.getEncProjectId());
			long projectId = Long.parseLong(strProjectId);
			
			ProjectMasterForm projectMasterForm =projectMasterService.getProjectDetailByProjectId(projectId);
			String strGroupId=encryptionService.dcrypt(projectMasterForm.getEncGroupId());
			Long groupId=Long.parseLong(strGroupId);
			ecrId=monthlyProgressService.MonthlyProgress(month, year, projectId, groupId);
			if(!ecrId.equals("")){
				workflowModel.setEncMonthlyProgressId(ecrId);
				String parentId = encryptionService.dcrypt(ecrId);
				List<CategoryMasterModel> categoryModel = monthlyProgressService.getCategoryByParentId(parentId);
				workflowModel.setCategoryModel(categoryModel);
			}
		
			String encPageId = encryptionService.encrypt("1");
			String encWorkflowId=encryptionService.encrypt("1");
			workflowModel.setEncPageId(encPageId);
			workflowModel.setEncWorkflowId(encWorkflowId);
		}
		return workflowModel;
	}
	@RequestMapping(value="/getMonthlyProgressByGroup",method=RequestMethod.POST)
	public @ResponseBody WorkflowModel getMonthlyProgressByGroup(MonthlyProgressModel monthlyProgressModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		int month= monthlyProgressModel.getMonth();
		int year = monthlyProgressModel.getYear();
		if(month==0){
			Calendar calendar= GregorianCalendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			year= calendar.get(Calendar.YEAR);
			if(month==0){
				month= 12;
				year=year-1;
			}
			
		}
		String EcrId=null;
		WorkflowModel workflowModel=new WorkflowModel();
			
			/*String strProjectId = encryptionService.dcrypt(monthlyProgressModel.getEncProjectId());
			long projectId = Long.parseLong(strProjectId);
			ProjectMasterForm projectMasterForm =projectMasterService.getProjectDetailByProjectId(projectId);	
			String strGroupId=encryptionService.dcrypt(projectMasterForm.getEncGroupId());
			Long groupId=Long.parseLong(strGroupId);*/
			EcrId=monthlyProgressService.MonthlyProgressByGroup(month, year,  monthlyProgressModel.getGroupId());
			workflowModel.setEncMonthlyProgressId(EcrId);
			String encPageId = encryptionService.encrypt("1");
			String encWorkflowId=encryptionService.encrypt("2");
			workflowModel.setEncPageId(encPageId);
			workflowModel.setEncWorkflowId(encWorkflowId);
	
		return workflowModel;
			}
	@RequestMapping(value="/getChildCategoryByParentId",method=RequestMethod.POST)
	public @ResponseBody List<CategoryMasterModel> getChildCategoryByParentId(CategoryMasterModel categoryMasterModel,HttpServletRequest request){
				
		
		List<CategoryMasterModel> categoryMasterList=monthlyProgressService.getChildCategoryByParentId(categoryMasterModel.getParentId(), categoryMasterModel.getEncCategoryId());
		
		return categoryMasterList;			
	
	}
	
	@RequestMapping(value="/monthlyProgressForProjects")
	public String monthlyProgressForProjects(HttpServletRequest request){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		monthlyProgressService.writeProgressReportAuthorityCheck();
		
		List<EmployeeRoleMasterModel> allRolesofEmplyee = userInfo.getEmployeeRoleList();
		Set<EmployeeRoleMasterModel> filteredRoles = new HashSet<EmployeeRoleMasterModel>();
		
		if(null != allRolesofEmplyee && allRolesofEmplyee.size()>0){
			filteredRoles = allRolesofEmplyee.stream()			      
			        .filter(x -> (3 == x.getNumRoleId() || 4== x.getNumRoleId())).collect(Collectors.toSet());		
		}
		
		 
		if(userInfo.getSelectedEmployeeRole()!=null){			
			if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5 || userInfo.getSelectedEmployeeRole().getNumRoleId()==6 || userInfo.getSelectedEmployeeRole().getNumRoleId()==9 ){
				List<ProjectMasterModel> projects = projectMasterService.getAllActiveProjectMasterData();					
				request.setAttribute("projects", projects);
			}else{
				if(filteredRoles.size()>0){
					request.setAttribute("projects", filteredRoles);
				}
			}		
		}else{
			if(filteredRoles.size()>0){
				request.setAttribute("projects", filteredRoles);
			}
		}
		
		return "monthlyProgressForprojects";	
	}
	
	@RequestMapping(value="/activeProgressReportsWithGCCount",method=RequestMethod.POST)
		public @ResponseBody int activeProgressReportsWithGCCount(MonthlyProgressDetailsModel model){		
			return monthlyProgressService.activeProgressReportsWithGCCount(model.getActionId());
		}
	

	@RequestMapping(value="/activeProgressReportsWithGCDetails",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> activeProgressReportsWithGCDetails(MonthlyProgressDetailsModel model){		
		return monthlyProgressService.activeProgressReportsWithGCDetails(model.getActionId());
	}
	
	@RequestMapping(value="/getCategoryURL",method=RequestMethod.POST)
	public @ResponseBody String getCategoryURL(CategoryMasterModel categoryMasterModel,HttpServletRequest request){		
		String catController="";
		String strCatId = encryptionService.dcrypt(categoryMasterModel.getEncCategoryId());
		long categoryId = Long.parseLong(strCatId);
		CategoryMaster detail= projectCategoryService.getProjectCategoryByCatId(categoryId);
		if(detail!=null){
		catController= detail.getStrCategoryController();
		}
		return catController;
	}
	
	@RequestMapping(value="/getMinCategoryId",method=RequestMethod.POST)
	public @ResponseBody String getMinCategoryId(MonthlyProgressModel monthlyProgressModel,HttpServletRequest request){		
		String strCatId="";
		String strProgressId = encryptionService.dcrypt(monthlyProgressModel.getEncProgressDetailsId());
		int progressId = Integer.parseInt(strProgressId);
		String encCategoryId= monthlyProgressService.getMinCategoryByPId(progressId);
		if(!encCategoryId.equals("")){
			strCatId= encCategoryId;
		}
		return strCatId;
	}
	
	@RequestMapping(value="/sendToPMO",method=RequestMethod.POST)
	public @ResponseBody List<String> sendToPMO(MonthlyProgressModel monthlyProgressModel,HttpServletRequest request){		
		return monthlyProgressService.sendToPMO(monthlyProgressModel);		
	}
	
	@RequestMapping(value="/activeProgressReportsbyGCforCurrentMonth",method=RequestMethod.POST)
	public @ResponseBody int activeProgressReportsbyGCCount(MonthlyProgressDetailsModel model){	
				Date date=new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year=calendar.get(Calendar.YEAR);
				int month=calendar.get(Calendar.MONTH) + 1;

		return monthlyProgressService.allActiveProgressReportsbyGCCount(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/activeProgressReportsbyGCforlastMonth",method=RequestMethod.POST)
	public @ResponseBody int activeProgressReportsbyGCforlastMonth(MonthlyProgressDetailsModel model){	
		LocalDate now = LocalDate.now(); 
		LocalDate earlier = now.minusMonths(1);

		//int month=earlier.getMonth();
		int month=earlier.getMonthOfYear();
		int year=earlier.getYear(); 
		 

		return monthlyProgressService.allActiveProgressReportsbyGCCount(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/activeProgressReportsDetailsbyGCforCurrentMonth",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> activeProgressReportsDetailsbyGCforCurrentMonth(MonthlyProgressDetailsModel model){		
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH) + 1;
		return monthlyProgressService.activeProgressReportsDetailsbyGCforCurrentMonth(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/activeProgressReportsDetailsbyGCforLastMonth",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> activeProgressReportsDetailsbyGCforLastMonth(MonthlyProgressDetailsModel model){		
		LocalDate now = LocalDate.now(); 
		LocalDate earlier = now.minusMonths(1);
		int month=earlier.getMonthOfYear();
		int year=earlier.getYear();
		return monthlyProgressService.activeProgressReportsDetailsbyGCforCurrentMonth(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/activeProgressReportsDetails",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> activeProgressReportsDetails(MonthlyProgressDetailsModel model){		
		
		int month=Integer.parseInt(model.getMonth());
		int year=Integer.parseInt(model.getYear());
		return monthlyProgressService.activeProgressReportsDetailsbyGCforCurrentMonth(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/PendingProgressReportsAtPL",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> PendingProgressReportsAtPL(MonthlyProgressDetailsModel model){		
		
		int month=Integer.parseInt(model.getMonth());
		int year=Integer.parseInt(model.getYear());
		return monthlyProgressService.PendingProgressReportsAtPL(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/PendingProgressReportsAtGC",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> PendingProgressReportsAtGC(MonthlyProgressDetailsModel model){		
		
		int month=Integer.parseInt(model.getMonth());
		int year=Integer.parseInt(model.getYear());
		return monthlyProgressService.PendingProgressReportsAtGC(year,month,model.getActionId());
	}
	
	@RequestMapping(value="/revisedReportAtPL",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> revisedReportAtPL(MonthlyProgressDetailsModel model){		
		
		
		return monthlyProgressService.revisedReportAtPL(model.getActionId());
	}
	
	@RequestMapping(value="/pendingProgressReportsAtPL",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> pendingProgressReportsAtPL(MonthlyProgressDetailsModel model){		

		return monthlyProgressService.pendingProgressReportsAtPL();
	}
	
	@RequestMapping(value="/SentForRevisionCount",method=RequestMethod.POST)
	public @ResponseBody int SentForRevisionCount(MonthlyProgressDetailsModel model){		
		return monthlyProgressService.SentForRevisionCount(model.getActionId());
	}
	
	@RequestMapping(value="/PendingAtPLCount",method=RequestMethod.POST)
	public @ResponseBody int PendingAtPLCount(MonthlyProgressDetailsModel model){		
		int count=0;
		List<MonthlyProgressModel> details=monthlyProgressService.pendingProgressReportsAtPL();
		if(details.size()>0){
			count=details.size();
		}
		else{
			return count;
		}
		return count;
	}
	
	@RequestMapping(value="/PendingProgressReportsAtPI",method=RequestMethod.POST)
	public @ResponseBody List<MonthlyProgressModel> PendingProgressReportsAtPI(MonthlyProgressDetailsModel model){		
		
		int month=Integer.parseInt(model.getMonth());
		int year=Integer.parseInt(model.getYear());
		return monthlyProgressService.PendingProgressReportsAtPI(year,month,model.getActionId());
	}
	
}