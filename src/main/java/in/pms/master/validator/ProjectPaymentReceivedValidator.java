package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.dao.ProjectPaymentReceivedDao;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectPaymentReceivedWithoutInvoiceDomain;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectPaymentReceivedService;

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
@Component("ProjectPaymentReceivedValidator")
public class ProjectPaymentReceivedValidator implements Validator {
	
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	ProjectPaymentReceivedService projectPaymentReceivedService;

	@Autowired
	ProjectPaymentReceivedDao projectPaymentReceivedDao;
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");
	
	public ProjectPaymentReceivedValidator(){
		namePattern = Pattern.compile(groupNameRegex);
	}	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectPaymentReceivedModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		
		ProjectPaymentReceivedModel projectPaymentReceivedModel = (ProjectPaymentReceivedModel) obj;	
				
		if(projectPaymentReceivedModel.getProjectId() == 0 && projectPaymentReceivedModel.getInvoiceId()== 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
			errors.rejectValue("invoiceId", "dropdown.defaultval.errorMsg", new Object[]{"'invoiceId'"}, "Value 0 is not allowed");
		}
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numReceivedAmount", "Payment_Received.received_Amount.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dtPayment", "Payment_Received.payment_Date.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strPaymentMode", "Payment_Received.payment_Mode.required");
		/*
		 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strUtrNumber",
		 * "Payment_Received.utr_Number.required");
		 */
		if(projectPaymentReceivedModel.getNumReceivedAmount() <= 0){
			errors.rejectValue("numReceivedAmount", "positive.value.required", new Object[]{"'projectId'"}, "Only positive value allowed");
		}
		
		if(projectPaymentReceivedModel.getStrPaymentMode() == null){
			errors.rejectValue("strPaymentMode", "dropdown.defaultval.errorMsg", new Object[]{"'strPaymentMode'"}, "Value 0 is not allowed");			
		}
		
		
		
