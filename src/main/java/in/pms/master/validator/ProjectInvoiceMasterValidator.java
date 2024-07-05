package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;


import in.pms.master.model.ProjectInvoiceMasterModel;

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

public class ProjectInvoiceMasterValidator implements Validator {

	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ProjectInvoiceMasterValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectInvoiceMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectInvoiceMasterModel projectInvoiceMasterModel = (ProjectInvoiceMasterModel) obj;		
		if(projectInvoiceMasterModel.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		
	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strInvoiceRefno", "Project_Invoice_Master.invoiceReferenceNumber.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtInvoice", "Project_Invoice_Master.invoiceDate.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numInvoiceAmt", "Project_Invoice_Master.invoiceAmount.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numTaxAmount", "Project_Invoice_Master.invoiceTaxAmount.required");
		if(projectInvoiceMasterModel.getNumInvoiceAmt() <= 0){
			errors.rejectValue("numInvoiceAmt", "positive.value.required", new Object[]{"'projectId'"}, "Only positive value allowed");
		}
		
		if(projectInvoiceMasterModel.getNumTaxAmount() <= 0){
			errors.rejectValue("numTaxAmount", "positive.value.required", new Object[]{"'projectId'"}, "Only positive value allowed");
		}
		
	/*	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strInvoiceStatus", "Project_Invoice_Master.invoiceStatus.required");
		
		if(projectInvoiceMasterModel.getStrInvoiceStatus() == null){
			errors.rejectValue("strInvoiceStatus", "dropdown.defaultval.errorMsg", new Object[]{"'strInvoiceStatus'"}, "Value Null is not allowed");
		}*/
	
	}
	
	
}



