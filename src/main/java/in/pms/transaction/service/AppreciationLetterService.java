package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.transaction.model.AppreciationLetterModel;
import in.pms.transaction.model.OthersModel;
import in.pms.transaction.model.TalksModel;

import java.util.List;

import javax.transaction.Transactional;

public interface AppreciationLetterService {

	/*@Transactional
	public String checkDuplicateAppreciationLetter(AppreciationLetterModel appreciationLetterModel);
*/
	@Transactional
	public AppreciationLetterModel saveUpdateAppreciationLetter(AppreciationLetterModel appreciationLetterModel);

	@Transactional
	public AppreciationLetterModel deleteApprciationLetter(AppreciationLetterModel appreciationLetterModel);

	public List<AppreciationLetterModel> loadAppreciationDetail(int monthlyProgressId,
			long categoryId);
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	
}
