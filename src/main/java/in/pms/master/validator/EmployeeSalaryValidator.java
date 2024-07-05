package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.EmployeeSalaryModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class EmployeeSalaryValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String employeeNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public EmployeeSalaryValidator(){
		namePattern = Pattern.compile(employeeNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return EmployeeSalaryModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {	
		EmployeeSalaryModel employeeSalaryModel = (EmployeeSalaryModel) obj;		
		if(employeeSalaryModel.getEmployeeId() == 0){
			errors.rejectValue("employeeId", "dropdown.defaultval.errorMsg", new Object[]{"'employeeId'"}, "Value 0 is not allowed");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salary", "employee.employeeSalary.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "employee.startDate.required");
	}

}
