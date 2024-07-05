package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ParentOrganisationMasterDao;
import in.pms.master.domain.ParentOrganisationMaster;
import in.pms.master.model.ParentOrganisationMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class ParentOrganisationMasterServiceImpl implements ParentOrganisationMasterService {

	@Autowired
	ParentOrganisationMasterDao parentOrganisationMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PARENT_ORGANISATION')")
	public long saveUpdateParentOrganisationMaster(
			ParentOrganisationMasterModel parentOrganisationMasterModel) {
		ParentOrganisationMaster parentOrganisationMaster = convertOrganisationMasterModelToDomain(parentOrganisationMasterModel);
		return parentOrganisationMasterDao.saveUpdateParentOrganisationMaster(parentOrganisationMaster);
	}

	@Override
	public String checkDuplicateParentOrganisationName(
			ParentOrganisationMasterModel parentOrganisationMasterModel) {
		String result=	"";
		ParentOrganisationMaster parentOrganisationMaster = parentOrganisationMasterDao.getParentOrganisationMasterByName(parentOrganisationMasterModel.getOrganisationName());
		
		 if(null == parentOrganisationMaster){
				return null; 
			 }else if(parentOrganisationMasterModel.getNumId() != 0){
				 if(parentOrganisationMaster.getNumId() == parentOrganisationMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Parent Organisation with same name already exist with Id "+parentOrganisationMaster.getNumId();
				 }
			 }else{
				if(parentOrganisationMaster.getNumIsValid() == 0){
					result = "Parent Organisation Details already exist with Id "+parentOrganisationMaster.getNumId() +". Please activate same record";
				}else{
					result = "Parent Organisation Details already exist with Id "+parentOrganisationMaster.getNumId();
				}			
			 }
			return result;		
	}

	@Override
	public ParentOrganisationMaster getParentOrganisationById(long numId) {
		return parentOrganisationMasterDao.getParentOrganisationById(numId);		
	}

	@Override
	@PreAuthorize("hasAuthority('READ_PARENT_ORGANISATION_MST')")
	public List<ParentOrganisationMasterModel> getAllParentOrganisation() {
		return convertParentOrganisationMasterDomainToModelList(parentOrganisationMasterDao.getAllParentOrganisation());
	}

	@Override
	public List<ParentOrganisationMasterModel> getAllActiveParentOrganisation() {
		return convertParentOrganisationMasterDomainToModelList(parentOrganisationMasterDao.getAllActiveParentOrganisation());
	}
	
	public ParentOrganisationMaster convertOrganisationMasterModelToDomain(ParentOrganisationMasterModel parentOrganisationMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ParentOrganisationMaster parentOrganisationMaster = new ParentOrganisationMaster();
		
		if(parentOrganisationMasterModel.getNumId()!=0){				
			parentOrganisationMaster =  parentOrganisationMasterDao.getParentOrganisationById(parentOrganisationMasterModel.getNumId());
		}
		parentOrganisationMaster.setContactNumber(parentOrganisationMasterModel.getContactNumber());
		parentOrganisationMaster.setDtTrDate(new Date());
		parentOrganisationMaster.setNumTrUserId(userInfo.getEmployeeId());
		if(parentOrganisationMasterModel.isValid()){
			parentOrganisationMaster.setNumIsValid(1);
		}else{
			parentOrganisationMaster.setNumIsValid(0);
		}
		parentOrganisationMaster.setOrganisationAddress(parentOrganisationMasterModel.getOrganisationAddress());
		parentOrganisationMaster.setOrganisationName(parentOrganisationMasterModel.getOrganisationName());
		parentOrganisationMaster.setOrganisationShortName(parentOrganisationMasterModel.getOrganisationShortName());		
		return parentOrganisationMaster;
	}
	
	public ParentOrganisationMasterModel convertOrganisationMasterDomainToModel(ParentOrganisationMaster parentOrganisationMaster){
		ParentOrganisationMasterModel parentOrganisationMasterModel = new ParentOrganisationMasterModel();
	
		if(parentOrganisationMaster.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+parentOrganisationMaster.getNumId());
			parentOrganisationMasterModel.setEncOrganisationId(encryptedId);
		}
		parentOrganisationMasterModel.setNumId(parentOrganisationMaster.getNumId());
		if(parentOrganisationMaster.getNumIsValid() ==1){
			parentOrganisationMasterModel.setValid(true);
		}else{
			parentOrganisationMasterModel.setValid(false);
		}
	
		parentOrganisationMasterModel.setContactNumber(parentOrganisationMaster.getContactNumber());
		parentOrganisationMasterModel.setOrganisationAddress(parentOrganisationMaster.getOrganisationAddress());
		parentOrganisationMasterModel.setOrganisationName(parentOrganisationMaster.getOrganisationName());
		parentOrganisationMasterModel.setOrganisationShortName(parentOrganisationMaster.getOrganisationShortName());
		return parentOrganisationMasterModel;
		
	}
	
	public List<ParentOrganisationMasterModel> convertParentOrganisationMasterDomainToModelList(List<ParentOrganisationMaster> parentOrganisationMasters){
		List<ParentOrganisationMasterModel> list = new ArrayList<ParentOrganisationMasterModel>();
			for(int i=0;i<parentOrganisationMasters.size();i++){
				ParentOrganisationMaster parentOrganisationMaster = parentOrganisationMasters.get(i);
				ParentOrganisationMasterModel organisationMasterModel = new ParentOrganisationMasterModel();
				
				if(parentOrganisationMaster.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+parentOrganisationMaster.getNumId());
					organisationMasterModel.setEncOrganisationId(encryptedId);
				}
				organisationMasterModel.setNumId(parentOrganisationMaster.getNumId());
				if(parentOrganisationMaster.getNumIsValid() ==1){
					organisationMasterModel.setValid(true);
				}else{
					organisationMasterModel.setValid(false);
				}
			
				organisationMasterModel.setContactNumber(parentOrganisationMaster.getContactNumber());
				organisationMasterModel.setOrganisationAddress(parentOrganisationMaster.getOrganisationAddress());
				organisationMasterModel.setOrganisationName(parentOrganisationMaster.getOrganisationName());
				organisationMasterModel.setOrganisationShortName(parentOrganisationMaster.getOrganisationShortName());
				
				list.add(organisationMasterModel);
	}
		return list;
	}
	
}
