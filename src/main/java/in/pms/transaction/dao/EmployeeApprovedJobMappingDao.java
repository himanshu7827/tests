package in.pms.transaction.dao;

import in.pms.transaction.domain.EmployeeApprovedJobMapping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeApprovedJobMappingDao extends JpaRepository<EmployeeApprovedJobMapping, Integer> {
	
	@Query("select a from EmployeeApprovedJobMapping a join fetch a.employeeMasterDomain where a.approvedJob.projectMasterDomain.numId=:projectId and a.approvedJob.status in ('Assigned','Created') and a.numIsValid=1")
	List<EmployeeApprovedJobMapping> getActiveDataByProject(@Param("projectId") long projectId);

	@Query("select a from EmployeeApprovedJobMapping a join fetch a.employeeMasterDomain where a.employeeMasterDomain.numId=:employeeId and a.approvedJob.status='Assigned' and a.numIsValid=1")
	List<EmployeeApprovedJobMapping> getActiveDataByEmployee(@Param("employeeId") long employeeId);

}
