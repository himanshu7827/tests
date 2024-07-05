package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;



import in.pms.master.domain.MilestoneTypeMaster;
import in.pms.master.model.MilestoneTypeMasterForm;




public interface MilestoneTypeMasterService {
	
	
	public List<MilestoneTypeMasterForm> getAllMilestoneTypeData();
	
	public String checkDuplicateMilestoneTypeData(MilestoneTypeMasterForm milestoneTypeMasterForm);
	
	@Transactional
	public MilestoneTypeMaster saveMilestoneTypeData(MilestoneTypeMasterForm milestoneTypeMasterForm);
	
	public List<MilestoneTypeMasterForm> getActiveSkillData();
	
 	
}
