package in.pms.master.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;

import lombok.Getter;
import lombok.Setter;
import in.pms.global.model.WorkflowModel;
import in.pms.master.domain.ClientMasterDomain;
import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.transaction.model.CollaborativeOrgDetailsModel;

@Getter
@Setter
public class ProjectMasterForm {
	
	private long numId;	
	private String strProjectName;		
	private double projectCost;	
	private String strTotalCost;
	private long[] numIds;
	private Integer projectDuration;
	private String strProjectStatus;
	private String projectType;
	private String strBriefDescription;
	private String strProjectObjective;
	private String strProjectScope;
	private String strProjectAim;
	private boolean isValid;
	private String mouDate;
	private String startDate;
	private String endDate;
	private String workOrderDate;
	private String encNumId;
	private String strPLName;
	private String strHODName;//Added by devesh on 28-11-23 to store HOD name
	private int categoryId;//Added by devesh on 05-02-24 to get project category
	private double numReceivedAmount;
	//Bhavesh (22-09-23) created a variable  numReceivedAmountInr to store the received amount in inr
	private String numReceivedAmountInr;
	private String numReceivedAmountTemp;
	//Bhavesh (22-09-23)
	private String strReceivedAmout;
	private long projectId;
	private String encProjectId;
	private String encProposalDocumentId;
	private String encworkOrderDocumentId;
	private String encmouDocumentId;
	private String encSrsDocumentId;
	private String encGroupId;
	
	private String businessType;
	private String clientName;
	private long clientId;
	private String encClientId;
	private String strProjectRemarks;
	private String strFundedScheme;
	private String endUserName;
	private long endUserId;
	private Date dtEndDate;
	
	private List<ProjectDocumentMasterModel> projectDocument = new ArrayList<ProjectDocumentMasterModel>();
	
	private long applicationId;
	private String encApplicationId;
	private ClientMasterDomain clientMasterDomain=new ClientMasterDomain();
	private Set<ContactPersonMasterDomain> contactPersonList=new HashSet<ContactPersonMasterDomain>();
	
	private String thrustAreas;
	private String projectCategory;
	
	private JSONArray collaborationOrganisationDetails;
	
	private List<Long> contactPersonId=new ArrayList<Long>(); 
	
	private String projectRefrenceNo;
	
	 private String administrationNo;
	 
	 private String description;
	 private String closureDate;
	 private List<GroupMasterModel> groupMasterModelList = new ArrayList<GroupMasterModel>();
	 private String strRoleName;
	 private double totalOutlay;
	 private String strProjectCost;
	 private double numProjectAmountLakhs;
	 private String strGroupName;
	 private int numCorpMonthlySharing;
	 private String strGST;
	 private String strTAN;
	 private String closureStatus;
	 private boolean convertProposalToProject;
	 
	 private WorkflowModel workflowModel;
	 private String strTechClosureDate;
	 private long numProposalCost;

	 /*-------- ADD Variable for Row color [12-10-2023] ----------------*/
	 private String strValidatedRemarks;
	 private String strValidateStatus;
	 private String validateDate;
	 private String rowColor;
	 // add variable of collobrative by varun
	 List <CollaborativeOrgDetailsModel> collaborativeOrgDetailsModels;
	 
}