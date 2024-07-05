package in.pms.transaction.model;

import java.util.Date;

import in.pms.transaction.domain.MonthlyProgressDomain;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyProgressDetailsModel {
	
	private long numId;
	private long numCateoryId;
	private int numParentCateory;
	private int numCategorySequence;
	private MonthlyProgressDomain monthlyProgressDomain;
	private String encId;
	private int actionId;
	private String dtFromDate;
	private String month;
	private String year;
	
	
}