package in.pms.transaction.validator;


import in.pms.transaction.model.ExitInterviewModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class ExitInterviewValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ExitInterviewModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {	
		ExitInterviewModel exitInterviewModel = (ExitInterviewModel) obj;	

		
		
	}

}
