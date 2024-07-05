package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.TalksDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.TalksDomain;
import in.pms.transaction.model.TalksModel;

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
public class TalksServiceImpl implements TalksService{

	@Autowired
	TalksDao talksDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	
	public List<TalksModel> loadTalksDetail(int monthlyProgressId,long categoryId){
		List<TalksDomain> domainList = talksDao.loadTalksDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertTalkDomainToModelList(domainList);
		}
		return null;
		
	}

	public List<TalksModel> convertTalkDomainToModelList(List<TalksDomain> empCopyRightList){
		List<TalksModel> list = new ArrayList<TalksModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				TalksDomain talksDomain = empCopyRightList.get(i);
				TalksModel talksModel = new TalksModel();
				
				talksModel.setNumTalkID(talksDomain.getNumTalkID());
				talksModel.setStrEventName(talksDomain.getStrEventName());;
				talksModel.setStrAgencyName(talksDomain.getStrAgencyName());
				talksModel.setDtDate(DateUtils.dateToString(talksDomain.getDtDate()));
				talksModel.setStrTalkTitle(talksDomain.getStrTalkTitle());
				talksModel.setStrNameSpeaker(talksDomain.getStrNameSpeaker());
				talksModel.setStrCityLocation(talksDomain.getStrCityLocation());
				//System.out.println("talksDomain.getNumTalkID("+talksDomain.getNumTalkID());
				//talksModel.setNumGroupCategoryId(talksDomain.getNumGroupCategoryId());
				list.add(talksModel);
	}
		return list;
	}

	
	@Override
	/*public String checkDuplicateTalks(TalksModel talksModel) {
		String result=	"";
			TalksDomain talksDomain = talksDao.getTalkbyCategoryId(talksModel.getNumCategoryId());
			
		 if(null == talksDomain){
					return null; 
				 }else if(talksModel.getNumTalkID()!= 0){
					 if(talksDomain.getNumTalkID() == talksModel.getNumTalkID())
					 {
						 return null; 
					 }
					 else{
						 result = "Details already exist!";
						 
					 }
				 }else{
					 if(talksDomain.getNumIsValid() == 1){
							result = " Details already exist ! ";
						}
					}
		
				return result;	
		}*/
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public TalksModel saveUpdateTalks(TalksModel talksModel){
		TalksDomain talksDomain = convertTalkModelToDomain(talksModel);
		talksDao.save(talksDomain).getNumTalkID();
		return talksModel;
	}

	private TalksDomain convertTalkModelToDomain(TalksModel talksModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			TalksDomain talksDomain = new TalksDomain();
			if(talksModel.getNumTalkID()!=0){				
				talksDomain =  talksDao.getOne(talksModel.getNumTalkID());
			}
			if(talksModel.getNumTalkID()==0){
			
					String encCategoryId = talksModel.getEncCategoryId();
					String encMonthlyProgressId = talksModel.getEncMonthlyProgressId();
					long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
					int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
					List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, categoryID);
					if(null != progressDetails && progressDetails.size()>0){
						talksDomain.setMonthlyProgressDetails(progressDetails.get(0));
					
				
			}
			}
			
			talksDomain.setDtTrDate(new Date());
			talksDomain.setNumTrUserId(userInfo.getEmployeeId());
			talksDomain.setNumIsValid(1);
		//	talksDomain.setNumGroupCategoryId(talksModel.getNumGroupCategoryId());;
			talksDomain.setStrEventName(talksModel.getStrEventName());
			try{
				talksDomain.setDtDate(DateUtils.dateStrToDate(talksModel.getDtDate()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			talksDomain.setStrAgencyName(talksModel.getStrAgencyName());
			talksDomain.setStrTalkTitle(talksModel.getStrTalkTitle());
			talksDomain.setStrNameSpeaker(talksModel.getStrNameSpeaker());
			talksDomain.setStrCityLocation(talksModel.getStrCityLocation());
		
			return talksDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public TalksModel deleteTalks(TalksModel talksModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		TalksDomain talksDomain = talksDao.getOne(talksModel.getNumTalkID());
		
		if(talksDomain != null)
			talksDomain.setNumIsValid(0);
		talksDomain.setDtTrDate(new Date());
		talksDomain.setNumTrUserId(userInfo.getEmployeeId());
		talksDao.save(talksDomain).getNumTalkID();
		
		return talksModel;



	}
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<TalksDomain> domainList = talksDao.loadTalksByDetailsId(progressDetailsId);
		
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[6];
			obj[0] = (Object)"Event Name";
			obj[1] = (Object)"Agency Name";
			obj[2] = (Object)"Talk Title";			
			obj[3] = (Object)"Date";
			obj[4] = (Object)"Name of Speaker";
			obj[5] = (Object)"City/Location";
			
			dataList.add(obj);
		}
		
		for(TalksDomain domain : domainList){
			Object[] obj = new Object[6];
			obj[0] =domain.getStrEventName();
			obj[1] = domain.getStrAgencyName();
			obj[2] = domain.getStrTalkTitle();
			if(null != domain.getDtDate()){
				obj[3] =DateUtils.dateToString(domain.getDtDate());
			}else{
				obj[3] = "";
			}
			obj[4] = domain.getStrNameSpeaker();
			obj[5] = domain.getStrCityLocation();
			dataList.add(obj);
		}		
		return dataList;		
	}
	
	
}