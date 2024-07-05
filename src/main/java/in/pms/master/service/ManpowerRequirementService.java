package in.pms.master.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ManpowerRequirementModel;




public interface ManpowerRequirementService {
	
	@Transactional
	public long saveManpowerRequirementData(ManpowerRequirementModel manpowerRequirementModel);
	public List<ManpowerRequirementModel> getAllManpowerRequirement(long projectId);
	@Transactional
	public Map<String,List<EmployeeRoleMasterModel>> getManpowerRequirementWithAssignedRole(long projectId);
	@Transactional
	public int deleteManpowerReq(long numId);
	//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
	@Transactional
	public Map<String, List<EmployeeRoleMasterModel>> getManpowerRequirementWithAllAssignedRole(long projectId);
}