package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;

@Repository
public class TSProjectMasterDao {

	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get ProjectType from project Id
	public String getProjectTimesheetType(Long projectId){
		String query = "SELECT e.timesheetType FROM ProjectMasterModel e WHERE e.numId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(projectId);
		List<String> result = daoHelper.findByQuery(query, paraList);
		
		return (result != null && !result.isEmpty()) ? result.get(0) : "b";
	}
}
