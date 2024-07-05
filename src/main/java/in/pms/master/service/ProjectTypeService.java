package in.pms.master.service;

import in.pms.master.domain.ProjectTypeMaster;
import in.pms.master.model.ProjectTypeModel;

import java.util.List;

import javax.transaction.Transactional;

public interface ProjectTypeService {

	@Transactional
	public ProjectTypeMaster mergeProjectType(ProjectTypeMaster projectTypeMaster) ;
	
	public List<ProjectTypeModel> getAllProjectType();
	
	public List<ProjectTypeModel> getActiveProjectType();
	
	public ProjectTypeMaster getProjectTypeById(int l);
	
}
