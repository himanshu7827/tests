package in.pms.transaction.service;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.States;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ShowNextPrevBtnModel;

public interface ProgressReportService {

	public List<CategoryMasterModel> getAllCategoryList(String strRole);

	public List<CategoryMasterModel> getSubCategoryList(long numCategoryId);

	
	public boolean saveUpdateDeploymentDetails(HttpServletRequest request,
			DeploymentTotDetailsModel deploymentTotDetailsModel);

	public List<States> getStateList();

	@Transactional
	public List<DeploymentTotDetailsModel> getDeploymentDetails(int monthlyProgressId, long numCategoryId);
	
	@Transactional
	public void deleteDeployementDetails(DeploymentTotDetailsModel deploymentTotDetailsModel);

	@Transactional
	public long uploadProgressRportImages(DeploymentTotDetailsModel deploymentTotDetailsModel);
	
	public List<DeploymentTotDetailsModel> getUplodedImages(String string);

	@Transactional
	public boolean downloadDeploymentImages(DeploymentTotDetailsModel deploymentTotDetailsModel, HttpServletResponse response);

	@Transactional
	public boolean deleteImageDetails(DeploymentTotDetailsModel deploymentTotDetailsModel);

	@Transactional
	public ShowNextPrevBtnModel getPrevNextBtnController(
			String encCategoryId, String encMonthlyProgressId);

	
	List<Object[]> getPreviewDataWithHeaderForTOT(long progressDetailsId);	
	
	public List<CategoryMasterModel> getAllCategoryByFlag(String strRole);
}
