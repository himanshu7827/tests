package in.pms.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.master.model.ClientMasterModel;
import in.pms.master.model.DocStageMappingModel;
import in.pms.master.model.DocStageMasterModel;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.OrganisationMasterModel;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.master.service.DocStageMappingService;
import in.pms.master.service.DocStageMasterService;
import in.pms.master.service.DocumentTypeMasterService;
import in.pms.master.validator.DocStageMappingValidator;
import in.pms.master.validator.ProposalMasterValidator;

@Controller
@RequestMapping("/mst")

public class DocStageMappingController {
	
	@Autowired
	DocStageMappingService docStageMappingService;
	
	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	
	@Autowired
	DocStageMasterService docStageMasterService;
	
	@RequestMapping("/docStageMapping")
	public String getAllDocStageMapping(HttpServletRequest request, DocStageMappingModel docStageMappingModel){		
		List<DocStageMappingModel> list = docStageMappingService.getAllDocStageMappingDomain();
		request.setAttribute("data", list);		
		List<DocumentTypeMasterModel> listDocument = documentTypeMasterService.getAllActiveDocumentTypeMasterDomain();
		request.setAttribute("documentlist", listDocument);		
		List<DocStageMasterModel> listStage = docStageMasterService.getAllActiveDocStageMasterDomain();
		request.setAttribute("stagelist", listStage);		
		return "docStageMapping";
	}
	
	@RequestMapping(value="/saveUpdateDocStageMapping",method=RequestMethod.POST)
	public ModelAndView saveUpdateDocStageMapping(HttpServletRequest request, DocStageMappingModel docStageMappingModel, RedirectAttributes redirectAttributes, BindingResult bindingResult){
	
	ModelAndView mav = new ModelAndView();
	new DocStageMappingValidator().validate(docStageMappingModel, bindingResult);
    if (bindingResult.hasErrors()) {
    	 System.out.println("inside hasErrors");
  	  List<DocStageMappingModel> list = docStageMappingService.getAllDocStageMappingDomain();
  	  
  	List<DocumentTypeMasterModel> listDocument = documentTypeMasterService.getAllActiveDocumentTypeMasterDomain();
	request.setAttribute("documentlist", listDocument);		
	List<DocStageMasterModel> listStage = docStageMasterService.getAllActiveDocStageMasterDomain();
	request.setAttribute("stagelist", listStage);		
    request.setAttribute("data", list);
  	mav.setViewName("docStageMapping");
    return mav;           
    }

	long count =    docStageMappingService.checkUniqueDocument(docStageMappingModel);	
	if(count>0){
		
		redirectAttributes.addFlashAttribute("message",  "Document already exists against this Stage ");	
		redirectAttributes.addFlashAttribute("status", "error");
		}else{
			long id = docStageMappingService.saveUpdateDocStageMapping(docStageMappingModel);
			redirectAttributes.addFlashAttribute("message",  " Record saved Successfully with Id "+id);	
			redirectAttributes.addFlashAttribute("status", "success");
		}					
	
	mav.setViewName("redirect:/mst/docStageMapping");
	
	return mav;
		
	}


	
	////delete
	
	@RequestMapping(value="/deleteDocStageMapping",method=RequestMethod.POST)
	public String deleteDocStageMapping(DocStageMappingModel docStageMappingModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			 System.out.println("inside controller");
		if(docStageMappingModel.getNumIds()==null || docStageMappingModel.getNumIds().length==0){
			System.out.println("length"+ docStageMappingModel.getNumIds());
			List<DocStageMappingModel> list = docStageMappingService.getAllDocStageMappingDomain();
			request.setAttribute("data", list);	
			 System.out.println("before return");
			return "docStageMapping";
		}
		else{
			    	try
			    	{ System.out.println("inside try");
					
			    		docStageMappingService.deletedocStageMapping(docStageMappingModel);
			    		redirectAttributes.addFlashAttribute("message",  "Document/Stage details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
			    		List<DocStageMappingModel> list = docStageMappingService.getAllDocStageMappingDomain();
			    		redirectAttributes.addFlashAttribute("data", list);		
	                   
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/mst/docStageMapping";
		}
			    }
	
	
	//ajax call get Document using StageId
			@RequestMapping(value="/getDocumentByStageId",method=RequestMethod.POST,produces="application/json")
			public @ResponseBody List<DocStageMappingModel> getDocumentByStageId(@RequestParam("stageId") long stageId,DocStageMappingModel docStageMappingModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
			
				
					List<DocStageMappingModel> datalist = new ArrayList<DocStageMappingModel>();
				
					//System.out.println("stageId=== " +stageId);
					datalist = docStageMappingService.getDocumentByStageId(stageId,docStageMappingModel);
					request.setAttribute("data1", datalist);	
				return datalist; 
			}

	

}
