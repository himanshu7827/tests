package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.MilestoneActivityDomain;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.domain.TaskDocumentDomain;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.TaskDetailsModel;




public interface TaskDetailsService {
	
	
	public List<TaskDetailsModel> getAllTaskDetailsData();
	@Transactional
	public long saveTaskDetailsData(TaskDetailsModel taskDetailsModel);
	
	public List<ProjectMilestoneDomain> getMilestoneWithoutActualEndDt();	
	
/*	public List<MilestoneActivityModel> getActivityData(long id);
*/	
	public TaskDocumentDomain getTaskDocumentDetail(long uploadId);
	
	public List<TaskDetailsModel> getAllActiveTaskDetailsData(String taskStatus);
	
	public TaskDetailsModel getTaskDetailsData(long taskId);
	
	@Transactional
	public boolean updateTaskDetailStatus(long taskId,String taskStatus);
	
 	}
