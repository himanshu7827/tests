package in.pms.master.service;

import in.pms.master.model.OrganisationMasterModel;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface OrganisationMasterService {

	@Transactional
	public long saveUpdateOrganisationMaster(OrganisationMasterModel organisationMasterModel);
	
	public String checkDuplicateOrganisationName(OrganisationMasterModel organisationMasterModel);
	
	public OrganisationMasterModel getOrganisationMasterDomainById(long numId);
	
	public List<OrganisationMasterModel> getAllOrganisationMasterDomain();
	
	public List<OrganisationMasterModel> getAllActiveOrganisationMasterDomain();
	
	 public String getDistinctOrganisation(String assignedOrganisation);
	 public String getDistinctOrganisationShortName(String assignedOrganisation);
	
	@Transactional
	public void deleteOrganisationData(OrganisationMasterModel organisationMasterModel);
	
}
