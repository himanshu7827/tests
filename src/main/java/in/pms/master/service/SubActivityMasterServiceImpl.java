package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;



import in.pms.master.dao.SubActivityMasterDao;
import in.pms.master.domain.ActivityMasterDomain;
import in.pms.master.domain.SubActivityMasterDomain;
import in.pms.master.model.SubActivityMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubActivityMasterServiceImpl implements SubActivityMasterService{

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	SubActivityMasterDao subActivityMasterDao;
	
	@Autowired
	ActivityMasterService activityMasterService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_SUBACTIVITY_MST')")
	public long saveUpdateSubActivityMaster(SubActivityMasterModel subActivityMasterModel){
		SubActivityMasterDomain subActivityMasterDomain = convertSubActivityMasterModelToDomain(subActivityMasterModel);
		return subActivityMasterDao.saveUpdateSubActivityMaster(subActivityMasterDomain);
	}
		
	@Override
	public String checkDuplicateSubActivityName(SubActivityMasterModel subActivityMasterModel){
		String result=	"";
		SubActivityMasterDomain subActivityMasterDomain = subActivityMasterDao.getSubActivityMasterByName(subActivityMasterModel.getStrSubActivityName());
		
		 if(null == subActivityMasterDomain){
				return null; 
			 }else if(subActivityMasterModel.getNumId() != 0){
				 if(subActivityMasterDomain.getNumId() == subActivityMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "SubActivity with same name already exist with Id "+subActivityMasterDomain.getNumId();
				 }
			 }else{
				if(subActivityMasterDomain.getNumIsValid() == 0){
					result = "SubActivity Details already exist with Id "+subActivityMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "SubActivity Details already exist with Id "+subActivityMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public SubActivityMasterModel getSubActivityMasterDomainById(long numId){
		return convertSubActivityMasterDomainToModel(subActivityMasterDao.getSubActivityMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_SUBACTIVITY_MST')")
	public List<SubActivityMasterModel> getAllSubActivityMasterDomain(){
		return convertSubActivityMasterDomainToModelList(subActivityMasterDao.getAllSubActivityMasterDomain());
	}
	
	@Override
	public List<SubActivityMasterModel> getAllActiveSubActivityMasterDomain(){
		return convertSubActivityMasterDomainToModelList(subActivityMasterDao.getAllActiveSubActivityMasterDomain());
	}
	
	public SubActivityMasterDomain convertSubActivityMasterModelToDomain(SubActivityMasterModel subActivityMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		SubActivityMasterDomain subActivityMasterDomain = new SubActivityMasterDomain();
		if(subActivityMasterModel.getNumId()!=0){				
			subActivityMasterDomain =  subActivityMasterDao.getSubActivityMasterById(subActivityMasterModel.getNumId());
		}
		
		subActivityMasterDomain.setDtTrDate(new Date());
		subActivityMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(subActivityMasterModel.isValid()){
			subActivityMasterDomain.setNumIsValid(1);
		}else{
			subActivityMasterDomain.setNumIsValid(0);
		}
		
		subActivityMasterDomain.setStrSubActivityName(subActivityMasterModel.getStrSubActivityName());
		/*subActivityMasterDomain.setNumActivityId(subActivityMasterModel.getNumActivityId());*/
		
		/*subActivityMasterDomain.setActivityMasterDomain(activityMasterService.getActivityDomainById(subActivityMasterModel.getNumActivityId()));*/
		
		return subActivityMasterDomain;
	}
	
	public List<SubActivityMasterModel> convertSubActivityMasterDomainToModelList(List<SubActivityMasterDomain> subActivityMasterList){
		List<SubActivityMasterModel> list = new ArrayList<SubActivityMasterModel>();
			for(int i=0;i<subActivityMasterList.size();i++){
				SubActivityMasterDomain subActivityMasterDomain = subActivityMasterList.get(i);
				SubActivityMasterModel subActivityMasterModel = new SubActivityMasterModel();
				
				if(subActivityMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+subActivityMasterDomain.getNumId());
					subActivityMasterModel.setEncOrganisationId(encryptedId);
				}
				subActivityMasterModel.setNumId(subActivityMasterDomain.getNumId());
				//subActivityMasterDomain.get
				if(subActivityMasterDomain.getNumIsValid() ==1){
					subActivityMasterModel.setValid(true);
				}else{
					subActivityMasterModel.setValid(false);
				}
			
		
				subActivityMasterModel.setStrSubActivityName(subActivityMasterDomain.getStrSubActivityName());
			
			
				
				list.add(subActivityMasterModel);
	}
		return list;
	}

	
	
	public SubActivityMasterModel convertSubActivityMasterDomainToModel(SubActivityMasterDomain subActivityMasterDomain){
		SubActivityMasterModel subActivityMasterModel = new SubActivityMasterModel();
	
		if(subActivityMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+subActivityMasterDomain.getNumId());
			subActivityMasterModel.setEncOrganisationId(encryptedId);
		}
		subActivityMasterModel.setNumId(subActivityMasterDomain.getNumId());
		if(subActivityMasterDomain.getNumIsValid() ==1){
			subActivityMasterModel.setValid(true);
		}else{
			subActivityMasterModel.setValid(false);
		}
	
		
		subActivityMasterModel.setStrSubActivityName(subActivityMasterDomain.getStrSubActivityName());
		
		
		return subActivityMasterModel;
		
	}
	public long deleteSubActivity(SubActivityMasterModel subActivityMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<SubActivityMasterDomain> subActivityMasterList = subActivityMasterDao.getSubActivityMasterById(subActivityMasterModel.getIdCheck());
		for(int i=0;i<subActivityMasterList.size();i++){
			SubActivityMasterDomain subActivityMasterDomain = subActivityMasterList.get(i);
			subActivityMasterDomain.setNumIsValid(2);
			subActivityMasterDomain.setDtTrDate(new Date());
			subActivityMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = subActivityMasterDao.saveUpdateSubActivityMaster(subActivityMasterDomain);
		}
		return result;
	}	
	
	@Override
	 public List<SubActivityMasterDomain> getSubActivityDomainById(List<Long> numSubActivityId){
		return subActivityMasterDao.getSubActivityDomainById(numSubActivityId);
	}
	
	
}
