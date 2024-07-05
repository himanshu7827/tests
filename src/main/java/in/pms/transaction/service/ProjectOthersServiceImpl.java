package in.pms.transaction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProjectOthersDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectInnovationsDomain;
import in.pms.transaction.domain.ProjectOthersDomain;
import in.pms.transaction.model.ProjectOthersModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class ProjectOthersServiceImpl implements ProjectOthersService{
	
	@Autowired
	ProjectOthersDao projectOthersDao;
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	

	
		@Override
		@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public String SaveProjectOthers(ProjectOthersModel projectOthersModel) {
			ProjectOthersDomain domain=null;
			long numCateoryId =Long.parseLong(encryptionService.dcrypt(projectOthersModel.getEncCategoryId()));
    			String encMonthlyProgressId = projectOthersModel.getEncMonthlyProgressId();
    			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		domain = projectOthersDao.loadOthersDetail(monthlyProgressId, numCateoryId);
		if(domain!=null)
		{
		     domain.setStrProjectOthers(projectOthersModel.getStrProjectOthers());
		        domain.setStrProjectOthersHtml(projectOthersModel.getStrProjectOthersHtml());
		        domain.setDtTrDate(new Date());
			
		}
		else {
			domain = new ProjectOthersDomain();
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
		        //domain.setNumReportId(projectOthersModel.getNumReportId());
		        domain.setStrProjectOthers(projectOthersModel.getStrProjectOthers());
		        domain.setStrProjectOthersHtml(projectOthersModel.getStrProjectOthersHtml());
		       
		       
		        
			
		}
       
		String t= projectOthersDao.save(domain).getStrProjectOthersHtml();
		return t;
	}




		@Override
		public String getSavedProjectOthers(int reportId) {
			ProjectOthersDomain projectOthersDomain =null;
			projectOthersDomain = projectOthersDao.getdetail(reportId);
			if(projectOthersDomain!=null){
				return projectOthersDomain.getStrProjectOthersHtml();
			} else{
				return null;
			}
			
			
		}
		@Override
		public String loadOthersDetail(int monthlyProgressId, long categoryId) {
			ProjectOthersDomain projectOthersDomain = projectOthersDao.loadOthersDetail(monthlyProgressId,categoryId);
			if(projectOthersDomain!=null){
				return projectOthersDomain.getStrProjectOthersHtml();
			}
			return null;
		}
		
		@Override
		public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
			List<Object[]> dataList = new ArrayList<Object[]>();
			List<ProjectOthersDomain> domainList = projectOthersDao.loadPublicationByDetailsId(progressDetailsId);
			/*if(null != domainList && domainList.size()>0){
				Object[] obj = new Object[1];
				obj[0] = "";
				
				dataList.add(obj);
			}*/
			
			for(ProjectOthersDomain domain : domainList){
				Object[] obj = new Object[1];
				obj[0] =domain.getStrProjectOthersHtml();
			
				dataList.add(obj);
			}
			
			return dataList;		
		}
	}