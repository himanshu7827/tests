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
public class AdditionalQualificationModel {
	private long numQualID;
	private long numEmployeeID;
	private String strCertificateName;
	private String strEmployeeName;
	private String strEmployeeDesignation;
	private String strFocusArea;
	private String strDescriptionProgram;
	private long numGroupCategoryId;
	
	private String encMonthlyProgressId;
	private String encCategoryId;
}
