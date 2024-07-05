package in.pms.master.service;

import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.master.model.ProjectCategoryModel;
import in.pms.transaction.domain.CategoryMaster;

import java.util.List;

import javax.transaction.Transactional;

public interface ProjectCategoryService {
	
	@Transactional
	public ProjectCategoryMaster mergeProjectCategory(ProjectCategoryMaster ProjectCategoryMaster) ;
	
	public List<ProjectCategoryModel> getAllProjectCategory();
	
	public List<ProjectCategoryModel> getActiveProjectCategory();
	
	public ProjectCategoryMaster getProjectCategoryById(int id);
	
	public CategoryMaster getProjectCategoryByCatId(long categoryId);
	
}
