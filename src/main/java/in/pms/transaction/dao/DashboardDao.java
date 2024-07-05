package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository

public class DashboardDao {

	@Autowired
	DaoHelper daoHelper;

	public List<Object[]> getgroupBusinessTypeProjectCount() {
		StringBuffer query = new StringBuffer("select a.businessType.numId,a.businessType.businessTypeName,");
		query.append(
				" a.groupMaster.numId,a.groupMaster.groupName,count(businessType) from Application a where a.numIsValid= 1");
		query.append(
				" group by a.businessType.numId,a.businessType.businessTypeName,a.groupMaster.numId,a.groupMaster.groupName");
		query.append(" order by 1");
		List<Object[]> businessTypeList = daoHelper.findByQuery(query.toString());

		return businessTypeList;
	}

	
	public List<Object[]> getgroupEmployeeTypeEmployeeCount(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("select e.empTypeMasterDomain.numId, e.empTypeMasterDomain.empshortode, ");
		query.append(
				" e.groupMasterDomain.numId,e.groupMasterDomain.groupName,count(empTypeMasterDomain),e.empTypeMasterDomain.hierarchy,e.groupMasterDomain.showProjects from EmployeeMasterDomain e ");
		query.append(" where e.numIsValid= 1 and e.employmentStatus not in ('Terminate','Relieved')");
		if(assignedOrganisation != 0){
			query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}		
		if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and e.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append(
				" group by e.empTypeMasterDomain.numId,e.empTypeMasterDomain.empshortode,e.groupMasterDomain.numId,e.groupMasterDomain.groupName,6,e.groupMasterDomain.showProjects");
		query.append(" order by 7 desc , 4,6");
		List<Object[]> empTypeList = daoHelper.findByQuery(query.toString());		
		return empTypeList;
	}
	

	public List<Object[]> getgroupProjectCount(){
		
		List<Object[]> finalList = new ArrayList<Object[]>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole= userInfo.getSelectedEmployeeRole();
		if(selectedRole.getNumRoleId()!=0){
		
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		String assignedOrganisation = ""+selectedEmployeeRole.getNumOrganisationId();
		String assignedGroups = ""+selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		
		int currentYear = calendar.get(Calendar.YEAR);
		int lastYear = calendar.get(Calendar.YEAR)-1;
		int secondLastYear = calendar.get(Calendar.YEAR)-2;

		String strStart = "01/01/";
		String strEnd = "31/12/";
		
		int financialYear1;
		int financialYear2;
		int financialYear3;
		
		String strfinancialYear1 = "";
		String strfinancialYear2 = "";
		String strfinancialYear3 = "";
		
		Date year1Start = new Date();
		Date year1End = new Date();
		
		Date year2Start = new Date();
		Date year2End = new Date();
		
		Date year3Start = new Date();
		Date year3End = new Date();
//================================	one yearwise ====================================
		try {
			financialYear1 = currentYear;
			strfinancialYear1 = Integer.toString(financialYear1);
			year1Start = DateUtils.dateStrToDate(strStart+financialYear1);
			year1End = new Date();
			
			financialYear2 = (currentYear-1);
			strfinancialYear2 = Integer.toString(financialYear2);
			year2Start = DateUtils.dateStrToDate(strStart+lastYear);
			year2End = DateUtils.dateStrToDate(strEnd+lastYear);
			
			financialYear3 = (currentYear-2);
			strfinancialYear3 = Integer.toString(financialYear3);
			year3Start = DateUtils.dateStrToDate(strStart+secondLastYear);
			year3End = DateUtils.dateStrToDate(strEnd+secondLastYear);
			
		} catch (ParseException e) {				
			e.printStackTrace();
		}
		
		
//----------------------------------Financial yearwise---------------------------------		
		/*if(currentMonth<=2){
			
			try {
				financialYear1 = lastYear +" - "+currentYear;
				year1Start = DateUtils.dateStrToDate(strStart+lastYear);
				year1End = new Date();
				
				financialYear2 = secondLastYear +" - "+lastYear;
				year2Start = DateUtils.dateStrToDate(strStart+secondLastYear);
				year2End = DateUtils.dateStrToDate(strEnd+lastYear);
				
				financialYear3 =  (secondLastYear-1) +" - "+secondLastYear;
				year3Start = DateUtils.dateStrToDate(strStart+(secondLastYear-1));
				year3End = DateUtils.dateStrToDate(strEnd+secondLastYear);
				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			
		}else{
			
			try {
				financialYear1 = currentYear+" - "+(currentYear+1);
				year1Start = DateUtils.dateStrToDate(strStart+currentYear);
				year1End = new Date();
				
				financialYear2 = lastYear+" - "+currentYear;
				year2Start = DateUtils.dateStrToDate(strStart+lastYear);
				year2End = DateUtils.dateStrToDate(strEnd+currentYear);
				
				financialYear3 = secondLastYear+" - "+lastYear;
				year3Start = DateUtils.dateStrToDate(strStart+secondLastYear);
				year3End = DateUtils.dateStrToDate(strEnd+lastYear);
				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}*/
		
		
		StringBuffer query = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+strfinancialYear1+"',count(a) from ProjectMasterDomain a  ");
		query.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and  a.dtProjectStartDate between '"+year1Start +"' and '"+year1End+"'");
		
		StringBuffer query1 = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+strfinancialYear2+"',count(a) from ProjectMasterDomain a  ");
		query1.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and a.dtProjectStartDate between '"+year2Start +"' and '"+year2End +"'");
		
