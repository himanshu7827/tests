package in.pms.timesheet.service;

import java.util.List;

import in.pms.timesheet.dao.TSMiscOtherActivityMasterDao;
import in.pms.timesheet.model.MiscOtherActivityMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiscOtherActivityMasterService {
	
	@Autowired
	TSMiscOtherActivityMasterDao miscOtherActivityMasterDao;
	
	public Long entryInMiscOtherActivityMaster(MiscOtherActivityMasterModel miscOtherActivityMasterModel){
		return miscOtherActivityMasterDao.entryInMiscOtherActivityMaster(miscOtherActivityMasterModel);
	}
	public List<Long> getAllIds(){
		return miscOtherActivityMasterDao.getAllIds();
	}
	public List<String> getAllDesc(){
		return miscOtherActivityMasterDao.getAllDesc();
	}
}
