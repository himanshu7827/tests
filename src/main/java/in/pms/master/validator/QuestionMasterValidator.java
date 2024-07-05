package in.pms.master.validator;


import in.pms.master.model.QuestionMasterModel;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class QuestionMasterValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return QuestionMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {	
		QuestionMasterModel questionMasterModel = (QuestionMasterModel) obj;	

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strQuestions", "question.required");
		
		List<Long> answerList = questionMasterModel.getAnswerId();
		
		if(answerList.size() == 0){
			errors.rejectValue("answerId", "master.answer.required", new Object[]{"'answerId'"}, "Please Select answer");
		}	
	}

}
