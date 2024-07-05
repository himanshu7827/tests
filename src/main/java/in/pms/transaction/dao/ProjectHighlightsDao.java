package in.pms.transaction.dao;
import java.util.List;

import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectHighlightsDomain;
import in.pms.transaction.domain.ProjectInnovationsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectHighlightsDao extends JpaRepository<ProjectHighlightsDomain, Integer>
{
	@Query(value ="SELECT * from PMS_PROJECT_HIGHLIGHTS where  num_isvalid=1 and num_report_id_fk=:reportId", nativeQuery=true)
	public ProjectHighlightsDomain getdetail(@Param("reportId") int reportId);
	
	@Query(" from ProjectHighlightsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public ProjectHighlightsDomain loadHighlightsDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);

	@Query(" from ProjectHighlightsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<ProjectHighlightsDomain> loadHighlightsByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	

}

