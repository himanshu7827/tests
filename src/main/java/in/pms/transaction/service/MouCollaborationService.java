package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import javax.transaction.Transactional;

import in.pms.transaction.model.MouCollaborationModel;


public interface MouCollaborationService {

	/*@Transactional
	public String checkDuplicateMouCollab(MouCollaborationModel mouCollaborationModel);
*/
	@Transactional
	public MouCollaborationModel saveUpdateMouCollaboration(MouCollaborationModel mouCollaborationModel);

	@Transactional
	public MouCollaborationModel deleteMouCollaboration(MouCollaborationModel mouCollaborationModel);

	public List<in.pms.transaction.model.MouCollaborationModel> loadCollabDetail(
			int monthlyProgressId, long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);

	
}
