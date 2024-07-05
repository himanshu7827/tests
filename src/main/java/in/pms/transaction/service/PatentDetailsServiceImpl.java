package in.pms.transaction.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import in.pms.global.service.EncryptionService;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectBudgetDomain;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.PatentDetailsDao;
import in.pms.transaction.dao.ProjectPublicationDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.model.PublicationDetailsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class PatentDetailsServiceImpl implements PatentDetailsService{
	
	@Autowired
	PatentDetailsDao patentDetailsDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;

	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public PatentDetailsModel SaveProjectPatentDetails(PatentDetailsModel patentDetailsModel) {
		//setting the user Id from the session 
				
		// TODO Auto-generated method stub
				PatentDetailsDomain patentDetailsDomain=ModelToDomain(patentDetailsModel);
				
		int t= patentDetailsDao.save(patentDetailsDomain).getNumProjectPatentId();
		if(t!=0){
			//patentDetailsModel.setProjectPatentId(t);
			return patentDetailsModel;
		}else {
			return null;
		}
	}
	
	public PatentDetailsDomain ModelToDomain(PatentDetailsModel patentDetailsModel){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
		
		PatentDetailsDomain patentDetailsDomain = null;
		
		if(patentDetailsModel.getProjectPatentId() >0){
			patentDetailsDomain = patentDetailsDao.getOne(patentDetailsModel.getProjectPatentId());
		}
		
		if(null == patentDetailsDomain){
			patentDetailsDomain = new PatentDetailsDomain();
			
			long numCateoryId =Long.parseLong(encryptionService.dcrypt(patentDetailsModel.getEncCategoryId()));
			String encMonthlyProgressId = patentDetailsModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
			if(null != progressDetails && progressDetails.size()>0){
				patentDetailsDomain.setMonthlyProgressDetails(progressDetails.get(0));
			}
		}	
		patentDetailsDomain.setDtTrDate(new Date());
		patentDetailsDomain.setNumIsValid(1);
		patentDetailsDomain.setNumTrUserId(userInfo.getEmployeeId());
		//patentDetailsDomain.setNumReportId(patentDetailsModel.getNumReportId());
		patentDetailsDomain.setStrPatentTitle(patentDetailsModel.getStrPatentTitle());
		patentDetailsDomain.setStrInventorName(patentDetailsModel.getStrInventorName());
		patentDetailsDomain.setStrInventorAddress(patentDetailsModel.getStrInventorAddr());
		//patentDetailsDomain.setNumReportId(patentDetailsModel.getNumReportId());
		patentDetailsDomain.setStrReferenceNum(patentDetailsModel.getStrReferenceNum());
		if(patentDetailsModel.getStrIsFiled().equals("Y"))
		{
			patentDetailsDomain.setStrIsFiled("Filed");
			if(!patentDetailsModel.getDtFilingDate().isEmpty()){
				try {
					patentDetailsDomain.setDtFilingDate(DateUtils.dateStrToDate(patentDetailsModel.getDtFilingDate()));
					patentDetailsDomain.setDtAwardDate(null);
				} catch (ParseException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				}
		}
		else{
			patentDetailsDomain.setStrIsFiled("Awarded");
			if(!patentDetailsModel.getDtAwardDate().isEmpty()){
				try {
					patentDetailsDomain.setDtAwardDate(DateUtils.dateStrToDate(patentDetailsModel.getDtAwardDate()));
					patentDetailsDomain.setDtFilingDate(null);
				} catch (ParseException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				}
		}
		patentDetailsDomain.setStrIsAwarded(patentDetailsModel.getStrIsAwarded());
		patentDetailsDomain.setStrCountryDetials(patentDetailsModel.getStrCountryDetials());
		patentDetailsDomain.setStrStatus(patentDetailsModel.getStrStatus());
		return patentDetailsDomain;
		
	}

	@Override
	public List<PatentDetailsModel> getProjectPatentDetails(int reportId) {
		//List<PatentDetailsModel> modelList=new ArrayList<PatentDetailsModel>();
		List<PatentDetailsDomain> domainList=patentDetailsDao.getPublicationDetail(reportId);
		if(domainList.size()>0){
			return convertToModelList(domainList);
		}else{
			return null;
		}
	}
	
	@Override
	public List<PatentDetailsModel> loadPatentDetail(int monthlyProgressId,long categoryId){
		List<PatentDetailsDomain> domainList = patentDetailsDao.loadPatentDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertToModelList(domainList);
		}
		return null;
		
	}
	
	
	
	
	public List<PatentDetailsModel>  convertToModelList(List<PatentDetailsDomain> domainList){
		List<PatentDetailsModel> modelList = new ArrayList<PatentDetailsModel>();
		for(int i=0;i<domainList.size();i++){
			PatentDetailsModel patentDetailsModel = new PatentDetailsModel();
			PatentDetailsDomain patentDetailsDomain=domainList.get(i);
			patentDetailsModel.setProjectPatentId(patentDetailsDomain.getNumProjectPatentId());
			patentDetailsModel.setStrPatentTitle(patentDetailsDomain.getStrPatentTitle());
		//	patentDetailsModel.setNumReportId(patentDetailsDomain.getNumReportId());
			patentDetailsModel.setStrInventorName(patentDetailsDomain.getStrInventorName());
			patentDetailsModel.setStrInventorAddr(patentDetailsDomain.getStrInventorAddress());
			//patentDetailsModel.setNumReportId(patentDetailsDomain.getNumReportId());
			patentDetailsModel.setStrReferenceNum(patentDetailsDomain.getStrReferenceNum());
			patentDetailsModel.setStrIsFiled(patentDetailsDomain.getStrIsFiled());
			//patentDetailsModel.setStrIsAwarded(patentDetailsDomain.getStrIsAwarded());
			patentDetailsModel.setStrCountryDetials(patentDetailsDomain.getStrCountryDetials());
			patentDetailsModel.setStrStatus(patentDetailsDomain.getStrStatus());
			if(patentDetailsDomain.getStrIsFiled().equals("Filed"))
			{
				patentDetailsModel.setDtFilingDate(DateUtils.dateToString(patentDetailsDomain.getDtFilingDate()));
			}
			else
			{
				patentDetailsModel.setDtAwardDate(DateUtils.dateToString(patentDetailsDomain.getDtAwardDate()));
			}

			modelList.add(patentDetailsModel);
		}
		return modelList;
		
	}

	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<PatentDetailsDomain> domainList = patentDetailsDao.loadPatentByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[7];
			obj[0] = MessageBundleFile.getValueFromKey("patentdetails.refNumber");
			obj[1] = MessageBundleFile.getValueFromKey("patentdetails.title");
			obj[2] = MessageBundleFile.getValueFromKey("patentdetails.inventorname");			
			obj[3] = MessageBundleFile.getValueFromKey("patentdetails.country");
			obj[4] = MessageBundleFile.getValueFromKey("forms.status");
			obj[5] = "Date";
			obj[6] = MessageBundleFile.getValueFromKey("patentdetails.inventor.address");
			
			dataList.add(obj);
		}
		
		for(PatentDetailsDomain domain : domainList){
			Object[] obj = new Object[7];
			obj[0] =domain.getStrReferenceNum();
			obj[1] = domain.getStrPatentTitle();
			obj[2] = domain.getStrInventorName();
			obj[3] = domain.getStrCountryDetials();
			obj[4] = domain.getStrIsFiled();
			if(domain.getStrIsFiled().equals("Filed")){
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
			
			obj[6] = domain.getStrInventorAddress();
			dataList.add(obj);
		}		
		return dataList;		
	}
	
	@Override
	public void deletePatentDetails(PatentDetailsModel patentDetailsModel) {
		int count= patentDetailsModel.getProjectPatentIds().length;
		//System.out.println("count"+count);
		int[] patentIds= new int[count];
		patentIds=patentDetailsModel.getProjectPatentIds();
		
		for(int i=0;i<count;i++)
		{
	
			if(patentIds[i]!=0)
			{
				
				PatentDetailsDomain patentDetailsDomain=new PatentDetailsDomain();
				patentDetailsDomain=patentDetailsDao.getOne(patentIds[i]);
				patentDetailsDomain.setNumIsValid(0);
				patentDetailsDomain.setDtTrDate(new Date());
				
				patentDetailsDao.save(patentDetailsDomain);
			}
			
		}	
		
		
	}
	
	
}

	
