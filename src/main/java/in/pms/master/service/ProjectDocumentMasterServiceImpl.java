package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectDocumentMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.DocumentClassificationDomain;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.TempProjectDocumentDetailsDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.transaction.dao.MonthlyProgressDao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectDocumentMasterServiceImpl implements ProjectDocumentMasterService {

	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	
	@Autowired
	ProjectDocumentMasterDao projectDocumentMasterDao;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	DocumentFormatService documentFormatService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	MonthlyProgressDao monthlyProgressDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('UPLOAD_PROJECT_DOCUMENT')")
	public boolean uploadProjectDocument(ProjectDocumentMasterModel projectDocumentMasterModel) {
		boolean result = false;
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectDocumentMasterDomain projectDocumentMasterDomain = new ProjectDocumentMasterDomain();
		
		if(projectDocumentMasterModel.getNumId() != 0){
			if(null != projectDocumentMasterDao.uploadedProjectDocumentById(projectDocumentMasterModel))
				projectDocumentMasterDomain = projectDocumentMasterDao.uploadedProjectDocumentById(projectDocumentMasterModel);
		}
		projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectDocumentMasterDomain.setProjectId(projectDocumentMasterModel.getProjectId());
		projectDocumentMasterDomain.setDocumentTypeMasterDomain(documentTypeMasterService.getDocumentTypeMasterDetailById(projectDocumentMasterModel.getDocumentTypeId()));

		
			String documentDate = projectDocumentMasterModel.getDocumentDate();
			String documentFrom = projectDocumentMasterModel.getPeriodFrom();
			String documentTo = projectDocumentMasterModel.getPeriodTo();
			String description = projectDocumentMasterModel.getDescription();

			Calendar calendar = GregorianCalendar.getInstance();			
			String fileName= projectDocumentMasterModel.getProjectId()+"_"+calendar.getTimeInMillis();
			
			List<ProjectDocumentDetailsDomain> detailList = new ArrayList<ProjectDocumentDetailsDomain>();
			
			List<ProjectDocumentDetailsModel> detailModelList = projectDocumentMasterModel.getDetailsModels();
			int noOfFiles =0;
			for(int i=0;i<detailModelList.size();i++){				 
				ProjectDocumentDetailsModel model = detailModelList.get(i);				
				if(!model.getProjectDocumentFile().getOriginalFilename().equals("") && null !=model.getProjectDocumentFile().getOriginalFilename()){
					noOfFiles +=1;
					ProjectDocumentDetailsDomain detailDomain = new ProjectDocumentDetailsDomain();
					if(projectDocumentMasterModel.getNumId()!=0){						
						detailDomain = projectDocumentMasterDao.getProjectDocumentDetail(projectDocumentMasterModel.getNumId(), model.getDocumentFormatId());
						if(null == detailDomain){
							detailDomain= new ProjectDocumentDetailsDomain();
						}
					}
					ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectDocumentMasterModel.getProjectId());
					long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();
					String originalFileName= model.getProjectDocumentFile().getOriginalFilename();
					String extension = FilenameUtils.getExtension(originalFileName);
					String tempFileName = fileName +i+".";
					boolean uploadStatus = fileUploadService.uploadProjectFile(model.getProjectDocumentFile(), projectDocumentMasterModel.getProjectId(),groupId, tempFileName+extension);
					if(uploadStatus){
					detailDomain.setOriginalDocumentName(originalFileName);
					detailDomain.setDocumentName(tempFileName+extension);
					
					detailDomain.setDocumentFormatMaster(documentFormatService.getDocumentFormatById(model.getDocumentFormatId()));
					detailDomain.setProjectDocumentMasterDomain(projectDocumentMasterDomain);

					detailList.add(detailDomain);
					}
				}				
			}		
			try {
				projectDocumentMasterDomain.setDocumentDate(DateUtils.dateStrToDate(documentDate));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			
			if(detailList.size()>0){
				projectDocumentMasterDomain.setProjectDocumentDetailsDomainList(detailList);
			}else if(noOfFiles >0 && !(detailList.size()>0)){
				result = false;
				return result;
			}
			projectDocumentMasterDomain.setDtTrDate(new Date());
			projectDocumentMasterDomain.setNumIsValid(1);
			projectDocumentMasterDomain.setDescription(description);
			projectDocumentMasterDomain.setDocumentVersion(projectDocumentMasterModel.getDocumentVersion());;
			try {
				if(!documentFrom.equals("")){
					projectDocumentMasterDomain.setPeriodFrom(DateUtils.dateStrToDate(documentFrom));
				}
				if(!documentTo.equals("")){
					projectDocumentMasterDomain.setPeriodTo(DateUtils.dateStrToDate(documentTo));
				}				
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			projectDocumentMasterDao.merge(projectDocumentMasterDomain);	
			result= true;
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('VIEW_PROJECT_DOCUMENT')")
	public List<ProjectDocumentMasterModel> uploadedProjectDocument(ProjectDocumentMasterModel projectDocumentMasterModel){
		List<ProjectDocumentMasterModel> outputList = new ArrayList<ProjectDocumentMasterModel>();
		List<ProjectDocumentMasterDomain> dataList = projectDocumentMasterDao.uploadedProjectDocument(projectDocumentMasterModel);
		List<Long> duplicateData = new ArrayList<Long>();
		for(int i=0;i<dataList.size();i++){
			ProjectDocumentMasterDomain master = dataList.get(i);
			if(!duplicateData.contains(master.getNumId())){
				outputList.add(convertDomainToModel(master));
			}
			duplicateData.add(master.getNumId());
			
		}
		return outputList;
	}

	// GET PROJECT DOCUMENT BY PROJECT ID
	public List<ProjectDocumentMasterModel> getProjectDocumentByID(long projectId)
	{
		List<ProjectDocumentMasterModel> outputList = new ArrayList<ProjectDocumentMasterModel>();
		List<ProjectDocumentMasterDomain> dataList = projectDocumentMasterDao.getuploadedProjectDocumentByID(projectId);
		List<Long> duplicateData = new ArrayList<Long>();
		for(int i=0;i<dataList.size();i++){
			ProjectDocumentMasterDomain master = dataList.get(i);
			if(!duplicateData.contains(master.getNumId())){
				outputList.add(convertDomainToModel(master));
			}
			duplicateData.add(master.getNumId());
			
		}
		return outputList;
	}
	// End Changes
	
	@Override
	public ProjectDocumentDetailsDomain getProjectDocumentDetail(long uploadId){
		return  projectDocumentMasterDao.getProjectDocumentDetailsById(uploadId);
	}
	
	/*Added by devesh on 29-12-23 to get DocumentId from projectId for Reco Sheet Document*/
	@Override
	public ProjectDocumentMasterDomain getProjectDocumentIdFromProjectId(long projectId){
		return  projectDocumentMasterDao.getProjectDocumentIdFromProjectId(projectId);
	}
	/*End of Function*/
	
	@Override
	public ProjectDocumentDetailsDomain getProjectDocumentDetail(long parentId,long documentFormatId){
		return getProjectDocumentDetail(parentId,documentFormatId);
	}
	
	@Override	
	public List<ProjectDocumentMasterModel> uploadedDocumentByProjectId(long projectId){
		List<ProjectDocumentMasterDomain> documentsList = projectDocumentMasterDao.uploadedDocumentByProjectId(projectId);	
		return convertDomainListToModel(documentsList);
	}
	
	public ProjectDocumentMasterModel convertDomainToModel(ProjectDocumentMasterDomain master){
		ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
		model.setNumId(master.getNumId());
		model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));
		model.setDescription(master.getDescription());
		if(null != master.getDocumentDate() && !master.getDocumentDate().equals("")){
			model.setDocumentDate(DateUtils.dateToString(master.getDocumentDate()));
		}
		
		if(null != master.getPeriodFrom() && !master.getPeriodFrom().equals("")){
			model.setPeriodFrom(DateUtils.dateToString(master.getPeriodFrom()));
		}
		
		if(null != master.getPeriodTo() && !master.getPeriodTo().equals("")){
			model.setPeriodTo(DateUtils.dateToString(master.getPeriodTo()));
		}
		
		model.setDocumentVersion(master.getDocumentVersion());
		
		List<ProjectDocumentDetailsModel> childModelList = new ArrayList<ProjectDocumentDetailsModel>();
		
		List<ProjectDocumentDetailsDomain> childList = master.getProjectDocumentDetailsDomainList();
		for(int j=0;j<childList.size();j++){
			ProjectDocumentDetailsDomain childDomain = childList.get(j);
			ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
			childModel.setDocumentFormat(childDomain.getDocumentFormatMaster().getFormatName());
			childModel.setDocumentName(childDomain.getDocumentName());
			childModel.setIcon(childDomain.getDocumentFormatMaster().getIcon());
			childModel.setNumId(childDomain.getNumId());
			childModel.setEncNumId(encryptionService.encrypt(""+childDomain.getNumId()));
			childModelList.add(childModel);
		}
		/*----------------------- Get the Projects Data with Document Details [18-03-2024] ------------------------*/
		model.setStrProjectName(projectMasterDao.getProjectMasterDataById(master.getProjectId()).getStrProjectName());
		model.setDocumentTypeName(master.getDocumentTypeMasterDomain().getDocTypeName());
		model.setEncProjectId(encryptionService.encrypt(""+master.getProjectId()));
		model.setStrProjectRefNo(projectMasterDao.getProjectMasterDataById(master.getProjectId()).getStrProjectRefNo());
		/*----------------------- Get the Projects Data with Document Details [18-03-2024] ------------------------*/
		model.setDetailsModels(childModelList);
		return model;
	}
	
	public List<ProjectDocumentMasterModel> convertDomainListToModel(List<ProjectDocumentMasterDomain> masters){
		List<ProjectDocumentMasterModel> models = new ArrayList<ProjectDocumentMasterModel>();
		for(ProjectDocumentMasterDomain master : masters){
		ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
		model.setNumId(master.getNumId());
		model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));
		model.setDescription(master.getDescription());
		if(null != master.getDocumentDate() && !master.getDocumentDate().equals("")){
			model.setDocumentDate(DateUtils.dateToString(master.getDocumentDate()));
			model.setUploadedOn(master.getDocumentDate());
		}
		
		if(null != master.getPeriodFrom() && !master.getPeriodFrom().equals("")){
			model.setPeriodFrom(DateUtils.dateToString(master.getPeriodFrom()));
		}
		
		if(null != master.getPeriodTo() && !master.getPeriodTo().equals("")){
			model.setPeriodTo(DateUtils.dateToString(master.getPeriodTo()));
		}
		/*model.setProjectId(master.getProjectId());
		System.out.println("projectId"+master.getProjectId());
		model.setEncProjectId(encryptionService.encrypt(""+master.getProjectId()));
		model.setDocumentVersion(model.getDocumentVersion());*/
		model.setDocumentVersion(master.getDocumentVersion());
		List<ProjectDocumentDetailsModel> childModelList = new ArrayList<ProjectDocumentDetailsModel>();
		
		List<ProjectDocumentDetailsDomain> childList = master.getProjectDocumentDetailsDomainList();
		for(int j=0;j<childList.size();j++){
			ProjectDocumentDetailsDomain childDomain = childList.get(j);
			ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
			childModel.setDocumentFormat(childDomain.getDocumentFormatMaster().getFormatName());
			childModel.setDocumentName(childDomain.getDocumentName());
			childModel.setIcon(childDomain.getDocumentFormatMaster().getIcon());
			childModel.setNumId(childDomain.getNumId());
			childModel.setEncNumId(encryptionService.encrypt(""+childDomain.getNumId()));
			childModelList.add(childModel);
		}
		
		model.setDetailsModels(childModelList);
		try{
		if(null != master.getDocumentTypeMasterDomain()){
			model.setDocumentTypeName(master.getDocumentTypeMasterDomain().getDocTypeName());
			model.setDocumentTypeId(master.getDocumentTypeMasterDomain().getNumId());
			model.setIcon(master.getDocumentTypeMasterDomain().getIcon());
		}
		}catch(Exception e){
			log.error(""+e.getCause());
			log.error(e.getMessage());
			
		}
		
		models.add(model);
		}
		return models;
	}

	@Override
	public List<ProjectDocumentMasterModel> showProjectDocumentRevision(
			String encDocumentId) {
		String strDocumentId = encryptionService.dcrypt(encDocumentId);
		long documentId =Long.parseLong(strDocumentId);
		return convertDomainListToModel(projectDocumentMasterDao.showProjectDocumentRevision(documentId));
	}
	
	@Override
	public List<ProjectDocumentMasterModel> getProjectDocumentForDashboard(long projectId){		
		List<Object[]> dataList = projectDocumentMasterDao.getProjectDocumentForDashboard(projectId);
		List<ProjectDocumentMasterModel> finalList = new ArrayList<ProjectDocumentMasterModel>();
			for(Object[] object : dataList)	{
				ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
				model.setDocumentTypeName(""+object[1]);
				model.setEncNumId(encryptionService.encrypt(""+object[0]));
				
				model.setClassColor(""+object[2]);
				model.setDocumentTypeId((long)object[3]);
				model.setUploadedFor("Project");
				finalList.add(model);
			}
		return finalList;
		
	}
	
	@Override
	public boolean deleteProposalDocument(String parentDocumentId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectDocumentMasterModel projectDocumentMasterModel = new ProjectDocumentMasterModel();
		projectDocumentMasterModel.setNumId(Long.parseLong(encryptionService.dcrypt(parentDocumentId)));
		ProjectDocumentMasterDomain projectDocumentMasterDomain = projectDocumentMasterDao.uploadedProjectDocumentById(projectDocumentMasterModel);
		if(null != projectDocumentMasterDomain){
		projectDocumentMasterDomain.setNumIsValid(2);
		projectDocumentMasterDomain.setDtTrDate(new Date());
		projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectDocumentMasterDomain = projectDocumentMasterDao.merge(projectDocumentMasterDomain);
		if(projectDocumentMasterDomain.getNumId()>0){
			return true;
		}else{
			return false;
		}
	}
		else{
			return true;
		}
	}
	
	@Override
	@PreAuthorize("hasAuthority('UPLOAD_PROJECT_DOCUMENT')")
	public boolean uploadProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel){	
		
			boolean result = false;
			try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			ProjectDocumentMasterDomain projectDocumentMasterDomain = new ProjectDocumentMasterDomain();
			
			String strProjectId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncProjectId());
			long projectId = Long.parseLong(strProjectId);
			projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			projectDocumentMasterDomain.setProjectId(projectId);
			
			String strDocumentTypeId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentTypeId());
			long documentTypeId = Long.parseLong(strDocumentTypeId);
			projectDocumentMasterDomain.setDocumentTypeMasterDomain(documentTypeMasterService.getDocumentTypeMasterDetailById(documentTypeId));


				Calendar calendar = GregorianCalendar.getInstance();			
				String fileName= strProjectId+"_"+calendar.getTimeInMillis();
				
				List<ProjectDocumentDetailsDomain> detailList = new ArrayList<ProjectDocumentDetailsDomain>();
				int noOfFiles = 0;
							
					if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
						ProjectDocumentDetailsDomain detailDomain = new ProjectDocumentDetailsDomain();
						noOfFiles+=1;
						ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
						long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();						
						String originalFileName= projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename();
						String extension = FilenameUtils.getExtension(originalFileName);
						String tempFileName = fileName+".";
						boolean uploadStatus = fileUploadService.uploadProjectFile(projectDocumentDetailsModel.getProjectDocumentFile(), projectId,groupId, tempFileName+extension);
						if(uploadStatus){
						detailDomain.setOriginalDocumentName(originalFileName);
						detailDomain.setDocumentName(tempFileName+extension);
						
						
						String strFormatId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
						int formatId = Integer.parseInt(strFormatId);
						
						detailDomain.setDocumentFormatMaster(documentFormatService.getDocumentFormatById(formatId));
						detailDomain.setProjectDocumentMasterDomain(projectDocumentMasterDomain);

						detailList.add(detailDomain);
						}
					}				
								
				if(detailList.size()>0){
					projectDocumentMasterDomain.setProjectDocumentDetailsDomainList(detailList);
				}else if(noOfFiles >0 && !(detailList.size()>0)){
					result = false;
					return result;
				}
				projectDocumentMasterDomain.setDtTrDate(new Date());
				projectDocumentMasterDomain.setNumIsValid(1);
				
				projectDocumentMasterDomain.setDocumentVersion("1");
				
				projectDocumentMasterDao.merge(projectDocumentMasterDomain);	
				result= true;
			}catch(Exception e){
				e.printStackTrace();
				result=false;
			}
			return result;
		}
	
	@Override
	public Map<String,Map<String,List<ProjectDocumentMasterModel>>> documentDetailsCategoryWiseByProjectId(long projectId){
		Map<String,Map<String,List<ProjectDocumentMasterModel>>> dataMap = new LinkedHashMap<String, Map<String,List<ProjectDocumentMasterModel>>>();
		List<ProjectDocumentMasterDomain> dataList = projectDocumentMasterDao.documentDetailsCategoryWiseByProjectId(projectId);
		if(null != dataList && dataList.size()>0){
			for(ProjectDocumentMasterDomain master : dataList){
			ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
			model.setNumId(master.getNumId());
			model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));
			model.setDescription(master.getDescription());
			if(null != master.getDocumentDate() && !master.getDocumentDate().equals("")){
				model.setDocumentDate(DateUtils.dateToString(master.getDocumentDate()));
				model.setUploadedOn(master.getDocumentDate());
			}
			
			if(null != master.getPeriodFrom() && !master.getPeriodFrom().equals("")){
				model.setPeriodFrom(DateUtils.dateToString(master.getPeriodFrom()));
			}
			
			if(null != master.getPeriodTo() && !master.getPeriodTo().equals("")){
				model.setPeriodTo(DateUtils.dateToString(master.getPeriodTo()));
			}
			
			model.setDocumentVersion(master.getDocumentVersion());
			List<ProjectDocumentDetailsModel> childModelList = new ArrayList<ProjectDocumentDetailsModel>();
			
			List<ProjectDocumentDetailsDomain> childList = master.getProjectDocumentDetailsDomainList();
			for(int j=0;j<childList.size();j++){
				ProjectDocumentDetailsDomain childDomain = childList.get(j);
				ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
				childModel.setDocumentFormat(childDomain.getDocumentFormatMaster().getFormatName());
				childModel.setDocumentName(childDomain.getDocumentName());
				childModel.setIcon(childDomain.getDocumentFormatMaster().getIcon());
				childModel.setNumId(childDomain.getNumId());
				childModel.setEncNumId(encryptionService.encrypt(""+childDomain.getNumId()));
				childModelList.add(childModel);
			}
			
			model.setDetailsModels(childModelList);
			try{
			DocumentTypeMasterDomain documentTypeMasterDomain =master.getDocumentTypeMasterDomain();
			if(null != documentTypeMasterDomain){
				String documentTypeName = documentTypeMasterDomain.getDocTypeName();
				model.setDocumentTypeName(documentTypeName);
				model.setDocumentTypeId(documentTypeMasterDomain.getNumId());
				model.setIcon(documentTypeMasterDomain.getIcon());
				DocumentClassificationDomain classificationDomain =documentTypeMasterDomain.getDocumentClassification();
				if(null != classificationDomain){
					String classificationName = classificationDomain.getClassificationName();
					if(dataMap.containsKey(classificationName)){
						Map<String, List<ProjectDocumentMasterModel>> innerMap = dataMap.get(classificationName);
						if(innerMap.containsKey(documentTypeName)){
							List<ProjectDocumentMasterModel> tempList = innerMap.get(documentTypeName);
							tempList.add(model);
							innerMap.put(documentTypeName, tempList);
							dataMap.put(classificationName, innerMap);
						}else{
							List<ProjectDocumentMasterModel> tempList = new ArrayList<ProjectDocumentMasterModel>();
							tempList.add(model);
							innerMap.put(documentTypeName, tempList);
							dataMap.put(classificationName, innerMap);
						}
					}else{
						Map<String, List<ProjectDocumentMasterModel>> innerMap = new LinkedHashMap<String, List<ProjectDocumentMasterModel>>();
						
							List<ProjectDocumentMasterModel> tempList = new ArrayList<ProjectDocumentMasterModel>();
							tempList.add(model);
							innerMap.put(documentTypeName, tempList);
							dataMap.put(classificationName, innerMap);						
					}
				}else{
					if(dataMap.containsKey("Others")){
						Map<String, List<ProjectDocumentMasterModel>> innerMap = dataMap.get("Others");						
						if(innerMap.containsKey(documentTypeName)){
							List<ProjectDocumentMasterModel> tempList = innerMap.get(documentTypeName);
							tempList.add(model);
							innerMap.put(documentTypeName, tempList);
							dataMap.put("Others", innerMap);
						}else{					
							List<ProjectDocumentMasterModel> tempList = new ArrayList<ProjectDocumentMasterModel>();
							tempList.add(model);
							innerMap.put(documentTypeName, tempList);
							dataMap.put("Others", innerMap);						
						}
					}else{
						Map<String, List<ProjectDocumentMasterModel>> innerMap = new LinkedHashMap<String, List<ProjectDocumentMasterModel>>();
						
						List<ProjectDocumentMasterModel> tempList = new ArrayList<ProjectDocumentMasterModel>();
						tempList.add(model);
						innerMap.put(documentTypeName, tempList);
						dataMap.put("Others", innerMap);	
					}									
				}
			}
			}catch(Exception e){
				log.error(""+e.getCause());
				log.error(e.getMessage());
				
			}		
		 }
			
		}
		return dataMap;
	}
	
	/*@Override
	public List<ProjectDocumentMasterModel> getProjectDocumentWithDocTypeId(long projectId,List<DocumentTypeMasterModel> doc){
		List<Long> docTypeIds = new ArrayList<Long>();
		for(int i=0;i<doc.size();i++){
			long numId=doc.get(i).getNumId();
			docTypeIds.add(numId);
		}
		
		List<Object[]> dataList = monthlyProgressDao.getProjectDocumentWithDocTypeId(projectId,docTypeIds);
		List<ProjectDocumentMasterModel> finalList = new ArrayList<ProjectDocumentMasterModel>();
			for(Object[] object : dataList)	{
				ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
				model.setDocumentTypeName(""+object[1]);
				model.setEncNumId(encryptionService.encrypt(""+object[0]));
				
				model.setClassColor(""+object[2]);
				model.setDocumentTypeId((long)object[3]);
				model.setUploadedFor("Project");
				finalList.add(model);
			}
		return finalList;
		
	}*/
	
	public boolean uploadTempProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel){	
		
		boolean result = false;
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		TempProjectDocumentMasterDomain projectDocumentMasterDomain = new TempProjectDocumentMasterDomain();
		
		String strProjectId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncProjectId());
		long projectId = Long.parseLong(strProjectId);
		projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectDocumentMasterDomain.setProjectId(projectId);
		
		String strDocumentTypeId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentTypeId());
		long documentTypeId = Long.parseLong(strDocumentTypeId);
		projectDocumentMasterDomain.setDocumentTypeMasterDomain(documentTypeMasterService.getDocumentTypeMasterDetailById(documentTypeId));


			Calendar calendar = GregorianCalendar.getInstance();			
			String fileName= strProjectId+"_"+calendar.getTimeInMillis();
			
			List<TempProjectDocumentDetailsDomain> detailList = new ArrayList<TempProjectDocumentDetailsDomain>();
			int noOfFiles = 0;
						
				if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
					TempProjectDocumentDetailsDomain detailDomain = new TempProjectDocumentDetailsDomain();
					noOfFiles+=1;
					ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
					long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();						
					String originalFileName= projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename();
					String extension = FilenameUtils.getExtension(originalFileName);
					String tempFileName = fileName+".";
					boolean uploadStatus = fileUploadService.uploadProjectFile(projectDocumentDetailsModel.getProjectDocumentFile(), projectId,groupId, tempFileName+extension);
					if(uploadStatus){
					detailDomain.setOriginalDocumentName(originalFileName);
					detailDomain.setDocumentName(tempFileName+extension);
					
					
					String strFormatId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
					int formatId = Integer.parseInt(strFormatId);
					
					detailDomain.setDocumentFormatMaster(documentFormatService.getDocumentFormatById(formatId));
					detailDomain.setProjectDocumentMasterDomain(projectDocumentMasterDomain);

					detailList.add(detailDomain);
					}
				}				
							
			if(detailList.size()>0){
				projectDocumentMasterDomain.setProjectDocumentDetailsDomainList(detailList);
			}else if(noOfFiles >0 && !(detailList.size()>0)){
				result = false;
				return result;
			}
			projectDocumentMasterDomain.setDtTrDate(new Date());
			projectDocumentMasterDomain.setNumIsValid(1);
			
			projectDocumentMasterDomain.setDocumentVersion("1");
			
			projectDocumentMasterDao.merge(projectDocumentMasterDomain);	
			result= true;
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	@Override	
	public List<ProjectDocumentMasterModel> uploadedTempDocumentByProjectId(long projectId,List<DocumentTypeMasterModel> doc){
		List<Long> docTypeIds = new ArrayList<Long>();
		for(int i=0;i<doc.size();i++){
			long numId=doc.get(i).getNumId();
			docTypeIds.add(numId);
		}
		List<TempProjectDocumentMasterDomain> documentsList = monthlyProgressDao.uploadedTempDocumentByProjectId(projectId,docTypeIds);	
		return convertTempDomainListToModel(documentsList);
	}
	
	
	public List<ProjectDocumentMasterModel> convertTempDomainListToModel(List<TempProjectDocumentMasterDomain> masters){
		List<ProjectDocumentMasterModel> models = new ArrayList<ProjectDocumentMasterModel>();
		for(TempProjectDocumentMasterDomain master : masters){
		ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
		model.setNumId(master.getNumId());
		model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));
		model.setDescription(master.getDescription());
		model.setProjectId(master.getProjectId());
		if(null != master.getDocumentDate() && !master.getDocumentDate().equals("")){
			model.setDocumentDate(DateUtils.dateToString(master.getDocumentDate()));
			model.setUploadedOn(master.getDocumentDate());
		}
		
		if(null != master.getPeriodFrom() && !master.getPeriodFrom().equals("")){
			model.setPeriodFrom(DateUtils.dateToString(master.getPeriodFrom()));
		}
		
		if(null != master.getPeriodTo() && !master.getPeriodTo().equals("")){
			model.setPeriodTo(DateUtils.dateToString(master.getPeriodTo()));
		}
		
		model.setDocumentVersion(master.getDocumentVersion());
		List<ProjectDocumentDetailsModel> childModelList = new ArrayList<ProjectDocumentDetailsModel>();
		
		List<TempProjectDocumentDetailsDomain> childList = master.getProjectDocumentDetailsDomainList();
		for(int j=0;j<childList.size();j++){
			TempProjectDocumentDetailsDomain childDomain = childList.get(j);
			ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
			childModel.setDocumentFormat(childDomain.getDocumentFormatMaster().getFormatName());
			childModel.setDocumentName(childDomain.getDocumentName());
			childModel.setIcon(childDomain.getDocumentFormatMaster().getIcon());
			childModel.setNumId(childDomain.getNumId());
			childModel.setEncNumId(encryptionService.encrypt(""+childDomain.getNumId()));
			childModelList.add(childModel);
		}
		
		model.setDetailsModels(childModelList);
		try{
		if(null != master.getDocumentTypeMasterDomain()){
			model.setDocumentTypeName(master.getDocumentTypeMasterDomain().getDocTypeName());
			model.setDocumentTypeId(master.getDocumentTypeMasterDomain().getNumId());
			model.setIcon(master.getDocumentTypeMasterDomain().getIcon());
		}
		}catch(Exception e){
			log.error(""+e.getCause());
			log.error(e.getMessage());
			
		}
		
		models.add(model);
		}
		return models;
	}
	
	@Override
	public TempProjectDocumentDetailsDomain getTempProjectDocumentDetailsById(long uploadId){
		return  projectDocumentMasterDao.getTempProjectDocumentDetailsById(uploadId);
	}
	
	@Override
	public List<TempProjectDocumentMasterDomain> getTempProjectDocumentMasterDetails(long projectId, int docTypeId){
		return  projectDocumentMasterDao.getTempProjectDocumentMasterDetails(projectId,docTypeId);
	}
	
