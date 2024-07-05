package in.pms.master.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParentOrganisationMasterModel {
	
	private long numId;
	private String encOrganisationId;
	private int organisationId;
	private String organisationName;
	private String organisationShortName;
	private String contactNumber;
	private String organisationAddress;
	private boolean valid;	
}
