package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.DesignationMasterModel;

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
public class DesignationMasterValidator implements Validator {

	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public DesignationMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return DesignationMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		DesignationMasterModel designationMasterModel = (DesignationMasterModel) obj;		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strDesignationName", "Designation_Master.designationName.required");
		nameMatcher = namePattern.matcher(designationMasterModel.getStrDesignationName());
		
		if (designationMasterModel.getStrDesignationName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strDesignationName", "invalid.Name");
	      }
		
/*		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hierarchy", "empType.hierarchy.required");
*/		
	/*	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strDesignationShortCode", "Designation_Master.designationShortCode.required");*/
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strDesription", "Designation_Master.designationDescription.required");
*/
		
	
		
	
	}
	
}
