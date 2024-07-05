package in.pms.global.model;

import in.pms.transaction.model.CategoryMasterModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkflowModel {

	private long numLastActionId;
	private long numActionId;
	private long numTransactionId;
	private long numLastRoleId;
	private long numCurrentRoleId;
	private long numWorkflowId;
	private String encMonthlyProgressId;
    private String encWorkflowId;
    private String encPageId;
    private String strActionName;
    private String strEncActionId;
    private String strActionPerformed;
    private String strRemarks;
    private String strToolTip;
	private String strSuccessMsg;
    private int isRemarksReq;
    private String strType;
    private boolean valid;
    List<CategoryMasterModel> categoryModel = new ArrayList<CategoryMasterModel>();
    
    private String encCustomId;
    private String customId;
    private String transactionAt;
    private String employeeName;
    private String finClosureDate;
}
