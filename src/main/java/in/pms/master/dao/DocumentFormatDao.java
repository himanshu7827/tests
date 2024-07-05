package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.DocumentFormatModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentFormatDao {

	@Autowired
	DaoHelper daoHelper;
	
	public DocumentFormatMaster mergeDocumentFormat(DocumentFormatMaster documentFormatMaster){		
		return daoHelper.merge(DocumentFormatMaster.class, documentFormatMaster);	
	}
	
	public List<DocumentFormatMaster> getAllDocumentFormat(){		
		return daoHelper.findByQuery("from DocumentFormatMaster where numIsValid<>2 order by formatName");		
	}
	
	public List<DocumentFormatMaster> getActiveDocumentFormat(){		
		return daoHelper.findByQuery("from DocumentFormatMaster where numIsValid=1");		
	}
	
	public DocumentFormatMaster getDocumentFormatById(int id){	
		List<DocumentFormatMaster> list = daoHelper.findByQuery("from DocumentFormatMaster where numId="+id);
		if(list.size()>0){
			return list.get(0);
		}
		return null;		
	}
	
	public DocumentFormatMaster getDocumentFormatByName(String formatName){	
		List<DocumentFormatMaster> list = daoHelper.findByQuery("from DocumentFormatMaster where numIsValid<>2 and lower(formatName)= lower('"+formatName+"')");
		if(list.size()>0){
			return list.get(0);
		}
		return null;		
	}
	// Get Document format with its document Type
	public List<Object[]> documentFormatByDocumentType(DocumentFormatModel documentFormatModel){
		String query = "select a.numId, a.formatName,a.mimeTypes from DocumentFormatMaster a JOIN a.documentTypeMasterList b where b.numId="+documentFormatModel.getDocumentTypeId();		
		return daoHelper.findByQuery(query);
	}
	
	public List<DocumentFormatMaster> getAllFormatDataByIdList(List<Integer> ids){
		String query = "from DocumentFormatMaster t where t.numId in :ids";
		return daoHelper.findByIdListInteger(query, ids);
	}
}
