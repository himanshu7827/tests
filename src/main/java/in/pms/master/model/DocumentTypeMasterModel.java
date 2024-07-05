package in.pms.master.model;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DocumentTypeMasterModel {
	private long numId;	
	private String encStrId;
	private String docTypeName;
	private String description;
	private String shortCode;
	private boolean valid;
	private long[] numIds;
	private String icon;
	private boolean showOnDashboard;
	private int displaySeq;
	private List<Integer> docTypeFormatId;
	private String selectedFormatId; 
	private List<DocumentFormatModel> formatModels = new ArrayList<DocumentFormatModel>();
	private boolean mandatory;
}
