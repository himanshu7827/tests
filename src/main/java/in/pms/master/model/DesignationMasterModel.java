package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class DesignationMasterModel {

	private long numId;
	private boolean valid;
	int numDesignationId;
	String strDesignationName;
	String strDesription;
	String strDesignationShortCode;
	private String encOrganisationId;
	private String idCheck;
	private int isGroupSpecific;
	private int isOrganisationSpecific;
	private int isThirdPartySpecific;
    private int hierarchy; 
    private String designationDetail;
    private int numProjectSpecific;
    
    private long count;
	
}
