package in.pms.timesheet.service;
import java.util.List;

import in.pms.timesheet.dao.TSWeekEffortTransactionDao;
import in.pms.timesheet.model.WeekEffortTransactionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekEffortTransactionService {
	
	@Autowired
	TSWeekEffortTransactionDao weekEffortTransactionDao;
	
	public Integer entryInTimesheetWeekEffortTrn(WeekEffortTransactionModel weekEffortTransactionModel){
		return weekEffortTransactionDao.entryInTimesheetWeekEffortTrn(weekEffortTransactionModel);
	}
	public List<Object> getWeeksEffort(List<Integer> transactionIdWeek) {
		return weekEffortTransactionDao.getTotalWeeksEffort(transactionIdWeek);
	}
	
	//REPORTS
		
}
