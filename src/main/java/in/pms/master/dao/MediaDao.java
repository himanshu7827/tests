package in.pms.master.dao;

import in.pms.master.domain.MediaDomain;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


	

	@Repository
	public interface MediaDao extends JpaRepository<MediaDomain , Long> {


		@Query("from MediaDomain where numId<>0 and numIsValid<>2")
		public List<MediaDomain> getAllMediaDetails();

		@Query("from MediaDomain e where e.numIsValid<>2 and  e.numId=:id")
		public MediaDomain getMediaById(@Param("id") long id);

		@Query(" from MediaDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
		public List<MediaDomain> loadMediaDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
			
		@Query(" from MediaDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
		public List<MediaDomain> loadMediaByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
}
