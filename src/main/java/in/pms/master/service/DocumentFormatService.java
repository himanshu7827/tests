package in.pms.master.service;

import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.model.DocumentFormatModel;

import java.util.List;

import javax.transaction.Transactional;

public interface DocumentFormatService {
	
	@Transactional
	public int mergeDocumentFormatMaster(DocumentFormatModel documentFormatModel);
	
	public List<DocumentFormatModel> getAllDocumentFormatModel();
	
	public List<DocumentFormatModel> getActiveDocumentFormatModel();
	
	public DocumentFormatModel getDocumentFormatModelById(int id);
	
	public DocumentFormatMaster getDocumentFormatByName(String formatName);
	
	public String checkDuplicateDocumentFormat(DocumentFormatModel documentFormatModel);
	
	public List<DocumentFormatModel> documentFormatByDocumentType(DocumentFormatModel documentFormatModel);
	
	public DocumentFormatMaster getDocumentFormatById(int id);

}
