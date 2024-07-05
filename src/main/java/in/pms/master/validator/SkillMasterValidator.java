package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.SkillMasterModel;

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
public class SkillMasterValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String skillNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public SkillMasterValidator(){
		namePattern = Pattern.compile(skillNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return SkillMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		SkillMasterModel skillMasterModel = (SkillMasterModel) obj;		
				
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strSkillName", "skill.skillName.required");
		nameMatcher = namePattern.matcher(skillMasterModel.getStrSkillName());
		if (skillMasterModel.getStrSkillName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("strSkillName", "invalid.Name");
	      }
		
	}

}
