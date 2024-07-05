package in.pms.master.dao;

import java.util.List;

import in.pms.master.domain.AnswerMasterDomain;
import in.pms.master.domain.ContactPersonMasterDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerMasterDao extends JpaRepository<AnswerMasterDomain , Long> {

	@Query("from AnswerMasterDomain where lower(strAnswer)=lower(:strAnswer) and numIsValid<>2")
	public AnswerMasterDomain getAnswerByName(@Param("strAnswer") String strAnswer);
	
	@Query("from AnswerMasterDomain where numId<>0 and numIsValid<>2")
	public List<AnswerMasterDomain> getAllAnswerData();
	
	@Query("from AnswerMasterDomain where numId<>0 and numIsValid=1")
	public List<AnswerMasterDomain> getActiveAnswerData();
	
	@Query("from AnswerMasterDomain e where e.numIsValid<>2 and  e.numId=:id")
	public AnswerMasterDomain getAnswerDataById(@Param("id") long id);
	
	@Query("from AnswerMasterDomain c where c.numId in :ids")
	public List<AnswerMasterDomain> getAnswerDataByIds(@Param("ids") List<Long> ids);
		
}
