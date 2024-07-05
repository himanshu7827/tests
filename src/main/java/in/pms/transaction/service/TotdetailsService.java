package in.pms.transaction.service;

import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.ShowNextPrevBtnModel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

public interface TotdetailsService {
@Transactional
	public boolean saveUpdateDTotDetails(HttpServletRequest request,
			DeploymentTotDetailsModel deploymentTotDetailsModel);

	@Transactional
	public List<DeploymentTotDetailsModel> getTotDetails(int monthlyProgressId,
			long numCategoryId);

	@Transactional
	public void deleteTotDetails(
			DeploymentTotDetailsModel deploymentTotDetailsModel);
}
