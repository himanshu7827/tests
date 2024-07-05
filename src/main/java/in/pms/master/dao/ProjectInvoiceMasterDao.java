package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectInvoiceMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateProjectInvoiceMaster(ProjectInvoiceMasterDomain projectInvoiceMasterDomain){
		
		projectInvoiceMasterDomain =daoHelper.merge(ProjectInvoiceMasterDomain.class, projectInvoiceMasterDomain);		
			return projectInvoiceMasterDomain.getNumId();
		}
		
		public ProjectInvoiceMasterDomain getProjectInvoiceMasterById(long id){
			/*ProjectInvoiceMasterDomain projectInvoiceMasterDomain =null;*/
			/*String query = "from ProjectInvoiceMasterDomain p join fetch p.projectPaymentReceivedDomain where p.numId="+id;
			List<ProjectInvoiceMasterDomain> list = daoHelper.findByQuery(query);
			if(list.size()>0){
				projectInvoiceMasterDomain =list.get(0);
			}
			return projectInvoiceMasterDomain;	*/
			return daoHelper.findById(ProjectInvoiceMasterDomain.class, id);
		}
		
		public ProjectInvoiceMasterDomain getProjectInvoiceMasterByRefNo(String strInvoiceRefno,long projectId){
			String query = "from ProjectInvoiceMasterDomain where lower(strInvoiceRefno)=lower('"+strInvoiceRefno+"') and projectId="+projectId+" and numIsValid<>2" ;	
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomainList = daoHelper.findByQuery(query);		
			if(projectInvoiceMasterDomainList.size()>0){
				return projectInvoiceMasterDomainList.get(0);
			}
			return null;
		}

		public List<ProjectInvoiceMasterDomain> getAllProjectInvoiceMasterDomain(){
			String query = "from ProjectInvoiceMasterDomain where numId<>0 and numIsValid<>2";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<ProjectInvoiceMasterDomain> getAllActiveProjectInvoiceMasterDomain(){
			String query = "from ProjectInvoiceMasterDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<ProjectInvoiceMasterDomain> getProjectInvoiceMasterById(String ids){
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterrList =  daoHelper.findByQuery ("from ProjectInvoiceMasterDomain where numId in ("+ids+")");		
			return projectInvoiceMasterrList;
		}

		
	
		public List<ProjectInvoiceMasterDomain> getAllProjectInvoiceByProjectID(long projectId) {
			String query = "select distinct e from ProjectInvoiceMasterDomain e left join fetch e.projectPaymentSeheduleMasterDomain  where e.numIsValid<>2 and e.projectId="+projectId;	
							
			
			return daoHelper.findByQuery(query);
		}
		
	
		

		public List<ProjectInvoiceMasterDomain> getProjectInvoiceRefNoByProjectID(long projectId) {
			String query = "from ProjectInvoiceMasterDomain e where e.numIsValid=1 and e.strInvoiceStatus in ('Generated','Payment Partially Paid') and e.projectId="+projectId;	
			
			return daoHelper.findByQuery(query);
		}
		
		//Query for Project Details (for payment realized tab, to find invoices which are generated or partially paid for a particular project)
		public List<Object[]>  getProjectInvoiceDetailsByProjectId(long projectId){	
			StringBuffer query = new StringBuffer("select a.dtInvoice, a.strInvoiceRefno, a.numInvoiceAmt, a.numTaxAmount, a.numInvoiceTotalAmt, a.strInvoiceStatus, a.projectId,a.strInvoiceType from ProjectInvoiceMasterDomain a");
			query.append(" where a.numIsValid=1 and a.strInvoiceStatus IN ('Generated','Payment Partially Paid')  and a.projectId="+projectId);	
			query.append(" order by 1 desc");
			return daoHelper.findByQuery(query.toString());
			
			}
		public List<ProjectInvoiceMasterDomain> getProjectInvoiceMstDomainById(long id) {
			String query = "from ProjectInvoiceMasterDomain p join fetch p.projectPaymentReceivedDomain where p.numId="+id;		
			return daoHelper.findByQuery(query);
		}

		
		public List<ProjectInvoiceMasterDomain> getInvoiceDetailsByInvoiceId(long invoiceId) {
			//and e.strInvoiceStatus in ('Generated','Payment Partially Paid')
			String query = "from ProjectInvoiceMasterDomain e where e.numIsValid=1  and e.numId="+invoiceId;	
			
			return daoHelper.findByQuery(query);
		}

		public long getPendingPaymentsCount() {
			long result = 0 ;
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
			
			StringBuffer query = new StringBuffer("select count(a)");
			query.append(" from pms_project_invoice_mst a , pms_project_master b,application_project c, trans_application d where a.num_isvalid=1");
			query.append(" and a.str_invoice_status in ('Payment Partially Paid','Generated') and a.num_project_id= b.num_project_id");
			query.append(" and b.num_project_id = c.project_id and c.application_id = d.num_application_id");
			
			if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
				query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
			}

			if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 5 && roleId != 7){
				query.append(" and b.num_project_id in ("+assignedProjects+")");
			}
			
			/*StringBuffer query = new StringBuf
			 * fer("select count(DISTINCT a) from ProjectInvoiceMasterDomain a ");	
			query.append(" where a.strInvoiceStatus IN ('Payment Partially Paid','Generated')");
			query.append(" and a.numIsValid=1 ");*/
			List<BigInteger> temp = daoHelper.runNative(query.toString());
			
			if(temp!=null && temp.size()>0 && temp.get(0)!=null){
				result =  temp.get(0).longValue();
			}
			return result;
		}
		
		public List<Object[]> getPendingPaymentsDetail(){
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
			if(roleId ==0){
				return null;
			}
			//String query = "    "; 
			StringBuffer query = new StringBuffer("select b.str_project_name,a.str_invoice_refno,a.str_invoice_type,a.str_invoice_status,");
			query.append(" a.dt_invoice, a.num_invoice_amt,a.num_tax_amount,a.num_invoice_total_amt, b.num_project_id,e.str_group_name,b.str_project_ref_no,f.str_client_name from");
			query.append(" pms_project_invoice_mst a , pms_project_master b,application_project c, trans_application d,pms_group_master e,pms_client_master f where a.num_isvalid=1");
			query.append(" and a.str_invoice_status in ('Payment Partially Paid','Generated') and a.num_project_id= b.num_project_id");
			/*--------------- All Project Pending Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
			query.append(" and (b.str_closure_status='Technical' or b.str_closure_status is null)");
			/*-------------------------------------------------------------------------------------------------------*/
			query.append(" and b.num_project_id = c.project_id and c.application_id = d.num_application_id and e.group_id=d.num_group_id_fk AND d.client_id_fk=f.client_id");
			/*if(assignedOrganisation != 0){
				query.append(" and a.application.groupMaster.organisationMasterDomain.numId in ("+assignedOrganisation+")");
			}*/		

			if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
				query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
			}

			if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7){
				query.append(" and b.num_project_id in ("+assignedProjects+")");
			}
			
			query.append("  order by e.str_group_name,a.dt_invoice desc");
			
			List<Object[]> obj = daoHelper.runNative(query.toString());
			System.out.println("Pending Payments: "+query);
			return obj;	
			
			/*StringBuffer query = new StringBuffer("select a.strInvoiceRefno, a.dtInvoice, a.numInvoiceAmt, a.numTaxAmount, a.numInvoiceTotalAmt, a.strInvoiceStatus");
			query.append("from ProjectInvoiceMasterDomain a");
			query.append(" where a.strInvoiceStatus IN ('Payment Partially Paid','Generated')");
			query.append(" and a.numIsValid=1 ");
			query.append(" order by 2 desc");
			return daoHelper.findByQuery(query.toString());*/
			
		 
		}
		
		public List<Object[]> getPendingInvoiceDetail(){
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			//int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
			if(roleId ==0){
				return null;
			}
			
			StringBuffer query = new StringBuffer("SELECT b.str_project_name,a.dt_payment_due_date,a.str_purpose,a.str_remarks,b.num_project_id,a.num_amount,a.num_payment_sequence,e.str_group_name,b.str_project_ref_no,h.str_client_name ");
			query.append(" FROM pms_project_payment_schedule_mst a,pms_project_master b,trans_application f,application_project c,pms_group_master e,pms_client_master h WHERE a.num_isvalid = 1 AND a.num_project_id = b.num_project_id and e.group_id=f.num_group_id_fk and b.num_project_id = c.project_id and c.application_id= f.num_application_id AND f.client_id_fk=h.client_id and");
			
			/*--------------- All Project Due Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
			query.append(" (b.str_closure_status='Technical' or b.str_closure_status is null) and");
			/*-------------------------------------------------------------------------------------------------------*/
			query.append(" num_project_payment_id in (select a.num_project_payment_id from pms_project_payment_schedule_mst a,application_project c,trans_application d");
			query.append(" where   a.num_isvalid=1  and a.dt_payment_due_date < CURRENT_DATE AND c.application_id = d.num_application_id  and a.num_project_id = c.project_id ");
			if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
				query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
			}
			if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 ){
				query.append(" and b.num_project_id in ("+assignedProjects+")");
			}
			query.append(" except all SELECT DISTINCT b.num_project_payment_id FROM invoice_schedule_mst b  EXCEPT ALL SELECT DISTINCT w.num_project_payment_id FROM without_invoice_schedule_mst w) ");

			query.append("ORDER BY 8,2,1");
			System.out.println("Due Payments : "+query);
			List<Object[]> obj = daoHelper.runNative(query.toString());
			return obj;	
			
			
		 
		}
		
