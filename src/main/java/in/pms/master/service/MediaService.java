package in.pms.master.service;

import in.pms.master.domain.MediaDomain;
import in.pms.master.model.MediaModel;
import in.pms.transaction.model.PublicationDetailsModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface MediaService {


	
	public List<MediaModel> getAllMediaDetails();
	
	@Transactional
	public MediaModel saveUpdateMedia(MediaModel mediaModel);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public MediaModel deleteMedia(MediaModel mediaModel);
	
	@Transactional
	public List<MediaModel> loadMediaDetail(int monthlyProgressId,long categoryId);
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	
}
