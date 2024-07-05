package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.EmpTypeMasterModel;

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
public class EmpTypeMasterValidator implements Validator{

	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public EmpTypeMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return EmpTypeMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		EmpTypeMasterModel empTypeMasterModel = (EmpTypeMasterModel) obj;		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bgColor", "empType.color.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hierarchy", "empType.hierarchy.required");
           
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strEmpTypeName", "Employee_Type_Master.employeeTypeName.required");
		nameMatcher = namePattern.matcher(empTypeMasterModel.getStrEmpTypeName());
		
		if (empTypeMasterModel.getStrEmpTypeName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strEmpTypeName", "invalid.Name");
	      }	
	}	
}
