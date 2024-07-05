package in.pms.master.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDetailsModel {
	
	private long numId;	
	private String taskName;
	private boolean valid;
    private long[] numIds;
    private String taskDescription;
    private long taskCreatedBy;
    private long projectId;
    private String encProjectId;
    private String projectName;
    private float expectedTime;
    private String priority;
    private int withMilestone;
    private long milestoneId;
    private MultipartFile taskDocumentFile;
    private long activityRadioValue;
    private String milestoneName;
    private String activity;
	private String encNumId;	
	private long documentId;
	private String encDocumentId;	
	List<ProjectDocumentDetailsModel> detailsModels = new ArrayList<ProjectDocumentDetailsModel>();
	List<MilestoneActivityModel> activityModels = new ArrayList<MilestoneActivityModel>();
	private String assignDate;
	private String employeeName;
    private String encTaskId;
	
	}
