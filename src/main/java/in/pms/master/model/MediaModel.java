package in.pms.master.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaModel {	

	private long numId;
	private long[] numIds;
	private String source;
	private String dateOfmedia;
	private String sourceDetails;
	private String details;
	private String location;
	private String anyOtherDetails;
	private String encMonthlyProgressId;
	private String encCategoryId;
	
}