public List<Object[]> getPendingPaymentsDetailsByInvoiceDt(String invoiceDt, String symbol){
	
	Date invoiceDate=null;

	try{
	if(null != invoiceDt && !invoiceDt.equals("")){
		try {
			invoiceDate = DateUtils.dateStrToDate(invoiceDt);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		invoiceDate = new Date();
	}
	}
	catch(Exception e){
		e.printStackTrace();
	}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
			if(roleId ==0){
				return null;
			} 
			StringBuffer query = new StringBuffer("select b.str_project_name,a.str_invoice_refno,a.str_invoice_type,a.str_invoice_status,");
			query.append(" a.dt_invoice, a.num_invoice_amt,a.num_tax_amount,a.num_invoice_total_amt, b.num_project_id,e.str_group_name,b.str_project_ref_no,f.str_client_name from");
			query.append(" pms_project_invoice_mst a , pms_project_master b,application_project c, trans_application d,pms_group_master e,pms_client_master f where a.num_isvalid=1");
			query.append(" and A.dt_invoice "+symbol+"  '"+ invoiceDate+"' and a.str_invoice_status in ('Payment Partially Paid','Generated') and a.num_project_id= b.num_project_id");
			/*--------------- All Project Due Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
			query.append(" and (b.str_closure_status='Technical' or b.str_closure_status is null)");
			/*-------------------------------------------------------------------------------------------------------*/
			query.append(" and b.num_project_id = c.project_id and c.application_id = d.num_application_id and e.group_id=d.num_group_id_fk AND d.client_id_fk=f.client_id");
		
			if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
				query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
			}

			//if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7){
			//	query.append(" and b.num_project_id in ("+assignedProjects+")");
			//}
			
			//Modified by devesh on 22-04-24 to fix data when assigned projects are null
			if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && roleId != 5){
				query.append(" and b.num_project_id in ("+assignedProjects+")");
			}
			//End of condition
			
			query.append("  order by e.str_group_name,a.dt_invoice desc");
			System.out.println("Pending by Dates :"+query);
			List<Object[]> obj = daoHelper.runNative(query.toString());
			return obj;	
			
			
			
		 
		}

