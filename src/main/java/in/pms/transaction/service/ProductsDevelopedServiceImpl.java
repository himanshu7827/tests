package in.pms.transaction.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.ProjectBudgetDomain;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.PatentDetailsDao;
import in.pms.transaction.dao.ProductsDevelopedDao;
import in.pms.transaction.dao.ProjectPublicationDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProductDevelopedDomain;
import in.pms.transaction.domain.ProjectInnovationsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.domain.TotDetailsDomain;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.PatentDetailsModel;
import in.pms.transaction.model.ProductsDevelopedModel;
import in.pms.transaction.model.PublicationDetailsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public  class ProductsDevelopedServiceImpl implements ProductsDevelopedService{
	
	@Autowired
	ProductsDevelopedDao productsDevelopedDao;

	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	
	@Autowired
	EncryptionService encryptionService;
	

	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public String SaveProductsDeveloped(ProductsDevelopedModel productsDevelopedModel) {
          ProductDevelopedDomain domain= null;
         // System.out.println(" category id"+productsDevelopedModel.getEncCategoryId());
			long numCateoryId =Long.parseLong(encryptionService.dcrypt(productsDevelopedModel.getEncCategoryId()));
			String encMonthlyProgressId = productsDevelopedModel.getEncMonthlyProgressId();
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			if(productsDevelopedModel.getNumId()!=0){
				domain=productsDevelopedDao.getOne(productsDevelopedModel.getNumId());
				}
          if(domain!=null){
        	  domain.setStrProductsDeveloped(productsDevelopedModel.getStrProductsDeveloped());
              domain.setStrProductsDevelopedHtml(productsDevelopedModel.getStrProductsDevelopedHtml());
              domain.setDtTrDate(new Date());
           
          }
          else{
        	  domain= new ProductDevelopedDomain();
        	  Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        		UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
                domain.setDtTrDate(new Date());
                domain.setNumIsValid(1);
                domain.setNumTrUserId(userInfo.getEmployeeId());
/*                long numCateoryId = productsDevelopedModel.getNumCateoryId();
    			String encMonthlyProgressId = productsDevelopedModel.getEncMonthlyProgressId();
    			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId)*/
    			List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCateoryId);
    			if(null != progressDetails && progressDetails.size()>0){
    				domain.setMonthlyProgressDetails(progressDetails.get(0));
    			}
                
               // domain.setNumReportId(productsDevelopedModel.getNumReportId());
                domain.setStrProductsDeveloped(productsDevelopedModel.getStrProductsDeveloped());
                domain.setStrProductsDevelopedHtml(productsDevelopedModel.getStrProductsDevelopedHtml());
                domain.setStrStatus("SAD");  
          }
          
          
         
          
		String t= productsDevelopedDao.save(domain).getStrProductsDevelopedHtml();
		if(t!=null){
			return"success";
		}else{
		return "error";
		}
		}



	@Override
	public String getSavedProductsDeveloped(int reportId) {
		ProductDevelopedDomain productDevelopedDomain =null;
		productDevelopedDomain = productsDevelopedDao.getdetail(reportId);
		if(productDevelopedDomain!=null){
			return productDevelopedDomain.getStrProductsDevelopedHtml();
		} else{
			return null;
		}
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public void writeProductsDevelopedAuthorityCheck(){
		
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public void readProductsDevelopedAuthorityCheck(){
		
	}
	
	@Override
	public List<ProductsDevelopedModel> loadProductsDevelopedDetail(int monthlyProgressId,long categoryId){
		List<ProductDevelopedDomain> domainList = productsDevelopedDao.loadProductsDevelopedDetail(monthlyProgressId,categoryId);
		List<ProductsDevelopedModel> descrList=new ArrayList<ProductsDevelopedModel>();
		if(domainList.size()>0){
			for(int i=0;i<domainList.size();i++){
				ProductDevelopedDomain domain= domainList.get(i);
				ProductsDevelopedModel model=new ProductsDevelopedModel();
				model.setStrProductsDevelopedHtml(domain.getStrProductsDevelopedHtml());
				model.setNumId(domain.getNumId());
				descrList.add(model);
			}
			return descrList;
		}else{
		return null;
		}
	}



	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId) {
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<ProductDevelopedDomain> domainList = productsDevelopedDao.loadPublicationByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[1];
			obj[0] = "Description";
			
			dataList.add(obj);
		}
		
		for(ProductDevelopedDomain domain : domainList){
			Object[] obj = new Object[1];
			
			obj[0] =domain.getStrProductsDevelopedHtml();
		
			dataList.add(obj);
		}
		
		return dataList;		
	}
	
	@Override
	public void deleteProductsDeveloped(ProductsDevelopedModel productsDevelopedModel) {
		int count= productsDevelopedModel.getNumIds().length;
		//System.out.println("count"+count);
		int[] developedIds= new int[count];
		developedIds=productsDevelopedModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
	
			if(developedIds[i]!=0)
			{
				
				ProductDevelopedDomain productDevelopedDomain=new ProductDevelopedDomain();
				productDevelopedDomain=productsDevelopedDao.getOne(developedIds[i]);
				productDevelopedDomain.setNumIsValid(2);
				productDevelopedDomain.setDtTrDate(new Date());
				
				productsDevelopedDao.save(productDevelopedDomain);
			}
			
		}	
		
		
	}
	}