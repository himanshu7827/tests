package in.pms.master.controller;

import in.pms.master.dao.DocStageMasterDao;
import in.pms.master.model.DocStageMasterModel;
import in.pms.master.service.DocStageMasterService;
import in.pms.master.validator.DocStageMasterValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

	@Controller
	@RequestMapping("/mst")
	@Slf4j
	public class DocStageMasterController {
		
		@Autowired
		DocStageMasterService docStageMasterService;
		
		@Autowired
		DocStageMasterDao docStageMasterDao;
		
		@RequestMapping("/docStageMaster")
		public String docStageMasterMaster(DocStageMasterModel docStageMasterModel,HttpServletRequest request){		
			List<DocStageMasterModel> list = docStageMasterService.getAllDocStageMasterDomain();
			request.setAttribute("data", list);		
			return "docStageMaster";
		}
		
		@RequestMapping(value="/saveUpdateDocStageMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateDocStageMaster(DocStageMasterModel docStageMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			
			new DocStageMasterValidator().validate(docStageMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  List<DocStageMasterModel> list = docStageMasterService.getAllActiveDocStageMasterDomain();

		  		request.setAttribute("data", list);
		    	  mav.setViewName("docStageMaster");
		          return mav;
		      }
			
			
			String strDuplicateCheck = docStageMasterService.checkDuplicateStageName(docStageMasterModel);
			if(null == strDuplicateCheck){
			long id = docStageMasterService.saveUpdateDocStageMaster(docStageMasterModel);
			if(id>0){
				if(docStageMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Document Stage record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Document Stage record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			}else{
				
				
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
			List<DocStageMasterModel> list = docStageMasterService.getAllDocStageMasterDomain();
			request.setAttribute("docStageMasterModel",new DocStageMasterModel());
			request.setAttribute("data", list);	
			mav.setViewName("redirect:/mst/docStageMaster");
			return mav;
		}
	
		@RequestMapping(value = "/deleteDocumentStage",  method = RequestMethod.POST)
		public String deleteDocumentStage(DocStageMasterModel docStageMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
	    {   
			try
	    	{				
	    		long result= docStageMasterService.deleteDocumentStage(docStageMasterModel);
	    		if(result !=-1){
	    			redirectAttributes.addFlashAttribute("message",  "Document Stage record deleted Successfully");	
	    			redirectAttributes.addFlashAttribute("status", "success");
	    		}else{
	    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
	    			redirectAttributes.addFlashAttribute("status", "error");
	    		}
	    		List<DocStageMasterModel> list = docStageMasterService.getAllDocStageMasterDomain();
				request.setAttribute("data", list);        
                
                
	    	}
	    	catch(Exception e){
          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

	    	}		    								


return "redirect:/mst/docStageMaster";
	    }
		
	}
