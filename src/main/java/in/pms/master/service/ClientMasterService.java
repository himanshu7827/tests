package in.pms.master.service;

import in.pms.master.model.ClientMasterModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ClientMasterService {

	@Transactional
	public long saveUpdateClientMaster(ClientMasterModel clientMasterModel);
	
	public String checkDuplicateClientName(ClientMasterModel clientMasterModel);
	
	public ClientMasterModel getClientMasterDomainById(long numId);
	
	public List<ClientMasterModel> getAllClientMasterDomain();
	
	public List<ClientMasterModel> getAllActiveClientMasterDomain();
	
	public List<ClientMasterModel> getAllActiveClientMasterDomainByApplicationId(long applicationId);
}
