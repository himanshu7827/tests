package in.pms.master.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
public class ProjectDocumentMasterModel {
	private long numId;
	private String encNumId;
	private long projectId;	
	private String encProjectId;
	private long documentTypeId;	
	private String documentDate;
	private long uploadedBy;	
	private String periodFrom;
	private String periodTo;	
	private String description;
	private String documentVersion;	
	List<ProjectDocumentDetailsModel> detailsModels = new ArrayList<ProjectDocumentDetailsModel>();
	private String documentTypeName;
	private String icon;
	private long proposalId;
	private String title;
	private int applicationId;
	private String classColor;
	private String uploadedFor;
	private Date uploadedOn;
	
	private String template;
	private long revId;//Added by devesh on 28-09-23 to get revisionId in modal
	
	/*---- Variable for store project related Details [18-03-2024] ----*/
	private int groupId;
	private String projectIds;
	private String documentTypeIds;
	private String strProjectName;
	private String strProjectRefNo;
}