		StringBuffer query2 = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+strfinancialYear3+"',count(a) from ProjectMasterDomain a  ");
		query2.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and a.dtProjectStartDate between '"+year3Start +"' and '"+year3End +"'");
		

		
		if(null != assignedOrganisation){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			query1.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			query2.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		/*if(null != assignedGroups  && !(assignedGroups.equals("0") || assignedGroups.equals("8"))){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
			query1.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
			query2.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}*/
		
		if(roleId == 5){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
			query1.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
			query2.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		query.append(" group by 1,2,3");
		query1.append(" group by 1,2,3");
		query2.append(" group by 1,2,3");
		
		
		
				
		finalList.addAll(daoHelper.findByQuery(query2.toString()));
		finalList.addAll(daoHelper.findByQuery(query1.toString()));
		finalList.addAll(daoHelper.findByQuery(query.toString()));
		
		}
		return finalList;
	}



	public long getTotalActiveEmployees(){
		int result = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
	/*	String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuilder  query = new StringBuilder ("select count(e)"); 
		query.append(" from EmployeeMasterDomain e where e.numIsValid=1 and e.employmentStatus not in ('Terminate','Relieved')");
		
		if(assignedOrganisation != 0){
			/*query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
			//Above query modified by devesh on 04-06-24 to include ED designation in count
			query.append(" and (e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +") or e.designationMaster.numId=31)");
		}		
		if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
			query.append(" and e.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		
		List<Long> countempList = daoHelper.findByQuery(query.toString());		
		if(countempList!=null && countempList.size()>0 && countempList.get(0)!=null){			
			result = countempList.get(0).intValue();
		}
		return result;
	}
	
	
	
	public List<Object[]> getAllActiveProjectName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		
		StringBuilder  query = new StringBuilder("select a,a.application.groupMaster from ProjectMasterDomain a ");
		query.append(" where a.strProjectStatus not IN ('Terminated','Completed') and a.numIsValid=1 ");
		if(null != assignedOrganisation){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
	if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		List<Object[]> allProjects = daoHelper.findByQuery(query.toString());

		return allProjects;
	}

	
	public List<Object[]> getProjectCountByGroupandBusinessType(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		StringBuffer query = new StringBuffer("select count(a),a.application.groupMaster.groupName,a.application.businessType.businessTypeName from ProjectMasterDomain a ");		
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
		if(null != assignedOrganisation){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		query.append(" group by 2,3  order by 2 ");
		
		return daoHelper.findByQuery(query.toString());
	}
	
	
	public List<Object[]> getProjectCountByBusinessType(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		StringBuffer query = new StringBuffer("select count(a),a.application.businessType.businessTypeName from ProjectMasterDomain a ");		
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
		
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}

		
		if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		
		query.append(" group by 2  order by 2 ");
		
		return daoHelper.findByQuery(query.toString());
	}
	
	
	public List<Object[]> getProjectCostByBusinessType(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		String assignedProjects = userInfo.getAssignedProjects();
		StringBuffer query = new StringBuffer("select sum(a.projectCost),a.application.businessType.businessTypeName from ProjectMasterDomain a ");		
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		query.append(" group by 2  order by 2 ");
		
		return daoHelper.findByQuery(query.toString());
	}

	////Query to find count of total ongoing projects based on organization
	
	public int getOngoingProjectsCountByOrganisation(){
		int result = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		*/
		String assignedProjects = userInfo.getAssignedProjects();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		int assignedProject = selectedEmployeeRole.getNumProjectId();
		StringBuffer query = new StringBuffer("select count(a) from ProjectMasterDomain a ");
		/*Bhavesh(28-07-23)
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");*/
		query.append(" where a.numIsValid=1 and a.strProjectStatus IN ('Ongoing')");
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}		
	
		if(roleId != 6 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
	
		if( assignedProject !=0 && roleId != 6 && roleId != 9 && roleId !=8 && roleId !=5){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		List<Long> temp = daoHelper.findByQuery(query.toString());
		
		if(temp!=null && temp.size()>0 && temp.get(0)!=null){
			result = temp.get(0).intValue();
		}
		return result;
		
	}
	////Query to find count of total ongoing projects cost based on organization
	public double getOngoingProjectsCostByOrganisation(){
		double result = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		String assignedProjects = userInfo.getAssignedProjects();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups =  selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("select sum(a.projectCost) from ProjectMasterDomain a ");	
		/*Bhavesh(28-07-23)
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");*/
		query.append(" where a.numIsValid=1 and a.strProjectStatus IN ('Ongoing')");
		//System.out.println(assignedOrganisation);
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
	/*if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}*/
		
		if(roleId != 6 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
	if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
	System.out.print("ONGOING COST:"+query);
		List<Double> temp = daoHelper.findByQuery(query.toString());		
		if(temp!=null && temp.size()>0 && temp.get(0)!=null){
			result = temp.get(0);
		}
		return result;		
	}

/* --------------------------------------------------------- */
//  Query to get Total outlay share in 19/05/2023
	
	public String getCDACoutlay() {
		String result="";
		StringBuilder  query = new StringBuilder ("Select sum(a.num_proposal_cost) from pms.trans_application a,pms.pms_project_master b,pms.application_project c where b.num_project_id=c.project_id and a.num_application_id=c.application_id ");
		/*Bhavesh(28-07-23)
		query.append("and a.num_isvalid=1 and a.is_converted_to_project='true' and b.str_project_status not IN ('Terminated','Completed') "); */
		query.append("and a.num_isvalid=1 and a.is_converted_to_project='true' and b.str_project_status IN ('Ongoing') ");
		List<Double> dataList = daoHelper.runNative(query.toString());
		if(dataList.size()>0){				
			result = dataList.get(0)+"";
		}
		return result;
	}

/* --------------------------------------------------------- */	
	////Query to find count of total ongoing projects and their costs based on group and organization
	public List<Object[]> getProjectCountandCostByGroup(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		String assignedProjects = userInfo.getAssignedProjects();
		
		StringBuffer query = new StringBuffer("select count(a),sum(a.projectCost),a.application.groupMaster.groupName,sum(a.application.totalProposalCost) from ProjectMasterDomain a");		
		/*Bhavesh(28-07-23)
		query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");*/
		query.append(" where a.numIsValid=1 and a.strProjectStatus  IN ('Ongoing')");
		
		
		if(assignedOrganisation != 0){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
	/*	
	if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}*/
		if(roleId != 6 && roleId != 9 && assignedGroups != 0){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
	
		
		if ((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length() == 1))
				&& roleId != 6 && roleId != 8 && roleId != 5) {
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		
		
		query.append(" group by 3  order by 3 ");
		return daoHelper.findByQuery(query.toString());
	}
	
	//Query for Project wise Expenditure
	//Query for Expenditure Table for Project Expenditure group-wise and project-wise
	public List<Object[]> getProjectWiseExpenditure(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		String assignedProjects = userInfo.getAssignedProjects();
		StringBuilder query = new StringBuilder("select a.application.groupMaster.groupName,a.strProjectName,a.numId ");
		query.append(" , (select sum(numExpenditureAmount) from ProjectExpenditureDetailsDomain b where b.numIsValid=1 and b.projectMasterDomain.numId=a.numId)");
		query.append(" , (select sum(salaryInProject) from ManpowerUtilizationDetails c where c.numIsValid=1 and c.projectMasterDomain.numId=a.numId)");

		query.append(" from ProjectMasterDomain a where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
		if(null != assignedOrganisation){
			query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		}
		/*if(null != assignedProjects){
			query.append(" and a.numId in ("+assignedProjects+")");
		}*/
		if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			query.append(" and a.numId in ("+assignedProjects+")");
		}
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<Object[]> getGenderWiseEmployeeCount(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		/*String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();*/
		
		EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
		int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
		int assignedGroups = selectedEmployeeRole.getNumGroupId();
		int roleId = selectedEmployeeRole.getNumRoleId();
		
		StringBuffer query = new StringBuffer("select e.gender, count(e.gender) ");
		query.append(" from EmployeeMasterDomain e ");
		query.append(" where e.numIsValid= 1 and e.employmentStatus not in ('Terminate','Relieved')");
		if( assignedOrganisation != 0){
			query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
		}		
		if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
			query.append(" and e.groupMasterDomain.numId in ("+assignedGroups+")");
		}
		query.append(" group by 1 order by 1");		
		List<Object[]> empGenderList = daoHelper.findByQuery(query.toString());	
		return empGenderList;
	}
	
public List<Object[]> getProjectDetailGraph(){
		
		List<Object[]> finalList = new ArrayList<Object[]>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		String assignedOrganisation = userInfo.getAssignedOrganisation();
		String assignedGroups = userInfo.getAssignedGroups();
		
		
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		
		int currentYear = calendar.get(Calendar.YEAR);
		int lastYear = calendar.get(Calendar.YEAR)-1;
		int secondLastYear = calendar.get(Calendar.YEAR)-2;

		String strStart = "01/04/";
		String strEnd = "31/03/";
		
		String financialYear1 ="";
		String financialYear2 ="";
		String financialYear3 ="";
		
		Date year1Start = new Date();
		Date year1End = new Date();
		
		Date year2Start = new Date();
		Date year2End = new Date();
		
		Date year3Start = new Date();
		Date year3End = new Date();
		
		if(currentMonth<=2){
			
			try {
				financialYear1 = lastYear +" - "+currentYear;
				year1Start = DateUtils.dateStrToDate(strStart+lastYear);
				year1End = new Date();
				
				financialYear2 = secondLastYear +" - "+lastYear;
				year2Start = DateUtils.dateStrToDate(strStart+secondLastYear);
				year2End = DateUtils.dateStrToDate(strEnd+lastYear);
				
				financialYear3 =  (secondLastYear-1) +" - "+secondLastYear;
				year3Start = DateUtils.dateStrToDate(strStart+(secondLastYear-1));
				year3End = DateUtils.dateStrToDate(strEnd+secondLastYear);
				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			
		}else{
			
			try {
				financialYear1 = currentYear+" - "+(currentYear+1);
				year1Start = DateUtils.dateStrToDate(strStart+currentYear);
				year1End = new Date();
				
				financialYear2 = lastYear+" - "+currentYear;
				year2Start = DateUtils.dateStrToDate(strStart+lastYear);
				year2End = DateUtils.dateStrToDate(strEnd+currentYear);
				
				financialYear3 = secondLastYear+" - "+lastYear;
				year3Start = DateUtils.dateStrToDate(strStart+secondLastYear);
				year3End = DateUtils.dateStrToDate(strEnd+lastYear);
				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		//trunc(a.dtProjectStartDate) between trunc("+year1Start +") and trunc("+year1End +")");
		
		StringBuffer query = new StringBuffer(" select count(a.projectMasterDomain),count(a),'"+financialYear1+"' from ProjectPaymentReceivedDomain a  ");
		query.append(" where a.numIsValid=1  and  a.projectMasterDomain.dtProjectStartDate between '"+year1Start +"' and '"+year1End+"'");
		
		StringBuffer query1 = new StringBuffer(" select count(a.projectMasterDomain),count(a),'"+financialYear2+"' from ProjectPaymentReceivedDomain a  ");
		query1.append(" where a.numIsValid=1  and  a.projectMasterDomain.dtProjectStartDate between '"+year2Start +"' and '"+year2End +"'");
		
		StringBuffer query2 = new StringBuffer(" select count(a.projectMasterDomain),count(a),'"+financialYear3+"' from ProjectPaymentReceivedDomain a  ");
		query2.append(" where a.numIsValid=1  and a.projectMasterDomain.dtProjectStartDate between '"+year3Start +"' and '"+year3End +"'");
		

		
		if(null != assignedOrganisation){
			query.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			query1.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			query2.append(" and a.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		}
		
		if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
			query.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			query1.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			query2.append(" and a.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
		}
		
		query.append(" group by 3");
		query1.append(" group by 3");
		query2.append(" group by 3");
		
		
		
				
		finalList.addAll(daoHelper.findByQuery(query.toString()));
		finalList.addAll(daoHelper.findByQuery(query1.toString()));
		finalList.addAll(daoHelper.findByQuery(query2.toString()));
		
		
		return finalList;
	}

public List<Object[]> getgroupProposalCount(){
	
	List<Object[]> finalList = new ArrayList<Object[]>();
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	Calendar calendar = Calendar.getInstance();
		
	int currentYear = calendar.get(Calendar.YEAR);
	int lastYear = calendar.get(Calendar.YEAR)-1;
	int secondLastYear = calendar.get(Calendar.YEAR)-2;

	String strStart = "01/01/";
	String strEnd = "31/12/";
	
	String financialYear1 ="";
	String financialYear2 ="";
	String financialYear3 ="";
	
	Date year1Start = new Date();
	Date year1End = new Date();
	
	Date year2Start = new Date();
	Date year2End = new Date();
	
	Date year3Start = new Date();
	Date year3End = new Date();
		
	try {
		financialYear1 = ""+currentYear;		
		year1Start = DateUtils.dateStrToDate(strStart+financialYear1);
		year1End = new Date();
		
		financialYear2 = ""+(currentYear-1);
		year2Start = DateUtils.dateStrToDate(strStart+lastYear);
		year2End = DateUtils.dateStrToDate(strEnd+lastYear);
		
		financialYear3 = ""+(currentYear-2);
		year3Start = DateUtils.dateStrToDate(strStart+secondLastYear);
		year3End = DateUtils.dateStrToDate(strEnd+secondLastYear);
		
	} catch (ParseException e) {				
		e.printStackTrace();
	}
	
	StringBuffer query = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+financialYear1+"',count(a) from ProposalMasterDomain a  ");
	query.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and  a.dateOfSubmission between '"+year1Start +"' and '"+year1End+"'");
	
	StringBuffer query1 = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+financialYear2+"',count(a) from ProposalMasterDomain a  ");
	query1.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and a.dateOfSubmission between '"+year2Start +"' and '"+year2End +"'");
	
	StringBuffer query2 = new StringBuffer(" select a.application.groupMaster.numId,a.application.groupMaster.groupName,'"+financialYear3+"',count(a) from ProposalMasterDomain a  ");
	query2.append(" where a.numIsValid=1  and a.application.groupMaster.showProjects = 1 and a.dateOfSubmission between '"+year3Start +"' and '"+year3End +"'");
	

	
	if(assignedOrganisation != 0){
		query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		query1.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
		query2.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
	}
	
	if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		query1.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
		query2.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
	}
	
	query.append(" group by 1,2,3");
	query1.append(" group by 1,2,3");
	query2.append(" group by 1,2,3");
	
	
	
			
	finalList.addAll(daoHelper.findByQuery(query2.toString()));
	finalList.addAll(daoHelper.findByQuery(query1.toString()));
	finalList.addAll(daoHelper.findByQuery(query.toString()));
	
	
	return finalList;
}


public List<Object[]> getOngoinProjectlist(long groupId){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	
	int roleId = selectedEmployeeRole.getNumRoleId();
	String assignedProjects = userInfo.getAssignedProjects();
	
	StringBuffer query = new StringBuffer("select count(a),sum(a.projectCost),a.application.groupMaster.groupName from ProjectMasterDomain a ");		
	query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");
	
	if(assignedOrganisation != 0){
		query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
	}
/*	
if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
		query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
	}*/
	
		query.append(" and a.application.groupMaster.numId in ("+groupId+")");
	

	
	if ((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length() == 1))
			&& roleId != 6 && roleId != 8 && roleId != 5) {
		query.append(" and a.numId in ("+assignedProjects+")");
	}
	
	
	query.append(" group by 3  order by 3 ");
	return daoHelper.findByQuery(query.toString());
}

public long getActiveEmployeesAtCDAC(){
	int result = 0;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
/*	String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuilder  query = new StringBuilder ("select count(e)"); 
	query.append(" from EmployeeMasterDomain e where e.numIsValid=1 and e.numDeputedAt=1 and  e.employmentStatus not in ('Terminate','Relieved')");
	
	if(assignedOrganisation != 0){
		/*query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
		//Above query modified by devesh on 04-06-24 to include ED designation in count
		query.append(" and (e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +") or e.designationMaster.numId=31)");
	}		
	if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and e.groupMasterDomain.numId in ("+assignedGroups+")");
	}
	
	List<Long> countempList = daoHelper.findByQuery(query.toString());		
	if(countempList!=null && countempList.size()>0 && countempList.get(0)!=null){			
		result = countempList.get(0).intValue();
	}
	return result;
}

//added by devesh on 14/06/23 for clientemployeecount
public long getActiveEmployeesAtCLIENT(){
	int result = 0;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
/*	String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuilder  query = new StringBuilder ("select count(e)"); 
	query.append(" from EmployeeMasterDomain e where e.numIsValid=1 and e.numDeputedAt=2 and  e.employmentStatus not in ('Terminate','Relieved')");
	
	if(assignedOrganisation != 0){
		/*query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");*/
		//Above query modified by devesh on 04-06-24 to include ED designation in count
		query.append(" and (e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +") or e.designationMaster.numId=31)");
	}		
	if(roleId != 6 && roleId != 8 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and e.groupMasterDomain.numId in ("+assignedGroups+")");
	}
	
	List<Long> countempList = daoHelper.findByQuery(query.toString());		
	if(countempList!=null && countempList.size()>0 && countempList.get(0)!=null){			
		result = countempList.get(0).intValue();
	}
	return result;
}
//End

//Added by Devesh on 13-10-23 to get last updated at status for employees
public List<Object[]> getLastUpdatedAtDateForEmployees(){
	int result = 0;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
/*	String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuilder  query = new StringBuilder ("SELECT t.dt_tr_date AS latest_transaction_date, t.str_emp_name FROM pms.pms_employee_master t WHERE t.dt_tr_date = (SELECT MAX(dt_tr_date) FROM pms.pms_employee_master);"); 

	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;
}
//End of Function

public List<Object[]> getGroupGenderWiseEmployeeCount(String groupName){
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	
	/*String assignedOrganisation = userInfo.getAssignedOrganisation();
	String assignedGroups = userInfo.getAssignedGroups();*/
	
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	//int assignedGroups = selectedEmployeeRole.getNumGroupId();
	//int roleId = selectedEmployeeRole.getNumRoleId();
	
	StringBuffer query = new StringBuffer("select e.gender, count(e.gender) ");
	query.append(" from EmployeeMasterDomain e ");
	query.append(" where e.numIsValid= 1 and e.employmentStatus not in ('Terminate','Relieved')");
	if( assignedOrganisation != 0){
		query.append(" and e.groupMasterDomain.organisationMasterDomain.numId in ("+assignedOrganisation +")");
	}		
	/*if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){*/
		query.append(" and e.groupMasterDomain.groupName in ('"+groupName+"')");
	/*}*/
	query.append(" group by 1 order by 1");		
	List<Object[]> empGenderList = daoHelper.findByQuery(query.toString());	
	return empGenderList;
}


////Query to find count of total ongoing projects and their costs based on group and organization
public List<Object[]> getOngoingProjectCount(String projectIdString,Date StartDate,Date EndDate){


StringBuffer query = new StringBuffer("select p.num_custom_id from pms_transaction_master p");		
/*Bhavesh(28-07-23)
query.append(" where a.numIsValid=1 and a.strProjectStatus not IN ('Terminated','Completed')");*/
query.append(" where p.num_isvalid=1 and p.dt_tr_date between '"+StartDate+"' and '"+EndDate+"' and p.num_action_id_fk = 12 and p.num_role_id_fk = 9 and p.num_workflow_type_id_fk = 3");



/*	
if(null != assignedGroups && !(assignedGroups.contains("0") || assignedGroups.contains("8")) && assignedGroups.length()==1){
	query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
}*/
if(projectIdString !=null|| projectIdString !=""){
	query.append(" and p.num_custom_id in ("+projectIdString+")");
}


return daoHelper.findByQuery(query.toString());
}
	
}


