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
public class MouCollaborationModel {
	private long numCollabID;
	private String strObjective;
	private String dtFromDate;
	private String dtValidityDate;
	private String strCollabAgency;
	private String encMonthlyProgressId;
	private String encCategoryId;
	
}
