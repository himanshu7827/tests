package in.pms.master.service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.DocumentFormatDao;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.model.DocumentFormatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DocumentFormatServiceImpl implements DocumentFormatService {
	
	@Autowired
	DocumentFormatDao documentFormatDao;
	
	@Autowired
	MessageSource messageSource;

	@Override
	@PreAuthorize("hasAuthority('WRITE_DOC_FORMAT')")
	public int mergeDocumentFormatMaster(DocumentFormatModel documentFormatModel) {
		DocumentFormatMaster documentFormatMaster = documentFormatDao.mergeDocumentFormat(convertModelToDomain(documentFormatModel));
		return documentFormatMaster.getNumId();
	}

	@Override
	//@PreAuthorize("hasAuthority('WRITE_DOC_FORMAT')")
	public List<DocumentFormatModel> getAllDocumentFormatModel() {		
		return convertDomainListToModelList(documentFormatDao.getAllDocumentFormat());
	}

	@Override
	public List<DocumentFormatModel> getActiveDocumentFormatModel() {
		return convertDomainListToModelList(documentFormatDao.getActiveDocumentFormat());
	}

	@Override
	public DocumentFormatModel getDocumentFormatModelById(int id) {
		return convertDomainToModel(documentFormatDao.getDocumentFormatById(id));	
	}

	@Override
	public DocumentFormatMaster getDocumentFormatByName(String formatName) {
		return documentFormatDao.getDocumentFormatByName(formatName);
	}
	
	@Override
	public DocumentFormatMaster getDocumentFormatById(int id){
		return documentFormatDao.getDocumentFormatById(id);
	}
	
	@Override
	public String checkDuplicateDocumentFormat(DocumentFormatModel documentFormatModel){
		String result=	"";
		DocumentFormatMaster documentFormatMaster = documentFormatDao.getDocumentFormatByName(documentFormatModel.getFormatName());
		
		 if(null == documentFormatMaster){
				return null; 
		 }else if(documentFormatModel.getNumId() != 0){
				 if(documentFormatMaster.getNumId() == documentFormatModel.getNumId()){
					 return null; 
				 }else{
					 
					 result = "Document Format Already exist "+documentFormatModel.getNumId();
				 }
		}else{
				if(documentFormatMaster.getNumIsValid() == 0){
					result = "Document Format Already exist with Id " +documentFormatMaster.getNumId() +". Activate Same Record";
				}else{
					result = "Document Format Already exist with Id "+documentFormatMaster.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public List<DocumentFormatModel> documentFormatByDocumentType(DocumentFormatModel documentFormatModel){		
		return convertObjectListToModelList(documentFormatDao.documentFormatByDocumentType(documentFormatModel));
	}
	
	public DocumentFormatMaster convertModelToDomain(DocumentFormatModel model){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		DocumentFormatMaster domain = new DocumentFormatMaster();
		if(model.getNumId()!=0){
			domain = documentFormatDao.getDocumentFormatById(model.getNumId());
		}
		domain.setFormatName(model.getFormatName());
		domain.setMimeTypes(model.getMimeTypes());
		domain.setNumTrUserId(userInfo.getEmployeeId());
		domain.setIcon(model.getIcon());
		if(model.getMimeType().equals("1")){
			domain.setMimeType(true);
		}else{
			domain.setMimeType(false);
		}
		if(model.isValid()){
			domain.setNumIsValid(1);
		}else{
			domain.setNumIsValid(0);
		}
		return domain;
	}
	
	public DocumentFormatModel convertDomainToModel(DocumentFormatMaster domain){
		DocumentFormatModel model = new DocumentFormatModel();
		model.setFormatName(domain.getFormatName());
		model.setIcon(domain.getIcon());
		if(domain.isMimeType()){
			model.setMimeType("1");
		}else{
			model.setMimeType("-1");
		}
		
		model.setMimeTypes(domain.getMimeTypes());
		if(domain.getNumIsValid() == 1){
			model.setValid(true);
		}else{
			model.setValid(false);
		}
		
		model.setNumId(domain.getNumId());
		
		return model;
	}
	
	public List<DocumentFormatModel> convertDomainListToModelList(List<DocumentFormatMaster> domainList){
		List<DocumentFormatModel> list = new ArrayList<DocumentFormatModel>();
		for(int i=0;i<domainList.size();i++){
			DocumentFormatMaster domain = domainList.get(i);
			DocumentFormatModel model = new DocumentFormatModel();
			model.setFormatName(domain.getFormatName());
			model.setIcon(domain.getIcon());
			if(domain.isMimeType()){
				model.setMimeType("1");
			}else{
				model.setMimeType("-1");
			}
			
			model.setMimeTypes(domain.getMimeTypes());
			if(domain.getNumIsValid() == 1){
				model.setValid(true);
			}else{
				model.setValid(false);
			}
			
			model.setNumId(domain.getNumId());
			list.add(model);
		}	
		return list;
	}

	public List<DocumentFormatModel> convertObjectListToModelList(List<Object[]> domainList){
		List<DocumentFormatModel> list = new ArrayList<DocumentFormatModel>();
		for(int i=0;i<domainList.size();i++){
			Object[] obj = domainList.get(i);
			DocumentFormatModel model = new DocumentFormatModel();
			model.setFormatName(""+obj[1]);
			model.setMimeType(""+obj[2]);
			model.setNumId(Integer.parseInt(""+obj[0]));
			list.add(model);
		}	
		return list;
	}
	
}
