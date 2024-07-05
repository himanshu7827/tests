package in.pms.global.model;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionMasterModel extends TransactionInfoDomain{
	private String strRemarks;
	private long numActionId;
	private long numRoleId;
	private int numMonthlyProgressId;
	private long numWorkflowId;

	private int customId;
}
