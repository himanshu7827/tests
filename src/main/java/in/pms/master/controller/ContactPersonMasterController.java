package in.pms.master.controller;

import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ClientMasterModel;
import in.pms.master.model.ContactPersonMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.ClientContactPersonMasterService;
import in.pms.master.service.ClientMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.validator.ClientContactPersonMasterValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

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
public class ContactPersonMasterController {
	
	@Autowired
	ClientContactPersonMasterService clientContactPersonMasterService;
	
	/*@Autowired
	OrganisationMasterService organisationMasterService;*/
	
	@Autowired
	ClientMasterService clientMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@RequestMapping("/clientContactPersonMaster")
	public String clientContactPersonMaster(ContactPersonMasterModel contactPersonMasterModel,HttpServletRequest request){		
		String referrer = request.getHeader("referer");
		if(null != referrer && !referrer.contains("endUserMaster")) {
			if(null != request.getParameter("encNumId")){
				request.setAttribute("encApplicationId", request.getParameter("encNumId"));
				if(null != referrer && !referrer.contains("encNumId")){
					request.setAttribute("referrerValue", referrer+"?encNumId="+request.getParameter("encNumId"));
				}else if(null != referrer && referrer.contains("encNumId")){
					request.setAttribute("referrerValue", referrer);
				}
			}
		}
		/*----------- Handle After Save Contact Person Redirected [29-05-2024] ----*/
		if(null != referrer && referrer.contains("clientContactPersonMaster")){
			String [] splitReferrer=referrer.split("\\?encNumId");
		    String[] splitHost = referrer.split("PMS");
		    String url = splitHost[0]+"PMS/applicationBasicDetails?encNumId";
		    if (splitReferrer.length > 1 && splitReferrer[1] != null && !splitReferrer[1].isEmpty()) {
				request.setAttribute("referrerValue", url+splitReferrer[1]);
			}
			
		}
		/*----------- Handle After Save Contact Person Redirected [29-05-2024] ----*/
		
		List<ClientMasterModel> clientMasterList =  clientMasterService.getAllActiveClientMasterDomain();
		request.setAttribute("clientMasterList", clientMasterList);
		return "clientContactPersonMaster";

	}
	@RequestMapping(value="/saveUpdateContactPersonMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateContactPersonMaster(ContactPersonMasterModel contactPersonMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			
			new ClientContactPersonMasterValidator().validate(contactPersonMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  request.setAttribute("showForm", 1);
		    	  List<ContactPersonMasterModel> list = clientContactPersonMasterService.getAllActiveContactPersonMasterDomain();

		  		request.setAttribute("data", list);
		    	  mav.setViewName("clientContactPersonMaster");
		          return mav;
		      }
		      String referrer=contactPersonMasterModel.getReferrerValue();
				String referrerValue="";
				if(null != referrer && !referrer.equals("")){
					String [] splitReferrer=referrer.split("\\?encNumId");
					 referrerValue=splitReferrer[1];
				}
			
			String strDuplicateCheck = clientContactPersonMasterService.checkDuplicateContactPersonName(contactPersonMasterModel);
			if(null == strDuplicateCheck){
			long id = clientContactPersonMasterService.saveUpdateContactPersonMaster(contactPersonMasterModel);
			if(id>0){
				if(contactPersonMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Client Contact record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Client Contact record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			}else{				
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
		/*	List<ContactPersonMasterModel> list = clientContactPersonMasterService.getAllContactPersonMasterDomain();
			request.setAttribute("contactPersonMasterModel",new ContactPersonMasterModel());
			request.setAttribute("data", list);	*/
			if(referrerValue.equals("")){
				mav.setViewName("redirect:/mst/clientContactPersonMaster");
			}
			else{
				mav.setViewName("redirect:/mst/clientContactPersonMaster?encNumId"+referrerValue);   //-- Redirect with encNumID [29-05-2024] 
			}
			return mav;
		}

		@RequestMapping(value = "/deleteClientContact",  method = RequestMethod.POST)
		public String deleteClientContact(ContactPersonMasterModel contactPersonMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
	    {	
					  
					  try
				    	{				
				    		long result= clientContactPersonMasterService.deleteClientContact(contactPersonMasterModel);
				    		if(result !=-1){
				    			redirectAttributes.addFlashAttribute("message",  "Client Contact record deleted Successfully");	
				    			redirectAttributes.addFlashAttribute("status", "success");
				    		}else{
				    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
				    			redirectAttributes.addFlashAttribute("status", "error");
				    		}
				    		List<ContactPersonMasterModel> list = clientContactPersonMasterService.getAllContactPersonMasterDomain();
							request.setAttribute("data", list);        
		                    
		                    
				    	}
				    	catch(Exception e){
			          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

				    	}		    								
		
			
			return "redirect:/mst/clientContactPersonMaster";
	    }
		
		//ajax call get contact using clientid
		@RequestMapping(value="/getContactPersonByClientId",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ContactPersonMasterModel> getContactPersonByClientId(@RequestParam("clientId") long clientId,HttpServletRequest request,HttpServletResponse response){
				List<ContactPersonMasterModel> datalist = new ArrayList<ContactPersonMasterModel>();				
				datalist = clientContactPersonMasterService.getContactPersonByClientId(clientId);
				request.setAttribute("data1", datalist);	
			return datalist; 
		}

		@RequestMapping("/changeProjectClientContactPerson")
		public String changeProjectClientContactPerson(ContactPersonMasterModel contactPersonMasterModel,HttpServletRequest request){	
			List<ProjectMasterModel> projectList =new ArrayList<ProjectMasterModel>();
			projectList = projectMasterService.getAllActiveProjectMasterData();
			request.setAttribute("projectList", projectList);	
			return "changeProjectClientContactPerson";

		}
		@RequestMapping(value="/getProjectClientContactPersonDetails",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody ProjectMasterForm getProjectClientContactPersonDetails(@RequestParam("projectId") long projectId,HttpServletRequest request,HttpServletResponse response){
			ProjectMasterDomain projectMasterDomain =projectMasterService.getProjectMasterDataWithClientById(projectId);
			List<ClientMasterModel> clientData = clientMasterService.getAllActiveClientMasterDomainByApplicationId(projectMasterDomain.getApplication().getNumId());
			ProjectMasterForm model=new ProjectMasterForm();
			if(clientData.size()>0 && clientData.get(0) !=null ){
			model.setClientName(clientData.get(0).getClientName());
			model.setClientId(clientData.get(0).getNumId());
			
			List<ContactPersonMasterDomain> listClientContact = new ArrayList<ContactPersonMasterDomain>();				
			listClientContact = clientContactPersonMasterService.getAllContactPersonByClientId(clientData.get(0).getNumId());
	        Set<ContactPersonMasterDomain> set = new HashSet<ContactPersonMasterDomain>(listClientContact);
			model.setContactPersonList(set);

			Set<ContactPersonMasterDomain> contactMaster=projectMasterDomain.getContactMaster();
			if(contactMaster.size()==0){
				contactMaster=projectMasterDomain.getApplication().getContactMaster();
			}
			final List<Long> contacts = new ArrayList<Long>();
			for (final ContactPersonMasterDomain contactPersonMasterDomain : contactMaster) {			
				contacts.add(contactPersonMasterDomain.getNumId());
			}
			model.setContactPersonId(contacts);
			}
			

			return model;

		}
		
		
		@RequestMapping(value="/saveUpdateProjectContactPersonMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateProjectContactPersonMaster(ContactPersonMasterModel contactPersonMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
				ProjectMasterDomain domain=projectMasterService.getProjectMasterDataById(contactPersonMasterModel.getProjectId());
				Set<ContactPersonMasterDomain> set=new HashSet<ContactPersonMasterDomain>();
				List<Long> list= contactPersonMasterModel.getContactPersonId();
				for (Long contactPersonId : list) {
					ContactPersonMasterDomain contactPerson=clientContactPersonMasterService.getContactPersonMasterById(contactPersonId);
					set.add(contactPerson);
				}
				domain.setContactMaster(set);
			domain = projectMasterService.mergeClientContactDetails(domain);
			if(domain.getNumId()>0){
					redirectAttributes.addFlashAttribute("message",  " Contact Person for Funding Organization saved Successfully ");	
					redirectAttributes.addFlashAttribute("status", "success");
								
			}
			
			mav.setViewName("redirect:/mst/changeProjectClientContactPerson");
			return mav;
		}
	}

