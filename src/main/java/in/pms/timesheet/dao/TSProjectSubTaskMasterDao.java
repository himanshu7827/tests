package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;

@Repository
public class TSProjectSubTaskMasterDao {

	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get all project sub task names project task wise
	public List<List<String>> getProjectSubTaskNames(List<Long> projectTaskIds){
		List<List<String>> projectSubTaskNames = new ArrayList<List<String>>();
		for(Long i: projectTaskIds) {
			String query = "SELECT m.projectSubTaskName FROM ProjectSubTaskMasterModel m WHERE m.projectTaskId = ?0 AND m.isValid = 1 ORDER BY m.projectSubTaskId";
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(i);
			List<String> result = daoHelper.findByQuery(query, paraList);
			projectSubTaskNames.add(result);
		}
		return projectSubTaskNames;	
	}
	
	//Purpose - To get all project sub task names project task wise
	public List<List<Long>> getProjectSubTaskIds(List<Long> projectTaskIds){
		List<List<Long>> projectSubTaskIds = new ArrayList<List<Long>>();
		for(long i: projectTaskIds) {
			String query = "SELECT m.projectSubTaskId FROM ProjectSubTaskMasterModel m WHERE m.projectTaskId = ?0 AND m.isValid = 1 ORDER BY m.projectSubTaskId";
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(i);
			List<Long> result = daoHelper.findByQuery(query, paraList);
			projectSubTaskIds.add(result);
		}			
		return projectSubTaskIds;	
	}
}
