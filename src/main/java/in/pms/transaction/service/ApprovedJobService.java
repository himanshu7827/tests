package in.pms.transaction.service;

import in.pms.transaction.model.ApprovedJobModel;
import in.pms.transaction.model.EmployeeApprovedJobMappingModel;
import in.pms.transaction.model.JobTitleModel;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface ApprovedJobService {
	
	@Transactional
	public void saveApprovedJobData(ApprovedJobModel approvedJobModel);
	

	@Transactional
	public Map<String,List<ApprovedJobModel>> getApprovedJobsByProjectId(long projectId);
	
	public  List getApprovedJobsCount(EmployeeApprovedJobMappingModel employeeApprovedJobMappingModel);
	
	public void viewApprovedJobAuthorityCheck();

	@Transactional
	public int  checkUniqueJobCode(ApprovedJobModel approvedJobModel);

	
	@Transactional
	public List<ApprovedJobModel> getApprovedJobsByProjectIdCreated(
			long projectId);
	
	@Transactional
	public void saveJobTitle(JobTitleModel jobTitleModel);


	void jobTitleAuthorityCheck();



}
