package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.ClientMasterDao;
import in.pms.master.domain.ClientMasterDomain;
import in.pms.master.model.ClientMasterModel;
import in.pms.transaction.domain.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ClientMasterServiceImpl implements ClientMasterService{

	@Autowired
	ClientMasterDao clientMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_CLIENT_MST')")
	public long saveUpdateClientMaster(ClientMasterModel clientMasterModel){
		ClientMasterDomain clientMasterDomain = convertClientMasterModelToDomain(clientMasterModel);
		return clientMasterDao.save(clientMasterDomain).getNumId();
	}
	@SuppressWarnings("null")
	@Override
	public String checkDuplicateClientName(ClientMasterModel clientMasterModel){
		String result = "";
		 ClientMasterDomain clientMasterDomain = clientMasterDao.getClientMasterByName(clientMasterModel.getClientName());
	
		 if(null == clientMasterDomain){
			return null; 
		 }else if(clientMasterModel.getNumId() != 0){
			 if(clientMasterDomain.getNumId() == clientMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Client with same name already exist with Id "+clientMasterDomain.getNumId();
			 }
		 }else{
			if(clientMasterDomain.getNumIsValid() == 0){
				result = "Client Already Registered with Id "+clientMasterDomain.getNumId() +". Please activate same record";
			}else{
				result = "Client Already Registered with Id "+clientMasterDomain.getNumId();
			}			
		 }
		return result;
	}
	
	@Override
	public ClientMasterModel getClientMasterDomainById(long numId){
		// ---------------------  Get A funding organization data by its id [11/09/2023] --------------------------------
		List<ClientMasterDomain> allActiveClientList = clientMasterDao.getAllActiveClientMasterDomain();
		List<ClientMasterDomain> getCurrentIdList = new ArrayList<>();
		for(ClientMasterDomain dummyList:allActiveClientList){
			if(dummyList.getNumId()==numId){
				getCurrentIdList.add(dummyList);
			}
		}
		return convertClientMasterDomainToModel(getCurrentIdList.get(0));
		//return convertClientMasterDomainToModel(clientMasterDao.getOne(numId));
		//----------------------- End Get A funding organization data by its id -------------------------------------------
	}
	@Override
	@PreAuthorize("hasAuthority('READ_CLIENT_MST')")
	public List<ClientMasterModel> getAllClientMasterDomain(){
		return convertClientMasterDomainToModelList(clientMasterDao.getAllClientMasterDomain());			
	}
	
	@Override
	public List<ClientMasterModel> getAllActiveClientMasterDomain(){		
		return convertClientMasterDomainToModelList(clientMasterDao.getAllActiveClientMasterDomain());
	}
	
	
	public ClientMasterDomain convertClientMasterModelToDomain(ClientMasterModel clientMasterModel){
		ClientMasterDomain clientMasterDomain = new ClientMasterDomain();
		if(clientMasterModel.getNumId()!=0){						
			Optional<ClientMasterDomain> optional =  clientMasterDao.findById(clientMasterModel.getNumId());
			if(optional.isPresent()){
				clientMasterDomain = optional.get();
			}			
		}
		clientMasterDomain.setClientAddress(clientMasterModel.getClientAddress());
		clientMasterDomain.setClientName(clientMasterModel.getClientName());
		clientMasterDomain.setContactNumber(clientMasterModel.getContactNumber());
		clientMasterDomain.setDtTrDate(new Date());
		clientMasterDomain.setNumIsValid(1);
		/*if(clientMasterModel.isValid()){
		
		}else{
			clientMasterDomain.setNumIsValid(0);

		}*/
		clientMasterDomain.setParentClientId(clientMasterModel.getParentClientId());
		clientMasterDomain.setShortCode(clientMasterModel.getShortCode());		
		
		return clientMasterDomain;
	}
	
	public ClientMasterModel convertClientMasterDomainToModel(ClientMasterDomain clientMasterDomain){
		ClientMasterModel clientMasterModel = new ClientMasterModel();
		clientMasterModel.setClientAddress(clientMasterDomain.getClientAddress());
		clientMasterModel.setClientName(clientMasterDomain.getClientName());
		clientMasterModel.setContactNumber(clientMasterDomain.getContactNumber());
		if(clientMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+clientMasterDomain.getNumId());
			clientMasterModel.setEncStrId(encryptedId);
		}
		clientMasterModel.setNumId(clientMasterDomain.getNumId());
	/*	if(clientMasterDomain.getNumIsValid()==1){
			clientMasterModel.setValid(true);
		}else{
			clientMasterModel.setValid(false);
		}*/
		clientMasterModel.setParentClientId(clientMasterDomain.getParentClientId());
		if(clientMasterDomain.getParentClientId() != 0){
			String encryptedParentId = encryptionService.encrypt(""+clientMasterDomain.getParentClientId());
			clientMasterModel.setEncStrParentClientId(encryptedParentId);
		}		
		clientMasterModel.setShortCode(clientMasterDomain.getShortCode());
		return clientMasterModel;
	}
	
	
	public List<ClientMasterModel> convertClientMasterDomainToModelList(List<ClientMasterDomain> clientMasterDomainList){
		List<ClientMasterModel> list = new ArrayList<ClientMasterModel>();
			for(int i=0;i<clientMasterDomainList.size();i++){
				ClientMasterDomain clientMasterDomain = clientMasterDomainList.get(i);
				ClientMasterModel clientMasterModel = new ClientMasterModel();
				clientMasterModel.setClientAddress(clientMasterDomain.getClientAddress());
				clientMasterModel.setClientName(clientMasterDomain.getClientName());
				clientMasterModel.setContactNumber(clientMasterDomain.getContactNumber());
				if(clientMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+clientMasterDomain.getNumId());
					clientMasterModel.setEncStrId(encryptedId);
				}
				
				clientMasterModel.setNumId(clientMasterDomain.getNumId());
				/*if(clientMasterDomain.getNumIsValid() ==1){
					clientMasterModel.setValid(true);
				}else{
					clientMasterModel.setValid(false);
				}*/
				
				clientMasterModel.setParentClientId(clientMasterDomain.getParentClientId());
				
				if(clientMasterDomain.getParentClientId() != 0){
					String encryptedParentId = encryptionService.encrypt(""+clientMasterDomain.getParentClientId());
					clientMasterModel.setEncStrParentClientId(encryptedParentId);
				}				
				clientMasterModel.setShortCode(clientMasterDomain.getShortCode());		
				list.add(clientMasterModel);
	}
		return list;
	}
	@Override
	public List<ClientMasterModel> getAllActiveClientMasterDomainByApplicationId(long applicationId) {
		List<Application> list=clientMasterDao.getAllActiveClientMasterDomainByApplicationId(applicationId);
		List<ClientMasterDomain> clientList=new ArrayList<ClientMasterDomain>();
		if(list.size()>0)
			clientList.add(list.get(0).getClientMaster());
		return convertClientMasterDomainToModelList(clientList);
		
	}
	
}
