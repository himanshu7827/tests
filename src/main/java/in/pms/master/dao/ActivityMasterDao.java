package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ActivityMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityMasterDao {



	@Autowired
	DaoHelper daoHelper;	
	
public long saveUpdateActivityMaster(ActivityMasterDomain activityMasterDomain){
		
	activityMasterDomain =daoHelper.merge(ActivityMasterDomain.class, activityMasterDomain);		
		return activityMasterDomain.getNumId();
	}
	
	public ActivityMasterDomain getActivityMasterById(long id){
		List<ActivityMasterDomain> activityMasterrList =  daoHelper.findByQuery("from ActivityMasterDomain where numId="+id);
		if(activityMasterrList.size()>0){
			return activityMasterrList.get(0);
		}
		return null;
	}
	
	public ActivityMasterDomain getActivityMasterByName(String strActivityName){
		String query = "from ActivityMasterDomain where lower(strActivityName)=lower('"+strActivityName+"') and numIsValid<>2" ;	
		List<ActivityMasterDomain> activityMasterDomainList = daoHelper.findByQuery(query);		
		if(activityMasterDomainList.size()>0){
			return activityMasterDomainList.get(0);
		}
		return null;
	}


	public List<ActivityMasterDomain> getAllActivityMasterDomain(){
		String query = "select distinct  a from ActivityMasterDomain a join fetch a.subActivityMasterDomain  where a.numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<ActivityMasterDomain> getAllActiveActivityMasterDomain(){
		String query = "from ActivityMasterDomain where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}

	
	public List<ActivityMasterDomain> getActivityMasterById(String ids){
		List<ActivityMasterDomain> activityMasterrList =  daoHelper.findByQuery ("from ActivityMasterDomain where numId in ("+ids+")");		
		return activityMasterrList;
	}
	
	public ActivityMasterDomain getActivityDomainById(long id){
		return daoHelper.findById(ActivityMasterDomain.class, id);
	}
	
}
