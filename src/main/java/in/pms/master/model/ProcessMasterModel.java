package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessMasterModel {	
	private long numProcessId;
	private String strProcessName;
	private String strProcessPath;	
	private String strProcessDescription;
	private String strProcessEcode;
	private boolean valid;
	private long[] numIds;
}






