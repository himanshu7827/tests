package in.pms.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSProjectSubTaskMasterDao;

@Service
public class ProjectSubTaskMasterService {
	
	@Autowired
	TSProjectSubTaskMasterDao projectSubTaskMasterDao;
	
	public List<List<String>> getProjectSubTaskNames(List<Long> projectTaskIds){
		return this.projectSubTaskMasterDao.getProjectSubTaskNames(projectTaskIds);
	}
	
	public List<List<Long>> getProjectSubTaskIds(List<Long> projectTaskIds){
		return this.projectSubTaskMasterDao.getProjectSubTaskIds(projectTaskIds);
	}
}
