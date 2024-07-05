package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import javax.transaction.Transactional;

import in.pms.transaction.model.MouCollaborationModel;
import in.pms.transaction.model.PublicationDetailsModel;
import in.pms.transaction.model.TalksModel;


public interface TalksService {

	/*@Transactional
	public String checkDuplicateTalks(TalksModel talksModel);
*/
	@Transactional
	public TalksModel saveUpdateTalks(TalksModel talksModel);

	@Transactional
	public TalksModel deleteTalks(TalksModel talksModel);

	@Transactional
	public List<TalksModel> loadTalksDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
}
