package in.pms.master.service;


import in.pms.master.model.DocStageMasterModel;





import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface DocStageMasterService {

	@Transactional	
	 public long saveUpdateDocStageMaster(DocStageMasterModel docStageMasterModel);
	
	// public String checkStageName(DocStageMasterModel docStageMasterModel);
	
	 public String checkDuplicateStageName(DocStageMasterModel docStageMasterModel);
	 
	 public DocStageMasterModel getDocStageMasterDomainById(long numId);
	
	 public List<DocStageMasterModel> getAllDocStageMasterDomain();
	
	 public List<DocStageMasterModel> getAllActiveDocStageMasterDomain();
	
	@Transactional
	public long deleteDocumentStage(DocStageMasterModel docStageMasterModel);
	
	
}
