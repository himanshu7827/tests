package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillMasterModel {
	
	private long numId;	
	private String strSkillName;
	private boolean valid;
    private long[] numIds;
	
	}
