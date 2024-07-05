package in.pms.master.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MilestoneReviewModel {
	
	private long numId;	
	private String strMilestoneName;
	private String projectName;
	private String groupName;
	private String clientName;
	private String milestoneDate;
	private long milestoneId;
	private String reviewDate;
	private String completionDate;
	private String strRemarks;
	private boolean valid;
    private long[] numIds;
    private boolean status;
    private String referrer;
   
	
	}
