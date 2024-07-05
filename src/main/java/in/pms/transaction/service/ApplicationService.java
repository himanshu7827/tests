package in.pms.transaction.service;

import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;

import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProposalMasterModel;
import in.pms.transaction.model.ApplicationModel;

public interface ApplicationService {

	@Transactional
	public ApplicationModel saveUpdateApplication(ApplicationModel applicationModel);
	
	public List<ProjectMasterForm> getApplicationByGroupName(String groupName);

	@Transactional
	public ApplicationModel getApplicationById(long applicationId);
	
	public List<ApplicationModel>getAllApplicaionData();
	
	public List<ApplicationModel> getProposalDetailByGruopId(long groupId);
	JSONArray getProposalCountByGroup();
	
	@Transactional
	public String createApplicationCopy(long applicationId);
	
	@Transactional
	public long getProposalIdByProjectId(long projectId);

	@Transactional
	public int deleteApplication(long appicationId);
	
	public List<Object[]> getProposalDetailByGruopIdnew(long groupId);//Added by devesh on 23/08/23 for accessing group id of proposal master table if it is non-zero

	public List<Object[]> getAllApplicationDataList();//Added by devesh on 25/08/23 for accessing group id of proposal master table if it is non-zero

	public long getProposalIdByProjectIdAndDate(long projectId, String startRange, String endRange); /*Added by devesh on 25-01-24 to get proposal with date range date range*/
}
