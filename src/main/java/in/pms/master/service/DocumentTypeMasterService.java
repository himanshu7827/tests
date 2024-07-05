package in.pms.master.service;


import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public interface DocumentTypeMasterService {

	
	@Transactional
	
	public long saveUpdateDocTypeMaster(DocumentTypeMasterModel documentTypeMasterModel);
	
	public String checkDuplicateDocumentTypeName(DocumentTypeMasterModel documentTypeMasterModel);
	
	public DocumentTypeMasterModel getDocumentTypeMasterDomainById(long numId);
	
	public DocumentTypeMasterDomain getDocumentTypeMasterDetailById(long numId);
	@Transactional
	public List<DocumentTypeMasterModel> getAllDocumentTypeMasterDomain();
	@Transactional
	public List<DocumentTypeMasterModel> getAllActiveDocumentTypeMasterDomain();
	
	public Map<String, List<DocumentTypeMasterModel>> retrieveDocumentTypeByClassification();
	
	@Transactional
	public void deleteDocument(DocumentTypeMasterModel documentTypeMasterModel);
	
	

}
