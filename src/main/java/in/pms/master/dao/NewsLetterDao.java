package in.pms.master.dao;

/*import in.pms.master.domain.NewsLetterFilterMapping;*/
import in.pms.login.domain.DeviceMetadata;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ModuleTypeMaster;
import in.pms.master.domain.NewsLetterFilterMapping;
import in.pms.master.domain.NewsLetterFilterMaster;
import in.pms.master.domain.NewsLetterMaster;
import in.pms.master.domain.NewsLetterRoleMapping;
import in.pms.master.domain.NewsletterDocuments;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.model.NewsLetterFilterForm;
/*import in.cdacnoida.serb.proposer.domain.NewsletterDocuments;*/
import in.pms.global.dao.DaoHelper;
import in.pms.global.util.DateUtils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class NewsLetterDao {

	@Autowired
	public DaoHelper daoHelper;
	
	public List<NewsLetterFilterMaster> getAllFilters() {
		String query = "Select r from NewsLetterFilterMaster r where r.numFilterId not in (1)";
		return daoHelper.findByQuery(query);
	}

	public NewsLetterFilterMaster getAllFilters(int numFilterId) {
		String query = "Select r from NewsLetterFilterMaster r where r.numFilterId="+numFilterId;
		List<NewsLetterFilterMaster> lnlfm = daoHelper.findByQuery(query);
		return lnlfm.get(0);
	}

	public List<NewsLetterFilterForm> getUsersForNewsLetter(String query) {
		return daoHelper.getUsersForNewsLetter(query);
	}	
	public List<NewsLetterFilterForm> getPropNewsLetter(String query) {
		return daoHelper.gePropNewsLetter(query);
	}

	public NewsLetterMaster addNewsLetterDetails(NewsLetterMaster nlm) {
		return daoHelper.persist(NewsLetterMaster.class,nlm);
	}

	/*public void addNewsLetterMapping(NewsLetterFilterMapping nlfm) {
		//daoHelper.persist(NewsLetterFilterMapping.class, nlfm);
		daoHelper.merge(NewsLetterFilterMapping.class, nlfm);
	}*/
	
	public NewsLetterFilterMapping addNewsLetterMapping(NewsLetterFilterMapping nlfm){
		return daoHelper.merge(NewsLetterFilterMapping.class, nlfm);
	 }
	
	public NewsLetterRoleMapping addNewsLetterRoleMapping(NewsLetterRoleMapping nlrm){
		return daoHelper.merge(NewsLetterRoleMapping.class, nlrm);
	 }
	
	public void addNewsLetterFilter(NewsLetterFilterMaster domain)
	{
		if(domain.getNumFilterId()==0)
		daoHelper.persist(NewsLetterFilterMaster.class, domain);
		else
			daoHelper.merge(NewsLetterFilterMaster.class, domain);
	}
	
/*	public boolean checkValidation(String queryString)
	{
		return daoHelper.checkValidation(queryString);
	}
	
	public boolean checkValidationForProposalID(String queryString)
	{
		return daoHelper.checkValidationForProposalID(queryString);
	}
	*/
	public List<NewsLetterFilterMaster> getAllSavedFilters() {
		String query = "Select r from NewsLetterFilterMaster r where r.numIsValid =1 and  r.numFilterId not in (1)";
		return daoHelper.findByQuery(query);
	}
	
	public int deleteFilter(int numId)
	{
		return 	daoHelper.deleteByAttribute(NewsLetterFilterMaster.class,"numFilterId",numId);
	}
	
	/*public List<NewsLetterMaster> getAllSavedNewsLetterMaster(String currentDate) {
		
		String query = "Select r from NewsLetterMaster r where r.numIsValid =1 and  r.strStatus='SUB' and r.isPeriodic!=1 and  r.scheduledDate='"+currentDate+"'";
		return daoHelper.findByQuery(query);
	}*/
	public List<NewsLetterMaster> getAllSavedNewsLetterMaster(String currentDate){
		/*Date curDate=DateUtils.dateStrToDate(currentDate);*/
		StringBuffer buffer = new StringBuffer("select * from pms_newsletter_master where num_isperiodic!=1 and num_isvalid=1  and str_status='SUB' and scheduleddate='"+currentDate+"'");
      
	    List<NewsLetterMaster> newsLetterMaster = daoHelper.runNative(buffer.toString(), NewsLetterMaster.class);	
		return newsLetterMaster;
	}
	
	public List<NewsLetterFilterMapping> getFilterMappingForNewsLetter(int numNewsLetterId) {
		String query = "Select r from NewsLetterFilterMapping r where r.numNewsletterId="+numNewsLetterId+" and r.numIsValid =1";
		List<NewsLetterFilterMapping> nlfm = daoHelper.findByQuery(query);
		return nlfm;  
	}
	
/*

	*/
	
	
	
	public void updateNewsLetterStatus(NewsLetterMaster nlm) {
		daoHelper.persist(NewsLetterMaster.class, nlm);
	}
	
	
	public List<NewsLetterMaster> getAllSavedNewsLetters(String currentDate) {
		
		String query = "Select r from NewsLetterMaster r where r.numIsValid =1 and  r.strStatus='SUB' and r.scheduledDate>=to_timestamp('"+currentDate+"', 'yyyy-mm-dd')";
		
		return daoHelper.findByQuery(query);
	}

/*	public NewsLetterMaster getNewsLetterObjectFromDB(int numNewsLetterId) {
		// TODO Auto-generated method stub
		
		//List<NewsLetterMaster> newsletterList=daoHelper.findByAttribute(NewsLetterMaster.class, "numNewsLetterId", numNewsLetterId);
	
		return daoHelper.findByAttribute(NewsLetterMaster.class,"numNewsLetterId", numNewsLetterId).get(0);
	
		// return newsletterList.get(0);
		
	}*/
	
	public NewsLetterMaster getNewsLetterObjectFromDB(int numNewsLetterId){
		NewsLetterMaster newsLetterMaster =null;
		String query = "from NewsLetterMaster where numNewsLetterId="+numNewsLetterId;
		List<NewsLetterMaster> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			newsLetterMaster =list.get(0);
		}
		return newsLetterMaster;	
	}
	
	public void updateNewsLetterFilterMapping(
			NewsLetterFilterMapping nlfm) {
		daoHelper.persist(NewsLetterFilterMapping.class, nlfm);
		
	}

	/*public void updateMappedNewsLettersToFilter(int numFilterId) {
		// TODO Auto-generated method stub
		List<NewsLetterMaster> dataList=daoHelper.findByAttribute(NewsLetterMaster.class, "numNewsLetterId", numNewsLetterId);
	}*/

