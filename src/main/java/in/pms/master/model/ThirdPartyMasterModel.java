package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ThirdPartyMasterModel {
	private long numId;	
	private long[] numIds;
	private String encStrId;
	private String agencyName;
	private String agencyAddress;
	private String  contactPerson;
	private String contactNumber;
	private String mobileNumber;
	private String startDate;
	private String endDate;
	private boolean valid;
}
