package in.pms.master.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ReportMaster;
import in.pms.master.domain.RoleMasterDomain;

@Repository
public class RoleMasterDao {

	@Autowired
	DaoHelper daoHelper;	
	
	
	public long saveUpdateRoleMaster(RoleMasterDomain roleMasterDomain){
		roleMasterDomain = daoHelper.merge(RoleMasterDomain.class, roleMasterDomain);
		return roleMasterDomain.getNumId();
	}
	
	public RoleMasterDomain getRoleMasterByName(String roleName){
		String query = "from RoleMasterDomain where numIsValid!=2 and lower(roleName)=lower('"+roleName+"')";	
		List<RoleMasterDomain> roleMasterDomainList = daoHelper.findByQuery(query);		
		if(roleMasterDomainList.size()>0){
			return roleMasterDomainList.get(0);
		}
		return null;
	}
	
	public RoleMasterDomain getRoleMasterDomainById(long numId){
		RoleMasterDomain roleMasterDomain =null;
		String query = "from RoleMasterDomain where numId="+numId;
		List<RoleMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			roleMasterDomain =list.get(0);
		}
		return roleMasterDomain;	
	}
	
	public List<RoleMasterDomain> getAllRoleMasterDomain(){
		String query = "from RoleMasterDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<RoleMasterDomain> getAllActiveRoleMasterDomain(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		int hierarchy = userInfo.getHighestRoleHierarchy();
		
		StringBuffer query = new StringBuffer("from RoleMasterDomain where numIsValid=1");
		if(hierarchy != -10){
			query.append(" and hierarchy>"+hierarchy);
		}
		
		query.append(" order by hierarchy");
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public void deleteRoleMaster(RoleMasterDomain roleMasterDomain)
	{
		RoleMasterDomain roleMaster = daoHelper.findById(RoleMasterDomain.class, roleMasterDomain.getNumId());
		roleMaster.setNumIsValid(2);;
		roleMaster.setDtTrDate(roleMasterDomain.getDtTrDate());
		daoHelper.merge(RoleMasterDomain.class, roleMaster);
	}
	
	public RoleMasterDomain getRoleDomainById(long id){
		return daoHelper.findById(RoleMasterDomain.class, id);
	}
	
	public List<RoleMasterDomain> getApplicationSpecificRoles(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		int hierarchy = userInfo.getHighestRoleHierarchy();
		
		StringBuilder query = new StringBuilder("from RoleMasterDomain where numIsValid=1");
		query.append(" and accessLevel='Application'");
		if(hierarchy != -10){
			query.append(" and hierarchy>"+hierarchy);
		}		
		query.append(" order by hierarchy");
		return  daoHelper.findByQuery(query.toString());	
	}
	
	 public String getReportByRoleId(Long id) {
			//String query = "select string_agg(privilege_id::character varying,',') from roles_privileges where role_id="+id;
	    	String query ="select string_agg(cast(report_id as varchar),',') from roles_reports where role_id="+id;
	    	List<String> list = daoHelper.runNative(query);
			if(list.size()>0) {
				return list.get(0);
			}
			return "";
			
		}
	 
	 public RoleMasterDomain getRoleByName(String roleName){
			String query = "from RoleMasterDomain where roleName='"+roleName+"'";	
			List<RoleMasterDomain> roleList = daoHelper.findByQuery(query);		
			if(roleList.size()>0){
				return roleList.get(0);
			}
			return null;
		}
	 
	 public long mergeRoleMaster(RoleMasterDomain role){//change the parameter according to role report
	    	role = daoHelper.merge(RoleMasterDomain.class, role);// change the cla
			return role.getNumId();
		}
	 
	 public RoleMasterDomain getRoleById(long numId){
	    	RoleMasterDomain role =null;
	    	role = daoHelper.findById(RoleMasterDomain.class, numId);
			return role;	
		}
	 
	
	 public List<ReportMaster> getAllActiveReportDomainId(List<Integer> ids) {
			String query = "from ReportMaster p where p.numQueryId in :ids";
			return daoHelper.findByIdListInteger(query, ids);
		
		}
	 public List<RoleMasterDomain> getAllActiveRoleMasterDomainForMapping(){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			int hierarchy = userInfo.getHighestRoleHierarchy();
			
			StringBuffer query = new StringBuffer("from RoleMasterDomain where numIsValid=1 and accessLevel in ('Application')");
			if(hierarchy != -10){
				query.append(" and hierarchy>"+hierarchy);
			}
			
			query.append(" order by hierarchy");
			return  daoHelper.findByQuery(query.toString());	
		}
}
