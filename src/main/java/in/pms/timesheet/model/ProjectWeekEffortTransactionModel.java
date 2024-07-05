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
@Table(name = "pms_timesheet_project_week_effort_trn")
public class ProjectWeekEffortTransactionModel implements Serializable{
	@EmbeddedId
    private ProjectWeekEffortTransactionModelId id;
	
	@Column(name="str_project_effort")
	private String totalEffortProject;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	@Column(name="num_isvalid")
	private Integer isValid;
	
	@Column(name="num_misc_other_act_id")
	private Long miscOtherActivityId;

	public ProjectWeekEffortTransactionModelId getId() {
		return id;
	}

	public void setId(ProjectWeekEffortTransactionModelId id) {
		this.id = id;
	}

	public String getTotalEffortProject() {
		return totalEffortProject;
	}

	public void setTotalEffortProject(String totalEffortProject) {
		this.totalEffortProject = totalEffortProject;
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

	public Long getMiscOtherActivityId() {
		return miscOtherActivityId;
	}

	public void setMiscOtherActivityId(Long miscOtherActivityId) {
		this.miscOtherActivityId = miscOtherActivityId;
	}

	@Override
	public String toString() {
		return "ProjectWeekEffortTransactionModel [id=" + id + ", totalEffortProject=" + totalEffortProject
				+ ", transactionTime=" + transactionTime + ", isValid=" + isValid + ", miscOtherActivityId="
				+ miscOtherActivityId + "]";
	}

	
}
