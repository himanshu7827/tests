package in.pms.transaction.service;

import in.pms.transaction.domain.CollaborativeOrgDetailsDomain;
import in.pms.transaction.model.CollaborativeOrgDetailsModel;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public interface CollaborativeOrgDetailsService {
 
	public JSONArray getAllActiveCollaborativeOrgDetailsDomain(long applicationId);
	@Transactional
	public	JSONArray saveUpdateCollaborativeOrgDetails(CollaborativeOrgDetailsModel collaborativeOrgDetailsModel);

	public CollaborativeOrgDetailsDomain getCollaborativeOrgDetailsById(long numId);
	
	@Transactional
	public long deleteCollaborativeOrgDetails(CollaborativeOrgDetailsModel collaborativeOrgDetailsModel);

	

}
