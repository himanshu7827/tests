package in.pms.login.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import in.pms.login.dao.RoleDao;
import in.pms.login.domain.Privilege;
import in.pms.login.domain.Role;
import in.pms.login.model.RolePrivilegeModel;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.ThrustAreaMasterForm;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;

	@Override
	@PreAuthorize("hasAuthority('READ_ROLEPRIVILEGE_MST')")
	public List<RolePrivilegeModel> getAllRoleDetails() {
		List<Role> roleLists = roleDao.roleName();
		List<RolePrivilegeModel> rolePrivilegeModel1 = new ArrayList<RolePrivilegeModel>();

		for (int i = 0; i < roleLists.size(); i++) {
			RolePrivilegeModel rolePrivilegeModel = new RolePrivilegeModel();
			rolePrivilegeModel.setRoleId(roleLists.get(i).getId());
			rolePrivilegeModel.setRoleName(roleLists.get(i).getName());
			rolePrivilegeModel1.add(rolePrivilegeModel);
		}

		return rolePrivilegeModel1;
	}

	@Override
	public List<Role> getAllActiveRoleDomain() {
		return roleDao.getAllActiveRoleDomain();
	}

	public List<Role> getAllActiveRoleDomainId(List<Long> ids) {
		return roleDao.getAllActiveRoleDomainId(ids);
	}

	@Override
	public String getPrivilegeByRoleId(Long id) {
		return roleDao.getPrivilegeByRoleId(id);
	}
	
	@Override
	public String checkDuplicateRoleData(RolePrivilegeModel rolePrivilegeModel){
		String result = "";
		Role role = roleDao.getRoleByName(rolePrivilegeModel.getRoleName());
	
		 if(null == role){
			return null; 
		 }else if(role.getId() == rolePrivilegeModel.getRoleId()){
				 return null; 
			 }else{
				 result = "Role with same name already exist with Id "+role.getId();			 
		 }
		 
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_ROLEPRIVILEGE_MST')")
        public long saveRoleData(RolePrivilegeModel rolePrivilegeModel){
		Role role = convertRolePrivilegeModelToDomain(rolePrivilegeModel);
		return roleDao.mergeRoleMaster(role);
	}
	
	public Role convertRolePrivilegeModelToDomain(RolePrivilegeModel rolePrivilegeModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		Role role = new Role();
		if(rolePrivilegeModel.getRoleId()!=0){		
			role =  roleDao.getRoleById(rolePrivilegeModel.getRoleId());
		}
		role.setPrivileges(roleDao.getAllActivePrivilegeDomainId(rolePrivilegeModel.getSelectedPrivilege()));
		role.setName(rolePrivilegeModel.getRoleName());
		
		return role;
	}

	
}
