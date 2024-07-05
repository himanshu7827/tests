package in.pms.global.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.pms.global.domain.TransactionMasterDomain;

@Repository
public interface TransactionDao  extends JpaRepository<TransactionMasterDomain, Long>{

	@Query("from TransactionMasterDomain t join fetch t.employeeMasterDomain where t.monthlyProgressDomain.numId=:monthlyProgressId and t.numIsValid=1")
	List<TransactionMasterDomain> getTransactionDetails(@Param("monthlyProgressId") int numMonthlyProgressId);

	@Transactional
    @Modifying
	@Query(nativeQuery=true,value="update pms_transaction_master set num_isvalid=0 where num_monthly_progress_id_fk=:monthlyProgressId")
	void updateAllRowsWithNumIsValidZero(@Param("monthlyProgressId") int numMonthlyProgressId);
	
	@Query("from TransactionMasterDomain t join fetch t.employeeMasterDomain where t.monthlyProgressDomain.numId=:monthlyProgressId order by t.dtTrDate desc")
	List<TransactionMasterDomain> getProceedingDetails(@Param("monthlyProgressId") int numMonthlyProgressId);

	
	@Query("from TransactionMasterDomain t join fetch t.employeeMasterDomain where t.customId=:customId and t.workflowMasterDomain.numWorkflowId=:workflowId and t.numIsValid=1")
	List<TransactionMasterDomain> getLatestTransactionDetails(@Param("customId") int customId,@Param("workflowId") Long workflowId);

	@Transactional
    @Modifying
	@Query(nativeQuery=true,value="update pms_transaction_master set num_isvalid=0 where num_custom_id=:num_custom_id and num_workflow_type_id_fk=:num_workflow_type_id")
	void updateAllRowsWithNumIsValidZero(@Param("num_custom_id") int numCustomId, @Param("num_workflow_type_id") long workflowId);
	
	@Query(value="select ptm.dt_tr_date, ptm.str_remarks,ptm.num_action_id_fk, pem.str_emp_name, pam.str_name , pam.str_action_performed from pms_transaction_master ptm,pms_employee_master pem, pms_action_master pam where ptm.num_isvalid  in (0,1) and ptm.num_emp_id_fk = pem.emp_id and ptm.num_action_id_fk = pam.num_action_id and ptm.num_workflow_type_id_fk =:workflowId and num_custom_id=:customId order by ptm.dt_tr_date desc", nativeQuery= true)
	List<Object[]> getTransactionDetails(@Param("customId")int customId,@Param("workflowId") int workflowId);
	
	//Bhaveshh(18-03-24) new method get transaction details OF ONGOING
		@Query(value="select p.num_custom_id from pms_transaction_master p where p.num_isvalid=1 and p.dt_tr_date between :startRange and :endRange and p.num_action_id_fk = 12 and p.num_role_id_fk = 9 and p.num_workflow_type_id_fk = 3 and CAST(p.num_custom_id AS TEXT) IN :projectIdList", nativeQuery= true)
		List<Object[]> getOngoingProjectCount(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("projectIdList") List<String> projectIdList);
		
	//Bhaveshh(18-03-24) new method get transaction details OF PROJECT RECIEVED
	@Query(value="select  p.num_custom_id from pms_transaction_master p where p.dt_tr_date between :startRange and :endRange and p.num_action_id_fk = 13 and p.num_role_id_fk = 3 and p.num_workflow_type_id_fk = 3 and CAST(p.num_custom_id AS TEXT) IN :projectIdLists", nativeQuery= true)
	List<Object[]> getProjectRecievedCount(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("projectIdLists") List<String> projectIdList);
				
	/*
	 * //Bhaveshh(18-03-24) new method get transaction details OF closure initiated
	 * 
	 * @Query(
	 * value="select distinct p.num_custom_id from pms_transaction_master p where p.dt_tr_date between :startRange and :endRange and  (p.num_action_id_fk = 26 OR p.num_action_id_fk = 15) and p.num_role_id_fk = 3 and p.num_workflow_type_id_fk = 4  and CAST(p.num_custom_id AS TEXT) IN :projectIdList"
	 * , nativeQuery= true) List<Object[]>
	 * getProjectClosureInitiatedCount(@Param("startRange")Date
	 * startRange,@Param("endRange") Date endRange, @Param("projectIdList")
	 * List<String> projectIdList);
	 */
	
