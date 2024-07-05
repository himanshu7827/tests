package in.pms.timesheet.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_misc_other_activity_master")
public class MiscOtherActivityMasterModel {
	@Id
	@Column(name="num_misc_other_act_id",length=10)	
	private long MiscOtherActivityId;
	
	@Column(name="str_misc_other_activity_desc" , length=150)
	private String MiscOtherActivityDesc;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private LocalDate transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;

	public long getMiscOtherActivityId() {
		return MiscOtherActivityId;
	}

	public void setMiscOtherActivityId(long miscOtherActivityId) {
		MiscOtherActivityId = miscOtherActivityId;
	}

	public String getMiscOtherActivityDesc() {
		return MiscOtherActivityDesc;
	}

	public void setMiscOtherActivityDesc(String miscOtherActivityDesc) {
		MiscOtherActivityDesc = miscOtherActivityDesc;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public LocalDate getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDate transactionTime) {
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
		return "MiscOtherActivityMasterModel [MiscOtherActivityId="
				+ MiscOtherActivityId + ", MiscOtherActivityDesc="
				+ MiscOtherActivityDesc + ", isValid=" + isValid
				+ ", transactionTime=" + transactionTime
				+ ", transactionUserId=" + transactionUserId + "]";
	}

	
	
}
