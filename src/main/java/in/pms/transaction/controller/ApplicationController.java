package in.pms.transaction.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.model.BusinessTypeModel;
import in.pms.master.model.ClientMasterModel;
import in.pms.master.model.ContactPersonMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.model.ProjectCategoryModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectTypeModel;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.master.service.BusinessTypeService;
import in.pms.master.service.ClientContactPersonMasterService;
import in.pms.master.service.ClientMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.EndUserMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectTypeService;
import in.pms.master.service.ProposalMasterService;
import in.pms.master.service.ThrustAreaMasterService;
import in.pms.transaction.model.ApplicationModel;
import in.pms.transaction.service.ApplicationService;

@Controller
public class ApplicationController {
	
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
	ApplicationService applicationService;
	
	@Autowired
	ClientMasterService clientMasterService;
	
	@Autowired
	ThrustAreaMasterService thrustAreaMasterService;
	
	@Autowired
	ClientContactPersonMasterService clientContactPersonMasterService;
	
	@Autowired
	ProposalMasterService proposalMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	EndUserMasterService endUserMasterService;
	
	
	@RequestMapping("applicationBasicDetails")
	private ModelAndView applicationBasicDetails(ApplicationModel applicationModel,HttpServletRequest request,Model map){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		ModelAndView modelAndView = new ModelAndView();
		List<BusinessTypeModel> businessTypeModelList = businessTypeService.getActiveBusinessType();
		request.setAttribute("businessTypeModelList", businessTypeModelList);
		
		List<ProjectTypeModel> projectTypeModelList = projectTypeService.getActiveProjectType();
		request.setAttribute("projectTypeModelList", projectTypeModelList);
		
		List<ProjectCategoryModel> projectCategoryModelList =projectCategoryService.getActiveProjectCategory();
		request.setAttribute("projectCategoryModelList", projectCategoryModelList);
		
		List<OrganisationMasterModel> organisationList = organisationMasterService.getAllActiveOrganisationMasterDomain();
		request.setAttribute("organisationList", organisationList);	
			
		
		List<ClientMasterModel> listClient = clientMasterService.getAllActiveClientMasterDomain();
		request.setAttribute("clientlist", listClient);
        
		List<ClientMasterModel> listEndUser = endUserMasterService.getAllActiveEndUserMasterDomain();
		request.setAttribute("endUserlist", listEndUser);
        
		
		List<ThrustAreaMasterForm> listthrust = thrustAreaMasterService.getActiveThrustAreaData();
		request.setAttribute("thrustlist", listthrust);
		
			List<GroupMasterModel> listGroups = groupMasterService.getAllActiveGrpMasterDomain(selectedEmployeeRoleMasterModel.getNumOrganisationId());
		/*List<GroupMasterModel> listGroups = groupMasterService.getAllActiveGroupMasterDomainByUserInfo();*/
			request.setAttribute("grouplist", listGroups);
		
		Map md = map.asMap();
		long applicationId=0;
	      if(md.get("applicationId")!=null)
		        applicationId=Integer.parseInt(md.get("applicationId").toString());
	      else if(applicationModel.getEncNumId()!=null){
	  		String appID= encryptionService.dcrypt(applicationModel.getEncNumId());
	    	  applicationId=Long.parseLong(appID);
	      }
		if(applicationId>0 ){
			ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(applicationId);
			ApplicationModel model=applicationService.getApplicationById(applicationId);
			/*------ Check group proposal year check  [12-04-2024]----*/
			long proposalYearCheck = groupMasterService.getGroupMasterDomainById(model.getGroupId()).getProposalYearCheck();
			boolean proposalYear = false;
			if (proposalYearCheck == 1) {
			    if (model.isDateOfSubEqualsCurrentYear()
			        || selectedEmployeeRoleMasterModel.getNumRoleId() == 5
			        || proposalMasterModel.getStatus().equals("SAD")) {
			    	proposalYear = true;
			    }else {
			    	proposalYear = false;
			    }
			}else {
				proposalYear = true;
			}
			/*----- End of Check group proposal year check  [12-04-2024]----*/
			if(proposalYear) {
				//Added by devesh on 25/8/23 for setting groupname in the jsp
				if (proposalMasterModel.getGroupId() != 0) {
			        // Assuming getGroupNames returns the new group name based on groupId
					model.setGroupId(proposalMasterModel.getGroupId());
			    }
				//System.out.println("Proposal ID: " + proposalMasterModel.getProposalTitle() +", Group ID: " + proposalMasterModel.getGroupId() +", Group Name: " + proposalMasterModel.getGroupName());
				//End of setting groupname in the jsp
				//Added by devesh on 03/10/23 for setting proposal cost in the jsp
				if (proposalMasterModel.getProposalCost() != 0) {
			        // Assuming getGroupNames returns the new group name based on groupId
					model.setProposalCost(proposalMasterModel.getProposalCost());
			    }
				//End of setting proposal cost in the jsp
				request.setAttribute("applicationDetails", model);
				modelAndView.addObject("applicationModel",model);
				
				List<GroupMasterModel> listGroup = groupMasterService.getAllActiveGroupMasterDomainByUserInfo();
				request.setAttribute("grouplist", listGroup);
				List<ContactPersonMasterModel> listClientContact = clientContactPersonMasterService.getContactPersonByClientId(model.getClientId());
				request.setAttribute("clientContactlist", listClientContact);
				
				//ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(applicationId);
				if(null != proposalMasterModel && proposalMasterModel.getStatus()!=null && proposalMasterModel.getStatus().equals("SUB")){
					//Proposal Already Submitted
					//Make a copy of Proposal
					
					applicationService.createApplicationCopy(applicationId);
	
				}
			}
			else{
				 modelAndView.setViewName("accessDenied");
				 return modelAndView;
			}
		}
		modelAndView.setViewName("applicationBasicDetails");
		return modelAndView;
	}
	
