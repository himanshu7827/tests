package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.domain.ReportMaster;
import in.pms.master.domain.TempProjectMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ReportMasterModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	public List<ProjectMasterDomain> getAllProjectMasterData(){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects =userInfo.getAssignedProjects();
		String assignedGroups = userInfo.getAssignedGroups();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedEmployeeRole.getNumRoleId();
		StringBuilder query = new StringBuilder("from ProjectMasterDomain a where a.numIsValid in(0,1)");
		if(null != assignedOrganisation){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+" )");
		}
		
		if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		//if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
		//	query.append(" and numId in ("+assignedProjects+")");
		//}
		
		//Modified by devesh on 22-04-24 to fix data when assigned projects are null
		if(roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5 && roleId != 7){
			query.append(" and numId in ("+assignedProjects+")");			
		}
		//End of condition
		
		query.append(" order by strProjectName");
		
		return  daoHelper.findByQuery(query.toString());	
	}
	
	// below query added by varun to getviewAllProjectMasterData
	public List<ProjectMasterDomain> getviewAllProjectMasterData(String groupId, String startRange, String endRange){
		
		Date startDate=null;
		Date endDate=null;
	
		if(null != startRange && !startRange.equals("")){
			try {
				startDate = DateUtils.dateStrToDate(startRange);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			try {
				startDate = DateUtils.dateStrToDate("01/01/2012");
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
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
	String assignedProjects = userInfo.getAssignedProjects();
	/*StringBuffer  query = new StringBuffer("select a.projectMaster from Application a join  a.projectMaster b where a.groupMaster.numId="+groupid);
	*/		
	List<ProjectMasterDomain> list= new ArrayList<>();
	if(groupId !=null && !groupId.isEmpty()){
	StringBuilder query = new StringBuilder("from ProjectMasterDomain a where a.numIsValid=1 and a.application.groupMaster.numId in ("+groupId+")");
	query.append(" and a.dtWorkOrderDate between '").append(startDate).append("' and '").append(endDate).append("'");
	list = daoHelper.findByQuery(query.toString());
	System.out.println(query.toString());
	}
	return list;
	}
	
	public List<ProjectMasterDomain> getAllActiveProjectMasterData(){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects =userInfo.getAssignedProjects();
		/*String assignedGroups = userInfo.getAssignedGroups();
		String assignedOrganisation = userInfo.getAssignedOrganisation();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer  query = new StringBuffer("from ProjectMasterDomain a where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
		
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+" )");
		}
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		//if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1) && roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5){
		//	query.append(" and a.numId in ("+assignedProjects+")");
		//}
		
		//Modified by devesh on 22-04-24 to fix data when assigned projects are null
		if(roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5 && roleId != 7){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		//End of condition
		
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public ProjectMasterDomain mergeProjectMaster(ProjectMasterDomain projectMasterDomain){
		projectMasterDomain = daoHelper.merge(ProjectMasterDomain.class, projectMasterDomain);		
		return projectMasterDomain;
	}

	
	public ProjectMasterDomain getProjectMasterDataById(long numId){
		ProjectMasterDomain projectMasterDomain =null;
		String query = "from ProjectMasterDomain where  numIsValid<>2  and numId="+numId;
		List<ProjectMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			projectMasterDomain =list.get(0);
		}
		return projectMasterDomain;	
	}
	
	public List<Object[]> getValidationlog(long numId){
		
		//String query = "Select str_financial_validate_remark, str__validate_status, dt__validate_date from pms_log.pms_project_master_log where num_project_id="+numId;

		//String query = "Select str_financial_validate_remark, str__validate_status, dt__validate_date from pms_log.pms_project_master_log where num_project_id="+numId + " AND (str_financial_validate_remark IS NOT NULL OR str__validate_status IS NOT NULL OR dt__validate_date IS NOT NULL)";
		String query = "SELECT str_financial_validate_remark, str__validate_status, dt__validate_date " +
	               "FROM pms_log.pms_project_master_log " +
	               "WHERE num_project_id=" + numId +
	               " AND ((str_financial_validate_remark IS NOT NULL OR str__validate_status IS NOT NULL OR dt__validate_date IS NOT NULL) " +
	               "AND (str_financial_validate_remark IS NOT NULL OR str__validate_status IS NOT NULL))";

		List<Object[]> list = daoHelper.runNative(query.toString());
		/*if(list.size()>0){
			projectMasterDomain =list.get(0);
		}*/
		System.out.println("validate query print" + query);
		return list;	
	}
	
	public ProjectMasterDomain getProjectMasterByName(String projectName){
		String query = "from ProjectMasterDomain where  numIsValid<>2 and strProjectName='"+projectName+"'" ;	
		List<ProjectMasterDomain> budgetHeadList = daoHelper.findByQuery(query);		
		if(budgetHeadList.size()>0){
			return budgetHeadList.get(0);
		}
		return null;
	}
	
	public void deleteProjectMaster(ProjectMasterDomain projectMasterDomain){
		ProjectMasterDomain budgetMaster = daoHelper.findById(ProjectMasterDomain.class, projectMasterDomain.getNumId());
		budgetMaster.setNumIsValid(2);;
		budgetMaster.setDtTrDate(projectMasterDomain.getDtTrDate());
		daoHelper.merge(ProjectMasterDomain.class, budgetMaster);
	}
	
	public List<ProjectMasterDomain> getProjectDataByGroupId(long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects = userInfo.getAssignedProjects();
		EmployeeRoleMasterModel employeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		/*StringBuffer  query = new StringBuffer("select a.projectMaster from Application a join  a.projectMaster b where a.groupMaster.numId="+groupid);
		*/		
		StringBuilder query = new StringBuilder("from ProjectMasterDomain a where a.strProjectStatus not in ('Terminated','Completed') and a.numIsValid=1 and a.application.groupMaster.numId="+groupId);
		long selectedRole = 0;
		if(null != employeeRoleMasterModel){
			selectedRole = employeeRoleMasterModel.getNumRoleId();
		}
		if(selectedRole != 5 && selectedRole !=6 && selectedRole !=8 && selectedRole !=9){
			if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and a.numId in ("+assignedProjects+")");
			}else{
				return null;
			}
		}
		
		query.append(" order by strProjectName");
		
		List<ProjectMasterDomain> list = daoHelper.findByQuery(query.toString());
		return list;
			
	}
	
	public List<Object[]>  viewProjectDetailsData(String groupId){
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
//		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate>=CURRENT_DATE) and b.num_isvalid=1) plName,p.num_project_id,bs.str_business_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk");	
// Comment in the 25/05/2023
		//buffer.append(" (COALESCE((select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate>=current_date) and b.num_isvalid=1),(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate>=current_date) and b.num_isvalid=1) ))");
// End Comment	
		buffer.append(" (COALESCE((select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
		//buffer.append(" (COALESCE((select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate>=current_date) and b.num_isvalid=1),(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate>=current_date) and b.num_isvalid=1) ))");
		buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status not IN ('Terminated','Completed','Under Approval') and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		
		if(!groupId.equals("")){
			buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and  b.num_application_id=c.application_id and b.num_group_id_fk="+groupId+")");
		}
		buffer.append(" order by p.dt_project_start desc");

		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	}
	
	public List<Object[]>  viewValidatedProjectDetailsData(Date startRange, Date endRange){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		//int assignedProject=selectedRole.getNumProjectId();
		String assignedProjects =userInfo.getAssignedProjects();
		int	assignedGroup=selectedRole.getNumGroupId();
		int roleId = selectedRole.getNumRoleId(); 
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_work_order,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append(" (COALESCE((select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
		buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.str__validate_status,p.str_financial_validate_remark,gm.str_group_name");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm, pms_group_master gm");
		//buffer.append(" where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status not IN ('Terminated','Under Approval') and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id and b.num_group_id_fk = gm.group_id");
		buffer.append(" where p.num_isvalid=1 and b.num_application_id=c.application_id and  c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id and b.num_group_id_fk = gm.group_id");

		buffer.append(" and p.dt_work_order between '" + startRange + "' and '" + endRange + "'");
		if(roleId == 5) {
		    buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and  b.num_application_id=c.application_id and b.num_group_id_fk=" + assignedGroup + ")");
		} 
/*		if (roleId == 6 || roleId == 9 || roleId ==7) {
		    buffer.append(" and p.num_project_id in (" + assignedProject + ")");
		}
		*/
		if(roleId==3 || roleId==4 || roleId==15 || roleId==2){
			buffer.append(" and p.num_project_id in (" + assignedProjects + ")");
			//query.append(" and p.numTrUserId = " + userInfo.getEmployeeId() + " ");
		}

		buffer.append(" order by p.dt_project_start desc");
		//System.out.println("Query: " + buffer.toString());
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return obj;
		/*StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append(" (COALESCE((select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
		buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.str__validate_status,p.str_financial_validate_remark,gm.str_group_name");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status not IN ('Terminated','Under Approval') and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		
             if(roleId == 5){
			buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and  b.num_application_id=c.application_id and b.num_group_id_fk="+assignedGroup+")");
		}else if (roleId != 5 && roleId != 6 && roleId != 9 && (roleId == 3 || roleId == 4 || roleId == 2)){			
			buffer.append(" and p.num_project_id in ("+assignedProject+")");
		}
		buffer.append(" order by p.dt_project_start desc");
		System.out.println("Quer:"+buffer.toString());
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;*/
	}
	
	
	public List<Object[]>  viewProjectDetailsDataById(String projectId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		if(userInfo.getSelectedEmployeeRole()!=null && userInfo.getSelectedEmployeeRole().getNumProjectId()>0)
			projectId=userInfo.getSelectedEmployeeRole().getNumProjectId()+"";
		else
			projectId=userInfo.getAssignedProjects();
		StringBuffer buffer = new StringBuffer("");
		boolean hasProject=false;
		if(projectId!=null && !projectId.equals("")){
		hasProject=true;
		buffer.append("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append(" (COALESCE((select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id),(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) ))");
		//buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName ,p.num_project_id,bs.str_business_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk ");
		buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk ");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.num_project_id in ("+projectId+")");
		}
		
		if(!(userInfo.getSelectedEmployeeRole()!=null && userInfo.getSelectedEmployeeRole().getNumProjectId()>0)){
			if(hasProject)
		buffer.append(" UNION ");		
		buffer.append(" select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where  p.num_isvalid=1 and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and num_tr_user_id="+userInfo.getEmployeeId()+")");
		}
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	}

	public List<Object[]> getAllProjectDetails() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		StringBuffer buffer = new StringBuffer("");
		if(null != selectedRole){
		int assignedProject=selectedRole.getNumProjectId();
		int	assignedGroup=selectedRole.getNumGroupId();
		int roleId = selectedRole.getNumRoleId(); 
		//System.out.println("role id...."+roleId);
		String assignedProjects = userInfo.getAssignedProjects();

		/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
		if(roleId == 6 || roleId ==8 || roleId == 9 || roleId == 5 || roleId == 7 || roleId == 14 || assignedProject != 0 || roleId ==13){
			buffer.append("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
			buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
			//buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate >= CURRENT_DATE)) plName,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");	
			/*--------------------  Get Distinct name ---------------*/
			buffer.append("(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");	

			
			buffer.append(" from pms_project_master p, pms_project_type_master bs, trans_application b, application_project c, pms_client_master cm where  p.num_isvalid=1 and p.str_project_status not IN ('Terminated','Completed','Under Approval') and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and  b.num_project_type_fk = bs.num_project_type_id and b.client_id_fk= cm.client_id");
			if(roleId == 5){
				buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
			}else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && roleId != 14 && assignedProject != 0 && roleId !=13){/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
				buffer.append(" and p.num_project_id in ("+assignedProjects+")");
			}
			buffer.append(" UNION ");
		}		
		}
		buffer.append("  select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		/*----*/
		buffer.append("(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where p.num_isvalid=1 and b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName ,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");
		/*----*/
		//buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where p.num_isvalid=1 and b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and (b.dt_enddate is null or b.dt_enddate >= CURRENT_DATE)) plName ,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");
		
		buffer.append(" from pms_project_master p, pms_project_type_master bs, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and  b.num_project_type_fk = bs.num_project_type_id and b.client_id_fk= cm.client_id");
		/*buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Under Approval'))");*/
		//new condition added to hide moved projects from PL of previous group by devesh on 30-08-23
		if(selectedRole.getNumGroupId() != 0)
			buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Under Approval')) and b.num_group_id_fk in ("+selectedRole.getNumGroupId()+")");
		else
			buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Under Approval'))");
		//end of condition
		
		buffer.append(" order by dt_project_start DESC");

		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	
	}
	
	public List<ProjectMasterDomain> getProjectDetailsByApplicationId(long applicationId){
		String query = "from ProjectMasterDomain a where numIsValid=1 and a.application.numId="+applicationId;
		return daoHelper.findByQuery(query);
	}
	public List<ProjectRolesMaster> getProjectRolesDetails(){
		String query = "from ProjectRolesMaster a where numIsValid=1 order by hierarchy";
		return daoHelper.findByQuery(query);
	}
	
	public List<ProjectRolesMaster> getProjectRolesDetailsById(int numId){
		String query = "from ProjectRolesMaster a where numIsValid=1 and a.numId="+numId;
		return daoHelper.findByQuery(query);
	}
	

	public ProjectMasterDomain getProjectMasterDataWithClientById(long projectId) {
		ProjectMasterDomain domain =null;
		String query = "from ProjectMasterDomain p left join fetch p.contactMaster left join fetch p.application.contactMaster where  p.numIsValid=1  and p.numId="+projectId;
		List<ProjectMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			domain =list.get(0);
		}
		return domain;	
	}
	
	public String getYearlyProject(int year){
		
		String result="";
		/*--------------  Add Project Refernence Number is not null Condition [01/08/2023]  ----------------------------------*/
		StringBuilder  query = new StringBuilder ("Select count(a) from pms_project_master a where a.str_project_ref_no is not null and to_char(a.dt_project_start,'YYYY')= '"+year+"'");
		List<BigInteger> dataList = daoHelper.runNative(query.toString());
		if(dataList.size()>0){				
			result = (dataList.get(0).intValue()+1)+"";
		}
		return result;
	}
	
	public List<Object[]>  getCompletedProjectData(String groupId, String strStartDate, String strEndDate){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure");	
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and p.str_closure_status='Financial' and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and b.num_application_id=c.application_id and b.num_group_id_fk="+groupId+") and p.dt_project_closure  between to_date('"+ strStartDate + "','dd/mm/yyyy') and to_date('"+ strEndDate + "','dd/mm/yyyy') ");
		if(selectedRole.getNumGroupId() != 0) buffer.append(" and b.num_group_id_fk in ("+selectedRole.getNumGroupId()+")");//Added by devesh on 01/09/23 to display only projects of user group 
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	}
	
	public List<Object[]>  getCompletedProjectDataByProjectIds(String startDate, String endDate, List<Long> projectIds){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure");	
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and p.str_closure_status='Financial' and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.dt_project_closure  between to_date('"+ startDate + "','dd/mm/yyyy') and to_date('"+ endDate + "','dd/mm/yyyy') and p.num_project_id in :ids");
		if(selectedRole.getNumGroupId() != 0) buffer.append(" and b.num_group_id_fk in ("+selectedRole.getNumGroupId()+")");//Added by devesh on 01/09/23 to display only projects of user group 
		List<Object[]> obj = daoHelper.runNative(buffer.toString(),projectIds);
		return 	obj;
	}
	
	
	public List<ProjectMasterDomain> getProjectDataByGroupIds(String groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects = userInfo.getAssignedProjects();
		/*StringBuffer  query = new StringBuffer("select a.projectMaster from Application a join  a.projectMaster b where a.groupMaster.numId="+groupid);
		*/		
		StringBuilder query = new StringBuilder("from ProjectMasterDomain a where a.strProjectStatus not in ('Terminated','Completed') and a.numIsValid=1 and a.application.groupMaster.numId in ("+groupId+")");
		List<ProjectMasterDomain> list = daoHelper.findByQuery(query.toString());
		return list;
			
	}
