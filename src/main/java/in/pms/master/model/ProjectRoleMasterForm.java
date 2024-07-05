package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRoleMasterForm {	
	
	private long numId;
	private String roleName;
	private int hierarchy;
	private long[] numIds;
}
