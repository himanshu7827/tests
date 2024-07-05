package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.TimeSheetMasterDao;
import in.pms.master.domain.TaskMasterDomain;
import in.pms.master.domain.SubTaskMasterDomain;
import in.pms.master.domain.DescriptionMasterDomain;
import in.pms.master.model.TimeSheetMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TimeSheetMasterServiceImpl implements TimeSheetMasterService {

	
	@Autowired
	TimeSheetMasterDao timesheetMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	//To save/update record by Himanshu on 10-05-2024
	@Override
	public long saveUpdateDescMaster(TimeSheetMasterModel timesheetMasterModel){
		DescriptionMasterDomain timesheetMasterDomain = convertTimeSheetMasterModelToDomain(timesheetMasterModel);
		return timesheetMasterDao.saveUpdateDescMaster(timesheetMasterDomain);
	}
	
	@Override
	public long saveUpdateDescMasterTest(TimeSheetMasterModel timesheetMasterModel){
		DescriptionMasterDomain timesheetMasterDomain = convertTimeSheetMasterModelToDomain(timesheetMasterModel);
		return timesheetMasterDao.saveUpdateDescMaster(timesheetMasterDomain);
	}
		
	
	//To get all tasks by Himanshu on 10-05-2024
	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_PRIVILEGE')")
	public List<TimeSheetMasterModel> getAllTaskMasterDomain(){
		return convertDescriptionMasterDomainToModelList(timesheetMasterDao.getAllTaskMasterDomain());
	}
	
	
	//To convert domain to model by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> convertDescriptionMasterDomainToModelList(List<TaskMasterDomain> taskMasterList){
	List<TimeSheetMasterModel> list = new ArrayList<TimeSheetMasterModel>();
		for(int i=0;i<taskMasterList.size();i++){
			TaskMasterDomain taskMasterDomain = taskMasterList.get(i);
			TimeSheetMasterModel timesheetMasterModel = new TimeSheetMasterModel();
			
		
			timesheetMasterModel.setTaskId(taskMasterDomain.getTaskId());
			timesheetMasterModel.setTaskName(taskMasterDomain.getTaskName());
			
			
			list.add(timesheetMasterModel);
		}
	return list;
}
	
	//To get all tasks description by Himanshu on 10-05-2024
	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_PRIVILEGE')")
	public List<TimeSheetMasterModel> getAllActiveDescriptionMasterDomain(){
		return convertAllSubTaskDescriptionMasterDomainToModelList(timesheetMasterDao.getAllSubTaskDescriptionMasterDomain());
	}
	
	
	//To convert domain to model by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> convertAllSubTaskDescriptionMasterDomainToModelList(List<DescriptionMasterDomain> descriptionMasterList){
	List<TimeSheetMasterModel> list = new ArrayList<TimeSheetMasterModel>();
		for(int i=0;i<descriptionMasterList.size();i++){
			TimeSheetMasterModel timesheetMasterModel = new TimeSheetMasterModel();
			
			DescriptionMasterDomain subtaskMasterDomain = descriptionMasterList.get(i);
			subtaskMasterDomain.getDescId();	
			subtaskMasterDomain.getSubtaskDescription();
			subtaskMasterDomain.getSubtaskMasterDomain().getSubtaskId();
			subtaskMasterDomain.getSubtaskMasterDomain().getSubtaskName();
			subtaskMasterDomain.getSubtaskMasterDomain().getTaskMasterDomain().getTaskId();
			subtaskMasterDomain.getSubtaskMasterDomain().getTaskMasterDomain().getTaskName();
			
			if(subtaskMasterDomain.getNumIsValid() ==1){
				timesheetMasterModel.setValid(true);
			}else{
				timesheetMasterModel.setValid(false);
			}
		
			timesheetMasterModel.setDescId(subtaskMasterDomain.getDescId());
			timesheetMasterModel.setSubtaskDescription(subtaskMasterDomain.getSubtaskDescription());
			timesheetMasterModel.setSubtaskId(subtaskMasterDomain.getSubtaskMasterDomain().getSubtaskId());			
			timesheetMasterModel.setSubtaskName(subtaskMasterDomain.getSubtaskMasterDomain().getSubtaskName());
			timesheetMasterModel.setTaskId(subtaskMasterDomain.getSubtaskMasterDomain().getTaskMasterDomain().getTaskId());
			timesheetMasterModel.setTaskName(subtaskMasterDomain.getSubtaskMasterDomain().getTaskMasterDomain().getTaskName());;
			
			
			list.add(timesheetMasterModel);
		}
	return list;
}
	
	
	//To get subtasks by task id by Himanshu on 10-05-2024
	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_PRIVILEGE')")
	public List<TimeSheetMasterModel> getSubTaskMasterById(long taskId){
		return convertSubTaskMasterDomainToModelList(timesheetMasterDao.getSubTaskListMasterById(taskId));
	}
	
	
	//To convert domain to model by Himanshu on 10-05-2024
	public List<TimeSheetMasterModel> convertSubTaskMasterDomainToModelList(List<SubTaskMasterDomain> subtaskMasterList){
	List<TimeSheetMasterModel> list = new ArrayList<TimeSheetMasterModel>();
		for(int i=0;i<subtaskMasterList.size();i++){
			SubTaskMasterDomain subtaskMasterDomain = subtaskMasterList.get(i);
			TimeSheetMasterModel timesheetMasterModel = new TimeSheetMasterModel();
			
		
			timesheetMasterModel.setTaskId(subtaskMasterDomain.getSubtaskId());
			timesheetMasterModel.setTaskName(subtaskMasterDomain.getSubtaskName());
			
			
			list.add(timesheetMasterModel);
		}
	return list;
}
	
	
	//To convert domain to model by Himanshu on 10-05-2024
	public DescriptionMasterDomain convertTimeSheetMasterModelToDomain(TimeSheetMasterModel timesheetMasterModel){
		DescriptionMasterDomain descriptionMasterDomain = new DescriptionMasterDomain();
		if(timesheetMasterModel.getDescId()!=0){				
			descriptionMasterDomain =  timesheetMasterDao.getDescriptionMaster(timesheetMasterModel.getDescId());
		}
		
		descriptionMasterDomain.setDtTrDate(new Date());
		
		if(timesheetMasterModel.isValid()){
			descriptionMasterDomain.setNumIsValid(1);
		}else{
			descriptionMasterDomain.setNumIsValid(0);
		}

		descriptionMasterDomain.setSubtaskDescription(timesheetMasterModel.getSubtaskDescription());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		descriptionMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		
		SubTaskMasterDomain subtaskMasterDomain= timesheetMasterDao.getSubTaskMasterById(timesheetMasterModel.getSubtaskId());		
		descriptionMasterDomain.setSubtaskMasterDomain(subtaskMasterDomain);
		return descriptionMasterDomain;
	}
	

}
