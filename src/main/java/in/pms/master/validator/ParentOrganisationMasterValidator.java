package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ParentOrganisationMasterModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Getter
@Setter
public class ParentOrganisationMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String organisationNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ParentOrganisationMasterValidator(){
		namePattern = Pattern.compile(organisationNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ParentOrganisationMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ParentOrganisationMasterModel parentOrganisationMasterModel = (ParentOrganisationMasterModel) obj;	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organisationName", "organisation.organisationName.required");
		
		nameMatcher = namePattern.matcher(parentOrganisationMasterModel.getOrganisationName());
		if (parentOrganisationMasterModel.getOrganisationName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("organisationName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "organisation.contactNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organisationAddress", "organisation.organisationAddress.required");
	}

}
