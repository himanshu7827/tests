package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DocStageMappingModel {
	
	private long numId;
	private String encDocStageMapId;
	private long documentId;
	private String encDocumentId;
	private long stageId;
	private String encStageId;
	
	private String docTypeName;
	private String strStageName;
	private boolean mandatory;
	private long docSeq;
	private boolean valid;
	private long[] numIds;

}
