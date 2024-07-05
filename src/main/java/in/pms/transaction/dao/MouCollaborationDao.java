package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */

import in.pms.transaction.domain.AppreciationLetterDomain;
import in.pms.transaction.domain.MouCollaborationDomain;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.List;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MouCollaborationDao extends JpaRepository<MouCollaborationDomain, Long> {
	
	@Query("from MouCollaborationDomain where numIsValid=1 and numGroupCategoryId= :id ")
	public MouCollaborationDomain getMoubyCategoryId(@Param("id") long id);
		
	@Query("from MouCollaborationDomain where numCollabID<>0 and numIsValid<>0 order by dtTrDate")
	public List<MouCollaborationDomain> getAllMouCollab();
	
	@Query(" from MouCollaborationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<MouCollaborationDomain> loadCollabDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from MouCollaborationDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<MouCollaborationDomain> loadMouByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
	
		
}
