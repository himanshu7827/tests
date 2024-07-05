package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerMasterModel {
	
	private long numId;	
	private String strAnswer;
	private boolean valid;
    private long[] numIds;
	
	}
