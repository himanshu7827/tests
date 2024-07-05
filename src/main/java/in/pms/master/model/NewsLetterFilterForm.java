package in.pms.master.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsLetterFilterForm {
	
	private int numFilterId;
	
	private String strFilterName;
	
	private String strFilterDesc;
	
	private String strFilterQuery;
	
	private String strFilterType;
	
	private String to;
	private String cc;
	private String bcc;
	


	private String strSubject;
	
	private String strContent;
	
	private String strIDs;
	
	private int numUserId;
	
	private String strSMSContent;
	
	private String strSMSFlag;
	
	private String strEmailId;
	
	private String strMobileNumber;
	
    private int[] arrCheckbox;
    
    private String documentIds;
    
    private String scheduledDate;
	private int numletterId;

    private String periodicNewsletterDate;
    private int numProjectId;
    
	
	
}
