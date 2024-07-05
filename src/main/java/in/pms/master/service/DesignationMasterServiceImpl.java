package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.DesignationMasterDao;
import in.pms.master.domain.DesignationMasterDomain;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.master.model.DesignationMasterModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationMasterServiceImpl implements DesignationMasterService{

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	DesignationMasterDao designationMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_DESIGNATION_MST')")
	public long saveUpdateDesignationMaster(DesignationMasterModel designationMasterModel){
		DesignationMasterDomain designationMasterDomain = convertDesignationMasterModelToDomain(designationMasterModel);
		return designationMasterDao.saveUpdateDesignationMaster(designationMasterDomain);
	}
		
	@Override
	public String checkDuplicateDesignationName(DesignationMasterModel designationMasterModel){
		String result=	"";
		DesignationMasterDomain designationMasterDomain = designationMasterDao.getDesignationMasterByName(designationMasterModel.getStrDesignationName());
		
		 if(null == designationMasterDomain){
				return null; 
			 }else if(designationMasterModel.getNumId() != 0){
				 if(designationMasterDomain.getNumId() == designationMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Employee Type with same name already exist with Id "+designationMasterDomain.getNumId();
				 }
			 }else{
				if(designationMasterDomain.getNumIsValid() == 0){
					result = "Employee Type Details already exist with Id "+designationMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Employee Type Details already exist with Id "+designationMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public DesignationMasterDomain getDesignationMasterDomainById(long numId){
		return designationMasterDao.getDesignationMasterById(numId);
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_DESIGNATION_MST')")
	public List<DesignationMasterModel> getAllDesignationMasterDomain(){
		return convertDesignationMasterDomainToModelList(designationMasterDao.getAllDesignationMasterDomain());
	}
	
	@Override
	public List<DesignationMasterModel> getAllActiveDesignationMasterDomain(){
		return convertDesignationMasterDomainToModelList(designationMasterDao.getAllActiveDesignationMasterDomain());
	}
	
	public DesignationMasterDomain convertDesignationMasterModelToDomain(DesignationMasterModel designationMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		DesignationMasterDomain designationMasterDomain = new DesignationMasterDomain();
		if(designationMasterModel.getNumId()!=0){				
			designationMasterDomain =  designationMasterDao.getDesignationMasterById(designationMasterModel.getNumId());
		}
		
		designationMasterDomain.setDtTrDate(new Date());
		designationMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(designationMasterModel.isValid()){
			designationMasterDomain.setNumIsValid(1);
		}else{
			designationMasterDomain.setNumIsValid(0);
		}
		designationMasterDomain.setDesignationName(designationMasterModel.getStrDesignationName());
		designationMasterDomain.setDesignationShortCode(designationMasterModel.getStrDesignationShortCode());
		designationMasterDomain.setDescription(designationMasterModel.getStrDesription());
		designationMasterDomain.setNumIsOrganisationSpecific(designationMasterModel.getIsOrganisationSpecific());
		designationMasterDomain.setNumIsGroupSpecific(designationMasterModel.getIsGroupSpecific());
		designationMasterDomain.setHierarchy(designationMasterModel.getHierarchy());
		designationMasterDomain.setNumIsThirdPartyDesignation(designationMasterModel.getIsThirdPartySpecific());
		designationMasterDomain.setNumProjectSpecific(designationMasterModel.getNumProjectSpecific());
		return designationMasterDomain;
	}
	
	public List<DesignationMasterModel> convertDesignationMasterDomainToModelList(List<DesignationMasterDomain> empTypeMasterList){
		List<DesignationMasterModel> list = new ArrayList<DesignationMasterModel>();
			for(int i=0;i<empTypeMasterList.size();i++){
				DesignationMasterDomain designationMasterDomain = empTypeMasterList.get(i);
				DesignationMasterModel designationMasterModel = new DesignationMasterModel();
				
				if(designationMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+designationMasterDomain.getNumId());
					designationMasterModel.setEncOrganisationId(encryptedId);
				}
				designationMasterModel.setNumId(designationMasterDomain.getNumId());
				if(designationMasterDomain.getNumIsValid() ==1){
					designationMasterModel.setValid(true);
				}else{
					designationMasterModel.setValid(false);
				}
			
		
				designationMasterModel.setStrDesignationName(designationMasterDomain.getDesignationName());
				designationMasterModel.setStrDesignationShortCode(designationMasterDomain.getDesignationShortCode());
				designationMasterModel.setStrDesription(designationMasterDomain.getDescription());
				designationMasterModel.setIsGroupSpecific(designationMasterDomain.getNumIsGroupSpecific());
				designationMasterModel.setIsOrganisationSpecific(designationMasterDomain.getNumIsOrganisationSpecific());
				designationMasterModel.setHierarchy(designationMasterDomain.getHierarchy());
				designationMasterModel.setIsThirdPartySpecific(designationMasterDomain.getNumIsThirdPartyDesignation());
				designationMasterModel.setNumProjectSpecific(designationMasterDomain.getNumProjectSpecific());
				list.add(designationMasterModel);
	}
		return list;
	}

	
	
	public DesignationMasterModel convertDesignationMasterDomainToModel(DesignationMasterDomain designationMasterDomain){
		DesignationMasterModel designationMasterModel = new DesignationMasterModel();
	
		if(designationMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+designationMasterDomain.getNumId());
			designationMasterModel.setEncOrganisationId(encryptedId);
		}
		designationMasterModel.setNumId(designationMasterDomain.getNumId());
		if(designationMasterDomain.getNumIsValid() ==1){
			designationMasterModel.setValid(true);
		}else{
			designationMasterModel.setValid(false);
		}
	
		
		designationMasterModel.setStrDesignationName(designationMasterDomain.getDesignationName());
		designationMasterModel.setStrDesignationShortCode(designationMasterDomain.getDesignationShortCode());
		designationMasterModel.setStrDesription(designationMasterDomain.getDescription());
		
		return designationMasterModel;
		
	}
	public long deleteDesignation(DesignationMasterModel designationMasterModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<DesignationMasterDomain> empTypeMasterList = designationMasterDao.getDesignationMasterById(designationMasterModel.getIdCheck());
		for(int i=0;i<empTypeMasterList.size();i++){
			DesignationMasterDomain designationMasterDomain = empTypeMasterList.get(i);
			designationMasterDomain.setNumIsValid(2);
			designationMasterDomain.setDtTrDate(new Date());
			designationMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = designationMasterDao.saveUpdateDesignationMaster(designationMasterDomain);
		}
		return result;
	}	
	
	@Override
	public List<String> getActiveDesignationName(){
		return designationMasterDao.getActiveDesignationName();
		
	}

	
	
	public List<DesignationMasterModel> getSpecificDesigantion(int isThirdPartySpecific) {
		return convertDesignationMasterDomainToModelList(designationMasterDao.getSpecificDesigantion(isThirdPartySpecific));
	}
	
	@Override
	public long saveDesignationData(DesignationMasterModel designationMasterModel){
		String designationDataDetails = designationMasterModel.getDesignationDetail();
		long id=0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		JSONArray jsonArray = new JSONArray(designationDataDetails);
		for (int j = 0; j < jsonArray.length(); j++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(j);
		   long designationId =  jsonObject.getLong("designation");
		   int hierarchy =  jsonObject.getInt("hierarchy");
		    DesignationMasterDomain designationMasterDomain = designationMasterDao.getDesignationMasterById(designationId);
		    designationMasterDomain.setNumId(designationId);
		    designationMasterDomain.setHierarchy(hierarchy);
		    designationMasterDomain.setDtTrDate(new Date());
			designationMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		    id= designationMasterDao.saveUpdateDesignationMaster(designationMasterDomain);
		}
		return id;
	} 
}
