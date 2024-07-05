package in.pms.master.model;

import lombok.Data;
@Data

public class ProjectPaymentScheduleMasterModel {

	private long numId;
	private String encId;
	private boolean valid;
	
	private int numPaymentSequence;
	private Double numAmount;
	private double numInvoiceAmtInLakhs;
	private boolean linkedWithMilestone;
	private String strPurpose;
	private String strRemarks;
	private String strPaymentDueDate;
	private int numDueAfterDays;
	
	private String milestoneName;
	private String encProjectId;
	private String idCheck;
	private String strProjectName;
	
	private long projectId;
	private long numMilestoneId;
	private String encMiletoneId;
	private String strAmount;
	
	private String receivedAmount;
	private String strGroupName;
	private String strReferenceNumber;
	private String strClientName;
	
}

