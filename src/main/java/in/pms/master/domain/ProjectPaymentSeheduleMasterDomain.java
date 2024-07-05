package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_project_payment_schedule_log",schema="pms_log")
@Table(name="pms_project_payment_schedule_mst")
public class ProjectPaymentSeheduleMasterDomain extends TransactionInfoDomain implements Serializable  { /**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="num_project_payment_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="num_payment_sequence",length=50)
	private int numPaymentSequence;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_payment_due_date")
	private Date dtPaymentDueDate;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_amount",precision=19,scale=2)
	private Double numAmount;
	
	@Column(name="str_purpose", length=3000)
	private String strPurpose;
	
	@Column(name="str_remarks", length=3000)
	private String strRemarks;
	
	
	@Column(name="is_linked_with_milestone",length=2, columnDefinition = "int default 0")
	private int linkedWithMilestone;
	
	@Column(name="num_project_id",length=12)
	private long projectId;
	
	@Column(name="num_milestone_id",length=12)
	private long numMilestoneId;

	
	@Column(name="num_due_after_days",columnDefinition="int default 0")
	private int numDueAfterDays;

	@Column(name="num_due_after_months",columnDefinition="int default 0")
	private int numDueAfterMonths;
	
	}


