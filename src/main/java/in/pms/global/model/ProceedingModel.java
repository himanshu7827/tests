package in.pms.global.model;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProceedingModel extends TransactionInfoDomain{
	private String strRemarks;
	private long numActionId;
	private long numRoleId;
	private int numMonthlyProgressId;
	private long numWorkflowId;
	
	private String groupName;
	private String strProjectName;
	private int month;
	private int year;
	private String roleName;
	private String strDateTime;
	private String strName;
	private String strAction;

}
