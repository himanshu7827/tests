package in.pms.login.service;

import in.pms.global.model.ProceedingModel;
import in.pms.login.domain.TransactionActivity;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


public interface TransactionActivityService {

	@Transactional
	public long saveTransactionActivity(TransactionActivity transactionActivity);
	
	@Transactional
	public List<ProceedingModel> getProceedings(int monProgID);
	
}
