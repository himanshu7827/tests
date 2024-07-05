package in.pms.master.service;

import in.pms.master.domain.ActivityMasterDomain;
import in.pms.master.model.ActivityMasterModel;
import in.pms.master.model.ExpenditureHeadModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ActivityMasterService {



		
		@Transactional	
		 public long saveUpdateActivityMaster(ActivityMasterModel activityMasterModel);		
		
		 public ActivityMasterModel getActivityMasterDomainById(long numId);
		
		 public List<ActivityMasterModel> getAllActivityMasterDomain();
		
		 public List<ActivityMasterModel> getAllActiveActivityMasterDomain();
		
		@Transactional
		public long deleteActivity(ActivityMasterModel activityMasterModel);
		
		public ActivityMasterDomain getActivityDomainById(long numActivityId);
		
		public String checkDuplicateActivityName(ActivityMasterModel activityMasterModel);
	
		
	}

	


