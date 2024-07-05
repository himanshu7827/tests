package in.pms.master.dao;

import java.util.Date;
import java.util.List;

import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProjectMilestoneReviewMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneReviewMasterDao extends JpaRepository<ProjectMilestoneReviewMaster, Long> {

	@Query("from ProjectMilestoneReviewMaster where numId<>0 and numIsValid<>2")
	public List<ProjectMilestoneReviewMaster> getAllProjectMilestoneReviewDomain();
	
	@Query("from ProjectMilestoneReviewMaster where numId<>0 and numIsValid=1")
	public List<ProjectMilestoneReviewMaster> getAllActiveProjectMilestoneReviewDomain();
	
	@Query("from ProjectMilestoneReviewMaster e where e.numIsValid<>2 and  e.numId=:reviewId")
	public ProjectMilestoneReviewMaster getAllMilestoneReviewByReviewId(@Param("reviewId") long reviewId);
	
	@Query("from ProjectMilestoneReviewMaster e where e.projectMilestoneDomain.numId=:milestoneId and  e.numIsValid=1 order by reviewDate desc")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataByMilestoneId(@Param("milestoneId") long milestoneId);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.completionDate between :startRange and :endRange and m.reviewDate !=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.numId in (:projects) and  (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataByNoOfDays(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("projects") List<Long> projects);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.completionDate between :startRange and :endRange and m.reviewDate !=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in (:org) and  (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataByNoOfDaysForOrg(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("org") List<Long> org);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.completionDate between :startRange and :endRange and m.reviewDate !=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.application.groupMaster.numId in (:groups) and (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataByNoOfDaysForGrp(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("groups") List<Long> groups);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.reviewDate <=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.numId in (:projects) and  (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewData(@Param("startRange") Date startRange,@Param("projects") List<Long> projects);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.reviewDate <=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in (:org) and  (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataForOrg(@Param("startRange") Date startRange,@Param("org") List<Long> org);
	
	@Query("from ProjectMilestoneReviewMaster m where m.numIsValid=1 and m.reviewDate <=:startRange and m.reviewDate = (select max(reviewDate) from ProjectMilestoneReviewMaster c where c.projectMilestoneDomain.numId= m.projectMilestoneDomain.numId) and m.projectMilestoneDomain.projectMasterDomain.application.groupMaster.numId in (:groups) and (m.projectMilestoneDomain.strStatus ='Not Completed' or m.projectMilestoneDomain.strStatus is null)")
	public List<ProjectMilestoneReviewMaster> getMilestoneReviewDataForGrp(@Param("startRange") Date startRange,@Param("groups") List<Long> groups);
	
}
