package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.model.ResponseModel;
import in.pms.master.model.TaskAssignmentModel;
import in.pms.master.model.TaskDetailsModel;



public interface TaskAssignmentService {
	
	
	public List<TaskAssignmentModel> getAllTaskAssignmentData();
	@Transactional
	public long saveTaskAssignmentData(TaskAssignmentModel taskAssignmentModel);
		
	public List<TaskAssignmentModel> getActiveTaskAssignmentData();
	
}
