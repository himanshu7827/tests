package in.pms.master.service;

import in.pms.master.model.ClientMasterModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface EndUserMasterService {

	@Transactional
	public long saveUpdateEndUserMaster(ClientMasterModel clientMasterModel);
	
	public String checkDuplicateEndUserName(ClientMasterModel clientMasterModel);
	
	public ClientMasterModel getEndUserMasterDomainById(long numId);
	
	public List<ClientMasterModel> getAllEndUserMasterDomain();
	
	public List<ClientMasterModel> getAllActiveEndUserMasterDomain();
	
	public List<ClientMasterModel> getAllActiveEndUserMasterDomainByApplicationId(long applicationId);
}
