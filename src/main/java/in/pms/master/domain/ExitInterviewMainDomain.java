package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

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
@AuditTable(value="pms_interview_exit_main_mst_log",schema="pms_log")
@Table(name="pms_interview_exit_main_mst")
public class ExitInterviewMainDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_interview_exit_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="interviewExitMaster")
	@TableGenerator(name="interviewExitMaster", initialValue=1, allocationSize=1)
	private long numId;
	
	@ManyToOne(optional = false)
   	@JoinColumn(name="employee_id")
    private EmployeeMasterDomain employeeMasterDomain;
	
	@Column(name="str_fla_email",length=50)
	private String strFlaEmail;
	
	@Column(name="str_sla_email",length=50)
	private String strSlaEmail;
	
	@Column(name="str_remarks_employee",length=100)
	private String strEmployeeRemarks;
	
	@Column(name="str_remarks_fla",length=100)
	private String strFlaRemarks;
	
	@Column(name="str_remarks_sla",length=100)
	private String strSlaRemarks;
	
	@Column(name="str_remarks_hr",length=100)
	private String strHrRemarks;
	
	@Column(name="str_status",length=50)
	private String strStatus;
	

	@OneToMany(fetch=FetchType.LAZY,mappedBy="exitInterviewMainDomain",cascade=CascadeType.ALL)   
    public List<ExitInterviewDomain> exitInterviewChildDomain;
	
	

		
	}
