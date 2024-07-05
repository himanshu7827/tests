package in.pms.master.model;

import in.pms.global.model.WorkflowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectMasterModel {
	
	private long numId;	
	private long numProjectId;
	private String strProjectName;
	private boolean valid;
    private long[] numIds;
	private String strMilestoneName;
	private String encProjectId;
	private String projectRefNo;
	private String endDate;
	private String startDate;
	private String status;
	private String strInvoiceType;
	private String strProjectCost;
	private String strClientName;
	 private double numProjectAmountLakhs;
	 private String projectClosureDate;
	 private String strWorkOrderDate;
	 private String strMouDate;
	 private String strGrouptName;
	 private String strReferenceNumber;
	 private String encGroupId;
	 private String strGST;
	 private String strTAN;
/*	 private String tempProjectClosureDate;*/
	 private String strClosureRemarks;
	 /*----------  Add WorkflowModel for get the latest transaction of project in transaction master [04/08/2023] ---*/
	 private WorkflowModel workflowModel;
	 
	 /*--------- Add some Fields for closed Projects [12-10-2023] ---------*/
	 private String strReceivedAmount;
	 private String strPLName;
	 private Integer projectDuration;
	 private String strTotalCost;
	 private String strValidatedRemarks;
	 private String strValidateStatus;
	 private String validateDate;
	 private String rowColor;
	}
