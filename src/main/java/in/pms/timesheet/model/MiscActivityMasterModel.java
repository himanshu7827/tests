package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_misc_activity_master")
public class MiscActivityMasterModel {
	
	@Id
	@Column(name="num_misc_act_id",length=10)	
	private long MiscActivityId;
	
	@Column(name="str_misc_activity_name" , length=150)
	private String MiscActivityName;
	
	@Column(name="num_isvalid" , length=150)
	private int isValid;
	
	@Column(name="dt_tr_date",length=15)
	private Date transactionTime;
	
	@Column(name="num_tr_user_id",length=15)
	private int transactionUserId;
	
	@Column(name="str_other_activity_desc",length=15)
	private String otherActivity;

	public long getMiscActivityId() {
		return MiscActivityId;
	}

	public void setMiscActivityId(long miscActivityId) {
		MiscActivityId = miscActivityId;
	}

	public String getMiscActivityName() {
		return MiscActivityName;
	}

	public void setMiscActivityName(String miscActivityName) {
		MiscActivityName = miscActivityName;
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

	public String getOtherActivity() {
		return otherActivity;
	}

	public void setOtherActivity(String otherActivity) {
		this.otherActivity = otherActivity;
	}

	@Override
	public String toString() {
		return "MiscActivityMasterModel [MiscActivityId=" + MiscActivityId
				+ ", MiscActivityName=" + MiscActivityName + ", isValid="
				+ isValid + ", transactionTime=" + transactionTime
				+ ", transactionUserId=" + transactionUserId
				+ ", otherActivity=" + otherActivity + "]";
	}
	
	
}
