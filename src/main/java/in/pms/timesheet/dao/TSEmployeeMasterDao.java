package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.timesheet.model.EmployeeMasterModel;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Repository
public class TSEmployeeMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	//Purpose - To get employee name from employee Id
	public String getEmpNameById(long empId){
		EmployeeMasterModel employeeMasterDomain = new EmployeeMasterModel();
		String query = "from EmployeeMasterModel where numId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query, paraList);
		if(list != null && !list.isEmpty()){
			employeeMasterDomain =list.get(0);
		}
		if(employeeMasterDomain != null) {
	        return employeeMasterDomain.getEmployeeName();
	    } else {
	        // Handle the case when employeeMasterDomain is null, for example, return null or throw an exception
	        return null;
	    }
	}
	
	//Purpose - To get group id from employee Id
	public long getGroupIdById(long empId){
		
		EmployeeMasterModel employeeMasterDomain =null;
		String query = "from EmployeeMasterModel where numId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query, paraList);
		if(!list.isEmpty()){
			employeeMasterDomain =list.get(0);
		}
		
		if (employeeMasterDomain != null) {
	        return employeeMasterDomain.getGroupid();
	    } 
		else {
	        // Handle the case where no employee with given ID is found
	        // You can throw an exception, return a default value, or handle it based on your application's logic
	        throw new IllegalStateException("Employee with ID " + empId + " not found");
	    }	
	}
	
	//REPORT
		//R1 - Finding Employee details under GC
			//Purpose - To fetch all employee name under particular group for reporting
	public List<String> getEmpNamesByGroupId(long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
		EmployeeMasterModel employeeMasterDomain =null;
		String query="";
		ArrayList<Object> paraList = new ArrayList<>();
		 if(roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
			query = "from EmployeeMasterModel where num_isvalid=1";
		}
		 else {
			query = "from EmployeeMasterModel where numGroupId = ?0 and num_isvalid=1";
			paraList.add(groupId);
		}
		
		//String query = "from EmployeeMasterModel where numGroupId = ?0 and num_isvalid=1";
		//ArrayList<Object> paraList = new ArrayList<>();
		//paraList.add(groupId);
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query, paraList);
		List<String> empList = new ArrayList<String>();
		for(int i =0; i<list.size(); i++){
			empList.add(list.get(i).getEmployeeName());
		}
		return empList;	
	}
	
			//Purpose - To fetch all employee name under particular group for reporting
	public List<Long> getEmpIdsByGroupId(long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	 	
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
		EmployeeMasterModel employeeMasterDomain =null;
		String query="";
		ArrayList<Object> paraList = new ArrayList<>();
		 if(roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
				query = "from EmployeeMasterModel where num_isvalid=1";
		}
		 else {
			query = "from EmployeeMasterModel where numGroupId = ?0 and num_isvalid=1";
			paraList.add(groupId);
		}
		//String query = "from EmployeeMasterModel where numGroupId = ?0 and num_isvalid=1";
		//ArrayList<Object> paraList = new ArrayList<>();
		//paraList.add(groupId);
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query, paraList);
		List<Long> empList = new ArrayList<Long>();
		for(int i =0; i<list.size(); i++){
			empList.add(list.get(i).getNumId());
		}
		return empList;	
	}
	//MAILING
		//purpose - to get all employees except GC
	public List<Long> getEmpIdsAll(){
		EmployeeMasterModel employeeMasterDomain =null;
		String query = "from EmployeeMasterModel e WHERE e.numIsValid = 1 AND e.employmentStatus = 'Working'";
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query);
		List<Long> empList = new ArrayList<Long>();
		for(int i =0; i<list.size(); i++){
			empList.add(list.get(i).getNumId());
		}
		return empList;	
	}
	
		//Purpose - To get employee mail from employee Id
	public String getEmpMailById(long empId){
		EmployeeMasterModel employeeMasterDomain =null;
		String query = "from EmployeeMasterModel where numId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<EmployeeMasterModel> list = daoHelper.findByQuery(query, paraList);
		if(!list.isEmpty()){
			employeeMasterDomain =list.get(0);
		}
		if (employeeMasterDomain != null) { // Add null check here
	        return employeeMasterDomain.getOfficeEmail();
	    } else {
	        // Handle the case where employeeMasterDomain is null
	        return null; // Or throw an exception or handle it according to your requirements
	    }	
	}
	
	/*//purpose - to get all employees except GC
		public List<Long> getEmpIdsAllOnlyTMML(){
			EmployeeMasterModel employeeMasterDomain =null;
			String query = "from EmployeeMasterModel e JOIN FETCH EmployeeRoleMstModel r ON e.numId = r.numEmpId WHERE e.numIsValid = 1 AND e.employmentStatus = 'Working' AND r.numRoleId in (1,2)";
			List<EmployeeMasterModel> list = daoHelper.findByQuery(query);
			List<Long> empList = new ArrayList<Long>();
			for(int i =0; i<list.size(); i++){
				empList.add(list.get(i).getNumId());
			}
			return empList;	
		}*/
	
}
