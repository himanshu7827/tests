package in.pms.master.model;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectDocumentDetailsModel {	
	
	private long numId;	
	private String encNumId;
	private String encStrId;
	private String documentName;	
	private String documentFormat;	
	private String icon;
	private int documentFormatId;
	private String encDocumentFormatId;
	private String encDocumentTypeId;
	private String encProjectId;	
	private MultipartFile projectDocumentFile;
	private MultipartFile proposalDocumentFile;

}
