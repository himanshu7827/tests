package in.pms.master.validator;


import in.pms.master.model.AnswerMasterModel;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class AnswerMasterValidator implements Validator {
	
	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return AnswerMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		AnswerMasterModel answerMasterModel = (AnswerMasterModel) obj;		
				
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strAnswer", "strAnswer.required");
				
	}

}
