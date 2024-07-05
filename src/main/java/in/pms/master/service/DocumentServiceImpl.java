package in.pms.master.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.master.dao.DocumentDao;
import in.pms.master.domain.NewsletterDocuments;

@Service
public class DocumentServiceImpl implements DocumentService {

	
	@Autowired
	public DocumentDao documentDao;
	
	public NewsletterDocuments getNewsletterDocuments(int docId) {
		NewsletterDocuments lod = documentDao.getNewsletterDocuments(docId);
		return lod;
	}
	
	public void updateNewsletterDocuments(int numNewsLetterId, int docName) {
		NewsletterDocuments lod = documentDao.getNewsletterDocuments(docName);
		if(lod!=null){
			lod.setNewsLetterId(numNewsLetterId);
			lod.setIsDraft(0);
			documentDao.updateNewsLetterDocuments(lod);
		}
		
	}
	
	public int insertNewsLetterDocuments(NewsletterDocuments nd) {
		NewsletterDocuments lod = documentDao.insertNewsletterDocuments(nd);
		return lod.getNumId();

	}
	
	public boolean deleteNewsletterDocuments(int docName) {
		NewsletterDocuments lod = documentDao.getNewsletterDocuments(docName);
		return documentDao.deleteNewsLetter(lod);
	}
}