	//Bhaveshh(18-03-24) new method get transaction details OF closure initiated project whose date is not elapsed
		@Query(value="SELECT P .num_project_id,P .str_project_name FROM pms_project_master P, \r\n"
				+ "pms_project_type_master bs,trans_application b,application_project C,pms_client_master cm,\r\n"
				+ "pms_transaction_master tm, pms_group_master G WHERE P .num_isvalid = 1 and p.dt_project_end > CURRENT_DATE  AND P.num_project_id=tm.num_custom_id \r\n"
				+ "and tm.num_workflow_type_id_fk=4  AND b.num_application_id = C .application_id AND \r\n"
				+ "C .project_id = P .num_project_id AND C .application_id = b.num_application_id AND \r\n"
				+ "b.num_project_type_fk = bs.num_project_type_id AND b.client_id_fk = cm.client_id and \r\n"
				+ "tm.num_isvalid=1 and b.num_group_id_fk=G.group_id AND P .str_project_status ='Ongoing'\r\n"
				+ "and b.num_group_id_fk in (:groupId) and P .num_project_id in (select distinct p.num_custom_id from pms_transaction_master p where p.dt_tr_date between :startRange and :endRange  and  (p.num_action_id_fk = 26 OR p.num_action_id_fk = 15) and p.num_role_id_fk = 3 and p.num_workflow_type_id_fk = 4  and CAST(p.num_custom_id AS TEXT) IN :projectIdList \r\n"
				+ "AND p.num_custom_id NOT IN ( \r\n"
				+ "	SELECT distinct t.num_custom_id \r\n"
				+ "        FROM pms_transaction_master t\r\n"
				+ "        WHERE t.dt_tr_date < :startRange and  (t.num_action_id_fk = 26 OR t.num_action_id_fk = 15) and t.num_role_id_fk = 3 and t.num_workflow_type_id_fk = 4))", nativeQuery= true)
		List<Object[]> getProjectClosureInitiatedCount(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("groupId") int groupId, @Param("projectIdList") List<String> projectIdList);

		
		//Bhaveshh(28-05-24) new method closed project on transaction date 
				@Query(value="select p.num_custom_id from pms_transaction_master p where p.num_isvalid=1 and p.dt_tr_date between :startRange and :endRange and p.num_action_id_fk = 23 and p.num_role_id_fk = 7 and p.num_workflow_type_id_fk = 4 and CAST(p.num_custom_id AS TEXT) IN :projectIdList", nativeQuery= true)
				List<Object[]> completedProjectCurrentWeek(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("projectIdList") List<String> projectIdList);
				
				
				//Bhaveshh(28-05-24) Technically closed project on transaction date 
				@Query(value="SELECT p.num_custom_id, p.dt_tr_date \r\n"
						+ "FROM ( \r\n"
						+ "    SELECT DISTINCT p.num_custom_id, MIN(p.dt_tr_date) AS dt_tr_date \r\n"
						+ "    FROM pms_transaction_master p,application_project ap,trans_application ta \r\n"
						+ "    WHERE p.dt_tr_date BETWEEN :startRange AND :endRange \r\n"
						+ "      AND p.num_action_id_fk = 18 \r\n"
						+ "      AND p.num_role_id_fk = 9 \r\n"
						+ "      AND p.num_workflow_type_id_fk = 4 AND p.num_custom_id=ap.project_id and ap.application_id=ta.num_application_id \r\n"
						+ "	and ta.num_group_id_fk=:assinedgroup \r\n"
						+ "    GROUP BY p.num_custom_id \r\n"
						+ ") p \r\n"
						+ "ORDER BY p.dt_tr_date ASC", nativeQuery= true)
				List<Object[]> technicallyClosedProjectCurrentWeek(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("assinedgroup") int assinedgroup);
				
		
				//Bhaveshh(28-05-24)Financially pending  project on transaction date 
				@Query(value="SELECT p.num_custom_id, p.dt_tr_date \r\n"
						+ "FROM ( \r\n"
						+ "    SELECT DISTINCT p.num_custom_id, MIN(p.dt_tr_date) AS dt_tr_date \r\n"
						+ "    FROM pms_transaction_master p,application_project ap,trans_application ta \r\n"
						+ "    WHERE p.dt_tr_date BETWEEN :startRange AND :endRange \r\n"
						+ "      AND (p.num_action_id_fk = 18 OR p.num_action_id_fk = 29) \r\n"
						+ "      AND (p.num_role_id_fk = 9 OR p.num_role_id_fk = 7)  \r\n"
						+ "      AND p.num_workflow_type_id_fk = 4 AND p.num_custom_id=ap.project_id and ap.application_id=ta.num_application_id \r\n"
						+ "	and ta.num_group_id_fk=:assinedgroup and p.num_custom_id not in \r\n"
						+ "(select tm .num_custom_id from pms_transaction_master tm  where tm.num_isvalid=1 and tm.num_action_id_fk = 23 and tm.num_role_id_fk = 7 and tm.num_workflow_type_id_fk = 4 ) \r\n"
						+ "    GROUP BY p.num_custom_id \r\n"
						+ ") p \r\n"
						+ "ORDER BY p.dt_tr_date DESC", nativeQuery= true)
				List<Object[]> financialPendingProjectCurrentWeek(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("assinedgroup") int assinedgroup);
				
