package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.SubActivityMasterModel;

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
public class SubActivityMasterValidator implements Validator {


	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public SubActivityMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return SubActivityMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		SubActivityMasterModel subActivityMasterModel = (SubActivityMasterModel) obj;		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strSubActivityName", "subActivity_Master.subActivity_Name.required");
		nameMatcher = namePattern.matcher(subActivityMasterModel.getStrSubActivityName());
		
		if (subActivityMasterModel.getStrSubActivityName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strSubActivityName", "invalid.Name");
	      }	
		
		/*if(subActivityMasterModel.getNumActivityId() == 0){
			errors.rejectValue("numActivityId", "dropdown.defaultval.errorMsg", new Object[]{"'numActivityId'"}, "Value 0 is not allowed");
	}	*/
}
}