package in.pms.transaction.model;

import lombok.Data;

@Data
public class ManpowerUtilizationDetailsModel {
	private long numId;	
	private long projectId;
	private float utilization;	
	private double salaryInProject;	
}
