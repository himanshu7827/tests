package in.pms.transaction.service;

import java.util.List;

import in.pms.master.domain.ModuleTypeMaster;
import in.pms.transaction.model.ModuleTypeModel;

public interface ModuleService {

	
	public List<ModuleTypeModel> getAllActiveModules();

	public ModuleTypeMaster getModuleById(long moduleTypeId);
	
	public ModuleTypeMaster getModuleByName(String name);
}
