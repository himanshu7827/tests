package in.pms.transaction.dao;
import java.util.List;

import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectHighlightsDomain;
import in.pms.transaction.domain.ProjectInnovationsDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectInnovationsDao extends JpaRepository<ProjectInnovationsDomain, Integer>
{
	@Query(value ="SELECT * from PMS_PROJECT_INNOVATIONS where  num_isvalid=1 and num_report_id_fk=:reportId", nativeQuery=true)
	public ProjectInnovationsDomain getdetail(@Param("reportId") int reportId);
	
	@Query(" from ProjectInnovationsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<ProjectInnovationsDomain> loadInnovationDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);

	@Query(" from ProjectInnovationsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<ProjectInnovationsDomain> loadPublicationByDetailsId(@Param("progressDetailsId") long progressDetailsId);

	
}