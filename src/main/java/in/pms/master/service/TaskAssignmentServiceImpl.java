package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.TaskAssignmentDao;
import in.pms.master.domain.TaskAssignmentDomain;
import in.pms.master.domain.TaskDocumentDomain;
import in.pms.master.model.TaskAssignmentModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService{
	
	@Autowired
	TaskAssignmentDao taskAssignmentDao;
	@Autowired
	FileUploadService fileUploadService;
	@Autowired
	TaskDocumentService taskDocumentService;
	@Autowired
	EncryptionService encryptionService ;
	
	@Override
	public List<TaskAssignmentModel> getAllTaskAssignmentData(){
		return convertTaskAssignmentDomainToModelList(taskAssignmentDao.getAllTaskAssignmentData());			
	}
	
	@Override
	public List<TaskAssignmentModel> getActiveTaskAssignmentData(){
		return convertTaskAssignmentDomainToModelList(taskAssignmentDao.getActiveTaskAssignmentData());			
	}

	public List<TaskAssignmentModel> convertTaskAssignmentDomainToModelList(List<TaskAssignmentDomain> taskAssignmentList){
		List<TaskAssignmentModel> list = new ArrayList<TaskAssignmentModel>();
			for(int i=0;i<taskAssignmentList.size();i++){
				TaskAssignmentDomain taskAssignmentDomain = taskAssignmentList.get(i);
				TaskDocumentDomain detailDomain = new TaskDocumentDomain();
				TaskAssignmentModel taskAssignmentModel = new TaskAssignmentModel();
				taskAssignmentModel.setNumId(taskAssignmentDomain.getNumId());
				//taskAssignmentModel.setStrAssignedName(taskAssignmentDomain.getAssignedName());
				taskAssignmentModel.setDescription(taskAssignmentDomain.getDescription());
				taskAssignmentModel.setDocumentId(detailDomain.getNumId());
				taskAssignmentModel.setEncDocumentId(encryptionService.encrypt(""+detailDomain.getNumId()));
				
				list.add(taskAssignmentModel);
	}
		return list;
	}
	
	
	@Override
        public long saveTaskAssignmentData(TaskAssignmentModel taskAssignmentModel){
		TaskAssignmentDomain taskAssignmentDomain = convertTaskAssignmentModelToDomain(taskAssignmentModel);
		return taskAssignmentDao.mergeTaskAssignmentMaster(taskAssignmentDomain);
	}
	
	public TaskAssignmentDomain convertTaskAssignmentModelToDomain(TaskAssignmentModel taskAssignmentModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		TaskAssignmentDomain taskAssignmentDomain = new TaskAssignmentDomain();
		if(taskAssignmentModel.getNumId()!=0){		
			taskAssignmentDomain =  taskAssignmentDao.getAllTaskAssignmentById(taskAssignmentModel.getNumId());
		}
		taskAssignmentDomain.setAssignedTo(userInfo.getEmployeeId());
		taskAssignmentDomain.setDescription(taskAssignmentModel.getDescription());
		taskAssignmentDomain.setDtTrDate(new Date());
		taskAssignmentDomain.setNumTrUserId(userInfo.getEmployeeId());
		/*if(!taskAssignmentModel.getTaskDocumentFile().isEmpty()){
			String encProjectId=taskAssignmentModel.getEncProjectId();
			String encId=encryptionService.dcrypt(encProjectId);
			long numProjectId = Long.parseLong(encId);
		TaskDocumentDomain detailDomain = new TaskDocumentDomain();
		Calendar calendar = GregorianCalendar.getInstance();	
		String fileName= numProjectId+"_"+calendar.getTimeInMillis();
		String originalFileName= taskAssignmentModel.getTaskDocumentFile().getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFileName);
		String tempFileName = fileName +".";
		fileUploadService.uploadTaskFile(taskAssignmentModel.getTaskDocumentFile(), numProjectId, tempFileName+extension);
		detailDomain.setOriginalDocumentName(originalFileName);
		detailDomain.setProjectId(numProjectId);
		detailDomain.setStrDocumentName(tempFileName+extension);
		taskAssignmentDomain.setDocumentId(taskDocumentService.saveTaskDocument(detailDomain));
		}*/
		
		return taskAssignmentDomain;
	}


	
}
