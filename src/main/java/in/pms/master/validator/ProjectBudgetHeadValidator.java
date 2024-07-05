package in.pms.master.validator;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.transaction.model.ProjectBudgetForm;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectBudgetHeadValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String nameRegex = RegexValidationFile.getValueFromKey("amount.regex");
	
	public ProjectBudgetHeadValidator(){
		namePattern = Pattern.compile(nameRegex);
	}
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectBudgetForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectBudgetForm projectBudgetForm = (ProjectBudgetForm) obj;	
		if(projectBudgetForm.getNumProjectId() == 0){
			errors.rejectValue("validateError", "dropdown.defaultval.errorMsg", new Object[]{"'numProjectId'"}, "Value 0 is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "Project_Payment_Schedule.amount.required");
		
		String stramount=""+projectBudgetForm.getAmount();
		nameMatcher = namePattern.matcher(stramount);
		if (stramount != null && !nameMatcher.matches()){			
	          errors.rejectValue("validateError", "amount.regex.message");
	      }
		}
	}
