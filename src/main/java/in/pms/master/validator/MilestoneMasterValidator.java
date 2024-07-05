package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;

import in.pms.master.model.ProjectMilestoneForm;

import java.util.ArrayList;
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
public class MilestoneMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String milestoneNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public MilestoneMasterValidator(){
		namePattern = Pattern.compile(milestoneNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectMilestoneForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectMilestoneForm projectMilestoneForm = (ProjectMilestoneForm) obj;	
		if(projectMilestoneForm.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "milestoneName", "milestone.milestoneName.required");
		nameMatcher = namePattern.matcher(projectMilestoneForm.getMilestoneName());
		if (projectMilestoneForm.getMilestoneName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("milestoneName", "invalid.Name");
	      }
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expectedStartDate", "milestone.expectedStartDate.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expectedCompletionDate", "milestone.expectedCompletionDate.required");

	
	}
	
}
