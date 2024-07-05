package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;





import in.pms.master.domain.DocStageMasterDomain;

import in.pms.master.model.DocStageMasterModel;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocStageMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	
	public long saveUpdateDocStageMaster(DocStageMasterDomain docStageMasterDomain){
		
		docStageMasterDomain =daoHelper.merge(DocStageMasterDomain.class, docStageMasterDomain);		
		return docStageMasterDomain.getNumId();
	}
	
	public DocStageMasterDomain getDocStageMasterById(long id){
		List<DocStageMasterDomain> docStageMasterList =  daoHelper.findByQuery("from DocStageMasterDomain where numId="+id);
		if(docStageMasterList.size()>0){
			return docStageMasterList.get(0);
		}
		return null;
	}
	
	public DocStageMasterDomain getDocStageMasterByName(String strStageName){
		String query = "from DocStageMasterDomain where lower(strStageName)=lower('"+strStageName+"') and numIsValid<>2" ;	
		List<DocStageMasterDomain> docStageMasterDomainList = daoHelper.findByQuery(query);		
		if(docStageMasterDomainList.size()>0){
			return docStageMasterDomainList.get(0);
		}
		return null;
	}

	public List<DocStageMasterDomain> getAllDocStageMasterDomain(){
		String query = "from DocStageMasterDomain where numId<>0 and numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<DocStageMasterDomain> getAllActiveDocStageMasterDomain(){
		String query = "from DocStageMasterDomain where numId<>0 and numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<DocStageMasterDomain> getDocStageMasterById(String ids){
		List<DocStageMasterDomain> docStageMasterList =  daoHelper.findByQuery ("from DocStageMasterDomain where numId in ("+ids+")");		
		return docStageMasterList;
	}
}
