package in.pms.master.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectInvoiceMasterDao;
import in.pms.master.dao.ProjectPaymentScheduleMasterDao;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectPaymentSeheduleMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Currency;

@Service
public class ProjectInvoiceMasterServiceImpl implements ProjectInvoiceMasterService{

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ProjectInvoiceMasterDao projectInvoiceMasterDao;
	
	@Autowired
	ProjectPaymentScheduleMasterDao projectPaymentScheduleMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_INVOICE_MST')")
	public long saveUpdateProjectInvoiceMaster(ProjectInvoiceMasterModel projectInvoiceMasterModel){
		ProjectInvoiceMasterDomain projectInvoiceMasterDomain = convertProjectInvoiceMasterModelToDomain(projectInvoiceMasterModel);
		return projectInvoiceMasterDao.saveUpdateProjectInvoiceMaster(projectInvoiceMasterDomain);
	}
		
	@Override
	public String checkDuplicateProjectInvoiceRefNo(ProjectInvoiceMasterModel projectInvoiceMasterModel){
		String result=	"";
		ProjectInvoiceMasterDomain projectInvoiceMasterDomain = projectInvoiceMasterDao.getProjectInvoiceMasterByRefNo(projectInvoiceMasterModel.getStrInvoiceRefno(),projectInvoiceMasterModel.getProjectId());
		
		 if(null == projectInvoiceMasterDomain){
				return null; 
			 }else if(projectInvoiceMasterModel.getNumId() != 0){
				 if(projectInvoiceMasterDomain.getNumId() == projectInvoiceMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Project Invoice with same Reference Number already exist with Id "+projectInvoiceMasterDomain.getNumId();
				 }
			 }else{
				if(projectInvoiceMasterDomain.getNumIsValid() == 0){
					result = "Project Invoice with same Reference Number Details already exist with Id "+projectInvoiceMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Invoice with same Reference Number Details already exist with Id "+projectInvoiceMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ProjectInvoiceMasterModel getProjectInvoiceMasterDomainById(long numId){
		return convertProjectInvoiceMasterDomainToModel(projectInvoiceMasterDao.getProjectInvoiceMasterById(numId));
	}
	
	@Override
	public List<ProjectInvoiceMasterModel> getProjectInvoiceMstDomainById(long numId){
		List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomain=projectInvoiceMasterDao.getInvoiceDetailsByInvoiceId(numId);
		List<ProjectInvoiceMasterModel> projectInvoiceMasterModelList = convertProjectInvoiceMasterDomainToModelList(projectInvoiceMasterDomain);
		return projectInvoiceMasterModelList;
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_INVOICE_MST')")
	public List<ProjectInvoiceMasterModel> getAllProjectInvoiceMasterDomain(){
		return convertProjectInvoiceMasterDomainToModelList(projectInvoiceMasterDao.getAllProjectInvoiceMasterDomain());
	}
	
	@Override
	public List<ProjectInvoiceMasterModel> getAllActiveProjectInvoiceMasterDomain(){
		return convertProjectInvoiceMasterDomainToModelList(projectInvoiceMasterDao.getAllActiveProjectInvoiceMasterDomain());
	}
	
	/*@Override
	public List<ProjectInvoiceMasterModel> getAllActiveProjectMasterDomain(){
		return convertOrganisationMasterDomainToModelList(ProjectInvoiceMasterDao.getAllActiveProjectMasterDomain());
	}*/
	
	public ProjectInvoiceMasterDomain convertProjectInvoiceMasterModelToDomain(ProjectInvoiceMasterModel projectInvoiceMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectInvoiceMasterDomain projectInvoiceMasterDomain = new ProjectInvoiceMasterDomain();
		if(projectInvoiceMasterModel.getNumId()!=0){				
			projectInvoiceMasterDomain =  projectInvoiceMasterDao.getProjectInvoiceMasterById(projectInvoiceMasterModel.getNumId());
		}
		else{
			projectInvoiceMasterDomain.setStrInvoiceStatus("Generated");
		}
		
		projectInvoiceMasterDomain.setDtTrDate(new Date());
		projectInvoiceMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(projectInvoiceMasterModel.isValid()){
			projectInvoiceMasterDomain.setNumIsValid(1);
		}else{
			projectInvoiceMasterDomain.setNumIsValid(0);
		}
		projectInvoiceMasterDomain.setStrInvoiceRefno(projectInvoiceMasterModel.getStrInvoiceRefno());
		try{
		projectInvoiceMasterDomain.setDtInvoice(DateUtils.dateStrToDate(projectInvoiceMasterModel.getDtInvoice()));
		}
		catch (ParseException e) {				
			e.printStackTrace();
		}
		projectInvoiceMasterDomain.setNumInvoiceAmt(projectInvoiceMasterModel.getNumInvoiceAmt());
		projectInvoiceMasterDomain.setNumInvoiceTotalAmt(projectInvoiceMasterModel.getNumInvoiceTotalAmt());
		projectInvoiceMasterDomain.setNumTaxAmount(projectInvoiceMasterModel.getNumTaxAmount());
		
		projectInvoiceMasterDomain.setStrInvoiceType(projectInvoiceMasterModel.getStrInvoiceType());
		projectInvoiceMasterDomain.setProjectId(projectInvoiceMasterModel.getProjectId());
			
		if(null != projectInvoiceMasterModel.getScheduledPaymentIDs() && projectInvoiceMasterModel.getScheduledPaymentIDs().size()>0){
			List<ProjectPaymentSeheduleMasterDomain> details= projectPaymentScheduleMasterDao.getAllProjectPaymentSchedule(projectInvoiceMasterModel.getScheduledPaymentIDs());			

			Set<ProjectPaymentSeheduleMasterDomain> set=new HashSet<ProjectPaymentSeheduleMasterDomain>();
			set.addAll(details);
			projectInvoiceMasterDomain.setProjectPaymentSeheduleMasterDomain(set);			
		}else{
			projectInvoiceMasterDomain.setProjectPaymentSeheduleMasterDomain(null);
		}
		
		
		return projectInvoiceMasterDomain;
	}
	
	public List<ProjectInvoiceMasterModel> convertProjectInvoiceMasterDomainToModelList(List<ProjectInvoiceMasterDomain> projectInvoiceMasterList){
		List<ProjectInvoiceMasterModel> list = new ArrayList<ProjectInvoiceMasterModel>();
			for(int i=0;i<projectInvoiceMasterList.size();i++){
				ProjectInvoiceMasterDomain domain = projectInvoiceMasterList.get(i);
				ProjectInvoiceMasterModel model = new ProjectInvoiceMasterModel();
				
				if(domain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+domain.getNumId());
					model.setEncProjectId(encryptedId);
				}
				model.setNumId(domain.getNumId());
				if(domain.getNumIsValid() ==1){
					model.setValid(true);
				}else{
					model.setValid(false);
				}
			
		
				model.setStrInvoiceRefno(domain.getStrInvoiceRefno());
				model.setStrInvoiceDate(DateUtils.dateToString(domain.getDtInvoice()));
				model.setNumInvoiceAmt(domain.getNumInvoiceAmt());
				model.setNumTaxAmount(domain.getNumTaxAmount());
				model.setNumInvoiceTotalAmt(domain.getNumInvoiceTotalAmt());
				model.setStrInvoiceAmt(CurrencyUtils.convertToINR(domain.getNumInvoiceAmt()));
				model.setStrTaxAmount(CurrencyUtils.convertToINR(domain.getNumTaxAmount()));
				model.setStrInvoiceTotalAmt(CurrencyUtils.convertToINR(domain.getNumInvoiceTotalAmt()));
			/*	model.setStrInvoiceStatus(domain.getStrInvoiceStatus());*/
				model.setStrInvoiceType(domain.getStrInvoiceType());
				/*try{
				Set<ProjectPaymentSeheduleMasterDomain> details=domain.getProjectPaymentSeheduleMasterDomain();
				final List<Long> collection = new ArrayList<Long>();
				for (final ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain : details) {			
					collection.add(projectPaymentSeheduleMasterDomain.getNumId());
				}
				model.setScheduledPaymentIDs(collection);
				}
				catch(Exception e){
					e.printStackTrace();
				}*/
			/*if(null == domain.getProjectPaymentSeheduleMasterDomain())
				{
				model.setScheduledPaymentID(0);
				}
				else{
					model.setScheduledPaymentID(domain.getProjectPaymentSeheduleMasterDomain().getNumId());
					
				}*/
				
/*				int[] scheduleIds = null;

				try {
			    	
					String[] arrScheduleIds=domain.getStrPaymentScheduleIds().split(",");
					    
					scheduleIds=Arrays.stream(arrScheduleIds).mapToInt(Integer::parseInt).toArray();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				model.setScheduledPaymentIDs(scheduleIds);*/
				list.add(model);
	}
		return list;
	}

	
	
	@Override
	public List<ProjectInvoiceMasterModel> getProjectInvoiceByProjectId(long projectId) {		
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomain = projectInvoiceMasterDao.getAllProjectInvoiceByProjectID(projectId);
			List<ProjectInvoiceMasterModel> projectInvoiceMasterModelList = convertProjectInvoiceMasterDomainToModelListNew(projectInvoiceMasterDomain);
			return projectInvoiceMasterModelList;
	}
	
	
	
	@Override
	public List<ProjectInvoiceMasterModel> getProjectInvoiceRefNoByProjectId(long projectId) {		
			List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomain = projectInvoiceMasterDao.getProjectInvoiceRefNoByProjectID(projectId);
			List<ProjectInvoiceMasterModel> projectInvoiceMasterModelList = convertProjectInvoiceMasterDomainToModelList(projectInvoiceMasterDomain);
			return projectInvoiceMasterModelList;
	}
	
	
	
	
	public ProjectInvoiceMasterModel convertProjectInvoiceMasterDomainToModel(ProjectInvoiceMasterDomain projectInvoiceMasterDomain){
		ProjectInvoiceMasterModel projectInvoiceMasterModel = new ProjectInvoiceMasterModel();
	
		if(projectInvoiceMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+projectInvoiceMasterDomain.getNumId());
			projectInvoiceMasterModel.setEncProjectId(encryptedId);
		}
		projectInvoiceMasterModel.setNumId(projectInvoiceMasterDomain.getNumId());
		if(projectInvoiceMasterDomain.getNumIsValid() ==1){
			projectInvoiceMasterModel.setValid(true);
		}else{
			projectInvoiceMasterModel.setValid(false);
		}
	
		
		projectInvoiceMasterModel.setStrInvoiceRefno(projectInvoiceMasterDomain.getStrInvoiceRefno());
		projectInvoiceMasterModel.setDtInvoice(DateUtils.dateToString(projectInvoiceMasterDomain.getDtInvoice()));
		projectInvoiceMasterModel.setNumInvoiceAmt(projectInvoiceMasterDomain.getNumInvoiceAmt());
		projectInvoiceMasterModel.setNumTaxAmount(projectInvoiceMasterDomain.getNumTaxAmount());
		projectInvoiceMasterModel.setNumInvoiceTotalAmt(projectInvoiceMasterDomain.getNumInvoiceTotalAmt());
		/*projectInvoiceMasterModel.setStrInvoiceStatus(projectInvoiceMasterDomain.getStrInvoiceStatus());*/
		projectInvoiceMasterModel.setStrInvoiceType(projectInvoiceMasterDomain.getStrInvoiceType());
		
		return projectInvoiceMasterModel;
		
	}
	public long deleteProjectInvoice(ProjectInvoiceMasterModel projectInvoiceMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ProjectInvoiceMasterDomain> projectInvoiceMasterList = projectInvoiceMasterDao.getProjectInvoiceMasterById(projectInvoiceMasterModel.getIdCheck());
		for(int i=0;i<projectInvoiceMasterList.size();i++){
			ProjectInvoiceMasterDomain projectInvoiceMasterDomain = projectInvoiceMasterList.get(i);
			projectInvoiceMasterDomain.setNumIsValid(2);
			projectInvoiceMasterDomain.setDtTrDate(new Date());
			projectInvoiceMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = projectInvoiceMasterDao.saveUpdateProjectInvoiceMaster(projectInvoiceMasterDomain);
		}
		return result;
	}	
	
	@Override
	 public ProjectInvoiceMasterDomain getProjectInvoiceDomainById(long numId){
		return projectInvoiceMasterDao.getProjectInvoiceMasterById(numId);
	}

	@Override
	public List<ProjectInvoiceMasterModel> getProjectInvoiceDetailsByProjectId(long projectId) {
		List <Object[]> List = projectInvoiceMasterDao.getProjectInvoiceDetailsByProjectId(projectId);
		List<ProjectInvoiceMasterModel> dataList = new ArrayList<ProjectInvoiceMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strInvoiceDate = null;
			Date invoiceDate = (Date) obj[0];
			strInvoiceDate = DateUtils.dateToString(invoiceDate);
			
			String strInvoiceAmt = null;
			String strTaxAmount = null;
			String strInvoiceTotalAmt = null;
			strInvoiceAmt = CurrencyUtils.convertToINR(obj[2]);
			strTaxAmount = CurrencyUtils.convertToINR(obj[3]);
			strInvoiceTotalAmt = CurrencyUtils.convertToINR(obj[4]);
			
			String strInvoiceRefno = (String) obj[1];
			String strInvoiceStatus = (String) obj[5];
			long projectId1 = (Long) obj[6];
			String strInvoiceType = (String) obj[7];
			ProjectInvoiceMasterModel projectInvoiceMasterModel = new ProjectInvoiceMasterModel();
			projectInvoiceMasterModel.setStrInvoiceDate(strInvoiceDate);
			projectInvoiceMasterModel.setStrInvoiceAmt(strInvoiceAmt);
			projectInvoiceMasterModel.setStrTaxAmount(strTaxAmount);
			projectInvoiceMasterModel.setStrInvoiceTotalAmt(strInvoiceTotalAmt);
			projectInvoiceMasterModel.setStrInvoiceRefno(strInvoiceRefno);
			projectInvoiceMasterModel.setStrInvoiceStatus(strInvoiceStatus);
			projectInvoiceMasterModel.setStrInvoiceType(strInvoiceType);
			projectInvoiceMasterModel.setEncProjectId(encryptionService.encrypt(""+projectId1));
			dataList.add(projectInvoiceMasterModel);
		}
			
		return dataList;
	}
	
