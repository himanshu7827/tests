package in.pms.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSParentTransactionDao;
import in.pms.timesheet.model.ParentTransactionModel;

@Service
public class ParentTransactionService {
	
	@Autowired
	TSParentTransactionDao parentTransactionDao;
	public Integer entryInTimesheetParentTrn(ParentTransactionModel parentTransactionModel){
		return parentTransactionDao.entryInTimesheetParentTrn(parentTransactionModel);
	}
	
	public List<Integer> getTransactionId(ParentTransactionModel parentTransactionModel){
		return parentTransactionDao.getTransactionId(parentTransactionModel);
	}
	
	public List<Integer> getAllTransactionId(){
		return parentTransactionDao.getAllTransactionId();
		
	}
}
