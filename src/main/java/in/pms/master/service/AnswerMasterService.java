package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.model.AnswerMasterModel;


public interface AnswerMasterService {
	
	
	public List<AnswerMasterModel> getAllAnswerData();
	public String checkDuplicateAnswerData(AnswerMasterModel answerMasterModel);
	@Transactional
	public AnswerMasterDomain saveAnswerData(AnswerMasterModel answerMasterModel);
	
	public List<AnswerMasterModel> getActiveAnswerData();
	
	public List<AnswerMasterDomain> getAllAnswerDataById(List<Long> numId);
	
 	
}
