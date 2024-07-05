package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */


import in.pms.transaction.domain.AdditionalQualificationDomain;


import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdditionalQualificationDao extends JpaRepository<AdditionalQualificationDomain, Long> {
	
	@Query("from AdditionalQualificationDomain where numIsValid=1 and numEmployeeID= :id ")
	public AdditionalQualificationDomain getAddQualEmpId(@Param("id") long id);
		
	@Query("from AdditionalQualificationDomain where numQualID<>0 and numIsValid=1 order by dtTrDate")
	public List<AdditionalQualificationDomain> getAllAdditionalcollab();
	
	@Query(" from AdditionalQualificationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<AdditionalQualificationDomain> loadAdditionQualificationDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from AdditionalQualificationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<AdditionalQualificationDomain> loadAdditionalByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	}
