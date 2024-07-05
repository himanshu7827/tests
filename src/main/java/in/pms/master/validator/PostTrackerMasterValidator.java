package in.pms.master.validator;

import in.pms.global.misc.ResourceBundleFile;
import in.pms.master.model.PostTrackerMasterModel;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PostTrackerMasterValidator implements Validator {
	
	

	@Override
	public boolean supports(Class<?> arg0) {
		return PostTrackerMasterModel.class.equals(arg0);
	}
	@Override
	public void validate(Object obj, Errors errors) {
		PostTrackerMasterModel postTrackerMasterModel = (PostTrackerMasterModel) obj;
		if (postTrackerMasterModel.getVacancyType().equals("0")) {
			errors.rejectValue("vacancyType", "dropdown.defaultval.errorMsg", new Object[] { "'vacancyType'" },
					"Value 0 is not allowed");
		}
		
	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postName", "post.postName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strCode", "post.postCode.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postDescription", "post.postDescription.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "baseSalary", "post.baseSalary.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minExperience", "post.minExperience.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "approvedPost", "post.approvedPost.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noticePeriod", "post.noticePeriod.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numValidity", "post.numValidity.required");
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "post.startDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "post.endDate.required");*/
		
		String vacancyType=","+ResourceBundleFile.getValueFromKey("VALIDVACANCYTYPE")+",";
		
		if (!vacancyType.contains(","+postTrackerMasterModel.getVacancyType()+",")){
			errors.rejectValue("vacancyType", "dropdown.valuenotinrange.errorMsg", new Object[]{"'vacancyType'"}, "Selected Option is not allowed");
		}
	}

}
