package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.service.ClientMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProposalDocumentMasterService;
import in.pms.master.service.ProposalMasterService;
import in.pms.master.service.ThrustAreaMasterService;
import in.pms.master.validator.ProposalMasterValidator;
import in.pms.transaction.model.ApplicationModel;
import in.pms.transaction.service.ApplicationService;
import in.pms.transaction.service.CollaborativeOrgDetailsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/mst")
public class ProposalMasterController {
	
	@Autowired
	ProposalMasterService proposalMasterService;
	
	@Autowired
	OrganisationMasterService organisationMasterService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ClientMasterService clientMasterService;
	
	@Autowired
	ThrustAreaMasterService thrustAreaMasterService;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired 
	EncryptionService encryptionService;
	
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	
	@Autowired
	CollaborativeOrgDetailsService collaborativeOrgDetailsService;
	
	
	@RequestMapping("/proposalMaster")
	public ModelAndView getAllProposalMaster(HttpServletRequest request, @ModelAttribute ProposalMasterModel proposalMasterModal,Model model1){
		ModelAndView modelAndView = new ModelAndView();
		int applicationId=0;
		Map md = model1.asMap();
	   
	      if(md.get("applicationId")!=null)
	        applicationId=Integer.parseInt(md.get("applicationId").toString());
	    
		if(applicationId >0){
			request.setAttribute("applicationId",applicationId);
			ApplicationModel app=applicationService.getApplicationById(applicationId);
			request.setAttribute("proposalCategoryId",app.getCategoryId()); /*-------------- Get Project Category ID [25-01-2024] -------------------------------*/
			if(app!=null){
				request.setAttribute("totalCost", (int)app.getTotalProposalCost());	
			}
			ProposalMasterModel model=proposalMasterService.getProposalMasterByApplicationId(applicationId);
			if(model!=null){
			request.setAttribute("proposalData", model);
			proposalMasterModal.setNumId(model.getNumId());
			proposalMasterModal.setProposalTitle(model.getProposalTitle());
			proposalMasterModal.setProposalStatus(model.getProposalStatus());
			proposalMasterModal.setBackground(model.getBackground());
			proposalMasterModal.setSummary(model.getSummary());
			proposalMasterModal.setSubmittedBy(model.getSubmittedBy());
			proposalMasterModal.setObjectives(model.getObjectives());
			proposalMasterModal.setApplicationId(applicationId);
			proposalMasterModal.setRemarks(model.getRemarks());
			proposalMasterModal.setStatus(model.getStatus());
			proposalMasterModal.setProposalRefNo(model.getProposalRefNo());
			modelAndView.addObject("title",model.getProposalTitle());
			modelAndView.addObject("proposalMasterModal",model);
			}
			else{
				/*List<ProposalMasterModel> list = proposalMasterService.getAllProposalMasterDomain();
				request.setAttribute("data", list);	*/
				ApplicationModel application=applicationService.getApplicationById(applicationId);
				if(application!=null){
					request.setAttribute("totalCost", app.getTotalProposalCost());	
				}
				ProposalMasterModel model2=new ProposalMasterModel();
				model2.setApplicationId(applicationId);
				model2.setProposalTitle(application.getProposalTitle());
				List<ProposalMasterModel> list = proposalMasterService.getAllProposalMasterDomain();
				request.setAttribute("data", list);	
				modelAndView.addObject("proposalMasterModal",model2);


				}
			

		}
		else{

		List<ProposalMasterModel> list = proposalMasterService.getAllProposalMasterDomain();
		request.setAttribute("data", list);	
		modelAndView.addObject("proposalMasterModal",list);

		}
		//request.getSession().setAttribute("applicationId", applicationId);
		modelAndView.setViewName("proposalMaster");
		return modelAndView;

		//return "proposalMaster";
		
		
		
		
	}
	
