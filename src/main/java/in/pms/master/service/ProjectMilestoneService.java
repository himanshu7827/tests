package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.ProjectMilestoneForm;




public interface ProjectMilestoneService {
	
	
	public List<ProjectMilestoneForm> getAllProjectMilestoneData();
	public List<ProjectMilestoneForm> getAllProjectMilestone();
	public String checkDuplicateProjectMilestoneData(ProjectMilestoneForm projectMilestoneForm);
	@Transactional
	public long saveProjectMilestoneData(ProjectMilestoneForm projectMilestoneForm);
	 	
	public List<ProjectMilestoneDomain> getMilestoneDataByProjectId(long projectId);
	
	public ProjectMilestoneDomain getProjectMilestoneDataByMilestoneId(long milestoneId);
	
	public List<ProjectMilestoneForm> getMilestoneByProjectId(long projectId);
	@Transactional	
	public ProjectMilestoneForm getMilestoneDataByMilestoneId(long milestoneId);
	
	
	public List<ProjectMilestoneForm> getMilestoneReviewDetail(int days);
	
	public List<ProjectMilestoneForm> getMilestoneReviewDetail();
	
	public List<ProjectMilestoneForm> MilestoneExceededDeadlineDetailByDates(String expComplDate,String symbol);


}
