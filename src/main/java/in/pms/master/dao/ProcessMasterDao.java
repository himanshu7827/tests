package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ProcessMasterDomain;
import in.pms.master.domain.RoleMasterDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateProcessMaster(ProcessMasterDomain processMasterDomain){
		processMasterDomain = daoHelper.merge(ProcessMasterDomain.class, processMasterDomain);
		return processMasterDomain.getNumProcessId();
	}
	
	public ProcessMasterDomain getProcessMasterByName(String processName){
		String query = "from ProcessMasterDomain where  numIsValid!=2 and lower(strProcessName)=lower('"+processName+"')";	
		List<ProcessMasterDomain> processMasterDomainList = daoHelper.findByQuery(query);		
		if(processMasterDomainList.size()>0){
			return processMasterDomainList.get(0);
		}
		return null;
	}
	
	public ProcessMasterDomain getProcessMasterDomainById(long numId){
		ProcessMasterDomain processeMasterDomain =null;
		String query = "from ProcessMasterDomain where numProcessId="+numId;
		List<ProcessMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			processeMasterDomain =list.get(0);
		}
		return processeMasterDomain;	
	}
	
	public List<ProcessMasterDomain> getAllProcessData()
	{
		String query="select p from ProcessMasterDomain p where p.numIsValid!=2 order by p.dtTrDate";
		return daoHelper.findByQuery(query);	
	}
	
	public List<RoleMasterDomain> getAllActiveRoleMasterDomain(){
		String query = "from RoleMasterDomain where numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	public void deleteProcessMaster(ProcessMasterDomain processMasterDomain)
	{
		ProcessMasterDomain processMaster = daoHelper.findById(ProcessMasterDomain.class, processMasterDomain.getNumProcessId());
		processMaster.setNumIsValid(2);;
		processMaster.setDtTrDate(processMasterDomain.getDtTrDate());
		daoHelper.merge(ProcessMasterDomain.class, processMaster);
	}
}
