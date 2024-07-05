package in.pms.master.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.transaction.model.CollaborativeOrgDetailsModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Getter
@Setter
public class CollaborativeOrgDetailsValidator implements Validator{
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public CollaborativeOrgDetailsValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return CollaborativeOrgDetailsModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		CollaborativeOrgDetailsModel collaborativeOrgDetailsModel = (CollaborativeOrgDetailsModel) obj;		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organisationName", "organisation.organisationName.required");
		/*nameMatcher = namePattern.matcher(collaborativeOrgDetailsModel.getOrganisationName());
		if (collaborativeOrgDetailsModel.getOrganisationName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("organisationName", "invalid.Name");
	      }*/
		
	
	}


}
