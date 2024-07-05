package in.pms.timesheet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.timesheet.dao.TSEmployeeRoleMstDao;

@Service
public class EmployeeRoleMstService {
	
	@Autowired
	public TSEmployeeRoleMstDao employeeRoleMstDao;
	
	public List<String> getProjectNames(long empId) {
		return this.employeeRoleMstDao.getProjectNamesByUserId(empId);
	}
	
	public List<Integer> getProjectIds(long empId) {
		return this.employeeRoleMstDao.getProjectIdsByUserId(empId);
	}
	
	public List<String> getProjectTimesheetTypesByUserId(long empId){
		return this.employeeRoleMstDao.getProjectTimesheetTypesByUserId(empId);
	}
	
	public List<String> getProjectNamesByGroupId(int groupId) {
		return this.employeeRoleMstDao.getProjectNamesByGroupId(groupId);
	}
	
	public List<Integer> getProjectIdsByGroupId(int groupId) {
		return this.employeeRoleMstDao.getProjectIdsByGroupId(groupId);
	}
	
	public List<String> getProjectTimesheetTypesByGroupId(int groupId){
		return this.employeeRoleMstDao.getProjectTimesheetTypesByGroupId(groupId);
	}
	
	public long getRoleIdByUserId(int userId){
		return this.employeeRoleMstDao.getRoleIdByUserId(userId);
	}
	
	//REPORTS
			//R1 - Finding Employee details under PL
	public List<Long> getEmployeeIdByProjectId(List<Integer> projectIds, long roleIdOfLoggedUser){
		return this.employeeRoleMstDao.getEmployeeIdByProjectId(projectIds, roleIdOfLoggedUser);
	}
	
	//MAILING
	public List<Long> getEmployeeIdsByRoleId(long roleId){
		return this.employeeRoleMstDao.getEmployeeIdsByRoleId(roleId);
	}
	
	public List<Long> getEmployeeIdsByRoleIdAndGroupID(long roleId, int groupId){
		return this.employeeRoleMstDao.getEmployeeIdsByRoleIdAndGroupID(roleId, groupId);
	}
	
	public Long getHODsByEmployeeId(long numEmpId) {
		return this.employeeRoleMstDao.getHODsByEmployeeId(numEmpId);
	}
	
	public Long getPIsByEmployeeId(long numEmpId) {
		return this.employeeRoleMstDao.getPIsByEmployeeId(numEmpId);
	}
	
	public List<Long> getEmployeeIdsOfTMML(int groupId){
		return this.employeeRoleMstDao.getEmployeeIdsOfTMML(groupId);
	}
	
	public long getRoleIdByUserIdForMail(int userId){
		return this.employeeRoleMstDao.getRoleIdByUserIdForMail(userId);
	}
}
