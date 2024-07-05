package in.pms.global.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.global.domain.RoleActionMappingDomain;

@Repository
public interface RoleActionMappingDao extends JpaRepository<RoleActionMappingDomain, Long>{

	@Query("from RoleActionMappingDomain r where r.roleMasterDomain.numId=:numRoleId and r.actionMasterDomain.numActionId=:numActionId and r.workflowMasterDomain.numWorkflowId=:numWorkflowId and r.numIsValid=1")
	List<RoleActionMappingDomain> getRoleActionMappingDetails(@Param("numActionId")long numActionId, @Param("numRoleId")long numRoleId,@Param("numWorkflowId") long numWorkflowId);
	
	

}
