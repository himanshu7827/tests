package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BusinessTypeModel {
		private long numId;
		private String encNumId;
		private String businessTypeName;	
		private String shortCode;	
		private String description;
		private long applicationId;
		private boolean valid;
}
