package in.pms.transaction.dao;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.dao.DaoHelper;
import in.pms.transaction.domain.AdditionalQualificationDomain;
import in.pms.transaction.domain.MouCollaborationDomain;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.domain.TalksDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TalksDao extends JpaRepository<TalksDomain, Long> {
	
	

	@Query("from TalksDomain where numIsValid=1 and numCategoryId=:id")
	public TalksDomain getTalkbyCategoryId(@Param("id") long id);
		
	@Query("from TalksDomain where numTalkID<>0 and numIsValid<>0 order by dtTrDate")
	public List<TalksDomain> getAllTalks();
	
	@Query(" from TalksDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<TalksDomain> loadTalksDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from TalksDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<TalksDomain> loadTalksByDetailsId(@Param("progressDetailsId") long progressDetailsId);
	
}
