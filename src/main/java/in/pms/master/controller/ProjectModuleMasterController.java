package in.pms.master.controller;




import in.pms.global.service.EncryptionService;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectModuleMasterModel;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectModuleMasterService;


import in.pms.master.validator.ProjectModuleMasterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ProjectModuleMasterController {
	
	@Autowired
	ProjectModuleMasterService projectModuleMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping("/projectModuleMaster")
	public String projectModuleMaster(ProjectModuleMasterModel projectModuleMasterModel,HttpServletRequest request,Model map){		
		Map md = map.asMap();
		List<ProjectMasterModel> projectList =new ArrayList<ProjectMasterModel>();
		int projectId=0;
		if(null != request.getParameter("encProjectId")){
			try{
			String encProjectId = request.getParameter("encProjectId");
			String strProjectId = encryptionService.dcrypt(encProjectId);
			projectId = Integer.parseInt(strProjectId);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	      if(md.get("projectId")!=null){
	    	  projectId=Integer.parseInt(md.get("projectId").toString());
	      }
	      if(projectId != 0){
	    	  ProjectMasterModel projectData = projectMasterService.getProjectMasterModelById(projectId);	    	  
	  			request.setAttribute("projectData", projectData);
	    	  List<ProjectModuleMasterModel> list = projectModuleMasterService.getAllProjectModuleMasterDomain();
				request.setAttribute("data", list);   
				request.setAttribute("projectId", projectId);
				request.setAttribute("encProjectId", encryptionService.encrypt(""+projectId));
	      }else{
				 return "redirect:/accessDenied"; 
			  }
		return "projectModuleMaster";

	}

	@RequestMapping(value="/saveUpdateProjectModuleMaster",method=RequestMethod.POST)	
		public ModelAndView saveUpdateProjectModuleMaster(ProjectModuleMasterModel projectModuleMasterModel,HttpServletRequest request,RedirectAttributes redirectAttributes, BindingResult bindingResult){		
			ModelAndView mav = new ModelAndView();
			
			new ProjectModuleMasterValidator().validate(projectModuleMasterModel, bindingResult);
		      if (bindingResult.hasErrors()) {
		    	  request.setAttribute("showForm", 1);
		    	  List<ProjectModuleMasterModel> list = projectModuleMasterService.getAllActiveProjectModuleMasterDomain();

		  		request.setAttribute("data", list);
		  		redirectAttributes.addFlashAttribute("projectId",projectModuleMasterModel.getProjectId());
		    	  mav.setViewName("projectModuleMaster");
		          return mav;
		      }
			
			
			String strDuplicateCheck = projectModuleMasterService.checkDuplicateProjectModuleName(projectModuleMasterModel);
			if(null == strDuplicateCheck){
			long id = projectModuleMasterService.saveUpdateProjectModuleMaster(projectModuleMasterModel);
			if(id>0){
				if(projectModuleMasterModel.getNumId()==0){
					redirectAttributes.addFlashAttribute("message",  "Project Module record saved Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}else{
					redirectAttributes.addFlashAttribute("message",  "Project Module record updated Successfully with Id "+id);	
					redirectAttributes.addFlashAttribute("status", "success");
				}					
			}
			}else{				
				redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
				redirectAttributes.addFlashAttribute("status", "error");
			}
	  		redirectAttributes.addFlashAttribute("projectId",projectModuleMasterModel.getProjectId());

			mav.setViewName("redirect:/mst/projectModuleMaster");
			return mav;
		}

		@RequestMapping(value = "/deletetProjectModule",  method = RequestMethod.POST)
		public String deleteProjectModule(ProjectModuleMasterModel projectModuleMasterModel, HttpServletRequest request,RedirectAttributes redirectAttributes)
	    {	
					  
					  try
				    	{				
				    		long result= projectModuleMasterService.deleteProjectModule(projectModuleMasterModel);
				    		if(result !=-1){
				    			redirectAttributes.addFlashAttribute("message",  "Project Module record deleted Successfully");	
				    			redirectAttributes.addFlashAttribute("status", "success");
				    		}else{
				    			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
				    			redirectAttributes.addFlashAttribute("status", "error");
				    		}
				    		List<ProjectModuleMasterModel> list = projectModuleMasterService.getAllProjectModuleMasterDomain();
							request.setAttribute("data", list);        
		                    
		                    
				    	}
				    	catch(Exception e){
			          	log.error(e.getCause()+"\t"+e.getMessage()+"\t"+e.getStackTrace());

				    	}		    								
		
				  		redirectAttributes.addFlashAttribute("projectId",projectModuleMasterModel.getProjectId());

			return "redirect:/mst/projectModuleMaster";
	    }
		
		//ajax call get Project Module using ProjectId
		@RequestMapping(value="/getProjectModuleByProjectId",method=RequestMethod.POST,produces="application/json")
		public @ResponseBody List<ProjectModuleMasterModel> getProjectModuleByProjectId(@RequestParam("projectId") long projectId,ProjectModuleMasterModel projectModuleMasterModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		
			
				List<ProjectModuleMasterModel> datalist = new ArrayList<ProjectModuleMasterModel>();
			
				
				datalist = projectModuleMasterService.getProjectModuleByProjectId(projectId);
				request.setAttribute("data1", datalist);	
			return datalist; 
		}

		
	}

