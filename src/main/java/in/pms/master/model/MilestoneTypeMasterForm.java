package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MilestoneTypeMasterForm {
	
	private long numId;	
	private String strMilestoneTypeName;
	private boolean valid;
    private long[] numIds;
	
	}
