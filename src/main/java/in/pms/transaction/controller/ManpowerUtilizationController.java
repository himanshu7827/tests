package in.pms.transaction.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.global.service.EncryptionService;
import in.pms.master.model.EmployeeSalaryModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.transaction.model.ManpowerUtilizationModel;
import in.pms.transaction.service.ManpowerUtilizationService;
import in.pms.transaction.validator.ManpowerUtilizationValidator;



@Controller
public class ManpowerUtilizationController {
	
	@Autowired
	ManpowerUtilizationService manpowerUtilizationService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@RequestMapping("/manpowerUtilization")
	public String getAllClientMaster(HttpServletRequest request, ManpowerUtilizationModel manpowerUtilizationModel){
		manpowerUtilizationService.getUtilAccess();
		String referer = request.getHeader("referer");
		if(null != referer && referer.contains("projectDetails")){
			request.setAttribute("referer", referer);
		}
		return "manpowerUtilization";
	}

	@RequestMapping(value="getUtilizationForEmployee",method=RequestMethod.POST)
	public @ResponseBody List<Object[]> getUtilizationForEmployee(ManpowerUtilizationModel manpowerUtilizationModel){
		return manpowerUtilizationService.getUtilizationForEmployee(manpowerUtilizationModel.getEmployeeId(), manpowerUtilizationModel.getMonth(), manpowerUtilizationModel.getYear());
	}
	

	@RequestMapping(value="saveMonthlyUtilizationForEmployee",method=RequestMethod.POST)
	@ResponseBody
	public  List<String> saveMonthlyUtilizationForEmployee(ManpowerUtilizationModel manpowerUtilizationModel,RedirectAttributes redirectAttributes, BindingResult bindingResult){	
		List<String> result = new ArrayList<String>();		
		new ManpowerUtilizationValidator().validate(manpowerUtilizationModel, bindingResult);
		
	      if (bindingResult.hasErrors()) {
	    	  for (Object object : bindingResult.getAllErrors()) {
	              if(object instanceof FieldError) {
	                  FieldError fieldError = (FieldError) object;	                  
	                  result.add(fieldError.getField() + "#" + fieldError.getDefaultMessage());
	              }
	    	  }
	        return result;
	      }
		
		long id = manpowerUtilizationService.mergeManpowerUtilization(manpowerUtilizationModel);
		if(id>0){
			result.add("Success");
		}/*else{
			result.add("Fail");
		}*/
		return result;		
	}
	
	@RequestMapping(value="/getManpowerUtilization",method=RequestMethod.POST)
	public @ResponseBody List<ManpowerUtilizationModel> getManpowerUtilization(ManpowerUtilizationModel manpowerUtilizationModel , BindingResult result,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		int month= manpowerUtilizationModel.getMonth();
		int year = manpowerUtilizationModel.getYear();
		//System.out.println(month);
		//System.out.println(year);
		if(month==0){
			Calendar calendar= GregorianCalendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			year= calendar.get(Calendar.YEAR);
			if(month==0){
				month= 12;
				year=year-1;
			}
			
		}
		
		List<ManpowerUtilizationModel> datalist = new ArrayList<ManpowerUtilizationModel>();
		
		if(null != manpowerUtilizationModel.getEncProjectId()){
			//System.out.println(manpowerUtilizationModel.getEncProjectId());
			String strProjectId = encryptionService.dcrypt(manpowerUtilizationModel.getEncProjectId());
			long projectId = Long.parseLong(strProjectId);
			//System.out.println(projectId);
			datalist = manpowerUtilizationService.getManpowerUtilization(projectId, month, year);
		}else{
			
			datalist = manpowerUtilizationService.getManpowerUtilization(manpowerUtilizationModel.getProjectId(), month, year);

		}
		
		request.setAttribute("data1", datalist);	
	return datalist; 
		/*String projectId = encryptionService.dcrypt(manpowerUtilizationModel.getEncProjectId());
		return manpowerUtilizationService.getManpowerUtilization(projectId, month, year);*/
}	
	
	@RequestMapping(value="importUtilizationDetail",method= RequestMethod.POST)
	@ResponseBody
	public List<String> saveUtilizationDetail(ManpowerUtilizationModel manpowerUtilizationModel,HttpServletRequest request){	
		List<String> result = manpowerUtilizationService.saveUtilizationDetails(manpowerUtilizationModel);
		return result; 		
	}
	
	
}
