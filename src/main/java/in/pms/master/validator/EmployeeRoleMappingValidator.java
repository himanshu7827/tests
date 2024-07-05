package in.pms.master.validator;


import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.OrganisationMasterModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Getter
@Setter
public class EmployeeRoleMappingValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	@Override
	public boolean supports(Class<?> paramClass) {		
		return OrganisationMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		EmployeeMasterModel employeeMasterModel = (EmployeeMasterModel) obj;	
		if(employeeMasterModel.getEmployeeId() == 0){
			errors.rejectValue("employeeId", "dropdown.defaultval.errorMsg", new Object[]{"'employeeId'"}, "Value 0 is not allowed");
		}
		List<Long> roleList =employeeMasterModel.getRoles() ;
		if(null == roleList || roleList.size() == 0){

			errors.rejectValue("roles", "Employee_role_Master.roleName.required", new Object[]{"'roles'"}, "Please select Roles");
		}
		
		
	}

}
