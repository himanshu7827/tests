package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.EmployeeMasterDomain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "pms_employee_approved_job_mapping")
public class EmployeeApprovedJobMapping extends TransactionInfoDomain{
	
	@Id
	@Column(name="num_job_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="approved_job_mapping")
	@TableGenerator(name="approved_job_mapping", initialValue=1, allocationSize=1)	
	private int numId;	
	
	@ManyToOne
	@JoinColumn(name = "num_employee_id_fk")
	private EmployeeMasterDomain employeeMasterDomain;		
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_start")	
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_end")
	private Date endDate;
	
	@Column(name="str_remark",length= 500)
	private String remark;

	@ManyToOne(optional = false)
	@JoinColumn(name = "num_approved_Job_id_fk")
	private ApprovedJobDomain approvedJob;
	
}
