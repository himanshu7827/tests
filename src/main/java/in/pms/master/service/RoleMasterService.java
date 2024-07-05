package in.pms.master.service;


import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.pms.login.model.RoleReportModel;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.RoleMasterModel;

public interface RoleMasterService {

	@Transactional
	public long saveUpdateRoleMaster(RoleMasterModel roleMasterModel);
	
	public String checkDuplicateRoleName(RoleMasterModel roleMasterModel);
	
	public RoleMasterModel getRoleById(long numId);
	
	public List<RoleMasterModel> getAllRoleMaster();
	
	public List<RoleMasterModel> getAllActiveRoleMasterDomain();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteRoleData(RoleMasterModel roleMasterModel);
	
	public RoleMasterDomain getRoleDomainById(long numRoleId);
	
	public List<RoleMasterModel> getApplicationSpecificRoles();
	
	public RoleMasterDomain getRoleMasterById(long numId);

	public List<RoleReportModel> getAllRoleDetails();

	public String getReportByRoleId(Long Id);
	
	public String checkDuplicateRoleData(RoleReportModel roleReportModel);
	
	@Transactional
	public long saveRoleData(RoleReportModel roleReportModel);
	
	
	public List<RoleMasterModel> getAllActiveRoleMasterDomainForMapping();
}
