package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.MilestoneReviewMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProjectMilestoneReviewMaster;
import in.pms.master.model.MilestoneReviewModel;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.transaction.domain.Application;
import in.pms.transaction.model.MonthlyProgressModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class MilestoneReviewServiceImpl implements MilestoneReviewService{
	
	@Autowired
	MilestoneReviewMasterDao milestoneReviewMasterDao;
	@Autowired
	ProjectMilestoneService projectMilestoneService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
		
	@Override
	//@PreAuthorize("hasAuthority('READ_Milestone_Review_MST')")
	public List<MilestoneReviewModel> getAllMilestoneReviewData(){
		return convertMilestoneReviewMasterDomainToModelList(milestoneReviewMasterDao.getAllProjectMilestoneReviewDomain());			
	}
	
	@Override
	public List<MilestoneReviewModel> getActiveMilestoneReviewData(){
		return convertMilestoneReviewMasterDomainToModelList(milestoneReviewMasterDao.getAllActiveProjectMilestoneReviewDomain());			
	}
	@PreAuthorize("hasAuthority('READ_MILESTONE_REVIEW_MST')")
	public List<MilestoneReviewModel> getMilestoneReviewDataByMilestoneId(long milestoneId){
		return convertMilestoneReviewMasterDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataByMilestoneId(milestoneId));			
	}

	public List<MilestoneReviewModel> convertMilestoneReviewMasterDomainToModelList(List<ProjectMilestoneReviewMaster> milestoneReviewDataList){
		List<MilestoneReviewModel> list = new ArrayList<MilestoneReviewModel>();
			for(int i=0;i<milestoneReviewDataList.size();i++){
				ProjectMilestoneReviewMaster projectMilestoneReviewMaster = milestoneReviewDataList.get(i);
				MilestoneReviewModel milestoneReviewModel = new MilestoneReviewModel();
				milestoneReviewModel.setReviewDate(DateUtils.dateToString(projectMilestoneReviewMaster.getReviewDate()));
				milestoneReviewModel.setCompletionDate(DateUtils.dateToString(projectMilestoneReviewMaster.getCompletionDate()));
				milestoneReviewModel.setStrRemarks(projectMilestoneReviewMaster.getStrRemarks());
				milestoneReviewModel.setProjectName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getProjectMasterDomain().getStrProjectName());
				milestoneReviewModel.setClientName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getProjectMasterDomain().getApplication().getClientMaster().getClientName());
				milestoneReviewModel.setStrMilestoneName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getMilestoneName());
				milestoneReviewModel.setNumId(projectMilestoneReviewMaster.getNumId());
				milestoneReviewModel.setGroupName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getProjectMasterDomain().getApplication().getGroupMaster().getGroupName());
				if(projectMilestoneReviewMaster.getNumIsValid()==1){
					milestoneReviewModel.setValid(true);
				}else{
					milestoneReviewModel.setValid(false);
				}
				
				/*if(projectMilestoneReviewMaster.getProjectMilestoneDomain().getStrStatus().equals("Completed")){
					milestoneReviewModel.setStatus(true);
				}else{
					milestoneReviewModel.setStatus(false);
				}*/
				
				list.add(milestoneReviewModel);
	}
		return list;
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_MILESTONE_REVIEW_MST')")
        public long saveMilestoneReviewData(MilestoneReviewModel milestoneReviewModel){
		ProjectMilestoneReviewMaster projectMilestoneReviewMaster = convertMilestoneReviewMasterModelToDomain(milestoneReviewModel);
		return milestoneReviewMasterDao.save(projectMilestoneReviewMaster).getNumId();
	}
	
	public ProjectMilestoneReviewMaster convertMilestoneReviewMasterModelToDomain(MilestoneReviewModel milestoneReviewModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectMilestoneReviewMaster projectMilestoneReviewMaster = new ProjectMilestoneReviewMaster();
		if(milestoneReviewModel.getNumId()!=0){		
			projectMilestoneReviewMaster =  milestoneReviewMasterDao.getAllMilestoneReviewByReviewId(milestoneReviewModel.getNumId());
		}
		try {
			projectMilestoneReviewMaster.setReviewDate(DateUtils.dateStrToDate(milestoneReviewModel.getReviewDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			projectMilestoneReviewMaster.setCompletionDate(DateUtils.dateStrToDate(milestoneReviewModel.getCompletionDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projectMilestoneReviewMaster.setStrRemarks(milestoneReviewModel.getStrRemarks());
		projectMilestoneReviewMaster.setDtTrDate(new Date());
		projectMilestoneReviewMaster.setNumTrUserId(userInfo.getEmployeeId());
		ProjectMilestoneDomain projectMilestoneDomain=projectMilestoneService.getProjectMilestoneDataByMilestoneId(milestoneReviewModel.getMilestoneId());
		projectMilestoneReviewMaster.setProjectMilestoneDomain(projectMilestoneDomain);
		projectMilestoneReviewMaster.setNumIsValid(1);
		if(milestoneReviewModel.isStatus()){
			projectMilestoneDomain.setStrStatus("Completed");
			try {
				projectMilestoneDomain.setCompletionDate(DateUtils.dateStrToDate(milestoneReviewModel.getCompletionDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			projectMilestoneDomain.setStrStatus("Not Completed");

		}
		 
		return projectMilestoneReviewMaster;
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewData(List<Long> projects){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();				
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewData(startDate,projects));			
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewDataForOrg(List<Long> org){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();		
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataForOrg(startDate,org));			
	
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewDataForGrp(List<Long> groups){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();		
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataForGrp(startDate,groups));			
	
	}
	
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDays(int days,List<Long> projects){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();
		calendar.add(Calendar.DATE, days);
		Date endDate=calendar.getTime();
		
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataByNoOfDays(startDate,endDate,projects));			
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDaysForOrg(int days,List<Long> org){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();
		calendar.add(Calendar.DATE, days);
		Date endDate=calendar.getTime();
		
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataByNoOfDaysForOrg(startDate,endDate,org));			
	
	}
	
	@Override
	public List<ProjectMilestoneForm> getMilestoneReviewDataByNoOfDaysForGrp(int days,List<Long> groups){
		Calendar calendar= GregorianCalendar.getInstance();
		Date startDate= calendar.getTime();
		calendar.add(Calendar.DATE, days);
		Date endDate=calendar.getTime();
		
		return convertProjectMilestoneDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataByNoOfDaysForGrp(startDate,endDate,groups));			
	
	}
	
	
	public List<ProjectMilestoneForm> convertProjectMilestoneDomainToModelList(List<ProjectMilestoneReviewMaster> milestoneDataList){
		List<ProjectMilestoneForm> list = new ArrayList<ProjectMilestoneForm>();
			for(int i=0;i<milestoneDataList.size();i++){
				ProjectMilestoneReviewMaster projectMilestoneReviewMaster = milestoneDataList.get(i);
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getMilestoneName());
				projectMilestoneForm.setCompletionDate(DateUtils.dateToString(projectMilestoneReviewMaster.getCompletionDate()));
				ProjectMasterDomain projectMasterDomain = projectMilestoneReviewMaster.getProjectMilestoneDomain().getProjectMasterDomain();
				Application application = projectMasterDomain.getApplication();
				projectMilestoneForm.setGroupName(application.getGroupMaster().getGroupName());
				projectMilestoneForm.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				projectMilestoneForm.setStrProjectName(projectMasterDomain.getStrProjectName());
				projectMilestoneForm.setStrProjectReference(projectMasterDomain.getStrProjectRefNo());
				projectMilestoneForm.setClientName(application.getClientMaster().getClientName());
				projectMilestoneForm.setMilestoneId(projectMilestoneReviewMaster.getProjectMilestoneDomain().getNumId());
				projectMilestoneForm.setEncMilestsoneId(encryptionService.encrypt(""+projectMilestoneReviewMaster.getProjectMilestoneDomain().getNumId()));

				list.add(projectMilestoneForm);
	}
			
			
		return list;
	}
	public List<MilestoneReviewModel> getMilestoneReviewHistory(long milestoneId){
		return convertMilestoneReviewMasterDomainToModelList(milestoneReviewMasterDao.getMilestoneReviewDataByMilestoneId(milestoneId));			
	}
	
	
	public List<ProjectMilestoneForm> getMilestoneRevData(){
		List<ProjectMilestoneForm> dataList = new ArrayList<ProjectMilestoneForm>();
		Map<String, String> map = new HashMap<String, String>(); 
		List<Object[]> newDataList =  projectMasterDao.getMilesStoneData();
		String encProjectId = "";
		if(null != newDataList){	
		for(Object[] obj : newDataList){
			ProjectMilestoneForm model = new ProjectMilestoneForm();
			
			model.setMilestoneId(new Integer(""+obj[1]));
			model.setEncMilestsoneId(encryptionService.encrypt(""+new Integer(""+obj[1])));
			model.setClientName(""+obj[5]);
			model.setStrProjectReference(""+obj[4]);
			model.setStrProjectName(""+obj[3]);
			model.setMilestoneName(""+obj[0]);
			model.setCompletionDate(DateUtils.dateToString((Date)obj[2]));
			model.setGroupName(""+obj[6]);
			if(map.containsKey(""+obj[3])){
				encProjectId= map.get(""+obj[3]);
			}else{
				encProjectId = encryptionService.encrypt(""+""+new Integer(""+obj[7]));
				map.put(""+obj[3], encProjectId);
			}
			model.setEncProjectId(encProjectId);
			
			dataList.add(model);
		}
	}
	return dataList;
	}
	
	
/*	public List<ProjectMilestoneForm> convertDomainToModelList(List<ProjectMilestoneReviewMaster> milestoneDataList){
		List<ProjectMilestoneForm> list = new ArrayList<ProjectMilestoneForm>();
			for(int i=0;i<milestoneDataList.size();i++){
				ProjectMilestoneReviewMaster projectMilestoneReviewMaster = milestoneDataList.get(i);
				ProjectMilestoneForm projectMilestoneForm = new ProjectMilestoneForm();
				projectMilestoneForm.setMilestoneName(projectMilestoneReviewMaster.getProjectMilestoneDomain().getMilestoneName());
				projectMilestoneForm.setCompletionDate(DateUtils.dateToString(projectMilestoneReviewMaster.getCompletionDate()));
				ProjectMasterDomain projectMasterDomain = projectMilestoneReviewMaster.getProjectMilestoneDomain().getProjectMasterDomain();
				Application application = projectMasterDomain.getApplication();
				projectMilestoneForm.setGroupName(application.getGroupMaster().getGroupName());
				projectMilestoneForm.setEncProjectId(encryptionService.encrypt(""+projectMasterDomain.getNumId()));
				projectMilestoneForm.setStrProjectName(projectMasterDomain.getStrProjectName());
				projectMilestoneForm.setStrProjectReference(projectMasterDomain.getStrProjectRefNo());
				projectMilestoneForm.setClientName(application.getClientMaster().getClientName());
				projectMilestoneForm.setMilestoneId(projectMilestoneReviewMaster.getProjectMilestoneDomain().getNumId());
				projectMilestoneForm.setEncMilestsoneId(encryptionService.encrypt(""+projectMilestoneReviewMaster.getProjectMilestoneDomain().getNumId()));

				list.add(projectMilestoneForm);
	}
			
			
		return list;
	}*/
	
	
	public List<ProjectMilestoneForm> getMilestoneRevDataByDates(String expComplDate,String symbol){
		List<ProjectMilestoneForm> dataList = new ArrayList<ProjectMilestoneForm>();
		Map<String, String> map = new HashMap<String, String>(); 
		List<Object[]> newDataList =  projectMasterDao.getMilestoneRevDataByDates(expComplDate,symbol);
		String encProjectId = "";
		for(Object[] obj : newDataList){
			ProjectMilestoneForm model = new ProjectMilestoneForm();
			
			model.setMilestoneId(new Integer(""+obj[1]));
			model.setEncMilestsoneId(encryptionService.encrypt(""+new Integer(""+obj[1])));
			model.setClientName(""+obj[5]);
			model.setStrProjectReference(""+obj[4]);
			model.setStrProjectName(""+obj[3]);
			model.setMilestoneName(""+obj[0]);
			model.setCompletionDate(DateUtils.dateToString((Date)obj[2]));
			model.setGroupName(""+obj[6]);
			if(map.containsKey(""+obj[3])){
				encProjectId= map.get(""+obj[3]);
			}else{
				encProjectId = encryptionService.encrypt(""+""+new Integer(""+obj[7]));
				map.put(""+obj[3], encProjectId);
			}
			model.setEncProjectId(encProjectId);
			
			dataList.add(model);
		}
	return dataList;
	}
}
