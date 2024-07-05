package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.EndUserMasterDao;
import in.pms.master.domain.EndUserMasterDomain;
import in.pms.master.model.ClientMasterModel;
import in.pms.transaction.domain.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class EndUserMasterServiceImpl implements EndUserMasterService{

	@Autowired
	EndUserMasterDao endUserMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_CLIENT_MST')")
	public long saveUpdateEndUserMaster(ClientMasterModel clientMasterModel){
		EndUserMasterDomain EndUserMasterDomain = convertEndUserMasterModelToDomain(clientMasterModel);
		return endUserMasterDao.saveUpdateEndUserMaster(EndUserMasterDomain);
	}
	@SuppressWarnings("null")
	@Override
	public String checkDuplicateEndUserName(ClientMasterModel clientMasterModel){
		String result = "";
		 EndUserMasterDomain EndUserMasterDomain = endUserMasterDao.getEndUserMasterByName(clientMasterModel.getClientName());
	
		 if(null == EndUserMasterDomain){
			return null; 
		 }else if(clientMasterModel.getNumId() != 0){
			 if(EndUserMasterDomain.getNumId() == clientMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Client with same name already exist with Id "+EndUserMasterDomain.getNumId();
			 }
		 }else{
			if(EndUserMasterDomain.getNumIsValid() == 0){
				result = "Client Already Registered with Id "+EndUserMasterDomain.getNumId() +". Please activate same record";
			}else{
				result = "Client Already Registered with Id "+EndUserMasterDomain.getNumId();
			}			
		 }
		return result;
	}
	
	@Override
	public ClientMasterModel getEndUserMasterDomainById(long numId){
		return convertEndUserMasterDomainToModel(endUserMasterDao.getEndUserMasterDomainById(numId));
	}
	@Override
	@PreAuthorize("hasAuthority('READ_CLIENT_MST')")
	public List<ClientMasterModel> getAllEndUserMasterDomain(){
		return convertEndUserMasterDomainToModelList(endUserMasterDao.getAllEndUserMasterDomain());			
	}
	
	@Override
	public List<ClientMasterModel> getAllActiveEndUserMasterDomain(){		
		return convertEndUserMasterDomainToModelList(endUserMasterDao.getAllActiveEndUserMasterDomain());
	}
	
	
	public EndUserMasterDomain convertEndUserMasterModelToDomain(ClientMasterModel clientMasterModel){
		EndUserMasterDomain EndUserMasterDomain = new EndUserMasterDomain();
		if(clientMasterModel.getNumId()!=0){
			//Encrypted id found
			//Decrypt and set to id proporty
			//and load data for update and delete activity			
			EndUserMasterDomain =  endUserMasterDao.getEndUserMasterDomainById(clientMasterModel.getNumId());
		}
		EndUserMasterDomain.setClientAddress(clientMasterModel.getClientAddress());
		EndUserMasterDomain.setClientName(clientMasterModel.getClientName());
		EndUserMasterDomain.setContactNumber(clientMasterModel.getContactNumber());
		EndUserMasterDomain.setDtTrDate(new Date());
		if(clientMasterModel.isValid()){
		EndUserMasterDomain.setNumIsValid(1);
		}else{
			EndUserMasterDomain.setNumIsValid(0);

		}
		//EndUserMasterDomain.setParentClientId(clientMasterModel.getParentClientId());
		EndUserMasterDomain.setShortCode(clientMasterModel.getShortCode());		
		
		return EndUserMasterDomain;
	}
	
	public ClientMasterModel convertEndUserMasterDomainToModel(EndUserMasterDomain EndUserMasterDomain){
		ClientMasterModel clientMasterModel = new ClientMasterModel();
		clientMasterModel.setClientAddress(EndUserMasterDomain.getClientAddress());
		clientMasterModel.setClientName(EndUserMasterDomain.getClientName());
		clientMasterModel.setContactNumber(EndUserMasterDomain.getContactNumber());
		if(EndUserMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+EndUserMasterDomain.getNumId());
			clientMasterModel.setEncStrId(encryptedId);
		}
		clientMasterModel.setNumId(EndUserMasterDomain.getNumId());
		if(EndUserMasterDomain.getNumIsValid()==1){
			clientMasterModel.setValid(true);
		}else{
			clientMasterModel.setValid(false);
		}
	/*	//clientMasterModel.setParentClientId(EndUserMasterDomain.getParentClientId());
		if(EndUserMasterDomain.getParentClientId() != 0){
			String encryptedParentId = encryptionService.encrypt(""+EndUserMasterDomain.getParentClientId());
			clientMasterModel.setEncStrParentClientId(encryptedParentId);
		}*/		
		clientMasterModel.setShortCode(EndUserMasterDomain.getShortCode());
		return clientMasterModel;
	}
	
	
	public List<ClientMasterModel> convertEndUserMasterDomainToModelList(List<EndUserMasterDomain> EndUserMasterDomainList){
		List<ClientMasterModel> list = new ArrayList<ClientMasterModel>();
			for(int i=0;i<EndUserMasterDomainList.size();i++){
				EndUserMasterDomain EndUserMasterDomain = EndUserMasterDomainList.get(i);
				ClientMasterModel clientMasterModel = new ClientMasterModel();
				clientMasterModel.setClientAddress(EndUserMasterDomain.getClientAddress());
				clientMasterModel.setClientName(EndUserMasterDomain.getClientName());
				clientMasterModel.setContactNumber(EndUserMasterDomain.getContactNumber());
				if(EndUserMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+EndUserMasterDomain.getNumId());
					clientMasterModel.setEncStrId(encryptedId);
				}
				
				clientMasterModel.setNumId(EndUserMasterDomain.getNumId());
				if(EndUserMasterDomain.getNumIsValid() ==1){
					clientMasterModel.setValid(true);
				}else{
					clientMasterModel.setValid(false);
				}
				
				/*clientMasterModel.setParentClientId(EndUserMasterDomain.getParentClientId());
				
				if(EndUserMasterDomain.getParentClientId() != 0){
					String encryptedParentId = encryptionService.encrypt(""+EndUserMasterDomain.getParentClientId());
					clientMasterModel.setEncStrParentClientId(encryptedParentId);
				}	*/			
				clientMasterModel.setShortCode(EndUserMasterDomain.getShortCode());		
				list.add(clientMasterModel);
	}
		return list;
	}
	@Override
	public List<ClientMasterModel> getAllActiveEndUserMasterDomainByApplicationId(long applicationId) {
		List<Application> list=endUserMasterDao.getAllActiveEndUserMasterDomainByApplicationId(applicationId);
		List<EndUserMasterDomain> clientList=new ArrayList<EndUserMasterDomain>();
		if(list.size()>0)
			clientList.add(list.get(0).getEndUserMaster());
		return convertEndUserMasterDomainToModelList(clientList);
		
	}
	
}
