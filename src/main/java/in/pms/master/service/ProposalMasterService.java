package in.pms.master.service;

import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.ProposalMasterModel;
import in.pms.transaction.domain.Application;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ProposalMasterService {

	@Transactional
	public ProposalMasterModel saveUpdateProposalMaster(ProposalMasterModel proposalMasterModel);
	
	
    public String checkDuplicateProposalName(ProposalMasterModel proposalMasterModel);
	
	public ProposalMasterModel getProposalMasterDomainById(long numId);
	
	public ProposalMasterDomain getProposalDomainById(long numId);
	
	public List<ProposalMasterModel> getAllProposalMasterDomain();
	
	public List<ProposalMasterModel> getAllActiveProposalMasterDomain();

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProposal(ProposalMasterModel proposalMasterModel);

	@Transactional(propagation=Propagation.REQUIRED)
	public List<ProposalMasterModel> viewGroup(int OrganisationId);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<ProposalMasterModel> viewContact(int ClientId);

	public ProposalMasterModel getProposalMasterByApplicationId(long applicationId);
	
	@Transactional
	public long createEmptyProposalDetails(Application application);

	@Transactional
	public boolean submitProposal(long applicationId);
	
	/*public List<ProposalMasterModel> getProposalDetailByGruopId(long groupId);*/
	
	
	@Transactional
	List<Object[]> getproposalHistory(long applicationId);
	
	@Transactional
	public ProposalMasterModel getVersionDetails(long numId);
	
	@Transactional
	public List<ProposalMasterModel> getActiveProposals(String startRange, String endRange);
	
	
	@Transactional
	public List<ProposalMasterModel> getActiveProposalsByGroup(String startRange, String endRange,String encGroupId);


	public List<Object[]> getproposalHistorydetails(long applicationId); //proposal history detail service added by devesh on 28/7/23 


	public boolean isProposalCodeExists(String proposalCode);
	
	/*--------- Get the Proposal Data where the project start date and end date are provided [08-02-2024] ----------*/
	public List<ProposalMasterModel> getProposalDataOfCurrentYearProjects(String startRange, String endRange);
}
