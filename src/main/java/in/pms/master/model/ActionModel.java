package in.pms.master.model;
/**
 * @author amitkumarsaini
 *
 */
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionModel {
	private long numActionId;
	private String strName;
	private String strActionPerformed;
	private boolean valid;
	
}
