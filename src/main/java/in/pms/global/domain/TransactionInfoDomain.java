package in.pms.global.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class TransactionInfoDomain {

	@Column(name="NUM_ISVALID",length=2)
    int numIsValid;
	
	@Column(name="DT_TR_DATE")
	Date dtTrDate;
	
	@Column(name="NUM_TR_USER_ID")
	int  numTrUserId;


	public Date getDtTrDate() {
		return dtTrDate;
	}

	public void setDtTrDate(Date dtTrDate) {
		this.dtTrDate = dtTrDate;
	}

	public int getNumTrUserId() {
		return numTrUserId;
	}

	public void setNumTrUserId(int numTrUserId) {
		this.numTrUserId = numTrUserId;
	}

	public int getNumIsValid() {
		return numIsValid;
	}

	public void setNumIsValid(int numIsValid) {
		this.numIsValid = numIsValid;
	}
	

	
}