	@RequestMapping(value="/saveProposalMasterDetails",method=RequestMethod.POST)	
	@ResponseBody
	public long saveProposalMasterDetails(@ModelAttribute("proposalMasterModel") ProposalMasterModel proposalMasterModel ,HttpServletRequest request,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		proposalMasterModel = proposalMasterService.saveUpdateProposalMaster(proposalMasterModel);			
		return proposalMasterModel.getNumId();
	}
	
	@RequestMapping(value="/saveUpdateProposalMaster",method=RequestMethod.POST)
	public @ResponseBody ProposalMasterModel saveUpdateProposalMaster(HttpServletRequest request, ProposalMasterModel proposalMasterModel, RedirectAttributes redirectAttributes, BindingResult bindingResult){	
	
		ModelAndView mav = new ModelAndView();
		new ProposalMasterValidator().validate(proposalMasterModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	   
	  		redirectAttributes.addFlashAttribute("message",  "Error in saving record");	
			redirectAttributes.addFlashAttribute("status", "error");
			proposalMasterModel.setNumHasError(1);
	    	  mav.setViewName("proposalMaster");
	        
	          
	          
	      }
		
	  	String strDuplicateCheck = proposalMasterService.checkDuplicateProposalName(proposalMasterModel);
		if(null == strDuplicateCheck){
			proposalMasterModel.setApplicationId(proposalMasterModel.getApplicationId());
			ApplicationModel model=applicationService.getApplicationById(proposalMasterModel.getApplicationId());
			proposalMasterModel.setProposalTitle(model.getProposalTitle());
			proposalMasterModel= proposalMasterService.saveUpdateProposalMaster(proposalMasterModel);
			long id=proposalMasterModel.getNumId();
			if(id>0){
				if(proposalMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Proposal record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Proposal record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}		
				proposalMasterModel.setNumHasError(0);

			}
			}else{
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
				proposalMasterModel.setNumHasError(1);
			}		
		return proposalMasterModel;
		}
	      


	
	
