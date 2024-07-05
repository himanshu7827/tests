package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ManpowerRequirementDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManpowerRequirementDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveManpowerRequirementMaster(ManpowerRequirementDomain manpowerRequirementDomain){
		manpowerRequirementDomain = daoHelper.merge(ManpowerRequirementDomain.class, manpowerRequirementDomain);
		return manpowerRequirementDomain.getNumId();
	}
	
	public ManpowerRequirementDomain getManpowerRequirementById(long numId){
		ManpowerRequirementDomain manpowerRequirementDomain =null;
		manpowerRequirementDomain = daoHelper.findById(ManpowerRequirementDomain.class, numId);
		return manpowerRequirementDomain;	
	}
	
	public List<ManpowerRequirementDomain> getAllManpowerRequirment(long projectId){
		String query = "from ManpowerRequirementDomain a  where a.numIsValid=1 and a.projectMasterDomain.numId="+projectId +" order by a.numRequiredType";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<EmployeeRoleMasterDomain> getManpowerByNumId(long reqId){
		List<EmployeeRoleMasterDomain> employeeRoleMasterrList =  daoHelper.findByQuery("from EmployeeRoleMasterDomain e where e.numIsValid=1 and e.manpowerRequirementDomain.numId="+reqId);
		return employeeRoleMasterrList;
	}

}