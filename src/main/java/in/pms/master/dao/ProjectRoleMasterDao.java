package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.domain.RoleMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRoleMasterDao {

	@Autowired
	DaoHelper daoHelper;	
	
	
	public long saveUpdateProjectRoleMaster(ProjectRolesMaster projectRolesMaster){
		projectRolesMaster = daoHelper.merge(ProjectRolesMaster.class,projectRolesMaster);
		return projectRolesMaster.getNumId();
	}
	
	/*public ProjectRolesMaster getProjectRoleByName(String roleName){
		String query = "from ProjectRolesMaster where numIsValid!=2 and lower(roleName)=lower('"+roleName+"')";	
		List<ProjectRolesMaster> roleMasterDomainList = daoHelper.findByQuery(query);		
		if(roleMasterDomainList.size()>0){
			return roleMasterDomainList.get(0);
		}
		return null;
	}*/
	public int getProjectRoleByName(String roleName, long numId){
		
		int flag= 0;
		if(numId==0){
	 		String query= "from ProjectRolesMaster where numIsValid!=2 and lower(roleName)=lower('"+roleName+"')"  ;
	 		
	 		List<ProjectRolesMaster> count = daoHelper.findByQuery(query);
	 		
	 		for(ProjectRolesMaster ppd:count)
	 		{
	 			if(ppd.getRoleName()!=null && !ppd.getRoleName().equals("") && ppd.getRoleName().trim().equalsIgnoreCase(roleName.trim()))
	 			{
		 			flag=1;
		 			break;
	 			}
	 		
	 		}
		}
		
		if(numId!=0){
	 		String query= "from ProjectRolesMaster where numId!="+numId+" and numIsValid!=2 and lower(roleName)=lower('"+roleName+"')"  ;
	 		
	 		List<ProjectRolesMaster> count = daoHelper.findByQuery(query);
	 		
	 		for(ProjectRolesMaster ppd:count)
	 		{
	 			if(ppd.getRoleName()!=null && !ppd.getRoleName().equals("") && ppd.getRoleName().trim().equalsIgnoreCase(roleName.trim()))
	 			{
		 			flag=1;
		 			break;
	 			}
	 		
	 		}
		}
	 
			return flag;
		
		}
	
	
	public ProjectRolesMaster getProjectRoleById(long numId){
		ProjectRolesMaster roleMasterDomain =null;
		String query = "from ProjectRolesMaster where numId="+numId;
		List<ProjectRolesMaster> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			roleMasterDomain =list.get(0);
		}
		return roleMasterDomain;	
	}
	
	public List<ProjectRolesMaster> getAllProjectRole(){
		String query = "from ProjectRolesMaster where numIsValid<>2";
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
	
	public void deleteProjectRoleMaster(ProjectRolesMaster projectRolesMaster)
	{
		ProjectRolesMaster roleMaster = daoHelper.findById(ProjectRolesMaster.class, projectRolesMaster.getNumId());
		roleMaster.setNumIsValid(2);;
		roleMaster.setDtTrDate(projectRolesMaster.getDtTrDate());
		daoHelper.merge(ProjectRolesMaster.class, roleMaster);
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
	
}