			if(!projectPaymentReceivedModel.isPaymentWithoutInvoice()){
		long invoiceId = projectPaymentReceivedModel.getInvoiceId();
		long receiveId=projectPaymentReceivedModel.getNumId();
		Double prevPaymentRcvdAmt=0.0;
		Double mappedAmount=0.0;
		Double allowedMapAmountLimit=0.0;
		ProjectInvoiceMasterDomain invoiceDomain=projectInvoiceMasterService.getProjectInvoiceDomainById(invoiceId);
		
			if(projectPaymentReceivedModel.isPrevPayment()){
				ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain= projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceById(projectPaymentReceivedModel.getPaymentId());
				prevPaymentRcvdAmt=projectPaymentReceivedWithoutInvoiceDomain.getNumReceivedAmount();
				mappedAmount=projectPaymentReceivedWithoutInvoiceDomain.getNumMappedAmount();
				allowedMapAmountLimit=(prevPaymentRcvdAmt-mappedAmount);
				
			}
		if(null == invoiceDomain){
			errors.rejectValue("invoiceId", "form.invalid.value", new Object[]{"'strPaymentMode'"}, "Invalid Invoice Id");
		}
		
		if(null != invoiceDomain && !projectPaymentReceivedModel.isPrevPayment()){
			if(invoiceDomain.getStrInvoiceStatus().equals("Generated")){
				/*Double check_amount=projectPaymentReceivedModel.getNumReceivedAmount();
				System.out.println(check_amount);*/
				if(projectPaymentReceivedModel.getNumReceivedAmount() > invoiceDomain.getNumInvoiceAmt()){
			  		/*------------------ Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ---------------------*/
					Double oldOtherPaymentReceived = (projectPaymentReceivedModel.getItTDS() != null ? projectPaymentReceivedModel.getItTDS() : 0.0)
					        + (projectPaymentReceivedModel.getGstTDS() != null ? projectPaymentReceivedModel.getGstTDS() : 0.0)
					        + (projectPaymentReceivedModel.getShortPayment() != null ? projectPaymentReceivedModel.getShortPayment() : 0.0)
					        + (projectPaymentReceivedModel.getLdPayment() != null ? projectPaymentReceivedModel.getLdPayment() : 0.0)
					        + (projectPaymentReceivedModel.getOtherRecovery() != null ? projectPaymentReceivedModel.getOtherRecovery() : 0.0);
					Double oldExcessPaymentAmount = (projectPaymentReceivedModel.getExcessPaymentAmount() != null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0.0);
					projectPaymentReceivedModel.setNumReceivedAmount(
					        (projectPaymentReceivedModel.getNumReceivedAmount() != null ? projectPaymentReceivedModel.getNumReceivedAmount() : 0.0)
					                + oldOtherPaymentReceived
					                - oldExcessPaymentAmount
					);
			  		/*----------- End of Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ------------*/
					errors.rejectValue("numReceivedAmount", "defaultval.errorMsg", new Object[]{"'numReceivedAmount'"}, "Total Received Amount should not be greater than Invoice Amount");
				}
			}
			else if(invoiceDomain.getStrInvoiceStatus().equals("Payment Partially Paid")){
				double previousPaymentRecv=projectPaymentReceivedService.totalPaymentReceivedByInvoiceId(invoiceId,receiveId);
				if((previousPaymentRecv+projectPaymentReceivedModel.getNumReceivedAmount()) > invoiceDomain.getNumInvoiceAmt()){
			  		/*------------------ Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ---------------------*/
					Double oldOtherPaymentReceived = (projectPaymentReceivedModel.getItTDS() != null ? projectPaymentReceivedModel.getItTDS() : 0.0)
					        + (projectPaymentReceivedModel.getGstTDS() != null ? projectPaymentReceivedModel.getGstTDS() : 0.0)
					        + (projectPaymentReceivedModel.getShortPayment() != null ? projectPaymentReceivedModel.getShortPayment() : 0.0)
					        + (projectPaymentReceivedModel.getLdPayment() != null ? projectPaymentReceivedModel.getLdPayment() : 0.0)
					        + (projectPaymentReceivedModel.getOtherRecovery() != null ? projectPaymentReceivedModel.getOtherRecovery() : 0.0);
					Double oldExcessPaymentAmount = (projectPaymentReceivedModel.getExcessPaymentAmount() != null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0.0);
					projectPaymentReceivedModel.setNumReceivedAmount(
					        (projectPaymentReceivedModel.getNumReceivedAmount() != null ? projectPaymentReceivedModel.getNumReceivedAmount() : 0.0)
					                + oldOtherPaymentReceived
					                - oldExcessPaymentAmount
					);
			  		/*----------- End of Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ------------*/
					errors.rejectValue("numReceivedAmount", "defaultval.errorMsg", new Object[]{"'numReceivedAmount'"}, "Total Received Amount should not be greater than Invoice Amount");
				}
			}
		}
		
		if(null != invoiceDomain && projectPaymentReceivedModel.isPrevPayment()){
			if(invoiceDomain.getStrInvoiceStatus().equals("Generated") || invoiceDomain.getStrInvoiceStatus().equals("Payment Partially Paid")){
				if(projectPaymentReceivedModel.getNumReceivedAmount() > allowedMapAmountLimit){
			  		/*------------------ Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ---------------------*/
					Double oldOtherPaymentReceived = (projectPaymentReceivedModel.getItTDS() != null ? projectPaymentReceivedModel.getItTDS() : 0.0)
					        + (projectPaymentReceivedModel.getGstTDS() != null ? projectPaymentReceivedModel.getGstTDS() : 0.0)
					        + (projectPaymentReceivedModel.getShortPayment() != null ? projectPaymentReceivedModel.getShortPayment() : 0.0)
					        + (projectPaymentReceivedModel.getLdPayment() != null ? projectPaymentReceivedModel.getLdPayment() : 0.0)
					        + (projectPaymentReceivedModel.getOtherRecovery() != null ? projectPaymentReceivedModel.getOtherRecovery() : 0.0);
					Double oldExcessPaymentAmount = (projectPaymentReceivedModel.getExcessPaymentAmount() != null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0.0);
					projectPaymentReceivedModel.setNumReceivedAmount(
					        (projectPaymentReceivedModel.getNumReceivedAmount() != null ? projectPaymentReceivedModel.getNumReceivedAmount() : 0.0)
					                + oldOtherPaymentReceived
					                - oldExcessPaymentAmount
					);
			  		/*----------- End of Set the previous value of Received amount after deduction and add of excess payment or other payment [05-12-2023] ------------*/
					errors.rejectValue("numReceivedAmount", "defaultval.errorMsg", new Object[]{"'numReceivedAmount'"}, "Total Received Amount should not be greater than allowed limit");
				}
			}
		}
			}
		
	}
	
	
}






