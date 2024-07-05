package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.ProjectTypeDao;
import in.pms.master.domain.ProjectTypeMaster;
import in.pms.master.model.ProjectTypeModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {
	
	@Autowired
	ProjectTypeDao projectTypeDao;
	
	@Autowired
	EncryptionService encryptionService;
	

	@Override
	public ProjectTypeMaster mergeProjectType(ProjectTypeMaster projectTypeMaster) {		
		return projectTypeDao.mergeProjectType(projectTypeMaster);
	}

	@Override
	public List<ProjectTypeModel> getAllProjectType() {		
		return convertProjectTypeMasterToModelList(projectTypeDao.getAllProjectType());
	}

	@Override
	public List<ProjectTypeModel> getActiveProjectType() {		
		return convertProjectTypeMasterToModelList(projectTypeDao.getActiveProjectType());
	}

	@Override
	public ProjectTypeMaster getProjectTypeById(int id) {		
		return projectTypeDao.getProjectTypeById(id);
	}

	public ProjectTypeModel convertProjectTypeMasterToModel(ProjectTypeMaster domain){
		ProjectTypeModel model = new ProjectTypeModel();
		model.setDescription(domain.getDescription());
		model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
		model.setNumId(domain.getNumId());
		model.setShortCode(domain.getShortCode());
		model.setStrCode(domain.getStrCode());
		model.setProjectTypeName(domain.getProjectTypeName());
		if(domain.getNumIsValid() == 1){
			model.setValid(true);
		}else{
			model.setValid(false);
		}
		
		return model;
	}
	
	public List<ProjectTypeModel> convertProjectTypeMasterToModelList(List<ProjectTypeMaster> domains){
		List<ProjectTypeModel> modelList = new ArrayList<ProjectTypeModel>();
		for(ProjectTypeMaster domain:domains){
			ProjectTypeModel model = new ProjectTypeModel();
			model.setDescription(domain.getDescription());
			model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
			model.setNumId(domain.getNumId());
			model.setShortCode(domain.getShortCode());
			model.setStrCode(domain.getStrCode());
			model.setProjectTypeName(domain.getProjectTypeName());
			if(domain.getNumIsValid() == 1){
				model.setValid(true);
			}else{
				model.setValid(false);
			}
			modelList.add(model);
		}
		return modelList;
	}
	
}
