package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.model.MilestoneReviewModel;
import in.pms.master.model.ProjectMilestoneForm;

public interface MilestoneReviewService {
	
	
	public List<MilestoneReviewModel> getAllMilestoneReviewData();
	@Transactional
	public long saveMilestoneReviewData(MilestoneReviewModel milestoneReviewModel);
	
	public List<MilestoneReviewModel> getActiveMilestoneReviewData();
	
	@Transactional
	public List<MilestoneReviewModel> getMilestoneReviewDataByMilestoneId(long milestoneId);
	
	
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDays(int days,List<Long> projects);
	
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDaysForOrg(int days,List<Long> org);
	
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDaysForGrp(int days,List<Long> groups);
	
	public List<ProjectMilestoneForm> getMilestoneReviewData(List<Long> projects);
	
	public List<ProjectMilestoneForm> getMilestoneReviewDataForOrg(List<Long> org);
	
	public List<ProjectMilestoneForm> getMilestoneReviewDataForGrp(List<Long> groups);
	
	public List<MilestoneReviewModel> getMilestoneReviewHistory(long milestoneId);
	
	public List<ProjectMilestoneForm> getMilestoneRevData();
	
	public List<ProjectMilestoneForm> getMilestoneRevDataByDates(String expComplDate,String symbol);
 	
}
