package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DashboardModel {
	private long groupId;
	private String encGroupId;
	private long businessTypeId;
	private String encBusinessId;
	private long count;
	
	private String groupName;
	private String projectName;
	private String organisationName;
	private long projectCount;
	private double projectCost;
	private String strTotalExpenditure;
	private long projectId;
	private String encProjectId;
// Total Outlay 
	private double totalCost;
}
