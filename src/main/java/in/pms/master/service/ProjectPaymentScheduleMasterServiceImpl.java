package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProjectMilestoneDao;
import in.pms.master.dao.ProjectPaymentScheduleMasterDao;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.ProjectPaymentSeheduleMasterDomain;
import in.pms.master.model.ProjectPaymentScheduleMasterModel;

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
public class ProjectPaymentScheduleMasterServiceImpl implements ProjectPaymentScheduleMasterService{

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ProjectPaymentScheduleMasterDao projectPaymentScheduleMasterDao;
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	ProjectMilestoneDao projectMilestoneDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_PAYMENT_SCHEDULE_MST')")
	public long saveUpdateProjectPaymentScheduleMaster(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel){
		ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain = convertProjectPaymentScheduleMasterModelToDomain(projectPaymentScheduleMasterModel);
		return projectPaymentScheduleMasterDao.saveUpdateProjectPaymentScheduleMaster(projectPaymentSeheduleMasterDomain);
	}
		
	@Override
	public String checkDuplicateProjectPaymentScheduleSeqNo(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel){
		String result=	"";
		ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterDao.getProjectPaymentScheduleMasterBySeqNo(projectPaymentScheduleMasterModel.getNumPaymentSequence(),projectPaymentScheduleMasterModel.getProjectId());
		
		 if(null == projectPaymentSeheduleMasterDomain){
				return null; 
			 }else if(projectPaymentScheduleMasterModel.getNumId() != 0){
				 if(projectPaymentSeheduleMasterDomain.getNumId() == projectPaymentScheduleMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Project Payment Schedule with same Reference Number already exist with Id "+projectPaymentSeheduleMasterDomain.getNumId();
				 }
			 }else{
				if(projectPaymentSeheduleMasterDomain.getNumIsValid() == 0){
					result = "Project Payment Schedule with same Reference Number Details already exist with Id "+projectPaymentSeheduleMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Payment Schedule with same Reference Number Details already exist with Id "+projectPaymentSeheduleMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ProjectPaymentScheduleMasterModel getProjectPaymentScheduleMasterDomainById(long numId){
		return convertProjectPaymentScheduleMasterDomainToModel(projectPaymentScheduleMasterDao.getProjectPaymentScheduleMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_PAYMENT_SCHEDULE_MST')")
	public List<ProjectPaymentScheduleMasterModel> getAllProjectPaymentScheduleMasterDomain(){
		return convertProjectPaymentScheduleMasterDomainToModelList(projectPaymentScheduleMasterDao.getAllProjectPaymentScheduleMasterDomain());
	}
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getAllActiveProjectPaymentScheduleMasterDomain(){
		return convertProjectPaymentScheduleMasterDomainToModelList(projectPaymentScheduleMasterDao.getAllActiveProjectPaymentScheduleMasterDomain());
	}
	
	/*@Override
	public List<ProjectPaymentScheduleMasterModel> getAllActiveProjectMasterDomain(){
		return convertOrganisationMasterDomainToModelList(ProjectPaymentScheduleMasterDao.getAllActiveProjectMasterDomain());
	}*/
	
	public ProjectPaymentSeheduleMasterDomain convertProjectPaymentScheduleMasterModelToDomain(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain = new ProjectPaymentSeheduleMasterDomain();
		if(projectPaymentScheduleMasterModel.getNumId()!=0){				
			projectPaymentSeheduleMasterDomain =  projectPaymentScheduleMasterDao.getProjectPaymentScheduleMasterById(projectPaymentScheduleMasterModel.getNumId());
		}
		
		projectPaymentSeheduleMasterDomain.setDtTrDate(new Date());
		projectPaymentSeheduleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectPaymentSeheduleMasterDomain.setNumIsValid(1);
		/*if(projectPaymentScheduleMasterModel.isValid()){
			projectPaymentSeheduleMasterDomain.setNumIsValid(1);
		}else{
			projectPaymentSeheduleMasterDomain.setNumIsValid(0);
		}*/
		projectPaymentSeheduleMasterDomain.setNumPaymentSequence(projectPaymentScheduleMasterModel.getNumPaymentSequence());
		try {
			projectPaymentSeheduleMasterDomain.setDtPaymentDueDate(DateUtils.dateStrToDate(projectPaymentScheduleMasterModel.getStrPaymentDueDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projectPaymentSeheduleMasterDomain.setNumAmount(projectPaymentScheduleMasterModel.getNumAmount());
		projectPaymentSeheduleMasterDomain.setStrPurpose(projectPaymentScheduleMasterModel.getStrPurpose());
		projectPaymentSeheduleMasterDomain.setStrRemarks(projectPaymentScheduleMasterModel.getStrRemarks());
		projectPaymentSeheduleMasterDomain.setNumMilestoneId(projectPaymentScheduleMasterModel.getNumMilestoneId());
		projectPaymentSeheduleMasterDomain.setProjectId(projectPaymentScheduleMasterModel.getProjectId());
/*		projectPaymentSeheduleMasterDomain.setLinkedWithMilestone(projectPaymentScheduleMasterModel.isLinkedWithMilestone());
*/	
		if(projectPaymentScheduleMasterModel.isLinkedWithMilestone()){
			projectPaymentSeheduleMasterDomain.setLinkedWithMilestone(1);
		}else{
			projectPaymentSeheduleMasterDomain.setLinkedWithMilestone(0);
		}
		if(null != projectPaymentScheduleMasterModel.getStrPaymentDueDate()){
			
			// SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			   // String projectStartDate = "04/04/2019";
			    Date endDate=null;
				
					try {
						endDate = DateUtils.dateStrToDate(projectPaymentScheduleMasterModel.getStrPaymentDueDate());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			    ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();
			    projectMasterDomain=projectMasterDao.getProjectMasterDataById(projectPaymentScheduleMasterModel.getProjectId());
				
			    Date startDate = projectMasterDomain.getDtProjectStartDate();
				 
	
				
				String monthDays =DateUtils.getMonthandDaysBetweenDates(startDate, endDate);
				
				int years=0;
				int months=0;
				int days =0;
				
				String testData = monthDays;
				if(testData.contains("year") || testData.contains("years")){
					String yearValue = testData.split("year")[0];
					 years= Integer.parseInt(yearValue.trim());
					//System.out.println(yearValue.trim());
					
					// Year Completed
					//Work For Months now
					if(testData.contains("months") || testData.contains("month")){
						String yearMonthData = testData.split("month")[0];
						String monthStr = "";
						if(testData.contains("years")){
							monthStr = yearMonthData.split("years  and")[1];
						}else{
							monthStr = yearMonthData.split("year  and")[1];
						}					
						String tempMonthStr = monthStr.split("month")[0];
					int	tempMonths = Integer.parseInt(tempMonthStr.trim());
						months = tempMonths;					
					}
					
					months = years*12+months;
					
					
					if(testData.contains("days") || testData.contains("day")){
						if(testData.contains("months") || testData.contains("month")){
							String daysStr="";
							if(testData.contains("months")){
								daysStr = testData.split("months  and")[1];
							}else{
								daysStr = testData.split("month  and")[1];
							}
							
							String tempDays = daysStr.split("day")[0];
							days = Integer.parseInt(tempDays.trim());
						}else{
							String daysStr="";
							if(testData.contains("years")){
								daysStr = testData.split("years  and")[1];
							}else{
								daysStr = testData.split("year  and")[1];
							}
							
							String tempDays = daysStr.split("day")[0];
							days = Integer.parseInt(tempDays.trim());
						}
					}
					
				}else{
					
					if(testData.contains("months") || testData.contains("month")){
							String monthStr = testData.split("month")[0];											
							months = Integer.parseInt(monthStr.trim());					
					}
					if(testData.contains("days") || testData.contains("day")){
						if(testData.contains("months") || testData.contains("month")){
							String daysStr="";
							if(testData.contains("months")){
								daysStr = testData.split("months  and")[1];
							}else{
								daysStr = testData.split("month  and")[1];
							}
							//System.out.println("Test" +daysStr);
							String tempDays = daysStr.split("day")[0];
							days = Integer.parseInt(tempDays.trim());
						}else{
							String tempDays = testData.split("day")[0];
							days = Integer.parseInt(tempDays.trim());
						}
					}
					
				}
			
				
				projectPaymentSeheduleMasterDomain.setNumDueAfterDays(days);
				projectPaymentSeheduleMasterDomain.setNumDueAfterMonths(months);
		}
		
		
		return projectPaymentSeheduleMasterDomain;
	}
	
	
	
	
	
	
	
	
	
	
	public List<ProjectPaymentScheduleMasterModel> convertProjectPaymentScheduleMasterDomainToModelList(List<ProjectPaymentSeheduleMasterDomain> projectPaymentScheduleMasterList){
		List<ProjectPaymentScheduleMasterModel> list = new ArrayList<ProjectPaymentScheduleMasterModel>();
			for(int i=0;i<projectPaymentScheduleMasterList.size();i++){
				ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterList.get(i);
				ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel = new ProjectPaymentScheduleMasterModel();
				
				if(projectPaymentSeheduleMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+projectPaymentSeheduleMasterDomain.getNumId());
					projectPaymentScheduleMasterModel.setEncProjectId(encryptedId);
				}
				projectPaymentScheduleMasterModel.setNumId(projectPaymentSeheduleMasterDomain.getNumId());
				if(projectPaymentSeheduleMasterDomain.getNumIsValid() ==1){
					projectPaymentScheduleMasterModel.setValid(true);
				}else{
					projectPaymentScheduleMasterModel.setValid(false);
				}
			
		
				projectPaymentScheduleMasterModel.setNumPaymentSequence(projectPaymentSeheduleMasterDomain.getNumPaymentSequence());
				projectPaymentScheduleMasterModel.setStrPaymentDueDate(DateUtils.dateToString(projectPaymentSeheduleMasterDomain.getDtPaymentDueDate()));
				projectPaymentScheduleMasterModel.setNumAmount(projectPaymentSeheduleMasterDomain.getNumAmount());
				projectPaymentScheduleMasterModel.setStrAmount(CurrencyUtils.convertToINR(projectPaymentSeheduleMasterDomain.getNumAmount()));
				projectPaymentScheduleMasterModel.setStrPurpose(projectPaymentSeheduleMasterDomain.getStrPurpose());
				projectPaymentScheduleMasterModel.setStrRemarks(projectPaymentSeheduleMasterDomain.getStrRemarks());
				projectPaymentScheduleMasterModel.setNumMilestoneId(projectPaymentSeheduleMasterDomain.getNumMilestoneId());
				try{
				if(projectPaymentSeheduleMasterDomain.getNumMilestoneId()!=0){
				List<ProjectMilestoneDomain> details=projectMilestoneDao.getMilestoneDataById(projectPaymentSeheduleMasterDomain.getNumMilestoneId());
				if(details.size()>0){
					projectPaymentScheduleMasterModel.setMilestoneName(details.get(0).getMilestoneName());
				}
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			
				if(projectPaymentSeheduleMasterDomain.getLinkedWithMilestone() ==1){
					projectPaymentScheduleMasterModel.setLinkedWithMilestone(true);
					projectPaymentScheduleMasterModel.setEncMiletoneId(encryptionService.encrypt(""+projectPaymentSeheduleMasterDomain.getNumMilestoneId()));
				}else{
					projectPaymentScheduleMasterModel.setLinkedWithMilestone(false);
				}
/*					projectPaymentScheduleMasterModel.setLinkedWithMilestone(projectPaymentSeheduleMasterDomain.isLinkedWithMilestone());
*/				
				
				
				list.add(projectPaymentScheduleMasterModel);
	}
		return list;
	}

	
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getProjectPaymentScheduleByProjectId(long projectId) {		
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterDao.getAllProjectPaymentScheduleByProjectID(projectId);
			List<ProjectPaymentScheduleMasterModel> projectPaymentScheduleMasterModelList = convertProjectPaymentScheduleMasterDomainToModelList(projectPaymentSeheduleMasterDomain);
			return projectPaymentScheduleMasterModelList;
	}
	
	//Function added by devesh on 02-04-24 to get max payment sequence number for a project
	@Override
	public int getMaxProjectPaymentNumberByProjectID(long projectId) {
			return projectPaymentScheduleMasterDao.getMaxProjectPaymentNumberByProjectID(projectId);
	}
	//End of function

	@Override
	public List<ProjectPaymentScheduleMasterModel> getProjectPaymentScheduleByMilestoneId(long numMilestoneId) {		
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterDao.getAllProjectPaymentScheduleByMilestoneID(numMilestoneId);
			List<ProjectPaymentScheduleMasterModel> projectPaymentScheduleMasterModelList1 = convertProjectPaymentScheduleMasterDomainToModelList(projectPaymentSeheduleMasterDomain);
			return projectPaymentScheduleMasterModelList1;
	}
	
	
	
	
	
	public ProjectPaymentScheduleMasterModel convertProjectPaymentScheduleMasterDomainToModel(ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain){
		ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel = new ProjectPaymentScheduleMasterModel();
	
		if(projectPaymentSeheduleMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+projectPaymentSeheduleMasterDomain.getNumId());
			projectPaymentScheduleMasterModel.setEncProjectId(encryptedId);
		}
		projectPaymentScheduleMasterModel.setNumId(projectPaymentSeheduleMasterDomain.getNumId());
		if(projectPaymentSeheduleMasterDomain.getNumIsValid() ==1){
			projectPaymentScheduleMasterModel.setValid(true);
		}else{
			projectPaymentScheduleMasterModel.setValid(false);
		}
	
		
		projectPaymentScheduleMasterModel.setNumPaymentSequence(projectPaymentSeheduleMasterDomain.getNumPaymentSequence());
		projectPaymentScheduleMasterModel.setStrPaymentDueDate(DateUtils.dateToString(projectPaymentSeheduleMasterDomain.getDtPaymentDueDate()));
		projectPaymentScheduleMasterModel.setNumAmount(projectPaymentSeheduleMasterDomain.getNumAmount());
		projectPaymentScheduleMasterModel.setStrPurpose(projectPaymentSeheduleMasterDomain.getStrPurpose());
		
		if(projectPaymentSeheduleMasterDomain.getLinkedWithMilestone() ==1){
			projectPaymentScheduleMasterModel.setLinkedWithMilestone(true);
		}else{
			projectPaymentScheduleMasterModel.setLinkedWithMilestone(false);
		}
/*		projectPaymentScheduleMasterModel.setLinkedWithMilestone(projectPaymentSeheduleMasterDomain.isLinkedWithMilestone());
*/
		
		return projectPaymentScheduleMasterModel;
		
	}
	public long deleteProjectPaymentSchedule(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ProjectPaymentSeheduleMasterDomain> projectPaymentScheduleMasterList = projectPaymentScheduleMasterDao.getProjectPaymentScheduleMasterById(projectPaymentScheduleMasterModel.getIdCheck());
		for(int i=0;i<projectPaymentScheduleMasterList.size();i++){
			ProjectPaymentSeheduleMasterDomain projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterList.get(i);
			projectPaymentSeheduleMasterDomain.setNumIsValid(2);
			projectPaymentSeheduleMasterDomain.setDtTrDate(new Date());
			projectPaymentSeheduleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = projectPaymentScheduleMasterDao.saveUpdateProjectPaymentScheduleMaster(projectPaymentSeheduleMasterDomain);
		}
		return result;
	}
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getScheduledPaymentByProjectId(long projectId) {		
			List<ProjectPaymentSeheduleMasterDomain> projectPaymentSeheduleMasterDomain = projectPaymentScheduleMasterDao.getActiveProjectPaymentScheduleByProjectID(projectId);
			List<ProjectPaymentScheduleMasterModel> SchedulePaymentModelList = convertProjectPaymentScheduleMasterDomainToModelList(projectPaymentSeheduleMasterDomain);
			return SchedulePaymentModelList;
	}
	
	
	@Override
	public List<ProjectPaymentScheduleMasterModel> getPaymentScheduleWithReceivedAmount(long projectId) {		
			List<Object[]> objects = projectPaymentScheduleMasterDao.getPaymentScheduleWithReceivedAmount(projectId);
			
			List<ProjectPaymentScheduleMasterModel> modelList = new ArrayList<ProjectPaymentScheduleMasterModel>();
			for(int i=0;i<objects.size();i++){
				Object[] obj = objects.get(i); 
				ProjectPaymentScheduleMasterModel model = new ProjectPaymentScheduleMasterModel();			
					String encryptedId = encryptionService.encrypt(""+obj[0]);
					model.setEncId(encryptedId);				
					
					Date dueDate = (Date) obj[2];
					model.setStrPaymentDueDate(DateUtils.dateToString(dueDate));					
					model.setStrAmount(CurrencyUtils.convertToINR(obj[3]));
					model.setStrPurpose(""+obj[1]);
					if(null != obj[4]){
						model.setReceivedAmount(CurrencyUtils.convertToINR(obj[4]));
					}else{
						model.setReceivedAmount("");
					}
					modelList.add(model);
			}
			return modelList;
	}
	
	///Smrita  Count project Revenue based on financial year
	@Override
	public double getTotalProjectsRevenue(Date startRange, Date endRange) {
		double projectRevenue = projectPaymentScheduleMasterDao.getprojectsRevenueByOrganisation(startRange,endRange);		
		//Division by 1 Lakh to show amount in Lakhs and round is used for showing up to 2 decimal places
			return CurrencyUtils.round((projectRevenue/100000),2);
	}
}
