package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.DocStageMasterModel;

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


public class DocStageMasterValidator implements Validator{

	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public DocStageMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return DocStageMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		DocStageMasterModel docStageMasterModel = (DocStageMasterModel) obj;		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strStageName", "Document_Stage_Master.stageName.required");
		nameMatcher = namePattern.matcher(docStageMasterModel.getStrStageName());
		
		if (docStageMasterModel.getStrStageName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strStageName", "invalid.Name");
	      }
		
	
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strStageDescription", "Document_Stage_Master.stageDescription.required")*/;
	
		
	
		
	
	}
	
	
}
