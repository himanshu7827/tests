package in.pms.master.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalMasterModel {	

	private long numId;
	private long[] numIds;
	private long workshopId;
	private long roleId;
	private long actionId;
	private String actionName;
	private String roleName;
	private String workshopType;
	private int[] firstPageAction;
	private int[] secondPageAction;
	private int transactionImpact;
	private String statusTobeUpdated;
	private int copyTobeCreated;
	private long nextRoleId;
	private long numApprovalId;
	private long numRoleActionId;
}
