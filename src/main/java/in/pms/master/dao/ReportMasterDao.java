package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.InterfaceMaster;
import in.pms.master.domain.ReportMaster;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ReportMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReportMasterDao 
{
@Autowired 
DaoHelper daoHelper;

	
		
		public ReportMaster saveReportMaster(ReportMaster reportMaster) 
		{
			return daoHelper.merge(ReportMaster.class, reportMaster);
			
		}


		public List<InterfaceMaster> getInterfaceDetails() 
		{
			String query="from InterfaceMaster i where i.numIsValid=1 and i.numInterfaceId!=0";
			return daoHelper.findByQuery(query);
			
		}

		
		public List<InterfaceMaster> getInterfaceDetailsByID(int id) 
		{
			String query="from InterfaceMaster i where i.numIsValid=1 and i.numInterfaceId="+id;
			return daoHelper.findByQuery(query);
			
		}
		
		public List<ReportMaster> getReportType() {
			String query="from ReportMaster r where  r.numIsValid=1";
			return daoHelper.findByQuery(query);
		}

		public void DeleteReport(ReportMasterModel reportMasterModel,int userId) 
		{
			List<ReportMaster> cmms = daoHelper.findByAttribute(ReportMaster.class, "numQueryId",reportMasterModel.getNumReportId());
			 Iterator<ReportMaster> itr = cmms.iterator();
			 while(itr.hasNext())
			 {
			ReportMaster sch = itr.next();
	        sch.setStrQuery("");
	        // sch.setnumIsValid(0);
	         Date date=new Date();
                  sch.setDtTrDate(date);
                  sch.setNumTrUserId(userId);			
			 daoHelper.merge(ReportMaster.class, sch);
			 }
			
		}
						
		
		public List<ReportMaster> getReportData(int  queryId) {
			List<ReportMaster> reportMaster_list = new ArrayList<ReportMaster>();
			String Query = "from ReportMaster r where r.numQueryId="+queryId;
			
			reportMaster_list = daoHelper.findByQuery(Query);
			return reportMaster_list;
		}
				
							
		
		public void SaveEdittedReport(ReportMasterModel reportMasterModel, int userId) 
		{
			
			List<ReportMaster> cmms = daoHelper.findByAttributes(ReportMaster.class, "numQueryId",reportMasterModel.getNumReportId());
			 Iterator<ReportMaster> itr = cmms.iterator();
			 while(itr.hasNext())
			 {
			ReportMaster reportMaster = itr.next();
			reportMaster.setStrQuery(reportMasterModel.getStrQuery());
			reportMaster.setStrDesc(reportMasterModel.getStrDescription());
			reportMaster.setStrSheetNames(reportMasterModel.getStrSheetNames());
			List<InterfaceMaster> list=getInterfaceDetailsByID(reportMasterModel.getNumInterfaceId());
			
			if(list.size()>0){
			reportMaster.setInterfaceMaster(list.get(0));
			}
			reportMaster.setNumTrUserId(userId);
	        Date date=new Date();
	        reportMaster.setDtTrDate(date);
	       // reportMaster.setnumIsValid(1);   	
                
			 daoHelper.merge(ReportMaster.class, reportMaster);
			 }	
	
		}
		
		
		public List<ReportMaster> getReportTypeForInterface(int interfaceid) {
			String query="from ReportMaster r where  r.interfaceMaster.numInterfaceId="+interfaceid+" and r.numIsValid=1 ";
			return daoHelper.findByQuery(query);
		}
		
		public boolean isDuplicateReport(String eCode){
			boolean flag=false;
			String query="from ReportMaster r where  r.strECode='"+eCode+"' and r.numIsValid=1 ";
			List<ReportMaster> count = daoHelper.findByQuery(query);
			if(count.size()>0){
				flag=true;
				return flag;
			}
			return flag;
		}
		
		
		public String isSchemeSpecificQuery(String eCode){
			String ReportQuery="";
			String SheetName="";
			String Query1="Select r from ReportMaster r where   r.strECode='"+eCode+"' and r.numIsValid=1";
			List<ReportMaster> list =  daoHelper.findByQuery(Query1); 
		       if(list.size()>0){
		    	 
		    	 
		    		   ReportQuery= list.get(0).getStrQuery();
		    		   SheetName= list.get(0).getStrSheetNames();
		    	  
		    	 
		       } 
		       
			return ReportQuery+"%@12345@%"+SheetName;
		}
		
		public List<ReportMaster> getReportDetailByEcode(String eCode){
			String Query = "from ReportMaster r where  r.strECode='"+eCode+"' and r.numIsValid=1";
			return daoHelper.findByQuery(Query);
		
		}
		
	
		public List<ReportMaster> reportName(){
		String query="from ReportMaster r where  r.numIsValid=1";
		return daoHelper.findByQuery(query);
		}
		
		
		 public List<Object[]> getReportsByRoleInterface(int interfaceId){ 
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
			 UserInfo userInfo =(UserInfo) authentication.getPrincipal(); 
			 EmployeeRoleMasterModel selectedEmployeeRole =userInfo.getSelectedEmployeeRole();
			 int numRoleId = selectedEmployeeRole.getNumRoleId(); 
			 StringBuffer query = new StringBuffer("select v.num_query_id,v.str_name from pms_role_master r,pms_report_master v ,");
			 query.append(" roles_reports z where z.role_id= r.role_id and z.report_id= v.num_query_id and z.role_id= "+numRoleId);
			 query.append(" and v.num_interface_id="+interfaceId);
			 return daoHelper.runNative(query.toString()); 
		  }
		
}