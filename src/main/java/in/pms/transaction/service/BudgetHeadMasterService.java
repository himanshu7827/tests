package in.pms.transaction.service;

import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.transaction.model.BudgetHeadMasterForm;

import java.util.List;

import org.json.JSONArray;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



public interface BudgetHeadMasterService {

	
	public List<BudgetHeadMasterForm> getAllBugdetHeadData();
	
	public String checkDuplicateBugdetHeadData(BudgetHeadMasterForm budgetHeadMasterForm);
	
	public List<BudgetHeadMasterForm> getAllActiveBudgetMasterDomain();
	
	@Transactional
	public long saveBudgetHeadData(BudgetHeadMasterForm budgetHeadMasterForm);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteBudgetHeadData(BudgetHeadMasterForm budgetHeadMasterForm);

	public List<BudgetHeadMasterDomain> getBudgetHeadDataById(List<Integer> ids);
	
	public JSONArray getAmountAgainstBudgetHeadByProjectId(long projectId);

}
