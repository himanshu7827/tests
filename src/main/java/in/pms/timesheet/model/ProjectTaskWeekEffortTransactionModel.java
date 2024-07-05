package in.pms.timesheet.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "pms_timesheet_project_task_week_effort_trn")
public class ProjectTaskWeekEffortTransactionModel implements Serializable{
	
	@EmbeddedId
    private ProjectTaskWeekEffortTransactionModelId id;
	
	@Column(name="str_project_sub_task_desc_effort")
	private String totalEffortProjectSubTaskDesc;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	@Column(name="num_isvalid")
	private Integer isValid;

	public ProjectTaskWeekEffortTransactionModelId getId() {
		return id;
	}

	public void setId(ProjectTaskWeekEffortTransactionModelId id) {
		this.id = id;
	}
	
	public String getTotalEffortProjectSubTaskDesc() {
		return totalEffortProjectSubTaskDesc;
	}

	public void setTotalEffortProjectSubTaskDesc(String totalEffortProjectSubTaskDesc) {
		this.totalEffortProjectSubTaskDesc = totalEffortProjectSubTaskDesc;
	}

	public LocalDate getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDate transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "ProjectTaskWeekEffortTransactionModel [id=" + id + ", totalEffortProjectSubTaskDesc="
				+ totalEffortProjectSubTaskDesc + ", transactionTime=" + transactionTime + ", isValid=" + isValid + "]";
	}

	
}