public boolean updateUploadTempProjectDocument(ProjectDocumentDetailsModel projectDocumentDetailsModel){	
		
		boolean result = false;
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		TempProjectDocumentMasterDomain projectDocumentMasterDomain = new TempProjectDocumentMasterDomain();
		
		String strProjectId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncProjectId());
		long projectId = Long.parseLong(strProjectId);
		String strDocumentTypeId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentTypeId());
		long documentTypeId = Long.parseLong(strDocumentTypeId);
		String strFormatId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
		int formatId = Integer.parseInt(strFormatId);
		List<TempProjectDocumentMasterDomain> detailsOfTemp=projectDocumentMasterDao.getDetailsOfTempProjectDoc(projectId,userInfo.getEmployeeId(),documentTypeId,formatId);
		if(detailsOfTemp.size()>0){
			for(int i=0;i<detailsOfTemp.size();i++){
				TempProjectDocumentMasterDomain data=detailsOfTemp.get(i);
				data.setNumIsValid(0);
				projectDocumentMasterDao.merge(data);
			}
		}
		projectDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectDocumentMasterDomain.setProjectId(projectId);
		
		
		projectDocumentMasterDomain.setDocumentTypeMasterDomain(documentTypeMasterService.getDocumentTypeMasterDetailById(documentTypeId));


			Calendar calendar = GregorianCalendar.getInstance();			
			String fileName= strProjectId+"_"+calendar.getTimeInMillis();
			
			List<TempProjectDocumentDetailsDomain> detailList = new ArrayList<TempProjectDocumentDetailsDomain>();
			int noOfFiles = 0;
						
				if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
					TempProjectDocumentDetailsDomain detailDomain = new TempProjectDocumentDetailsDomain();
					noOfFiles+=1;
					ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
					long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();						
					String originalFileName= projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename();
					String extension = FilenameUtils.getExtension(originalFileName);
					String tempFileName = fileName+".";
					boolean uploadStatus = fileUploadService.uploadProjectFile(projectDocumentDetailsModel.getProjectDocumentFile(), projectId,groupId, tempFileName+extension);
					if(uploadStatus){
					detailDomain.setOriginalDocumentName(originalFileName);
					detailDomain.setDocumentName(tempFileName+extension);
					
					
					
					
					detailDomain.setDocumentFormatMaster(documentFormatService.getDocumentFormatById(formatId));
					detailDomain.setProjectDocumentMasterDomain(projectDocumentMasterDomain);

					detailList.add(detailDomain);
					}
				}				
							
			if(detailList.size()>0){
				projectDocumentMasterDomain.setProjectDocumentDetailsDomainList(detailList);
			}else if(noOfFiles >0 && !(detailList.size()>0)){
				result = false;
				return result;
			}
			projectDocumentMasterDomain.setDtTrDate(new Date());
			projectDocumentMasterDomain.setNumIsValid(1);
			
			projectDocumentMasterDomain.setDocumentVersion("1");
			
			projectDocumentMasterDao.merge(projectDocumentMasterDomain);	
			result= true;
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		}
		return result;
	}

	/*----------------------- Service function for get Projects Document Details [18-03-2024] ------------------------*/	
	@Override
	@PreAuthorize("hasAuthority('VIEW_PROJECT_DOCUMENT')")
	public List<ProjectDocumentMasterModel> getUploadedProjectDocumentForTile(ProjectDocumentMasterModel projectDocumentMasterModel){    
		List<ProjectDocumentMasterModel> outputList = new ArrayList<ProjectDocumentMasterModel>();
		List<ProjectDocumentMasterDomain> dataList = projectDocumentMasterDao.getUploadedProjectDocumentForTile(projectDocumentMasterModel);
		List<Long> duplicateData = new ArrayList<Long>();
		for(int i=0;i<dataList.size();i++){
			ProjectDocumentMasterDomain master = dataList.get(i);
			if(!duplicateData.contains(master.getNumId())){
				outputList.add(convertDomainToModel(master));
			}
			duplicateData.add(master.getNumId());
			
		}
		return outputList;
	}
}
