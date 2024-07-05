package in.pms.master.service;

import in.pms.global.domain.TransactionMasterDomain;
import in.pms.global.model.TransactionMasterModel;
import in.pms.global.model.WorkflowModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.WorkflowService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.DocumentTypeMasterDao;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.EndUserMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProjectPaymentReceivedDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.domain.DocumentTypeMasterDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.domain.TempEmployeeRoleMasterDomain;
import in.pms.master.domain.TempProjectMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectClosureModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.transaction.dao.ApplicationDao;
import in.pms.transaction.dao.EmployeeApprovedJobMappingDao;
import in.pms.transaction.domain.Application;
import in.pms.transaction.domain.EmployeeApprovedJobMapping;
import in.pms.transaction.model.CollaborativeOrgDetailsModel;
import in.pms.transaction.service.ApplicationService;
import in.pms.transaction.service.CollaborativeOrgDetailsService;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.icu.impl.UResource.Array;
@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	ProposalMasterDao proposalMasterDao;
	
	@Autowired
	ApplicationDao applicationDao;

	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	EndUserMasterDao endUserMasterDao;
	
	@Autowired
	CollaborativeOrgDetailsService collaborativeOrgDetailsService;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	@Autowired
	EmployeeApprovedJobMappingDao employeeApprovedJobMappingDao;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ProjectPaymentReceivedDao projectPaymentReceivedDao;
	
	@Autowired
	DocumentTypeMasterDao documentTypeMasterDao;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	
	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Override
	public List<ProjectMasterModel> getAllActiveProjectMasterData(){
		return convertProjectMasterDomainToModelList(projectMasterDao.getAllActiveProjectMasterData());			
	}
	
	public List<ProjectMasterModel> convertProjectMasterDomainToModelList(List<ProjectMasterDomain> projectDataList){
		List<ProjectMasterModel> list = new ArrayList<ProjectMasterModel>();
			for(int i=0;i<projectDataList.size();i++){
				ProjectMasterDomain projectMasterDomain = projectDataList.get(i);
				ProjectMasterModel projectMasterModel = new ProjectMasterModel();
				projectMasterModel.setStrProjectName(projectMasterDomain.getStrProjectName());
				projectMasterModel.setNumId(projectMasterDomain.getNumId());
				if(projectMasterDomain.getStrProjectRefNo() != null){
				projectMasterModel.setProjectRefNo(projectMasterDomain.getStrProjectRefNo());
				}
				projectMasterModel.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				if(projectMasterDomain.getNumIsValid()==1){
					projectMasterModel.setValid(true);
				}else{
					projectMasterModel.setValid(false);
				}
				if(null != projectMasterDomain.getProjectClosureDate()){
						
					}
				
				list.add(projectMasterModel);
	}
		return list;
	}
	
	public ProjectMasterForm getProjectDetailByProjectId(long projectId){
		return convertProjectMasterDomainToProjectMasterForm(projectMasterDao.getProjectMasterDataById(projectId));			
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_MST')")
	public List<ProjectMasterForm> getAllProjectDetailsData(){
		return convertBudgetMasterDomainToModelList(projectMasterDao.getAllProjectMasterData());			
	}
	
	// below function added by varun to getallviewrpoject data 
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_MST')")
	public List<ProjectMasterForm> getviewAllProjectDetailsData(String groupIds,String startRange, String endRange){
		return convertBudgetMasterDomainToModelList(projectMasterDao.getviewAllProjectMasterData(groupIds,startRange,endRange));			
	}

	@Override
	public List<ProjectMasterForm> convertBudgetMasterDomainToModelList(List<ProjectMasterDomain> projectMasterList){
		List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<projectMasterList.size();i++){
				ProjectMasterDomain projectMasterDomain = projectMasterList.get(i);
				ProjectMasterForm proposalMasterForm = new ProjectMasterForm();				
				//ThrustAreaMasterDomain thrustareadomain = new ThrustAreaMasterDomain();
				if(null != projectMasterDomain.getDtWorkOrderDate()){
				    String workOrderDate= DateUtils.dateToString(projectMasterDomain.getDtWorkOrderDate()); 
				    proposalMasterForm.setWorkOrderDate(workOrderDate);
				}
				if(null != projectMasterDomain.getDtProjectStartDate()){
				    String startDate= DateUtils.dateToString(projectMasterDomain.getDtProjectStartDate()); 
					proposalMasterForm.setStartDate(startDate);
				}
				if(null != projectMasterDomain.getDtProjectEndDate()){
				    String endDate= DateUtils.dateToString(projectMasterDomain.getDtProjectEndDate());
				    proposalMasterForm.setEndDate(endDate);
				}
				if(null != projectMasterDomain.getDtMOU()){
				    String MOUDate= DateUtils.dateToString(projectMasterDomain.getDtMOU());
					proposalMasterForm.setMouDate(MOUDate);
				}
				
				if(null != projectMasterDomain.getStrProjectRefNo()){
					proposalMasterForm.setProjectRefrenceNo(projectMasterDomain.getStrProjectRefNo());
				}
				
				if(null != projectMasterDomain.getAdministrationNo()){
					proposalMasterForm.setAdministrationNo(projectMasterDomain.getAdministrationNo());
				}
				
				if(null != projectMasterDomain.getStrdescription()){
					proposalMasterForm.setDescription(projectMasterDomain.getStrdescription());
				}
				if(null != projectMasterDomain.getStrGST()){
					proposalMasterForm.setStrGST(projectMasterDomain.getStrGST());
				}
				if(null != projectMasterDomain.getStrTAN()){
					proposalMasterForm.setStrTAN(projectMasterDomain.getStrTAN());
				}
				
				proposalMasterForm.setNumId(projectMasterDomain.getNumId());	
				proposalMasterForm.setEncNumId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				proposalMasterForm.setProjectCost(projectMasterDomain.getProjectCost());
				if(projectMasterDomain.getDtProjectStartDate()!=null&&projectMasterDomain.getDtProjectEndDate()!=null){
					
				proposalMasterForm.setProjectDuration(DateUtils.getDurationInMonths(projectMasterDomain.getDtProjectStartDate(), projectMasterDomain.getDtProjectEndDate()));

				}
				//proposalMasterForm.setProjectDuration(projectMasterDomain.getProjectDuration());
				proposalMasterForm.setProjectType(projectMasterDomain.getProjectType());
				proposalMasterForm.setStrBriefDescription(projectMasterDomain.getStrBriefDescription());
				proposalMasterForm.setStrProjectAim(projectMasterDomain.getStrProjectAim());
				proposalMasterForm.setStrProjectName(projectMasterDomain.getStrProjectName());
				proposalMasterForm.setStrProjectObjective(projectMasterDomain.getStrProjectObjective());
				proposalMasterForm.setStrProjectScope(projectMasterDomain.getStrProjectScope());
				proposalMasterForm.setStrProjectStatus(projectMasterDomain.getStrProjectStatus());
				proposalMasterForm.setStrProjectRemarks(projectMasterDomain.getStrProjectRemarks());
				Application application = projectMasterDomain.getApplication();
				
				proposalMasterForm.setClientName(application.getClientMaster().getClientName());
				proposalMasterForm.setTotalOutlay(application.getTotalProposalCost());
				proposalMasterForm.setProjectCategory(application.getCategoryMaster().getCategoryName());
				proposalMasterForm.setProjectType(application.getProjectTypeMaster().getProjectTypeName());
				proposalMasterForm.setStrFundedScheme(projectMasterDomain.getStrFundedScheme());
				proposalMasterForm.setStrGST(projectMasterDomain.getStrGST());
				proposalMasterForm.setStrTAN(projectMasterDomain.getStrTAN());
				proposalMasterForm.setStrValidatedRemarks(projectMasterDomain.getValidatedRemarks());
				proposalMasterForm.setStrValidateStatus(projectMasterDomain.getValidateStatus());
				proposalMasterForm.setClosureStatus(projectMasterDomain.getClosureStatus());
				
				EmployeeRoleMasterDomain employeeRoleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterByProjectId(projectMasterDomain.getNumId());
				if(employeeRoleMasterDomain!=null) {
					EmployeeMasterDomain employeeMasterDomain = employeeMasterDao.getEmployeeMasterById(employeeRoleMasterDomain.getNumEmpId());
					proposalMasterForm.setStrPLName(employeeMasterDomain.getEmployeeName());
				};
				
				String strThrustArea = "";
				Application applicationThrustArea =applicationDao.getThrustAreaByApplication(application.getNumId());
				if(null != applicationThrustArea){
					Set<ThrustAreaMasterDomain> thrustAreas = applicationThrustArea.getThrustAreas();
					if(null != thrustAreas){
						//System.out.println("app:"+application.getNumId());
						Iterator<ThrustAreaMasterDomain> itr = thrustAreas.iterator();
						while (itr.hasNext()) {
							ThrustAreaMasterDomain thrustArea = itr.next();
							if(StringUtils.isBlank(strThrustArea)){
								strThrustArea = thrustArea.getStrThrustAreaName();
							}else{
								strThrustArea = strThrustArea + ", "+thrustArea.getStrThrustAreaName();
							}				
						}			
					}
				}
				proposalMasterForm.setThrustAreas(strThrustArea);
			
				if(null!=projectMasterDomain.getApplication() && application.isCollaborative()){
					JSONArray calloab =collaborativeOrgDetailsService.getAllActiveCollaborativeOrgDetailsDomain(application.getNumId());

					List <CollaborativeOrgDetailsModel> collaborativeOrgDetailsModels = new ArrayList<>();
					for (int j = 0; j < calloab.length(); j++) {
					    JSONObject jsonObject = calloab.getJSONObject(j);
					    
					    // Get values from JSONObject
					    int numId = jsonObject.getInt("numId");
					    String encNumId = jsonObject.getString("encNumId");
					    String organisationName = jsonObject.getString("organisationName");
					    String organisationAddress = jsonObject.getString("organisationAddress");
					    String contactNumber = jsonObject.getString("contactNumber");
					    String email = jsonObject.getString("email");
					    String website = jsonObject.getString("website");
					    
					    CollaborativeOrgDetailsModel collaborativeOrgDetailsModel = new CollaborativeOrgDetailsModel();
					    collaborativeOrgDetailsModel.setEmail(email);
					    collaborativeOrgDetailsModel.setOrganisationName(organisationName);
					    collaborativeOrgDetailsModel.setOrganisationAddress(organisationAddress);
					    collaborativeOrgDetailsModel.setContactNumber(contactNumber);
					    collaborativeOrgDetailsModel.setWebsite(website);
					    collaborativeOrgDetailsModels.add(collaborativeOrgDetailsModel);
					   
					}
					proposalMasterForm.setCollaborativeOrgDetailsModels(collaborativeOrgDetailsModels);
				}
				
				if(projectMasterDomain.getNumIsValid()==1){
					proposalMasterForm.setValid(true);
				}else{
					proposalMasterForm.setValid(false);
				}
				/*----------------------- set project reference in the field [05-12-2023] --------------------*/
				proposalMasterForm.setProjectRefrenceNo(projectMasterDomain.getStrProjectRefNo());
				/*------------------------ End of set project reference in the field [05-12-2023] ------------------*/
				list.add(proposalMasterForm);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateProjectDetailsData(ProjectMasterForm projectMasterForm){
		String result = "";
		ProjectMasterDomain budgetHeadMasterDomain = projectMasterDao.getProjectMasterByName(projectMasterForm.getStrProjectName());
	
		 if(null == budgetHeadMasterDomain){
			return null; 
		 }else if(projectMasterForm.getNumId() != 0){
			 if(budgetHeadMasterDomain.getNumId() == projectMasterForm.getNumId()){
				 return null; 
			 }else{
				 result = "Project with same name already exist with Id "+budgetHeadMasterDomain.getNumId();
			 }
		 }
		 else{
				if(budgetHeadMasterDomain.getNumIsValid() == 0){
					result = "Project Already Registered with Id "+budgetHeadMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Already Registered with Id "+budgetHeadMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_MST')")
	public ProjectMasterDomain saveProjectDetailsData(ProjectMasterForm projectMasterForm){
		ProjectMasterDomain projectMasterDomain = convertProjecttMasterFormToDomain(projectMasterForm);
		projectMasterDomain = projectMasterDao.mergeProjectMaster(projectMasterDomain);
		if(projectMasterForm.isConvertProposalToProject()){
			// Insert into transaction Master Proposal Converted to Project

			TransactionMasterModel trMasterModel=new TransactionMasterModel();
			trMasterModel.setDtTrDate(new Date());
			trMasterModel.setNumActionId(13);
			trMasterModel.setNumRoleId(3);
			trMasterModel.setNumIsValid(1);
			trMasterModel.setNumWorkflowId(3);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
			trMasterModel.setCustomId((int) projectMasterDomain.getNumId());	
			
			workflowService.insertIntoTransaction(trMasterModel);
		}
		return projectMasterDomain;
	}
	
	@Override
	public ProjectMasterDomain mergeProjectMaster(ProjectMasterDomain projectMasterDomain){
	
		return projectMasterDao.mergeProjectMaster(projectMasterDomain);
	}
	
	public ProjectMasterDomain convertProjecttMasterFormToDomain(ProjectMasterForm projectMasterForm){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectMasterDomain projectMasterDomain = new ProjectMasterDomain();
		if(projectMasterForm.getNumId()!=0){		
			projectMasterDomain =  projectMasterDao.getProjectMasterDataById(projectMasterForm.getNumId());
		}
		//Added by devesh on 05-02-24 to set zero CDAC Outlay and Total Outlay for Internal Projects
			if(projectMasterForm.getCategoryId()==7){
				projectMasterForm.setProjectCost(0);
				projectMasterForm.setTotalOutlay(0);
			}
		//End of setting CDAC Outlay
		if(null != projectMasterForm.getMouDate() && !projectMasterForm.getMouDate().equals("") ){
		try {
			projectMasterDomain.setDtMOU(DateUtils.dateStrToDate(projectMasterForm.getMouDate()));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		}
		if(null != projectMasterForm.getEndDate() && !projectMasterForm.getEndDate().equals("")){
		try {
			projectMasterDomain.setDtProjectEndDate(DateUtils.dateStrToDate(projectMasterForm.getEndDate()));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		}
		if(null != projectMasterForm.getStartDate() && !projectMasterForm.getStartDate().equals("")){
		try {
			projectMasterDomain.setDtProjectStartDate(DateUtils.dateStrToDate(projectMasterForm.getStartDate()));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		}
		if(null != projectMasterForm.getWorkOrderDate() && !projectMasterForm.getWorkOrderDate().equals("")){
			try {
				projectMasterDomain.setDtWorkOrderDate(DateUtils.dateStrToDate(projectMasterForm.getWorkOrderDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		projectMasterDomain.setNumId(projectMasterForm.getNumId());
		try{
		
		if(projectMasterDomain!=null){
			if(projectMasterForm.getProjectCost()!=projectMasterDomain.getProjectCost()){
				Application app=projectMasterDomain.getApplication();
				if(null != app && app.isCollaborative()==false){
					/*app.setTotalProposalCost(projectMasterForm.getTotalOutlay());*/
					//Condition added by devesh on 05-02-24 to not allow zero total cost for external projects
					if(projectMasterForm.getTotalOutlay()!=0 || projectMasterForm.getCategoryId()==7){
						app.setTotalProposalCost(projectMasterForm.getTotalOutlay());
					}
					//End of condition
					applicationDao.mergeApplication(app);
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*projectMasterDomain.setStrProjectRefNo(projectMasterForm.getProjectRefrenceNo());*/
		projectMasterDomain.setAdministrationNo(projectMasterForm.getAdministrationNo());
		projectMasterDomain.setStrdescription(projectMasterForm.getDescription());
		projectMasterDomain.setStrBriefDescription(projectMasterForm.getStrBriefDescription());
		projectMasterDomain.setStrProjectAim(projectMasterForm.getStrProjectAim());
		projectMasterDomain.setStrProjectName(projectMasterForm.getStrProjectName());
		projectMasterDomain.setStrProjectObjective(projectMasterForm.getStrProjectObjective());
		projectMasterDomain.setStrProjectScope(projectMasterForm.getStrProjectScope());
		projectMasterDomain.setStrProjectStatus(projectMasterForm.getStrProjectStatus());
		
		/*projectMasterDomain.setProjectCost(projectMasterForm.getProjectCost());*/
		//Condition added by devesh on 05-02-24 to not allow zero project cost for external projects
		if(projectMasterForm.getProjectCost()!=0 || projectMasterForm.getCategoryId()==7){
			projectMasterDomain.setProjectCost(projectMasterForm.getProjectCost());
		}
		//End of condition
		//projectMasterDomain.setProjectDuration(projectMasterForm.getProjectDuration());
		projectMasterDomain.setProjectType(projectMasterForm.getProjectType());
		projectMasterDomain.setDtTrDate(new Date());
		projectMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectMasterDomain.setStrProjectRemarks(projectMasterForm.getStrProjectRemarks());
		projectMasterDomain.setStrFundedScheme(projectMasterForm.getStrFundedScheme());
		projectMasterDomain.setContactMaster(projectMasterForm.getContactPersonList());
		projectMasterDomain.setStrGST(projectMasterForm.getStrGST());
		projectMasterDomain.setStrTAN(projectMasterForm.getStrTAN());
		projectMasterDomain.setValidatedRemarks(projectMasterForm.getStrValidatedRemarks());
		projectMasterDomain.setValidateStatus(projectMasterForm.getStrValidateStatus());
		//projectMasterForm.get
		projectMasterDomain.setNumIsValid(1);
		
		Application application = new Application();
		if(projectMasterForm.getApplicationId() !=0){
			
			if(projectMasterForm.getNumId()==0){
				//New Project . Change Project Submission Status true in Application
				application = applicationDao.getApplicationById(projectMasterForm.getApplicationId());
				
				application.setConvertedToProject(true);
			}else{
				application.setNumId(projectMasterForm.getApplicationId());	
			}
			
			
			projectMasterDomain.setApplication(application);
		}
		if(null!=projectMasterDomain.getApplication())
		{
			application = applicationDao.getApplicationById(projectMasterDomain.getApplication().getNumId());
			/*application.setTotalProposalCost(projectMasterForm.getTotalOutlay());*/
			//Condition added by devesh on 05-02-24 to not allow zero total cost for external projects
			if(projectMasterForm.getTotalOutlay()!=0 || projectMasterForm.getCategoryId()==7){
				application.setTotalProposalCost(projectMasterForm.getTotalOutlay());
			}
			//End of condition
			projectMasterDomain.setApplication(application);
		}
	/*	if(null != projectMasterForm.getProjectRefrenceNo() && !projectMasterForm.getProjectRefrenceNo().equals("")){
			projectMasterDomain.setStrProjectRefNo(projectMasterForm.getProjectRefrenceNo());
		}*/
		
		return projectMasterDomain;
	}

	public void deleteProjectDetailsData(ProjectMasterForm budgetHeadMasterForm) {
		int count= budgetHeadMasterForm.getNumIds().length;
		long[] budgetMaster_ids= new long[count];
		budgetMaster_ids= budgetHeadMasterForm.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ProjectMasterDomain budgetHeadMasterDomain= new ProjectMasterDomain();
			budgetHeadMasterDomain.setNumId(budgetMaster_ids[i]);
			
			Date date= new Date();
			budgetHeadMasterDomain.setDtTrDate(date);

			projectMasterDao.deleteProjectMaster(budgetHeadMasterDomain);
			
			
		}		
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_MST')")
	public ProjectMasterDomain getProjectMasterDataById(long projectId){
		return projectMasterDao.getProjectMasterDataById(projectId);
	}

	@Override
	public List<ProjectMasterForm> getAllProjectMasterData(String assignedProjects) {
		List<Object[]> list = projectMasterDao.viewProjectDetailsDataById(assignedProjects);		
		return convertProjectDetailsToModelList(list);
	}
	
	public List<ProjectMasterModel> getProjectDataByGroupId(long groupId){		
		List<ProjectMasterModel> projectList =convertProjectMasterDomainToModelList(projectMasterDao.getProjectDataByGroupId(groupId));		
		return projectList;
	}
	
	@Override
	public List<ProjectMasterForm> viewProjectDetailsData(String encGroupId){
		String groupId = "";
		if(null != encGroupId){
			groupId = encryptionService.dcrypt(encGroupId);
		}
		
		return convertProjectDetailsToModelList(projectMasterDao.viewProjectDetailsData(groupId));
	}
	
	@Override
	public List<ProjectMasterForm> viewValidatedProjectDetailsData(String startRange1,String endRange1){
		String strStartDate = startRange1;
		String strEndDate = endRange1;
		Date startRange = null;
		Date endRange = null;
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startRange = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			try {
				startRange = DateUtils.dateStrToDate("01/01/2010");
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		
		if(null != strEndDate && !strEndDate.equals("")){
			try {
				endRange = DateUtils.dateStrToDate(strEndDate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}else{
			endRange = new Date();
		}
		return convertProjectDetailsToModelList(projectMasterDao.viewValidatedProjectDetailsData(startRange,endRange));
	}
	
	public List<ProjectMasterForm> convertProjectDetailsToModelList(List<Object[]>projectDetailsList){
		
		List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<projectDetailsList.size();i++){			
				Object[] projectDetailsDomain = projectDetailsList.get(i);	
				//System.out.println("projecttdomain"+  projectDetailsList.get(i));
				Object obj1=projectDetailsDomain[1];
				Object obj2=projectDetailsDomain[2];
				ProjectMasterForm projectMasterForm = new ProjectMasterForm();
				projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);
				projectMasterForm.setProjectRefrenceNo((String) projectDetailsDomain[10]);	
				if(obj1!=null){
				projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
				}
				if(obj2!=null){
				projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
				projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
				}
				projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));
				String strTotalCost=null;
				strTotalCost=CurrencyUtils.convertToINR(projectDetailsDomain[3]);
				projectMasterForm.setStrTotalCost(strTotalCost);
				double roundOfCost = 0.0;
				if(projectDetailsDomain[4]!=null){
					String strReceiveAmt=null;
					
					double recCost = Double.parseDouble(""+projectDetailsDomain[4]);
					roundOfCost = CurrencyUtils.round((recCost/100000),2);
					strReceiveAmt=CurrencyUtils.convertToINR(roundOfCost);
					projectMasterForm.setStrReceivedAmout(strReceiveAmt);
				
				}
			/*if(projectDetailsDomain[5]!=null){
					String strReceiveAmtWithoutInvoice=null;
					//strReceiveAmtWithoutInvoice=CurrencyUtils.convertToINR(projectDetailsDomain[5]);
					double roundOfReceiveCost = 0.0;
					double cost = Double.parseDouble(""+projectDetailsDomain[5]);
					if(cost >0){
						
						roundOfReceiveCost = CurrencyUtils.round((cost/100000),2);
						double sumOfAmout=  roundOfCost+roundOfReceiveCost;
						strReceiveAmtWithoutInvoice = CurrencyUtils.convertToINR(sumOfAmout);
						projectMasterForm.setStrReceivedAmout(strReceiveAmtWithoutInvoice);
					}
				
				}*/
				projectMasterForm.setStrPLName((String) projectDetailsDomain[5]);
				BigInteger b=new BigInteger(""+projectDetailsDomain[6]);
				projectMasterForm.setProjectId(b.longValue());
				projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
				List<DocumentTypeMasterDomain> docDetails=documentTypeMasterDao.getAllDocumentByShowOnDashboard();
				List<ProjectDocumentMasterModel> projectDocList=projectDocumentMasterService.getProjectDocumentForDashboard(b.longValue());
				if(null != projectDocList && projectDocList.size()>0){
					projectMasterForm.setProjectDocument(projectDocList);
				}
				List<DocumentTypeMasterDomain> docToBeSearchInProposal= new ArrayList<DocumentTypeMasterDomain>(); 
				if(projectDocList.size()!=docDetails.size()){
					
					
					for(int j=0;j<docDetails.size();j++){
						long docTypeId = docDetails.get(j).getNumId();
					ProjectDocumentMasterModel model = projectDocList.stream()
			                .filter(x -> docTypeId== x.getDocumentTypeId())
			                .findAny()
			                .orElse(null);
					
					if(model==null){
						docToBeSearchInProposal.add(docDetails.get(j));
					}
				}}
				if(docToBeSearchInProposal.size()>0)
				{		long proposalId =  applicationService.getProposalIdByProjectId(b.longValue());
							for(int k=0;k<docToBeSearchInProposal.size();k++){
								
								List<ProjectDocumentMasterModel> proposalDocList=proposalDocumentMasterService.getProposalDocumentForDashboard(proposalId,docToBeSearchInProposal.get(k).getNumId());
								if(proposalDocList.size()>0){
								projectMasterForm.getProjectDocument().addAll(proposalDocList);
								}
							}
							
				}
				
				
				List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoiceByPiD(b.longValue());
				if(null != withoutInvoiceList && withoutInvoiceList.size()>0){
					double roundOfProjectCost = 0.0;
					double totalReceAmt=0.0;
					for(int j=0;j<withoutInvoiceList.size();j++){
						Object[] obj = withoutInvoiceList.get(j);
				
						String strReceivedAmount = null;
						
						
						double projectCost = Double.parseDouble(""+obj[0]);
						if(projectCost >0){
							
							roundOfProjectCost = roundOfProjectCost+CurrencyUtils.round((projectCost/100000),2);
							strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
							
							
							if(projectDetailsDomain[4]!=null){
								String strReceiveAmt=null;
								double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);
								totalReceAmt=totalReceAmt+dbReceiveAmt+projectCost;
								double strAmount = CurrencyUtils.round((totalReceAmt/100000),2);
								strReceiveAmt = CurrencyUtils.convertToINR(strAmount);
								projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
							}
							else{
								projectMasterForm.setStrReceivedAmout(strReceivedAmount);	
							}
						}	
						}			
						}else{
							if(projectDetailsDomain[4]!=null){
								String strReceiveAmt=null;
								double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);								
								double strAmount = CurrencyUtils.round((dbReceiveAmt/100000),2);
								strReceiveAmt = CurrencyUtils.convertToINR(strAmount);							
								projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
							}
						}
				projectMasterForm.setBusinessType((String) projectDetailsDomain[7]);
				projectMasterForm.setClientName((String) projectDetailsDomain[8]);
				BigInteger cl=new BigInteger(""+projectDetailsDomain[9]);
				projectMasterForm.setClientId(cl.longValue());
				projectMasterForm.setEncClientId(encryptionService.encrypt(""+cl.longValue()));
				if(projectDetailsDomain[10] != null){
				projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[10]); 
				}
				if(projectDetailsDomain[11] !=null){
					BigInteger el=new BigInteger(""+projectDetailsDomain[11]);
					projectMasterForm.setEndUserId(el.longValue());
				}
				if(projectDetailsDomain.length==13)
				{
					Date date=(Date) projectDetailsDomain[12];
					if(date!=null)
					{
						projectMasterForm.setClosureDate(DateUtils.dateToString(date));
					}
				}
				/*if(projectDetailsDomain.length>13){
					projectMasterForm.setStrValidatedRemarks((String)projectDetailsDomain[14]);
					projectMasterForm.setStrValidateStatus((String)projectDetailsDomain[13]);
					projectMasterForm.setStrGroupName((String)projectDetailsDomain[15]);
				}
*/
				if (projectDetailsDomain.length > 13) {
				    projectMasterForm.setStrValidateStatus(
				    projectDetailsDomain[13] != null ? (String) projectDetailsDomain[13] : "");

				    // Check if the value is null, if true, set an empty string
				    projectMasterForm.setStrValidatedRemarks(
				    projectDetailsDomain[14] != null ? (String) projectDetailsDomain[14] : "");

				    projectMasterForm.setStrGroupName(
				            projectDetailsDomain[15] != null ? (String) projectDetailsDomain[15] : "");
				}
				
				list.add(projectMasterForm);
	}
		return list;
	}

	@Override
	public ProjectMasterModel getProjectMasterModelById(
			long projectId) {
		return convertSingleProjectMasterDomainToModel(projectMasterDao.getProjectMasterDataById(projectId));
	}
	
	
	/*public ProjectMasterModel getProjectvalidationModelById(long projectId) {
		List<ProjectMasterDomain> list = projectMasterDao.getValidationlog(projectId);
		return convertSingleProjectMasterDomainToModel(projectMasterDao.getValidationlog(projectId));
	}*/
	
	/*public List<ProjectMasterModel> getProjectvalidationModelById(long projectId) {
	    List<Objects[]> domainList = projectMasterDao.getValidationlog(projectId);
	    List<ProjectMasterModel> modelList = new ArrayList<>();

	    for (ProjectMasterDomain domain : domainList) {
	        ProjectMasterModel model = convertSingleProjectMasterDomainToModel(domain);
	        modelList.add(model);
	    }

	    return modelList;
	}*/
	
	// write the below method get the validation detail by varun 
	@Override
	public List<ProjectMasterModel> getProjectvalidationModelById(long projectId) {
	    List<Object[]> domainList = projectMasterDao.getValidationlog(projectId);
	    List<ProjectMasterModel> modelList = new ArrayList<>();

	    for (Object[] objArray : domainList) {
	        ProjectMasterModel model = new ProjectMasterModel();
	        if(null != objArray[0]){
	        	model.setStrValidatedRemarks(objArray[0].toString());
			}
			if(null != objArray[1]){
				model.setStrValidateStatus(objArray[1].toString());
			}
			if(null != objArray[2]){
				String[] date = objArray[2].toString().split("\\.");
			    model.setValidateDate(date[0]);
			}

	        modelList.add(model);
	    }

	    return modelList;
	}
	
	public ProjectMasterModel convertSingleProjectMasterDomainToModel(ProjectMasterDomain projectMasterDomain){
			
				ProjectMasterModel projectMasterModel = new ProjectMasterModel();
				projectMasterModel.setStrProjectName(projectMasterDomain.getStrProjectName());
				
				projectMasterModel.setNumId(projectMasterDomain.getNumId());
				projectMasterModel.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				if(projectMasterDomain.getNumIsValid()==1){
					projectMasterModel.setValid(true);
				}else{
					projectMasterModel.setValid(false);
				}
				 if(projectMasterDomain.getStrProjectRefNo() != null){
					 projectMasterModel.setProjectRefNo(projectMasterDomain.getStrProjectRefNo()); 
				 }
				 
				 if(null != projectMasterDomain.getDtProjectEndDate()){
					 projectMasterModel.setEndDate(DateUtils.dateToString(projectMasterDomain.getDtProjectEndDate()));
				 }
				 
				 if(null != projectMasterDomain.getDtProjectStartDate()){
					 projectMasterModel.setStartDate(DateUtils.dateToString(projectMasterDomain.getDtProjectStartDate()));
				 }
				/* if(null != projectMasterDomain.getTempProjectClosureDate()){
					 projectMasterModel.setTempProjectClosureDate(DateUtils.dateToString(projectMasterDomain.getTempProjectClosureDate()));
				 }*/
				 
				 if(null != projectMasterDomain.getValidatedRemarks()){
					 projectMasterModel.setStrValidatedRemarks(projectMasterDomain.getValidatedRemarks());
				 }
				 if(null != projectMasterDomain.getValidateStatus()){
					 projectMasterModel.setStrValidateStatus(projectMasterDomain.getValidateStatus());
				 }
				 if(null != projectMasterDomain.getValidateDate()){
					 projectMasterModel.setValidateDate(DateUtils.dateToString(projectMasterDomain.getValidateDate()));
				 }
		
				 projectMasterModel.setStatus(projectMasterDomain.getStrProjectStatus());
		return projectMasterModel;
	}


	@Override
	public List<ProjectMasterForm> getAllProjectDetails() {
		return convertProjectDetailsToModelList(projectMasterDao.getAllProjectDetails());
	}

	@Override
	public ProjectMasterForm getProjectDetailsByApplicationId(long applicationId){
		List<ProjectMasterDomain> projectsList = projectMasterDao.getProjectDetailsByApplicationId(applicationId);
			if(projectsList.size()>0){
				//ProjectMasterDomain projectMasterDomain=projectsList.get(0);
				//List<ProposalMasterDomain> proposalList = proposalMasterDao.getProposalMasterByApplicationId(applicationId);
				/*if(proposalList.size()>0){
					ProposalMasterDomain proposalMaster=proposalList.get(0);
					String proposalRefrenceNo =proposalMaster.getStrProposalRefNo();
					String domainProjectRefNo=projectMasterDomain.getStrProjectRefNo();
					String projectRefrenceNo = proposalRefrenceNo.replaceFirst("T", "A");
					if(!projectRefrenceNo.equals(domainProjectRefNo)){
					projectMasterDomain.setStrProjectRefNo(projectRefrenceNo);
					projectMasterService.mergeProjectMaster(projectMasterDomain);
					projectsList.add(0, projectMasterDomain);
					}
					
				}*/
				
			
			List<ProjectMasterForm> formList = convertBudgetMasterDomainToModelList(projectsList);
			if(formList.size()>0){
				return formList.get(0);						
			}else{
			return  null;
			}
		}else{
		return null;
		}
	}
	
	@Override
	public ProjectMasterForm getProjectDetailsFromProposalByApplicationId(long applicationId){
		List<ProposalMasterDomain> proposalList = proposalMasterDao.getProposalMasterByApplicationId(applicationId);
		if(proposalList.size()>0){
			return convertProposalDetailsToProjectMasterForm(proposalList.get(0));
		}else{
			return null;
		}
	}
	
	public ProjectMasterForm convertProposalDetailsToProjectMasterForm(ProposalMasterDomain proposalMaster){
		ProjectMasterForm projectMaster= new ProjectMasterForm();
		Application application = proposalMaster.getApplication();
		projectMaster.setStrProjectName(application.getProposalTitle());
		/*---------------------- SET Total Cost and CDAC Cost  ---------------------------------------------*/
		//projectMaster.setProjectCost(application.getTotalProposalCost());
		projectMaster.setProjectCost(proposalMaster.getProposalCost());
		projectMaster.setTotalOutlay(application.getTotalProposalCost());
		/*-------------------------------------------------------------------------------------------------*/
		projectMaster.setStrProjectObjective(proposalMaster.getObjectives());	
		projectMaster.setDescription(proposalMaster.getSummary());
		projectMaster.setApplicationId(application.getNumId());
		if(application.getContactMaster() != null )
		projectMaster.setContactPersonList(application.getContactMaster());
		/*String centerCode=application.getGroupMaster().getOrganisationMasterDomain().getStrCode();
		String GroupCode=application.getGroupMaster().getStrCode();
		Date projectStartDt=application.getProjectMaster().get(0).getDtProjectStartDate();
		Calendar calendar= GregorianCalendar.getInstance();
		calendar.setTime(projectStartDt);
		int fullYear = (calendar.get(Calendar.YEAR));
		int year = (calendar.get(Calendar.YEAR))% 100;				
		String strProjectCount= projectMasterDao.getYearlyProject(fullYear);
		String projectSeq="";
		String strYear=Integer.toString(year);
		String projectCategory=application.getCategoryMaster().getStrCode();
		String projectType=application.getProjectTypeMaster().getStrCode();
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
		}*/
		//String projectCode=centerCode+GroupCode+strYear+projectCategory+projectType+projectSeq+"A";
		/*String proposalRefrenceNo =proposalMaster.getStrProposalRefNo();
		String projectRefrenceNo = proposalRefrenceNo.replaceFirst("T", "A");
		projectMaster.setProjectRefrenceNo(projectRefrenceNo);*/
		projectMaster.setStrProjectStatus("Under Approval");
		return projectMaster;
	}
	
	public ProjectMasterForm convertProjectMasterDomainToProjectMasterForm(ProjectMasterDomain projectMasterDomain){
		ProjectMasterForm projectMasterForm = new ProjectMasterForm();				

	if(null != projectMasterDomain.getDtWorkOrderDate()){
	    String workOrderDate= DateUtils.dateToString(projectMasterDomain.getDtWorkOrderDate()); 
	    projectMasterForm.setWorkOrderDate(workOrderDate);
	}
	if(null != projectMasterDomain.getDtProjectStartDate()){
	    String startDate= DateUtils.dateToString(projectMasterDomain.getDtProjectStartDate()); 
		projectMasterForm.setStartDate(startDate);
	}
	if(null != projectMasterDomain.getDtProjectEndDate()){
	    String endDate= DateUtils.dateToString(projectMasterDomain.getDtProjectEndDate());
	    projectMasterForm.setEndDate(endDate);
	}
	if(null != projectMasterDomain.getDtMOU()){
	    String MOUDate= DateUtils.dateToString(projectMasterDomain.getDtMOU());
		projectMasterForm.setMouDate(MOUDate);
	}
	
	projectMasterForm.setNumId(projectMasterDomain.getNumId());	
	projectMasterForm.setEncNumId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
	projectMasterForm.setProjectCost(projectMasterDomain.getProjectCost());
	if(projectMasterDomain.getDtProjectStartDate()!=null&&projectMasterDomain.getDtProjectEndDate()!=null){		
	projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(projectMasterDomain.getDtProjectStartDate(), projectMasterDomain.getDtProjectEndDate()));
	}
	projectMasterForm.setStrBriefDescription(projectMasterDomain.getStrBriefDescription());
	projectMasterForm.setStrProjectAim(projectMasterDomain.getStrProjectAim());
	projectMasterForm.setStrProjectName(projectMasterDomain.getStrProjectName());
	projectMasterForm.setStrProjectObjective(projectMasterDomain.getStrProjectObjective());
	projectMasterForm.setStrProjectScope(projectMasterDomain.getStrProjectScope());
	projectMasterForm.setStrProjectStatus(projectMasterDomain.getStrProjectStatus());
	projectMasterForm.setStrProjectRemarks(projectMasterDomain.getStrProjectRemarks());
	projectMasterForm.setStrFundedScheme(projectMasterDomain.getStrFundedScheme());
	projectMasterForm.setDescription(projectMasterDomain.getStrdescription());
	projectMasterForm.setAdministrationNo(projectMasterDomain.getAdministrationNo());
	if(projectMasterDomain.getStrProjectRefNo() != null){
	projectMasterForm.setProjectRefrenceNo(projectMasterDomain.getStrProjectRefNo());
	}
	projectMasterForm.setStrGST(projectMasterDomain.getStrGST());
	projectMasterForm.setStrTAN(projectMasterDomain.getStrTAN());
	if(projectMasterDomain.getNumIsValid()==1){
		projectMasterForm.setValid(true);
	}else{
		projectMasterForm.setValid(false);
	}	
	if(null!=projectMasterDomain.getApplication()){
		Application application = projectMasterDomain.getApplication();
		projectMasterForm.setClientMasterDomain(application.getClientMaster());
		if(projectMasterDomain.getApplication().getEndUserMaster() != null)
		projectMasterForm.setEndUserName(application.getEndUserMaster().getClientName());
		if(projectMasterDomain.getApplication().getProjectTypeMaster() != null )
		projectMasterForm.setProjectType(application.getProjectTypeMaster().getProjectTypeName());
		projectMasterForm.setEncGroupId(encryptionService.encrypt(String.valueOf(application.getGroupMaster().getNumId())));
		//Set<ThrustAreaMasterDomain> thrustAreas =application.getThrustAreas();
		String strThrustArea = "";
		Application applicationThrustArea =applicationDao.getThrustAreaByApplication(application.getNumId());
		if(null != applicationThrustArea){
			Set<ThrustAreaMasterDomain> thrustAreas = applicationThrustArea.getThrustAreas();
			if(null != thrustAreas){
				Iterator<ThrustAreaMasterDomain> itr = thrustAreas.iterator();
				while (itr.hasNext()) {
					ThrustAreaMasterDomain thrustArea = itr.next();
					if(StringUtils.isBlank(strThrustArea)){
						strThrustArea = thrustArea.getStrThrustAreaName();
					}else{
						strThrustArea = strThrustArea + ", "+thrustArea.getStrThrustAreaName();
					}				
				}			
			}
		}
		projectMasterForm.setThrustAreas(strThrustArea);
		
		projectMasterForm.setProjectCategory(application.getCategoryMaster().getCategoryName());
		if(application.isCollaborative()){
			projectMasterForm.setCollaborationOrganisationDetails(
			collaborativeOrgDetailsService.getAllActiveCollaborativeOrgDetailsDomain(application.getNumId()));
		}
		projectMasterForm.setTotalOutlay(application.getTotalProposalCost());
		projectMasterForm.setNumCorpMonthlySharing(projectMasterDomain.getCorpMonthlySharing());
	}
	
	return projectMasterForm;
	}

	@Override
	@PreAuthorize("hasAuthority('CHANGE_CLIENT_CONTACT_PERSON')")
	public ProjectMasterDomain getProjectMasterDataWithClientById(long projectId) {
		return projectMasterDao.getProjectMasterDataWithClientById(projectId);
	}

	@Override
	public ProjectMasterDomain mergeClientContactDetails(ProjectMasterDomain domain) {
		return projectMasterDao.mergeProjectMaster(domain);
	}
	
	@Override
	public JSONArray getProjectTypeWiseProjects(){
		Map<String,List<ProjectMasterDomain>> finalMap = new LinkedHashMap<String,List<ProjectMasterDomain>>();
		List<ProjectMasterDomain> projectList = projectMasterDao.getAllActiveProjectMasterData();

		
			for(ProjectMasterDomain projectMaster: projectList){
				if(projectMaster.getApplication() !=null && projectMaster.getApplication().getBusinessType()!=null){
				String businessTypeName = projectMaster.getApplication().getBusinessType().getBusinessTypeName();
				
				if(finalMap.containsKey(businessTypeName)){
					List<ProjectMasterDomain> tempList = finalMap.get(businessTypeName);
					tempList.add(projectMaster);
					finalMap.put(businessTypeName, tempList);
				}else{
					List<ProjectMasterDomain> tempList = new ArrayList<ProjectMasterDomain>();
					tempList.add(projectMaster);
					finalMap.put(businessTypeName, tempList);
				}		
				}
			}
			
			JSONArray finalArray = new JSONArray();
		/*	JSONArray headerArray = new JSONArray();
			
			headerArray.put("Project Name");
			headerArray.put("Project Cost");*/
			//finalArray.put(headerArray);
			
			//finalMap
			  for (Entry<String, List<ProjectMasterDomain>> entry : finalMap.entrySet())  {
				   JSONObject obj = new JSONObject();
				   //array.put(entry.getKey());
				  
				   List<ProjectMasterDomain> projects =  entry.getValue();
				   JSONArray projectsarray = new JSONArray();
				   for(int i =0;i<projects.size();i++){
					   ProjectMasterDomain domain =    projects.get(i);
					   JSONObject object = new JSONObject();
					   object.put("projectName", domain.getStrProjectName());
					   object.put("outlay", domain.getProjectCost());
					   projectsarray.put(object);
					}
				   obj.put(entry.getKey(),projectsarray);
				   
				   finalArray.put(obj);
			   }
			
		return finalArray;
	}
	
	@Override
	@PreAuthorize("hasAuthority('PROJECT_CLOSURE')")
	public boolean projectClosure(ProjectClosureModel projectClosureModel){
		boolean result = false;
		
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
			int selectedRoleId=selectedRole.getNumRoleId();
			int employeeId = userInfo.getEmployeeId();
		
			
		String teamDetails = projectClosureModel.getTeamDetails();
		String strProjectId = encryptionService.dcrypt(projectClosureModel.getEncProjectId());
		long projectId = Long.parseLong(strProjectId);
		try{
		Date currentDate = new Date();
		
		Date closureDate = null;
		String closureDateTemp = projectClosureModel.getClosureDate();
		
		if(null != closureDateTemp && !closureDateTemp.equals("")){
			closureDate = DateUtils.dateStrToDate(closureDateTemp);
		}else{
			closureDate = new Date();
		}
		List<EmployeeApprovedJobMapping> mappedApprovedPosts = employeeApprovedJobMappingDao.getActiveDataByProject(projectId);				
		
		for(EmployeeApprovedJobMapping jobMapping: mappedApprovedPosts){
			jobMapping.setEndDate(closureDate);			
			jobMapping.setNumTrUserId(employeeId);			
			jobMapping.getApprovedJob().setClosedOn(closureDate);			
			jobMapping.getApprovedJob().setStatus("Closed");
			jobMapping.getApprovedJob().setDtTrDate(currentDate);
			jobMapping.getApprovedJob().setNumTrUserId(employeeId);
			jobMapping.setRemark("Project "+projectClosureModel.getStatus());				
			employeeApprovedJobMappingDao.save(jobMapping);
		}
		
		
		if(null != teamDetails){
			JSONArray jsonArray = new JSONArray(teamDetails);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String releaseDate = jsonObject.getString("releaseDate");
				String empRoleId = encryptionService.dcrypt(jsonObject.getString("empRoleId"));
				long roleId = Long.parseLong(empRoleId);
				EmployeeRoleMasterDomain roleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterById(roleId);
				/*if(null != roleMasterDomain){
					roleMasterDomain.setRemarks(projectClosureModel.getStatus());
					if(null != releaseDate && !releaseDate.equals("")){
						roleMasterDomain.setDtEndDate(DateUtils.dateStrToDate(releaseDate));
					}else{
						roleMasterDomain.setDtEndDate(closureDate);
					}
					roleMasterDomain.setDtTrDate(currentDate);
					roleMasterDomain.setNumTrUserId(employeeId);
					employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(roleMasterDomain);				
				}*/
				TempEmployeeRoleMasterDomain tempEmployeeRoleMasterDomain=new TempEmployeeRoleMasterDomain();
				RoleMasterDomain roleMaster=new RoleMasterDomain();
				roleMaster.setNumId(selectedRoleId);
				tempEmployeeRoleMasterDomain.setDtTrDate(currentDate);
				if(null != releaseDate && !releaseDate.equals("")){
					/*tempEmployeeRoleMasterDomain.setDtEndDate(DateUtils.dateStrToDate(releaseDate));*/
					//Added by devesh to set closure date as end date when release date is after closure date
					Date releaseDateObj = DateUtils.dateStrToDate(releaseDate);
					if (releaseDateObj.after(closureDate)) {
				        tempEmployeeRoleMasterDomain.setDtEndDate(closureDate);
				    } else {
				        tempEmployeeRoleMasterDomain.setDtEndDate(releaseDateObj);
				    }
					//End of setting end date
				}else{
					tempEmployeeRoleMasterDomain.setDtEndDate(closureDate);
				}
				tempEmployeeRoleMasterDomain.setNumEmpId(roleMasterDomain.getNumEmpId());
				tempEmployeeRoleMasterDomain.setNumTrUserId(employeeId);
				tempEmployeeRoleMasterDomain.setRemarks(projectClosureModel.getStatus());
				tempEmployeeRoleMasterDomain.setNumProjectId((int)projectId);
				tempEmployeeRoleMasterDomain.setRoleMasterDomain(roleMasterDomain.getRoleMasterDomain());
				tempEmployeeRoleMasterDomain.setNumEmpRoleId(roleMasterDomain.getNumId());
				tempEmployeeRoleMasterDomain.setDtStartDate(roleMasterDomain.getDtStartDate());
				tempEmployeeRoleMasterDomain.setNumGroupId(roleMasterDomain.getNumGroupId());
				tempEmployeeRoleMasterDomain.setUtilization(roleMasterDomain.getUtilization());
				tempEmployeeRoleMasterDomain.setNumIsValid(1);
				
				employeeRoleMasterDao.saveUpdateTempEmployeeRoleMaster(tempEmployeeRoleMasterDomain);
			}		
		}	
		
		
			TempProjectMasterDomain tempProjectMasterDomain=new TempProjectMasterDomain();
			tempProjectMasterDomain.setNumProjectId(projectId);
			tempProjectMasterDomain.setProjectClosureDate(closureDate);		
			tempProjectMasterDomain.setNumEmpId(employeeId);
			tempProjectMasterDomain.setClosureRemarks(projectClosureModel.getClosureRemark());	
			tempProjectMasterDomain.setNumRoleId(selectedRoleId);
			tempProjectMasterDomain.setDtTrDate(currentDate);
			tempProjectMasterDomain.setNumIsValid(1);
			tempProjectMasterDomain.setNumTrUserId(employeeId);
			projectMasterDao.mergeProjectMaster(tempProjectMasterDomain);
			result= true;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			/*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/
			Set<EmployeeRoleMasterModel> filteredRoles = new HashSet<EmployeeRoleMasterModel>();
			EmployeeRoleMasterDomain latestPL = employeeRoleMasterDao.getPLStatusByProjectIds((int)projectId);
		    if(latestPL != null && employeeId==latestPL.getNumEmpId()) {
				EmployeeRoleMasterModel employeeRoleMaster = new EmployeeRoleMasterModel();
				filteredRoles.add(employeeRoleMaster);
		    }
	        /*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/

			if(selectedRoleId==3 || filteredRoles.size()>0)
			{
				//Added by devesh on 01/09/23 for setting closure role action for HOD
				TransactionMasterModel trMasterModel=new TransactionMasterModel();
				List<Object[]> allocatedEmployees = employeeRoleMasterDao.projectTeamWiseEmployees(projectId,0);
				Boolean show=false;
				for(Object[] a:allocatedEmployees){
					if(a[8].equals("Head of Department")){
						show=true;
						break;
					}
				}
				if(show){
					//TransactionMasterModel trMasterModel=new TransactionMasterModel();
					trMasterModel.setDtTrDate(new Date());
					trMasterModel.setNumActionId(26);
					trMasterModel.setNumRoleId(3);
					trMasterModel.setNumIsValid(1);
					trMasterModel.setNumWorkflowId(4);
					trMasterModel.setStrRemarks(projectClosureModel.getClosureRemark());  // Set Remarks while Update or Initiate the Project Closure [06-12-2023]
					trMasterModel.setNumTrUserId(employeeId);
					trMasterModel.setCustomId((int)projectId);
				}
				else{
					//TransactionMasterModel trMasterModel=new TransactionMasterModel();
					trMasterModel.setDtTrDate(new Date());
					trMasterModel.setNumActionId(15);
					trMasterModel.setNumRoleId(3);
					trMasterModel.setNumIsValid(1);
					trMasterModel.setNumWorkflowId(4);
					trMasterModel.setStrRemarks(projectClosureModel.getClosureRemark()); // Set Remarks while Update or Initiate the Project Closure [06-12-2023]
					trMasterModel.setNumTrUserId(employeeId);
					trMasterModel.setCustomId((int)projectId);
				}
				//End of setting role action
		/*TransactionMasterModel trMasterModel=new TransactionMasterModel();
		trMasterModel.setDtTrDate(new Date());
		trMasterModel.setNumActionId(15);
		trMasterModel.setNumRoleId(3);
		trMasterModel.setNumIsValid(1);
		trMasterModel.setNumWorkflowId(4);
		
		trMasterModel.setNumTrUserId(employeeId);
		trMasterModel.setCustomId((int)projectId);*/	
		
		workflowService.insertIntoTransaction(trMasterModel);
			}
		//Added by devesh on 08-09-23 to give authorization to send to GC from HOD 	
		else if(selectedRoleId==15){
			TransactionMasterModel trMasterModel=new TransactionMasterModel();
			trMasterModel.setDtTrDate(new Date());
			trMasterModel.setNumActionId(28);
			trMasterModel.setNumRoleId(15);
			trMasterModel.setNumIsValid(1);
			trMasterModel.setNumWorkflowId(4);
			trMasterModel.setStrRemarks(projectClosureModel.getClosureRemark());  // Set Remarks while Update or Initiate the Project Closure [06-12-2023]
			trMasterModel.setNumTrUserId(employeeId);
			trMasterModel.setCustomId((int)projectId);
			workflowService.insertIntoTransaction(trMasterModel);
		}
		else if(selectedRoleId==5){
			TransactionMasterModel trMasterModel=new TransactionMasterModel();
			trMasterModel.setDtTrDate(new Date());
			trMasterModel.setNumActionId(24);
			trMasterModel.setNumRoleId(5);
			trMasterModel.setNumIsValid(1);
			trMasterModel.setNumWorkflowId(4);
			trMasterModel.setStrRemarks(projectClosureModel.getClosureRemark());  // Set Remarks while Update or Initiate the Project Closure [06-12-2023]
			trMasterModel.setNumTrUserId(employeeId);
			trMasterModel.setCustomId((int)projectId);
			workflowService.insertIntoTransaction(trMasterModel);
		}
		//End of Action
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}	
	
	@Override
	@PreAuthorize("hasAuthority('READ_COMPLETED_PROJECT')")
	public List<ProjectMasterForm> getAllCompletedProject(String startDate,String endDate){
		List<ProjectMasterForm> projectMasterFormList=new ArrayList<ProjectMasterForm>();
		ProjectMasterForm tempProjectMasterForm= new ProjectMasterForm();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		int roleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
		/*if(userInfo.getSelectedEmployeeRole().getNumRoleId()==2 || userInfo.getSelectedEmployeeRole().getNumRoleId()==3 || userInfo.getSelectedEmployeeRole().getNumRoleId()==4)*/
		if(roleId==2 || roleId==3 || roleId==4 || roleId==15)//HOD is added by devesh on 19-10-23
		{
			List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
			List<Long> longs = numProjectIds.stream()
			        .mapToLong(Integer::longValue)
			        .boxed().collect(Collectors.toList());
			projectMasterFormList=convertProjectDetailsToModelList(projectMasterDao.getCompletedProjectDataByProjectIds(startDate,endDate,longs));
		}
		else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5)
		{
			List<Integer> numGroupIds=employeeRoleMasterDao.getGroupIds(userInfo.getEmployeeId());
			if(null != numGroupIds && numGroupIds.size() == 1){
				//Load All completed projects
				projectMasterFormList=convertProjectDetailsToModelList(projectMasterDao.getCompletedProjectData(""+numGroupIds.get(0),startDate,endDate));
			}else if(null != numGroupIds && numGroupIds.size() > 1){
				//Load Groups
				List<Long> longs = numGroupIds.stream()
				        .mapToLong(Integer::longValue)
				        .boxed().collect(Collectors.toList());
				tempProjectMasterForm.setGroupMasterModelList(groupMasterService.getGroupMasterByIds(longs));
				projectMasterFormList.add(tempProjectMasterForm);
			}
			
		}
		else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==6 || userInfo.getSelectedEmployeeRole().getNumRoleId()==9 || userInfo.getSelectedEmployeeRole().getNumRoleId()==7 )
		{
			Long orgId = Long.valueOf(userInfo.getAssignedOrganisation()).longValue();
			
			tempProjectMasterForm.setGroupMasterModelList(groupMasterService.getAllActiveGrpMasterDomain(orgId));
			projectMasterFormList.add(tempProjectMasterForm);
			
		}
		
		return projectMasterFormList;			
	}

	@Override
	public List<ProjectMasterForm> getCompletedProjectByGroup(String groupId,String strStartDate,String strEndDate) {		
		return convertProjectDetailsToModelList(projectMasterDao.getCompletedProjectData(groupId,strStartDate,strEndDate));
	}
	@Override
	public List<ProjectMasterModel> getProjectDataByGroupIds(String groupIds) {
		List<ProjectMasterModel> projectList =convertProjectMasterDomainToModelList(projectMasterDao.getProjectDataByGroupIds(groupIds));		
		return projectList;
	}
	

	
		public List<ProjectMasterForm> getProjectRolesDetails(){
			return convertMediaDomainToModelList(projectMasterDao.getProjectRolesDetails());			
		
	}
		public List<ProjectMasterForm> convertMediaDomainToModelList(List<ProjectRolesMaster> projectRolesMaster){
			List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
				for(int i=0;i<projectRolesMaster.size();i++){
					ProjectRolesMaster prm = projectRolesMaster.get(i);
					ProjectMasterForm pm = new ProjectMasterForm();
					pm.setNumId(prm.getNumId());
					pm.setStrRoleName(prm.getRoleName());
				
					list.add(pm);
		}
			return list;
		}

		@Override
		public List<ProjectMasterModel> getNewProjectsDetail(ProjectMasterModel model) {
			String strStartDate = model.getStartDate();
			String strEndDate = model.getEndDate();
			Date startRange = null;
			Date endRange = null;
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startRange = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(null != strEndDate && !strEndDate.equals("")){
				try {
					endRange = DateUtils.dateStrToDate(strEndDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				endRange = new Date();
			}
			//System.out.println("service endrange" + endRange);
			List<Object[]> list = projectMasterDao.getNewProjectsDetail(startRange, endRange);
			List<ProjectMasterModel> dataList = new ArrayList<ProjectMasterModel>();
			if(null != list){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				String projectName = (String) obj[0];
				BigInteger b=new BigInteger(""+obj[2]);
				//long projectId = (long) obj[4];
				String strprojectAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[1]);
				//System.out.println(projectCost);
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strprojectAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				Date dtProjectStartDate = (Date) obj[4];
				String strClientName = (String) obj[5];
				//System.out.print(strReceivedAmount);
				/*System.out.print(projectName);
				System.out.println(projectCost);
				System.out.println(b);*/
				//System.out.println(strClientName);
				
				ProjectMasterModel projectMasterModel = new ProjectMasterModel();
				projectMasterModel.setStrProjectName(projectName);
				projectMasterModel.setStrProjectCost(strprojectAmount + "Lakhs");
				projectMasterModel.setNumProjectAmountLakhs(roundOfProjectCost);
				projectMasterModel.setStartDate(DateUtils.dateToString(dtProjectStartDate));
				projectMasterModel.setStrClientName(strClientName);
				projectMasterModel.setNumProjectId(b.longValue());
				projectMasterModel.setStrWorkOrderDate(DateUtils.dateToString((Date) obj[7]));
				projectMasterModel.setStrMouDate(DateUtils.dateToString((Date) obj[8]));
				projectMasterModel.setStrGrouptName((String)obj[9]);
				projectMasterModel.setStrReferenceNumber((String)obj[10]);
				/*if(null != paymentDate){
					projectPaymentReceivedModel.setStrdtPayment(DateUtils.dateToString(paymentDate));
					projectPaymentReceivedModel.setTempDate(paymentDate);
				}*/
				
				
				projectMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
		/*---------------------------- Get the Latest Transaction Detail of Project [ 04/08/2023 ]----------------------------------------------*/
				Date dtProjectEndDate = (Date) obj[6];
				if (dtProjectEndDate != null) {
				    projectMasterModel.setEndDate(DateUtils.dateToString(dtProjectEndDate));
				} else {
				    projectMasterModel.setEndDate(""); // Set to empty string if the end date is null
				}
				projectMasterModel.setWorkflowModel(workflowService.getLatestTransactionDetail((int) projectMasterModel.getNumProjectId(), 3));
		/*---------------------------- [ 04/08/2023 ]----------------------------------------------*/
				dataList.add(projectMasterModel);			
			}
			}
			//System.out.println(dataList);
			return dataList;
		}

		@Override
		public long getNewProjectCount(Date startRange1, Date endRange1) {
			//long count =  0;
			//String newProjectCount = null;
			//List<Double> count = projectMasterDao.getNewProjectsCountByGroup(startRange1,endRange1);
			
			//newProjectCount = count.toString();
			long newProjectCount = projectMasterDao.getNewProjectsCountByGroup(startRange1,endRange1);
			return newProjectCount;
		}
		
		@Override
		public long getClosedProjectCount(Date startRange, Date endRange) {
			
			long closedProjectCount = projectMasterDao.getClosedProjectsCountByGroup(startRange,endRange);
			//System.out.println("closedProjectCount"+closedProjectCount);
			return closedProjectCount;
		}
		
		@Override
		public List<ProjectMasterModel> getClosedProjectsDetail(ProjectMasterModel model) {
			String strStartDate = model.getStartDate();
			String strEndDate = model.getEndDate();
			Date startRange = null;
			Date endRange = null;
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startRange = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(null != strEndDate && !strEndDate.equals("")){
				try {
					endRange = DateUtils.dateStrToDate(strEndDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				endRange = new Date();
			}
			/*------------------- Get the All Closed Project Details [12-10-2023] -------------------*/
			List<Object[]> list = projectMasterDao.getCompletedProjectData(startRange, endRange);
			List<ProjectMasterModel> dataList = new ArrayList<ProjectMasterModel>();
			if(null != list){
				for(int i=0;i<list.size();i++){
					Object[] obj = list.get(i);
					ProjectMasterModel projectMasterModel = new ProjectMasterModel();
					/*
					String projectName = (String) obj[0];
					BigInteger b=new BigInteger(""+obj[2]);
					String strprojectAmount = null;
					double roundOfProjectCost = 0.0;
					double projectCost = Double.parseDouble(""+obj[1]);
					strprojectAmount = CurrencyUtils.convertToINR(projectCost);
					Date dtProjectStartDate = (Date) obj[4];
					String strClientName = (String) obj[5];
					Date projectClosureDate = (Date) obj[6];				
					ProjectMasterModel projectMasterModel = new ProjectMasterModel();
					projectMasterModel.setStrProjectName(projectName);
					projectMasterModel.setStrProjectCost(strprojectAmount);
					projectMasterModel.setNumProjectAmountLakhs(roundOfProjectCost);
					projectMasterModel.setStartDate(DateUtils.dateToString(dtProjectStartDate));
					projectMasterModel.setStrClientName(strClientName);
					projectMasterModel.setProjectClosureDate(DateUtils.dateToString(projectClosureDate));
					projectMasterModel.setNumProjectId(b.longValue());
					projectMasterModel.setStrGrouptName((String)obj[7]);
					projectMasterModel.setStrReferenceNumber((String)obj[8]);
					Date dtProjectEndDate = (Date) obj[9];
					projectMasterModel.setEndDate(DateUtils.dateToString(dtProjectEndDate));
					String totalCost = CurrencyUtils.convertToINR(Double.parseDouble(""+obj[10]));
					projectMasterModel.setStrTotalCost(totalCost);
					projectMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));*/

					String projectName = (String) obj[0];
					Date dtProjectStartDate = (Date) obj[1];
					Date dtProjectEndDate = (Date) obj[2];
					String strprojectAmount = CurrencyUtils.convertToINR(Double.parseDouble(""+obj[3]));
					String strClientName = (String) obj[6];
					String totalCost = CurrencyUtils.convertToINR(Double.parseDouble(""+obj[9]));
					projectMasterModel.setProjectDuration(DateUtils.getDurationInMonths(((Date)obj[1]), ((Date)obj[2])));
					BigInteger b=new BigInteger(""+obj[10]);
					projectMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));

					projectMasterModel.setStrProjectName(projectName);
					projectMasterModel.setStrProjectCost(strprojectAmount);
					projectMasterModel.setStartDate(DateUtils.dateToString(dtProjectStartDate));
					projectMasterModel.setEndDate(DateUtils.dateToString(dtProjectEndDate));
					projectMasterModel.setStrClientName(strClientName);
					projectMasterModel.setStrGrouptName((String)obj[8]);
					projectMasterModel.setStrReferenceNumber((String)obj[7]);
					projectMasterModel.setStrTotalCost(totalCost);
					
					double receiveAmt = 0.0;
					Object withInvoiceReceived=obj[4];
					String strReceiveAmt=null;
					if(withInvoiceReceived != null){
						receiveAmt = Double.parseDouble(""+withInvoiceReceived);
					}
					Object withoutInvoiceReceivedData = obj[11];
					if(withoutInvoiceReceivedData != null){
						double withoutInvoiceReceived = Double.parseDouble(""+obj[11]);
						receiveAmt += withoutInvoiceReceived;
					}
					if(receiveAmt>0){
						projectMasterModel.setRowColor("black");
					}else{
					    projectMasterModel.setRowColor("red");	
					}
					strReceiveAmt=CurrencyUtils.convertToINR(receiveAmt);
					projectMasterModel.setStrReceivedAmount(strReceiveAmt);
					if (obj[5] == null) {
					    projectMasterModel.setStrPLName("");
					} else {
					    projectMasterModel.setStrPLName((String) obj[5]);
					}
					/*------------ Get Workflow data for closed Projects tile [07-03-2024] ------------*/
					projectMasterModel.setWorkflowModel(workflowService.getLatestTransactionDetail(b.intValue(), 4));
					/*--------------------------------- EOL [12-10-2023] -----------------------------*/
					dataList.add(projectMasterModel);			
				}
			}
			//System.out.println(dataList);
			return dataList;
		}

		@Override
		public String getProjectIdByApplicationId(ProjectMasterForm projectMasterForm){
			String result ="";
			String encApplicationId = projectMasterForm.getEncApplicationId();
			if(null != encApplicationId){
				String strApplicationId = encryptionService.dcrypt(encApplicationId);
				long applicationId = Long.parseLong(strApplicationId);
						
				List<ProjectMasterDomain> dataList = projectMasterDao.getProjectDetailsByApplicationId(applicationId);
				if(null != dataList && dataList.size()>0){
					ProjectMasterDomain domain = dataList.get(0);
					result  =encryptionService.encrypt(""+domain.getNumId());
				}
			}
			return result;
		}

		@Override
		public int checkProjectDate(ProjectMasterForm projectMasterForm) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			EmployeeRoleMasterDomain employeeRoleMasterDomain=null;
			
			List<EmployeeRoleMasterDomain> dataList=employeeRoleMasterDao.getEmployeeRoleMasterDomain(projectMasterForm.getNumId(),userInfo.getEmployeeId());
			/*if(domain.size()>0){
				employeeRoleMasterDomain=domain.get(0);
			}*/
			
			if(dataList.size()>0){
				employeeRoleMasterDomain = dataList.stream()
		                .filter(x -> 3 == x.getRoleMasterDomain().getNumId())
		                .findAny()
		                .orElse(null);
				
			}
			
			if(employeeRoleMasterDomain!=null){
				if(!employeeRoleMasterDomain.getDtStartDate().equals(projectMasterForm.getStartDate())){
					try {
						employeeRoleMasterDomain.setDtStartDate(DateUtils.dateStrToDate(projectMasterForm.getStartDate()));
					} catch (ParseException e) {					
						e.printStackTrace();
					}
					employeeRoleMasterDomain.setDtTrDate(new Date());
					employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
				}
			
				}else if(!(null != dataList && dataList.size() >0)){
					employeeRoleMasterDomain = new EmployeeRoleMasterDomain();
				
					 employeeRoleMasterDomain.setDtStartDate(new Date());
				 
				
				
				
				ProjectMasterDomain projDomain=projectMasterDao.getProjectMasterDataById(projectMasterForm.getNumId());
				
				Application application =  projDomain.getApplication();
				if(null != application){
					employeeRoleMasterDomain.setDtTrDate(new Date());
					employeeRoleMasterDomain.setNumOrganisationId((int)application.getGroupMaster().getOrganisationMasterDomain().getNumId());
					employeeRoleMasterDomain.setNumGroupId((int)application.getGroupMaster().getNumId());
				}
				
				employeeRoleMasterDomain.setDtTrDate(new Date());
				employeeRoleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
				employeeRoleMasterDomain.setNumEmpId(userInfo.getEmployeeId());
				employeeRoleMasterDomain.setRoleMasterDomain(roleMasterService.getRoleDomainById(3));
				employeeRoleMasterDomain.setUtilization(10); // Involvement percent set to 10 for PL by devesh on 25-04-24
				employeeRoleMasterDomain.setNumProjectId(Integer.parseInt(projectMasterForm.getNumId()+""));
				employeeRoleMasterDomain.setNumIsValid(1);
				employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
					
			}
			return 1;
		}
		
		//Function added by devesh on 19-02-24 to map HODs and PIs to project
		@Override
		public int checkProjectDateByRole(ProjectMasterForm projectMasterForm, int roleId) {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		    EmployeeRoleMasterDomain employeeRoleMasterDomain = null;

		    List<EmployeeRoleMasterDomain> dataList = new ArrayList<EmployeeRoleMasterDomain>();
		    long HODempId = 0;
		    long PIempId = 0;
		    
		    if(roleId==15){
	        	HODempId = employeeRoleMasterDao.getHODEmployeeIds(userInfo.getEmployeeId(),userInfo.getGroupId());  // Pass Group Id While Retrieve HOD ID [19-04-2024]
	        	dataList = employeeRoleMasterDao.getEmployeeRoleMasterDomain(projectMasterForm.getNumId(), (int) HODempId);
	        }
		    else if(roleId==4){
		    	PIempId = employeeRoleMasterDao.getPIEmployeeIds(userInfo.getEmployeeId(),userInfo.getGroupId()); // Pass Group Id While Retrieve PI ID [19-04-2024]
	        	dataList = employeeRoleMasterDao.getEmployeeRoleMasterDomain(projectMasterForm.getNumId(), (int) PIempId);
	        }
		    else dataList = employeeRoleMasterDao.getEmployeeRoleMasterDomain(projectMasterForm.getNumId(), userInfo.getEmployeeId());

		    if (dataList.size() > 0) {
		        employeeRoleMasterDomain = dataList.stream()
		                .filter(x -> roleId == x.getRoleMasterDomain().getNumId())
		                .findAny()
		                .orElse(null);
		    }

		    if (employeeRoleMasterDomain != null) {
		        /*if (!employeeRoleMasterDomain.getDtStartDate().equals(projectMasterForm.getStartDate())) {
		            try {
		                employeeRoleMasterDomain.setDtStartDate(DateUtils.dateStrToDate(projectMasterForm.getStartDate()));
		            } catch (ParseException e) {
		                e.printStackTrace(); // Handle or log the exception appropriately
		            }
		            employeeRoleMasterDomain.setDtTrDate(new Date());
		            employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
		        }*/
		    } else if (!(null != dataList && dataList.size() > 0)) {
		        employeeRoleMasterDomain = new EmployeeRoleMasterDomain();
		        try {
		            employeeRoleMasterDomain.setDtStartDate(DateUtils.dateStrToDate(projectMasterForm.getStartDate()));
		        } catch (ParseException e) {
		            e.printStackTrace(); // Handle or log the exception appropriately
		        }

		        ProjectMasterDomain projDomain = projectMasterDao.getProjectMasterDataById(projectMasterForm.getNumId());

		        Application application = projDomain.getApplication();
		        if (null != application) {
		            employeeRoleMasterDomain.setDtTrDate(new Date());
		            employeeRoleMasterDomain.setNumOrganisationId((int) application.getGroupMaster().getOrganisationMasterDomain().getNumId());
		            employeeRoleMasterDomain.setNumGroupId((int) application.getGroupMaster().getNumId());
		        }

		        employeeRoleMasterDomain.setDtTrDate(new Date());
		        if(roleId==15){
		        	HODempId = employeeRoleMasterDao.getHODEmployeeIds(userInfo.getEmployeeId(),userInfo.getGroupId()); // Pass Group Id While Retrieve HOD ID [19-04-2024]
					if(HODempId != 0){
						employeeRoleMasterDomain.setNumTrUserId((int) HODempId);
						employeeRoleMasterDomain.setNumEmpId(HODempId);
						employeeRoleMasterDomain.setRoleMasterDomain(roleMasterService.getRoleDomainById(roleId));
						employeeRoleMasterDomain.setUtilization(1);
					}
					else return 1;
		        }
		        else if (roleId==4){
		        	PIempId = employeeRoleMasterDao.getPIEmployeeIds(userInfo.getEmployeeId(),userInfo.getGroupId()); // Pass Group Id While Retrieve PI ID [19-04-2024]
					if(PIempId != 0){
						employeeRoleMasterDomain.setNumTrUserId((int) PIempId);
						employeeRoleMasterDomain.setNumEmpId(PIempId);
						employeeRoleMasterDomain.setRoleMasterDomain(roleMasterService.getRoleDomainById(roleId));
						employeeRoleMasterDomain.setUtilization(1);
					}
					else return 1;
		        }
		        else{
		        	employeeRoleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
					employeeRoleMasterDomain.setNumEmpId(userInfo.getEmployeeId());
					employeeRoleMasterDomain.setRoleMasterDomain(roleMasterService.getRoleDomainById(roleId));
		        }
		        employeeRoleMasterDomain.setNumProjectId(Integer.parseInt(projectMasterForm.getNumId() + ""));
		        employeeRoleMasterDomain.setNumIsValid(1);
		        employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
		    }

		    return 1;
		}
		//End of Function
		
		@Override
		public List<ProjectMasterForm> getPendingClosureDetail() {
			List <Object[]> List = projectMasterDao.getPendingClosureDetail();
			List<ProjectMasterForm> listForm = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<List.size();i++){
				Object[] obj = List.get(i);
				ProjectMasterForm proposalMasterForm = new ProjectMasterForm();	
				if(null != obj[3]){
				    String startDate= DateUtils.dateToString((Date)obj[3]); 
					proposalMasterForm.setStartDate(startDate);
				}
				if(null != obj[4]){
				    String endDate= DateUtils.dateToString((Date)obj[4]);
				    proposalMasterForm.setEndDate(endDate);
				}
				//Added by devesh on 21-09-23 to set values of project cost
				BigInteger b=new BigInteger(""+obj[1]);
				String strprojectAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[8]);
				//System.out.println(projectCost);
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strprojectAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				//End of project cost
				
				proposalMasterForm.setEncNumId(encryptionService.encrypt(""+obj[1]));
				proposalMasterForm.setStrProjectName((String)obj[2]);
				proposalMasterForm.setStrGroupName((String)obj[0]);
				proposalMasterForm.setProjectRefrenceNo((String)obj[5]);
				proposalMasterForm.setClientName((String)obj[6]);
				proposalMasterForm.setStrPLName((String)obj[7]);
				//Added by devesh on 21-09-23 to set values of cdac outlay and workflow status
				//System.out.println(projectCost);
				proposalMasterForm.setStrProjectCost(strprojectAmount + "Lakhs");
				proposalMasterForm.setNumProjectAmountLakhs(roundOfProjectCost);
				proposalMasterForm.setProjectId(b.longValue());
				proposalMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int) proposalMasterForm.getProjectId(), 4));
				//End of setting cdac outlay and workflow status
				listForm.add(proposalMasterForm);
			}
			
			return listForm;
		
			
			
		}
		
		//Added new service for Pending Closure Tile without closure initialized details by devesh on 17/8/23
		@Override
		public List<ProjectMasterForm> getPendingClosureDetailforOngoing() {
			List <Object[]> List = projectMasterDao.getPendingClosureDetailforOngoing();
			List<ProjectMasterForm> listForm = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<List.size();i++){
				Object[] obj = List.get(i);
				ProjectMasterForm proposalMasterForm = new ProjectMasterForm();	
				if(null != obj[3]){
				    String startDate= DateUtils.dateToString((Date)obj[3]); 
					proposalMasterForm.setStartDate(startDate);
				}
				if(null != obj[4]){
				    String endDate= DateUtils.dateToString((Date)obj[4]);
				    proposalMasterForm.setEndDate(endDate);
				}
				proposalMasterForm.setEncNumId(encryptionService.encrypt(""+obj[1]));
				proposalMasterForm.setStrProjectName((String)obj[2]);
				proposalMasterForm.setStrGroupName((String)obj[0]);
				proposalMasterForm.setProjectRefrenceNo((String)obj[5]);
				proposalMasterForm.setClientName((String)obj[6]);
				proposalMasterForm.setStrPLName((String)obj[7]);
				listForm.add(proposalMasterForm);
			}
			
			return listForm;
		
			
			
		}
		//End of Service
		
		@Override
		public List<ProjectMasterModel> getAllClosedProjectByGroup(ProjectMasterModel model) {
			String strStartDate = model.getStartDate();
			String strEndDate = model.getEndDate();
			Date startRange = null;
			Date endRange = null;
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startRange = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(null != strEndDate && !strEndDate.equals("")){
				try {
					endRange = DateUtils.dateStrToDate(strEndDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				endRange = new Date();
			}
			//String strEncGroupId=model.getEncGroupId().substring( 1, model.getEncGroupId().length() - 1 );
			String strGroupId = encryptionService.dcrypt(model.getEncGroupId());
			long groupId = Long.parseLong(strGroupId);
			List<Object[]> list = projectMasterDao.getAllClosedProjectByGroup(startRange, endRange,groupId);
			List<ProjectMasterModel> dataList = new ArrayList<ProjectMasterModel>();
			if(null != list){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				String projectName = (String) obj[0];
				BigInteger b=new BigInteger(""+obj[2]);
			
				String strprojectAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[1]);
				
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strprojectAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				Date dtProjectStartDate = (Date) obj[4];
				String strClientName = (String) obj[5];
				Date projectClosureDate = (Date) obj[6];
				
				
				ProjectMasterModel projectMasterModel = new ProjectMasterModel();
				projectMasterModel.setStrProjectName(projectName);
				projectMasterModel.setStrProjectCost(strprojectAmount + "Lakhs");
				projectMasterModel.setNumProjectAmountLakhs(roundOfProjectCost);
				projectMasterModel.setStartDate(DateUtils.dateToString(dtProjectStartDate));
				projectMasterModel.setStrClientName(strClientName);
				projectMasterModel.setProjectClosureDate(DateUtils.dateToString(projectClosureDate));
				projectMasterModel.setNumProjectId(b.longValue());
				projectMasterModel.setStrGrouptName((String)obj[7]);
				projectMasterModel.setStrReferenceNumber((String)obj[8]);
			
				
				projectMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
				dataList.add(projectMasterModel);			
			}
			}
			
			return dataList;
		}

		@Override
		public List<ProjectMasterModel> getNewProjectsByGroupDetail(ProjectMasterModel model) {
			String strStartDate = model.getStartDate();
			String strEndDate = model.getEndDate();
			Date startRange = null;
			Date endRange = null;
			if(null != strStartDate && !strStartDate.equals("")){
				try {
					startRange = DateUtils.dateStrToDate(strStartDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			if(null != strEndDate && !strEndDate.equals("")){
				try {
					endRange = DateUtils.dateStrToDate(strEndDate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}else{
				endRange = new Date();
			}
			//System.out.println("service endrange" + endRange);
			//String strEncGroupId=model.getEncGroupId().substring( 1, model.getEncGroupId().length() - 1 );
			String strGroupId = encryptionService.dcrypt(model.getEncGroupId());
			long groupId = Long.parseLong(strGroupId);
			List<Object[]> list = projectMasterDao.getNewProjectsByGroupDetail(startRange, endRange,groupId);
			List<ProjectMasterModel> dataList = new ArrayList<ProjectMasterModel>();
			if(null != list){
			for(int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				String projectName = (String) obj[0];
				BigInteger b=new BigInteger(""+obj[2]);
				//long projectId = (long) obj[4];
				String strprojectAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[1]);
				//System.out.println(projectCost);
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strprojectAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				Date dtProjectStartDate = (Date) obj[4];
				String strClientName = (String) obj[5];
				
				
				ProjectMasterModel projectMasterModel = new ProjectMasterModel();
				projectMasterModel.setStrProjectName(projectName);
				projectMasterModel.setStrProjectCost(strprojectAmount + "Lakhs");
				projectMasterModel.setNumProjectAmountLakhs(roundOfProjectCost);
				projectMasterModel.setStartDate(DateUtils.dateToString(dtProjectStartDate));
				projectMasterModel.setStrClientName(strClientName);
				projectMasterModel.setNumProjectId(b.longValue());
				projectMasterModel.setStrWorkOrderDate(DateUtils.dateToString((Date) obj[7]));
				projectMasterModel.setStrMouDate(DateUtils.dateToString((Date) obj[8]));
				projectMasterModel.setStrGrouptName((String)obj[9]);
				projectMasterModel.setStrReferenceNumber((String)obj[10]);
		
				
				
				projectMasterModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
				dataList.add(projectMasterModel);			
			}
			}
			
			return dataList;
		}

		@Override
		public List<ProjectMasterForm> getPendingClosureDetailByDate(String closureDate, String symbol) {
			List <Object[]> List = projectMasterDao.getPendingClosureDetailByDate(closureDate,symbol);
			List<ProjectMasterForm> listForm = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<List.size();i++){
				Object[] obj = List.get(i);
				ProjectMasterForm proposalMasterForm = new ProjectMasterForm();	
				if(null != obj[3]){
				    String startDate= DateUtils.dateToString((Date)obj[3]); 
					proposalMasterForm.setStartDate(startDate);
				}
				if(null != obj[4]){
				    String endDate= DateUtils.dateToString((Date)obj[4]);
				    proposalMasterForm.setEndDate(endDate);
				}
				//Added by devesh on 21-09-23 to set values of project cost
				BigInteger b=new BigInteger(""+obj[1]);
				String strprojectAmount = null;
				double roundOfProjectCost = 0.0;
				double projectCost = Double.parseDouble(""+obj[8]);
				//System.out.println(projectCost);
				roundOfProjectCost = CurrencyUtils.round((projectCost/100000),2);
				strprojectAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
				//End of project cost
				proposalMasterForm.setEncNumId(encryptionService.encrypt(""+obj[1]));
				proposalMasterForm.setStrProjectName((String)obj[2]);
				proposalMasterForm.setStrGroupName((String)obj[0]);
				proposalMasterForm.setProjectRefrenceNo((String)obj[5]);
				proposalMasterForm.setClientName((String)obj[6]);
				proposalMasterForm.setStrPLName((String)obj[7]);
				//Added by devesh on 21-09-23 to set values of cdac outlay and workflow status
				//System.out.println(projectCost);
				proposalMasterForm.setStrProjectCost(strprojectAmount + "Lakhs");
				proposalMasterForm.setNumProjectAmountLakhs(roundOfProjectCost);
				proposalMasterForm.setProjectId(b.longValue());
				proposalMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int) proposalMasterForm.getProjectId(), 4));
				//End of setting cdac outlay and workflow status
				listForm.add(proposalMasterForm);
			}
			
			return listForm;
		
			
			
		}
		
		//Added new service for Pending Closure Tile without closure initialized details by devesh on 17/8/23
		@Override
		public List<ProjectMasterForm> getPendingClosureDetailByDateforOngoing(String closureDate, String symbol) {
			List <Object[]> List = projectMasterDao.getPendingClosureDetailByDateforOngoing(closureDate,symbol);
			List<ProjectMasterForm> listForm = new ArrayList<ProjectMasterForm>();
			for(int i=0;i<List.size();i++){
				Object[] obj = List.get(i);
				ProjectMasterForm proposalMasterForm = new ProjectMasterForm();	
				if(null != obj[3]){
				    String startDate= DateUtils.dateToString((Date)obj[3]); 
					proposalMasterForm.setStartDate(startDate);
				}
				if(null != obj[4]){
				    String endDate= DateUtils.dateToString((Date)obj[4]);
				    proposalMasterForm.setEndDate(endDate);
				}
				proposalMasterForm.setEncNumId(encryptionService.encrypt(""+obj[1]));
				proposalMasterForm.setStrProjectName((String)obj[2]);
				proposalMasterForm.setStrGroupName((String)obj[0]);
				proposalMasterForm.setProjectRefrenceNo((String)obj[5]);
				proposalMasterForm.setClientName((String)obj[6]);
				proposalMasterForm.setStrPLName((String)obj[7]);
				listForm.add(proposalMasterForm);
			}
			
			return listForm;
		
			
			
		}
		//End of Service
		
		public ProjectMasterDomain saveProjectDetails(String encProjId,int selectedVal){
			String strProjectId = encryptionService.dcrypt(encProjId);
			long projectId = Long.parseLong(strProjectId);
			return projectMasterDao.updateProject(projectId,selectedVal);
		}
		@Override
		@PreAuthorize("hasAuthority('UNDER_APPROVAL_PROJECTS')")
		public List<ProjectMasterForm> getUnderApprovalProjects(){
			return convertUnderApprovalProjectsToModelList(projectMasterDao.getUnderApprovalProjects());
		}
		
		public List<ProjectMasterForm> convertUnderApprovalProjectsToModelList(List<Object[]>projectDetailsList){
			List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
				for(int i=0;i<projectDetailsList.size();i++){			
					Object[] projectDetailsDomain = projectDetailsList.get(i);	
					Object obj1=projectDetailsDomain[1];
					Object obj2=projectDetailsDomain[2];
					ProjectMasterForm projectMasterForm = new ProjectMasterForm();
					projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);
					if(obj1!=null){
					projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
					}
					if(obj2!=null){
					projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
					projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
					}
					projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));
					String strTotalCost=null;
					strTotalCost=CurrencyUtils.convertToINR(projectDetailsDomain[3]);
					projectMasterForm.setStrTotalCost(strTotalCost);
					double roundOfCost = 0.0;
					
				
					projectMasterForm.setStrPLName((String) projectDetailsDomain[4]);
					BigInteger b=new BigInteger(""+projectDetailsDomain[5]);
					projectMasterForm.setProjectId(b.longValue());
					projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
					
					List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoiceByPiD(b.longValue());
					
					projectMasterForm.setBusinessType((String) projectDetailsDomain[6]);
					projectMasterForm.setClientName((String) projectDetailsDomain[7]);
					BigInteger cl=new BigInteger(""+projectDetailsDomain[8]);
					projectMasterForm.setClientId(cl.longValue());
					projectMasterForm.setEncClientId(encryptionService.encrypt(""+cl.longValue()));
					if(projectDetailsDomain[9] != null){
					projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[9]); 
					}
					if(projectDetailsDomain[10] !=null){
						BigInteger el=new BigInteger(""+projectDetailsDomain[10]);
						projectMasterForm.setEndUserId(el.longValue());
					}
					projectMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int) projectMasterForm.getProjectId(), 3));
					list.add(projectMasterForm);
		}
			return list;
		}
		
		@Override
		public List<String> validateProjectDetails(String encProjectId){
			long numId = 0;
			List<String> resultList = new ArrayList<String>();
			String strProjectId = encryptionService.dcrypt(encProjectId);
			numId = Long.parseLong(strProjectId);
			ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(numId);
			String projectStatus = projectMasterDomain.getStrProjectStatus();
			if(null != projectStatus && (projectStatus.equalsIgnoreCase("Ongoing") || projectStatus.equalsIgnoreCase("Completed") || projectStatus.equalsIgnoreCase("Terminated"))){
				resultList.add("-1");
				return resultList;
			}
			
			if(null == projectMasterDomain.getStrProjectName()){
				resultList.add("Project Name required");
			}
			
			if(null == projectMasterDomain.getStrProjectObjective()){
				resultList.add("Project Objective required");
			}
			
			if(null == projectMasterDomain.getDtProjectStartDate()){
				resultList.add("Project Start Date required");
			}
			
			if(null == projectMasterDomain.getDtProjectEndDate()){
				resultList.add("Project End Date required");
			}
			
			return resultList;
		}
		
		
		@Override
		public String submitProjectDetails(long projectId){

			ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(projectId);
			List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByApplicationId(projectMasterDomain.getApplication().getNumId());
			if(list!=null && list.size()>0){
				ProposalMasterDomain domain =list.get(0);
				if (projectMasterDomain.getStrProjectRefNo() == null || projectMasterDomain.getStrProjectRefNo().isEmpty()) {
					String centerCode=domain.getApplication().getGroupMaster().getOrganisationMasterDomain().getStrCode();
					String GroupCode=domain.getApplication().getGroupMaster().getStrCode();
					Date projectStartDt=projectMasterDomain.getDtProjectStartDate();
					Calendar calendar= GregorianCalendar.getInstance();
					calendar.setTime(projectStartDt);
					int fullYear = (calendar.get(Calendar.YEAR));
					int year = (calendar.get(Calendar.YEAR))% 100;				
					String strProjectCount= proposalMasterDao.getYearlyProject(fullYear);
					String projectSeq="";
					String strYear=Integer.toString(year);
					String projectCategory=domain.getApplication().getCategoryMaster().getStrCode();
					String projectType=domain.getApplication().getProjectTypeMaster().getStrCode();
					/*if(strProjectCount==0){
						projectSeq="001";
					}
					else if(strProjectCount>0 && strProjectCount<9){
						projectSeq="00"+String.valueOf(strProjectCount+1);
					}
					else if(strProjectCount>10 && strProjectCount<99){
						projectSeq="0"+String.valueOf(strProjectCount+1);
					}
					else{
						projectSeq=String.valueOf(strProjectCount+1);
					}*/
					/*-----  Project Sequence Number ------------*/
					if (strProjectCount.length() == 1) {
						projectSeq = "00" + (strProjectCount);
					} else if (strProjectCount.length() == 2) {
						projectSeq = "0" + (strProjectCount);
					} else {
						projectSeq = (strProjectCount);
					}
					String proposalCode=centerCode+GroupCode+strYear+projectCategory+projectType+projectSeq+"A";  
					projectMasterDomain.setStrProjectRefNo(proposalCode);
					projectMasterDomain = projectMasterDao.mergeProjectMaster(projectMasterDomain);
				}
			}
			WorkflowModel workflowModel =	workflowService.getLatestTransactionDetails((int)projectId, 3);
			if(null == workflowModel || (null != workflowModel && (workflowModel.getNumLastActionId() == 5 ||workflowModel.getNumLastActionId() == 13))){
				TransactionMasterModel trMasterModel=new TransactionMasterModel();
				trMasterModel.setDtTrDate(new Date());
				trMasterModel.setNumActionId(10);
				trMasterModel.setNumRoleId(3);
				trMasterModel.setNumIsValid(1);
				trMasterModel.setNumWorkflowId(3);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				UserInfo userInfo = (UserInfo) authentication.getPrincipal();
				trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
				trMasterModel.setCustomId((int)projectId);		
				workflowService.insertIntoTransaction(trMasterModel);
				return "success";
			}else{
				return "Activity already initiated";
			}
		}
		
		public String getDetailOfTransaction(long projectId){

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			List<EmployeeRoleMasterModel> allRolesofEmplyee = userInfo.getEmployeeRoleList();
			Set<EmployeeRoleMasterModel> filteredRoles = new HashSet<EmployeeRoleMasterModel>();
			
			int employeeId = userInfo.getEmployeeId();
			
			if(null != allRolesofEmplyee && allRolesofEmplyee.size()>0){
				filteredRoles = allRolesofEmplyee.stream()			      
				        .filter(x -> (3 == x.getNumRoleId() && projectId == x.getNumProjectId())).collect(Collectors.toSet());		
			}
			
			if(filteredRoles.size()==0){
				/*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/
				EmployeeRoleMasterDomain latestPL = employeeRoleMasterDao.getPLStatusByProjectIds((int)projectId);
			    if (latestPL != null && employeeId==latestPL.getNumEmpId()) {
					EmployeeRoleMasterModel employeeRoleMaster = new EmployeeRoleMasterModel();
					filteredRoles.add(employeeRoleMaster);
			    }
		        /*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/
			}
			
			if(filteredRoles.size()==0){
				return "You are not authorised to Initiate Project Closure";
			}
			
			WorkflowModel workflowModel =	workflowService.getLatestTransactionDetails((int)projectId, 4);
			//List<TransactionMasterDomain> list=projectMasterDao.getDetailOfTransaction(projectId);
			if(null == workflowModel || (null != workflowModel && workflowModel.getNumLastActionId()== 17)){
			
				
				return "success";
			}else{
				return "Activity already initiated";
			}
		
		}
		
		@Override
		public String submitProjectClosureInitiateByPL(long projectId){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			List<EmployeeRoleMasterModel> allRolesofEmplyee = userInfo.getEmployeeRoleList();
			Set<EmployeeRoleMasterModel> filteredRoles = new HashSet<EmployeeRoleMasterModel>();
			int employeeId = userInfo.getEmployeeId();
			
			if(null != allRolesofEmplyee && allRolesofEmplyee.size()>0){
				filteredRoles = allRolesofEmplyee.stream()			      
				        .filter(x -> (3 == x.getNumRoleId() && projectId == x.getNumProjectId())).collect(Collectors.toSet());		
			}
			
			if(filteredRoles.size()==0){
				/*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/
				EmployeeRoleMasterDomain latestPL = employeeRoleMasterDao.getPLStatusByProjectIds((int)projectId);
			    if (latestPL != null && employeeId==latestPL.getNumEmpId()) {
					EmployeeRoleMasterModel employeeRoleMaster = new EmployeeRoleMasterModel();
					filteredRoles.add(employeeRoleMaster);
			    }
		        /*------------ If PL Map End Date Elapsed than PL can initiate the Closure [29-05-2024] --------*/
			}
			
			if(filteredRoles.size()==0){
				return "You are not authorised to Initiate Project Closure";
			}
			
			WorkflowModel workflowModel =	workflowService.getLatestTransactionDetails((int)projectId, 4);
			//List<TransactionMasterDomain> list=projectMasterDao.getDetailOfTransaction(projectId);
			if(null == workflowModel || (null != workflowModel && workflowModel.getNumLastActionId()== 17)){
			
				TransactionMasterModel trMasterModel=new TransactionMasterModel();
				trMasterModel.setDtTrDate(new Date());
				trMasterModel.setNumActionId(15);
				trMasterModel.setNumRoleId(3);
				trMasterModel.setNumIsValid(1);
				trMasterModel.setNumWorkflowId(4);
				
				trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
				trMasterModel.setCustomId((int)projectId);	
				
				workflowService.insertIntoTransaction(trMasterModel);
				return "success";
			}else{
				return "Activity already initiated";
			}
		}
		
		@Override
		@PreAuthorize("hasAuthority('UNDER_CLOSURE_PROJECTS')")
		public List<ProjectMasterForm> underClosureProjects(){
			return convertUnderClosureProjectsToModelList(projectMasterDao.getUnderClosureProjects());
		}
		
		public List<ProjectMasterForm> convertUnderClosureProjectsToModelList(List<Object[]>projectDetailsList){
			
			if(projectDetailsList!=null && projectDetailsList.size()>0 ){
			List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
				for(int i=0;i<projectDetailsList.size();i++){			
					Object[] projectDetailsDomain = projectDetailsList.get(i);	
					Object obj1=projectDetailsDomain[1];
					Object obj2=projectDetailsDomain[2];
					ProjectMasterForm projectMasterForm = new ProjectMasterForm();
					projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);
					
					if(obj1!=null){
					projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
					}
					if(obj2!=null){
					projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
					projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
					}
					projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));
					String strTotalCost=null;
					strTotalCost=CurrencyUtils.convertToINR(projectDetailsDomain[3]);
					projectMasterForm.setStrTotalCost(strTotalCost);
					
					//Added the receive amount and its difference for pending payments [12-10-2023]
					double receiveAmt = 0.0;
				    double totalCost = Double.parseDouble(""+projectDetailsDomain[3]);
					try{
						Object obj12=projectDetailsDomain[12];
						if(obj12!=null){
							receiveAmt = Double.parseDouble(""+obj12);
						}
						Object obj13=projectDetailsDomain[13];
						if(obj13!=null){
							double withoutInvoiceReceived = Double.parseDouble(""+projectDetailsDomain[13]);
							receiveAmt += withoutInvoiceReceived;
						}
						if(receiveAmt>0){
							projectMasterForm.setRowColor("");
						}else{
							projectMasterForm.setRowColor("red");	
						}
						projectMasterForm.setNumReceivedAmountInr(CurrencyUtils.convertToINR(receiveAmt));
						// Calculate the difference
						double difference = totalCost - receiveAmt;
						if(difference>=0){
							projectMasterForm.setNumReceivedAmountTemp(CurrencyUtils.convertToINR(""+difference));
						}else{
							double receiveAmt1 = 0.0;
							projectMasterForm.setNumReceivedAmountTemp(CurrencyUtils.convertToINR(""+receiveAmt1));
						}
					}catch(Exception e){
						 
					}
					//added the receive amount and its difference for pending payments [12-10-2023]
				
					projectMasterForm.setStrPLName((String) projectDetailsDomain[4]);
					BigInteger b=new BigInteger(""+projectDetailsDomain[5]);
					projectMasterForm.setProjectId(b.longValue());
					projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
				
					projectMasterForm.setBusinessType((String) projectDetailsDomain[6]);
					projectMasterForm.setClientName((String) projectDetailsDomain[7]);
					BigInteger cl=new BigInteger(""+projectDetailsDomain[8]);
					projectMasterForm.setClientId(cl.longValue());
					projectMasterForm.setEncClientId(encryptionService.encrypt(""+cl.longValue()));
					if(projectDetailsDomain[9] != null){
					projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[9]); 
					}
					if(projectDetailsDomain[10] !=null){
						BigInteger el=new BigInteger(""+projectDetailsDomain[10]);
						projectMasterForm.setEndUserId(el.longValue());
					}
					projectMasterForm.setStrGroupName(""+projectDetailsDomain[11]);
					/*projectMasterForm.setNumRoleId(roleId);*/
					projectMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int) projectMasterForm.getProjectId(), 4));
					list.add(projectMasterForm);
		}
			return list;
			}
			else{
				return null;
			}
		}
		
		@Override
		public ProjectClosureModel getTempProjectMasterModelById(
				long projectId) {
			return convertMasterDomainToModel(projectMasterDao.getTempProjectMasterDataById(projectId));
		}
		
		
		public ProjectClosureModel convertMasterDomainToModel(TempProjectMasterDomain projectMasterDomain){
			if(null != projectMasterDomain){
			ProjectClosureModel projectMasterModel = new ProjectClosureModel();
			
			projectMasterModel.setClosureRemark(projectMasterDomain.getClosureRemarks());
			
			 if(null != projectMasterDomain.getProjectClosureDate()){
				 projectMasterModel.setClosureDate(DateUtils.dateToString(projectMasterDomain.getProjectClosureDate()));
			 }
	
	return projectMasterModel;
			}else{
				return null;
			}
}
		
		@Override
		@PreAuthorize("hasAuthority('READ_COMPLETED_PROJECT')")
		public List<ProjectMasterForm> getAllPendingCompletedProject(){
			List<ProjectMasterForm> projectMasterFormList=new ArrayList<ProjectMasterForm>();
			ProjectMasterForm tempProjectMasterForm= new ProjectMasterForm();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			/*if(userInfo.getSelectedEmployeeRole().getNumRoleId()==2 || userInfo.getSelectedEmployeeRole().getNumRoleId()==3 || userInfo.getSelectedEmployeeRole().getNumRoleId()==4)*/
			//Added by devesh on 08-09-23 to add FInancial PRoject closure list for HOD
			if(userInfo.getSelectedEmployeeRole().getNumRoleId()==2 || userInfo.getSelectedEmployeeRole().getNumRoleId()==3 || userInfo.getSelectedEmployeeRole().getNumRoleId()==4 || userInfo.getSelectedEmployeeRole().getNumRoleId()==15 || userInfo.getSelectedEmployeeRole().getNumRoleId()==0)
			{
				List<Integer> numProjectIds=employeeRoleMasterDao.getProjectIds(userInfo.getEmployeeId());
				List<Long> longs = numProjectIds.stream()
				        .mapToLong(Integer::longValue)
				        .boxed().collect(Collectors.toList());
				projectMasterFormList=convertProjectDetailsforClosureToModelList(projectMasterDao.getPendingCompletedProjectDataByProjectIds(longs));
			}
			else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5 ||  userInfo.getSelectedEmployeeRole().getNumRoleId()==9 )
			{
				List<Integer> numGroupIds=employeeRoleMasterDao.getGroupIds(userInfo.getEmployeeId());
				if(null != numGroupIds && numGroupIds.size() == 1){
					//Load All completed projects
					projectMasterFormList=convertProjectDetailsforClosureToModelList(projectMasterDao.getPendingCompletedProjectData(""+numGroupIds.get(0),userInfo.getSelectedEmployeeRole().getNumRoleId()));
				}else if(null != numGroupIds && numGroupIds.size() > 1){
					//Load Groups
					List<Long> longs = numGroupIds.stream()
					        .mapToLong(Integer::longValue)
					        .boxed().collect(Collectors.toList());
					tempProjectMasterForm.setGroupMasterModelList(groupMasterService.getGroupMasterByIds(longs));
					projectMasterFormList.add(tempProjectMasterForm);
				}
				
			}
			else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==6 || userInfo.getSelectedEmployeeRole().getNumRoleId()==7 )
			{
				Long orgId = Long.valueOf(userInfo.getAssignedOrganisation()).longValue();
				
				tempProjectMasterForm.setGroupMasterModelList(groupMasterService.getAllActiveGrpMasterDomain(orgId));
				projectMasterFormList.add(tempProjectMasterForm);
				
			}
			
			return projectMasterFormList;			
		}
		
		
		public List<ProjectMasterForm> convertProjectDetailsforClosureToModelList(List<Object[]>projectDetailsList){
			List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
				for(int i=0;i<projectDetailsList.size();i++){			
					Object[] projectDetailsDomain = projectDetailsList.get(i);	
					Object obj1=projectDetailsDomain[1];
					Object obj2=projectDetailsDomain[2];
					ProjectMasterForm projectMasterForm = new ProjectMasterForm();
					projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);
					if(obj1!=null){
					projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
					}
					if(obj2!=null){
					projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
					projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
					}
					projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));
					String strTotalCost=null;
					strTotalCost=CurrencyUtils.convertToINR(projectDetailsDomain[3]);
					projectMasterForm.setStrTotalCost(strTotalCost);					
					//[12-10-2023] added the receive amount and its difference for pending payments
					double receiveAmt = 0.0;
					double totalCost = Double.parseDouble(""+projectDetailsDomain[3]);
					try{
						projectMasterForm.setStrGroupName(""+projectDetailsDomain[14]);
						if(projectDetailsDomain[4]!=null){
							receiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);
						}
						//--------- If the amount not map with any invoice -----------------
						Object obj15=projectDetailsDomain[15];
						if(obj15!=null){
							double withoutInvoiceReceived = Double.parseDouble(""+projectDetailsDomain[15]);
							receiveAmt += withoutInvoiceReceived;
						}
						if(receiveAmt>0){
							projectMasterForm.setRowColor("");
						}else{
							projectMasterForm.setRowColor("red");	
						}
						projectMasterForm.setNumReceivedAmountInr(CurrencyUtils.convertToINR(receiveAmt));
						double difference = totalCost - receiveAmt;
						if(difference>=0){
							projectMasterForm.setNumReceivedAmountTemp(CurrencyUtils.convertToINR(""+difference));
						}else{
							double receiveAmt1 = 0.0;
							projectMasterForm.setNumReceivedAmountTemp(CurrencyUtils.convertToINR(""+receiveAmt1));
						}
					}catch(Exception e){	 
					}   
					//[12-10-2023] added the receive amount and its difference for pending payments
					
					/*double roundOfCost = 0.0;
					if(projectDetailsDomain[4]!=null){
						String strReceiveAmt=null;
						
						double recCost = Double.parseDouble(""+projectDetailsDomain[4]);
						roundOfCost = CurrencyUtils.round((recCost/100000),2);
						strReceiveAmt=CurrencyUtils.convertToINR(roundOfCost);
						projectMasterForm.setStrReceivedAmout(strReceiveAmt);
					
					}*/
					/*if(projectDetailsDomain[5]!=null){
						String strReceiveAmtWithoutInvoice=null;
						//strReceiveAmtWithoutInvoice=CurrencyUtils.convertToINR(projectDetailsDomain[5]);
						double roundOfReceiveCost = 0.0;
						double cost = Double.parseDouble(""+projectDetailsDomain[5]);
						if(cost >0){
							
							roundOfReceiveCost = CurrencyUtils.round((cost/100000),2);
							double sumOfAmout=  roundOfCost+roundOfReceiveCost;
							strReceiveAmtWithoutInvoice = CurrencyUtils.convertToINR(sumOfAmout);
							projectMasterForm.setStrReceivedAmout(strReceiveAmtWithoutInvoice);
						}
					
					}*/
					projectMasterForm.setStrPLName((String) projectDetailsDomain[5]);
					BigInteger b=new BigInteger(""+projectDetailsDomain[6]);
					projectMasterForm.setProjectId(b.longValue());
					projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
					List<DocumentTypeMasterDomain> docDetails=documentTypeMasterDao.getAllDocumentByShowOnDashboard();
					List<ProjectDocumentMasterModel> projectDocList=projectDocumentMasterService.getProjectDocumentForDashboard(b.longValue());
					if(null != projectDocList && projectDocList.size()>0){
						projectMasterForm.setProjectDocument(projectDocList);
					}
					List<DocumentTypeMasterDomain> docToBeSearchInProposal= new ArrayList<DocumentTypeMasterDomain>(); 
					if(projectDocList.size()!=docDetails.size()){
						
						
						for(int j=0;j<docDetails.size();j++){
							long docTypeId = docDetails.get(j).getNumId();
						ProjectDocumentMasterModel model = projectDocList.stream()
				                .filter(x -> docTypeId== x.getDocumentTypeId())
				                .findAny()
				                .orElse(null);
						
						if(model==null){
							docToBeSearchInProposal.add(docDetails.get(j));
						}
					}}
					if(docToBeSearchInProposal.size()>0)
					{		long proposalId =  applicationService.getProposalIdByProjectId(b.longValue());
								for(int k=0;k<docToBeSearchInProposal.size();k++){
									
									List<ProjectDocumentMasterModel> proposalDocList=proposalDocumentMasterService.getProposalDocumentForDashboard(proposalId,docToBeSearchInProposal.get(k).getNumId());
									if(proposalDocList.size()>0){
									projectMasterForm.getProjectDocument().addAll(proposalDocList);
									}
								}
								
					}
					
					
					List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoiceByPiD(b.longValue());
					if(null != withoutInvoiceList && withoutInvoiceList.size()>0){
						double roundOfProjectCost = 0.0;
						double totalReceAmt=0.0;
						for(int j=0;j<withoutInvoiceList.size();j++){
							Object[] obj = withoutInvoiceList.get(j);
					
							String strReceivedAmount = null;
							
							
							double projectCost = Double.parseDouble(""+obj[0]);
							if(projectCost >0){
								
								roundOfProjectCost = roundOfProjectCost+CurrencyUtils.round((projectCost/100000),2);
								strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
								
								
								if(projectDetailsDomain[4]!=null){
									String strReceiveAmt=null;
									double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);
									totalReceAmt=totalReceAmt+dbReceiveAmt+projectCost;
									double strAmount = CurrencyUtils.round((totalReceAmt/100000),2);
									strReceiveAmt = CurrencyUtils.convertToINR(strAmount);
									projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
								}
								else{
									projectMasterForm.setStrReceivedAmout(strReceivedAmount);	
								}
							}	
							}			
							}else{
								if(projectDetailsDomain[4]!=null){
									String strReceiveAmt=null;
									double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);								
									double strAmount = CurrencyUtils.round((dbReceiveAmt/100000),2);
									strReceiveAmt = CurrencyUtils.convertToINR(strAmount);							
									projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
								}
							}
					projectMasterForm.setBusinessType((String) projectDetailsDomain[7]);
					projectMasterForm.setClientName((String) projectDetailsDomain[8]);
					BigInteger cl=new BigInteger(""+projectDetailsDomain[9]);
					projectMasterForm.setClientId(cl.longValue());
					projectMasterForm.setEncClientId(encryptionService.encrypt(""+cl.longValue()));
					if(projectDetailsDomain[10] != null){
					projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[10]); 
					}
					if(projectDetailsDomain[11] !=null){
						BigInteger el=new BigInteger(""+projectDetailsDomain[11]);
						projectMasterForm.setEndUserId(el.longValue());
					}
					if(projectDetailsDomain.length==14)
					{
						Date date=(Date) projectDetailsDomain[12];
						if(date!=null)
						{
							projectMasterForm.setClosureDate(DateUtils.dateToString(date));
						}
						Date techClosureDate=(Date) projectDetailsDomain[13];
						if(techClosureDate!=null)
						{
							projectMasterForm.setStrTechClosureDate(DateUtils.dateToString(techClosureDate));
						}
					}
					
					projectMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int)b.longValue(), 4));  //Bhavesh Get Workflow by project id [01-01-2024]
					list.add(projectMasterForm);
		}
			return list;
		}
		
		public List<ProjectMasterForm> getUnderClosure(){
			return convertUnderClosureProjectsToModelList(projectMasterDao.getUnderClosure());
		}
		
		public List<ProjectMasterForm> getUnderClosureforPMO(){
			return convertUnderClosureProjectsToModelList(projectMasterDao.getUnderClosureforPMO());
		}
		
		public List<ProjectMasterForm> getUnderClosureforGCFin(){
			return convertUnderClosureProjectsToModelList(projectMasterDao.getUnderClosureforGCFin());
		}
		
		
		/*--------------------------- Get Project details for download project details excel feature [ added by Anuj ]   ---------------------------------------------------------------------*/
				@Override
				public List<ProjectMasterForm> viewProjectDetailsForExcel(String encGroupId){
					String groupId = "";
					if(null != encGroupId){
						groupId = encryptionService.dcrypt(encGroupId);
					}
					return convertProjectDetailsForExcelToModelList(projectMasterDao.viewProjectDetailsForExcel(groupId));
				}
				
				@Override
				public List<ProjectMasterForm> convertProjectDetailsForExcelToModelList(List<Object[]>projectDetailsList){
					List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
						for(int i=0;i<projectDetailsList.size();i++){			
							Object[] projectDetailsDomain = projectDetailsList.get(i);	
							Object obj1=projectDetailsDomain[1];
							Object obj2=projectDetailsDomain[2];
							ProjectMasterForm projectMasterForm = new ProjectMasterForm();
							projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);
							if(obj1!=null){
							projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
							}
							if(obj2!=null){
							projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
							projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
							}
							projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));
							String strTotalCost=null;
							strTotalCost=CurrencyUtils.convertToINR(projectDetailsDomain[3]);
							projectMasterForm.setStrTotalCost(strTotalCost);
							double roundOfCost = 0.0;
							if(projectDetailsDomain[4]!=null){
								String strReceiveAmt=null;
								
								double recCost = Double.parseDouble(""+projectDetailsDomain[4]);
								roundOfCost = CurrencyUtils.round((recCost/100000),2);
								strReceiveAmt=CurrencyUtils.convertToINR(roundOfCost);
								projectMasterForm.setStrReceivedAmout(strReceiveAmt);
							
							}
						/*if(projectDetailsDomain[5]!=null){
								String strReceiveAmtWithoutInvoice=null;
								//strReceiveAmtWithoutInvoice=CurrencyUtils.convertToINR(projectDetailsDomain[5]);
								double roundOfReceiveCost = 0.0;
								double cost = Double.parseDouble(""+projectDetailsDomain[5]);
								if(cost >0){
									
									roundOfReceiveCost = CurrencyUtils.round((cost/100000),2);
									double sumOfAmout=  roundOfCost+roundOfReceiveCost;
									strReceiveAmtWithoutInvoice = CurrencyUtils.convertToINR(sumOfAmout);
									projectMasterForm.setStrReceivedAmout(strReceiveAmtWithoutInvoice);
								}
							
							}*/
							projectMasterForm.setStrPLName((String) projectDetailsDomain[5]);
							BigInteger b=new BigInteger(""+projectDetailsDomain[6]);
							projectMasterForm.setProjectId(b.longValue());
							projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
							List<DocumentTypeMasterDomain> docDetails=documentTypeMasterDao.getAllDocumentByShowOnDashboard();
							List<ProjectDocumentMasterModel> projectDocList=projectDocumentMasterService.getProjectDocumentForDashboard(b.longValue());
							if(null != projectDocList && projectDocList.size()>0){
								projectMasterForm.setProjectDocument(projectDocList);
							}
							List<DocumentTypeMasterDomain> docToBeSearchInProposal= new ArrayList<DocumentTypeMasterDomain>(); 
							if(projectDocList.size()!=docDetails.size()){
								
								
								for(int j=0;j<docDetails.size();j++){
									long docTypeId = docDetails.get(j).getNumId();
								ProjectDocumentMasterModel model = projectDocList.stream()
						                .filter(x -> docTypeId== x.getDocumentTypeId())
						                .findAny()
						                .orElse(null);
								
								if(model==null){
									docToBeSearchInProposal.add(docDetails.get(j));
								}
							}}
							if(docToBeSearchInProposal.size()>0)
							{		long proposalId =  applicationService.getProposalIdByProjectId(b.longValue());
										for(int k=0;k<docToBeSearchInProposal.size();k++){
											
											List<ProjectDocumentMasterModel> proposalDocList=proposalDocumentMasterService.getProposalDocumentForDashboard(proposalId,docToBeSearchInProposal.get(k).getNumId());
											if(proposalDocList.size()>0){
											projectMasterForm.getProjectDocument().addAll(proposalDocList);
											}
										}
										
							}
							
							
							List<Object[]> withoutInvoiceList = projectPaymentReceivedDao.getPaymentReceivedDetailsWithoutInvoiceByPiD(b.longValue());
							if(null != withoutInvoiceList && withoutInvoiceList.size()>0){
								double roundOfProjectCost = 0.0;
								double totalReceAmt=0.0;
								for(int j=0;j<withoutInvoiceList.size();j++){
									Object[] obj = withoutInvoiceList.get(j);
							
									String strReceivedAmount = null;
									
									
									double projectCost = Double.parseDouble(""+obj[0]);
									if(projectCost >0){
										
										roundOfProjectCost = roundOfProjectCost+CurrencyUtils.round((projectCost/100000),2);
										strReceivedAmount = CurrencyUtils.convertToINR(roundOfProjectCost);
										
										
										if(projectDetailsDomain[4]!=null){
											String strReceiveAmt=null;
											double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);
											totalReceAmt=totalReceAmt+dbReceiveAmt+projectCost;
											double strAmount = CurrencyUtils.round((totalReceAmt/100000),2);
											strReceiveAmt = CurrencyUtils.convertToINR(strAmount);
											projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
										}
										else{
											projectMasterForm.setStrReceivedAmout(strReceivedAmount);	
										}
									}	
									}			
									}else{
										if(projectDetailsDomain[4]!=null){
											String strReceiveAmt=null;
											double dbReceiveAmt = Double.parseDouble(""+projectDetailsDomain[4]);								
											double strAmount = CurrencyUtils.round((dbReceiveAmt/100000),2);
											strReceiveAmt = CurrencyUtils.convertToINR(strAmount);							
											projectMasterForm.setStrReceivedAmout(strReceiveAmt);							
										}
									}
							projectMasterForm.setBusinessType((String) projectDetailsDomain[7]);
							projectMasterForm.setClientName((String) projectDetailsDomain[8]);
							BigInteger cl=new BigInteger(""+projectDetailsDomain[9]);
							projectMasterForm.setClientId(cl.longValue());
							projectMasterForm.setEncClientId(encryptionService.encrypt(""+cl.longValue()));
							if(projectDetailsDomain[10] != null){
							projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[10]); 
							}
							if(projectDetailsDomain[11] !=null){
								BigInteger el=new BigInteger(""+projectDetailsDomain[11]);
								projectMasterForm.setEndUserId(el.longValue());
							}							
							projectMasterForm.setStrHODName((String) projectDetailsDomain[13]);//Added by devesh on 28-11-23 to set HOD name in project master form
							if(projectDetailsDomain.length==14)
							/*if(projectDetailsDomain.length==13)*/
							{
								projectMasterForm.setProjectType(""+projectDetailsDomain[12]);
							}
							list.add(projectMasterForm);
				}
					return list;
				}
				
