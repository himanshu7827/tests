package in.pms.transaction.service;

import java.util.List;

import in.pms.transaction.model.ProjectHighlightsModel;

import org.springframework.transaction.annotation.Transactional;

public interface ProjectHighlightsService {
	@Transactional
	public ProjectHighlightsModel SaveHighlights(ProjectHighlightsModel projectHighlightsModel);
	
	public ProjectHighlightsModel getSavedHighlights(int reportId);

	@Transactional
	public ProjectHighlightsModel loadHighlightsDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	

}