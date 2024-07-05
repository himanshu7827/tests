package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMilestoneForm {
	
	private long numId;
	private String milestoneName;
	private String clientName;
	private String strProjectName;
	private String strProjectReference;
	private String groupName;
	private String strDesription;
	private String expectedStartDate;
	private String completionDate;
	private long projectId;
	private boolean valid;
	private long[] numIds;
	private int noOfDays;
	private String projectStartDate;
	private String projectEndDate;
	private String encMilestsoneId;
	private String strStatus;
	private String thisDate;
	
	private long milestoneId;
	private String encProjectId;
	private String milestoneTypeName;
	private int milestoneTypeId;
	

}
