package in.pms.master.service;

import in.pms.master.domain.SeminarEventDomain;
import in.pms.master.model.AwardWonModel;
import in.pms.master.model.SeminarEventModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface SeminarEventService {


	
	public List<SeminarEventModel> getAllSeminar();
	
	@Transactional
	public SeminarEventModel saveUpdateSeminarDetails(SeminarEventModel seminarEventModel);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public SeminarEventModel deleteSeminarDetails(SeminarEventModel seminarEventModel);
	
	@Transactional
	public List<SeminarEventModel> loadSeminarEventDetails(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	
}
