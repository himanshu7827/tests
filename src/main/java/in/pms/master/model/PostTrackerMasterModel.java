package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class PostTrackerMasterModel {
	private long numId;	
	private long[] numIds;
	private String encStrId;
	private String postName;
    private String postDescription;
    private float  baseSalary; 
    private String vacancyType;
    private float minExperience;
    private int approvedPost;
    private int noticePeriod;
	private String startDate;
	private String endDate;
	private boolean valid;
	private String strCode;
	private int numValidity;
}