	@RequestMapping(value="/saveUpdateApplicationBasicDetails",method=RequestMethod.POST,produces="application/json")
	private @ResponseBody ApplicationModel saveApplicationBasicDetails(@ModelAttribute ApplicationModel applicationModel,HttpServletRequest request){
		//System.out.println("applicationModel");
		//int businessTypeId  = Integer.parseInt(request.getParameter("businessTypeId"));
		//int projectTypeId  = Integer.parseInt(request.getParameter("projectTypeId"));
		
		//System.out.println("projectTypeId"+businessTypeId+"projectTypeId"+projectTypeId);
		if(applicationModel.getThrustAreaId().size()==0){
			List<Long> arl = new ArrayList<Long>();
			arl.add(0L);
			applicationModel.setThrustAreaId(arl);
		}
		if(applicationModel.getContactPersonId().size()==0){
			List<Long> arl = new ArrayList<Long>();
			arl.add(0L);
			applicationModel.setContactPersonId(arl);
		}
		
		applicationService.saveUpdateApplication(applicationModel);		
		request.getSession().setAttribute("applicationId", applicationModel.getNumId());
		
		return applicationModel;
		
		
	}
	
	
	@RequestMapping(value="/projectDetailsByGroupName",method=RequestMethod.POST)
	private @ResponseBody List<ProjectMasterForm> getApplicationByGroupName(HttpServletRequest request){
		String groupName = request.getParameter("groupName");
		return applicationService.getApplicationByGroupName(groupName);			
	}
	
	@RequestMapping("/ViewApplicationDetails")
	public String getAllApplicationDetails(HttpServletRequest request, ApplicationModel applicationModel){	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedRole.getNumOrganisationId();		
		long roleId=selectedRole.getNumRoleId();		   
	
		/*if(roleId == 0){
			request.setAttribute("data", null);		
			return "ApplicationList";
		}*/
		request.setAttribute("roleId", roleId); //Added by devesh on 13-02-24 to get roleId of login user
		
		if(roleId == 6){					     
		    List<GroupMasterModel> groupnames =groupMasterService.getAllActiveGrpMasterDomain(assignedOrganisation);
			request.setAttribute("groupnames", groupnames);
			return "EDApplicationList";
		}else if(roleId == 9){
			 List<GroupMasterModel> groupnames =groupMasterService.getAllActiveGrpMasterDomain(assignedOrganisation);
				request.setAttribute("groupnames", groupnames);
				return "PMOApplicationList";
		}else{
			List<ApplicationModel> list = applicationService.getAllApplicaionData();
			request.setAttribute("data", list);		
			return "ApplicationList";
	    }
		
	   /* if(roleId==6 || assignedGroupId ==8){
	    	 if(null != assignedOrganisation && !assignedOrganisation.equals("")){
					Long orgId = Long.valueOf(assignedOrganisation).longValue();		     
				    List<GroupMasterModel> groupnames =groupMasterService.getAllActiveGrpMasterDomain(orgId);
					request.setAttribute("groupnames", groupnames);
				
	    	 if(null != groupnames && groupnames.size()>0){
					long groupId = groupnames.get(0).getNumId();
					List<ApplicationModel> datalist  = applicationService.getProposalDetailByGruopId(groupId);
					request.setAttribute("activeGroupData", datalist);
				}	
	    	 }
	    	 if(roleId==6)
			return "EDApplicationList";
	    	 else
	    	return "PMOApplicationList";*/
	   
	}
	
