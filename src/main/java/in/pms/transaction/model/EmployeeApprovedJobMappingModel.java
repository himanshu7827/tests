package in.pms.transaction.model;

import lombok.Data;

@Data
public class EmployeeApprovedJobMappingModel {

	private int numId;
	private long employeeId;		
	private String employeeName;
	private String status;	
	private String startDate;
	private String endDate;	
	private String remark;	
	private int approvedJobId;
	private String encApprovedJobId;
	private String approvedJobName;
	private long groupId;
	private String groupName;
	private String selectedGroups;
	private String jobStatus;
}
