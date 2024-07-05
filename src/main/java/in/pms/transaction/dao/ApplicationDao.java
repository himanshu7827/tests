package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.transaction.domain.Application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationDao {
	@Autowired
	DaoHelper daoHelper;
	
	public Application mergeApplication(Application application){
		return daoHelper.merge(Application.class, application);
	}
	
	public List<Application> getApplicationByGroupName(String groupName){
		//System.out.println(groupName);
		String query = "select a from Application a  join fetch a.projectMaster b where a.numIsValid=1 and  a.groupMaster.groupName='"+groupName+"'";
		
		return 
				
				daoHelper.findByQuery(query);
	}
	

	public Application getApplicationById(long applicationId){				
		return daoHelper.findById(Application.class,applicationId);
	}
	public List<Application> getAllApplicaionData(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedRole.getNumRoleId();
		int selectedOrganisation = selectedRole.getNumOrganisationId();
		int selectedGroup = selectedRole.getNumGroupId();
		/*List<EmployeeRoleMasterModel> list=userInfo.getEmployeeRoleList();
		boolean userSpecific=true;
		 for (int i = 0; i < list.size(); i++) {
		    	EmployeeRoleMasterModel employeeRoleModel = list.get(i);
				long roleid=employeeRoleModel.getNumRoleId();
				if(roleid ==5 || roleid==6 ){
					userSpecific = false;
					break;
				}
		    }*/
		 
		StringBuilder query = new StringBuilder("from Application a left join fetch  a.groupMaster g left join fetch a.proposalMaster p where a.numIsValid=1 ");
		if(roleId != 5 && roleId != 6){
			query.append("and a.numTrUserId="+userInfo.getEmployeeId() +" and p.status in ('SUB','REV','SAD') and a.convertedToProject = false");
		}else{
			query.append("and p.status in ('SUB','REV') and a.convertedToProject = false");
		}		
		
		if(selectedOrganisation != 0){
			query.append(" and a.groupMaster.organisationMasterDomain.numId in ("+selectedOrganisation+")");
		}		
		if(selectedGroup != 0){
			query.append(" and a.groupMaster.numId in ("+selectedGroup+")");
		}
	
		return  daoHelper.findByQuery(query.toString());	
	}
	
	//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero
	public List<Object[]> getAllApplicationDataList(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedRole.getNumRoleId();
		int selectedOrganisation = selectedRole.getNumOrganisationId();
		int selectedGroup = selectedRole.getNumGroupId();
		//Added by devesh on 16-02-24 to get employee ids of employees mapped in common projects of HOD and PI
		List<Long> numEmployeeIds=getEmployeeIds(userInfo.getEmployeeId());
		StringBuilder numEmployeeIdsString = new StringBuilder();
		for (Long employeeId : numEmployeeIds) {
		    if (numEmployeeIdsString.length() > 0) {
		    	numEmployeeIdsString.append(",");
		    }
		    numEmployeeIdsString.append(employeeId);
		}
		//End of getting employee Ids
		/*List<EmployeeRoleMasterModel> list=userInfo.getEmployeeRoleList();
		boolean userSpecific=true;
		 for (int i = 0; i < list.size(); i++) {
		    	EmployeeRoleMasterModel employeeRoleModel = list.get(i);
				long roleid=employeeRoleModel.getNumRoleId();
				if(roleid ==5 || roleid==6 ){
					userSpecific = false;
					break;
				}
		    }*/
		 
		StringBuilder query = new StringBuilder();
        query.append("SELECT a.num_application_id,a.str_proposal_title,prjtyp.str_project_type_name,categ.str_category_name,c.str_organisation_name,g.str_group_name,a.num_proposal_cost,p.str_proposal_ref_no,p.str_status FROM trans_application a");
        query.append(" JOIN application_proposal b ON a.num_application_id = b.application_id");
        query.append(" LEFT JOIN pms_proposal_master p ON b.proposal_id = p.proposal_id");
        query.append(" JOIN pms_group_master g ON g.group_id = CASE WHEN p.group_id <> 0 THEN p.group_id ELSE a.num_group_id_fk END");
        query.append(" JOIN pms_organisation_master c ON g.organisation_id = c.organisation_id");
        query.append(" JOIN pms_project_category_master categ ON categ.num_project_category_id = a.num_project_category_fk");
        query.append(" JOIN pms_project_type_master prjtyp ON prjtyp.num_project_type_id = a.num_project_type_fk");
        query.append(" WHERE a.num_isvalid = 1");
        // handle condition for GC [29-04-2024] 
        if(roleId == 5){
			query.append("and p.str_status in ('SUB','REV') and a.is_converted_To_Project = false");
		}else if (roleId == 4 || roleId == 15){
			query.append("and a.num_tr_user_id in ("+numEmployeeIdsString+") and p.str_status in ('SUB','REV','SAD') and a.is_converted_To_Project = false");
		}else{
			// handle condition for all other than GC,PI,HOD [29-04-2024] 
			query.append("and a.num_tr_user_id="+userInfo.getEmployeeId() +" and p.str_status in ('SUB','REV','SAD') and a.is_converted_To_Project = false");
		}		
		
		if(selectedOrganisation != 0){
			query.append(" AND c.organisation_id IN ("+selectedOrganisation+")");
		}		
		if(selectedGroup != 0){
			query.append(" and g.group_id in ("+selectedGroup+")");
		}
	
		//System.out.println("query.." + query);

        List<Object[]> obj = daoHelper.runNative(query.toString());
		return 	obj;	
	}
	//End of query
	
	//Function added by devesh on 15-02-24 to get employee ids of employee mapped in common projects with user
	public List<Long> getEmployeeIds(long numEmpId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedRole.getNumRoleId();
		ArrayList<Object> paraList = new ArrayList<>();
		StringBuffer query = new StringBuffer("Select distinct emp.numEmpId from EmployeeRoleMasterDomain emp where emp.numProjectId in (Select distinct e.numProjectId from EmployeeRoleMasterDomain e where e.numIsValid=1 and  e.roleMasterDomain.numId in(1,2,3,4,15) and e.numEmpId=?0) ");
		if(roleId == 15){
			query.append("and emp.numIsValid=1 and  emp.roleMasterDomain.numId in(1,2,3,4,15)");
		}
		else{
			query.append("and emp.numIsValid=1 and  emp.roleMasterDomain.numId in(1,2,3,4)");
		}
		paraList.add(numEmpId);
		return daoHelper.findByQuery(query.toString(), paraList);
	}
	//End of function
	
	public  List<Application>  getProposalDetailByGruopId(long groupId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		List<EmployeeRoleMasterModel> list=userInfo.getEmployeeRoleList();
		boolean userSpecific=true;
		 for (int i = 0; i < list.size(); i++) {
		    	EmployeeRoleMasterModel employeeRoleModel = list.get(i);
				long roleid=employeeRoleModel.getNumRoleId();
				if(roleid ==5 || roleid==6 ){
					userSpecific = false;
					break;
				}
		    }
		 
		StringBuilder query = new StringBuilder("from Application a left join fetch  a.groupMaster g left join fetch a.proposalMaster p where a.numIsValid=1 and g.numId="+groupId);
		if(userSpecific){
			query.append("and a.numTrUserId="+userInfo.getEmployeeId());
		}else{
			if(assignedGroups.contains("8"))
				query.append("and p.status in ('SUB','REV','SAD')");
			else 
				query.append("and p.status in ('SUB','REV')");
		}
		
		
		if(null != assignedOrganisation){
			query.append(" and a.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		/*if(null != assignedGroups && !(assignedGroups.contains("0") && assignedGroups.length()==1)){*/
		if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8"))){
			query.append(" and a.groupMaster.numId in ("+assignedGroups+")");
		}
	
		return  daoHelper.findByQuery(query.toString());
	}
	
	//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero
		public  List<Object[]>  getProposalDetailByGruopIdnew(long groupId){
			long roleid=0;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();
			List<EmployeeRoleMasterModel> list=userInfo.getEmployeeRoleList();
			boolean userSpecific=true;
			 for (int i = 0; i < list.size(); i++) {
			    	EmployeeRoleMasterModel employeeRoleModel = list.get(i);
					roleid=employeeRoleModel.getNumRoleId();
					if(roleid ==5 || roleid==6 ){
						userSpecific = false;
						break;
					}
			    }
			 System.out.println("roleid..."+roleid);
			
			 StringBuilder query = new StringBuilder();
		        query.append("SELECT a.num_application_id,a.str_proposal_title,prjtyp.str_project_type_name,categ.str_category_name,c.str_organisation_name,g.str_group_name,a.num_proposal_cost,p.str_proposal_ref_no,p.str_status FROM trans_application a");
		        query.append(" JOIN pms_group_master g ON (");
		        query.append(" SELECT CASE WHEN d.group_id <> 0 THEN d.group_id ELSE a.num_group_id_fk END");
		        query.append(" FROM pms_proposal_master d, application_proposal e");
		        query.append(" WHERE e.proposal_id = d.proposal_id AND e.application_id = a.num_application_id");
		        query.append(" LIMIT 1");
		        query.append(" ) = g.group_id");
		        query.append(" JOIN application_proposal b ON a.num_application_id = b.application_id");
		        query.append(" LEFT JOIN pms_proposal_master p ON b.proposal_id = p.proposal_id");
		        query.append(" JOIN pms_organisation_master c ON g.organisation_id = c.organisation_id");
		        query.append(" JOIN pms_project_category_master categ ON categ.num_project_category_id = a.num_project_category_fk");
		        query.append(" JOIN pms_project_type_master prjtyp ON prjtyp.num_project_type_id = a.num_project_type_fk");
		        query.append(" WHERE a.num_isvalid = 1");
		        query.append("   AND g.group_id = "+groupId);

		        if (userSpecific) {
		            query.append("AND a.num_tr_user_id ="+userInfo.getEmployeeId());
		        } else {
		        	if(assignedGroups.contains("8"))
		        		query.append("AND p.str_status IN ('SUB', 'REV', 'SAD')");
					else 
						query.append("and p.str_status in ('SUB','REV')");
		        }

		        query.append("AND c.organisation_id IN ("+assignedOrganisation+")");
		        query.append(" order by p.dt_tr_date");

		        System.out.println("query.." + query);

		        List<Object[]> obj = daoHelper.runNative(query.toString());
				return 	obj;

		}
		//End of query
	
	public List<Object[]> getProposalCountByGroup(){
		/*StringBuilder query =  new StringBuilder("select count(a),count(a.) from Apllication a");
		query.append(" a.numIsValid=1 and a.convertedToProject='t'");*/
		
		/*StringBuilder query = new StringBuilder("select count(*) from  ProposalMasterDomain a where a.numIsValid=1 and ");*/
		
		return null;
	}
	
	
	public String createApplicationCopy(long applicationId){
		return daoHelper.findByFun1("makeApplicationCopy", applicationId);
	}
	
	// Handle the null pointer exception for thrust area by varun
	public Application getThrustAreaByApplication(long applicationId){
		String query = "select distinct a from  Application a join fetch a.thrustAreas where a.numId="+applicationId;		
		Application application = null;
		List<Application> applications = daoHelper.findByQuery(query);
		if(null != applications && !applications.isEmpty()){
			return applications.get(0);
		}
		return null;
	}
	
	public long getProposalIdByProjectId(long projectId){
		ProjectMasterDomain projectMasterDomain = daoHelper.findById(ProjectMasterDomain.class,projectId);
		StringBuffer  query = new StringBuffer("select distinct a from Application a join fetch  a.proposalMaster b ");
		query.append(" where b.numIsValid =1 and a.numId ="+projectMasterDomain.getApplication().getNumId());
		List<Application> applications = daoHelper.findByQuery(query.toString());
		if(null != applications && applications.size()>0){
			List<ProposalMasterDomain> proposals = applications.get(0).getProposalMaster();
			if(null != proposals && proposals.size()>0){
				return proposals.get(0).getNumId();
			}
		}
		return 0;
	}
	
	/*Added by devesh on 25-01-24 to get proposal with date range date range*/
	public long getProposalIdByProjectIdAndDate(long projectId, String startRange, String endRange){
		
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
		
		ProjectMasterDomain projectMasterDomain = daoHelper.findById(ProjectMasterDomain.class,projectId);
		
		StringBuffer  query = new StringBuffer("select distinct a from Application a join fetch  a.proposalMaster b ");
		query.append(" where b.numIsValid =1 and a.numId ="+projectMasterDomain.getApplication().getNumId());
		query.append("and  b.dateOfSubmission between '"+startDate+ "' and '"+ endDate+"'");
		List<Application> applications = daoHelper.findByQuery(query.toString());
		if(null != applications && applications.size()>0){
			List<ProposalMasterDomain> proposals = applications.get(0).getProposalMaster();
			if(null != proposals && proposals.size()>0){
				return proposals.get(0).getNumId();
			}
		}
		return 0;
	}
	/*End of function*/
	
	
	public List<ProposalMasterDomain> getProposalByApplicationId(long applicationId){
		String query = "from ProposalMasterDomain pm where pm.numIsValid=1 and pm.application.numId="+applicationId;
		return daoHelper.findByQuery(query);
		}
}