	//Delete
	@RequestMapping(value="/deleteProposal",method=RequestMethod.POST)
	public String deleteProposal(ProposalMasterModel proposalMasterModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			 //System.out.println("inside controller");
		if(proposalMasterModel.getNumIds()==null || proposalMasterModel.getNumIds().length==0){
			//System.out.println("length"+ proposalMasterModel.getNumIds());
			List<ProposalMasterModel> list = proposalMasterService.getAllProposalMasterDomain();
			request.setAttribute("data", list);	
			 //System.out.println("before return");
			return "proposalMaster";
		}
		else{
			    	try
			    	{ // System.out.println("inside try");
					
			    		proposalMasterService.deleteProposal(proposalMasterModel);
			    		redirectAttributes.addFlashAttribute("message",  "Proposal details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		List<ProposalMasterModel> list = proposalMasterService.getAllProposalMasterDomain();
						request.setAttribute("data", list);		
	                   
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/mst/proposalMaster";
		}
			    }
	
	
	////Group by organisation Id

	@RequestMapping(value = "/getGroupByOrgId", method = RequestMethod.POST)
	public @ResponseBody
	List<ProposalMasterModel>  FetchGroupByOrg(HttpServletRequest request) 
	{
		int OrganisationId = Integer.parseInt(request.getParameter("organisationId"));
		
		List<ProposalMasterModel> list1 = proposalMasterService.viewGroup(OrganisationId);
		
		//System.out.println("list1"+list1.size()+""+list1.get(0).getGroupName());
		return list1;
	}
	
	
////Contact Person By Client

	@RequestMapping(value = "/getContactByClient", method = RequestMethod.POST)
	public @ResponseBody
	List<ProposalMasterModel>  FetchContactByClient(HttpServletRequest request) 
	{
		int ClientId = Integer.parseInt(request.getParameter("clientId"));
		
		List<ProposalMasterModel> list1 = proposalMasterService.viewContact(ClientId);
		
		return list1;
	}
	
	@RequestMapping(value="proposalDetailsByApplicationId",method=RequestMethod.POST)
	@ResponseBody
	public ProposalMasterModel getProposalMasterByApplicationId(long applicationId){
		return proposalMasterService.getProposalMasterByApplicationId(applicationId);
	}
	
	@RequestMapping(value="/proposalDetails/{encId}")
	public String proposalDetails(HttpServletRequest request,@PathVariable("encId") String encId){	
		String strApplicationId= encryptionService.dcrypt(encId);
		long applicationId= Long.parseLong(strApplicationId);
		ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(applicationId);
		//ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(proposalId);
		//Added by devesh on 25/8/23 for setting groupname in the jsp
		if (proposalMasterModel.getGroupId() != 0) {
	        // Assuming getGroupNames returns the new group name based on groupId
			String groupIdString = Long.toString(proposalMasterModel.getGroupId());
			proposalMasterModel.setGroupName(groupMasterService.getDistinctGroupsForOrganisation(groupIdString));
	    }
		//System.out.println("Proposal ID: " + proposalMasterModel.getProposalTitle() +", Group ID: " + proposalMasterModel.getGroupId() +", Group Name: " + proposalMasterModel.getGroupName());
		//End of setting groupname in the jsp
		//ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(proposalId);
		List<ProjectDocumentMasterModel> proposalDocument = proposalDocumentMasterService.documentsByProposalId(proposalMasterModel.getNumId());
		JSONArray collaborativeArray =collaborativeOrgDetailsService.getAllActiveCollaborativeOrgDetailsDomain(applicationId);
		request.setAttribute("collaborativeArray", collaborativeArray);
		request.setAttribute("proposalMaster", proposalMasterModel);
		request.setAttribute("proposalDocument", proposalDocument);
		request.setAttribute("encApplicationId", encId);
		
		return "proposalDetails";
	}
/*	@RequestMapping("/proposalList")
	public String getAllProposalsConvertedToProject(HttpServletRequest request, ProjectMasterForm projectMasterForm){		
		List<ProposalMasterModel> list = proposalMasterService.getAllActiveProposalMasterDomain();
		request.setAttribute("data", list);		
		return "proposalList";
	}*/
	@RequestMapping("/proposalList")
	public String getGroupNameWithColor(HttpServletRequest request, GroupMasterModel groupMasterModel){	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		
		if(null != assignedOrganisation && !assignedOrganisation.equals("")){
			Long orgId = Long.valueOf(assignedOrganisation).longValue();		     
		    List<GroupMasterModel> groupNames =groupMasterService.getAllActiveGrpMasterDomain(orgId);
			request.setAttribute("groupnames", groupNames);
			
			if(null != groupNames && groupNames.size()>0){
				long groupId = groupNames.get(0).getNumId();
				List<ApplicationModel> datalist  = applicationService.getProposalDetailByGruopId(groupId);
				request.setAttribute("activeGroupData", datalist);
			}
					
	}
		return "proposalList";
}

	
	@RequestMapping(value="/previewAndSubmit")
	public ModelAndView previewAndSubmit(HttpServletRequest request,Model model1){	
			ModelAndView modelAndView = new ModelAndView();
			int applicationId=0;
			Map md = model1.asMap();
		   
		      if(md.get("applicationId")!=null)
		        applicationId=Integer.parseInt(md.get("applicationId").toString());
		    
			if(applicationId >0){
				ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(applicationId);
				//ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(proposalId);
				List<ProjectDocumentMasterModel> proposalDocument = proposalDocumentMasterService.documentsByProposalId(proposalMasterModel.getNumId());
				JSONArray collaborativeArray =collaborativeOrgDetailsService.getAllActiveCollaborativeOrgDetailsDomain(applicationId);
				request.setAttribute("collaborativeArray", collaborativeArray);
				request.setAttribute("proposalMaster", proposalMasterModel);
				request.setAttribute("proposalDocument", proposalDocument);
				request.setAttribute("encApplicationId", encryptionService.encrypt(applicationId+""));
				modelAndView.setViewName("previewNSubmitProposal");

			}
			else{

			}
			return modelAndView;

		}
	@RequestMapping(value="/submitProposal",method=RequestMethod.POST)
	@ResponseBody
	public boolean submitProposal(@RequestParam("encApplicationId") String encApplicationId){
		String strApplicationId=encryptionService.dcrypt(encApplicationId);
		long applicationId= Long.parseLong(strApplicationId);
		return proposalMasterService.submitProposal(applicationId);
	}
	
	@RequestMapping(value="/proposalHistory",method=RequestMethod.POST)
	public @ResponseBody List<Object[]> proposalHistory(@RequestParam("encId") String encId, HttpServletRequest request){
		String strApplication= encryptionService.dcrypt(encId);
		long applicationId= Long.parseLong(strApplication);
		List<Object[]> list=null;
		list = proposalMasterService.getproposalHistory(applicationId);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj=list.get(i);
				Date dt=(Date)obj[2];
				if(null != dt){
					obj[2]=DateUtils.dateToDateTimeString(dt);
				}
			}
			return list;
		}
		else{
			return null;	
		}
		
	}
	