public List<Object[]> getPendingInvoiceDetailbyDate(String dueDate,String symbol){
	Date paymentDueDate=null;

	try{
	if(null != dueDate && !dueDate.equals("")){
		try {
			paymentDueDate = DateUtils.dateStrToDate(dueDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}else{
		paymentDueDate = new Date();
	}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	//int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	String assignedProjects = userInfo.getAssignedProjects();
	
	if(roleId ==0){
		return null;
	}
	
	StringBuffer query = new StringBuffer("SELECT b.str_project_name,a.dt_payment_due_date,a.str_purpose,a.str_remarks,b.num_project_id,a.num_amount,a.num_payment_sequence,e.str_group_name,b.str_project_ref_no,h.str_client_name ");
	query.append(" FROM pms_project_payment_schedule_mst a,pms_project_master b,trans_application f,application_project c,pms_group_master e,pms_client_master h WHERE a.num_isvalid = 1 AND a.dt_payment_due_date "+symbol+"  '"+ paymentDueDate+"'  and a.num_project_id = b.num_project_id and e.group_id=f.num_group_id_fk and b.num_project_id = c.project_id and c.application_id= f.num_application_id AND f.client_id_fk=h.client_id and");
	/*--------------- All Project Due Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
	query.append(" (b.str_closure_status='Technical' or b.str_closure_status is null) and");
	/*-------------------------------------------------------------------------------------------------------*/
	query.append(" num_project_payment_id in (select a.num_project_payment_id from pms_project_payment_schedule_mst a,application_project c,trans_application d");
	query.append(" where   a.num_isvalid=1  and a.dt_payment_due_date < CURRENT_DATE AND c.application_id = d.num_application_id  and a.num_project_id = c.project_id ");
	if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
	}
	//if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 ){
	//	query.append(" and b.num_project_id in ("+assignedProjects+")");
	//}
	
	//Modified by devesh on 22-04-24 to fix data when assigned projects are null
	if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 ){
		query.append(" and b.num_project_id in ("+assignedProjects+")");
	}
	//End of condition
	
	query.append(" except all SELECT DISTINCT b.num_project_payment_id FROM invoice_schedule_mst b  EXCEPT ALL SELECT DISTINCT w.num_project_payment_id FROM without_invoice_schedule_mst w) ");

	query.append("ORDER BY 8,2,1");
	
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
	
	
 
}

//Bhavesh(01-03-2024) getting the pending payment details year wise
public List<Object[]> getPendingPaymentsDetailsByInvoiceYear(String invoiceFromDate, String invoiceToDate) {
	Date invoiceFromDt=null;
	Date invoiceToDt=null;
	
	
	
	try {
		invoiceFromDt = DateUtils.dateStrToDate(invoiceFromDate);
		invoiceToDt = DateUtils.dateStrToDate(invoiceToDate);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
			int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
			int assignedGroups = selectedEmployeeRole.getNumGroupId();
			int roleId = selectedEmployeeRole.getNumRoleId();
			String assignedProjects = userInfo.getAssignedProjects();
			
			if(roleId ==0){
				return null;
			} 
			StringBuffer query = new StringBuffer("select b.str_project_name,a.str_invoice_refno,a.str_invoice_type,a.str_invoice_status,");
			query.append(" a.dt_invoice, a.num_invoice_amt,a.num_tax_amount,a.num_invoice_total_amt, b.num_project_id,e.str_group_name,b.str_project_ref_no,f.str_client_name from");
			query.append(" pms_project_invoice_mst a , pms_project_master b,application_project c, trans_application d,pms_group_master e,pms_client_master f where a.num_isvalid=1");
			query.append(" and A.dt_invoice  between '"+invoiceFromDt+"' and  '"+ invoiceToDt+"' and a.str_invoice_status in ('Payment Partially Paid','Generated') and a.num_project_id= b.num_project_id");
			/*--------------- All Project Due Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
			query.append(" and (b.str_closure_status='Technical' or b.str_closure_status is null)");
			/*-------------------------------------------------------------------------------------------------------*/
			query.append(" and b.num_project_id = c.project_id and c.application_id = d.num_application_id and e.group_id=d.num_group_id_fk AND d.client_id_fk=f.client_id");
		
			if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
				query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
			}

			//if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7){
			//	query.append(" and b.num_project_id in ("+assignedProjects+")");
			//}
			
			//Modified by devesh on 22-04-24 to fix data when assigned projects are null
			if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 7 && roleId != 5){
				query.append(" and b.num_project_id in ("+assignedProjects+")");
			}
			//End of condition
			
			query.append("  order by e.str_group_name,a.dt_invoice desc");
			System.out.println("Pending by Dates :"+query);
			List<Object[]> obj = daoHelper.runNative(query.toString());
			return obj;	
}

