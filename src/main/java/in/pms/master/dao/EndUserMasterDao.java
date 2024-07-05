package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.EndUserMasterDomain;
import in.pms.transaction.domain.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EndUserMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateEndUserMaster(EndUserMasterDomain EndUserMasterDomain){
		EndUserMasterDomain = daoHelper.merge(EndUserMasterDomain.class, EndUserMasterDomain);
		return EndUserMasterDomain.getNumId();
	}
	
	public EndUserMasterDomain getEndUserMasterByName(String clientName){
		String query = "from EndUserMasterDomain where clientName='"+clientName+"'";	
		List<EndUserMasterDomain> EndUserMasterDomainList = daoHelper.findByQuery(query);		
		if(EndUserMasterDomainList.size()>0){
			return EndUserMasterDomainList.get(0);
		}
		return null;
	}
	
	public EndUserMasterDomain getEndUserMasterDomainById(long numId){
		EndUserMasterDomain EndUserMasterDomain =null;
		String query = "from EndUserMasterDomain where numId="+numId;
		List<EndUserMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			EndUserMasterDomain =list.get(0);
		}
		return EndUserMasterDomain;	
	}
	
	public List<EndUserMasterDomain> getAllEndUserMasterDomain(){
		String query = "from EndUserMasterDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<EndUserMasterDomain> getAllActiveEndUserMasterDomain(){
		String query = "from EndUserMasterDomain where numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<Application> getAllActiveEndUserMasterDomainByApplicationId(long applicationId){
	
		String query =" from Application a left join fetch a.clientMaster where a.numIsValid=1 and  a.numId="+applicationId;
		
		return  daoHelper.findByQuery(query.toString());	
	}
}
