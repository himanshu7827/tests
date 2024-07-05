package in.pms.master.dao;

import java.util.List;

import in.pms.master.domain.PostTrackerMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface PostTrackerMasterDao  extends JpaRepository<PostTrackerMasterDomain, Long>{	
	@Query("from PostTrackerMasterDomain where numIsValid=1")
	public List<PostTrackerMasterDomain> getAllActivePostTrackerMasterDomain();
	@Query("from PostTrackerMasterDomain where numIsValid in (0,1)")
	public List<PostTrackerMasterDomain> getAllPostTrackerMasterDomain();
	@Query( "from PostTrackerMasterDomain where numId=:id")
	public PostTrackerMasterDomain getPostTrackerMasterDomainById(@Param("id") long id);
}
