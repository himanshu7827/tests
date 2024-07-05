package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.SkillMasterDomain;
import in.pms.master.model.SkillMasterModel;



public interface SkillMasterService {
	
	
	public List<SkillMasterModel> getAllSkillData();
	public String checkDuplicateSkillData(SkillMasterModel skillMasterModel);
	@Transactional
	public SkillMasterDomain saveSkillData(SkillMasterModel skillMasterModel);
	
	public List<SkillMasterModel> getActiveSkillData();
	
 	
}
