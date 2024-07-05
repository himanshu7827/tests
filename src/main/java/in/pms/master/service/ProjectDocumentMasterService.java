package in.pms.master.service;

import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.TempProjectDocumentDetailsDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

public interface ProjectDocumentMasterService {
	
	@Transactional
	public boolean uploadProjectDocument(ProjectDocumentMasterModel projectDocumentMasterModel);	
	
	public List<ProjectDocumentMasterModel> uploadedProjectDocument(ProjectDocumentMasterModel projectDocumentMasterModel);
	
	// GET PROJECT DOCUMENT BY PROJECT ID
	public List<ProjectDocumentMasterModel> getProjectDocumentByID(long projectId);
	//
	
	public ProjectDocumentDetailsDomain getProjectDocumentDetail(long uploadId);
	
	public ProjectDocumentDetailsDomain getProjectDocumentDetail(long parentId,long documentFormatId);
	
	public List<ProjectDocumentMasterModel> uploadedDocumentByProjectId(long projectId);

	@Transactional
	public List<ProjectDocumentMasterModel> showProjectDocumentRevision(String encDocumentId);
	
	
	@Transactional
	public List<ProjectDocumentMasterModel> getProjectDocumentForDashboard(long projectId);
	
	@Transactional
	public boolean deleteProposalDocument(String parentDocumentId);

	@Transactional
	public boolean uploadProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel);
	
	@Transactional
	public Map<String,Map<String,List<ProjectDocumentMasterModel>>> documentDetailsCategoryWiseByProjectId(long projectId);
	
	/*@Transactional
	public List<ProjectDocumentMasterModel> getProjectDocumentWithDocTypeId(long projectId,List<DocumentTypeMasterModel> doc);*/
	
	@Transactional
	public boolean uploadTempProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel);
	
	@Transactional
	public List<ProjectDocumentMasterModel> uploadedTempDocumentByProjectId(long projectId,List<DocumentTypeMasterModel> doc);
	
	public TempProjectDocumentDetailsDomain getTempProjectDocumentDetailsById(long uploadId);
	
	@Transactional
	public boolean updateUploadTempProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel);
	
	public List<TempProjectDocumentMasterDomain> getTempProjectDocumentMasterDetails(long projectId, int docTypeId);

	public ProjectDocumentMasterDomain getProjectDocumentIdFromProjectId(long projectId);
	
	/*----------------------- Service function for get Projects Document Details [18-03-2024] ------------------------*/	
	public List<ProjectDocumentMasterModel> getUploadedProjectDocumentForTile(ProjectDocumentMasterModel projectDocumentMasterModel);
}
