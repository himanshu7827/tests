package in.pms.master.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectMilestoneDao;
import in.pms.master.dao.TaskDetailsDao;
import in.pms.master.domain.ProjectMilestoneDomain;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.domain.TaskDocumentDomain;
import in.pms.master.model.MilestoneActivityModel;
import in.pms.master.model.TaskDetailsModel;
@Service
public class TaskDetailsServiceImpl implements TaskDetailsService{
	
	@Autowired
	TaskDetailsDao taskDetailsDao;
	@Autowired
	ProjectMilestoneDao projectMilestoneDao;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	DocumentFormatService documentFormatService;
	@Autowired
	TaskDocumentService taskDocumentService;
	
	@Autowired
	EncryptionService encryptionService;
	
	
	@Override
	@PreAuthorize("hasAuthority('READ_TASKDETAIL_MST')")
	public List<TaskDetailsModel> getAllTaskDetailsData(){
		return convertTaskDetailsDomainToModelList(taskDetailsDao.getAllTaskDetailsData());	
	}
	
	public List<TaskDetailsModel> convertTaskDetailsDomainToModelList(List<Object[]>taskDetailsList){
		List<TaskDetailsModel> list = new ArrayList<TaskDetailsModel>();
			for(int i=0;i<taskDetailsList.size();i++){			
				Object[] taskDetailsDomain = taskDetailsList.get(i);				
				TaskDetailsModel taskDetailsModel = new TaskDetailsModel();
				taskDetailsModel.setExpectedTime((float) taskDetailsDomain[4]);
				taskDetailsModel.setPriority((String) taskDetailsDomain[5]);
				BigInteger b=new BigInteger(""+taskDetailsDomain[6]);
				taskDetailsModel.setProjectId(b.longValue());
				taskDetailsModel.setProjectName(""+taskDetailsDomain[9]);
				taskDetailsModel.setTaskDescription((String) taskDetailsDomain[7]);
				taskDetailsModel.setTaskName((String) taskDetailsDomain[8]);
				taskDetailsModel.setMilestoneName((String) taskDetailsDomain[10]);
				BigInteger b3=new BigInteger(""+taskDetailsDomain[11]);
				taskDetailsModel.setWithMilestone(b3.intValue());
				BigInteger b2=new BigInteger(""+taskDetailsDomain[12]);
				taskDetailsModel.setMilestoneId(b2.longValue());
				BigInteger b4=new BigInteger(""+taskDetailsDomain[13]);
				taskDetailsModel.setActivityRadioValue(b4.longValue());
				taskDetailsModel.setActivity((String) taskDetailsDomain[14]);
				BigInteger b5=new BigInteger(""+taskDetailsDomain[15]);
				taskDetailsModel.setDocumentId(b5.longValue());
				taskDetailsModel.setEncDocumentId(encryptionService.encrypt(""+b5.longValue()));
				BigInteger b1=new BigInteger(""+taskDetailsDomain[0]);
				taskDetailsModel.setNumId(b1.longValue());
				if((Integer)taskDetailsDomain[2]==1){
					taskDetailsModel.setValid(true);
				}else{
					taskDetailsModel.setValid(false);
				}				
				list.add(taskDetailsModel);
	}
		return list;
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_TASKDETAIL_MST')")
        public long saveTaskDetailsData(TaskDetailsModel taskDetailsModel){
		TaskDetailsDomain taskDetailsDomain = convertTaskDetailsModelToDomain(taskDetailsModel);
		return taskDetailsDao.mergeTaskDetailsMaster(taskDetailsDomain);
	}
	
	public TaskDetailsDomain convertTaskDetailsModelToDomain(TaskDetailsModel taskDetailsModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
		if(taskDetailsModel.getNumId()!=0){		
			taskDetailsDomain =  taskDetailsDao.getTaskDetailsById(taskDetailsModel.getNumId());
		}
		taskDetailsDomain.setTaskName(taskDetailsModel.getTaskName());
		taskDetailsDomain.setTaskDescription(taskDetailsModel.getTaskDescription());
		taskDetailsDomain.setProjectId(taskDetailsModel.getProjectId());
		taskDetailsDomain.setExpectedTime(taskDetailsModel.getExpectedTime());
		taskDetailsDomain.setPriority(taskDetailsModel.getPriority());
		taskDetailsDomain.setDtTrDate(new Date());
		taskDetailsDomain.setNumTrUserId(userInfo.getEmployeeId());
		taskDetailsDomain.setWithMilestone(taskDetailsModel.getWithMilestone());
		taskDetailsDomain.setMilestoneId(taskDetailsModel.getMilestoneId());
		taskDetailsDomain.setActivityId(taskDetailsModel.getActivityRadioValue());
		if(taskDetailsModel.getNumId()==0){	
			taskDetailsDomain.setTaskStatus("New");
		}
		if(taskDetailsModel.isValid()){
			taskDetailsDomain.setNumIsValid(1);
			}else{
				taskDetailsDomain.setNumIsValid(0);

			}
		if(!taskDetailsModel.getTaskDocumentFile().isEmpty()){			
		TaskDocumentDomain detailDomain = new TaskDocumentDomain();
		Calendar calendar = GregorianCalendar.getInstance();	
		String fileName= taskDetailsModel.getProjectId()+"_"+calendar.getTimeInMillis();
		String originalFileName= taskDetailsModel.getTaskDocumentFile().getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFileName);
		String tempFileName = fileName +".";
		fileUploadService.uploadTaskFile(taskDetailsModel.getTaskDocumentFile(), taskDetailsModel.getProjectId(), tempFileName+extension);
		detailDomain.setOriginalDocumentName(originalFileName);
		detailDomain.setProjectId(taskDetailsModel.getProjectId());
		detailDomain.setStrDocumentName(tempFileName+extension);
		taskDetailsDomain.setDocumentId(taskDocumentService.saveTaskDocument(detailDomain));
		}
		
	
		
		return taskDetailsDomain;
	}

