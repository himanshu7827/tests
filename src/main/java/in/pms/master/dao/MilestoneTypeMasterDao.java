package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.MilestoneTypeMaster;
import in.pms.master.domain.SkillMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MilestoneTypeMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public MilestoneTypeMaster  mergeMilestoneTypeDomain(MilestoneTypeMaster milestoneTypeMaster){
		return daoHelper.merge(MilestoneTypeMaster.class, milestoneTypeMaster);
		 
	}
	
	
	
	public MilestoneTypeMaster getMilestoneTypeByName(String strMilestoneTypeName){
		String query = "from MilestoneTypeMaster where  numIsValid=1 and lower(strMilestoneTypeName)=lower('"+strMilestoneTypeName+"')" ;	
		List<MilestoneTypeMaster> skillList = daoHelper.findByQuery(query);		
		if(skillList.size()>0){
			return skillList.get(0);
		}
		return null;
	}
	
	public MilestoneTypeMaster getMilestoneTypeDataById(long id){
		String query = "from MilestoneTypeMaster s where s.numId ="+id;
		List<MilestoneTypeMaster> dataList = daoHelper.findByQuery(query);
		if(dataList.size()>0){
			return dataList.get(0);
		}
		return null;
	}
	
	

	
	public List<MilestoneTypeMaster> getActiveMilestoneTypeData(){
		String query = "from MilestoneTypeMaster where numIsValid=1 ";
		return  daoHelper.findByQuery(query);	
	}
	
	
	
}
