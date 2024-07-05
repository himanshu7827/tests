package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {
	
	private String successMsg;
	private String failureMsg;
	private boolean status;
	List<TaskDetailsModel> taskDetailsModelList;
	
	}
