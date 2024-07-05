package in.pms.timesheet.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.timesheet.model.WeekEffortTransactionModel;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSWeekEffortTransactionDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To save data in pms_date_use table
	@Transactional
	public Integer entryInTimesheetWeekEffortTrn(WeekEffortTransactionModel weekEffortTransactionModel){

		weekEffortTransactionModel = daoHelper.merge(WeekEffortTransactionModel.class, weekEffortTransactionModel);

        //return weekEffortTransactionModel.getParentTransactionModel().getTransactionId();
		return weekEffortTransactionModel.getTransactionId();
  }
	//Purpose - To get total week Effort for all project/activities in each week for particular month and year to show on UI
	public List<Object> getTotalWeeksEffort(List<Integer> transactionIdWeek){
		
		/*
		 * Integer empId = dateUseModelId.getEmpId(); String monthName =
		 * dateUseModelId.getMonthName(); String weekName =
		 * dateUseModelId.getWeekName(); //long projectId =
		 * dateUseProjectModelId.getProjectId(); String yearName =
		 * dateUseModelId.getYearName();
		 */
		List<Object> result = new ArrayList<Object>();
		if(transactionIdWeek.isEmpty()) {
			return result;
		}
		//for(int i = 0; i<transactionIdWeek.size(); i++) {
			//String query = "SELECT f.weekName, e.totalEffortWeek FROM WeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.parentTransactionModel.transactionId in (?0)";
			String query = "SELECT f.weekName, e.totalEffortWeek FROM WeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.transactionId = f.transactionId WHERE e.isValid=1 AND e.transactionId in (?0)";

			 ArrayList<Object> paraList = new ArrayList<>();
			 paraList.add(transactionIdWeek);
			
			
			 result = (daoHelper.findByQuery(query, paraList));
		//}
		/*
		 * if(weekName == null){ String query =
		 * "SELECT e.id.weekName, e.totalEffortWeek FROM DateUseModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2"
		 * ;
		 * 
		 * ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName);
		 * 
		 * result = daoHelper.findByQuery(query, paraList); }else{ String query =
		 * "SELECT e.id.weekName, e.totalEffortWeek FROM DateUseModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.weekName = ?3"
		 * ;
		 * 
		 * ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); paraList.add(weekName);
		 * 
		 * result = daoHelper.findByQuery(query, paraList); }
		 */
		
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//REPORTS
		
}
