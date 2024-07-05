package in.pms.transaction.model;
/**
 * @author amitkumarsaini
 *
 */
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TalksModel {
	private long numTalkID;
	private String strEventName;
	private String strAgencyName;
	private String strTalkTitle;
	private String dtDate;
	private String strNameSpeaker;
	private String strCityLocation;
	//private long numGroupCategoryId;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
	
	
}
