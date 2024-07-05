package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class ProjectTypeModel {
		private int numId;
		private String encNumId;
		private String projectTypeName;
		private String shortCode;
		private String description;
		private boolean valid;
		private String strCode;
}
