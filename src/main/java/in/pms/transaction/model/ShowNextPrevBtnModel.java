package in.pms.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowNextPrevBtnModel {
	
	private String strPrevController;
	private String strNextController;
	private String encPrevCategoryId;
	private String encNextCategoryId;
	private String categoryType;
	
}
