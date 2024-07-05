package in.pms.master.service;

import in.pms.master.domain.ProposalDocumentDetailsDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.ProjectDocumentMasterModel;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
@Service
public interface ProposalDocumentMasterService {
	
	@Transactional
	public boolean uploadProposalDocument(ProjectDocumentMasterModel projectDocumentMasterModel);
	
	public void downloadProposalDocument(long documentId,String documentType);
	
	public List<ProjectDocumentMasterModel> uploadedProposalDocument(ProjectDocumentMasterModel projectDocumentMasterModel);

	public List<ProposalMasterDomain> getApplicationIdbyProposalId(long proposalId);
	public ProposalDocumentDetailsDomain getProposalDocumentDetail(long uploadId);
	
	public List<ProjectDocumentMasterModel> documentsByProposalId(long proposalId);

	@Transactional
	public boolean deleteProposalDocument(String parentDocumentId);
	@Transactional
	public List<ProjectDocumentMasterModel> showProposalDocumentRevision(String encDocumentId);
	
	@Transactional
	public List<ProjectDocumentMasterModel> getProposalDocumentForDashboard(long proposalId,long docTypeId);
	
	@Transactional
	public Map<String,Map<String,List<ProjectDocumentMasterModel>>> documentDetailsCategoryWiseByProposalId(long proposalId);
	
	//  serviceInterface method for count the document from proposaldocumentmaster table by proposal id 
	public long checkDocumentMaster(long proposalId);

	public List<ProjectDocumentMasterModel> documentsHistoryByRevId(long proposalId);//Added by devesh on 26-09-23 to get document history

	public List<Object[]> getProposalDocumentDetailbyRevId(long uploadId, long numId);//Added by devesh on 27-09-23 to get document details
	
	/*----- Function for get the proposal document details according to project ID [18-03-2024] ------*/
	public List<ProjectDocumentMasterModel> documentDetailsProposal(ProjectDocumentMasterModel proposalId);
}
