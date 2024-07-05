package in.pms.master.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

@Data
public class ProposalDocumentMasterModel {

	private long proposalId;
	private List<Long> numId;
	private List<Integer> updateFlag;
	private List<Integer> documentTypeId;	
	private List<String> documentDate;
	private long uploadedBy;		
	private List<String> originalDocumentName;	
	private List<String> documentName;
	private List<MultipartFile> projectDocumentFile;
	private List<String> proposalStage;
	private String description;
	private int applicationId;
	private String periodFrom;
	private String periodTo;	
	private String documentVersion;	
	List<ProposalDocumentMasterModel> detailsModels = new ArrayList<ProposalDocumentMasterModel>();
	private String documentTypeName;
	private String icon;
	private String encNumId;
}
