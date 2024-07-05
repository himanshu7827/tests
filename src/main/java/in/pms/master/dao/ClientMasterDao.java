package in.pms.master.dao;

import in.pms.master.domain.ClientMasterDomain;
import in.pms.transaction.domain.Application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMasterDao extends JpaRepository<ClientMasterDomain, Long>{

	@Query(value="from ClientMasterDomain where numIsValid=1 and clientName=:clientName")
	public ClientMasterDomain getClientMasterByName(@Param("clientName")String clientName);	

	@Query(value="from ClientMasterDomain where numIsValid=1")
	public List<ClientMasterDomain> getAllClientMasterDomain();
	
	@Query(value ="from ClientMasterDomain where numIsValid=1 order by clientName")
	public List<ClientMasterDomain> getAllActiveClientMasterDomain();
	
	@Query(value="from Application a left join fetch a.clientMaster where a.numIsValid=1 and  a.numId=:applicationId")
	public List<Application> getAllActiveClientMasterDomainByApplicationId(@Param("applicationId") long applicationId);
}
