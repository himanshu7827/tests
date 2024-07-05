package in.pms.transaction.validator;

import in.pms.transaction.model.ManpowerUtilizationDetailsModel;
import in.pms.transaction.model.ManpowerUtilizationModel;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ManpowerUtilizationValidator implements Validator{

	@Override
	public boolean supports(Class<?> paramClass) {
		return ManpowerUtilizationModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ManpowerUtilizationModel model = (ManpowerUtilizationModel) obj;
		
		if(model.getMonth() == 0 || model.getYear() == 0){
			errors.rejectValue("month", "dropdown.defaultval.errorMsg", new Object[]{"'month'"}, "Please Select Month");
		}
		
		if(model.getEmployeeId() == 0){
			errors.rejectValue("employeeId", "dropdown.defaultval.errorMsg", new Object[]{"'employeeId'"}, "Please Select Employee");			
		}
		
		List<ManpowerUtilizationDetailsModel> utilizationDetailsList = model.getUtilizationDetails();
		float totalUtilization = 0;
		float totalSalary= 0;
		for(int i=0;i<utilizationDetailsList.size();i++){
			ManpowerUtilizationDetailsModel detailModel = utilizationDetailsList.get(i);
			if(detailModel.getProjectId() == 0){
				errors.rejectValue("utilizationError", "form.project.name.required", new Object[]{"'utilizationError'"}, "Project Name is required");
			}
			totalUtilization+=detailModel.getUtilization();		
			totalSalary+=detailModel.getSalaryInProject();
		}
		if(totalUtilization>100){
			errors.rejectValue("utilizationError", "form.utilization.max.limit", new Object[]{"'utilizationError'"}, "Total Utilization can not be greater than 100");
		}else if(totalUtilization == 0){
			errors.rejectValue("utilizationError", "form.utilization.min.limit", new Object[]{"'utilizationError'"}, "Please enter Utilization for any of project");
		}
		
		if(totalSalary> model.getSalaryByAuthority()){
			errors.rejectValue("salaryByAuthority", "form.total.sal.max.limit", new Object[]{"'salaryByAuthority'"}, "Salary Range Exceeds");
		}else if (model.getSalaryByAuthority() == 0){			
			errors.rejectValue("salaryByAuthority", "form.salary.authority.value", new Object[]{"'salaryByAuthority'"}, "Change in Salary should be non zero");
		}
		
	}

}
