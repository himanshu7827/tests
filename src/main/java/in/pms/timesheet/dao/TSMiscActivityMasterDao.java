package in.pms.timesheet.dao;

import in.pms.global.dao.DaoHelper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSMiscActivityMasterDao {

	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get all miscellaneous activity names
	public List<String> getMiscActivityNames(){
		
		String query = "SELECT m.MiscActivityName FROM MiscActivityMasterModel m ORDER BY m.MiscActivityId";
		List<String> result = daoHelper.findByQuery(query);
		
		return (result != null && !result.isEmpty()) ? result : null;	
	}
	
	//Purpose - To get all miscellaneous activity ids
	public List<Integer> getMiscActivityIds(){
		
		String query = "SELECT m.MiscActivityId FROM MiscActivityMasterModel m ORDER BY m.MiscActivityId";
		List<Integer> result = daoHelper.findByQuery(query);
		
		return (result != null && !result.isEmpty()) ? result : null;	
	}
}
