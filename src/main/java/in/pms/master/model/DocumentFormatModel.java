package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentFormatModel {	
	private int numId;	
	private String encFormatId;
	private String formatName;
	private String mimeTypes;
	private String mimeType;	
	private boolean valid;
	private String icon;
	private long documentTypeId;
	
}
