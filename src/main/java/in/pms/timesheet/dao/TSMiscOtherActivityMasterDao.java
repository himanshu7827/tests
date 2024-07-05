package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import in.pms.global.dao.DaoHelper;
import in.pms.timesheet.model.MiscOtherActivityMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSMiscOtherActivityMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To save data in pms_misc_other_activity_master table
	@Transactional
	public Long entryInMiscOtherActivityMaster(MiscOtherActivityMasterModel miscOtherActivityMasterModel){

		miscOtherActivityMasterModel = daoHelper.merge(MiscOtherActivityMasterModel.class, miscOtherActivityMasterModel);

        return miscOtherActivityMasterModel.getMiscOtherActivityId();
	}
	 //Purpose - to get the all Id inserted
	public List<Long> getAllIds(){
		String query = "SELECT mo.MiscOtherActivityId FROM MiscOtherActivityMasterModel mo ORDER BY mo.MiscOtherActivityId";
		List<Long> result = daoHelper.findByQuery(query);
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//Purpose - to get the all misc other activity desc inserted
	public List<String> getAllDesc(){
		String query = "SELECT mo.MiscOtherActivityDesc FROM MiscOtherActivityMasterModel mo ORDER BY mo.MiscOtherActivityId";
		List<String> result = daoHelper.findByQuery(query);
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
}
