package in.pms.login.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.domain.Privilege;
import in.pms.login.domain.Role;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {
	@Autowired
	DaoHelper daoHelper;
	
	public Role findByName(String name){
		List<Role> roleList = daoHelper.findByQuery("from Role where name="+name);
		if(roleList.size()>0){
			return roleList.get(0);
		}
		return null;
	}
	public List<Role> roleName(){
		String query = "from Role ";	
		List<Role> roleList = daoHelper.findByQuery(query);		
		
		return roleList;
	}
	
    
    
    public List<Role> getAllActiveRoleDomain() {
		String query = "from Role";
		return daoHelper.findByQuery(query);
	}
    public List<Role> getAllActiveRoleDomainId(List<Long> ids) {
		String query = "from Role r where r.id in :ids";
		return daoHelper.findByIdList(query, ids);
	
	}

    public String getPrivilegeByRoleId(Long id) {
		//String query = "select string_agg(privilege_id::character varying,',') from roles_privileges where role_id="+id;
    	String query ="select string_agg(cast(privilege_id as varchar),',') from roles_privileges where role_id="+id;
    	List<String> list = daoHelper.runNative(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return "";
		
	}
	
    public Role getRoleByName(String Name){
		String query = "from Role where name='"+Name+"'";	
		List<Role> roleList = daoHelper.findByQuery(query);		
		if(roleList.size()>0){
			return roleList.get(0);
		}
		return null;
	}
	
    public long mergeRoleMaster(Role role){
    	role = daoHelper.merge(Role.class, role);
		return role.getId();
	}
    
    public Role getRoleById(long numId){
    	Role role =null;
    	role = daoHelper.findById(Role.class, numId);
		return role;	
	}
    
    public List<Privilege> getAllActivePrivilegeDomainId(List<Long> ids) {
		String query = "from Privilege p where p.id in :ids";
		return daoHelper.findByIdList(query, ids);
	
	}
}
