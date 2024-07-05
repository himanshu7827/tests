package in.pms.master.service;

import in.pms.master.domain.ParentOrganisationMaster;
import in.pms.master.model.ParentOrganisationMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ParentOrganisationMasterService {

	@Transactional
	public long saveUpdateParentOrganisationMaster(ParentOrganisationMasterModel parentOrganisationMasterModel);
	
	public String checkDuplicateParentOrganisationName(ParentOrganisationMasterModel parentOrganisationMasterModel);
	
	public ParentOrganisationMaster getParentOrganisationById(long numId);
	
	public List<ParentOrganisationMasterModel> getAllParentOrganisation();
	
	public List<ParentOrganisationMasterModel> getAllActiveParentOrganisation();	
	
}
