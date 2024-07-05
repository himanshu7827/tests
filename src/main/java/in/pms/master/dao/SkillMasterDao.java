package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.SkillMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SkillMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public SkillMasterDomain  mergeSkillMasterDomain(SkillMasterDomain skillMasterDomain){
		return daoHelper.merge(SkillMasterDomain.class, skillMasterDomain);
		 
	}
	
	
	
	public SkillMasterDomain getSkillByName(String skillName){
		String query = "from SkillMasterDomain where  numIsValid=1 and lower(strSkillName)=lower('"+skillName+"')" ;	
		List<SkillMasterDomain> skillList = daoHelper.findByQuery(query);		
		if(skillList.size()>0){
			return skillList.get(0);
		}
		return null;
	}
	
	public SkillMasterDomain getSkillDataById(long id){
		String query = "from SkillMasterDomain s where s.numId ="+id;
		List<SkillMasterDomain> dataList = daoHelper.findByQuery(query);
		if(dataList.size()>0){
			return dataList.get(0);
		}
		return null;
	}
	
	

	
	public List<SkillMasterDomain> getActiveSkillData(){
		String query = "from SkillMasterDomain where numIsValid=1 ";
		return  daoHelper.findByQuery(query);	
	}
	
	
	
}
