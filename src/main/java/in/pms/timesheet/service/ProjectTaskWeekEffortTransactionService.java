package in.pms.timesheet.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSProjectTaskWeekEffortTransactionDao;
import in.pms.timesheet.model.ProjectTaskWeekEffortTransactionModel;

@Service
public class ProjectTaskWeekEffortTransactionService {
	
	@Autowired
	TSProjectTaskWeekEffortTransactionDao projectTaskWeekEffortTransactionDao;
	
	public Integer entryInTimesheetProjectTaskWeekEffortTrn(ProjectTaskWeekEffortTransactionModel projectTaskWeekEffortTransactionModel){
		return projectTaskWeekEffortTransactionDao.entryInTimesheetProjectTaskWeekEffortTrn(projectTaskWeekEffortTransactionModel);
	}
	
	public HashMap<Long, String> getTaskAndSubTaskDescEffortByProjectIdBasic(List<Integer> transactionId, Long projectId){
		return projectTaskWeekEffortTransactionDao.getTaskAndSubTaskDescEffortByProjectIdBasic(transactionId, projectId);
	}
	
	public HashMap<Long, String> getTaskAndSubTaskDescEffortByProjectIdDetailed(List<Integer> transactionId, Long projectId){
		return projectTaskWeekEffortTransactionDao.getTaskAndSubTaskDescEffortByProjectIdDetailed(transactionId, projectId);
	}
}
