package in.pms.timesheet.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "pms_timesheet_week_effort_trn")
public class WeekEffortTransactionModel implements Serializable{
	
	@Id
	@Column(name = "num_transaction_id")
    private Integer transactionId;
	
	@Column(name="str_total_effort_week")
	private String totalEffortWeek;
	
	@Column(name="num_isvalid")
	private Integer isValid;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	
	public String getTotalEffortWeek() {
		return totalEffortWeek;
	}

	public void setTotalEffortWeek(String totalEffortWeek) {
		this.totalEffortWeek = totalEffortWeek;
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

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	
	@Override
	public String toString() {
		return "WeekEffortTransactionModel [totalEffortWeek=" + totalEffortWeek
				+ ", isValid=" + isValid + ", transactionTime=" + transactionTime + ", transactionId="
				+ transactionId + "]";
	}
	

}
