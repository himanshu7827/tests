package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.service.DesignationMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.transaction.model.ApprovedJobModel;
import in.pms.transaction.model.EmployeeApprovedJobMappingModel;
import in.pms.transaction.model.JobTitleModel;
import in.pms.transaction.service.ApprovedJobService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ApprovedJobController {

	
	@Autowired
	GroupMasterService groupMasterService;	
	@Autowired
	ApprovedJobService approvedJobService;
	
	@Autowired
	DesignationMasterService designationMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	
	
	@RequestMapping(value="/approvedJob")
	public String createArrovedJob(ApprovedJobModel approvedJobModel, HttpServletRequest request){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		if(null != assignedOrganisation && !assignedOrganisation.equals("")){
			Long orgId = Long.valueOf(assignedOrganisation).longValue();
			List<GroupMasterModel> groupList = groupMasterService.getAllActiveGrpMasterDomain(orgId);
			request.setAttribute("groupList", groupList);
		}
		
		List<DesignationMasterModel> designationList = designationMasterService.getAllActiveDesignationMasterDomain();
		request.setAttribute("designationList", designationList);
		
		return "approvedJob";
	}
	
	@RequestMapping(value="/saveApprovedJobs",method=RequestMethod.POST)	
	public String saveProposalMasterDetails(@ModelAttribute("approvedJobModel") ApprovedJobModel approvedJobModel ,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		 approvedJobService.saveApprovedJobData(approvedJobModel);
		 int size= approvedJobModel.getJobReferences().size();
		/* request.setAttribute("message", " "+size +" Job Codes generated successfully");*/
		 redirectAttributes.addFlashAttribute("message"," "+size +" Job Codes generated successfully");
		 redirectAttributes.addFlashAttribute("status", "success");
				
		return "redirect:/approvedJob";
		 
	}
	
	@RequestMapping(value="/getApprovedJobDetails",method=RequestMethod.POST)	
	@ResponseBody
	public Map<String,List<ApprovedJobModel>> ViewProjectDetails(@ModelAttribute("approvedJobModel") ApprovedJobModel approvedJobModel ,@RequestParam("projectId") long projectId,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		Map<String,List<ApprovedJobModel>> assignedManpower = approvedJobService.getApprovedJobsByProjectId(projectId);
		request.setAttribute("assignedManpower", assignedManpower);
		return assignedManpower;
		
	}

	@RequestMapping(value="/getApprovedJobDetailsCreated",method=RequestMethod.POST)	
	@ResponseBody
	public List<ApprovedJobModel> getApprovedJobDetailsCreated(@ModelAttribute("approvedJobModel") ApprovedJobModel approvedJobModel ,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		int projectId = approvedJobModel.getProjectId();
		List<ApprovedJobModel> list = approvedJobService.getApprovedJobsByProjectIdCreated(projectId);
			
		return list;
		
	}
	
	

	@RequestMapping(value="approvedJobs")
	public String viewApprovedJob(HttpServletRequest request, EmployeeApprovedJobMappingModel model){
		approvedJobService.viewApprovedJobAuthorityCheck();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		 if(null != assignedOrganisation && !assignedOrganisation.equals("")){
				Long orgId = Long.valueOf(assignedOrganisation).longValue();		     
			    List<GroupMasterModel> groupList =groupMasterService.getAllActiveGrpMasterDomain(orgId);
				request.setAttribute("groupList", groupList);
			}
		
		return "viewApprovedJob";		
	}
	
	@RequestMapping(value="approvedJobs",method=RequestMethod.POST)
	public @ResponseBody List getApprovedJob(HttpServletRequest request, @ModelAttribute EmployeeApprovedJobMappingModel employeeApprovedJobMappingModel){
		return approvedJobService.getApprovedJobsCount(employeeApprovedJobMappingModel);	 		
	}
	

	@RequestMapping(value = "checkUniqueJobCode", method = RequestMethod.POST)
    public @ResponseBody
    int checkUniqueJobCode(@ModelAttribute("approvedJobModel") ApprovedJobModel approvedJobModel,HttpServletRequest request,RedirectAttributes redirectAttributes) {

    	int flag=0;
        flag = approvedJobService.checkUniqueJobCode(approvedJobModel);
        return flag;
    }

	@RequestMapping(value="/jobTitle")
	public String createApprovedJob(JobTitleModel jobTitleModel, HttpServletRequest request){
		approvedJobService.jobTitleAuthorityCheck();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		if(null != assignedOrganisation && !assignedOrganisation.equals("")){
			Long orgId = Long.valueOf(assignedOrganisation).longValue();
			List<GroupMasterModel> groupList = groupMasterService.getAllActiveGrpMasterDomain(orgId);
			request.setAttribute("groupList", groupList);
		}

		return "jobTitle";
	}
	
	
	@RequestMapping(value="/saveJobTitle",method=RequestMethod.POST)	
	public String saveJobTitle(@ModelAttribute("jobTitleModel") JobTitleModel jobTitleModel ,HttpServletRequest request,RedirectAttributes redirectAttributes){
		approvedJobService.saveJobTitle(jobTitleModel);
		 redirectAttributes.addFlashAttribute("message","Job Codes Mapped successfully");
		 redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/jobTitle";
		 
	}
	

	
}
