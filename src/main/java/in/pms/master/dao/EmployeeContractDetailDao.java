package in.pms.master.dao;

import java.util.List;

import in.pms.master.domain.EmployeeContractDetailDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeContractDetailDao extends JpaRepository<EmployeeContractDetailDomain , Long> {

	@Query("from EmployeeContractDetailDomain where numId<>0 and numIsValid<>2")
	public List<EmployeeContractDetailDomain> getAllContractData();
	
	@Query("from EmployeeContractDetailDomain where numId<>0 and numIsValid=1")
	public List<EmployeeContractDetailDomain> getActiveContractData();
	
	@Query("from EmployeeContractDetailDomain e where e.numIsValid<>2 and  e.numId=:ids")
	public List<EmployeeContractDetailDomain> getContractDataById(@Param("ids") List<Long> ids);
	
	@Query("from EmployeeContractDetailDomain e where e.numIsValid=1 and  e.employeeMasterDomain.numId=:id")
	public List<EmployeeContractDetailDomain> getContractDataByEmpId(@Param("id") long id);	
	
	@Query("from EmployeeContractDetailDomain e where e.numIsValid<>2 and  e.numId=:id")
	public EmployeeContractDetailDomain getContractDataById(@Param("id") long id);
}
