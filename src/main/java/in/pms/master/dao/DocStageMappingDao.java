package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.DocStageMappingDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DocStageMappingDao {
	
	@Autowired DaoHelper daoHelper;
	
	public long saveUpdateDocStageMapping(DocStageMappingDomain docStageMappingDomain){
		
		docStageMappingDomain= daoHelper.merge(DocStageMappingDomain.class,docStageMappingDomain );
		
		
		return docStageMappingDomain.getNumId();
		
	}
	
	public DocStageMappingDomain getDocumentByName(String docTypeName){
		String query = "from DocStageMappingDomain where lower(docTypeName)=lower('"+docTypeName+"')";	
		List<DocStageMappingDomain> docStageMappingDomainList = daoHelper.findByQuery(query);		
		if(docStageMappingDomainList.size()>0){
			return docStageMappingDomainList.get(0);
		}
		return null;
	}
	
	//unique
	public int getDuplicateValue(long docId, long StageId){
		String query = "from DocStageMappingDomain where  docStageMasterDomain.numId="+StageId+" and  documentTypeMasterDomain.numId="+docId+" and numIsValid<>2";	
		List<DocStageMappingDomain> docStageMappingDomainList = daoHelper.findByQuery(query);		
		return docStageMappingDomainList.size();
	
	}
	
	
	public DocStageMappingDomain getDocStageMapDomainById(long numId){
		DocStageMappingDomain docStageMappingDomain =null;
		String query = "from DocStageMappingDomain where numId="+numId;
		List<DocStageMappingDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			docStageMappingDomain =list.get(0);
		}
		return docStageMappingDomain;	
	}
	
	
	
	public List<DocStageMappingDomain> getAllDocStageMappingDomain(){
		String query = "from DocStageMappingDomain where numIsValid<>2";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<DocStageMappingDomain> getAllActiveDocStageMappingDomain(){
		String query = "from DocStageMappingDomain where numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	
	public void deletedocStageMapping(DocStageMappingDomain docStageMappingDomain)
	{
		DocStageMappingDomain docStageMapping = daoHelper.findById(DocStageMappingDomain.class, docStageMappingDomain.getNumId());
		docStageMapping.setNumIsValid(2);
		docStageMapping.setDtTrDate(docStageMappingDomain.getDtTrDate());
		daoHelper.merge(DocStageMappingDomain.class, docStageMapping);
	}

	public List<DocStageMappingDomain> getAllDocStageMappingBystageID(long stageId) {
		String query = "from DocStageMappingDomain e where e.numIsValid=1 and  e.docStageMasterDomain.numId="+stageId;
		//String query = "from DocStageMappingDomain e join fetch e.documentTypeMasterDomain where e.numIsValid<>2 and e.docStageMasterDomain.numId="+stageId;
		System.out.println("query is"+query);
		return daoHelper.findByQuery(query);
	}
	
	public List<DocStageMappingDomain> getDocumentsByStageName(String stageName) {
		//String query = "select distinct a from DocStageMappingDomain e join e.documentTypeMasterDomain a join fetch a.documentFormatList  where e.numIsValid=1 and  e.docStageMasterDomain.strStageName='"+stageName+"'";
		String query = "select distinct e from DocStageMappingDomain e join fetch e.documentTypeMasterDomain a join fetch a.documentFormatList  where e.numIsValid=1 and  e.docStageMasterDomain.strStageName='"+stageName+"' order by e.docSeq";
		return daoHelper.findByQuery(query);
	}

}
