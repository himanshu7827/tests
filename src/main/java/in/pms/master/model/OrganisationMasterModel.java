package in.pms.master.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrganisationMasterModel {
	
	private long numId;
	private String encOrganisationId;
	private int organisationId;
	private String organisationName;
	private String organisationShortName;
	private String contactNumber;
	private String organisationAddress;
	private long[] numIds;
	private boolean valid;
	private List<String> thrustAreaData;
	private String thrustArea;
	private String thrustAreaIds;
	private long parentOrganisationId;
	private String parentOrganisationName;
	private String strCode;
	
}
