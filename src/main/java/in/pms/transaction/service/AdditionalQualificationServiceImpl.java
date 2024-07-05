package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.transaction.dao.AdditionalQualificationDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.MouCollaborationDao;
import in.pms.transaction.domain.AdditionalQualificationDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.MouCollaborationDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.AdditionalQualificationModel;
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
public class AdditionalQualificationServiceImpl implements AdditionalQualificationService{

	@Autowired
	AdditionalQualificationDao additionalQualificationDao;

	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public List<AdditionalQualificationModel> getAllActiveEmployeeMasterDomain() {
		List<EmployeeMasterDomain> list = employeeMasterDao.getAllActiveEmployee();
		return convertDomainListToModelList(list);
	}
	
	public List<AdditionalQualificationModel> convertDomainListToModelList(List<EmployeeMasterDomain> list){
		List<AdditionalQualificationModel> outputList = new ArrayList<AdditionalQualificationModel>();
		
		for(int i=0;i<list.size();i++){
			AdditionalQualificationModel model = new AdditionalQualificationModel();
			EmployeeMasterDomain domain = list.get(i);
			model.setNumEmployeeID(domain.getNumId());
			model.setStrEmployeeName(domain.getEmployeeName());
			model.setStrEmployeeDesignation(domain.getDesignationMaster().getDesignationName());			
			outputList.add(model);
		}
		
		return outputList;
	}
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public List<AdditionalQualificationModel> getAllAdditionalcollab() {
		return convertDomainToModelList(additionalQualificationDao.getAllAdditionalcollab());
		
	}
	
	public List<AdditionalQualificationModel> convertDomainToModelList(List<AdditionalQualificationDomain> empCopyRightList){
		List<AdditionalQualificationModel> list = new ArrayList<AdditionalQualificationModel>();
			for(int i=0;i<empCopyRightList.size();i++){
				AdditionalQualificationDomain additionalQualificationDomain = empCopyRightList.get(i);
				AdditionalQualificationModel additionalQualificationModel = new AdditionalQualificationModel();
				
				additionalQualificationModel.setNumEmployeeID(additionalQualificationDomain.getNumEmployeeID());
				additionalQualificationModel.setNumQualID(additionalQualificationDomain.getNumQualID());
				additionalQualificationModel.setNumGroupCategoryId(additionalQualificationDomain.getNumGroupCategoryId());
				additionalQualificationModel.setStrCertificateName(additionalQualificationDomain.getStrCertificateName());
				additionalQualificationModel.setStrDescriptionProgram(additionalQualificationDomain.getStrDescriptionProgram());
				additionalQualificationModel.setStrEmployeeDesignation(additionalQualificationDomain.getStrEmployeeDesignation());
				additionalQualificationModel.setStrEmployeeName(additionalQualificationDomain.getStrEmployeeName());
				additionalQualificationModel.setStrFocusArea(additionalQualificationDomain.getStrFocusArea());
				list.add(additionalQualificationModel);
	}
		return list;
	}

	@Override
	public String checkDuplicateAdditionalQual(
			AdditionalQualificationModel additionalQualificationModel) {
		String result=	"";
			AdditionalQualificationDomain additionalQualificationDomain = additionalQualificationDao.getAddQualEmpId(additionalQualificationModel.getNumEmployeeID());
		
		 if(null == additionalQualificationDomain){
					return null; 
				 }else if(additionalQualificationModel.getNumQualID()!= 0){
					 if(additionalQualificationDomain.getNumQualID() == additionalQualificationModel.getNumQualID())
					 {
						 return null; 
					 }
					 else{
						 result = "Details already exist!";
						 
					 }
				 }else{
					 if(additionalQualificationDomain.getNumIsValid() == 1){
							result = " Details already exist ! ";
						}
					}
		
		return result;	
		}

	
@Override
@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
public AdditionalQualificationModel saveUpdateAdditionalQual(
		AdditionalQualificationModel additionalQualificationModel) {
		AdditionalQualificationDomain additionalQualificationDomain = convertModelToDomain(additionalQualificationModel);
		additionalQualificationDao.save(additionalQualificationDomain).getNumQualID();
		return additionalQualificationModel;
	}

