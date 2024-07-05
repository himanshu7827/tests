package in.pms.transaction.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.States;
import in.pms.transaction.dao.MonthlyProgressDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProductServiceDetailsDao;
import in.pms.transaction.dao.ProgressReportDao;
import in.pms.transaction.dao.StatesDao;
import in.pms.transaction.domain.DeploymentTotDetailsDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.ProductServiceDetailsDomain;
import in.pms.transaction.domain.ProjectPublicationDomain;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.ProductServiceDetailsModel;

@Service
public class ProductServiceDetailsServiceImpl implements ProductServiceDetailsService {

	@Autowired
	ProductServiceDetailsDao productServiceDetailsDao;
	@Autowired
	ProgressReportDao progressReportDao;
	@Autowired 
	StatesDao statesDao;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	@Autowired
	MonthlyProgressDao monthlyProgressDao;
	@Autowired
	ProgressReportService progressReportService;
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public boolean saveUpdateProductServiceDetails(HttpServletRequest request,
			ProductServiceDetailsModel productServiceDetailsModel) {

		ProductServiceDetailsDomain productServiceDetailsDomain=new ProductServiceDetailsDomain();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		boolean isFlag=false;
		productServiceDetailsDomain.setNumProductServiceDetailsId(productServiceDetailsModel.getNumProductServiceDetailsId());
		productServiceDetailsDomain.setStrServiceTitle(productServiceDetailsModel.getStrServiceTitle());
		productServiceDetailsDomain.setStrDescription(productServiceDetailsModel.getStrDescription());
		productServiceDetailsDomain.setStrCdacRole(productServiceDetailsModel.getStrCdacRole());
		productServiceDetailsDomain.setStrCollaborator(productServiceDetailsModel.getStrCollaborator());
		productServiceDetailsDomain.setStrCollaboratorsTotParners(productServiceDetailsModel.getStrCollaboratorsTotParners());
		productServiceDetailsDomain.setStrCoordinator(productServiceDetailsModel.getStrCoordinator());
		productServiceDetailsDomain.setStrInauguratedBy(productServiceDetailsModel.getStrInauguratedBy());
		productServiceDetailsDomain.setStrObjective(productServiceDetailsModel.getStrObjective());
		productServiceDetailsDomain.setStrPastDeploymentDetails(productServiceDetailsModel.getStrPastDeploymentDetails());
		if(null != productServiceDetailsModel.getDtProductServiceDate()){
		try {
			productServiceDetailsDomain.setDtProductServiceDate(DateUtils.dateStrToDate(productServiceDetailsModel.getDtProductServiceDate()));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		}
		productServiceDetailsDomain.setNumStateId(productServiceDetailsModel.getNumStateId());
		productServiceDetailsDomain.setStrCity(productServiceDetailsModel.getStrCity());
		productServiceDetailsDomain.setNumIsValid(1);
		if(productServiceDetailsModel.getStrDocumentIds()!=null && !productServiceDetailsModel.getStrDocumentIds().equals(""))
		{
			
			productServiceDetailsDomain.setStrDocumentIds(productServiceDetailsModel.getStrDocumentIds());
		}
		String encCategoryId = productServiceDetailsModel.getEncCategoryId();
		String encMonthlyProgressId = productServiceDetailsModel.getEncMonthlyProgressId();
		long numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
		int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCategoryId);
		if(null != progressDetails && progressDetails.size()>0){
			productServiceDetailsDomain.setMonthlyProgressDetails(progressDetails.get(0));
		}
		productServiceDetailsDomain.setDtTrDate(new Date());
		productServiceDetailsDomain.setNumTrUserId(userInfo.getEmployeeId());
		
		 ProductServiceDetailsDomain productServiceDetailsDomain2=productServiceDetailsDao.save(productServiceDetailsDomain);
		if(productServiceDetailsDomain2!=null)
			isFlag=true;
		
		return isFlag;
	
	}

