package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.BusinessTypeMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessTypeDao {
	
	@Autowired
	DaoHelper daoHelper;

	public BusinessTypeMaster mergeBusinessType(BusinessTypeMaster businessTypeMaster) {		
		return daoHelper.merge(BusinessTypeMaster.class,businessTypeMaster);
	}

	
	public List<BusinessTypeMaster> getAllBusinessType() {
		String query = "From BusinessTypeMaster where numIsValid<>2";
		return daoHelper.findByQuery(query);
	}

	
	public List<BusinessTypeMaster> getActiveBusinessType() {
		String query = "From BusinessTypeMaster b where b.numIsValid=1 order by b.businessTypeName";
		return daoHelper.findByQuery(query);
	}

	
	public BusinessTypeMaster getBusinessTypeById(long id) {
		return daoHelper.findById(BusinessTypeMaster.class, id);
	}
	
	public List<String> getdistinctBusinessTypeNames() {
		StringBuffer query = new StringBuffer(
				"select distinct a.businessType.businessTypeName  from Application a where  a.numIsValid=1");

		List<String> businessTypeNamesList = daoHelper.findByQuery(query.toString());
		return businessTypeNamesList;
	}

}
