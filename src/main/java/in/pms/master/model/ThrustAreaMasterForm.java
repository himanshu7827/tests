package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThrustAreaMasterForm {
	
	private long numId;	
	private String strThrustAreaName;
	private boolean valid;
    private long[] numIds;
	
	}
