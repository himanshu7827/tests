package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.ManpowerRequirementDao;
import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.RoleMasterService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Getter
@Setter
@Component("EmployeeRoleMasterValidator")
public class EmployeeRoleMasterValidator implements Validator {

	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	ManpowerRequirementDao manpowerRequirementDao;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public EmployeeRoleMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return EmployeeRoleMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		EmployeeRoleMasterModel employeeRoleMasterModel = (EmployeeRoleMasterModel) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strStartDate", "employee_Role.start_Date.required");

		if(employeeRoleMasterModel.getNumEmpId() == 0){
			errors.rejectValue("numEmpId", "dropdown.defaultval.errorMsg", new Object[]{"'numEmpId'"}, "Value 0 is not allowed");
		}
				
		if(employeeRoleMasterModel.getNumRoleId() == 0){
			errors.rejectValue("numRoleId", "dropdown.defaultval.errorMsg", new Object[]{"'numRoleId'"}, "Value 0 is not allowed");
		}else{
			RoleMasterModel roleMasterModel = roleMasterService.getRoleById(employeeRoleMasterModel.getNumRoleId());
			if(roleMasterModel.getAccessLevel().equals("Application")){

				if(employeeRoleMasterModel.getNumOrganisationId() == 0){
					errors.rejectValue("numOrganisationId", "dropdown.defaultval.errorMsg", new Object[]{"'numOrganisationId'"}, "Value 0 is not allowed");
				}
				
				if(employeeRoleMasterModel.getNumGroupId() == 0){
					errors.rejectValue("numGroupId", "dropdown.defaultval.errorMsg", new Object[]{"'numGroupId'"}, "Value 0 is not allowed");
				}
				if(employeeRoleMasterModel.getNumProjectId() == 0){
					errors.rejectValue("numProjectId", "dropdown.defaultval.errorMsg", new Object[]{"'numProjectId'"}, "Value 0 is not allowed");
				}
				
				if(employeeRoleMasterModel.getNumManReqId() == 0){
					errors.rejectValue("numManReqId", "dropdown.defaultval.errorMsg", new Object[]{"'numManReqId'"}, "Value 0 is not allowed");
				}			
				
			} else if(roleMasterModel.getAccessLevel().equals("Group")){
				employeeRoleMasterModel.setInvolvement("0");
				if(employeeRoleMasterModel.getNumOrganisationId() == 0){
					errors.rejectValue("numOrganisationId", "dropdown.defaultval.errorMsg", new Object[]{"'numOrganisationId'"}, "Value 0 is not allowed");
				}
				
				if(employeeRoleMasterModel.getNumGroupId() == 0){
					errors.rejectValue("numGroupId", "dropdown.defaultval.errorMsg", new Object[]{"'numGroupId'"}, "Value 0 is not allowed");
				}				
			}else if(roleMasterModel.getAccessLevel().equals("Organisation")){
				employeeRoleMasterModel.setInvolvement("0");
				if(employeeRoleMasterModel.getNumOrganisationId() == 0){
					errors.rejectValue("numOrganisationId", "dropdown.defaultval.errorMsg", new Object[]{"'numOrganisationId'"}, "Value 0 is not allowed");
				}		
						
			}
			
		
		
	}
	}
	
}
