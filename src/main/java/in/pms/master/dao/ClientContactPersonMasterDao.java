package in.pms.master.dao;

import java.util.List;

import in.pms.master.domain.ContactPersonMasterDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientContactPersonMasterDao extends JpaRepository<ContactPersonMasterDomain, Long> {

	@Query("from ContactPersonMasterDomain where lower(strContactPersonName)=lower(:contactPersonName) and numIsValid<>2")
	public ContactPersonMasterDomain getContactPersonMasterByName(@Param("contactPersonName") String contactPersonName);
	
	@Query("from ContactPersonMasterDomain where numId<>0 and numIsValid<>2")
	public List<ContactPersonMasterDomain> getAllContactPersonMasterDomain();
	
	@Query("from ContactPersonMasterDomain where numId<>0 and numIsValid=1")
	public List<ContactPersonMasterDomain> getAllActiveContactPersonMasterDomain();
	
	@Query("from ContactPersonMasterDomain e where e.numIsValid<>2 and  e.clientId=:clientId  order by strContactPersonName")
	public List<ContactPersonMasterDomain> getAllContactPersonByClientId(@Param("clientId") long clientId);
	
	@Query("from ContactPersonMasterDomain c where c.numId in :clientIds")
	public List<ContactPersonMasterDomain> getContactPersonMasterById(@Param("clientIds") List<Long> ids);
	
	@Query("select a.contactMaster from ProjectMasterDomain a  join  a.contactMaster  where a.numId=:projectId")
	public List<ContactPersonMasterDomain> projectWiseContactPerson(@Param("projectId") long projectId);
	
	@Query(value="select a.application.contactMaster from ProjectMasterDomain a where a.numId=:projectId",nativeQuery=false)
	public List<ContactPersonMasterDomain> applicationWiseContactPerson(@Param("projectId") long projectId);
	
	
}
