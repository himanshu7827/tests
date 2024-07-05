package in.pms.transaction.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.transaction.domain.CollaborativeOrgDetailsDomain;

@Repository
public class CollaborativeOrgDetailsDao {
	
	@Autowired
	DaoHelper daoHelper;

	public long mergeCollaborativeOrgDetails(CollaborativeOrgDetailsDomain collaborativeOrgDetailsDomain) {
		collaborativeOrgDetailsDomain = daoHelper.merge(CollaborativeOrgDetailsDomain.class, collaborativeOrgDetailsDomain);
		return collaborativeOrgDetailsDomain.getNumId();
	}

	
	public List<CollaborativeOrgDetailsDomain> getAllCollaborativeOrgDetailsDomain(){
		String query = "from CollaborativeOrgDetailsDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}


	public List<CollaborativeOrgDetailsDomain> getAllActiveCollaborativeOrgDetailsDomain(long applicationId){
		String query = "from CollaborativeOrgDetailsDomain a where a.numIsValid=1 and a.application.numId="+applicationId;
		return  daoHelper.findByQuery(query);	
	}
	
	public CollaborativeOrgDetailsDomain getCollaborativeOrgDetailsByName(String organisationName){
		String query = "from CollaborativeOrgDetailsDomain where lower(organisationName)=lower('"+organisationName+"') and numIsValid<>2";	
		List<CollaborativeOrgDetailsDomain> collaborativeOrgDetailsDomainList = daoHelper.findByQuery(query);		
		if(collaborativeOrgDetailsDomainList.size()>0){
			return collaborativeOrgDetailsDomainList.get(0);
		}
		return null;
	}
	
	
	public CollaborativeOrgDetailsDomain getCollaborativeOrgDetailsDomainById(long numId){
		return daoHelper.findById(CollaborativeOrgDetailsDomain.class, numId);
		
	}
	
}
