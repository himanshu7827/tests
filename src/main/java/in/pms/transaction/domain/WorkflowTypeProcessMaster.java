package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="workflow_type_process_master_log",schema="pms_log")
@Table(name="workflow_type_process_master")
public class WorkflowTypeProcessMaster extends TransactionInfoDomain implements Serializable {

	private static final long serialVersionUID = -5625421529285153860L;
	@Id
	@Column(name="num_process_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="workflow_type_process_master")
	@TableGenerator(name="workflow_type_process_master", initialValue=1, allocationSize=1)	
	private long numId;

	@Column(name="num_step_id")
	 private int stepId;
	
	@Column(name="str_controller_name")
	 private String controllerName;
	
	@Column(name="str_description")
	 private String description;
	
	@Column(name="str_ecode")
	 private String ecode;
	
	 @ManyToOne
	 @JoinColumn(name = "num_workflow_type_id_fk")
	 private WorkflowTypeMaster workflowTypeMaster;
	 
}
