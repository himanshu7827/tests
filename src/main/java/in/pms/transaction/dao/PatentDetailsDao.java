package in.pms.transaction.dao;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PatentDetailsDao extends JpaRepository<PatentDetailsDomain, Integer>{
	@Query(value ="SELECT * from PMS_PROJECT_PATENT_DETAILS where  num_isvalid=1 and num_report_id=:reportId", nativeQuery=true)
	public List<PatentDetailsDomain> getPublicationDetail(@Param("reportId") int reportId);
	
	@Query(" from PatentDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<PatentDetailsDomain> loadPatentByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	@Query(" from PatentDetailsDomain a  where a.numIsValid =1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId order by dtTrDate")
	public List<PatentDetailsDomain> loadPatentDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
}