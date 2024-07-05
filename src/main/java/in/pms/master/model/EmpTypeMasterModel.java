package in.pms.master.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpTypeMasterModel {

	
	private long numId;
	private boolean valid;
	private int numStageId;
	private String strEmpTypeName;
	private String empShortName;
	private String encOrganisationId;
	private String idCheck;
	private String bgColor;
	private int hierarchy;

	
}
