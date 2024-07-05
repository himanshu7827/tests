package in.pms.timesheet.service;

import java.util.List;

import in.pms.timesheet.dao.TSProjectTaskHelpMasterDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskHelpMasterService {

	@Autowired
	TSProjectTaskHelpMasterDao projectTaskHelpMasterDao;
	
	public List<Object> getProjectTaskHelpDesc(){
		return this.projectTaskHelpMasterDao.getProjectTaskHelpDesc();
	}
}
