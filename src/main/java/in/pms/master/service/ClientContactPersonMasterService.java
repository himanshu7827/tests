package in.pms.master.service;

import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.model.ContactPersonMasterModel;






import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ClientContactPersonMasterService {

	@Transactional	
	 public long saveUpdateContactPersonMaster(ContactPersonMasterModel contactPersonMasterModel);		
	
	 public String checkDuplicateContactPersonName(ContactPersonMasterModel contactPersonMasterModel);
	 
	 public ContactPersonMasterModel getContactPersonMasterDomainById(long numId);
	
	 public List<ContactPersonMasterModel> getAllContactPersonMasterDomain();
	
	 public List<ContactPersonMasterModel> getAllActiveContactPersonMasterDomain();
	
	@Transactional
	public long deleteClientContact(ContactPersonMasterModel contactPersonMasterModel);

	@Transactional(propagation=Propagation.REQUIRED)
	public List<ContactPersonMasterModel> getContactPersonByClientId(long organizationId);

	@Transactional
	public List<ContactPersonMasterModel> getContactPersonDetails(long numProjectId);

	public List<ContactPersonMasterDomain> getAllContactPersonByClientId(
			long numId);

	public ContactPersonMasterDomain getContactPersonMasterById(long contactPersonId);

}
