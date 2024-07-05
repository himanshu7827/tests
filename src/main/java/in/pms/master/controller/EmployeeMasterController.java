package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.domain.Role;
import in.pms.login.service.RoleService;
import in.pms.master.dao.DesignationMasterDao;
import in.pms.master.dao.EmpTypeMasterDao;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.OrganisationMasterDao;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.OrganisationMasterDomain;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.EmpTypeMasterModel;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.model.PostTrackerMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.service.DesignationMasterService;
import in.pms.master.service.EmpTypeMasterService;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.GlobalService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.PostTrackerMasterService;
import in.pms.master.validator.EmployeeRoleMappingValidator;
import in.pms.transaction.model.DashboardModel;
import in.pms.transaction.model.MiscDataModel;
import in.pms.transaction.model.MonthlyProgressDetailsModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.DashboardService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class EmployeeMasterController {
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	@Autowired
	EmpTypeMasterService empTypeMasterService;
	@Autowired
	OrganisationMasterService organisationMasterService;
	@Autowired
	GroupMasterService groupMasterService;
	@Autowired
	public GlobalService globalService;
	@Autowired
	RoleService roleService;
	@Autowired
	DesignationMasterService designationMasterService;
	@Autowired
	PostTrackerMasterService postTrackerMasterService;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	@Qualifier("EmployeeMasterValidator")
	Validator validator;
	
	
	@Autowired
	EmpTypeMasterDao empTypeMasterDao;
	
	@Autowired
	OrganisationMasterDao organisationMasterDao;
	
	@Autowired
	GroupMasterDao groupMasterDao;
	@Autowired
	DesignationMasterDao designationMasterDao;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	
	@Autowired
	DashboardService dashboardService;
	
	@RequestMapping("/employeeMaster")
	public String getAllEmployeeMaster(HttpServletRequest request, EmployeeMasterModel employeeMasterModel,Model model){		
		List<EmpTypeMasterModel>list =empTypeMasterService.getAllActiveEmpTypeMasterDomain();
		request.setAttribute("List", list);	
		List<OrganisationMasterModel>list1=organisationMasterService.getAllActiveOrganisationMasterDomain();
		request.setAttribute("List1", list1);
		List<Role>list3=roleService.getAllActiveRoleDomain();
		request.setAttribute("List3", list3);	
		List<DesignationMasterModel>list4=designationMasterService.getAllActiveDesignationMasterDomain();
		request.setAttribute("List4", list4);
		List<PostTrackerMasterModel>list5=postTrackerMasterService.getAllActivePostTrackerMasterDomain();
		request.setAttribute("List5", list5);
		model.addAttribute(employeeMasterModel);
		if(null!=request.getParameter("encEmpId")){
			request.setAttribute("profileFlag", 1);
			String encEmpId=request.getParameter("encEmpId");
			String encryptedId = encryptionService.dcrypt(""+encEmpId);
			long empId=Long.parseLong(encryptedId);
			employeeMasterModel=employeeMasterService.getEmployeeMasterDomainByIdForUpdate(empId);
			List<GroupMasterDomain> groupList = groupMasterService.getGroupDomainById(employeeMasterModel.getOrganisationId());
			employeeMasterModel.setUpdateFlag(1);
			request.setAttribute("groupList", groupList);
			model.addAttribute(employeeMasterModel);
		}
		else{
			request.setAttribute("profileFlag", 0);
			
		}
		return "employeeMaster";
	}
	
	
	@RequestMapping("/UpdateEmployeeMaster")
	public String updateAllEmployeeMaster(HttpServletRequest request, EmployeeMasterModel employeeMasterModel,Model model){		
		model.addAttribute(employeeMasterModel);
		if(null!=request.getParameter("encEmpId")){
			request.setAttribute("profileFlag", 1);
			String encEmpId=request.getParameter("encEmpId");
			String encryptedId = encryptionService.dcrypt(""+encEmpId);
			long empId=Long.parseLong(encryptedId);
			employeeMasterModel=employeeMasterService.getEmployeeMasterDomainByIdForUpdate(empId);
			
			if(employeeMasterModel.getEmployeeTypeId()!=0){				
				EmpTypeMasterDomain empTypeMasterDomain = empTypeMasterDao.getEmpTypeMasterById(employeeMasterModel.getEmployeeTypeId());
				request.setAttribute("List", empTypeMasterDomain);	
			}
			
			if(employeeMasterModel.getOrganisationId()!=0){
			OrganisationMasterDomain organisationMasterDomain= organisationMasterDao.getOrganisationMasterById(employeeMasterModel.getOrganisationId());		
			request.setAttribute("List1", organisationMasterDomain);
			}
			
			if(employeeMasterModel.getDesignation()!=0){
			DesignationMasterDomain list4 = designationMasterDao.getDesignationMasterById(employeeMasterModel.getDesignation());
			request.setAttribute("List4", list4);
			}
			
			if(employeeMasterModel.getGroupId()>0){
			GroupMasterDomain groupList =  groupMasterDao.getGroupMasterDomainById(employeeMasterModel.getGroupId());
			request.setAttribute("groupList", groupList);
			}
			employeeMasterModel.setUpdateFlag(1);

			model.addAttribute(employeeMasterModel);
		}
		else{
			request.setAttribute("profileFlag", 0);
			
		}
		request.setAttribute("employeeMasterModel", employeeMasterModel);
		return "updateEmployeeMaster";
	}
	
	@RequestMapping(value="/GroupData",method=RequestMethod.POST)
	public @ResponseBody List<GroupMasterModel> getgroups(@RequestParam("organisation") long orgId, HttpServletRequest request){
		List<GroupMasterModel> groupData = groupMasterService.getAllActiveGrpMasterDomain(orgId);
		return groupData;
	}
	
	@RequestMapping(value="/EmployeeData",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel getdata(@RequestParam("searchEmployeeId") long empId, HttpServletRequest request){
		EmployeeMasterModel data = employeeMasterService.getEmployeeDetails(empId);
		return data;
	}
	
	@RequestMapping(value="/EmployeeDataByEmail",method=RequestMethod.POST)
	public @ResponseBody boolean getemaildata(@RequestParam("searchEmployeeId") long empId,@RequestParam("officeEmail") String email, HttpServletRequest request){
		boolean data = employeeMasterService.getEmployeeEmail(empId,email);
		return data;
	}
	
	
	@RequestMapping(value="/saveUpdateEmployeeMaster",method=RequestMethod.POST)	
	public ModelAndView saveUpdateEmployeeMaster(HttpServletRequest request, EmployeeMasterModel employeeMasterModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		validator.validate(employeeMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  List<EmpTypeMasterModel>list =empTypeMasterService.getAllActiveEmpTypeMasterDomain();
	  		request.setAttribute("List", list);	
	  		List<OrganisationMasterModel>list1=organisationMasterService.getAllActiveOrganisationMasterDomain();
	  		request.setAttribute("List1", list1);
	  		List<Role>list3=roleService.getAllActiveRoleDomain();
	  		request.setAttribute("List3", list3);	
	  		List<DesignationMasterModel>list4=designationMasterService.getAllActiveDesignationMasterDomain();
	  		request.setAttribute("List4", list4);
	  		List<PostTrackerMasterModel>list5=postTrackerMasterService.getAllActivePostTrackerMasterDomain();
			request.setAttribute("List5", list5);
	    	  mav.setViewName("employeeMaster");
			  return mav;  
	    	  
	      }
	      String strDuplicateCheck = employeeMasterService.checkDuplicateEmployeeEmail(employeeMasterModel);
			if(null == strDuplicateCheck){
		long id = employeeMasterService.saveUpdateEmployeeMaster(employeeMasterModel);
		if(id>0){
			//Added by devesh on 15-03-24 to update group id in default employee master 
			if (globalService.checkDuplicateDefaultRole((int) id)) {
				EmployeeRoleMasterModel defaultEmpRole = employeeRoleMasterService.getDefaultEmployeeRoleByEmpId(id);
				int roleId= (int) defaultEmpRole.getNumRoleId();
				int projectId= defaultEmpRole.getNumProjectId();
				int grpId= 0;
				if(roleId != 6 && roleId != 7 && roleId != 8 && roleId != 9) {
					grpId = (int) employeeMasterModel.getGroupId();
				}
				else {
					grpId = (int) defaultEmpRole.getNumGroupId();
				}
				
				int organizationId= defaultEmpRole.getNumOrganisationId();
				
				globalService.updateDefaultRole((int) id,roleId,projectId,grpId,organizationId);
			}
			//End of condition
			if(employeeMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Employee record saved successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Employee record saved/updated successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}	
			
			
		}	
			}
			else{
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
			
			String previousUrl = request.getHeader("referer");
			if(null != previousUrl){
				if(previousUrl.contains("?encEmpId")){
					mav.setViewName("redirect:/dashboard");
				}else{
					mav.setViewName("redirect:/mst/employeeMaster");
				}
			}else{
				mav.setViewName("redirect:/mst/employeeMaster");
			}
			
			
		return mav;
	}
	
	@RequestMapping(value="/getDesignationData",method=RequestMethod.POST)
	public @ResponseBody DesignationMasterDomain getDesignationData(@RequestParam("designationId") long designationId, HttpServletRequest request){
		DesignationMasterDomain data = designationMasterService.getDesignationMasterDomainById(designationId);
		return data;
	}
	
	@RequestMapping("/EmployeeRoleMapping")
	public String getAllEmpRoleMaster(HttpServletRequest request, EmployeeMasterModel employeeMasterModel){		
		List<EmployeeMasterModel> list = employeeMasterService.getAllActiveEmployeeMasterDomain();
		request.setAttribute("List", list);	
		List<Role>list1=roleService.getAllActiveRoleDomain();
		request.setAttribute("List1", list1);	
		return "EmployeeRoleMapping";
	}
	
	@RequestMapping(value="/EmployeeDatabyempId",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel getemployeedata(@RequestParam("employeeId") long empId, HttpServletRequest request){
		EmployeeMasterModel data = employeeMasterService.getEmployeeDetails(empId);
		return data;
	}
	
	@RequestMapping(value="/saveEmployeeRoleMapping",method=RequestMethod.POST)	
	public ModelAndView saveEmployeeRoleMapping(HttpServletRequest request, EmployeeMasterModel employeeMasterModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new EmployeeRoleMappingValidator().validate(employeeMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
		List<EmployeeMasterModel> list = employeeMasterService.getAllActiveEmployeeMasterDomain();
		request.setAttribute("List", list);	
		List<Role>list1=roleService.getAllActiveRoleDomain();
		request.setAttribute("List1", list1);
		 mav.setViewName("EmployeeRoleMapping");
         return mav;
     }
		long id = employeeMasterService.mergeRoleInEmployeeMaster(employeeMasterModel);
		if(id>0){
			if(employeeMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Employee Role details saved/updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Employee Role details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		
		mav.setViewName("redirect:/mst/EmployeeRoleMapping");
		return mav;
		
	}
	//ajax call get ThirdParty using EmployeeTypeName
	@RequestMapping(value="/getThirdPartyById",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody List<EmployeeMasterModel> getThirdPartyById(@RequestParam("employeeTypeId") long employeeTypeId,EmployeeMasterModel employeeMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			List<EmployeeMasterModel> datalist = new ArrayList<EmployeeMasterModel>();
			//System.out.println("employeeTypeId=== " +employeeTypeId);
			datalist = employeeMasterService.getThirdPartyById(employeeTypeId, employeeMasterModel);
			//System.out.println(datalist);
			request.setAttribute("data", datalist);	
			return datalist; 
	}

	
	@RequestMapping(value="/UpdateEmployeeMasterProfile",method=RequestMethod.POST)	
	public ModelAndView updateEmployeeMasterProfile(HttpServletRequest request, EmployeeMasterModel employeeMasterModel,BindingResult bindingResult, RedirectAttributes redirectAttributes,Model model){		
		ModelAndView mav = new ModelAndView();
		employeeMasterModel.setUpdateFlag(1);
		validator.validate(employeeMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  request.setAttribute("profileFlag", 1);
				
				employeeMasterModel=employeeMasterService.getEmployeeMasterDomainByIdForUpdate(employeeMasterModel.getNumId());
				
				if(employeeMasterModel.getEmployeeTypeId()!=0){				
					EmpTypeMasterDomain empTypeMasterDomain = empTypeMasterDao.getEmpTypeMasterById(employeeMasterModel.getEmployeeTypeId());
					request.setAttribute("List", empTypeMasterDomain);	
				}
				
				if(employeeMasterModel.getOrganisationId()!=0){
				OrganisationMasterDomain organisationMasterDomain= organisationMasterDao.getOrganisationMasterById(employeeMasterModel.getOrganisationId());		
				request.setAttribute("List1", organisationMasterDomain);
				}
				
				if(employeeMasterModel.getDesignation()!=0){
				DesignationMasterDomain list4 = designationMasterDao.getDesignationMasterById(employeeMasterModel.getDesignation());
				request.setAttribute("List4", list4);
				}
				
				if(employeeMasterModel.getGroupId()>0){
				GroupMasterDomain groupList =  groupMasterDao.getGroupMasterDomainById(employeeMasterModel.getGroupId());
				request.setAttribute("groupList", groupList);
				}
				employeeMasterModel.setUpdateFlag(1);

				request.setAttribute("employeeMasterModel", employeeMasterModel);
				
	    	  mav.setViewName("updateEmployeeMaster");
				model.addAttribute(employeeMasterModel);

			  return mav;    
	    	  
	      }
	      String strDuplicateCheck = employeeMasterService.checkDuplicateEmployeeEmail(employeeMasterModel);
			if(null == strDuplicateCheck){
		long id = employeeMasterService.saveUpdateEmployeeMasterProfile(employeeMasterModel);
		if(id>0){
		
				redirectAttributes.addFlashAttribute("message",  "Employee record saved/updated successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
		}
	
					mav.setViewName("redirect:/dashboard");
				
			
			
			
		return mav;
	}
			return mav;
	
}
	
	@RequestMapping(value="/getEmployeesByGroupByEmployementType", method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getEmployeesByGroupByEmployementType(EmployeeMasterModel employeeMasterModel ,HttpServletRequest request){
		List<EmployeeMasterModel> employeeList = employeeMasterService.getEmployeesByGroupByEmployementType();
		request.setAttribute("employeeList", employeeList);
		//System.out.print("income"+incomeList);
		return employeeList;		
	}
	
	@RequestMapping(value="/employeeBasicDetails", method=RequestMethod.POST)
	public @ResponseBody String employeeBasicDetails(EmployeeMasterModel employeeMasterModel ,HttpServletRequest request){
		JSONObject object  = employeeMasterService.getEmployeeBasicDetails(employeeMasterModel.getEncEmployeeId());		
		return object.toString();		
	}
	
	@RequestMapping(value="/getEmpbyDesignationForGroup", method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getEmpbyDesignationForGroup(@RequestParam("group")String group,@RequestParam("designation")String designation,HttpServletRequest request){
		String groupName=group.trim();
		String strDesignation=designation.trim();
		List<EmployeeMasterModel> list= employeeMasterService.getEmpbyDesignationForGroup(groupName,strDesignation);
		return list;		
	}
	
	@RequestMapping(value="importEmployeeDetail",method= RequestMethod.POST)
	@ResponseBody
	public List<String> saveEmployeeDetail(EmployeeMasterModel employeeMasterModel,HttpServletRequest request){	
		List<String> result = employeeMasterService.saveEmployeeDetails(employeeMasterModel);
		return result; 		
	}
	
	@RequestMapping("/ContractEndRecords")
	public String getContractDetail(HttpServletRequest request, EmployeeMasterModel employeeMasterModel){
		
		Calendar calendar = Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH)+1;
		String currentMonth=Integer.toString(month);
		String currentYear=Integer.toString(calendar.get(Calendar.YEAR));
		String date="";
		if(month<10){			
		date="0"+currentMonth+"/"+currentYear;
		}
		else
		{
			date=currentMonth+"/"+currentYear;	
		}
		List<EmployeeMasterModel> list = employeeMasterService.getContractDetails(date) ;
		
        request.setAttribute("data", list);
		request.setAttribute("date", date);
		    return "ContractEndRecords";
	}
	
	@RequestMapping(value="/ContractEndRecordsByMonth",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getData(@RequestParam("date") String date,HttpServletRequest request){
		
		List<EmployeeMasterModel> list = employeeMasterService.getContractDetails(date) ;				
		return list;
	}
	
	@RequestMapping(value="/releaseEmployee",method=RequestMethod.GET)
	public String releaseEmployee(HttpServletRequest request,EmployeeMasterModel employeeMasterModel){		
		employeeMasterService.releaseEmployeeAuthorityCheck();			
		return "releaseEmployee";
	}
	
	@RequestMapping(value="/searchEmployeeData",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel searchEmployeeData(HttpServletRequest request,@ModelAttribute EmployeeMasterModel employeeMasterModel){		
		EmployeeMasterModel employeeData= employeeMasterService.getEmployeeBasicDetail(employeeMasterModel) ;				
		
		if(null != employeeData){
			List<EmployeeRoleMasterModel> employeeRoleList = employeeRoleMasterService.getActiveEmployeeRoleByEmpId(employeeData.getNumId());
			employeeData.setEmployeeRoleList(employeeRoleList);
		}		
		return employeeData;
	}
	
	
	@RequestMapping(value="releaseEmployee",method=RequestMethod.POST)
	public @ResponseBody boolean  releaseEmployeePost(HttpServletRequest request, @ModelAttribute EmployeeMasterModel employeeMasterModel){
		return employeeMasterService.releaseEmployee(employeeMasterModel);			
	}
	
	@RequestMapping(value="/ViewEmployeeONGroupID",method=RequestMethod.POST)	
	@ResponseBody
	public List<EmployeeMasterModel> ViewProjectDetails(@ModelAttribute("projectMasterForm") EmployeeMasterModel employeeMasterModel ,HttpServletRequest request,RedirectAttributes redirectAttributes){		
			Long encGroupId = employeeMasterModel.getGroupId();	
		
			List<EmployeeMasterModel> list = employeeMasterService.viewEmployeeOnGroupID(encGroupId);
			
		return list;
		
	}
	
	@RequestMapping(value="/loadEmployeeDetailsCount",method=RequestMethod.POST)
	public @ResponseBody int loadEmployeeDetailsCount(MonthlyProgressDetailsModel model){		
		int count=0;
		List<EmployeeMasterModel> details=employeeMasterService.loadEmployeeDetails();
		if(details.size()>0){
			count=details.size();
		}
		else{
			return count;
		}
		return count;
	}
	
	@RequestMapping(value="/getEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getEmployeeDetails(MonthlyProgressDetailsModel model){		
		List<EmployeeMasterModel> details=employeeMasterService.loadEmployeeDetails();
		return details;
	}
	
	@RequestMapping(value="/getNewJoinedEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getNewJoinedEmployeeDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.loadJoinedEmployeeDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		return details;
	}
	
	@RequestMapping(value="/getResignedEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getResignedEmployeeDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.loadResignedEmployeeDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		return details;
	}
	//added by khushboo
	@RequestMapping(value="/getResignedEmployeeDetailsCustomized",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel getResignedEmployeeDetailsCustomized(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		EmployeeMasterModel empMaster = new EmployeeMasterModel();
		List<EmployeeMasterModel> details=employeeMasterService.loadResignedEmployeeDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		List<EmployeeMasterModel> detailsCustomized=employeeMasterService.loadResignedEmployeeCustomizedDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		empMaster.setEmployeeMasterList(details);
		empMaster.setEmployeeMasterCustomizedList(detailsCustomized);
		return empMaster;
	}
	
	
	@RequestMapping(value="/getNewJoinedEmployeeByGroupDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getNewJoinedEmployeeByGroupDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.getNewJoinedEmployeeByGroupDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate(),projectMastermodel.getEncGroupId());
		return details;
	}
	
	@RequestMapping(value="/getResignedEmployeeByGroupDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getResignedEmployeeByGroupDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.getResignedEmployeeByGroupDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate(),projectMastermodel.getEncGroupId());
		return details;
	}
	
	@RequestMapping(value="/getAllDetails",method=RequestMethod.POST)
	public @ResponseBody Map<String, MiscDataModel> getAllDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		Map<String, MiscDataModel> getallDetails = employeeMasterService.getDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		return getallDetails;
	}
	

	@RequestMapping(value="/searchEmployeeDataByName",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> searchEmployeeDataByName(HttpServletRequest request,@ModelAttribute EmployeeMasterModel employeeMasterModel){		
		List<EmployeeMasterModel> employeeData= employeeMasterService.searchEmpDataByName(employeeMasterModel) ;				
	
		return employeeData;
	}
	
	@RequestMapping(value="/relievedEmployee",method=RequestMethod.GET)
	public String relievedEmployee(HttpServletRequest request,EmployeeMasterModel employeeMasterModel){		
		employeeMasterService.releaseEmployeeAuthorityCheck();			
		return "relievedEmployee";
	}
	
	@RequestMapping(value="/searchRelievedEmployeeData",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel searchRelievedEmployeeData(HttpServletRequest request,@ModelAttribute EmployeeMasterModel employeeMasterModel){		
		EmployeeMasterModel employeeData= employeeMasterService.getRelievedEmployeeBasicDetail(employeeMasterModel) ;				
		
		if(null != employeeData){
			List<EmployeeRoleMasterModel> employeeRoleList = employeeRoleMasterService.getActiveEmployeeRoleByEmpIdNew(employeeData.getNumId());
			employeeData.setEmployeeRoleList(employeeRoleList);
		}		
		return employeeData;
	}
	
	@RequestMapping(value="/searchRelievedEmployeeDataByName",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> searchRelievedEmployeeDataByName(HttpServletRequest request,@ModelAttribute EmployeeMasterModel employeeMasterModel){		
		List<EmployeeMasterModel> employeeData= employeeMasterService.searchRelievedEmpDataByName(employeeMasterModel) ;				
	
		return employeeData;
	}
	
	@RequestMapping(value="/EmployeeDataByEmailId",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel EmployeeDataByEmailId(@RequestParam("officeEmail") String email, HttpServletRequest request){
		EmployeeMasterModel data = employeeMasterService.getEmployeeDetailsByEmailId(email);
		return data;
	}
	
	@RequestMapping(value="/loadUnderUtiEmployeeCount",method=RequestMethod.POST)
	public @ResponseBody int loadUnderUtiEmployeeCount(MonthlyProgressDetailsModel model){		
		int count=0;
		List<EmployeeMasterModel> details=employeeMasterService.loadUnderUtilizationEmployees();
		if(details.size()>0){
			count=details.size();
		}
		else{
			return count;
		}
		return count;
	}
	
	@RequestMapping(value="/getUnderUtiEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getUnderUtiEmployeeDetails(MonthlyProgressDetailsModel model){		
		List<EmployeeMasterModel> details=employeeMasterService.loadUnderUtilizationEmployees();
		return details;
	}
	
	@RequestMapping(value="/getEmployeesByEmploymentType",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getEmployeesByEmploymentType(EmployeeMasterModel model){		
		List<EmployeeMasterModel> details=employeeMasterService.getEmployeesByEmploymentType(model.getEmployeeTypeName());
		return details;
	}
	
	@RequestMapping(value="/employeeDetailsWithInvolvementsGroupWise",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> employeeDetailsWithInvolvementsGroupWise(EmployeeMasterModel model){		
		List<EmployeeMasterModel> details=employeeMasterService.employeeDetailsWithInvolvementsGroupWise(model.getGroupName());
		return details;
	}
	
	@RequestMapping(value="/employeeDetailsByCategory",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> employeeDetailsByCategory(EmployeeMasterModel model){		
		List<EmployeeMasterModel> details=employeeMasterService.employeeDetailsByCategory(model.getCategory());
		return details;
	}
	
	@RequestMapping(value="/getEmployeeCountByEmployementType", method=RequestMethod.POST)
	public @ResponseBody  JSONArray  getEmployeeCountByEmployementType(@RequestParam("groupName") String groupName,ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
		JSONArray employmentTypeWiseCount = employeeMasterService.getEmployeeCountByGroupwise(groupName);
		return employmentTypeWiseCount;
	}
	
	@RequestMapping(value="/getGroupGenderWiseEmployeeCount", method=RequestMethod.POST)
	public @ResponseBody  JSONArray  getGroupGenderWiseEmployeeCount(@RequestParam("groupName") String groupName,ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
		JSONArray  empgenderdata = dashboardService.getGroupGenderWiseEmployeeCount(groupName);
		return empgenderdata;
	}
	
	@RequestMapping(value="/getDesignationWiseEmployee", method=RequestMethod.POST)
	public @ResponseBody  List<DesignationMasterModel>  getDesignationWiseEmployee(@RequestParam("groupName") String groupName,ProjectPaymentReceivedModel projectPaymentReceivedModel ,HttpServletRequest request){
		List<DesignationMasterModel> employeeCountByGroupandDesignation= employeeMasterService.getEmployeeCountBySingleGroupandDesignation(groupName);
		return employeeCountByGroupandDesignation;
	}
	
	@RequestMapping(value="/getNewRejoinEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getNewRejoinEmployeeDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.loadRejoinRelievedEmployeeDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate(),projectMastermodel.getStatus());
		return details;
	}
	
	@RequestMapping(value="/getPreviousHistoryOfEmp",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeMasterModel> getPreviousHistoryOfEmp(EmployeeMasterModel model ,HttpServletRequest request){		
		List<EmployeeMasterModel> details=employeeMasterService.getPreviousHistoryOfEmp(model.getEmployeeName(), model.getDateOfBirth());
		return details;
	}
	
	@RequestMapping(value="/getCountRejoinEmployeeDetails",method=RequestMethod.POST)
	public @ResponseBody EmployeeMasterModel getCountRejoinEmployeeDetails(ProjectMasterModel projectMastermodel ,HttpServletRequest request){		
		EmployeeMasterModel details=employeeMasterService.getCountRejoinEmployeeDetails(projectMastermodel.getStartDate(), projectMastermodel.getEndDate());
		return details;
	}
	
	
	
}
