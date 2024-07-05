package in.pms.transaction.dao;

import in.pms.transaction.domain.DesignationForClient;
import in.pms.transaction.domain.DesignationProjectCategoryMapping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface DesignationProjectCategoryMappingDao extends
		JpaRepository<DesignationProjectCategoryMapping, Integer> {

//	@Query("from DesignationProjectCategoryMapping a where a.numIsValid =1 and a.designationForClient.numDesignationId =:designationId and a.projectCategoryMaster.numId=:projectCategoryId")
//	public List<DesignationProjectCategoryMapping> getDesignationProjects(@Param("projectCategoryId")int projectCategoryId,@Param("designationId")int designationId);
	
	//Query modified by devesh on 30-04-24 to include numDeputed value in where condition
	@Query("from DesignationProjectCategoryMapping a where a.numIsValid =1 and a.designationForClient.numDesignationId =:designationId and a.projectCategoryMaster.numId=:projectCategoryId and a.numDeputedAt=:numDeputedAt")
	public List<DesignationProjectCategoryMapping> getDesignationProjects(@Param("projectCategoryId")int projectCategoryId,@Param("designationId")int designationId,@Param("numDeputedAt")int numDeputedAt);
	
	@Query("select distinct a.designationForClient from DesignationProjectCategoryMapping a where a.numIsValid =1  and a.projectCategoryMaster.numId=:projectCategoryId order by a.designationForClient.hierarchy")
	public List<DesignationForClient> getDataByCategory(@Param("projectCategoryId")int projectCategoryId);
	
	
}
