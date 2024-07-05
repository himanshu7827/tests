package in.pms.master.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import in.pms.global.service.EncryptionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.ClientContactPersonMasterDao;
import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.model.ContactPersonMasterModel;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientContactPersonMasterServiceImpl implements ClientContactPersonMasterService {

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ClientContactPersonMasterDao clientContactPersonMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_CLIENT_CONTACT_MST')")
	public long saveUpdateContactPersonMaster(ContactPersonMasterModel contactPersonMasterModel){
		ContactPersonMasterDomain contactPersonMasterDomain = convertContactPersonMasterModelToDomain(contactPersonMasterModel);
		return clientContactPersonMasterDao.save(contactPersonMasterDomain).getNumId();
	}
		
	@Override
	public String checkDuplicateContactPersonName(ContactPersonMasterModel contactPersonMasterModel){
		String result=	"";
		ContactPersonMasterDomain contactPersonMasterDomain = clientContactPersonMasterDao.getContactPersonMasterByName(contactPersonMasterModel.getStrContactPersonName());
		
		 if(null == contactPersonMasterDomain){
				return null; 
			 }else if(contactPersonMasterModel.getNumId() != 0){
				 if(contactPersonMasterDomain.getNumId() == contactPersonMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Contact Person with same name already exist with Id "+contactPersonMasterDomain.getNumId();
				 }
			 }else{
				if(contactPersonMasterDomain.getNumIsValid() == 0){
					result = "Contact Person Details already exist with Id "+contactPersonMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Contact Person Details already exist with Id "+contactPersonMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ContactPersonMasterModel getContactPersonMasterDomainById(long numId){
		return convertContactPersonMasterDomainToModel(clientContactPersonMasterDao.getOne(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_CLIENT_CONTACT_MST')")
	public List<ContactPersonMasterModel> getAllContactPersonMasterDomain(){
		return convertContactPersonMasterDomainToModelList(clientContactPersonMasterDao.getAllContactPersonMasterDomain());
	}
	
	@Override
	public List<ContactPersonMasterModel> getAllActiveContactPersonMasterDomain(){
		return convertContactPersonMasterDomainToModelList(clientContactPersonMasterDao.getAllActiveContactPersonMasterDomain());
	}
	
	public ContactPersonMasterDomain convertContactPersonMasterModelToDomain(ContactPersonMasterModel contactPersonMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ContactPersonMasterDomain contactPersonMasterDomain = new ContactPersonMasterDomain();
		if(contactPersonMasterModel.getNumId()!=0){				
			contactPersonMasterDomain =  clientContactPersonMasterDao.getOne(contactPersonMasterModel.getNumId());
		}
		
		contactPersonMasterDomain.setDtTrDate(new Date());
		contactPersonMasterDomain.setNumTrUserId(userInfo.getEmployeeId());

		if(contactPersonMasterModel.isValid()){
			contactPersonMasterDomain.setNumIsValid(1);
		}else{
			contactPersonMasterDomain.setNumIsValid(0);
		}
		contactPersonMasterDomain.setStrContactPersonName(contactPersonMasterModel.getStrContactPersonName());
		contactPersonMasterDomain.setStrDesignation(contactPersonMasterModel.getStrDesignation());
		contactPersonMasterDomain.setStrMobileNumber(contactPersonMasterModel.getStrMobileNumber());
		contactPersonMasterDomain.setStrEmailId(contactPersonMasterModel.getStrEmailId());
		contactPersonMasterDomain.setStrRoles(contactPersonMasterModel.getStrRoles());
		contactPersonMasterDomain.setStrResponsibility(contactPersonMasterModel.getStrResponsibility());
		contactPersonMasterDomain.setStrOfficeAddress(contactPersonMasterModel.getStrOfficeAddress());
		contactPersonMasterDomain.setStrResidenceAddress(contactPersonMasterModel.getStrResidenceAddress());
		//contactPersonMasterDomain.setOrganisationId(contactPersonMasterModel.getOrganisationId());
		contactPersonMasterDomain.setClientId(contactPersonMasterModel.getOrganisationId());
		return contactPersonMasterDomain;
	}
	
	public List<ContactPersonMasterModel> convertContactPersonMasterDomainToModelList(List<ContactPersonMasterDomain> contactPersonMasterList){
		List<ContactPersonMasterModel> list = new ArrayList<ContactPersonMasterModel>();
			for(int i=0;i<contactPersonMasterList.size();i++){
				ContactPersonMasterDomain contactPersonMasterDomain = contactPersonMasterList.get(i);
				ContactPersonMasterModel contactPersonMasterModel = new ContactPersonMasterModel();
				
				if(contactPersonMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+contactPersonMasterDomain.getNumId());
					contactPersonMasterModel.setEncOrganisationId(encryptedId);
				}
				contactPersonMasterModel.setNumId(contactPersonMasterDomain.getNumId());
				if(contactPersonMasterDomain.getNumIsValid() ==1){
					contactPersonMasterModel.setValid(true);
				}else{
					contactPersonMasterModel.setValid(false);
				}	
		
				contactPersonMasterModel.setStrContactPersonName(contactPersonMasterDomain.getStrContactPersonName());
				contactPersonMasterModel.setStrDesignation(contactPersonMasterDomain.getStrDesignation());
				contactPersonMasterModel.setStrMobileNumber(contactPersonMasterDomain.getStrMobileNumber());
				contactPersonMasterModel.setStrEmailId(contactPersonMasterDomain.getStrEmailId());
				contactPersonMasterModel.setStrRoles(contactPersonMasterDomain.getStrRoles());
				contactPersonMasterModel.setStrResponsibility(contactPersonMasterDomain.getStrResponsibility());
				contactPersonMasterModel.setStrOfficeAddress(contactPersonMasterDomain.getStrOfficeAddress());
				contactPersonMasterModel.setStrResidenceAddress(contactPersonMasterDomain.getStrResidenceAddress());
			
				
				list.add(contactPersonMasterModel);
	}
		return list;
	}

	
	
	@Override
	public List<ContactPersonMasterModel> getContactPersonByClientId(long clientId){		
			List<ContactPersonMasterDomain> contactPersonMasterDomain = clientContactPersonMasterDao.getAllContactPersonByClientId(clientId);
			List<ContactPersonMasterModel> contactPersonMasterModelList = convertContactPersonMasterDomainToModelList(contactPersonMasterDomain);
			return contactPersonMasterModelList;	
	}
	
	public ContactPersonMasterModel convertContactPersonMasterDomainToModel(ContactPersonMasterDomain contactPersonMasterDomain){
		ContactPersonMasterModel contactPersonMasterModel = new ContactPersonMasterModel();
	
		if(contactPersonMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+contactPersonMasterDomain.getNumId());
			contactPersonMasterModel.setEncOrganisationId(encryptedId);
		}
		contactPersonMasterModel.setNumId(contactPersonMasterDomain.getNumId());
		if(contactPersonMasterDomain.getNumIsValid() ==1){
			contactPersonMasterModel.setValid(true);
		}else{
			contactPersonMasterModel.setValid(false);
		}
	
		
		contactPersonMasterModel.setStrContactPersonName(contactPersonMasterDomain.getStrContactPersonName());
		contactPersonMasterModel.setStrDesignation(contactPersonMasterDomain.getStrDesignation());
		contactPersonMasterModel.setStrMobileNumber(contactPersonMasterDomain.getStrMobileNumber());
		contactPersonMasterModel.setStrEmailId(contactPersonMasterDomain.getStrEmailId());
		contactPersonMasterModel.setStrRoles(contactPersonMasterDomain.getStrRoles());
		contactPersonMasterModel.setStrResponsibility(contactPersonMasterDomain.getStrResponsibility());
		contactPersonMasterModel.setStrOfficeAddress(contactPersonMasterDomain.getStrOfficeAddress());
		contactPersonMasterModel.setStrResidenceAddress(contactPersonMasterDomain.getStrResidenceAddress());
		
		return contactPersonMasterModel;
		
	}
	public long deleteClientContact(ContactPersonMasterModel contactPersonMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		String[] selectedIds= contactPersonMasterModel.getIdCheck().split(",");
		List<String> tempList = Arrays.asList(selectedIds);
		List<Long> selectedList = tempList.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

		List<ContactPersonMasterDomain> contactPersonMasterList = clientContactPersonMasterDao.getContactPersonMasterById(selectedList);
		for(int i=0;i<contactPersonMasterList.size();i++){
			ContactPersonMasterDomain contactPersonMasterDomain = contactPersonMasterList.get(i);
			contactPersonMasterDomain.setNumIsValid(2);
			contactPersonMasterDomain.setDtTrDate(new Date());
			contactPersonMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = clientContactPersonMasterDao.save(contactPersonMasterDomain).getNumId();
		}
		return result;
	}

	
	public List<ContactPersonMasterModel> getContactPersonDetails(long numProjectId) {		
		List<ContactPersonMasterModel> list=new ArrayList<ContactPersonMasterModel>();
		List<ContactPersonMasterDomain> projectWiseContactPersons= clientContactPersonMasterDao.projectWiseContactPerson(numProjectId);
		if(null != projectWiseContactPersons && projectWiseContactPersons.size()>0){
			list = convertContactPersonMasterDomainToModelList(projectWiseContactPersons);
		}else{
			List<ContactPersonMasterDomain> proposalWiseContactPersons= clientContactPersonMasterDao.applicationWiseContactPerson(numProjectId);
			if(null != proposalWiseContactPersons && proposalWiseContactPersons.size()>0){
				list = convertContactPersonMasterDomainToModelList(proposalWiseContactPersons);
			}
		}
		return list;
	}

	
	public List<ContactPersonMasterDomain> getAllContactPersonByClientId(
			long numId) {
		return clientContactPersonMasterDao.getAllContactPersonByClientId(numId);
	}

	
	public ContactPersonMasterDomain getContactPersonMasterById(
			long contactPersonId) {
		return 	clientContactPersonMasterDao.getOne(contactPersonId);

	}	
	
	
	
}
