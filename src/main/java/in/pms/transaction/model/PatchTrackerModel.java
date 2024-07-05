package in.pms.transaction.model;
 import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
  @Getter
  @Setter
  @ToString
  
  public class PatchTrackerModel{
	  private long id;
	  private String severity ;
	  private String type;
	  private String strdescription;
	  private String strRequestedBy;
	  private String strNameOfFiles;
	  private String strTeamMembers;
	  private String strBugId;
	  private String depDate;
	  private String strSvnNo;
	  private boolean valid;
	  private boolean valid2;
	  private String stage;
	  private String transDate;
	  private String strUserId;
	  private String strModules;
  
  }