////Query to find count of total new projects

public long getNewProjectsCountByGroup(Date startRange1, Date endRange1){
	int result = 0;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	String assignedProjects = userInfo.getAssignedProjects();

	if(roleId == 0){
		return 0;
	}
	
	StringBuffer query = new StringBuffer("select count(a) from ProjectMasterDomain a ");		
	query.append(" where a.dtProjectStartDate between '"+startRange1+ "' and '"+endRange1+ "' ");
	/*--------------------------------------- Add Query for Only Under Approval Project [ 04-08-2023 ]    ------------------------------------------------------*/
	query.append("and a.strProjectStatus in ('Under Approval') ");
	/*---------------------------------------------------------------------------------------------*/
	query.append(" and a.numIsValid=1 ");
	if(assignedOrganisation != 0){
		query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
	}		

	if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
	}

	/*
	 * if((null != assignedProjects && !(assignedProjects.contains("0") &&
	 * assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9 &&
	 * roleId != 7){ query.append(" and a.numId in ("+assignedProjects+")"); }
	 */
	
	//Modified by devesh on 22-04-24 to fix data when assigned projects are null
	if(roleId !=5 && roleId !=6 && roleId !=9 && roleId != 7 && roleId != 8){
		query.append(" and a.numId in ("+assignedProjects+")");
	}
	//End of condition
	
	List<Long> temp = daoHelper.findByQuery(query.toString());
	
	if(temp!=null && temp.size()>0 && temp.get(0)!=null){
		result = temp.get(0).intValue();
	}
	return result;
	
}
	
	public List<Object[]> getNewProjectsDetail(Date startRange, Date endRange){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		if(roleId == 0){
			return null;
		}
		//System.out.println(endRange);
	    StringBuilder query = new StringBuilder(" select p.strProjectName,p.projectCost ,");
	    query.append(" p.numId, p.application.groupMaster.numId, p.dtProjectStartDate, p.application.clientMaster.clientName,p.dtProjectEndDate,p.dtWorkOrderDate,p.dtMOU,p.application.groupMaster.groupName,p.strProjectRefNo ");
	    query.append(" from ProjectMasterDomain p");
	    query.append(" where p.numIsValid = 1 and p.dtProjectStartDate between '"+startRange+ "' and '"+ endRange+"'");
		/*------------------------------------------ Add Query for Only Under Approval Project [ 04-08-2023 ] ---------------------------------------------------*/
		query.append(" and p.strProjectStatus in ('Under Approval') ");
		/*---------------------------------------------------------------------------------------------*/
	
	        if(assignedOrganisation != 0){
				query.append(" and p.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
	        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0 && roleId !=7){
				query.append(" and p.application.groupMaster.numId in ("+assignedGroups+")");
			}
	      
			/*
			 * if((null != assignedProjects && !(assignedProjects.contains("0") &&
			 * assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9 &&
			 * roleId !=7){ query.append(" and p.numId in ("+assignedProjects+")"); }
			 */
	        //Modified by devesh on 22-04-24 to fix data when assigned projects are null
	        if(roleId !=5 && roleId !=6 && roleId !=9 && roleId !=7 && roleId != 8){
	    		query.append(" and p.numId in ("+assignedProjects+")");
	    	}
	        //End of condition

		query.append(" order by 9,5 desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	
	public long getClosedProjectsCountByGroup(Date startRange, Date endRange){
		int result = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		if(roleId == 0){
			return 0;
		}
		
		
		StringBuffer query = new StringBuffer("select count(a) from ProjectMasterDomain a ");		
		query.append(" where a.projectClosureDate  between '"+startRange+ "' and '"+endRange+ "' ");
		query.append(" and a.strProjectStatus IN ('Terminated','Completed') and  a.closureStatus='Financial'");
		query.append(" and a.numIsValid=1 ");
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}		

		if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}

		
		 if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9 && roleId != 7){
	    		query.append(" and a.numId in ("+assignedProjects+")");
	    	}
		List<Long> temp = daoHelper.findByQuery(query.toString());
		
		if(temp!=null && temp.size()>0 && temp.get(0)!=null){
			result = temp.get(0).intValue();
		}
		//System.out.println(result);
		return result;
		
	}
	
	public List<Object[]> getClosedProjectsDetail(Date startRange, Date endRange){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
			return null;
		}
		//System.out.println(endRange);
	    StringBuilder query = new StringBuilder(" select p.strProjectName,p.projectCost ,");
	    query.append(" p.numId, p.application.groupMaster.numId, p.dtProjectStartDate, p.application.clientMaster.clientName,p.projectClosureDate,p.application.groupMaster.groupName,p.strProjectRefNo ");
	    query.append(" from ProjectMasterDomain p");
	    query.append(" where p.numIsValid = 1 and p.projectClosureDate between '"+startRange+ "' and '"+ endRange+"'");
	    query.append(" and p.strProjectStatus IN ('Terminated','Completed') and p.closureStatus='Financial'");
	
	        if(assignedOrganisation != 0){
				query.append(" and p.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
	        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0  && roleId !=7){
				query.append(" and p.application.groupMaster.numId in ("+assignedGroups+")");
			}
	        if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9 &&  roleId !=7){
	    		query.append(" and p.numId in ("+assignedProjects+")");
	    	}

		query.append(" order by 7 desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<Object[]> getPendingClosureDetail(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();

		//StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		/*--------- Add DISTINCT For Unique PL Name [19-09-2023] --------------------------*/
		/*StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");*/
		//Modified by devesh on 21-09-23 to add cdac outlay and workflow status in query
		StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName,projMaster.num_project_cost from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		//End of cdac outlay and workflow status addition
		query.append(" where projMaster.num_isvalid=1 and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id");
		query.append(" and projMaster.num_project_id = c.project_id");
		if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
		}
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 3 && roleId != 4){
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}*/
		
		/*
		 * if((null != assignedProjects && !(assignedProjects.contains("0") &&
		 * assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 &&
		 * roleId != 7){
		 * query.append(" and projMaster.num_project_id in ("+assignedProjects+")"); }
		 */
		
		//Modified by devesh on 22-04-24 to fix data when assigned projects are null
		if(roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7 && roleId !=9){
			query.append(" and projMaster.num_project_id in ("+assignedProjects+")");
		}
		//End of condition
		
		query.append(" ORDER BY projMaster.dt_project_end  asc,projMaster.str_project_name");
		//System.out.println("\n query.."+query+"\n");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	//Added new query for Pending Closure Tile without closure initialized details by devesh on 17/8/23
	public List<Object[]> getPendingClosureDetailforOngoing(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		/*query.append(" where projMaster.num_isvalid=1 and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id");*/
		//Modified by devesh to exclude those project whose project closure is started
		query.append(" where projMaster.num_isvalid=1 and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id and not exists (select 1 from pms_temp_project_master temp where temp.num_project_id = projMaster.num_project_id)");
		//End
		query.append(" and projMaster.num_project_id = c.project_id");
		if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
		}
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 3 && roleId != 4){
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}*/
		
		if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7){
			query.append(" and projMaster.num_project_id in ("+assignedProjects+")");
		}
		
		query.append(" ORDER BY projMaster.dt_project_end  asc,projMaster.str_project_name");
		//System.out.println("\n query.."+query+"\n");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	//End of Query
	
	
	public List<Object[]> PendingProgressReportsAtPL(int year,int month,Date dtLastdate){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("select dependent.num_monthly_progress_id_fk , main.num_project_id, dependent.num_month,dependent.num_year,dependent.dt_tr_date,main.str_group_name,dependent.str_action_performed from (select * from pms_project_master a, trans_application b, application_project c,pms_group_master d where  a.num_project_id= c.project_id and b.num_application_id= c.application_id and b.num_group_id_fk=d.group_id  ");
		
		if(roleId == 5){
			query.append(" and b.num_group_id_fk in ("+assignedGroups+")");
		}
		

		query.append("and a.num_project_id in (SELECT P .num_project_id FROM pms_project_master P WHERE P .str_project_status = 'Ongoing' AND P .dt_project_start <='"+dtLastdate+"' EXCEPT ALL ( SELECT P .num_project_id_fk FROM pms_transaction_master T, pms_monthly_progress_status P WHERE P .num_progress_status_id = T .num_monthly_progress_id_fk AND P .num_month ="+month+" AND P .num_year ="+year+" AND T .num_isvalid = 1 and t.num_action_id_fk !=5 ORDER BY  P .num_group_id_fk))) main  left join (select r.str_action_performed,p.num_monthly_progress_id_fk,q.num_month,q.num_year,p.dt_tr_date,q.num_group_id_fk,q.num_project_id_fk from pms_transaction_master p,pms_monthly_progress_status  q, pms_action_master r where p.num_isvalid=1  and p.num_monthly_progress_id_fk= q.num_progress_status_id  and q .num_month ="+month+" AND q .num_year ="+year+" AND q .num_isvalid = 1 and p.num_action_id_fk= r.num_action_id)  dependent on  dependent.num_project_id_fk=main.num_project_id");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	public List<Object[]> PendingProgressReportsAtGC(int year,int month,Date dtLastdate){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("SELECT t.num_monthly_progress_id_fk,A.num_project_id,p.num_month,p.num_year,t.dt_tr_date,g.str_group_name,r.str_action_performed FROM pms_project_master A,trans_application b,application_project C,pms_transaction_master t,pms_monthly_progress_status p,pms_action_master r,pms_group_master g WHERE A .num_project_id = C .project_id AND b.num_application_id = C.application_id and  b.num_group_id_fk=g.group_id  ");
		
		if(roleId == 5){
			query.append("and b.num_group_id_fk in ("+assignedGroups+")");
		}
		

		query.append("AND A.num_project_id=p.num_project_id_fk  AND p.num_progress_status_id=t.num_monthly_progress_id_fk AND t.num_action_id_fk in (4,7) AND p.num_month="+month+" and p.num_year="+year+" and t.num_isvalid=1 AND A.str_project_status = 'Ongoing' AND A.dt_project_start <='"+dtLastdate+"' AND t.num_action_id_fk= r.num_action_id");
		System.out.println("Query1:"+query.toString());
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	public List<Object[]> revisedReportAtPL(long actionId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		//EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		
		String assignedProjects = userInfo.getAssignedProjects();
		/*-----  Add Action 5,20,27 in Action Id [ 21-05-2024 ] ----------*/
	    StringBuffer query = new StringBuffer("select t.num_monthly_progress_id_fk,p.num_project_id_fk,p.num_month,num_year,t.dt_tr_date,p.num_group_id_fk,r.str_action_performed from pms_transaction_master t, pms_monthly_progress_status p,pms_action_master r where t.num_action_id_fk in (5,27,20) and p.num_project_id_fk in("+assignedProjects+") and p.num_progress_status_id=t.num_monthly_progress_id_fk AND r.num_action_id=T.num_action_id_fk and t.num_isvalid=1 order by p.num_group_id_fk ");

		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
	}
	
	public List<Object[]> pendingProgressReportsAtPL(String startDate, String endDate,int fixDate){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedProjects = userInfo.getAssignedProjects();
		StringBuffer query = new StringBuffer("SELECT dependent.num_monthly_progress_id_fk,main.str_project_name,main.gtmonth,main.gtyear,dependent.dt_tr_date,main.str_group_name,main.str_project_ref_no,main.num_project_id FROM((SELECT P .num_project_id,P .str_project_name,P .str_project_ref_no,G .str_group_name,EXTRACT (MONTH FROM (GENERATE_SERIES (case when p.dt_project_start <= to_date('"+startDate+"','dd/MM/yyyy')THEN date_trunc('month',to_date('"+startDate+"','dd/MM/yyyy'))	when p.dt_project_start > '"+startDate+"' then date_trunc('month', p.dt_project_start) end, to_date('"+endDate+"','dd/MM/yyyy'),'1 Month'))) AS gtmonth,EXTRACT ( YEAR FROM ( GENERATE_SERIES (case when p.dt_project_start <= to_date('"+startDate+"','dd/MM/yyyy') THEN date_trunc('month',to_date('"+startDate+"','dd/MM/yyyy'))	 when p.dt_project_start > '"+startDate+"' then	date_trunc('month', p.dt_project_start) end, to_date('"+endDate+"','dd/MM/yyyy'),'1 Month'))) AS gtYear FROM pms_project_master P,trans_application b,application_project C, pms_group_master G WHERE P .num_project_id = C .project_id AND b.num_application_id = C .application_id AND b.num_group_id_fk = G .group_id AND P .str_project_status = 'Ongoing'AND P .num_project_id IN ("+assignedProjects+")) main LEFT JOIN (	SELECT	T .num_monthly_progress_id_fk,P .num_project_id_fk,P .num_month, num_year,T .dt_tr_date, P .num_group_id_fk FROM pms_transaction_master T, pms_monthly_progress_status P WHERE P .num_progress_status_id = T .num_monthly_progress_id_fk AND T .num_isvalid = 1) dependent ON dependent.num_month = main.gtmonth AND dependent.num_year = main.gtyear AND dependent.num_project_id_fk = main.num_project_id)");

			List<Object[]> obj = daoHelper.runNative(query.toString());
			return obj;	
		}
	
	public List<Object[]> getMilesStoneData(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		StringBuffer query = new StringBuffer("SELECT A .str_milestone_name,A .num_milestone_id,A .dt_milestone_date,b.str_project_name,b.str_project_ref_no,cm.str_client_name,g.str_group_name,b.num_project_id FROM pms_project_milestone_mst A, pms_project_master b, trans_application C, application_project d,pms_client_master cm,pms_group_master g WHERE A .num_project_id_fk = b.num_project_id AND b.num_project_id = d.project_id AND C .num_application_id = d.application_id AND g.group_id=C.num_group_id_fk and cm.client_id = C .client_id_fk AND A .str_status IS NULL AND A .dt_completion_date IS NULL AND A .dt_milestone_date < CURRENT_DATE AND A .num_isvalid = 1 and b.str_closure_status IS NULL");
		
		if(roleId == 5){
			query.append(" and c.num_group_id_fk in ("+assignedGroups+")");
		}else if (roleId != 5 && roleId != 6 && roleId != 9 && (roleId == 3 || roleId == 4 || roleId == 2)){			
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}else if (roleId == 5 || roleId == 6 || roleId == 9){			
			
		}else{
			return null;
		}
		
		query.append(" UNION (SELECT b.str_milestone_name, b.num_milestone_id,A .dt_completion,C .str_project_name,C .str_project_ref_no,f.str_client_name,g.str_group_name,c.num_project_id FROM pms_project_milestone_review_mst A,pms_project_milestone_mst b,pms_project_master C,trans_application d,application_project e,pms_client_master f,pms_group_master g WHERE A .num_milestone_id_fk = b.num_milestone_id AND b.num_project_id_fk = C .num_project_id AND C .num_project_id = e.project_id AND d.num_application_id = e.application_id AND f.client_id = d.client_id_fk AND g.group_id=d.num_group_id_fk and ( b.str_status = 'Not Completed' OR b.str_status IS NULL) AND b.dt_completion_date IS NULL AND A .dt_completion < CURRENT_DATE AND A .num_isvalid = 1 and C.str_closure_status IS NULL AND A .dt_review = ( SELECT MAX (dt_review) FROM pms_project_milestone_review_mst WHERE num_isvalid = 1 AND num_milestone_id_fk = A .num_milestone_id_fk )");
		if(roleId == 5){
			query.append(" and G.group_id in ("+assignedGroups+"))");
		}else if (roleId != 5 && roleId != 6 && roleId != 9 && (roleId == 3 || roleId == 4 || roleId == 2)){			
			query.append(" and c.num_project_id in ("+assignedProjects+"))");
		}else{
			query.append(" ) ");
		}
		
		query.append(" order by 4");

		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	public List<Object[]> activeProgressReportsDetailsbyGCforCurrentMonth(int year,int month,long actionId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		/*----  Add Action 6 in Action Id [ 21-05-2024 ] ----------*/
		StringBuffer query = new StringBuffer("select t.num_monthly_progress_id_fk,p.num_project_id_fk,p.num_month,num_year,t.dt_tr_date,p.num_group_id_fk,r.str_action_performed from pms_transaction_master t, pms_monthly_progress_status p,pms_action_master r where t.num_action_id_fk in (6) and p.num_progress_status_id=t.num_monthly_progress_id_fk AND r.num_action_id=T.num_action_id_fk and p.num_month="+month+" and p.num_year="+year+" and t.num_isvalid=1 ");
		
		if(roleId == 5){
			query.append(" and p.num_group_id_fk ="+assignedGroups);
		}
		

		query.append(" order by p.num_group_id_fk ");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
	}
	
	public List<Object[]> loadProjectsReceived(String startRange, String endRange,int orgId){

		StringBuffer query = new StringBuffer("SELECT A .num_group_id_fk,d.str_group_name,COUNT (*),SUM (P .num_project_cost) FROM pms_project_master P,trans_application A, application_project b, pms_group_master d, pms_organisation_master e WHERE A .num_isvalid = 1 AND A .num_application_id = b.application_id AND b.project_id = P .num_project_id AND A .num_group_id_fk = d.group_id AND d.organisation_id=e.organisation_id AND P .num_isvalid = 1 AND P .dt_project_start BETWEEN to_date('"+startRange+"','dd/MM/yyyy')AND to_date('"+endRange+"','dd/MM/yyyy') AND e.organisation_id="+orgId+" GROUP BY 1,2");
		
		
		List<Object[]> obj = daoHelper.runNative(query.toString()); 
		return obj;	
			
	}
	
	public List<Object[]> getAllClosedProjectByGroup(Date startRange, Date endRange,long strEncGroupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
			return null;
		}
	    StringBuilder query = new StringBuilder(" select p.strProjectName,p.projectCost ,");
	    query.append(" p.numId, p.application.groupMaster.numId, p.dtProjectStartDate, p.application.clientMaster.clientName,p.projectClosureDate,p.application.groupMaster.groupName,p.strProjectRefNo ");
	    query.append(" from ProjectMasterDomain p");
	    query.append(" where p.numIsValid = 1 and p.projectClosureDate between '"+startRange+ "' and '"+ endRange+"'");
	    query.append(" and p.strProjectStatus IN ('Terminated','Completed')");
	
	        if(assignedOrganisation != 0){
				query.append(" and p.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}

				query.append(" and p.application.groupMaster.numId in ("+strEncGroupId+")");
	        if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9){
	    		query.append(" and p.numId in ("+assignedProjects+")");
	    	}

		query.append(" order by 7 desc");
		return daoHelper.findByQuery(query.toString());
	}

	public List<Object[]> getNewProjectsByGroupDetail(Date startRange, Date endRange,long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		if(roleId == 0){
			return null;
		}
		//System.out.println(endRange);
	    StringBuilder query = new StringBuilder(" select p.strProjectName,p.projectCost ,");
	    query.append(" p.numId, p.application.groupMaster.numId, p.dtProjectStartDate, p.application.clientMaster.clientName,p.dtProjectEndDate,p.dtWorkOrderDate,p.dtMOU,p.application.groupMaster.groupName,p.strProjectRefNo ");
	    query.append(" from ProjectMasterDomain p");
	    query.append(" where p.numIsValid = 1 and p.dtProjectStartDate between '"+startRange+ "' and '"+ endRange+"'");
	
	        if(assignedOrganisation != 0){
				query.append(" and p.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}

				query.append(" and p.application.groupMaster.numId in ("+groupId+")");
			
	      
	        if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId !=5 && roleId !=6 && roleId !=9){
	    		query.append(" and p.numId in ("+assignedProjects+")");
	    	}

		query.append(" order by 9,5 desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<Object[]> PendingProgressReportsAtPI(int year,int month,Date dtLastdate,long actionId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("SELECT t.num_monthly_progress_id_fk,A.num_project_id,p.num_month,p.num_year,t.dt_tr_date,g.str_group_name,r.str_action_performed FROM pms_project_master A,trans_application b,application_project C,pms_transaction_master t,pms_monthly_progress_status p,pms_action_master r,pms_group_master g WHERE A .num_project_id = C .project_id AND b.num_application_id = C.application_id and  b.num_group_id_fk=g.group_id  ");
		
		if(roleId == 5){
			query.append("and b.num_group_id_fk in ("+assignedGroups+")");
		}
		
		/*----  Add Action 26,27( sent to HOD and back to HOD ) in Action Id [ 21-05-2024 ] ----------*/
		query.append("AND A.num_project_id=p.num_project_id_fk  AND p.num_progress_status_id=t.num_monthly_progress_id_fk AND t.num_action_id_fk in (26,27) AND p.num_month="+month+" and p.num_year="+year+" and t.num_isvalid=1 AND A.str_project_status = 'Ongoing' AND A.dt_project_start <='"+dtLastdate+"' AND t.num_action_id_fk= r.num_action_id");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	public List<Object[]> getPendingClosureDetailByDate(String closureDate,String symbol){
		Date closurePendingDate=null;

		try{
		if(null != closureDate && !closureDate.equals("")){
			try {
				closurePendingDate = DateUtils.dateStrToDate(closureDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			closurePendingDate = new Date();
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		/*StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");*/
		//Modified by devesh on 21-09-23 to add cdac outlay and workflow status in query
		StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName,projMaster.num_project_cost from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		//End of cdac outlay and workflow status addition
		query.append(" where projMaster.num_isvalid=1 and projMaster.dt_project_end "+symbol+"  '"+ closurePendingDate+"'  and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id");
		query.append(" and projMaster.num_project_id = c.project_id");
		if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
		}
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 3 && roleId != 4){
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}*/
		
		if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7){
			query.append(" and projMaster.num_project_id in ("+assignedProjects+")");
		}
		
		query.append(" ORDER BY projMaster.dt_project_end  asc,projMaster.str_project_name");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	//Added new query for Pending Closure Tile without closure initialized details by devesh on 17/8/23
	public List<Object[]> getPendingClosureDetailByDateforOngoing(String closureDate,String symbol){
		Date closurePendingDate=null;

		try{
		if(null != closureDate && !closureDate.equals("")){
			try {
				closurePendingDate = DateUtils.dateStrToDate(closureDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			closurePendingDate = new Date();
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		/*query.append(" where projMaster.num_isvalid=1 and projMaster.dt_project_end "+symbol+"  '"+ closurePendingDate+"'  and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id");*/
		//Modified by devesh to exclude those project whose project closure is started
		query.append(" where projMaster.num_isvalid=1 and projMaster.dt_project_end "+symbol+"  '"+ closurePendingDate+"'  and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id and not exists (select 1 from pms_temp_project_master temp where temp.num_project_id = projMaster.num_project_id)");
		//End
		query.append(" and projMaster.num_project_id = c.project_id");
		if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
		}
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 3 && roleId != 4){
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}*/
		
		if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7){
			query.append(" and projMaster.num_project_id in ("+assignedProjects+")");
		}
		
		query.append(" ORDER BY projMaster.dt_project_end  asc,projMaster.str_project_name");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	//End of Query
	
	public List<Object[]> getMilestoneRevDataByDates(String expComplDate,String symbol){
		
		//System.out.println(expComplDate + "varun");
	//	System.out.println(symbol + "anuj");
		Date compDate=null;
		//added the variable name by varun on 06-07-2023
		Date symbol1=null;
		try{
		if(null != expComplDate && !expComplDate.equals("")){
			try {
				compDate = DateUtils.dateStrToDate(expComplDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			compDate = new Date();
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		//added if condition and try block 
		if(symbol.contains("/")){
			
			try{
			if(null != symbol && !symbol.equals("")){
				try {
					symbol1 = DateUtils.dateStrToDate(symbol);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				symbol1 = new Date();
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		//changed in query not mentioned non completed milestone
		//StringBuffer query = new StringBuffer("SELECT A .str_milestone_name,A .num_milestone_id,A .dt_milestone_date,b.str_project_name,b.str_project_ref_no,cm.str_client_name,g.str_group_name,b.num_project_id FROM pms_project_milestone_mst A, pms_project_master b, trans_application C, application_project d,pms_client_master cm,pms_group_master g WHERE A.dt_milestone_date "+symbol+"  '"+ compDate+"' and  A .num_project_id_fk = b.num_project_id AND b.num_project_id = d.project_id AND C .num_application_id = d.application_id AND g.group_id=C.num_group_id_fk and cm.client_id = C .client_id_fk AND (A .str_status = 'Not Completed' OR A .str_status IS NULL) AND A .dt_completion_date IS NULL AND A .dt_milestone_date < CURRENT_DATE AND A .num_isvalid = 1");
		/*StringBuffer query = new StringBuffer("SELECT A .str_milestone_name,A .num_milestone_id,A .dt_milestone_date,b.str_project_name,b.str_project_ref_no,cm.str_client_name,g.str_group_name,b.num_project_id FROM pms_project_milestone_mst A, pms_project_master b, trans_application C, application_project d,pms_client_master cm,pms_group_master g WHERE A.dt_milestone_date "+symbol+"  '"+ compDate+"' and  A .num_project_id_fk = b.num_project_id AND b.num_project_id = d.project_id AND C .num_application_id = d.application_id AND g.group_id=C.num_group_id_fk and cm.client_id = C .client_id_fk AND (A .str_status IS NULL) AND A .dt_completion_date IS NULL AND A .dt_milestone_date < CURRENT_DATE AND A .num_isvalid = 1 AND b.str_closure_status IS NULL");*/
		
		//added the query ,changed in between dates and added the if condition if symbol.contains by varun on 06-07-2023
		StringBuffer query = new StringBuffer("SELECT A .str_milestone_name,A .num_milestone_id,A .dt_milestone_date,b.str_project_name,b.str_project_ref_no,cm.str_client_name,g.str_group_name,b.num_project_id FROM pms_project_milestone_mst A, pms_project_master b, trans_application C, application_project d,pms_client_master cm,pms_group_master g ");
		if(symbol.contains("/"))
		{
			query.append("WHERE A.dt_milestone_date BETWEEN '"+compDate+"' and '"+ symbol1+"' and ");
		}else{
			query.append("WHERE A.dt_milestone_date "+symbol+"  '"+ compDate+"' and ");
		}
		query.append("A .num_project_id_fk = b.num_project_id AND b.num_project_id = d.project_id AND C .num_application_id = d.application_id AND g.group_id=C.num_group_id_fk and cm.client_id = C .client_id_fk AND (A .str_status IS NULL) AND A .dt_completion_date IS NULL AND A .dt_milestone_date < CURRENT_DATE AND A .num_isvalid = 1 AND b.str_closure_status IS NULL");

		if(roleId == 5){
			query.append(" and c.num_group_id_fk in ("+assignedGroups+")");
		}else if (roleId != 5 && roleId != 6 && roleId != 9 && (roleId == 3 || roleId == 4 || roleId == 2)){			
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}
		
/*		query.append(" UNION (SELECT b.str_milestone_name, b.num_milestone_id,A .dt_completion,C .str_project_name,C .str_project_ref_no,f.str_client_name,g.str_group_name,c.num_project_id FROM pms_project_milestone_review_mst A,pms_project_milestone_mst b,pms_project_master C,trans_application d,application_project e,pms_client_master f,pms_group_master g WHERE A .num_milestone_id_fk = b.num_milestone_id AND b.num_project_id_fk = C .num_project_id AND C .num_project_id = e.project_id AND d.num_application_id = e.application_id AND f.client_id = d.client_id_fk AND g.group_id=d.num_group_id_fk and ( b.str_status = 'Not Completed' OR b.str_status IS NULL) AND b.dt_completion_date IS NULL AND A .dt_completion < CURRENT_DATE AND A .dt_completion "+symbol+"  '"+ compDate+"' AND A .num_isvalid = 1 AND C.str_closure_status IS NULL AND A .dt_review = ( SELECT MAX (dt_review) FROM pms_project_milestone_review_mst WHERE num_isvalid = 1 AND num_milestone_id_fk = A .num_milestone_id_fk )");*/
		


//break the query and added between date by varun 0n 06-07-2023
query.append(" UNION (SELECT b.str_milestone_name, b.num_milestone_id,A .dt_completion,C .str_project_name,C .str_project_ref_no,f.str_client_name,g.str_group_name,c.num_project_id FROM pms_project_milestone_review_mst A,pms_project_milestone_mst b,pms_project_master C,trans_application d,application_project e,pms_client_master f,pms_group_master g WHERE A .num_milestone_id_fk = b.num_milestone_id AND b.num_project_id_fk = C .num_project_id AND C .num_project_id = e.project_id AND d.num_application_id = e.application_id AND f.client_id = d.client_id_fk AND g.group_id=d.num_group_id_fk and ( b.str_status = 'Not Completed' OR b.str_status IS NULL) AND b.dt_completion_date IS NULL AND A .dt_completion < CURRENT_DATE AND ");
if(symbol.contains("/"))
{
	query.append("A .dt_completion  BETWEEN '"+compDate+"' and '"+ symbol1+"' ");
}else{
	query.append("A .dt_completion "+symbol+"  '"+ compDate+"' ");
}

query.append("AND A .num_isvalid = 1 AND C.str_closure_status IS NULL AND A .dt_review = ( SELECT MAX (dt_review) FROM pms_project_milestone_review_mst WHERE num_isvalid = 1 AND num_milestone_id_fk = A .num_milestone_id_fk )");
		if(roleId == 5){
			query.append(" and G.group_id in ("+assignedGroups+"))");
		}else if (roleId != 5 && roleId != 6 && roleId != 9 && (roleId == 3 || roleId == 4 || roleId == 2)){			
			query.append(" and c.num_project_id in ("+assignedProjects+"))");
		}
		else{
			query.append(" ) ");
		}
		query.append(" order by 4");
		//System.out.println(query +"milestonequeryvarun");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	
	public ProjectMasterDomain updateProject(long numProjectId,int selectedVal) 
	{	ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();
		ProjectMasterDomain pmd=getProjectMasterDataById(numProjectId);
			if(pmd!=null){
			pmd.setCorpMonthlySharing(selectedVal);
			 Date date=new Date();
			 pmd.setDtTrDate(date);
              		
             projectMasterDomain= daoHelper.merge(ProjectMasterDomain.class, pmd);
	
		}
		return projectMasterDomain;
	}

	public List<Object[]> getUnderApprovalProjects() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		StringBuffer buffer = new StringBuffer("");
		if(null != selectedRole){
		int assignedProject=selectedRole.getNumProjectId();
		int	assignedGroup=selectedRole.getNumGroupId();
		int roleId = selectedRole.getNumRoleId(); 
		
		String assignedProjects = userInfo.getAssignedProjects();

		/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
		if(roleId == 6 || roleId ==8 || roleId == 9 || roleId == 5 || roleId == 7 || assignedProject != 0 || roleId==13){
			buffer.append("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");			
			buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");	
			buffer.append(" from pms_project_master p, pms_project_type_master bs, trans_application b, application_project c, pms_client_master cm where  p.num_isvalid=1 and p.str_project_status not IN ('Terminated','Completed','Ongoing') and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and  b.num_project_type_fk = bs.num_project_type_id and b.client_id_fk= cm.client_id");
			if(roleId == 5){
				buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
			}else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProject != 0 && roleId != 13){/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
				buffer.append(" and p.num_project_id in ("+assignedProjects+")");
			}
			buffer.append(" UNION ");
		}		
		}
		buffer.append("  select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where p.num_isvalid=1 and b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id) plName ,p.num_project_id,bs.str_project_type_name, cm.str_client_name,cm.client_id,p.str_project_ref_no,b.end_user_id_fk");
		buffer.append(" from pms_project_master p, pms_project_type_master bs, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id  and c.project_id = p.num_project_id and c.application_id = b.num_application_id and  b.num_project_type_fk = bs.num_project_type_id and b.client_id_fk= cm.client_id");
		/*buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Ongoing'))");*/
		//new condition added to hide moved projects from PL of previous group by devesh on 30-08-23
		if(selectedRole.getNumGroupId() != 0)
			buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Ongoing')) and b.num_group_id_fk in ("+selectedRole.getNumGroupId()+")");
		else
			buffer.append(" and p.num_project_id in (select num_project_id from pms_project_master where num_isvalid=1 and  num_tr_user_id="+userInfo.getEmployeeId()+" and p.str_project_status not IN ('Terminated','Completed','Ongoing'))");
		//end of condition
		
		buffer.append(" order by dt_project_start DESC");

		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	
	}
	
public List<Object[]> getPendingMonthlyProgReportAtGC(int month,int year,Date dtLastdate){
		
		
		StringBuffer query = new StringBuffer("SELECT G.group_id,G .str_group_name,A .num_project_id FROM pms_project_master A,trans_application b,application_project C,pms_transaction_master T,pms_monthly_progress_status P,pms_action_master r,pms_group_master G WHERE A .num_project_id = C .project_id AND b.num_application_id = C .application_id AND b.num_group_id_fk = G .group_id AND A .num_project_id = P .num_project_id_fk AND P .num_progress_status_id = T .num_monthly_progress_id_fk AND T .num_action_id_fk IN (4, 7) AND P .num_month = "+month+" AND P .num_year = "+year+" AND T .num_isvalid = 1 AND A .str_project_status = 'Ongoing' AND A .dt_project_start <= '"+dtLastdate+"' AND T .num_action_id_fk = r.num_action_id UNION SELECT ta.num_group_id_fk ,G .str_group_name,ppm.num_project_id FROM trans_application ta, application_project ap,pms_project_master ppm,pms_group_master G WHERE ta.num_isvalid = 1 AND ta.num_group_id_fk = G .group_id and ta.num_application_id = ap.application_id AND ppm.num_project_id = ap.project_id AND ppm.num_isvalid = 1 AND ppm.str_project_status = 'Ongoing' AND ppm .dt_project_start <= '"+dtLastdate+"' AND ppm.num_project_id NOT IN (SELECT P .num_project_id_fk FROM pms_transaction_master T, pms_monthly_progress_status P WHERE P .num_progress_status_id = T .num_monthly_progress_id_fk AND T .num_isvalid = 1 and P.num_year="+year+" and P.num_month="+month+") ");
		
		
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}
	


	
	public String getGCName( String groupName){
		String result = "";
		String query = "SELECT str_office_email FROM pms_employee_master WHERE emp_id = (SELECT num_emp_id FROM	pms_employee_role_mst a,pms_group_master b WHERE b.str_group_name = '"+groupName+"' AND a.num_group_id=b.group_id AND role_id = 5 and (dt_enddate is null or dt_enddate >= CURRENT_DATE)) ";
		List<String> dataList = daoHelper.runNative(query);
		if(dataList.size()>0){
			result = dataList.get(0);
		}
		return result;
	}
	
	public List<TransactionMasterDomain> getDetailOfTransaction(long projectId){
		String query = "from TransactionMasterDomain a where a.numIsValid=1 and a.customId="+projectId+" and a.workflowMasterDomain.numWorkflowId=4 and a.actionMasterDomain.numActionId=17";
		return daoHelper.findByQuery(query);
	}
	
	public List<Object[]> getUnderClosureProjects() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		StringBuffer buffer = new StringBuffer("");
		List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
		List<Long> longs = numProjectIds.stream()
		        .mapToLong(Integer::longValue)
		        .boxed().collect(Collectors.toList());
		List<Object[]> obj=null;
		if(null != selectedRole){
		int assignedProject=selectedRole.getNumProjectId();
		int	assignedGroup=selectedRole.getNumGroupId();
		int roleId = selectedRole.getNumRoleId(); 
		
	
		/*if(roleId == 3 || roleId == 5 || roleId ==7 || roleId == 9 ){*/
		/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
		if(roleId ==2 || roleId == 3 || roleId == 5 || roleId ==7 || roleId == 9 || roleId == 15 || roleId == 4 || roleId ==13){ //Added by devesh on 01/09/23 to show under closure list to HOD
		
		/*if(roleId == 3 || roleId == 9 || roleId == 5 || roleId == 7 || assignedProject != 0){*/
		if(roleId ==2 || roleId == 3 || roleId == 9 || roleId == 5 || roleId == 7 || roleId == 15 || assignedProject != 0 || roleId ==13){ //Added by devesh on 01/09/23 to show under closure list to HOD
			buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (A .str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE b.role_id = 3 AND A .emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
			/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
			if(roleId == 7 || roleId ==13){
				buffer.append(" AND P .str_project_status ='Completed' AND P.str_closure_status='Technical'");
			}
			/*if(roleId == 3 || roleId == 5 || roleId == 9){*/
			if(roleId ==2 || roleId == 3 || roleId == 5 || roleId == 9 || roleId == 15 || roleId == 4){ //Added by devesh on 01/09/23 to show under closure list to HOD
				buffer.append(" AND P .str_project_status ='Ongoing' ");
			}
			if(roleId == 5){
				buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
			}
			/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
			if(roleId == 7 || roleId == 9 || roleId==5 ||roleId ==13){ 
			/*if(roleId==5 || roleId == 7 || roleId == 9 || roleId!=3 || roleId!=15){*/ //Added by devesh on 01/09/23 to show under closure list to HOD
				//buffer.append(" and tm.num_action_id_fk!=17 ");
				obj = daoHelper.runNative(buffer.toString());
			}
			else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProject != 0 && roleId !=13){
				buffer.append(" and p.num_project_id in:ids");
				buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");//Added to display group projects to only group members in under closure by devesh on 31-08-23 
				obj = daoHelper.runNative(buffer.toString(),longs);
			}

		}		
		}
		}
		
		return 	obj;
	
	}
	
	public TempProjectMasterDomain mergeProjectMaster(TempProjectMasterDomain tempProjectMasterDomain){
		tempProjectMasterDomain = daoHelper.merge(TempProjectMasterDomain.class, tempProjectMasterDomain);		
		return tempProjectMasterDomain;
	}
	
	public TempProjectMasterDomain getTempProjectMasterDataById(long numId){
		TempProjectMasterDomain projectMasterDomain =null;
		String query = "from TempProjectMasterDomain where  numIsValid<>2  and numProjectId="+numId+" order by numId desc";
		List<TempProjectMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			projectMasterDomain =list.get(0);
		}
		return projectMasterDomain;	
	}
	
	public List<Object[]>  getPendingCompletedProjectData(String groupId,int roleId){
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		//Bhavesh (22-09-23) added Distinct in  a.str_emp_name
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(Distinct a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.dt_technical_closure,e.str_group_name");	
		// get the all active amount which is not with any invoice [03-10-2023]
		buffer.append(",(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1)");
		//
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm,pms_group_master e where p.num_isvalid=1 and b.num_group_id_fk=e.group_id and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and p.str_closure_status='Technical' and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and b.num_application_id=c.application_id )  ");
		if(roleId == 5){
			buffer.append(" and b.num_group_id_fk in ("+groupId+")");
		}
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	}
	
public List<Object[]>  getPendingCompletedProjectDataByProjectIds( List<Long> projectIds){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int assignedProject=selectedRole.getNumProjectId();
		int	assignedGroup=selectedRole.getNumGroupId();
		int roleId = selectedRole.getNumRoleId();
		
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.dt_technical_closure");	
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and p.str_closure_status='Technical' and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append("  and p.num_project_id in :ids");
		//Added to display group projects to only group members in under closure by devesh on 31-08-23 
		if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && assignedProject != 0){
			buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
		}
		//End of condition
		List<Object[]> obj = daoHelper.runNative(buffer.toString(),projectIds);
		return 	obj;
	}

public List<Object[]> getUnderClosure(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
	StringBuffer buffer = new StringBuffer("");
	if(null != selectedRole){
	int assignedProject=selectedRole.getNumProjectId();
	int	assignedGroup=selectedRole.getNumGroupId();
	int roleId = selectedRole.getNumRoleId(); 
	
	String assignedProjects = userInfo.getAssignedProjects();

	//Bhavesh (22-09-23) added in query (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount, to get the received amount.
	if(roleId == 3 || roleId == 6 || roleId ==8 || roleId == 9 || roleId == 5 || roleId == 7 || assignedProject != 0){
		//------------------- Add DISTINCT keyword and role_id=3 in query for display only the project managers [21-09-2023] ---------------------------------//
		buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (DISTINCT A.str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE b.role_id=3 and A.emp_id = b.num_emp_id AND b.num_project_id = P.num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name, (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1) FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
		//buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (A.str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE A.emp_id = b.num_emp_id AND b.num_project_id = P.num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
	
		if(roleId == 7 || roleId == 6){
			buffer.append(" AND P .str_project_status ='Completed' AND P.str_closure_status='Technical'");
		}
		if(roleId == 3 || roleId == 5 || roleId == 9){
			buffer.append(" AND P .str_project_status in ('Ongoing','Completed') ");
		}
		if(roleId == 5){
			//------------------ Add action id 28 to get Sent back to GC data also add 16 [12-10-2023] -----//
			//buffer.append(" and b.num_group_id_fk in ("+assignedGroup+") and tm.num_action_id_fk in (15,20,28,16) and tm.num_role_id_fk != 7");
			/*----- Remove (and tm.num_role_id_fk != 7 ) back from GC finance also visible in closure request tile [06-12-2023] ---*/ 
			buffer.append(" and b.num_group_id_fk in ("+assignedGroup+") and tm.num_action_id_fk in (15,20,28,16)");
		}
		
		
		else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProject != 0){
			buffer.append(" and p.num_project_id in ("+assignedProjects+")");
		}

	}		
	}
	
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	
	return 	obj;
}
	
public List<Object[]> getUnderClosureforPMO(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
	StringBuffer buffer = new StringBuffer("");
	if(null != selectedRole){
	int assignedProject=selectedRole.getNumProjectId();
	int	assignedGroup=selectedRole.getNumGroupId();
	int roleId = selectedRole.getNumRoleId(); 
	
	String assignedProjects = userInfo.getAssignedProjects();

	
	if(roleId == 3 || roleId == 6 || roleId ==8 || roleId == 9 || roleId == 5 || roleId == 7 || assignedProject != 0){
		//------------------- Add DISTINCT keyword and role_id=3 in query for display only the project managers [21-09-2023] ---------------------------------//
		//buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (A .str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE A .emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
		buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (DISTINCT A .str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE b.role_id=3 and A .emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
		
		if(roleId == 7){
			buffer.append(" AND P .str_project_status ='Completed' AND P.str_closure_status='Technical'");
		}
		if(roleId == 3 || roleId == 5 || roleId == 9){
			buffer.append(" AND P .str_project_status ='Ongoing' ");
		}
		if(roleId == 5){
			buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
		}
		
		if(roleId==9|| roleId !=3 ){
			buffer.append(" and tm.num_action_id_fk in (16)");
		}
		else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProject != 0){
			buffer.append(" and p.num_project_id in ("+assignedProjects+")");
		}

	}		
	}
	
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	
	return 	obj;
}


public List<Object[]> getUnderClosureforGCFin(){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
	StringBuffer buffer = new StringBuffer("");
	if(null != selectedRole){
	int assignedProject=selectedRole.getNumProjectId();
	int	assignedGroup=selectedRole.getNumGroupId();
	int roleId = selectedRole.getNumRoleId(); 
	
	String assignedProjects = userInfo.getAssignedProjects();

	/*------------------ Add roleId 13 for Fin Executive [30-11-2023] --------------------------------*/
	if(roleId == 3 || roleId == 6 || roleId ==8 || roleId == 9 || roleId == 5 || roleId == 7 || assignedProject != 0 || roleId == 13){
		//------------------- Add DISTINCT keyword and role_id=3 in query for display only the project managers [21-09-2023] ---------------------------------//
		//buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg (A .str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE A .emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");			
		/*buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg(DISTINCT A.str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE b.role_id=3 and A.emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");	*/
		//Modified by devesh on 12-10-23 to include CDAC Outlay, Payment Received and Payment Pending columns in closure request tile
		buffer.append("SELECT P .str_project_name,P .dt_project_start,P .dt_project_end,P .num_project_cost,(SELECT string_agg(DISTINCT A.str_emp_name, ',') FROM pms_employee_master A, pms_employee_role_mst b WHERE b.role_id=3 and A.emp_id = b.num_emp_id AND b.num_project_id = P .num_project_id) plName,P .num_project_id, bs.str_project_type_name, cm.str_client_name, cm.client_id, P .str_project_ref_no, b.end_user_id_fk,G.str_group_name,(select SUM(num_received_amount) from pms_project_payment_received where num_project_id=P.num_project_id and num_isvalid=1) num_received_amount,(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=P.num_project_id and num_isvalid=1) FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");	
		//End of line
		
		if(roleId == 7){
			/*-------------------------- Add 24 Action for sent to Gc Finance action also -----------------------------------*/
			buffer.append(" AND P .str_project_status ='Completed' AND P.str_closure_status='Technical' and tm.num_action_id_fk in (18,24)");
		}
		if(roleId == 3 || roleId == 5 || roleId == 9){
			buffer.append(" AND P .str_project_status ='Ongoing' ");
		}
		if(roleId == 5){
			buffer.append(" and b.num_group_id_fk in ("+assignedGroup+")");
		}
		
		else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProject != 0 && roleId != 13){
			buffer.append(" and p.num_project_id in ("+assignedProjects+")");
		}

	}		
	}
	
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	
	return 	obj;
}

/*------------------------------ Query for get project details for excel download  -----------------------------------------------------------------------------------------------*/
public List<Object[]>  viewProjectDetailsForExcel(String groupId){
	StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
	buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
	//buffer.append(" (COALESCE((select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
	buffer.append(" (COALESCE((select string_agg(DISTINCT CONCAT(a.str_emp_name, ' (', TO_CHAR(b.dt_startdate, 'dd/mm/yyyy'), ' - ',TO_CHAR(b.dt_enddate, 'dd/mm/yyyy') , ')'),',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT CONCAT(a.str_emp_name, ' (', TO_CHAR(b.dt_startdate, 'dd/mm/yyyy'), ' - ',TO_CHAR(b.dt_enddate, 'dd/mm/yyyy') , ')'),',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
	buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,b.num_project_type_fk");
	buffer.append(" ,(select string_agg(DISTINCT CONCAT(a.str_emp_name, ' (', TO_CHAR(b.dt_startdate, 'dd/mm/yyyy'), ' - ',TO_CHAR(b.dt_enddate, 'dd/mm/yyyy') , ')'),',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=15 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1)"); //Added by devesh on 29-11-23 to add HOD name column in excel
	/*------------------------------- GET The HOD Name for Order By [17-01-2023] --------------------------------------*/
	buffer.append(" ,(SELECT ARRAY_TO_STRING(ARRAY_AGG(CONCAT(emp_name) ORDER BY dt_enddate DESC),', ') FROM (SELECT DISTINCT a.str_emp_name AS emp_name, b.dt_enddate FROM pms_employee_master a, pms_employee_role_mst b WHERE b.role_id = 15 AND a.emp_id = b.num_emp_id AND b.num_project_id = p.num_project_id AND b.num_isvalid = 1) AS ordered_data) AS HOD_Name");
	/*-------------------------------  END Of GET The HOD Name for Order By [17-01-2023] -------------------------------*/
	buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status not IN ('Terminated','Completed','Under Approval') and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
	
	if(!groupId.equals("")){
		buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and  b.num_application_id=c.application_id and b.num_group_id_fk="+groupId+")");
	}
	/* --------------- Add Order By HOD_Name [ 17-01-2024 ] ----------------------*/
	buffer.append(" order by HOD_Name, CASE b.num_project_type_fk WHEN 1 THEN 1 WHEN 3 THEN 2 WHEN 4 THEN 3 WHEN 6 THEN 4	WHEN 2 THEN 5 WHEN 5 THEN 6 End,p.dt_project_start ");
	System.out.println("Query:"+buffer.toString());
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	return 	obj;
}

/*------------------------------ Query for get under closure projects list without role ids  [29-08-2023] ------------------------------------*/
public List<Object[]> getUnderClosureList(){
	StringBuffer buffer = new StringBuffer("SELECT P.str_project_name,P.dt_project_start,P.dt_project_end,P.num_project_id, bs.str_project_type_name, P.str_project_ref_no,G.str_group_name"); 
	buffer.append(" FROM pms_project_master P, pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1  AND P.num_project_id=tm.num_custom_id and tm.num_workflow_type_id_fk=4");
	buffer.append(" AND b.num_application_id = C.application_id AND C.project_id = P.num_project_id AND C.application_id = b.num_application_id");
	buffer.append(" AND b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and  tm.num_isvalid=1 and b.num_group_id_fk=G.group_id");
	buffer.append(" AND P .str_project_status ='Ongoing'");
	List<Object[]> obj = daoHelper.runNative(buffer.toString());
	
	return 	obj;
}
/*----------------------- Query For Get the Closed Project Details [12-10-2023]---------------------------------*/
	public List<Object[]>  getCompletedProjectData(Date startRange, Date endRange){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
		/*Bhavesh (30-10-23) getting all the project Ids and convert it into string to get the closed project for PM*/
		List<Long> longs = numProjectIds.stream()
		        .mapToLong(Integer::longValue)
		        .boxed().collect(Collectors.toList());
		String result = longs.stream()
		        .map(Object::toString)
		        .collect(Collectors.joining(", "));
		/*Bhavesh (30-10-23) END of getting all the project Ids and convert it into string to get the closed project for PM*/

		
		if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
			return null;
		}
		StringBuffer query = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		query.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		query.append("(select string_agg(Distinct a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,");
		query.append(" cm.str_client_name,p.str_project_ref_no,e.str_group_name,b.num_proposal_cost,p.num_project_id");
		query.append(",(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1)");
		query.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm,pms_group_master e where ");
		query.append("p.num_isvalid=1 and b.num_group_id_fk=e.group_id and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and ");
		query.append("p.str_closure_status='Financial' and c.project_id = p.num_project_id and c.application_id = b.num_application_id ");
		query.append("and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id ");
		query.append("and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 ");
		query.append("and b.num_application_id=c.application_id ) and p.dt_project_closure between '"+startRange+ "' and '"+ endRange+"'" );
	
		if(roleId == 5){
			query.append(" and b.num_group_id_fk in ("+assignedGroups+")");
		}
		else if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 && assignedProjects != ""){
			
			query.append(" and p.num_project_id in ("+result +")");
		}
		
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return 	obj;
	}
	/*----------------------- EOF Query For Get the Closed Project Details [12-10-2023]---------------------------------*/	
	
	/*------------------------- Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/	
	public List<Object[]>  getAllFinancialClosurePendingProjectByDate(String groupId,int roleId,Date startRange, Date endRange){
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append("(select string_agg(Distinct a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.dt_technical_closure,e.str_group_name");	
		buffer.append(",(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1)");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm,pms_group_master e where p.num_isvalid=1 and b.num_group_id_fk=e.group_id and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and p.str_closure_status='Technical' and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id");
		buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and b.num_application_id=c.application_id ) ");

		buffer.append(" and p.dt_project_closure between '"+startRange+"' and '"+endRange+"'");
		if(roleId == 5){
			buffer.append(" and b.num_group_id_fk in ("+groupId+")");
		}
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		
		
		return 	obj;
	}
	/*------------------------- EOF Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/
	
	/*----------------------- Query For Get All Projects Details [18-03-2024] ------------------------*/
	public List<ProjectMasterDomain> getProjectNameByGroupId(long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedProjects = userInfo.getAssignedProjects();
		EmployeeRoleMasterModel employeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		
		List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());

		String longs = numProjectIds.stream()
			    .map(String::valueOf) 
			    .collect(Collectors.joining(",")); 
			
		StringBuilder query = new StringBuilder("from ProjectMasterDomain a where a.strProjectStatus not in ('Terminated') and a.numIsValid=1 ");
		long selectedRole = 0;
		if(null != employeeRoleMasterModel){
			selectedRole = employeeRoleMasterModel.getNumRoleId();
		}

		if(groupId==0){
			if(selectedRole !=9 && selectedRole != 6 && selectedRole != 7){
				query.append("and a.application.groupMaster.numId="+groupId);
			}
		}else{
			query.append("and a.application.groupMaster.numId="+groupId);
		}
		
		if(selectedRole != 5 && selectedRole !=6 && selectedRole !=7 && selectedRole !=9){
			if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and a.numId in ("+longs+")");
			}else{
				return null;
			}
		}
		query.append(" order by strProjectName");
		
		List<ProjectMasterDomain> list = daoHelper.findByQuery(query.toString());
		return list;
	}
	/*----------------------- End of Query For Get All Projects Details [18-03-2024] ------------------------*/


	
	public List<Object[]> getPendingClosureDetailByDates(int assignedGroup,String closureDateFrom,String closureDateTo){
		Date closurePendingDateFrom=null;
		Date closurePendingDateTo=null;

		try{
		if((null != closureDateFrom && !closureDateFrom.equals("")) && (null != closureDateTo && !closureDateTo.equals(""))){
			try {
				closurePendingDateFrom = DateUtils.dateStrToDate(closureDateFrom);
				closurePendingDateTo= DateUtils.dateStrToDate(closureDateTo);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();*/
		
		
		int assignedGroups = 0;
		
		if(assignedGroup !=0){
			
			 assignedGroups = assignedGroup;
			
		}
		/*int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		*/
		/*StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");*/
		//Modified by devesh on 21-09-23 to add cdac outlay and workflow status in query
		StringBuffer query = new StringBuffer("select e.str_group_name,projMaster.num_project_id,projMaster.str_project_name,projMaster.dt_project_start,projMaster.dt_project_end,projMaster.str_project_ref_no,clMas.str_client_name,(select string_agg(a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=projMaster.num_project_id) plName,projMaster.num_project_cost from pms_project_master projMaster,application_project c,trans_application d,pms_group_master e,pms_client_master clMas");
		//End of cdac outlay and workflow status addition
		query.append(" where projMaster.num_isvalid=1 and projMaster.dt_project_end between '"+closurePendingDateFrom+"' and '"+ closurePendingDateTo+"'  and d.client_id_fk=clMas.client_id and projMaster.dt_project_end < CURRENT_DATE and e.group_id=d.num_group_id_fk and projMaster.str_project_status in ('Ongoing') and c.application_id = d.num_application_id");
		query.append(" and projMaster.num_project_id = c.project_id");
		if( assignedGroups != 0){
			query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
		}
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 3 && roleId != 4){
			query.append(" and b.num_project_id in ("+assignedProjects+")");
		}*/
		
		/*if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7){
			query.append(" and projMaster.num_project_id in ("+assignedProjects+")");
		}*/
		
		query.append(" ORDER BY projMaster.dt_project_end  asc,projMaster.str_project_name");
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return obj;	
			
	}

	/*----------------------- Bhavesh Query For Get the Closed Project Details [08-05-2024]---------------------------------*/
	public List<Object[]>  getCompletedProjectData(Date startRange, Date endRange,int assignedGroup,int roleID ){
		
		
		
		int assignedGroups = 0;
		int roleId = 0;
		
		if(assignedGroup!=0){
			assignedGroups = assignedGroup;
			
		}
		
		if(roleID!=0){
			roleId = roleID;
			
		}
		
		
		StringBuffer query = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		query.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		query.append("(select string_agg(Distinct a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,");
		query.append(" cm.str_client_name,p.str_project_ref_no,e.str_group_name,b.num_proposal_cost,p.num_project_id");
		query.append(",(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1)");
		query.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm,pms_group_master e where ");
		query.append("p.num_isvalid=1 and b.num_group_id_fk=e.group_id and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and ");
		query.append("p.str_closure_status='Financial' and c.project_id = p.num_project_id and c.application_id = b.num_application_id ");
		query.append("and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id ");
		query.append("and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 ");
		query.append("and b.num_application_id=c.application_id ) and p.dt_project_closure between '"+startRange+ "' and '"+ endRange+"'" );
	
		if(roleId == 5){
			query.append(" and b.num_group_id_fk in ("+assignedGroups+")");
		}
		
		
		List<Object[]> obj = daoHelper.runNative(query.toString());
		return 	obj;
	}
	/*----------------------- Bhavesh Query For Get the Closed Project Details [08-05-2024]---------------------------------*/	

	

	/*------------------------- Get All Financial Closure Pending Projects by  [12-10-2023]-------------------------------*/	
	public List<Object[]>  getAllFinancialClosurePendingProjectByTechnicalClosureDate(String groupId,int roleId,Date startRange, Date endRange){
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost");
		
		buffer.append(" from pms_project_master p,trans_application b,application_project c");
		

		buffer.append(" where p.dt_technical_closure between '"+startRange+"' and '"+endRange+"' and b.num_application_id=c.application_id and c.project_id = p.num_project_id and p.num_isvalid=1" );
		if(roleId == 5){
			buffer.append(" and b.num_group_id_fk in ("+groupId+")");
		}
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		
		
		return 	obj;
	}
	/*------------------------- EOF Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/

	public List<Object[]>  viewValidatedProjectDetailsDataForReport(Date startRange, Date endRange,int assignedGroups,int roleID){
		
		int	assignedGroup=0;
		if(assignedGroups!=0){
			
		assignedGroup=assignedGroups;
		}
		
		int roleId = 0;
		if(roleID!=0){
			
			roleId=roleID;
		}
		 
		StringBuffer buffer = new StringBuffer("select p.str_project_name,p.dt_work_order,p.dt_project_end,p.num_project_cost,");
		buffer.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		buffer.append(" (COALESCE((select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1),(select string_agg(DISTINCT a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=4 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) ))");
		buffer.append(" ,p.num_project_id,pc.str_category_name, cm.str_client_name,cm.client_id,p.str_project_ref_no ,b.end_user_id_fk,p.dt_project_closure,p.str__validate_status,p.str_financial_validate_remark,gm.str_group_name");
		buffer.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm, pms_group_master gm");
		//buffer.append(" where p.num_isvalid=1 and b.num_application_id=c.application_id and p.str_project_status not IN ('Terminated','Under Approval') and c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id and b.num_group_id_fk = gm.group_id");
		buffer.append(" where p.num_isvalid=1 and b.num_application_id=c.application_id and  c.project_id = p.num_project_id and c.application_id = b.num_application_id and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id and b.num_group_id_fk = gm.group_id");

		buffer.append(" and p.dt_tr_date between '" + startRange + "' and '" + endRange + "'");
		if(roleId == 5) {
		    buffer.append(" and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 and  b.num_application_id=c.application_id and b.num_group_id_fk=" + assignedGroup + ")");
		} 

		buffer.append(" order by p.dt_project_start desc");
		//System.out.println("Query: " + buffer.toString());
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return obj;
		
	}
	
	
	/*----------------------- Query For Get the Closed Project Details by group Id [12-10-2023]---------------------------------*/
	public List<Object[]>  getCompletedProjectDataByGroup(int assignedGroups){
		
		
		
		StringBuffer query = new StringBuffer("select p.str_project_name,p.dt_project_start,p.dt_project_end,p.num_project_cost,");
		query.append(" (select SUM(num_received_amount) from pms_project_payment_received where num_project_id=p.num_project_id and num_isvalid=1) num_received_amount,");
		query.append("(select string_agg(Distinct a.str_emp_name,',') from pms_employee_master a , pms_employee_role_mst b where b.role_id=3 and a.emp_id=b.num_emp_id and b.num_project_id=p.num_project_id and b.num_isvalid=1) plName,");
		query.append(" cm.str_client_name,p.str_project_ref_no,e.str_group_name,b.num_proposal_cost,p.num_project_id");
		query.append(",(select SUM(num_received_amount-num_mapped_amount) from pms_project_payment_received_without_invoice where num_project_id=p.num_project_id and num_isvalid=1)");
		query.append(" from pms_project_master p, pms_project_category_master pc, trans_application b, application_project c, pms_client_master cm,pms_group_master e where ");
		query.append("p.num_isvalid=1 and b.num_group_id_fk=e.group_id and b.num_application_id=c.application_id and p.str_project_status IN ('Terminated','Completed') and ");
		query.append("p.str_closure_status='Financial' and c.project_id = p.num_project_id and c.application_id = b.num_application_id ");
		query.append("and b.num_project_category_fk = pc.num_project_category_id and b.client_id_fk= cm.client_id ");
		query.append("and p.num_project_id in (select c.project_id  from trans_application b, application_project c where b.num_isvalid=1 ");
		query.append("and b.num_application_id=c.application_id )" );
	
	
			query.append(" and b.num_group_id_fk in ("+assignedGroups+")");
		
		
		List<Object[]> obj = daoHelper.runNative(query.toString());
		
		return 	obj;
	}
	/*----------------------- EOF Query For Get the Closed Project Details [12-10-2023]---------------------------------*/
	
	
}