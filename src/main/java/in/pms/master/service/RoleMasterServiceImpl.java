package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.login.model.RoleReportModel;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.RoleMasterModel;



@Service
public class RoleMasterServiceImpl implements RoleMasterService {
	
	@Autowired
	RoleMasterDao roleMasterDao;


	@Override
	@PreAuthorize("hasAuthority('WRITE_EMPLOYEE_ROLE_MST')")
	public long saveUpdateRoleMaster(RoleMasterModel roleMasterModel){
		RoleMasterDomain roleMasterDomain = convertRoleMasterModelToDomain(roleMasterModel);
		return roleMasterDao.saveUpdateRoleMaster(roleMasterDomain);
	}
	
	public RoleMasterDomain convertRoleMasterModelToDomain(RoleMasterModel roleMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		RoleMasterDomain roleMasterDomain = new RoleMasterDomain();
		if(roleMasterModel.getNumId()!=0){		
			roleMasterDomain =  roleMasterDao.getRoleMasterDomainById(roleMasterModel.getNumId());
		}
		roleMasterDomain.setRoleName(roleMasterModel.getRoleName());
		roleMasterDomain.setRoleShortName(roleMasterModel.getRoleShortName());
		roleMasterDomain.setAccessLevel(roleMasterModel.getAccessLevel());
		roleMasterDomain.setDtTrDate(new Date());
		roleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		roleMasterDomain.setBgColor(roleMasterModel.getBgColor());
		if(roleMasterModel.isValid()){
			roleMasterDomain.setNumIsValid(1);
			}else{
				roleMasterDomain.setNumIsValid(0);
			}		
		
		return roleMasterDomain;
	}

	
	@Override
	public String checkDuplicateRoleName(RoleMasterModel roleMasterModel){
		String result = "";
		RoleMasterDomain roleMasterDomain = roleMasterDao.getRoleMasterByName(roleMasterModel.getRoleName());
	
		 if(null == roleMasterDomain){
			return null; 
		 }else if(roleMasterModel.getNumId() != 0){
			 if(roleMasterDomain.getNumId() == roleMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Role with same name already exist with Id "+roleMasterDomain.getNumId();
			 }
		 }
		 else{
				if(roleMasterDomain.getNumIsValid() == 0){
					result = "Budget Already Registered with Id "+roleMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Budget Already Registered with Id "+roleMasterDomain.getNumId();
				}			
			 }
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
	/*@PreAuthorize("hasAuthority('READ_EMPLOYEE_ROLE_MST')")*/
	public List<RoleMasterModel> getAllRoleMaster(){
		return convertRoleDomainToModelList(roleMasterDao.getAllRoleMasterDomain());			
	}

	public List<RoleMasterModel> convertRoleDomainToModelList(List<RoleMasterDomain> roleDataList){
		List<RoleMasterModel> list = new ArrayList<RoleMasterModel>();
			for(int i=0;i<roleDataList.size();i++){
				RoleMasterDomain roleMasterDomain = roleDataList.get(i);
				RoleMasterModel roleMasterModel = new RoleMasterModel();
				roleMasterModel.setRoleName(roleMasterDomain.getRoleName());
				roleMasterModel.setRoleShortName(roleMasterDomain.getRoleShortName());
				roleMasterModel.setNumId(roleMasterDomain.getNumId());
				roleMasterModel.setAccessLevel(roleMasterDomain.getAccessLevel());
				roleMasterModel.setHierarchy(roleMasterDomain.getHierarchy());
				roleMasterModel.setBgColor(roleMasterDomain.getBgColor());
				if(roleMasterDomain.getNumIsValid()==1){
					roleMasterModel.setValid(true);
				}else{
					roleMasterModel.setValid(false);
				}
	
				list.add(roleMasterModel);
	}
		return list;
	}

	@Override
	public List<RoleMasterModel> getAllActiveRoleMasterDomain() {		
		return convertRoleMasterDomaintoModel(roleMasterDao.getAllActiveRoleMasterDomain());
	}
	@Override
	public List<RoleMasterModel> getAllActiveRoleMasterDomainForMapping() {		
		return convertRoleMasterDomaintoModel(roleMasterDao.getAllActiveRoleMasterDomainForMapping());
	}
	public void deleteRoleData(RoleMasterModel roleMasterModel) {
		int count= roleMasterModel.getNumIds().length;
		long[] roleMaster_ids= new long[count];
		roleMaster_ids= roleMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			RoleMasterDomain roleMasterDomain= new RoleMasterDomain();
			roleMasterDomain.setNumId(roleMaster_ids[i]);			
			Date date= new Date();
			roleMasterDomain.setDtTrDate(date);
			roleMasterDao.deleteRoleMaster(roleMasterDomain);			
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
	
	
	 @Override
	 
	 @PreAuthorize("hasAuthority('ACCESS_ROLE_REPORT')")
	 public List<RoleReportModel> getAllRoleDetails() { 
            List<RoleMasterDomain> roleLists = roleMasterDao.getAllActiveRoleMasterDomain(); 
	        List<RoleReportModel> roleReportModel1 =new ArrayList<RoleReportModel>();
		
		  for (int i = 0; i < roleLists.size(); i++) { RoleReportModel roleReportModel
		  = new RoleReportModel();
		  roleReportModel.setRoleId(roleLists.get(i).getNumId());
		  roleReportModel.setRoleName(roleLists.get(i).getRoleName());
		  roleReportModel1.add(roleReportModel); }
		 
	  
	 return roleReportModel1; 
	 }
	 
	 @Override
	 public String getReportByRoleId(Long id) {
			return roleMasterDao.getReportByRoleId(id);
		}
	 
	 @Override
		public String checkDuplicateRoleData(RoleReportModel roleReportModel){
			String result = "";
			RoleMasterDomain role = roleMasterDao.getRoleByName(roleReportModel.getRoleName());
		
			 if(null == role){
				return null; 
			 }else if(role.getNumId() == roleReportModel.getRoleId()){
					 return null; 
				 }else{
					 result = "Role with same name already exist with Id "+role.getNumId();			 
			 }
			 
			return result;
		}
	 
	 @Override
		/* @PreAuthorize("hasAuthority('WRITE_ROLEREPORT_MST')") */
	        public long saveRoleData(RoleReportModel roleReportModel){
			RoleMasterDomain role = convertRoleReportModelToDomain(roleReportModel);//change according to role report
			return roleMasterDao.mergeRoleMaster(role);
		}
		
		public RoleMasterDomain convertRoleReportModelToDomain(RoleReportModel roleReportModel){			
			RoleMasterDomain role = new RoleMasterDomain();
			if(roleReportModel.getRoleId()!=0){		
				role =  roleMasterDao.getRoleById(roleReportModel.getRoleId());
			}
			role.setReports(roleMasterDao.getAllActiveReportDomainId(roleReportModel.getSelectedReport()));
			role.setRoleName(roleReportModel.getRoleName());
			
			return role;
		}
}
