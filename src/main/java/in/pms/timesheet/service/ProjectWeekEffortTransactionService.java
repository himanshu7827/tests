package in.pms.timesheet.service;
import java.util.List;
import in.pms.timesheet.dao.TSProjectWeekEffortTransactionDao;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModel;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModelId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectWeekEffortTransactionService {
	@Autowired
	TSProjectWeekEffortTransactionDao projectWeekEffortTransactionDao;
	
	public Integer entryInTimesheetProjectWeekEffortTrn(ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel){
		return projectWeekEffortTransactionDao.entryInTimesheetProjectWeekEffortTrn(projectWeekEffortTransactionModel);
	}
	
	
	public List<Object> getProjectEffort(List<Integer> transactionId) {
		return projectWeekEffortTransactionDao.getTotalProjectEffort(transactionId);
	}
	
	public List<Object> getActivityEffort(List<Integer> transactionId) {
		return projectWeekEffortTransactionDao.getTotalActivityEffort(transactionId);
	}
	
	public List<Object> getEachAndEveryEffort(List<Integer> transactionIdEachAndEvery) {
		return projectWeekEffortTransactionDao.getEachAndEveryEffort(transactionIdEachAndEvery);
	}
	public List<Long> getMiscOtherActivityId(ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel){
		return projectWeekEffortTransactionDao.getMiscOtherActivityId(projectWeekEffortTransactionModel);
	}
	
	//REPORTS
		
	//R3   -    LOGGED IN USER MONTH WISE REPORT
	public List<Object> reportUserMonthWiseEffort(ProjectWeekEffortTransactionModelId projectWeekUserEffortTransactionModelId) {
		return projectWeekEffortTransactionDao.reportUserMonthWiseEffort(projectWeekUserEffortTransactionModelId);
	}
	
	//R4   -    LOGGED IN USER ACTIVITY WISE(Pie) REPORT (Month filter)
	public List<Object> reportUserActivityWiseEffortPie(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId) {
		return projectWeekEffortTransactionDao.reportUserActivityWiseEffortPie(projectweekEffortTransactionModelId);
	}

	//R9   -    LOGGED IN USER PROJECT(Pie) WISE REPORT (Month filter) 
	public List<Object> reportUserProjectWiseEffortPie(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId) {
		return projectWeekEffortTransactionDao.reportUserProjectWiseEffortPie(projectweekEffortTransactionModelId);
	}
	
	// R10	-	GROUP MONTH WISE REPORT (PROJECT + ACTIVITY) 
	public List<Object> reportGroupProjectActivityMonthEffort(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupIdByUserId) {
		return projectWeekEffortTransactionDao.reportGroupProjectActivityMonthEffort(projectweekEffortTransactionModelId,groupIdByUserId);
	}
	
	public List<Object> reportGroupProjectActivityMonthEffortNotGC(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupID, List<Long> empIdsUnderLoggedUser){
		return projectWeekEffortTransactionDao.reportGroupProjectActivityMonthEffortNotGC(projectweekEffortTransactionModelId, groupID, empIdsUnderLoggedUser);
	}
		
	//R11 - GROUP PROJECT-MONTH WISE PIE CHART 
	public List<Object> reportMonthProjectGroupWiseEffort(ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie, long groupId) {
		return projectWeekEffortTransactionDao.reportMonthProjectGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie,groupId);
	}
	
	public List<Object> reportMonthProjectGroupWiseEffortNotGC(ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie, List<Long> empIdsUnderLoggedUser){
		return projectWeekEffortTransactionDao.reportMonthProjectGroupWiseEffortNotGC(projectgroupWeeksEffortTransactionModelpie, empIdsUnderLoggedUser);
	}
	
	//R12 - GROUP ACTIVITY-MONTH WISE PIE CHART
	public List<Object> reportMonthActivityGroupWiseEffort(ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie, long groupId) {
		return projectWeekEffortTransactionDao.reportMonthActivityGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie,groupId);
	}
	
	public List<Object> reportMonthActivityGroupWiseEffortNotGC(ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie, List<Long> empIdsUnderLoggedUser){
		return projectWeekEffortTransactionDao.reportMonthActivityGroupWiseEffortNotGC(projectgroupWeeksEffortTransactionModelpie, empIdsUnderLoggedUser);
	}	
	//MAILING
		//purpose - to find employee with no effort last week
	public List<Long> getEmployeeWithNoEffortLastWeek(List<Long> empIdsAll){
		return projectWeekEffortTransactionDao.getEmployeeWithNoEffortLastWeek(empIdsAll);
	}
		
}
