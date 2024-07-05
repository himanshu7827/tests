package in.pms.master.service;

import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.EmployeeDefaultRoleMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.DesignationForm;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import in.pms.global.dao.GlobalDao;
//import in.pms.global.domain.DesignationMaster;
//import in.pms.global.model.DesignationForm;

@Service
public class GlobalServiceImplimentation implements GlobalService  {


	@Autowired
	private GlobalDao gDao;
	
	@Autowired
	private RoleMasterDao roleMasterDao;
	
	public void saveDesignation(DesignationForm DesigForm)
	{
		DesignationMaster addDesignation =new DesignationMaster();
		addDesignation.setDesignationName((WordUtils.capitalize(DesigForm.getStrDesignationName())));		
		addDesignation.setDesignationShortCode(DesigForm.getStrDesignationShortCode());
		addDesignation.setDescription(DesigForm.getStrDesription());
		addDesignation.setNumIsValid(1);
		addDesignation.setNumTrUserId(1);
		addDesignation.setDtTrDate(new Date());
		gDao.saveDesignation(addDesignation);
		System.out.println("serviceimp");
	}
	
	public void updateDesignation(DesignationForm DesigForm) 
	{
		gDao.updateDesignation(DesigForm);

	}
	
	public void deleteDesignation(DesignationForm DesigForm) 
	{  
		gDao.deleteDesignation(DesigForm);
		
	}
	
	public List<DesignationMaster> getDesignationlist() {

		return gDao.getDesignationlist();
	}

	public DesignationForm getExistUser(String userName)
	{
		String userExist="";
		DesignationForm regForm =new DesignationForm();
		List<DesignationMaster> manReg = gDao.getUserExist(userName);
		if(manReg.size()>0)
		{
			userExist="YES";
			regForm.setNumIsValid(manReg.get(0).getNumIsValid());
			
			
		}
		regForm.setUserExist(userExist);
		return regForm;
	}

	
	public boolean checkDuplicateDefaultRole(int empId) {
		boolean isDuplicateForMofification=false;
		List<EmployeeDefaultRoleMasterDomain> result_list= gDao.isDuplicateDefaultRoleMaster(empId);
		  if((result_list.size()>0))
		  {
		  	isDuplicateForMofification=true;
		  }
	   return isDuplicateForMofification;
	}

	
	public void setDefaultRole(int empId, int roleId, int projectId, int grpId,
			int organizationId) {
		EmployeeDefaultRoleMasterDomain dfm=new EmployeeDefaultRoleMasterDomain();
		dfm.setNumEmpId(empId);
		dfm.setNumGroupId(grpId);
		dfm.setNumProjectId(projectId);
		dfm.setNumOrganisationId(organizationId);
		RoleMasterDomain roleMasterDomain = new RoleMasterDomain();
		if(roleId!=0){		
			roleMasterDomain =  roleMasterDao.getRoleMasterDomainById(roleId);
		}
		dfm.setRoleMasterDomain(roleMasterDomain);
		dfm.setDtTrDate(new Date());
		dfm.setNumIsValid(1);
		gDao.setEmployeeDefaultRoleid(dfm);
	}

	
	public void updateDefaultRole(int empId, int roleId, int projectId,
			int grpId, int organizationId) {
		EmployeeDefaultRoleMasterDomain dfm=new EmployeeDefaultRoleMasterDomain();
		dfm.setNumEmpId(empId);
		dfm.setNumGroupId(grpId);
		dfm.setNumProjectId(projectId);
		dfm.setNumOrganisationId(organizationId);
		RoleMasterDomain roleMasterDomain = new RoleMasterDomain();
		if(roleId!=0){		
			roleMasterDomain =  roleMasterDao.getRoleMasterDomainById(roleId);
		}
		dfm.setRoleMasterDomain(roleMasterDomain);
		dfm.setDtTrDate(new Date());
		dfm.setNumIsValid(1);
		gDao.UpdateDefaultRoleMaster(dfm);
	}
	
}