	private AdditionalQualificationDomain convertModelToDomain(AdditionalQualificationModel additionalQualificationModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			AdditionalQualificationDomain additionalQualificationDomain = new AdditionalQualificationDomain();
			if(additionalQualificationModel.getNumQualID()!=0){				
				additionalQualificationDomain =  additionalQualificationDao.getOne(additionalQualificationModel.getNumQualID());
			}
			if(additionalQualificationModel.getNumQualID()==0){
				
				long numCateoryId =Long.parseLong(encryptionService.dcrypt(additionalQualificationModel.getEncCategoryId()));
				String encMonthlyProgressId = additionalQualificationModel.getEncMonthlyProgressId();
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
				if(null != progressDetails && progressDetails.size()>0){
					additionalQualificationDomain.setMonthlyProgressDetails(progressDetails.get(0));
				}
				
			}
			EmployeeMasterDomain employeeMasterDomain = employeeMasterDao.getEmployeeMasterById(additionalQualificationModel.getNumEmployeeID());
			additionalQualificationDomain.setDtTrDate(new Date());
			additionalQualificationDomain.setNumTrUserId(userInfo.getEmployeeId());
			additionalQualificationDomain.setNumIsValid(1);
			additionalQualificationDomain.setNumEmployeeID(additionalQualificationModel.getNumEmployeeID());
			additionalQualificationDomain.setNumQualID(additionalQualificationModel.getNumQualID());
			additionalQualificationDomain.setNumGroupCategoryId(additionalQualificationModel.getNumGroupCategoryId());
			additionalQualificationDomain.setStrCertificateName(additionalQualificationModel.getStrCertificateName());
			additionalQualificationDomain.setStrDescriptionProgram(additionalQualificationModel.getStrDescriptionProgram());
			additionalQualificationDomain.setStrEmployeeDesignation(employeeMasterDomain.getDesignationMaster().getDesignationName());
			additionalQualificationDomain.setStrEmployeeName(employeeMasterDomain.getEmployeeName());
			additionalQualificationDomain.setStrFocusArea(additionalQualificationModel.getStrFocusArea());
		
			return additionalQualificationDomain;
		
	}
	
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public AdditionalQualificationModel deleteAddQual(AdditionalQualificationModel additionalQualificationModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		AdditionalQualificationDomain additionalQualificationDomain = additionalQualificationDao.getOne(additionalQualificationModel.getNumQualID());
		
		if(additionalQualificationDomain != null)
			additionalQualificationDomain.setNumIsValid(0);
		additionalQualificationDomain.setDtTrDate(new Date());
		additionalQualificationDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = additionalQualificationDao.save(additionalQualificationDomain).getNumQualID();
		
		return additionalQualificationModel;
	}

	@Override
	public List<AdditionalQualificationModel> loadAdditionalqualificationDetail(
			int monthlyProgressId, long categoryId) {
		List<AdditionalQualificationDomain> domainList = additionalQualificationDao.loadAdditionQualificationDetail(monthlyProgressId,categoryId);
		if(null != domainList){
			return convertDomainToModelList(domainList);
		}
		return null;
	}
	
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<AdditionalQualificationDomain> domainList = additionalQualificationDao.loadAdditionalByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[4];
			
			obj[0] = "Employee Details";
			obj[1] = "Course/Certificate Name";
			obj[2] = "Focus Area";
			obj[3] = "Description of Program";
		
			dataList.add(obj);
		}
		
		for(AdditionalQualificationDomain domain : domainList){
			Object[] obj = new Object[4];
			obj[0] ="<b>"+domain.getStrEmployeeName()+"</b> <br/>("+domain.getStrEmployeeDesignation()+")";
			obj[1] = domain.getStrCertificateName();
			obj[2] = domain.getStrFocusArea();
			obj[3] = domain.getStrDescriptionProgram();
		
			dataList.add(obj);
		}
		
		return dataList;		
	}



}
