package in.pms.transaction.dao;

import java.util.List;

import in.pms.master.domain.ExitInterviewDomain;
import in.pms.master.domain.ExitInterviewMainDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExitInterviewDao extends JpaRepository<ExitInterviewMainDomain , Long> {
	
	@Query("from ExitInterviewMainDomain where numId<>0 and numIsValid=1")
	public List<ExitInterviewMainDomain> getActiveExitInterviewData();
	
	@Query("from ExitInterviewDomain e where e.numIsValid<>2 and  e.exitInterviewMainDomain.numId=:id")
	public List<ExitInterviewDomain> getExitInterviewChildDataById(@Param("id") long id);
	
	@Query("from ExitInterviewMainDomain e where e.numIsValid=1 and  e.employeeMasterDomain.numId=:id")
	public ExitInterviewMainDomain getExitInterviewMainDomainDataByEmpId(@Param("id") long id);
	
	@Query("from ExitInterviewMainDomain e where e.numIsValid=1 and e.strStatus='SUB' and e.strFlaEmail=:id")
	public List<ExitInterviewMainDomain> getExitInterviewMainDomainDataByFlaEmail(@Param("id") String id);
	
	@Query("from ExitInterviewMainDomain e where e.numIsValid=1 and e.strStatus='FLA Approved' and e.strSlaEmail=:id")
	public List<ExitInterviewMainDomain> getExitInterviewMainDomainDataBySlaEmail(@Param("id") String id);
	
	@Query("from ExitInterviewMainDomain e where e.numIsValid=1 and  e.numId=:id")
	public ExitInterviewMainDomain getExitInterviewMainDomainDataById(@Param("id") long id);
	
	@Query("from ExitInterviewMainDomain e where e.numIsValid=1 and e.strStatus='SLA Approved'")
	public List<ExitInterviewMainDomain> getExitInterviewDataApprovedBySla();
			
}
