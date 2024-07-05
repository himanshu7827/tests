package in.pms.master.model;

import java.util.List;

import lombok.Data;

@Data
public class ProjectInvoiceMasterModel {


	private long numId;
	private boolean valid;
	
	private String strInvoiceRefno;
	private String dtInvoice;
	private Double numInvoiceAmt;
	private double numInvoiceAmtInLakhs;
	private Double numTaxAmount;
	private Double numInvoiceTotalAmt;
	private Double paymentDue;
	private String strInvoiceStatus;
	private String strInvoiceDate;
	
	private String encProjectId;
	private String idCheck;
	private long projectId;
	private long scheduledPaymentID;
	
	private String strInvoiceAmt;
	private String strTaxAmount;
	private String strInvoiceTotalAmt;
	private String strInvoiceType;
	private String strProjectName;
	//private int[] scheduledPaymentIDs;
	private List<Long> scheduledPaymentIDs;
	private String strGroupName;
	private String strReferenceNumber;
	private String strClientName;
	 
}
