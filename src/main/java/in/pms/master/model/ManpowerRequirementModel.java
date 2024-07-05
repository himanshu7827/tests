package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManpowerRequirementModel {
	
	private long numId;	
	private String startDate;
	private String endDate;
	private int involvement;
	private int count;
	private long projectId;
	private String projectName;
	private int designationId;
	private String designationName;
	private boolean valid;
    private long[] numIds;
    private String strDescription;	
    private String purpose;	
	private String ratePerManMonth;
	private int numProjectRoles;
	private String strRolesName;
	private String actualRatePerManMonth;
	private int deputedAt;
	private String requiredType;
}
