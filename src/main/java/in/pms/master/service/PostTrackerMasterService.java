


package in.pms.master.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.PostTrackerMasterDomain;
import in.pms.master.model.PostTrackerMasterModel;
//import in.pms.transaction.model.BudgetHeadMasterForm;

@Service
public interface PostTrackerMasterService {
	
	@Transactional
	public long saveUpdatePostTrackerMaster(PostTrackerMasterModel postTrackerMasterModel);
	
	//public String checkDuplicatePostTrackerMasterName(PostTrackerMasterModel postTrackerMasterModel);
	
/*	public PostTrackerMasterModel getPostTrackerMasterDomainById(long numId);
	
	
	
	
	*/
	public List<PostTrackerMasterModel> getAllPostTrackerMasterDomain();
	public List<PostTrackerMasterModel> getAllActivePostTrackerMasterDomain();
	public PostTrackerMasterDomain getPostTrackerDomainById(long numId);
	/*
	@Transactional(propagation=Propagation.REQUIRED)
	public void deletePostTrackerMaster(PostTrackerMasterModel postTrackerMasterModel);*/

}

