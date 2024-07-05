package in.pms.transaction.domain;

import java.io.Serializable;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_manpower_util_dtl_log",schema="pms_log")
@Table(name="pms_manpower_utilization_dtl",schema="pms")
public class ManpowerUtilizationDetails  extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -969836155808546788L;
	
	@Id
	@Column(name="num_utilization_dtl_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="pms_manpower_utilization")
	@TableGenerator(name="pms_manpower_utilization", initialValue=1, allocationSize=1)	
	private long numId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;
	@Column(name="num_utilization")
	private float utilization;
	@Column(name="num_salary_in_project")	
	private double salaryInProject;
	
	
	@ManyToOne(cascade=CascadeType.ALL)	
	@JoinColumn(name="num_utilization_id_fk")
		private ManpowerUtilization manpowerUtilization;	

}
