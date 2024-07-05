package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.AnswerMasterDao;
import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.model.AnswerMasterModel;
@Service

public class AnswerMasterServiceImpl implements AnswerMasterService{
	
	@Autowired
	AnswerMasterDao answerMasterDao;
	
	@Override
	//@PreAuthorize("hasAuthority('READ_ANSWER_MST')")
	public List<AnswerMasterModel> getAllAnswerData(){
		return convertAnswerMasterDomainToModelList(answerMasterDao.getAllAnswerData());			
	}
	
	@Override
	public List<AnswerMasterModel> getActiveAnswerData(){
		return convertAnswerMasterDomainToModelList(answerMasterDao.getActiveAnswerData());			
	}

	public List<AnswerMasterModel> convertAnswerMasterDomainToModelList(List<AnswerMasterDomain> answerDataList){
		List<AnswerMasterModel> list = new ArrayList<AnswerMasterModel>();
			for(int i=0;i<answerDataList.size();i++){
				AnswerMasterDomain answerMasterDomain = answerDataList.get(i);
				AnswerMasterModel answerMasterModel = new AnswerMasterModel();
				answerMasterModel.setStrAnswer(answerMasterDomain.getStrAnswer());
				answerMasterModel.setNumId(answerMasterDomain.getNumId());
				if(answerMasterDomain.getNumIsValid()==1){
					answerMasterModel.setValid(true);
				}else{
					answerMasterModel.setValid(false);
				}
				
				list.add(answerMasterModel);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateAnswerData(AnswerMasterModel answerMasterModel){
		String result = "";
		AnswerMasterDomain answerMasterDomain = answerMasterDao.getAnswerByName(answerMasterModel.getStrAnswer());
	
		 if(null == answerMasterDomain){
			return null; 
		 }else if(answerMasterModel.getNumId() != 0){
			 if(answerMasterDomain.getNumId() == answerMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Answer already exist with Id "+answerMasterDomain.getNumId();
			 }
		 }
		 else{
				if(answerMasterDomain.getNumIsValid() == 0){
					result = "Answer Already Registered with Id "+answerMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Answer Already Registered with Id "+answerMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	
	@Override
	//@PreAuthorize("hasAuthority('WRITE_ANSWER_MST')")
        public AnswerMasterDomain saveAnswerData(AnswerMasterModel answerMasterModel){
		AnswerMasterDomain answerMasterDomain = convertAnswerMasterModelToDomain(answerMasterModel);
		return answerMasterDao.save(answerMasterDomain);
	}
	
	public AnswerMasterDomain convertAnswerMasterModelToDomain(AnswerMasterModel answerMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		AnswerMasterDomain answerMasterDomain = new AnswerMasterDomain();
		if(answerMasterModel.getNumId()!=0){		
			answerMasterDomain =  answerMasterDao.getOne(answerMasterModel.getNumId());
		}
		answerMasterDomain.setStrAnswer(answerMasterModel.getStrAnswer());
		answerMasterDomain.setDtTrDate(new Date());
		answerMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		answerMasterDomain.setNumIsValid(1);
			
	
		
		return answerMasterDomain;
	}
	
	@Override
	public List<AnswerMasterDomain> getAllAnswerDataById(List<Long> numId){
	 return answerMasterDao.getAnswerDataByIds(numId);
 }

}
