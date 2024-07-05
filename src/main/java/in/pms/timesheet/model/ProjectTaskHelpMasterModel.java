package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_project_task_desc_help_master")
public class ProjectTaskHelpMasterModel {

	@Id
	@Column(name="num_project_task_desc_help_id",length=10)	
	private long projectTaskHelpId;
	
	@Column(name="num_project_task_id" , length=150)
	private long projectTaskId;
	
	@Column(name="str_project_task_desc_help")
	private String projectTaskDescHelp;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private Date transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;

	public long getProjectTaskHelpId() {
		return projectTaskHelpId;
	}

	public void setProjectTaskHelpId(long projectTaskHelpId) {
		this.projectTaskHelpId = projectTaskHelpId;
	}

	public long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(long projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getProjectTaskDescHelp() {
		return projectTaskDescHelp;
	}

	public void setProjectTaskDescHelp(String projectTaskDescHelp) {
		this.projectTaskDescHelp = projectTaskDescHelp;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public int getTransactionUserId() {
		return transactionUserId;
	}

	public void setTransactionUserId(int transactionUserId) {
		this.transactionUserId = transactionUserId;
	}

	@Override
	public String toString() {
		return "ProjectTaskHelpMasterModel [projectTaskHelpId="
				+ projectTaskHelpId + ", projectTaskId=" + projectTaskId
				+ ", projectTaskDescHelp=" + projectTaskDescHelp + ", isValid="
				+ isValid + ", transactionTime=" + transactionTime
				+ ", transactionUserId=" + transactionUserId + "]";
	}
	
	
}
