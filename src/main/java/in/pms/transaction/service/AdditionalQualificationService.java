package in.pms.transaction.service;
/**
 * @author amitkumarsaini
 *
 */
import java.util.List;

import javax.transaction.Transactional;

import in.pms.transaction.domain.AdditionalQualificationDomain;
import in.pms.transaction.model.AdditionalQualificationModel;
import in.pms.transaction.model.MouCollaborationModel;
import in.pms.transaction.model.PublicationDetailsModel;


public interface AdditionalQualificationService {

	public List<AdditionalQualificationModel> getAllAdditionalcollab();
	
	@Transactional
	public String checkDuplicateAdditionalQual(AdditionalQualificationModel additionalQualificationModel);

	@Transactional
	public AdditionalQualificationModel saveUpdateAdditionalQual(AdditionalQualificationModel additionalQualificationModel);
	
	@Transactional
	public AdditionalQualificationModel deleteAddQual(AdditionalQualificationModel additionalQualificationModel);

	@Transactional
	public List<AdditionalQualificationModel> getAllActiveEmployeeMasterDomain();
	@Transactional
	public List<AdditionalQualificationModel> loadAdditionalqualificationDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
}