/*-----------------   Handle Request By HOD [ 21/07/2023 added by_Anuj ]    ----------------------------------------------------------------------------------*/
				@Override
				public String submitProjectDetailsbyHOD(long projectId){			
					WorkflowModel workflowModel =	workflowService.getLatestTransactionDetails((int)projectId, 3);
					if(null == workflowModel || (null != workflowModel && (workflowModel.getNumLastActionId() == 5 ||workflowModel.getNumLastActionId() == 13))){
						TransactionMasterModel trMasterModel=new TransactionMasterModel();
						trMasterModel.setDtTrDate(new Date());
						trMasterModel.setNumActionId(26);
						trMasterModel.setNumRoleId(3);
						trMasterModel.setNumIsValid(1);
						trMasterModel.setNumWorkflowId(3);
						Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
						UserInfo userInfo = (UserInfo) authentication.getPrincipal();
						trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
						trMasterModel.setCustomId((int)projectId);	
						
						workflowService.insertIntoTransaction(trMasterModel);
						return "success";
					}else{
						return "Activity already initiated";
					}
				}
				
				@Override
				public String ProjectReferenceNumberbyHOD(long projectId)
				{		
					ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(projectId);
					List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByApplicationId(projectMasterDomain.getApplication().getNumId());
					if(list!=null && list.size()>0){
						ProposalMasterDomain domain =list.get(0);
						 if (projectMasterDomain.getStrProjectRefNo() == null || projectMasterDomain.getStrProjectRefNo().isEmpty()) {
							String centerCode=domain.getApplication().getGroupMaster().getOrganisationMasterDomain().getStrCode();
							String GroupCode=domain.getApplication().getGroupMaster().getStrCode();
							Date projectStartDt=projectMasterDomain.getDtProjectStartDate();
							Calendar calendar= GregorianCalendar.getInstance();
							calendar.setTime(projectStartDt);
							int fullYear = (calendar.get(Calendar.YEAR));
							int year = (calendar.get(Calendar.YEAR))% 100;				
							String strProjectCount= proposalMasterDao.getYearlyProject(fullYear);
							String projectSeq="";
							String strYear=Integer.toString(year);
							String projectCategory=domain.getApplication().getCategoryMaster().getStrCode();
							String projectType=domain.getApplication().getProjectTypeMaster().getStrCode();
		
							if (strProjectCount.length() == 1) {
								projectSeq = "00" + (strProjectCount);
							} else if (strProjectCount.length() == 2) {
								projectSeq = "0" + (strProjectCount);
							} else {
								projectSeq = (strProjectCount);
							}
							String proposalCode=centerCode+GroupCode+strYear+projectCategory+projectType+projectSeq+"A";  
							projectMasterDomain.setStrProjectRefNo(proposalCode);
							projectMasterDomain = projectMasterDao.mergeProjectMaster(projectMasterDomain);
							return "success";
						}else{
							return "Activity already initiated";
						}
					}else{
						return "Activity already initiated";
					}
				}
				
