package in.pms.transaction.service;

import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.model.PublicationDetailsModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




public interface PatentDetailsService {	
	@Transactional
	public PatentDetailsModel SaveProjectPatentDetails(PatentDetailsModel patentDetailsModel);
	
	@Transactional
	public List<PatentDetailsModel> getProjectPatentDetails(int reportId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	@Transactional
	public List<PatentDetailsModel> loadPatentDetail(int monthlyProgressId,long categoryId);

	@Transactional
	public void deletePatentDetails(PatentDetailsModel patentDetailsModel);
	
}