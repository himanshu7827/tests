package in.pms.master.validator;
import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ProposalDocumentMasterModel;
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

public class uploadProposalDocumentValidator implements Validator{
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public uploadProposalDocumentValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProposalDocumentMasterModel.class.equals(paramClass);
	}


	@Override
	public void validate(Object obj, Errors errors) {		
		ProposalDocumentMasterModel proposalDocumentMasterModel = (ProposalDocumentMasterModel) obj;		
		if(proposalDocumentMasterModel.getDocumentTypeId() == null){
			errors.rejectValue("documentTypeId", "dropdown.defaultval.errorMsg", new Object[]{"'documentTypeId'"}, "Value null is not allowed");
			
		}

   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",  "document.description.required");
	nameMatcher = namePattern.matcher((CharSequence) proposalDocumentMasterModel.getDescription());
	if (proposalDocumentMasterModel.getDescription() != null && !nameMatcher.matches()){			
          errors.rejectValue("description", "invalid.Name");
      }
	}
}
