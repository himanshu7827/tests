package in.pms.transaction.service;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProjectPublicationDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.PublicationDetailsModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class ProjectPublicationDetailsServiceImpl implements ProjectPublicationDetailsService{
	
	@Autowired
	ProjectPublicationDetailsDao projectPublicationDetailsDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public PublicationDetailsModel saveProjectPublicationDetails(PublicationDetailsModel publicationDetailsModel){	
		ProjectPublicationDomain projectPublicationDomain = convertModelToDomain(publicationDetailsModel);
		int t=projectPublicationDetailsDao.save(projectPublicationDomain).getNumProjectPublicationId();
		if(t!=0){
			//publicationDetailsModel.setNumProjectPublicationId(t);
			return publicationDetailsModel;
		}else {
			return null;
		}
	}

	public ProjectPublicationDomain convertModelToDomain(PublicationDetailsModel publicationDetailsModel){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();		
		ProjectPublicationDomain projectPublicationDomain = null;
		
		if(publicationDetailsModel.getNumProjectPublicationId() >0){
			projectPublicationDomain = projectPublicationDetailsDao.getOne(publicationDetailsModel.getNumProjectPublicationId());
		}
		
		if(null == projectPublicationDomain){
			projectPublicationDomain = new ProjectPublicationDomain();
			long numCateoryId =Long.parseLong(encryptionService.dcrypt(publicationDetailsModel.getEncCategoryId()));
			String encMonthlyProgressId = publicationDetailsModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
			if(null != progressDetails && progressDetails.size()>0){
				projectPublicationDomain.setMonthlyProgressDetails(progressDetails.get(0));
			}
			
		}
		
		projectPublicationDomain.setNumIsValid(1);
		projectPublicationDomain.setDtTrDate(new Date());
		projectPublicationDomain.setNumTrUserId(userInfo.getEmployeeId());

		//projectPublicationDomain.setNumReportId(publicationDetailsModel.getNumReportId());
		projectPublicationDomain.setStrPublicationDescription(publicationDetailsModel.getStrPublicationDescription());
		projectPublicationDomain.setStrPublicationType(publicationDetailsModel.getStrPublicationType());
		projectPublicationDomain.setStrPublicaionTitle(publicationDetailsModel.getStrPublicaionTitle());
		projectPublicationDomain.setStrAuthorDetails(publicationDetailsModel.getStrAuthorDetails());
		projectPublicationDomain.setStrJournalName(publicationDetailsModel.getStrJournalName());
		projectPublicationDomain.setStrConferenceCity(publicationDetailsModel.getStrConferenceCity());
		projectPublicationDomain.setStrReferenceNumber(publicationDetailsModel.getStrReferenceNumber());
		projectPublicationDomain.setStrPublisher(publicationDetailsModel.getStrPublisher());
		projectPublicationDomain.setStrOrganisation(publicationDetailsModel.getStrOrganisation());
		try {
			projectPublicationDomain.setDtpublication(DateUtils.dateStrToDate(publicationDetailsModel.getDtpubdt()));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		return projectPublicationDomain;
		
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<PublicationDetailsModel> getPublicationDetails(int reportId) {
			
		List<ProjectPublicationDomain> domainList =projectPublicationDetailsDao.getPublicationDetail(reportId);
		
	    if(domainList.size()>0){	    	
			return convertToModelList(domainList) ;
	    } else{		
		return null;
	}
}
	
	@Override
	public List<PublicationDetailsModel> loadPublicationDetail(int monthlyProgressId,long categoryId){
		List<ProjectPublicationDomain> domainList = projectPublicationDetailsDao.loadPublicationDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertToModelList(domainList);
		}
		return null;
		
	}
	
	public List<PublicationDetailsModel> convertToModelList(List<ProjectPublicationDomain> domainList){
		List<PublicationDetailsModel> modelList = new ArrayList<PublicationDetailsModel>();
		for(int i=0;i<domainList.size();i++){
	    	ProjectPublicationDomain projectPublicationDomain=domainList.get(i);
	    	PublicationDetailsModel model= new PublicationDetailsModel();
	    	model.setNumProjectPublicationId(projectPublicationDomain.getNumProjectPublicationId());
	    
	    	model.setStrPublicationDescription(projectPublicationDomain.getStrPublicationDescription());
	    	model.setStrPublicationType(projectPublicationDomain.getStrPublicationType());
	    	model.setStrPublicaionTitle(projectPublicationDomain.getStrPublicaionTitle());
	    	model.setStrAuthorDetails(projectPublicationDomain.getStrAuthorDetails());
	    	model.setStrJournalName(projectPublicationDomain.getStrJournalName());
	    	model.setStrConferenceCity(projectPublicationDomain.getStrConferenceCity());
	    	model.setStrReferenceNumber(projectPublicationDomain.getStrReferenceNumber());
	    	model.setStrPublisher(projectPublicationDomain.getStrPublisher());
	    	model.setStrOrganisation(projectPublicationDomain.getStrOrganisation());
	    	model.setDtpubdt(DateUtils.dateToString(projectPublicationDomain.getDtpublication()));
	    	modelList.add(model);
	}
		return modelList;
}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<ProjectPublicationDomain> domainList = projectPublicationDetailsDao.loadPublicationByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[9];
			obj[0] = MessageBundleFile.getValueFromKey("publicationdetail.refNumber");
			obj[1] = MessageBundleFile.getValueFromKey("publicationdetail.Publication.Type");
			obj[2] = MessageBundleFile.getValueFromKey("publicationdetail.publisher");
			obj[3] = MessageBundleFile.getValueFromKey("publicationdetail.title");
			obj[4] = MessageBundleFile.getValueFromKey("publicationdetail.journal.name");
			obj[5] = MessageBundleFile.getValueFromKey("publicationdetail.date");
			obj[6] = MessageBundleFile.getValueFromKey("publicationdetail.author");
			obj[7] = MessageBundleFile.getValueFromKey("publicationdetail.conferenceCity");
			obj[8] = MessageBundleFile.getValueFromKey("publicationdetail.Organization");
			dataList.add(obj);
		}
		
		for(ProjectPublicationDomain domain : domainList){
			Object[] obj = new Object[9];
			obj[0] =domain.getStrReferenceNumber();
			obj[1] = domain.getStrPublicationType();
			obj[2] = domain.getStrPublisher();
			obj[3] = domain.getStrPublicaionTitle();
			obj[4] = domain.getStrJournalName();
			if(null != domain.getDtpublication()){
				obj[5] =DateUtils.dateToString(domain.getDtpublication());
			}else{
				obj[5] = "";
			}
			obj[6] = domain.getStrAuthorDetails();
			obj[7] = domain.getStrConferenceCity();
			obj[8] = domain.getStrOrganisation();
			
			dataList.add(obj);
		}
		
		return dataList;		
	}

}
