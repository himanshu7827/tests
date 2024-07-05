package in.pms.timesheet.dao;

import in.pms.global.dao.DaoHelper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSProjectTaskMasterDao {
	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get all project task names
	public List<String> getProjectTaskNames(){
		
		String query = "SELECT m.projectTaskName FROM ProjectTaskMasterModel m WHERE m.isValid = 1 ORDER BY m.projectTaskId";
		List<String> result = daoHelper.findByQuery(query);
		
		return (result != null && !result.isEmpty()) ? result : null;	
	}
	
	//Purpose - To get all project task ids
	public List<Long> getProjectTaskIds(){
		
		String query = "SELECT m.projectTaskId FROM ProjectTaskMasterModel m WHERE m.isValid = 1 ORDER BY m.projectTaskId";
		List<Long> result = daoHelper.findByQuery(query);
		
		return (result != null && !result.isEmpty()) ? result : null;	
	}
	
	//Purpose - To get all project task id from project sub task desc id
	public Long getProjectTaskIdFromProjectSubTaskDescId(Long projectSubTaskDescId){
		
		String query = "SELECT m.projectTaskId FROM ProjectTaskMasterModel m WHERE m.isValid = 1 AND m.projectTaskId IN (SELECT p.projectTaskId FROM ProjectSubTaskMasterModel p WHERE p.isValid = 1 AND p.projectSubTaskId IN (SELECT q.projectSubTaskId FROM ProjectSubTaskDescMasterModel q WHERE q.isValid = 1 AND q.projectSubTaskDescId = ?0)) ORDER BY m.projectTaskId";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(projectSubTaskDescId);
		List<Long> result = daoHelper.findByQuery(query, paraList);
		return (result != null && !result.isEmpty()) ? result.get(0) : 0;	
	}
}
