package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.MouCollaborationDao;
import in.pms.transaction.domain.AppreciationLetterDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.MouCollaborationDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.AppreciationLetterModel;
import in.pms.transaction.model.MouCollaborationModel;
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
public class MouCollaborationServiceImpl implements MouCollaborationService{

	@Autowired
	MouCollaborationDao mouCollaborationDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<MouCollaborationModel> loadCollabDetail(int monthlyProgressId,long categoryId){
		List<MouCollaborationDomain> domainList = mouCollaborationDao.loadCollabDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertMouCollabDomainToModelList(domainList);
		}
		return null;
		
	
	}

	public List<MouCollaborationModel> convertMouCollabDomainToModelList(List<MouCollaborationDomain> empCopyRightList){
		List<MouCollaborationModel> list = new ArrayList<MouCollaborationModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				MouCollaborationDomain mouCollaborationDomain = empCopyRightList.get(i);
				MouCollaborationModel mouCollaborationModel = new MouCollaborationModel();
				
				mouCollaborationModel.setNumCollabID(mouCollaborationDomain.getNumCollabID());
				mouCollaborationModel.setStrCollabAgency(mouCollaborationDomain.getStrCollabAgency());
				mouCollaborationModel.setStrObjective(mouCollaborationDomain.getStrObjective());
				mouCollaborationModel.setDtFromDate(DateUtils.dateToString(mouCollaborationDomain.getDtFromDate()));
				mouCollaborationModel.setDtValidityDate(DateUtils.dateToString(mouCollaborationDomain.getDtValidityDate()));
				
				list.add(mouCollaborationModel);
	}
		return list;
	}

	
	/*@Override
	public String checkDuplicateMouCollab(MouCollaborationModel mouCollaborationModel) {
		String result=	"";
			MouCollaborationDomain mouCollaborationDomain = mouCollaborationDao.getMoubyCategoryId(mouCollaborationModel.getNumCategoryId());
			
		 if(null == mouCollaborationDomain){
					return null; 
				 }else if(mouCollaborationModel.getNumCollabID()!= 0){
					 if(mouCollaborationDomain.getNumCollabID() == mouCollaborationModel.getNumCollabID())
					 {
						 return null; 
					 }
					 else{
						 result = "Details already exist!";
						 
					 }
				 }else{
					 if(mouCollaborationDomain.getNumIsValid() == 1){
							result = " Details already exist ! ";
						}
					}
		
				return result;	
		}*/
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public MouCollaborationModel saveUpdateMouCollaboration(MouCollaborationModel mouCollaborationModel){
		MouCollaborationDomain mouCollaborationDomain = convertMouModelToDomain(mouCollaborationModel);
		mouCollaborationDao.save(mouCollaborationDomain).getNumCollabID();
		return mouCollaborationModel;
	}

	private MouCollaborationDomain convertMouModelToDomain(MouCollaborationModel mouCollaborationModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			MouCollaborationDomain mouCollaborationDomain = new MouCollaborationDomain();
			if(mouCollaborationModel.getNumCollabID()!=0){				
				mouCollaborationDomain =  mouCollaborationDao.getOne(mouCollaborationModel.getNumCollabID());
			}
	if(mouCollaborationModel.getNumCollabID()==0){
		String encCategoryId = mouCollaborationModel.getEncCategoryId();
			String encMonthlyProgressId = mouCollaborationModel.getEncMonthlyProgressId();
			long categoryID = Long.parseLong(encryptionService.dcrypt(encCategoryId));
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, categoryID);
			if(null != progressDetails && progressDetails.size()>0){
				mouCollaborationDomain.setMonthlyProgressDetails(progressDetails.get(0));
		
		}
		}
			mouCollaborationDomain.setDtTrDate(new Date());
			mouCollaborationDomain.setNumTrUserId(userInfo.getEmployeeId());
			mouCollaborationDomain.setNumIsValid(1);
			
			mouCollaborationDomain.setStrCollabAgency(mouCollaborationModel.getStrCollabAgency());
			try{
				mouCollaborationDomain.setDtFromDate(DateUtils.dateStrToDate(mouCollaborationModel.getDtFromDate()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			try{
				mouCollaborationDomain.setDtValidityDate(DateUtils.dateStrToDate(mouCollaborationModel.getDtValidityDate()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			mouCollaborationDomain.setStrObjective(mouCollaborationModel.getStrObjective());
		
			return mouCollaborationDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public MouCollaborationModel deleteMouCollaboration(MouCollaborationModel mouCollaborationModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		
		MouCollaborationDomain mouCollaborationDomain = mouCollaborationDao.getOne(mouCollaborationModel.getNumCollabID());
		
		if(mouCollaborationDomain != null)
			mouCollaborationDomain.setNumIsValid(0);
		mouCollaborationDomain.setDtTrDate(new Date());
		mouCollaborationDomain.setNumTrUserId(userInfo.getEmployeeId());
		mouCollaborationDao.save(mouCollaborationDomain).getNumCollabID();
		
		return mouCollaborationModel;
	}
	
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<MouCollaborationDomain> domainList = mouCollaborationDao.loadMouByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[4];
			obj[0] = "Agency";
			obj[1] = "From Date";
			obj[2] = "Validity date";
			obj[3] = "Objective/Purpose";
			dataList.add(obj);
		}
		
		for(MouCollaborationDomain domain : domainList){
			Object[] obj = new Object[4];
			obj[0] =domain.getStrCollabAgency();
			if(null != domain.getDtFromDate()){
				obj[1] =DateUtils.dateToString(domain.getDtFromDate());
			}else{
				obj[1] = "";
			}
			if(null != domain.getDtValidityDate()){
				obj[2] =DateUtils.dateToString(domain.getDtValidityDate());
			}else{
				obj[2] = "";
			}
			obj[3] = domain.getStrObjective();
			
			dataList.add(obj);
		}
		
		return dataList;		
	}

}

