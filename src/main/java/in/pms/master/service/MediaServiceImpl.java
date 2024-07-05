package in.pms.master.service;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.MediaDao;
import in.pms.master.domain.MediaDomain;
import in.pms.master.model.MediaModel;



import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class MediaServiceImpl implements MediaService {
	
	@Autowired
	MediaDao mediaDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;


	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<MediaModel> getAllMediaDetails(){
		return convertMediaDomainToModelList(mediaDao.getAllMediaDetails());			
	}

	public List<MediaModel> convertMediaDomainToModelList(List<MediaDomain> mediaList){
		List<MediaModel> list = new ArrayList<MediaModel>();
			for(int i=0;i<mediaList.size();i++){
				MediaDomain mediaDomain = mediaList.get(i);
				MediaModel mediaModel = new MediaModel();
				mediaModel.setNumId(mediaDomain.getNumId());
				mediaModel.setDetails(mediaDomain.getDetails());
				mediaModel.setSource(mediaDomain.getSource());
				mediaModel.setLocation(mediaDomain.getLocation());
				mediaModel.setSourceDetails(mediaDomain.getSourceDetails());
				mediaModel.setAnyOtherDetails(mediaDomain.getAnyOtherSource());
			
				try{
					mediaModel.setDateOfmedia(DateUtils.dateToString(mediaDomain.getDateOfmedia()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				list.add(mediaModel);
	}
		return list;
	}
	
	/*@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public MediaDomain saveUpdateMedia(MediaModel mediaModel){
		MediaDomain mediaDomain = convertMediaMasterModelToDomain(mediaModel);
		return mediaDao.saveUpdateMedia(mediaDomain);
		return mediaDao.save(mediaDomain);
	}*/
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public MediaModel saveUpdateMedia(MediaModel mediaModel){
		MediaDomain mediaDomain = convertMediaMasterModelToDomain(mediaModel);
		mediaDao.save(mediaDomain).getNumId();
		//appreciationLetterDao.save(appreciationLetterDomain).getNumAppreciationID();
		return mediaModel;
	}
	
	public MediaDomain convertMediaMasterModelToDomain(MediaModel mediaModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		MediaDomain mediaDomain = new MediaDomain();
		if(mediaModel.getNumId()!=0){		
			//mediaDomain =  mediaDao.getMediaById(mediaModel.getNumId());
			mediaDomain =  mediaDao.getOne(mediaModel.getNumId());
		}
		if(mediaModel.getNumId()==0){
			mediaDomain = new MediaDomain();
			String encCateoryId = mediaModel.getEncCategoryId();
			String encMonthlyProgressId = mediaModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			long numCateoryId = Long.parseLong(encryptionService.dcrypt(encCateoryId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
			if(null != progressDetails && progressDetails.size()>0){
				mediaDomain.setMonthlyProgressDetails(progressDetails.get(0));
			}
			
		}
		mediaDomain.setNumId(mediaModel.getNumId());
		mediaDomain.setDetails(mediaModel.getDetails());
		mediaDomain.setSource(mediaModel.getSource());
		mediaDomain.setLocation(mediaModel.getLocation());
		mediaDomain.setSourceDetails(mediaModel.getSourceDetails());
		mediaDomain.setAnyOtherSource(mediaModel.getAnyOtherDetails());
		
		try{
			mediaDomain.setDateOfmedia(DateUtils.dateStrToDate(mediaModel.getDateOfmedia()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		mediaDomain.setNumIsValid(1);
		mediaDomain.setNumTrUserId(userInfo.getEmployeeId());
		mediaDomain.setDtTrDate(new Date());

		return mediaDomain;
	}
	
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public MediaModel deleteMedia(MediaModel mediaModel) {
		int count= mediaModel.getNumIds().length;
		long[] media_ids= new long[count];
		media_ids= mediaModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			//MediaDomain mediaDomain= new MediaDomain();			
			Date date= new Date();
		//	mediaDao.getOne(mediaModel.getNumId());
			MediaDomain mediaDetails =mediaDao.getOne(media_ids[i]);
			mediaDetails.setNumIsValid(2);
			mediaDetails.setDtTrDate(date);
			mediaDao.save(mediaDetails).getNumId();		
			
		}	
		return mediaModel;
}

	public List<MediaModel> loadMediaDetail(int monthlyProgressId,long categoryId){
		List<MediaDomain> domainList = mediaDao.loadMediaDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertMediaDomainToModelList(domainList);
		}
		return null;
	}
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<MediaDomain> domainList = mediaDao.loadMediaByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[6];
			obj[0] = MessageBundleFile.getValueFromKey("media.source");
			obj[1] = MessageBundleFile.getValueFromKey("media.source.details");
			obj[2] = MessageBundleFile.getValueFromKey("media.details");			
			obj[3] = MessageBundleFile.getValueFromKey("media.date");
			obj[4] = MessageBundleFile.getValueFromKey("media.location");
			obj[5] = MessageBundleFile.getValueFromKey("media.anyOther");
			
			dataList.add(obj);
		}
		
		for(MediaDomain domain : domainList){
			Object[] obj = new Object[6];
			obj[0] =domain.getSource();
			obj[1] = domain.getSourceDetails();
			obj[2] = domain.getDetails();
			if(null != domain.getDateOfmedia()){
				obj[3] =DateUtils.dateToString(domain.getDateOfmedia());
			}else{
				obj[3] = "";
			}
			obj[4] = domain.getLocation();
			obj[5]=domain.getAnyOtherSource();
			dataList.add(obj);
		}		
		return dataList;		
	}
	
}
