package in.pms.transaction.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.pms.global.misc.ResourceBundleFile;
import in.pms.transaction.model.PatchTrackerModel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter


public class PatchTrackerValidator implements Validator {
	
	
	
	
	
	@Override
	public boolean supports(Class<?> args) {
	 
		return PatchTrackerModel.class.equals(args);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		PatchTrackerModel patchTrackerModel = (PatchTrackerModel) obj;
		if(patchTrackerModel.getSeverity().equals("0")){
			errors.rejectValue("severity", "dropdown.defaultval.errorMsg", new Object[]{"'severity'"}, "Value 0 is not allowed");
		}
		String validSeverity=","+ResourceBundleFile.getValueFromKey("severity")+",";
		if (!validSeverity.contains(","+patchTrackerModel.getSeverity()+",")){
			errors.rejectValue("severity", "dropdown.valuenotinrange.errorMsg", new Object[]{"'severity'"}, "Selected Option is not allowed");
		}
		
		
		if(patchTrackerModel.getType().equals("0")){
			errors.rejectValue("type", "dropdown.defaultval.errorMsg", new Object[]{"'type'"}, "Value 0 is not allowed");
		}
		String validType=","+ResourceBundleFile.getValueFromKey("type")+",";
		if (!validType.contains(","+patchTrackerModel.getType()+",")){
			errors.rejectValue("type", "dropdown.valuenotinrange.errorMsg", new Object[]{"'type'"}, "Selected Option is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strdescription", "Patch_Tracker.description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strNameOfFiles", "Patch_Tracker.nameOfFiles.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strTeamMembers", "Patch_Tracker.teamMembers.required");
		if(patchTrackerModel.getStage().equals("0")){
			errors.rejectValue("stage", "dropdown.defaultval.errorMsg", new Object[]{"'stage'"}, "Value 0 is not allowed");
		}
		String validStage=","+ResourceBundleFile.getValueFromKey("stage")+",";
		if (!validStage.contains(","+patchTrackerModel.getStage()+",")){
			errors.rejectValue("stage", "dropdown.valuenotinrange.errorMsg", new Object[]{"'stage'"}, "Selected Option is not allowed");
		}
		
	

		
	}
	
}