package in.pms.master.service;


import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.ProjectRoleMasterForm;
import in.pms.master.model.RoleMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectRoleMasterService {

	@Transactional
	public long saveUpdateProjectRoleMaster(ProjectRoleMasterForm projectRoleMasterForm);
	
	public int checkDuplicateProjectRoleName(ProjectRoleMasterForm projectRoleMasterForm);
	
	public RoleMasterModel getRoleById(long numId);
	
	public List<ProjectRoleMasterForm> getAllProjectRole();
	
	public List<RoleMasterModel> getAllActiveRoleMasterDomain();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProjectRoleData(ProjectRoleMasterForm projectRoleMasterForm);
	
	public RoleMasterDomain getRoleDomainById(long numRoleId);
	
	public List<RoleMasterModel> getApplicationSpecificRoles();
	
	public RoleMasterDomain getRoleMasterById(long numId);
	
	
}
