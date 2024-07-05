package in.pms.global.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.global.domain.ActionMasterDomain;
import in.pms.global.domain.WorkflowMasterDomain;

@Repository
public interface ActionMasterDao extends JpaRepository<ActionMasterDomain, Long>{

	@Query("from ActionMasterDomain a where a.numActionId in(:newList) and a.numIsValid=1")
	List<ActionMasterDomain> getActionDetails(@Param("newList") List<Long> newList);

	@Query("from ActionMasterDomain a where a.numIsValid<>2")
	List<ActionMasterDomain> getAllActionDetails();
	
	@Query("from ActionMasterDomain where strName=:strName")
	public ActionMasterDomain getAction(@Param("strName") String strName) ;
	
	
	
}
