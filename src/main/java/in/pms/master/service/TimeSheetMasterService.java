package in.pms.master.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.TaskMasterDomain;
import in.pms.master.domain.SubTaskMasterDomain;
import in.pms.master.domain.DescriptionMasterDomain;
import in.pms.master.model.TimeSheetMasterModel;
@Service

public interface TimeSheetMasterService {

	//To save/update record by Himanshu on 10-05-2024
	@Transactional
	public long saveUpdateDescMaster(TimeSheetMasterModel timesheetMasterModel);
	
	@Transactional
	public long saveUpdateDescMasterTest(TimeSheetMasterModel timesheetMasterModel);
	
	//To get all tasks by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> getAllTaskMasterDomain();
	
	
	//To get subtasks by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> getSubTaskMasterById(long taskId);
	
	
	//To get all tasks description by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> getAllActiveDescriptionMasterDomain();


}
