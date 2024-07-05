package in.pms.master.service;

import in.pms.master.domain.TaskDocumentDomain;


import javax.transaction.Transactional;

public interface TaskDocumentService {
	
	@Transactional
	public long saveTaskDocument(TaskDocumentDomain taskDocumentDomain);	

}