	@Override
	public long mergeProjectInvoiceMaster(ProjectInvoiceMasterDomain projectInvoiceMasterDomain){
		return projectInvoiceMasterDao.saveUpdateProjectInvoiceMaster(projectInvoiceMasterDomain);
	}

	/*@Override
	public List<ProjectInvoiceMasterModel> getInvoiceDetailsByInvoiceId(long invoiceId) {
		List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomain = projectInvoiceMasterDao.getInvoiceDetailsByInvoiceId(invoiceId);
		List<ProjectInvoiceMasterModel> projectInvoiceMasterModelList = convertProjectInvoiceMasterDomainToModelList(projectInvoiceMasterDomain);
		return projectInvoiceMasterModelList;
	}*/
	
	@Override
	public long  getPendingPaymentsCount() {
		long pendingPaymentCount = projectInvoiceMasterDao.getPendingPaymentsCount();
		//System.out.println("closedProjectCount"+closedProjectCount);
		return pendingPaymentCount;
	}
	
	
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetail() {
		List <Object[]> List = projectInvoiceMasterDao.getPendingInvoiceDetail();
		List<ProjectPaymentScheduleMasterModel> dataList = new ArrayList<ProjectPaymentScheduleMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strPaymentDueDate = null;
			Date paymentDueDate = (Date) obj[1];
			strPaymentDueDate = DateUtils.dateToString(paymentDueDate);
	
			BigInteger b=new BigInteger(""+obj[4]);
			String strProjectName = (String) obj[0];
			String amount = null;
			amount = CurrencyUtils.convertToINR(obj[5]);
			
			double cost = 0.0;
			double projectAmount = Double.parseDouble(""+obj[5]);
			cost = CurrencyUtils.round((projectAmount/100000),2);
			amount = CurrencyUtils.convertToINR(cost);
			ProjectPaymentScheduleMasterModel paymentScheduleMasterModel = new ProjectPaymentScheduleMasterModel();
			paymentScheduleMasterModel.setStrRemarks((String) obj[3]);
			paymentScheduleMasterModel.setStrPurpose((String) obj[2]);
			paymentScheduleMasterModel.setStrProjectName(strProjectName);
			paymentScheduleMasterModel.setStrPaymentDueDate(strPaymentDueDate);
			paymentScheduleMasterModel.setStrAmount(amount);
			paymentScheduleMasterModel.setNumInvoiceAmtInLakhs(cost);
			paymentScheduleMasterModel.setStrGroupName((String) obj[7]);
			paymentScheduleMasterModel.setStrReferenceNumber((String) obj[8]);
			paymentScheduleMasterModel.setStrClientName((String) obj[9]);
			paymentScheduleMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(paymentScheduleMasterModel);
		}			
		return dataList;
	}

	
	
	@Override
	public List<ProjectInvoiceMasterModel> getPendingPaymentsInvoiceDetail(ProjectInvoiceMasterModel model) {
		List <Object[]> List = projectInvoiceMasterDao.getPendingPaymentsDetail();
		List<ProjectInvoiceMasterModel> dataList = new ArrayList<ProjectInvoiceMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strInvoiceDate = null;
			Date invoiceDate = (Date) obj[4];
			strInvoiceDate = DateUtils.dateToString(invoiceDate);
			
			String strInvoiceAmt = null;
			String strTaxAmount = null;
			String strInvoiceTotalAmt = null;
			strInvoiceAmt = CurrencyUtils.convertToINR(obj[5]);
			strTaxAmount = CurrencyUtils.convertToINR(obj[6]);
			strInvoiceTotalAmt = CurrencyUtils.convertToINR(obj[7]);
			
			String strInvoiceRefno = (String) obj[1];
			String strInvoiceStatus = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[8]);
			//long projectId1 = (Long) obj[8];
			String strProjectName = (String) obj[0];
			ProjectInvoiceMasterModel projectInvoiceMasterModel = new ProjectInvoiceMasterModel();
			projectInvoiceMasterModel.setStrInvoiceDate(strInvoiceDate);
			projectInvoiceMasterModel.setStrInvoiceAmt(strInvoiceAmt);
			projectInvoiceMasterModel.setNumInvoiceAmt((Double) obj[5]);
			projectInvoiceMasterModel.setStrTaxAmount(strTaxAmount);
			projectInvoiceMasterModel.setNumTaxAmount((Double) obj[6]);
			projectInvoiceMasterModel.setStrInvoiceTotalAmt(strInvoiceTotalAmt);
			projectInvoiceMasterModel.setNumInvoiceTotalAmt((Double) obj[7]);
			projectInvoiceMasterModel.setStrInvoiceRefno(strInvoiceRefno);
			projectInvoiceMasterModel.setStrInvoiceStatus(strInvoiceStatus);
			projectInvoiceMasterModel.setStrProjectName(strProjectName);
			projectInvoiceMasterModel.setStrGroupName((String) obj[9]);
			projectInvoiceMasterModel.setStrReferenceNumber((String) obj[10]);
			projectInvoiceMasterModel.setStrClientName((String) obj[11]);
			projectInvoiceMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(projectInvoiceMasterModel);
		}
			
		return dataList;
	}

	
	public List<ProjectInvoiceMasterModel> convertProjectInvoiceMasterDomainToModelListNew(List<ProjectInvoiceMasterDomain> projectInvoiceMasterList){
		List<ProjectInvoiceMasterModel> list = new ArrayList<ProjectInvoiceMasterModel>();
			for(int i=0;i<projectInvoiceMasterList.size();i++){
				ProjectInvoiceMasterDomain domain = projectInvoiceMasterList.get(i);
				ProjectInvoiceMasterModel model = new ProjectInvoiceMasterModel();
				
				if(domain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+domain.getNumId());
					model.setEncProjectId(encryptedId);
				}
				model.setNumId(domain.getNumId());
				if(domain.getNumIsValid() ==1){
					model.setValid(true);
				}else{
					model.setValid(false);
				}
			
		
				model.setStrInvoiceRefno(domain.getStrInvoiceRefno());
				model.setStrInvoiceDate(DateUtils.dateToString(domain.getDtInvoice()));
				model.setNumInvoiceAmt(domain.getNumInvoiceAmt());
				model.setNumTaxAmount(domain.getNumTaxAmount());
				model.setNumInvoiceTotalAmt(domain.getNumInvoiceTotalAmt());
				model.setStrInvoiceAmt(CurrencyUtils.convertToINR(domain.getNumInvoiceAmt()));
				model.setStrTaxAmount(CurrencyUtils.convertToINR(domain.getNumTaxAmount()));
				model.setStrInvoiceTotalAmt(CurrencyUtils.convertToINR(domain.getNumInvoiceTotalAmt()));
			/*	model.setStrInvoiceStatus(domain.getStrInvoiceStatus());*/
				model.setStrInvoiceType(domain.getStrInvoiceType());
				try{
				Set<ProjectPaymentSeheduleMasterDomain> details=domain.getProjectPaymentSeheduleMasterDomain();
				final List<Long> collection = new ArrayList<Long>();
				for (final ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain : details) {			
					collection.add(projectPaymentSeheduleMasterDomain.getNumId());
				}
				model.setScheduledPaymentIDs(collection);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			
				list.add(model);
	}
		return list;
	}

	@Override
	public List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceDt(String invoiceStartDate, String symbol) {
		List <Object[]> List = projectInvoiceMasterDao.getPendingPaymentsDetailsByInvoiceDt(invoiceStartDate,symbol);
		List<ProjectInvoiceMasterModel> dataList = new ArrayList<ProjectInvoiceMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strInvoiceDate = null;
			Date invoiceDate = (Date) obj[4];
			strInvoiceDate = DateUtils.dateToString(invoiceDate);
			
			String strInvoiceAmt = null;
			String strTaxAmount = null;
			String strInvoiceTotalAmt = null;
			strInvoiceAmt = CurrencyUtils.convertToINR(obj[5]);
			strTaxAmount = CurrencyUtils.convertToINR(obj[6]);
			strInvoiceTotalAmt = CurrencyUtils.convertToINR(obj[7]);
			
			String strInvoiceRefno = (String) obj[1];
			String strInvoiceStatus = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[8]);
			//long projectId1 = (Long) obj[8];
			String strProjectName = (String) obj[0];
			ProjectInvoiceMasterModel projectInvoiceMasterModel = new ProjectInvoiceMasterModel();
			projectInvoiceMasterModel.setStrInvoiceDate(strInvoiceDate);
			projectInvoiceMasterModel.setStrInvoiceAmt(strInvoiceAmt);
			projectInvoiceMasterModel.setNumInvoiceAmt((Double) obj[5]);
			projectInvoiceMasterModel.setStrTaxAmount(strTaxAmount);
			projectInvoiceMasterModel.setNumTaxAmount((Double) obj[6]);
			projectInvoiceMasterModel.setStrInvoiceTotalAmt(strInvoiceTotalAmt);
			projectInvoiceMasterModel.setNumInvoiceTotalAmt((Double) obj[7]);
			projectInvoiceMasterModel.setStrInvoiceRefno(strInvoiceRefno);
			projectInvoiceMasterModel.setStrInvoiceStatus(strInvoiceStatus);
			projectInvoiceMasterModel.setStrProjectName(strProjectName);
			projectInvoiceMasterModel.setStrGroupName((String) obj[9]);
			projectInvoiceMasterModel.setStrReferenceNumber((String) obj[10]);
			projectInvoiceMasterModel.setStrClientName((String) obj[11]);
			projectInvoiceMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(projectInvoiceMasterModel);
		}
			
		return dataList;
	}
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyDate(String dueDate, String symbol) {
		List <Object[]> List = projectInvoiceMasterDao.getPendingInvoiceDetailbyDate(dueDate,symbol);
		List<ProjectPaymentScheduleMasterModel> dataList = new ArrayList<ProjectPaymentScheduleMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strPaymentDueDate = null;
			Date paymentDueDate = (Date) obj[1];
			strPaymentDueDate = DateUtils.dateToString(paymentDueDate);
	
			BigInteger b=new BigInteger(""+obj[4]);
			String strProjectName = (String) obj[0];
			String amount = null;
			amount = CurrencyUtils.convertToINR(obj[5]);
			
			double cost = 0.0;
			double projectAmount = Double.parseDouble(""+obj[5]);
			cost = CurrencyUtils.round((projectAmount/100000),2);
			amount = CurrencyUtils.convertToINR(cost);
			ProjectPaymentScheduleMasterModel paymentScheduleMasterModel = new ProjectPaymentScheduleMasterModel();
			paymentScheduleMasterModel.setStrRemarks((String) obj[3]);
			paymentScheduleMasterModel.setStrPurpose((String) obj[2]);
			paymentScheduleMasterModel.setStrProjectName(strProjectName);
			paymentScheduleMasterModel.setStrPaymentDueDate(strPaymentDueDate);
			paymentScheduleMasterModel.setStrAmount(amount);
			paymentScheduleMasterModel.setNumInvoiceAmtInLakhs(cost);
			paymentScheduleMasterModel.setStrGroupName((String) obj[7]);
			paymentScheduleMasterModel.setStrReferenceNumber((String) obj[8]);
			paymentScheduleMasterModel.setStrClientName((String) obj[9]);
			paymentScheduleMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(paymentScheduleMasterModel);
		}			
		return dataList;
	}

	//Bhavesh(01-03-2024) getting the pending payment details year wise
	@Override
	public List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceYear(String invoiceFromDate, String invoiceToDate) {
		List <Object[]> List = projectInvoiceMasterDao.getPendingPaymentsDetailsByInvoiceYear(invoiceFromDate,invoiceToDate);
		List<ProjectInvoiceMasterModel> dataList = new ArrayList<ProjectInvoiceMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strInvoiceDate = null;
			Date invoiceDate = (Date) obj[4];
			strInvoiceDate = DateUtils.dateToString(invoiceDate);
			
			String strInvoiceAmt = null;
			String strTaxAmount = null;
			String strInvoiceTotalAmt = null;
			strInvoiceAmt = CurrencyUtils.convertToINR(obj[5]);
			strTaxAmount = CurrencyUtils.convertToINR(obj[6]);
			strInvoiceTotalAmt = CurrencyUtils.convertToINR(obj[7]);
			
			String strInvoiceRefno = (String) obj[1];
			String strInvoiceStatus = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[8]);
			//long projectId1 = (Long) obj[8];
			String strProjectName = (String) obj[0];
			ProjectInvoiceMasterModel projectInvoiceMasterModel = new ProjectInvoiceMasterModel();
			projectInvoiceMasterModel.setStrInvoiceDate(strInvoiceDate);
			projectInvoiceMasterModel.setStrInvoiceAmt(strInvoiceAmt);
			projectInvoiceMasterModel.setNumInvoiceAmt((Double) obj[5]);
			projectInvoiceMasterModel.setStrTaxAmount(strTaxAmount);
			projectInvoiceMasterModel.setNumTaxAmount((Double) obj[6]);
			projectInvoiceMasterModel.setStrInvoiceTotalAmt(strInvoiceTotalAmt);
			projectInvoiceMasterModel.setNumInvoiceTotalAmt((Double) obj[7]);
			projectInvoiceMasterModel.setStrInvoiceRefno(strInvoiceRefno);
			projectInvoiceMasterModel.setStrInvoiceStatus(strInvoiceStatus);
			projectInvoiceMasterModel.setStrProjectName(strProjectName);
			projectInvoiceMasterModel.setStrGroupName((String) obj[9]);
			projectInvoiceMasterModel.setStrReferenceNumber((String) obj[10]);
			projectInvoiceMasterModel.setStrClientName((String) obj[11]);
			projectInvoiceMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(projectInvoiceMasterModel);
		}
			
		return dataList;
		
	}

	@Override
	public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyYear(String dueFromDate, String dueToDate) {
		// TODO Auto-generated method stub
		List <Object[]> List = projectInvoiceMasterDao.getPendingInvoiceDetailbyYear(dueFromDate,dueToDate);
		List<ProjectPaymentScheduleMasterModel> dataList = new ArrayList<ProjectPaymentScheduleMasterModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String strPaymentDueDate = null;
			Date paymentDueDate = (Date) obj[1];
			strPaymentDueDate = DateUtils.dateToString(paymentDueDate);
	
			BigInteger b=new BigInteger(""+obj[4]);
			String strProjectName = (String) obj[0];
			String amount = null;
			amount = CurrencyUtils.convertToINR(obj[5]);
			
			double cost = 0.0;
			double projectAmount = Double.parseDouble(""+obj[5]);
			cost = CurrencyUtils.round((projectAmount/100000),2);
			amount = CurrencyUtils.convertToINR(cost);
			ProjectPaymentScheduleMasterModel paymentScheduleMasterModel = new ProjectPaymentScheduleMasterModel();
			paymentScheduleMasterModel.setStrRemarks((String) obj[3]);
			paymentScheduleMasterModel.setStrPurpose((String) obj[2]);
			paymentScheduleMasterModel.setStrProjectName(strProjectName);
			paymentScheduleMasterModel.setStrPaymentDueDate(strPaymentDueDate);
			paymentScheduleMasterModel.setStrAmount(amount);
			paymentScheduleMasterModel.setNumInvoiceAmtInLakhs(cost);
			paymentScheduleMasterModel.setStrGroupName((String) obj[7]);
			paymentScheduleMasterModel.setStrReferenceNumber((String) obj[8]);
			paymentScheduleMasterModel.setStrClientName((String) obj[9]);
			paymentScheduleMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(paymentScheduleMasterModel);
		}			
		return dataList;
	}
}
