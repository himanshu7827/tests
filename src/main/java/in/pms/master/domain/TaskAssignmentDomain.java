package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@AuditTable(value="pms_task_assignment_mst_log",schema="pms_log")
@Table(name="pms_task_assignment_mst")
public class TaskAssignmentDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_assignment_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="assigned_to")
	private int assignedTo;
	@Column(name="str_description",length=2000)
	private String description;
	@Column(name="num_document_id",nullable = false, columnDefinition = "int default 0")
	private long documentId;
	
	}
