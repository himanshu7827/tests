package in.pms.transaction.model;

import java.util.List;

import lombok.Data;


@Data
public class ApprovedJobModel {

	private int numId;

	private int projectId;
	private String projectCode;
	private String projectName;
	
	private int groupId;
	private String encGroupId;
	private String groupName;
	
	private int designationId;
	private String encDesignationId;
	private String designationName;
	
	private String count;
	private String jobCode;	
	
	private int durationInMonths;	
	
	private String status;
	
	private String approvedOn;
		
	private String createdOn;
	
	private String closedOn;
	
	private boolean approvalFlag;
	
	private List<String> jobReferences;
	
	private int noOfPositions;
	
}
