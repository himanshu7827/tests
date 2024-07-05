package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@AuditTable(value="pms_task_details_mst_log",schema="pms_log")
@Table(name="pms_task_details_mst")
public class TaskDetailsDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_task_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_task_name",length=250)
	private String taskName;
	@Column(name="str_task_description",length=2000)
	private String taskDescription;
	@Column(name="num_project_id")
	private long projectId;
	@Column(name="num_expected_time")
	private float expectedTime;
	@Column(name="str_priority",length=20)
	private String priority;
	@Column(name="str_task_status",length=20)
	private String taskStatus;
	@Column(name="num_milestone_id",nullable = false, columnDefinition = "int default 0")
	private long milestoneId;
	@Column(name="num_with_milestone")
	private int withMilestone;
	@Column(name="num_document_id",nullable = false, columnDefinition = "int default 0")
	private long documentId;
	/*@ManyToOne(optional = false)
    @JoinColumn(name = "num_assignment_id")
    private TaskAssignmentDomain taskAssignmentDomain;	*/
	@Column(name="milestone_activity_id",nullable = false, columnDefinition = "int default 0")
	private long activityId;
	}
