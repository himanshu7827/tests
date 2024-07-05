package in.pms.transaction.service;

import in.pms.master.model.ProjectMasterModel;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.OthersModel;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface MonthlyProgressService {

	public List<OthersModel> getAllOthers();

	@Transactional
	public String MonthlyProgress(int month, int year,long projectId,long groupId);

	@Transactional
	public MonthlyProgressModel getById(int numId);
	
	@Transactional
	public List<MonthlyProgressModel> getMonthlyProgressDetailsByPId(int monthlyProgressId);
	
	@Transactional
	public String MonthlyProgressByGroup(int month, int year,long groupId);
	
	public void writeProgressReportAuthorityCheck();
	@Transactional
	public List<ProjectMasterModel>  getProjectIdsForReport(int month, int year, String projectIds,int selectedProjectType);
	
	public List<CategoryMasterModel> getCategoryByParentId(String parentId);

	List<CategoryMasterModel> getChildCategoryByParentId(String encParentId,String encCategoryId);
	
	public int activeProgressReportsWithGCCount(long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> activeProgressReportsWithGCDetails(long actionId);
	
	@Transactional
	public String getMinCategoryByPId(int monthlyProgressId);
	
	@Transactional
	public List<String> sendToPMO(MonthlyProgressModel monthlyProgressModel);
	
	public int allActiveProgressReportsbyGCCount(int year,int month,long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> activeProgressReportsDetailsbyGCforCurrentMonth(int year,int month,long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> PendingProgressReportsAtPL(int year,int month,long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> PendingProgressReportsAtGC(int year,int month,long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> revisedReportAtPL(long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> pendingProgressReportsAtPL();
	
	public int SentForRevisionCount(long actionId);
	
	@Transactional
	public List<MonthlyProgressModel> PendingProgressReportsAtPI(int year,int month,long actionId);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void getPendingMonthlyProgReportAtGC();
}