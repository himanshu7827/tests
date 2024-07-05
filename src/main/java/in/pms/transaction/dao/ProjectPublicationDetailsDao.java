package in.pms.transaction.dao;
import java.util.List;

import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProjectPublicationDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectPublicationDetailsDao extends JpaRepository<ProjectPublicationDomain, Integer>
{
	@Query(value ="SELECT * from PMS_PROJECT_PUBLICATION_DETAILS where  num_isvalid=1 and num_report_id=:reportId", nativeQuery=true)
	public List<ProjectPublicationDomain> getPublicationDetail(@Param("reportId") int reportId);	
	

	@Query(" from ProjectPublicationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<ProjectPublicationDomain> loadPublicationDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from ProjectPublicationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtpublication,strPublicationType")
	public List<ProjectPublicationDomain> loadPublicationByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
}