package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.EmpTypeMasterDomain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpTypeMasterDao {

	@Autowired
	DaoHelper daoHelper;	
	
public long saveUpdateEmpTypeMaster(EmpTypeMasterDomain empTypeMasterDomain){
		
	empTypeMasterDomain =daoHelper.merge(EmpTypeMasterDomain.class, empTypeMasterDomain);		
		return empTypeMasterDomain.getNumId();
	}
	
public EmpTypeMasterDomain getEmpTypeMasterById(long id){
	/*List<EmpTypeMasterDomain> empTypeMasterrList =  daoHelper.findByQuery("from EmpTypeMasterDomain where numId="+id);
	if(empTypeMasterrList.size()>0){
		return empTypeMasterrList.get(0);
	}
	return null;*/
	
	return daoHelper.findById(EmpTypeMasterDomain.class, id);
}
	
	public EmpTypeMasterDomain getEmpTypeMasterByName(String stremptypename){
		String query = "from EmpTypeMasterDomain where lower(strEmpTypeName)=lower('"+stremptypename+"') and numIsValid<>2" ;	
		List<EmpTypeMasterDomain> empTypeMasterDomainList = daoHelper.findByQuery(query);		
		if(empTypeMasterDomainList.size()>0){
			return empTypeMasterDomainList.get(0);
		}
		return null;
	}

	public List<EmpTypeMasterDomain> getAllEmpTypeMasterDomain(){
		String query = "from EmpTypeMasterDomain where numId<>0 and numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<EmpTypeMasterDomain> getAllActiveEmpTypeMasterDomain(){
		String query = "from EmpTypeMasterDomain where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}

	
	public List<EmpTypeMasterDomain> getEmpTypeMasterById(String ids){
		List<EmpTypeMasterDomain> empTypeMasterrList =  daoHelper.findByQuery ("from EmpTypeMasterDomain where numId in ("+ids+")");		
		return empTypeMasterrList;
	}
	
	
	public List<String> getdistinctEmpTypeNames() {
		StringBuffer query = new StringBuffer(
				"select empshortode  from EmpTypeMasterDomain e where  e.numIsValid=1 order by hierarchy");
		List<String> empTypeNamesList = daoHelper.findByQuery(query.toString());
		return empTypeNamesList;
	}

	
}
