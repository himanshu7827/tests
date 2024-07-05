package in.pms.master.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ThirdPartyMasterDomain;
import in.pms.master.model.ThirdPartyMasterModel;
import in.pms.transaction.model.BudgetHeadMasterForm;

@Service
public interface ThirdPartyMasterService {
	
	@Transactional
	public long saveUpdateThirdPartyMaster(ThirdPartyMasterModel thirdPartyMasterModel);
	
	public String checkDuplicateThirdPartyName(ThirdPartyMasterModel thirdPartyMasterModel);
	
	public ThirdPartyMasterModel getThirdPartyMasterDomainById(long numId);
	
	public ThirdPartyMasterDomain getThirdPartyDomainById(long numId);
	
	public List<ThirdPartyMasterModel> getAllThirdPartyMasterDomain();
	
	public List<ThirdPartyMasterModel> getAllActiveThirdPartyMasterDomain();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteThirdPartyMaster(ThirdPartyMasterModel thirdPartyMasterModel);
	
	public List<ThirdPartyMasterDomain> getThirdPartyMasterDomain();

}
