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
import in.pms.master.dao.MilestoneTypeMasterDao;
import in.pms.master.dao.SkillMasterDao;
import in.pms.master.domain.MilestoneTypeMaster;
import in.pms.master.domain.SkillMasterDomain;
import in.pms.master.model.MilestoneTypeMasterForm;
import in.pms.master.model.SkillMasterModel;
@Service
public class MilestoneTypeMasterServiceImpl implements MilestoneTypeMasterService{
	
	@Autowired
	MilestoneTypeMasterDao milestoneTypeMasterDao;
	
	@Override
	
	public List<MilestoneTypeMasterForm> getAllMilestoneTypeData(){
		return convertMasterDomainToModelList(milestoneTypeMasterDao.getActiveMilestoneTypeData());			
	}
	
	@Override
	public List<MilestoneTypeMasterForm> getActiveSkillData(){
		return convertMasterDomainToModelList(milestoneTypeMasterDao.getActiveMilestoneTypeData());			
	}

	public List<MilestoneTypeMasterForm> convertMasterDomainToModelList(List<MilestoneTypeMaster> dataList){
		List<MilestoneTypeMasterForm> list = new ArrayList<MilestoneTypeMasterForm>();
			for(int i=0;i<dataList.size();i++){
				MilestoneTypeMaster milestoneTypeMaster = dataList.get(i);
				MilestoneTypeMasterForm milestoneTypeMasterForm = new MilestoneTypeMasterForm();
				milestoneTypeMasterForm.setStrMilestoneTypeName(milestoneTypeMaster.getStrMilestoneTypeName());
				milestoneTypeMasterForm.setNumId(milestoneTypeMaster.getNumId());
				if(milestoneTypeMaster.getNumIsValid()==1){
					milestoneTypeMasterForm.setValid(true);
				}else{
					milestoneTypeMasterForm.setValid(false);
				}
				
				list.add(milestoneTypeMasterForm);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateMilestoneTypeData(MilestoneTypeMasterForm milestoneTypeMasterForm){
		String result = "";
		MilestoneTypeMaster milestoneTypeMaster = milestoneTypeMasterDao.getMilestoneTypeByName(milestoneTypeMasterForm.getStrMilestoneTypeName());
	
		 if(null == milestoneTypeMaster){
			return null; 
		 }else if(milestoneTypeMasterForm.getNumId() != 0){
			 if(milestoneTypeMaster.getNumId() == milestoneTypeMasterForm.getNumId()){
				 return null; 
			 }else{
				 result = "Milestone Type name already exist with Id "+milestoneTypeMaster.getNumId();
			 }
		 }
		 else{
				if(milestoneTypeMaster.getNumIsValid() == 0){
					result = "Milestone Type name Already Registered with Id "+milestoneTypeMaster.getNumId() +". Please activate same record";
				}else{
					result = "Milestone Type name Already Registered with Id "+milestoneTypeMaster.getNumId();
				}			
			 }
		return result;
	}
	
	@Override
	
        public MilestoneTypeMaster saveMilestoneTypeData(MilestoneTypeMasterForm milestoneTypeMasterForm){
		MilestoneTypeMaster milestoneTypeMaster = convertMasterModelToDomain(milestoneTypeMasterForm);
		return milestoneTypeMasterDao.mergeMilestoneTypeDomain(milestoneTypeMaster);
	}
	
	public MilestoneTypeMaster convertMasterModelToDomain(MilestoneTypeMasterForm milestoneTypeMasterForm){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		MilestoneTypeMaster milestoneTypeMaster = new MilestoneTypeMaster();
		if(milestoneTypeMasterForm.getNumId()!=0){		
			milestoneTypeMaster =  milestoneTypeMasterDao.getMilestoneTypeDataById(milestoneTypeMasterForm.getNumId());
		}
		milestoneTypeMaster.setStrMilestoneTypeName(milestoneTypeMasterForm.getStrMilestoneTypeName());
		milestoneTypeMaster.setDtTrDate(new Date());
		milestoneTypeMaster.setNumTrUserId(userInfo.getEmployeeId());
		milestoneTypeMaster.setNumIsValid(1);
			
	
		
		return milestoneTypeMaster;
	}


}
