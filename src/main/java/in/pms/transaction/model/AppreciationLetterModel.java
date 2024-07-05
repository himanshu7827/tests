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
public class AppreciationLetterModel {
	private long numAppreciationID;
	private String strDescription;
	private String dateOfAppreciation;
	private String strAppreciaitionAgency;
	private String strRecipientName;
	private String encMonthlyProgressId;
	private String encCategoryId;
}
