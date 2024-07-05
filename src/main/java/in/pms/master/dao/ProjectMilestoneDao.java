package in.pms.master.dao;

import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProjectModuleMasterDomain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMilestoneDao extends JpaRepository<ProjectMilestoneDomain, Long> {
	
	@Query("from ProjectMilestoneDomain where  numIsValid!=2 and lower(milestoneName)=lower(:milestoneName) and projectMasterDomain.numId=:projectId")	
	public ProjectMilestoneDomain getProjectMilestoneByName(@Param("milestoneName") String milestoneName,@Param("projectId") long projectId);
	
	@Query("from ProjectMilestoneDomain where numIsValid in(0,1)")
	public List<ProjectMilestoneDomain> getAllProjectMilestoneData();
	
	@Query("from ProjectModuleMasterDomain where numId<>0 and numIsValid=1")
	public List<ProjectModuleMasterDomain> getAllActiveProjectModuleMasterDomain();
	
	@Query("from ProjectMilestoneDomain where numIsValid<>2 and actualCompletionDate<>Null")
	public List<ProjectMilestoneDomain> getMilestoneWithoutActualEndDt();
		
	@Query( "from ProjectMilestoneDomain where numIsValid=1 and projectMasterDomain.numId=:prjId order by expectedStartDate")
	public List<ProjectMilestoneDomain> getMilestoneDataByProjectId(@Param("prjId") long prjId);
	
	@Query("from ProjectMilestoneDomain m  where m.numIsValid=1  and m.expectedStartDate between :startRange and :endRange and m.projectMasterDomain.numId in (:projects) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneDataByNoOfDays(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("projects") List<Long> projects);
	
	@Query("from ProjectMilestoneDomain m  where m.numIsValid=1  and m.expectedStartDate between :startRange and :endRange and m.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in (:org) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneReviewDataByNoOfDaysForOrg(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("org") List<Long> org);
	
	@Query("from ProjectMilestoneDomain m  where m.numIsValid=1  and m.expectedStartDate between :startRange and :endRange  and m.projectMasterDomain.application.groupMaster.numId in (:groups) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneReviewDataByNoOfDaysForGrp(@Param("startRange") Date startRange,@Param("endRange") Date endRange,@Param("groups") List<Long> groups);
	
	@Query("from ProjectMilestoneDomain m where m.numIsValid=1 and m.expectedStartDate <=:startRange  and  m.projectMasterDomain.numId in (:projects) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneData(@Param("startRange") Date startRange,@Param("projects") List<Long> projects);
	
	@Query("from ProjectMilestoneDomain m  where m.numIsValid=1  and m.expectedStartDate <=:startRange and m.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in (:org) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneReviewDataForOrg(@Param("startRange") Date startRange,@Param("org") List<Long> org);
	
	@Query("from ProjectMilestoneDomain m  where m.numIsValid=1  and m.expectedStartDate <=:startRange and m.projectMasterDomain.application.groupMaster.numId in (:groups) and (m.strStatus ='Not Completed' or m.strStatus is null)")
	public List<ProjectMilestoneDomain> getMilestoneReviewDataForGrp(@Param("startRange") Date startRange,@Param("groups") List<Long> groups);
	
	@Query( "from ProjectMilestoneDomain where numIsValid=1 and numId=:numId")
	public List<ProjectMilestoneDomain> getMilestoneDataById(@Param("numId") long numId);
	
}
