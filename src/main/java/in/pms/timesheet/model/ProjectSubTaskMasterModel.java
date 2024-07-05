package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_timesheet_project_sub_task_master")
public class ProjectSubTaskMasterModel {

	@Id
	@Column(name="num_project_sub_task_id",length=10)	
	private long projectSubTaskId;
	
	@Column(name="num_project_task_id",length=10)	
	private long projectTaskId;
	
	@Column(name="str_project_sub_task_name" , length=150)
	private String projectSubTaskName;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private Date transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;

	public long getProjectSubTaskId() {
		return projectSubTaskId;
	}

	public void setProjectSubTaskId(long projectSubTaskId) {
		this.projectSubTaskId = projectSubTaskId;
	}

	public long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(long projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getProjectSubTaskName() {
		return projectSubTaskName;
	}

	public void setProjectSubTaskName(String projectSubTaskName) {
		this.projectSubTaskName = projectSubTaskName;
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
		return "ProjectSubTaskMasterModel [projectSubTaskId=" + projectSubTaskId + ", projectTaskId=" + projectTaskId
				+ ", projectSubTaskName=" + projectSubTaskName + ", isValid=" + isValid + ", transactionTime="
				+ transactionTime + ", transactionUserId=" + transactionUserId + "]";
	}
	
	
}