//Bhavesh(01-03-2024) getting the pending invoice details year wise
public List<Object[]> getPendingInvoiceDetailbyYear(String dueFromDate, String dueToDate) {
	// TODO Auto-generated method stub
	
	Date dueFromDt=null;
	Date dueToDt=null;
	
	
	
	try {
		dueFromDt = DateUtils.dateStrToDate(dueFromDate);
		dueToDt = DateUtils.dateStrToDate(dueToDate);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	EmployeeRoleMasterModel selectedEmployeeRole = userInfo.getSelectedEmployeeRole();
	//int assignedOrganisation = selectedEmployeeRole.getNumOrganisationId();
	int assignedGroups = selectedEmployeeRole.getNumGroupId();
	int roleId = selectedEmployeeRole.getNumRoleId();
	String assignedProjects = userInfo.getAssignedProjects();
	
	if(roleId ==0){
		return null;
	}
	
	StringBuffer query = new StringBuffer("SELECT b.str_project_name,a.dt_payment_due_date,a.str_purpose,a.str_remarks,b.num_project_id,a.num_amount,a.num_payment_sequence,e.str_group_name,b.str_project_ref_no,h.str_client_name ");
	query.append(" FROM pms_project_payment_schedule_mst a,pms_project_master b,trans_application f,application_project c,pms_group_master e,pms_client_master h WHERE a.num_isvalid = 1 AND a.dt_payment_due_date >=  '"+ dueFromDt+"' and  a.dt_payment_due_date <= '"+ dueToDt+"'  and a.num_project_id = b.num_project_id and e.group_id=f.num_group_id_fk and b.num_project_id = c.project_id and c.application_id= f.num_application_id AND f.client_id_fk=h.client_id and");
	/*--------------- All Project Due Payment where project closure status not technical closed in (23/06/2023)  -------------------------*/
	query.append(" (b.str_closure_status='Technical' or b.str_closure_status is null) and");
	/*-------------------------------------------------------------------------------------------------------*/
	query.append(" num_project_payment_id in (select a.num_project_payment_id from pms_project_payment_schedule_mst a,application_project c,trans_application d");
	query.append(" where   a.num_isvalid=1  and a.dt_payment_due_date < CURRENT_DATE AND c.application_id = d.num_application_id  and a.num_project_id = c.project_id ");
	if(roleId != 6 && roleId != 9 && roleId != 7 && assignedGroups != 0){
		query.append(" and d.num_group_id_fk in ("+assignedGroups+")");
	}
	//if((null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)) && roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 ){
	//	query.append(" and b.num_project_id in ("+assignedProjects+")");
	//}
	
	//Modified by devesh on 22-04-24 to fix data when assigned projects are null
	if(roleId != 6 && roleId !=8 && roleId != 9 && roleId != 5 && roleId != 7 ){
		query.append(" and b.num_project_id in ("+assignedProjects+")");
	}
	//End of condition
	
	query.append(" except all SELECT DISTINCT b.num_project_payment_id FROM invoice_schedule_mst b  EXCEPT ALL SELECT DISTINCT w.num_project_payment_id FROM without_invoice_schedule_mst w) ");

	query.append("ORDER BY 8,2,1");
	
	
	
	List<Object[]> obj = daoHelper.runNative(query.toString());
	return obj;	
	
}
}
