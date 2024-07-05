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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="employee_salary_mst_log",schema="pms_log")
@Table(name="pms_employee_salary_mst")
public class EmployeeSalaryDomain extends TransactionInfoDomain implements
		Serializable,Comparable<EmployeeSalaryDomain> {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="num_employee_id")
	private long employeeId;
	@Column(name="num_salary")
	private double salary;
	@Column(name="dt_start")
	private Date startDate;
	@Column(name="dt_end")
	private Date endDate;
	
	@Override
	public int compareTo(EmployeeSalaryDomain o) {
		 return this.getStartDate().compareTo(o.getStartDate());		
	}
	
	
}
