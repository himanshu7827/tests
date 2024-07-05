package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_timesheet_project_sub_task_desc_master")
public class ProjectSubTaskDescMasterModel {

	@Id
	@Column(name="num_project_sub_task_desc_id",length=10)	
	private long projectSubTaskDescId;
	
	@Column(name="num_project_sub_task_id",length=10)	
	private long projectSubTaskId;
	
	@Column(name="str_project_sub_task_description" , length=150)
	private String projectSubTaskDescName;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private Date transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;

	public long getProjectSubTaskDescId() {
		return projectSubTaskDescId;
	}

	public void setProjectSubTaskDescId(long projectSubTaskDescId) {
		this.projectSubTaskDescId = projectSubTaskDescId;
	}

	public long getProjectSubTaskId() {
		return projectSubTaskId;
	}

	public void setProjectSubTaskId(long projectSubTaskId) {
		this.projectSubTaskId = projectSubTaskId;
	}

	public String getProjectSubTaskDescName() {
		return projectSubTaskDescName;
	}

	public void setProjectSubTaskDescName(String projectSubTaskDescName) {
		this.projectSubTaskDescName = projectSubTaskDescName;
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
		return "ProjectSubTaskDescMasterModel [projectSubTaskDescId=" + projectSubTaskDescId + ", projectSubTaskId="
				+ projectSubTaskId + ", projectSubTaskDescName=" + projectSubTaskDescName + ", isValid=" + isValid
				+ ", transactionTime=" + transactionTime + ", transactionUserId=" + transactionUserId + "]";
	}
	
	
}
