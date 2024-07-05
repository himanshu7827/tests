package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.global.util.DateUtils;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;
import in.pms.master.service.ProjectMasterService;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
@Component("ProjectPaymentScheduleMasterValidator")
public class ProjectPaymentScheduleMasterValidator implements Validator {
	@Autowired
	ProjectMasterService projectMasterService;

	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ProjectPaymentScheduleMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectPaymentScheduleMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel = (ProjectPaymentScheduleMasterModel) obj;		
		if(projectPaymentScheduleMasterModel.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numPaymentSequence", "Project_Payment_Schedule.paymentSequenceNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPaymentDueDate", "Project_Payment_Schedule.paymentDueDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numAmount", "Project_Payment_Schedule.Amount.required");
		ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();
	    projectMasterDomain=projectMasterService.getProjectMasterDataById(projectPaymentScheduleMasterModel.getProjectId());
		
	    Date startDate = projectMasterDomain.getDtProjectStartDate();
	    Date endDate=null;
		try {
			endDate = DateUtils.dateStrToDate(projectPaymentScheduleMasterModel.getStrPaymentDueDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(startDate.compareTo(endDate) > 0){
			errors.rejectValue("strPaymentDueDate", "defaultval.errorMsg", new Object[]{"'strPaymentDueDate'"}, "Payment Due Date must be greater than project Date.");

	    }
		
		if(projectPaymentScheduleMasterModel.getNumAmount() <= 0){
			errors.rejectValue("numAmount", "positive.value.required", new Object[]{"'projectId'"}, "Only positive value allowed");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPurpose", "Project_Payment_Schedule.Purpose.required");
		if(projectPaymentScheduleMasterModel.isLinkedWithMilestone()){
		if(projectPaymentScheduleMasterModel.getNumMilestoneId() == 0){
			errors.rejectValue("numMilestoneId", "dropdown.defaultval.errorMsg", new Object[]{"'numMilestoneId'"}, "Value 0 is not allowed");
		}
		}
	}
	
}



