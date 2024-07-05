package in.pms.transaction.domain;

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

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.EmployeeMasterDomain;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_manpower_utilization_log",schema="pms_log")
@Table(name="pms_manpower_utilization",schema="pms")
public class ManpowerUtilization extends TransactionInfoDomain implements Serializable{


	private static final long serialVersionUID = 767008283346851081L;

	@Id
	@Column(name="num_manpower_utilization_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="pms_manpower_utilization")
	@TableGenerator(name="pms_manpower_utilization", initialValue=1, allocationSize=1)	
	private long numId;
	@ManyToOne(optional = false)
	@JoinColumn(name="num_employee_id_fk")
	private EmployeeMasterDomain employeeMasterDomain;
	@Column(name="num_month")
	private int month;
	@Column(name="num_year")
	private int year;
	@Column(name="num_total_Salary_By_System")
	private double totalSalaryBySystem;
	@Column(name="num_total_Salary_By_Authority")
	private double totalSalaryByAuthority;
	@Column(name="num_total_Salary_Cost")
	private double totalSalaryCost;
	
	@Column(name="str_submission_status")
	private String submissionStatus;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="manpowerUtilization",fetch=FetchType.LAZY) 
	private List<ManpowerUtilizationDetails> utilizationDetails;
	
	
}
