package in.pms.global.dao;

import in.pms.global.domain.ApprovalMasterDomain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkflowDao extends JpaRepository<ApprovalMasterDomain, Long>{

	
	
	@Query("from ApprovalMasterDomain a where a.workflowMasterDomain.numWorkflowId=:numWorkflowId and a.numIsValid=1 and a.roleMasterDomain.numId=:numLastRoleId and a.actionMasterDomain.numActionId=:numLastActionId and a.numNextRoleId=:numCurrentRoleId")
	List<ApprovalMasterDomain> getApprovalDetails(@Param("numWorkflowId")long numWorkflowId,@Param("numLastRoleId")long numLastRoleId,@Param("numLastActionId") long numLastActionId,
			@Param("numCurrentRoleId")long numCurrentRoleId);
	
	@Query("from ApprovalMasterDomain a where a.workflowMasterDomain.numWorkflowId=:numWorkflowId and a.numIsValid=1 and a.roleMasterDomain.numId=:numLastRoleId and a.actionMasterDomain.numActionId=:numLastActionId")
	List<ApprovalMasterDomain> getApprovalDetailsbyIds(@Param("numWorkflowId")long numWorkflowId,@Param("numLastRoleId")long numLastRoleId,@Param("numLastActionId") long numLastActionId);
	

	@Query("from ApprovalMasterDomain a where a.workflowMasterDomain.numWorkflowId=:numWorkflowId and a.numIsValid=1 and a.roleMasterDomain.numId=:numRoleId and a.actionMasterDomain.numActionId=:numActionId")
	List<ApprovalMasterDomain> getPreviewApprovalDetails(@Param("numWorkflowId")long numWorkflowId,@Param("numRoleId")long numRoleId,@Param("numActionId") long numActionId);

	@Query("from ApprovalMasterDomain a where a.workflowMasterDomain.numWorkflowId=:numWorkflowTypeId and a.numIsValid=1 and a.roleMasterDomain.numId=:numRoleId and a.actionMasterDomain.numActionId=:numActionId and a.numNextRoleId=:numNextRoleId")
	List<ApprovalMasterDomain> getProposedActions(@Param("numWorkflowTypeId")long numWorkflowTypeId,@Param("numRoleId")long numRoleId,@Param("numActionId") long numActionId,@Param("numNextRoleId")long numNextRoleId);

	//--------------- add ptm.num_role_id_fk for get the role id of user [26-09-2023] ---------------------------------------------
	@Query(value="select ptm.dt_tr_date, ptm.str_remarks,ptm.num_action_id_fk, pem.str_emp_name, pam.str_name , pam.str_action_performed,ptm.num_role_id_fk from pms_transaction_master ptm,pms_employee_master pem, pms_action_master pam where ptm.num_isvalid =1 and ptm.num_emp_id_fk = pem.emp_id and ptm.num_action_id_fk = pam.num_action_id and ptm.num_workflow_type_id_fk =:workflowId and num_custom_id=:customId", nativeQuery= true)
	public List<Object[]> getLatestTransactionDetail(@Param("customId")int customId,@Param("workflowId") int workflowId);
	
	@Query(value="select ptm.dt_tr_date, ptm.str_remarks,ptm.num_action_id_fk, pem.str_emp_name, pam.str_name , pam.str_action_performed from pms_transaction_master ptm,pms_employee_master pem, pms_action_master pam where ptm.num_isvalid =1 and ptm.num_emp_id_fk = pem.emp_id and ptm.num_action_id_fk = pam.num_action_id and ptm.num_workflow_type_id_fk =:workflowId and num_custom_id=:customId and ptm.num_action_id_fk!=17", nativeQuery= true)
	public List<Object[]> getLatestTransactionDetailWithoutReject(@Param("customId")int customId,@Param("workflowId") int workflowId);
	
}
