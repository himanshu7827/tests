package in.pms.transaction.dao;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.transaction.domain.PatchTrackerDomain;


@Repository
public interface PatchTrackerDao extends JpaRepository<PatchTrackerDomain, Long>
{

	@Query("select distinct strRequestedBy from PatchTrackerDomain where numIsValid in (0,1) and lower(strRequestedBy) like lower(:searchString) and dtTrDate>=:date")
	public List<String> getRequesterName(@Param("searchString") String searchString,@Param("date") Date date);
	
	
	@Query("select distinct strNameOfFiles from PatchTrackerDomain where numIsValid in (0,1) and lower(strNameOfFiles) like lower(:searchString) and dtTrDate>=:date")
	public List<String> getNameOfFiles(@Param("searchString") String searchString,@Param("date") Date date);
	

  @Query("select distinct strTeamMembers from PatchTrackerDomain where numIsValid in (0,1) and lower(strTeamMembers) like lower(:searchString) and dtTrDate>=:date")
   public List<String> getTeamMembers(@Param("searchString") String searchString,@Param("date") Date date);


   @Query("select distinct strModules from PatchTrackerDomain where numIsValid in (0,1) and lower(strModules) like lower(:searchString) and dtTrDate>=:date")
   public List<String> getModules(@Param("searchString") String searchString,@Param("date") Date date);

}