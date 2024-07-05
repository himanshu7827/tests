package in.pms.global.model;



import java.util.Date;

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
	
	private Date dtPayment;
	private Double numReceivedAmount;
	private String strPaymentMode;
	private String strUtrNumber;
	private String strdtPayment;
	
	
	private long invoiceId;
	private String invoiceCode;
	
	private String encProjectId;
	private String idCheck;
	private long projectId;
	
	private int showForm;
	private String strReceivedAmount;
	private Double numReceivedAmountLakhs;
	
	
	
	
	
}
