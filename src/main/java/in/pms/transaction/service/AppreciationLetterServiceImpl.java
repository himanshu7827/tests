package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.AppreciationLetterDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.domain.AppreciationLetterDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.OthersDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.AppreciationLetterModel;
import in.pms.transaction.model.OthersModel;

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
public class AppreciationLetterServiceImpl implements AppreciationLetterService{

	@Autowired
	AppreciationLetterDao appreciationLetterDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	
	public List<AppreciationLetterModel> loadAppreciationDetail(int monthlyProgressId,long categoryId){
		List<AppreciationLetterDomain> domainList = appreciationLetterDao.loadAppreciationDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertAppreciationLetterDomainToModelList(domainList);
		}
		return null;
		
	}
	
	public List<AppreciationLetterModel> convertAppreciationLetterDomainToModelList(List<AppreciationLetterDomain> empCopyRightList){
		List<AppreciationLetterModel> list = new ArrayList<AppreciationLetterModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				AppreciationLetterDomain appreciationLetterDomain = empCopyRightList.get(i);
				AppreciationLetterModel appreciationLetterModel = new AppreciationLetterModel();
				
				appreciationLetterModel.setNumAppreciationID(appreciationLetterDomain.getNumAppreciationID());
				appreciationLetterModel.setStrDescription(appreciationLetterDomain.getStrDescription());
				appreciationLetterModel.setStrRecipientName(appreciationLetterDomain.getStrRecipientName());
				appreciationLetterModel.setDateOfAppreciation(DateUtils.dateToString(appreciationLetterDomain.getDateOfAppreciation()));
				appreciationLetterModel.setStrAppreciaitionAgency(appreciationLetterDomain.getStrAppreciaitionAgency());
				
				list.add(appreciationLetterModel);
	}
		return list;
	}
	
	/*
	@Override
	public String checkDuplicateAppreciationLetter(AppreciationLetterModel appreciationLetterModel) {
		String result=	"";
			AppreciationLetterDomain appreciationLetterDomain = appreciationLetterDao.getAppreciationByreference(appreciationLetterModel.getNumCategoryId());
			
		 if(null == appreciationLetterDomain){
					return null; 
				 }else if(appreciationLetterModel.getNumAppreciationID()!= 0){
					 if(appreciationLetterDomain.getNumAppreciationID() == appreciationLetterModel.getNumAppreciationID())
					 {
						 return null; 
					 }
					 else{
						 result = "Appreciation Letter Details with same ID already exist.";
						 
					 }
				 }else{
					 if(appreciationLetterDomain.getNumIsValid() == 1){
							result = "Appreciation Letter Details with same ID already exist ! ";
						}
					}
		
				return result;	
		}*/
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public AppreciationLetterModel saveUpdateAppreciationLetter(AppreciationLetterModel appreciationLetterModel){
		AppreciationLetterDomain appreciationLetterDomain = convertAppreciationModelToDomain(appreciationLetterModel);
		 appreciationLetterDao.save(appreciationLetterDomain).getNumAppreciationID();
		 return appreciationLetterModel;
	}

	private AppreciationLetterDomain convertAppreciationModelToDomain(AppreciationLetterModel appreciationLetterModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			AppreciationLetterDomain appreciationLetterDomain = new AppreciationLetterDomain();
			if(appreciationLetterModel.getNumAppreciationID()!=0){				
				appreciationLetterDomain =  appreciationLetterDao.getOne(appreciationLetterModel.getNumAppreciationID());
			}
	if(appreciationLetterModel.getNumAppreciationID()==0){
				
		String encCategoryId = appreciationLetterModel.getEncCategoryId();
		String encMonthlyProgressId = appreciationLetterModel.getEncMonthlyProgressId();
		long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
		int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, categoryID);
		if(null != progressDetails && progressDetails.size()>0){
			appreciationLetterDomain.setMonthlyProgressDetails(progressDetails.get(0));
		
		}
		}
			appreciationLetterDomain.setDtTrDate(new Date());
			appreciationLetterDomain.setNumTrUserId(userInfo.getEmployeeId());
			appreciationLetterDomain.setNumIsValid(1);
			
			appreciationLetterDomain.setStrDescription(appreciationLetterModel.getStrDescription());
			appreciationLetterDomain.setStrAppreciaitionAgency(appreciationLetterModel.getStrAppreciaitionAgency());
			try{
				appreciationLetterDomain.setDateOfAppreciation(DateUtils.dateStrToDate(appreciationLetterModel.getDateOfAppreciation()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			appreciationLetterDomain.setStrRecipientName(appreciationLetterModel.getStrRecipientName());
			
			return appreciationLetterDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public AppreciationLetterModel deleteApprciationLetter(AppreciationLetterModel appreciationLetterModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		AppreciationLetterDomain appreciationLetterDomain = appreciationLetterDao.getOne(appreciationLetterModel.getNumAppreciationID());
		if(appreciationLetterDomain != null)
			appreciationLetterDomain.setNumIsValid(0);
		appreciationLetterDomain.setDtTrDate(new Date());
		appreciationLetterDomain.setNumTrUserId(userInfo.getEmployeeId());
		appreciationLetterDao.save(appreciationLetterDomain).getNumAppreciationID();
		
		return appreciationLetterModel;
	}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<AppreciationLetterDomain> domainList = appreciationLetterDao.loadAppreciationByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[4];
			obj[0] = "Description";
			obj[1] = "Appreciated By";
			obj[2] = "Date";
			obj[3] = "Recipient name";
			dataList.add(obj);
		}
		
		for(AppreciationLetterDomain domain : domainList){
			Object[] obj = new Object[4];
			obj[0] =domain.getStrDescription();
			obj[1] = domain.getStrAppreciaitionAgency();
			if(null != domain.getDateOfAppreciation()){
				obj[2] =DateUtils.dateToString(domain.getDateOfAppreciation());
			}else{
				obj[2] = "";
			}
			obj[3] = domain.getStrRecipientName();
			
			dataList.add(obj);
		}
		
		return dataList;		
	}
	

}
