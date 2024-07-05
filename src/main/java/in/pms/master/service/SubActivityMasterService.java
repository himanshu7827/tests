package in.pms.master.service;

import in.pms.master.domain.SubActivityMasterDomain;
import in.pms.master.model.ActivityMasterModel;
import in.pms.master.model.SubActivityMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface SubActivityMasterService {

		
		@Transactional	
		 public long saveUpdateSubActivityMaster(SubActivityMasterModel subActivityMasterModel);		
		
		public String checkDuplicateSubActivityName(SubActivityMasterModel subActivityMasterModel);
		 
		 public SubActivityMasterModel getSubActivityMasterDomainById(long numId);
		
		 public List<SubActivityMasterModel> getAllSubActivityMasterDomain();
		
		 public List<SubActivityMasterModel> getAllActiveSubActivityMasterDomain();
		
		@Transactional
		public long deleteSubActivity(SubActivityMasterModel subActivityMasterModel);

		public List<SubActivityMasterDomain> getSubActivityDomainById(List<Long> numSubActivityId);
				
		
	}

	


