package in.pms.transaction.service;



import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.ExitInterviewMainDomain;
import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.transaction.model.ExitInterviewModel;

public interface ExitInterviewService {
	
	
	@Transactional
	public long saveExitInterviewData(ExitInterviewModel exitInterviewModel,String flaEmail,String slaEmail,String empRemarks);
	
	public ExitInterviewMainDomain getExitInterviewMainDomainDataByEmpId();
	
	//public ExitInterviewMainDomain getExitInterviewMainDomainDataByFlaEmail();
	
	//public ExitInterviewMainDomain getExitInterviewMainDomainDataBySlaEmail();
	
	public List<ExitInterviewModel> getActiveExitInterviewDataByFla();

	public List<ExitInterviewModel> getActiveExitInterviewDataBySla();
	
	@Transactional
	public boolean updateFlaNdSlaStatus(long exitInterviewId,String status,int approvalId,String remarks);
	
	public List<ExitInterviewModel> getActiveExitInterviewDataApprovedBySla();
	
	@Transactional
	public long saveExitInterviewByHr(ExitInterviewModel exitInterviewModel,long employeeId);
	
}