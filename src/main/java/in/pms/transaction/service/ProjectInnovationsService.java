package in.pms.transaction.service;

import java.util.List;

import in.pms.transaction.model.ProjectInnovationsModel;

import org.springframework.transaction.annotation.Transactional;




public interface ProjectInnovationsService {	
	@Transactional
	public ProjectInnovationsModel SaveProjectInnovations(ProjectInnovationsModel projectInnovationsModel);
	
	public String getSavedInnovationDetails(int reportId);
	
	@Transactional
	public List<ProjectInnovationsModel> loadInnovationDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);

	@Transactional
	public void deleteInnovationDetails(ProjectInnovationsModel projectInnovationsModel);
}