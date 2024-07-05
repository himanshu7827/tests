package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectPaymentReceivedDomain;
import in.pms.master.domain.ProjectPaymentReceivedWithoutInvoiceDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectPaymentReceivedDao {

	@Autowired
	DaoHelper daoHelper;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	public long saveUpdatePaymentReceived(ProjectPaymentReceivedDomain projectPaymentReceivedDomain){
		
		projectPaymentReceivedDomain =daoHelper.merge(ProjectPaymentReceivedDomain.class, projectPaymentReceivedDomain);		
			return projectPaymentReceivedDomain.getNumId();
		}
		
		public ProjectPaymentReceivedDomain getPaymentReceivedById(long id){
			List<ProjectPaymentReceivedDomain> projectPaymentReceivedList =  daoHelper.findByQuery("from ProjectPaymentReceivedDomain where numId="+id);
			if(projectPaymentReceivedList.size()>0){
				return projectPaymentReceivedList.get(0);
			}
			return null;
		}
		
		public ProjectPaymentReceivedDomain getPaymentReceivedNo(String strUtrNumber,long projectId){
			String query = "from ProjectPaymentReceivedDomain e where lower(e.strUtrNumber)=lower('"+strUtrNumber+"') and e.projectMasterDomain.numId="+projectId+" and e.numIsValid<>2" ;	
			List<ProjectPaymentReceivedDomain> projectPaymentReceivedDomainList = daoHelper.findByQuery(query);		
			if(projectPaymentReceivedDomainList.size()>0){
				return projectPaymentReceivedDomainList.get(0);
			}
			return null;
		}

		public List<ProjectPaymentReceivedDomain> getAllPaymentReceivedDomain(){
			String query = "from ProjectPaymentReceivedDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<ProjectPaymentReceivedDomain> getAllActivePaymentReceivedDomain(){
			String query = "from ProjectPaymentReceivedDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<ProjectPaymentReceivedDomain> getPaymentReceivedById(String ids){
			List<ProjectPaymentReceivedDomain> projectPaymentReceivedList =  daoHelper.findByQuery ("from ProjectPaymentReceivedDomain where numId in ("+ids+")");		
			return projectPaymentReceivedList;
		}

		
	
		public List<ProjectPaymentReceivedDomain> getAllPaymentReceivedByProjectID(long projectId) {
			String query = "from ProjectPaymentReceivedDomain e JOIN FETCH e.projectMasterDomain  where e.numIsValid=1 and e.projectMasterDomain.numId="+projectId;	
							
			//System.out.println("query is"+query);
			return daoHelper.findByQuery(query);
		}
		
		public List<ProjectPaymentReceivedDomain> getProjectPaymentReceivedByProjectId(long projectId) {
			String query = "from ProjectPaymentReceivedDomain e JOIN FETCH e.projectInvoiceMasterDomain  where e.numIsValid=1 and e.projectMasterDomain.numId="+projectId+" order by e.dtPayment desc";	
			return daoHelper.findByQuery(query);
		}
	
		public double totalPaymentReceivedByInvoiceId(long invoiceId,long receiveId){
			double result = 0;
			String query = "select COALESCE(sum(a.numReceivedAmount),0) from ProjectPaymentReceivedDomain a where a.numIsValid=1 and a.projectInvoiceMasterDomain.numId="+invoiceId +" and a.numId!="+receiveId;
			List<Double> data = daoHelper.findByQuery(query);
			if(data.size()>0){
				result = data.get(0);
			}
			return result;
		}
	
		public List<ProjectPaymentReceivedDomain> getActivePaymentReceivedByProjectId(long projectId){
			String query = "from ProjectPaymentReceivedDomain e JOIN FETCH e.projectInvoiceMasterDomain where e.numIsValid=1 and e.projectMasterDomain.numId="+projectId;	
			return daoHelper.findByQuery(query);
		}
		
		//Query for income
		public List<Double> getIncome(Date date,Date endRange){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
				return new ArrayList<Double>();
			}
			
			
			StringBuffer query = new StringBuffer("select sum(p.numReceivedAmount) from ProjectPaymentReceivedDomain p");
			query.append(" where p.numIsValid=1 and p.dtPayment >= '"+date+ "' and p.dtPayment <='"+ endRange+"'");
			if(assignedOrganisation!= 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			//if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1) && roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5){
			//	query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			//}
			
			//Modified by devesh on 22-04-24 to fix data when assigned projects are null
			if(roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5 && roleId != 7){
				query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			}
			//End of condition
			
			return daoHelper.findByQuery(query.toString());
		}

		//Query for Income Model(Group wise total Payment Received)
		public List<Object[]> getPaymentReceivedByGroup(Date date,Date endDate){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
	       StringBuilder query =  new StringBuilder("select sum(p.numReceivedAmount), p.projectMasterDomain.application.groupMaster.numId,");
	       query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.application.groupMaster.bgColor");
	       query.append(" from ProjectPaymentReceivedDomain p");
	       query.append(" where p.dtPayment between '"+date+ "' and '"+ endDate+"'");
	       query.append(" and p.numIsValid = 1");
	       if(assignedOrganisation != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9){
				query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			}
	       query.append(" GROUP BY 2,3,4 ");
	       query.append(" order by 3");
	   
	    return daoHelper.findByQuery(query.toString());
		}

		public List<Object[]> getPaymentReceivedByProjectByGroup(Date startRange, Date endRange,ProjectPaymentReceivedModel projectPaymentReceivedModel){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			/*UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			String assignedGroups = userInfo.getAssignedGroups();
			String assignedProjects = userInfo.getAssignedProjects();*/
			
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
				return null;
			}
		    StringBuilder query = new StringBuilder(" select sum(p.numReceivedAmount), p.projectMasterDomain.application.groupMaster.numId,");
		    query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.strProjectName,p.projectMasterDomain.numId,p.projectMasterDomain.strProjectRefNo ");
		    query.append(" from ProjectPaymentReceivedDomain p");
			/* query.append(" where p.numIsValid = 1"); */
		    query.append(" where p.numIsValid = 1 and p.dtPayment >= '"+startRange+ "' and p.dtPayment <='"+ endRange+"' ");
		  
		        if(assignedOrganisation != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				}
				
		        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0 && projectPaymentReceivedModel.getEncGroupId()==null){
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
				}
		        if(projectPaymentReceivedModel.getEncGroupId()!=null){
		        	//String strEncGroupId=projectPaymentReceivedModel.getEncGroupId().substring( 1, projectPaymentReceivedModel.getEncGroupId().length() - 1 );
					String strGroupId = encryptionService.dcrypt(projectPaymentReceivedModel.getEncGroupId());
					long groupId = Long.parseLong(strGroupId);
		        	query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+groupId+")");
		        }
		        if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
					query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
				}
		    query.append(" GROUP BY 2,3,4,5,6 ");
			query.append(" order by 3");
			return daoHelper.findByQuery(query.toString());
		}
	//Query for Project Details (for payment realized tab)
		
		public List<Object[]> getPaymentReceivedwithInvoice(long projectId){
			
			StringBuffer query = new StringBuffer("select p.dtPayment, p.numReceivedAmount,p.strPaymentMode,p.strUtrNumber, p.projectInvoiceMasterDomain.strInvoiceRefno, ");
			/*------------ Add (p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment) to get column values [05-12-2023] --------------------*/
			query.append(" p.projectMasterDomain.numId, p.projectInvoiceMasterDomain.numId,p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment from ProjectPaymentReceivedDomain p");
			query.append(" where p.numIsValid=1 and p.projectMasterDomain.numId="+projectId);
			
			return daoHelper.findByQuery(query.toString());
			}

		public long saveUpdatePaymentReceivedWithoutInvoice(ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain) {
			projectPaymentReceivedWithoutInvoiceDomain =daoHelper.merge(ProjectPaymentReceivedWithoutInvoiceDomain.class, projectPaymentReceivedWithoutInvoiceDomain);		
			return projectPaymentReceivedWithoutInvoiceDomain.getNumId();
		}

		public ProjectPaymentReceivedWithoutInvoiceDomain getPaymentReceivedWithoutInvoiceById(long numId) {
			List<ProjectPaymentReceivedWithoutInvoiceDomain> projectPaymentReceivedList =  daoHelper.findByQuery("from ProjectPaymentReceivedWithoutInvoiceDomain where numIsValid=1 and numId="+numId);
			if(projectPaymentReceivedList.size()>0){
				return projectPaymentReceivedList.get(0);
			}
			return null;
		}

		public ProjectPaymentReceivedWithoutInvoiceDomain getPaymentReceivedWithoutInvoiceNo(String strUtrNumber, long projectId) {
			String query = "from ProjectPaymentReceivedWithoutInvoiceDomain e where lower(e.strUtrNumber)=lower('"+strUtrNumber+"') and e.projectMasterDomain.numId="+projectId+" and e.numIsValid<>2" ;	
			List<ProjectPaymentReceivedWithoutInvoiceDomain> projectPaymentReceivedDomainList = daoHelper.findByQuery(query);		
			if(projectPaymentReceivedDomainList.size()>0){
				return projectPaymentReceivedDomainList.get(0);
			}
			return null;
		}
		
		public List<ProjectPaymentReceivedWithoutInvoiceDomain> getPaymentReceivedWithoutInvoiceByProjectId(long projectId) {
			return daoHelper.findByQuery("from ProjectPaymentReceivedWithoutInvoiceDomain e left join fetch e.projectMasterDomain where e.projectMasterDomain.numId="+projectId+" and e.numIsValid<>2 and e.numReceivedAmount > e.numMappedAmount");
			
		}
		/*public List<Object[]> getPaymentReceivedDetails(Date startRange, Date endRange){
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
		    StringBuilder query = new StringBuilder(" select sum(p.numReceivedAmount), p.projectMasterDomain.application.groupMaster.numId,");
		    query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.strProjectName,p.projectMasterDomain.numId ");
		    query.append(" from ProjectPaymentReceivedDomain p");
		    query.append(" where p.numIsValid = 1 and p.dtPayment >= '"+startRange+ "' and p.dtPayment <='"+ endRange+"'");
		  
		        if(assignedOrganisation != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				}
				
		        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
				}
		        if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
					query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
				}
		    query.append(" GROUP BY 2,3,4,5 ");
			query.append(" order by 3 ");
			return daoHelper.findByQuery(query.toString());
		}
		*/
		public List<Object[]> getPaymentReceivedDetails(Date startRange, Date endRange,ProjectPaymentReceivedModel model){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
						
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
			List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
			String strProjectIds = numProjectIds.toString();
			strProjectIds = strProjectIds.substring(1, strProjectIds.length()-1); 
			if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
				return null;
			}
		    StringBuilder query = new StringBuilder(" select p.numReceivedAmount, p.projectMasterDomain.application.groupMaster.numId,");
		    query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.strProjectName,p.projectMasterDomain.numId, ");
		    query.append(" p.dtPayment,p.projectMasterDomain.strProjectRefNo ");
		    /*------------ Add (p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment) to get column values [05-12-2023] --------------------*/
		    query.append(",p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment");
		    /*-------------------------------------------------------------------*/
		    query.append(" from ProjectPaymentReceivedDomain p");
		    query.append(" where p.numIsValid = 1 and p.dtPayment >= '"+startRange+ "' and p.dtPayment <='"+ endRange+"' ");
		
		        if(assignedOrganisation != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				}
				if(model.getEncGroupId()==null){
		        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0 ){
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
				}
				}
				else{
					String strGroupId = encryptionService.dcrypt(model.getEncGroupId());
					long groupId = Long.parseLong(strGroupId);
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+groupId+")");
				}
				/*
				 * if(null != assignedProjects && !(assignedProjects.contains("0") &&
				 * assignedProjects.length()==1)){
				 * query.append(" and p.projectMasterDomain.numId in ("+strProjectIds+")"); }
				 */
				
				//Modified by devesh on 22-04-24 to fix data when assigned projects are null
				if(roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5 && roleId != 7){
					query.append(" and p.projectMasterDomain.numId in ("+strProjectIds+")");
				}
				
				//End of condition

		        query.append(" order by 6");
		        return daoHelper.findByQuery(query.toString());
		}
		
		public  List<Double> getPaymentReceivedWithoutInvoice(Date startRange, Date endRange){
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
		    StringBuilder query = new StringBuilder(" select sum(numReceivedAmount-numMappedAmount) ");	    
		    query.append(" from ProjectPaymentReceivedWithoutInvoiceDomain p");		    
		    query.append(" where p.numIsValid = 1 and p.dtPayment >= '"+startRange+ "' and p.dtPayment <='"+ endRange+"'");		  
		        if(assignedOrganisation != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				}
				
		        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
				}
		        //if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5){
				//	query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
				//}
		        
		        //Modified by devesh on 22-04-24 to fix data when assigned projects are null
		        if(roleId != 6 && roleId !=8 && roleId != 9 && roleId !=5 && roleId != 7){
					query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
				}
		        //End of condition
		    
			return daoHelper.findByQuery(query.toString());
		}
		
		public List<Object[]> getWithoutInvoicePaymentReceived(Date date,Date endDate){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
	       StringBuilder query =  new StringBuilder("select sum(p.numReceivedAmount), p.projectMasterDomain.application.groupMaster.numId,");
	       query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.application.groupMaster.bgColor");
	       query.append(" from ProjectPaymentReceivedDomain p");
	       query.append(" where p.dtPayment between '"+date+ "' and '"+ endDate+"'");
	       query.append(" and p.numIsValid = 1");
	       if(assignedOrganisation != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}
			
			if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
				query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
			}
			if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9){
				query.append(" and p.projectMasterDomain.numId in ("+assignedProjects+")");
			}
	       query.append(" GROUP BY 2,3,4 ");
	       query.append(" order by 3");
	   
	    return daoHelper.findByQuery(query.toString());
		}
		
		public List<Object[]> getPaymentReceivedDetailsWithoutInvoice(Date startRange, Date endRange,ProjectPaymentReceivedModel model){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
						
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
			String strProjectIds = numProjectIds.toString();
			strProjectIds = strProjectIds.substring(1, strProjectIds.length()-1); 
			if(assignedOrganisation == 0 && assignedGroups == 0 && roleId == 0 && assignedProjects.equals("")){
				return null;
			}
		    StringBuilder query = new StringBuilder(" select (p.numReceivedAmount-p.numMappedAmount), p.projectMasterDomain.application.groupMaster.numId,");
		    query.append(" p.projectMasterDomain.application.groupMaster.groupName, p.projectMasterDomain.strProjectName,p.projectMasterDomain.numId, ");
		    query.append(" p.dtPayment,p.projectMasterDomain.strProjectRefNo ");
		    /*------------ Add (p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment) to get column values [05-12-2023] --------------------*/
		    query.append(",p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment");
		    /*-------------------------------------------------------------------*/
		    query.append(" from ProjectPaymentReceivedWithoutInvoiceDomain p");
		    query.append(" where p.numIsValid = 1 and p.dtPayment >= '"+startRange+ "' and p.dtPayment <='"+ endRange+"'");
		  
		        if(assignedOrganisation != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
				}
				if(model.getEncGroupId()==null){
		        if(roleId != 6 && roleId !=8 && roleId != 9 && assignedGroups != 0){
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+assignedGroups+")");
				}
				}
		        else{
					String strGroupId = encryptionService.dcrypt(model.getEncGroupId());
					long groupId = Long.parseLong(strGroupId);
					query.append(" and p.projectMasterDomain.application.groupMaster.numId in ("+groupId+")");
				}
		        //if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9){
				//	query.append(" and p.projectMasterDomain.numId in ("+strProjectIds+")");
				//}
				
				//Modified by devesh on 22-04-24 to fix data when assigned projects are null
				if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && roleId != 5){
					query.append(" and p.projectMasterDomain.numId in ("+strProjectIds+")");
				}
				//End of condition
				
		    //query.append(" GROUP BY 2,3,4,5,6,7 ");
		    //query.append(" GROUP BY 3 ");
			query.append(" order by 6,4 desc");
			return daoHelper.findByQuery(query.toString());
		}
		
		public long getPaymentReceviedCountByGroup(Date startRange, Date endRange){
			int result = 0;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();

			
			
			StringBuffer query = new StringBuffer("select count(a) from ProjectMasterDomain a ");		
			query.append(" where a.projectClosureDate  between '"+startRange+ "' and '"+endRange+ "' ");
			query.append(" and a.strProjectStatus IN ('Terminated','Completed')");
			query.append(" and a.numIsValid=1 ");
			if(assignedOrganisation != 0){
				query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}		

			if(roleId != 6 && roleId != 9 && assignedGroups != 0){
				query.append(" and a.application.groupMaster.numId in ("+assignedGroups+")");
			}

			if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
				query.append(" and a.numId in ("+assignedProjects+")");
			}
			List<Long> temp = daoHelper.findByQuery(query.toString());
			
			if(temp!=null && temp.size()>0 && temp.get(0)!=null){
				result = temp.get(0).intValue();
			}
			//System.out.println(result);
			return result;
			
		}
		
		public List<Object[]> getPaymentReceivedDetailsWithoutInvoiceByPiD(long projectId){
			
		    StringBuilder query = new StringBuilder(" select (p.numReceivedAmount-p.numMappedAmount),");
		    query.append("p.projectMasterDomain.numId, ");
		    query.append(" p.dtPayment,p.strUtrNumber,p.strPaymentMode ");
		    /*------------ Add (p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment) to get column values [05-12-2023] --------------------*/
		    query.append(",p.itTDS,p.gstTDS,p.shortPayment,p.ldPayment,p.otherRecovery,p.excessPayment");
		    query.append(" from ProjectPaymentReceivedWithoutInvoiceDomain p");
		    query.append(" where p.numIsValid = 1 and p.projectMasterDomain.numId="+projectId);
		  
		    return daoHelper.findByQuery(query.toString());
		}
		
		public List<Object[]> loadIncome(String startRange, String endRange,int orgId){
			
			StringBuffer query = new StringBuffer("SELECT A.num_group_id_fk,g.str_group_name, SUM (P .num_received_amount) FROM trans_application A,application_project b,pms_project_payment_received P,pms_group_master g, pms_organisation_master o WHERE P .num_isvalid = 1 and A .num_application_id = b.application_id AND b.project_id= P .num_project_id AND g.organisation_id=o.organisation_id AND A .num_group_id_fk = g.group_id AND P .dt_payment >= to_date('"+startRange+"','dd/MM/yyyy') AND P .dt_payment <= to_date('"+endRange+"','dd/MM/yyyy') AND g.organisation_id IN ("+orgId+") AND g.num_show_in_projects=1 GROUP BY 1,2");
			
			
			List<Object[]> obj = daoHelper.runNative(query.toString()); 
			return obj;	
				
		}
		
		public List<Object[]> loadPaymentReceivedWithoutInvoice(String startRange, String endRange,int orgId){

			
			StringBuffer query = new StringBuffer("SELECT A.num_group_id_fk,g.str_group_name,SUM (P.num_received_amount - P.num_mapped_amount) FROM trans_application A, application_project b, pms_group_master g, pms_organisation_master o, pms_project_payment_received_without_invoice P WHERE P .num_isvalid = 1 and A .num_application_id = b.application_id AND b.project_id= P .num_project_id AND g.organisation_id=o.organisation_id AND A .num_group_id_fk = g.group_id AND P .dt_payment >= to_date('"+startRange+"','dd/MM/yyyy') AND P .dt_payment <= to_date('"+endRange+"','dd/MM/yyyy') AND g.organisation_id IN ("+orgId+") AND g.num_show_in_projects=1 GROUP BY 1,2");
			
			
			List<Object[]> obj = daoHelper.runNative(query.toString()); 
			return obj;	
				
		}
}
