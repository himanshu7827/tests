package in.pms.master.service;

import in.pms.master.domain.NewsletterDocuments;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface DocumentService {
	
	@Transactional(propagation=Propagation.REQUIRED)
	public NewsletterDocuments getNewsletterDocuments(int docId);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateNewsletterDocuments(int numNewsLetterId, int parseInt);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertNewsLetterDocuments(NewsletterDocuments nd);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean deleteNewsletterDocuments(int docName);

}
