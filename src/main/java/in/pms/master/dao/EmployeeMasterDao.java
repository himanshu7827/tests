package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.EmployeeContractDetailDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeMasterDao {

	@Autowired
	DaoHelper daoHelper;

	public long saveUpdateEmployeeMaster(EmployeeMasterDomain employeeMasterDomain){
		employeeMasterDomain = daoHelper.merge(EmployeeMasterDomain.class, employeeMasterDomain);
		return employeeMasterDomain.getNumId();
	}

	public EmployeeMasterDomain getEmployeeMasterById(long numId){
		EmployeeMasterDomain employeeMasterDomain =null;
		String query = "from EmployeeMasterDomain where numId="+numId;
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			employeeMasterDomain =list.get(0);
		}
		return employeeMasterDomain;	
	}
	
	public List<EmployeeMasterDomain> getAllEmployeeMasterDomain(){
		String query = "from EmployeeMasterDomain where numIsValid<>2 and employmentStatus !='Relieved'";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<EmployeeMasterDomain> getAllActiveEmployeeMasterDomain(){
		String query = "from EmployeeMasterDomain where numIsValid=1 and employmentStatus !='Relieved' order by employeeName";
		return  daoHelper.findByQuery(query);	
	}	
	
	public List<EmployeeMasterDomain> getAllActiveEmployee(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int selectedRole = selectedEmployeeRole.getNumRoleId();
		int selectedGroup = selectedEmployeeRole.getNumGroupId();
	
		StringBuffer query = new StringBuffer("from EmployeeMasterDomain a where numIsValid=1 and employmentStatus !='Relieved' ");
		if(selectedRole != 6 & selectedRole != 8 && selectedRole != 9){
			query.append(" and groupMasterDomain.numId in ("+selectedGroup+")");
		}
		if(selectedRole == 5){
			query.append("order by a.designationMaster.hierarchy, employeeName");
		}else{
		query.append(" order by employeeName");
		}
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public EmployeeMasterDomain findByEmail(String email){
		EmployeeMasterDomain employeeMasterDomain = null;
		String query = "from EmployeeMasterDomain a join fetch a.groupMasterDomain join fetch a.empTypeMasterDomain join fetch a.designationMaster where a.numIsValid=1 and a.employmentStatus !='Relieved' and a.officeEmail='"+email+"'";
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			employeeMasterDomain = list.get(0);
					
		}
		return employeeMasterDomain;		
	}
	
	public EmployeeMasterDomain findAnyByEmail(String email){
		EmployeeMasterDomain employeeMasterDomain = null;
		String query = "from EmployeeMasterDomain a join fetch a.groupMasterDomain join fetch a.empTypeMasterDomain join fetch a.designationMaster where a.officeEmail='"+email+"' and a.employmentStatus !='Relieved'";
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			employeeMasterDomain = list.get(0);					
		}
		return employeeMasterDomain;		
	}
	
	

	public void delete(EmployeeMasterDomain employeeMasterDomain) {
				
	}
	
	public EmployeeMasterDomain getEmployeeDetails(long empId){
		String query = "from EmployeeMasterDomain a where a.numIsValid<>2 and employmentStatus !='Relieved' and a.numId="+empId;
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;	
	}
	
	public EmployeeMasterDomain getEmployeeByEmail(String email){
		String query = "from EmployeeMasterDomain a where a.numIsValid<>2 and employmentStatus !='Relieved' and  a.officeEmail='"+email+"'";
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;	
	}
	
	public long getSequence(){
		long seq=0;
		String query = " select nextval('TP_EMP_SEQ')";
		List<BigInteger> list=daoHelper.runNative(query);
	   if(list.size()>0){
		seq =list.get(0).longValue();
	   }
		return seq;
	}
	
	public EmployeeMasterDomain getOfficeEmailByName(String email){
		String query = "from EmployeeMasterDomain where  numIsValid!=2  and employmentStatus !='Relieved' and lower(officeEmail)=lower('"+email+"')" ;	
		List<EmployeeMasterDomain> empList = daoHelper.findByQuery(query);		
		if(empList.size()>0){
			return empList.get(0);
		}
		return null;
	}
	
	public List<Object[]> getEmployeeCountByEmployementType(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		StringBuilder  query = new StringBuilder ("select a.empTypeMasterDomain.strEmpTypeName,a.empTypeMasterDomain.hierarchy,count(a)"); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(null != assignedOrganisation){
			/*query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
			query.append(" and a.organisationid in ("+assignedOrganisation +")"); //Modified by devesh on 06-03-24 to fix employee count for regular
		}
		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append(" group by 1,2 order by 2");		
		return daoHelper.findByQuery(query.toString());
	}
	
	//added by devesh for deputed wise graph
public List<Object[]> getdeputedwiseEmployeeCount(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		StringBuffer query = new StringBuffer("Select numDeputedAt,count(a.numDeputedAt)");
		query.append(" from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(null != assignedOrganisation){
			query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}
		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append(" group by 1 order by 1");		
		return daoHelper.findByQuery(query.toString());
	}
// End of deputed graph
	
public List<Object[]> getEmployeeCountByGroupandDesignation(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		
		StringBuilder  query = new StringBuilder ("select a.groupMasterDomain.groupName,a.designationMaster.designationName,count(a) "); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(null != assignedOrganisation){
			query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}
		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		
		query.append(" group by 1,2 order by 1");
		
		return daoHelper.findByQuery(query.toString());
	}

	
	public List<Object[]> getEmployeeCountByGroupandDesignationSupport(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		
		StringBuilder  query = new StringBuilder ("select a.groupMasterDomain.groupName,a.designationMaster.designationName,count(a) "); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')");
		
		if(null != assignedOrganisation){
			query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}
		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		
		query.append("and a.groupMasterDomain.strGroupTypeFlag in('S')");
		query.append(" group by 1,2 order by 1");
		
		return daoHelper.findByQuery(query.toString());
	}
	
public List<Object[]> getEmployeeCountByGroupandDesignationTechnical(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		
		StringBuilder  query = new StringBuilder ("select a.groupMasterDomain.groupName,a.designationMaster.designationName,count(a) "); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')");
		
		if(null != assignedOrganisation){
			/*query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
			query.append(" and a.organisationid in ("+assignedOrganisation +")"); //Modified by devesh on 06-03-2024 to get center group data
		}
		
		//Added by devesh on 06-03-2024 to get only ED in center group
		query.append(" AND ((a.groupMasterDomain.numId <> 0) OR (a.groupMasterDomain.numId = 0 AND a.designationMaster.numId = 31))");
		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append("and a.groupMasterDomain.strGroupTypeFlag in('T')");
		//query.append("and a.groupMasterDomain.strGroupTypeFlag in('S')");
		query.append(" group by 1,2 order by 1");
		
		return daoHelper.findByQuery(query.toString());
	}
	public List<EmployeeMasterDomain> getThirdPartyById(long employeeTypeId){
		//StringBuilder query = new StringBuilder("select a.thirdPartyMasterDomain.numId, a.thirdPartyMasterDomain.strAgencyName");
		//query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.empTypeMasterDomain.numId ="+employeeTypeId);
		//return daoHelper.findByQuery(query.toString());
		/*EmployeeMasterDomain employeeMasterDomain = null;*/
		String query = "from EmployeeMasterDomain a join fetch a.thirdPartyMasterDomain b join fetch a.empTypeMasterDomain c where  a.numIsValid<>2 and a.employmentStatus !='Relieved' and c.numId='"+employeeTypeId+"'";
		return daoHelper.findByQuery(query);
		
		
	}
	public List<String> getdistinctEmpGender() {
		StringBuffer query = new StringBuffer(
				"select distinct  e.gender  from EmployeeMasterDomain e where  e.numIsValid=1 and e.employmentStatus !='Relieved'");

		List<String> empGenderList = daoHelper.findByQuery(query.toString());
		return empGenderList;
	}
	
	public long getCountByPostId(long id) {
		long count=0;
		StringBuilder query = new StringBuilder("select count(e) from EmployeeMasterDomain e where e.employmentStatus in ('Working','Deputation')  and e.numIsValid=1 and e.postTrackerMaster.numId="+id);
		List<Long> countList = daoHelper.findByQuery(query.toString());
		if(countList.size()>0){
			count =countList.get(0).longValue();	
		}
		return count;
	}
	
	public List<Object[]> getEmployeeCountByGroup(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuilder  query = new StringBuilder ("select count(a),a.groupMasterDomain.groupName "); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(assignedOrganisation != 0){
			query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}	
		query.append(" group by 2 order by 2");		
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<Object[]> getEmployeeCountByCategory(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuilder  query = new StringBuilder ("select a.category,count(a)"); 
		query.append(" from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(assignedOrganisation != 0){
			query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append(" group by a.category order by a.category");
		
		return daoHelper.findByQuery(query.toString());
	}
	
	///Query to find new joinings and resignations year-wise
	public Map<String, List<Long>> getYearWiseEmployeeCount(){
			Map<String, List<Long>> finalMap = new LinkedHashMap<String, List<Long>>();
			List<Long> list1 = new ArrayList<Long>();
			List<Long> list2 = new ArrayList<Long>();
			List<Long> list3 = new ArrayList<Long>();
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			/*String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();*/
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			
			Calendar calendar = Calendar.getInstance();
			//int currentMonth = calendar.get(Calendar.MONTH);
			
			int currentYear = calendar.get(Calendar.YEAR);
			int lastYear = calendar.get(Calendar.YEAR)-1;
			int secondLastYear = calendar.get(Calendar.YEAR)-2;

			String strStart = "01/01/";
			String strEnd = "31/12/";
			
			String year1 ="";
			String year2 ="";
			String year3 ="";
			
			Date year1Start = new Date();
			Date year1End = new Date();
			
			Date year2Start = new Date();
			Date year2End = new Date();
			
			Date year3Start = new Date();
			Date year3End = new Date();
		
				
				try {
					year1 = ""+currentYear;
					year1Start = DateUtils.dateStrToDate(strStart+currentYear);
					year1End = new Date();
					
					year2 = ""+lastYear;
					year2Start = DateUtils.dateStrToDate(strStart+lastYear);
					year2End = DateUtils.dateStrToDate(strEnd+lastYear);
					
					year3 = ""+secondLastYear;
					year3Start = DateUtils.dateStrToDate(strStart+secondLastYear);
					year3End = DateUtils.dateStrToDate(strEnd+secondLastYear);
					
				} catch (ParseException e) {				
					e.printStackTrace();
				}
		
			
			StringBuffer queryA = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryA.append(" where a.numIsValid=1  and a.dateOfJoining between '"+year1Start +"' and '"+year1End+"'");
			
			StringBuffer queryB = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryB.append(" where a.numIsValid=1  and a.employmentStatus!='Working' and ((a.dateOfRelease BETWEEN '"+year1Start+"' AND '"+year1End+"') OR ( a.dateOfResignation BETWEEN '"+year1Start+"' AND '"+year1End+"'))");
			
			StringBuffer queryA1 = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryA1.append(" where a.numIsValid=1  and a.dateOfJoining between '"+year2Start +"' and '"+year2End+"'");
			
			StringBuffer queryB1 = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryB1.append(" where a.numIsValid=1  and a.employmentStatus!='Working' and ((a.dateOfRelease BETWEEN '"+year2Start+"' AND '"+year2End+"') OR ( a.dateOfResignation BETWEEN '"+year2Start+"' AND '"+year2End+"'))");
			
			StringBuffer queryA2 = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryA2.append(" where a.numIsValid=1  and a.dateOfJoining between '"+year3Start +"' and '"+year3End+"'");
			
			StringBuffer queryB2 = new StringBuffer("select count(a) from EmployeeMasterDomain a");
			queryB2.append(" where a.numIsValid=1  and a.employmentStatus!='Working' and ((a .dateOfRelease BETWEEN '"+year3Start+"' AND '"+year3End+"') OR ( a .dateOfResignation BETWEEN '"+year3Start+"' AND '"+year3End+"'))");
			
			
			if(assignedOrganisation != 0){
				queryA.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				queryA1.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				queryA2.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				queryB.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				queryB1.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				queryB2.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				
			}
			
			if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
				queryA.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
				queryA1.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
				queryA2.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
				queryB.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
				queryB1.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
				queryB2.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
			}
			
					
			
					
			list1= daoHelper.findByQuery(queryA.toString());
			List<Long> listB = daoHelper.findByQuery(queryB.toString());
			if(listB.size()>0){
				list1.add(listB.get(0));
			}
			
			list2=daoHelper.findByQuery(queryA1.toString());
			List<Long> list1B = daoHelper.findByQuery(queryB1.toString());
			if(list1B.size()>0){
				list2.add(list1B.get(0));
			}
			list3=daoHelper.findByQuery(queryA2.toString());
			
			List<Long> list2B =daoHelper.findByQuery(queryB2.toString());
			
			if(list2B.size()>0){
				list3.add(list2B.get(0));
			}
			
			
			
			finalMap.put(year3, list3);
			finalMap.put(year2, list2);
			finalMap.put(year1, list1);
			
			
			return finalMap;
		}

public List<Object[]> getEmployeesByGroupByEmployementType(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		
		//StringBuilder  query = new StringBuilder ("select a.employeeName,a.groupMasterDomain.groupName,a.empTypeMasterDomain.strEmpTypeName, a.dateOfJoining,a.empTypeMasterDomain.hierarchy,a.designationMaster.designationName");
		StringBuilder  query = new StringBuilder ("select a.employeeName,a.groupMasterDomain.groupName,a.empTypeMasterDomain.strEmpTypeName, a.dateOfJoining,a.empTypeMasterDomain.hierarchy,a.designationMaster.designationName,a.numDeputedAt,");//updated by devesh on 14/6/23 for deputed at column
		query.append(" a.designationMaster.designationorder from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
		if(null != assignedOrganisation){
			/*query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
			query.append(" and a.organisationid in ("+assignedOrganisation +")"); //Modified by devesh on 06-03-24 to get all employees data
		}		
		if(null != assignedGroups && !(assignedGroups.equals("0") || assignedGroups.equals("8"))){
			query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		//query.append(" group by 1, 2,3,4,5,6 ");
		query.append(" group by 1, 2,3,4,5,6,7,8 ");//updated by devesh on 15/6/23 for deputed at value
		query.append(" order by a.designationMaster.designationorder"); //Modified by devesh on 05-06-24 to correct hierarchy
		
		return daoHelper.findByQuery(query.toString());
	}

public List<EmployeeMasterDomain> getEmpbyDesignationForGroup(String groupName, String strDesignation) {
	String query = "from EmployeeMasterDomain a where a.numIsValid=1 and  a.designationMaster.designationName='"+strDesignation+"' and a.groupMasterDomain.groupName='"+groupName+"' and  a.employmentStatus not in ('Terminate','Relieved')";
	List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
	return list;
}

public List<Object[]> getAllActiveEmployeeWithCount(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	String assignedGroups = userInfo.getAssignedGroups();
	//Bhavesh(06-7-23)(added num_deputed_at )
		StringBuilder  query = new StringBuilder ("select e.emp_id,e.str_emp_name,to_char(e.dt_joining, 'DD/MM/YYYY') as doj, (SELECT count(*) FROM pms_employee_role_mst WHERE num_emp_id = e.emp_id ) as count ,f.designation_name,e.num_deputed_at from pms_employee_master e, pms_designation_master f where f.num_designation_id=e.num_designation_id and e.str_employment_status != 'Relieved' and e.num_isvalid=1");
		if(null != assignedGroups && !assignedGroups.equals("0")){
			query.append("and e.group_id_fk in ("+assignedGroups+")");
		}
		query.append(" order by e.str_emp_name");
		return  daoHelper.runNative(query.toString());	
	}


public List<EmployeeContractDetailDomain> getContractDetails(String date){
	StringBuilder  query = new StringBuilder ("Select a from EmployeeContractDetailDomain a  join fetch a.employeeMasterDomain b where to_char(a.endDate,'MM/YYYY')= '"+date+"' and b.employmentStatus !='Relieved'");
	return daoHelper.findByQuery(query.toString());
}
public List<EmployeeMasterDomain> getAllEmployeeGroupID(Long encGroupId){
	String query = "from EmployeeMasterDomain where numIsValid=1 and employmentStatus !='Relieved' and groupMasterDomain.numId="+encGroupId+" order by employeeName ";
	return  daoHelper.findByQuery(query);	
}

public List<Object[]> loadEmployeeDetails(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuffer query = new StringBuffer("select test.utilization, test.str_emp_name,test. str_mobile_number , test.dt_joining,test.str_office_email,test.emp_id,test.str_group_name,test.designation_name from (SELECT b.str_emp_name,b.str_mobile_number,b.dt_joining,b.str_office_email,b .emp_id,C .str_group_name,d.designation_name,(select sum(num_utilization) from pms_employee_role_mst where num_isvalid=1 and num_emp_id=b.emp_id and role_id!="+roleId+" and (dt_enddate is null or dt_enddate >= CURRENT_DATE) and dt_startdate<= current_date) utilization FROM pms_employee_master b, pms_group_master C,pms_designation_master d WHERE b.group_id_fk= c.group_id AND b.num_designation_id=d.num_designation_id AND C .group_type_flag = 'T' and b.str_employment_status='Working' and b.num_isvalid=1");
	
	if(roleId == 5){
		query.append("and c.group_id ="+assignedGroups);
	}
	query.append(" ORDER BY 8,1) test where test.utilization is null");
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
		
}

public List<Object[]> loadJoinedEmployeeDetails(String startRange, String endRange){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,c.str_group_name,A.dt_joining,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND A .dt_joining between '"+startDate+ "' and '"+endDate+ "' AND A .num_isvalid = 1 ");
	
	if(roleId == 5){
		query.append("and C.group_id ="+assignedGroups);
	}
	query.append("ORDER BY A .dt_joining DESC ,2");
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
		
}

public List<Object[]> loadResignedEmployeeDetails(String startRange, String endRange){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	/*StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,COALESCE(A .dt_release_date,A.dt_resignation_date),A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND ((A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') OR ( A .dt_resignation_date BETWEEN '"+startDate+"' AND '"+endDate+"')) AND A .str_employment_status != 'Working' AND A .num_isvalid = 1 ");*/
	//Modified by devesh on 19/10/23 to modify condition of Employee status to Relieved
	StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,A .dt_release_date,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND (A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1 ");
	if(roleId == 5){
		query.append("and C.group_id ="+assignedGroups);
	}
	query.append("UNION SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,d.dt_end,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C, pms_employee_contract_detail_mst d WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A.emp_id=d.num_employee_id_fk AND d.num_isvalid=1 AND A.dt_release_date is null AND (d .dt_end BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status != 'Working' AND A .num_isvalid = 1 ");
	//End of comment
	if(roleId == 5){
		query.append("and C.group_id ="+assignedGroups);
	}
	
	query.append(" ORDER BY  5 DESC,2");
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}
public List<Object[]> loadResignedEmployeeDetailsCustomized(String startRange, String endRange){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	/*StringBuffer query = new StringBuffer("SELECT C.str_group_name,b.designation_name,Count(*) FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND ((A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') OR ( A .dt_resignation_date BETWEEN '"+startDate+"' AND '"+endDate+"')) AND A .str_employment_status != 'Working' AND A .num_isvalid = 1  GROUP BY 1,2");*/
	//Modified by devesh on 19/10/23 to modify condition of Employee status to Relieved
	StringBuffer query = new StringBuffer("SELECT C.str_group_name,b.designation_name,Count(*) FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND (A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1  GROUP BY 1,2");
	if(roleId == 5){
		query.append("and C.group_id ="+assignedGroups);
	}
	query.append("UNION SELECT C.str_group_name,b.designation_name,Count(*) FROM pms_employee_master A,pms_designation_master b,pms_group_master C,pms_employee_contract_detail_mst d WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A.emp_id=d.num_employee_id_fk AND d.num_isvalid=1 AND A.dt_release_date is null  AND (d .dt_end BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1  GROUP BY 1,2");
	//End of comment
	if(roleId == 5){
		query.append("and C.group_id ="+assignedGroups);
	}
	
	query.append(" ORDER BY 1");
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}
public List<Object[]> employeeDetailsWithInvolvements(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel employeeRoleMasterModel =userInfo.getSelectedEmployeeRole();
	StringBuffer query = new StringBuffer("select b.str_group_name,a.emp_id,a.str_emp_name,c.designation_name,");
	query.append(" a.num_deputed_at,");//added by devesh on 09/06/23 for deputed at column
	query.append(" d.str_emp_type_name,a.str_office_email,case when b.num_show_in_projects=1 THEN");
	query.append(" (select string_agg('<b>'|| f.str_project_name || '</b> As '|| c.str_role_name ||' Since ' ||");
	query.append(" (case when e.dt_enddate is null THEN to_char(e.dt_startdate,'dd-MM-yyyy') ELSE");
	query.append(" to_char(e.dt_startdate,'dd-MM-yyyy') || ' till '|| to_char(e.dt_enddate,'dd-MM-yyyy') end) ||', with ' ||e.num_utilization ||' % involvement' ,'<br/>') involvement");
	query.append(" from pms_employee_role_mst e,pms_role_master c ,pms_employee_master d,pms_project_master f");
	query.append(" where e.num_isvalid=1 and (e.dt_enddate is null or e.dt_enddate>=current_date)and e.dt_startdate <= CURRENT_DATE");
	query.append(" and e.role_id= c.role_id  and e.num_emp_id= d.emp_id	and f.num_project_id= e.num_project_id 	and e.num_emp_id=a.emp_id");
	query.append(" ) ELSE 	null 	end 	 from pms_employee_master a,pms_group_master b,");
	query.append(" pms_designation_master c,pms_employee_type_master d	 where a.num_isvalid=1"); 
	query.append(" and a.group_id_fk = b.group_id and a.num_designation_id= c.num_designation_id");
	query.append(" and a.num_emp_type_id= d.num_emp_type_id	and a.str_employment_status ='Working'");
	query.append(" and (b.num_isvalid=1 or a.num_designation_id=31 )"); // Added by devesh on 04-06-24 to correct data in employee table
	if(employeeRoleMasterModel.getNumRoleId() == 5){
		query.append(" and  b.group_id="+employeeRoleMasterModel.getNumGroupId());
	}
	/*query.append(" order by 1,d.num_hierarchy,c.num_hierarchy,3");*/
	query.append(" order by c.num_order,3"); //Modified by devesh on 04-06-24 to correct hierarchy
	
	return daoHelper.runNative(query.toString());
	}

public List<Object[]> loadJoinedEmployee(String startRange, String endRange,int orgId){

	
	StringBuffer query = new StringBuffer("SELECT A.group_id_fk,C .str_group_name,COUNT (*) FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND A .dt_joining BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy') AND A .num_isvalid = 1 AND A.organisation_id = "+orgId+" GROUP BY 1,2 ");
	
	
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}

public List<Object[]> loadResinedEmployees(String startRange, String endRange,int orgId){
	
	/*StringBuffer query = new StringBuffer("SELECT A.group_id_fk,C .str_group_name,COUNT (*) FROM pms_employee_master A, pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND ((A .dt_release_date BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy')) OR (A .dt_resignation_date BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy'))) AND A .str_employment_status != 'Working' AND A .num_isvalid = 1 AND A.organisation_id = "+orgId+" GROUP BY 1,2 ");*/
	//Modified by devesh on 19-10-23 to fix count of relieved employees
	StringBuffer query = new StringBuffer("SELECT A.group_id_fk,C .str_group_name,COUNT (*) FROM pms_employee_master A, pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND (A .dt_release_date BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy')) AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1 GROUP BY 1,2 ");
	query.append("UNION SELECT A.group_id_fk,C .str_group_name,COUNT (*) FROM pms_employee_master A, pms_designation_master b,pms_group_master C,pms_employee_contract_detail_mst d WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A.emp_id=d.num_employee_id_fk AND d.num_isvalid=1 AND A.dt_release_date is null AND (d .dt_end BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy')) AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1 GROUP BY 1,2 ");
	//End of query
	
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}

public List<Object[]> loadEmployeesCount(int orgId){
	
	
	StringBuffer query = new StringBuffer("SELECT A .group_id_fk, C .str_group_name,COUNT (*) FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_isvalid = 1 AND A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A .str_employment_status='Working' AND A .organisation_id = "+orgId+" and C.num_show_in_projects=1  GROUP BY 1, 2");
	
	
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}

public List<Object[]> getNewJoinedEmployeeByGroupDetails(String startRange, String endRange,long groupId){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
	
	
	StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,c.str_group_name,A.dt_joining,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND A .dt_joining between '"+startDate+ "' and '"+endDate+ "' AND A .num_isvalid = 1 ");
	
	
		query.append("and C.group_id ="+groupId);
	
	query.append("ORDER BY A .dt_joining DESC ,2");
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
		
}

public List<Object[]> getResignedEmployeeByGroupDetails(String startRange, String endRange,long groupId){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}

	/*StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,COALESCE(A .dt_release_date,A.dt_resignation_date),A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND ((A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') OR ( A .dt_resignation_date BETWEEN '"+startDate+"' AND '"+endDate+"')) AND A .str_employment_status != 'Working' AND A .num_isvalid = 1 ");*/
	//Modified by devesh on 19/10/23 to modify condition of Employee status to Relieved
	StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,A .dt_release_date,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id  AND (A .dt_release_date BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status = 'Relieved' AND A .num_isvalid = 1 ");
	query.append("and C.group_id ="+groupId);
	query.append("UNION SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,d.dt_end,A .str_employment_status FROM pms_employee_master A,pms_designation_master b,pms_group_master C, pms_employee_contract_detail_mst d WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A.emp_id=d.num_employee_id_fk AND d.num_isvalid=1 AND A.dt_release_date is null AND (d .dt_end BETWEEN '"+startDate+"' AND '"+endDate+"') AND A .str_employment_status != 'Working' AND A .num_isvalid = 1");
	//End of comment
	
		query.append("and C.group_id ="+groupId);
	
	
	query.append(" ORDER BY  5 DESC,2");
	List<Object[]> obj = daoHelper.runNative(query.toString()); 
	return obj;	
		
}

public List<Object[]> getEmployeeCountByEmployementTypeNew(){
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
	String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	StringBuilder  query = new StringBuilder ("select a.empTypeMasterDomain.strEmpTypeName,a.empTypeMasterDomain.hierarchy,count(a)"); 
	query.append(" from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
	if(null != assignedOrganisation){
		query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
	}
	
	if(roleId != 6 && roleId != 8 && roleId != 9){
		query.append(" and a.groupMasterDomain.numId in ("+assignedGroups+")");
	}
	query.append(" group by 1,2 order by 2");		
	return daoHelper.findByQuery(query.toString());
}


public List<Object[]> employeeDetailsWithInvolvements(long groupId){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel employeeRoleMasterModel =userInfo.getSelectedEmployeeRole();
	StringBuffer query = new StringBuffer("select b.str_group_name,a.emp_id,a.str_emp_name,c.designation_name,");
	query.append(" d.str_emp_type_name,case when b.num_show_in_projects=1 THEN");
	query.append(" (select string_agg('<b>'|| f.str_project_name || '</b> As '|| c.str_role_name ||' Since ' ||");
	query.append(" (case when e.dt_enddate is null THEN to_char(e.dt_startdate,'dd-MM-yyyy') ELSE");
	query.append(" to_char(e.dt_startdate,'dd-MM-yyyy') || ' till '|| to_char(e.dt_enddate,'dd-MM-yyyy') end) ||', with ' ||e.num_utilization ||' % involvement' ,'<br/>') involvement");
	query.append(" from pms_employee_role_mst e,pms_role_master c ,pms_employee_master d,pms_project_master f");
	query.append(" where e.num_isvalid=1 and (e.dt_enddate is null or e.dt_enddate>=current_date)and e.dt_startdate <= CURRENT_DATE");
	query.append(" and e.role_id= c.role_id  and e.num_emp_id= d.emp_id	and f.num_project_id= e.num_project_id 	and e.num_emp_id=a.emp_id");
	query.append(" ) ELSE 	null 	end 	 from pms_employee_master a,pms_group_master b,");
	query.append(" pms_designation_master c,pms_employee_type_master d	 where a.num_isvalid=1"); 
	query.append(" and a.group_id_fk = b.group_id and a.num_designation_id= c.num_designation_id");
	query.append(" and a.num_emp_type_id= d.num_emp_type_id	and a.str_employment_status ='Working'");
	
		query.append(" and  b.group_id="+groupId);
	
	query.append(" order by 1,d.num_hierarchy,c.num_hierarchy,3");	
	
	return daoHelper.runNative(query.toString());
	}

public List<EmployeeMasterDomain> searchEmpDataByName(String employeeName){

	
	StringBuffer query = new StringBuffer("SELECT * FROM pms_employee_master WHERE  str_employment_status!='Relieved' and num_isvalid!=2 and str_emp_name iLIKE '%"+employeeName+"%'");
    
    List<EmployeeMasterDomain> newsLetterMaster = daoHelper.runNative(query.toString(), EmployeeMasterDomain.class);	
	return newsLetterMaster;
	
}
public EmployeeMasterDomain getRelievedEmployeeDetails(long empId){
	String query = "from EmployeeMasterDomain a where a.numIsValid<>2 and employmentStatus ='Relieved' and a.numId="+empId;
	List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
	if(list.size()>0){
		return list.get(0);
	}
	return null;	
}

public EmployeeMasterDomain getRelievedEmployeeByEmail(String email){
	String query = "from EmployeeMasterDomain a where a.numIsValid<>2 and employmentStatus ='Relieved' and  a.officeEmail='"+email+"'";
	List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
	if(list.size()>0){
		return list.get(0);
	}
	return null;	
}

public List<EmployeeMasterDomain> searchRelievedEmpDataByName(String employeeName){

	
	StringBuffer query = new StringBuffer("SELECT * FROM pms_employee_master WHERE  str_employment_status='Relieved' and num_isvalid!=2 and str_emp_name iLIKE '%"+employeeName+"%'");
    
    List<EmployeeMasterDomain> newsLetterMaster = daoHelper.runNative(query.toString(), EmployeeMasterDomain.class);	
	return newsLetterMaster;
	
}

public List<Object[]> loadUnderUtilizationEmployee(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuffer query = new StringBuffer("select test.utilization, test.str_emp_name,test. str_mobile_number , test.dt_joining,test.str_office_email,test.emp_id,test.str_group_name,test.designation_name from (SELECT b.str_emp_name,b.str_mobile_number,b.dt_joining,b.str_office_email,b .emp_id,C .str_group_name,d.designation_name,(select sum(num_utilization) from pms_employee_role_mst where num_isvalid=1 and num_emp_id=b.emp_id and role_id!="+roleId+" and (dt_enddate is null or dt_enddate >= CURRENT_DATE) and dt_startdate<= current_date) utilization FROM pms_employee_master b, pms_group_master C,pms_designation_master d WHERE b.group_id_fk= c.group_id AND b.num_designation_id=d.num_designation_id AND C .group_type_flag = 'T' and b.str_employment_status='Working' ");
	
	if(roleId == 5){
		query.append("and c.group_id ="+assignedGroups);
	}
	query.append(" ORDER BY 8,1) test where test.utilization <100");
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
		
}

	public List<EmployeeMasterDomain> getEmployeesByEmploymentType(String employmentTypeName){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		
		int selectedRole = selectedEmployeeRole.getNumRoleId();
		
		if(selectedRole == 5 || selectedRole == 6 || selectedRole == 7 || selectedRole==8 || selectedRole ==9){
			StringBuilder query = new StringBuilder("from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus='Working'");
			query.append(" and a.empTypeMasterDomain.strEmpTypeName=? and a.empTypeMasterDomain.numIsValid=1");
			if(selectedRole==5){
				query.append(" and a.groupMasterDomain.numId="+selectedEmployeeRole.getNumGroupId());
			}
			query.append(" order by a.empTypeMasterDomain.hierarchy,a.designationMaster.hierarchy, a.employeeName");
			ArrayList<Object> paraList = new ArrayList<Object>();
			paraList.add(employmentTypeName);
			
			return daoHelper.findByQuery(query.toString(), paraList);
		}	
		return null;
	}
	
	public List<Object[]> employeeDetailsWithInvolvementsGroupWise(String groupName){
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel employeeRoleMasterModel =userInfo.getSelectedEmployeeRole();*/
		StringBuffer query = new StringBuffer("select b.str_group_name,a.emp_id,a.str_emp_name,c.designation_name,");
		query.append(" d.str_emp_type_name,case when b.num_show_in_projects=1 THEN");
		query.append(" (select string_agg('<b>'|| f.str_project_name || '</b> As '|| c.str_role_name ||' Since ' ||");
		query.append(" (case when e.dt_enddate is null THEN to_char(e.dt_startdate,'dd-MM-yyyy') ELSE");
		query.append(" to_char(e.dt_startdate,'dd-MM-yyyy') || ' till '|| to_char(e.dt_enddate,'dd-MM-yyyy') end) ||', with ' ||e.num_utilization ||' % involvement' ,'<br/>') involvement");
		query.append(" from pms_employee_role_mst e,pms_role_master c ,pms_employee_master d,pms_project_master f");
		query.append(" where e.num_isvalid=1 and (e.dt_enddate is null or e.dt_enddate>=current_date)and e.dt_startdate <= CURRENT_DATE");
		query.append(" and e.role_id= c.role_id  and e.num_emp_id= d.emp_id	and f.num_project_id= e.num_project_id 	and e.num_emp_id=a.emp_id");
		query.append(" ) ELSE 	null 	end 	 from pms_employee_master a,pms_group_master b,");
		query.append(" pms_designation_master c,pms_employee_type_master d	 where a.num_isvalid=1"); 
		query.append(" and a.group_id_fk = b.group_id and a.num_designation_id= c.num_designation_id");
		query.append(" and a.num_emp_type_id= d.num_emp_type_id	and a.str_employment_status ='Working'");
		
			query.append(" and  b.str_group_name='"+groupName+"'");
	
		query.append(" order by 1,d.num_hierarchy,c.num_hierarchy,3");	
		
		return daoHelper.runNative(query.toString());
		}
	
public List<EmployeeMasterDomain> getEmployeeCountByCategoryName(String category){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	
	StringBuilder  query = new StringBuilder ("from EmployeeMasterDomain a where a.numIsValid =1 and a.category=? and a.employmentStatus not in ('Terminate','Relieved') ");
	if(assignedOrganisation != 0){
		query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		query.append(" order by a.groupMasterDomain.groupName,a.empTypeMasterDomain.hierarchy,a.designationMaster.hierarchy, a.employeeName");	
	}	
	ArrayList<Object> paraList = new ArrayList<Object>();
	paraList.add(category);
	return  daoHelper.findByQuery(query.toString(),paraList);
	}




public List<Object[]> getEmployeeCountByGroupwise(String groupName){
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
	/*String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();*/
	StringBuilder  query = new StringBuilder ("select a.empTypeMasterDomain.strEmpTypeName,a.empTypeMasterDomain.hierarchy,count(a)"); 
	query.append(" from EmployeeMasterDomain a where a.numIsValid =1 and a.employmentStatus not in ('Terminate','Relieved')");
	if(null != assignedOrganisation){
		query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
	}
	
	
		query.append(" and a.groupMasterDomain.groupName in ('"+groupName+"')");
	
	query.append(" group by 1,2 order by 2");		
	return daoHelper.findByQuery(query.toString());
}

public List<Object[]> getEmployeeCountBySingleGroupandDesignation(String group){
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
	/*String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();*/
	
	
	StringBuilder  query = new StringBuilder ("select a.designationMaster.designationName,count(a),a.designationMaster.hierarchy "); 
	query.append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')");
	if(null != assignedOrganisation){
		query.append(" and a.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
	}
	
	
		query.append(" and a.groupMasterDomain.groupName in ('"+group+"')");
	    
	
	query.append(" group by 1,3 order by a.designationMaster.hierarchy");
	
	return daoHelper.findByQuery(query.toString());
}

 public List<String> getDesignationsOfActiveEmployees(String groupType){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();		
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	if(roleId == 6 || roleId == 7 || roleId == 8 || roleId == 9){//Updated by devesh on 21/6/23 for adding designation group table for gcfinance	
		/*
		 * StringBuilder query = new StringBuilder
		 * ("select distinct a.designationMaster.designationName "); query.
		 * append(" from EmployeeMasterDomain a where a.numIsValid=1 and a.employmentStatus not in ('Terminate','Relieved')"
		 * );
		 * query.append(" and a.groupMasterDomain.strGroupTypeFlag in ('"+groupType+"')"
		 * ); query.append(" order by a.designationMaster.hierarchy");
		 * 
		 * return daoHelper.findByQuery(query.toString());
		 */
		
		StringBuilder query = new StringBuilder("select pdm.designation_name  from pms_designation_master pdm where  num_designation_id in (");
		query.append(" select distinct num_designation_id from pms_employee_master a, pms_group_master b"); 
		query.append(" where a.num_isvalid =1 and a.str_employment_status ='Working'");
		//query.append("	and a.group_id_fk = b.group_id and b.group_type_flag = '"+groupType+"') order by pdm.num_hierarchy ");
		//Updated by devesh on 21/6/23 for adding designation group table for gcfinance
		query.append("	and a.group_id_fk = b.group_id and b.group_type_flag = '"+groupType+"') order by pdm.num_order ");
		return daoHelper.runNative(query.toString());
	}else{
		return null;
	}
		
 }
 
 public List<Object[]> loadRejoinRelievedEmployeeDetails(String startRange, String endRange,String Status){
		Date startDate=null;
		Date endDate=null;

		try {
			 startDate = DateUtils.dateStrToDate(startRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		if(null != endRange && !endRange.equals("")){
			try {
				endDate = DateUtils.dateStrToDate(endRange);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			endDate = new Date();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,A .dt_joining,A .str_employment_status,A.dt_emp_dob FROM pms_employee_master A, pms_designation_master b, pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A.str_employment_status IN ('"+Status+"') AND A .dt_joining BETWEEN '"+startDate+ "' AND '"+endDate+ "' AND A .num_isvalid = 1 AND (upper(A .str_emp_name), to_char(A.dt_emp_dob,'dd-mm-yyyy')) IN ( SELECT upper(emp.str_emp_name), to_char(emp.dt_emp_dob,'dd-mm-yyyy') FROM 	pms_employee_master emp WHERE emp.num_isvalid = 1 AND emp.str_employment_status IN ('Relieved')) " );
		
		if(roleId == 5){
			query.append(" and C.group_id ="+assignedGroups );
		}
		query.append(" ORDER BY A .dt_joining DESC ,2");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
 
 public List<Object[]> loadRejoinEmployeeDetails(String startRange, String endRange){
		Date startDate=null;
		Date endDate=null;

		try {
			 startDate = DateUtils.dateStrToDate(startRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		if(null != endRange && !endRange.equals("")){
			try {
				endDate = DateUtils.dateStrToDate(endRange);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			endDate = new Date();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,A .dt_joining,A .str_employment_status,A.dt_emp_dob FROM pms_employee_master A, pms_designation_master b, pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A .dt_joining BETWEEN '"+startDate+ "' AND '"+endDate+ "' AND A .num_isvalid = 1 AND (upper(A .str_emp_name), to_char(A.dt_emp_dob,'dd-mm-yyyy')) IN ( SELECT upper(emp.str_emp_name), to_char(emp.dt_emp_dob,'dd-mm-yyyy') FROM 	pms_employee_master emp WHERE emp.num_isvalid = 1 AND emp.str_employment_status IN ('Relieved')) " );
		
		if(roleId == 5){
			query.append(" and C.group_id ="+assignedGroups );
		}
		query.append(" ORDER BY A .dt_joining DESC ,2");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}

public List<Object[]> getPreviousHistoryOfEmp(String empName, String dob){
		Date dateOfBirth=null;
		

		try {
			dateOfBirth = DateUtils.dateStrToDate(dob);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}

		
		StringBuffer query = new StringBuffer("select b.designation_name,C .str_group_name,A .dt_joining, A.dt_release_date,A.emp_id from pms_employee_master A, pms_designation_master b,pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id and A .str_emp_name='"+empName+"' and A.dt_emp_dob='"+dateOfBirth+"' and dt_release_date IS NOT NULL  order by A.emp_id  " );
		
		
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}

public List<Object[]> getCountRejoinEmployeeDetails(String startRange, String endRange){
	Date startDate=null;
	Date endDate=null;

	try {
		 startDate = DateUtils.dateStrToDate(startRange);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	if(null != endRange && !endRange.equals("")){
		try {
			endDate = DateUtils.dateStrToDate(endRange);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		endDate = new Date();
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuffer query = new StringBuffer("SELECT A .emp_id,A .str_emp_name,b.designation_name,C .str_group_name,A .dt_joining,A .str_employment_status,A.dt_emp_dob FROM pms_employee_master A, pms_designation_master b, pms_group_master C WHERE A .num_designation_id = b.num_designation_id AND A .group_id_fk = C .group_id AND A .dt_joining BETWEEN '"+startDate+ "' AND '"+endDate+ "' AND A .num_isvalid = 1 AND (upper(A .str_emp_name), to_char(A.dt_emp_dob,'dd-mm-yyyy')) IN ( SELECT upper(emp.str_emp_name), to_char(emp.dt_emp_dob,'dd-mm-yyyy') FROM 	pms_employee_master emp WHERE emp.num_isvalid = 1 AND emp.str_employment_status IN ('Relieved')) " );
	
	if(roleId == 5){
		query.append(" and C.group_id ="+assignedGroups );
	}
	query.append(" ORDER BY A .dt_joining DESC ,2");
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
		
}
}