	public List<ProjectMilestoneDomain> getMilestoneWithoutActualEndDt(){
		return projectMilestoneDao.getMilestoneWithoutActualEndDt();
	}	
	
	
	/*public List<MilestoneActivityModel> getActivityData(long id){
		return convertMilestoneActivityDomainToModelList(projectMilestoneDao.getActivityData(id));	
	}*/
	
	public List<MilestoneActivityModel> convertMilestoneActivityDomainToModelList(List<Object[]>milestoneList){
		List<MilestoneActivityModel> list = new ArrayList<MilestoneActivityModel>();
			for(int i=0;i<milestoneList.size();i++){			
				Object[] milestoneActivityDomain = milestoneList.get(i);				
				MilestoneActivityModel milestoneActivityModel = new MilestoneActivityModel();
				BigInteger b1=new BigInteger(""+milestoneActivityDomain[0]);
				milestoneActivityModel.setNumId(b1.longValue());
				milestoneActivityModel.setDescription((String) milestoneActivityDomain[1]);	
				milestoneActivityModel.setStrModuleName((String) milestoneActivityDomain[2]);
				list.add(milestoneActivityModel);
	}
		return list;
	}
	
	@Override
	public TaskDocumentDomain getTaskDocumentDetail(long uploadId){
		return  taskDetailsDao.getTaskDocumentDetailsById(uploadId);
	}
	
	public List<TaskDetailsModel> getAllActiveTaskDetailsData(String taskStatus){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		long empid=userInfo.getEmployeeId();
		return convertActiveTaskDetailsDomainToModelList(taskDetailsDao.getAllActiveTaskDetailsData(empid,taskStatus));	
	}
	
	public List<TaskDetailsModel> convertActiveTaskDetailsDomainToModelList(List<TaskDetailsDomain>taskList){
		List<TaskDetailsModel> list = new ArrayList<TaskDetailsModel>();
			for(int i=0;i<taskList.size();i++){
				TaskDetailsDomain taskDetailsDomain = taskList.get(i);
				TaskDetailsModel taskDetailsModel = new TaskDetailsModel();
				taskDetailsModel.setNumId(taskDetailsDomain.getNumId());
				taskDetailsModel.setEncTaskId(encryptionService.encrypt(""+taskDetailsDomain.getNumId()));
				taskDetailsModel.setTaskName(taskDetailsDomain.getTaskName());
				taskDetailsModel.setProjectId(taskDetailsDomain.getProjectId());
				taskDetailsModel.setEncProjectId(encryptionService.encrypt(""+taskDetailsDomain.getProjectId()));
				taskDetailsModel.setExpectedTime(taskDetailsDomain.getExpectedTime());
				taskDetailsModel.setPriority(taskDetailsDomain.getPriority());
				list.add(taskDetailsModel);
	}
		return list;
	}
	@Override
	public TaskDetailsModel getTaskDetailsData(long taskId){
		return convertTaskDetailsDataToModel(taskDetailsDao.getTaskDetailsData(taskId));	
	}
	
	
	public TaskDetailsModel convertTaskDetailsDataToModel(Object[] taskDetailsDomain){
				
				TaskDetailsModel taskDetailsModel = new TaskDetailsModel();
				/*BigInteger b5=new BigInteger(""+taskDetailsDomain[15]);
				taskDetailsModel.setDocumentId(b5.longValue());
				taskDetailsModel.setEncDocumentId(encryptionService.encrypt(""+b5.longValue()));*/
				BigInteger b1=new BigInteger(""+taskDetailsDomain[0]);
				taskDetailsModel.setNumId(b1.longValue());
				taskDetailsModel.setEncTaskId(encryptionService.encrypt(""+b1.longValue()));
				taskDetailsModel.setTaskName((String) taskDetailsDomain[1]);
				BigInteger b2=new BigInteger(""+taskDetailsDomain[2]);
				taskDetailsModel.setNumId(b2.intValue());
				taskDetailsModel.setAssignDate(DateUtils.dateToString((Date)taskDetailsDomain[3]));
				BigInteger b3=new BigInteger(""+taskDetailsDomain[4]);
				taskDetailsModel.setProjectId(b3.longValue());
				taskDetailsModel.setTaskDescription((String) taskDetailsDomain[5]);
				taskDetailsModel.setProjectName((String) taskDetailsDomain[6]);
				taskDetailsModel.setEmployeeName((String) taskDetailsDomain[7]);
				BigInteger b4=new BigInteger(""+taskDetailsDomain[8]);
				taskDetailsModel.setDocumentId(b4.longValue());
				taskDetailsModel.setEncDocumentId(encryptionService.encrypt(""+b4.longValue()));
				
				//BigInteger b5=new BigInteger(""+taskDetailsDomain[9]);
				taskDetailsModel.setExpectedTime(Float.parseFloat(""+taskDetailsDomain[9]));
				taskDetailsModel.setPriority((String) taskDetailsDomain[10]);
	
		return taskDetailsModel;
	}
	
	@Override
	public boolean updateTaskDetailStatus(long taskId,String taskStatus){
		boolean result = false;
		try{
		TaskDetailsDomain domain = taskDetailsDao.getTaskDetailsById(taskId);
		domain.setTaskStatus(taskStatus);
		taskDetailsDao.mergeTaskDetailsMaster(domain);
		result=true;
		}catch(Exception e){
			
		}
		return result;
	}
	
}
