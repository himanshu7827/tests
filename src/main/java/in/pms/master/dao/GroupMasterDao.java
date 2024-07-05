package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;



@Repository
public class GroupMasterDao {

	@Autowired
	DaoHelper daoHelper;

	public long saveUpdateGroupMaster(GroupMasterDomain groupMasterDomain) {
		groupMasterDomain = daoHelper.merge(GroupMasterDomain.class,
				groupMasterDomain);
		return groupMasterDomain.getNumId();
	}

	public GroupMasterDomain getGroupMasterById(long id) {		
		return daoHelper.findById(GroupMasterDomain.class, id);
	}
	
	public GroupMasterDomain getGroupMasterByNameandOrganisation(String groupName,long organisationId){
		String query = "from GroupMasterDomain where lower(groupName)=lower('"+groupName+"') and organisationMasterDomain.numId="+organisationId;	
		List<GroupMasterDomain> groupMasterDomainList = daoHelper.findByQuery(query);		
		if(groupMasterDomainList.size()>0){
			return groupMasterDomainList.get(0);
		}
		return null;
	}

	public GroupMasterDomain getGroupMasterDomainByNameandOrganisation(
			String organisationName, long organisationId) {
		String query = "from GroupMasterDomain where lower(organisationName)=lower('"
				+ organisationName
				+ "') and organisationMasterDomain.numId="
				+ organisationId;
		List<GroupMasterDomain> groupMasterDomainList = daoHelper
				.findByQuery(query);
		if (groupMasterDomainList.size() > 0) {
			return groupMasterDomainList.get(0);
		}
		return null;
	}

	public List<GroupMasterDomain> getAllGroupMasterDomain() {
		String query = "from GroupMasterDomain where numIsValid<>2";
		return daoHelper.findByQuery(query);
	}

	public List<GroupMasterDomain> getAllActiveGroupMasterDomain() {
		String query = "from GroupMasterDomain where numIsValid=1";
		return daoHelper.findByQuery(query);
	}
	
	// added below method by varun to get all technical  group
	public List<GroupMasterDomain> getAllTechnicalActiveGroupMasterDomain() {
		String query = "from GroupMasterDomain where numIsValid=1 and strGroupTypeFlag='T'";
		return daoHelper.findByQuery(query);
	}
	
	public List<GroupMasterDomain> getGroupMasterByOrganisationId(long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		//String orgNames = userInfo.getAssignedOrganisation();
		
		EmployeeRoleMasterModel employeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
		String orgNames = ""+employeeRoleMasterModel.getNumOrganisationId();
			
		StringBuffer query = new StringBuffer("from GroupMasterDomain  a where a.numIsValid=1 and  a.organisationMasterDomain.numId=" + id );
		if(null!=orgNames){
			query.append(" and a.organisationMasterDomain.numId in ("+orgNames +")");		
		}	
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<GroupMasterDomain> getAllActiveGrpMasterDomain(long orgId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();		
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String groupShortNames = ""+selectedEmployeeRole.getStrGroupShortName();
		System.out.println(groupShortNames);
		
		StringBuffer query = new StringBuffer("from GroupMasterDomain a JOIN FETCH a.organisationMasterDomain ");
		query.append(" where a.numIsValid=1 and a.organisationMasterDomain.numId="+orgId);
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.numId in ("+assignedGroups +") ");			
		}
		
/*		if(roleId !=8 && roleId !=10){
			query.append(" and a.showProjects = 1");
		}*/
		// Show All Groups for HR , PMO and ED office
		/*if(roleId !=8 && roleId !=10 && roleId !=9){*/
		if(roleId !=8 && roleId !=10 && roleId !=9 && roleId != 6){//Added by devesh on 20-10-23 to show all groups to ED
			query.append(" and a.showProjects = 1");
		}
		query.append(" order by a.groupName");
		return daoHelper.findByQuery(query.toString());
	}
	
	
	public List<GroupMasterDomain> getAllActiveGroupMasterDomainByUserInfo(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		StringBuffer query =new  StringBuffer("from GroupMasterDomain a where a.numId<>0 and a.numIsValid=1");
		String groups = userInfo.getAssignedGroups();	
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){		
			query.append(" and numId in ("+assignedGroups+")");
		}
		query.append(" order by groupName");
		return  daoHelper.findByQuery(query.toString());	
	}
	
	public List<String> getdistinctGroupNames() {
		StringBuffer query = new StringBuffer(
				"select distinct  a.groupName  from GroupMasterDomain a where  a.numIsValid=1");

		List<String>groupNamesList = daoHelper.findByQuery(query.toString());
		return groupNamesList;
	}
	
	public String getGroupNames(String assignedGroups){
		String query = "select string_agg(str_group_name,',') from pms_group_master where group_id IN ("+assignedGroups+")";
		List<String> list = daoHelper.runNative(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public String getGroupShortNames(String assignedGroups){
		String query = "select string_agg(str_group_short_code,',') from pms_group_master where group_id IN ("+assignedGroups+")";
		List<String> list = daoHelper.runNative(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<String> getdistinctGroupNamesWithColor(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();	
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();	
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		StringBuffer query = new StringBuffer(
				"select distinct  a.bgColor||'='||a.groupName  from GroupMasterDomain a where  a.numIsValid=1 and a.showProjects = 1");
		
		if(assignedOrganisation != 0){
			query.append(" and a.organisationMasterDomain.numId = "+assignedOrganisation);
		}
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and a.numId = "+assignedGroups);
		}
		
		
		List<String> groupNamesList = daoHelper.findByQuery(query.toString());
		return groupNamesList;
	}
	
	public GroupMasterDomain getGroupMasterDomainById(long groupId) {
		String query = "from GroupMasterDomain where numIsValid=1 and numId="+groupId;
		List<GroupMasterDomain> list= daoHelper.findByQuery(query);
		if(list.size()>0)
			return list.get(0);
		else return null;
	}
	
	public List<GroupMasterDomain> getGroupMasterByIds(List<Long> groupId) {
		String query = "from GroupMasterDomain where numIsValid=1 and numId in :ids";
		List<GroupMasterDomain> list= daoHelper.findByIdList(query, groupId);
		 return list;
	}
	
	//Purpose TImesheet Report for PMO and ED
	public List<GroupMasterDomain> getGroupMasterDomainByOrg(long organisationId) {
		String query = "from GroupMasterDomain where numIsValid=1 and organisationMasterDomain.numId="+organisationId;
		List<GroupMasterDomain> list= daoHelper.findByQuery(query);
		return list;
	}
	
}
