package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ManpowerRequirementModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Getter
@Setter
public class ManpowerRequirementValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String nameRegex = RegexValidationFile.getValueFromKey("numeric.regex");
	
	public ManpowerRequirementValidator(){
		namePattern = Pattern.compile(nameRegex);
	}	
	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ManpowerRequirementModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ManpowerRequirementModel manpowerRequirementModel = (ManpowerRequirementModel) obj;	
		if(manpowerRequirementModel.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		if(manpowerRequirementModel.getDesignationId() == 0){
			errors.rejectValue("designationId", "dropdown.defaultval.errorMsg", new Object[]{"'designationId'"}, "Value 0 is not allowed");
		}
		
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "count", "manpower.count.required");
	    String strCount=String.valueOf(manpowerRequirementModel.getCount());
		nameMatcher = namePattern.matcher(strCount);
		if (strCount != null && !nameMatcher.matches()){			
	          errors.rejectValue("count", "numeric.regex.message");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "involvement", "manpower.involvement.required");
	    String strinvolvement=String.valueOf(manpowerRequirementModel.getInvolvement());
		nameMatcher = namePattern.matcher(strinvolvement);
		if (strinvolvement != null && !nameMatcher.matches()){			
	          errors.rejectValue("involvement", "numeric.regex.message");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "employee.startDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "manpower.enddate.required");		
	}

}
