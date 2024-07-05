package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectPaymentSeheduleMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

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
public class ProjectPaymentScheduleMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateProjectPaymentScheduleMaster(ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain){
		
		projectPaymentSeheduleMasterDomain =daoHelper.merge(ProjectPaymentSeheduleMasterDomain.class, projectPaymentSeheduleMasterDomain);		
			return projectPaymentSeheduleMasterDomain.getNumId();
		}
		
		public ProjectPaymentSeheduleMasterDomain getProjectPaymentScheduleMasterById(long id){
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentScheduleMasterrList =  daoHelper.findByQuery("from ProjectPaymentSeheduleMasterDomain where numId="+id);
			if(projectPaymentScheduleMasterrList.size()>0){
				return projectPaymentScheduleMasterrList.get(0);
			}
			return null;
		}
		
		public ProjectPaymentSeheduleMasterDomain getProjectPaymentScheduleMasterBySeqNo(int numPaymentSequence,long projectId){
			String query = "from ProjectPaymentSeheduleMasterDomain where numPaymentSequence='"+numPaymentSequence+"' and projectId="+projectId+" and numIsValid<>2" ;	
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentScheduleMasterDomainList = daoHelper.findByQuery(query);		
			if(projectPaymentScheduleMasterDomainList.size()>0){
				return projectPaymentScheduleMasterDomainList.get(0);
			}
			return null;
		}

		public List<ProjectPaymentSeheduleMasterDomain> getAllProjectPaymentScheduleMasterDomain(){
			String query = "from ProjectPaymentSeheduleMasterDomain where numId<>0 and numIsValid<>2";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<ProjectPaymentSeheduleMasterDomain> getAllActiveProjectPaymentScheduleMasterDomain(){
			String query = "from ProjectPaymentSeheduleMasterDomain where numId<>0 and numIsValid=1";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<ProjectPaymentSeheduleMasterDomain> getProjectPaymentScheduleMasterById(String ids){
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentScheduleMasterrList =  daoHelper.findByQuery ("from ProjectPaymentSeheduleMasterDomain where numId in ("+ids+")");		
			return projectPaymentScheduleMasterrList;
		}

		
	
		public List<ProjectPaymentSeheduleMasterDomain> getAllProjectPaymentScheduleByProjectID(long projectId) {
			String query = "from ProjectPaymentSeheduleMasterDomain e where e.numIsValid=1 and e.projectId="+projectId +" order by dtPaymentDueDate";	
							
			//System.out.println("query is"+query);
			return daoHelper.findByQuery(query);
		}
		
		//Function added by devesh on 02-04-24 to get max payment sequence number for a project
		public int getMaxProjectPaymentNumberByProjectID(long projectId) {
			int result = 0;
			String query = "select max(e.numPaymentSequence) from ProjectPaymentSeheduleMasterDomain e where e.numIsValid=1 and e.projectId=?0";	
			ArrayList<Object> paramList = new ArrayList<Object>();
			 paramList.add(projectId);
			List<Integer> temp = daoHelper.findByQuery(query.toString(),paramList);		
			if(temp!=null && temp.size()>0 && temp.get(0)!=null){
				result = temp.get(0);
			}
			return result;
		}
		//End of function
		
		public List<ProjectPaymentSeheduleMasterDomain> getActiveProjectPaymentScheduleByProjectID(long projectId) {
			String query = "from ProjectPaymentSeheduleMasterDomain e where e.numIsValid=1 and e.projectId="+projectId +" order by dtPaymentDueDate";	
							
			
			return daoHelper.findByQuery(query);
		}
		
		
		public List<ProjectPaymentSeheduleMasterDomain> getAllProjectPaymentScheduleByMilestoneID(long numMilestoneId) {
			String query = "from ProjectPaymentSeheduleMasterDomain e where e.numIsValid<>2 and e.numMilestoneId="+numMilestoneId;	
							
			//System.out.println("query is"+query);
			return daoHelper.findByQuery(query);
		}
		
		public List<Object[]> getPaymentScheduleWithReceivedAmount(long projectId) {
			StringBuffer query = new StringBuffer("select a.num_project_payment_id,a.str_purpose, a.dt_payment_due_date, a.num_amount,");
			query.append(" (select sum(num_received_amount) from pms_project_payment_received where num_isvalid=1 and ");
			query.append(" num_invoice_id in (select distinct b.num_invoice_id from pms_project_invoice_mst b,invoice_schedule_mst c where b.num_isvalid=1 and "); 
			query.append(" c.num_project_payment_id =a.num_project_payment_id and b.num_invoice_id = c.num_invoice_id)) from pms_project_payment_schedule_mst a"); 
			query.append(" where num_isvalid=1 and num_project_id="+projectId);
			query.append("	union ");
			query.append(" select 0,'Without Schedule Payment',CURRENT_DATE,0.0,(select sum(a.num_received_amount) from pms_project_payment_received a");
			query.append(" where a.num_isvalid=1 and a.num_invoice_id in (select b.num_invoice_id from pms_project_invoice_mst b ");
			query.append(" where b.num_isvalid=1 and num_project_id="+projectId);
			query.append(" except select DISTINCT b.num_invoice_id from pms_project_invoice_mst b,invoice_schedule_mst c where b.num_isvalid=1 and "); 
			query.append(" b.num_invoice_id = c.num_invoice_id and b.num_project_id= "+projectId+" )) order by 3");
			
			return daoHelper.runNative(query.toString());
		}
		
		/*public List<Object[]> getPaymentScheduleWithReceivedAmount(long projectId) {
			StringBuffer query = new StringBuffer("select a.num_project_payment_id,a.str_purpose, a.dt_payment_due_date, a.num_amount,");
			query.append(" (select sum(num_received_amount) from pms_project_payment_received where num_isvalid=1 and ");
			query.append(" num_invoice_id in (select num_invoice_id from pms_project_invoice_mst b where num_isvalid=1 and ");
			query.append(" num_payment_schedule_id_fk =a.num_project_payment_id)) from pms_project_payment_schedule_mst a ");
			query.append("where num_isvalid=1 and num_project_id="+projectId);
			query.append("	union ");
			query.append("select 0,'Without Schedule Payment',CURRENT_DATE,0.0,(select sum(num_received_amount) from pms_project_payment_received ");
			query.append(" where num_isvalid=1 and 	num_invoice_id in (select num_invoice_id from pms_project_invoice_mst ");
			query.append(" where num_isvalid=1 and num_payment_schedule_id_fk is null and num_project_id="+projectId+")) order by 3");


			return daoHelper.runNative(query.toString());
		}*/
		
		public List<ProjectPaymentSeheduleMasterDomain> getAllProjectPaymentSchedule(List<Long> ids){
			//ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain =null;
			String query = "from ProjectPaymentSeheduleMasterDomain t where t.numId in :ids";
			return daoHelper.findByIdList(query, ids);
		}
		
	////Query to find count of total revenue of projects 
		public double getprojectsRevenueByOrganisation(Date startRange, Date endRange){
			double result = 0;
						  
			ArrayList<Object> paramList = new ArrayList<Object>();
			 paramList.add(startRange);
			 paramList.add(endRange);
			 
			
			StringBuffer query = new StringBuffer("select sum(a.numAmount) from ProjectPaymentSeheduleMasterDomain a ");
			query.append( " where a.numIsValid=1 and a.dtPaymentDueDate>=? and a.dtPaymentDueDate<=?");
			
			
			List<Double> temp = daoHelper.findByQuery(query.toString(),paramList);		
			if(temp!=null && temp.size()>0 && temp.get(0)!=null){
				result = temp.get(0);
			}
			return result;		
		}
	
}
