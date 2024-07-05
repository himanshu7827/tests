package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ActivityMasterModel;

import java.util.List;
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
public class ActivityMasterValidator implements Validator{

	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ActivityMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ActivityMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ActivityMasterModel activityMasterModel = (ActivityMasterModel) obj;		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strActivityName", "activity_Master.activity_Name.required");
		nameMatcher = namePattern.matcher(activityMasterModel.getStrActivityName());
		
		if (activityMasterModel.getStrActivityName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strActivityName", "invalid.Name");
	      }	
		
		List<Long> subActivityId = activityMasterModel.getNumSubActivityId();
		
		if(subActivityId.size() == 0){
			errors.rejectValue("numSubActivityId", "dropdown.defaultval.errorMsg", new Object[]{"'numSubActivityId'"}, "Value 0 is not allowed");
	}	
		
	}	
}
/*List<String> thrustAreaList = organisationMasterModel.getThrustAreaData();

if(null == thrustAreaList || thrustAreaList.size() == 0){
	errors.rejectValue("thrustAreaData", "organisation.thrustarea.required", new Object[]{"'thrustAreaData'"}, "Please Select Thrust Area");
}*/