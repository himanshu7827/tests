package in.pms.transaction.service;

import in.pms.global.dao.DaoHelper;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.DesignationMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.transaction.dao.ApprovedJobDao;
import in.pms.transaction.dao.EmployeeApprovedJobMappingDao;
import in.pms.transaction.domain.ApprovedJobDomain;
import in.pms.transaction.domain.EmployeeApprovedJobMapping;
import in.pms.transaction.model.ApprovedJobModel;
import in.pms.transaction.model.EmployeeApprovedJobMappingModel;
import in.pms.transaction.model.JobTitleModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class ApprovedJobServiceImpl implements ApprovedJobService {
	
	@Autowired
	ApprovedJobDao approvedJobDao;
	
	@Autowired
	DesignationMasterDao designationMasterDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	EmployeeApprovedJobMappingDao  employeeApprovedJobMappingDao;
	
	@Autowired
	DaoHelper daoHelper;
	
	@Override
	public void  saveApprovedJobData(ApprovedJobModel approvedJobModel){
		try{
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		List<String> jobReferences=approvedJobModel.getJobReferences();	
		if(jobReferences.size()>0){
			GroupMasterDomain groupMasterDomain= new GroupMasterDomain();
			DesignationMaster designationMaster= new DesignationMaster();
			ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();			
			groupMasterDomain.setNumId(approvedJobModel.getGroupId());
			designationMaster.setNumDesignationId(approvedJobModel.getDesignationId());
			projectMasterDomain.setNumId(approvedJobModel.getProjectId());
			EmployeeMasterDomain employeeMasterDomain=new EmployeeMasterDomain();
			employeeMasterDomain.setNumId(0);
			
			for (int i = 0; i < jobReferences.size(); i++) {
				ApprovedJobDomain approvedJobDomain=new ApprovedJobDomain();
				
				//approvedJobDomain.setNumId(approvedJobModel.getNumId());				
				approvedJobDomain.setGroupMasterDomain(groupMasterDomain);
				approvedJobDomain.setDesignationMaster(designationMaster);
				approvedJobDomain.setProjectMasterDomain(projectMasterDomain);
				try{
					approvedJobDomain.setApprovedOn(DateUtils.dateStrToDate(approvedJobModel.getApprovedOn()));			
				}
				catch(Exception e){
					e.printStackTrace();
				}
				approvedJobDomain.setJobCode(jobReferences.get(i));
				approvedJobDomain.setDurationInMonths(approvedJobModel.getDurationInMonths());
				approvedJobDomain.setNumIsValid(1);	
				approvedJobDomain.setDtTrDate(new Date());
				approvedJobDomain.setNumTrUserId(userInfo.getEmployeeId());
				approvedJobDomain.setStatus("Created");
				//  
				List<EmployeeApprovedJobMapping> employeeApprovedJobs = new ArrayList<EmployeeApprovedJobMapping>();
				EmployeeApprovedJobMapping empAppJob= new EmployeeApprovedJobMapping();
				
				
			    empAppJob.setDtTrDate(new Date());
				empAppJob.setNumTrUserId(userInfo.getEmployeeId());
				empAppJob.setNumIsValid(1);	
				try{
					empAppJob.setStartDate(DateUtils.dateStrToDate(approvedJobModel.getApprovedOn()));			
					}
					catch(Exception e){
						e.printStackTrace();
					}
				
				
				empAppJob.setApprovedJob(approvedJobDomain);
				
				empAppJob.setEmployeeMasterDomain(employeeMasterDomain);
				empAppJob.setRemark("Job Request Created");
				employeeApprovedJobs.add(empAppJob);
				approvedJobDomain.setEmployeeApprovedJobs(employeeApprovedJobs);
				//employeeApprovedJobMappingDao.save(empAppJob);
				approvedJobDomain=	approvedJobDao.save(approvedJobDomain);	
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
/*	public ApprovedJobDomain convertApprovedJobFormToDomain(ApprovedJobModel approvedJobModel){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ApprovedJobDomain approvedJobDomain = new ApprovedJobDomain();

		
		
		List<String> jobReferences=approvedJobModel.getJobReferences();	
		if(jobReferences.size()>0){
			for (int i = 0; i < jobReferences.size(); i++) {
				approvedJobDomain.setNumId(approvedJobModel.getNumId());
				GroupMasterDomain groupMasterDomain= new GroupMasterDomain();
				DesignationMaster designationMaster= new DesignationMaster();
				ProjectMasterDomain projectMasterDomain=new ProjectMasterDomain();
				groupMasterDomain.setNumId(approvedJobModel.getGroupId());
				designationMaster.setNumDesignationId(approvedJobModel.getDesignationId());
				projectMasterDomain.setNumId(approvedJobModel.getProjectId());
				approvedJobDomain.setGroupMasterDomain(groupMasterDomain);
				approvedJobDomain.setDesignationMaster(designationMaster);
				approvedJobDomain.setProjectMasterDomain(projectMasterDomain);
				approvedJobDomain.setNumTrUserId(userInfo.getEmployeeId());
				approvedJobDomain.setStatus("Created");
				try{
				approvedJobDomain.setApprovedOn(DateUtils.dateStrToDate(approvedJobModel.getApprovedOn()));
				approvedJobDomain.setCreatedOn(DateUtils.dateStrToDate(approvedJobModel.getClosedOn()));
				approvedJobDomain.setClosedOn(DateUtils.dateStrToDate(approvedJobModel.getClosedOn()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				approvedJobDomain.setJobCode(jobReferences.get(i));
				approvedJobDomain.setDurationInMonths(approvedJobModel.getDurationInMonths());
				approvedJobDomain.setNumIsValid(1);	
				approvedJobDomain.setDtTrDate(new Date());
			}
		}
				
	
		return approvedJobDomain;
	}*/
	/*@Override
	public List<ApprovedJobModel> getApprovedJobDetails(long projectId){
		return convertDomainToModelList(approvedJobDao.getApprovedJobDetails(projectId));			
	}

	public List<ApprovedJobModel> convertDomainToModelList(List<ApprovedJobDomain> approveJobDataList){
		List<ApprovedJobModel> list = new ArrayList<ApprovedJobModel>();
			for(int i=0;i<approveJobDataList.size();i++){
				ApprovedJobDomain approvedJobDomain = approveJobDataList.get(i);
				ApprovedJobModel approvedJobModel = new ApprovedJobModel();
				approvedJobModel.setJobCode(approvedJobDomain.getJobCode());
				approvedJobModel.setStatus(approvedJobDomain.getStatus());
				approvedJobModel.setDesignationName(approvedJobDomain.getDesignationMaster().getDesignationName());
				try{
				approvedJobModel.setApprovedOn(DateUtils.dateToString(approvedJobDomain.getApprovedOn()));
				}
				catch(Exception e){
					e.printStackTrace();
				}
				list.add(approvedJobModel);
	}
		return list;
	}*/
	
	@Override
	public Map<String,List<ApprovedJobModel>> getApprovedJobsByProjectId(long projectId){
		Map<String,List<ApprovedJobModel>> dataMap = new LinkedHashMap<String, List<ApprovedJobModel>>();
		List<Object[]> list = approvedJobDao.getApprovedJobDetails(projectId);
		
		
		String requirementName="";
		for(int i=0;i<list.size();i++){
			
			Object[] obj = list.get(i);
			
			Date approvedOn = (Date) obj[1];
			
			String designationId=obj[0].toString();
			String durationInMonths=obj[2].toString();
			String approDate="";
			Date createdDate=(Date)obj[1];

			try{
			
			 approDate=DateUtils.dateToString(createdDate);
			}
			catch(Exception e){
				
			}
			ProjectMasterDomain pmDetails=projectMasterDao.getProjectMasterDataById(projectId);
			String projectName=pmDetails.getStrProjectName();
			DesignationMasterDomain details=designationMasterDao.getDesignationMasterById(Integer.parseInt(designationId));
			String designationName=details.getDesignationName();
			requirementName = designationName  +"**"+  approDate +"@@"+ projectName+"##"+durationInMonths;
			List<ApprovedJobDomain> jobCodeDetails = approvedJobDao.getJobCodeDetails(projectId,Integer.parseInt(designationId),approvedOn);
			List<ApprovedJobModel> ApprovedJobList = new ArrayList<ApprovedJobModel>();
			for(int j=0;j<jobCodeDetails.size();j++){
				ApprovedJobModel approvedJobModel = new ApprovedJobModel();
				String status="";
				approvedJobModel.setJobCode(jobCodeDetails.get(j).getJobCode());
				 status=jobCodeDetails.get(j).getStatus();
				if(status.equals("Created")){
					status="Not yet filled";
				}
				approvedJobModel.setStatus(status);
				ApprovedJobList.add(approvedJobModel);
			}
			
			dataMap.put(requirementName, ApprovedJobList);	
		}
		
		
		return dataMap;
	}


	@Override
	public List getApprovedJobsCount(EmployeeApprovedJobMappingModel model){
		List list = new ArrayList();
		Date startDate = null;
		String strStartDate = model.getStartDate();
		if(null != strStartDate && !strStartDate.equals("")){
			try {
				startDate = DateUtils.dateStrToDate(strStartDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null == startDate){
			Calendar cal = GregorianCalendar.getInstance();
			cal.add(Calendar.MONTH, -1);			
			startDate = cal.getTime();
		}
		
		Date endDate = null;
		String strEndDate = model.getEndDate();
		if(null != strEndDate && !strEndDate.equals("")){
			try {
				endDate = DateUtils.dateStrToDate(strEndDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null == endDate){
			Calendar cal = GregorianCalendar.getInstance();					
			endDate = cal.getTime();
		}
		
		String status = model.getJobStatus();
		if(status.equals("ALL")){
			status = null;
		}else if(status.equals("NYF")){
			status ="Created";
		}else if(status.equals("ASS")){
			status ="Assigned";
		}else if(status.equals("CLS")){
			status ="Closed";
		}else if(status.equals("EXP")){
			status ="Expired";
		}
		
		List<String> groupList = new ArrayList<String>();
		
		List<Long> groups = null;
		String selectedGroup = model.getSelectedGroups();
		if(null != selectedGroup && !selectedGroup.equals("")){
			String[] tempGroup = selectedGroup.split(",");
			for(int i=0;i<tempGroup.length;i++){
				if(null == groups){
					groups = new ArrayList<Long>();
				}
				groups.add(Long.parseLong(tempGroup[i]));
			}
		}
		
		
		List<Object[]> countList = new ArrayList<Object[]>();
		
		if( (null != groups && groups.size() > 0 ) && null != status){
			if(status.equals("Created")){
				countList = approvedJobDao.getAllFreshJobsCount(groups);
			}else if(status.equals("Assigned") || status.equals("Expired")){
				countList = approvedJobDao.getJobsCountByStatus(startDate,endDate,groups,status);
			}else if(status.equals("Closed")){
				countList = approvedJobDao.getAllClosedJobsCount(startDate,endDate,groups);
			}
		}else if((null != groups && groups.size() > 0 ) && null == status){			
			countList = approvedJobDao.getAllApprovedJobsCount(startDate,endDate,groups);
		}		
		
		Map<String,List<ApprovedJobModel>> dataMap = new LinkedHashMap<String, List<ApprovedJobModel>>();
		
		
		for(int i=0;i<countList.size();i++){
			Object[] obj = countList.get(i);
			String groupName = ""+obj[2];
			String designationName = ""+obj[3];
						
			if(!groupList.contains(groupName)){
				groupList.add(groupName);
			}
			
			String groupId = ""+obj[0];
			String designationId = ""+obj[1];
			String count = ""+obj[5];
			ApprovedJobModel approvedJobModel = new ApprovedJobModel();
			approvedJobModel.setGroupName(groupName);
			approvedJobModel.setEncGroupId(encryptionService.encrypt(groupId));
			
			approvedJobModel.setDesignationName(designationName);
			approvedJobModel.setEncDesignationId(encryptionService.encrypt(designationId));
			
			approvedJobModel.setCount(count);
			
			if(dataMap.containsKey(designationName)){
				List<ApprovedJobModel> tempJobList = dataMap.get(designationName);
				tempJobList.add(approvedJobModel);
				dataMap.put(designationName, tempJobList);
			}else{
				List<ApprovedJobModel> tempJobList = new ArrayList<ApprovedJobModel>();
				tempJobList.add(approvedJobModel);
				dataMap.put(designationName, tempJobList);
			}		
		}
		
		//outputMap
		
		for(int i=0;i< groupList.size();i++){			
			String tempGroup = groupList.get(i);			
			for (Map.Entry<String,List<ApprovedJobModel>> entry : dataMap.entrySet()){				
				 List<ApprovedJobModel> tempList =  entry.getValue();
				 if(tempList.size() != groupList.size()){
			        	ApprovedJobModel approvedJobModel = tempList.stream()
				                .filter(x -> tempGroup.equals(x.getGroupName()))
				                .findAny()
				                .orElse(null);
			        	if(null == approvedJobModel){
			        		tempList.add(i, new ApprovedJobModel());
			        		dataMap.put(entry.getKey(), tempList);
			        	}
			        }
			}
		}		
		list.add(groupList);
		list.add(dataMap);	
		return list;
		
	}
	public int  checkUniqueJobCode(ApprovedJobModel approvedJobModel){
		int result=0;
		 result=designationMasterDao.checkUniqueJobCode(approvedJobModel);
			return  result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('VIEW_APPROVED_JOB')")
	public void viewApprovedJobAuthorityCheck(){
		
	}
	
	@Override
	public List<ApprovedJobModel> getApprovedJobsByProjectIdCreated(long projectId){
		//List<ApprovedJobModel> dataMap = new List<ApprovedJobModel>();
		List<ApprovedJobDomain>  list11 = approvedJobDao.getApprovedJobDetailsCreated(projectId);
		return ConvertDomaintOModel1(list11);
	}

	private List<ApprovedJobModel> ConvertDomaintOModel1(List<ApprovedJobDomain> list11) {
		List<ApprovedJobModel> list = new ArrayList<ApprovedJobModel>();
		for(int i=0;i<list11.size();i++){
			ApprovedJobDomain approvedJobDomain = list11.get(i);
			ApprovedJobModel approvedJobModel = new ApprovedJobModel();
			approvedJobModel.setJobCode(approvedJobDomain.getJobCode());
			approvedJobModel.setNumId(approvedJobDomain.getNumId());
		
			list.add(approvedJobModel);
}
	return list;
	}

	@Override
	@PreAuthorize("hasAuthority('WRITE_JOB_TITLE')")
	public void saveJobTitle(JobTitleModel jobTitleModel) {
	
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		int jobid = jobTitleModel.getJobID();
		List<EmployeeApprovedJobMapping> approvedJobs= approvedJobDao.getApprovedJobDetails(jobid);
		
		if(null != approvedJobs){
			EmployeeApprovedJobMapping employeeApprovedJobMapping= approvedJobs.get(0);
			employeeApprovedJobMapping.setNumIsValid(0);
			try{
				employeeApprovedJobMapping.setEndDate(DateUtils.dateStrToDate(jobTitleModel.getApprovedOn()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			employeeApprovedJobMapping.setDtTrDate(new Date());
			employeeApprovedJobMapping.setNumTrUserId(userInfo.getEmployeeId());
			
			employeeApprovedJobMapping = daoHelper.merge(EmployeeApprovedJobMapping.class, employeeApprovedJobMapping);
			
			EmployeeApprovedJobMapping newDomain= new EmployeeApprovedJobMapping();
			
			newDomain.setNumIsValid(1);
			//newDomain.setStartDate(new Date());
			try{
				newDomain.setStartDate(DateUtils.dateStrToDate(jobTitleModel.getApprovedOn()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			ApprovedJobDomain approvedjobDomain=new ApprovedJobDomain();
			approvedjobDomain= employeeApprovedJobMapping.getApprovedJob();
			approvedjobDomain.setStatus("Assigned");
			try{
				approvedjobDomain.setCreatedOn(DateUtils.dateStrToDate(jobTitleModel.getApprovedOn()));
				}
				catch (ParseException e) {				
					e.printStackTrace();
				}
			newDomain.setApprovedJob(approvedjobDomain);
			newDomain.setDtTrDate(new Date());
			EmployeeMasterDomain employeeMasterDomain = new EmployeeMasterDomain();
			employeeMasterDomain.setNumId(jobTitleModel.getEmpId());
			newDomain.setEmployeeMasterDomain(employeeMasterDomain);
			newDomain.setNumTrUserId(userInfo.getEmployeeId());
			
			
			employeeApprovedJobMappingDao.save(newDomain);
		}
		
			
		}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_JOB_TITLE')")
	public void jobTitleAuthorityCheck(){		
	}
	

	
}