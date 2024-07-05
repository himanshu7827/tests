package in.pms.master.controller;



import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeSalaryModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeSalaryService;
import in.pms.master.validator.EmployeeSalaryValidator;
import in.pms.transaction.model.ExitInterviewModel;
import in.pms.transaction.model.ManpowerUtilizationModel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
public class EmployeeSalaryController {

	@Autowired
	EmployeeSalaryService employeeSalaryService;
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@RequestMapping("/EmployeeSalaryMaster")
	public String getAllEmpSalMaster(HttpServletRequest request, EmployeeSalaryModel employeeSalaryModel){		
		List<EmployeeMasterModel> list = employeeMasterService.getAllActiveEmployeeMasterDomain();
		request.setAttribute("data", list);		
		return "EmployeeSalaryMaster";
	}
	
	@RequestMapping(value="/EmployeeSalaryData",method=RequestMethod.POST)
	public @ResponseBody List<EmployeeSalaryModel> getdata(@RequestParam("employeeId") long empId, HttpServletRequest request){
		List<EmployeeSalaryModel> data = employeeSalaryService.getAllEmployeeSalData(empId);
		return data;
	}
	
	@RequestMapping(value="/SaveEmployeeSalaryMaster",method=RequestMethod.POST)	
	public ModelAndView saveEmployeeSalMaster(HttpServletRequest request, EmployeeSalaryModel employeeSalaryModel,BindingResult bindingResult, RedirectAttributes redirectAttributes){		
		ModelAndView mav = new ModelAndView();
		new EmployeeSalaryValidator().validate(employeeSalaryModel, bindingResult);
	      if (bindingResult.hasErrors()) {
	  			
	    	  mav.setViewName("EmployeeSalaryMaster");
	          return mav;
	      }
		
		long id = employeeSalaryService.saveEmployeeSalData(employeeSalaryModel);
		if(id>0){
			if(employeeSalaryModel.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",  "Employee Salary details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}
			else{
				redirectAttributes.addFlashAttribute("message",  "Employee Salary details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}
		}
			else if(id==-1){
				redirectAttributes.addFlashAttribute("message",  "End Date needs to be filled ");	
				redirectAttributes.addFlashAttribute("status", "success");
			}
			else if(id==-2){
				redirectAttributes.addFlashAttribute("message",  "Employee salary already exist for selected time interval");	
				redirectAttributes.addFlashAttribute("status", "success");
			}
			
			
			/*else{
				redirectAttributes.addFlashAttribute("message",  "Employee Salary details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}	*/			
		
		mav.setViewName("redirect:/mst/EmployeeSalaryMaster");
		
		return mav;
	}
	
	@RequestMapping(value="/getEmployeeSalary",method=RequestMethod.POST)	
	public @ResponseBody double getEmployeeSalary(ManpowerUtilizationModel manpowerUtilizationModel){
		return employeeSalaryService.getEmployeeSalary(manpowerUtilizationModel.getEmployeeId(), manpowerUtilizationModel.getMonth(), manpowerUtilizationModel.getYear());
	}
	
	@RequestMapping(value="importSalaryDetail",method= RequestMethod.POST)
	@ResponseBody
	public List<String> saveSalaryDetail(EmployeeSalaryModel employeeSalaryModel,HttpServletRequest request){	
		List<String> result = employeeSalaryService.saveEmployeeSalDetails(employeeSalaryModel);
		return result; 		
	}

	
}
