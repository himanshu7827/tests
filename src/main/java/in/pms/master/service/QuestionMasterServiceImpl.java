 package in.pms.master.service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.AnswerMasterDao;
import in.pms.master.dao.QuestionMasterDao;
import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.domain.QuestionMasterDomain;
import in.pms.master.model.QuestionMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class QuestionMasterServiceImpl implements QuestionMasterService{
	
	@Autowired
	QuestionMasterDao questionMasterDao;
	
	@Autowired
	AnswerMasterDao answerMasterDao;
	
	@Autowired
	AnswerMasterService answerMasterService;
	
	@Override
	public List<QuestionMasterModel> getAllQuestionData(){
		return convertQuestionMasterDomainToModelList(questionMasterDao.getAllQuestionData());			
	}
	
	@Override
	//@PreAuthorize("hasAuthority('READ_QUESTION_MST')")
	public List<QuestionMasterModel> getActiveQuestionData(){
		return convertQuestionMasterDomainToModelList(questionMasterDao.getActiveQuestionData());			
	}
	
	public List<QuestionMasterModel> getActiveQuestions(){
		return convertQuestionMasterDomainToModelList(questionMasterDao.getActiveQuestionData());			
	}

	public List<QuestionMasterModel> convertQuestionMasterDomainToModelList(List<QuestionMasterDomain> questionList){
		List<QuestionMasterModel> list = new ArrayList<QuestionMasterModel>();
			for(int i=0;i<questionList.size();i++){
				QuestionMasterDomain questionMasterDomain = questionList.get(i);
				QuestionMasterModel questionMasterModel = new QuestionMasterModel();
				questionMasterModel.setStrQuestions(questionMasterDomain.getStrQuestions());
				questionMasterModel.setNumId(questionMasterDomain.getNumId());
				List<AnswerMasterDomain> answerMasterDomainList=questionMasterDomain.getAnswerMasterDomains();
				  String answerIds ="";
				  String answer ="";
				for(int j=0;j<answerMasterDomainList.size();j++){
					AnswerMasterDomain answerMasterDomain=answerMasterDomainList.get(j);
					if(answerIds.equals("")){
						answerIds = ""+answerMasterDomain.getNumId();
						answer = ""+answerMasterDomain.getStrAnswer();
					}else{
						answerIds+=","+answerMasterDomain.getNumId();
						answer+=","+answerMasterDomain.getStrAnswer();
					}
				}
				questionMasterModel.setAnswerIds(answerIds);
				questionMasterModel.setAnswer(answer);
				
				//questionMasterModel.setQuestionTypeId(questionMasterDomain.getQuestionTypeMasterDomain().getNumId());
				if(questionMasterDomain.getNumIsValid()==1){
					questionMasterModel.setValid(true);
				}else{
					questionMasterModel.setValid(false);
				}
				
				list.add(questionMasterModel);
	}
		return list;
	}
	
	
		@Override
		//@PreAuthorize("hasAuthority('WRITE_QUESTION_MST')")
        public long saveQuestionData(QuestionMasterModel questionMasterModel){
		QuestionMasterDomain questionMasterDomain = convertQuestionModelToDomain(questionMasterModel);
		return questionMasterDao.mergeQuestionMaster(questionMasterDomain);
	}
	
	public QuestionMasterDomain convertQuestionModelToDomain(QuestionMasterModel questionMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		QuestionMasterDomain questionMasterDomain = new QuestionMasterDomain();
		if(questionMasterModel.getNumId()!=0){		
			questionMasterDomain =  questionMasterDao.getAllQusestionTypeById(questionMasterModel.getNumId());
		}
		questionMasterDomain.setStrQuestions(questionMasterModel.getStrQuestions());
		questionMasterDomain.setDtTrDate(new Date());
		questionMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		questionMasterDomain.setNumIsValid(1);
		
		List<AnswerMasterDomain> answerList=new ArrayList<AnswerMasterDomain>();
		if(questionMasterModel.getAnswerId().size()>0)
		answerList = answerMasterDao.getAnswerDataByIds(questionMasterModel.getAnswerId());
		questionMasterDomain.setAnswerMasterDomains(answerList);	
		                                                                                                                                                                                                                                                                                                                        
		return questionMasterDomain;
	}

	@Override
	public String checkDuplicateQuestionData(QuestionMasterModel questionMasterModel){
		String result = "";
		QuestionMasterDomain questionMasterDomain = questionMasterDao.getQuestionByName(questionMasterModel.getStrQuestions());
	
		 if(null == questionMasterDomain){
			return null; 
		 }else if(questionMasterModel.getNumId() != 0){
			 if(questionMasterDomain.getNumId() == questionMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Question already exist with Id "+questionMasterDomain.getNumId();
			 }
		 }
		 else{
				if(questionMasterDomain.getNumIsValid() == 0){
					result = "Question already exist with Id "+questionMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Question already exist with Id "+questionMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	
}
