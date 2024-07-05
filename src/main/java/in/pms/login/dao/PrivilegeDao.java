package in.pms.login.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.login.domain.Privilege;

@Repository
public interface PrivilegeDao extends JpaRepository<Privilege, Long> {

	@Query("from Privilege")
	public List<Privilege> privilegeName();
	
	@Query("from Privilege where lower(name)=lower(:privilegeName)")
	public Privilege getPrivilegeByName(@Param("privilegeName") String privilegeName);
	

	@Query("from Privilege where id=:privilegeId")
	public Privilege getPrivilegeById(@Param("privilegeId") long privilegeId );

}
