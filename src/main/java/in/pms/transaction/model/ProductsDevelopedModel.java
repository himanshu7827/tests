package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductsDevelopedModel {
	private int numId;
	private String strProductsDevelopedHtml;
	private String strProductsDeveloped;
	//private int numReportId;
	private String strStatus;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
	private int[] numIds;
}