package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ProjectModuleMasterDomain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ProjectModuleMasterDao {

	
	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateProjectModuleMaster(ProjectModuleMasterDomain projectModuleMasterDomain){
		
		projectModuleMasterDomain =daoHelper.merge(ProjectModuleMasterDomain.class, projectModuleMasterDomain);		
			return projectModuleMasterDomain.getNumId();
		}
		
		public ProjectModuleMasterDomain getProjectModuleMasterById(long id){
			List<ProjectModuleMasterDomain> projectModuleMasterrList =  daoHelper.findByQuery("from ProjectModuleMasterDomain where numId="+id);
			if(projectModuleMasterrList.size()>0){
				return projectModuleMasterrList.get(0);
			}
			return null;
		}
		
		public ProjectModuleMasterDomain getProjectModuleMasterByName(String strModuleName,long projectId){
			String query = "from ProjectModuleMasterDomain where lower(strModuleName)=lower('"+strModuleName+"') and projectId="+projectId+" and numIsValid<>2" ;	
			List<ProjectModuleMasterDomain> projectModuleMasterDomainList = daoHelper.findByQuery(query);		
			if(projectModuleMasterDomainList.size()>0){
				return projectModuleMasterDomainList.get(0);
			}
			return null;
		}

		public List<ProjectModuleMasterDomain> getAllProjectModuleMasterDomain(){
			String query = "from ProjectModuleMasterDomain where numId<>0 and numIsValid<>2";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<ProjectModuleMasterDomain> getAllActiveProjectModuleMasterDomain(){
			String query = "from ProjectModuleMasterDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<ProjectModuleMasterDomain> getProjectModuleMasterById(String ids){
			List<ProjectModuleMasterDomain> projectModuleMasterrList =  daoHelper.findByQuery ("from ProjectModuleMasterDomain where numId in ("+ids+")");		
			return projectModuleMasterrList;
		}

		
	
		public List<ProjectModuleMasterDomain> getAllProjectModuleByProjectID(long projectId) {
			String query = "from ProjectModuleMasterDomain e where e.numIsValid=1 and  e.projectId="+projectId;			
			//System.out.println("query is"+query);
			return daoHelper.findByQuery(query);
		}
		
		
}
