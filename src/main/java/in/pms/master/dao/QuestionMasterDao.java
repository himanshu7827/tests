package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.QuestionMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long mergeQuestionMaster(QuestionMasterDomain questionMasterDomain){
		questionMasterDomain = daoHelper.merge(QuestionMasterDomain.class, questionMasterDomain);
		return questionMasterDomain.getNumId();
	}
	
	
	public QuestionMasterDomain getAllQusestionTypeById(long numId){
		QuestionMasterDomain questionMasterDomain =null;
		String query = "from QuestionMasterDomain where numId="+numId;
		List<QuestionMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			questionMasterDomain =list.get(0);
		}
		return questionMasterDomain;	
	}
	
	public List<QuestionMasterDomain> getAllQuestionData(){
		String query = "Select distinct a from QuestionMasterDomain a  join fetch a.answerMasterDomains where a.numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	
	public List<QuestionMasterDomain> getActiveQuestionData(){
		String query = "from QuestionMasterDomain where numIsValid=1 ";
		return  daoHelper.findByQuery(query);	
	}
	
	
	public QuestionMasterDomain getQuestionByName(String question){
		String query = "from QuestionMasterDomain where  numIsValid!=2 and lower(strQuestions)=lower('"+question+"')" ;	
		List<QuestionMasterDomain> questionList = daoHelper.findByQuery(query);		
		if(questionList.size()>0){
			return questionList.get(0);
		}
		return null;
	}
	
	}
