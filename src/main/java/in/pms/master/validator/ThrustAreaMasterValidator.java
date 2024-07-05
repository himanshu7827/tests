package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ThrustAreaMasterForm;

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
public class ThrustAreaMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String thrustAreaNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ThrustAreaMasterValidator(){
		namePattern = Pattern.compile(thrustAreaNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ThrustAreaMasterForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ThrustAreaMasterForm thrustAreaMasterForm = (ThrustAreaMasterForm) obj;		
				
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strThrustAreaName", "thrust.thrustAreaName.required");
		nameMatcher = namePattern.matcher(thrustAreaMasterForm.getStrThrustAreaName());
		if (thrustAreaMasterForm.getStrThrustAreaName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strThrustAreaName", "invalid.Name");
	      }
		
	}

}
