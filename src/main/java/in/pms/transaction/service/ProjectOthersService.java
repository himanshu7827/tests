package in.pms.transaction.service;

import java.util.List;

import in.pms.transaction.model.ProjectOthersModel;

import org.springframework.transaction.annotation.Transactional;




public interface ProjectOthersService {	
	@Transactional
	public String  SaveProjectOthers(ProjectOthersModel ProjectOthersModel);
	
	public String getSavedProjectOthers(int reportId);
	
	@Transactional
	public String loadOthersDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
}