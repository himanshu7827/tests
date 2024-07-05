package in.pms.transaction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class DesignationForClientModel {
	
	private int numId;
	private boolean valid;
	private String encNumId;
	private String designationName;
	private String desription;
	private String designationShortCode;	
	
	private String cost;
	private int projectCategoryId;
	private int designationId;
	// new variables added by devesh on 30-04-24 to get deputed at value, fromDate and toDate of basecost
	private String categoryName;
	private int numDeputedAt; 
	private String fromDate;
	private String toDate;
	/*-------------- End --------------*/
	
}
