package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "pms_approved_job_detail")
public class ApprovedJobDomain extends TransactionInfoDomain{

	@Id
	@Column(name="num_job_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="pms_approved_job_detail")
	@TableGenerator(name="pms_approved_job_detail", initialValue=1, allocationSize=1)	
	private int numId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_group_id_fk")
	private GroupMasterDomain groupMasterDomain;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_designation_id_fk")
	private DesignationMaster designationMaster;
	
	@Column(name="str_job_code",length=100)
	private String jobCode;
	
	@Column(name="num_duration_in_months")
	private int durationInMonths;
	
	@Column(name="str_status",length=20)
	private String status;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_approved_on")
	private Date approvedOn;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_created_on")	
	private Date createdOn;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_closed_on")
	private Date closedOn;
	
	@Column(name="is_Approved")
	private boolean approved;
	
	@OneToMany(mappedBy = "approvedJob", cascade = CascadeType.ALL)
    private List<EmployeeApprovedJobMapping> employeeApprovedJobs;
}
