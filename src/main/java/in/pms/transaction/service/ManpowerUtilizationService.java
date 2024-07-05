package in.pms.transaction.service;

import in.pms.master.domain.EmployeeSalaryDomain;
import in.pms.master.model.EmployeeSalaryModel;
import in.pms.transaction.domain.ManpowerUtilization;
import in.pms.transaction.model.ManpowerUtilizationModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ManpowerUtilizationService {
	
	public List<Object[]> getUtilizationForEmployee(long employeeId, int month,int year);
	
	@Transactional	
	public long mergeManpowerUtilization(ManpowerUtilizationModel manpowerUtilizationModel);
	public void getUtilAccess();
	
	public List<ManpowerUtilizationModel> getManpowerUtilization(long projectId, int month, int year);
	
	@Transactional
	public List<String> saveUtilizationDetails(ManpowerUtilizationModel manpowerUtilizationModel);
	
	@Transactional
	public ManpowerUtilization saveManpowerUtilizationData(ManpowerUtilization manpowerUtilization);

}
