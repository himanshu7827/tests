package in.pms.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSProjectSubTaskDescMasterDao;
import in.pms.timesheet.dao.TSProjectSubTaskMasterDao;

@Service
public class ProjectSubTaskDescMasterService {

	@Autowired
	TSProjectSubTaskDescMasterDao projectSubTaskDescMasterDao;
	
	public List<List<List<String>>> getProjectSubTaskDescNames(List<List<Long>> projectSubTaskIds){
		return this.projectSubTaskDescMasterDao.getProjectSubTaskDescNames(projectSubTaskIds);
	}
	
	public List<List<List<Long>>> getProjectSubTaskDescIds(List<List<Long>> projectSubTaskIds){
		return this.projectSubTaskDescMasterDao.getProjectSubTaskDescIds(projectSubTaskIds);
	}
}
