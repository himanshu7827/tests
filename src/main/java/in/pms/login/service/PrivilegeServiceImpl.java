package in.pms.login.service;

import in.pms.login.dao.PrivilegeDao;
import in.pms.login.domain.Privilege;
import in.pms.login.domain.Role;
import in.pms.login.model.PrivilegeModel;
import in.pms.login.util.UserInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	
	@Autowired
	PrivilegeDao privilegeDao;
	
	@Autowired
	public List<Privilege> privilegeName(){
		return privilegeDao.privilegeName();
	}

	
	public String checkDuplicatePrivilegeData(PrivilegeModel privilegeModel) {
		String result = "";
		Privilege privilege = privilegeDao.getPrivilegeByName(privilegeModel.getPrivlegeName());
	
		 if(null == privilege){
			return null; 
		 }else if(privilege.getId() == privilegeModel.getPrivlegeId()){
				 return null; 
			 }else{
				 result = "Privilege with same name already exist with Id "+privilege.getId();			 
		 }
		 
		return result;
	}


	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE_MST')")
	public long savePrivilegeData(PrivilegeModel privilegeModel) {
		Privilege privilege = convertPrivilegeModelToDomain(privilegeModel);
		return privilegeDao.save(privilege).getId();
	}


	private Privilege convertPrivilegeModelToDomain(
			PrivilegeModel privilegeModel) {
		Privilege privilege = new Privilege();
		if(privilegeModel.getPrivlegeId()!=0){		
			privilege =  privilegeDao.getPrivilegeById(privilegeModel.getPrivlegeId());
		}
		privilege.setName(privilegeModel.getPrivlegeName());
		
		return privilege;
	}
}
