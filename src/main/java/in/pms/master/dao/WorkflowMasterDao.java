package in.pms.master.dao;

import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.transaction.domain.CopyRightDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkflowMasterDao extends JpaRepository<WorkflowMasterDomain, Long>{

	@Query("from WorkflowMasterDomain a where a.numIsValid<>2")
	List<WorkflowMasterDomain> getAlWorkflowDetails();
	
	@Query("from WorkflowMasterDomain where strType=:strType")
	public WorkflowMasterDomain getworkflow(@Param("strType") String strType) ;
	
	

}
