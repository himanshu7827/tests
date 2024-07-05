package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalMasterModel {
	private long numId;
	private String encStrId;
	private String proposalTitle;	
	private String proposalType;	
	private String dateOfSubmission;	
	private String proposalAbstract;
	private String businessTypeName;
	private String projectTypeName;
	private String projectCategory;
	private int categoryId;
	private int proposalCost;
	private double totalProposalCost;
	private int duration;	
	private String submittedBy;
	private String submittedTo;
	private String contactPerson;
	private String proposalStatus;
	private String objectives;
	private String summary;
	private String background;
	private String uploadFile;
	private long clientId;
	private long groupId;
	private long thrustAreaId;
	private long organisationId;
	private boolean valid;
	private long[] numIds;
	private String groupName;
	private long contactPersonId;
	private long applicationId;
	private String encApplicationId;
	private int numHasError;
	private String organisationName;
    private String thrustArea;

	 private String organisationAddress;
	 private String contactNumber;
	 private String email;
	 private boolean corporateApproval;
	 private String dateOfSub;
	 private String clearanceReceiveDate;
	 private String status;
	 private String remarks;
	 private String proposalRefNo;
	 private String endUser;
	 private int numVersion;
	 private boolean convertedToProject;
	 private String receivedProjectStatus;
	 private String strClientName;
	 private String strProposalTotalCost;
	 private String strProposalCost;
	 private double numProposalAmountLakhs; 
	 private double totalProjectCost;
	 private String strProjectCost;
	 private int numCountOfConverted;
	 
	 /*------- variable use for project some data when get proposal [08-02-2024] --*/
	 private String strProjectName;
	 private String strProjectWorkOrderDate;
	 private String encProjectId;
	 private String strProjectRefNo;
	 /*------- end of variable use for project some data when get proposal [08-02-2024] --*/
}
