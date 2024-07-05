package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ModuleTypeMaster;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ModulesTypeDao {
	@Autowired
	DaoHelper daoHelper;
	
	public ModuleTypeMaster mergeModules(ModuleTypeMaster application){
		return daoHelper.merge(ModuleTypeMaster.class, application);
	}
	
	public List<ModuleTypeMaster> getActiveModulesType(){
		String query = "select a from ModuleTypeMaster a  where a.numIsValid=1";
		return daoHelper.findByQuery(query);
	}

	public ModuleTypeMaster getModuleById(long moduleTypeId) {
		return daoHelper.findById(ModuleTypeMaster.class, moduleTypeId);

	}
	
	public ModuleTypeMaster getModuleByName(String moduleName) {
		return daoHelper.findByAttribute(ModuleTypeMaster.class,"moduleName", moduleName).get(0);

	}
	

}
