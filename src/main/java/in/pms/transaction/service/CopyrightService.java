package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import javax.transaction.Transactional;

import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.MediaModel;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.CopyRightModel;

public interface CopyrightService {

	public List<CopyRightModel> getAllCopyRight();

	/*@Transactional
	public String checkDuplicateCopyright(CopyRightModel copyRightModel);*/

	@Transactional
	public CopyRightModel saveUpdateCopyrightMaster(CopyRightModel copyRightModel);

	@Transactional
	public CopyRightModel deleteCopyright(CopyRightModel copyRightModel);
	
	@Transactional
	public List<CopyRightModel> loadCopyrightDetail(int monthlyProgressId,long categoryId);
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);

	
}
