package in.pms.master.service;
import in.pms.master.model.ProcessMasterModel;
import in.pms.master.model.RoleMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProcessMasterService {

	public List<ProcessMasterModel> getAllProcessData();
	
	@Transactional
	public long saveUpdateProcessMaster(ProcessMasterModel processMasterModel);
	
	public String checkDuplicateProcessName(ProcessMasterModel processMasterModel);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deletProcessData(ProcessMasterModel processMasterModel);
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


