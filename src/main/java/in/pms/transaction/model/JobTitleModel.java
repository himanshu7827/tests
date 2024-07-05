package in.pms.transaction.model;

import java.util.List;

import lombok.Data;


@Data
public class JobTitleModel {

	//private int numId;

	private int projectId;
	private String projectCode;
	private String projectName;
	
	private int groupId;
	private String encGroupId;
	private String groupName;
	
	private long empId;

	private String employeeName;
	//private String designationName;
	

	private int jobID;
	private String jobTitle;
	
	private String approvedOn; 
	
}
