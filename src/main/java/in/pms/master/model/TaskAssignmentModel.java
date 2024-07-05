package in.pms.master.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TaskAssignmentModel {
	
	private long numId;	
	private String description;
	private MultipartFile taskAssigmentFile;
    private long[] numIds;
    private MultipartFile taskDocumentFile;
    private long documentId;
	private String encDocumentId;	
    private List<TaskDetailsModel> taskDetailsModelList;
    private String encTaskId;
    private long projectId;
    private long taskid;
    private String encProjectId;
	}
