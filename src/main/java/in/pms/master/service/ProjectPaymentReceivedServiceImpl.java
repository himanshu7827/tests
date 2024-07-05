package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.ProjectPaymentReceivedDao;
import in.pms.master.dao.ProjectPaymentScheduleMasterDao;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectPaymentReceivedDomain;
import in.pms.master.domain.ProjectPaymentReceivedWithoutInvoiceDomain;
import in.pms.master.domain.ProjectPaymentSeheduleMasterDomain;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.model.ProjectPaymentWithoutInvoiceMasterModel;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectPaymentReceivedServiceImpl implements ProjectPaymentReceivedService{

	private static final String[] String = null;


	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ProjectPaymentReceivedDao projectPaymentReceivedDao;
	
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	GlobalDao globalDao;
	
	@Autowired
	ProjectPaymentScheduleMasterDao projectPaymentScheduleMasterDao;
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_PAYMENT_RECEIVED_MST')")
	public long saveUpdatePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel){	
		if(!projectPaymentReceivedModel.isPaymentWithoutInvoice()){
			
			ProjectPaymentReceivedDomain projectPaymentReceivedDomain = convertPaymentReceivedModelToDomain(projectPaymentReceivedModel);
			long withoutInvId=0;
			long id=0;
			if(projectPaymentReceivedModel.isPrevPayment()){
				ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain= projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceById(projectPaymentReceivedModel.getPaymentId());
				Double alreadyMappedAmt=projectPaymentReceivedWithoutInvoiceDomain.getNumMappedAmount();
				if(projectPaymentReceivedModel.isValid())
				projectPaymentReceivedWithoutInvoiceDomain.setNumMappedAmount(alreadyMappedAmt+projectPaymentReceivedModel.getNumReceivedAmount());
				else
				projectPaymentReceivedWithoutInvoiceDomain.setNumMappedAmount(alreadyMappedAmt-projectPaymentReceivedModel.getNumReceivedAmount());

				String str=projectPaymentReceivedWithoutInvoiceDomain.getStrPaymentReceivedIds();
				projectPaymentReceivedWithoutInvoiceDomain.setStrPaymentReceivedIds(str);
				Set<ProjectPaymentSeheduleMasterDomain> set=new HashSet<ProjectPaymentSeheduleMasterDomain>();
				if(null != projectPaymentReceivedModel.getScheduledPaymentIDs() && projectPaymentReceivedModel.getScheduledPaymentIDs().size()>0){
					List<ProjectPaymentSeheduleMasterDomain> details= projectPaymentScheduleMasterDao.getAllProjectPaymentSchedule(projectPaymentReceivedModel.getScheduledPaymentIDs());			
					set.addAll(details);				
				}
				projectPaymentReceivedWithoutInvoiceDomain.setProjectPaymentSeheduleMasterDomain(set);
			 withoutInvId=projectPaymentReceivedDao.saveUpdatePaymentReceivedWithoutInvoice(projectPaymentReceivedWithoutInvoiceDomain);	
			}
			projectPaymentReceivedDomain.setNumPaymentWithoutInvId(withoutInvId);
			id=projectPaymentReceivedDao.saveUpdatePaymentReceived(projectPaymentReceivedDomain);

			

		 return id;
		}
		else{
			ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain = convertPaymentReceivedWithoutInvoiceModelToDomain(projectPaymentReceivedModel);
			return projectPaymentReceivedDao.saveUpdatePaymentReceivedWithoutInvoice(projectPaymentReceivedWithoutInvoiceDomain);	
			
		}
	}
		
	private ProjectPaymentReceivedWithoutInvoiceDomain convertPaymentReceivedWithoutInvoiceModelToDomain(
			ProjectPaymentReceivedModel projectPaymentReceivedModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain = new ProjectPaymentReceivedWithoutInvoiceDomain();
		if(projectPaymentReceivedModel.getNumId()!=0){				
			projectPaymentReceivedWithoutInvoiceDomain =  projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceById(projectPaymentReceivedModel.getNumId());
		}
		
		projectPaymentReceivedWithoutInvoiceDomain.setDtTrDate(new Date());
		projectPaymentReceivedWithoutInvoiceDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(projectPaymentReceivedModel.isValid()){
			projectPaymentReceivedWithoutInvoiceDomain.setNumIsValid(1);
		}else{
			projectPaymentReceivedWithoutInvoiceDomain.setNumIsValid(0);
		}
		projectPaymentReceivedWithoutInvoiceDomain.setNumReceivedAmount(projectPaymentReceivedModel.getNumReceivedAmount());
		if(projectPaymentReceivedWithoutInvoiceDomain.getNumMappedAmount()==null)
		projectPaymentReceivedWithoutInvoiceDomain.setNumMappedAmount(0.0);

		try{
		projectPaymentReceivedWithoutInvoiceDomain.setDtPayment(DateUtils.dateStrToDate(projectPaymentReceivedModel.getDtPayment()));
		}catch (ParseException e) {				
			e.printStackTrace();
		}
		projectPaymentReceivedWithoutInvoiceDomain.setStrPaymentMode(projectPaymentReceivedModel.getStrPaymentMode());
		projectPaymentReceivedWithoutInvoiceDomain.setStrUtrNumber(projectPaymentReceivedModel.getStrUtrNumber());
		projectPaymentReceivedWithoutInvoiceDomain.setProjectMasterDomain(projectMasterService.getProjectMasterDataById(projectPaymentReceivedModel.getProjectId()));
		projectPaymentReceivedWithoutInvoiceDomain.setStrRemarks(projectPaymentReceivedModel.getRemarks());
		Set<ProjectPaymentSeheduleMasterDomain> set=new HashSet<ProjectPaymentSeheduleMasterDomain>();

		if(null != projectPaymentReceivedModel.getScheduledPaymentIDs() && projectPaymentReceivedModel.getScheduledPaymentIDs().size()>0){
			List<ProjectPaymentSeheduleMasterDomain> details= projectPaymentScheduleMasterDao.getAllProjectPaymentSchedule(projectPaymentReceivedModel.getScheduledPaymentIDs());			
			set.addAll(details);
		}		
		projectPaymentReceivedWithoutInvoiceDomain.setProjectPaymentSeheduleMasterDomain(set);
		/*----------------------------------- set the fields [itTDS,gstTDS,LD,ShortPayment,otherRecovery and excess_Payment] values in the domain [05-12-2023]---*/
		projectPaymentReceivedWithoutInvoiceDomain.setItTDS(projectPaymentReceivedModel.getItTDS()!= null ? projectPaymentReceivedModel.getItTDS() : 0);
		projectPaymentReceivedWithoutInvoiceDomain.setGstTDS(projectPaymentReceivedModel.getGstTDS()!= null ? projectPaymentReceivedModel.getGstTDS() : 0);
		projectPaymentReceivedWithoutInvoiceDomain.setShortPayment(projectPaymentReceivedModel.getShortPayment()!= null ? projectPaymentReceivedModel.getShortPayment() : 0);
		projectPaymentReceivedWithoutInvoiceDomain.setOtherRecovery(projectPaymentReceivedModel.getOtherRecovery()!= null ? projectPaymentReceivedModel.getOtherRecovery() : 0);
		projectPaymentReceivedWithoutInvoiceDomain.setLdPayment(projectPaymentReceivedModel.getLdPayment()!= null ? projectPaymentReceivedModel.getLdPayment() : 0);
		projectPaymentReceivedWithoutInvoiceDomain.setExcessPayment(projectPaymentReceivedModel.getExcessPaymentAmount()!= null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0);
		/*-----------------------------------------------------------------------------------------*/
		return projectPaymentReceivedWithoutInvoiceDomain;

		
	}
						

	@Override
	public String checkDuplicatePaymentReceivedNo(ProjectPaymentReceivedModel projectPaymentReceivedModel){
		String result=	"";
		if(!projectPaymentReceivedModel.isPaymentWithoutInvoice()){
		ProjectPaymentReceivedDomain projectPaymentReceivedDomain = projectPaymentReceivedDao.getPaymentReceivedNo(projectPaymentReceivedModel.getStrUtrNumber(),projectPaymentReceivedModel.getProjectId());
		
		 if(null == projectPaymentReceivedDomain){
				return null; 
			 }else if(projectPaymentReceivedModel.getNumId() != 0){
				 if(projectPaymentReceivedDomain.getNumId() == projectPaymentReceivedModel.getNumId()){
					 return null; 
				 }else{
					 result = "Project Payment Received with same Reference Number already exist with Id "+projectPaymentReceivedDomain.getNumId();
				 }
			 }else{
				if(projectPaymentReceivedDomain.getNumIsValid() == 0){
					result = "Project Payment Received with same Reference Number Details already exist with Id "+projectPaymentReceivedDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Payment Received with same Reference Number Details already exist with Id "+projectPaymentReceivedDomain.getNumId();
				}			
			 }
			return result;	
	}else{
		
		ProjectPaymentReceivedWithoutInvoiceDomain projectPaymentReceivedWithoutInvoiceDomain= projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceNo(projectPaymentReceivedModel.getStrUtrNumber(),projectPaymentReceivedModel.getProjectId());
		 if(null == projectPaymentReceivedWithoutInvoiceDomain){
				return null; 
			 }else if(projectPaymentReceivedModel.getNumId() != 0){
				 if(projectPaymentReceivedWithoutInvoiceDomain.getNumId() == projectPaymentReceivedModel.getNumId()){
					 return null; 
				 }else{
					 result = "Project Payment Received with same Reference Number already exist with Id "+projectPaymentReceivedWithoutInvoiceDomain.getNumId();
				 }
			 }else{
				if(projectPaymentReceivedWithoutInvoiceDomain.getNumIsValid() == 0){
					result = "Project Payment Received with same Reference Number Details already exist with Id "+projectPaymentReceivedWithoutInvoiceDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Payment Received with same Reference Number Details already exist with Id "+projectPaymentReceivedWithoutInvoiceDomain.getNumId();
				}			
			 }
			return result;	
	}
	}
	
	@Override
	public ProjectPaymentReceivedModel getPaymentReceivedDomainById(long numId){
		return convertPaymentReceivedDomainToModel(projectPaymentReceivedDao.getPaymentReceivedById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_PAYMENT_RECEIVED_MST')")
	public List<ProjectPaymentReceivedModel> getAllPaymentReceivedDomain(){
		return convertPaymentReceivedDomainToModelList(projectPaymentReceivedDao.getAllPaymentReceivedDomain(),0);
	}
	
	@Override
	public List<ProjectPaymentReceivedModel> getAllActivePaymentReceivedDomain(){
		return convertPaymentReceivedDomainToModelList(projectPaymentReceivedDao.getAllActivePaymentReceivedDomain(),0);
	}
	
	/*@Override
	public List<ProjectInvoiceMasterModel> getAllActiveProjectMasterDomain(){
		return convertOrganisationMasterDomainToModelList(ProjectPaymentReceivedDao.getAllActiveProjectMasterDomain());
	}*/
	
	public ProjectPaymentReceivedDomain convertPaymentReceivedModelToDomain(ProjectPaymentReceivedModel projectPaymentReceivedModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectPaymentReceivedDomain projectPaymentReceivedDomain = new ProjectPaymentReceivedDomain();
		if(projectPaymentReceivedModel.getNumId()!=0){				
			projectPaymentReceivedDomain =  projectPaymentReceivedDao.getPaymentReceivedById(projectPaymentReceivedModel.getNumId());
		}
		
		projectPaymentReceivedDomain.setDtTrDate(new Date());
		projectPaymentReceivedDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(projectPaymentReceivedModel.isValid()){
			projectPaymentReceivedDomain.setNumIsValid(1);
		}else{
			projectPaymentReceivedDomain.setNumIsValid(0);
		}
		projectPaymentReceivedDomain.setNumReceivedAmount(projectPaymentReceivedModel.getNumReceivedAmount());
		try{
		projectPaymentReceivedDomain.setDtPayment(DateUtils.dateStrToDate(projectPaymentReceivedModel.getDtPayment()));
		}catch (ParseException e) {				
			e.printStackTrace();
		}
		projectPaymentReceivedDomain.setStrPaymentMode(projectPaymentReceivedModel.getStrPaymentMode());
		projectPaymentReceivedDomain.setStrUtrNumber(projectPaymentReceivedModel.getStrUtrNumber());
		projectPaymentReceivedDomain.setProjectInvoiceMasterDomain(projectInvoiceMasterService.getProjectInvoiceDomainById(projectPaymentReceivedModel.getInvoiceId()));
		//projectPaymentReceivedDomain.setProjectId(projectPaymentReceivedModel.getProjectId());
		projectPaymentReceivedDomain.setProjectMasterDomain(projectMasterService.getProjectMasterDataById(projectPaymentReceivedModel.getProjectId()));

		long invoiceId = projectPaymentReceivedModel.getInvoiceId();
		long receiveId= projectPaymentReceivedModel.getNumId();
		ProjectInvoiceMasterDomain invoiceDomain=projectInvoiceMasterService.getProjectInvoiceDomainById(invoiceId);
		double previousPaymentRecv=totalPaymentReceivedByInvoiceId(invoiceId,receiveId);
		

		if(invoiceDomain.getStrInvoiceStatus().equals("Generated")){
			if((((previousPaymentRecv)+(projectPaymentReceivedModel.getNumReceivedAmount())== invoiceDomain.getNumInvoiceAmt()))){
				invoiceDomain.setStrInvoiceStatus("Payment Fully Paid");
			}else if(projectPaymentReceivedModel.getNumReceivedAmount() < invoiceDomain.getNumInvoiceAmt()){				
					invoiceDomain.setStrInvoiceStatus("Payment Partially Paid");	
				}
		}
		else if(invoiceDomain.getStrInvoiceStatus().equals("Payment Partially Paid")){
			if((((previousPaymentRecv)+(projectPaymentReceivedModel.getNumReceivedAmount())== invoiceDomain.getNumInvoiceAmt()))){
				invoiceDomain.setStrInvoiceStatus("Payment Fully Paid");
			}
			else if(((previousPaymentRecv)+(projectPaymentReceivedModel.getNumReceivedAmount())< invoiceDomain.getNumInvoiceAmt())){
				invoiceDomain.setStrInvoiceStatus("Payment Partially Paid");	
			}

		
	}
		/*------------------------------------ set the fields [itTDS,gstTDS,LD,ShortPayment,otherRecovery and excess_Payment] values in the domain [05-12-2023]---*/
		projectPaymentReceivedDomain.setItTDS(projectPaymentReceivedModel.getItTDS()!= null ? projectPaymentReceivedModel.getItTDS() : 0);
		projectPaymentReceivedDomain.setGstTDS(projectPaymentReceivedModel.getGstTDS()!= null ? projectPaymentReceivedModel.getGstTDS() : 0);
		projectPaymentReceivedDomain.setShortPayment(projectPaymentReceivedModel.getShortPayment()!= null ? projectPaymentReceivedModel.getShortPayment() : 0);
		projectPaymentReceivedDomain.setOtherRecovery(projectPaymentReceivedModel.getOtherRecovery()!= null ? projectPaymentReceivedModel.getOtherRecovery() : 0);
		projectPaymentReceivedDomain.setLdPayment(projectPaymentReceivedModel.getLdPayment()!= null ? projectPaymentReceivedModel.getLdPayment() : 0);
		projectPaymentReceivedDomain.setExcessPayment(projectPaymentReceivedModel.getExcessPaymentAmount()!= null ? projectPaymentReceivedModel.getExcessPaymentAmount() : 0);
		/*-----------------------------------------------------------------------------------------*/
		projectInvoiceMasterService.mergeProjectInvoiceMaster(invoiceDomain);
						
		return projectPaymentReceivedDomain;
	}
	
	public List<ProjectPaymentReceivedModel> convertPaymentReceivedDomainToModelList(List<ProjectPaymentReceivedDomain> paymentReceivedList,long projectId){
		List<ProjectPaymentReceivedModel> list = new ArrayList<ProjectPaymentReceivedModel>();
		//ProjectPaymentReceivedModel projectInvoiceModel = new ProjectPaymentReceivedModel();
		ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();
	    
		Double projectCost=0.0;
			for(int i=0;i<paymentReceivedList.size();i++){
				ProjectPaymentReceivedDomain projectPaymentReceivedDomain = paymentReceivedList.get(i);
				if(i==0 && projectId !=0){
					projectMasterDomain= projectMasterService.getProjectMasterDataById(projectId);					
					//projectCost=(projectMasterDomain.getProjectCost())*100000;
					projectCost=(projectMasterDomain.getProjectCost());
				}
			
				ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
				
				if(projectPaymentReceivedDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+projectPaymentReceivedDomain.getNumId());
					projectPaymentReceivedModel.setEncProjectId(encryptedId);
				}
				projectPaymentReceivedModel.setNumId(projectPaymentReceivedDomain.getNumId());
				if(projectPaymentReceivedDomain.getNumIsValid() ==1){
					projectPaymentReceivedModel.setValid(true);
				}else{
					projectPaymentReceivedModel.setValid(false);
				}
				projectCost=projectCost-projectPaymentReceivedDomain.getNumReceivedAmount();
				String strProjectCost=CurrencyUtils.convertToINR(projectCost);
				projectPaymentReceivedModel.setPaymentDue(strProjectCost);
				projectPaymentReceivedModel.setNumReceivedAmount(projectPaymentReceivedDomain.getNumReceivedAmount());
				projectPaymentReceivedModel.setStrdtPayment(DateUtils.dateToString(projectPaymentReceivedDomain.getDtPayment()));
				projectPaymentReceivedModel.setStrPaymentMode(projectPaymentReceivedDomain.getStrPaymentMode());
				projectPaymentReceivedModel.setStrUtrNumber(projectPaymentReceivedDomain.getStrUtrNumber());
				projectPaymentReceivedModel.setInvoiceCode(projectPaymentReceivedDomain.getProjectInvoiceMasterDomain().getStrInvoiceRefno());
				projectPaymentReceivedModel.setInvoiceId(projectPaymentReceivedDomain.getProjectInvoiceMasterDomain().getNumId());
				projectPaymentReceivedModel.setEncInvoiceId(encryptionService.encrypt(""+projectPaymentReceivedDomain.getProjectInvoiceMasterDomain().getNumId()));
				projectPaymentReceivedModel.setStrInvoiceStatus(projectPaymentReceivedDomain.getProjectInvoiceMasterDomain().getStrInvoiceStatus());
				projectPaymentReceivedModel.setWithInvoice(true);
				/*----- set the fields [itTDS,gstTDS,LD,ShortPayment,otherRecovery and excess_Payment] values in the model [05-12-2023]---*/
				projectPaymentReceivedModel.setItTDS(projectPaymentReceivedDomain.getItTDS());
				projectPaymentReceivedModel.setGstTDS(projectPaymentReceivedDomain.getGstTDS());
				projectPaymentReceivedModel.setShortPayment(projectPaymentReceivedDomain.getShortPayment());
				projectPaymentReceivedModel.setOtherRecovery(projectPaymentReceivedDomain.getOtherRecovery());
				projectPaymentReceivedModel.setLdPayment(projectPaymentReceivedDomain.getLdPayment());
				projectPaymentReceivedModel.setExcessPaymentAmount(projectPaymentReceivedDomain.getExcessPayment());
				/*-----------------------------------------------------------------------------------------*/
				if(projectPaymentReceivedDomain.getNumPaymentWithoutInvId()>0){
					projectPaymentReceivedModel.setPrevPayment(true);
					projectPaymentReceivedModel.setPaymentId(projectPaymentReceivedDomain.getNumPaymentWithoutInvId());
					ProjectPaymentReceivedWithoutInvoiceDomain withoutInv = projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceById(projectPaymentReceivedDomain.getNumPaymentWithoutInvId());
					projectPaymentReceivedModel.setStrPrevPaymentDetails(withoutInv.getStrUtrNumber() +"[ dated " + withoutInv.getDtPayment()+" : (INR)"+withoutInv.getNumReceivedAmount() +" ]");
				}else
					projectPaymentReceivedModel.setPrevPayment(false);

				list.add(projectPaymentReceivedModel);
	}
		return list;
	}

	
	
	@Override
	public List<ProjectPaymentReceivedModel> getPaymentReceivedByProjectId(long projectId) {		
			List<ProjectPaymentReceivedDomain> projectPaymentReceivedDomain = projectPaymentReceivedDao.getAllPaymentReceivedByProjectID(projectId);
			List<ProjectPaymentReceivedModel> paymentReceivedModelList = convertPaymentReceivedDomainToModelList(projectPaymentReceivedDomain,projectId);
			return paymentReceivedModelList;
	}
	
	public ProjectPaymentReceivedModel convertPaymentReceivedDomainToModel(ProjectPaymentReceivedDomain projectPaymentReceivedDomain){
		ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
	
		if(projectPaymentReceivedDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+projectPaymentReceivedDomain.getNumId());
			projectPaymentReceivedModel.setEncProjectId(encryptedId);
		}
		projectPaymentReceivedModel.setNumId(projectPaymentReceivedDomain.getNumId());
		if(projectPaymentReceivedDomain.getNumIsValid() ==1){
			projectPaymentReceivedModel.setValid(true);
		}else{
			projectPaymentReceivedModel.setValid(false);
		}
	
		
		projectPaymentReceivedModel.setNumReceivedAmount(projectPaymentReceivedDomain.getNumReceivedAmount());
		projectPaymentReceivedModel.setDtPayment(DateUtils.dateToString(projectPaymentReceivedDomain.getDtPayment()));
		projectPaymentReceivedModel.setStrPaymentMode(projectPaymentReceivedDomain.getStrPaymentMode());
		projectPaymentReceivedModel.setStrUtrNumber(projectPaymentReceivedDomain.getStrUtrNumber());
	
		
		return projectPaymentReceivedModel;
		
	}
	public long deletePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ProjectPaymentReceivedDomain> projectPaymentReceivedList = projectPaymentReceivedDao.getPaymentReceivedById(projectPaymentReceivedModel.getIdCheck());
		for(int i=0;i<projectPaymentReceivedList.size();i++){
			ProjectPaymentReceivedDomain projectPaymentReceivedDomain = projectPaymentReceivedList.get(i);
			projectPaymentReceivedDomain.setNumIsValid(2);
			projectPaymentReceivedDomain.setDtTrDate(new Date());
			projectPaymentReceivedDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = projectPaymentReceivedDao.saveUpdatePaymentReceived(projectPaymentReceivedDomain);
		}
		return result;
	}	
	

	@Override
	public List<ProjectPaymentReceivedModel> getProjectPaymentReceivedByProjectId(long projectId) {		
			List<ProjectPaymentReceivedDomain> projectPaymentReceivedDomain = projectPaymentReceivedDao.getProjectPaymentReceivedByProjectId(projectId);
			List<ProjectPaymentReceivedModel> paymentReceivedModelList = convertPaymentReceivedDomainToModelList(projectPaymentReceivedDomain,projectId);
			
			List<ProjectPaymentReceivedWithoutInvoiceDomain> projectPaymentReceivedWithoutInvoiceDomain = projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceByProjectId(projectId);
			List<ProjectPaymentReceivedModel> list = convertProjectWithoutInvForLedger(projectPaymentReceivedWithoutInvoiceDomain, projectId);
			paymentReceivedModelList.addAll(list);
			
			return paymentReceivedModelList;
	}
	
	private List<ProjectPaymentReceivedModel> convertProjectWithoutInvForLedger(
			List<ProjectPaymentReceivedWithoutInvoiceDomain> projectPaymentReceivedWithoutInvoiceDomain, long projectId) {
		List<ProjectPaymentReceivedModel> list=new ArrayList<ProjectPaymentReceivedModel>();
		Double AmtRcvdAgnstInvocie=0.0;
		for (ProjectPaymentReceivedWithoutInvoiceDomain domain : projectPaymentReceivedWithoutInvoiceDomain) {
			ProjectPaymentReceivedModel model=new ProjectPaymentReceivedModel();
			model.setDtPayment(domain.getDtPayment()+"");
			model.setStrdtPayment(DateUtils.dateToString(domain.getDtPayment()));
			model.setNumReceivedAmount(domain.getNumReceivedAmount());
			model.setNumMappedAmount(domain.getNumMappedAmount());
			model.setNumBalanceAmount(domain.getNumReceivedAmount()-domain.getNumMappedAmount());
			model.setStrPaymentMode(domain.getStrPaymentMode());
			model.setStrUtrNumber(domain.getStrUtrNumber());
			/*---- Set the fields [itTDS,gstTDS,LD,ShortPayment,other recovery and excess_Payment] values in the model [05-12-2023]---*/
			model.setItTDS(domain.getItTDS());
			model.setGstTDS(domain.getGstTDS());
			model.setShortPayment(domain.getShortPayment());
			model.setOtherRecovery(domain.getOtherRecovery());
			model.setLdPayment(domain.getLdPayment());
			model.setExcessPaymentAmount(domain.getExcessPayment());
			/*-----------------------------------------------------------------------------------------*/
			if(domain.getNumIsValid() ==1){
				model.setValid(true);
			}else{
				model.setValid(false);
			}
			//Double projectCost=(domain.getProjectMasterDomain().getProjectCost())*100000;
			Double projectCost=(domain.getProjectMasterDomain().getProjectCost());
			List<ProjectPaymentReceivedModel> datalist = getProjectPaymentReceivedWithInvoiceByProject(projectId);
		
			for (ProjectPaymentReceivedModel projectPaymentReceivedModel : datalist) {
				AmtRcvdAgnstInvocie+=projectPaymentReceivedModel.getNumReceivedAmount();
			}
			projectCost = projectCost-AmtRcvdAgnstInvocie-(domain.getNumReceivedAmount()-domain.getNumMappedAmount());
			AmtRcvdAgnstInvocie=AmtRcvdAgnstInvocie+domain.getNumReceivedAmount();
			String strProjectCost=CurrencyUtils.convertToINR(projectCost);
			model.setPaymentDue(strProjectCost);
			model.setPrevPayment(true);
			model.setNumId(domain.getNumId());
			model.setWithInvoice(false);
			model.setRemarks(domain.getStrRemarks());
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
	public double totalPaymentReceivedByInvoiceId(long invoiceId,long receiveId){
		double result =-1;
		result = projectPaymentReceivedDao.totalPaymentReceivedByInvoiceId(invoiceId,receiveId);
		
		return result;
	}
	
	@Override
	public List<ProjectPaymentReceivedModel> getActivePaymentReceivedByProjectId(long projectId){
		return convertPaymentReceivedDomainToModelList(projectPaymentReceivedDao.getActivePaymentReceivedByProjectId(projectId), projectId);
	}
	
	@Override
	public String getIncome(Date startDate,Date endRange) {
		String payment =  null;
		List<Double> paymentList = projectPaymentReceivedDao.getIncome(startDate,endRange);	
		List<Double> withoutInvoicePaymentList =projectPaymentReceivedDao.getPaymentReceivedWithoutInvoice(startDate,endRange);
		double actualAmount = 0;
		
		if(paymentList.size()>0){			
			if(null!= paymentList.get(0) ){
				actualAmount = paymentList.get(0);				
			}		
		}
		
		if(withoutInvoicePaymentList.size()>0){			
			if(null!= withoutInvoicePaymentList.get(0) ){
				actualAmount += withoutInvoicePaymentList.get(0);				
			}		
		}
		
		double dataInLakhs = CurrencyUtils.round((actualAmount/100000),2);
		payment = CurrencyUtils.convertToINR(dataInLakhs);
		
		return payment;
	}

	

	@Override
	public JSONArray getIncomeByGroup(Date startDate,Date endDate) {
		
		JSONArray finalArray = new JSONArray(); 
		JSONArray tempArray = new JSONArray(); 
		JSONArray colorArray = new JSONArray(); 
			tempArray.put("Group");
			tempArray.put("Income");
			
		finalArray.put(tempArray);
		
		List<Object[]> dataList = projectPaymentReceivedDao.getPaymentReceivedByGroup(startDate,endDate);
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONArray array = new JSONArray();
			array.put(obj[2]);
			array.put(obj[0]);
			colorArray.put(obj[3]);
			finalArray.put(array);
		}
		finalArray.put(colorArray);
		return finalArray;	
	}

	@Override
	public List<ProjectPaymentReceivedModel> getIncomeByGroupwiseProject(ProjectPaymentReceivedModel projectPaymentReceiveModel) {
		String strStartDate = projectPaymentReceiveModel.getStartDate();
		String strEndDate = projectPaymentReceiveModel.getEndDate();
		Date startRange = null;
		Date endRange = null;
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startRange = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		if(null != strEndDate && !strEndDate.equals("")){
			try {
				endRange = DateUtils.dateStrToDate(strEndDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			endRange = new Date();
		}
		List<Object[]> List = projectPaymentReceivedDao.getPaymentReceivedByProjectByGroup(startRange,endRange,projectPaymentReceiveModel);
		List<ProjectPaymentReceivedModel> dataList = new ArrayList<ProjectPaymentReceivedModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String groupName = (String) obj[2];
			String projectName = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[4]);
		
			//long projectId = (long) obj[4];
			String strReceivedAmount = null;
			double roundOfProjectCost = 0.0;
			double projectCost = Double.parseDouble(""+obj[0]);
			//System.out.println(projectCost);
			roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
			strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
			//System.out.print(strReceivedAmount);
			ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
			projectPaymentReceivedModel.setGroupName(groupName);
			projectPaymentReceivedModel.setProjectName(projectName);
			projectPaymentReceivedModel.setStrReceivedAmount(strReceivedAmount + " Lakhs");
			projectPaymentReceivedModel.setProjectId(b.longValue());
			projectPaymentReceivedModel.setStrReferenceNumber((String)obj[5]);
			projectPaymentReceivedModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(projectPaymentReceivedModel);
			//proposalMasterForm.setEncNumId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
		}
		return dataList;
	}

	@Override
	public List<ProjectPaymentReceivedModel> getProjectPaymentReceivedWithInvoiceByProject(long projectId) {
		List<Object[]> List = projectPaymentReceivedDao.getPaymentReceivedwithInvoice(projectId);
		List<ProjectPaymentReceivedModel> dataList = new ArrayList<ProjectPaymentReceivedModel>();
		//System.out.println(List.size());
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String paymentDate = null;
			String strAmount = null;
			Date strPaymentDate = (Date) obj[0];
			paymentDate = DateUtils.dateToString(strPaymentDate);			
			strAmount = CurrencyUtils.convertToINR(obj[1]);			
			String paymentMode = (String) obj[2];
			String utrNumber = (String) obj[3];
			String invoiceRefNo = (String) obj[4];
			long strProjectId = (Long) obj[5];
			long invoiceId = (long) obj[6];
			ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
			projectPaymentReceivedModel.setDtpaymentDate(paymentDate);
			projectPaymentReceivedModel.setNumReceivedAmount((Double) obj[1]);
			projectPaymentReceivedModel.setStrReceivedAmount(strAmount);;
			projectPaymentReceivedModel.setStrPaymentMode(paymentMode);
			projectPaymentReceivedModel.setStrUtrNumber(utrNumber);
			projectPaymentReceivedModel.setInvoiceRefNo(invoiceRefNo);
			
			projectPaymentReceivedModel.setEncProjectId(encryptionService.encrypt(""+strProjectId));			
			projectPaymentReceivedModel.setInvoiceId(invoiceId);
			projectPaymentReceivedModel.setEncInvoiceId(encryptionService.encrypt(""+invoiceId));
			dataList.add(projectPaymentReceivedModel);
		}
			
		return dataList;
	}

	
	public List<ProjectPaymentWithoutInvoiceMasterModel> getProjectPaymentWithoutInvoice(long projectId) {
		List<ProjectPaymentReceivedWithoutInvoiceDomain> projectInvoiceMasterDomain = projectPaymentReceivedDao.getPaymentReceivedWithoutInvoiceByProjectId(projectId);
		List<ProjectPaymentWithoutInvoiceMasterModel> list = convertProjectWithoutInvoiceMasterDomainToModelList(projectInvoiceMasterDomain);
		return list;
	}

	private List<ProjectPaymentWithoutInvoiceMasterModel> convertProjectWithoutInvoiceMasterDomainToModelList(List<ProjectPaymentReceivedWithoutInvoiceDomain> projectInvoiceMasterDomain) {
		List<ProjectPaymentWithoutInvoiceMasterModel> list=new ArrayList<ProjectPaymentWithoutInvoiceMasterModel>();
		for (ProjectPaymentReceivedWithoutInvoiceDomain domain : projectInvoiceMasterDomain) {
			ProjectPaymentWithoutInvoiceMasterModel model=new ProjectPaymentWithoutInvoiceMasterModel();
			model.setNumId(domain.getNumId());
			model.setNumReceivedAmount(domain.getNumReceivedAmount());
			model.setNumMappedAmount(domain.getNumMappedAmount());
			model.setStrUtrNumber(domain.getStrUtrNumber());
			model.setDtPayment(domain.getDtPayment());
			model.setStrPaymentRcvdDate(domain.getDtPayment()+"");
			list.add(model);
		}
		return list;
	}

	@Override
	public List<ProjectPaymentReceivedModel> getPaymentReceivedDetails(ProjectPaymentReceivedModel model){
		String strStartDate = model.getStartDate();
		String strEndDate = model.getEndDate();
		Date startRange = null;
		Date endRange = null;
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startRange = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		if(null != strEndDate && !strEndDate.equals("")){
			try {
				endRange = DateUtils.dateStrToDate(strEndDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			endRange = new Date();
		}
		
		List<Object[]> list = projectPaymentReceivedDao.getPaymentReceivedDetails(startRange,endRange,model);
		List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoice(startRange,endRange, model);
		
		
		Map<String, String> map = new HashMap<String, String>(); 
		
		List<ProjectPaymentReceivedModel> dataList = new ArrayList<ProjectPaymentReceivedModel>();
		if(null != list){
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
			String groupName = (String) obj[2];
			String projectName = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[4]);
			String encProjectId = "";
			if(map.containsKey(projectName)){
				encProjectId= map.get(projectName);
			}else{
				encProjectId = encryptionService.encrypt(""+b.longValue());
				map.put(projectName, encProjectId);
			}
	
			
			Date paymentDate = (Date) obj[5];
		
			//long projectId = (long) obj[4];
			String strReceivedAmount = null;
			double roundOfProjectCost = 0.0;
			double projectCost = Double.parseDouble(""+obj[0]);
			//System.out.println(projectCost);
			roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
			strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
			//System.out.print(strReceivedAmount);
			ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
			projectPaymentReceivedModel.setGroupName(groupName);
			projectPaymentReceivedModel.setProjectName(projectName);
			projectPaymentReceivedModel.setStrReceivedAmount(strReceivedAmount + " Lakhs");
			projectPaymentReceivedModel.setNumReceivedAmountLakhs(roundOfProjectCost);
			projectPaymentReceivedModel.setProjectId(b.longValue());
			projectPaymentReceivedModel.setStrReferenceNumber((String)obj[6]);
			if(null != paymentDate){
				projectPaymentReceivedModel.setStrdtPayment(DateUtils.dateToString(paymentDate));
				projectPaymentReceivedModel.setTempDate(paymentDate);
			}
			projectPaymentReceivedModel.setEncProjectId(encProjectId);
			
			dataList.add(projectPaymentReceivedModel);			
		}
		}
		
		
		if(null != withoutInvoiceList){
		for(int i=0;i<withoutInvoiceList.size();i++){
			Object[] obj = withoutInvoiceList.get(i);
			String groupName = (String) obj[2];
			String projectName = (String) obj[3];
			BigInteger b=new BigInteger(""+obj[4]);
			String encProjectId = "";
			if(map.containsKey(projectName)){
				encProjectId= map.get(projectName);
			}else{
				encProjectId = encryptionService.encrypt(""+b.longValue());
				map.put(projectName, encProjectId);
			}
			
			String strReceivedAmount = null;
			double roundOfProjectCost = 0.0;
			double projectCost = Double.parseDouble(""+obj[0]);
			if(projectCost >0){
				Date paymentDate = (Date) obj[5];
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				//System.out.print(strReceivedAmount);
				ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
				projectPaymentReceivedModel.setGroupName(groupName);
				projectPaymentReceivedModel.setProjectName(projectName);
				projectPaymentReceivedModel.setStrReceivedAmount(strReceivedAmount + " Lakhs");
				projectPaymentReceivedModel.setNumReceivedAmountLakhs(roundOfProjectCost);
				projectPaymentReceivedModel.setProjectId(b.longValue());
				projectPaymentReceivedModel.setStrReferenceNumber((String)obj[6]);
				projectPaymentReceivedModel.setEncProjectId(encProjectId);
				if(null != paymentDate){
					projectPaymentReceivedModel.setStrdtPayment(DateUtils.dateToString(paymentDate));
					projectPaymentReceivedModel.setTempDate(paymentDate);
				}
				dataList.add(projectPaymentReceivedModel);
			}			
			}
		}
		//System.out.println(dataList);
		try{
		dataList.sort((ProjectPaymentReceivedModel s1, ProjectPaymentReceivedModel s2)->s2.getTempDate().compareTo(s1.getTempDate()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dataList;
		
	}
	@Override
	public List<ProjectPaymentReceivedModel> getTotalProjectPaymentReceivedWithInvoiceByProject(long projectId) {
		List<Object[]> List = projectPaymentReceivedDao.getPaymentReceivedwithInvoice(projectId);
		List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoiceByPiD(projectId);
		List<ProjectPaymentReceivedModel> dataList = new ArrayList<ProjectPaymentReceivedModel>();
		
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String paymentDate = null;
			String strAmount = null;
			Date strPaymentDate = (Date) obj[0];
			paymentDate = DateUtils.dateToString(strPaymentDate);			
			strAmount = CurrencyUtils.convertToINR(obj[1]);			
			String paymentMode = (String) obj[2];
			String utrNumber = (String) obj[3];
			String invoiceRefNo = (String) obj[4];
			long strProjectId = (Long) obj[5];
			long invoiceId = (long) obj[6];
			ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
			projectPaymentReceivedModel.setDtpaymentDate(paymentDate);
			projectPaymentReceivedModel.setTempDate(strPaymentDate);
			projectPaymentReceivedModel.setNumReceivedAmount((Double) obj[1]);
			/*------- Get the [itTDS,gstTDS,LD,ShortPayment,other recovery and excess_Payment] fields value [05-12-2023] ----------------*/
			Double amt_ItTDS = (Double) obj[7];
			projectPaymentReceivedModel.setItTDS(amt_ItTDS);
			Double amt_GstTDS = (Double) obj[8];
			projectPaymentReceivedModel.setGstTDS(amt_GstTDS);
			Double amt_ShortPayment = (Double) obj[9];
			projectPaymentReceivedModel.setShortPayment(amt_ShortPayment);
			Double amt_LdPayment = (Double) obj[10];
			projectPaymentReceivedModel.setLdPayment(amt_LdPayment);
			Double amt_OtherRecovery = (Double) obj[11];
			projectPaymentReceivedModel.setOtherRecovery(amt_OtherRecovery);
			Double amt_ExcessPayment = (Double) obj[12];
			projectPaymentReceivedModel.setExcessPaymentAmount(amt_ExcessPayment);
			/*--- EOL Get the [itTDS,gstTDS,LD,ShortPayment,other recovery and excess_Payment] fields value [05-12-2023] ----------------*/
			projectPaymentReceivedModel.setStrReceivedAmount(strAmount);;
			projectPaymentReceivedModel.setStrPaymentMode(paymentMode);
			projectPaymentReceivedModel.setStrUtrNumber(utrNumber);
			projectPaymentReceivedModel.setInvoiceRefNo(invoiceRefNo);
			
			projectPaymentReceivedModel.setEncProjectId(encryptionService.encrypt(""+strProjectId));			
			projectPaymentReceivedModel.setInvoiceId(invoiceId);
			projectPaymentReceivedModel.setEncInvoiceId(encryptionService.encrypt(""+invoiceId));
			dataList.add(projectPaymentReceivedModel);
		}
		if(null != withoutInvoiceList){
			for(int i=0;i<withoutInvoiceList.size();i++){
				Object[] obj = withoutInvoiceList.get(i);
				/*String groupName = (String) obj[2];
				String projectName = (String) obj[3];*/
				BigInteger b=new BigInteger(""+obj[1]);
			
				
				String strReceivedAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[0]);
				if(projectCost >0){
					Date paymentDate = (Date) obj[2];
					roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
					strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
					ProjectPaymentReceivedModel projectPaymentReceivedModel = new ProjectPaymentReceivedModel();
					/*projectPaymentReceivedModel.setGroupName(groupName);
					projectPaymentReceivedModel.setProjectName(projectName);*/
					//projectPaymentReceivedModel.setStrReceivedAmount(strReceivedAmount + " Lakhs");
					projectPaymentReceivedModel.setStrReceivedAmount(CurrencyUtils.convertToINR(projectCost));
					/*---------- Get the [itTDS,gstTDS,LD,ShortPayment,other recovery and excess_Payment] fields value [05-12-2023] ----------------*/
					Double amt_ItTDS = (Double) obj[5];
					Double amt_GstTDS = (Double) obj[6];
					Double amt_ShortPayment = (Double) obj[7];
					Double amt_LdPayment = (Double) obj[8];
					Double amt_OtherRecovery = (Double) obj[9];
					Double amt_ExcessPayment = (Double) obj[10];
					projectPaymentReceivedModel.setItTDS(amt_ItTDS);
					projectPaymentReceivedModel.setGstTDS(amt_GstTDS);
					projectPaymentReceivedModel.setShortPayment(amt_ShortPayment);
					projectPaymentReceivedModel.setLdPayment(amt_LdPayment);
					projectPaymentReceivedModel.setOtherRecovery(amt_OtherRecovery);
					projectPaymentReceivedModel.setExcessPaymentAmount(amt_ExcessPayment);
					projectPaymentReceivedModel.setNumReceivedAmount((Double) obj[0]);
					/*------------------ EOL Get the [itTDS,gstTDS,LD,ShortPayment,other recovery and excess_Payment] fields value [05-12-2023] ----------------*/
					projectPaymentReceivedModel.setNumReceivedAmountLakhs(roundOfProjectCost);
					projectPaymentReceivedModel.setProjectId(b.longValue());		
					projectPaymentReceivedModel.setStrUtrNumber((String)obj[3]);
					projectPaymentReceivedModel.setStrPaymentMode((String)obj[4]);
					projectPaymentReceivedModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
					if(null != paymentDate){
						projectPaymentReceivedModel.setDtpaymentDate(DateUtils.dateToString(paymentDate));
						projectPaymentReceivedModel.setTempDate(paymentDate);
					}else{
						projectPaymentReceivedModel.setTempDate(new Date());
					}
					dataList.add(projectPaymentReceivedModel);
				}			
				}
			}
			try{
				if(null != dataList && dataList.size()>0){
				dataList.sort((ProjectPaymentReceivedModel s1, ProjectPaymentReceivedModel s2)->s2.getTempDate().compareTo(s1.getTempDate()));
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		return dataList;
	}

	
}
