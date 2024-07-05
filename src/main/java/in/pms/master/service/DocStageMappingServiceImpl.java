package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.DocStageMappingDao;
import in.pms.master.dao.DocStageMasterDao;
import in.pms.master.dao.DocumentTypeMasterDao;
import in.pms.master.domain.DocStageMappingDomain;
import in.pms.master.domain.DocStageMasterDomain;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.model.DocStageMappingModel;
import in.pms.master.model.DocumentFormatModel;
import in.pms.master.model.DocumentTypeMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocStageMappingServiceImpl implements DocStageMappingService {
	
	@Autowired
	DocStageMappingDao docStageMappingDao;
	
	@Autowired
	DocumentTypeMasterDao documentTypeMasterDao;
	
	@Autowired
	DocStageMasterDao docStageMasterDao; 
	
	@Autowired
	EncryptionService encryptionService;

	@Override
	@PreAuthorize("hasAuthority('WRITE_DOC_STAGE_MAP')")
	public long saveUpdateDocStageMapping(DocStageMappingModel docStageMappingModel) {
		DocStageMappingDomain docStageMappingDomain=	convertDocStageMapModelToDomain(docStageMappingModel);
		return docStageMappingDao.saveUpdateDocStageMapping(docStageMappingDomain);
	}

	@Override
	public DocStageMappingModel getDocStageMapDomainById(long numId) {
		return convertDocStageMapDomainToModel(docStageMappingDao.getDocStageMapDomainById(numId));
		//return null;
	}

	@Override
	@PreAuthorize("hasAuthority('READ_DOC_STAGE_MAP')")
	public List<DocStageMappingModel> getAllDocStageMappingDomain() {
		return convertDocStageMapDomainToModelList(docStageMappingDao.getAllDocStageMappingDomain());
		//return null;
	}

	@Override
	public List<DocStageMappingModel> getAllActiveDocStageMappingDomain() {
		return convertDocStageMapDomainToModelList(docStageMappingDao.getAllActiveDocStageMappingDomain());
		//return null;
	}
	
	//Model To Domain conversion
	public DocStageMappingDomain convertDocStageMapModelToDomain(DocStageMappingModel docStageMappingModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		DocStageMappingDomain docStageMappingDomain = new DocStageMappingDomain();
		if(docStageMappingModel.getNumId()!=0){
					
			docStageMappingDomain =  docStageMappingDao.getDocStageMapDomainById(docStageMappingModel.getNumId());
		}
		
		docStageMappingDomain.setDocSeq(docStageMappingModel.getDocSeq());
		docStageMappingDomain.setDtTrDate(new Date());
		DocumentTypeMasterDomain documentTypeMasterDomain=  documentTypeMasterDao.getDocumentTypeMasterDomainById(docStageMappingModel.getDocumentId());
		docStageMappingDomain.setDocumentTypeMasterDomain(documentTypeMasterDomain);
		
		DocStageMasterDomain docStageMasterDomain=  docStageMasterDao.getDocStageMasterById(docStageMappingModel.getStageId());
		docStageMappingDomain.setDocStageMasterDomain(docStageMasterDomain);
		
		
		docStageMappingDomain.setMandatory(docStageMappingModel.isMandatory());
		
		
			docStageMappingDomain.setNumIsValid(1);
		
			//docStageMappingDomain.setNumIsValid(0);

			docStageMappingDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		
		return docStageMappingDomain;
		
	
	}
	
	//Domain  to Model Conversion
	public DocStageMappingModel convertDocStageMapDomainToModel(DocStageMappingDomain docStageMappingDomain){
		DocStageMappingModel docStageMappingModel = new DocStageMappingModel();
		if(docStageMappingDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+docStageMappingDomain.getNumId());
			docStageMappingModel.setEncDocStageMapId(encryptedId);;
		}
		
		docStageMappingModel.setDocSeq(docStageMappingDomain.getDocSeq());
		
		docStageMappingModel.setNumId(docStageMappingDomain.getNumId());
	if(docStageMappingDomain.getNumIsValid() ==1){
			docStageMappingModel.setValid(true);
		}else{
			docStageMappingModel.setValid(false);
		}
		
		
		//docStageMappingModel.setIsMandatory(docStageMappingDomain.getIsMandatory());
		docStageMappingModel.setMandatory(docStageMappingDomain.isMandatory());
		
		DocumentTypeMasterDomain documentTypeMasterDomain =docStageMappingDomain.getDocumentTypeMasterDomain();
		docStageMappingModel.setDocumentId(documentTypeMasterDomain.getNumId());
		docStageMappingModel.setDocTypeName(documentTypeMasterDomain.getDocTypeName());
		
		
		DocStageMasterDomain docStageMasterDomain =docStageMappingDomain.getDocStageMasterDomain();
		docStageMappingModel.setStageId(docStageMasterDomain.getNumId());
		docStageMappingModel.setStrStageName(docStageMasterDomain.getStrStageName());
		
		
		return docStageMappingModel;
		
		
	}

	
	//Domain to Model List conversion
	public List<DocStageMappingModel> convertDocStageMapDomainToModelList(List<DocStageMappingDomain> docStageMappingList){
		
		List<DocStageMappingModel> list = new ArrayList<DocStageMappingModel>();
		for(int i=0;i<docStageMappingList.size();i++){
			DocStageMappingDomain docStageMappingDomain = docStageMappingList.get(i);
			DocStageMappingModel docStageMappingModel = new DocStageMappingModel();
			
			if(docStageMappingDomain.getNumId() != 0){
				String encryptedId = encryptionService.encrypt(""+docStageMappingDomain.getNumId());
				docStageMappingModel.setEncDocStageMapId(encryptedId);;
			}
			docStageMappingModel.setNumId(docStageMappingDomain.getNumId());
			if(docStageMappingDomain.getNumIsValid() ==1){
				docStageMappingModel.setValid(true);
			}else{
				docStageMappingModel.setValid(false);
			}
		
			docStageMappingModel.setDocSeq(docStageMappingDomain.getDocSeq());
			//docStageMappingModel.setIsMandatory(docStageMappingDomain.getIsMandatory());
			docStageMappingModel.setMandatory(docStageMappingDomain.isMandatory());
			DocumentTypeMasterDomain documentTypeMasterDomain =docStageMappingDomain.getDocumentTypeMasterDomain();
			docStageMappingModel.setDocumentId(documentTypeMasterDomain.getNumId());
			docStageMappingModel.setDocTypeName(documentTypeMasterDomain.getDocTypeName());
			
			
			DocStageMasterDomain docStageMasterDomain =docStageMappingDomain.getDocStageMasterDomain();
			docStageMappingModel.setStageId(docStageMasterDomain.getNumId());
			docStageMappingModel.setStrStageName(docStageMasterDomain.getStrStageName());
			list.add(docStageMappingModel);
}
		
		return list;
	
	}
