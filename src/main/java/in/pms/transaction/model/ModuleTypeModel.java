package in.pms.transaction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ModuleTypeModel {
		private long moduleTypeId;
		private String moduleTypeName;	
		private String shortCode;	
		private String description;
		private boolean valid;
}
