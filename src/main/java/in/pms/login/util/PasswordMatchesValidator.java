package in.pms.login.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import in.pms.master.model.EmployeeMasterModel;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final EmployeeMasterModel user = (EmployeeMasterModel) obj;
       // return user.getPassword().equals(user.getMatchingPassword());
        return true;
    }

}