	@Override
	public List<ProductServiceDetailsModel> getProductServiceDetails(int monthlyProgressId,long numCategoryId) {
			
		List<ProductServiceDetailsDomain> productServiceDetailsList=productServiceDetailsDao.getProductServiceDetails(monthlyProgressId,numCategoryId);
		List<ProductServiceDetailsModel> productServiceDetailsModelList=new ArrayList<ProductServiceDetailsModel>();
		States states=null;
		for(ProductServiceDetailsDomain psd:productServiceDetailsList)
		{
			ProductServiceDetailsModel productServiceDetailsModel=new ProductServiceDetailsModel();
			productServiceDetailsModel.setNumProductServiceDetailsId(psd.getNumProductServiceDetailsId());
			productServiceDetailsModel.setStrServiceTitle(psd.getStrServiceTitle());
			productServiceDetailsModel.setStrDescription(psd.getStrDescription());
			productServiceDetailsModel.setStrCdacRole(psd.getStrCdacRole());
			productServiceDetailsModel.setStrCollaborator(psd.getStrCollaborator());
			productServiceDetailsModel.setStrCollaboratorsTotParners(psd.getStrCollaboratorsTotParners());
			productServiceDetailsModel.setStrCoordinator(psd.getStrCoordinator());
			productServiceDetailsModel.setStrInauguratedBy(psd.getStrInauguratedBy());
			productServiceDetailsModel.setStrObjective(psd.getStrObjective());
			productServiceDetailsModel.setStrPastDeploymentDetails(psd.getStrPastDeploymentDetails());
			productServiceDetailsModel.setDtProductServiceDate(DateUtils.dateToString(psd.getDtProductServiceDate()));
			productServiceDetailsModel.setStrProductServiceDate(DateUtils
						.dateToString(psd
								.getDtProductServiceDate()));
			states=statesDao.getOne(psd.getNumStateId());
			productServiceDetailsModel.setNumStateId(states.getNumStateId());
			productServiceDetailsModel.setStrState(states.getStrStateName());
			productServiceDetailsModel.setStrDocumentIds(psd.getStrDocumentIds());
			productServiceDetailsModel.setNumStateId(psd.getNumStateId());
			productServiceDetailsModel.setStrCity(psd.getStrCity());
			productServiceDetailsModelList.add(productServiceDetailsModel);
		

		}
		return productServiceDetailsModelList;
	}

	@Override
	public void deleteProductServiceDetails(ProductServiceDetailsModel productServiceDetailsModel) {
		int count= productServiceDetailsModel.getNumProductServiceDetailsIds().length;
		//System.out.println("count"+count);
		long[] productServiceIds= new long[count];
		productServiceIds=productServiceDetailsModel.getNumProductServiceDetailsIds();
		
		for(int i=0;i<count;i++)
		{
			/*ProductServiceDetailsModel productServiceDetailsModel2 = new ProductServiceDetailsModel();
			productServiceDetailsModel2.setNumProductServiceDetailsId(productServiceIds[i]);
			*/
			

			//productServiceDetailsDao.deleteProductServiceDetails(productServiceDetailsModel2);
			ProductServiceDetailsDomain pro=productServiceDetailsDao.getOne(productServiceIds[i]);
			pro.setNumIsValid(0);
			pro.setDtTrDate(new Date());
            try {
					
				
				if(pro.getStrDocumentIds()!=null && !pro.getStrDocumentIds().equals(""))
				{
					String[] arrDocIds=pro.getStrDocumentIds().split(",");
				    long[] numDeploymentIds;

				    
				   numDeploymentIds=Arrays.stream(arrDocIds).mapToLong(Long::parseLong).toArray();
					DeploymentTotDetailsModel deploymentTotDetailsModel2=new DeploymentTotDetailsModel();
					deploymentTotDetailsModel2.setNumDocumentsIds(numDeploymentIds);
					deploymentTotDetailsModel2.setEncCategoryId(productServiceDetailsModel.getEncCategoryId());
					deploymentTotDetailsModel2.setEncMonthlyProgressId(productServiceDetailsModel.getEncMonthlyProgressId());
					progressReportService.deleteImageDetails(deploymentTotDetailsModel2);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}

			productServiceDetailsDao.save(pro);
			
		}	
		

		
	}
	
	@Override
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<ProductServiceDetailsDomain> domainList = productServiceDetailsDao.loadProductServiceByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[8];
			obj[0] = "Service";
			obj[1] = "Date";
			obj[2] = MessageBundleFile.getValueFromKey("product.inaugurated.by");
			obj[3] = MessageBundleFile.getValueFromKey("cdac.role");
			obj[4] = MessageBundleFile.getValueFromKey("product.past_deployment");
			obj[5] = MessageBundleFile.getValueFromKey("label.state");
			obj[6] = MessageBundleFile.getValueFromKey("label.city");
			obj[7] = "";
			dataList.add(obj);
		}
		
		for(ProductServiceDetailsDomain domain : domainList){
			List<States> state=statesDao.GetStateList();
			String st=null;
			for(int i=0;i<state.size();i++){
				if(state.get(i).getNumStateId()==domain.getNumStateId()){
					st=state.get(i).getStrStateName();
				}
			}
			//String d=state.getStrStateName();
			Object[] obj = new Object[8];
			obj[0] =domain.getStrServiceTitle();
			if(null != domain.getDtProductServiceDate()){
				obj[1] =DateUtils.dateToString(domain.getDtProductServiceDate());
			}else{
				obj[1] = "";
			}
			obj[2] = domain.getStrInauguratedBy();
			obj[3] = domain.getStrCdacRole();
			obj[4]=domain.getStrPastDeploymentDetails();
			obj[5] = st;
			obj[6] = domain.getStrCity();
			obj[7]= "<a onclick=getProdServiceDocs("+domain.getNumProductServiceDetailsId()+")>view Document</a>";
			
			dataList.add(obj);
		}
		
		return dataList;		
	}

}
