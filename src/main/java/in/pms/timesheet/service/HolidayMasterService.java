package in.pms.timesheet.service;


import java.util.List;

import in.pms.timesheet.dao.TSHolidayMasterDao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayMasterService {
	@Autowired
	TSHolidayMasterDao holidayMasterDao;
	
	public List<Integer> getHolidayList(String month, String year, Integer leaveTypeId) {
		return holidayMasterDao.getHolidayList(month, year,leaveTypeId);
	}
	
	public List<String> getHolidayNames(String month, String year, Integer leaveTypeId) {
		return holidayMasterDao.getHolidayNames(month, year, leaveTypeId);
	}
	
	public List<Object> getHolidayCountEachWeek(String month, String year, Integer leaveTypeId){
		return holidayMasterDao.getHolidayCountEachWeek(month, year, leaveTypeId);
	}
}
