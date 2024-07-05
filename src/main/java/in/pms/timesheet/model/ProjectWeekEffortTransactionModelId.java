package in.pms.timesheet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectWeekEffortTransactionModelId implements Serializable{
	
	@ManyToOne(optional = false)
   	@JoinColumn(name="num_transaction_id")
   	private ParentTransactionModel parentTransactionModel;
	
	@Column(name = "num_project_id")
    private long projectId;
	
	@Column(name = "str_status")
    private String status;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ParentTransactionModel getParentTransactionModel() {
		return parentTransactionModel;
	}

	public void setParentTransactionModel(ParentTransactionModel parentTransactionModel) {
		this.parentTransactionModel = parentTransactionModel;
	}

	@Override
	public String toString() {
		return "projectId=" + projectId
				+ ", status=" + status + ", parentTransactionModel=" + parentTransactionModel + "]";
	}

	
}
