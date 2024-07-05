package in.pms.transaction.service;

import in.pms.transaction.model.PublicationDetailsModel;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;


public interface ProjectPublicationDetailsService {	
	@Transactional
	public PublicationDetailsModel saveProjectPublicationDetails(PublicationDetailsModel PublicationDetailsModel);
	
	public List<PublicationDetailsModel> getPublicationDetails(int reportId);
	
	@Transactional
	public List<PublicationDetailsModel> loadPublicationDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
}