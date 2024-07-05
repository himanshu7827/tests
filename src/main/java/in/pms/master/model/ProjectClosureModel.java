package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectClosureModel {
	
	private String encProjectId;	
	private String teamDetails;	
	private List<ProjectDocumentMasterModel> projectDocuments;	
	private String closureRemark;
	private String closureDate;
	private String status;
	private int numActionId;
	
}
