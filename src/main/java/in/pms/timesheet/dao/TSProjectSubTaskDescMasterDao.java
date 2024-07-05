package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;

@Repository
public class TSProjectSubTaskDescMasterDao {

	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get all project sub task desc names project sub task wise
	public List<List<List<String>>> getProjectSubTaskDescNames(List<List<Long>> projectSubTaskIds){
		List<List<List<String>>> projectSubTaskDescNames = new ArrayList<List<List<String>>>();
		for(List<Long> i: projectSubTaskIds) {
			List<List<String>> temp = new ArrayList<List<String>>();
			for(Long j: i) {
				String query = "SELECT m.projectSubTaskDescName FROM ProjectSubTaskDescMasterModel m WHERE m.projectSubTaskId = ?0 AND m.isValid = 1 ORDER BY m.projectSubTaskDescId";
				ArrayList<Object> paraList = new ArrayList<>();
				paraList.add(j);
				List<String> result = daoHelper.findByQuery(query, paraList);
				temp.add(result);
			}
			
			projectSubTaskDescNames.add(temp);
		}
		return projectSubTaskDescNames;	
	}
	
	//Purpose - To get all project sub task desc names project sub task wise
	public List<List<List<Long>>> getProjectSubTaskDescIds(List<List<Long>> projectSubTaskIds){
		List<List<List<Long>>> projectSubTaskDescIds = new ArrayList<List<List<Long>>>();
		for(List<Long> i: projectSubTaskIds) {
			List<List<Long>> temp = new ArrayList<List<Long>>();
			for(Long j: i) {
				String query = "SELECT m.projectSubTaskDescId FROM ProjectSubTaskDescMasterModel m WHERE m.projectSubTaskId = ?0 AND m.isValid = 1 ORDER BY m.projectSubTaskDescId";
				ArrayList<Object> paraList = new ArrayList<>();
				paraList.add(j);
				List<Long> result = daoHelper.findByQuery(query, paraList);
				temp.add(result);
			}
			
			projectSubTaskDescIds.add(temp);
		}
		return projectSubTaskDescIds;	
	}
}
