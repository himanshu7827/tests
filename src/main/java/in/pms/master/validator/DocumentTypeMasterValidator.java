package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.DocumentTypeMasterModel;
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
public class DocumentTypeMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	
	private Pattern seqPattern;
	private Matcher seqMatcher;

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	private String nameRegex = RegexValidationFile.getValueFromKey("integer.regex");

	public DocumentTypeMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
		seqPattern = Pattern.compile(nameRegex);

	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return DocumentTypeMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		DocumentTypeMasterModel documentTypeMasterModel = (DocumentTypeMasterModel) obj;		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeName", "document.documentName.required");
		nameMatcher = namePattern.matcher(documentTypeMasterModel.getDocTypeName());
		if (documentTypeMasterModel.getDocTypeName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("docTypeName", "invalid.Name");
	      }
		    String strSeq=String.valueOf(documentTypeMasterModel.getDisplaySeq());
		    seqMatcher = seqPattern.matcher(strSeq);
			if (strSeq != null && !seqMatcher.matches()){			
		          errors.rejectValue("displaySeq", "numeric.regex.message");
		      }
	
	}

}
