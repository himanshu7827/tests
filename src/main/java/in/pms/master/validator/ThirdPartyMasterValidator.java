package in.pms.master.validator;
import in.pms.master.model.ThirdPartyMasterModel;
import in.pms.global.misc.RegexValidationFile;
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

public class ThirdPartyMasterValidator implements Validator{

	private Pattern namePattern;
	private Matcher nameMatcher;	
	private Pattern addressPattern;
	private Matcher addressMatcher;
	private Pattern mobilePattern;
	private Matcher mobileMatcher;
	private Pattern contactPattern;
	private Matcher contactMatcher;

	
	private String nameRegex = RegexValidationFile.getValueFromKey("name.regex");
	private String addressRegex = RegexValidationFile.getValueFromKey("address.regex");
	private String mobileRegex = RegexValidationFile.getValueFromKey("mobileno.regex");
	private String numericRegex = RegexValidationFile.getValueFromKey("numeric.regex");

	public ThirdPartyMasterValidator(){
		namePattern = Pattern.compile(nameRegex);
		addressPattern = Pattern.compile(addressRegex);
		mobilePattern = Pattern.compile(mobileRegex);
		contactPattern = Pattern.compile(numericRegex);

	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ThirdPartyMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ThirdPartyMasterModel thirdPartyMasterModel = (ThirdPartyMasterModel) obj;		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agencyName", "thirdParty.agency.name.required");
		nameMatcher = namePattern.matcher(thirdPartyMasterModel.getAgencyName());
		if (thirdPartyMasterModel.getAgencyName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("agencyName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agencyAddress", "thirdParty.agency.address.required");
		addressMatcher = addressPattern.matcher(thirdPartyMasterModel.getAgencyAddress());
		if (thirdPartyMasterModel.getAgencyAddress() != null && !addressMatcher.matches()){			
	          errors.rejectValue("agencyAddress", "invalid.Name");
	      }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", "thirdParty.mobile.number.required");
		mobileMatcher = mobilePattern.matcher(thirdPartyMasterModel.getMobileNumber());
		if (thirdPartyMasterModel.getMobileNumber() != null && !mobileMatcher.matches()){			
	          errors.rejectValue("mobileNumber", "invalid.Number");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "thirdParty.contact.number.required");
		contactMatcher = contactPattern.matcher(thirdPartyMasterModel.getContactNumber());
		if (thirdPartyMasterModel.getContactNumber() != null && !contactMatcher.matches()){			
	          errors.rejectValue("contactNumber", "invalid.Number");
	      }
	
	}

}
