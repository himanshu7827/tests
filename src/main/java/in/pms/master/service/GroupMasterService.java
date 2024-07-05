package in.pms.master.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.model.GroupMasterModel;
@Service

public interface GroupMasterService {


	@Transactional
	public long saveUpdateGroupMaster(GroupMasterModel groupMasterModel);
	
	public String checkDuplicateGroupName(GroupMasterModel GroupMasterModel);
	
	public GroupMasterDomain getGroupMasterDomainById(long numId);
	
	public List<GroupMasterModel> getAllGroupMasterDomain();
	
	public List<GroupMasterModel> getAllActiveGroupMasterDomain();
	
	public List<GroupMasterModel> getAllActiveGrpMasterDomain(long orgId);

	
	public List<GroupMasterDomain> getGroupDomainById(long organisationId);
	 public List<String> getdistinctGroupNames();

	 public String getDistinctGroupsForOrganisation(String assignedGroups);
	 public String getDistinctGroupShortNamesForOrganisation(String assignedGroups);
	 public List<String> getdistinctGroupNamesWithColor();
	 public List<GroupMasterModel> getAllActiveGroupMasterDomainByUserInfo();
	 public List<GroupMasterModel> getGroupMasterByIds(List<Long> ids);
	 
	 //Purpose TImesheet Report for PMO and ED
	 public List<GroupMasterModel> getGroupMasterDomainByOrg(long organisationId);

	 public List<GroupMasterModel> getAllTechnicalActiveGroupMasterDomain();

}
