package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ProposalMasterModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter


public class ProposalMasterValidator implements Validator{
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ProposalMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	
	
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return ProposalMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ProposalMasterModel proposalMasterModel = (ProposalMasterModel) obj;
		/*if(proposalMasterModel.getProposalType() == null){
			errors.rejectValue("proposalType", "dropdown.defaultval.errorMsg", new Object[]{"'proposalType'"}, "Value null is not allowed");
			
		}
		else if(proposalMasterModel.getThrustAreaId() == 0){
			errors.rejectValue("thrustAreaId", "dropdown.defaultval.errorMsg", new Object[]{"'thrustAreaId'"}, "Value 0 is not allowed");
		}
		
		else if(proposalMasterModel.getGroupId() == 0){
			errors.rejectValue("groupId", "dropdown.defaultval.errorMsg", new Object[]{"'groupId'"}, "Value 0 is not allowed");
		}
		
		else if(proposalMasterModel.getOrganisationId() == 0){
			errors.rejectValue("organisationId", "dropdown.defaultval.errorMsg", new Object[]{"'organisationId'"}, "Value 0 is not allowed");
		}
		/*else if(proposalMasterModel.getContactPersonId() == 0){
			errors.rejectValue("contactPerson", "dropdown.defaultval.errorMsg", new Object[]{"'contactPerson'"}, "Value 0 is not allowed");
		}*/
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "proposalTitle", "proposal.proposalTitle.required");
		nameMatcher = namePattern.matcher(proposalMasterModel.getProposalTitle());
		if (proposalMasterModel.getProposalTitle() != null && !nameMatcher.matches()){			
	          errors.rejectValue("proposalTitle", "invalid.Name");
	      }*/
		//contact person
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPerson", "proposal.contactPerson.required");
		nameMatcher = namePattern.matcher(proposalMasterModel.getContactPerson());
		if (proposalMasterModel.getContactPerson() != null && !nameMatcher.matches()){			
	          errors.rejectValue("contactPerson", "invalid.Name");
	      }*/
		
		//objectives
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "objectives", "proposal.objectives.required");
		nameMatcher = namePattern.matcher(proposalMasterModel.getObjectives());
		if (proposalMasterModel.getObjectives() != null && !nameMatcher.matches()){			
			          errors.rejectValue("objectives", "invalid.Name");
			 }
		//summary
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summary", "proposal.summary.required");
		nameMatcher = namePattern.matcher(proposalMasterModel.getSummary());
		if (proposalMasterModel.getSummary() != null && !nameMatcher.matches()){			
	          errors.rejectValue("summary", "invalid.Name");
	      }
		//background
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "background", "proposal.background.required");
		nameMatcher = namePattern.matcher(proposalMasterModel.getBackground());
		if (proposalMasterModel.getBackground() != null && !nameMatcher.matches()){			
			  errors.rejectValue("background", "invalid.Name");
			      }
		//Submitted By
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "submittedBy", "proposal.submittedBy.required");
				nameMatcher = namePattern.matcher(proposalMasterModel.getSubmittedBy());
				if (proposalMasterModel.getSubmittedBy() != null && !nameMatcher.matches()){			
					  errors.rejectValue("submittedBy", "invalid.Name");
					      }
	}

}
