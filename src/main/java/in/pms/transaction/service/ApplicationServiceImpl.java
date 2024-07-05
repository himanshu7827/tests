package in.pms.transaction.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ClientContactPersonMasterDao;
import in.pms.master.dao.ClientMasterDao;
import in.pms.master.dao.EndUserMasterDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.dao.ThrustAreaMasterDao;
import in.pms.master.domain.BusinessTypeMaster;
import in.pms.master.domain.ClientMasterDomain;
import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.master.domain.ProjectTypeMaster;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.service.BusinessTypeService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.ProjectCategoryService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectTypeService;
import in.pms.master.service.ProposalMasterService;
import in.pms.transaction.dao.ApplicationDao;
import in.pms.transaction.domain.Application;
import in.pms.transaction.model.ApplicationModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	BusinessTypeService businessTypeService;
	@Autowired
	ProjectTypeService projectTypeService;
	@Autowired
	ProjectCategoryService projectCategoryService;
	@Autowired
	OrganisationMasterService organisationMasterService;
	@Autowired
	GroupMasterService groupMasterService;	
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ApplicationDao applicationDao;
	
	@Autowired
	ThrustAreaMasterDao thrustAreaMasterDao;
	
	@Autowired
	ClientMasterDao clientMasterDao;
	
	@Autowired
	ClientContactPersonMasterDao clientContactPersonMasterDao;
	
	@Autowired
	ProposalMasterService proposalMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	EndUserMasterDao endUserMasterDao;
	@Autowired
	ProposalMasterDao proposalMasterDao;
	
	@Override
	public ApplicationModel saveUpdateApplication(ApplicationModel applicationModel) {		
		Application application = convertApplicationModelToDomain(applicationModel);
		application = applicationDao.mergeApplication(application);
		applicationModel.setNumId(application.getNumId());
		applicationModel.setEncNumId(encryptionService.encrypt(""+application.getNumId()));
		ProposalMasterModel proposalMasterModel =proposalMasterService.getProposalMasterByApplicationId(application.getNumId());
		if(null == proposalMasterModel){
			proposalMasterService.createEmptyProposalDetails(application);
		}
		return applicationModel;
	}
	
	
	
	
	public Application convertApplicationModelToDomain(ApplicationModel applicationModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		Application domain  = new Application();
		//Added by devesh on 01-02-24 to set zero Total Cost for Internal Projects
		if(applicationModel.getCategoryId()==7){
			applicationModel.setTotalProposalCost(0);
		}
		//End of setting CDAC Outlay
		if(applicationModel.getNumId() != 0){
			domain = applicationDao.getApplicationById(applicationModel.getNumId());
			//domain.setNumId(applicationModel.getNumId());
		}
		if(applicationModel.isCorporateApproval()){
			domain.setNumCorporateApproval(1);;
		}else{
			domain.setNumCorporateApproval(0);

		}
		if(null!=applicationModel.getDateOfSubmission() && !applicationModel.getDateOfSubmission().equals("")){
			try {
		domain.setDateOfSub(DateUtils.dateStrToDate(applicationModel.getDateOfSubmission()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		if(null!=applicationModel.getClearanceReceivedDate() && !applicationModel.getClearanceReceivedDate().equals("")){
			try {
		domain.setClearanceReceiveDate(DateUtils.dateStrToDate(applicationModel.getClearanceReceivedDate()));
		} 
			catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		domain.setProposalTitle(applicationModel.getProposalTitle());
		//domain.setBusinessType(businessTypeService.getBusinessTypeById(applicationModel.getBusinessTypeId()));
		domain.setBusinessType(businessTypeService.getBusinessTypeById(0));
		domain.setCategoryMaster(projectCategoryService.getProjectCategoryById(applicationModel.getCategoryId()));
		domain.setGroupMaster(groupMasterService.getGroupMasterDomainById(applicationModel.getGroupId()));		
		domain.setProjectTypeMaster(projectTypeService.getProjectTypeById(applicationModel.getProjectTypeId()));
		domain.setNumTrUserId(userInfo.getEmployeeId());
		domain.setNumIsValid(1);
		domain.setDtTrDate(new Date());
		List<ThrustAreaMasterDomain> thrustAreaMasterDomain= thrustAreaMasterDao.getAllThrustDataByIdList(applicationModel.getThrustAreaId());			

		Set<ThrustAreaMasterDomain> set=new HashSet<ThrustAreaMasterDomain>();
		set.addAll(thrustAreaMasterDomain);
		domain.setThrustAreas(set);
		domain.setClientMaster(clientMasterDao.getOne(applicationModel.getClientId()));
		domain.setEndUserMaster(endUserMasterDao.getEndUserMasterDomainById(applicationModel.getEndUserId()));
		List<ContactPersonMasterDomain> contactPersonMasterList=new ArrayList<ContactPersonMasterDomain>();
		if(applicationModel.getContactPersonId().size()>0)
		contactPersonMasterList = clientContactPersonMasterDao.getContactPersonMasterById(applicationModel.getContactPersonId());
		Set<ContactPersonMasterDomain> set2=new HashSet<ContactPersonMasterDomain>();
		set2.addAll(contactPersonMasterList);
		domain.setContactMaster(set2);		
		domain.setTotalProposalCost(applicationModel.getTotalProposalCost());		
		domain.setCollaborative(applicationModel.isCollaborative());
		return domain;
	}

	@Override
	public List<ProjectMasterForm> getApplicationByGroupName(String groupName){
		List<Application> applicationList = applicationDao.getApplicationByGroupName(groupName);
		List<ProjectMasterForm> finalList = new ArrayList<ProjectMasterForm>();
		for(int i=0;i<applicationList.size();i++){
			Application application= applicationList.get(i);
			List<ProjectMasterForm> dataList = projectMasterService.convertBudgetMasterDomainToModelList(application.getProjectMaster());
			finalList.addAll(dataList);
		}
		
		return finalList;
	}



	/*public ApplicationModel getApplicationById(long applicationId) {
		Application application = applicationDao.getApplicationById(applicationId);
		return convertApplicationDomainToModel(application);		
	}*/

	//Function modified by devesh on 13-02-24 to set DateOfSubEqualsCurrentYear flag in the modal
	public ApplicationModel getApplicationById(long applicationId) {
		Application application = applicationDao.getApplicationById(applicationId);
		List<ProposalMasterDomain> proposalList = applicationDao.getProposalByApplicationId(applicationId);
        ApplicationModel model = convertApplicationDomainToModel(application);
        if (!proposalList.isEmpty()) {
            Date dateOfSubmission = proposalList.get(0).getDateOfSubmission();
            model.setDateOfSubEqualsCurrentYear(isSubmissionYearCurrent(dateOfSubmission));
            model.setStrStatus(proposalList.get(0).getStatus());
        } else {
            model.setDateOfSubEqualsCurrentYear(true);
        }
        /*------ Check group proposal year check  [12-04-2024]----*/
        long proposalYearCheck = groupMasterService.getGroupMasterDomainById(model.getGroupId()).getProposalYearCheck();
        if(proposalYearCheck==1){
        	model.setProposalYearCheck(true);
        	
        }else{
        	model.setProposalYearCheck(false);
        }
        /*----- End of Check group proposal year check  [12-04-2024]----*/
		return model;
	}
	//End of Function

	//Function added by devesh on 13-02-24 to check if date of submission is same as current year
	private boolean isSubmissionYearCurrent(Date dateOfSubmission) {
        if (dateOfSubmission != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateOfSubmission);
            int submissionYear = cal.get(Calendar.YEAR);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return submissionYear == currentYear;
        }
        return true; // If dateOfSubmission is null
    }
	//End of Function

	private ApplicationModel convertApplicationDomainToModel(Application application) {
		ApplicationModel model = new ApplicationModel();
		model.setBusinessTypeId(application.getBusinessType().getNumId());
		model.setClientId(application.getClientMaster().getNumId());
		if(application.getEndUserMaster() !=null)
		model.setEndUserId(application.getEndUserMaster().getNumId());
		model.setProjectTypeId(application.getProjectTypeMaster().getNumId());
		model.setCategoryId(application.getCategoryMaster().getNumId());
		//GroupMasterDomain groupMasterDomain = application.getGroupMaster();
		model.setOrganisationId(application.getGroupMaster().getOrganisationMasterDomain().getNumId());
		model.setGroupId(application.getGroupMaster().getNumId());
		model.setNumId(application.getNumId());
		model.setEncNumId(encryptionService.encrypt(application.getNumId()+""));
		model.setCollaborative(application.isCollaborative());
		Set<ThrustAreaMasterDomain> thrustAreas=application.getThrustAreas();
		final List<Long> collection = new ArrayList<Long>();
		for (final ThrustAreaMasterDomain thrustAreaMasterDomain : thrustAreas) {			
			collection.add(thrustAreaMasterDomain.getNumId());
		}
		model.setThrustAreaId(collection);
		Set<ContactPersonMasterDomain> contactMaster=application.getContactMaster();
		final List<Long> contacts = new ArrayList<Long>();
		for (final ContactPersonMasterDomain contactPersonMasterDomain : contactMaster) {			
			contacts.add(contactPersonMasterDomain.getNumId());
		}
		model.setContactPersonId(contacts);
		model.setProposalTitle(application.getProposalTitle());
		model.setTotalProposalCost(application.getTotalProposalCost());
		model.setConvertedToProject(application.isConvertedToProject());
		if(application.getNumCorporateApproval() ==1){
			model.setCorporateApproval(true);
		}else{
			model.setCorporateApproval(false);
		}
		if(null!=application.getDateOfSub()){
		model.setDateOfSubmission(DateUtils.dateToString(application.getDateOfSub()));
		}
		if(null!=application.getClearanceReceiveDate()){
		model.setClearanceReceivedDate(DateUtils.dateToString(application.getClearanceReceiveDate()));
		}
		return model;
		
		
	}
	
	@Override
	public List<ApplicationModel> getAllApplicaionData(){
		return convertApplicationDomainToModelList(applicationDao.getAllApplicaionData());			
	}
	
//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero
	@Override
	public List<Object[]> getAllApplicationDataList() {
		List<Object[]> list = applicationDao.getAllApplicationDataList();
		List<Object[]> list1 = new ArrayList<>();
		for (Object[] row : list) {
			// Assuming the first value in each row is a BigInteger value that you want to encrypt
            BigInteger originalValue = (BigInteger) row[0];
            String encryptedValue = encryptionService.encrypt(""+originalValue); // Implement your encryption logic here

            // Create a new row with the encrypted value appended
            Object[] modifiedRow = new Object[row.length + 1];
            System.arraycopy(row, 0, modifiedRow, 0, row.length);
            modifiedRow[row.length] = encryptedValue;

            list1.add(modifiedRow);
        }

		/*for (Object[] array1 : list1) {
		    StringBuilder sb = new StringBuilder("[");
		    for (Object obj : array1) {
		        sb.append(obj).append(", ");
		    }
		    // Remove the trailing ", " and add a closing bracket
		    sb.setLength(sb.length() - 2);
		    sb.append("]");
		    System.out.println(sb.toString());
		}*/
		return list1;
		
	}
	//End of Application DataList
	
	public List<ApplicationModel>convertApplicationDomainToModelList(List<Application> applicationList){
		
		List<ApplicationModel>list=new ArrayList<ApplicationModel>();
		for(int i=0;i<applicationList.size();i++){
			Application application=applicationList.get(i);
			ApplicationModel applicationModel=new ApplicationModel();
			applicationModel.setProposalTitle(application.getProposalTitle());
			applicationModel.setTotalProposalCost(application.getTotalProposalCost());
			applicationModel.setNumId(application.getNumId());
			ProposalMasterModel proposalMasterModel	=proposalMasterService.getProposalMasterByApplicationId(application.getNumId());
			applicationModel.setStrStatus(proposalMasterModel.getStatus());
			applicationModel.setEncNumId(encryptionService.encrypt(""+application.getNumId()));
			BusinessTypeMaster businessTypeMaster=application.getBusinessType();
			ProjectTypeMaster projectTypeMaster=application.getProjectTypeMaster();
			ProjectCategoryMaster projectCategoryMaster=application.getCategoryMaster();
			GroupMasterDomain groupMasterDomain=application.getGroupMaster();
			applicationModel.setBusinessTypeName(businessTypeMaster.getBusinessTypeName());
			applicationModel.setProjectTypeName(projectTypeMaster.getProjectTypeName());
			applicationModel.setCategoryName(projectCategoryMaster.getCategoryName());
			applicationModel.setGroupName(groupMasterDomain.getGroupName());
			applicationModel.setOrganisationName(groupMasterDomain.getOrganisationMasterDomain().getOrganisationName());
			ClientMasterDomain clientMasterDomain = application.getClientMaster();
			if(clientMasterDomain !=null){
			applicationModel.setClientId(clientMasterDomain.getNumId());
			applicationModel.setClientName(clientMasterDomain.getClientName());
			}
			applicationModel.setConvertedToProject(application.isConvertedToProject());
			List<ProposalMasterDomain> proposalMasterDomain=application.getProposalMaster();
			if(proposalMasterDomain.size()>0 && proposalMasterDomain.get(0).getStrProposalRefNo()!= null){
			applicationModel.setProposalRefNo(proposalMasterDomain.get(0).getStrProposalRefNo());
			}
			list.add(applicationModel);
		}
		return list;
	}
	
	public List<ApplicationModel> convertApplicationObjectToModelList(List<Object[]>applicationObjectList){
		List<ApplicationModel> list = new ArrayList<ApplicationModel>();
			for(int i=0;i<applicationObjectList.size();i++){			
				Object[] applicationDomain = applicationObjectList.get(i);	
				
				ApplicationModel applicationModel = new ApplicationModel();
				applicationModel.setProposalTitle((String)applicationDomain[0]);
				applicationModel.setGroupName((String)applicationDomain[1]);
				list.add(applicationModel);
	}
		return list;
	}

	@Override
	public List<ApplicationModel> getProposalDetailByGruopId(long groupId) {
		return convertApplicationDomainToModelList(applicationDao.getProposalDetailByGruopId(groupId));
		
	}
	
//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero
	@Override
	public List<Object[]> getProposalDetailByGruopIdnew(long groupId) {
		List<Object[]> list = applicationDao.getProposalDetailByGruopIdnew(groupId);
		List<Object[]> list1 = new ArrayList<>();
		for (Object[] row : list) {
			// Assuming the first value in each row is a BigInteger value that you want to encrypt
            BigInteger originalValue = (BigInteger) row[0];
            String encryptedValue = encryptionService.encrypt(""+originalValue); // Implement your encryption logic here

            // Create a new row with the encrypted value appended
            Object[] modifiedRow = new Object[row.length + 1];
            System.arraycopy(row, 0, modifiedRow, 0, row.length);
            modifiedRow[row.length] = encryptedValue;

            list1.add(modifiedRow);
        }

		/*for (Object[] array1 : list1) {
		    StringBuilder sb = new StringBuilder("[");
		    for (Object obj : array1) {
		        sb.append(obj).append(", ");
		    }
		    // Remove the trailing ", " and add a closing bracket
		    sb.setLength(sb.length() - 2);
		    sb.append("]");
		    System.out.println(sb.toString());
		}*/
		return list1;
		
	}
	//End of Service
	
	@Override
	public JSONArray getProposalCountByGroup() {
	return null;
	}
	
	@Override
	public String createApplicationCopy(long applicationId){
		return applicationDao.createApplicationCopy(applicationId);
	}
	
	@Override
	public long getProposalIdByProjectId(long projectId){
		return applicationDao.getProposalIdByProjectId(projectId);
	}

	/*Added by devesh on 25-01-24 to get proposal with date range date range*/
	@Override
	public long getProposalIdByProjectIdAndDate(long projectId, String startRange, String endRange){
		return applicationDao.getProposalIdByProjectIdAndDate(projectId, startRange, endRange);
	}
	/*End of Function*/

	@Override
	public int deleteApplication(long applicationId) {
		int status=1;
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		List<ProposalMasterDomain> domain=applicationDao.getProposalByApplicationId(applicationId);
		Application application =null;
		
		if(null != domain  && domain.size()>0){
			application= domain.get(0).getApplication();
			application.setNumIsValid(2);
			application.setNumTrUserId(userInfo.getEmployeeId());
			application.setDtTrDate(new Date());
			application = applicationDao.mergeApplication(application);
			
			ProposalMasterDomain proposalMasterDomain=domain.get(0);
			proposalMasterDomain.setNumIsValid(2);
			proposalMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			proposalMasterDomain.setDtTrDate(new Date());
			proposalMasterDomain.setStatus("DEL");
			proposalMasterDao.saveUpdateProposalMaster(proposalMasterDomain);
		}else{
			status=3;
		}
		
		
		}
		catch(Exception e){
			e.printStackTrace();
			status=2;
		}
		
		return status;
	}
}
