package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollaborativeOrgDetailsModel {
	private long applicationId;
	
	private long numId;	
	private String encId;

	private String inputData;
	// below  added variable model data by varun 
	 String encNumId ;
	    String organisationName;
	    String organisationAddress;
	    String contactNumber;
	    String email;
	    String website ;
}


