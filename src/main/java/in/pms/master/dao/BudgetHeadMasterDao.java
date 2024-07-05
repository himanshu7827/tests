package in.pms.master.dao;

import in.pms.master.domain.BudgetHeadMasterDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetHeadMasterDao extends
		JpaRepository<BudgetHeadMasterDomain, Long> {

	@Query("from BudgetHeadMasterDomain where numIsValid=1")
	public List<BudgetHeadMasterDomain> getAllActiveBudgetMasterDomain();

	@Query("from BudgetHeadMasterDomain where numIsValid in(0,1)")
	public List<BudgetHeadMasterDomain> getAllBudgetData();

	@Query("from BudgetHeadMasterDomain where numId in :ids")
	public List<BudgetHeadMasterDomain> getBudgetHeadDataById(
			@Param("ids") List<Integer> numId);

	@Query(value="from BudgetHeadMasterDomain where  numIsValid!=2 and lower(strBudgetHeadName)= lower(:budgetName)")
	public BudgetHeadMasterDomain getBudgetHeadByName(
			@Param("budgetName") String budgetName);

	@Query(nativeQuery = true, value = "select b.str_budget_head_name ,a.num_amount, a.num_year, a.num_project_id_fk FROM pms_project_budget_master a, pms_project_budget_head_mst b where a.num_budget_head_id = b.num_budget_head_id and a.num_isvalid = 1 and a.num_project_id_fk=:projectId order by 3")
	public List<Object[]> getAmountAgainstBudgetHeadByProjectId(@Param("projectId") long projectId);
}
