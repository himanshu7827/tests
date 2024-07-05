package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import javax.transaction.Transactional;




import in.pms.transaction.model.OthersModel;
import in.pms.transaction.model.TalksModel;

public interface OthersService {

	/*@Transactional
	public String checkDuplicateOthers(OthersModel othersModel);*/

	@Transactional
	public OthersModel saveUpdateOthers(OthersModel othersModel);

	@Transactional
	public OthersModel deleteOthers(OthersModel othersModel);

	@Transactional
	public List<OthersModel> loadOthersDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	
}
