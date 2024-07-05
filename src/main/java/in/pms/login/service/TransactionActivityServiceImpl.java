package in.pms.login.service;

import in.pms.global.dao.TransactionDao;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.model.ProceedingModel;
import in.pms.global.util.DateUtils;
import in.pms.login.dao.TransactionActivityDao;
import in.pms.login.domain.TransactionActivity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionActivityServiceImpl implements 	TransactionActivityService {

	@Autowired
	TransactionActivityDao transactionActivityDao;
	
	@Autowired
	TransactionDao transactionDao;
	
	@Override
	public long saveTransactionActivity(TransactionActivity transactionActivity){
		return transactionActivityDao.save(transactionActivity).getNumId();
	}

@Override
public List<ProceedingModel> getProceedings(int monProgID) {
		
		List<TransactionMasterDomain> transactionMasterDomainList=transactionDao.getProceedingDetails(monProgID);
		List<ProceedingModel> proceedingList = new ArrayList<ProceedingModel>();
		if(transactionMasterDomainList.size()>0)
		{
			for(int i=0;i<transactionMasterDomainList.size();i++){
			TransactionMasterDomain transactionMasterDomain = transactionMasterDomainList.get(i);
			ProceedingModel proceedingModel = new ProceedingModel();
			proceedingModel.setRoleName(transactionMasterDomain.getRoleMasterDomain().getRoleName());
			proceedingModel.setStrRemarks(transactionMasterDomain.getStrRemarks());
			proceedingModel.setStrAction(transactionMasterDomain.getActionMasterDomain().getStrActionPerformed());
			proceedingModel.setStrDateTime(DateUtils.dateToDateTimeString(transactionMasterDomain.getDtTrDate()));
			transactionMasterDomain.getWorkflowMasterDomain().getStrType();
			proceedingModel.setStrName(transactionMasterDomain.getEmployeeMasterDomain().getEmployeeName());
			proceedingList.add(proceedingModel);
		}
		}
		return proceedingList;
}
	
	
	
	
}
