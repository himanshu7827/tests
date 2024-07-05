package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;


@Entity

@Getter
@Setter

@Table(name="pms_monthly_progress_status",schema="pms")
public class MonthlyProgressDomain extends TransactionInfoDomain implements Serializable{


	private static final long serialVersionUID = 767008283346851081L;

	@Id
	@Column(name="num_progress_status_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="pms_monthly_progress_status")
	@TableGenerator(name="pms_monthly_progress_status", initialValue=1, allocationSize=1)	
	private int numId;
	
	@Column(name="num_submission_initiated_by")
	private int numSubmissionInitiatedBy;
	
	
	@Column(name="str_current_status",length=3)
	private String strCurrentStatus;
	
	@Column(name="num_current_copy_with")
	private int numCurrentCopyWith;
	
	
	@ManyToOne(optional = true)
	@JoinColumn(name="Num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;
	
	@Column(name="num_month")
	private int month;
	
	@Column(name="num_year")
	private int year;
	
	@Column(name="Str_remarks",length=300)
	private String strRemarks;
	
	
	@ManyToOne(optional = false)
	@JoinColumn(name="Num_Group_id_fk")
	private GroupMasterDomain groupMaster;
	

	@Column(name="str_submission_status")
	private String submissionStatus;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDomain",fetch=FetchType.LAZY) 
	private List<MonthlyProgressDetails> monthlyProgress;
	
	
}