package in.pms.master.service;

import in.pms.global.service.EncryptionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.DocStageMasterDao;
import in.pms.master.domain.DocStageMasterDomain;
import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.model.DocStageMasterModel;
import in.pms.master.model.EmpTypeMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocStageMasterServiceImpl implements DocStageMasterService {
	@Autowired
	DocStageMasterDao docStageMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_DOC_STAGE_MST')")
	public long saveUpdateDocStageMaster(DocStageMasterModel docStageMasterModel){
		DocStageMasterDomain docStageMasterDomain = convertDocStageMasterModelToDomain(docStageMasterModel);
		return docStageMasterDao.saveUpdateDocStageMaster(docStageMasterDomain);
	}
		
	@Override
	public String checkDuplicateStageName(DocStageMasterModel docStageMasterModel){
		String result=	"";
		DocStageMasterDomain docStageMasterDomain = docStageMasterDao.getDocStageMasterByName(docStageMasterModel.getStrStageName());
		
		 if(null == docStageMasterDomain){
				return null; 
			 }else if(docStageMasterModel.getNumId() != 0){
				 if(docStageMasterDomain.getNumId() == docStageMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Document Stage with same name already exist with Id "+docStageMasterDomain.getNumId();
				 }
			 }else{
				if(docStageMasterDomain.getNumIsValid() == 0){
					result = "docStageMaster Details already exist with Id "+docStageMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "docStageMaster Details already exist with Id "+docStageMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public DocStageMasterModel getDocStageMasterDomainById(long numId){
		return convertDocStageMasterDomainToModel(docStageMasterDao.getDocStageMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_DOC_STAGE_MST')")
	public List<DocStageMasterModel> getAllDocStageMasterDomain(){
		return convertDocStageMasterDomainToModelList(docStageMasterDao.getAllDocStageMasterDomain());
	}
	
	@Override
	public List<DocStageMasterModel> getAllActiveDocStageMasterDomain(){
		return convertDocStageMasterDomainToModelList(docStageMasterDao.getAllActiveDocStageMasterDomain());
	}
	
	public DocStageMasterDomain convertDocStageMasterModelToDomain(DocStageMasterModel docStageMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		DocStageMasterDomain docStageMasterDomain = new DocStageMasterDomain();
		if(docStageMasterModel.getNumId()!=0){				
			docStageMasterDomain =  docStageMasterDao.getDocStageMasterById(docStageMasterModel.getNumId());
		}
		
		docStageMasterDomain.setDtTrDate(new Date());
		docStageMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(docStageMasterModel.isValid()){
			docStageMasterDomain.setNumIsValid(1);
		}else{
			docStageMasterDomain.setNumIsValid(0);
		}
		docStageMasterDomain.setDocStageDescription(docStageMasterModel.getStrStageDescription());
		docStageMasterDomain.setStrStageName(docStageMasterModel.getStrStageName());
		
		
		return docStageMasterDomain;
	}
	
	public List<DocStageMasterModel> convertDocStageMasterDomainToModelList(List<DocStageMasterDomain> docStageMasterList){
		List<DocStageMasterModel> list = new ArrayList<DocStageMasterModel>();
			for(int i=0;i<docStageMasterList.size();i++){
				DocStageMasterDomain docStageMasterDomain = docStageMasterList.get(i);
				DocStageMasterModel docStageMasterModel = new DocStageMasterModel();
				
				if(docStageMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+docStageMasterDomain.getNumId());
					docStageMasterModel.setEncOrganisationId(encryptedId);
				}
				docStageMasterModel.setNumId(docStageMasterDomain.getNumId());
				if(docStageMasterDomain.getNumIsValid() ==1){
					docStageMasterModel.setValid(true);
				}else{
					docStageMasterModel.setValid(false);
				}
			
		
				docStageMasterModel.setStrStageName(docStageMasterDomain.getStrStageName());
				docStageMasterModel.setStrStageDescription(docStageMasterDomain.getDocStageDescription());
			
				
				list.add(docStageMasterModel);
	}
		return list;
	}

	
	
	public DocStageMasterModel convertDocStageMasterDomainToModel(DocStageMasterDomain docStageMasterDomain){
		DocStageMasterModel docStageMasterModel = new DocStageMasterModel();
	
		if(docStageMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+docStageMasterDomain.getNumId());
			docStageMasterModel.setEncOrganisationId(encryptedId);
		}
		docStageMasterModel.setNumId(docStageMasterDomain.getNumId());
		if(docStageMasterDomain.getNumIsValid() ==1){
			docStageMasterModel.setValid(true);
		}else{
			docStageMasterModel.setValid(false);
		}
	
		
		docStageMasterModel.setStrStageName(docStageMasterDomain.getStrStageName());
		docStageMasterModel.setStrStageDescription(docStageMasterDomain.getDocStageDescription());
		
		return docStageMasterModel;
		
	}
	public long deleteDocumentStage(DocStageMasterModel docStageMasterModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<DocStageMasterDomain> docStageMasterList = docStageMasterDao.getDocStageMasterById(docStageMasterModel.getIdCheck());
		for(int i=0;i<docStageMasterList.size();i++){
			DocStageMasterDomain docStageMasterDomain = docStageMasterList.get(i);
			docStageMasterDomain.setNumIsValid(2);
			docStageMasterDomain.setDtTrDate(new Date());
			docStageMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			 return docStageMasterDao.saveUpdateDocStageMaster(docStageMasterDomain);
		}
		return result;
	}	
	
}