/*	*/

/*	public void updateNewsLetterDocumenst(
			NewsletterDocuments nld) {
		// TODO Auto-generated method stub
		daoHelper.persist(NewsletterDocuments.class, nld);
		
	}*/

	public void updateNewsLetter(NewsLetterMaster newsLetterMasterObj) {
		// TODO Auto-generated method stub
		daoHelper.persist(NewsLetterMaster.class, newsLetterMasterObj);
	}

	public List<NewsLetterMaster> getAllSavedPeriodicNewsLetterMaster(String currentDay,String currentMonth,String currentYear) {
		
		String query = "Select r from NewsLetterMaster r where r.numIsValid =1 and isPeriodic=1 and " 
				          +" (substr(r.strPeriodicScheduleDate,1,2)='"+currentDay+"' or substr(r.strPeriodicScheduleDate,1,2) ='00') and " 
				          +" (upper(substr(r.strPeriodicScheduleDate,4,3)) =upper('"+currentMonth+"') or substr(r.strPeriodicScheduleDate,4,3) ='000') AND"
				          +" (substr(r.strPeriodicScheduleDate,8,4) ='"+currentYear+"' or substr(r.strPeriodicScheduleDate,8,4) ='0000')";
		return daoHelper.findByQuery(query);
	}

	public List<NewsLetterMaster> getAllSavedPeriodicNewsLetters(String currentDay, String currentMonth, String currentYear) {
		String query = "Select r from NewsLetterMaster r where r.numIsValid =1 and isPeriodic=1 "; 
//		          +" (substr(r.strPeriodicScheduleDate,1,2)>='"+currentDay+"' or substr(r.strPeriodicScheduleDate,1,2) ='00') and " 
//		          +" (upper(substr(r.strPeriodicScheduleDate,4,3)) >=upper('"+currentMonth+"') or substr(r.strPeriodicScheduleDate,4,3) ='000') AND"
//		          +" (substr(r.strPeriodicScheduleDate,8,4) >='"+currentYear+"' or substr(r.strPeriodicScheduleDate,8,4) ='0000')";
		return daoHelper.findByQuery(query);
		
	}
	
	public List<NewsLetterFilterMaster> getAllFiltersByFilterType(String filterType) {
		StringBuilder query = new StringBuilder("from NewsLetterFilterMaster  where numIsValid=1");
		if(null != filterType && !filterType.equalsIgnoreCase("ALL")){
			query.append(" and strFilterType='"+filterType+"'");
		}
		return daoHelper.findByQuery(query.toString());
	}
	
	
	public List<NewsLetterFilterMaster> getFiltersByIds(String filterId){
		StringBuilder query = new StringBuilder("from NewsLetterFilterMaster  where numIsValid=1");
		query.append(" and numFilterId in ("+filterId+")");
		return daoHelper.findByQuery(query.toString());
	}
	
	public String getDetailsOfEmplAppLevel(int roleId, int projectId){

	    String  query = "SELECT String_agg(str_office_email,',') FROM pms_employee_master WHERE emp_id IN (SELECT num_emp_id FROM pms_employee_role_mst WHERE num_isvalid=1 and (dt_enddate is null or dt_enddate >= CURRENT_DATE) and  role_id = "+roleId+" and num_project_id="+projectId+")";

	    List<String> list = daoHelper.runNative(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return "";
	}
	
	public String getDetailsOfEmplGroupLevel(int roleId, int projectId){

	    String query = "SELECT String_agg(str_office_email,',') FROM pms_employee_master WHERE emp_id IN (SELECT num_emp_id FROM pms_employee_role_mst WHERE num_isvalid=1 and role_id = "+roleId+" and num_group_id = (select a.num_group_id_fk from trans_application a,application_project b where a.num_application_id = b.application_id and b.project_id="+projectId+")) ";

	    List<String> list = daoHelper.runNative(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return "";
	}
	
	public String getDetailsOfEmplOrgLevel(int roleId, int projectId){

	    String query = "SELECT String_agg(str_office_email,',') FROM pms_employee_master WHERE emp_id IN (SELECT num_emp_id FROM pms_employee_role_mst WHERE num_isvalid=1 and role_id = "+roleId+" AND num_organisation_id = ( SELECT organisation_id FROM pms_group_master WHERE group_id = ( SELECT A .num_group_id_fk FROM trans_application A, application_project b WHERE A .num_application_id = b.application_id AND b.project_id = "+projectId+" ))) ";

	    List<String> list = daoHelper.runNative(query);
		if(list.size()>0) {
			return list.get(0);
		}
		return "";
	}
	
	public List<NewsletterDocuments> getNewsletterDocuments(int numNewsLetterId) {
		String query = "Select r from NewsletterDocuments r where r.newsLetterId="+numNewsLetterId+" and r.numIsValid =1";
		List<NewsletterDocuments> nld = daoHelper.findByQuery(query);
		return nld;
	}
	
	public List<NewsLetterRoleMapping> getDetailsOfRole(int numNewsLetterId){
		String query = "Select r from NewsLetterRoleMapping r where r.numNewsLetterId="+numNewsLetterId+" and r.numIsValid =1";
		List<NewsLetterRoleMapping> nld = daoHelper.findByQuery(query);
		return nld;
	}
	
	public NewsletterDocuments getNewsletterDocumentsByDocId(int docId) {
		String query = "select r from NewsletterDocuments r where r.numId = "+docId;
		List<NewsletterDocuments> lod = daoHelper.findByQuery(query);
		if(lod.size()>0){
			return lod.get(0);
		}
		else{
			NewsletterDocuments od = new NewsletterDocuments();
			od.setStrdocname("NONE");
			od.setNumId(0);
			return od;
		}
	}
	
}
