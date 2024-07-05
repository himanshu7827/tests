package in.pms.transaction.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class MiscDataModel {

	
	private BigInteger submittedProposalCount;
	private int groupId;
	private String encGroupId;
	private long totalCost;
	private BigInteger closedProposalCout;
	private long projectTotalCost;
	private BigInteger projectReceviedCount;
	private BigInteger joinEmpCout;
	private BigInteger resignEmpCount;
	private BigInteger incomeCount;
	private BigInteger employeeCount;
	private String income;
	private String strProjectTotalCost;
	private String strTotalCost;
	private BigInteger totalProjects;
	private String totalOutlayOfProject;
	private String groupName;
}
