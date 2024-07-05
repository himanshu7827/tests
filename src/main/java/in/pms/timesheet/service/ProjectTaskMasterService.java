package in.pms.timesheet.service;

import java.util.List;

import in.pms.timesheet.dao.TSProjectTaskMasterDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskMasterService {
	@Autowired
	TSProjectTaskMasterDao projectTaskMasterDao;
	
	public List<String> getProjectTaskNames(){
		return this.projectTaskMasterDao.getProjectTaskNames();
	}
	
	public List<Long> getProjectTaskIds(){
		return this.projectTaskMasterDao.getProjectTaskIds();
	}
	
	public Long getProjectTaskIdFromProjectSubTaskDescId(Long projectSubTaskDescId){
		return this.projectTaskMasterDao.getProjectTaskIdFromProjectSubTaskDescId(projectSubTaskDescId);
	}
}
