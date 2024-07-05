package in.pms.master.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.master.domain.ThirdPartyMasterDomain;

@Repository

public class ThirdPartyMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateThirdPartyMaster(ThirdPartyMasterDomain thirdPartyMasterDomain){
		thirdPartyMasterDomain = daoHelper.merge(ThirdPartyMasterDomain.class, thirdPartyMasterDomain);
		return thirdPartyMasterDomain.getNumId();
	}
	
	public ThirdPartyMasterDomain getThirdPartyMasterByName(String agencyName){
		String query = "from ThirdPartyMasterDomain where strAgencyName='"+agencyName+"'";	
		List<ThirdPartyMasterDomain> thirdPartyMasterDomainList = daoHelper.findByQuery(query);		
		if(thirdPartyMasterDomainList.size()>0){
			return thirdPartyMasterDomainList.get(0);
		}
		return null;
	}
	
	public ThirdPartyMasterDomain getThirdPartyMasterDomainById(long numId){
		ThirdPartyMasterDomain thirdPartyMasterDomain =null;
		String query = "from ThirdPartyMasterDomain where numId="+numId;
		List<ThirdPartyMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			thirdPartyMasterDomain =list.get(0);
		}
		return thirdPartyMasterDomain;	
	}
	
	public List<ThirdPartyMasterDomain> getAllThirdPartyMasterDomain(){
		String query = "from ThirdPartyMasterDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<ThirdPartyMasterDomain> getAllActiveThirdPartyMasterDomain(){
		String query = "from ThirdPartyMasterDomain where numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	/*public long getSequence(){
		long seq=0;
		String query = " select nextval('TP_EMP_SEQ')";
		List<BigInteger> list=daoHelper.runNative(query);
	   if(list.size()>0){
		seq =list.get(0).longValue();
	   }
		return seq;
	}*/

	
	public void deleteThirdPartyMaster(ThirdPartyMasterDomain thirdPartyMasterDomain)
	{
		ThirdPartyMasterDomain thirdparty = daoHelper.findById(ThirdPartyMasterDomain.class, thirdPartyMasterDomain.getNumId());
		thirdparty.setNumIsValid(2);;
		thirdparty.setDtTrDate(thirdPartyMasterDomain.getDtTrDate());
		daoHelper.merge(ThirdPartyMasterDomain.class, thirdparty);
	}
}
