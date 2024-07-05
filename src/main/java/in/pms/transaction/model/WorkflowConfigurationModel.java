package in.pms.transaction.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
 public class WorkflowConfigurationModel {	
	private long numId;
	private String encNumId;
	private int businessTypeId;
	private String businessTypeName;
	private int projectTypeId;
	private String projectTypeName;
	private int categoryId;
	private String categoryName;
	private long groupId;
	private String groupName;
	private long organisationId;
	private String organisationName;
	private float totalProposalCost;
	private int moduleTypeId;
	private String moduleTypeName;
	private int workflowTypeId;
	private String strWorkflowType;
	
	private List<CollaborativeOrgDetailsModel> collaborativeOrg = new ArrayList<CollaborativeOrgDetailsModel>();
	
	private boolean valid;
	private String errorMessage;
}
