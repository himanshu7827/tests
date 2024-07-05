package in.pms.transaction.model;

import java.util.List;

import lombok.Data;

@Data
 public class ApplicationModel {	
	private long numId;
	private String encNumId;
	private long businessTypeId;
	private String businessTypeName;
	private int projectTypeId;
	private String projectTypeName;
	private int categoryId;
	private String categoryName;
	private long groupId;
	private String groupName;
	private long organisationId;
	private String organisationName;
	private double totalProposalCost;	
	private int proposalCost; //Added by devesh on 03-10-23 to get proposal cost
	
	private boolean valid;
	private String errorMessage;
	private String proposalTitle;	
	private String submittedTo;
	private String contactPerson;
	private List<Long> thrustAreaId;
	private boolean collaborative;
	private long clientId;
	private long endUserId;
	 private List<Long> contactPersonId;
	 private String clientName;
	 
	 private boolean convertedToProject;
		private boolean corporateApproval;
		private String dateOfSubmission;
		private String clearanceReceivedDate;
		private String proposalRefNo;
		private String strStatus;
		private boolean dateOfSubEqualsCurrentYear; //Added by devesh on 12-02-24 to check date of submission from proposal master
	
	 private boolean proposalYearCheck; //Store the value of proposal year check [12-04-2024]

}
