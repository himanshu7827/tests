package in.pms.master.service;
import in.pms.master.domain.EmployeeSalaryDomain;
import in.pms.master.model.EmployeeSalaryModel;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;



public interface EmployeeSalaryService {

	
	public List<EmployeeSalaryModel> getAllEmployeeSalData(long empId);
	
	@Transactional
	public long saveEmployeeSalData(EmployeeSalaryModel employeeSalaryModel);
	
	public double getEmployeeSalary(long employeeId,int month,int year);

	public List<String> saveEmployeeSalDetails(EmployeeSalaryModel employeeSalaryModel);
	
	@Transactional
	public EmployeeSalaryDomain saveEmployeeSalaryData(EmployeeSalaryDomain employeeSalaryDomain);

}
