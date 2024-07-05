package in.pms.transaction.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pms.master.domain.ModuleTypeMaster;
import in.pms.transaction.dao.ModulesTypeDao;
import in.pms.transaction.model.ModuleTypeModel;

@Service
public class ModuleServiceImpl implements ModuleService {
	
	@Autowired
	ModulesTypeDao moduleTypesDao;
	

	
	public List<ModuleTypeModel> getAllActiveModules() {
		return convertBusinessTypeToModelList(moduleTypesDao.getActiveModulesType());
	}


	private List<ModuleTypeModel> convertBusinessTypeToModelList(
			List<ModuleTypeMaster> activeModulesType) {
		
		List<ModuleTypeModel> mtm=new ArrayList<ModuleTypeModel>();
		for (ModuleTypeMaster moduleTypeMaster : activeModulesType) {
			ModuleTypeModel moduleTypeModel=new ModuleTypeModel();
			moduleTypeModel.setModuleTypeName(moduleTypeMaster.getModuleName());
			moduleTypeModel.setDescription(moduleTypeMaster.getDescription());
			moduleTypeModel.setModuleTypeId(moduleTypeMaster.getNumId());
			moduleTypeModel.setShortCode(moduleTypeMaster.getShortCode());
			mtm.add(moduleTypeModel);
		}
		return mtm;
	}


	
	public ModuleTypeMaster getModuleById(long moduleTypeId) {
		return moduleTypesDao.getModuleById(moduleTypeId);
	}
	
	
	public ModuleTypeMaster getModuleByName(String name){
		return moduleTypesDao.getModuleByName(name);
	}
	
	
}
