package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.OrganisationMasterDao;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.OrganisationMasterDomain;
import in.pms.master.model.GroupMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class GroupMasterServiceImpl implements GroupMasterService {

	@Autowired
	OrganisationMasterDao organisationMasterDao;
	
	@Autowired
	GroupMasterDao groupMasterDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public long saveUpdateGroupMaster(GroupMasterModel groupMasterModel){
		GroupMasterDomain groupMasterDomain = convertGroupMasterModelToDomain(groupMasterModel);
		return groupMasterDao.saveUpdateGroupMaster(groupMasterDomain);
	}
		
	@Override
	public String checkDuplicateGroupName(GroupMasterModel groupMasterModel){
		String result=	"";
		GroupMasterDomain groupMasterDomain = groupMasterDao.getGroupMasterByNameandOrganisation(groupMasterModel.getGroupName(),groupMasterModel.getOrganisationId());
		
		 if(null == groupMasterDomain){
				return null; 
			 }else if(groupMasterModel.getNumId() != 0){
				 if(groupMasterDomain.getNumId() == groupMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Group with same name already exist with Id "+groupMasterDomain.getNumId();
				 }
			 }else{
				if(groupMasterDomain.getNumIsValid() == 0){
					result = "Group Details already exist with Id "+groupMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Group Details already exist with Id "+groupMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public GroupMasterDomain getGroupMasterDomainById(long numId){
		return groupMasterDao.getGroupMasterById(numId);
	}
	
	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_PRIVILEGE')")
	public List<GroupMasterModel> getAllGroupMasterDomain(){
		return convertGroupMasterDomainToModelList(groupMasterDao.getAllGroupMasterDomain());
	}
	
	@Override
	public List<GroupMasterModel> getAllActiveGroupMasterDomain(){
		return convertGroupMasterDomainToModelList(groupMasterDao.getAllActiveGroupMasterDomain());
	}
	
	// added below method by varun to get all technical  group
	@Override
	public List<GroupMasterModel> getAllTechnicalActiveGroupMasterDomain(){
		return convertGroupMasterDomainToModelList(groupMasterDao.getAllTechnicalActiveGroupMasterDomain());
	}
	
	@Override
	public List<GroupMasterModel> getAllActiveGrpMasterDomain(long orgId){
		return convertGroupMasterDomainToModelList(groupMasterDao.getAllActiveGrpMasterDomain(orgId));
	}

	@Override
	public List<GroupMasterModel> getAllActiveGroupMasterDomainByUserInfo(){
		return convertGroupMasterDomainToModelList(groupMasterDao.getAllActiveGroupMasterDomainByUserInfo());
	}
	
	public GroupMasterDomain convertGroupMasterModelToDomain(GroupMasterModel groupMasterModel){
		GroupMasterDomain groupMasterDomain = new GroupMasterDomain();
		if(groupMasterModel.getNumId()!=0){				
			groupMasterDomain =  groupMasterDao.getGroupMasterById(groupMasterModel.getNumId());
		}
		groupMasterDomain.setContactNumber(groupMasterModel.getContactNumber());
		groupMasterDomain.setDtTrDate(new Date());
		
		if(groupMasterModel.isValid()){
			groupMasterDomain.setNumIsValid(1);
		}else{
			groupMasterDomain.setNumIsValid(0);
		}
		groupMasterDomain.setGroupAddress(groupMasterModel.getGroupAddress());
		groupMasterDomain.setGroupName(groupMasterModel.getGroupName());
		groupMasterDomain.setGroupShortName(groupMasterModel.getGroupShortName());
		groupMasterDomain.setBgColor(groupMasterModel.getBgColor());
		groupMasterDomain.setStrCode(groupMasterModel.getStrCode());
		
		OrganisationMasterDomain organisationMasterDomain= organisationMasterDao.getOrganisationMasterById(groupMasterModel.getOrganisationId());		
		groupMasterDomain.setOrganisationMasterDomain(organisationMasterDomain);
		
		/*------ Get the value of proposal year check and set yes(1) or no(0) [12-04-2024]----*/
		if(groupMasterModel.isProposalYearCheck()){
			groupMasterDomain.setProposalYearCheck(1);;
		}else{
			groupMasterDomain.setProposalYearCheck(0);
		}
		/*----- End of Get the value of proposal year check and set yes(1) or no(0) [12-04-2024]----*/
		return groupMasterDomain;
	}
	
	public List<GroupMasterModel> convertGroupMasterDomainToModelList(List<GroupMasterDomain> groupMasterList){
		List<GroupMasterModel> list = new ArrayList<GroupMasterModel>();
			for(int i=0;i<groupMasterList.size();i++){
				GroupMasterDomain groupMasterDomain = groupMasterList.get(i);
				GroupMasterModel groupMasterModel = new GroupMasterModel();
				
				if(groupMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+groupMasterDomain.getNumId());
					groupMasterModel.setEncGroupId(encryptedId);
				}
				groupMasterModel.setNumId(groupMasterDomain.getNumId());
				if(groupMasterDomain.getNumIsValid() ==1){
					groupMasterModel.setValid(true);
				}else{
					groupMasterModel.setValid(false);
				}
			
				groupMasterModel.setStrCode(groupMasterDomain.getStrCode());
				groupMasterModel.setContactNumber(groupMasterDomain.getContactNumber());
				groupMasterModel.setGroupAddress(groupMasterDomain.getGroupAddress());
				groupMasterModel.setGroupName(groupMasterDomain.getGroupName());
				groupMasterModel.setGroupShortName(groupMasterDomain.getGroupShortName());
				OrganisationMasterDomain organisationMasterDomain =groupMasterDomain.getOrganisationMasterDomain();
				groupMasterModel.setOrganisationId(organisationMasterDomain.getNumId());
				groupMasterModel.setOrganisationName(organisationMasterDomain.getOrganisationName());
				groupMasterModel.setOrganisationAddress(organisationMasterDomain.getOrganisationAddress());
				groupMasterModel.setBgColor(groupMasterDomain.getBgColor());
				groupMasterModel.setShowProjects(String.valueOf(groupMasterDomain.getShowProjects()));
				
				/*----- Set the value of proposal year check yes(1) or no(0) [12-04-2024]----*/
				if(groupMasterDomain.getProposalYearCheck()==1){
					groupMasterModel.setProposalYearCheck(true);
				}else{
					groupMasterModel.setProposalYearCheck(false);	
				}
				/*----- End of Set the value of proposal year check yes(1) or no(0) [12-04-2024]----*/
				list.add(groupMasterModel);
	}
		return list;
	}

	public GroupMasterModel convertGroupMasterDomainToModel(GroupMasterDomain groupMasterDomain){
		GroupMasterModel groupMasterModel = new GroupMasterModel();
	
		if(groupMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+groupMasterDomain.getNumId());
			groupMasterModel.setEncGroupId(encryptedId);
		}
		groupMasterModel.setNumId(groupMasterDomain.getNumId());
		if(groupMasterDomain.getNumIsValid() ==1){
			groupMasterModel.setValid(true);
		}else{
			groupMasterModel.setValid(false);
		}
	
		groupMasterModel.setContactNumber(groupMasterDomain.getContactNumber());
		groupMasterModel.setOrganisationAddress(groupMasterDomain.getGroupAddress());
		groupMasterModel.setOrganisationName(groupMasterDomain.getGroupName());
		groupMasterModel.setGroupShortName(groupMasterDomain.getGroupShortName());
		groupMasterModel.setBgColor(groupMasterDomain.getBgColor());
		
		OrganisationMasterDomain organisationMasterDomain =groupMasterDomain.getOrganisationMasterDomain();
		groupMasterModel.setOrganisationId(organisationMasterDomain.getNumId());
		groupMasterModel.setOrganisationName(organisationMasterDomain.getOrganisationName());
		groupMasterModel.setOrganisationAddress(organisationMasterDomain.getOrganisationAddress());
		
		return groupMasterModel;		
	}
	
	@Override
	public List<GroupMasterDomain> getGroupDomainById(long organisationId) {
		List<GroupMasterDomain> groupMasterDomain= groupMasterDao.getGroupMasterByOrganisationId(organisationId);

		return groupMasterDomain;
		}

	@Override
	public List<String> getdistinctGroupNames() {
		return groupMasterDao.getdistinctGroupNames();
		
	}
	@Override
	public String getDistinctGroupsForOrganisation(String assignedGroups){
		return groupMasterDao.getGroupNames(assignedGroups);
	}
	@Override
	public String getDistinctGroupShortNamesForOrganisation(String assignedGroups){
		return groupMasterDao.getGroupShortNames(assignedGroups);
	}
	
	
	@Override
	public List<String> getdistinctGroupNamesWithColor(){
		return groupMasterDao.getdistinctGroupNamesWithColor();
	}
	
	@Override
	public List<GroupMasterModel> getGroupMasterByIds(List<Long> ids){
		return convertGroupMasterDomainToModelList(groupMasterDao.getGroupMasterByIds(ids));
	}
	
	//Purpose TImesheet Report for PMO and ED
	@Override
	public List<GroupMasterModel> getGroupMasterDomainByOrg(long organisationId){
		List<GroupMasterModel> list = convertGroupMasterDomainToModelList(groupMasterDao.getGroupMasterDomainByOrg(organisationId));
		return list;
	}
}
