package in.pms.master.service;



import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.model.ProjectPaymentScheduleMasterModel;

public interface ProjectPaymentScheduleMasterService {

	@Transactional	
	 public long saveUpdateProjectPaymentScheduleMaster(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel);		
	
	 public String checkDuplicateProjectPaymentScheduleSeqNo(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel);
	 
	 public ProjectPaymentScheduleMasterModel getProjectPaymentScheduleMasterDomainById(long numId);
	
	 public List<ProjectPaymentScheduleMasterModel> getAllProjectPaymentScheduleMasterDomain();
	
	 public List<ProjectPaymentScheduleMasterModel> getAllActiveProjectPaymentScheduleMasterDomain();
	
	@Transactional
	public long deleteProjectPaymentSchedule(ProjectPaymentScheduleMasterModel projectPaymentScheduleMasterModel);


	public List<ProjectPaymentScheduleMasterModel> getProjectPaymentScheduleByProjectId(long projectId);
	
	public List<ProjectPaymentScheduleMasterModel> getProjectPaymentScheduleByMilestoneId(long numMilestoneId);

	public List<ProjectPaymentScheduleMasterModel> getScheduledPaymentByProjectId(
			long projectId);

	List<ProjectPaymentScheduleMasterModel> getPaymentScheduleWithReceivedAmount(long projectId);
	
	//public List<ProjectPaymentScheduleMasterModel> getAllActiveProjectMasterDomain();
	
	///Smrita  Count project Revenue based on financial year	
    @Transactional
    public double getTotalProjectsRevenue(Date startRange, Date endRange);

	public int getMaxProjectPaymentNumberByProjectID(long projectId); //Function added by devesh on 02-04-24 to get max payment sequence number for a project
}
