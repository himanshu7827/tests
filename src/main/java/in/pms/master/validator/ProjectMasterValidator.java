package in.pms.master.validator;

import in.pms.master.model.ProjectMasterForm;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProjectMasterValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return ProjectMasterForm.class.equals(paramClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProjectMasterForm projectMasterForm =  (ProjectMasterForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strProjectName", "Project_Module_Master.projectName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workOrderDate", "project.workorderate.required");
		
		if(projectMasterForm.getStrProjectStatus().equals("0")){
			errors.rejectValue("strProjectStatus", "dropdown.defaultval.errorMsg", new Object[]{"'strProjectStatus'"}, "Value 0 is not allowed");
		}
		
		if(!(projectMasterForm.getStrProjectStatus().equals("Ongoing") || projectMasterForm.getStrProjectStatus().equals("Completed") || projectMasterForm.getStrProjectStatus().equals("Terminated") || projectMasterForm.getStrProjectStatus().equals("Withdrawn"))){
			errors.rejectValue("strProjectStatus", "dropdown.valuenotinrange.errorMsg", new Object[]{"'strProjectStatus'"}, "Selected Option is not allowed");

		}	
		/*if(projectMasterForm.getProjectType().equals("0")){
			errors.rejectValue("projectType", "dropdown.defaultval.errorMsg", new Object[]{"'projectType'"}, "Value 0 is not allowed");
		}*/
		
		/*if(!(projectMasterForm.getProjectType().equals("Commercial") || projectMasterForm.getProjectType().equals("R&D"))){
			errors.rejectValue("projectType", "dropdown.valuenotinrange.errorMsg", new Object[]{"'projectType'"}, "Selected Option is not allowed");
		}*/		
	}

}
