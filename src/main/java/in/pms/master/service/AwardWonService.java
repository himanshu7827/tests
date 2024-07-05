package in.pms.master.service;

import in.pms.master.model.AwardWonModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AwardWonService {


	
	public List<AwardWonModel> getAllAward();
	
	@Transactional
	public AwardWonModel saveUpdateAwardWonDetails(AwardWonModel awardWonModel);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public AwardWonModel deleteAwardWonDetails(AwardWonModel awardWonModel);
	
	@Transactional
	public List<AwardWonModel> loadAwardDetails(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
}
