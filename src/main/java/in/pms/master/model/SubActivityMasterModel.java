package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubActivityMasterModel {
	
	private long numId;
	private boolean valid;
	
	private long numActivityId;
	private long numSubActivityId;
	private String strSubActivityName;
	
	private String encOrganisationId;
	private String idCheck;
	private String checkbox;
}
