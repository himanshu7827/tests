package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.global.misc.ResourceBundleFile;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.PostTrackerMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.service.DesignationMasterService;
import in.pms.master.service.PostTrackerMasterService;

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
@Component("EmployeeMasterValidator")
public class EmployeeMasterValidator implements Validator {
	@Autowired
	DesignationMasterService designationMasterService;
	@Autowired
	PostTrackerMasterService postTrackerMasterService;
	@Autowired
	EmployeeMasterDao employeeMasterDao;

	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String employeeNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public EmployeeMasterValidator(){
		namePattern = Pattern.compile(employeeNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return EmployeeMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		EmployeeMasterModel employeeMasterModel = (EmployeeMasterModel) obj;
		/*if(employeeMasterModel.getNumId() == 0){
			errors.rejectValue("numId", "dropdown.defaultval.errorMsg", new Object[]{"'numId'"}, "Value 0 is not allowed");
		}*/
		if(employeeMasterModel.getUpdateFlag() !=1){
		if(employeeMasterModel.getDesignation()==0){
			errors.rejectValue("designation", "dropdown.defaultval.errorMsg", new Object[]{"'designation'"}, "Value 0 is not allowed");
		}
		
		if(employeeMasterModel.getEmployeeTypeId() == 0){
			errors.rejectValue("employeeTypeId", "dropdown.defaultval.errorMsg", new Object[]{"'employeeTypeId'"}, "Value 0 is not allowed");
		}
		
		
		String validEmpStatus=","+ResourceBundleFile.getValueFromKey("VALIDEMPLOYMENTSTATUS")+",";
		if (!validEmpStatus.contains(","+employeeMasterModel.getEmploymentStatus()+",")){
			errors.rejectValue("employmentStatus", "dropdown.valuenotinrange.errorMsg", new Object[]{"'employmentStatus'"}, "Selected Option is not allowed");
		}
		
		String gender=","+ResourceBundleFile.getValueFromKey("VALIDGENDER")+",";
		if (!gender.contains(","+employeeMasterModel.getGender()+",")){
			errors.rejectValue("gender", "dropdown.valuenotinrange.errorMsg", new Object[]{"'gender'"}, "Selected Option is not allowed");
		}
		String category=","+ResourceBundleFile.getValueFromKey("VALIDCATEGORY")+",";
		if (!category.contains(","+employeeMasterModel.getCategory()+",")){
			errors.rejectValue("category", "dropdown.valuenotinrange.errorMsg", new Object[]{"'category'"}, "Selected Option is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeName", "employee.employeeName.required");
		nameMatcher = namePattern.matcher(employeeMasterModel.getEmployeeName());
		if (employeeMasterModel.getEmployeeName() != null && !nameMatcher.matches()){			
	          errors.rejectValue("employeeName", "invalid.Name");
	      }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "officeEmail", "employee.officeEmail.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", "employee.mobileNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "employee.dateOfBirth.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfJoining", "employee.dateOfJoining.required");
		
		
		long designationId = employeeMasterModel.getDesignation();
		DesignationMasterDomain designationMasterDomain=designationMasterService.getDesignationMasterDomainById(designationId);
		if(designationMasterDomain.getNumIsGroupSpecific()==1){
			if(employeeMasterModel.getOrganisationId() == 0){
				errors.rejectValue("organisationId", "dropdown.defaultval.errorMsg", new Object[]{"'organisationId'"}, "Value 0 is not allowed");
			}
			if(employeeMasterModel.getGroupId() == 0){
				errors.rejectValue("groupId", "dropdown.defaultval.errorMsg", new Object[]{"'groupId'"}, "Value 0 is not allowed");
			}
			
		}
		
		else if(designationMasterDomain.getNumIsOrganisationSpecific()==1){
			if(employeeMasterModel.getOrganisationId() == 0){
				errors.rejectValue("organisationId", "dropdown.defaultval.errorMsg", new Object[]{"'organisationId'"}, "Value 0 is not allowed");
			}
		}
		}else{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNumber", "employee.mobileNumber.required");

		}
		/*if(employeeMasterModel.getNumId()==0){*/
		/*
		if(employeeMasterModel.getPostId() != 0){
			EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeMasterById(employeeMasterModel.getNumId());
			
			if(null != employeeMasterDomain && null != employeeMasterDomain.getPostTrackerMaster()){
				if(employeeMasterModel.getPostId()!= employeeMasterDomain.getPostTrackerMaster().getNumId()){	
				PostTrackerMasterDomain postTrackerMasterDomain=postTrackerMasterService.getPostTrackerDomainById(employeeMasterModel.getPostId());
				long previousReg=employeeMasterDao.getCountByPostId(employeeMasterModel.getPostId());
				if(previousReg >=postTrackerMasterDomain.getNumApprovedPost()){
			    errors.rejectValue("postId", "employee.registration");
			}
				}
			
			}else{
				PostTrackerMasterDomain postTrackerMasterDomain=postTrackerMasterService.getPostTrackerDomainById(employeeMasterModel.getPostId());
				long previousReg=employeeMasterDao.getCountByPostId(employeeMasterModel.getPostId());
				if(previousReg >=postTrackerMasterDomain.getNumApprovedPost()){
			    errors.rejectValue("postId", "employee.registration");
			}
			}
		}
		*/
	/*	else{
			if(employeeMasterModel.getEmployeeTypeId() != 35){
				errors.rejectValue("postId", "dropdown.defaultval.errorMsg", new Object[]{"'postId'"}, "Value 0 is not allowed");
			}
			
		}*/
	
	
	}
}



