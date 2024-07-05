package in.pms.global.domain;

import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.transaction.domain.MonthlyProgressDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_transaction_master")
public class TransactionMasterDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7568952904201295504L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="transactionMaster")
	@TableGenerator(name="transactionMaster", initialValue=1, allocationSize=1)
	@Column(name="num_transaction_id")	
	private long numTransactionId;
	@Column(name="str_remarks",length=300)
	private String strRemarks;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_action_id_fk")
	private ActionMasterDomain actionMasterDomain;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_role_id_fk")
	private RoleMasterDomain roleMasterDomain;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_monthly_progress_id_fk")
	private MonthlyProgressDomain monthlyProgressDomain;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_workflow_type_id_fk")
	private WorkflowMasterDomain workflowMasterDomain;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_emp_id_fk")
	private EmployeeMasterDomain employeeMasterDomain;
	
	@Column(name="num_custom_id", columnDefinition="int default 0")
	/**
	 * This will hold PK of Resource on which Activity is performed
	 */
	private int customId;
}
