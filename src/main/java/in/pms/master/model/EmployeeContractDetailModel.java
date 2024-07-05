package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeContractDetailModel {
	
	private long numId;	
	
	private boolean valid;
	private long[] numIds;
	private String contractStartDate;
	private String contractEndDate;
}
