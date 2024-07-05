package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;


import in.pms.master.domain.ExpenditureHeadDomain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExpenditureHeadDao {

	@Autowired
	DaoHelper daoHelper;	
	
public long saveUpdateExpenditureHead(ExpenditureHeadDomain expenditureHeadDomain){
		
	expenditureHeadDomain =daoHelper.merge(ExpenditureHeadDomain.class, expenditureHeadDomain);		
		return expenditureHeadDomain.getNumId();
	}
	
	public ExpenditureHeadDomain getExpenditureHeadMasterById(long id){
		List<ExpenditureHeadDomain> expenditureHeadMasterrList =  daoHelper.findByQuery("from ExpenditureHeadDomain where numId="+id);
		if(expenditureHeadMasterrList.size()>0){
			return expenditureHeadMasterrList.get(0);
		}
		return null;
	}
	
	public ExpenditureHeadDomain getExpenditureHeadMasterByName(String strExpenditureHeadName){
		String query = "from ExpenditureHeadDomain where lower(strExpenditureHeadName)=lower('"+strExpenditureHeadName+"') and numIsValid<>2" ;	
		List<ExpenditureHeadDomain> expenditureHeadDomainList = daoHelper.findByQuery(query);		
		if(expenditureHeadDomainList.size()>0){
			return expenditureHeadDomainList.get(0);
		}
		return null;
	}

	public List<ExpenditureHeadDomain> getAllExpenditureHeadMasterDomain(){
		String query = "from ExpenditureHeadDomain where numId<>0 and numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<ExpenditureHeadDomain> getAllActiveExpenditureHeadMasterDomain(){
		String query = "from ExpenditureHeadDomain where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}

	
	public List<ExpenditureHeadDomain> getExpenditureHeadMasterById(String ids){
		List<ExpenditureHeadDomain> expenditureHeadMasterrList =  daoHelper.findByQuery ("from ExpenditureHeadDomain where numId in ("+ids+")");		
		return expenditureHeadMasterrList;
	}
	
}
