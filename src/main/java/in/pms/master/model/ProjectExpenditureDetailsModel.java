package in.pms.master.model;

import lombok.Data;
@Data

public class ProjectExpenditureDetailsModel {
	
	private long numId;
	private boolean valid;
	

	private long numProjectId;
	private long numBudgetHeadId;
	private long numExpenditureHeadId;
	private String dtExpenditureDate;
	private Double numExpenditureAmount;
	private String strExpenditureDescription;
	
	private String strProjectName;
	private String strBudgetHeadName;
	private String strExpenditureHeadName;

	private String encEmpId;
	private String idCheck;
	private String startDate;
	
	
	
}
