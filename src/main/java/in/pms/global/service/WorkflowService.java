package in.pms.global.service;

import in.pms.global.domain.ApprovalMasterDomain;
import in.pms.global.domain.RoleActionMappingDomain;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.model.TransactionMasterModel;
import in.pms.global.model.WorkflowModel;
import in.pms.transaction.model.MonthlyProgressModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


public interface WorkflowService {

	@Transactional
	public WorkflowModel getTransactionDetails(int numMonthlyProgressId);
	@Transactional
	public List<WorkflowModel> getNextRoleActionDetails(WorkflowModel workflowModel);
	@Transactional
	public WorkflowModel doWorkAccrodingToAction(WorkflowModel workflowModel);
	public TransactionMasterDomain insertIntoTransaction(TransactionMasterModel transactionMasterModel);
	public List<RoleActionMappingDomain> getRoleActionMappingDetails(long numActionId,long numRoleId,long numWorkflowId);
	public boolean updateMonhlyProgressReport(MonthlyProgressModel monthlyProgressModel);
	public boolean allowPreviewEdit(long numWorkflowId, long numRoleId, long numActionId);
	
	List<ApprovalMasterDomain> getProposedActions(long numWorkflowTypeId,long numRoleId, long numActionId,long numNextRoleId);
	
	@Transactional
	public WorkflowModel getLatestTransactionDetails(int customId,long workflowId);
	
	
	@Transactional
	public WorkflowModel getLatestTransactionDetail(int customId,int workflowId);
	
	@Transactional
	public WorkflowModel getLatestTransactionDetailWithoutReject(int customId,int workflowId);
	
	@Transactional
	public List<WorkflowModel> getTransactionDetails(int customId,int workflowId);
	
}
