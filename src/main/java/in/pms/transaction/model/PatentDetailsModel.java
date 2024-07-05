package in.pms.transaction.model;

import java.util.*;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatentDetailsModel 
{
	private int projectPatentId;
	private String strPatentTitle; 
	private String strInventorName;
	private String strInventorAddr;
	private String strReferenceNum; 
	private String strIsFiled;
	private String dtFilingDate;
	private String dtAwardDate;
	private String strIsAwarded;
	private String strCountryDetials; 
	private String strStatus;
	private int[] projectPatentIds;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
	
}
