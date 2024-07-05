package in.pms.transaction.model;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyProgressModel extends TransactionInfoDomain{
	private int numId;
	private String encNumId;
	private int numSubmissionInitiatedBy;
	private String strCurrentStatus;
	private int numCurrentCopyWith;
	private ProjectMasterDomain projectMasterDomain;
	private int month;
	private int year;
	private String strRemarks;
	private GroupMasterDomain groupMaster;
	private String submissionStatus;
	private List<MonthlyProgressDetails> monthlyProgress;
	private String encProjectId;
	private long projectId;
	private long groupId;
	private int numParentCateory;
	private long monthlyProgressId; 
	private long numCateoryId;
	private int numCategorySequence;
	private String strCategoryName;
	
	private String encGroupId;
	
	private String encProgressDetailsId;
	private String encProgressDetailsIds;
	private String encCategoryId;
	
	
	List objects = new ArrayList();
	
	private String strProjectName;
	private String strProjectReferenceNo;
	
	private String strMonth;
	private String strGroupName;
	private String transactionDate;
	private int previewFlag;
	private String strActionName;
}