package in.pms.transaction.model;
/**
 * @author amitkumarsaini
 *
 */

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CopyRightModel {
	private long numCopyRightID;
	private String strTitle;
	private String dateOfCopyright;
	private String strAgency;
	private String strCopyrightFiledAwarded;
	private String dtFilingDate;
	private String dtAwardDate;
	private String strReferenceNumber;
	
	 private String copyRightDetail;
	 private String encMonthlyProgressId;
	 private String encCategoryId;
	
	}
