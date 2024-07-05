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
@Table(name = "pms_role_action_mapping")
public class RoleActionMappingDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2888888044524447031L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="roleActionMapping")
	@TableGenerator(name="roleActionMapping", initialValue=1, allocationSize=1)
	@Column(name="num_id",length=10)	
	private long numRoleActionMapId;
	@Column(name="str_status",length=50)
	private String strStatus;
	@Column(name="num_is_copy_create",length=10)	
	private int numIsCopyCreate;
	@Column(name="num_transaction_impact",length=10)	
	private int numTransactionImpact;
	@Column(name="is_remarks_req",columnDefinition = "int default -1")
	private int isRemarksReq;
	@Column(name="str_tool_tip",length=100)
	private String strToolTip;
	@Column(name="str_success_msg",length=300)
	private String strSuccessMsg;
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
