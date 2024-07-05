package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectBudgetDomain;
import in.pms.master.domain.ProjectBudgetDomainTemp;
import in.pms.master.domain.ProjectMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectBudgetDao {

	@Autowired
	DaoHelper daoHelper;
	

	public ProjectBudgetDomain getAllBudgetDataById(long numId){		
		return daoHelper.findById(ProjectBudgetDomain.class,numId);			
	}
	
	public ProjectBudgetDomainTemp getTempBudgetDataById(long numId){
			return daoHelper.findById(ProjectBudgetDomainTemp.class, numId);
	}
	
	public long saveProjectDetailsMaster(ProjectBudgetDomain projectMasterDomain){
		projectMasterDomain = daoHelper.merge(ProjectBudgetDomain.class, projectMasterDomain);
		return projectMasterDomain.getNumId();
		
	}
	
	public long saveTempProjectDetails(ProjectBudgetDomainTemp projectMasterDomainTemp){
		projectMasterDomainTemp = daoHelper.merge(ProjectBudgetDomainTemp.class, projectMasterDomainTemp);
		return projectMasterDomainTemp.getNumId();
		
	}
	
	public List<ProjectBudgetDomain> getAllBudgetDataDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects = userInfo.getAssignedProjects();
		StringBuilder query = new StringBuilder("from ProjectBudgetDomain p where numIsValid in(0,1)");
		if(null != assignedProjects){
			query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
		}
		return  daoHelper.findByQuery(query.toString());	
	}
	
	
	public List<ProjectBudgetDomain> getBudgetDetailByProjectIdYear(long projectid,int year){
		
		StringBuilder query = new StringBuilder("from ProjectBudgetDomain p where numIsValid in(0,1) and p.numYear="+year);
		query.append(" and p.projectMasterDomain.numId ="+projectid);
		
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public List<ProjectBudgetDomainTemp> getTempBudgetDetailByProjectId(long projectId){
		
		StringBuilder query = new StringBuilder("from ProjectBudgetDomainTemp p where numIsValid=1");
		query.append(" and p.projectId="+projectId);
		
		return  daoHelper.findByQuery(query.toString());	
	}
	
public List<ProjectBudgetDomain> getBudgetDetailByProjectId(long projectId){
		
		StringBuilder query = new StringBuilder("from ProjectBudgetDomain p where numIsValid=1");
		query.append(" and p.projectMasterDomain.numId="+projectId);
		
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public ProjectBudgetDomain	 getProjectBudgetByBudgetHeadIdYear(int budgetHeadId,int year){
		ProjectBudgetDomain projectBudgetDomain = new ProjectBudgetDomain();
		
		StringBuilder query = new StringBuilder("from ProjectBudgetDomain p where numIsValid=1");
		query.append(" and p.numYear ="+year +" and p.numBudgetHeadId="+budgetHeadId);
		
		List<ProjectBudgetDomain> list = daoHelper.findByQuery(query.toString());
		if(list.size()>0){
			projectBudgetDomain =list.get(0);
		}
		
		return projectBudgetDomain;
	}
	
	public ProjectBudgetDomainTemp	 getTempProjectBudgetByBudgetHeadIdYear(int budgetHeadId,int year){
		ProjectBudgetDomainTemp projectBudgetDomainTemp = new ProjectBudgetDomainTemp();
		
		StringBuilder query = new StringBuilder("from ProjectBudgetDomainTemp p where numIsValid=1");
		query.append(" and p.numYear ="+year +" and p.numBudgetHeadId="+budgetHeadId);
		
		List<ProjectBudgetDomainTemp> list = daoHelper.findByQuery(query.toString());
		if(list.size()>0){
			projectBudgetDomainTemp =list.get(0);
		}		
		return projectBudgetDomainTemp;
	}
}

