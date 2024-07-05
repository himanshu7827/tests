package in.pms.master.service;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.dao.SeminarEventDao;
import in.pms.master.domain.AwardWonDomain;
import in.pms.master.domain.MediaDomain;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.SeminarEventDomain;
import in.pms.master.model.AwardWonModel;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.model.SeminarEventModel;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class SeminarEventServiceImpl implements SeminarEventService {
	
	@Autowired
	SeminarEventDao seminarEventDao;

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<SeminarEventModel> getAllSeminar(){
		return convertSeminarDomainToModelList(seminarEventDao.getAllSeminar());			
	}

	public List<SeminarEventModel> convertSeminarDomainToModelList(List<SeminarEventDomain> seminarDataList){
		List<SeminarEventModel> list = new ArrayList<SeminarEventModel>();
			for(int i=0;i<seminarDataList.size();i++){
				SeminarEventDomain seminarEventDomain = seminarDataList.get(i);
				SeminarEventModel seminarEventModel = new SeminarEventModel();
				seminarEventModel.setNumId(seminarEventDomain.getNumId());
				seminarEventModel.setCdacRoles(seminarEventDomain.getCdacRoles());
				seminarEventModel.setNoOfParticipant(seminarEventDomain.getNoOfParticipant());
				seminarEventModel.setStrCollaborators(seminarEventDomain.getCollaborators());
				seminarEventModel.setDateOfSeminar(DateUtils.dateToString(seminarEventDomain.getDateOfSeminar()));
				seminarEventModel.setSeminarType(seminarEventDomain.getSeminarType());
				seminarEventModel.setStrProfileOfParticipant(seminarEventDomain.getStrProfileOfParticipant());
				seminarEventModel.setCoordinatingPerson(seminarEventDomain.getCoordinatingPerson());
				seminarEventModel.setObjectives(seminarEventDomain.getObjectives());
				seminarEventModel.setVenue(seminarEventDomain.getVenue());
				seminarEventModel.setAnyOtherTypeDetails(seminarEventDomain.getAnyOtherTypeDetails());
				
				list.add(seminarEventModel);
	}
		return list;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public SeminarEventModel saveUpdateSeminarDetails(SeminarEventModel seminarEventModel){
		SeminarEventDomain seminarEventDomain = convertSeminarMasterModelToDomain(seminarEventModel);
		//return seminarEventDao.saveUpdateSeminarDetails(seminarEventDomain);
		 seminarEventDao.save(seminarEventDomain).getNumId();
		 return seminarEventModel;
	}
	
	public SeminarEventDomain convertSeminarMasterModelToDomain(SeminarEventModel seminarEventModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		SeminarEventDomain seminarEventDomain = new SeminarEventDomain();
		if(seminarEventModel.getNumId()!=0){		
			//seminarEventDomain =  seminarEventDao.getSeminarDomainById(seminarEventModel.getNumId());
			seminarEventDomain =  seminarEventDao.getOne(seminarEventModel.getNumId());
		}
		if(seminarEventModel.getNumId()==0){
			seminarEventDomain = new SeminarEventDomain();
			String encCateoryId = seminarEventModel.getEncCategoryId();
			String encMonthlyProgressId = seminarEventModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long numCateoryId = Long.parseLong(encryptionService.dcrypt(encCateoryId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
			if(null != progressDetails && progressDetails.size()>0){
				seminarEventDomain.setMonthlyProgressDetails(progressDetails.get(0));
			}
			
		}
		//seminarEventDomain.setNumId(seminarEventModel.getNumId());
		seminarEventDomain.setCdacRoles(seminarEventModel.getCdacRoles());
		seminarEventDomain.setNoOfParticipant(seminarEventModel.getNoOfParticipant());
		seminarEventDomain.setCollaborators(seminarEventModel.getStrCollaborators());
		seminarEventDomain.setAnyOtherTypeDetails(seminarEventModel.getAnyOtherTypeDetails());
		seminarEventDomain.setDtTrDate(new Date());
		try{
		seminarEventDomain.setDateOfSeminar(DateUtils.dateStrToDate(seminarEventModel.getDateOfSeminar()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		seminarEventDomain.setSeminarType(seminarEventModel.getSeminarType());
		seminarEventDomain.setStrProfileOfParticipant(seminarEventModel.getStrProfileOfParticipant());
		seminarEventDomain.setCoordinatingPerson(seminarEventModel.getCoordinatingPerson());
		seminarEventDomain.setObjectives(seminarEventModel.getObjectives());
		seminarEventDomain.setVenue(seminarEventModel.getVenue());
		seminarEventDomain.setNumIsValid(1);
		seminarEventDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		

		return seminarEventDomain;
	}
	
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public SeminarEventModel deleteSeminarDetails(SeminarEventModel seminarEventModel) {
		int count= seminarEventModel.getNumIds().length;
		long[] seminar_ids= new long[count];
		seminar_ids= seminarEventModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{			
			Date date= new Date();
			SeminarEventDomain seminarEventDomain =seminarEventDao.getOne(seminar_ids[i]);
			seminarEventDomain.setNumIsValid(2);
			seminarEventDomain.setDtTrDate(date);
			seminarEventDao.save(seminarEventDomain);			
		}
		return seminarEventModel;
}

	public List<SeminarEventModel> loadSeminarEventDetails(int monthlyProgressId,long categoryId){
		List<SeminarEventDomain> domainList = seminarEventDao.loadSeminarEventDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertSeminarDomainToModelList(domainList);
		}
		return null;
	
	}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<SeminarEventDomain> domainList = seminarEventDao.loadSeminarByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[10];
			obj[0] = MessageBundleFile.getValueFromKey("seminar.type");
			obj[1] = MessageBundleFile.getValueFromKey("seminar.date");
			obj[2] = MessageBundleFile.getValueFromKey("seminar.objectives");			
			obj[3] = MessageBundleFile.getValueFromKey("seminar.coordinating.person");
			obj[4] = MessageBundleFile.getValueFromKey("seminar.roles");
			obj[5] = MessageBundleFile.getValueFromKey("seminar.venue");
			obj[6] = MessageBundleFile.getValueFromKey("seminar.no.of.participant");			
			obj[7] = MessageBundleFile.getValueFromKey("seminar.profile.participant");
			obj[8] = MessageBundleFile.getValueFromKey("seminar.collaborators");
			obj[9]=MessageBundleFile.getValueFromKey("seminar.any.other.type");
			
			dataList.add(obj);
		}
		
		for(SeminarEventDomain domain : domainList){
			Object[] obj = new Object[10];
			obj[0] =domain.getSeminarType();
			if(null != domain.getDateOfSeminar()){
				obj[1] =DateUtils.dateToString(domain.getDateOfSeminar());
			}else{
				obj[1] = "";
			}
			obj[2] = domain.getObjectives();
			obj[3] = domain.getCoordinatingPerson();
			obj[4] = domain.getCdacRoles();
			obj[5] = domain.getVenue();
			obj[6] = domain.getNoOfParticipant();
			obj[7] = domain.getStrProfileOfParticipant();
			obj[8] = domain.getCollaborators();
			obj[9]=domain.getAnyOtherTypeDetails();
			dataList.add(obj);
		}		
		return dataList;		
	}
	
}
