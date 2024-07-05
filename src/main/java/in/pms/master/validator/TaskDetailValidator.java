package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.global.misc.ResourceBundleFile;
import in.pms.master.model.TaskDetailsModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Getter
@Setter
public class TaskDetailValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String taskDetailRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public TaskDetailValidator(){
		namePattern = Pattern.compile(taskDetailRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return TaskDetailsModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		TaskDetailsModel taskDetailsModel = (TaskDetailsModel) obj;	
		if(taskDetailsModel.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskName", "task.taskName.required");
		nameMatcher = namePattern.matcher(taskDetailsModel.getTaskName());
		if (taskDetailsModel.getTaskName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("taskName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskDescription", "document.description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expectedTime", "task.expectedTime.required");
		
		String priority=","+ResourceBundleFile.getValueFromKey("VALIDPRIORITY")+",";
		if (!priority.contains(","+taskDetailsModel.getPriority()+",")){
			errors.rejectValue("priority", "dropdown.valuenotinrange.errorMsg", new Object[]{"'priority'"}, "Selected Option is not allowed");
		}
	}

}
