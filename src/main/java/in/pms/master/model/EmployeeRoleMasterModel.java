package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString

public class EmployeeRoleMasterModel {

	private long numId;
	private String encId;
	private boolean valid;
	
	private String strEmp;
	private String encEmployeeId;
	private long numEmpId;
	private String encRoleId;
	private int numRoleId;
	private int numGroupId;
	private int numOrganisationId;
/*	private int numApplicationId;*/
	private int numProjectId;	
	private String encProjectId;
	private String strEndDate;
	private String strStartDate;
	
	private String strEmpName;
	private String strRoleName;
	private String strGroupName;
	private String strOrganisationName;
	private String strApplicationName;
	private String strProjectName;
	
	private String encEmpId;
	private String idCheck;
	private long roleId;	
	String teamDetails;	
	private int primaryProject;	
	private String designation;
	private int activityCheckedValue;
	private long numPlEmpId;
	private int  numManReqId;
	private String strManReqDetails ;
	private Integer manpowerRequirementId;	
	private String duration;
	private String strGroupShortName;
	private String involvement;
	private String strEmploymentStatus;
	private int numDeputedAt;
	/*-----  Add variable for store projectRef number [ 21-05-2024 ] ----------*/
	private String projectRefNo;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numProjectId;
		result = prime * result
				+ ((strProjectName == null) ? 0 : strProjectName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeRoleMasterModel other = (EmployeeRoleMasterModel) obj;
		if (numProjectId != other.numProjectId)
			return false;
		if (strProjectName == null) {
			if (other.strProjectName != null)
				return false;
		} else if (!strProjectName.equals(other.strProjectName))
			return false;
		return true;
	}
	
	
}
