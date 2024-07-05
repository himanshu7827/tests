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
import in.pms.master.dao.SkillMasterDao;
import in.pms.master.domain.SkillMasterDomain;
import in.pms.master.model.SkillMasterModel;
@Service
public class SkillMasterServiceImpl implements SkillMasterService{
	
	@Autowired
	SkillMasterDao skillMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('READ_SKILL_MST')")
	public List<SkillMasterModel> getAllSkillData(){
		return convertSkillMasterDomainToModelList(skillMasterDao.getActiveSkillData());			
	}
	
	@Override
	public List<SkillMasterModel> getActiveSkillData(){
		return convertSkillMasterDomainToModelList(skillMasterDao.getActiveSkillData());			
	}

	public List<SkillMasterModel> convertSkillMasterDomainToModelList(List<SkillMasterDomain> skillDataList){
		List<SkillMasterModel> list = new ArrayList<SkillMasterModel>();
			for(int i=0;i<skillDataList.size();i++){
				SkillMasterDomain skillMasterDomain = skillDataList.get(i);
				SkillMasterModel skillMasterModel = new SkillMasterModel();
				skillMasterModel.setStrSkillName(skillMasterDomain.getStrSkillName());
				skillMasterModel.setNumId(skillMasterDomain.getNumId());
				if(skillMasterDomain.getNumIsValid()==1){
					skillMasterModel.setValid(true);
				}else{
					skillMasterModel.setValid(false);
				}
				
				list.add(skillMasterModel);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateSkillData(SkillMasterModel skillMasterModel){
		String result = "";
		SkillMasterDomain skillMasterDomain = skillMasterDao.getSkillByName(skillMasterModel.getStrSkillName());
	
		 if(null == skillMasterDomain){
			return null; 
		 }else if(skillMasterModel.getNumId() != 0){
			 if(skillMasterDomain.getNumId() == skillMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Skill name already exist with Id "+skillMasterDomain.getNumId();
			 }
		 }
		 else{
				if(skillMasterDomain.getNumIsValid() == 0){
					result = "Skill Already Registered with Id "+skillMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Skill Already Registered with Id "+skillMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_SKILL_MST')")
        public SkillMasterDomain saveSkillData(SkillMasterModel skillMasterModel){
		SkillMasterDomain skillMasterDomain = convertSkillMasterModelToDomain(skillMasterModel);
		return skillMasterDao.mergeSkillMasterDomain(skillMasterDomain);
	}
	
	public SkillMasterDomain convertSkillMasterModelToDomain(SkillMasterModel skillMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		SkillMasterDomain skillMasterDomain = new SkillMasterDomain();
		if(skillMasterModel.getNumId()!=0){		
			skillMasterDomain =  skillMasterDao.getSkillDataById(skillMasterModel.getNumId());
		}
		skillMasterDomain.setStrSkillName(skillMasterModel.getStrSkillName());
		skillMasterDomain.setDtTrDate(new Date());
		skillMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			skillMasterDomain.setNumIsValid(1);
			
	
		
		return skillMasterDomain;
	}


}
