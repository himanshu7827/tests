package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ProjectTypeMaster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectTypeDao {
	
	@Autowired
	DaoHelper daoHelper;

	public ProjectTypeMaster mergeProjectType(ProjectTypeMaster projectTypeMaster) {		
		return daoHelper.merge(ProjectTypeMaster.class,projectTypeMaster);
	}

	
	public List<ProjectTypeMaster> getAllProjectType() {
		String query = "From ProjectTypeMaster where numIsValid<>2";
		return daoHelper.findByQuery(query);
	}

	
	public List<ProjectTypeMaster> getActiveProjectType() {
		String query = "From ProjectTypeMaster p where p.numIsValid=1 order by p.projectTypeName";
		return daoHelper.findByQuery(query);
	}

	
	public ProjectTypeMaster getProjectTypeById(int id) {
		return daoHelper.findById(ProjectTypeMaster.class, id);
	}

}
