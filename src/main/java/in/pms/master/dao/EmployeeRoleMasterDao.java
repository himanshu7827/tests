package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.domain.LoginHistoryDomain;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ProjectRoleAccessRoleMapping;
import in.pms.master.domain.TempEmployeeRoleMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRoleMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateEmployeeRoleMaster(EmployeeRoleMasterDomain employeeRoleMasterDomain){
		
		employeeRoleMasterDomain =daoHelper.merge(EmployeeRoleMasterDomain.class, employeeRoleMasterDomain);		
			return employeeRoleMasterDomain.getNumId();
		}
		
		public EmployeeRoleMasterDomain getEmployeeRoleMasterById(long id){
			List<EmployeeRoleMasterDomain> employeeRoleMasterrList =  daoHelper.findByQuery("from EmployeeRoleMasterDomain where numId="+id);
			if(employeeRoleMasterrList.size()>0){
				return employeeRoleMasterrList.get(0);
			}
			return null;
		}
		
		/*public EmployeeRoleMasterDomain getProjectInvoiceMasterByRefNo(String strInvoiceRefno,long projectId){
			String query = "from ProjectInvoiceMasterDomain where lower(strInvoiceRefno)=lower('"+strInvoiceRefno+"') and projectId="+projectId+" and numIsValid<>2" ;	
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomainList = daoHelper.findByQuery(query);		
			if(projectInvoiceMasterDomainList.size()>0){
				return projectInvoiceMasterDomainList.get(0);
			}
			return null;
		}*/

		public List<EmployeeRoleMasterDomain> getAllEmployeeRoleMasterDomain(){
			String query = "from EmployeeRoleMasterDomain where numId<>0 and numIsValid<>2";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<EmployeeRoleMasterDomain> getAllActiveEmployeeRoleMasterDomain(){
			String query = "from EmployeeRoleMasterDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		/*
		public List<ProjectInvoiceMasterDomain> getProjectInvoiceMasterById(String ids){
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterrList =  daoHelper.findByQuery ("from ProjectInvoiceMasterDomain where numId in ("+ids+")");		
			return projectInvoiceMasterrList;
		}
*/
		
	
		/*public List<EmployeeRoleMasterDomain> getAllEmployeeRoleByEmpId(long numEmpId) {
			String query = "from EmployeeRoleMasterDomain e where e.numIsValid<>2 and e.numEmpId="+numEmpId;
			
			return daoHelper.findByQuery(query);
		}*/
	
//}

public List<Object[]> getAllEmployeeRoleByEmpId(long numEmpId) {
	
	StringBuffer buffer = new StringBuffer("select empRole.num_emp_role_id,emp.str_emp_name,d.str_role_name,org.str_organisation_name,grp.str_group_name,");
	buffer.append("empRole.dt_startdate,empRole.dt_enddate,empRole.num_emp_id,empRole.role_id,empRole.num_organisation_id,empRole.num_group_id,empRole.num_project_id,p.str_project_name, ");
	buffer.append(" empRole.num_primary_project,empRole.num_id_fk,empRole.str_manreq_details,empRole.num_utilization from pms_employee_role_mst empRole,pms_employee_master emp,pms_organisation_master org,pms_group_master grp,pms_project_master p,pms_role_master d ");
	buffer.append("where empRole.NUM_ISVALID=1 and empRole.num_emp_id=emp.emp_id and empRole.num_organisation_id=org.organisation_id and empRole.num_group_id=grp.group_id "); 
	buffer.append("and empRole.role_id=d.role_id and empRole.num_project_id=p.num_project_id and empRole.num_isvalid=1 and empRole.num_emp_id="+numEmpId);


	//System.out.println(buffer.toString());
	
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	return 	obj;
	
}

public List<Object[]> getActiveEmployeeRoleByEmpId(long numEmpId) {	
	StringBuffer buffer = new StringBuffer("select empRole.num_emp_role_id,emp.str_emp_name,d.str_role_name,org.str_organisation_name,grp.str_group_name,");
	buffer.append("empRole.dt_startdate,empRole.dt_enddate,empRole.num_emp_id,empRole.role_id,empRole.num_organisation_id,empRole.num_group_id,empRole.num_project_id,p.str_project_name,  ");
	buffer.append(" empRole.num_primary_project,empRole.num_utilization from pms_employee_role_mst empRole,pms_employee_master emp,pms_organisation_master org,pms_group_master grp,pms_project_master p,pms_role_master d ");
	buffer.append("where empRole.num_isvalid=1 and empRole.num_emp_id=emp.emp_id and  empRole.num_organisation_id=org.organisation_id and empRole.num_group_id=grp.group_id "); 
	buffer.append("and empRole.role_id=d.role_id and empRole.num_project_id=p.num_project_id and (empRole.dt_enddate is null or empRole.dt_enddate>=current_date) and empRole.num_emp_id="+numEmpId);
	// Add is_valid=1 for only display active project name [13-10-2023] -------------//
	buffer.append(" and p.num_isvalid in (1,0)");
	List<Object[]> obj = daoHelper.runNative(buffer.toString());	
	return 	obj;	
}
		public int getEmployeeHighestRoleHierarchy(long employeeId){
			int result = -10;
			String query ="select min(num_hierarchy) from pms_role_master where role_id in  (select distinct role_id from pms_employee_role_mst where (dt_enddate is null or dt_enddate>=current_date) and num_isvalid=1 and num_emp_id="+employeeId+")";
			List<Integer> listData = daoHelper.runNative(query);
			if(null != listData.get(0)){
				result = listData.get(0);
			}
			return result;
		}
		
		public String getDistinctOrganisationsForEmployee(long employeeId){
			String result = "";
			String query = "select string_agg (distinct concat('',num_organisation_id),',') from pms_employee_role_mst where (dt_enddate is null or dt_enddate>=current_date) and num_isvalid=1 and num_emp_id="+employeeId;
			List<String> dataList = daoHelper.runNative(query);
			if(dataList.size()>0){
				result = dataList.get(0);
			}
			return result;
		}
		
		public String getDistinctGroupsForEmployee(long employeeId){
			String result = "";
			String query = "select string_agg (distinct concat('',num_group_id),',') from pms_employee_role_mst where (dt_enddate is null or dt_enddate>=current_date) and num_isvalid=1 and num_emp_id="+employeeId;
			List<String> dataList = daoHelper.runNative(query);
			if(dataList.size()>0){
				result = dataList.get(0);
			}
			return result;
		}
		public String getDistinctProjectsForEmployee(long employeeId){
			String result = "";
			String query = "select string_agg (distinct concat('',num_project_id),',') from pms_employee_role_mst where (dt_enddate is null or dt_enddate>=current_date) and num_isvalid=1 and num_emp_id="+employeeId;
			List<String> dataList = daoHelper.runNative(query);
			if(dataList.size()>0){
				result = dataList.get(0);
			}
			return result;
		}
		
		
		public List<Object[]> getProjectTeamDetails(String strProjectId){
			StringBuffer query = new StringBuffer("select c.num_hierarchy,b.str_emp_name,c.str_role_name from pms_employee_role_mst a ,pms_employee_master b,");
			query.append(" pms_role_master c where a.num_emp_id=b.emp_id and a.role_id = c.role_id and (a.dt_enddate is null or a.dt_enddate>=CURRENT_DATE)");
			query.append(" and a.num_isvalid=1 and num_project_id="+strProjectId); 			 
			query.append(" order by num_hierarchy");			
			return daoHelper.runNative(query.toString());
		}
		
		public List<Object[]> getEmployeeFromProject(String monthStartDate,String monthEndDate){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();			
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int selectedRole = selectedEmployeeRole.getNumRoleId();
			int selectedGroup = selectedEmployeeRole.getNumGroupId();
			String[] dateArray = monthStartDate.split("/");
			
			StringBuilder query = new StringBuilder("select distinct a.num_emp_id,b.str_emp_name," );
			query.append("(select sum(d.num_utilization) from pms_manpower_utilization c,pms_manpower_utilization_dtl d where ");
			query.append("  c.num_manpower_utilization_id=d.num_utilization_id_fk and c.num_isvalid=1 and d.num_isvalid=1 and c.num_employee_id_fk=a.num_emp_id");
			query.append(" and c.num_month="+dateArray[1]+" and c.num_year="+dateArray[2]);
			query.append(") from pms_employee_role_mst a,pms_employee_master b, pms_role_master c");
			if(selectedRole == 5){
				query.append(" where a.num_group_id="+selectedGroup);
			}else{
				query.append("  where a.num_project_id in ("+userInfo.getAssignedProjects()+")"); 
			}
			query.append(" and a.num_emp_id = b.emp_id and a.role_id=c.role_id and c.num_hierarchy >=" +userInfo.getHighestRoleHierarchy() );
			query.append(" and (a.dt_enddate is null and   (to_date('"+monthEndDate+"','dd-mm-yyyy') >=a.dt_startdate) ) order by 2");			
			return daoHelper.runNative(query.toString());		
		}

		public List<Object[]> getDefaultEmployeeRoleByEmpId(long numEmpId) {
			StringBuffer buffer = new StringBuffer("select empRole.num_id,emp.str_emp_name,d.str_role_name,org.str_organisation_name,grp.str_group_name,");
			buffer.append("empRole.dt_startdate,empRole.dt_enddate,empRole.num_emp_id,empRole.role_id,empRole.num_organisation_id,empRole.num_group_id,empRole.num_project_id,p.str_project_name  ");
			buffer.append("from pms_employee_default_role_mst empRole,pms_employee_master emp,pms_organisation_master org,pms_group_master grp,pms_project_master p,pms_role_master d ");
			buffer.append("where empRole.num_emp_id=emp.emp_id and  empRole.num_organisation_id=org.organisation_id and empRole.num_group_id=grp.group_id "); 
			buffer.append("and empRole.role_id=d.role_id and empRole.num_project_id=p.num_project_id and (empRole.dt_enddate is null or empRole.dt_enddate>=current_date) and empRole.num_emp_id="+numEmpId);
			List<Object[]> obj = daoHelper.runNative(buffer.toString());	
			return 	obj;
		}
		
		public List<EmployeeRoleMasterDomain> getEmployeeRoleMasterDomain(long empId){
			String query = "from EmployeeRoleMasterDomain where numIsValid=1 and (dtEndDate is null or dtEndDate>=CURRENT_DATE) and numEmpId="+empId;
			return  daoHelper.findByQuery(query);	
		}
	
		
		public List<EmployeeRoleMasterDomain> getActiveEmployeeRoleByEmpIdNew(long numEmpId) {	
			String query=" from EmployeeRoleMasterDomain left join fetch RoleMasterDomain  where numEmpId="+numEmpId +" and numIsValid=1";
			return daoHelper.findByQuery(query); 
		}

		public List<Object[]> getAllEmpRole(EmployeeRoleMasterModel employeeRoleMasterModel) {			
			StringBuffer query= new StringBuffer(" select a.dt_startdate,a.dt_enddate,a.num_project_id,a.role_id,a.num_utilization,");
			query.append(" b.str_project_name,b.dt_project_start,b.dt_project_end,a.num_emp_role_id from pms_employee_role_mst a ,pms_project_master b");
			query.append(" where a.num_isvalid=1 and a.num_project_id= b.num_project_id and a.num_emp_id="+employeeRoleMasterModel.getNumEmpId());
			if(employeeRoleMasterModel.getNumId() != 0){
				query.append(" and a.num_emp_role_id != "+employeeRoleMasterModel.getNumId());
			}
			return daoHelper.runNative(query.toString()); 
			
		}

		public List<ProjectRoleAccessRoleMapping> getProjectRoleAccessRoleMapping(int numRoleId) {
			String query=" from ProjectRoleAccessRoleMapping  where roleMst="+numRoleId +" and numIsValid=1";
			 return daoHelper.findByQuery(query); 
		}
		
		public List<Object[]> projectTeamWiseEmployees(long projectId,long roleId){
			//Bhavesh(06-7-23)(added num_deputed_at )
			StringBuilder buffer = new StringBuilder("select empRole.num_emp_role_id,emp.str_emp_name,empRole.dt_startdate,to_char(empRole.dt_enddate, 'DD/MM/YYYY') as enddate,empRole.num_emp_id,empRole.num_primary_project,emp.num_designation_id,desg.designation_name,");
			buffer.append(" rolemst.str_role_name,prj.str_project_name,(SELECT count(*) FROM pms_employee_role_mst WHERE  num_isvalid =1 and (dt_enddate is null or dt_enddate >= current_date) and dt_startdate <= current_date and num_emp_id = emp.emp_id ) as count,empRole.num_id_fk ,empRole.num_utilization,to_char(emp.dt_joining, 'DD/MM/YYYY'),empRole.role_id,emp.num_deputed_at from pms_employee_role_mst empRole,pms_employee_master emp,pms_designation_master desg,pms_role_master rolemst,pms_project_master prj where empRole.num_emp_id=emp.emp_id ");	
			buffer.append(" and (empRole.dt_enddate is null or empRole.dt_enddate>=current_date) and ");
			buffer.append("desg.num_designation_id=emp.num_designation_id and empRole.role_id=rolemst.role_id and empRole.num_project_id=prj.num_project_id and empRole.num_isvalid=1 and empRole.num_project_id="+projectId );
			System.out.println(buffer);
			return daoHelper.runNative(buffer.toString());
			}

		
		public EmployeeRoleMasterDomain getEmployeeRoleMasterByProjectId(long projectId){
			List<EmployeeRoleMasterDomain> employeeRoleMasterrList =  daoHelper.findByQuery("from EmployeeRoleMasterDomain e where  e.roleMasterDomain.numId=3 and e.numProjectId="+projectId);
			if(employeeRoleMasterrList.size()>0){
				return employeeRoleMasterrList.get(0);
			}
			return null;
		}
	
		public List<EmployeeRoleMasterDomain> getEmployeeRoleMasterByEmpId(long empId){
			List<EmployeeRoleMasterDomain> employeeRoleMasterList =  daoHelper.findByQuery("from EmployeeRoleMasterDomain empRole where empRole.primaryProject=1 and empRole.numEmpId="+empId);
			return employeeRoleMasterList;
		}

		public List<EmployeeRoleMasterDomain> getProjectPL(long projectId) {
			return daoHelper.findByQuery("from EmployeeRoleMasterDomain empRole where empRole.roleMasterDomain.numId=3 and empRole.numProjectId="+projectId);
		}
		
		public List<Object[]> getAllTeamDetailsByProject(String strProjectId){
			StringBuffer query = new StringBuffer("select c.num_hierarchy,b.str_emp_name,c.str_role_name,a.dt_startdate,a.dt_enddate,a.num_isvalid,a.num_emp_id,");
			// add (a.num_utilization) for get % of involvment of employee [added by anuj]
			query.append(" a.role_id,a.num_emp_role_id,a.num_id_fk,d.designation_name,d.num_designation_id,b.str_employment_status,b.num_deputed_at,a.num_utilization from pms_employee_role_mst a,pms_employee_master b,pms_role_master c,pms_designation_master d,pms_project_master P where  ");
			/*query.append(" a.num_emp_id=b.emp_id and a.role_id = c.role_id and d.num_designation_id= b.num_designation_id and a.num_isvalid=1 and A.num_project_id=P.num_project_id and (A .dt_enddate IS NULL OR A .dt_enddate >= P .dt_project_end) and P.num_project_id="+strProjectId); */
			//modified by devesh on 02-11-23 to fix query condition of end date greater than equal to current date
			query.append(" a.num_emp_id=b.emp_id and a.role_id = c.role_id and d.num_designation_id= b.num_designation_id and a.num_isvalid=1 and A.num_project_id=P.num_project_id and (A .dt_enddate IS NULL OR A .dt_enddate >= current_date) and P.num_project_id="+strProjectId); 
			//End of comment
			query.append(" order by 1,2");
			System.out.println("\n query..."+query.toString());
			return daoHelper.runNative(query.toString());
		}
		
		//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
		public List<Object[]> getAllTeamDetailsByProjectForAllEndDate(String strProjectId){
			StringBuffer query = new StringBuffer("select c.num_hierarchy,b.str_emp_name,c.str_role_name,a.dt_startdate,a.dt_enddate,a.num_isvalid,a.num_emp_id,");
			// add (a.num_utilization) for get % of involvment of employee [added by anuj]
			query.append(" a.role_id,a.num_emp_role_id,a.num_id_fk,d.designation_name,d.num_designation_id,b.str_employment_status,b.num_deputed_at,a.num_utilization from pms_employee_role_mst a,pms_employee_master b,pms_role_master c,pms_designation_master d,pms_project_master P where  ");
			query.append(" a.num_emp_id=b.emp_id and a.role_id = c.role_id and d.num_designation_id= b.num_designation_id and a.num_isvalid=1 and A.num_project_id=P.num_project_id and (A .dt_enddate IS NULL OR A .dt_enddate >= P .dt_project_end OR A .dt_enddate < P .dt_project_end) and P.num_project_id="+strProjectId); 			 
			query.append(" order by 1,2");			
			return daoHelper.runNative(query.toString());
		}
		//End of Query
		
		public List<Integer> getProjectIds(long numEmpId) {
			//Added 18/09/23 to role 15 for HOD
			StringBuffer query = new StringBuffer("Select distinct e.numProjectId from EmployeeRoleMasterDomain e where e.numIsValid=1 and  e.roleMasterDomain.numId in(2,3,4,15) and e.numEmpId="+numEmpId);
			return daoHelper.findByQuery(query.toString());
		}
	//	added below method getProjectIdsforTeam to get projectid from numroleId by varun on 19-10-2023
		public List<Integer> getProjectIdsforTeam(long numRoleId) {
			//Bhavesh(23-10-23)get the employee Id from userInfo 
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			long roleEmpId =  userInfo.getEmployeeId();
			//Added 18/09/23 to role 15 for HOD
			//StringBuffer query = new StringBuffer("Select distinct e.numProjectId from EmployeeRoleMasterDomain e where e.numIsValid=1 and e.numEmpId="+roleEmpId e.roleMasterDomain.numId ="+numRoleId);
			StringBuffer query = new StringBuffer("SELECT DISTINCT e.numProjectId FROM EmployeeRoleMasterDomain e WHERE e.numIsValid = 1 AND e.numEmpId = " + roleEmpId + " AND e.roleMasterDomain.numId = " + numRoleId);
           
			return daoHelper.findByQuery(query.toString());
		}
		
		public List<Integer> getGroupIds(long numEmpId) {
			StringBuffer query = new StringBuffer("Select distinct e.numGroupId from EmployeeRoleMasterDomain e where e.numIsValid=1 and  e.roleMasterDomain.numId =5 and e.numEmpId="+numEmpId);
			return daoHelper.findByQuery(query.toString());
		}
		public List<EmployeeRoleMasterDomain> getEmployeeRoleMasterDomain(long projectId,int empId){
			String query = "from EmployeeRoleMasterDomain where numEmpId="+empId+" and numProjectId="+projectId+" and numIsValid=1 and (dtEndDate is null or dtEndDate>=current_date)";
			return  daoHelper.findByQuery(query.toString());	
		}
		
		// br tag is added to start with the new line in below query by varun on 16-10-2023
		public List<Object[]> getAllTeamMembers(){
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			/*StringBuffer query = new StringBuffer("select d.str_emp_name,e.designation_name,string_agg('<br> </br><b>' || f.str_project_name || '</b> As '|| c.str_role_name ||' Since ' ||");*/
			//Below line modified by devesh on 29-11-23 to get project reference number before project name
			StringBuffer query = new StringBuffer("select d.str_emp_name,e.designation_name,string_agg('<br> </br><b>' || CASE WHEN f.str_project_ref_no IS NOT NULL THEN '<span style=\"color:darkblue;\">' || f.str_project_ref_no || '</span> ' || ' - ' ELSE '' END || f.str_project_name || '</b> As '|| c.str_role_name ||' Since ' ||");
			/*query.append(" (case when a.dt_enddate is null THEN to_char(a.dt_startdate,'dd-MM-yyyy') ELSE");*/
			query.append(" (case when a.dt_enddate is null THEN to_char(a.dt_startdate,'dd-MM-yyyy')|| CASE WHEN d.dt_release_date IS NOT NULL THEN ' till ' || to_char(d.dt_release_date, 'dd-MM-yyyy') ELSE '' END ELSE"); // Added by devesh on 30-11-23 to display release date as end date if end date is null
			query.append(" to_char(a.dt_startdate,'dd-MM-yyyy') || ' till '|| to_char(a.dt_enddate,'dd-MM-yyyy') end),'.') involvement ");
			query.append(" from pms_employee_role_mst a,pms_role_master c ,pms_employee_master d, pms_designation_master e,pms_project_master f,");
			query.append(" (select  p.num_project_id,q.num_hierarchy from pms_employee_role_mst p,pms_role_master q where p.num_isvalid=1 and p.num_emp_id="+ userInfo.getEmployeeId());
			query.append(" and (dt_enddate is null or dt_enddate>=current_date)  and p.role_id= q.role_id ) as b where");
			query.append(" a.num_isvalid=1 and (dt_enddate is null or dt_enddate>=current_date)and a.num_project_id = b.num_project_id and a.role_id= c.role_id");
			query.append(" and a.num_project_id = b.num_project_id and c.num_hierarchy >= b.num_hierarchy and a.num_emp_id= d.emp_id");
			query.append(" and d.num_designation_id = e.num_designation_id and f.num_project_id= a.num_project_id group by 1,2 order by 1");
			
			return daoHelper.runNative(query.toString());
		}
		
		//Added by devesh on 30-11-23 to get team members according to projects
		public List<Object[]> getAllTeamMembersProjectWise(){
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			StringBuffer query = new StringBuffer("select ('<b>' || CASE WHEN f.str_project_ref_no IS NOT NULL THEN '<span style=\"color:darkblue;\">' || f.str_project_ref_no || '</span> ' || ' - ' ELSE '' END || f.str_project_name || '</b>') involvement ");
			query.append(" ,e.designation_name,string_agg('<br><b>' ||d.str_emp_name|| '</b> As '|| c.str_role_name ||' Since ' || (case when a.dt_enddate is null THEN to_char(a.dt_startdate,'dd-MM-yyyy')|| CASE WHEN d.dt_release_date IS NOT NULL THEN ' till ' ||");
			query.append(" to_char(d.dt_release_date, 'dd-MM-yyyy') ELSE '' END ELSE to_char(a.dt_startdate,'dd-MM-yyyy') || ' till '|| to_char(a.dt_enddate,'dd-MM-yyyy') end),'. <br>') str_emp_name");
			query.append(" from pms_employee_role_mst a,pms_role_master c ,pms_employee_master d, pms_designation_master e,pms_project_master f,");
			query.append(" (select  p.num_project_id,q.num_hierarchy from pms_employee_role_mst p,pms_role_master q where p.num_isvalid=1 and p.num_emp_id="+ userInfo.getEmployeeId());
			query.append(" and (dt_enddate is null or dt_enddate>=current_date)  and p.role_id= q.role_id ) as b where");
			query.append(" a.num_isvalid=1 and (dt_enddate is null or dt_enddate>=current_date)and a.num_project_id = b.num_project_id and a.role_id= c.role_id");
			query.append(" and a.num_project_id = b.num_project_id and c.num_hierarchy >= b.num_hierarchy and a.num_emp_id= d.emp_id");
			query.append(" and d.num_designation_id = e.num_designation_id and f.num_project_id= a.num_project_id group by 1,2 order by 1");
			
			return daoHelper.runNative(query.toString());
		}
		//End of Function
		
		public List<EmployeeRoleMasterDomain> getAllocatedInvolvement(EmployeeRoleMasterModel model){
			StringBuffer query = new StringBuffer("from EmployeeRoleMasterDomain a where a.numIsValid=1 ");
			query.append(" and a.manpowerRequirementDomain.numId="+model.getNumManReqId());
						
			if(model.getNumId() != 0){
				query.append(" and a.numId !="+model.getNumId());
			}
			
			return daoHelper.findByQuery(query.toString());
		
		}
		
		public boolean checkforDateOverlap(String fromDate,String toDate ,String newFromDate,String newToDate){
			String query="Select (to_date('"+newFromDate+"','dd/MM/yyyy'), to_date('"+newToDate+"','dd/MM/yyyy')+1) OVERLAPS  (to_date('"+fromDate+"','dd/MM/yyyy'), to_date('"+toDate+"','dd/MM/yyyy')+1)";
			List<Boolean> list = daoHelper.runNative(query);
			if(null != list && list.size()>0){
				return list.get(0);
			}else
			return false;
		}
		
		public List<Object[]> getTeamMIdName(){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) auth.getPrincipal();
			/*
			 * StringBuffer query = new
			 * StringBuffer("select DISTINCT d.emp_id,d.str_emp_name,d.num_designation_id  from pms_employee_role_mst a ,pms_employee_master d,"
			 * ); query.
			 * append(" (select  p.num_project_id from pms_employee_role_mst p,pms_role_master q where p.num_isvalid=1 and p.num_emp_id="
			 * +userInfo.getEmployeeId()); query.
			 * append(" and (dt_enddate is null or dt_enddate>=current_date) and p.dt_startdate <= current_date  and p.role_id= q.role_id ) as b"
			 * ); query.
			 * append(" where a.num_isvalid=1 and (a.dt_enddate is null or a.dt_enddate>=current_date) and a.num_project_id = b.num_project_id"
			 * ); query.append(" and a.num_emp_id= d.emp_id  order by 2");
			 */
			
			//String query = "select DISTINCT   d.emp_id,d.str_emp_name,d.num_designation_id ,ff.designation_name from pms_employee_role_mst a,pms_designation_master as ff ,pms_employee_master d,  (select  p.num_project_id from pms_employee_role_mst p,pms_role_master q where p.num_isvalid=1 and p.num_emp_id="+userInfo.getEmployeeId()+" and (dt_enddate is null or dt_enddate>=current_date) and p.dt_startdate <= current_date  and p.role_id= q.role_id ) as b where a.num_isvalid=1 and (a.dt_enddate is null or a.dt_enddate>=current_date) and a.num_project_id = b.num_project_id and a.num_emp_id= d.emp_id  and d.num_designation_id=ff.num_designation_id  order by 2";
			String query = "select DISTINCT   d.emp_id,d.str_emp_name,d.num_designation_id ,ff.designation_name from pms_employee_role_mst a,pms_designation_master as ff ,pms_employee_master d,pms_role_master c,  (select  p.num_project_id from pms_employee_role_mst p,pms_role_master q where p.num_isvalid=1 and p.num_emp_id="+userInfo.getEmployeeId()+" and (dt_enddate is null or dt_enddate>=current_date) and p.dt_startdate <= current_date  and p.role_id= q.role_id ) as b where a.num_isvalid=1 and (a.dt_enddate is null or a.dt_enddate>=current_date) and a.num_project_id = b.num_project_id and a.num_emp_id= d.emp_id  and d.num_designation_id=ff.num_designation_id and c.num_hierarchy>=4 and c.role_id= a.role_id order by 2";
			
			
			return daoHelper.runNative(query);
						
		}
		
		public List<EmployeeRoleMasterDomain> checkRoleInProject(int roleId, long projectId){
			
			StringBuffer query = new StringBuffer("from EmployeeRoleMasterDomain a where a.numIsValid=1");
			query.append(" and a.roleMasterDomain.numId="+roleId);
			query.append(" and a.numProjectId="+projectId);
			query.append(" and a.dtStartDate<=current_date() and (a.dtEndDate is null or a.dtEndDate>=current_date())");
			return daoHelper.findByQuery(query.toString());
		}
		
		
		public List<Object[]> getActiveEmployeeRoleByEmpIdWithoutEndDate(long numEmpId) {	
			StringBuffer buffer = new StringBuffer("select empRole.num_emp_role_id,emp.str_emp_name,d.str_role_name,");
			buffer.append("empRole.dt_startdate,empRole.dt_enddate,empRole.num_emp_id,empRole.role_id,empRole.num_organisation_id,empRole.num_group_id,empRole.num_project_id,p.str_project_name,  ");
			buffer.append(" empRole.num_primary_project,empRole.num_utilization from pms_employee_role_mst empRole,pms_employee_master emp,pms_project_master p,pms_role_master d ");
			buffer.append("where empRole.num_isvalid=1 and empRole.num_emp_id=emp.emp_id "); 
			buffer.append("and empRole.role_id=d.role_id and empRole.num_project_id=p.num_project_id and empRole.num_emp_id="+numEmpId);
			List<Object[]> obj = daoHelper.runNative(buffer.toString());	
			return 	obj;	
		}
		
		
//		public List<EmployeeRoleMasterDomain> getClosedProjectEmpListDao(long projectId,Date projectClosureDate )
//		{   ArrayList<Object> paraList=new ArrayList<Object>();
//		   
//			String query = "from EmployeeRoleMasterDomain a where  a.dtEndDate=? and a.numIsValid=? and a.numProjectId= ?";
//			paraList.add(0,projectClosureDate);
//			paraList.add(1,1);
//			paraList.add(2,projectId );
//			
//			List<EmployeeRoleMasterDomain> empList=daoHelper.findByQuery(query,paraList);
//	        if(empList.size()>0){
//				return empList;
//			}else
//				return null;
//	      
//		}
		public List<Object[]>  getClosedProjectEmpListDao(long projectId){
			  /*String query=	"select e.str_emp_name,e.emp_id,d.designation_name,r.dt_startdate,r.dt_enddate,r.num_utilization from  pms_employee_role_mst r,pms_project_master p,pms_employee_master e,pms_designation_master d where r.num_isvalid=1 and p.num_isvalid=1 and p.num_project_id ="+projectId
	  		+ "and p.num_project_id=r.num_project_id and e.num_isvalid =1 and r.num_emp_id =e.emp_id and e.num_designation_id=d.num_designation_id and p.dt_project_closure= r.dt_enddate ";*/
	//Modified by devesh on 26-10-23 to fix released resources data and bifercate between technical and financial release
	  String query=	"select e.str_emp_name,e.emp_id,d.designation_name,r.dt_startdate,r.dt_enddate,r.num_utilization,p.dt_technical_closure,p.dt_financial_closure,CASE WHEN p.dt_technical_closure != p.dt_financial_closure or p.dt_financial_closure is null THEN(CASE WHEN p.dt_technical_closure >= r.dt_enddate THEN 'technical' WHEN p.dt_financial_closure >= r.dt_enddate AND p.dt_technical_closure < r.dt_enddate THEN 'financial' ELSE NULL END) WHEN p.dt_technical_closure = p.dt_financial_closure and r.role_id IN (3, 4, 15) THEN 'financial' ELSE 'technical' END AS row_mark from  pms_employee_role_mst r,pms_project_master p,pms_employee_master e,pms_designation_master d where r.num_isvalid=1 and p.num_isvalid=1 and p.num_project_id ="+projectId
		  		+ "and p.num_project_id=r.num_project_id and e.num_isvalid =1 and r.num_emp_id =e.emp_id and e.num_designation_id=d.num_designation_id";
  
		  		
			return daoHelper.runNative(query.toString());
	        			
		}
		
		public List<EmployeeRoleMasterDomain> getEmpOfTheProject(long projectId,Date closureDate){
			String query = "from EmployeeRoleMasterDomain e where e.roleMasterDomain.numId=1 and e.numProjectId="+projectId+" and e.numIsValid=1 and (e.dtEndDate is null or e.dtEndDate>'"+closureDate+"')";
			return  daoHelper.findByQuery(query.toString());
		}

		public long saveUpdateTempEmployeeRoleMaster(TempEmployeeRoleMasterDomain employeeRoleMasterDomain){
			
			employeeRoleMasterDomain =daoHelper.merge(TempEmployeeRoleMasterDomain.class, employeeRoleMasterDomain);		
				return employeeRoleMasterDomain.getNumId();
			}
		
		public List<TempEmployeeRoleMasterDomain> getDetailsOfTempEmpRole(long projectId ){
			String query = "from TempEmployeeRoleMasterDomain e where e.numProjectId="+projectId+" and e.numIsValid=1 ";
			return  daoHelper.findByQuery(query.toString());
		}

		
		public List<Object[]> getAllTempTeamDetailsByProject(String strProjectId){
			//Bhavesh(14-07-2023)added num_utilization 
			StringBuffer query = new StringBuffer("select c.num_hierarchy,b.str_emp_name,c.str_role_name,a.dt_startdate,a.dt_enddate,a.num_isvalid,a.num_emp_id, ");
			query.append(" a.role_id,a.num_emp_role_id,a.num_id_fk,d.designation_name,d.num_designation_id,b.str_employment_status,b.num_deputed_at,a.num_utilization from pms_temp_employee_role_mst a,pms_employee_master b,pms_role_master c,pms_designation_master d,pms_project_master P where  ");
			/*query.append(" a.num_emp_id=b.emp_id and a.role_id = c.role_id and d.num_designation_id= b.num_designation_id AND P .num_project_id = A .num_project_id and a.num_isvalid=1  and (A .dt_enddate IS NULL OR A .dt_enddate = P .dt_project_end OR A .dt_enddate > P .dt_project_end) and A.num_project_id="+strProjectId); 	*/
			//Modified by devesh on 02-11-23 to fix release resource data on view closure
			query.append(" a.num_emp_id=b.emp_id and a.role_id = c.role_id and d.num_designation_id= b.num_designation_id AND P .num_project_id = A .num_project_id and a.num_isvalid=1  and (A .role_id in (1,2)) and A.num_project_id="+strProjectId);
			query.append(" order by 1,2");	
			System.out.println("query..."+query.toString());
			return daoHelper.runNative(query.toString());
		}
		
		//Function added by devesh on 19-02-24 to get employee ids of HOD mapped in common projects with user
		public Long getHODEmployeeIds(long numEmpId,long groupId) {
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(numEmpId); 
	        paraList.add(groupId);  
	        /*------- Add Same Group Condition While Retrieve the HOD ID [19-04-2024] --*/
	        String hqlQuery = "SELECT DISTINCT emp.numEmpId " +
	                          "FROM EmployeeRoleMasterDomain emp " +
	                          "WHERE emp.numIsValid = 1 " +
	                          "AND emp.roleMasterDomain.numId IN (15) " +
	                          "AND emp.numProjectId IN (" +
	                          "    SELECT DISTINCT e.numProjectId " +
	                          "    FROM EmployeeRoleMasterDomain e " +
	                          "    WHERE e.numIsValid = 1 " +
	                          "    AND e.roleMasterDomain.numId IN (1, 2, 3, 4) " +
	                          "    AND e.numEmpId = ?0" + 
	                          ") " +
	                          "AND emp.numEmpId IN (" +
	                          "    SELECT em.numId " +
	                          "    FROM EmployeeMasterDomain em " +
	                          "    WHERE em.numId = emp.numEmpId " +
	                          "    AND em.groupMasterDomain.numId = ?1" +
	                          ")";
	        /*------- End of Add Same Group Condition While Retrieve the HOD ID [19-04-2024] --*/
	        List<Long> resultList = daoHelper.findByQuery(hqlQuery, paraList);
			if(null != resultList && resultList.size()>0){
				return resultList.get(0);
			}else
			return (long) 0;
		}
		//End of function
		
		//Function added by devesh on 19-02-24 to get employee ids of PI mapped in common projects with user
		public Long getPIEmployeeIds(long numEmpId,long groupId) {
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(numEmpId); 
	        paraList.add(groupId);  
	        /*------- Add Same Group Condition While Retrieve the PI ID [19-04-2024] --*/
	        String hqlQuery = "SELECT DISTINCT emp.numEmpId " +
	                          "FROM EmployeeRoleMasterDomain emp " +
	                          "WHERE emp.numIsValid = 1 " +
	                          "AND emp.roleMasterDomain.numId IN (4) " +
	                          "AND emp.numProjectId IN (" +
	                          "    SELECT DISTINCT e.numProjectId " +
	                          "    FROM EmployeeRoleMasterDomain e " +
	                          "    WHERE e.numIsValid = 1 " +
	                          "    AND e.roleMasterDomain.numId IN (1, 2, 3) " +
	                          "    AND e.numEmpId = ?0" + 
	                          ") " +
	                          "AND emp.numEmpId IN (" +
	                          "    SELECT em.numId " +
	                          "    FROM EmployeeMasterDomain em " +
	                          "    WHERE em.numId = emp.numEmpId " +
	                          "    AND em.groupMasterDomain.numId = ?1" +
	                          ")";
	        /*------- End of Add Same Group Condition While Retrieve the PI ID [19-04-2024] --*/
			List<Long> list = daoHelper.findByQuery(hqlQuery, paraList);
			if(null != list && list.size()>0){
				return list.get(0);
			}else
			return (long) 0;
		}
		//End of function
		
		//Function get latest PL mapped in projects [29-05-2024]
		public EmployeeRoleMasterDomain getPLStatusByProjectIds(int projectId) {
			ArrayList<Object> paraList = new ArrayList<>();
	        paraList.add(projectId);  
	        String hqlQuery = "FROM EmployeeRoleMasterDomain emp " +
	                          "WHERE emp.numIsValid = 1 " +
	                          "AND emp.roleMasterDomain.numId IN (3) " +
	                          "AND emp.numProjectId = ?0 ORDER BY CASE WHEN emp.dtEndDate IS NULL THEN 1 ELSE 0 END, emp.dtEndDate DESC";
	        List<EmployeeRoleMasterDomain> list = daoHelper.findByQuery(hqlQuery, paraList);
			if(null != list && list.size()>0){
				return list.get(0);
			}
			return (EmployeeRoleMasterDomain) null;
		}
		//End of function
}
