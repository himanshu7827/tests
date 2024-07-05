package in.pms.master.service;

import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.model.DesignationMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface DesignationMasterService {
	
	@Transactional	
	 public long saveUpdateDesignationMaster(DesignationMasterModel designationMasterModel);		
	
	 public String checkDuplicateDesignationName(DesignationMasterModel designationMasterModel);
	 
	 public DesignationMasterDomain getDesignationMasterDomainById(long numId);
	
	 public List<DesignationMasterModel> getAllDesignationMasterDomain();
	
	 public List<DesignationMasterModel> getAllActiveDesignationMasterDomain();
	
	@Transactional
	public long deleteDesignation(DesignationMasterModel designationMasterModel);
	
	public List<String> getActiveDesignationName();

	@Transactional
	public List<DesignationMasterModel> getSpecificDesigantion(int isThirdPartySpecific);
	
	@Transactional
	public long saveDesignationData(DesignationMasterModel designationMasterModel);

}
