package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.timesheet.model.ParentTransactionModel;

@Repository
public class TSParentTransactionDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To save data in pms_timesheet_parent_trn table
	@Transactional
	public Integer entryInTimesheetParentTrn(ParentTransactionModel parentTransactionModel){

		parentTransactionModel = daoHelper.merge(ParentTransactionModel.class, parentTransactionModel);

        return parentTransactionModel.getTransactionId();
	}
		
	//to get transaction id from specific week, year, month, emp id
	public List<Integer> getTransactionId(ParentTransactionModel parentTransactionModel){
		List<Integer> result = new ArrayList<Integer>();
		ArrayList<Object> paraList = new ArrayList<>();
		String query = "";
		if(parentTransactionModel.getWeekName()==null && parentTransactionModel.getMonthName() != null) {
			query = "SELECT e.transactionId FROM ParentTransactionModel e WHERE e.empId = ?0 AND e.monthName = ?1 AND e.yearName = ?2";

			 
			 paraList.add(parentTransactionModel.getEmpId());
			 paraList.add(parentTransactionModel.getMonthName());
			 paraList.add(parentTransactionModel.getYearName());
		}
		else if(parentTransactionModel.getWeekName()!=null && parentTransactionModel.getMonthName() == null){
			query = "SELECT e.transactionId FROM ParentTransactionModel e WHERE e.empId = ?0 AND e.yearName = ?1 AND e.weekName = ?2";

			 
			 paraList.add(parentTransactionModel.getEmpId());
			 paraList.add(parentTransactionModel.getYearName());
			 paraList.add(parentTransactionModel.getWeekName());
		}
		else if(parentTransactionModel.getWeekName()!=null && parentTransactionModel.getMonthName() != null){
			query = "SELECT e.transactionId FROM ParentTransactionModel e WHERE e.empId = ?0 AND e.monthName = ?1 AND e.yearName = ?2 AND e.weekName = ?3";

			 
			 paraList.add(parentTransactionModel.getEmpId());
			 paraList.add(parentTransactionModel.getMonthName());
			 paraList.add(parentTransactionModel.getYearName());
			 paraList.add(parentTransactionModel.getWeekName());
		}
		else {
			query = "SELECT e.transactionId FROM ParentTransactionModel e WHERE e.empId = ?0 AND e.yearName = ?1 ";			 
			 paraList.add(parentTransactionModel.getEmpId());
			 paraList.add(parentTransactionModel.getYearName());
		}
		 result = daoHelper.findByQuery(query, paraList);
		 return result;
  }
	//to get all transaction ID
	public List<Integer> getAllTransactionId(){
		
		String query = "SELECT e.transactionId FROM ParentTransactionModel e";
		List<Integer> result = daoHelper.findByQuery(query);
		return result;
  }
}
