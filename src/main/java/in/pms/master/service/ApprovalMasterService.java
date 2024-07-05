package in.pms.master.service;

import in.pms.master.model.ApprovalMasterModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ApprovalMasterService {
	
	@Transactional
	public ApprovalMasterModel getApprovalDetails(long workshopId, long roleId, long actionId);
	
	@Transactional
	public ApprovalMasterModel saveApprovalDetails(ApprovalMasterModel approvalMasterModel);

}
