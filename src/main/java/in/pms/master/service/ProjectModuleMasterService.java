package in.pms.master.service;


import in.pms.master.model.ProjectModuleMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectModuleMasterService {

	@Transactional	
	 public long saveUpdateProjectModuleMaster(ProjectModuleMasterModel projectModuleMasterModel);		
	
	 public String checkDuplicateProjectModuleName(ProjectModuleMasterModel projectModuleMasterModel);
	 
	 public ProjectModuleMasterModel getProjectModuleMasterDomainById(long numId);
	
	 public List<ProjectModuleMasterModel> getAllProjectModuleMasterDomain();
	
	 public List<ProjectModuleMasterModel> getAllActiveProjectModuleMasterDomain();
	
	@Transactional
	public long deleteProjectModule(ProjectModuleMasterModel projectModuleMasterModel);

	@Transactional(propagation=Propagation.REQUIRED)
	public List<ProjectModuleMasterModel> getProjectModuleByProjectId(long projectId);
	
	//public List<ProjectModuleMasterModel> getAllActiveProjectMasterDomain();
	
}
