package in.pms.timesheet.dao;

import java.util.List;

import in.pms.global.dao.DaoHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSProjectTaskHelpMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To get all project task desc help data to show on UI help
	public List<Object> getProjectTaskHelpDesc(){
		
		String query = "SELECT m.projectTaskName, h.projectTaskDescHelp FROM ProjectTaskMasterModel m JOIN fetch ProjectTaskHelpMasterModel h ON m.projectTaskId = h.projectTaskId ORDER BY m.projectTaskId";
		List<Object> result = daoHelper.findByQuery(query);
		
		return (result != null && !result.isEmpty()) ? result : null;	
	}
}
