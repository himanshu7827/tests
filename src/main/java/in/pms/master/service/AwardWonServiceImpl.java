package in.pms.master.service;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.AwardWonDao;
import in.pms.master.domain.AwardWonDomain;
import in.pms.master.domain.MediaDomain;
import in.pms.master.model.AwardWonModel;


import in.pms.master.model.MediaModel;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class AwardWonServiceImpl implements AwardWonService {
	
	@Autowired
	AwardWonDao awardWonDao;

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<AwardWonModel> getAllAward(){
		return convertAwardDomainToModelList(awardWonDao.getAllAward());			
	}

	public List<AwardWonModel> convertAwardDomainToModelList(List<AwardWonDomain> awardWonList){
		List<AwardWonModel> list = new ArrayList<AwardWonModel>();
			for(int i=0;i<awardWonList.size();i++){
				AwardWonDomain awardWonDomain = awardWonList.get(i);
				AwardWonModel awardWonModel = new AwardWonModel();
				awardWonModel.setNumId(awardWonDomain.getNumId());
				awardWonModel.setAwardBy(awardWonDomain.getAwardBy());
				awardWonModel.setAwardName(awardWonDomain.getAwardName());
				awardWonModel.setAwardType(awardWonDomain.getAwardType());
				try{
				awardWonModel.setDateOfAward(DateUtils.dateToString(awardWonDomain.getDateOfAward()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				awardWonModel.setLocation(awardWonDomain.getLocation());
				awardWonModel.setProjectAwardedFor(awardWonDomain.getProjectAwardedFor());
				awardWonModel.setRecipientName(awardWonDomain.getRecipientName());
				awardWonModel.setAchievementDescription(awardWonDomain.getAchievementDescription());
				
				list.add(awardWonModel);
	}
		return list;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public AwardWonModel saveUpdateAwardWonDetails(AwardWonModel awardWonModel){
		AwardWonDomain awardWonDomain = convertAwardMasterModelToDomain(awardWonModel);
		 awardWonDao.save(awardWonDomain).getNumId();
		 return awardWonModel;
	}
	
	public AwardWonDomain convertAwardMasterModelToDomain(AwardWonModel awardWonModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		AwardWonDomain awardWonDomain = new AwardWonDomain();
		if(awardWonModel.getNumId()!=0){		
			awardWonDomain =  awardWonDao.getOne(awardWonModel.getNumId());
		}
		if(awardWonModel.getNumId()==0){
			awardWonDomain = new AwardWonDomain();
			String encCateoryId = awardWonModel.getEncCategoryId();
			String encMonthlyProgressId = awardWonModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long numCateoryId = Long.parseLong(encryptionService.dcrypt(encCateoryId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
			if(null != progressDetails && progressDetails.size()>0){
				awardWonDomain.setMonthlyProgressDetails(progressDetails.get(0));
			}
			
		}
		awardWonDomain.setAwardBy(awardWonModel.getAwardBy());
		awardWonDomain.setAwardName(awardWonModel.getAwardName());
		awardWonDomain.setAwardType(awardWonModel.getAwardType());
		awardWonDomain.setAchievementDescription(awardWonModel.getAchievementDescription());
		try{
			awardWonDomain.setDateOfAward(DateUtils.dateStrToDate(awardWonModel.getDateOfAward()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		awardWonDomain.setLocation(awardWonModel.getLocation());
		awardWonDomain.setProjectAwardedFor(awardWonModel.getProjectAwardedFor());
		awardWonDomain.setRecipientName(awardWonModel.getRecipientName());
		awardWonDomain.setNumIsValid(1);
		awardWonDomain.setNumTrUserId(userInfo.getEmployeeId());
		awardWonDomain.setDtTrDate(new Date());
		
		return awardWonDomain;
	}
	
	

	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public AwardWonModel deleteAwardWonDetails(AwardWonModel awardWonModel) {
		int count= awardWonModel.getNumIds().length;
		long[] award_ids= new long[count];
		award_ids= awardWonModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{			
			Date date= new Date();
			AwardWonDomain awardWonDomain =awardWonDao.getOne(award_ids[i]);
			awardWonDomain.setNumIsValid(2);
			awardWonDomain.setDtTrDate(date);
			awardWonDao.save(awardWonDomain).getNumId();			
		}	
		return awardWonModel;
}

	public List<AwardWonModel> loadAwardDetails(int monthlyProgressId,long categoryId){
		List<AwardWonDomain> domainList = awardWonDao.loadAwardWonDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertAwardDomainToModelList(domainList);
		}
		return null;
	}
	
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<AwardWonDomain> domainList = awardWonDao.loadAwardByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[8];
			obj[0] = MessageBundleFile.getValueFromKey("award.name");
			obj[1] = MessageBundleFile.getValueFromKey("award.type");
			obj[2] = MessageBundleFile.getValueFromKey("award.date");			
			obj[3] = MessageBundleFile.getValueFromKey("award.reciepient.name");
			obj[4] = MessageBundleFile.getValueFromKey("award.by");
			obj[5] = MessageBundleFile.getValueFromKey("award.location");
			obj[6] = MessageBundleFile.getValueFromKey("product.awarded.for");
			obj[7] = MessageBundleFile.getValueFromKey("award.achievement.desc");
			
			dataList.add(obj);
		}
		
		for(AwardWonDomain domain : domainList){
			Object[] obj = new Object[8];
			obj[0] =domain.getAwardName();
			obj[1] = domain.getAwardType();
			String awardDate = "";
			if(null != domain.getDateOfAward()){
				awardDate = DateUtils.dateToString(domain.getDateOfAward());
			}
			obj[2] = awardDate;
			obj[3] = domain.getRecipientName();
			obj[4] = domain.getAwardBy();	
			obj[5]=domain.getLocation();
			obj[6]=domain.getProjectAwardedFor();
			obj[7]=domain.getAchievementDescription();
			dataList.add(obj);
		}		
		return dataList;		
	}
}
