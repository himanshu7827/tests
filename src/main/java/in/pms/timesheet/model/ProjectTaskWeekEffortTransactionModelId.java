package in.pms.timesheet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectTaskWeekEffortTransactionModelId implements Serializable{
		@ManyToOne(optional = false)
	   	@JoinColumn(name="num_transaction_id")
	   	private ParentTransactionModel parentTransactionModel;
		
	 	@Column(name = "num_project_id")
	    private long projectId;
	    
	 	@Column(name = "num_project_task_id")
	    private long projectTaskId;
	 	 
	    @Column(name = "num_project_sub_task_desc_id")
	    private long projectSubTaskDescId;
	    

		public long getProjectId() {
			return projectId;
		}

		public void setProjectId(long projectId) {
			this.projectId = projectId;
		}
		
		public long getProjectTaskId() {
			return projectTaskId;
		}

		public void setProjectTaskId(long projectTaskId) {
			this.projectTaskId = projectTaskId;
		}

		public long getProjectSubTaskDescId() {
			return projectSubTaskDescId;
		}

		public void setProjectSubTaskDescId(long projectSubTaskDescId) {
			this.projectSubTaskDescId = projectSubTaskDescId;
		}

		public ParentTransactionModel getParentTransactionModel() {
			return parentTransactionModel;
		}

		public void setParentTransactionModel(ParentTransactionModel parentTransactionModel) {
			this.parentTransactionModel = parentTransactionModel;
		}

		@Override
		public String toString() {
			return "ProjectTaskWeekEffortTransactionModelId [parentTransactionModel=" + parentTransactionModel
					+ ", projectId=" + projectId + ", projectTaskId=" + projectTaskId + ", projectSubTaskDescId="
					+ projectSubTaskDescId + "]";
		}

		
}
