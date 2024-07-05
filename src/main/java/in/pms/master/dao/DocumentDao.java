package in.pms.master.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.NewsletterDocuments;

@Repository
public class DocumentDao {

	Date dtSysDate = new Date();

	@Autowired
	public DaoHelper daoHelper;
	
	public NewsletterDocuments getNewsletterDocuments(int docId) {
		String query = "select r from NewsletterDocuments r where r.numId = "+docId;
		List<NewsletterDocuments> lod = daoHelper.findByQuery(query);
		if(lod.size()>0){
			return lod.get(0);
		}
		else{
			NewsletterDocuments od = new NewsletterDocuments();
			od.setStrdocname("NONE");
			od.setNumId(0);
			return od;
		}
	}
	
	public void updateNewsLetterDocuments(NewsletterDocuments lod) {
		daoHelper.merge(NewsletterDocuments.class, lod);
	}
	
	public NewsletterDocuments insertNewsletterDocuments(NewsletterDocuments nd) {
		return daoHelper.persist(NewsletterDocuments.class, nd);
	}
	
	public boolean deleteNewsLetter(NewsletterDocuments lod) {
		return daoHelper.delete(lod);
	}
}