	/*Added new proposal history controller by devesh on 28/7/23*/
	@RequestMapping(value="/proposalHistorydetails",method=RequestMethod.POST)
	public @ResponseBody List<Object[]> proposalHistorynew(@RequestParam("encId") String encId, HttpServletRequest request){
		String strApplication= encryptionService.dcrypt(encId);
		long applicationId= Long.parseLong(strApplication);
		List<Object[]> list=null;
		list = proposalMasterService.getproposalHistorydetails(applicationId);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj=list.get(i);
				Date dt=(Date)obj[2];
				Date dt13 = (Date) obj[13]; // Assuming 12th element is the date object
				if(null != dt){
					obj[2]=DateUtils.dateToDateTimeString(dt);
				}
				if (dt13 != null) {
		            obj[13] = DateUtils.dateToDateTimeString(dt13);
		        }
			}
			return list;
		}
		else{
			return null;	
		}
		
	}
	/*End of new proposal history controller*/
	
	@RequestMapping(value="/proposalVersionDetails",method=RequestMethod.POST)
	public String proposalVersionDetails(ProposalMasterModel proposalMasterModel, HttpServletRequest request){
	ProposalMasterModel proposalMaster=new ProposalMasterModel();
	proposalMaster=proposalMasterService.getVersionDetails(proposalMasterModel.getNumId());
	request.setAttribute("proposalMaster", proposalMaster);
	/*request.setAttribute("version", proposalMasterModel.getNumVersion());*/
	request.setAttribute("version", proposalMasterModel.getNumVersion()-1);//Decreased value of version by 1
	//Added by devesh on 26-09-23 to get document details in version history
	List<ProjectDocumentMasterModel> proposalDocument = proposalDocumentMasterService.documentsHistoryByRevId(proposalMasterModel.getNumId());
	request.setAttribute("proposalDocument", proposalDocument);
	//End of document details
	
		return "versionDetails";
	}
	
	@RequestMapping(value="/getActiveProposals", method=RequestMethod.POST)
	public @ResponseBody List<ProposalMasterModel> getActiveProposals(@RequestParam("startDate") String startDate ,@RequestParam("endDate") String endDate,HttpServletRequest request){
		List<ProposalMasterModel> newProposalsList = proposalMasterService.getActiveProposals(startDate, endDate);
		
		request.setAttribute("newProposalsList", newProposalsList);	
		
		return newProposalsList;		
	}
	
	@RequestMapping(value="/getActiveProposalsByGroup", method=RequestMethod.POST)
	public @ResponseBody List<ProposalMasterModel> getActiveProposalsByGroup(@RequestParam("startDate") String startDate ,@RequestParam("endDate") String endDate,@RequestParam("encGroupId") String encGroupId,HttpServletRequest request){
		List<ProposalMasterModel> newProposalsList = proposalMasterService.getActiveProposalsByGroup(startDate, endDate,encGroupId);
		
		request.setAttribute("newProposalsList", newProposalsList);	
		
		return newProposalsList;		
	}
	
	
}
