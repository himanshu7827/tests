package in.pms.transaction.dao;

import in.pms.transaction.domain.DesignationForClient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationForClientDao extends JpaRepository<DesignationForClient, Integer> {

	@Query("from DesignationForClient where numIsValid=1 order by hierarchy")
	public List<DesignationForClient> getActiveDesignationForClient();

	@Query("from DesignationForClient where numIsValid in (0,1) and lower(designationName) = lower(:designationName)")
	public List<DesignationForClient> getByName(@Param("designationName")String designationName);
	
	
}
