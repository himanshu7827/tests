package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;

@Repository
public class TSEmployeeRoleMstDao{
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To get all project names under a employee from their employee Id
	public List<String> getProjectNamesByUserId(long empId){
		
		String query = "SELECT DISTINCT p.strProjectName, e.numProjectId, p.strProjectRefNo FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numEmpId = ?0 AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<String> projectNames = new ArrayList<>();

	    if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0 && row[0] instanceof String) {
	                	if(row[2] == null){
	                		projectNames.add((String) row[0]);
	                	}else{
	                		projectNames.add((String) row[2] + " - " +row[0]);
	                	}
	                    //projectNames.add((String) row[2] + " - " +row[0]);
	                }
	            }
	        }
	    }
		return (projectNames != null && !projectNames.isEmpty()) ? projectNames : null;	
	}
	
	//Purpose - To get all project Ids under a employee from thier employee Id
	public List<Integer> getProjectIdsByUserId(long empId){
		
		String query = "SELECT DISTINCT e.numProjectId, p.strProjectName FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numEmpId = ?0 AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		//String query = "SELECT e.numProjectId FROM EmployeeRoleMstModel e WHERE e.dtEndDate =null AND e.numEmpId ="+empId;
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<Integer> projectIds = new ArrayList<>();

	    if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0 && row[0] instanceof Integer) {
	                    projectIds.add((Integer) row[0]);
	                }
	            }
	        }
	    }
		return (projectIds != null && !projectIds.isEmpty()) ? projectIds : null;	
	}
	
	
	//Purpose - To get all project types under a employee from thier employee Id
	public List<String> getProjectTimesheetTypesByUserId(long empId){
		
		String query = "SELECT DISTINCT p.timesheetType, e.numProjectId FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numEmpId = ?0 AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		//String query = "SELECT e.numProjectId FROM EmployeeRoleMstModel e WHERE e.dtEndDate =null AND e.numEmpId ="+empId;
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(empId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<String> projectTypes = new ArrayList<String>();
		if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0) {
	                	projectTypes.add((String) row[0]);
	                }
	            }
	        }
	    }
		return (projectTypes != null && !projectTypes.isEmpty()) ? projectTypes : null;	
	}
	
	//Purpose - To get all project names from  group Id
	public List<String> getProjectNamesByGroupId(int groupId){
		
		String query = "SELECT DISTINCT p.strProjectName, e.numProjectId, p.strProjectRefNo FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numGroupId = ?0 AND p.strProjectName != 'All Project' AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(groupId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<String> projectNames = new ArrayList<>();

	    if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0 && row[0] instanceof String) {
	                	if(row[2] == null){
	                		projectNames.add((String) row[0]);
	                	}else{
	                		projectNames.add((String) row[2] + " - " +row[0]);
	                	}
	                    //projectNames.add((String) row[2] + " - " +row[0]);
	                }
	            }
	        }
	    }
		return projectNames;	
	}
		
	//Purpose - To get all project Ids from  group Id
	public List<Integer> getProjectIdsByGroupId(int groupId){
		
		String query = "SELECT DISTINCT e.numProjectId, p.strProjectName FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numGroupId = ?0 AND p.strProjectName != 'All Project' AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		//String query = "SELECT e.numProjectId FROM EmployeeRoleMstModel e WHERE e.dtEndDate =null AND e.numEmpId ="+empId;
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(groupId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<Integer> projectIds = new ArrayList<>();

	    if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0 && row[0] instanceof Integer) {
	                    projectIds.add((Integer) row[0]);
	                }
	            }
	        }
	    }
		return (projectIds != null && !projectIds.isEmpty()) ? projectIds : null;	
	}
	
	//Purpose - To get all project Types from  group Id
	public List<String> getProjectTimesheetTypesByGroupId(int groupId){
		
		String query = "SELECT DISTINCT p.timesheetType, e.numProjectId FROM EmployeeRoleMstModel e JOIN fetch ProjectMasterModel p ON p.numId = e.numProjectId WHERE (p.strProjectStatus = 'Ongoing' OR p.strProjectStatus = 'Under Approval') AND e.numGroupId = ?0 AND p.strProjectName != 'All Project' AND p.strProjectName != 'Sub-Program' AND (e.dtEndDate>=CURRENT_DATE OR e.dtEndDate IS NULL) AND p.numIsValid = 1 AND e.numIsValid = 1 ORDER BY e.numProjectId";
		//String query = "SELECT e.numProjectId FROM EmployeeRoleMstModel e WHERE e.dtEndDate =null AND e.numEmpId ="+empId;
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(groupId);
		List<Object> result = daoHelper.findByQuery(query, paraList);
		List<String> projectTypes = new ArrayList<String>();
		if (result != null && !result.isEmpty()) {
	        for (Object obj : result) {
	            if (obj instanceof Object[]) {
	                Object[] row = (Object[]) obj;
	                if (row.length > 0 && row[0] instanceof String) {
	                	projectTypes.add((String) row[0]);
	                }
	            }
	        }
	    }
		return (projectTypes != null && !projectTypes.isEmpty()) ? projectTypes : null;
	}
	
	//Purpose - To get all role Id from  user Id
	public long getRoleIdByUserId(int userId){
		
		//EmployeeRoleMstModel employeeRoleMstModel = new EmployeeRoleMstModel();
		String query = "select e.numRoleId from EmployeeRoleMstModel e where e.numEmpId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add((long)userId);
		
		List<Long> list = daoHelper.findByQuery(query, paraList);
		/*if(list.size()>0 && list != null){
			employeeRoleMstModel =list.get(0);
		}*/
		return (list == null || list.isEmpty())?0:list.get(0); 
		//return (list.isEmpty() || list == null)?0:employeeRoleMstModel.getNumRoleId();
	}
	
	//REPORTS
		//R1 - Finding Employee details under PL
	public List<Long> getEmployeeIdByProjectId(List<Integer> projectIds, long roleIdOfLoggedUser){
		//List<Long> empIds = new ArrayList<Long>();
		if (projectIds.isEmpty()) {
	        // Return an empty list as no employee IDs are provided
	        return new ArrayList<Long>();
	    }
		String query="";
		if(roleIdOfLoggedUser == 3){
			 query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numProjectId in ?0 and e.numRoleId  in (1,2)";
		}
		else if(roleIdOfLoggedUser==15){
			 query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numProjectId in ?0 and e.numRoleId in (1,2,3,4)";
		}
		else if(roleIdOfLoggedUser==4){
			 query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numProjectId in ?0 and e.numRoleId in (1,2,3)";
		}
		//String query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numProjectId in ?0 and e.numRoleId in (1, 2)";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(projectIds);
		List<Long> list = daoHelper.findByQuery(query, paraList);
		//System.out.println(list);
		/*for(int i=0; i<list.size(); i++){
			empIds.add(list.get(i).getNumEmpId());
		}*/
		return (!list.isEmpty())?list: new ArrayList<Long>();
		//return employeeRoleMstModel.getNumRoleId();
		}
	
	//MAILING
	public List<Long> getEmployeeIdsByRoleId(long roleId){
		String query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numRoleId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(roleId);
		List<Long> list = daoHelper.findByQuery(query, paraList);
		return (!list.isEmpty())?list: new ArrayList<Long>();
	}
	
	public List<Long> getEmployeeIdsByRoleIdAndGroupID(long roleId, int groupId){
		String query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numRoleId = ?0 and e.numGroupId = ?1";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(roleId);
		paraList.add(groupId);
		List<Long> list = daoHelper.findByQuery(query, paraList);
		return (!list.isEmpty())?list: new ArrayList<Long>();
	}
	
	public Long getHODsByEmployeeId(long numEmpId) {
        ArrayList<Object> paraList = new ArrayList<>();
        StringBuffer query = new StringBuffer("Select distinct emp.numEmpId from EmployeeRoleMstModel emp where emp.numProjectId in (Select distinct e.numProjectId from EmployeeRoleMstModel e where e.numIsValid=1 and  e.numRoleId in (1,2,3,4) and e.numEmpId=?0) ");
        query.append("and emp.numIsValid=1 and  emp.numRoleId in (15)");
        paraList.add(numEmpId);
        List<Long> list = daoHelper.findByQuery(query.toString(), paraList);
        if(list != null && !list.isEmpty()){
              return list.get(0);
        }
        else {
        	return (long) 0;
        }
	}


	public Long getPIsByEmployeeId(long numEmpId) {
        ArrayList<Object> paraList = new ArrayList<>();
        StringBuffer query = new StringBuffer("Select distinct emp.numEmpId from EmployeeRoleMstModel emp where emp.numProjectId in (Select distinct e.numProjectId from EmployeeRoleMstModel e where e.numIsValid=1 and  e.numRoleId in (1,2,3) and e.numEmpId=?0) ");
        query.append("and emp.numIsValid=1 and  emp.numRoleId in (4)");
        paraList.add(numEmpId);
        List<Long> list = daoHelper.findByQuery(query.toString(), paraList);
        if(list != null && !list.isEmpty()){
              return list.get(0);
        }
        else {
        	return (long) 0;
        }
	}
	
	public List<Long> getEmployeeIdsOfTMML(int groupId){
		String query = "select distinct e.numEmpId from EmployeeRoleMstModel e where e.numRoleId in (1,2) and e.numGroupId = ?0";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(groupId);
		List<Long> list = daoHelper.findByQuery(query, paraList);
		return (!list.isEmpty())?list: new ArrayList<Long>();
	}
	
	//Purpose - To get all role Id from  user Id
	public long getRoleIdByUserIdForMail(int userId){
		
		//EmployeeRoleMstModel employeeRoleMstModel = new EmployeeRoleMstModel();
		String query = "select e.numRoleId from EmployeeRoleMstModel e where e.numEmpId = ?0 order by e.numRoleId ASC";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add((long)userId);
		
		List<Long> list = daoHelper.findByQuery(query, paraList);
		/*if(list.size()>0 && list != null){
			employeeRoleMstModel =list.get(0);
		}*/
		return (list == null || list.isEmpty())?0:list.get(0); 
		//return (list.isEmpty() || list == null)?0:employeeRoleMstModel.getNumRoleId();
	}
}
