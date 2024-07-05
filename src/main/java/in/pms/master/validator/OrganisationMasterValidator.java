package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.OrganisationMasterModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Getter
@Setter
public class OrganisationMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String organisationNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public OrganisationMasterValidator(){
		namePattern = Pattern.compile(organisationNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return OrganisationMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		OrganisationMasterModel organisationMasterModel = (OrganisationMasterModel) obj;	
		if(organisationMasterModel.getParentOrganisationId() == 0){
			errors.rejectValue("parentOrganisationId", "dropdown.defaultval.errorMsg", new Object[]{"'parentOrganisationId'"}, "Value 0 is not allowed");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organisationName", "organisation.organisationName.required");
		nameMatcher = namePattern.matcher(organisationMasterModel.getOrganisationName());
		if (organisationMasterModel.getOrganisationName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("organisationName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "organisation.contactNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organisationAddress", "organisation.organisationAddress.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strCode", "organisation.strCode.required");

		
		List<String> thrustAreaList = organisationMasterModel.getThrustAreaData();
		
		if(null == thrustAreaList || thrustAreaList.size() == 0){
			errors.rejectValue("thrustAreaData", "organisation.thrustarea.required", new Object[]{"'thrustAreaData'"}, "Please Select Thrust Area");
		}
	}

}
