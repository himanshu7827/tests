package in.pms.master.validator;

import in.pms.master.model.RoleMasterModel;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RoleMasterValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return RoleMasterModel.class.equals(paramClass);		
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoleMasterModel roleMasterModel =  (RoleMasterModel) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName", "Employee_role_Master.roleName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleShortName", "Employee_role_Master.roleShortName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bgColor", "role.roleColor.required");

		if(roleMasterModel.getAccessLevel().equals("0")){
			errors.rejectValue("accessLevel", "dropdown.defaultval.errorMsg", new Object[]{"'accessLevel'"}, "Value 0 is not allowed");
		}
		
		if(!(roleMasterModel.getAccessLevel().equals("All") || roleMasterModel.getAccessLevel().equals("Organisation") || roleMasterModel.getAccessLevel().equals("Group") || roleMasterModel.getAccessLevel().equals("Application"))){
			errors.rejectValue("accessLevel", "dropdown.valuenotinrange.errorMsg", new Object[]{"'accessLevel'"}, "Selected Option is not allowed");

		}		
	}

}
