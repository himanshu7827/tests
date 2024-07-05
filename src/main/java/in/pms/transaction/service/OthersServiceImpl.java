package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.OtherDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.OthersDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.domain.TalksDomain;
import in.pms.transaction.model.OthersModel;
import in.pms.transaction.model.TalksModel;
import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;

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
public class OthersServiceImpl implements OthersService{

	@Autowired
	OtherDao otherDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	
	public List<OthersModel> loadOthersDetail(int monthlyProgressId,long categoryId){
		List<OthersDomain> domainList = otherDao.loadOthersDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertOthersDomainToModelList(domainList);
		}
		return null;
		
	}

	public List<OthersModel> convertOthersDomainToModelList(List<OthersDomain> empCopyRightList){
		List<OthersModel> list = new ArrayList<OthersModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				OthersDomain othersDomain = empCopyRightList.get(i);
				OthersModel othersModel = new OthersModel();
				
				othersModel.setNumOthersID(othersDomain.getNumOthersID());
				othersModel.setStrAppreciaitionAgency(othersDomain.getStrAppreciaitionAgency());
				othersModel.setStrCityLocation(othersDomain.getStrCityLocation());
				othersModel.setStrRecognition(othersDomain.getStrRecognition());
				othersModel.setDateOfOthers(DateUtils.dateToString(othersDomain.getDateOfOthers()));
			
				list.add(othersModel);
	}
		return list;
	}

	
	/*@Override
	public String checkDuplicateOthers(OthersModel othersModel) {
		String result=	"";
			OthersDomain othersDomain = otherDao.getOthersbyCategoryId(othersModel.getNumCategoryId());
			
		 if(null == othersDomain){
					return null; 
				 }else if(othersModel.getNumOthersID()!= 0){
					 if(othersDomain.getNumOthersID() == othersModel.getNumOthersID())
					 {
						 return null; 
					 }
					 else{
						 result = "Details already exist!";
						 
					 }
				 }else{
					 if(othersDomain.getNumIsValid() == 1){
							result = " Details already exist ! ";
						}
					}
		
				return result;	
		}*/
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public OthersModel saveUpdateOthers(OthersModel othersModel){
		OthersDomain othersDomain = convertOthersModelToDomain(othersModel);
		otherDao.save(othersDomain).getNumOthersID();
		return othersModel;
	}

	private OthersDomain convertOthersModelToDomain(OthersModel othersModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			OthersDomain OthersDomain = new OthersDomain();
			if(othersModel.getNumOthersID()!=0){				
				OthersDomain =  otherDao.getOne(othersModel.getNumOthersID());
			}
			if(othersModel.getNumOthersID()==0){
				
				String encCategoryId = othersModel.getEncCategoryId();
				String encMonthlyProgressId = othersModel.getEncMonthlyProgressId();
				long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, categoryID);
				if(null != progressDetails && progressDetails.size()>0){
					OthersDomain.setMonthlyProgressDetails(progressDetails.get(0));
				
			
		}
		}
			OthersDomain.setDtTrDate(new Date());
			OthersDomain.setNumTrUserId(userInfo.getEmployeeId());
			OthersDomain.setNumIsValid(1);
		
			OthersDomain.setStrRecognition(othersModel.getStrRecognition());
			OthersDomain.setStrAppreciaitionAgency(othersModel.getStrAppreciaitionAgency());
			try{
				OthersDomain.setDateOfOthers(DateUtils.dateStrToDate(othersModel.getDateOfOthers()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			OthersDomain.setStrCityLocation(othersModel.getStrCityLocation());
		
			return OthersDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public OthersModel deleteOthers(OthersModel othersModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		OthersDomain othersDomain = otherDao.getOne(othersModel.getNumOthersID());
		//OthersDomain OthersDomain = new OthersDomain();
		if(othersDomain != null)
			othersDomain.setNumIsValid(0);
		othersDomain.setDtTrDate(new Date());
		othersDomain.setNumTrUserId(userInfo.getEmployeeId());
		otherDao.save(othersDomain).getNumOthersID();
		
		return othersModel;
	}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<OthersDomain> domainList = otherDao.loadOtherByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[4];
			obj[0] = "Recognition";
			obj[1] = "Appreciated By";
			obj[2] = "Date";
			obj[3] = "City/Location";
			dataList.add(obj);
		}
		
		for(OthersDomain domain : domainList){
			Object[] obj = new Object[4];
			obj[0] =domain.getStrRecognition();
			obj[1] = domain.getStrAppreciaitionAgency();
			if(null != domain.getDateOfOthers()){
				obj[2] =DateUtils.dateToString(domain.getDateOfOthers());
			}else{
				obj[2] = "";
			}
			obj[3] = domain.getStrCityLocation();
			dataList.add(obj);
		}
		
		return dataList;		
	}



}
