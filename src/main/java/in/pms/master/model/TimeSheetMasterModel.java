package in.pms.master.model;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

//Model for task, subtask and description by Himanshu on 10-05-2024
@Getter
@Setter
@ToString
public class TimeSheetMasterModel {

	private long taskId;
	private long subtaskId;
	private long descId;
	private String taskName;
	private String subtaskName;
	private String subtaskDescription;
	private boolean valid;
	
}