	@RequestMapping("/convertAmountToINR")
	public @ResponseBody String convertAmountToINR(HttpServletRequest request){	
		if(request.getParameter("amount")!=null){
		String amount= request.getParameter("amount").trim();
		Double num=0.0;
		 boolean numeric = true;
	        try {
	        	if(!amount.equalsIgnoreCase("nan"))
	             num = Double.parseDouble(amount);
	        	else
	        	numeric=false;
	        } catch (NumberFormatException e) {
	            numeric = false;
	        }
	        if(numeric){
				return CurrencyUtils.convertToINR(amount);
			}else{
				return "0";
			}
		}
		return "0";
	}
	
	@RequestMapping("/getEncryptedKey")
	public @ResponseBody String getEncryptedKey(HttpServletRequest request){	
		String id= request.getParameter("id").trim();			
		return encryptionService.encrypt(id);	
	}
	
	@RequestMapping("viewApplicationStatus")
	@ResponseBody
	public ApplicationModel viewApplicationStatus(long applicationId){
		return applicationService.getApplicationById(applicationId);
	}
	@RequestMapping(value="/getProposalDetailByGruopId",method=RequestMethod.POST)
	public @ResponseBody List<ApplicationModel> getProposalDetailByGruopId(@RequestParam("encGroupId") String encGroupId,ApplicationModel applicationModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		String strGroupId= encryptionService.dcrypt(encGroupId);
		long groupId= Long.parseLong(strGroupId);
		List<ApplicationModel> datalist  = applicationService.getProposalDetailByGruopId(groupId);
		request.setAttribute("activeGroupData", datalist);
			
		return datalist; 
	}
	
	//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero
		@RequestMapping(value="/getProposalDetailByGruopIdnew",method=RequestMethod.POST)
		public @ResponseBody List<Object[]> getProposalDetailByGruopIdnew(@RequestParam("encGroupId") String encGroupId,ApplicationModel applicationModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			long groupId;
		    try {
		        groupId = Long.parseLong(encGroupId);
		    } catch (NumberFormatException e) {
		        String strGroupId = encryptionService.dcrypt(encGroupId);
		        groupId = Long.parseLong(strGroupId);
		    }
			/*List<ApplicationModel> datalist  = applicationService.getProposalDetailByGruopId(groupId);*/
			List<Object[]> datalist  = applicationService.getProposalDetailByGruopIdnew(groupId);
			request.setAttribute("activeGroupData", datalist);
			
			return datalist; 
		}
		//End of Proposal Detail Controller
		
		//Added by devesh on 25/8/23 to get GC and PL proposal list from SQL query
		@RequestMapping(value="/getProposalDetailListforGC",method=RequestMethod.POST)
	    @ResponseBody
	    public List<Object[]> getApplicationDataList() {
	        return applicationService.getAllApplicationDataList();
	    }
	    //End of proposal list
	
	@RequestMapping(value="/deleteApplication",method=RequestMethod.POST)
	public @ResponseBody int deleteApplication(@RequestParam("applicationId") String applicationId,HttpServletRequest request){
		String Id= encryptionService.dcrypt(applicationId);
		long appId= Long.parseLong(Id);
		return applicationService.deleteApplication(appId);
	}
	
	
}
