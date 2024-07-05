package in.pms.transaction.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryMasterModel {
	private long numProjectId;
	private long numCategoryId;
	private long numSubCategoryId;
	private String strCategoryName;
	private int numCategorySequence;
	private int numParentCategory;
	private String strDescription;
	private String isUploadRequired;
	private String strProjectGroupFlag;
	private String strMonthAndYear;
	private String strCategoryController;
	private String encCategoryId;
	private String parentId;
}
