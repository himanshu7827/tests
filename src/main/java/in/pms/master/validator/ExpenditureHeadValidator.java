package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ExpenditureHeadModel;

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
public class ExpenditureHeadValidator implements Validator{
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ExpenditureHeadValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ExpenditureHeadModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ExpenditureHeadModel expenditureHeadModel = (ExpenditureHeadModel) obj;		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strExpenditureHeadName", "expenditure_Head.expenditure_Head_Name.required");
		nameMatcher = namePattern.matcher(expenditureHeadModel.getStrExpenditureHeadName());
		
		if (expenditureHeadModel.getStrExpenditureHeadName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strExpenditureHeadName", "invalid.Name");
	      }	
	}	
}
