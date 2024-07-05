package in.pms.master.service;

import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.MilestoneReviewMasterDao;
import in.pms.master.dao.MilestoneTypeMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProjectMilestoneDao;
import in.pms.master.domain.MilestoneActivityDomain;
import in.pms.master.domain.MilestoneTypeMaster;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProjectMilestoneReviewMaster;
import in.pms.master.domain.ProjectModuleMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProjectModuleMasterModel;
import in.pms.transaction.domain.Application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class ProjectMilestoneServiceImpl implements ProjectMilestoneService{
	
	@Autowired
	ProjectMilestoneDao projectMilestoneDao;
	@Autowired
	ProjectMasterDao projectMasterDao;
	@Autowired
	ProjectModuleMasterService projectModuleMasterService;
	
	@Autowired
	MilestoneReviewService milestoneReviewService;
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	MilestoneReviewMasterDao milestoneReviewMasterDao;
	
	@Autowired
	MilestoneTypeMasterDao milestoneTypeMasterDao;
	
	
	@Override
	public List<ProjectMilestoneForm> getAllProjectMilestoneData(){
		return null;
		//return convertProjectMilestoneDomainToModelList(projectMilestoneDao.getAllProjectMilestoneData(),projectMasterdao.getAllActiveProjectMasterData());			
	}
	
	
	public List<ProjectMilestoneDomain> getMilestoneDataByProjectId(long projectId){
		return projectMilestoneDao.getMilestoneDataByProjectId(projectId);
	}
	
	@Override
	public ProjectMilestoneDomain getProjectMilestoneDataByMilestoneId(long milestoneId){
		return projectMilestoneDao.getOne(milestoneId);
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneByProjectId(long projectId){
		return convertProjectMilestoneDomainToModelList(projectMilestoneDao.getMilestoneDataByProjectId(projectId));
	}
	
	
	
	public List<MilestoneActivityModel> convertProjectModuleMasterDomainToModelList(List<ProjectModuleMasterDomain> projectModuleMasterList,List<MilestoneActivityDomain> milestoneModuleList ){
		List<MilestoneActivityModel> list = new ArrayList<MilestoneActivityModel>();
		for(int i=0;i<projectModuleMasterList.size();i++){
			ProjectModuleMasterDomain projectModuleMasterDomain = projectModuleMasterList.get(i);
			MilestoneActivityModel milestoneModuleModel = new MilestoneActivityModel();
			milestoneModuleModel.setStrModuleName(projectModuleMasterDomain.getStrModuleName());
			list.add(milestoneModuleModel);
}
		for(int j=0;j<milestoneModuleList.size();j++){
			MilestoneActivityDomain milestoneModuleDomain = milestoneModuleList.get(j);
			MilestoneActivityModel milestoneModuleModel = new MilestoneActivityModel();
			milestoneModuleModel.setNumId(milestoneModuleDomain.getNumId());
			/*milestoneModuleModel.setStartDate(milestoneModuleDomain.getStartDate());
			milestoneModuleModel.setEndDate(milestoneModuleDomain.getEndDate());*/
			milestoneModuleModel.setDescription(milestoneModuleDomain.getStrDescription());
			list.add(milestoneModuleModel);

		}
	return list;
	}

	public List<MilestoneActivityModel> convertProjectMilestoneActivityDomainToModelList(List<MilestoneActivityDomain> milestoneDataList){
		List<MilestoneActivityModel> list = new ArrayList<MilestoneActivityModel>();
			for(int i=0;i<milestoneDataList.size();i++){
				MilestoneActivityDomain milestoneActivityDomain = milestoneDataList.get(i);
				MilestoneActivityModel milestoneActivityModel = new MilestoneActivityModel();
				milestoneActivityModel.setDescription(milestoneActivityDomain.getStrDescription());
				if(milestoneActivityDomain.getWithModule()==1){
					ProjectModuleMasterModel projectModuleMasterModel = projectModuleMasterService.getProjectModuleMasterDomainById(milestoneActivityDomain.getModuleId());
					milestoneActivityModel.setStrModuleName(projectModuleMasterModel.getStrModuleName());
					milestoneActivityModel.setStrModuleDescription(projectModuleMasterModel.getStrModuleDescription());
				}
				
				if(milestoneActivityDomain.getStartDate() !=null){
				milestoneActivityModel.setStartDate(DateUtils.dateToString(milestoneActivityDomain.getStartDate()));
				}
				if(milestoneActivityDomain.getEndDate() !=null){
				milestoneActivityModel.setEndDate(DateUtils.dateToString(milestoneActivityDomain.getEndDate()));
				}
				list.add(milestoneActivityModel);
	}
			
			
		return list;
	}
	
	public List<ProjectMilestoneForm> convertProjectMilestoneDomainToModelList(List<ProjectMilestoneDomain> milestoneDataList){
		List<ProjectMilestoneForm> list = new ArrayList<ProjectMilestoneForm>();
			for(int i=0;i<milestoneDataList.size();i++){
				ProjectMilestoneDomain projectMilestoneDomain = milestoneDataList.get(i);
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneDomain.getMilestoneName());
				projectMilestoneForm.setMilestoneTypeId(projectMilestoneDomain.getNumMilestoneTypeId());
				MilestoneTypeMaster data=milestoneTypeMasterDao.getMilestoneTypeDataById(projectMilestoneForm.getMilestoneTypeId());
				if(data!=null){
					projectMilestoneForm.setMilestoneTypeName(data.getStrMilestoneTypeName());
				}
				projectMilestoneForm.setStrDesription(projectMilestoneDomain.getDescription());
				projectMilestoneForm.setNumId(projectMilestoneDomain.getNumId());
				projectMilestoneForm.setEncMilestsoneId(encryptionService.encrypt(""+projectMilestoneDomain.getNumId()));
				projectMilestoneForm.setExpectedStartDate(DateUtils.dateToString(projectMilestoneDomain.getExpectedStartDate()));
				projectMilestoneForm.setGroupName(projectMilestoneDomain.getProjectMasterDomain().getApplication().getGroupMaster().getGroupName());
				projectMilestoneForm.setStrProjectName(projectMilestoneDomain.getProjectMasterDomain().getStrProjectName());
				projectMilestoneForm.setStrStatus(projectMilestoneDomain.getStrStatus());
				try{
				projectMilestoneForm.setProjectStartDate(DateUtils.dateToString(projectMilestoneDomain.getProjectMasterDomain().getDtProjectStartDate()));
				projectMilestoneForm.setProjectEndDate(DateUtils.dateToString(projectMilestoneDomain.getProjectMasterDomain().getDtProjectEndDate()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				String tempStatus=  projectMilestoneDomain.getStrStatus();
				if(null !=tempStatus &&  tempStatus.equals("Not Completed")){
					List<ProjectMilestoneReviewMaster> milestoneReviewDataList=milestoneReviewMasterDao.getMilestoneReviewDataByMilestoneId(projectMilestoneDomain.getNumId());				
					if(null != milestoneReviewDataList && milestoneReviewDataList.size()>0)
					{
						ProjectMilestoneReviewMaster projectMilestoneReviewMaster = milestoneReviewDataList.get(0);
						projectMilestoneForm.setThisDate(DateUtils.dateToString(projectMilestoneReviewMaster.getCompletionDate()));
						
					}
				}else if(null !=tempStatus && tempStatus.equals("Completed")){
					projectMilestoneForm.setThisDate(DateUtils.dateToString(projectMilestoneDomain.getCompletionDate()));
				}
				
				
				
				
				if(projectMilestoneDomain.getNumIsValid()==1){
					projectMilestoneForm.setValid(true);
				}else{
					projectMilestoneForm.setValid(false);
				}
				list.add(projectMilestoneForm);
	}
			
			
		return list;
	}
	
	@Override
	public String checkDuplicateProjectMilestoneData(ProjectMilestoneForm projectMilestoneForm){
		String result = "";
		ProjectMilestoneDomain projectMilestoneDomain = projectMilestoneDao.getProjectMilestoneByName(projectMilestoneForm.getMilestoneName(),projectMilestoneForm.getProjectId());
	
		 if(null == projectMilestoneDomain){
			return null; 
		 }else if(projectMilestoneForm.getNumId() != 0){
			 if(projectMilestoneDomain.getNumId() == projectMilestoneForm.getNumId()){
				 return null; 
			 }else{
				 result = "Project Milestone with same name already exist with Id "+projectMilestoneDomain.getNumId();
			 }
		 }
		 else{
				if(projectMilestoneDomain.getNumIsValid() == 0){
					result = "Project Milestone Already Registered with Id "+projectMilestoneDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Milestone Already Registered with Id "+projectMilestoneDomain.getNumId();
				}			
			 }
		 System.out.println("aq1qjk "+result);
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECTMILESTONE_MST')")
	public long saveProjectMilestoneData(ProjectMilestoneForm projectMilestoneForm){
		ProjectMilestoneDomain projectMilestoneDomain = convertProjectMilestoneModelToDomain(projectMilestoneForm);
		return projectMilestoneDao.save(projectMilestoneDomain).getNumId();
	}
	
	public ProjectMilestoneDomain convertProjectMilestoneModelToDomain(ProjectMilestoneForm projectMilestoneForm){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectMilestoneDomain projectMilestoneDomain = new ProjectMilestoneDomain();
		if(projectMilestoneForm.getNumId()!=0){
			projectMilestoneDomain =  projectMilestoneDao.getOne(projectMilestoneForm.getNumId());
		}
		
		ProjectMasterDomain projectMasterDomain =projectMasterDao.getProjectMasterDataById(projectMilestoneForm.getProjectId());
		projectMilestoneDomain.setProjectMasterDomain(projectMasterDomain);
	    projectMilestoneDomain.setMilestoneName(projectMilestoneForm.getMilestoneName());
	    projectMilestoneDomain.setNumMilestoneTypeId(projectMilestoneForm.getMilestoneTypeId());
	    projectMilestoneDomain.setDescription(projectMilestoneForm.getStrDesription());
	   /* if(projectMilestoneForm.getNumId() == 0){*/
			if(null != projectMilestoneForm.getExpectedStartDate() && !projectMilestoneForm.getExpectedStartDate().equals("")){
				try {
					projectMilestoneDomain.setExpectedStartDate(DateUtils.dateStrToDate(projectMilestoneForm.getExpectedStartDate()));
				} catch (ParseException e) {				
					e.printStackTrace();
				}
			}
			
			//Bhavesh(22-02-2024) commented the else part as we want to edit the date
	    /*}else{
			String allowedRoles = ResourceBundleFile.getValueFromKey("EditBaselineDateForMilestone");

			List<EmployeeRoleMasterModel> roles = userInfo.getEmployeeRoleList();	
			boolean dateEditFlag = false;
			String[] tempRole = allowedRoles.split(",");
			
			for(int i=0;i<tempRole.length;i++){
				int role = Integer.parseInt(tempRole[i]);
				
				EmployeeRoleMasterModel model = roles.stream()
		                .filter(x -> role == x.getNumRoleId())
		                .findAny()
		                .orElse(null);
				
				if(null != model){
					dateEditFlag= true;
					break;
				}			
			}
			
			if(dateEditFlag){
				if(null != projectMilestoneForm.getExpectedStartDate() && !projectMilestoneForm.getExpectedStartDate().equals("")){
					try {
						projectMilestoneDomain.setExpectedStartDate(DateUtils.dateStrToDate(projectMilestoneForm.getExpectedStartDate()));
					} catch (ParseException e) {				
						e.printStackTrace();
					}
				}
			}
	    	
	    }
		*/
		projectMilestoneDomain.setDtTrDate(new Date());
		projectMilestoneDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		/*---------------- Add Condition For Delete or Add Milestone Records [23-10-2023] --------------------------*/
		if(projectMilestoneForm.isValid()){
			projectMilestoneDomain.setNumIsValid(2);
		}else{
			projectMilestoneDomain.setNumIsValid(1);
		}	
		
		
		//projectMilestoneDomain.setMilestoneActivityDomains(milestoneActivityDomains);
		return projectMilestoneDomain;
	}

	
	public List<ProjectMilestoneForm> getAllProjectMilestone(){
		return convertMilestoneMasterDomainToModelList(projectMilestoneDao.getAllProjectMilestoneData());			
	}
	
	public List<ProjectMilestoneForm> convertMilestoneMasterDomainToModelList(List<ProjectMilestoneDomain> milestoneDataList){
		List<ProjectMilestoneForm> list = new ArrayList<ProjectMilestoneForm>();
			for(int i=0;i<milestoneDataList.size();i++){
				ProjectMilestoneDomain projectMilestoneDomain = milestoneDataList.get(i);
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneDomain.getMilestoneName());
				projectMilestoneForm.setExpectedStartDate(DateUtils.dateToString(projectMilestoneDomain.getExpectedStartDate()));
				projectMilestoneForm.setNumId(projectMilestoneDomain.getNumId());
				if(projectMilestoneDomain.getNumIsValid()==1){
					projectMilestoneForm.setValid(true);
				}else{
					projectMilestoneForm.setValid(false);
				}
				
				list.add(projectMilestoneForm);
	}
		return list;
	}
	@PreAuthorize("hasAuthority('READ_MILESTONE_REVIEW_MST')")
	public ProjectMilestoneForm getMilestoneDataByMilestoneId(long milestoneId){
		return convertMilestoneDomainToModel(projectMilestoneDao.getOne(milestoneId));			
	}
	
	public ProjectMilestoneForm convertMilestoneDomainToModel(ProjectMilestoneDomain milestoneData){
				ProjectMilestoneDomain projectMilestoneDomain = milestoneData;
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneDomain.getMilestoneName());
				projectMilestoneForm.setStrProjectName(projectMilestoneDomain.getProjectMasterDomain().getStrProjectName());
				projectMilestoneForm.setClientName(projectMilestoneDomain.getProjectMasterDomain().getApplication().getClientMaster().getClientName());
				projectMilestoneForm.setExpectedStartDate(DateUtils.dateToString(projectMilestoneDomain.getExpectedStartDate()));
				projectMilestoneForm.setNumId(projectMilestoneDomain.getNumId());
				projectMilestoneForm.setProjectStartDate(DateUtils.dateToString(projectMilestoneDomain.getProjectMasterDomain().getDtProjectStartDate()));
				projectMilestoneForm.setProjectEndDate(DateUtils.dateToString(projectMilestoneDomain.getProjectMasterDomain().getDtProjectEndDate()));
				if(projectMilestoneDomain.getNumIsValid()==1){
					projectMilestoneForm.setValid(true);
				}else{
					projectMilestoneForm.setValid(false);
				}
				projectMilestoneForm.setStrDesription(projectMilestoneDomain.getDescription());
		return projectMilestoneForm;
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')")
	public List<ProjectMilestoneForm> getMilestoneReviewDetail(){
		
		List<ProjectMilestoneForm>  milestoneReviews= milestoneReviewService.getMilestoneRevData();
		return milestoneReviews;
		
	}
	
	

	@Override
	@PreAuthorize("hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')")
	public List<ProjectMilestoneForm> getMilestoneReviewDetail(int days){		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRole = userInfo.getSelectedEmployeeRole();
		int roleId = selectedRole.getNumRoleId();		
		String assignedProjects = userInfo.getAssignedProjects();
		
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();
		calendar.add(Calendar.DATE, days);
		Date endDate=calendar.getTime();
		List<ProjectMilestoneForm>  milestoneReviews =new ArrayList<ProjectMilestoneForm>();
		List<ProjectMilestoneDomain>  milestones =new ArrayList<ProjectMilestoneDomain>();
		
		if(roleId == 6 || roleId == 7 || roleId == 8 || roleId == 9){						
			List<Long> orgList =new ArrayList<Long>();
			orgList.add((long) selectedRole.getNumOrganisationId());
			 milestoneReviews = milestoneReviewService.getMilestoneReviewDataByNoOfDaysForOrg(days,orgList);		
			  milestones = projectMilestoneDao.getMilestoneReviewDataByNoOfDaysForOrg(startDate,endDate,orgList);
		}else if(roleId == 5){
			//Group Specific Data Loading
			
			
			List<Long> groupList = new ArrayList<Long>();
			groupList.add((long) selectedRole.getNumGroupId());		
			 milestoneReviews = milestoneReviewService.getMilestoneReviewDataByNoOfDaysForGrp(days,groupList);		
			  milestones = projectMilestoneDao.getMilestoneReviewDataByNoOfDaysForGrp(startDate,endDate,groupList);
		}else{
		
		if(null != assignedProjects && !(assignedProjects.contains("0") && assignedProjects.length()==1)){
			//Project Specific Data Loading
			String[] tempProjects = assignedProjects.split(",");
			List<String> tempList = Arrays.asList(tempProjects);
			
			List<Long> projectList = tempList.stream().map(Long::valueOf).collect(Collectors.toList());
			
			 milestoneReviews = milestoneReviewService.getMilestoneReviewDataByNoOfDays(days,projectList);		
			  milestones = projectMilestoneDao.getMilestoneDataByNoOfDays(startDate,endDate,projectList);
		}
	}	
		 
		for(int i=0;i<milestones.size();i++){
			ProjectMilestoneDomain temp = milestones.get(i);
			
			ProjectMilestoneForm tempForm = milestoneReviews.stream()
		                .filter(x -> temp.getNumId() == x.getMilestoneId()) 
		                .findAny()
		                .orElse(null); 
			
			if(null == tempForm){
				milestoneReviews.add(convertProjectMilestoneDomainToModel(temp));
			}
		}		
		
		return milestoneReviews;	
	}
	
	
	
	public ProjectMilestoneForm convertProjectMilestoneDomainToModel(ProjectMilestoneDomain projectMilestoneDomain){			
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneDomain.getMilestoneName());
				projectMilestoneForm.setNumId(projectMilestoneDomain.getNumId());
				projectMilestoneForm.setExpectedStartDate(DateUtils.dateToString(projectMilestoneDomain.getExpectedStartDate()));
				ProjectMasterDomain projectMasterDomain = projectMilestoneDomain.getProjectMasterDomain();
				Application application = projectMasterDomain.getApplication();
				projectMilestoneForm.setGroupName(application.getGroupMaster().getGroupName());
				projectMilestoneForm.setStrProjectName(projectMasterDomain.getStrProjectName());
				projectMilestoneForm.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				projectMilestoneForm.setStrProjectReference(projectMasterDomain.getStrProjectRefNo());
				projectMilestoneForm.setClientName(application.getClientMaster().getClientName());
				if(projectMilestoneDomain.getNumIsValid()==1){
					projectMilestoneForm.setValid(true);
				}else{
					projectMilestoneForm.setValid(false);
				}
				projectMilestoneForm.setMilestoneId(projectMilestoneDomain.getNumId());
				projectMilestoneForm.setEncMilestsoneId(encryptionService.encrypt(""+projectMilestoneDomain.getNumId()));
				return projectMilestoneForm;
			}
	
	
	
	@Override
	@PreAuthorize("hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')")
	public List<ProjectMilestoneForm> MilestoneExceededDeadlineDetailByDates(String expComplDate,String symbol){
		
		List<ProjectMilestoneForm>  milestoneReviews =new ArrayList<ProjectMilestoneForm>();
		
		milestoneReviews = milestoneReviewService.getMilestoneRevDataByDates(expComplDate,symbol);
		return milestoneReviews;
		
	}
}
