package in.pms.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSProjectMasterDao;

@Service
public class ProjectMasterService {

	@Autowired
	TSProjectMasterDao projectMasterDao;
	
	public String getProjectTimesheetType(Long projectId){
		return projectMasterDao.getProjectTimesheetType(projectId);
	}
}
