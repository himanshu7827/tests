package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterModel {	
	private long numId;
	private String encRoleId;
	private String roleName;	
	private String roleShortName;
	private boolean valid;
	private long[] numIds;
	private String accessLevel;
	private int hierarchy;
	private String bgColor;
}
