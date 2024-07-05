package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ThrustAreaMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ThrustAreaMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long mergeThrustAreaMaster(ThrustAreaMasterDomain thrustAreaMasterDomain){
		thrustAreaMasterDomain = daoHelper.merge(ThrustAreaMasterDomain.class, thrustAreaMasterDomain);
		return thrustAreaMasterDomain.getNumId();
	}
	
	
	
	public ThrustAreaMasterDomain getThrustAreaByName(String thrustName){
		String query = "from ThrustAreaMasterDomain where  numIsValid!=2 and lower(strThrustAreaName)=lower('"+thrustName+"')" ;	
		List<ThrustAreaMasterDomain> thrustAreaList = daoHelper.findByQuery(query);		
		if(thrustAreaList.size()>0){
			return thrustAreaList.get(0);
		}
		return null;
	}
	
	public ThrustAreaMasterDomain getAllThrustDataById(long id){
		ThrustAreaMasterDomain thrustAreaMasterDomain =null;
		String query = "from ThrustAreaMasterDomain t where t.numId ="+id;
		List<ThrustAreaMasterDomain> dataList = daoHelper.findByQuery(query);
		if(dataList.size()>0){
			return dataList.get(0);
		}
		return null;
	}
	
	public List<ThrustAreaMasterDomain> getAllThrustDataByIdList(List<Long> ids){
		ThrustAreaMasterDomain thrustAreaMasterDomain =null;
		String query = "from ThrustAreaMasterDomain t where t.numId in :ids";
		return daoHelper.findByIdList(query, ids);
	}
	
	public List<ThrustAreaMasterDomain> getAllThrustData(){
		String query = "from ThrustAreaMasterDomain where numIsValid in(0,1)";
		return  daoHelper.findByQuery(query);	
	}
	
	
	public List<ThrustAreaMasterDomain> getActiveThrustData(){
		String query = "from ThrustAreaMasterDomain where numIsValid=1 order by strThrustAreaName";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<ThrustAreaMasterDomain> getThrustAreaData(String ids){
		String query = "from ThrustAreaMasterDomain where numId in ("+ids+")";
		return  daoHelper.findByQuery(query);
	}
	
	
}
