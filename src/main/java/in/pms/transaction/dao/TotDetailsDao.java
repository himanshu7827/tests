package in.pms.transaction.dao;

import in.pms.transaction.domain.DeploymentTotDetailsDomain;
import in.pms.transaction.domain.TotDetailsDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TotDetailsDao extends JpaRepository<TotDetailsDomain, Long>{
	
	/*@Query("from DeploymentTotDetailsDomain e where e.numGroupCategoryId=12072020 and e.numIsValid=1")
	public List<DeploymentTotDetailsDomain> getDeploymentDetails(@Param("numGroupCategoryId") long numGroupCategoryId);
	*/
	//@Query("Select e from DeploymentTotDetailsDomain e where e.numGroupCategoryId=12072020 and e.numIsValid=1")
	
	@Query(" from TotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by strAgencyName")
	public List<TotDetailsDomain> loadDeploymentTOTByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	@Query(" from TotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<TotDetailsDomain> getTotDetails(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from TotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<TotDetailsDomain> loadDeploymentDetailsByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
}

