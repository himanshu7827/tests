package in.pms.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetHeadMasterForm {
	
	private long numId;	
	private String strBudgetHeadName;
	private String strDescription;	
	private boolean valid;
	private String shortCode;
	private long[] numIds;
	private int year;
	private float numAmount;
	private String strBudgetAmount;
	private long projectId;
	private String encProjectId;
	
	
}
