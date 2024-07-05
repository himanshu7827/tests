package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActivityMasterModel {

	private long numId;
	private boolean valid;

	private long numActivityId;
	private String strActivityName;
	private List<Long> numSubActivityId;
	
	private String encOrganisationId;
	private String idCheck;
	private String checkbox;
	
	private String subActivityNames;
	private String subActivityIds;
}






	


	

