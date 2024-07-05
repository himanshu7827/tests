package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "pms_timesheet_project_task_master")
public class ProjectTaskMasterModel {
	@Id
	@Column(name="num_project_task_id",length=10)	
	private long projectTaskId;
	
	@Column(name="str_project_task_name" , length=150)
	private String projectTaskName;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private Date transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;
	
	@Column(name="str_project_task_desc",length=15)
	private String projectTaskDesc;

	public long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(long projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getProjectTaskName() {
		return projectTaskName;
	}

	public void setProjectTaskName(String projectTaskName) {
		this.projectTaskName = projectTaskName;
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

	public String getProjectTaskDesc() {
		return projectTaskDesc;
	}

	public void setProjectTaskDesc(String projectTaskDesc) {
		this.projectTaskDesc = projectTaskDesc;
	}

	@Override
	public String toString() {
		return "ProjectTaskMasterModel [projectTaskId=" + projectTaskId
				+ ", projectTaskName=" + projectTaskName + ", isValid="
				+ isValid + ", transactionTime=" + transactionTime
				+ ", transactionUserId=" + transactionUserId
				+ ", projectTaskDesc=" + projectTaskDesc + "]";
	}
	
	
}