//delete function
	@Override
	public void deletedocStageMapping(DocStageMappingModel docStageMappingModel) {
		int count= docStageMappingModel.getNumIds().length;
		//System.out.println("count"+count);
		long[] docStageMap_ids= new long[count];
		docStageMap_ids= docStageMappingModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			DocStageMappingDomain docStageMappingDomain = new DocStageMappingDomain();
			docStageMappingDomain.setNumId(docStageMap_ids[i]);
			
			docStageMappingDomain.setDtTrDate(new Date());

			docStageMappingDao.deletedocStageMapping(docStageMappingDomain);
			
			
		}	
		
	}

	@Override
	public List<DocStageMappingModel> getDocumentByStageId(long stageId, DocStageMappingModel docStageMappingModel) {
		
			log.info("method getDocumentByStageId here");
			List<DocStageMappingDomain> listDSM = docStageMappingDao.getAllDocStageMappingBystageID(stageId);
			List<DocStageMappingModel> listDocStageMappingModel = convertDocStageIdMapDomainToModel(listDSM);
			return listDocStageMappingModel;

	}

	private List<DocStageMappingModel> convertDocStageIdMapDomainToModel(List<DocStageMappingDomain> listDSM) {
		log.info("method convertDocStageIdMapDomainToModel here");
		log.info("listDSM.size()"+listDSM.size());		
		List<DocStageMappingModel> list = new ArrayList<DocStageMappingModel>();
				
		for(int i=0;i<listDSM.size();i++){	
		List<DocStageMappingModel> docStageMappingModel =new ArrayList<DocStageMappingModel>();
	
			DocStageMappingModel docStageMapModel =new DocStageMappingModel();
			DocStageMappingDomain docStageMappingDomain = listDSM.get(i); 
			
			docStageMapModel.setNumId(docStageMappingDomain.getNumId());
			docStageMapModel.setDocumentId(docStageMappingDomain.getDocStageMasterDomain().getNumId());
			docStageMapModel.setDocTypeName(docStageMappingDomain.getDocumentTypeMasterDomain().getDocTypeName());
			docStageMapModel.setStrStageName(docStageMappingDomain.getDocStageMasterDomain().getStrStageName());
			docStageMapModel.setDocSeq(docStageMappingDomain.getDocSeq());
			docStageMapModel.setMandatory(docStageMappingDomain.isMandatory());
			docStageMappingModel.add(docStageMapModel);
			list.add(docStageMapModel);		
		}
		
		return list;

	}

	@Override
	public String checkDuplicateDocumentName(DocStageMappingModel docStageMappingModel) {
		String result = "";
		DocStageMappingDomain docStageMappingDomain = docStageMappingDao.getDocumentByName(docStageMappingModel.getDocTypeName());
	
		 if(null == docStageMappingDomain){
			return null; 
		 }else if(docStageMappingModel.getNumId() != 0){
			 if(docStageMappingDomain.getNumId() == docStageMappingModel.getNumId()){
				 return null; 
			 }else{
				 result = "Document with same name already exist with Id "+docStageMappingModel.getNumId();
			 }
		 }else{
			if(docStageMappingDomain.getNumIsValid() == 0){
				result = "Document Already Registered with Id "+docStageMappingDomain.getNumId() +". Please activate same record";
			}else{
				result = "Document Already Registered with Id "+docStageMappingDomain.getNumId();
			}			
		 }
		return result;
	}
	
	
	//unique
	@Override
	public long checkUniqueDocument(DocStageMappingModel docStageMappingModel)
	{ log.info("doc id"+ docStageMappingModel.getDocumentId() );
        log.info("stage id"+ docStageMappingModel.getStageId());
	  long count = docStageMappingDao.getDuplicateValue(docStageMappingModel.getDocumentId(), docStageMappingModel.getStageId());
      return count;
	}
	
	
	@Override
	public List<DocumentTypeMasterModel> getDocumentsByStageName(String stageName) {
		List<DocStageMappingDomain> listData = docStageMappingDao.getDocumentsByStageName(stageName);		
		List<DocumentTypeMasterModel> listDocStageMappingModel = convertdocumentTypeMasterDomainToModelList(listData);
		return listDocStageMappingModel;
	}
	
	public List<DocumentTypeMasterModel> convertdocumentTypeMasterDomainToModelList(List<DocStageMappingDomain> docStageMappingDomains){
		List<DocumentTypeMasterModel> list = new ArrayList<DocumentTypeMasterModel>();
			for(int i=0;i<docStageMappingDomains.size();i++){
				DocStageMappingDomain docStageMappingDomain = docStageMappingDomains.get(i);
				DocumentTypeMasterDomain documentTypeMasterDomain = docStageMappingDomain.getDocumentTypeMasterDomain();
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
				List<DocumentFormatMaster> documentFormatMasterList=documentTypeMasterDomain.getDocumentFormatList();
				List<DocumentFormatModel> formatList = new ArrayList<DocumentFormatModel>();
				for (int j=0;j<documentFormatMasterList.size();j++) {
					DocumentFormatMaster formatMaster= documentFormatMasterList.get(j);
					DocumentFormatModel model = new DocumentFormatModel();
					model.setFormatName(formatMaster.getFormatName());
					model.setNumId(formatMaster.getNumId());
					model.setEncFormatId(encryptionService.encrypt(""+formatMaster.getNumId()));
					model.setIcon(formatMaster.getIcon());
					formatList.add(model);
				}
				documentTypeMasterModel.setFormatModels(formatList);
				documentTypeMasterModel.setSelectedFormatId(selectedFormatId);
				
				documentTypeMasterModel.setIcon(documentTypeMasterDomain.getIcon());
				documentTypeMasterModel.setMandatory(docStageMappingDomain.isMandatory());
						
				list.add(documentTypeMasterModel);
	
	}
		return list;
	}

}
