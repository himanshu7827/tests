package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProposalMasterModel;
import in.pms.transaction.dao.ApplicationDao;
import in.pms.transaction.domain.Application;
import in.pms.transaction.service.ApplicationService;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class ProposalMasterServiceImpl implements ProposalMasterService {
	@Autowired
	ProposalMasterDao proposalMasterDao;
	@Autowired
	GroupMasterDao groupMasterDao;
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ApplicationDao applicationDao;
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROPOSAL_MST')")
	public ProposalMasterModel saveUpdateProposalMaster(ProposalMasterModel proposalMasterModel) {
		ProposalMasterDomain proposalMasterDomain=	convertProposalMasterModelToDomain(proposalMasterModel);
		proposalMasterDomain=proposalMasterDao.saveUpdateProposalMaster(proposalMasterDomain);
		proposalMasterModel.setNumId(proposalMasterDomain.getNumId());
		if(proposalMasterModel.getRemarks()!=null&&!(proposalMasterModel.getRemarks().isEmpty())){
			proposalMasterDao.updateProposaCopyRemark(proposalMasterModel.getApplicationId(),proposalMasterModel.getRemarks());
		}
		return proposalMasterModel;
	}
	
	@SuppressWarnings("null")
	@Override
	public String checkDuplicateProposalName(ProposalMasterModel proposalMasterModel){
		String result = "";
		Application application = proposalMasterDao.getProposalByName(proposalMasterModel.getProposalTitle());
	
		 if(null == application){
			return null; 
		 }else if(proposalMasterModel.getNumId() != 0){
			 if(application.getNumId() == proposalMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Client with same name already exist with Id "+proposalMasterModel.getNumId();
			 }
		 }else{
			if(application.getNumIsValid() == 0){
				result = "Client Already Registered with Id "+application.getNumId() +". Please activate same record";
			}else{
				result = "Client Already Registered with Id "+application.getNumId();
			}			
		 }
		return result;
	}
	
	
	@Override
	public ProposalMasterModel getProposalMasterDomainById(long numId){
		return convertProposalMasterDomainToModel(proposalMasterDao.getProposalMasterDomainById(numId));
	}
	
	@Override
	public ProposalMasterDomain getProposalDomainById(long numId){
		return proposalMasterDao.getProposalMasterDomainById(numId);
	}

	@Override
	@PreAuthorize("hasAuthority('READ_PROPOSAL_MST')")
	public List<ProposalMasterModel> getAllProposalMasterDomain(){
		return convertProposalMasterDomainToModelList(proposalMasterDao.getAllProposalMasterDomain());
	}
	
	
	@Override
	public List<ProposalMasterModel> getAllActiveProposalMasterDomain(){		
		return convertProposalMasterDomainToModelList(proposalMasterDao.getAllActiveProposalMasterDomain());
	}
	
	public ProposalMasterModel convertProposalMasterDomainToModel(ProposalMasterDomain proposalMasterDomain){
		ProposalMasterModel proposalMasterModel= new ProposalMasterModel();
		Application application =proposalMasterDomain.getApplication();
		proposalMasterModel.setProposalTitle(application.getProposalTitle());
		
		proposalMasterModel.setProjectTypeName(application.getProjectTypeMaster().getProjectTypeName());
		proposalMasterModel.setProjectCategory(application.getCategoryMaster().getCategoryName());
		proposalMasterModel.setOrganisationName(application.getGroupMaster().getOrganisationMasterDomain().getOrganisationName());
		proposalMasterModel.setGroupName(application.getGroupMaster().getGroupName());
		Set<ThrustAreaMasterDomain> thrustAreas=application.getThrustAreas();
		String collection=null ;
		for (final ThrustAreaMasterDomain thrustAreaMasterDomain : thrustAreas) {			
			collection=thrustAreaMasterDomain.getStrThrustAreaName();
		}
		proposalMasterModel.setThrustArea(collection);		
		Set<ContactPersonMasterDomain> contactMaster=application.getContactMaster();
		String contactperson=null;
		for (final ContactPersonMasterDomain contactPersonMasterDomain : contactMaster) {			
			contactperson=contactPersonMasterDomain.getStrContactPersonName();
		}
		proposalMasterModel.setContactPerson(contactperson);
		proposalMasterModel.setProposalAbstract(proposalMasterDomain.getProposalAbstract());
		proposalMasterModel.setProposalCost(proposalMasterDomain.getProposalCost());
		proposalMasterModel.setDuration(proposalMasterDomain.getDuration());
		proposalMasterModel.setSubmittedBy(proposalMasterDomain.getSubmittedBy());
		proposalMasterModel.setSubmittedTo(proposalMasterDomain.getApplication().getClientMaster().getClientName());
		proposalMasterModel.setObjectives(proposalMasterDomain.getObjectives());
		proposalMasterModel.setBackground(proposalMasterDomain.getBackground());
		proposalMasterModel.setSummary(proposalMasterDomain.getSummary());
		proposalMasterModel.setTotalProposalCost(proposalMasterDomain.getApplication().getTotalProposalCost());		
		proposalMasterModel.setGroupId(proposalMasterDomain.getGroupId());
		proposalMasterModel.setOrganisationId(proposalMasterDomain.getOrganisationId());
		proposalMasterModel.setProposalStatus(proposalMasterDomain.getProposalStatus());
		if(proposalMasterDomain.getApplication().getNumCorporateApproval() ==1){
			proposalMasterModel.setCorporateApproval(true);
		}else{
			proposalMasterModel.setCorporateApproval(false);
		}
		if(proposalMasterDomain.getApplication().getDateOfSub()!=null){
			proposalMasterModel.setDateOfSub(DateUtils.dateToString(proposalMasterDomain.getApplication().getDateOfSub()));
		}
		if(proposalMasterDomain.getApplication().getClearanceReceiveDate()!=null){
			proposalMasterModel.setClearanceReceiveDate(DateUtils.dateToString(proposalMasterDomain.getApplication().getClearanceReceiveDate()));
		}
		
		if(proposalMasterDomain.getDateOfSubmission()!=null)
		proposalMasterModel.setDateOfSubmission(DateUtils.dateToString(proposalMasterDomain.getDateOfSubmission()));
		
		if(proposalMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+proposalMasterDomain.getNumId());
			proposalMasterModel.setEncStrId(encryptedId);
		}
		proposalMasterModel.setNumId(proposalMasterDomain.getNumId());
		if(proposalMasterDomain.getNumIsValid()==1){
			proposalMasterModel.setValid(true);
		}else{
			proposalMasterModel.setValid(false);
		}
		if(application !=null){
			proposalMasterModel.setApplicationId(application.getNumId());
			proposalMasterModel.setEncApplicationId(encryptionService.encrypt(""+application.getNumId()));
			proposalMasterModel.setConvertedToProject(application.isConvertedToProject());
		}
		
		proposalMasterModel.setStatus(proposalMasterDomain.getStatus());
		proposalMasterModel.setRemarks(proposalMasterDomain.getRemarks());
		proposalMasterModel.setProposalRefNo(proposalMasterDomain.getStrProposalRefNo());
		if(proposalMasterDomain.getApplication().getEndUserMaster()!=null)
		proposalMasterModel.setEndUser(proposalMasterDomain.getApplication().getEndUserMaster().getClientName());

		return proposalMasterModel;
	}
	
	public List<ProposalMasterModel> convertProposalMasterDomainToModelList(List<ProposalMasterDomain> proposalMasterDomainList){
		
		List<ProposalMasterModel> list = new ArrayList<ProposalMasterModel>();
		for(int i=0;i<proposalMasterDomainList.size();i++){
			ProposalMasterDomain proposalMasterDomain = proposalMasterDomainList.get(i);
			ProposalMasterModel proposalMasterModel = new ProposalMasterModel();
			Application app=proposalMasterDomain.getApplication();
			if(app!=null){
				proposalMasterModel.setProposalTitle(proposalMasterDomain.getApplication().getProposalTitle());
				proposalMasterModel.setEncApplicationId(encryptionService.encrypt(""+app.getNumId()));
				proposalMasterModel.setConvertedToProject(app.isConvertedToProject());
			}else
				proposalMasterModel.setProposalTitle("");
			
			proposalMasterModel.setProposalAbstract(proposalMasterDomain.getProposalAbstract());
			proposalMasterModel.setProposalCost(proposalMasterDomain.getProposalCost());
			proposalMasterModel.setDuration(proposalMasterDomain.getDuration());
			proposalMasterModel.setSubmittedBy(proposalMasterDomain.getSubmittedBy());
			proposalMasterModel.setObjectives(proposalMasterDomain.getObjectives());
			proposalMasterModel.setBackground(proposalMasterDomain.getBackground());
			
			proposalMasterModel.setGroupId(proposalMasterDomain.getGroupId());
			proposalMasterModel.setOrganisationId(proposalMasterDomain.getOrganisationId());
			
			proposalMasterModel.setProposalStatus(proposalMasterDomain.getProposalStatus());			
			proposalMasterModel.setStatus(proposalMasterDomain.getStatus());
			proposalMasterModel.setRemarks(proposalMasterDomain.getRemarks());
			if(proposalMasterDomain.getNumId() != 0){
				String encryptedId = encryptionService.encrypt(""+proposalMasterDomain.getNumId());
				proposalMasterModel.setEncStrId(encryptedId);
			}
			proposalMasterModel.setNumId(proposalMasterDomain.getNumId());
			if(proposalMasterDomain.getNumIsValid()==1){
				proposalMasterModel.setValid(true);
			}else{
				proposalMasterModel.setValid(false);
			}
			
					
			list.add(proposalMasterModel);
		}
		
		return list;	
	}
	

	
	public ProposalMasterDomain convertProposalMasterModelToDomain(ProposalMasterModel proposalMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProposalMasterDomain proposalMasterDomain = new ProposalMasterDomain();
		//proposalMasterDomain.setProposalTitle(proposalMasterModel.getProposalTitle());
		proposalMasterDomain.setProposalAbstract(proposalMasterModel.getProposalAbstract());
		/*if(null !=proposalMasterModel.getDateOfSubmission()){
			try {
				proposalMasterDomain.setDateOfSubmission(DateUtils.dateStrToDate(proposalMasterModel.getDateOfSubmission()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}*/
		if(proposalMasterModel.getNumId() != 0){
			proposalMasterDomain = proposalMasterDao.getProposalMasterDomainById(proposalMasterModel.getNumId());
			//domain.setNumId(applicationModel.getNumId());
		}
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date convertedDate=null;
		if(null != proposalMasterModel.getDateOfSubmission() && !proposalMasterModel.getDateOfSubmission().equals("")){
			try {
				convertedDate = dateFormat.parse(proposalMasterModel.getDateOfSubmission());
		        proposalMasterDomain.setDateOfSubmission(convertedDate);
			} catch (ParseException e) {
				e.printStackTrace();				
			}
		}

		proposalMasterDomain.setDtTrDate(new Date());
		if(proposalMasterModel.getProposalCost()!=0){
			proposalMasterDomain.setProposalCost(proposalMasterModel.getProposalCost());
		}
		//Added by devesh on 01-02-24 to set zero CDAC Outlay for Internal Projects
		if(proposalMasterModel.getCategoryId()==7){
			proposalMasterDomain.setProposalCost(0);
		}
		//End of setting CDAC Outlay
		if(proposalMasterModel.getDuration()!=0){
		proposalMasterDomain.setDuration(proposalMasterModel.getDuration());
		}
		proposalMasterDomain.setSubmittedBy(proposalMasterModel.getSubmittedBy());
		
		proposalMasterDomain.setObjectives(proposalMasterModel.getObjectives());
		proposalMasterDomain.setSummary(proposalMasterModel.getSummary());
		
		proposalMasterDomain.setBackground(proposalMasterModel.getBackground());
		//proposalMasterDomain.setClientId(proposalMasterModel.getClientId());
		//proposalMasterDomain.setGroupId(proposalMasterModel.getGroupId());
		//proposalMasterDomain.setThrustAreaId(proposalMasterModel.getThrustAreaId());
		//proposalMasterDomain.setOrganisationId(proposalMasterModel.getOrganisationId());
		//proposalMasterDomain.setContactPerson(proposalMasterModel.getContactPerson());
		proposalMasterDomain.setProposalStatus(proposalMasterModel.getProposalStatus());
		//proposalMasterDomain.setUploadFile(proposalMasterModel.getUploadFile());
		
		//TODO : check the value of isvalid and then set
		proposalMasterDomain.setNumIsValid(1);

	
		/*if(proposalMasterModel.isValid()){
			proposalMasterDomain.setNumIsValid(1);
		}else{
			proposalMasterDomain.setNumIsValid(0);
		}*/
		Application application = applicationDao.getApplicationById(proposalMasterModel.getApplicationId());
		//application.setProposalTitle(proposalMasterModel.getProposalTitle());
		proposalMasterDomain.setApplication(application);
		proposalMasterDomain.setRemarks(proposalMasterModel.getRemarks());
		proposalMasterDomain.setStrProposalRefNo(proposalMasterModel.getProposalRefNo());
		//proposalMasterDomain.setAdministrationNo(proposalMasterModel.getAdministrationNo());
		return proposalMasterDomain;
	}

	@Override
	public void deleteProposal(ProposalMasterModel proposalMasterModel) {
		int count= proposalMasterModel.getNumIds().length;
		//System.out.println("count"+count);
		long[] proposal_ids= new long[count];
		proposal_ids= proposalMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ProposalMasterDomain proposalMasterDomain = new ProposalMasterDomain();
			proposalMasterDomain.setNumId(proposal_ids[i]);
			
			proposalMasterDomain.setDtTrDate(new Date());

			proposalMasterDao.deleteProposal(proposalMasterDomain);
			
			
		}	
		
	}

	@Override
	public List< ProposalMasterModel> viewGroup(int OrganisationId) {
		List<GroupMasterDomain> viewGroup = proposalMasterDao.showGroup(OrganisationId);
		
		 List< ProposalMasterModel> proposalMasterModel =  new ArrayList<ProposalMasterModel>();
		for(int i=0; i < viewGroup.size(); i++){
			GroupMasterDomain groupDomain1 = viewGroup.get(i);
			ProposalMasterModel proposalMasterModel1 = new ProposalMasterModel();
	
		proposalMasterModel1.setGroupId(groupDomain1.getNumId());
		proposalMasterModel1.setGroupName(groupDomain1.getGroupName());
		proposalMasterModel.add(proposalMasterModel1);
		}
		return  proposalMasterModel;
	}

	
	////contact person on change client
	@Override
	public List<ProposalMasterModel> viewContact(int ClientId) {
		List<ContactPersonMasterDomain> viewContact = proposalMasterDao.showContact(ClientId);
		 List< ProposalMasterModel> proposalMasterModel =  new ArrayList<ProposalMasterModel>();
		 for(int i=0; i < viewContact.size(); i++){
			 ContactPersonMasterDomain conactDomain = viewContact.get(i);
			 ProposalMasterModel proposalMasterModel1 = new ProposalMasterModel();
			 proposalMasterModel1.setContactPersonId(conactDomain.getNumId());
			 proposalMasterModel1.setContactPerson(conactDomain.getStrContactPersonName());
			 proposalMasterModel.add(proposalMasterModel1);
		 }
		return proposalMasterModel;
	}

	
	
	public ProposalMasterModel getProposalMasterByApplicationId(
			long applicationId) {
		List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByApplicationId(applicationId);
		if(list!=null && list.size()>0){
			return convertProposalMasterDomainToModel(list.get(0));
		}else
			return null;
	}

	@Override
	public long createEmptyProposalDetails(Application application){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();

		ProposalMasterDomain domain = new ProposalMasterDomain(application);
		domain.setNumTrUserId(userInfo.getEmployeeId());
		domain.setDtTrDate(new Date());
		domain.setNumIsValid(1);
		domain.setStatus("SAD");

		domain = proposalMasterDao.saveUpdateProposalMaster(domain);
		return domain.getNumId();
	}

	
	public boolean submitProposal(long applicationId) {
		List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByApplicationId(applicationId);
		if(list!=null && list.size()>0){
		ProposalMasterDomain domain =list.get(0);
		
		
		// To Generate Proposal Code Example :- NP23T001
		String proposalCode = "";
		if(domain.getStrProposalRefNo()==null || domain.getStrProposalRefNo().length()==0){
			String centerCode=domain.getApplication().getGroupMaster().getOrganisationMasterDomain().getStrCode();
			String GroupCode=domain.getApplication().getGroupMaster().getStrCode();

			DateFormat df = new SimpleDateFormat("yy"); // Just the year,with 2
														// digits
			/*String strYear = df.format(Calendar.getInstance().getTime());*/
			
			String strYear = df.format(domain.getDateOfSubmission()); /*Modified by devesh on 14-02-24 to get year code from date of submission*/
			
			System.out.println(strYear);

			DateFormat df1 = new SimpleDateFormat("yyyy"); // Just the fullyear
			String formattedDate1 = df1
					.format(Calendar.getInstance().getTime());
			System.out.println(formattedDate1);

			int fullYear = Integer.parseInt(formattedDate1);

			String strProjectCount = proposalMasterDao
					.getYearlyProposal(fullYear);
			String projectSeq = "";
			/*System.out.println(strProjectCount);
			if (strProjectCount.length() == 1) {
				projectSeq = "00" + (strProjectCount);
			} else if (strProjectCount.length() == 2) {
				projectSeq = "0" + (strProjectCount);
			} else {
				projectSeq = (strProjectCount);
			}
			
			proposalCode = centerCode + GroupCode + strYear + "T" + projectSeq;
			System.out.print("Generate a number:" + proposalCode);
			*/
			
			//Loop added by devesh on 15-02-24 to check duplicate proposal code and increment project seq if duplicate exist
			do {
		        // Increment strProjectCount
		        int incrementedCount = Integer.parseInt(strProjectCount) + 1;
		        strProjectCount = String.valueOf(incrementedCount);

		        // Format projectSeq based on strProjectCount length
		        if (strProjectCount.length() == 1) {
		            projectSeq = "00" + strProjectCount;
		        } else if (strProjectCount.length() == 2) {
		            projectSeq = "0" + strProjectCount;
		        } else {
		            projectSeq = strProjectCount;
		        }

		        // Generate proposalCode
		        proposalCode = centerCode + GroupCode + strYear + "T" + projectSeq;

		    } while (isProposalCodeExists(proposalCode));
			//End of loop
			
			domain.setStrProposalRefNo(proposalCode);

		}
		// End Proposal Code Generation
		
/*
		String centerCode=domain.getApplication().getGroupMaster().getOrganisationMasterDomain().getStrCode();
		String GroupCode=domain.getApplication().getGroupMaster().getStrCode();
		Date projectStartDt=domain.getDateOfSubmission();
		Calendar calendar= GregorianCalendar.getInstance();
		calendar.setTime(projectStartDt);
		int fullYear = (calendar.get(Calendar.YEAR));
		int year = (calendar.get(Calendar.YEAR))% 100;				
		String strProjectCount= proposalMasterDao.getYearlyProject(fullYear);
		String projectSeq="";
		String strYear=Integer.toString(year);
		String projectCategory=domain.getApplication().getCategoryMaster().getStrCode();
		String projectType=domain.getApplication().getProjectTypeMaster().getStrCode();
		if(strProjectCount.length() == 0){
			projectSeq="001";
		}
		else if(strProjectCount.length() == 1){
			projectSeq="00"+(strProjectCount);
		}
		else if(strProjectCount.length()==2){
			projectSeq="0"+(strProjectCount);
		}
		else{
			projectSeq=(strProjectCount);
		}
		String proposalCode=centerCode+GroupCode+strYear+projectCategory+projectType+projectSeq+"T"; 
		String status=domain.getStatus();
		if(status != null && status.equals("REV")){
			List<Object[]> object = proposalMasterDao.getProposalMasterHistoryById(applicationId);
			if(null != object && object.size()>0){
				Object[] proposalHistoryDomain = object.get(0);
				BigInteger categoryId=new BigInteger(""+proposalHistoryDomain[0]);
				BigInteger projectTypeId=new BigInteger(""+proposalHistoryDomain[1]);
				long catId=domain.getApplication().getCategoryMaster().getNumId();
				long prjTypeId=domain.getApplication().getProjectTypeMaster().getNumId();
					if(categoryId.intValue()!=catId || projectTypeId.intValue() != prjTypeId){
						domain.setStrProposalRefNo(proposalCode);
					}
					else{
						domain.setStrProposalRefNo(proposalCode);
					}
			}
			
		}else{
			domain.setStrProposalRefNo(proposalCode);
		}
*/
		domain.setStatus("SUB");
		domain=proposalMasterDao.saveUpdateProposalMaster(domain);
		if(domain.getStatus()=="SUB")
			return true;
		}
		return false;
	}
	
	//Function added by devesh on 15-02-24 to check duplicate proposal code and return true if duplicate exist
	@Override
	public boolean isProposalCodeExists(String proposalCode){
		List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByProposalCode(proposalCode);
	
		 if(list.isEmpty()){
			return false; 
		 }

		return true;
	}
	//End of function

	@Override
	public List<Object[]> getproposalHistory(long applicationId) {
		// TODO Auto-generated method stub
		return proposalMasterDao.getProposalHistoryById(applicationId);
	}
	
	//added function to get proposal history details by devesh on 28/7/23
	@Override
	public List<Object[]> getproposalHistorydetails(long applicationId) {
		// TODO Auto-generated method stub
		return proposalMasterDao.getProposalHistorydetailsById(applicationId);
	}
	//End of get proposal hisory details by id

	@Override
	public ProposalMasterModel getVersionDetails(long numId) {
		List<Object[]> list=proposalMasterDao.getVersionDetails(numId);
		ProposalMasterModel model=new ProposalMasterModel();
		if(list.size()>0){
			//System.out.println("in list");
			Object[] obj=list.get(0);
			model.setProposalTitle(""+obj[0]);
			//System.out.println(""+obj[0]);
			model.setSummary(""+obj[1]);
			model.setProjectTypeName(""+obj[2]);
			model.setProjectCategory(""+obj[3]);
			model.setGroupName(""+obj[4]);
			if(obj[5]!=null){
				Date dt=(Date)obj[5];
			model.setDateOfSubmission(DateUtils.dateToString(dt));
			}
			model.setDuration((Integer)obj[6]);
			model.setTotalProposalCost((Double)obj[7]);
			model.setProposalCost((Integer)obj[8]);
			model.setSubmittedBy(""+obj[9]);
			model.setObjectives(""+obj[10]);
			model.setBackground(""+obj[11]);
			model.setProposalStatus(""+obj[12]);
			//Added by devesh on 26-09-23 to set proposal id in version details
			BigInteger b=new BigInteger(""+obj[13]);
			model.setNumId(b.longValue());
			//End of setting proposal id
			return model;
			
		}
		
		else{
			return  null;
		}
	}

	/*@Override
	public List<ProposalMasterModel> getProposalDetailByGruopId(long groupId) {
		List<ProposalMasterDomain> proposalMasterDomain = applicationDao.getProposalDetailByGruopId(long groupId);
		List<ProposalMasterModel> proposalMasterModelList = convertProposalMasterDomainToModelList(proposalMasterDomain);
		return proposalMasterModelList;
	}*/
	
	@Override
	public List<ProposalMasterModel> getActiveProposals(String startRange, String endRange){
		List<ProposalMasterDomain> dataList = proposalMasterDao.getActiveProposals(startRange, endRange);
		if(null != dataList){
			//return convertDomainToModelList(dataList);
			//Added by devesh on 29/08/23 to change group name if proposal groupid is not zero
			List<ProposalMasterModel> newProposalsList = convertDomainToModelList(dataList);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
			int selectedGroup = selectedRole.getNumGroupId();
			int roleId = selectedRole.getNumRoleId();
			for (ProposalMasterModel proposal : newProposalsList) {
		        // Assuming "groupId" is the field name in ProposalMasterModel
		        //System.out.println("groupId: " + proposal.getGroupId());
		        if (proposal.getGroupId() != 0) {
		            // Change the groupName value based on the condition
		        	String groupIdString = Long.toString(proposal.getGroupId());
		            proposal.setGroupName(groupMasterDao.getGroupNames(groupIdString));
		        }
		    }
			/*for (ProposalMasterModel proposal : newProposalsList) {
		        System.out.println("Group ID: " + proposal.getGroupId() + ", Group Name: " + proposal.getGroupName());
		    }*/
			/*if(selectedGroup != 0){*/ //Commented by devesh on 24-05-24 to use role id in condition
			if(roleId !=6 && roleId !=9 && roleId != 7 && roleId != 8){
				// Filter out rows with the same groupName as selectedGroup
			    List<ProposalMasterModel> filteredProposals = new ArrayList<>();
			    for (ProposalMasterModel proposal : newProposalsList) {
			        if (proposal.getGroupName().equals(groupMasterDao.getGroupNames(Long.toString(selectedGroup)))) {
			            filteredProposals.add(proposal);
			        }
			    }
			    //int roleId = selectedRole.getNumRoleId();
			    // added the role for hod and PI to get the proposalsubmitted data by varun
			    /*if(roleId==15 || roleId==4)
			    {
			    	List<ProposalMasterDomain> newProposalsList12 = new ArrayList<>();
			    	List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIdsforTeam(roleId);
			    	List<Integer> proposalIdlist = new ArrayList<>();
	    		    for (Integer projectId : numProjectIds) {
	    		    		int proposalId = (int) applicationService.getProposalIdByProjectId(projectId);
	    		    		int proposalId = (int) applicationService.getProposalIdByProjectIdAndDate(projectId, startRange, endRange); Modified by devesh on 25-01-24 to pass date range in query
	    		    		proposalIdlist.add(proposalId);
	    		    	}
	    		    for (int proposalId : proposalIdlist) {
	    		    	List<ProposalMasterDomain> proposalmodel = proposalDocumentMasterService.getApplicationIdbyProposalId(proposalId);
	    		    	if(proposalmodel.size()>0){
	    		    		newProposalsList12.add(proposalmodel.get(0));
	    		    	}
    		    	}
	    		    List<ProposalMasterModel> newProposalsList1 = convertDomainToModelList(newProposalsList12);
	    		    return newProposalsList1;
	    		    
			    }*/
			    // Print the filtered list for testing
			    /*for (ProposalMasterModel proposal : filteredProposals) {
			        System.out.println("Group ID: " + proposal.getGroupId() + ", Group Name: " + proposal.getGroupName());
			    }*/
			    
			    return filteredProposals;
			}
			else{			
				/*for (ProposalMasterModel proposal : newProposalsList) {
			        System.out.println("Group ID: " + proposal.getGroupId() + ", Group Name: " + proposal.getGroupName());
			    }*/
				return newProposalsList;
			}
			//End of Condition for groupName
		}else{
			return null;
		}
	}
	public List<ProposalMasterModel> getActiveProposalsByGroup(String startRange, String endRange,String encGroupId){
		/*String strEncGroupId=encGroupId.substring( 1, encGroupId.length() - 1 );*/
		String strGroupId = encryptionService.dcrypt(encGroupId);
		long groupId = Long.parseLong(strGroupId);
		List<ProposalMasterDomain> dataList = proposalMasterDao.getActiveProposalsByGroup(startRange, endRange,groupId);
		if(null != dataList){
			return convertDomainToModelList(dataList);
		}else{
			return null;
		}
	}

