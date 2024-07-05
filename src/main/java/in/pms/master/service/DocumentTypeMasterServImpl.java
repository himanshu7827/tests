package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.DocumentFormatDao;
import in.pms.master.dao.DocumentTypeMasterDao;
import in.pms.master.domain.DocumentClassificationDomain;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class DocumentTypeMasterServImpl implements DocumentTypeMasterService {
	
	@Autowired
	DocumentTypeMasterDao documentTypeMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	DocumentFormatDao documentFormatDao;

	@Override
	@PreAuthorize("hasAuthority('WRITE_DOCUMENT_MST')")
	public long saveUpdateDocTypeMaster(DocumentTypeMasterModel documentTypeMasterModel) {
		DocumentTypeMasterDomain documentTypeMasterDomain = convertdocumentTypeMasterModelToDomain(documentTypeMasterModel);
		return documentTypeMasterDao.mergeDocTypeMaster(documentTypeMasterDomain);
	}


	@Override
	public String checkDuplicateDocumentTypeName(DocumentTypeMasterModel documentTypeMasterModel){
		String result = "";
		DocumentTypeMasterDomain documentTypeMasterDomain = documentTypeMasterDao.getDocumentTypeByName(documentTypeMasterModel.getDocTypeName());
	
		 if(null == documentTypeMasterDomain){
			return null; 
		 }else if(documentTypeMasterModel.getNumId() != 0){
			 if(documentTypeMasterDomain.getNumId() == documentTypeMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Document with same name already exist with Id "+documentTypeMasterModel.getNumId();
			 }
		 }else{
			if(documentTypeMasterDomain.getNumIsValid() == 0){
				result = "Document Already Registered with Id "+documentTypeMasterDomain.getNumId() +". Please activate same record";
			}else{
				result = "Document Already Registered with Id "+documentTypeMasterDomain.getNumId();
			}			
		 }
		return result;
	}
	
	
	@Override
	public DocumentTypeMasterModel getDocumentTypeMasterDomainById(long numId){
		return convertdocumentTypeMasterDomainToModel(documentTypeMasterDao.getDocumentTypeMasterDomainById(numId));
	}
	
	@Override
	public DocumentTypeMasterDomain getDocumentTypeMasterDetailById(long numId){
		return documentTypeMasterDao.getDocumentTypeMasterDomainById(numId);
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_DOCUMENT_MST')")
	public List<DocumentTypeMasterModel> getAllDocumentTypeMasterDomain() {
		return convertdocumentTypeMasterDomainToModelList(documentTypeMasterDao.getAllDocumentTypeMasterDomain());	

}
	@Override
	public List<DocumentTypeMasterModel> getAllActiveDocumentTypeMasterDomain(){		
		return convertdocumentTypeMasterDomainToModelList(documentTypeMasterDao.getAllActiveDocumentTypeMasterDomain());
	}
	@Override
	public Map<String, List<DocumentTypeMasterModel>> retrieveDocumentTypeByClassification(){
		Map<String, List<DocumentTypeMasterModel>> dataMap = new LinkedHashMap<String, List<DocumentTypeMasterModel>>();
		List<DocumentTypeMasterDomain> dataList = documentTypeMasterDao.retrieveDocumentTypeByClassification();
		
		for(DocumentTypeMasterDomain domain : dataList){
			DocumentTypeMasterModel model = new DocumentTypeMasterModel();
			model.setNumId(domain.getNumId());
			model.setDocTypeName(domain.getDocTypeName());
			
			if(null != domain.getDocumentClassification()){
				DocumentClassificationDomain documentClassification = domain.getDocumentClassification();
				if(dataMap.containsKey(documentClassification.getClassificationName())){
					List<DocumentTypeMasterModel> tempList = dataMap.get(documentClassification.getClassificationName());
					tempList.add(model);
					dataMap.put(documentClassification.getClassificationName(),tempList);					
				}else{
					List<DocumentTypeMasterModel> tempList = new ArrayList<DocumentTypeMasterModel>();
					tempList.add(model);
					dataMap.put(documentClassification.getClassificationName(),tempList);
				}
			}else{
				
				if(dataMap.containsKey("Others")){
					List<DocumentTypeMasterModel> tempList = dataMap.get("Others");
					tempList.add(model);
					dataMap.put("Others",tempList);					
				}else{
					List<DocumentTypeMasterModel> tempList = new ArrayList<DocumentTypeMasterModel>();
					tempList.add(model);
					dataMap.put("Others",tempList);
				}
			}
		}
		
		return dataMap;		
	}


	public DocumentTypeMasterDomain convertdocumentTypeMasterModelToDomain(DocumentTypeMasterModel documentTypeMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		DocumentTypeMasterDomain documentTypeMasterDomain = new DocumentTypeMasterDomain();
		if(documentTypeMasterModel.getNumId()!=0){
					
			documentTypeMasterDomain =  documentTypeMasterDao.getDocumentTypeMasterDomainById(documentTypeMasterModel.getNumId());
		}
		documentTypeMasterDomain.setDocTypeName(documentTypeMasterModel.getDocTypeName());
		documentTypeMasterDomain.setDescription(documentTypeMasterModel.getDescription());
		documentTypeMasterDomain.setShortCode(documentTypeMasterModel.getShortCode());
		documentTypeMasterDomain.setDtTrDate(new Date());
		documentTypeMasterDomain.setIcon(documentTypeMasterModel.getIcon());
		documentTypeMasterDomain.setDisplaySequence(documentTypeMasterModel.getDisplaySeq());
		if(documentTypeMasterModel.isShowOnDashboard()){
			documentTypeMasterDomain.setShowOnDashboard(1);;
		}else{
			documentTypeMasterDomain.setShowOnDashboard(0);

		}
		if(documentTypeMasterModel.isValid()){
			documentTypeMasterDomain.setNumIsValid(1);
		}else{
			documentTypeMasterDomain.setNumIsValid(0);

		}	
		List<DocumentFormatMaster> docFormatMasterDomain= documentFormatDao.getAllFormatDataByIdList(documentTypeMasterModel.getDocTypeFormatId());			
		documentTypeMasterDomain.setDocumentFormatList(docFormatMasterDomain);
		
		return documentTypeMasterDomain;
	}
	
	public DocumentTypeMasterModel convertdocumentTypeMasterDomainToModel(DocumentTypeMasterDomain documentTypeMasterDomain){
		DocumentTypeMasterModel documentTypeMasterModel = new DocumentTypeMasterModel();
		documentTypeMasterModel.setDocTypeName(documentTypeMasterDomain.getDocTypeName());
		documentTypeMasterModel.setDescription(documentTypeMasterDomain.getDescription());
		documentTypeMasterModel.setShortCode(documentTypeMasterDomain.getShortCode());
		if(documentTypeMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+documentTypeMasterDomain.getNumId());
			documentTypeMasterModel.setEncStrId(encryptedId);
		}
		documentTypeMasterModel.setNumId(documentTypeMasterDomain.getNumId());
		if(documentTypeMasterDomain.getNumIsValid()==1){
			documentTypeMasterModel.setValid(true);
		}else{
			documentTypeMasterModel.setValid(false);
		}
		documentTypeMasterModel.setIcon(documentTypeMasterDomain.getIcon());
		
		return documentTypeMasterModel;
	}
	
	public List<DocumentTypeMasterModel> convertdocumentTypeMasterDomainToModelList(List<DocumentTypeMasterDomain> documentTypeMasterDomainList){
		List<DocumentTypeMasterModel> list = new ArrayList<DocumentTypeMasterModel>();
			for(int i=0;i<documentTypeMasterDomainList.size();i++){
				DocumentTypeMasterDomain documentTypeMasterDomain = documentTypeMasterDomainList.get(i);
				DocumentTypeMasterModel documentTypeMasterModel = new DocumentTypeMasterModel();
				documentTypeMasterModel.setDocTypeName(documentTypeMasterDomain.getDocTypeName());
				documentTypeMasterModel.setDescription(documentTypeMasterDomain.getDescription());
				documentTypeMasterModel.setShortCode(documentTypeMasterDomain.getShortCode());
				if(documentTypeMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+documentTypeMasterDomain.getNumId());
					documentTypeMasterModel.setEncStrId(encryptedId);
				}
				documentTypeMasterModel.setNumId(documentTypeMasterDomain.getNumId());
				documentTypeMasterModel.setDisplaySeq(documentTypeMasterDomain.getDisplaySequence());
				if(documentTypeMasterDomain.getNumIsValid() ==1){
					documentTypeMasterModel.setValid(true);
				}else{
					documentTypeMasterModel.setValid(false);
				}
				
				if(documentTypeMasterDomain.getShowOnDashboard() ==1){
					documentTypeMasterModel.setShowOnDashboard(true);
				}else{
					documentTypeMasterModel.setShowOnDashboard(false);
				}
				String selectedFormatId="";
				List<DocumentFormatMaster>documentFormatMasterList=documentTypeMasterDomain.getDocumentFormatList();
				for (int j=0;j<documentFormatMasterList.size();j++) {
					DocumentFormatMaster documentFormatMaster= documentFormatMasterList.get(j);
					if(j == 0){
						selectedFormatId = ""+documentFormatMaster.getNumId();
					}else{
						selectedFormatId += ","+documentFormatMaster.getNumId();
					}
				}
				documentTypeMasterModel.setSelectedFormatId(selectedFormatId);
				
				documentTypeMasterModel.setIcon(documentTypeMasterDomain.getIcon());
				
						
				list.add(documentTypeMasterModel);
	
	}
		return list;
	}

	@Override
	public void deleteDocument(DocumentTypeMasterModel documentTypeMasterModel) {
		int count= documentTypeMasterModel.getNumIds().length;
		//System.out.println("count"+count);
		long[] document_ids= new long[count];
		document_ids= documentTypeMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			DocumentTypeMasterDomain documentTypeMasterDomain = documentTypeMasterDao.getDocumentTypeMasterDomainById(document_ids[i]);
					
			documentTypeMasterDomain.setDtTrDate(new Date());
			documentTypeMasterDomain.setNumIsValid(2);

			documentTypeMasterDao.mergeDocTypeMaster(documentTypeMasterDomain);
			
			
		}	
		
	}
	
	
	
}