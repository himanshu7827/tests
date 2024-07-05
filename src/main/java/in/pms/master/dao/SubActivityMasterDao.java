package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ActivityMasterDomain;
import in.pms.master.domain.SubActivityMasterDomain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SubActivityMasterDao {


	@Autowired
	DaoHelper daoHelper;	
	
public long saveUpdateSubActivityMaster(SubActivityMasterDomain subActivityMasterDomain){
		
	subActivityMasterDomain =daoHelper.merge(SubActivityMasterDomain.class, subActivityMasterDomain);		
		return subActivityMasterDomain.getNumId();
	}
	
	public SubActivityMasterDomain getSubActivityMasterById(long id){
		List<SubActivityMasterDomain> subActivityMasterrList =  daoHelper.findByQuery("from SubActivityMasterDomain where numId="+id);
		if(subActivityMasterrList.size()>0){
			return subActivityMasterrList.get(0);
		}
		return null;
	}
	
	public SubActivityMasterDomain getSubActivityMasterByName(String strSubActivityName){
		String query = "from SubActivityMasterDomain where lower(strSubActivityName)=lower('"+strSubActivityName+"') and numIsValid<>2" ;	
		List<SubActivityMasterDomain> subActivityMasterDomainList = daoHelper.findByQuery(query);		
		if(subActivityMasterDomainList.size()>0){
			return subActivityMasterDomainList.get(0);
		}
		return null;
	}

	public List<SubActivityMasterDomain> getAllSubActivityMasterDomain(){
		String query = "from SubActivityMasterDomain where numId<>0 and numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<SubActivityMasterDomain> getAllActiveSubActivityMasterDomain(){
		String query = "from SubActivityMasterDomain where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}

	
	public List<SubActivityMasterDomain> getSubActivityMasterById(String ids){
		List<SubActivityMasterDomain> subActivityMasterrList =  daoHelper.findByQuery ("from SubActivityMasterDomain where numId in ("+ids+")");		
		return subActivityMasterrList;
	}
	
	public List<SubActivityMasterDomain> getSubActivityDomainById(List<Long> id){
		/*String query = "from SubActivityMasterDomain where numIsValid<>2 and numId in ("+id+")";
		return  daoHelper.findByQuery(query);	*/
		List<SubActivityMasterDomain> subActivityMasterrList =  daoHelper.findByIdList ("from SubActivityMasterDomain where numIsValid<>2 and numId in :ids", id);		
		return subActivityMasterrList;
	}
	
	
}
