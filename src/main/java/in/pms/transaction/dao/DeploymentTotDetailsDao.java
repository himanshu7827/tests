package in.pms.transaction.dao;

import in.pms.transaction.domain.DeploymentTotDetailsDomain;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeploymentTotDetailsDao extends JpaRepository<DeploymentTotDetailsDomain, Long>{
	
	/*@Query("from DeploymentTotDetailsDomain e where e.numGroupCategoryId=12072020 and e.numIsValid=1")
	public List<DeploymentTotDetailsDomain> getDeploymentDetails(@Param("numGroupCategoryId") long numGroupCategoryId);
	*/
	//@Query("Select e from DeploymentTotDetailsDomain e where e.numGroupCategoryId=12072020 and e.numIsValid=1")
	
	@Query(" from DeploymentTotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by strServiceName")
	public List<DeploymentTotDetailsDomain> loadDeploymentTOTByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	@Query(" from DeploymentTotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<DeploymentTotDetailsDomain> getDeploymentDetails(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from DeploymentTotDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<DeploymentTotDetailsDomain> loadDeploymentDetailsByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
}

