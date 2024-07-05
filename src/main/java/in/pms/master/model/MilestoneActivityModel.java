package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MilestoneActivityModel {

	private long numId;
	private String description;
	private String startDate;
	private String endDate;
	private long moduleId;
	private int withModule;
	private String strModuleName;
	private String strModuleDescription;
}
