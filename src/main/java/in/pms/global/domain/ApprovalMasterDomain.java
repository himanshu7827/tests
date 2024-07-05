package in.pms.global.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import in.pms.master.domain.RoleMasterDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_approval_master")
public class ApprovalMasterDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2207999427042832057L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="approvalMaster")
	@TableGenerator(name="approvalMaster", initialValue=1, allocationSize=1)
	@Column(name="num_id",length=10)	
	private long numApprovalId;
	@Column(name="num_next_role_id",length=10)	
	private long numNextRoleId;
	@Column(name="str_first_page_action_ids",length=250)
	private String strFirstPageActionIds;
	@Column(name="str_second_page_action_ids",length=250)
	private String strSecondPageActionIds;
	
	@ManyToOne
	@JoinColumn(name="num_action_id_fk")
	private ActionMasterDomain actionMasterDomain;
	
	@ManyToOne
	@JoinColumn(name="num_role_id_fk")
	private RoleMasterDomain roleMasterDomain;
	
	@ManyToOne
	@JoinColumn(name="num_workflow_type_id_fk")
	private WorkflowMasterDomain workflowMasterDomain;
	
	
	
}
