package in.pms.transaction.service;

import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.transaction.dao.MonthlyProgressDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.TotDetailsDao;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.TotDetailsDomain;
import in.pms.transaction.model.DeploymentTotDetailsModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TotdetailsServiceImpl implements TotdetailsService{

	public static final String rootPath =FTPPropertiesReader.getValueFromKey("FTP_ROOT");
	
	
	@Autowired
	TotDetailsDao totDetailsDao;
	
	@Autowired
	MonthlyProgressDetailsDao monthlyProgressDetailsDao;
	@Autowired
	MonthlyProgressDao monthlyProgressDao;
	/*@Autowired
	Deployment */
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	EncryptionService encryptionService;
	
	//====================================TOT Details=====================================
	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public boolean saveUpdateDTotDetails(HttpServletRequest request,
			DeploymentTotDetailsModel deploymentTotDetailsModel) {
		TotDetailsDomain totDetailsDomain=new TotDetailsDomain();
		boolean isFlag=false;
		
		totDetailsDomain.setStrDescription(deploymentTotDetailsModel.getStrDescription());
		totDetailsDomain.setStrAgencyName(deploymentTotDetailsModel.getStrAgencyName());
		totDetailsDomain.setStrProductName(deploymentTotDetailsModel.getStrAgencyCity());
		totDetailsDomain.setNumTotId(deploymentTotDetailsModel.getNumTotId());
		totDetailsDomain.setDtTrDate(new Date());
		try{
			totDetailsDomain.setDtDeploymenTotDate(DateUtils.dateStrToDate(deploymentTotDetailsModel.getStrDeploymentTotDate()));
			}
			catch (ParseException e) {				
				e.printStackTrace();
			}
		
		
		
		
		totDetailsDomain.setNumIsValid(1);
					
				String encCategoryId = deploymentTotDetailsModel.getEncCategoryId();
				String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
				long numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCategoryId);
				if(null != progressDetails && progressDetails.size()>0){
					totDetailsDomain.setMonthlyProgressDetails(progressDetails.get(0));
				}
				TotDetailsDomain totDetailsDomain2=totDetailsDao.save(totDetailsDomain);
				if(totDetailsDomain2!=null)
				isFlag=true;	
					
				/*} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		return isFlag;
	}
	
	@Override
	public List<DeploymentTotDetailsModel> getTotDetails(int monthlyProgressId,long numCategoryId) {
		
		List<TotDetailsDomain> list=totDetailsDao.getTotDetails(monthlyProgressId, numCategoryId);
		List<DeploymentTotDetailsModel> totDetailsList=new ArrayList<DeploymentTotDetailsModel>();
	   for(TotDetailsDomain dtd:list)
		{
			
			DeploymentTotDetailsModel deploymentTotDetailsModel=new DeploymentTotDetailsModel();
			deploymentTotDetailsModel.setNumTotId(dtd.getNumTotId());
			deploymentTotDetailsModel.setStrDescription(dtd.getStrDescription());
			deploymentTotDetailsModel.setStrAgencyName(dtd.getStrAgencyName());
			deploymentTotDetailsModel.setStrAgencyCity(dtd.getStrProductName());
			deploymentTotDetailsModel.setStrDeploymentTotDate(DateUtils
						.dateToString(dtd
								.getDtDeploymenTotDate()));
			totDetailsList.add(deploymentTotDetailsModel);
		}
		return totDetailsList;
	}
	
	@Override
	public void deleteTotDetails(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		int count= deploymentTotDetailsModel.getNumTotIds().length;
		//System.out.println("count"+count);
		long[] deploymentIds= new long[count];
		deploymentIds=deploymentTotDetailsModel.getNumTotIds();
		
		for(int i=0;i<count;i++)
		{
	
			if(deploymentIds[i]!=0)
			{
				
				TotDetailsDomain totDetailsDomain=new TotDetailsDomain();
				totDetailsDomain=totDetailsDao.getOne(deploymentIds[i]);
				totDetailsDomain.setNumIsValid(0);
				totDetailsDomain.setDtTrDate(new Date());
				
				totDetailsDao.save(totDetailsDomain);
			}
			
		}	
		
		
	}

	
}
