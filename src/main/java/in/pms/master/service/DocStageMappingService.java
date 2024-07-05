package in.pms.master.service;

import in.pms.master.model.DocStageMappingModel;
import in.pms.master.model.DocumentTypeMasterModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

public interface DocStageMappingService {
	
	@Transactional

    public long saveUpdateDocStageMapping(DocStageMappingModel docStageMappingModel);
	public String checkDuplicateDocumentName(DocStageMappingModel docStageMappingModel);
	
	public DocStageMappingModel getDocStageMapDomainById(long numId);
	
	public List<DocStageMappingModel> getAllDocStageMappingDomain();
	
	public List<DocStageMappingModel> getAllActiveDocStageMappingDomain();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deletedocStageMapping(DocStageMappingModel docStageMappingModel);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<DocStageMappingModel> getDocumentByStageId(long stageId, DocStageMappingModel docStageMappingModel);

	public long checkUniqueDocument(DocStageMappingModel docStageMappingModel);
	@Transactional
	public List<DocumentTypeMasterModel> getDocumentsByStageName(String stageName);
}
