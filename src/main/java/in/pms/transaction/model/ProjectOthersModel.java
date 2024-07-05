package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProjectOthersModel {

	private long numId;
	//private int numReportId;
	private String strStatus;
	private String strProjectOthersHtml;
	private String strProjectOthers;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
}