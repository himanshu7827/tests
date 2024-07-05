package in.pms.transaction.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExitInterviewModel {
	
	private long numId;	
	private String strQuestion;
	private boolean valid;
    private long[] numIds;
    private List<Long> answerIds;
    private String answer;
    private String flaEmail;
    private String slaEmail;
    private String employeeRemarks;
    private String flaRemarks;
    private String slaRemarks;
    private String hrRemarks;
    private String status;
    private long questionId;
    private long answerId;
    private String exitInterviewDetails;
    private long empId;
    private String empName;
    private long exitInterviewId;
    private int approvalId;
    private int statusValue;
    private String remarks;
    private String endDateDetails;


}
