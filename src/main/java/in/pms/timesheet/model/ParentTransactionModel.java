package in.pms.timesheet.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "pms_timesheet_parent_trn")
public class ParentTransactionModel implements Serializable{
	@Id
	@Column(name = "num_transaction_id")
    private Integer transactionId;
	
	@Column(name = "emp_id")
    private Integer empId;

    @Column(name = "str_month", length = 25)
    private String monthName;

    @Column(name = "str_week", length = 25)
    private String weekName;

    @Column(name = "str_year", length = 25)
    private String yearName;
    
    @Column(name="num_isvalid")
	private Integer isValid;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public LocalDate getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDate transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "TimesheetParentTransactionModel [transactionId=" + transactionId + ", empId=" + empId + ", monthName="
				+ monthName + ", weekName=" + weekName + ", yearName=" + yearName + ", isValid=" + isValid
				+ ", transactionTime=" + transactionTime + "]";
	}
	
	
	
}
