package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
//import in.pms.global.domain.DesignationMaster;
//import in.pms.global.model.DesignationForm;

import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.EmployeeDefaultRoleMasterDomain;
import in.pms.master.domain.ForgotPassword;
import in.pms.master.model.DesignationForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class GlobalDao {

	
	@Autowired
	public DaoHelper daoHelper;
	
	public List<DesignationMaster> getDesignationName()
	{
		ArrayList<Object> paraList=new ArrayList<Object>();
		String qry="SELECT s from DesignationMaster s where s.numIsValid = 1";
		return daoHelper.findByQuery(qry,paraList);
	}
	
	
	
	public List<DesignationMaster> getdesignationShortCode()
	{
		ArrayList<Object> paraList=new ArrayList<Object>();
		String qry="SELECT s from DesignationMaster s where s.numIsValid = 1";
		return daoHelper.findByQuery(qry,paraList);
	}
	
	public void saveDesignation(DesignationMaster addDesignation) 
	{
		System.out.println("before executing Dao function");
		DesignationMaster newdm = daoHelper.persist(DesignationMaster.class,addDesignation);
		/*System.out.println("dao = ");
		System.out.println("dao 1 = "+newdm.getNumDesignationId());
		System.out.println("dao 2 = "+newdm.getDesignationName());*/
	}
	
	
	
	
	public void updateDesignation(DesignationForm DesigForm) {
		List<DesignationMaster> addDesignation = daoHelper.findByQuery("select e from DesignationMaster e where numDesignationId= "+ DesigForm.getNumDesignationId());
		System.out.println("DesigForm.getNumDesignationId()"+DesigForm.getNumDesignationId());
		// Iterator<DesignationMaster> itr = addDesignation.iterator();
		 if(addDesignation.size()>0){
			 DesignationMaster sch = addDesignation.get(0);
			 sch.setDesignationName(DesigForm.getStrDesignationName());
			 sch.setDescription(DesigForm.getStrDesription());
			 sch.setDesignationShortCode(DesigForm.getStrDesignationShortCode());
			 sch.setDtTrDate(new Date());
			 daoHelper.merge(DesignationMaster.class, sch);				
		 }	 
	}
	
	public void deleteDesignation(DesignationForm DesigForm) {
		System.out.println("deleteDesignation 2 = "+DesigForm.getCheckbox());
		System.out.println("DesigForm.getCheckbox().size() = "+DesigForm.getCheckbox().size());
		for(int i=0;i<DesigForm.getCheckbox().size();i++){
		int id= Integer.parseInt(DesigForm.getCheckbox().get(i).toString());
		System.out.println("id = "+id);
		List<DesignationMaster> addDesignation = daoHelper.findByQuery("select e from DesignationMaster e where numDesignationId= "+ id);
		System.out.println("addDesignation.size() = "+addDesignation.size());
		
		 if(addDesignation.size()>0){
			 System.out.println("update flag 2 "); 
			 DesignationMaster sch = addDesignation.get(0);		
			 sch.setNumIsValid(2);
			 sch.setDtTrDate(new Date());
			 daoHelper.merge(DesignationMaster.class, sch);
		 }
		 
		}
		
	}

	/*public List<ForeignManufacturerAddressDomain> getForAddress(int instID)
	{
		ArrayList<Object> paraList=new ArrayList<Object>();
		paraList.add(0,instID);
		String query="select r from ForeignManufacturerAddressDomain r where r.numIsValid=1 and r.numVersion=1 and r.numInstId=?";
		return daoHelper.findByQuery(query,paraList);	
	}*/

	public List<DesignationMaster> getDesignationlist() {
		ArrayList<Object> paraList=new ArrayList<Object>();

		String query="select r from DesignationMaster r where r.numIsValid=1";
		return daoHelper.findByQuery(query,paraList);
	}
	
	
	public List<DesignationMaster> getUserExist(String userName)
	{
		ArrayList<Object> paraList=new ArrayList<Object>();
		String query="Select m from DesignationMaster m where m.designationName like trim(?) and m.numIsValid in (1,0)";
		paraList.add(0, userName);
		List<DesignationMaster> registerList = daoHelper.findByQuery(query,paraList);
		return registerList;
	}



	public List<EmployeeDefaultRoleMasterDomain> isDuplicateDefaultRoleMaster(
			int empId) {
		
			String Query = "Select e from EmployeeDefaultRoleMasterDomain  e where e.numEmpId='"+ empId + "'   and e.numIsValid=1";
			return daoHelper.findByQuery(Query);
	}



	public void setEmployeeDefaultRoleid(EmployeeDefaultRoleMasterDomain dfm) {
		daoHelper.persist(EmployeeDefaultRoleMasterDomain.class, dfm);

		
	}



	public void UpdateDefaultRoleMaster(EmployeeDefaultRoleMasterDomain dfm) {
		List<EmployeeDefaultRoleMasterDomain> dfrm = getEmployeeDefaultRoleByEmpId(dfm.getNumEmpId());
		Iterator<EmployeeDefaultRoleMasterDomain> itr = dfrm.iterator();

		while (itr.hasNext()) {
			EmployeeDefaultRoleMasterDomain dfrm_itr = itr.next();
			dfrm_itr.setNumEmpId(dfm.getNumEmpId());
			dfrm_itr.setNumGroupId(dfm.getNumGroupId());
			dfrm_itr.setNumProjectId(dfm.getNumProjectId());
			dfrm_itr.setNumOrganisationId(dfm.getNumOrganisationId());
			dfrm_itr.setRoleMasterDomain(dfm.getRoleMasterDomain());
			dfrm_itr.setDtTrDate(new Date());
			dfrm_itr.setNumIsValid(1);
			daoHelper.merge(EmployeeDefaultRoleMasterDomain.class, dfrm_itr);
		}

		
	}
	
	public List<EmployeeDefaultRoleMasterDomain> getEmployeeDefaultRoleByEmpId(long empId){
		String query =" from EmployeeDefaultRoleMasterDomain where numEmpId="+empId+" and numIsValid=1";
		return daoHelper.findByQuery(query);
	}
	
	public ForgotPassword mergeForgotPassword(ForgotPassword obj){
		
		return daoHelper.merge(ForgotPassword.class, obj);
	}



	public String createMonthlyProgressReportCopy(long numTransactionId, int numMonthlyProgressId) {		
		return daoHelper.findByFun2("copyprogressreportdata", numTransactionId, numMonthlyProgressId);
	}
}