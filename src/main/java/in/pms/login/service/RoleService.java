package in.pms.login.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.pms.login.domain.Role;
import in.pms.login.model.RolePrivilegeModel;
import in.pms.master.model.ThrustAreaMasterForm;

@Service
public interface RoleService {
	
	@Transactional
	
	public List<RolePrivilegeModel> getAllRoleDetails();

    public List<Role> getAllActiveRoleDomain();
	
	public List<Role> getAllActiveRoleDomainId(List<Long> ids);
	
	public String getPrivilegeByRoleId(Long id);	
	
	public String checkDuplicateRoleData(RolePrivilegeModel rolePrivilegeModel);
	@Transactional
	public long saveRoleData(RolePrivilegeModel rolePrivilegeModel);

	
}
