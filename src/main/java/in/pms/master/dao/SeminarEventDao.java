package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.domain.AwardWonDomain;
import in.pms.master.domain.MediaDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.SeminarEventDomain;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;


	@Repository
	public interface SeminarEventDao extends JpaRepository<SeminarEventDomain , Long> {

		@Query("from SeminarEventDomain where numId<>0 and numIsValid<>2")
		public List<SeminarEventDomain> getAllSeminar();
		
		@Query("from SeminarEventDomain e where e.numIsValid<>2 and  e.numId=:id")
		public SeminarEventDomain getSeminarDomainById(@Param("id") long id);
		
		@Query(" from SeminarEventDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
		public List<SeminarEventDomain> loadSeminarEventDetail(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
		
		@Query(" from SeminarEventDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
		public List<SeminarEventDomain> loadSeminarByDetailsId(@Param("progressDetailsId") long progressDetailsId);
		
}
