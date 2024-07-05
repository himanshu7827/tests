package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProjectInnovationsModel {
	private int numId;
	private String strInnovationDescriptionHtml;
	private String strInnovationDescription;
//	private int numReportId;
	private String strStatus;
	private int[] numIds;
	private String encMonthlyProgressId;
	private String encCategoryId;
}