package in.pms.master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.master.dao.PostTrackerMasterDao;
import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.master.domain.PostTrackerMasterDomain;
import in.pms.master.model.PostTrackerMasterModel;

@Service
public class PostTrackerMasterServiceImpl implements PostTrackerMasterService{
	
	@Autowired
	PostTrackerMasterDao postTrackerMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_POST_TRACKER_MST')")
	public long saveUpdatePostTrackerMaster(PostTrackerMasterModel postTrackerMasterModel){
		PostTrackerMasterDomain postTrackerMasterDomain = convertPostTrackerMasterModelToDomain(postTrackerMasterModel);
		return postTrackerMasterDao.save(postTrackerMasterDomain).getNumId();
	}
	
	
	
	private PostTrackerMasterDomain convertPostTrackerMasterModelToDomain(PostTrackerMasterModel postTrackerMasterModel) {
		PostTrackerMasterDomain postTrackerMasterDomain = new PostTrackerMasterDomain();
		
		if(postTrackerMasterModel.getNumId()!=0){				
			postTrackerMasterDomain =  postTrackerMasterDao.getOne(postTrackerMasterModel.getNumId());
		}
		postTrackerMasterDomain.setStrPostName(postTrackerMasterModel.getPostName());
		postTrackerMasterDomain.setStrPostDescription(postTrackerMasterModel.getPostDescription());
		postTrackerMasterDomain.setNumBaseSalary(postTrackerMasterModel.getBaseSalary());
		postTrackerMasterDomain.setStrVacancyType(postTrackerMasterModel.getVacancyType());
		postTrackerMasterDomain.setNumMinExperience(postTrackerMasterModel.getMinExperience());
        postTrackerMasterDomain.setNumApprovedPost(postTrackerMasterModel.getApprovedPost());
        postTrackerMasterDomain.setNumNoticePeriod(postTrackerMasterModel.getNoticePeriod());
        postTrackerMasterDomain.setStrCode(postTrackerMasterModel.getStrCode());
        postTrackerMasterDomain.setNumValidityInMonths(postTrackerMasterModel.getNumValidity());
		if(null !=postTrackerMasterModel.getStartDate()){
			try {
				postTrackerMasterDomain.setDtPostStartDate(DateUtils.dateStrToDate(postTrackerMasterModel.getStartDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null !=postTrackerMasterModel.getEndDate()){
			try {
				postTrackerMasterDomain.setDtPostEndDate(DateUtils.dateStrToDate(postTrackerMasterModel.getEndDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}		
	
		postTrackerMasterDomain.setDtTrDate(new Date());
		
		
		postTrackerMasterDomain.setNumIsValid(1);
		
		return postTrackerMasterDomain;
	
}



	@Override
	public List<PostTrackerMasterModel> getAllActivePostTrackerMasterDomain() {				
		return convertPostTrackerMasterDomainToModelList(postTrackerMasterDao.getAllActivePostTrackerMasterDomain());
	}

	@Override
	@PreAuthorize("hasAuthority('READ_POST_TRACKER_MST')")
	public List<PostTrackerMasterModel> getAllPostTrackerMasterDomain(){		
		return convertPostTrackerMasterDomainToModelList(postTrackerMasterDao.getAllActivePostTrackerMasterDomain());
	}
	
	private PostTrackerMasterModel convertPostTrackerMasterDomainToModel(PostTrackerMasterDomain postTrackerMasterDomain) {
		PostTrackerMasterModel postTrackerMasterModel = new PostTrackerMasterModel();
		postTrackerMasterModel.setPostName(postTrackerMasterDomain.getStrPostName());
		postTrackerMasterModel.setPostDescription(postTrackerMasterDomain.getStrPostDescription());
		postTrackerMasterModel.setBaseSalary(postTrackerMasterDomain.getNumBaseSalary());
		postTrackerMasterModel.setVacancyType(postTrackerMasterDomain.getStrVacancyType());
		postTrackerMasterModel.setMinExperience(postTrackerMasterDomain.getNumMinExperience());
		postTrackerMasterModel.setApprovedPost(postTrackerMasterDomain.getNumApprovedPost());
		postTrackerMasterModel.setNoticePeriod(postTrackerMasterDomain.getNumNoticePeriod());
		postTrackerMasterModel.setNumValidity(postTrackerMasterDomain.getNumValidityInMonths());
		if(postTrackerMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+postTrackerMasterDomain.getNumId());
			postTrackerMasterModel.setEncStrId(encryptedId);
		}
		
		if(null != postTrackerMasterDomain.getDtPostStartDate() && !postTrackerMasterDomain.getDtPostStartDate().equals("")){
			
			postTrackerMasterModel.setStartDate(DateUtils.dateToString(postTrackerMasterDomain.getDtPostStartDate()));
		}
		if(null != postTrackerMasterDomain.getDtPostEndDate() && !postTrackerMasterDomain.getDtPostEndDate().equals("")){
		
		postTrackerMasterModel.setEndDate(DateUtils.dateToString(postTrackerMasterDomain.getDtPostEndDate()));
		}
		postTrackerMasterModel.setNumId(postTrackerMasterDomain.getNumId());
		if(postTrackerMasterDomain.getNumIsValid()==1){
			postTrackerMasterModel.setValid(true);
		}else{
			postTrackerMasterModel.setValid(false);
		}
		
		return postTrackerMasterModel;
	}
	
	private List<PostTrackerMasterModel> convertPostTrackerMasterDomainToModelList(List<PostTrackerMasterDomain> postTrackerMasterDomainList) {
		List<PostTrackerMasterModel> list = new  ArrayList<PostTrackerMasterModel>();
		for(int i=0;i<postTrackerMasterDomainList.size();i++){
			PostTrackerMasterModel postTrackerMasterModel = new PostTrackerMasterModel();
			PostTrackerMasterDomain postTrackerMasterDomain = postTrackerMasterDomainList.get(i);
			postTrackerMasterModel.setPostName(postTrackerMasterDomain.getStrPostName());
			postTrackerMasterModel.setPostDescription(postTrackerMasterDomain.getStrPostDescription());
			postTrackerMasterModel.setBaseSalary(postTrackerMasterDomain.getNumBaseSalary());
			postTrackerMasterModel.setVacancyType(postTrackerMasterDomain.getStrVacancyType());
			postTrackerMasterModel.setMinExperience(postTrackerMasterDomain.getNumMinExperience());
			postTrackerMasterModel.setApprovedPost(postTrackerMasterDomain.getNumApprovedPost());
			postTrackerMasterModel.setNoticePeriod(postTrackerMasterDomain.getNumNoticePeriod());
			postTrackerMasterModel.setStrCode(postTrackerMasterDomain.getStrCode());
			postTrackerMasterModel.setNumValidity(postTrackerMasterDomain.getNumValidityInMonths());
			if(postTrackerMasterDomain.getNumId() != 0){
				String encryptedId = encryptionService.encrypt(""+postTrackerMasterDomain.getNumId());
				postTrackerMasterModel.setEncStrId(encryptedId);
			}
			
			if(null != postTrackerMasterDomain.getDtPostStartDate()){				
				postTrackerMasterModel.setStartDate(DateUtils.dateToString(postTrackerMasterDomain.getDtPostStartDate()));
			}
			
			if(null != postTrackerMasterDomain.getDtPostEndDate()){			
				postTrackerMasterModel.setEndDate(DateUtils.dateToString(postTrackerMasterDomain.getDtPostEndDate()));
			}
			postTrackerMasterModel.setNumId(postTrackerMasterDomain.getNumId());
			if(postTrackerMasterDomain.getNumIsValid()==1){
				postTrackerMasterModel.setValid(true);
			}else{
				postTrackerMasterModel.setValid(false);
			}
			list.add(postTrackerMasterModel);
		}
		return list;
	}
	
	public PostTrackerMasterDomain getPostTrackerDomainById(long numId){
		return postTrackerMasterDao.getPostTrackerMasterDomainById(numId);
		
	}
}