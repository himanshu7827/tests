package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.model.QuestionMasterModel;
public interface QuestionMasterService {
	
	
	public List<QuestionMasterModel> getAllQuestionData();
	@Transactional
	public long saveQuestionData(QuestionMasterModel questionMasterModel);
	
	@Transactional
	public List<QuestionMasterModel> getActiveQuestionData();
	
	public String checkDuplicateQuestionData(QuestionMasterModel questionMasterModel);

	@Transactional
	public List<QuestionMasterModel> getActiveQuestions();
 	
}
