package in.pms.master.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.domain.ProposalMasterDomain;

@Repository
public class DocumentTypeMasterDao {
	
	
	@Autowired
	DaoHelper daoHelper;
	
	public long mergeDocTypeMaster(DocumentTypeMasterDomain documentTypeMasterDomain){
		documentTypeMasterDomain = daoHelper.merge(DocumentTypeMasterDomain.class, documentTypeMasterDomain);
		return documentTypeMasterDomain.getNumId();
	}
	
	public DocumentTypeMasterDomain getDocumentTypeByName(String docTypeName){
		String query = "from DocumentTypeMasterDomain where lower(docTypeName)=lower('"+docTypeName+"') and numIsValid<>2";	
		List<DocumentTypeMasterDomain> documentTypeMasterDomainList = daoHelper.findByQuery(query);		
		if(documentTypeMasterDomainList.size()>0){
			return documentTypeMasterDomainList.get(0);
		}
		return null;
	}
	
	
	public DocumentTypeMasterDomain getDocumentTypeMasterDomainById(long numId){
		DocumentTypeMasterDomain documentTypeMasterDomain =null;
		String query = "from DocumentTypeMasterDomain where numId="+numId;
		List<DocumentTypeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			documentTypeMasterDomain =list.get(0);
		}
		return documentTypeMasterDomain;	
	}
	
	
	
	public List<DocumentTypeMasterDomain> getAllDocumentTypeMasterDomain(){
		String query = "from DocumentTypeMasterDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}


	public List<DocumentTypeMasterDomain> getAllActiveDocumentTypeMasterDomain(){
		String query = "from DocumentTypeMasterDomain where numIsValid=1 order by docTypeName";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<DocumentTypeMasterDomain> getAllDocumentByShowOnDashboard(){
		String query = "from DocumentTypeMasterDomain where numIsValid=1 and showOnDashboard=1 order by numId";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<DocumentTypeMasterDomain> retrieveDocumentTypeByClassification(){
		String query = "select a from DocumentTypeMasterDomain a left join fetch a.documentClassification b where a.numIsValid=1 order by b.displaySequence,a.docTypeName";
		return  daoHelper.findByQuery(query);	
	}
}
