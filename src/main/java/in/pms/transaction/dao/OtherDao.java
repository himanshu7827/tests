package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.dao.DaoHelper;
import in.pms.transaction.domain.AdditionalQualificationDomain;
import in.pms.transaction.domain.OthersDomain;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.TalksDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OtherDao extends JpaRepository<OthersDomain, Long> {
	
	@Query("from OthersDomain where numIsValid=1 and numCategoryId= :id ")
	public OthersDomain getOthersbyCategoryId(@Param("id") long id);
		
	@Query("from OthersDomain where numOthersID<>0 and numIsValid<>0 order by dtTrDate")
	public List<OthersDomain> getAllOthers();

	@Query(" from OthersDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<OthersDomain> loadOthersDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from OthersDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<OthersDomain> loadOtherByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
}
