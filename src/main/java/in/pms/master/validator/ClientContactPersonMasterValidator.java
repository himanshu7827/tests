package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;


import in.pms.master.model.ContactPersonMasterModel;

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


public class ClientContactPersonMasterValidator implements Validator {

	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ClientContactPersonMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ContactPersonMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ContactPersonMasterModel contactPersonMasterModel = (ContactPersonMasterModel) obj;		
		if(contactPersonMasterModel.getOrganisationId() == 0){
			errors.rejectValue("organisationId", "dropdown.defaultval.errorMsg", new Object[]{"'organisationId'"}, "Value 0 is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strContactPersonName", "Client_Contact_Person_Master.clientContactPersonName.required");
		nameMatcher = namePattern.matcher(contactPersonMasterModel.getStrContactPersonName());
		if (contactPersonMasterModel.getStrContactPersonName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strContactPersonName", "invalid.Name");
	      }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strDesignation", "Client_Contact_Person_Master.contactPersonDesignation.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strMobileNumber", "Client_Contact_Person_Master.contactPersonMobileNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strEmailId", "Client_Contact_Person_Master.contactPersonEmailId.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strRoles", "Client_Contact_Person_Master.contactPersonRoles.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strResponsibility", "Client_Contact_Person_Master.contactPersonResponsibility.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strOfficeAddress", "Client_Contact_Person_Master.contactPersonOfficeAddress.required");
		
		
	}

	
}
