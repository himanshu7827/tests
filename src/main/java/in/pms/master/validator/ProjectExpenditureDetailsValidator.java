package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;


import in.pms.master.model.ProjectExpenditureDetailsModel;

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
public class ProjectExpenditureDetailsValidator implements Validator{

	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String amountRegex = RegexValidationFile.getValueFromKey("amount.regex");
	
	public ProjectExpenditureDetailsValidator(){
		namePattern = Pattern.compile(amountRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectExpenditureDetailsModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectExpenditureDetailsModel projectExpenditureDetailsModel = (ProjectExpenditureDetailsModel) obj;		
		if(projectExpenditureDetailsModel.getNumProjectId() == 0 && projectExpenditureDetailsModel.getNumBudgetHeadId() == 0 && projectExpenditureDetailsModel.getNumExpenditureHeadId() == 0){
			errors.rejectValue("numProjectId", "dropdown.defaultval.errorMsg", new Object[]{"'numProjectId'"}, "Value 0 is not allowed");
			errors.rejectValue("numBudgetHeadId", "dropdown.defaultval.errorMsg", new Object[]{"'numBudgetHeadId'"}, "Value 0 is not allowed");
			errors.rejectValue("numExpenditureHeadId", "dropdown.defaultval.errorMsg", new Object[]{"'numExpenditureHeadId'"}, "Value 0 is not allowed");
			
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numExpenditureAmount", "project_Expenditure_Details.expenditure_Amount.required");
		 String stramount=String.valueOf(projectExpenditureDetailsModel.getNumExpenditureAmount());
			nameMatcher = namePattern.matcher(stramount);
			if (stramount != null && !nameMatcher.matches()){			
		          errors.rejectValue("numExpenditureAmount", "amount.regex.message");
		      }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtExpenditureDate", "project_Expenditure_Details.expenditure_Date.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strExpenditureDescription", "project_Expenditure_Details.expenditure_Description.required");
	}
	
}
