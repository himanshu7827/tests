package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.ProjectCategoryDao;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.master.model.ProjectCategoryModel;
import in.pms.transaction.domain.CategoryMaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService{

	@Autowired
	ProjectCategoryDao projectCategoryDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public ProjectCategoryMaster mergeProjectCategory(
			ProjectCategoryMaster projectCategoryMaster) {		
		return projectCategoryDao.mergeProjectCategory(projectCategoryMaster);
	}

	
	@Override
	public List<ProjectCategoryModel> getAllProjectCategory() {		
		return convertProjectCategoryMasterToModelList(projectCategoryDao.getAllProjectCategory());
	}

	@Override
	public List<ProjectCategoryModel> getActiveProjectCategory() {		
		return convertProjectCategoryMasterToModelList(projectCategoryDao.getActiveProjectCategory());
	}

	@Override
	public ProjectCategoryMaster getProjectCategoryById(int id) {		
		return projectCategoryDao.getProjectCategoryById(id);
	}

	public ProjectCategoryModel convertProjectCategoryMasterToModel(ProjectCategoryMaster domain){
		ProjectCategoryModel model = new ProjectCategoryModel();
		model.setNumId(domain.getNumId());
		model.setDescription(domain.getDescription());
		model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
		model.setCategoryName(domain.getCategoryName());
		model.setShortCode(domain.getShortCode());
		model.setStrCode(domain.getStrCode());

		
		if(domain.getNumIsValid()==1){
			model.setValid(true);
		}else{
			model.setValid(false);
		}
		return model;
	}
	
	public List<ProjectCategoryModel> convertProjectCategoryMasterToModelList(List<ProjectCategoryMaster> domains){
		List<ProjectCategoryModel> modelList = new ArrayList<ProjectCategoryModel>();
		for(ProjectCategoryMaster domain :domains){
			ProjectCategoryModel model = new ProjectCategoryModel();
			model.setNumId(domain.getNumId());
			model.setDescription(domain.getDescription());
			model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
			model.setCategoryName(domain.getCategoryName());
			model.setShortCode(domain.getShortCode());
			model.setStrCode(domain.getStrCode());
			
			if(domain.getNumIsValid()==1){
				model.setValid(true);
			}else{
				model.setValid(false);
			}
			modelList.add(model);
		}
		
		return modelList;
	}
	
	@Override
	public CategoryMaster getProjectCategoryByCatId(long categoryId) {		
		return projectCategoryDao.getProjectCategoryByCatId(categoryId).get(0);
	}
	
}
