package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.BusinessTypeMaster;
import in.pms.master.domain.ModuleTypeMaster;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.master.domain.ProjectTypeMaster;

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
@AuditTable(value="pms_workflow_type_master_log",schema="pms_log")
@Table(name="pms_workflow_type_master",schema="pms")
public class WorkflowTypeMaster extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = 7573494684715563740L;

	@Id
	@Column(name="num_workflow_type_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="workflowTypeMaster")
	@TableGenerator(name="workflowTypeMaster", initialValue=1, allocationSize=1)	
	private int numId;

	@ManyToOne
	 @JoinColumn(name = "num_project_type_id_fk")
	 private ProjectTypeMaster projectTypeMaster;
	
	@ManyToOne
	 @JoinColumn(name = "num_business_type_id_fk")
	 private BusinessTypeMaster businessTypeMastertm;
	
	@ManyToOne
	 @JoinColumn(name = "num_project_category_id_fk")
	 private ProjectCategoryMaster projectCategoryMaster;
	
	@ManyToOne
	 @JoinColumn(name = "num_module_type_id_fk")
	 private ModuleTypeMaster moduleTypeMaster;
	
}
