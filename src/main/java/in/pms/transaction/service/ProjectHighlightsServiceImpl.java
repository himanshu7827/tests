package in.pms.transaction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProjectHighlightsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectHighlightsDomain;
import in.pms.transaction.model.ProjectHighlightsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class ProjectHighlightsServiceImpl implements ProjectHighlightsService{
	
	@Autowired
	ProjectHighlightsDao projectHighlightsDao;


	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public ProjectHighlightsModel SaveHighlights(ProjectHighlightsModel projectHighlightsModel) {
		ProjectHighlightsDomain domain=null;
		long numCateoryId =Long.parseLong(encryptionService.dcrypt(projectHighlightsModel.getEncCategoryId()));
			String encMonthlyProgressId = projectHighlightsModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		domain = projectHighlightsDao.loadHighlightsDetail(monthlyProgressId, numCateoryId);
		if(domain!=null)
		{
		     domain.setStrCompActivityHtml(projectHighlightsModel.getStrCompActivityHtml());
		     domain.setStrCompActivity(projectHighlightsModel.getStrCompActivity());
		     domain.setStrOngoingActivityHtml(projectHighlightsModel.getStrOngoingActivityHtml());
		     domain.setStrOngoingActivity(projectHighlightsModel.getStrOrngoingActivity());
		     domain.setDtTrDate(new Date());
			
		}
		else {
			
			domain = new ProjectHighlightsDomain();
			 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
				UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
		        domain.setDtTrDate(new Date());
		        domain.setNumIsValid(1);
		        domain.setNumTrUserId(userInfo.getEmployeeId());
		        domain.setStrStatus("SAD");
		        
		      
    			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
    			if(null != progressDetails && progressDetails.size()>0){
    				domain.setMonthlyProgressDetails(progressDetails.get(0));
    			}
                
		        
		       // domain.setNumReportId(projectHighlightsModel.getNumReportId());
		        domain.setStrCompActivity(projectHighlightsModel.getStrCompActivity());
		        domain.setStrCompActivityHtml(projectHighlightsModel.getStrCompActivityHtml());
		        domain.setStrOngoingActivity(projectHighlightsModel.getStrOrngoingActivity());
		        domain.setStrOngoingActivityHtml(projectHighlightsModel.getStrOngoingActivityHtml());
		       
		       
		        
			
		}
       
		int t= projectHighlightsDao.save(domain).getNumId();
		if(t!=0){
			return projectHighlightsModel; 
			}
		else{
			return null;
			}
		
	}

	@Override
	public ProjectHighlightsModel getSavedHighlights(int reportId) {
		ProjectHighlightsModel projectHighlightsModel= new ProjectHighlightsModel();
		ProjectHighlightsDomain projectHighlightsDomain =null;
		projectHighlightsDomain = projectHighlightsDao.getdetail(reportId);
		if(projectHighlightsDomain!=null){
			projectHighlightsModel.setStrCompActivityHtml(projectHighlightsDomain.getStrCompActivityHtml());
			projectHighlightsModel.setStrOngoingActivityHtml(projectHighlightsDomain.getStrOngoingActivityHtml());
			
			return projectHighlightsModel;
		} else{
			return null;
		}
		
		
	}
	
	@Override
	public ProjectHighlightsModel loadHighlightsDetail(int monthlyProgressId,
			long categoryId) {
		ProjectHighlightsModel projectHighlightsModel= new ProjectHighlightsModel();
		ProjectHighlightsDomain projectHighlightsDomain = projectHighlightsDao.loadHighlightsDetail(monthlyProgressId,categoryId);
		if(projectHighlightsDomain!=null){
			projectHighlightsModel.setStrCompActivityHtml(projectHighlightsDomain.getStrCompActivityHtml());
			projectHighlightsModel.setStrOngoingActivityHtml(projectHighlightsDomain.getStrOngoingActivityHtml());
			
			return projectHighlightsModel;
		}
			return null;
	}
	
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<ProjectHighlightsDomain> domainList = projectHighlightsDao.loadHighlightsByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[2];
			obj[0] ="Completed Activity";
			obj[1] ="Ongoing Activity";
			dataList.add(obj);
		}
		
		for(ProjectHighlightsDomain domain : domainList){
			Object[] obj = new Object[2];
			
			obj[0] =domain.getStrCompActivityHtml();
			obj[1] =domain.getStrOngoingActivityHtml();
		
			dataList.add(obj);
		}
		
		return dataList;		
	}
}
