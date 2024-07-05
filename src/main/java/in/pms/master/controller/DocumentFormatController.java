package in.pms.master.controller;

import in.pms.master.model.DocumentFormatModel;
import in.pms.master.service.DocumentFormatService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class DocumentFormatController {
	
	@Autowired
	DocumentFormatService documentFormatService;
	
	@RequestMapping("/documentFormat")
	public String projectInvoiceMaster(DocumentFormatModel documentFormatModel,HttpServletRequest request){		
	List<DocumentFormatModel> documentFormatList = documentFormatService.getAllDocumentFormatModel();
		request.setAttribute("documentFormatList", documentFormatList);
		return "documentFormat";
	}
	
	@RequestMapping(value="/saveUpdateDocumentFormat",method=RequestMethod.POST)	
	public String mergeDocumentFormat(DocumentFormatModel documentFormatModel,HttpServletRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes){

		String strDuplicateCheck = documentFormatService.checkDuplicateDocumentFormat(documentFormatModel);
		if(null == strDuplicateCheck){
		int id = documentFormatService.mergeDocumentFormatMaster(documentFormatModel);
		if(id>0){
			if(documentFormatModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Designation record saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Designation record updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{			
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}	
		return "redirect:/mst/documentFormat";
		
	}
	
	@RequestMapping(value="documentFormatByDocumentType",method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentFormatModel> documentFormatByDocumentType(HttpServletRequest request,DocumentFormatModel documentFormatModel){		
		return documentFormatService.documentFormatByDocumentType(documentFormatModel);
	}

}