/*-----------------   Handle Request By GC [ 21/07/2023 added by_Anuj ]    ----------------------------------------------------------------------------------*/
				@Override
				public String submitProjectDetailsbyGC(long projectId){						
					ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(projectId);
					List<ProposalMasterDomain> list=proposalMasterDao.getProposalMasterByApplicationId(projectMasterDomain.getApplication().getNumId());
					if(list!=null && list.size()>0){
						ProposalMasterDomain domain =list.get(0);
						 if (projectMasterDomain.getStrProjectRefNo() == null || projectMasterDomain.getStrProjectRefNo().isEmpty()) {
							String centerCode=domain.getApplication().getGroupMaster().getOrganisationMasterDomain().getStrCode();
							String GroupCode=domain.getApplication().getGroupMaster().getStrCode();
							Date projectStartDt=projectMasterDomain.getDtProjectStartDate();
							Calendar calendar= GregorianCalendar.getInstance();
							calendar.setTime(projectStartDt);
							int fullYear = (calendar.get(Calendar.YEAR));
							int year = (calendar.get(Calendar.YEAR))% 100;				
							String strProjectCount= proposalMasterDao.getYearlyProject(fullYear);
							String projectSeq="";
							String strYear=Integer.toString(year);
							String projectCategory=domain.getApplication().getCategoryMaster().getStrCode();
							String projectType=domain.getApplication().getProjectTypeMaster().getStrCode();
		
							if (strProjectCount.length() == 1) {
								projectSeq = "00" + (strProjectCount);
							} else if (strProjectCount.length() == 2) {
								projectSeq = "0" + (strProjectCount);
							} else {
								projectSeq = (strProjectCount);
							}
							String proposalCode=centerCode+GroupCode+strYear+projectCategory+projectType+projectSeq+"A";  
							projectMasterDomain.setStrProjectRefNo(proposalCode);
							projectMasterDomain = projectMasterDao.mergeProjectMaster(projectMasterDomain);
						 }
					}			
					WorkflowModel workflowModel =	workflowService.getLatestTransactionDetails((int)projectId, 3);
					if(null == workflowModel || (null != workflowModel && (workflowModel.getNumLastActionId() == 5 ||workflowModel.getNumLastActionId() == 13))){
						TransactionMasterModel trMasterModel=new TransactionMasterModel();
						trMasterModel.setDtTrDate(new Date());
						trMasterModel.setNumActionId(6);
						trMasterModel.setNumRoleId(5);
						trMasterModel.setNumIsValid(1);
						trMasterModel.setNumWorkflowId(3);
						Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
						UserInfo userInfo = (UserInfo) authentication.getPrincipal();
						trMasterModel.setNumTrUserId(userInfo.getEmployeeId());
						trMasterModel.setCustomId((int)projectId);	
						
						workflowService.insertIntoTransaction(trMasterModel);
						return "success";
					}else{
						return "Activity already initiated";
					}
				}
				
