package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.GroupMasterModel;

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
public class GroupMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public GroupMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return GroupMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		GroupMasterModel groupMasterModel = (GroupMasterModel) obj;		
		if(groupMasterModel.getOrganisationId() == 0){
			errors.rejectValue("organisationId", "dropdown.defaultval.errorMsg", new Object[]{"'organisationId'"}, "Value 0 is not allowed");
		}		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "group.groupName.required");
		nameMatcher = namePattern.matcher(groupMasterModel.getGroupName());
		if (groupMasterModel.getGroupName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("groupName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupShortName", "group.groupShortName.required");
		nameMatcher = namePattern.matcher(groupMasterModel.getGroupShortName());
		if (groupMasterModel.getGroupShortName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("groupShortName", "invalid.Name");
	      }
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "group.contactNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupAddress", "group.groupAddress.required");
	}

}
