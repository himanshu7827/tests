package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionMasterModel {
	
	private long numId;	
	private String strQuestions;
	private boolean valid;
    private long[] numIds;
    private long questionTypeId;
    private List<Long> answerId;
    private String answer;
    private String answerIds;
	}
