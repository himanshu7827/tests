package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ParentOrganisationMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentOrganisationMasterDao {
	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateParentOrganisationMaster(ParentOrganisationMaster parentOrganisationMaster){
		parentOrganisationMaster =daoHelper.merge(ParentOrganisationMaster.class, parentOrganisationMaster);		
		return parentOrganisationMaster.getNumId();
	}
	
	public ParentOrganisationMaster getParentOrganisationById(long id){
		return daoHelper.findById(ParentOrganisationMaster.class, id);
	}
	
	public ParentOrganisationMaster getParentOrganisationMasterByName(String organisationName){
		String query = "from ParentOrganisationMaster where lower(organisationName)=lower('"+organisationName+"')";	
		List<ParentOrganisationMaster> parentOrganisationMasterList = daoHelper.findByQuery(query);		
		if(parentOrganisationMasterList.size()>0){
			return parentOrganisationMasterList.get(0);
		}
		return null;
	}

	public List<ParentOrganisationMaster> getAllParentOrganisation(){
		String query = "from ParentOrganisationMaster where numId<>0 and numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<ParentOrganisationMaster> getAllActiveParentOrganisation(){
		String query = "from ParentOrganisationMaster where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
}
