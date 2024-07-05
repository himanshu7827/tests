package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.transaction.domain.ManpowerUtilization;
import in.pms.transaction.domain.ManpowerUtilizationDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ManpowerUtilizationDao {

	@Autowired
	DaoHelper daoHelper;
	
	public ManpowerUtilization mergeManpowerUtilization(ManpowerUtilization manpowerUtilization){
		return daoHelper.merge(ManpowerUtilization.class, manpowerUtilization);
	}
	
	public ManpowerUtilizationDetails mergeManpowerUtilizationDetails(ManpowerUtilizationDetails manpowerUtilization){
		return daoHelper.merge(ManpowerUtilizationDetails.class, manpowerUtilization);
	}
	
	public ManpowerUtilization getManpowerUtilization(long numId){
		StringBuilder query = new StringBuilder("from ManpowerUtilization a join fetch a.utilizationDetails b where a.numIsValid=1 and b.numIsValid=1 and a.numId="+numId);
		List<ManpowerUtilization> list = daoHelper.findByQuery(query.toString());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Object[]> getUtilizationForEmployee(long employeeId, int month,int year,String monthEndDate){
	/*	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		String assignedProject=userInfo.get();*/
		List<Object[]> dataList = new ArrayList<Object[]>();
		StringBuilder query = new StringBuilder("select b.num_project_id_fk,c.str_project_name,b.num_utilization_dtl_id,b.num_utilization,b.num_salary_in_project,a.num_manpower_utilization_id from pms_manpower_utilization a,");
		query.append(" pms_manpower_utilization_dtl b,pms_project_master c where a.num_manpower_utilization_id=b.num_utilization_id_fk and a.num_month="+month);
		query.append(" and a.num_year="+year+" and b.num_project_id_fk= c.num_project_id and a.num_employee_id_fk="+employeeId);
		/*if(null != assignedOrganisation){
			query.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(null != assignedGroups && !assignedGroups.equals("0")){
			query.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
		}
		if(null != assignedProject){
			query.append(" and a.projectMasterDomain.numId in ("+assignedProject+")");
		}*/
		dataList = daoHelper.runNative(query.toString());
		if(dataList.size()>0){
			return dataList;
		}else{
			StringBuilder query1 = new StringBuilder(" select distinct a.num_project_id,b.str_project_name,0 as utilization_id,0 as utilization,0 salary,0 from pms_employee_role_mst a,pms_project_master b where  a.num_emp_id ="+employeeId);
			query1.append(" and a.num_project_id= b.num_project_id and (a.dt_enddate is null and   (to_date('"+monthEndDate+"','dd/mm/yyyy') >=a.dt_startdate) ) ");	
			
			query1.append(" union select num_project_id,str_project_name,0 as utilization_id,0 as utilization,0 salary,0 from pms_project_master  where num_project_id=40");
			
			return daoHelper.runNative(query1.toString());
		}
	//	query.append(" UNION ");
		
	}
	
	//Query for Expenditure 
	public List<Double> getExpenditureFromManpower(Date date){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		String assignedProjects = userInfo.getAssignedProjects();
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(date);
		int year = startDate.get(Calendar.YEAR);
		int month = startDate.get(Calendar.MONTH)+1;
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
	
		StringBuffer query = new StringBuffer("select sum(m.salaryInProject) from ManpowerUtilizationDetails m");
		query.append(" where m.numIsValid=1 and m.manpowerUtilization.month >= '"+month+ "' and m.manpowerUtilization.year >= '"+ year+"'");
		if(assignedOrganisation != 0){
			query.append(" and m.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and m.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
		}
		if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			query.append(" and m.projectMasterDomain.numId in ("+assignedProjects+")");
		}
		return daoHelper.findByQuery(query.toString());
		
	}
	
	//Query for Expenditure Graph for Manpower Expenditure
		public List<Object[]> getManpowerExpenditureByGroup(Date date){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			//System.out.println("date"+date);
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();
			/*String assignedProjects = userInfo.getAssignedProjects();*/
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(date);
			//System.out.println("startDate"+startDate);
					int year = startDate.get(Calendar.YEAR);
					int month = startDate.get(Calendar.MONTH)+1;
				//	System.out.println("year"+year);
					//System.out.println("month"+month);
			StringBuffer query = new StringBuffer("select sum(a.salaryInProject),a.projectMasterDomain.application.groupMaster.numId,a.projectMasterDomain.application.groupMaster.groupName,");
			query.append(" a.projectMasterDomain.application.groupMaster.bgColor from ManpowerUtilizationDetails a where a.numIsValid=1 and a.manpowerUtilization.month >= "+month+ " and a.manpowerUtilization.year >= "+ year);
			//query.append("a.projectMasterDomain.application.groupMaster.numId in ()");
			
			if(null != assignedOrganisation){
				query.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
		
			if(null != assignedGroups  && !assignedGroups.equals("0")){
				query.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			/*if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and a.projectMasterDomain.numId in ("+assignedProjects+")");
			}*/
			query.append(" group by 2,3,4 ");
			query.append(" order by 3");
			return daoHelper.findByQuery(query.toString());
		}
		
		
		
		//Query for Manpower Utilization tab for projectDetails
		public List<Object[]> getManpowerUtilization(long projectId, int month, int year){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();
	
			List<Object[]> dataList = new ArrayList<Object[]>();
		
			StringBuilder query = new StringBuilder("select a.utilization,a.salaryInProject,a.manpowerUtilization.employeeMasterDomain.employeeName,");
			query.append(" a.manpowerUtilization.totalSalaryByAuthority, a.manpowerUtilization.totalSalaryBySystem, a.manpowerUtilization.month," );
			query.append(" a.manpowerUtilization.year, a.projectMasterDomain.numId from ManpowerUtilizationDetails a where a.manpowerUtilization.month="+month);
			query.append(" and a.manpowerUtilization.year="+year+" and a.projectMasterDomain.numId="+projectId);
			if(null != assignedOrganisation){
				query.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(null != assignedGroups && !assignedGroups.equals("0")){
				query.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			
			query.append(" order by 1,3");
			dataList =  daoHelper.findByQuery(query.toString());
			return dataList;			
			}
		
		public ManpowerUtilization getManpowerUtilizationByYrAndMonth(int month,int year,long empId){
			StringBuilder query = new StringBuilder("select distinct a from ManpowerUtilization a join fetch a.utilizationDetails b where a.numIsValid=1 and b.numIsValid=1 and a.month='"+month+"' and a.year='"+year+"' and a.employeeMasterDomain.numId="+empId);
			List<ManpowerUtilization> list = daoHelper.findByQuery(query.toString());
			if(list.size()>0){
				return list.get(0);
			}
			return null;
		}
		
		
		public void deleteMappedValue(ManpowerUtilizationDetails manpowerUtilizationDetails)
		{
			//System.out.println("in dao delete .....");
			daoHelper.deleteByAttribute(ManpowerUtilizationDetails.class, "manpowerUtilization", manpowerUtilizationDetails.getManpowerUtilization().getNumId());
			System.out.println("deleted");
			
		}
		
	
}
