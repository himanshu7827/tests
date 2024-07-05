package in.pms.master.model;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class GroupMasterModel {

	private long numId;
	private long organisationId;
	private String encOrganisationId;
	private String encGroupId;
	private String groupName;
	private String groupShortName;
	private String contactNumber;
	private String organisationAddress;
	private String groupAddress;
	private String organisationName;
	private String bgColor;
	private boolean valid;
	private String showProjects;
	private String strCode;
	
	private boolean proposalYearCheck; //- Store the value of proposal year check [12-04-2024]
}
