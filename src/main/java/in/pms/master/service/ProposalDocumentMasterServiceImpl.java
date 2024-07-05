package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProposalDocumentMasterDao;
import in.pms.master.domain.DocumentClassificationDomain;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
//import in.pms.master.domain.ProjectDocumentDetailsDomain;
//import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.ProposalDocumentDetailsDomain;
import in.pms.master.domain.ProposalDocumentMasterDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.transaction.domain.Application;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProposalDocumentMasterServiceImpl implements ProposalDocumentMasterService{
	
	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	
	@Autowired
	ProposalDocumentMasterDao proposalDocumentMasterDao;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	DocumentFormatService documentFormatService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProposalMasterService proposalMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;

	@Override
	public boolean uploadProposalDocument(ProjectDocumentMasterModel projectDocumentMasterModel) {
		boolean result = false;
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			ProposalDocumentMasterDomain proposalDocumentMasterDomain = new ProposalDocumentMasterDomain();
			
			if(projectDocumentMasterModel.getNumId() != 0){
				if(null != proposalDocumentMasterDao.uploadedProposalDocumentById(projectDocumentMasterModel))
					proposalDocumentMasterDomain =proposalDocumentMasterDao.uploadedProposalDocumentById(projectDocumentMasterModel);
			}
			proposalDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			proposalDocumentMasterDomain.setProposalId(projectDocumentMasterModel.getProposalId());
			proposalDocumentMasterDomain.setDocumentTypeMasterDomain(documentTypeMasterService.getDocumentTypeMasterDetailById(projectDocumentMasterModel.getDocumentTypeId()));

			
				String documentDate = projectDocumentMasterModel.getDocumentDate();
				String documentFrom = projectDocumentMasterModel.getPeriodFrom();
				String documentTo = projectDocumentMasterModel.getPeriodTo();
				String description = projectDocumentMasterModel.getDescription();

				Calendar calendar = GregorianCalendar.getInstance();			
				String fileName= projectDocumentMasterModel.getProposalId()+"_"+calendar.getTimeInMillis();
				
				List<ProposalDocumentDetailsDomain> detailList = new ArrayList<ProposalDocumentDetailsDomain>();
				
				List<ProjectDocumentDetailsModel> detailModelList = projectDocumentMasterModel.getDetailsModels();
				int noOfFiles = 0;
				for(int i=0;i<detailModelList.size();i++){
					ProjectDocumentDetailsModel model = detailModelList.get(i);				
					if(!model.getProjectDocumentFile().getOriginalFilename().equals("") && null !=model.getProjectDocumentFile().getOriginalFilename()){
						noOfFiles +=1;
						ProposalDocumentDetailsDomain detailDomain = new ProposalDocumentDetailsDomain();
						if(projectDocumentMasterModel.getNumId()!=0){						
							detailDomain = proposalDocumentMasterDao.getProposalDocumentDetail(projectDocumentMasterModel.getNumId(), model.getDocumentFormatId());
							if(null == detailDomain){
								detailDomain= new ProposalDocumentDetailsDomain();
							}
						}
						ProposalMasterDomain proposalMasterDomain=proposalMasterService.getProposalDomainById(projectDocumentMasterModel.getProposalId());
						long applicationId=proposalMasterDomain.getApplication().getNumId();
						long groupId=proposalMasterDomain.getApplication().getGroupMaster().getNumId();
						String originalFileName= model.getProjectDocumentFile().getOriginalFilename();
						String extension = FilenameUtils.getExtension(originalFileName);
						String tempFileName = fileName +i+".";
						boolean uploadStatus = fileUploadService.uploadProposalFile(model.getProjectDocumentFile(), projectDocumentMasterModel.getProposalId(),groupId, tempFileName+extension);
						
						if(uploadStatus){
						detailDomain.setOriginalDocumentName(originalFileName);
						detailDomain.setDocumentName(tempFileName+extension);
						//Added by devesh on 03-10-23 to set flag=1 for rev id
						detailDomain.setRevflag(1);
						//End of setting flag
						detailDomain.setDocumentFormatMaster(documentFormatService.getDocumentFormatById(model.getDocumentFormatId()));
						detailDomain.setProposalDocumentMasterDomain(proposalDocumentMasterDomain);

						detailList.add(detailDomain);
						}
					}				
				}		
				try {
					proposalDocumentMasterDomain.setDocumentDate(DateUtils.dateStrToDate(documentDate));
				} catch (ParseException e) {				
					e.printStackTrace();
				}
				
				if(detailList.size()>0){
					proposalDocumentMasterDomain.setProposalDocumentDetailsDomainList(detailList);
				}else if(noOfFiles>0 && !(detailList.size()>0)){
					result = false;
					return result;
				}
				proposalDocumentMasterDomain.setDtTrDate(new Date());
				proposalDocumentMasterDomain.setNumIsValid(1);
				proposalDocumentMasterDomain.setDescription(description);
				proposalDocumentMasterDomain.setDocumentVersion(projectDocumentMasterModel.getDocumentVersion());
				//Added by devesh on 03-10-23 to set flag=1 for rev id
				proposalDocumentMasterDomain.setRevflag(1);
				//End of setting flag
				try {
					if(!documentFrom.equals("")){
						proposalDocumentMasterDomain.setPeriodFrom(DateUtils.dateStrToDate(documentFrom));
					}
					if(!documentTo.equals("")){
						proposalDocumentMasterDomain.setPeriodTo(DateUtils.dateStrToDate(documentTo));
					}				
				} catch (ParseException e) {				
					e.printStackTrace();
				}
				proposalDocumentMasterDao.merge(proposalDocumentMasterDomain);	
				result= true;
			}catch(Exception e){
				result=false;
			}
		return result;
	}

	@Override
	public void downloadProposalDocument(long documentId, String documentType) {
	
		
	}
	
	@Override
	public List<ProjectDocumentMasterModel> uploadedProposalDocument(ProjectDocumentMasterModel projectDocumentMasterModel){
		List<ProjectDocumentMasterModel> outputList = new ArrayList<ProjectDocumentMasterModel>();
		List<ProposalDocumentMasterDomain> dataList = proposalDocumentMasterDao.uploadedProposalDocument(projectDocumentMasterModel);
		List<Long> duplicateData = new ArrayList<Long>();
		for(int i=0;i<dataList.size();i++){
			ProposalDocumentMasterDomain master = dataList.get(i);
			if(!duplicateData.contains(master.getNumId())){
				outputList.add(convertDomainToModel(master));
			}
			duplicateData.add(master.getNumId());
			
		}
		return outputList;
	}

	public ProjectDocumentMasterModel convertDomainToModel(ProposalDocumentMasterDomain master){
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
		
		List<ProposalDocumentDetailsDomain> childList = master.getProposalDocumentDetailsDomainList();
		for(int j=0;j<childList.size();j++){
			ProposalDocumentDetailsDomain childDomain = childList.get(j);
			ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
			childModel.setDocumentFormat(childDomain.getDocumentFormatMaster().getFormatName());
			childModel.setDocumentName(childDomain.getDocumentName());
			childModel.setIcon(childDomain.getDocumentFormatMaster().getIcon());
			childModel.setNumId(childDomain.getNumId());
			childModel.setEncNumId(encryptionService.encrypt(""+childDomain.getNumId()));
			childModelList.add(childModel);
		}
		
		model.setDetailsModels(childModelList);
		return model;
	}
	public List<ProjectDocumentMasterModel> convertDomainListToModel(List<ProposalDocumentMasterDomain> masters){
   List<ProjectDocumentMasterModel> models = new ArrayList<ProjectDocumentMasterModel>();
		for(ProposalDocumentMasterDomain master : masters){
		ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
		model.setNumId(master.getNumId());
		model.setUploadedFor("Proposal");
		model.setEncNumId(encryptionService.encrypt(""+master.getNumId()));
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
		
		model.setDescription(master.getDescription());
		model.setDocumentVersion(master.getDocumentVersion());
		
		List<ProjectDocumentDetailsModel> childModelList = new ArrayList<ProjectDocumentDetailsModel>();
		List<ProposalDocumentDetailsDomain> childList = master.getProposalDocumentDetailsDomainList();
		for(int j=0;j<childList.size();j++){
			ProposalDocumentDetailsDomain childDomain = childList.get(j);
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
	
	
	
	
	public List<ProposalMasterDomain> getApplicationIdbyProposalId(long proposalId) {
		return proposalDocumentMasterDao.getApplicationIdbyProposalId( proposalId);
		

		}
		
	@Override
	public ProposalDocumentDetailsDomain getProposalDocumentDetail(long uploadId){
		return  proposalDocumentMasterDao.getProposalDocumentDetailsById(uploadId);
	}
	
	//Added by devesh on 27-09-23 to get Document by Rev Id
	@Override
	public List<Object[]> getProposalDocumentDetailbyRevId(long uploadId, long numId){
		return  proposalDocumentMasterDao.getProposalDocumentDetailsbyRevId(uploadId, numId);
	}
	//End of Retrieving Document by Rev Id
	
	@Override	
	public List<ProjectDocumentMasterModel> documentsByProposalId(long proposalId){
		List<ProposalDocumentMasterDomain> proposalDocumentList = proposalDocumentMasterDao.uploadedDocumentByProposalId(proposalId);	
		if(proposalDocumentList != null){
		return convertDomainListToModel(proposalDocumentList);
		}
		else
		{
			return null;
		}
		//return null;
	}
	
	//Added by devesh on 26-09-23 to get document history
	@Override	
	public List<ProjectDocumentMasterModel> documentsHistoryByRevId(long numId){
		List<Object[]> proposalDocumentList = proposalDocumentMasterDao.uploadedDocumentHistoryByRevId(numId);
		List<ProjectDocumentMasterModel> models = new ArrayList<>();
		if(proposalDocumentList != null){
			for (Object[] row : proposalDocumentList) {
				BigInteger a=new BigInteger(""+row[1]);
				BigInteger b=new BigInteger(""+row[23]);
				BigInteger c=new BigInteger(""+row[26]);
	            ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
	            model.setRevId(c.longValue());
	            model.setNumId(a.longValue()); // Assuming the ID is in the first column
	            model.setUploadedFor("Proposal");
	            model.setEncNumId(encryptionService.encrypt(""+row[1]));

	            if (row[6] != null) {
	                model.setDocumentDate(DateUtils.dateToString((Date) row[6]));
	                model.setUploadedOn((Date) row[6]);
	            }
	            
	            if (row[9] != null) {
	                model.setPeriodFrom(DateUtils.dateToString((Date) row[9]));
	            }
	            
	            if (row[10] != null) {
	                model.setPeriodTo(DateUtils.dateToString((Date) row[10]));
	            }
	            
	            model.setDescription((String) row[5]);
	    		model.setDocumentVersion((String) row[8]);

	            // Create and populate child models
	            List<ProjectDocumentDetailsModel> childModelList = new ArrayList<>();
	            // Assuming you have a List<Object[]> for child data as well
	                ProjectDocumentDetailsModel childModel = new ProjectDocumentDetailsModel();
	                childModel.setDocumentFormat((String) row[17]); // Assuming format is in the first column
	                childModel.setDocumentName((String) row[18]); // Assuming name is in the second column
	                childModel.setIcon((String) row[19]); // Assuming icon is in the third column
	                childModel.setNumId(b.longValue()); // Assuming ID is in the fourth column
	                childModel.setEncNumId(encryptionService.encrypt(""+row[23]));
	                childModelList.add(childModel);

	            model.setDetailsModels(childModelList);

	            try{
	        		if(null != row[7]){
	        			model.setDocumentTypeName((String) row[24]);
	        			model.setDocumentTypeId((Integer) row[7]);
	        			model.setIcon((String) row[25]);
	        		}
	        		}catch(Exception e){
	        			log.error(""+e.getCause());
	        			log.error(e.getMessage());
	        			
	        		}
	            // Add the model to the list
	            models.add(model);
	        }
		    return models;
	    }
		else
		{
			return null;
		}
		//return null;
	}
	//End of document history
	
	@Override
	public boolean deleteProposalDocument(String parentDocumentId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectDocumentMasterModel projectDocumentMasterModel = new ProjectDocumentMasterModel();
		projectDocumentMasterModel.setNumId(Long.parseLong(encryptionService.dcrypt(parentDocumentId)));
		ProposalDocumentMasterDomain proposalDocumentMasterDomain = proposalDocumentMasterDao.uploadedProjectDocumentById(projectDocumentMasterModel);
		if(null != proposalDocumentMasterDomain){
			proposalDocumentMasterDomain.setNumIsValid(2);
			proposalDocumentMasterDomain.setDtTrDate(new Date());
			proposalDocumentMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			proposalDocumentMasterDomain = proposalDocumentMasterDao.merge(proposalDocumentMasterDomain);;
			if(proposalDocumentMasterDomain.getNumId()>0){
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
	public List<ProjectDocumentMasterModel> showProposalDocumentRevision(
			String encDocumentId) {
		String strDocumentId = encryptionService.dcrypt(encDocumentId);
		long documentId =Long.parseLong(strDocumentId);
		return convertDomainListToModel(proposalDocumentMasterDao.showProposalDocumentRevision(documentId));
	}
	
	@Override
	public List<ProjectDocumentMasterModel> getProposalDocumentForDashboard(long proposalId,long docTypeId){		
		List<Object[]> dataList = proposalDocumentMasterDao.getProposalDocumentForDashboard(proposalId,docTypeId);
		List<ProjectDocumentMasterModel> finalList = new ArrayList<ProjectDocumentMasterModel>();
			for(Object[] object : dataList)	{
				ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
				model.setDocumentTypeName(""+object[1]);
				model.setEncNumId(encryptionService.encrypt(""+object[0]));
				model.setClassColor(""+object[2]);
				model.setUploadedFor("Proposal");
				finalList.add(model);
			}
		return finalList;
		
	}
	
	@Override
	public Map<String,Map<String,List<ProjectDocumentMasterModel>>> documentDetailsCategoryWiseByProposalId(long proposalId){
		Map<String,Map<String,List<ProjectDocumentMasterModel>>> dataMap = new LinkedHashMap<String, Map<String,List<ProjectDocumentMasterModel>>>();
		List<ProposalDocumentMasterDomain> dataList = proposalDocumentMasterDao.documentDetailsCategoryWiseByProposalId(proposalId);
		if(null != dataList && dataList.size()>0){
			for(ProposalDocumentMasterDomain master : dataList){
			ProjectDocumentMasterModel model = new ProjectDocumentMasterModel();
			model.setNumId(master.getNumId());
			model.setUploadedFor("Proposal");
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
			
			List<ProposalDocumentDetailsDomain> childList = master.getProposalDocumentDetailsDomainList();
			for(int j=0;j<childList.size();j++){
				ProposalDocumentDetailsDomain childDomain = childList.get(j);
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
	
	// serviceImpl for count the document from proposaldocumentmaster table by proposal id 
	@Override
	public long checkDocumentMaster(long proposalId) {
		List<Long> count = proposalDocumentMasterDao
				.checkDocumentMaster(proposalId);
		return count.get(0);
	}
	/*----- Function for get the proposal document details according to project ID [18-03-2024] ------*/
	@Override
	public List<ProjectDocumentMasterModel> documentDetailsProposal(ProjectDocumentMasterModel projectDocumentMasterModel){
		List<ProjectDocumentMasterModel> dataMap = new ArrayList<>();
		List<ProposalDocumentMasterDomain> dataList = proposalDocumentMasterDao.documentDetails(projectDocumentMasterModel);
		if(null != dataList && dataList.size()>0){
			List<Long> duplicateData = new ArrayList<>();
			for(ProposalDocumentMasterDomain master : dataList){
				if(!duplicateData.contains(master.getNumId())){
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
					
					List<ProposalDocumentDetailsDomain> childList = master.getProposalDocumentDetailsDomainList();
					for(int j=0;j<childList.size();j++){
						ProposalDocumentDetailsDomain childDomain = childList.get(j);
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
						}
					}catch(Exception e){
						log.error(""+e.getCause());
						log.error(e.getMessage());			
					}
					List<ProposalMasterDomain> proposalDomainList = getApplicationIdbyProposalId(master.getProposalId());
					if(proposalDomainList != null){
				        Application applicationDomain = proposalDomainList.get(0).getApplication();
				        ProjectMasterForm projectDetail =  projectMasterService.getProjectDetailsByApplicationId(applicationDomain.getNumId());
				        if(projectDetail != null){
				        	model.setStrProjectName(projectDetail.getStrProjectName());
				        	model.setEncProjectId(encryptionService.encrypt(""+projectDetail.getNumId()));
				        	model.setStrProjectRefNo(projectDetail.getProjectRefrenceNo());
				        }
					}
					dataMap.add(model);
				}
				duplicateData.add(master.getNumId());
			}
		}
		return dataMap;
	}
	/*-------- End of Function for get the proposal document details according to project ID [18-03-2024] ------*/
}
