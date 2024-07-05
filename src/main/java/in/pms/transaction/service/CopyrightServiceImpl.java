package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.login.util.UserInfo;
import in.pms.master.domain.MediaDomain;
import in.pms.master.model.MediaModel;
import in.pms.transaction.domain.CopyRightDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.dao.CopyrightDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProgressReportDao;
import in.pms.transaction.domain.CategoryMaster;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.CopyRightModel;
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
public class CopyrightServiceImpl implements CopyrightService{

	@Autowired
	CopyrightDao copyrightDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<CopyRightModel> getAllCopyRight() {
		return convertCopyRightDomainToModelList(copyrightDao.getAllCopyRight());
		}
	
	public List<CopyRightModel> convertCopyRightDomainToModelList(List<CopyRightDomain> empCopyRightList){
		List<CopyRightModel> list = new ArrayList<CopyRightModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				CopyRightDomain copyRightDomain = empCopyRightList.get(i);
				CopyRightModel copyRightModel = new CopyRightModel();
				
				copyRightModel.setNumCopyRightID(copyRightDomain.getNumCopyRightID());
				copyRightModel.setStrTitle(copyRightDomain.getStrTitle());
				copyRightModel.setStrAgency(copyRightDomain.getStrAgency());
				copyRightModel.setStrReferenceNumber(copyRightDomain.getStrReferenceNumber());
				//copyRightModel.setCopyrightAwarded(copyRightDomain.getCopyrightAwarded());
				copyRightModel.setStrCopyrightFiledAwarded(copyRightDomain.getStrcopyrightFiledAwarded());
				if(copyRightDomain.getStrcopyrightFiledAwarded().equals("Filed"))
				{
					copyRightModel.setDtFilingDate(DateUtils.dateToString(copyRightDomain.getDtFilingDate()));
				}
				else
				{
					copyRightModel.setDtAwardDate(DateUtils.dateToString(copyRightDomain.getDtAwardDate()));
				}
				copyRightModel.setDateOfCopyright(DateUtils.dateToString(copyRightDomain.getDateOfCopyright()));
				
				list.add(copyRightModel);
	}
		return list;
	}

	
	/*@Override
	public String checkDuplicateCopyright(CopyRightModel copyRightModel) {
		String result=	"";
			CopyRightDomain copyRightDomain = copyrightDao.getCopyrightByreference(copyRightModel.getStrReferenceNumber(),copyRightModel.getNumGroupCategoryId());
			
		 if(null == copyRightDomain){
					return null; 
				 }else if(copyRightModel.getNumCopyRightID()!= 0){
					 if(copyRightDomain.getNumCopyRightID() == copyRightModel.getNumCopyRightID())
					 {
						 return null; 
					 }
					 else{
						 result = "Copyright Details with same Reference Number already exist with title '"+copyRightDomain.getStrTitle()+"'";
						 
					 }
				 }else{
					 if(copyRightDomain.getNumIsValid() == 1){
							result = "Copyright Details with same Reference Number already exist ! ";
						}
					}
		
				return result;	
		}*/
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public CopyRightModel saveUpdateCopyrightMaster(CopyRightModel copyRightModel){
		 CopyRightDomain copyRightDomain = convertCopyrightModelToDomain(copyRightModel);
		 copyrightDao.save(copyRightDomain).getNumCopyRightID();
		 return copyRightModel;
	}

	private CopyRightDomain convertCopyrightModelToDomain(CopyRightModel copyRightModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			CopyRightDomain copyRightDomain = new CopyRightDomain();
			if(copyRightModel.getNumCopyRightID()!=0){				
				copyRightDomain =  copyrightDao.getOne(copyRightModel.getNumCopyRightID());
			}
			
			if(copyRightModel.getNumCopyRightID()==0){
				copyRightDomain = new CopyRightDomain();
				String encCateoryId = copyRightModel.getEncCategoryId();
				String encMonthlyProgressId = copyRightModel.getEncMonthlyProgressId();
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				long numCateoryId = Integer.parseInt(encryptionService.dcrypt(encCateoryId));
				List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
				if(null != progressDetails && progressDetails.size()>0){
					copyRightDomain.setMonthlyProgressDetails(progressDetails.get(0));
				}
				
			}
			copyRightDomain.setDtTrDate(new Date());
			copyRightDomain.setNumTrUserId(userInfo.getEmployeeId());
			copyRightDomain.setNumIsValid(1);
			
			copyRightDomain.setStrTitle(copyRightModel.getStrTitle());
			copyRightDomain.setStrAgency(copyRightModel.getStrAgency());
			try{
				copyRightDomain.setDateOfCopyright(DateUtils.dateStrToDate(copyRightModel.getDateOfCopyright()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			if(copyRightModel.getStrCopyrightFiledAwarded().equals("Y"))
			{
				copyRightDomain.setStrcopyrightFiledAwarded("Filed");
				if(!copyRightModel.getDtFilingDate().isEmpty()){
					try {
						copyRightDomain.setDtFilingDate(DateUtils.dateStrToDate(copyRightModel.getDtFilingDate()));
						copyRightDomain.setDtAwardDate(null);
					} catch (ParseException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					}
			}
			else{
				copyRightDomain.setStrcopyrightFiledAwarded("Awarded");
				if(!copyRightModel.getDtAwardDate().isEmpty()){
					try {
						copyRightDomain.setDtAwardDate(DateUtils.dateStrToDate(copyRightModel.getDtAwardDate()));
						copyRightDomain.setDtFilingDate(null);
					} catch (ParseException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					}
			}
			copyRightDomain.setStrReferenceNumber(copyRightModel.getStrReferenceNumber());
			return copyRightDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public CopyRightModel deleteCopyright(CopyRightModel copyRightModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		CopyRightDomain copyRightDomain = copyrightDao.getOne(copyRightModel.getNumCopyRightID());
		//CopyRightDomain copyRightDomain = new CopyRightDomain();
		if(copyRightDomain != null)
			copyRightDomain.setNumIsValid(0);
			copyRightDomain.setDtTrDate(new Date());
			copyRightDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = copyrightDao.save(copyRightDomain).getNumCopyRightID();
		
		return copyRightModel;
	}
	
	public List<CopyRightModel> loadCopyrightDetail(int monthlyProgressId,long categoryId){
		List<CopyRightDomain> domainList = copyrightDao.loadCopyRightDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertCopyRightDomainToModelList(domainList);
		}
		return null;
	}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<CopyRightDomain> domainList = copyrightDao.loadCopyRightByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[6];
			obj[0] = "Reference Number";	
			obj[1] = "Title";	
			obj[2] = "Date of Copyright";
			obj[3] = "Agency";
			obj[4] = "Status";
			obj[5] = "Date";
			
			dataList.add(obj);
		}
		
		for(CopyRightDomain domain : domainList){
			Object[] obj = new Object[6];
			obj[0] =domain.getStrReferenceNumber();
			obj[1] = domain.getStrTitle();
			if(null != domain.getDateOfCopyright()){
				obj[2] =DateUtils.dateToString(domain.getDateOfCopyright());
			}else{
				obj[2] = "";
			}
			obj[3] = domain.getStrAgency();
			obj[4] = domain.getStrcopyrightFiledAwarded();
			if(domain.getStrcopyrightFiledAwarded().equals("Filed")){
				if(null == domain.getDtFilingDate()){
					obj[5] ="-";
				}else{
					obj[5] =DateUtils.dateToString(domain.getDtFilingDate());
				}
				
			}else{
				if(null == domain.getDtAwardDate()){
					obj[5] ="-";
				}else{
				obj[5] =DateUtils.dateToString(domain.getDtAwardDate());}
			}
			
			dataList.add(obj);
		}
		
		return dataList;		
	}

}
