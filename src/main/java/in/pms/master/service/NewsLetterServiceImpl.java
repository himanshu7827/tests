package in.pms.master.service;



import in.pms.global.dao.DaoHelper;

import in.pms.global.util.DateUtils;
import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.mail.dao.SendMail;
import in.pms.master.dao.NewsLetterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.domain.NewsLetterFilterMapping;
import in.pms.master.domain.NewsLetterFilterMaster;
import in.pms.master.domain.NewsLetterMaster;
import in.pms.master.domain.NewsLetterRoleMapping;
import in.pms.master.domain.NewsletterDocuments;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
/*import in.cdacnoida.serb.gl.domain.Registration;
import in.cdacnoida.serb.gl.misc.GlobalFun;
import in.cdacnoida.serb.gl.misc.ResourceBundleFile;
import in.cdacnoida.serb.gl.misc.SendMail;*/
import in.pms.master.model.NewsLetterFilterForm;
/*import in.cdacnoida.serb.proposer.domain.NewsletterDocuments;
import in.cdacnoida.serb.proposer.model.ProposalSubmissionForm;
import in.cdacnoida.serb.proposer.service.ProposalSubmissionService;*/



import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class NewsLetterServiceImpl implements NewsLetterService {
	
	Logger newsLetterPeriodicLogger =  LoggerFactory.getLogger("adminNewsLetterPeriodic");

	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	/*@Autowired
	GlobalDao globalDao;*/
	 
	@Autowired
	public NewsLetterDao newsletterDao;
	
	@Autowired
	public DocumentService documentService;
	
/*	@Autowired
	ProposalSubmissionService proposalSubmissionService;*/
	
	/* @Autowired
	 MSProposalDao msProposalDao;*/
	
	@Autowired
	public GlobalService globalService;

	@Autowired
	public FileUploadService fileUploadService;


	
	@Autowired
	DaoHelper daoHelper;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	RoleMasterDao roleMasterDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	/*@Autowired
	SendMail smail;*/
	
/*	
	
	


	


		
	
	public void getNewsLettersToBeTransfer()
	{
		System.out.println(" I am running");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate= formatter.format(new Date());
		 StringBuffer strAllEMailId=new StringBuffer();
		List<NewsLetterMaster> newsLetterMasterList = newsletterDao.getAllSavedNewsLetterMaster(currentDate);

		System.out.println("size of the news letter list is"+newsLetterMasterList.size());
		if(newsLetterMasterList.size()>0)
		{
			Iterator<NewsLetterMaster> itr=newsLetterMasterList.iterator();
			String queryFinal ="";
			String strSubject="";
			String strContent="";
			String smsContent="";
			while(itr.hasNext())
			{
				queryFinal="";
				NewsLetterMaster newsLetterMaster = itr.next();
				strSubject=newsLetterMaster.getStrSubject();
				strContent = newsLetterMaster.getStrContentMail();
				smsContent=newsLetterMaster.getStrContentSMS();

				List<NewsletterDocuments> documentList = newsletterDao.getNewsletterDocuments(newsLetterMaster.getNumNewsLetterId());
				List<NewsLetterFilterMapping> newsLetterFilterMappingList = newsletterDao.getFilterMappingForNewsLetter(newsLetterMaster.getNumNewsLetterId());
				System.out.println("size of the news letter filter mapping"+newsLetterFilterMappingList.size());
				if(newsLetterFilterMappingList.size()>0)
				{
					Iterator<NewsLetterFilterMapping> itr1=newsLetterFilterMappingList.iterator();
					while(itr1.hasNext())
					{
						NewsLetterFilterMapping newsLetterFilterMapping = itr1.next();
						int numFilterId=newsLetterFilterMapping.getNumFilterId();
						NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
						String query = nlfm.getStrFilterQuery();
						System.out.println(query);
						queryFinal = queryFinal + query + " UNION ";
					}

					queryFinal = queryFinal.substring(0, queryFinal.length()-7);
				}

				String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";

				String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";

				queryFinal = initialQuery + queryFinal + endQuery;

				System.out.println("this is final query "+queryFinal);
				List<NewsLetterFilterForm> 	uniqueSubjects = newsletterDao.getUsersForNewsLetter(queryFinal);
				System.out.println("size of the uniqe subjects is "+uniqueSubjects.size());
				
				scheduleEmailAndSmsProcessing(uniqueSubjects,strSubject,strContent,smsContent,documentList); 
				newsLetterMaster.setStrStatus("SENT");
				newsletterDao.updateNewsLetterStatus(newsLetterMaster);
				
			}
			
			// Email Confirmation For Committee Member
			 try {
				 SendMail smail = new SendMail();
				 String emailIDSchedulerCC=ResourceBundleFile.getValueFromKey("emailSchedulerCC");
				 smail.TransferToMailServer(emailIDSchedulerCC, "News Letter To Be Transfer", newsLetterMasterList.size()+", "+strSubject);
				 System.out.println("send mail emailSchedulerCC = ");
			 } catch (Exception e) {
				// TODO: handle exception
			}

		}
	}


	*/
				
				//Disabling the code of sending SMS on 7th Sep 2018 as SMS facility is not enabled in portal

				/*try{
					StringBuffer mobileNumbers = new StringBuffer();
					for (int i = 0 ; i < uniqueSubjects.size(); i++){
						mobileNumbers.append(","+ uniqueSubjects.get(i).getStrMobileNumber());
					}
					String mobileStr = mobileNumbers.substring(1);
					mobileStr = deDup(mobileStr);
					mobileStr = mobileStr.replaceAll(",0,", Matcher.quoteReplacement(","));

					SMSHttpPostClient.sendBulkSMS(mobileStr,smsContent);
				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception in sms catch");
				}*/
			/*}
		}
	}*/

		
	
/*	public String deDup(String s) {
	    return new LinkedHashSet<String>(Arrays.asList(s.split(","))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", ",");
	}
	
	
	
	public boolean checkValidation(String queryString)
	{
		return newsletterDao.checkValidation(queryString);
	}
	
	public boolean checkValidationForProposalID(String queryString)
	{
		return newsletterDao.checkValidationForProposalID(queryString);
	}*/
	
	
	
	public List<NewsLetterFilterForm> getAllSavedFilters() {

		List<NewsLetterFilterMaster> lnffm = newsletterDao.getAllSavedFilters();
		List<NewsLetterFilterForm> nlff = convertDomainToForm(lnffm);
		return nlff;
	}


private List<NewsLetterFilterForm> convertDomainToForm(
		List<NewsLetterFilterMaster> lnffm) {

	List<NewsLetterFilterForm> lnlff = new ArrayList<NewsLetterFilterForm>();
	for (int i = 0; i < lnffm.size(); i++) {
		NewsLetterFilterMaster nlfm = lnffm.get(i);
		NewsLetterFilterForm nlff = new NewsLetterFilterForm();
		nlff.setNumFilterId(nlfm.getNumFilterId());
		nlff.setStrFilterDesc(nlfm.getStrFilterDesc());
		nlff.setStrFilterName(nlfm.getStrFilterName());
		nlff.setStrFilterQuery(nlfm.getStrFilterQuery());
		nlff.setStrFilterType(nlfm.getStrFilterType());

		lnlff.add(nlff);
	}
	return lnlff;

}

public void saveFilter(NewsLetterFilterForm form)
{
	NewsLetterFilterMaster obj=new NewsLetterFilterMaster();
	obj.setNumFilterId(form.getNumFilterId());
	obj.setStrFilterName(form.getStrFilterName());
	obj.setStrFilterDesc(form.getStrFilterDesc());
	obj.setStrFilterQuery(form.getStrFilterQuery());
	obj.setNumTrUserId(form.getNumUserId());
	obj.setNumIsValid(1);
	obj.setStrFilterType(form.getStrFilterType());
	obj.setDtTrDate(new Date());
	newsletterDao.addNewsLetterFilter(obj);
}

public void deleteFilter(NewsLetterFilterForm form)
{
	
	int count=form.getArrCheckbox().length;
	int[] id= new int[count];
	id= form.getArrCheckbox();
	for (int i = 0; i <count; i++) 
	{  
		newsletterDao.deleteFilter(id[i]);
	}
	
	 
}

public List<NewsLetterFilterForm> getAllFilters() {

	List<NewsLetterFilterMaster> lnffm = newsletterDao.getAllFilters();
	lnffm = newsletterDao.getAllFilters();
	List<NewsLetterFilterForm> nlff = convertDomainToForm(lnffm);

	return nlff;
}

@Override
public List<NewsLetterMaster> getAllSavedNewsLetter() {
	// TODO Auto-generated method stub
	
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String currentDate= formatter.format(new Date());

	DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
	String currentDate2= formatter1.format(new Date());
	String currentDay=currentDate2.substring(0, 2);
	String currentMonth=currentDate2.substring(3, 6);
	String currentYear=currentDate2.substring(7);
	
	
	List<NewsLetterMaster> list= newsletterDao.getAllSavedNewsLetters(currentDate );
	
	List<NewsLetterMaster> list2=newsletterDao.getAllSavedPeriodicNewsLetters(currentDay,currentMonth, currentYear);
	list.addAll(list2);
	return list;
	
	
	
}

public void sendAllNewsLetterNow(NewsLetterFilterForm newsLetterFilterForm) {
	
String strSubject = newsLetterFilterForm.getStrSubject();
String subject=strSubject;
String globalTemplate = newsLetterFilterForm.getStrContent();
String strContent = globalTemplate;

String strIDs = newsLetterFilterForm.getStrIDs();

SendMail smail = new SendMail();
beanFactory.autowireBean(smail);
List<NewsLetterFilterForm> uniqueSubjects = new ArrayList<NewsLetterFilterForm>();
String queryFinal = "";

String filterType = newsLetterFilterForm.getStrFilterType();
if(filterType==null)
	filterType="";	

if(filterType.equalsIgnoreCase("p")){

if(strIDs.length()>0){
	String[] strArray = strIDs.split(",");		
	for(int i =0; i < strArray.length; i++){
		int numFilterId = Integer.parseInt(strArray[i]);
		NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
		String query = nlfm.getStrFilterQuery();
		
			if(strArray.length>1)
				queryFinal = queryFinal + query + " UNION ";
			else
				queryFinal = queryFinal + query;
		}
	
		if(strArray.length>1)
			queryFinal = queryFinal.substring(0, queryFinal.length()-7);
	}


	uniqueSubjects= newsletterDao.getPropNewsLetter(queryFinal);
	if(uniqueSubjects.size()>0)
	{	
		
		Iterator<NewsLetterFilterForm> itr=uniqueSubjects.iterator();
		
		while(itr.hasNext())
		{
			String toMailIds="";
			String ccMailIds="";
			String bccMailIds="";
			NewsLetterFilterForm newsLetterFilterForm1=itr.next();
			
			int projectId = newsLetterFilterForm1.getNumProjectId();
			
			
			String[] roleTo = newsLetterFilterForm.getTo().split(",");	
			if(roleTo.length>0){
			for(int i =0; i < roleTo.length; i++){
				if(!roleTo[i].equals("")){
				int roleId = Integer.parseInt(roleTo[i]);
				 String toMailId = getDetailsOfEmplApplLevel(roleId,projectId) ;
				 if(toMailId!=null && !toMailId.equals("")){
					 toMailIds=  toMailId +","+toMailIds;
				 		}
		
					}				
				}
			}
			try{
			
			if(toMailIds!=null && !toMailIds.equals("")){
			char lastLetterOfTo = toMailIds.charAt(toMailIds.length() - 1);
			if(lastLetterOfTo==','){
				toMailIds=toMailIds.substring(0,toMailIds.length()-1);
						}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String[] roleCc = newsLetterFilterForm.getCc().split(",");
			if(roleCc.length>0){
			for(int j =0; j < roleCc.length; j++){
				if(!roleCc[j].equals("")){
				int roleId = Integer.parseInt(roleCc[j]);
				String ccMailId = getDetailsOfEmplApplLevel(roleId,projectId);
				 if(ccMailId!=null && !ccMailId.equals("")){
					 ccMailIds=  ccMailId+","+ ccMailIds;
					 
				 		}
					}
				}
			}
			try{
				if(ccMailIds!=null && !ccMailIds.equals("")){
				char lastLetterOfCc = ccMailIds.charAt(ccMailIds.length() - 1);
				if(lastLetterOfCc==','){
				ccMailIds=ccMailIds.substring(0,ccMailIds.length()-1);
			}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String[] roleBCc = newsLetterFilterForm.getBcc().split(",");	
			if(roleBCc.length>0){
			for(int k =0; k < roleBCc.length; k++){
				if(!roleBCc[k].equals("")){
				int roleId = Integer.parseInt(roleBCc[k]);
				 String bccMailId = getDetailsOfEmplApplLevel(roleId,projectId);
				 if(bccMailId!=null && !bccMailId.equals("")){
					 bccMailIds=  bccMailId+","+bccMailIds;
			}
		
				 	}
				}
			}
			try{
				if(bccMailIds!=null && !bccMailIds.equals("")){
				char lastLetterOfBCc = bccMailIds.charAt(bccMailIds.length() - 1);
				if(lastLetterOfBCc==','){
				bccMailIds=bccMailIds.substring(0,bccMailIds.length()-1);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			ProjectMasterDomain projDetails=projectMasterDao.getProjectMasterDataById(projectId);
			if(projDetails!=null){
				strContent = globalTemplate;
				subject=strSubject;
				Calendar calendar = Calendar.getInstance();
				 String[] monthName = {"January", "February",
			                "March", "April", "May", "June", "July",
			                "August", "September", "October", "November",
			                "December"};
				int currentMonth=calendar.get(Calendar.MONTH);
				String month = monthName[currentMonth];
				int currentYear = calendar.get(Calendar.YEAR);
				strContent = strContent.replaceAll("\\$PROJECT\\_NAME\\$",projDetails.getStrProjectName());
				strContent = strContent.replaceAll("\\$PROJECT\\_COST\\$",""+CurrencyUtils.convertToINR(projDetails.getProjectCost()));
				strContent = strContent.replaceAll("\\$PROJECT\\_START\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectStartDate()));
				strContent = strContent.replaceAll("\\$PROJECT\\_END\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectEndDate()));
				/*strContent = strContent.replaceAll("\\$PROJECT\\_DURATION\\$",""+projDetails.getProjectDuration());*/
				strContent = strContent.replaceAll("\\$CURRENT\\_MONTH\\$",""+month);
				strContent = strContent.replaceAll("\\$CURRENT\\_YEAR\\$",""+currentYear);
				
				
				subject = subject.replaceAll("\\$PROJECT\\_NAME\\$",projDetails.getStrProjectName());
				subject = subject.replaceAll("\\$PROJECT\\_COST\\$",""+CurrencyUtils.convertToINR(projDetails.getProjectCost()));
				subject = subject.replaceAll("\\$PROJECT\\_START\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectStartDate()));
				subject = subject.replaceAll("\\$PROJECT\\_END\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectEndDate()));
				/*strContent = strContent.replaceAll("\\$PROJECT\\_DURATION\\$",""+projDetails.getProjectDuration());*/
				subject = subject.replaceAll("\\$CURRENT\\_MONTH\\$",""+month);
				subject = subject.replaceAll("\\$CURRENT\\_YEAR\\$",""+currentYear);
			}
			
			
			
			try {
			smail.TransferToMailServerWithCc(toMailIds, subject,strContent,ccMailIds,bccMailIds,filterType,projectId);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			
			
		}
	}
	NewsLetterMaster nlm1 = new NewsLetterMaster();
	
	nlm1.setNumIsValid(1);
	nlm1.setDtTrDate(new Date());
	
	nlm1.setNumTotalSubscribers(uniqueSubjects.size());
	nlm1.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	nlm1.setStrContentMail(strContent);
	nlm1.setStrContentSMS(newsLetterFilterForm.getStrSMSContent());
	nlm1.setStrMailSMSFlag(newsLetterFilterForm.getStrSMSFlag());
	nlm1.setStrStatus("SUB");
	nlm1.setStrSubject(strSubject);
	NewsLetterMaster lnlm = newsletterDao.addNewsLetterDetails(nlm1);
	NewsLetterRoleMapping nlrm= new NewsLetterRoleMapping();
	nlrm.setNumIsValid(1);
	nlrm.setNumNewsLetterId(lnlm.getNumNewsLetterId());
	nlrm.setStrTo(newsLetterFilterForm.getTo());
	nlrm.setStrBcc(newsLetterFilterForm.getBcc());
	nlrm.setStrCc(newsLetterFilterForm.getCc());
	nlrm.setDtTrDate(new Date());
	nlrm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	newsletterDao.addNewsLetterRoleMapping(nlrm);
	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
			nlfm.setNumIsValid(1);
			nlfm.setDtTrDate(new Date());
			nlfm.setNumFilterId(numFilterId);
			nlfm.setNumNewsletterId(lnlm.getNumNewsLetterId());
			nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
			newsletterDao.addNewsLetterMapping(nlfm);
	

		}
	}


	
}
/*else{
	
	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
			String query = nlfm.getStrFilterQuery();
			
			queryFinal = queryFinal + query + " UNION ";
		}
		queryFinal = queryFinal.substring(0, queryFinal.length()-7);
	}
			
	String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";
	
	String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";
	
	queryFinal = initialQuery + queryFinal + endQuery;
	uniqueSubjects = newsletterDao.getUsersForNewsLetter(queryFinal);



NewsLetterMaster nlm = new NewsLetterMaster();
	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
			String query = nlfm.getStrFilterQuery();
		
			queryFinal = queryFinal + query + " UNION ";
		}
		queryFinal = queryFinal.substring(0, queryFinal.length()-7);
	}
	String initialQuery1 = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";
	
	String endQuery1 = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";
	
	queryFinal = initialQuery1 + queryFinal + endQuery1;
	
	uniqueSubjects = newsletterDao.getUsersForNewsLetter(queryFinal);
	
	NewsLetterMaster nlm2 = new NewsLetterMaster();
	

nlm2.setNumIsValid(1);
nlm2.setDtTrDate(new Date());

nlm2.setNumTotalSubscribers(uniqueSubjects.size());
nlm2.setNumTrUserId(newsLetterFilterForm.getNumUserId());
nlm2.setStrContentMail(strContent);
nlm2.setStrContentSMS(newsLetterFilterForm.getStrSMSContent());
nlm2.setStrMailSMSFlag(newsLetterFilterForm.getStrSMSFlag());
nlm2.setStrStatus("SUB");
nlm2.setStrSubject(strSubject);

NewsLetterMaster lnlm = newsletterDao.addNewsLetterDetails(nlm2);

if(strIDs.length()>0){
	String[] strArray = strIDs.split(",");		
	for(int i =0; i < strArray.length; i++){
		int numFilterId = Integer.parseInt(strArray[i]);
		NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
		nlfm.setNumIsValid(1);
		nlfm.setDtTrDate(new Date());
		nlfm.setNumFilterId(numFilterId);
		nlfm.setNumNewsletterId(lnlm.getNumNewsLetterId());
		nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
		newsletterDao.addNewsLetterMapping(nlfm);

	}
}

int quantum = 90;
			
int totalIterations = uniqueSubjects.size()/quantum;
int remainder = uniqueSubjects.size()%quantum;
//SendMail smail = new SendMail();


String documentIds = newsLetterFilterForm.getDocumentIds();
if(documentIds!=null && documentIds.length()>0){ //send document as attachment
	documentIds = documentIds.substring(1, documentIds.length()-1);
	
	
	String[] documentArray = documentIds.split(",");
	File[] fileArray = new File[documentArray.length];
	String[] nameArray = new String[documentArray.length];
	
	for(int var = 0; var<documentArray.length;var++){
		try{;

		String docname = documentArray[var];
		NewsletterDocuments od = documentService.getNewsletterDocuments(Integer.parseInt(docname));
	
			
		File tempFile = File.createTempFile(od.getStrdocname(), ".pdf");
	    tempFile.deleteOnExit();
	    FileOutputStream out = new FileOutputStream(tempFile);
	    String globalPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
	    //String globalPath = ResourceBundleFile.getValueFromKey("NEWSLETTER_DOCUMENTS");
	    
	    String path = globalPath;
		
		String finalName = path + docname + ".pdf";
	    
	    IOUtils.copy(fileUploadService.getFiles(finalName), out);
	    fileArray[var] = tempFile;
	    
		nameArray[var] = od.getStrdocname();

		documentService.updateNewsletterDocuments(lnlm.getNumNewsLetterId(),Integer.parseInt(docname));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	if(totalIterations>0){
		for(int j = 0 ; j < totalIterations ; j++){
			try {
			StringBuffer emailIds = new StringBuffer();
			for(int i = (j*quantum); i < ((j+1)*quantum);i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
		StringBuffer emailIds = new StringBuffer();	
		
		for(int i = (totalIterations*quantum); i < ((totalIterations*quantum)+remainder); i++){
			emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
		}
		String emailStr = emailIds.substring(1);
		
			smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else{
		try {
		StringBuffer emailIds = new StringBuffer();
		for(int i = 0; i < remainder; i++){
			emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
		}
		String emailStr = emailIds.substring(1);
		
			smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	

}
else
{
	if(totalIterations>0){
		for(int j = 0 ; j < totalIterations ; j++){
			try {
			StringBuffer emailIds = new StringBuffer();
			for(int i = (j*quantum); i < ((j+1)*quantum);i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		StringBuffer emailIds = new StringBuffer();	
		try {
		for(int i = (totalIterations*quantum); i < ((totalIterations*quantum)+remainder); i++){
			emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
		}
		String emailStr = emailIds.substring(1);
		
			smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else{
		try {
		StringBuffer emailIds = new StringBuffer();
		for(int i = 0; i < remainder; i++){
			emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
		}
		String emailStr = emailIds.substring(1);
		
			smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}

}*/

}



public void saveNewsLetters(NewsLetterFilterForm newsLetterFilterForm) throws ParseException {
	
	
	String strSubject = newsLetterFilterForm.getStrSubject();
	String strContent = newsLetterFilterForm.getStrContent();
	String strIDs = newsLetterFilterForm.getStrIDs();
	
	
	
	NewsLetterMaster nlm = new NewsLetterMaster();
	
	nlm.setNumIsValid(1);
	nlm.setDtTrDate(new Date());
	
	
	nlm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	nlm.setStrContentMail(strContent);
	nlm.setStrContentSMS(newsLetterFilterForm.getStrSMSContent());
	nlm.setStrMailSMSFlag(newsLetterFilterForm.getStrSMSFlag());
	nlm.setStrStatus("SUB");
	nlm.setStrSubject(strSubject);
	if(newsLetterFilterForm.getPeriodicNewsletterDate()!=null && !newsLetterFilterForm.getPeriodicNewsletterDate().isEmpty())
	nlm.setIsPeriodic(1);
	else
	nlm.setIsPeriodic(0);
	try{
		if(newsLetterFilterForm.getPeriodicNewsletterDate()!=null){
	nlm.setStrPeriodicScheduleDate(newsLetterFilterForm.getPeriodicNewsletterDate());
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	try{
	if(newsLetterFilterForm.getScheduledDate()!=null){
    nlm.setScheduledDate(DateUtils.dateStrToDate(newsLetterFilterForm.getScheduledDate()));
	}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	NewsLetterMaster lnlm = newsletterDao.addNewsLetterDetails(nlm);
	NewsLetterRoleMapping nlrm= new NewsLetterRoleMapping();
	nlrm.setNumIsValid(1);
	nlrm.setNumNewsLetterId(lnlm.getNumNewsLetterId());
	nlrm.setStrTo(newsLetterFilterForm.getTo());
	nlrm.setStrBcc(newsLetterFilterForm.getBcc());
	nlrm.setStrCc(newsLetterFilterForm.getCc());
	nlrm.setDtTrDate(new Date());
	nlrm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	newsletterDao.addNewsLetterRoleMapping(nlrm);
	
	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
			nlfm.setNumIsValid(1);
			nlfm.setDtTrDate(new Date());
			nlfm.setNumFilterId(numFilterId);
			nlfm.setNumNewsletterId(lnlm.getNumNewsLetterId());
			nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
			newsletterDao.addNewsLetterMapping(nlfm);
			
			
		}
	}

	
	
	String documentIds = newsLetterFilterForm.getDocumentIds();
	if(documentIds!=null && documentIds.length()>0){ //send document as attachment
		documentIds = documentIds.substring(1, documentIds.length()-1);
		
	/*	System.out.println("Document IDS "+ documentIds);*/
		String[] documentArray = documentIds.split(",");
		File[] fileArray = new File[documentArray.length];
		String[] nameArray = new String[documentArray.length];
		
		for(int var = 0; var<documentArray.length;var++){
			try{

			String docname = documentArray[var];
			NewsletterDocuments od = documentService.getNewsletterDocuments(Integer.parseInt(docname));
	
				
			File tempFile = File.createTempFile(od.getStrdocname(), ".pdf");
		    tempFile.deleteOnExit();
		    FileOutputStream out = new FileOutputStream(tempFile);
		    
		    String globalPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
		    
		    String path = globalPath;
			
			String finalName = path + docname + ".pdf";
		    
		    IOUtils.copy(fileUploadService.getFiles(finalName), out);
		    fileArray[var] = tempFile;
		    
			nameArray[var] = od.getStrdocname();

			documentService.updateNewsletterDocuments(lnlm.getNumNewsLetterId(),Integer.parseInt(docname));
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	
}
public void deleteNewsLetter(String[] ids) {
	// TODO Auto-generated method stub
	for (int i = 0; i < ids.length; i++) {
		NewsLetterMaster newsletterObj = newsletterDao
				.getNewsLetterObjectFromDB(Integer.parseInt(ids[i]));
		newsletterObj.setNumIsValid(0);
		newsletterDao.updateNewsLetter(newsletterObj);
	}
}

@Override
public List<Integer> getNewsLetterFilterMappingDetails(
		int nlID) {
	// TODO Auto-generated method stub
	
	
	List<Integer> filterList=new ArrayList<Integer>();
	List<NewsLetterFilterMapping> filterMappingList = newsletterDao.getFilterMappingForNewsLetter(nlID);
	
	for (NewsLetterFilterMapping newsLetterFilterMapping : filterMappingList) {
		
		filterList.add(newsLetterFilterMapping.getNumFilterId());
	
	}
	
	
	return filterList;
}

@Override
public String getNewsLetterDisplayDate(int newsletterid) {
NewsLetterMaster newsletter = newsletterDao
			.getNewsLetterObjectFromDB(newsletterid);
	
	String scheuledDate = DateUtils.dateToString(newsletter
			.getScheduledDate());

	return scheuledDate;
}

@Override
public void updateNewsLetter(NewsLetterFilterForm newsLetterFilterForm) throws ParseException {
	
	String strSubject = newsLetterFilterForm.getStrSubject();
	String strContent = newsLetterFilterForm.getStrContent();
	NewsLetterMaster newsLetterMasterObj = newsletterDao
			.getNewsLetterObjectFromDB(newsLetterFilterForm
					.getNumletterId());
	String strIDs = newsLetterFilterForm.getStrIDs();
	 List<String> listOfFilter = new ArrayList<String>(); 
	//List<String> listOfFilter = new ArrayList<String>();

	if (strIDs.length() > 0) {

		String[] strArray = strIDs.split(",");

		for (int i = 0; i < strArray.length; i++) {
			listOfFilter.add(strArray[i]);
		}
	}

	List<NewsLetterFilterMapping> newsLetterFilterMappingList = newsletterDao
			.getFilterMappingForNewsLetter(newsLetterMasterObj
					.getNumNewsLetterId());

	for (NewsLetterFilterMapping nlfm : newsLetterFilterMappingList) {

		if (listOfFilter.contains("" + nlfm.getNumFilterId())) {
			
			listOfFilter.remove("" + nlfm.getNumFilterId());
		} else if (!listOfFilter.contains("" + nlfm.getNumFilterId())) {
			nlfm.setNumIsValid(0);
			newsletterDao.updateNewsLetterFilterMapping(nlfm);
		}

	}

	

	for (String filter : listOfFilter) {
		NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
		nlfm.setNumIsValid(1);
		nlfm.setDtTrDate(new Date());
		nlfm.setNumFilterId(Integer.parseInt(filter));
		nlfm.setNumNewsletterId(newsLetterFilterForm.getNumletterId());
		nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
		newsletterDao.addNewsLetterMapping(nlfm);

	}

	String documentIds = newsLetterFilterForm.getDocumentIds();
	if (documentIds != null && documentIds.length() > 0) { // send document
															// as attachment
		documentIds = documentIds.substring(1, documentIds.length() - 1);

	
		String[] documentArray = documentIds.split(",");
		File[] fileArray = new File[documentArray.length];
		String[] nameArray = new String[documentArray.length];

		for (int var = 0; var < documentArray.length; var++) {
			try {

				String docname = documentArray[var];
				NewsletterDocuments od = documentService
						.getNewsletterDocuments(Integer.parseInt(docname));

				File tempFile = File.createTempFile(od.getStrdocname(),
						".pdf");
				tempFile.deleteOnExit();
				FileOutputStream out = new FileOutputStream(tempFile);

				String globalPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");

				String path = globalPath;

				String finalName = path + docname + ".pdf";

				IOUtils.copy(fileUploadService.getFiles(finalName), out);
				fileArray[var] = tempFile;

				nameArray[var] = od.getStrdocname();

			

				documentService.updateNewsletterDocuments(
						newsLetterFilterForm.getNumletterId(),
						Integer.parseInt(docname));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	newsLetterMasterObj.setNumIsValid(1);
	newsLetterMasterObj.setDtTrDate(new Date());
	newsLetterMasterObj.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	newsLetterMasterObj.setStrContentMail(strContent);

	newsLetterMasterObj.setStrContentSMS(newsLetterFilterForm
			.getStrSMSContent());
	newsLetterMasterObj.setStrMailSMSFlag(newsLetterFilterForm
			.getStrSMSFlag());
	newsLetterMasterObj.setStrStatus("SUB");
	newsLetterMasterObj.setStrSubject(strSubject);
	if(newsLetterFilterForm.getPeriodicNewsletterDate()!=null && !newsLetterFilterForm.getPeriodicNewsletterDate().isEmpty())
	newsLetterMasterObj.setIsPeriodic(1);
	else
	newsLetterMasterObj.setIsPeriodic(0);	
	newsLetterMasterObj.setStrPeriodicScheduleDate(newsLetterFilterForm.getPeriodicNewsletterDate());
	newsLetterMasterObj.setScheduledDate(DateUtils.dateStrToDate(newsLetterFilterForm.getScheduledDate()));
	newsletterDao.updateNewsLetter(newsLetterMasterObj);
	List<NewsLetterRoleMapping> detailsOfRole=newsletterDao.getDetailsOfRole(newsLetterFilterForm.getNumletterId());
	
	NewsLetterRoleMapping nlrm= new NewsLetterRoleMapping();
	nlrm.setNumIsValid(1);
	nlrm.setNumNewsLetterId(detailsOfRole.get(0).getNumNewsLetterId());
	nlrm.setStrTo(newsLetterFilterForm.getTo());
	nlrm.setStrBcc(newsLetterFilterForm.getBcc());
	nlrm.setStrCc(newsLetterFilterForm.getCc());
	nlrm.setDtTrDate(new Date());
	nlrm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	nlrm.setNumId(detailsOfRole.get(0).getNumId());
	newsletterDao.addNewsLetterRoleMapping(nlrm);

}

	/*


	



	





		
		
	//===============amit saini ==============================================	

	public void sendAllNewsLetterNow(NewsLetterFilterForm newsLetterFilterForm) {
	
	String strSubject = newsLetterFilterForm.getStrSubject();
	
	String globalTemplate = newsLetterFilterForm.getStrContent();
	String strContent = globalTemplate;
	
	String strIDs = newsLetterFilterForm.getStrIDs();

	SendMail smail = new SendMail();
	
	List<NewsLetterFilterForm> uniqueSubjects = new ArrayList<NewsLetterFilterForm>();
	String queryFinal = "";
	
	String filterType = newsLetterFilterForm.getStrFilterType();
	if(filterType==null)
		filterType="";	
	
	if(filterType.equalsIgnoreCase("p")){
	
	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
			String query = nlfm.getStrFilterQuery();
			
				if(strArray.length>1)
					queryFinal = queryFinal + query + " UNION ";
				else
					queryFinal = queryFinal + query;
			}
		
			if(strArray.length>1)
				queryFinal = queryFinal.substring(0, queryFinal.length()-7);
		}

		String initialQuery = "select a.num_proposal_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0)as mobile_no,b.str_user_name from serb_proposal_master a , serb_user_registration b , serb_user_profile c where a.num_pi=b.num_user_id and b.num_user_id=c.user_id  and a.num_isvalid=1 and b.num_isvalid=1 and c.num_isvalid=1 and a.num_proposal_id IN(";
		
		String endQuery = " );" ;
				//a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";
		queryFinal = initialQuery + queryFinal+endQuery;
		
	
		uniqueSubjects= newsletterDao.getPropNewsLetter(queryFinal);
		if(uniqueSubjects.size()>0)
		{	
			
			Iterator<NewsLetterFilterForm> itr=uniqueSubjects.iterator();
			
			while(itr.hasNext())
			{
				
				NewsLetterFilterForm newsLetterFilterForm1=itr.next();
				
				long Proposalid = newsLetterFilterForm1.getNumProposalD();
				
				
				List<ProposalSubmissionForm> PropDetails = proposalSubmissionService.getProposalDetails(Proposalid);
				if(PropDetails.size()>0){				
					ProposalSubmissionForm proposalSubmissionForm = PropDetails.get(0);
					String encProposalId = encryptionService.encrypt(""+Proposalid);
					String encPIId = encryptionService.encrypt(""+proposalSubmissionForm.getNumPI());
				Registration registration = msProposalDao.getRegistrationDetails(""+PropDetails.get(0).getNumPI());
				strContent = globalTemplate;

				strContent = strContent.replaceAll("\\$TITLE\\_OF\\_THE\\_PROJECT\\$",proposalSubmissionForm.getStrProjectTitle());
				strContent = strContent.replaceAll("\\$USER\\_NAME\\$",proposalSubmissionForm.getStrPIName());
				strContent = strContent.replaceAll("\\$FILE\\_NUMBER\\$",globalDao.getFilename(Proposalid));
				strContent = strContent.replaceAll("\\$ENC\\_PROPOSAL\\_ID\\$",encProposalId);
				strContent = strContent.replaceAll("\\$ENC\\_PI\\_ID\\$",encPIId);
				strContent = strContent.replaceAll("\\$INSTITUTE\\_NAME\\$",proposalSubmissionForm.getStrPIInstitute());
				strContent = strContent.replaceAll("\\$INSTITUTE\\_ADDRESS\\$",proposalSubmissionForm.getStrPIInstituteAddress());
				strContent = strContent.replaceAll("\\$BROAD\\_AREA\\$",proposalSubmissionForm.getStrBroadAreaDesc());
				strContent = strContent.replaceAll("\\$SUB\\_AREA\\$",proposalSubmissionForm.getStrSubAreaDesc());
				strContent = strContent.replaceAll("\\$DESIGNATION\\$",proposalSubmissionForm.getStrDesignationId());
				strContent = strContent.replaceAll("\\$DEPARTMENT\\$",proposalSubmissionForm.getStrDepartmentId());
				strContent = strContent.replaceAll("\\$TOTAL\\_COST\\$",""+proposalSubmissionForm.getNumTotalCost());
				strContent = strContent.replaceAll("\\$SCHEME\\_NAME\\$",globalDao.getSchemeName(""+proposalSubmissionForm.getNumSchemeID()));
				strContent = strContent.replaceAll("amp;","");
				System.out.println("strContent 2 = "+strContent);
				
			String mail =registration.getStrUsername();
				
	
				NewsLetterMaster nlm = new NewsLetterMaster();
				
				nlm.setbTrFlag(1);
				nlm.setDtTrDate(new Date());
				
				nlm.setNumTotalSubscribers(uniqueSubjects.size());
				nlm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
				nlm.setStrContentMail(strContent);
				nlm.setStrContentSMS(newsLetterFilterForm.getStrSMSContent());
				nlm.setStrMailSMSFlag(newsLetterFilterForm.getStrSMSFlag());
				nlm.setStrStatus("SUB");
				nlm.setStrSubject(strSubject);
				
				NewsLetterMaster lnlm = newsletterDao.addNewsLetterDetails(nlm);

				if(strIDs.length()>0){
					String[] strArray = strIDs.split(",");		
					for(int i =0; i < strArray.length; i++){
						int numFilterId = Integer.parseInt(strArray[i]);
						NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
						nlfm.setbTrFlag(1);
						nlfm.setDtTrDate(new Date());
						nlfm.setNumFilterId(numFilterId);
						nlfm.setNumNewsletterId(lnlm.getNumNewsLetterId());
						nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
						newsletterDao.addNewsLetterMapping(nlfm);

					}
				}

				
				try {
				smail.TransferToMailServerWithoutDB(mail, strSubject,strContent);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
							
				


				
				
				
				}
				
			}
		}
		
		
		
		
	}
	else{
		
		if(strIDs.length()>0){
			String[] strArray = strIDs.split(",");		
			for(int i =0; i < strArray.length; i++){
				int numFilterId = Integer.parseInt(strArray[i]);
				NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
				String query = nlfm.getStrFilterQuery();
				System.out.println(query);
				queryFinal = queryFinal + query + " UNION ";
			}
			queryFinal = queryFinal.substring(0, queryFinal.length()-7);
		}
				
		String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";
		
		String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";
		
		queryFinal = initialQuery + queryFinal + endQuery;
		uniqueSubjects = newsletterDao.getUsersForNewsLetter(queryFinal);

	
	
	NewsLetterMaster nlm = new NewsLetterMaster();
		if(strIDs.length()>0){
			String[] strArray = strIDs.split(",");		
			for(int i =0; i < strArray.length; i++){
				int numFilterId = Integer.parseInt(strArray[i]);
				NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
				String query = nlfm.getStrFilterQuery();
			
				queryFinal = queryFinal + query + " UNION ";
			}
			queryFinal = queryFinal.substring(0, queryFinal.length()-7);
		}
		String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";
		
		String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";
		
		queryFinal = initialQuery + queryFinal + endQuery;
		
		uniqueSubjects = newsletterDao.getUsersForNewsLetter(queryFinal);
		
		NewsLetterMaster nlm = new NewsLetterMaster();
		
	
	nlm.setbTrFlag(1);
	nlm.setDtTrDate(new Date());
	
	nlm.setNumTotalSubscribers(uniqueSubjects.size());
	nlm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
	nlm.setStrContentMail(strContent);
	nlm.setStrContentSMS(newsLetterFilterForm.getStrSMSContent());
	nlm.setStrMailSMSFlag(newsLetterFilterForm.getStrSMSFlag());
	nlm.setStrStatus("SUB");
	nlm.setStrSubject(strSubject);
	
	NewsLetterMaster lnlm = newsletterDao.addNewsLetterDetails(nlm);

	if(strIDs.length()>0){
		String[] strArray = strIDs.split(",");		
		for(int i =0; i < strArray.length; i++){
			int numFilterId = Integer.parseInt(strArray[i]);
			NewsLetterFilterMapping nlfm = new NewsLetterFilterMapping();
			nlfm.setbTrFlag(1);
			nlfm.setDtTrDate(new Date());
			nlfm.setNumFilterId(numFilterId);
			nlfm.setNumNewsletterId(lnlm.getNumNewsLetterId());
			nlfm.setNumTrUserId(newsLetterFilterForm.getNumUserId());
			newsletterDao.addNewsLetterMapping(nlfm);

		}
	}

	int quantum = 90;
				
	int totalIterations = uniqueSubjects.size()/quantum;
	int remainder = uniqueSubjects.size()%quantum;
	//SendMail smail = new SendMail();

	
	String documentIds = newsLetterFilterForm.getDocumentIds();
	if(documentIds!=null && documentIds.length()>0){ //send document as attachment
		documentIds = documentIds.substring(1, documentIds.length()-1);
		
		System.out.println("Document IDS "+ documentIds);
		String[] documentArray = documentIds.split(",");
		File[] fileArray = new File[documentArray.length];
		String[] nameArray = new String[documentArray.length];
		
		for(int var = 0; var<documentArray.length;var++){
			try{;

			String docname = documentArray[var];
			NewsletterDocuments od = documentService.getNewsletterDocuments(Integer.parseInt(docname));
	
				
			File tempFile = File.createTempFile(od.getStrdocname(), ".pdf");
		    tempFile.deleteOnExit();
		    FileOutputStream out = new FileOutputStream(tempFile);
		    
		    String globalPath = ResourceBundleFile.getValueFromKey("NEWSLETTER_DOCUMENTS");
		    
		    String path = globalPath;
			
			String finalName = path + docname + ".pdf";
		    
		    IOUtils.copy(ftpService.getFiles(finalName), out);
		    fileArray[var] = tempFile;
		    
			nameArray[var] = od.getStrdocname();

			documentService.updateNewsletterDocuments(lnlm.getNumNewsLetterId(),Integer.parseInt(docname));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	
		if(totalIterations>0){
			for(int j = 0 ; j < totalIterations ; j++){
				try {
				StringBuffer emailIds = new StringBuffer();
				for(int i = (j*quantum); i < ((j+1)*quantum);i++){
					emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
				}
				String emailStr = emailIds.substring(1);
				
					smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			try {
			StringBuffer emailIds = new StringBuffer();	
			
			for(int i = (totalIterations*quantum); i < ((totalIterations*quantum)+remainder); i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			try {
			StringBuffer emailIds = new StringBuffer();
			for(int i = 0; i < remainder; i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithAttachment(emailStr, strSubject,strContent,fileArray,nameArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		

	}
	else
	{
		if(totalIterations>0){
			for(int j = 0 ; j < totalIterations ; j++){
				try {
				StringBuffer emailIds = new StringBuffer();
				for(int i = (j*quantum); i < ((j+1)*quantum);i++){
					emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
				}
				String emailStr = emailIds.substring(1);
				
					smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			StringBuffer emailIds = new StringBuffer();	
			try {
			for(int i = (totalIterations*quantum); i < ((totalIterations*quantum)+remainder); i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			try {
			StringBuffer emailIds = new StringBuffer();
			for(int i = 0; i < remainder; i++){
				emailIds.append(","+uniqueSubjects.get(i).getStrEmailId());
			}
			String emailStr = emailIds.substring(1);
			
				smail.TransferToMailServerWithoutDB(emailStr, strSubject,strContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}

	}
	
	}

}

	
	
	
	
	
	


	@Override
	public List<NewsLetterFilterMaster> getAllFiltersByFilterType(String filterType){		
		return newsletterDao.getAllFiltersByFilterType(filterType);		
	}
	
	@Override	
	public List<BigInteger> getFilterDataByFilterIds(String filterId){
		
		List<BigInteger> proposals = new ArrayList<BigInteger>();
		List<NewsLetterFilterMaster> tempList = newsletterDao.getFiltersByIds(filterId);
		if(null != tempList && tempList.size()>0){
			StringBuffer query = new StringBuffer();
			for(int i=0;i<tempList.size();i++){
				String tempQuery= tempList.get(i).getStrFilterQuery();
				if(i!=0){
					query.append(" union ");
				}
				
				query.append(tempQuery);
			}
			
			proposals = daoHelper.runNative(query.toString());
			
		}
		return proposals;
	}
}*/	


public String getDetailsOfEmplApplLevel(int roleId, int projectId){
	
		String roleAccessType="";
		RoleMasterDomain detailOfRole=roleMasterDao.getRoleMasterDomainById(roleId);
		if(detailOfRole!=null){
			roleAccessType= detailOfRole.getAccessLevel();
		}
		String empDetails="";
		if(roleAccessType.equals("Application")){
			
			empDetails =  newsletterDao.getDetailsOfEmplAppLevel(roleId,projectId);
		}
		else if(roleAccessType.equals("Group")){
			
			 empDetails =  newsletterDao.getDetailsOfEmplGroupLevel(roleId,projectId);
			}
		else{
			
			empDetails =  newsletterDao.getDetailsOfEmplOrgLevel(roleId,projectId);
		}

	
	return empDetails;
}

@Override
public String getPeriodicNewsLetterDisplayDate(int newsletterid) {
	NewsLetterMaster newsletter = newsletterDao
			.getNewsLetterObjectFromDB(newsletterid);
	if(newsletter.getIsPeriodic()==1)
	return newsletter.getStrPeriodicScheduleDate();
	else
	return "";
}


public HashMap<Integer, String> getDocumentMapForNewsletter(int newsletterid) {
	HashMap<Integer, String> documentMap = new LinkedHashMap<Integer, String>();

	List<NewsletterDocuments> documentList = newsletterDao
			.getNewsletterDocuments(newsletterid);
	for (NewsletterDocuments newsletterDocuments : documentList) {
		documentMap.put(newsletterDocuments.getNumId(),
				newsletterDocuments.getStrdocname());

	}
	return documentMap;

}

public NewsLetterFilterForm getDetailsOfRole(int newsletterid){
	List<NewsLetterRoleMapping> details= newsletterDao.getDetailsOfRole(newsletterid);
	NewsLetterFilterForm newsLetterFilterForm2 = new NewsLetterFilterForm();
	NewsLetterRoleMapping newsLetterRoleMapping= new NewsLetterRoleMapping();
	if(details.size()>0){
		newsLetterRoleMapping=details.get(0);
		
			newsLetterFilterForm2.setTo(newsLetterRoleMapping.getStrTo());
			newsLetterFilterForm2.setBcc(newsLetterRoleMapping.getStrBcc());
			newsLetterFilterForm2.setCc(newsLetterRoleMapping.getStrCc());
			return newsLetterFilterForm2;
	}
	
	return  newsLetterFilterForm2;
}

public NewsletterDocuments getNewsletterDocuments(int docId) {
	NewsletterDocuments lod = newsletterDao.getNewsletterDocumentsByDocId(docId);
	return lod;
}

@Override
public void getNewsLettersToBeTransferredPeriodically() {
	
	List<NewsLetterFilterForm> uniqueSubjects = new ArrayList<NewsLetterFilterForm>();
	newsLetterPeriodicLogger.info("Periodic Scheduler Started");

	DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	String currentDate= formatter.format(new Date());
	String currentDay=currentDate.substring(0, 2);
	String currentMonth=currentDate.substring(3, 6);
	String currentYear=currentDate.substring(7);
	List<NewsLetterMaster> newsLetterMasterList = newsletterDao.getAllSavedPeriodicNewsLetterMaster(currentDay,currentMonth,currentYear);
	
	newsLetterPeriodicLogger.info("Size of the news letter list is"+newsLetterMasterList.size());
	
	if(newsLetterMasterList.size()>0)
	{
		Iterator<NewsLetterMaster> itr=newsLetterMasterList.iterator();
		String queryFinal ="";
		String strSubject="";
		String strContent="";
		String smsContent="";
		String filterType="";
		while(itr.hasNext())
		{
			queryFinal="";
			NewsLetterMaster newsLetterMaster = itr.next();
			strSubject=newsLetterMaster.getStrSubject();
			strContent = newsLetterMaster.getStrContentMail();
			smsContent=newsLetterMaster.getStrContentSMS();

			if(newsLetterMaster.getNewsLetterType()  == 1){
				
				String finYear=getFinYear(new Date(),1); //1 for previous year .. 0 for same financial
				
				String year=finYear.substring(finYear.indexOf('-')+1);
				strContent = strContent.replaceAll("\\$FIN\\_YEAR\\$", finYear);
				strContent = strContent.replaceAll("\\$LAST\\_YEAR\\$", year);
				
			}else if(newsLetterMaster.getNewsLetterType()  == 2){

				String finYear=getFinYear(new Date(),0); //1 for previous year .. 0 for same financial
				String year=finYear.substring(finYear.indexOf('-')+1);
				strContent = strContent.replaceAll("\\$FIN\\_YEAR\\$", finYear);
				strContent = strContent.replaceAll("\\$LAST\\_YEAR\\$", year);
				
			}
		

			List<NewsletterDocuments> documentList = newsletterDao.getNewsletterDocuments(newsLetterMaster.getNumNewsLetterId());
			List<NewsLetterFilterMapping> newsLetterFilterMappingList = newsletterDao.getFilterMappingForNewsLetter(newsLetterMaster.getNumNewsLetterId());
			List<NewsLetterRoleMapping> newsLetterFilterRoleMappingList = newsletterDao.getDetailsOfRole(newsLetterMaster.getNumNewsLetterId());
	
			newsLetterPeriodicLogger.info("Size of the news letter filter mapping"+newsLetterFilterMappingList.size());
			
			if(newsLetterFilterMappingList.size()>0)
			{
				Iterator<NewsLetterFilterMapping> itr1=newsLetterFilterMappingList.iterator();
				while(itr1.hasNext())
				{
					NewsLetterFilterMapping newsLetterFilterMapping = itr1.next();
					int numFilterId=newsLetterFilterMapping.getNumFilterId();
					NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
					String query = nlfm.getStrFilterQuery();
					filterType=nlfm.getStrFilterType();
					queryFinal = queryFinal + query + " UNION ";
				}

				queryFinal = queryFinal.substring(0, queryFinal.length()-7);
			}

			/*String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";

			String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";

			queryFinal = initialQuery + queryFinal + endQuery;*/

			/*newsLetterPeriodicLogger.info("this is final query "+queryFinal);*/
			
			uniqueSubjects= newsletterDao.getPropNewsLetter(queryFinal);
			
			//List<NewsLetterFilterForm> 	uniqueSubjects2 = newsletterDao.getUsersForNewsLetter(queryFinal);
			newsLetterPeriodicLogger.info("size of the uniqe subjects is "+uniqueSubjects.size());
			scheduleEmailAndSmsProcessing(uniqueSubjects,strSubject,strContent,smsContent,documentList,newsLetterFilterRoleMappingList,filterType); 
			newsLetterMaster.setStrStatus("SENT");
			newsletterDao.updateNewsLetterStatus(newsLetterMaster);
			
			
			// Email Confirmation For Committee Member
		/*	 try {
				 StringBuffer allemailid=new StringBuffer();
				 for(int j=0;j<uniqueSubjects2.size();j++){
					 allemailid.append(uniqueSubjects2.get(j).getStrEmailId()+",");
				 }
				 
				 SendMail smail = new SendMail();
				 String emailIDSchedulerCC=ResourceBundleFile.getValueFromKey("emailSchedulerCC");
				 smail.TransferToMailServer(emailIDSchedulerCC, "News Letter Transfer Periodic "+strSubject, uniqueSubjects.size()+" = "+allemailid.toString());
				
			 } catch (Exception e) {
				// TODO: handle exception
			}*/
			
			
		}
		
		

	}
	
}

private String getFinYear(Date dt,int num)
{
	 final int    FIRST_FISCAL_MONTH  = Calendar.MARCH;
	 Calendar cal = Calendar.getInstance();
	 cal.setTime(dt);
	 int month = cal.get(Calendar.MONTH);
	 int year = cal.get(Calendar.YEAR);
	 int newyear = (month <= FIRST_FISCAL_MONTH) ? year-1 : year + 1;
	 String finyear = (newyear>year)?year-num+"-"+(newyear-num):(newyear-num)+"-"+(year-num);	 
	 System.out.println("this is financial year of current date.."+finyear);
	 return finyear;
}

public void scheduleEmailAndSmsProcessing(List<NewsLetterFilterForm> uniqueSubjects,String subject,String strContent,String smsContent,List<NewsletterDocuments> documentList,List<NewsLetterRoleMapping> newsLetterFilterRoleMappingList,String filterType){

	int quantum = 90;
	int totalIterations = uniqueSubjects.size()/quantum;
	//int remainder = uniqueSubjects.size()%quantum;
	SendMail smail = new SendMail();
	beanFactory.autowireBean(smail);
	String toRoles="";
	String bccRoles="";
	String ccRoles="";
	String globalTemplate = strContent;
	String content = globalTemplate;
	String globalSub = subject;
	String strSubject = globalSub;

	
	if(newsLetterFilterRoleMappingList.size()>0){
		toRoles=newsLetterFilterRoleMappingList.get(0).getStrTo();
		bccRoles=newsLetterFilterRoleMappingList.get(0).getStrBcc();
		ccRoles=newsLetterFilterRoleMappingList.get(0).getStrCc();
	}
	if(uniqueSubjects.size()>0)
	{
		newsLetterPeriodicLogger.info("Unique Subject size is ----------- " +uniqueSubjects.size());
		
		
		Iterator<NewsLetterFilterForm> itr=uniqueSubjects.iterator();
		
		while(itr.hasNext())
		{
			String toMailIds="";
			String ccMailIds="";
			String bccMailIds="";
			
			NewsLetterFilterForm newsLetterFilterForm1=itr.next();
			
			int projectId = newsLetterFilterForm1.getNumProjectId();
			
			String[] roleTo = toRoles.split(",");	
			if(roleTo.length>0){
			for(int i =0; i < roleTo.length; i++){
				if(!roleTo[i].equals("")){
				int roleId = Integer.parseInt(roleTo[i]);
				 String toMailId = getDetailsOfEmplApplLevel(roleId,projectId) ;
				 if(toMailId!=null && !toMailId.equals("")){
					 toMailIds=  toMailId +","+toMailIds;
				 		}
		
					}				
				}
			}
			try{
			
			if(toMailIds!=null && !toMailIds.equals("")){
			char lastLetterOfTo = toMailIds.charAt(toMailIds.length() - 1);
			if(lastLetterOfTo==','){
				toMailIds=toMailIds.substring(0,toMailIds.length()-1);
						}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String[] roleCc = ccRoles.split(",");
			if(roleCc.length>0){
			for(int j =0; j < roleCc.length; j++){
				if(!roleCc[j].equals("")){
				int roleId = Integer.parseInt(roleCc[j]);
				String ccMailId = getDetailsOfEmplApplLevel(roleId,projectId);
				 if(ccMailId!=null && !ccMailId.equals("")){
					 ccMailIds=  ccMailId+","+ ccMailIds;
					 
				 		}
					}
				}
			}
			try{
				if(ccMailIds!=null && !ccMailIds.equals("")){
				char lastLetterOfCc = ccMailIds.charAt(ccMailIds.length() - 1);
				if(lastLetterOfCc==','){
				ccMailIds=ccMailIds.substring(0,ccMailIds.length()-1);
			}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String[] roleBCc = bccRoles.split(",");	
			if(roleBCc.length>0){
			for(int k =0; k < roleBCc.length; k++){
				if(!roleBCc[k].equals("")){
				int roleId = Integer.parseInt(roleBCc[k]);
				 String bccMailId = getDetailsOfEmplApplLevel(roleId,projectId);
				 if(bccMailId!=null && !bccMailId.equals("")){
					 bccMailIds=  bccMailId+","+bccMailIds;
			}
		
				 	}
				}
			}
			try{
				if(bccMailIds!=null && !bccMailIds.equals("")){
				char lastLetterOfBCc = bccMailIds.charAt(bccMailIds.length() - 1);
				if(lastLetterOfBCc==','){
				bccMailIds=bccMailIds.substring(0,bccMailIds.length()-1);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}			
			
			ProjectMasterDomain projDetails=projectMasterDao.getProjectMasterDataById(projectId);
			if(projDetails!=null){
				content = globalTemplate;
				 strSubject = globalSub;

				Calendar calendar = Calendar.getInstance();
				 String[] monthName = {"January", "February",
			                "March", "April", "May", "June", "July",
			                "August", "September", "October", "November",
			                "December"};
				int currentMonth=calendar.get(Calendar.MONTH);
				String month = monthName[currentMonth];
				int currentYear = calendar.get(Calendar.YEAR);
				content = content.replaceAll("\\$PROJECT\\_NAME\\$",projDetails.getStrProjectName());
				content = content.replaceAll("\\$PROJECT\\_COST\\$",""+CurrencyUtils.convertToINR(projDetails.getProjectCost()));
				content = content.replaceAll("\\$PROJECT\\_START\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectStartDate()));
				content = content.replaceAll("\\$PROJECT\\_END\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectEndDate()));
				/*strContent = strContent.replaceAll("\\$PROJECT\\_DURATION\\$",""+projDetails.getProjectDuration());*/
				content = content.replaceAll("\\$CURRENT\\_MONTH\\$",""+month);
				content = content.replaceAll("\\$CURRENT\\_YEAR\\$",""+currentYear);
				
				
				strSubject = strSubject.replaceAll("\\$PROJECT\\_NAME\\$",projDetails.getStrProjectName());
				strSubject = strSubject.replaceAll("\\$PROJECT\\_COST\\$",""+CurrencyUtils.convertToINR(projDetails.getProjectCost()));
				strSubject = strSubject.replaceAll("\\$PROJECT\\_START\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectStartDate()));
				strSubject = strSubject.replaceAll("\\$PROJECT\\_END\\_DATE\\$",""+DateUtils.dateToString(projDetails.getDtProjectEndDate()));
				/*strContent = strContent.replaceAll("\\$PROJECT\\_DURATION\\$",""+projDetails.getProjectDuration());*/
				strSubject = strSubject.replaceAll("\\$CURRENT\\_MONTH\\$",""+month);
				strSubject = strSubject.replaceAll("\\$CURRENT\\_YEAR\\$",""+currentYear);
			}

		if(documentList.size()>0){ //send document as attachment
			File[] fileArray = new File[documentList.size()];
			String[] nameArray = new String[documentList.size()];



			for(int var = 0; var<documentList.size();var++){
				try{

					NewsletterDocuments od = documentList.get(var);
					String docname = od.getNumId().toString();
					
					File tempFile = File.createTempFile(od.getStrdocname(), ".pdf");
					tempFile.deleteOnExit();
					FileOutputStream out = new FileOutputStream(tempFile);
					String globalPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
					String path = globalPath; 
					String finalName = path + docname + ".pdf";
					IOUtils.copy(fileUploadService.getFiles(finalName), out);
					fileArray[var] = tempFile;
					nameArray[var] = od.getStrdocname();

				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			newsLetterPeriodicLogger.info("Total iteration is "+totalIterations);
		
				try {
		

					smail.TransferToMailServerWithAttachmentforNewsletter(toMailIds, strSubject,content,fileArray,nameArray,ccMailIds,bccMailIds,filterType,projectId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					newsLetterPeriodicLogger.error("***************************************************************************");
					newsLetterPeriodicLogger.error("Exception in News Letter Forward with Attachment",e);							
					newsLetterPeriodicLogger.error("***************************************************************************");
				}

			
		}
	
			else{

				
				try {
			
					smail.TransferToMailServerWithCc(toMailIds, strSubject,content,ccMailIds,bccMailIds,filterType,projectId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			
		
		}
	}
}


public void getNewsLettersToBeTransfer()
{
	
	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	String currentDate= formatter.format(new Date());
	// StringBuffer strAllEMailId=new StringBuffer();
	List<NewsLetterMaster> newsLetterMasterList = newsletterDao.getAllSavedNewsLetterMaster(currentDate);

	
	if(newsLetterMasterList.size()>0)
	{
		Iterator<NewsLetterMaster> itr=newsLetterMasterList.iterator();
		String queryFinal ="";
		String strSubject="";
		String strContent="";
		String smsContent="";
		String filterType="";
		
		
		while(itr.hasNext())
		{
			List<NewsLetterFilterForm> uniqueSubjects = new ArrayList<NewsLetterFilterForm>();
			queryFinal="";
			NewsLetterMaster newsLetterMaster = itr.next();
			strSubject=newsLetterMaster.getStrSubject();
			strContent = newsLetterMaster.getStrContentMail();
			smsContent=newsLetterMaster.getStrContentSMS();

			List<NewsletterDocuments> documentList = newsletterDao.getNewsletterDocuments(newsLetterMaster.getNumNewsLetterId());
			List<NewsLetterFilterMapping> newsLetterFilterMappingList = newsletterDao.getFilterMappingForNewsLetter(newsLetterMaster.getNumNewsLetterId());
			List<NewsLetterRoleMapping> newsLetterFilterRoleMappingList = newsletterDao.getDetailsOfRole(newsLetterMaster.getNumNewsLetterId());
			/*if(newsLetterFilterRoleMappingList.size()>0){
				toRoles=newsLetterFilterRoleMappingList.get(0).getStrTo();
				bccRoles=newsLetterFilterRoleMappingList.get(0).getStrBcc();
				ccRoles=newsLetterFilterRoleMappingList.get(0).getStrCc();
			}*/
			if(newsLetterFilterMappingList.size()>0)
			{
				Iterator<NewsLetterFilterMapping> itr1=newsLetterFilterMappingList.iterator();
				while(itr1.hasNext())
				{
					NewsLetterFilterMapping newsLetterFilterMapping = itr1.next();
					int numFilterId=newsLetterFilterMapping.getNumFilterId();
					NewsLetterFilterMaster nlfm = newsletterDao.getAllFilters(numFilterId);
					String query = nlfm.getStrFilterQuery();
					 filterType=nlfm.getStrFilterType();
					queryFinal = queryFinal + query + " UNION ";
				}

				queryFinal = queryFinal.substring(0, queryFinal.length()-7);
			}

		/*	String initialQuery = "select a.str_user_name,a.num_user_id, decode(b.str_sms_alert, 'Y',substr(mobile_no,-10,10),0) as mobile_no from (";

			String endQuery = " ) a, serb_user_registration b , serb_user_profile c where  a.num_user_id=b.num_user_id and a.num_user_id=c.user_id  and c.num_isvalid=1 ";

			queryFinal = initialQuery + queryFinal + endQuery;*/
			uniqueSubjects= newsletterDao.getPropNewsLetter(queryFinal);
			
			
			//List<NewsLetterFilterForm> 	uniqueSubjects2 = newsletterDao.getUsersForNewsLetter(queryFinal);
			
			scheduleEmailAndSmsProcessing(uniqueSubjects,strSubject,strContent,smsContent,documentList,newsLetterFilterRoleMappingList,filterType); 
			newsLetterMaster.setStrStatus("SENT");
			newsletterDao.updateNewsLetterStatus(newsLetterMaster);
			
		}
		
		// Email Confirmation For Committee Member
		/* try {
			 SendMail smail = new SendMail();
			 String emailIDSchedulerCC=ResourceBundleFile.getValueFromKey("emailSchedulerCC");
			 smail.TransferToMailServer(emailIDSchedulerCC, "News Letter To Be Transfer", newsLetterMasterList.size()+", "+strSubject);
			 System.out.println("send mail emailSchedulerCC = ");
		 } catch (Exception e) {
			// TODO: handle exception
		}*/

	}
}





}

