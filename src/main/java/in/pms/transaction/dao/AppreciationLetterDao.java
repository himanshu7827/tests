package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.dao.DaoHelper;
import in.pms.transaction.domain.AppreciationLetterDomain;


import in.pms.transaction.domain.OthersDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppreciationLetterDao extends JpaRepository<AppreciationLetterDomain, Long> {
	
	@Query("from AppreciationLetterDomain where numIsValid=1 and numCategoryId= :id ")
	public AppreciationLetterDomain getAppreciationByreference(@Param("id") long id);
		
	@Query("from AppreciationLetterDomain where numAppreciationID<>0 and numIsValid<>0 order by dtTrDate")
	public List<AppreciationLetterDomain> getAllAppreciationLetter() ;
	
	@Query(" from AppreciationLetterDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<AppreciationLetterDomain> loadAppreciationDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from AppreciationLetterDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate")
	public List<AppreciationLetterDomain> loadAppreciationByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
}
