package in.pms.master.service;

import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ProjectInvoiceMasterService {

	@Transactional	
	 public long saveUpdateProjectInvoiceMaster(ProjectInvoiceMasterModel projectInvoiceMasterModel);		
	
	 public String checkDuplicateProjectInvoiceRefNo(ProjectInvoiceMasterModel projectInvoiceMasterModel);
	 
	 public ProjectInvoiceMasterModel getProjectInvoiceMasterDomainById(long numId);
	 public List<ProjectInvoiceMasterModel> getProjectInvoiceMstDomainById(long numId);
	
	 public List<ProjectInvoiceMasterModel> getAllProjectInvoiceMasterDomain();
	
	 public List<ProjectInvoiceMasterModel> getAllActiveProjectInvoiceMasterDomain();
	
	@Transactional
	public long deleteProjectInvoice(ProjectInvoiceMasterModel projectInvoiceMasterModel);


	public List<ProjectInvoiceMasterModel> getProjectInvoiceByProjectId(long projectId);

	@Transactional
	public ProjectInvoiceMasterDomain getProjectInvoiceDomainById(long invoiceId);
	
	public List<ProjectInvoiceMasterModel> getProjectInvoiceRefNoByProjectId(long projectId);
	@Transactional	
	public long mergeProjectInvoiceMaster(ProjectInvoiceMasterDomain projectInvoiceMasterDomain);

	/*public List<ProjectInvoiceMasterModel> getInvoiceDetailsByInvoiceId(long invoiceId);*/
	public List<ProjectInvoiceMasterModel> getProjectInvoiceDetailsByProjectId(long projectId);
	
	 public long  getPendingPaymentsCount();
	 @Transactional
	 public List<ProjectInvoiceMasterModel> getPendingPaymentsInvoiceDetail(ProjectInvoiceMasterModel projectInvoiceMasterModel);
	 
	 @Transactional
	 public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetail();
	
	 @Transactional
	 public List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceDt(String invoiceDate,String symbol);
	 
	 @Transactional
	 public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyDate(String dueDate, String symbol);
    
	//Bhavesh(01-03-2024) getting the pending payment details year wise
	 @Transactional
	public List<ProjectInvoiceMasterModel> getPendingPaymentsDetailsByInvoiceYear(String invoiceFromDate, String invoiceToDate);
	
	//Bhavesh(01-03-2024) getting the pending payment details year wise
    @Transactional
	public List<ProjectPaymentScheduleMasterModel> getPendingInvoiceDetailbyYear(String dueFromDate, String dueToDate);
}
