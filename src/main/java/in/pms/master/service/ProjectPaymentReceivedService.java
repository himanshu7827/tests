package in.pms.master.service;

import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.model.ProjectPaymentWithoutInvoiceMasterModel;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectPaymentReceivedService {

	@Transactional	
	 public long saveUpdatePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel);		
	
	 public String checkDuplicatePaymentReceivedNo(ProjectPaymentReceivedModel projectPaymentReceivedModel);
	 
	 public ProjectPaymentReceivedModel getPaymentReceivedDomainById(long numId);
	
	 public List<ProjectPaymentReceivedModel> getAllPaymentReceivedDomain();
	
	 public List<ProjectPaymentReceivedModel> getAllActivePaymentReceivedDomain();
	
	@Transactional
	public long deletePaymentReceived(ProjectPaymentReceivedModel projectPaymentReceivedModel);

	@Transactional(propagation=Propagation.REQUIRED)
	public List<ProjectPaymentReceivedModel> getPaymentReceivedByProjectId(long projectId);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<ProjectPaymentReceivedModel> getProjectPaymentReceivedByProjectId(long projectId);
	
	public double totalPaymentReceivedByInvoiceId(long invoiceId,long receiveId);
	
	public List<ProjectPaymentReceivedModel> getActivePaymentReceivedByProjectId(long projectId);
	
	//Income 
    public String getIncome(Date startDate,Date endRange);
    
    //Income Groupwise
   JSONArray getIncomeByGroup(Date startDate,Date endDate);
   
   //Income By Project and Group
   @Transactional
   public List<ProjectPaymentReceivedModel> getIncomeByGroupwiseProject(ProjectPaymentReceivedModel projectPaymentReceivedModel);
   
   public List<ProjectPaymentReceivedModel> getProjectPaymentReceivedWithInvoiceByProject(long projectId);

   public List<ProjectPaymentWithoutInvoiceMasterModel> getProjectPaymentWithoutInvoice(long projectId);
   
   @Transactional
   public List<ProjectPaymentReceivedModel> getPaymentReceivedDetails(ProjectPaymentReceivedModel projectPaymentReceivedModel);
   
   public List<ProjectPaymentReceivedModel> getTotalProjectPaymentReceivedWithInvoiceByProject(long projectId);
   
}
