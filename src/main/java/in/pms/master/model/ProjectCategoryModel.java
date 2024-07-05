package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class ProjectCategoryModel {
	
		private int numId;		
		private String encNumId;		
		private String categoryName;		
		private String shortCode;		
		private String description;		
		private boolean valid;
		private String strCode;
}
