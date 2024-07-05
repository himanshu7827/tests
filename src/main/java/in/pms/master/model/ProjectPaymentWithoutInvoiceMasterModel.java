package in.pms.master.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectPaymentWithoutInvoiceMasterModel {


	private long numId;
	private boolean valid;
	
	private String strPaymentReceivedIds;
		
	private String encProjectId;
	private long projectId;
	private Double numReceivedAmount;
	private Double numMappedAmount;
	private String strRemarks;
	private String strUtrNumber;
	private Date dtPayment;
	private String strPaymentRcvdDate;
 
	 
}
