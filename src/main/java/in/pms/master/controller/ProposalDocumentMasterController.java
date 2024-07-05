package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
/*import in.pms.global.misc.FTPPropertiesReader;*/
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProposalDocumentMasterModel;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.service.DocumentTypeMasterService;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProposalDocumentMasterService;
import in.pms.master.service.ProposalMasterService;
import in.pms.transaction.service.ApplicationService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/mst")
public class ProposalDocumentMasterController {
	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	@Autowired
	ProposalMasterService proposalMasterService;
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	
	
	@RequestMapping("/uploadProposalDocument")
	public String uploadProposalDocument(HttpServletRequest request, ProjectDocumentMasterModel projectDocumentMasterModel,Model model1){	
		int applicationId=0;
		Map md = model1.asMap();
		   
	      if(md.get("applicationId")!=null){
	        applicationId=Integer.parseInt(md.get("applicationId").toString());
			ProposalMasterModel model=proposalMasterService.getProposalMasterByApplicationId(applicationId);
			request.setAttribute("proposalMasterModal",model);
			// Get All Document By Proposal Id 
						List<ProjectDocumentMasterModel> test = proposalDocumentMasterService.documentsByProposalId(model.getNumId());
						System.out.print(test.toString());
						request.setAttribute("alldocumentlist",test);
						//End Changes
		}
		else{
		
		List<ProposalMasterModel> proposalMasterModelList=proposalMasterService.getAllActiveProposalMasterDomain();
		request.setAttribute("proposalMasterModelList", proposalMasterModelList);
		}
		List<DocumentTypeMasterModel> documentTypeList =documentTypeMasterService.getAllActiveDocumentTypeMasterDomain();
		request.setAttribute("documentTypeList", documentTypeList);
		return "uploadProposalDocument";
		//return "uploadProposalDocument";
	}
	
	@RequestMapping(value="/saveProposalDocument",method = RequestMethod.POST)	
	public String saveProposalDocument(HttpServletRequest request, @ModelAttribute ProjectDocumentMasterModel projectDocumentMasterModel,  BindingResult bindingResult,RedirectAttributes redirectAttributes){		
		//List<MultipartFile> files=  proposalDocumentMasterModel.getProposalDocumentFile();
		boolean result = proposalDocumentMasterService.uploadProposalDocument(projectDocumentMasterModel);
		if(result){
			redirectAttributes.addFlashAttribute("message",  "Document Details saved successfully");	
			redirectAttributes.addFlashAttribute("status", "success");
		}else{
			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			redirectAttributes.addFlashAttribute("status", "error");
		}

		List<ProposalMasterDomain> list=proposalDocumentMasterService.getApplicationIdbyProposalId(projectDocumentMasterModel.getProposalId());
		
		if(list.size()>0)
		redirectAttributes.addFlashAttribute("applicationId",list.get(0).getApplication().getNumId());
			
		return "redirect:/mst/uploadProposalDocument";
		//proposalDocumentMasterService.uploadProposalDocument(proposalDocumentMasterModel);
		//return "uploadProposalDocument";
	}
	
	@RequestMapping(value="uploadedProposalDocument",method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectDocumentMasterModel> uploadedProposalDocument(HttpServletRequest request,ProjectDocumentMasterModel projectDocumentMasterModel){				
		return proposalDocumentMasterService.uploadedProposalDocument(projectDocumentMasterModel);
	}
	
		@RequestMapping(value="/proposalDocumentDetails/{encId}")
	public String proposalDocumentDetails(HttpServletRequest request,@PathVariable("encId") String encId){	
		//String encProjectId = encId;
		//String referrer = request.getHeader("referer");
		String strProposalId= encryptionService.dcrypt(encId);
		long proposalId= Long.parseLong(strProposalId);
		List<ProjectDocumentMasterModel> proposalDocument = proposalDocumentMasterService.documentsByProposalId(proposalId);
		//System.out.println("document"+projectDocument);
		request.setAttribute("proposalDocument", proposalDocument);
		/*if(null != referrer){		
			if(referrer.contains("/projectDetails/")){
				return "documentDetailWithoutHeader";
			}
		}
			*/
		return "proposalDocumentDetails";
	}
		@RequestMapping(value="showProposalDocumentRevision/{encDocumentId}",method=RequestMethod.GET)
		public String  showProjectDocumentRevision(HttpServletRequest request ,@PathVariable("encDocumentId") String encDocumentId){	
			//System.out.println(encDocumentId);
			List<ProjectDocumentMasterModel> proposalDocument = proposalDocumentMasterService.showProposalDocumentRevision(encDocumentId);
			request.setAttribute("proposalDocument", proposalDocument);				
		return "showProposalDocumentRevision";
		}
		
	// controller for count the document from proposaldocumentmaster table by proposal id 
		@RequestMapping(value="checkProposalDocumentExists",method = RequestMethod.POST)
		@ResponseBody
		public long checkProposalDocumentExists(HttpServletRequest request,ProjectDocumentMasterModel documentFormatModel){		
			long id = documentFormatModel.getDocumentTypeId();
			long count = proposalDocumentMasterService.checkDocumentMaster(id);
			System.out.print("id:"+id+"and count"+count);
			return count;
		}
		
		
}
