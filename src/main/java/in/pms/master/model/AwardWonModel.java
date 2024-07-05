package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwardWonModel {	

	private long numId;
	
	private String awardType;
	
	private String dateOfAward;
	private long[] numIds;
	private String awardName;
	private String recipientName;
	private String achievementDescription;
	private String awardBy;
	private String projectAwardedFor;
	private String location;
	private String encMonthlyProgressId;
	private String encCategoryId;
}
