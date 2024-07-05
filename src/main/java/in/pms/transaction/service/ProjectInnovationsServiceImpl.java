package in.pms.transaction.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectBudgetDomain;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.PatentDetailsDao;
import in.pms.transaction.dao.ProductsDevelopedDao;
import in.pms.transaction.dao.ProjectInnovationsDao;
import in.pms.transaction.dao.ProjectPublicationDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectInnovationsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.model.ProductsDevelopedModel;
import in.pms.transaction.model.ProjectInnovationsModel;
import in.pms.transaction.model.PublicationDetailsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class ProjectInnovationsServiceImpl implements ProjectInnovationsService{
	
	@Autowired
	ProjectInnovationsDao projectInnovationsDao;

	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;

	
	//@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
		@Override
		@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public ProjectInnovationsModel SaveProjectInnovations(ProjectInnovationsModel projectInnovationsModel) {
		ProjectInnovationsDomain domain=null;
		long numCateoryId =Long.parseLong(encryptionService.dcrypt(projectInnovationsModel.getEncCategoryId()));
		String encMonthlyProgressId = projectInnovationsModel.getEncMonthlyProgressId();
		int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		if(projectInnovationsModel.getNumId()>0)
		{
				domain = projectInnovationsDao.getOne(projectInnovationsModel.getNumId());
		}
		if(domain!=null)
		{
		     domain.setStrInnovationDescription(projectInnovationsModel.getStrInnovationDescription());
		     domain.setStrInnovationDescriptionHtml(projectInnovationsModel.getStrInnovationDescriptionHtml());
		     domain.setDtTrDate(new Date());
			
		}
		else {
			domain = new ProjectInnovationsDomain();
			 Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
				UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
		        domain.setDtTrDate(new Date());
		        domain.setNumIsValid(1);
		        domain.setNumTrUserId(userInfo.getEmployeeId());
		        domain.setStrStatus("SAD");
		        
		      /*  long numCateoryId = projectInnovationsModel.getNumCateoryId();
    			String encMonthlyProgressId = projectInnovationsModel.getEncMonthlyProgressId();
    			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));*/
    			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
    			if(null != progressDetails && progressDetails.size()>0){
    				domain.setMonthlyProgressDetails(progressDetails.get(0));
    			}
                
		        
		      //  domain.setNumReportId(projectInnovationsModel.getNumReportId());
		        domain.setStrInnovationDescription(projectInnovationsModel.getStrInnovationDescription());
		        domain.setStrInnovationDescriptionHtml(projectInnovationsModel.getStrInnovationDescriptionHtml());
		       
		       
		        
			
		}
		
		int t= projectInnovationsDao.save(domain).getNumId();
		if(t!=0){
			projectInnovationsModel.setNumId(t);
			return projectInnovationsModel;
		}else {
			return null;
		}
	}
	



		@Override
		public String getSavedInnovationDetails(int reportId) {
			ProjectInnovationsDomain projectInnovationsDomain =null;
			projectInnovationsDomain = projectInnovationsDao.getdetail(reportId);
			if(projectInnovationsDomain!=null){
				return projectInnovationsDomain.getStrInnovationDescriptionHtml();
			} else{
				return null;
			}
			
			
		}


		@Override
		public List<ProjectInnovationsModel> loadInnovationDetail(int monthlyProgressId,
				long categoryId) {
			List<ProjectInnovationsDomain> domainList = projectInnovationsDao.loadInnovationDetail(monthlyProgressId,categoryId);
			if(domainList!=null){
				return convertDomaintoModel(domainList);//.getStrInnovationDescriptionHtml();
			}
			return null;
			
		}
		private List<ProjectInnovationsModel> convertDomaintoModel(List<ProjectInnovationsDomain> domainList) {
			List<ProjectInnovationsModel> modelList = new ArrayList<ProjectInnovationsModel>();
			for(int i=0;i<domainList.size();i++){
				ProjectInnovationsModel projectInnovationsModel = new ProjectInnovationsModel();
				ProjectInnovationsDomain projectInnovationsDomain=domainList.get(i);
				
				projectInnovationsModel.setStrInnovationDescription(projectInnovationsDomain.getStrInnovationDescription());
				projectInnovationsModel.setNumId(projectInnovationsDomain.getNumId());
				projectInnovationsModel.setStrInnovationDescriptionHtml(projectInnovationsDomain.getStrInnovationDescriptionHtml());
				
				modelList.add(projectInnovationsModel);
			}
			return modelList;
			}




		@Override
		public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
			List<Object[]> dataList = new ArrayList<Object[]>();
			List<ProjectInnovationsDomain> domainList = projectInnovationsDao.loadPublicationByDetailsId(progressDetailsId);
			if(null != domainList && domainList.size()>0){
				Object[] obj = new Object[1];
				obj[0] = "Innovations";
				
				dataList.add(obj);
			}
			
			for(ProjectInnovationsDomain domain : domainList){
				Object[] obj = new Object[1];
				obj[0] =domain.getStrInnovationDescriptionHtml();
			
				dataList.add(obj);
			}
			
			return dataList;		
		}




		@Override
		public void deleteInnovationDetails(ProjectInnovationsModel projectInnovationsModel) {
			
				int count= projectInnovationsModel.getNumIds().length;
				//System.out.println("count"+count);
				int[] numIds= new int[count];
				numIds=projectInnovationsModel.getNumIds();
				
				for(int i=0;i<count;i++)
				{
			
					if(numIds[i]!=0)
					{
						
						ProjectInnovationsDomain projectInnovationsDomain=new ProjectInnovationsDomain();
						projectInnovationsDomain=projectInnovationsDao.getOne(numIds[i]);
						projectInnovationsDomain.setNumIsValid(0);
						projectInnovationsDomain.setDtTrDate(new Date());
						
						projectInnovationsDao.save(projectInnovationsDomain);
					}
					
				}	
				
				
			}
			
		}
	