/*---------- Get Under closure projects list without role ids [ 29-08-2023 ] -----------------------------------------------------------*/
				public List<ProjectMasterForm> underClosureProjectsList(){
					return convertUnderClosureProjectsListToModelList(projectMasterDao.getUnderClosureList());
				}
				public List<ProjectMasterForm> convertUnderClosureProjectsListToModelList(List<Object[]> projectDetailsList){			
						if(projectDetailsList!=null && projectDetailsList.size()>0 ){
							List<ProjectMasterForm> list = new ArrayList<ProjectMasterForm>();
							for(int i=0;i<projectDetailsList.size();i++){			
								Object[] projectDetailsDomain = projectDetailsList.get(i);	
								Object obj1=projectDetailsDomain[1];
								Object obj2=projectDetailsDomain[2];
								ProjectMasterForm projectMasterForm = new ProjectMasterForm();
								projectMasterForm.setStrProjectName((String) projectDetailsDomain[0]);						
								if(obj1!=null){
								projectMasterForm.setStartDate(DateUtils.dateToString((Date) projectDetailsDomain[1]));
								}
								if(obj2!=null){
								projectMasterForm.setEndDate(DateUtils.dateToString((Date) projectDetailsDomain[2]));
								projectMasterForm.setDtEndDate((Date)projectDetailsDomain[2]);
								}
								projectMasterForm.setProjectDuration(DateUtils.getDurationInMonths(((Date)projectDetailsDomain[1]), ((Date)projectDetailsDomain[2])));

								BigInteger b=new BigInteger(""+projectDetailsDomain[3]);
								projectMasterForm.setProjectId(b.longValue());
								projectMasterForm.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
								projectMasterForm.setBusinessType((String) projectDetailsDomain[4]);
								if(projectDetailsDomain[5] != null){
								projectMasterForm.setProjectRefrenceNo((String)projectDetailsDomain[5]); 
								}
								projectMasterForm.setStrGroupName(""+projectDetailsDomain[6]);
								projectMasterForm.setWorkflowModel(workflowService.getLatestTransactionDetail((int) projectMasterForm.getProjectId(), 4));
								
								String actionPerformed = projectMasterForm.getWorkflowModel().getStrActionPerformed();
								long numLastRoleId = projectMasterForm.getWorkflowModel().getNumLastRoleId();
								String projectName = projectMasterForm.getStrProjectName();
								if ("Sent back to PM".equalsIgnoreCase(actionPerformed) && numLastRoleId == 5) {
									continue;
								}else{
								    list.add(projectMasterForm);
								}
							}
							return list;
						}else{
							return null;
						}
				}
				
				/*------------------------- Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/	
				@Override
				public List<ProjectMasterForm> getAllFinancialClosurePendingProjectByDate(ProjectMasterForm model){
					String strStartDate = model.getStartDate();
					String strEndDate = model.getEndDate();
					Date startRange = null;
					Date endRange = null;
					if(null != strStartDate && !strStartDate.equals("")){
						try {
							startRange = DateUtils.dateStrToDate(strStartDate);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
					}else{
						try {
							startRange = DateUtils.dateStrToDate("01/01/2018");
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
					}
					
					if(null != strEndDate && !strEndDate.equals("")){
						try {
							endRange = DateUtils.dateStrToDate(strEndDate);
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
					}else{
						endRange = new Date();
					}
					List<ProjectMasterForm> projectMasterFormList=new ArrayList<ProjectMasterForm>();
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					UserInfo userInfo = (UserInfo) authentication.getPrincipal();
					if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5 ||  userInfo.getSelectedEmployeeRole().getNumRoleId()==9 ||  userInfo.getSelectedEmployeeRole().getNumRoleId()==6 )
					{
						int roleId=userInfo.getSelectedEmployeeRole().getNumRoleId();
						List<Integer> numGroupIds=employeeRoleMasterDao.getGroupIds(userInfo.getEmployeeId());
						if(roleId==6 || roleId==9){
							projectMasterFormList=convertProjectDetailsforClosureToModelList(projectMasterDao.getAllFinancialClosurePendingProjectByDate("0",userInfo.getSelectedEmployeeRole().getNumRoleId(),startRange,endRange));
						}else{
							projectMasterFormList=convertProjectDetailsforClosureToModelList(projectMasterDao.getAllFinancialClosurePendingProjectByDate(""+numGroupIds.get(0),userInfo.getSelectedEmployeeRole().getNumRoleId(),startRange,endRange));
						}
					}
					return projectMasterFormList;
				}
				/*------------------------- EOF Get All Financial Closure Pending Projects [12-10-2023]-------------------------------*/	

				/*--------- Service function for get Projects Details By Group Id [18-03-2024] -------------*/
				public List<ProjectMasterModel> getProjectNameByGroupId(long groupId){		
				    List<ProjectMasterModel> projectList = new ArrayList<>();
				    List<ProjectMasterDomain> projectMasterDomainList = projectMasterDao.getProjectNameByGroupId(groupId);
				    if (projectMasterDomainList != null) {
				        projectList = convertProjectMasterDomainToModelList(projectMasterDomainList);
				    }
				    return projectList;
				}
				/*--------- End of Service function for get Projects Details By Group Id [18-03-2024] -------------*/
}