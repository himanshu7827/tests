package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.master.model.ClientMasterModel;
import in.pms.master.model.ContactPersonMasterModel;
import in.pms.master.service.ClientContactPersonMasterService;
import in.pms.master.service.EndUserMasterService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class EndUserMasterController {

	@Autowired
	EndUserMasterService endUserMasterService;
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ClientContactPersonMasterService clientContactPersonMasterService;
	
	@RequestMapping("/endUserMaster")
	public String getAllEndUserMaster(HttpServletRequest request, ClientMasterModel clientMasterModel){	
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
		/*----------- Handle After Save Client Master Redirected [29-05-2024] ----*/
		if(null != referrer && referrer.contains("endUserMaster")){
			String [] splitReferrer=referrer.split("\\?encNumId");
		    String[] splitHost = referrer.split("PMS");
		    String url = splitHost[0]+"PMS/applicationBasicDetails?encNumId";
		    if (splitReferrer.length > 1 && splitReferrer[1] != null && !splitReferrer[1].isEmpty()) {
				request.setAttribute("referrerValue", url+splitReferrer[1]);
			}
		}
		/*----------- End of Handle After Save Client Master Redirected [29-05-2024] ----*/
		List<ClientMasterModel> list = endUserMasterService.getAllEndUserMasterDomain();
		request.setAttribute("data", list);		
		return "endUserMaster";
	}
	
	@RequestMapping(value="/saveUpdateendUserMaster",method=RequestMethod.POST)	
	public String saveUpdateendUserMaster(HttpServletRequest request, ClientMasterModel clientMasterModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = endUserMasterService.checkDuplicateEndUserName(clientMasterModel);
		String referrer=clientMasterModel.getReferrerValue();
		String referrerValue="";
		if(null != referrer && !referrer.equals("")){
			String [] splitReferrer=referrer.split("\\?encNumId");
			 referrerValue=splitReferrer[1];
		}
		
		if(null == strDuplicateCheck){
		long id = endUserMasterService.saveUpdateEndUserMaster(clientMasterModel);
		if(id>0){
			if(clientMasterModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Client record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Client record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
	/*	if(clientMasterModel.getReferrerValue().equals(null)){
		List<ClientMasterModel> list = clientMasterService.getAllClientMasterDomain();
		request.setAttribute("clientMasterModel",new ClientMasterModel());
		request.setAttribute("data", list);	
		return "redirect:/mst/clientMaster";
		}*/
		if(referrerValue.equals("")){
			return "redirect:/mst/endUserMaster";
		}
		else{
			return "redirect:/mst/endUserMaster?encNumId"+referrerValue;  //-- Redirect with encNumID [29-05-2024] 
		}

	}
	
	@RequestMapping(value="/getendUserMasterDetailsByEndUserId",method=RequestMethod.POST)
	public @ResponseBody ClientMasterModel getClientDetailsByClientId(@RequestParam("encClientId") String encClientId,@RequestParam("encProjectId") String encProjectId,ClientMasterModel clientMasterModel ,HttpServletRequest request){
		String encId=encryptionService.dcrypt(encClientId);
		long numClientId = Long.parseLong(encId);
		String projId=encryptionService.dcrypt(encProjectId);
		long numProjectId = Long.parseLong(projId);
		ClientMasterModel data = endUserMasterService.getEndUserMasterDomainById(numClientId);
		List<ContactPersonMasterModel> list=clientContactPersonMasterService.getContactPersonDetails(numProjectId);
		data.setContactPersonList(list);
		return data;
	}
	
	@RequestMapping(value="/endUserDetailsByApplicationId",method=RequestMethod.POST)
	public @ResponseBody List <ClientMasterModel> getClientDetailsByApplicationId(@RequestParam("encApplicationId") String encApplicationId,HttpServletRequest request){
		String encId=encryptionService.dcrypt(encApplicationId);
		long numApplicationId = Long.parseLong(encId);
		List<ClientMasterModel> clientData = endUserMasterService.getAllActiveEndUserMasterDomainByApplicationId(numApplicationId);
		return clientData;
	}
}
