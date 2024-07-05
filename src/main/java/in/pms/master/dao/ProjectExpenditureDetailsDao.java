package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectExpenditureDetailsDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectExpenditureDetailsDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateProjectExpenditureDetails(ProjectExpenditureDetailsDomain projectExpenditureDetailsDomain){
		
		projectExpenditureDetailsDomain =daoHelper.merge(ProjectExpenditureDetailsDomain.class, projectExpenditureDetailsDomain);		
			return projectExpenditureDetailsDomain.getNumId();
		}
		
		public ProjectExpenditureDetailsDomain getProjectExpenditureDetailsById(long id){
			List<ProjectExpenditureDetailsDomain> projectExpenditureDetailsList =  daoHelper.findByQuery("from ProjectExpenditureDetailsDomain where numId="+id);
			if(projectExpenditureDetailsList.size()>0){
				return projectExpenditureDetailsList.get(0);
			}
			return null;
		}
		
		/*public ProjectExpenditureDetailsDomain getProjectInvoiceMasterByRefNo(String strInvoiceRefno,long projectId){
			String query = "from ProjectExpenditureDetailsDomain where lower(strInvoiceRefno)=lower('"+strInvoiceRefno+"') and projectId="+projectId+" and numIsValid<>2" ;	
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomainList = daoHelper.findByQuery(query);		
			if(projectInvoiceMasterDomainList.size()>0){
				return projectInvoiceMasterDomainList.get(0);
			}
			return null;
		}*/

		public List<ProjectExpenditureDetailsDomain> getAllProjectExpenditureDetailsDomain(){
			String query = "from ProjectExpenditureDetailsDomain where numId<>0 and numIsValid<>2";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<ProjectExpenditureDetailsDomain> getAllActiveProjectExpenditureDetailsDomain(){
			String query = "from ProjectExpenditureDetailsDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<ProjectExpenditureDetailsDomain> getProjectExpenditureDetailsByProjectId(long numProjectId) {
			String query = "from ProjectExpenditureDetailsDomain e where e.numIsValid<>2 and e.projectMasterDomain.numId="+numProjectId;
			return daoHelper.findByQuery(query);
		}
		
		
		
		
		public List<Object[]> getExpenditureDetailsByProjectId(long numProjectId) {
			
			StringBuffer buffer = new StringBuffer("select a.num_project_expenditure_details_id,a.str_expenditure_description,a.num_expenditure_amount,a.dt_expendituredate,");
			buffer.append("b.str_project_name,c.str_budget_head_name,d.str_expenditure_head_name,b.num_project_id,c.num_budget_head_id,d.num_expenditure_head_id,d.NUM_ISVALID from pms_project_expenditure_details a,");
			buffer.append(" pms_project_master b, pms_project_budget_head_mst c,pms_expenditure_head_master d ");
			buffer.append(" where a.num_project_id=b.num_project_id and a.num_budget_head_id=c.num_budget_head_id");
			buffer.append(" and a.num_expenditure_head_id=d.num_expenditure_head_id and a.num_expenditure_head_id=d.num_expenditure_head_id");
			buffer.append(" and a.num_isvalid=1 and b.num_project_id="+numProjectId);


			//System.out.println(buffer.toString());
			
			List<Object[]> obj = daoHelper.runNative(buffer.toString());
			for(int i=0;i<obj.size();i++){
				//System.out.println(obj.get(i)[0]);
			}
			return 	obj;
			
		}
		//Query for Expenditure 
		public List<Double> getExpenditureFromProject(Date date){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
		/*	String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();*/
			
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			
			String assignedProjects = userInfo.getAssignedProjects();
			Date endDate = new Date();
			StringBuffer query = new StringBuffer("select sum(p.numExpenditureAmount) from ProjectExpenditureDetailsDomain p");
			query.append(" where p.numIsValid=1 and p.dtExpenditureDate  between '"+date+ "' and '"+ endDate+"'");
			if(assignedOrganisation != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			}
			return daoHelper.findByQuery(query.toString());
			
		}
		//Query for Expenditure Graph for Project Expenditure
		
		public List<Object[]> getProjectExpenditureByGroup(Date date){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();
			String assignedProjects = userInfo.getAssignedProjects();
			Date endDate = new Date();
			StringBuilder query = new StringBuilder("select sum(p.numExpenditureAmount),p.projectMasterDomain.application.groupMaster.numId, ");
			query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.application.groupMaster.bgColor ");
			query.append(" from ProjectExpenditureDetailsDomain p");
			query.append(" where p.numIsValid=1 and p.dtExpenditureDate  between '"+date+ "' and '"+ endDate+"'");
			if(null != assignedOrganisation){
				query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(null != assignedGroups  && !assignedGroups.equals("0")){
				query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			/*if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			}*/
			query.append(" GROUP BY 2,3,4");
			query.append(" order by 3");
			return daoHelper.findByQuery(query.toString());
		}
		

	
}

