package in.pms.transaction.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.transaction.dao.CollaborativeOrgDetailsDao;
import in.pms.transaction.domain.Application;
import in.pms.transaction.domain.CollaborativeOrgDetailsDomain;
import in.pms.transaction.model.CollaborativeOrgDetailsModel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CollaborativeOrgDetailsServiceImpl implements CollaborativeOrgDetailsService{

	
	@Autowired
	CollaborativeOrgDetailsDao collaborativeOrgDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	

	@Override
	public JSONArray getAllActiveCollaborativeOrgDetailsDomain(long applicationId) {		
		return convertCollaborativeOrgDetailsDomainToModelList(collaborativeOrgDetailsDao.getAllActiveCollaborativeOrgDetailsDomain(applicationId));		
	}

	
	
	
	@Override
	public CollaborativeOrgDetailsDomain getCollaborativeOrgDetailsById(long numId){
		return collaborativeOrgDetailsDao.getCollaborativeOrgDetailsDomainById(numId);
	}
	
	
	@Override	
	public JSONArray saveUpdateCollaborativeOrgDetails(CollaborativeOrgDetailsModel collaborativeOrgDetailsModel) {
		int result=0;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		 		 
		long applicationId =collaborativeOrgDetailsModel.getApplicationId();
		Application application = new Application();
		application.setNumId(applicationId);
		String inputData = collaborativeOrgDetailsModel.getInputData();
		if(null != inputData && !inputData.equals("")){
			JSONArray jsonArray = new JSONArray(inputData);
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonObject = jsonArray.getJSONObject(i);			   
			    CollaborativeOrgDetailsDomain domain = convertJSONObjectToDomain(jsonObject);
			    domain.setNumTrUserId(userInfo.getEmployeeId());
			    domain.setApplication(application);
				
				long numId=  collaborativeOrgDetailsDao.mergeCollaborativeOrgDetails(domain);
				if(numId>0){
					result++;
				}
			}
		}
		if(result>0){
			return convertCollaborativeOrgDetailsDomainToModelList(collaborativeOrgDetailsDao.getAllActiveCollaborativeOrgDetailsDomain(applicationId));
		}else{
			return null;
		}
	}
	

	public CollaborativeOrgDetailsDomain convertJSONObjectToDomain(JSONObject jsonObject) {	

		CollaborativeOrgDetailsDomain domain = new CollaborativeOrgDetailsDomain();
		domain.setNumId(jsonObject.getLong("numId"));

		domain.setDtTrDate(new Date());
		domain.setNumIsValid(1);
		domain.setOrganisationName(jsonObject.getString("organisationName"));
		domain.setContactNumber(jsonObject.getString("contactNumber"));
		domain.setEmail(jsonObject.getString("email"));
		domain.setWebsite(jsonObject.getString("website"));
		domain.setOrganisationAddress(jsonObject.getString("address"));
		return domain;
	}
	
	
	
	/*public CollaborativeOrgDetailsDomain convertCollaborativeOrgDetailsModelToDomain(CollaborativeOrgDetailsModel collaborativeOrgDetailsModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		CollaborativeOrgDetailsDomain collaborativeOrgDetailsDomain = new CollaborativeOrgDetailsDomain();
		if(collaborativeOrgDetailsModel.getNumId()!=0){
					
			collaborativeOrgDetailsDomain =  collaborativeOrgDetailsDao.getCollaborativeOrgDetailsDomainById(collaborativeOrgDetailsModel.getNumId());
		}
		collaborativeOrgDetailsDomain.setOrganisationName(collaborativeOrgDetailsModel.getOrganisationName());
		collaborativeOrgDetailsDomain.setOrganisationAddress(collaborativeOrgDetailsModel.getOrganisationAddress());
		collaborativeOrgDetailsDomain.setContactNumber(collaborativeOrgDetailsModel.getContactNumber());
		collaborativeOrgDetailsDomain.setEmail(collaborativeOrgDetailsModel.getEmail());
		collaborativeOrgDetailsDomain.setWebsite(collaborativeOrgDetailsModel.getWebsite());
		collaborativeOrgDetailsDomain.setDtTrDate(new Date());
		
		if(collaborativeOrgDetailsModel.isValid()){
			collaborativeOrgDetailsDomain.setNumIsValid(1);
		}else{
			collaborativeOrgDetailsDomain.setNumIsValid(0);

		}
		return collaborativeOrgDetailsDomain;
	}
	
	public CollaborativeOrgDetailsModel convertCollaborativeOrgDetailsDomainToModel(CollaborativeOrgDetailsDomain collaborativeOrgDetailsDomain){
		CollaborativeOrgDetailsModel collaborativeOrgDetailsModel = new CollaborativeOrgDetailsModel();
		collaborativeOrgDetailsModel.setOrganisationName(collaborativeOrgDetailsDomain.getOrganisationName());
		collaborativeOrgDetailsModel.setOrganisationAddress(collaborativeOrgDetailsDomain.getOrganisationAddress());
		collaborativeOrgDetailsModel.setContactNumber(collaborativeOrgDetailsDomain.getContactNumber());
		collaborativeOrgDetailsModel.setEmail(collaborativeOrgDetailsDomain.getEmail());
		collaborativeOrgDetailsModel.setWebsite(collaborativeOrgDetailsDomain.getWebsite());
		if(collaborativeOrgDetailsDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+collaborativeOrgDetailsDomain.getNumId());
			collaborativeOrgDetailsModel.setEncStrId(encryptedId);
		}
		collaborativeOrgDetailsModel.setNumId(collaborativeOrgDetailsDomain.getNumId());
		if(collaborativeOrgDetailsDomain.getNumIsValid()==1){
			collaborativeOrgDetailsModel.setValid(true);
		}else{
			collaborativeOrgDetailsModel.setValid(false);
		}
			
		
		return collaborativeOrgDetailsModel;
	}*/
	
	public JSONArray convertCollaborativeOrgDetailsDomainToModelList(List<CollaborativeOrgDetailsDomain> collaborativeOrgDetailsDomainList){
		JSONArray outputArray = new JSONArray();		
			for(int i=0;i<collaborativeOrgDetailsDomainList.size();i++){
				CollaborativeOrgDetailsDomain collaborativeOrgDetailsDomain = collaborativeOrgDetailsDomainList.get(i);

				JSONObject object = new JSONObject();
				object.put("numId", collaborativeOrgDetailsDomain.getNumId());
				object.put("encNumId", encryptionService.encrypt(""+collaborativeOrgDetailsDomain.getNumId()));
				object.put("organisationName", collaborativeOrgDetailsDomain.getOrganisationName());
				object.put("organisationAddress", collaborativeOrgDetailsDomain.getOrganisationAddress());
				
				
				
				object.put("contactNumber", collaborativeOrgDetailsDomain.getContactNumber());
				
				if(null != collaborativeOrgDetailsDomain.getEmail() && !collaborativeOrgDetailsDomain.getEmail().equals("")){
				object.put("email", collaborativeOrgDetailsDomain.getEmail());
				}else{
					object.put("email", "");
				}
				
				if(null != collaborativeOrgDetailsDomain.getWebsite() && !collaborativeOrgDetailsDomain.getWebsite().equals("")){
					object.put("website", collaborativeOrgDetailsDomain.getWebsite());
				}else{
					object.put("website", "");
				}
				outputArray.put(object);
	}
		return outputArray;
	}
	
	
	@Override
	public long deleteCollaborativeOrgDetails(CollaborativeOrgDetailsModel collaborativeOrgDetailsModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String strOrganisation = encryptionService.dcrypt(collaborativeOrgDetailsModel.getEncId());
		long organisationId = Long.parseLong(strOrganisation);
		
		CollaborativeOrgDetailsDomain domain = collaborativeOrgDetailsDao.getCollaborativeOrgDetailsDomainById(organisationId);
		
		domain.setNumIsValid(0);
		domain.setDtTrDate(new Date());
		domain.setNumTrUserId(userInfo.getEmployeeId());
		collaborativeOrgDetailsDao.mergeCollaborativeOrgDetails(domain);
		
		return organisationId;
	}


}
