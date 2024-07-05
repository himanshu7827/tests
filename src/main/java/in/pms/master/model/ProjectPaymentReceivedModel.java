package in.pms.master.model;



import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class ProjectPaymentReceivedModel {

	private long numId;
	private boolean valid;
	
	private String dtPayment;
	private Double numReceivedAmount;
	private Double numReceivedAmountLakhs;
	private String strPaymentMode;
	private String strUtrNumber;
	private String strdtPayment;
	private String paymentDue;
	
	private long invoiceId;
	private String invoiceCode;
	
	private String encProjectId;
	private String idCheck;
	private long projectId;
	private String groupName;
	private String projectName;
	
	private int showForm;
	private String startDate;
	private String endDate;
	
	private String invoiceRefNo;
	private String dtpaymentDate;
	private String strReceivedAmount; 
	private String encInvoiceId;
	private String strInvoiceStatus;
	
	private boolean paymentWithoutInvoice;
	private String remarks;
	private boolean prevPayment;
	private boolean withInvoice;
	private long paymentId;
	private String strPrevPaymentDetails;
	private Double numMappedAmount;
	private Double numBalanceAmount;

	private Date tempDate;
	private List<Long> scheduledPaymentIDs;
	private String strReferenceNumber;
	private String encGroupId;
	
	/*--------- Add 5 fields [itTDS,gstTDS,LD,ShortPayment,otherRecovery and excess_Payment] variables in the model  [05-12-2023] ---*/
	private Double itTDS;
	private Double gstTDS;
	private Double shortPayment;
	private Double ldPayment;
	private Double otherRecovery;
	private Double excessPaymentAmount;
}
