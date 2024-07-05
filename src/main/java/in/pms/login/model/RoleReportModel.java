package in.pms.login.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class RoleReportModel {
	
	private long roleId;
	private String encStrId;
	private String roleName;	
	//private String selectedRoles;
	private List<Integer> selectedReport;

}
