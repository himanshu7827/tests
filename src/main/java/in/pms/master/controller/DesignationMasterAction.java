/*
package in.pms.master.controller;

//import java.util.List;

import in.pms.global.dao.GlobalDao;
import in.pms.global.domain.DesignationMaster;
import in.pms.global.model.DesignationForm;
import in.pms.global.service.GlobalService;

//import in.pms.global.service.GlobalService;
import in.pms.master.dao.GlobalDao;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.model.DesignationForm;
import in.pms.master.service.GlobalService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class DesignationMasterAction {

	
	@Autowired
	GlobalDao gDao; 
	@Autowired
	GlobalService gService;
	
	@RequestMapping(value = "/DesignationMasterForm", method = RequestMethod.GET)
	public String dashboard(@ModelAttribute("DesigForm")DesignationForm DesigForm , HttpServletRequest request) {

		List<DesignationMaster> designationlist = gService.getDesignationlist();
		//int sizelist=designationlist.size();
		//System.out.println("designationlist=========="+sizelist);
		//request.setAttribute("sizelist", sizelist);
		request.setAttribute("designationlist", designationlist);

		
	return "DesignationMasterForm";
}

		@RequestMapping(value = "/saveDesignation", method = RequestMethod.POST)
		public String saveDesignation(@ModelAttribute("DesigForm") DesignationForm DesigForm, BindingResult result, HttpServletRequest request) {
			System.out.println("in save");
			
			
			 List<String> ByPassVariable = new ArrayList<String>();
				int checkErrorValue=0;
			List<String> errorMessage = new ArrayList<String>();
			
			ByPassVariable.add("fileUpload");
			ByPassVariable.add("fileUpload1");
			ByPassVariable.add("fileUpload2");
			ByPassVariable.add("fileUpload3");
			if (result.hasErrors()) {
				System.out.println("Inside has Error ");
				List<ObjectError> errorList = result.getAllErrors();
				
				for(int i=0;i<errorList.size();i++)
				{
					String codes = errorList.get(i).getCodes()[1].substring(errorList.get(i).getCodes()[1].lastIndexOf(".")+1, errorList.get(i).getCodes()[1].length());
					if(!ByPassVariable.contains(codes))
					{
					checkErrorValue=1;
					errorMessage.add(errorList.get(i).getDefaultMessage());
					}
				System.out.println("..error..."+errorList.get(i));
				//System.out.println("..error..."+errorList.get(i).getDefaultMessage());
				System.out.println("..error..."+errorList.get(i).getObjectName());
				//System.out.println("..error..."+errorList.get(i).getArguments());
				System.out.println("..error..."+errorList.get(i).getCodes());
				
				}
				
			}
			
			gService.saveDesignation(DesigForm);
			System.out.println("action");
			return "redirect:/DesignationMasterForm";}
		
		//}
		
		@RequestMapping(value = "/updateDesignation", method = RequestMethod.POST)
		public String updateDesignation(@ModelAttribute("DesigForm")DesignationForm DesigForm, BindingResult result, HttpServletRequest request) {
			gService.updateDesignation(DesigForm);
			return "redirect:/DesignationMasterForm";
		}
		
		@RequestMapping(value = "/deleteDesignation",  method = RequestMethod.POST)
		public String deleteDesignation(DesignationForm DesigForm, HttpServletRequest request)
	    {   
			System.out.println("inside delete = "+DesigForm.getCheckbox());
			gService.deleteDesignation(DesigForm);
			return "redirect:/DesignationMasterForm";
	    }
		
		
		@RequestMapping(value = "/CheckExistUser", method = RequestMethod.POST)
		public @ResponseBody DesignationForm checkExistUser(DesignationForm DesignationForm,HttpServletRequest request, HttpServletResponse response) {
			DesignationForm regist = new DesignationForm();
			String username = request.getParameter("username");
			regist = gService.getExistUser(username.toUpperCase());
			return regist;
		}
		
		
		
		
}

*/