package in.pms.timesheet.service;

import java.util.List;

import in.pms.timesheet.dao.TSMiscActivityMasterDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiscActivityMasterService {
	
	@Autowired
	TSMiscActivityMasterDao miscActivityMasterDao;
	
	public List<String> getMiscActivityNames() {
		return this.miscActivityMasterDao.getMiscActivityNames();
	}
	
	public List<Integer> getMiscActivityIds() {
		return this.miscActivityMasterDao.getMiscActivityIds();
	}
}
