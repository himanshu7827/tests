package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;



import in.pms.master.dao.ActivityMasterDao;
import in.pms.master.domain.ActivityMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.SubActivityMasterDomain;
import in.pms.master.model.ActivityMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityMasterServiceImpl implements ActivityMasterService{



	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	SubActivityMasterService subActivityMasterService;
	
	@Autowired
	ActivityMasterDao activityMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_ACTIVITY_MST')")
	public long saveUpdateActivityMaster(ActivityMasterModel activityMasterModel){
		ActivityMasterDomain activityMasterDomain = convertActivityMasterModelToDomain(activityMasterModel);
		return activityMasterDao.saveUpdateActivityMaster(activityMasterDomain);
	}
		
	@Override
	public String checkDuplicateActivityName(ActivityMasterModel activityMasterModel){
		String result=	"";
		ActivityMasterDomain activityMasterDomain = activityMasterDao.getActivityMasterByName(activityMasterModel.getStrActivityName());
		
		 if(null == activityMasterDomain){
				return null; 
			 }else if(activityMasterModel.getNumId() != 0){
				 if(activityMasterDomain.getNumId() == activityMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Activity with same name already exist with Id "+activityMasterDomain.getNumId();
				 }
			 }else{
				if(activityMasterDomain.getNumIsValid() == 0){
					result = "Activity Details already exist with Id "+activityMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Activity Details already exist with Id "+activityMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ActivityMasterModel getActivityMasterDomainById(long numId){
		return convertActivityMasterDomainToModel(activityMasterDao.getActivityMasterById(numId));
	}
	
	@Override
//	@PreAuthorize("hasAuthority('READ_ACTIVITY_MST')")
	public List<ActivityMasterModel> getAllActivityMasterDomain(){
		return convertActivityMasterDomainToModelList(activityMasterDao.getAllActivityMasterDomain(),true);
	}
	
	@Override
	public List<ActivityMasterModel> getAllActiveActivityMasterDomain(){
		return convertActivityMasterDomainToModelList(activityMasterDao.getAllActiveActivityMasterDomain(),false);
	}
	
	public ActivityMasterDomain convertActivityMasterModelToDomain(ActivityMasterModel activityMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ActivityMasterDomain activityMasterDomain = new ActivityMasterDomain();
		if(activityMasterModel.getNumId()!=0){				
			activityMasterDomain =  activityMasterDao.getActivityMasterById(activityMasterModel.getNumId());
		}
		
		activityMasterDomain.setDtTrDate(new Date());
		activityMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(activityMasterModel.isValid()){
			activityMasterDomain.setNumIsValid(1);
		}else{
			activityMasterDomain.setNumIsValid(0);
		}
		
		activityMasterDomain.setStrActivityName(activityMasterModel.getStrActivityName());
		System.out.println(activityMasterModel.getNumSubActivityId());
		activityMasterDomain.setSubActivityMasterDomain(subActivityMasterService.getSubActivityDomainById(activityMasterModel.getNumSubActivityId()));

		
		return activityMasterDomain;
	}
	
	public List<ActivityMasterModel> convertActivityMasterDomainToModelList(List<ActivityMasterDomain> activityMasterList,boolean activeFlag){
		
		List<ActivityMasterModel> list = new ArrayList<ActivityMasterModel>();
			for(int i=0;i<activityMasterList.size();i++){
				ActivityMasterDomain activityMasterDomain = activityMasterList.get(i);
				ActivityMasterModel activityMasterModel = new ActivityMasterModel();
				
				if(activityMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+activityMasterDomain.getNumId());
					activityMasterModel.setEncOrganisationId(encryptedId);
				}
				activityMasterModel.setNumId(activityMasterDomain.getNumId());
				if(activityMasterDomain.getNumIsValid() ==1){
					activityMasterModel.setValid(true);
				}else{
					activityMasterModel.setValid(false);
				}
			
		
				activityMasterModel.setStrActivityName(activityMasterDomain.getStrActivityName());
			
			if(activeFlag){
				List<SubActivityMasterDomain>  subActivities = activityMasterDomain.getSubActivityMasterDomain();
				
				String subActivityNames="";
				String subActivityIds="";
				
				for(int j=0;j<subActivities.size();j++){
					SubActivityMasterDomain subActivity =subActivities.get(j);
					long subActivityId = subActivity.getNumId();
					String subActivityName =subActivity.getStrSubActivityName();
					if(j == subActivities.size()-1){
						subActivityIds+=subActivityId;
						subActivityNames+=subActivityName;
					}else{
						subActivityIds+=subActivityId+",";
						subActivityNames+=subActivityName+" || ";
					}
					activityMasterModel.setSubActivityIds(subActivityIds);
					activityMasterModel.setSubActivityNames(subActivityNames);
				}
			}
				
				list.add(activityMasterModel);
	}
		return list;
	}

	
	
	public ActivityMasterModel convertActivityMasterDomainToModel(ActivityMasterDomain activityMasterDomain){
		ActivityMasterModel activityMasterModel = new ActivityMasterModel();
	
		if(activityMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+activityMasterDomain.getNumId());
			activityMasterModel.setEncOrganisationId(encryptedId);
		}
		activityMasterModel.setNumId(activityMasterDomain.getNumId());
		if(activityMasterDomain.getNumIsValid() ==1){
			activityMasterModel.setValid(true);
		}else{
			activityMasterModel.setValid(false);
		}
	
		
		activityMasterModel.setStrActivityName(activityMasterDomain.getStrActivityName());
		
		
		return activityMasterModel;
		
	}
	public long deleteActivity(ActivityMasterModel activityMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ActivityMasterDomain> activityMasterList = activityMasterDao.getActivityMasterById(activityMasterModel.getIdCheck());
		for(int i=0;i<activityMasterList.size();i++){
			ActivityMasterDomain activityMasterDomain = activityMasterList.get(i);
			activityMasterDomain.setNumIsValid(2);
			activityMasterDomain.setDtTrDate(new Date());
			activityMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = activityMasterDao.saveUpdateActivityMaster(activityMasterDomain);
		}
		return result;
	}	
	
	@Override
	 public ActivityMasterDomain getActivityDomainById(long numActivityId){
		return activityMasterDao.getActivityDomainById(numActivityId);
	}
	
	
}
