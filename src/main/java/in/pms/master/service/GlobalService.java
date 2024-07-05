package in.pms.master.service;



//import java.util.List;

import in.pms.master.domain.DesignationMaster;
import in.pms.master.model.DesignationForm;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import in.pms.global.domain.DesignationMaster;
//import in.pms.global.model.DesignationForm;

public interface GlobalService {

	@Transactional(propagation=Propagation.REQUIRED)
	public List<DesignationMaster> getDesignationlist() ;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveDesignation(DesignationForm DesigForm);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateDesignation(DesignationForm DesigForm);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteDesignation(DesignationForm DesigForm);

	@Transactional(propagation=Propagation.REQUIRED)
	public DesignationForm getExistUser(String userName);

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean checkDuplicateDefaultRole(int empId);

	@Transactional(propagation=Propagation.REQUIRED)
	public void setDefaultRole(int empId, int roleId, int projectId, int grpId,
			int organizationId);

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateDefaultRole(int empId, int roleId, int projectId,
			int grpId, int organizationId);
	
}
