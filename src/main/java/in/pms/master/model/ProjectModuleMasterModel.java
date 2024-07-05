package in.pms.master.model;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ProjectModuleMasterModel {

	
	private long numId;
	private boolean valid;
	
	private String strModuleName;
	private String strModuleDescription;
	
	private String encProjectId;
	private String idCheck;
	private long projectId;
	private String projectName;
	
}
