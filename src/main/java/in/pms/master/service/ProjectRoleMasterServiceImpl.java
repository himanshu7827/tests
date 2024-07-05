package in.pms.master.service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectRoleMasterDao;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.domain.ProjectInvoiceMasterDomain;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.ProjectRoleMasterForm;
import in.pms.master.model.RoleMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
public class ProjectRoleMasterServiceImpl implements ProjectRoleMasterService {
	
	@Autowired
	RoleMasterDao roleMasterDao;
	
	@Autowired
	ProjectRoleMasterDao projectRoleMasterDao;


	@Override
	/*@PreAuthorize("hasAuthority('WRITE_EMPLOYEE_ROLE_MST')")*/
	public long saveUpdateProjectRoleMaster(ProjectRoleMasterForm projectRoleMasterForm){
		ProjectRolesMaster roleMasterDomain = convertRoleMasterModelToDomain(projectRoleMasterForm);
		return projectRoleMasterDao.saveUpdateProjectRoleMaster(roleMasterDomain);
	}
	
	public ProjectRolesMaster convertRoleMasterModelToDomain(ProjectRoleMasterForm projectRoleMasterForm){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ProjectRolesMaster projectRolesMaster = new ProjectRolesMaster();
		if(projectRoleMasterForm.getNumId()!=0){		
			projectRolesMaster =  projectRoleMasterDao.getProjectRoleById(projectRoleMasterForm.getNumId());
		}
		projectRolesMaster.setRoleName(projectRoleMasterForm.getRoleName());
		projectRolesMaster.setDtTrDate(new Date());
		projectRolesMaster.setNumTrUserId(userInfo.getEmployeeId());
		projectRolesMaster.setHierarchy(projectRoleMasterForm.getHierarchy());
		
		return projectRolesMaster;
	}

	
	@Override
	public int checkDuplicateProjectRoleName(ProjectRoleMasterForm projectRoleMasterForm){
		
		 int result=0;
		 result=projectRoleMasterDao.getProjectRoleByName(projectRoleMasterForm.getRoleName(),projectRoleMasterForm.getNumId());
		
		 return result;
	}

	@Override
	public RoleMasterModel getRoleById(long numId) {
		RoleMasterDomain roleMasterDomain= roleMasterDao.getRoleMasterDomainById(numId);
		if(null != roleMasterDomain){
		return convertRoleMasterDomaintoModel(roleMasterDomain);
		}else{
			return null;
		}
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_ROLE')")
	public List<ProjectRoleMasterForm> getAllProjectRole(){
		return convertRoleDomainToModelList(projectRoleMasterDao.getAllProjectRole());			
	}

	public List<ProjectRoleMasterForm> convertRoleDomainToModelList(List<ProjectRolesMaster> roleDataList){
		List<ProjectRoleMasterForm> list = new ArrayList<ProjectRoleMasterForm>();
			for(int i=0;i<roleDataList.size();i++){
				ProjectRolesMaster roleMasterDomain = roleDataList.get(i);
				ProjectRoleMasterForm roleMasterModel = new ProjectRoleMasterForm();
				roleMasterModel.setRoleName(roleMasterDomain.getRoleName());
				roleMasterModel.setNumId(roleMasterDomain.getNumId());
				roleMasterModel.setHierarchy(roleMasterDomain.getHierarchy());

				list.add(roleMasterModel);
	}
		return list;
	}

	@Override
	public List<RoleMasterModel> getAllActiveRoleMasterDomain() {		
		return convertRoleMasterDomaintoModel(roleMasterDao.getAllActiveRoleMasterDomain());
	}

	public void deleteProjectRoleData(ProjectRoleMasterForm projectRoleMasterForm) {
		int count= projectRoleMasterForm.getNumIds().length;
		long[] roleMaster_ids= new long[count];
		roleMaster_ids= projectRoleMasterForm.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ProjectRolesMaster projectRolesMaster= new ProjectRolesMaster();
			projectRolesMaster.setNumId(roleMaster_ids[i]);			
			Date date= new Date();
			projectRolesMaster.setDtTrDate(date);
			projectRoleMasterDao.deleteProjectRoleMaster(projectRolesMaster);			
		}	
}
	
	public RoleMasterModel convertRoleMasterDomaintoModel(RoleMasterDomain roleMasterDomain){
		RoleMasterModel roleMasterModel = new RoleMasterModel();
		roleMasterModel.setAccessLevel(roleMasterDomain.getAccessLevel());
		roleMasterModel.setNumId(roleMasterDomain.getNumId());
		roleMasterModel.setRoleName(roleMasterDomain.getRoleName());
		roleMasterModel.setRoleShortName(roleMasterDomain.getRoleShortName());
		roleMasterModel.setValid(roleMasterDomain.getNumIsValid()== 1?true:false);
		return roleMasterModel;
	}
	
	public List<RoleMasterModel> convertRoleMasterDomaintoModel(List<RoleMasterDomain> list){
		List<RoleMasterModel> result = new ArrayList<RoleMasterModel>();
		for(int i=0;i<list.size();i++){
			RoleMasterDomain roleMasterDomain = list.get(i);
			RoleMasterModel roleMasterModel = new RoleMasterModel();
			roleMasterModel.setAccessLevel(roleMasterDomain.getAccessLevel());
			roleMasterModel.setNumId(roleMasterDomain.getNumId());
			roleMasterModel.setRoleName(roleMasterDomain.getRoleName());
			roleMasterModel.setRoleShortName(roleMasterDomain.getRoleShortName());
			roleMasterModel.setValid(roleMasterDomain.getNumIsValid()== 1?true:false);
			result.add(roleMasterModel);
		}
		return result;
	}
	
	@Override
	 public RoleMasterDomain getRoleDomainById(long numRoleId){
		return roleMasterDao.getRoleDomainById(numRoleId);
	}
	
	@Override
	@PreAuthorize("hasAuthority('PROJECT_TEAM_MAPPING')")
	public List<RoleMasterModel> getApplicationSpecificRoles(){
		List<RoleMasterDomain> rolesList= roleMasterDao.getApplicationSpecificRoles();
		if(null != rolesList){
		return convertRoleMasterDomaintoModel(rolesList);
		}else{
			return null;
		}		
	}

	@Override
	public RoleMasterDomain getRoleMasterById(long numId) {
		RoleMasterDomain roleMasterDomain= roleMasterDao.getRoleMasterDomainById(numId);
		return roleMasterDomain;
	}
	
}