public List<ProposalMasterModel> convertDomainToModelList(List<ProposalMasterDomain> dataList){
	
	
	List<ProposalMasterModel> list = new ArrayList<ProposalMasterModel>();
	for(int i=0;i<dataList.size();i++){
		ProposalMasterDomain proposalMasterDomain = dataList.get(i);
		ProposalMasterModel proposalMasterModel = new ProposalMasterModel();
		
	Application application =proposalMasterDomain.getApplication();
	proposalMasterModel.setStrClientName(application.getClientMaster().getClientName());
	proposalMasterModel.setProposalTitle(application.getProposalTitle());	
	proposalMasterModel.setProjectTypeName(application.getProjectTypeMaster().getProjectTypeName());
	proposalMasterModel.setProjectCategory(application.getCategoryMaster().getCategoryName());
	proposalMasterModel.setOrganisationName(application.getGroupMaster().getOrganisationMasterDomain().getOrganisationName());
	proposalMasterModel.setGroupName(application.getGroupMaster().getGroupName());
	//Added by devesh on 29/08/23 to set groupname for proposal master model
	proposalMasterModel.setGroupId(proposalMasterDomain.getGroupId());
	
	proposalMasterModel.setProposalAbstract(proposalMasterDomain.getProposalAbstract());
	proposalMasterModel.setProposalCost(proposalMasterDomain.getProposalCost());
	// display the total proposal cost added the below line and set the total cost by varun 
	proposalMasterModel.setTotalProposalCost(proposalMasterDomain.getApplication().getTotalProposalCost());
	//System.out.println("Bhavesh total proposal cost" + proposalMasterDomain.getApplication().getTotalProposalCost());
	proposalMasterModel.setDuration(proposalMasterDomain.getDuration());
	proposalMasterModel.setSubmittedBy(proposalMasterDomain.getSubmittedBy());
	proposalMasterModel.setSubmittedTo(application.getClientMaster().getClientName());
	proposalMasterModel.setObjectives(proposalMasterDomain.getObjectives());
	proposalMasterModel.setBackground(proposalMasterDomain.getBackground());
	proposalMasterModel.setSummary(proposalMasterDomain.getSummary());
	try{
	double roundOfProposalTotalCost = CurrencyUtils.round(((double)application.getTotalProposalCost()/100000),2);
	String strProposalTotalAmount = CurrencyUtils.convertToINR(roundOfProposalTotalCost);
	proposalMasterModel.setStrProposalTotalCost(strProposalTotalAmount + "Lakhs");	
	}
	catch(Exception e){
		e.printStackTrace();
	}
	try{
		double roundOfProposalCost = CurrencyUtils.round(((double)proposalMasterDomain.getProposalCost()/100000),2);
		String strProposalAmount = CurrencyUtils.convertToINR(roundOfProposalCost);
		proposalMasterModel.setStrProposalCost(strProposalAmount + "Lakhs");	
		proposalMasterModel.setNumProposalAmountLakhs(roundOfProposalCost);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	proposalMasterModel.setGroupId(proposalMasterDomain.getGroupId());
	proposalMasterModel.setOrganisationId(proposalMasterDomain.getOrganisationId());
	proposalMasterModel.setProposalStatus(proposalMasterDomain.getProposalStatus());
	if(application.getNumCorporateApproval() ==1){
		proposalMasterModel.setCorporateApproval(true);
	}else{
		proposalMasterModel.setCorporateApproval(false);
	}
	if(application.getDateOfSub()!=null){
		proposalMasterModel.setDateOfSub(DateUtils.dateToString(proposalMasterDomain.getApplication().getDateOfSub()));
	}
	if(proposalMasterDomain.getApplication().getClearanceReceiveDate()!=null){
		proposalMasterModel.setClearanceReceiveDate(DateUtils.dateToString(proposalMasterDomain.getApplication().getClearanceReceiveDate()));
		
	}
	if(application.isConvertedToProject()==true){
		
		proposalMasterModel.setReceivedProjectStatus("Yes");
		proposalMasterModel.setNumCountOfConverted(1);
		List<ProjectMasterDomain> projects = projectMasterDao.getProjectDetailsByApplicationId(application.getNumId());
		if(null != projects && projects.size() > 0){
			ProjectMasterDomain project = projects.get(0);
			
			try{
				double roundOfPropojectCost = CurrencyUtils.round((project.getProjectCost()/100000),2);
				String strProjectAmount = CurrencyUtils.convertToINR(roundOfPropojectCost);
				proposalMasterModel.setStrProjectCost(strProjectAmount + "Lakhs");	
				proposalMasterModel.setTotalProjectCost(roundOfPropojectCost);
				/*---------- Get Some Project Details for GC Dashboard Proposal Tile [08-02-2024] ----------*/
				proposalMasterModel.setStrProjectName(project.getStrProjectName());
				proposalMasterModel.setStrProjectWorkOrderDate(DateUtils.dateToString(project.getDtWorkOrderDate()));
				String encryptedProjectId = encryptionService.encrypt(""+project.getNumId());
				proposalMasterModel.setEncProjectId(encryptedProjectId);
				proposalMasterModel.setStrProjectRefNo(project.getStrProjectRefNo());
				/*---------- End of Get Some Project Details for GC Dashboard Proposal Tile [08-02-2024] ----------*/
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	else{
		proposalMasterModel.setReceivedProjectStatus("No");
	}
	
	if(proposalMasterDomain.getDateOfSubmission()!=null)
	proposalMasterModel.setDateOfSubmission(DateUtils.dateToString(proposalMasterDomain.getDateOfSubmission()));
	
	if(proposalMasterDomain.getNumId() != 0){
		String encryptedId = encryptionService.encrypt(""+proposalMasterDomain.getNumId());
		proposalMasterModel.setEncStrId(encryptedId);
	}
	proposalMasterModel.setNumId(proposalMasterDomain.getNumId());
	if(proposalMasterDomain.getNumIsValid()==1){
		proposalMasterModel.setValid(true);
	}else{
		proposalMasterModel.setValid(false);
	}
	
		proposalMasterModel.setApplicationId(application.getNumId());
		proposalMasterModel.setEncApplicationId(encryptionService.encrypt(""+application.getNumId()));
		proposalMasterModel.setConvertedToProject(application.isConvertedToProject());
	
	
	proposalMasterModel.setStatus(proposalMasterDomain.getStatus());
	proposalMasterModel.setRemarks(proposalMasterDomain.getRemarks());
	proposalMasterModel.setProposalRefNo(proposalMasterDomain.getStrProposalRefNo());
	if(proposalMasterDomain.getApplication().getEndUserMaster()!=null)
	proposalMasterModel.setEndUser(proposalMasterDomain.getApplication().getEndUserMaster().getClientName());

	list.add(proposalMasterModel);
	}
	return list;	
	}

	/*------------- Get the Proposal Data where the project start date and end date are provided [08-02-2024] ------------*/
	public List<ProposalMasterModel> getProposalDataOfCurrentYearProjects(String startRange, String endRange){
		List<ProposalMasterDomain> dataList = proposalMasterDao.getProposalDataOfCurrentYearProjects(startRange, endRange);
		if(null != dataList){
			List<ProposalMasterModel> newProposalsList = convertDomainToModelList(dataList);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
			int selectedGroup = selectedRole.getNumGroupId();
			int roleId = selectedRole.getNumRoleId();
			for (ProposalMasterModel proposal : newProposalsList) {
		        if (proposal.getGroupId() != 0) {
		        	String groupIdString = Long.toString(proposal.getGroupId());
		            proposal.setGroupName(groupMasterDao.getGroupNames(groupIdString));
		        }
		    }

			/*if(selectedGroup != 0){*/ //Commented by devesh on 24-05-24 to use role id in condition
			if(roleId !=6 && roleId !=9 && roleId != 7 && roleId != 8){ 
			    List<ProposalMasterModel> filteredProposals = new ArrayList<>();
			    for (ProposalMasterModel proposal : newProposalsList) {
			        if (proposal.getGroupName().equals(groupMasterDao.getGroupNames(Long.toString(selectedGroup)))) {
			            filteredProposals.add(proposal);
			        }
			    }
			    //int roleId = selectedRole.getNumRoleId();
			    if(roleId==15 || roleId==4)
			    {
			    	List<ProposalMasterDomain> newProposalsList12 = new ArrayList<>();
			    	List<Integer> numProjectIds = employeeRoleMasterDao.getProjectIdsforTeam(roleId);
			    	List<Integer> proposalIdlist = new ArrayList<>();
	    		    for (Integer projectId : numProjectIds) {
	    		    	int proposalId = (int) proposalMasterDao.getProposalIdByProjectIdAndWorkOrderDate(projectId, startRange, endRange); /*Modified by devesh on 25-01-24 to pass date range in query*/
	    		    	proposalIdlist.add(proposalId);
	    		    }
	    		    for (int proposalId : proposalIdlist) {
	    		    	List<ProposalMasterDomain> proposalmodel = proposalDocumentMasterService.getApplicationIdbyProposalId(proposalId);
	    		    	if(proposalmodel.size()>0){
	    		    		newProposalsList12.add(proposalmodel.get(0));
	    		    	}
    		    	}
	    		    List<ProposalMasterModel> newProposalsList1 = convertDomainToModelList(newProposalsList12);
	    		    return newProposalsList1;    
			    }
			    return filteredProposals;
			}else{			
				return newProposalsList;
			}
		}else{
			return null;
		}
	}
	/*------- End of Get the Proposal Data where the project start date and end date are provided [08-02-2024] -----------*/
}
