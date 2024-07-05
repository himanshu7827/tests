package in.pms.timesheet.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pms_timesheet_emp_week_approval")
public class EmpWeekApprovalModel implements Serializable{
	
	@Id
	@OneToOne(optional = false)
   	@JoinColumn(name="num_transaction_id")
   	private ParentTransactionModel parentTransactionModel;
	
	@Column(name = "num_approved_by_fla")
    private Integer approvedByFLA;
	
	@Column(name = "num_approved_by_sla")
    private Integer approvedBySLA;
	
	@Column(name="dt_approval_date")
	private LocalDate approvalDate;
	
	@Column(name="dt_rejection_date")
	private LocalDate rejectionDate;
	
	@Column(name="str_approval_status")
	private String approvalStatus;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	@Column(name="num_isvalid")
	private Integer isValid;
	

	public Integer getApprovedByFLA() {
		return approvedByFLA;
	}

	public void setApprovedByFLA(Integer approvedByFLA) {
		this.approvedByFLA = approvedByFLA;
	}

	public Integer getApprovedBySLA() {
		return approvedBySLA;
	}

	public void setApprovedBySLA(Integer approvedBySLA) {
		this.approvedBySLA = approvedBySLA;
	}

	public LocalDate getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDate approvalDate) {
		this.approvalDate = approvalDate;
	}

	public LocalDate getRejectionDate() {
		return rejectionDate;
	}

	public void setRejectionDate(LocalDate rejectionDate) {
		this.rejectionDate = rejectionDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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

	public ParentTransactionModel getParentTransactionModel() {
		return parentTransactionModel;
	}

	public void setParentTransactionModel(ParentTransactionModel parentTransactionModel) {
		this.parentTransactionModel = parentTransactionModel;
	}

	@Override
	public String toString() {
		return "EmpWeekApproval [approvedByFLA=" + approvedByFLA
				+ ", approvedBySLA=" + approvedBySLA + ", approvalDate=" + approvalDate + ", rejectionDate="
				+ rejectionDate + ", approvalStatus=" + approvalStatus + ", transactionTime=" + transactionTime
				+ ", isValid=" + isValid + ", parentTransactionModel=" + parentTransactionModel + "]";
	}
	
	
}
