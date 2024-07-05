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
public class OthersModel {
	private long numOthersID;
	private String strRecognition;
	private String dateOfOthers;
	private String strAppreciaitionAgency;
	private String strCityLocation;
	private String encMonthlyProgressId;
	private String encCategoryId;
}
