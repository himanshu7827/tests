package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.ThrustAreaMasterForm;



public interface ThrustAreaMasterService {
	
	
	public List<ThrustAreaMasterForm> getAllThrustAreaData();
	public String checkDuplicateThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm);
	@Transactional
	public long saveThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm);
	@Transactional
	public void deleteThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm);
	
	public List<ThrustAreaMasterForm> getActiveThrustAreaData();
	
	public List<ThrustAreaMasterDomain> getThrustAreaData(String ids);
 	
}