				/*
				 * //Bhaveshh(28-05-24)proposal submitted weekly on transaction date
				 * 
				 * @Query(
				 * value="SELECT p.proposal_id, p.dt_tr_date FROM ( SELECT DISTINCT p.proposal_id, MIN(p.dt_tr_date) AS dt_tr_date FROM pms_log.pms_proposal_log p,pms.application_proposal ap,pms.trans_application ta \r\n"
				 * +
				 * "WHERE p.dt_tr_date BETWEEN :startRange and :endRange and p .num_isvalid=1 and p .str_status='SUB' \r\n"
				 * +
				 * "AND p.proposal_id=ap.proposal_id and ap.application_id=ta.num_application_id and ta.num_group_id_fk=:assinedgroup and p.proposal_id in (select n .proposal_id from pms.pms_proposal_master n  where n.num_isvalid=1) GROUP BY p.proposal_id ) p \r\n"
				 * + "ORDER BY p.dt_tr_date ASC", nativeQuery= true) List<Object[]>
				 * getProposalCurrentWeek(@Param("startRange")Date startRange,@Param("endRange")
				 * Date endRange, @Param("assinedgroup") int assinedgroup);
				 */
				
				
				//Bhaveshh(28-05-24)proposal submitted weekly  on transaction date 
				@Query(value="select n .proposal_id, n.dt_tr_date from pms.pms_proposal_master n,pms.application_proposal app,pms.trans_application tap  where  n.dt_tr_date BETWEEN :startRange and :endRange and n.num_isvalid=1 and n.str_status='SUB' \r\n"
						+ "AND n.proposal_id=app.proposal_id and app.application_id=tap.num_application_id and tap.num_group_id_fk=:assinedgroup \r\n"
						+ " and n .proposal_id not in (select t .proposal_id  from pms_log.pms_proposal_master_log t where t.num_isvalid=1) UNION \r\n"
						+ "SELECT p.proposal_id, p.dt_tr_date FROM ( SELECT DISTINCT p.proposal_id, MIN(p.dt_tr_date) AS dt_tr_date FROM pms_log.pms_proposal_master_log p,pms.application_proposal ap,pms.trans_application ta \r\n"
						+ "WHERE p.dt_tr_date BETWEEN :startRange and :endRange and p .num_isvalid=1 and p .str_status='SUB' \r\n"
						+ "AND p.proposal_id=ap.proposal_id and ap.application_id=ta.num_application_id and ta.num_group_id_fk=:assinedgroup and p.proposal_id in (select j .proposal_id from pms.pms_proposal_master j  where j.num_isvalid=1) GROUP BY p.proposal_id ) p \r\n"
						+ "ORDER BY dt_tr_date ASC", nativeQuery= true)
				List<Object[]> getProposalCurrentWeek(@Param("startRange")Date startRange,@Param("endRange") Date endRange, @Param("assinedgroup") int assinedgroup);
		

}
