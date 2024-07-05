package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.MediaDomain;
import in.pms.transaction.domain.AdditionalQualificationDomain;
import in.pms.transaction.domain.CategoryMaster;
import in.pms.transaction.domain.CopyRightDomain;
import in.pms.transaction.domain.PatentDetailsDomain;

@Repository
public interface CopyrightDao extends JpaRepository<CopyRightDomain, Long> {
		
	@Query("from CopyRightDomain where numIsValid=1 and numGroupCategoryId=:id and strReferenceNumber=:strReferenceNumber")
	public CopyRightDomain getCopyrightByreference(@Param("strReferenceNumber") String strReferenceNumber,@Param("id") long id) ;
		
	@Query("from CopyRightDomain where numCopyRightID<>0 and numIsValid=1 order by dtTrDate")
	public List<CopyRightDomain> getAllCopyRight();
	
	@Query(" from CopyRightDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<CopyRightDomain> loadCopyRightDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);

	@Query(" from CopyRightDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<CopyRightDomain> loadCopyRightByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
}
