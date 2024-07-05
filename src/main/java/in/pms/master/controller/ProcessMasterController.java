package in.pms.master.controller;
import in.pms.master.model.ProcessMasterModel;

import in.pms.master.service.ProcessMasterService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class ProcessMasterController {

	@Autowired
	ProcessMasterService processMasterService;
	
	@RequestMapping("/ProcessMaster")
	public String getAllProcess(HttpServletRequest request, ProcessMasterModel processMasterModel){		
		List<ProcessMasterModel> list = processMasterService.getAllProcessData();
		request.setAttribute("data", list);		
		return "ProcessMaster";
	}
	
	
	
	
	@RequestMapping(value="/saveProcessMaster",method=RequestMethod.POST)	
	public String saveUpdateProcessMaster(HttpServletRequest request, ProcessMasterModel processMasterModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = processMasterService.checkDuplicateProcessName(processMasterModel);
		if(null == strDuplicateCheck){
		long id = processMasterService.saveUpdateProcessMaster(processMasterModel);
		if(id>0){
			if(processMasterModel.getNumProcessId()==0){
				redirectAttributes.addFlashAttribute("message",  "Process details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Process details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		List<ProcessMasterModel> list = processMasterService.getAllProcessData();
		request.setAttribute("data", list);				
		return "redirect:/mst/ProcessMaster";
	}
	
	@RequestMapping(value="/deleteProcessMaster",method=RequestMethod.POST)
	public String deleteProcessMaster(ProcessMasterModel processMasterModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			   
		if(processMasterModel.getNumIds()==null || processMasterModel.getNumIds().length==0){
			List<ProcessMasterModel> list = processMasterService.getAllProcessData();
			request.setAttribute("data", list);	
			return "ProcessMaster";
		}
		else{
			    	try
			    	{
					
			    		processMasterService.deletProcessData(processMasterModel);
			    		redirectAttributes.addFlashAttribute("message",  "Process details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status", "success");
						List<ProcessMasterModel> list = processMasterService.getAllProcessData();
						request.setAttribute("data", list);
	                   
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/mst/ProcessMaster";
		}
			    }
	
	
}
