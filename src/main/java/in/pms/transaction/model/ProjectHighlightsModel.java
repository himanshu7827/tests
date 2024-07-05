package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProjectHighlightsModel {

	private int numId;
	private String strCompActivityHtml;
	private String strCompActivity;
	private String strOngoingActivityHtml;
	private String strOrngoingActivity;
	//private int numReportId;
	private String strStatus;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
	
}