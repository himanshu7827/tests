package in.pms.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSEmployeeMasterDao;


@Service
public class EmployeeMasterService {
	
	@Autowired
	public TSEmployeeMasterDao employeeMasterDao;
	
	public String getEmpName(long empId) {
		return employeeMasterDao.getEmpNameById(empId);
	}
	
	public long getGroupId(long empId) {
		return employeeMasterDao.getGroupIdById(empId);
	}
	
	//REPORT
		//R1 - Finding Employee details under GC
	public List<String> getEmpNamesByGroupId(long groupId){
		return employeeMasterDao.getEmpNamesByGroupId(groupId);
	}
	
	public List<Long> getEmpIdsByGroupId(long groupId){
		return employeeMasterDao.getEmpIdsByGroupId(groupId);	
	}
	
	//MAILING
			//purpose - to get all employees
	public List<Long> getEmpIdsAll(){
		return employeeMasterDao.getEmpIdsAll();
	}
	
			//Purpose - To get employee mail from employee Id
	public String getEmpMailById(long empId){
		return employeeMasterDao.getEmpMailById(empId);
	}
	
}
