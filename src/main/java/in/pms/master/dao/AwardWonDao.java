package in.pms.master.dao;

import in.pms.master.domain.AwardWonDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AwardWonDao extends JpaRepository<AwardWonDomain , Long> {

	
	@Query("from AwardWonDomain where numId<>0 and numIsValid<>2")
	public List<AwardWonDomain> getAllAward();
	
	
	@Query("from AwardWonDomain e where e.numIsValid<>2 and  e.numId=:id")
	public AwardWonDomain getAwardDomainById(@Param("id") long id);
	
	@Query(" from AwardWonDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<AwardWonDomain> loadAwardWonDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	@Query(" from AwardWonDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by awardName")
	public List<AwardWonDomain> loadAwardByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
}
