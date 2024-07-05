package in.pms.transaction.service;

import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.misc.MessageBundleFile;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.global.util.PMSFtp;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.States;
import in.pms.transaction.dao.DeploymentTotDetailsDao;
import in.pms.transaction.dao.MonthlyProgressDao;
import in.pms.transaction.dao.MonthlyProgressDetailsDao;
import in.pms.transaction.dao.ProgressReportDao;
import in.pms.transaction.dao.ProgressReportDocumentsDao;
import in.pms.transaction.dao.StatesDao;
import in.pms.transaction.domain.CategoryMaster;
import in.pms.transaction.domain.DeploymentTotDetailsDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;
import in.pms.transaction.domain.MonthlyProgressDomain;
import in.pms.transaction.domain.ProgressReportDocumentsDomain;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.ShowNextPrevBtnModel;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProgressReportServiceImpl implements ProgressReportService{

	public static final String rootPath =FTPPropertiesReader.getValueFromKey("FTP_ROOT");
	
	@Autowired
	ProgressReportDao progressReportDao;
	@Autowired
	DeploymentTotDetailsDao deploymentTotDetailsDao;
	@Autowired
	StatesDao statesDao;
	@Autowired
	ProgressReportDocumentsDao progressReportDocumentsDao;
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
	
	@Override
	public List<CategoryMasterModel> getAllCategoryList(String strRole) {
		List<CategoryMaster> categoryMasterList=new ArrayList<CategoryMaster>();
		if(strRole.equals("P"))
		categoryMasterList=progressReportDao.getAllCategoryList(strRole);
		else if(strRole.equals("G"))
			categoryMasterList=progressReportDao.getAllCategoryListForGC();
			
		return convertDomainToModel(categoryMasterList);
	}
	
	public List<CategoryMasterModel> getAllCategoryByFlag(String strRole){
		List<CategoryMaster> categoryMasterList=new ArrayList<CategoryMaster>();
		
		categoryMasterList=progressReportDao.getAllCategoryList(strRole);	
		return convertDomainToModel(categoryMasterList);
	}

	private List<CategoryMasterModel> convertDomainToModel(List<CategoryMaster> categoryMasterList) {
		List<CategoryMasterModel> categoryMasterModelsList=new ArrayList<CategoryMasterModel>();
		for(CategoryMaster catmst:categoryMasterList)
		{
			CategoryMasterModel categoryMasterModel=new CategoryMasterModel();
			categoryMasterModel.setNumCategoryId(catmst.getNumCategoryId());
			categoryMasterModel.setEncCategoryId(encryptionService.encrypt(""+catmst.getNumCategoryId()));
			categoryMasterModel.setStrCategoryName(catmst.getStrCategoryName());
			categoryMasterModel.setNumCategorySequence(catmst.getNumCategorySequence());
			categoryMasterModel.setIsUploadRequired(catmst.getIsUploadRequired());
			categoryMasterModel.setStrDescription(catmst.getStrDescription());
			categoryMasterModel.setStrProjectGroupFlag(catmst.getStrProjectGroupFlag());
			categoryMasterModel.setStrCategoryController(catmst.getStrCategoryController());
			categoryMasterModelsList.add(categoryMasterModel);
		
			
		}
		return categoryMasterModelsList;
	}

	@Override
	public List<CategoryMasterModel> getSubCategoryList(long numCategoryId) {
		List<CategoryMaster> categoryMasterList=new ArrayList<CategoryMaster>();
		String strCategory=String.valueOf(numCategoryId);
		int numCategoryId1=Integer.parseInt(strCategory);
		categoryMasterList=progressReportDao.getSubCategoryList(numCategoryId1);
		
		return convertDomainToModel(categoryMasterList);

	}

	@Override
	@PreAuthorize("hasAuthority('WRITE_FOR_PROGRESS_REPORT')")
	public boolean saveUpdateDeploymentDetails(HttpServletRequest request,
			DeploymentTotDetailsModel deploymentTotDetailsModel) {
		DeploymentTotDetailsDomain deploymentTotDetailsDomain=new DeploymentTotDetailsDomain();
		boolean isFlag=false;
		/*try {*/
			  
				deploymentTotDetailsDomain.setStrServiceName(deploymentTotDetailsModel.getStrServiceName());
				deploymentTotDetailsDomain.setStrDescription(deploymentTotDetailsModel.getStrDescription());
				deploymentTotDetailsDomain.setStrAgencyName(deploymentTotDetailsModel.getStrAgencyName());
				deploymentTotDetailsDomain.setNumAgencyStateId(deploymentTotDetailsModel.getNumAgencyStateId());
				
				deploymentTotDetailsDomain.setStrAgencyCity(deploymentTotDetailsModel.getStrAgencyCity());
				deploymentTotDetailsDomain.setNumDeploymentId(deploymentTotDetailsModel.getNumDeploymentId());
				deploymentTotDetailsDomain.setNumDeploymentStateId(deploymentTotDetailsModel.getNumDeploymentStateId());
				deploymentTotDetailsDomain.setStrDeploymentCity(deploymentTotDetailsModel.getStrDeploymentCity());
				deploymentTotDetailsDomain.setDtTrDate(new Date());
				if(null != deploymentTotDetailsModel.getDtDeploymenTotDate()){
				try {
					deploymentTotDetailsDomain.setDtDeploymenTotDate(DateUtils.dateStrToDate(deploymentTotDetailsModel.getDtDeploymenTotDate()));
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				}
				deploymentTotDetailsDomain.setNumIsValid(1);
				if(deploymentTotDetailsModel.getStrDocumentIds()!=null && !deploymentTotDetailsModel.getStrDocumentIds().equals(""))
				{
					
					deploymentTotDetailsDomain.setStrDocumentIds(deploymentTotDetailsModel.getStrDocumentIds());
				}	
				String encCategoryId = deploymentTotDetailsModel.getEncCategoryId();
				String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
				long numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				List<MonthlyProgressDetails> progressDetails = monthlyProgressDetailsDao.getMonthlyProgressDetails(monthlyProgressId, numCategoryId);
				if(null != progressDetails && progressDetails.size()>0){
					deploymentTotDetailsDomain.setMonthlyProgressDetails(progressDetails.get(0));
				}
				deploymentTotDetailsDomain.setNumUnitsDeployedOrSold(deploymentTotDetailsModel.getNumUnitsDeployedOrSold());
				DeploymentTotDetailsDomain deploymentTotDetailsDomain2=deploymentTotDetailsDao.save(deploymentTotDetailsDomain);
				if(deploymentTotDetailsDomain2!=null)
				isFlag=true;	
					
				/*} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		return isFlag;
	}

	@Override
	public List<States> getStateList() {

		List<States> listState = new ArrayList<States>();
		List<States> arrlistState = new ArrayList<States>();
		int i = 0;
		try{
		arrlistState = statesDao.GetStateList();
		
		Iterator<States> itr = arrlistState.iterator();
		
		while(itr.hasNext())
		{
			States stm = new States();
			States st = new States();
			stm = itr.next();
			st.setNumStateId(stm.getNumStateId());
			st.setStrStateName(stm.getStrStateName());
			listState.add(i, st);
			i++;
		}
		}
		catch(Exception e)
		{
			System.out.println("error is in userprofileService impl getStateList     "+e);
		}
		return listState;
		
		
	
	}

	@Override
	public List<DeploymentTotDetailsModel> getDeploymentDetails(int monthlyProgressId,long numCategoryId) {
		
		List<DeploymentTotDetailsDomain> list=deploymentTotDetailsDao.getDeploymentDetails(monthlyProgressId, numCategoryId);
		List<DeploymentTotDetailsModel>  deploymentTotDetailsList=new ArrayList<DeploymentTotDetailsModel>();
	    States states=null;
		for(DeploymentTotDetailsDomain dtd:list)
		{
			
			DeploymentTotDetailsModel deploymentTotDetailsModel=new DeploymentTotDetailsModel();
			deploymentTotDetailsModel.setNumDeploymentId(dtd.getNumDeploymentId());
			deploymentTotDetailsModel.setStrServiceName(dtd.getStrServiceName());
			deploymentTotDetailsModel.setStrDescription(dtd.getStrDescription());
			deploymentTotDetailsModel.setStrAgencyName(dtd.getStrAgencyName());
			states=statesDao.getOne(dtd.getNumAgencyStateId());
			deploymentTotDetailsModel.setNumAgencyStateId(dtd.getNumAgencyStateId());
			deploymentTotDetailsModel.setStrAgencyState(states.getStrStateName());
			states=statesDao.getOne(dtd.getNumDeploymentStateId());
			deploymentTotDetailsModel.setStrDeploymentState(states.getStrStateName());
			deploymentTotDetailsModel.setStrAgencyCity(dtd.getStrAgencyCity());
			deploymentTotDetailsModel.setNumDeploymentId(dtd.getNumDeploymentId());
			deploymentTotDetailsModel.setNumDeploymentStateId(dtd.getNumDeploymentStateId());
			deploymentTotDetailsModel.setStrDeploymentCity(dtd.getStrDeploymentCity());
			deploymentTotDetailsModel.setStrDocumentIds(dtd.getStrDocumentIds());
			deploymentTotDetailsModel.setStrDeploymentTotDate(DateUtils
						.dateToString(dtd
								.getDtDeploymenTotDate()));
			/*deploymentTotDetailsModel.setNumGroupCategoryId(dtd.getNumGroupCategoryId());*/
			deploymentTotDetailsModel.setNumUnitsDeployedOrSold(dtd.getNumUnitsDeployedOrSold());
			deploymentTotDetailsList.add(deploymentTotDetailsModel);
		}
		return deploymentTotDetailsList;
	}

	@Override
	public void deleteDeployementDetails(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		int count= deploymentTotDetailsModel.getNumDeploymentIds().length;
		//System.out.println("count"+count);
		long[] deploymentIds= new long[count];
		deploymentIds=deploymentTotDetailsModel.getNumDeploymentIds();
		
		for(int i=0;i<count;i++)
		{
	
			if(deploymentIds[i]!=0)
			{
				
				DeploymentTotDetailsDomain deploymentTotDetailsDomain=new DeploymentTotDetailsDomain();
				deploymentTotDetailsDomain=deploymentTotDetailsDao.getOne(deploymentIds[i]);
				deploymentTotDetailsDomain.setNumIsValid(0);
				deploymentTotDetailsDomain.setDtTrDate(new Date());
				try {
					
				
				if(deploymentTotDetailsDomain.getStrDocumentIds()!=null)
				{
					String[] arrDocIds=deploymentTotDetailsDomain.getStrDocumentIds().split(",");
				    long[] numDeploymentIds;

				    
				   numDeploymentIds=Arrays.stream(arrDocIds).mapToLong(Long::parseLong).toArray();
					DeploymentTotDetailsModel deploymentTotDetailsModel2=new DeploymentTotDetailsModel();
					deploymentTotDetailsModel2.setNumDocumentsIds(numDeploymentIds);
					deploymentTotDetailsModel2.setEncCategoryId(deploymentTotDetailsModel.getEncCategoryId());
					deploymentTotDetailsModel2.setEncMonthlyProgressId(deploymentTotDetailsModel.getEncMonthlyProgressId());
					deleteImageDetails(deploymentTotDetailsModel2);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
				deploymentTotDetailsDao.save(deploymentTotDetailsDomain);
			}
			
		}	
		
		
	}

	@Override
	public long uploadProgressRportImages(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		
		boolean result=false;
		long numDocId=0;
		try {
			
		
		Calendar calendar = GregorianCalendar.getInstance();			
		String fileName= deploymentTotDetailsModel.getStrDocumentCaption()+"_"+calendar.getTimeInMillis();
		ProgressReportDocumentsDomain progressReportDocumentsDomain=new ProgressReportDocumentsDomain();
		String originalFileName= deploymentTotDetailsModel.getProgressReportQualityImages().getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFileName);
		String tempFileName = fileName+".";
		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=deploymentTotDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		MonthlyProgressDomain monthlyProgressDomain=new MonthlyProgressDomain();
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			monthlyProgressDomain=monthlyProgressDao.getOne(monthlyProgressId);
			
		}
		result=fileUploadService.uploadProgressRportImages(deploymentTotDetailsModel.getProgressReportQualityImages(), numCategoryId,monthlyProgressDomain,tempFileName+extension);
		if(result)
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			progressReportDocumentsDomain.setStrDocumentName(tempFileName+extension);
			//progressReportDocumentsDomain.setStrDocumentFormat(strDocumentFormat);
			progressReportDocumentsDomain.setOriginalDocumentName(deploymentTotDetailsModel.getProgressReportQualityImages().getOriginalFilename());
			//progressReportDocumentsDomain.setNumCategoryId(0);
			progressReportDocumentsDomain.setNumTrUserId(userInfo.getEmployeeId());
			progressReportDocumentsDomain.setDtTrDate(new Date());
			progressReportDocumentsDomain.setStrDocumentCaption(deploymentTotDetailsModel.getStrDocumentCaption());
			progressReportDocumentsDomain.setNumIsValid(1);
			//progressReportDocumentsDomain.setNumGroupCategoryId(deploymentTotDetailsModel.getNumGroupCategoryId());
			ProgressReportDocumentsDomain progressReportDocumentsDomain2=progressReportDocumentsDao.save(progressReportDocumentsDomain);
			if(progressReportDocumentsDomain2!=null)
			numDocId=progressReportDocumentsDomain2.getNumDocumentId();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numDocId;
	}


	
	@Override
	public List<DeploymentTotDetailsModel> getUplodedImages(String strDocumentIds) {
		List<DeploymentTotDetailsModel> deploymentTotDetailsList=new ArrayList<DeploymentTotDetailsModel>();
		if(strDocumentIds!=null && !strDocumentIds.equals(""))
		{
			
			/*String strDocId="";
			if (strDocumentIds.startsWith(",") && strDocumentIds.endsWith(",")) {
				strDocId = strDocumentIds.substring(1, strDocumentIds.length() - 1);
					
			}
			else if(strDocumentIds.endsWith(","))
			{
				strDocId = strDocumentIds.substring(0, strDocumentIds.length() - 1);
				
			}
			else if(strDocumentIds.startsWith(","))
			{
				strDocId = strDocumentIds.substring(1, strDocumentIds.length());
				
			}
			else
			{
				strDocId=strDocumentIds;
			}
		    */String[] arrDocIds=strDocumentIds.split(",");
		    List<ProgressReportDocumentsDomain> progressReportDocumentList=null;
		    try {
		    	 List<String> list = Arrays.asList(arrDocIds);

					List<Long> newList = list.stream()
												.map(s -> Long.parseLong(s))
												.collect(Collectors.toList());
						progressReportDocumentList=progressReportDocumentsDao.getUplodedImages(newList);

			} catch (Exception e) {
				// TODO: handle exception
			}
		   			
			
			for(ProgressReportDocumentsDomain prd:progressReportDocumentList)
			{
				DeploymentTotDetailsModel deploymentTotDetailsModel=new DeploymentTotDetailsModel();
				deploymentTotDetailsModel.setNumDocumentId(prd.getNumDocumentId());
				deploymentTotDetailsModel.setStrDocumentName(prd.getStrDocumentName());
				deploymentTotDetailsModel.setStrDocumentCaption(prd.getStrDocumentCaption());
				deploymentTotDetailsList.add(deploymentTotDetailsModel);
				
			}
		}
		return deploymentTotDetailsList;
	}

	@Override
	public boolean downloadDeploymentImages(DeploymentTotDetailsModel deploymentTotDetailsModel, HttpServletResponse response) {

		boolean result = false;
		//String documentId = encryptionService.dcrypt(encDocumentId);
		
				
		ProgressReportDocumentsDomain progressDocumentDetail =progressReportDocumentsDao.getOne(deploymentTotDetailsModel.getNumDocumentId());
		
		String fileName= progressDocumentDetail.getStrDocumentName();
		String originalFileName= progressDocumentDetail.getOriginalDocumentName();
		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=deploymentTotDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		MonthlyProgressDomain monthlyProgressDomain=new MonthlyProgressDomain();
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			monthlyProgressDomain=monthlyProgressDao.getOne(monthlyProgressId);
			
		}
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/ProgressReportFiles/"+monthlyProgressDomain.getYear()+"/"+monthlyProgressDomain.getMonth()+"/"+monthlyProgressDomain.getProjectMasterDomain().getNumId()+"/"+numCategoryId;			
			try {				
				
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);					
				InputStream inputStream = ftpClient.retrieveFileStream(rootPath+"/"+filePath+"/"+fileName);				
				response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");
				response.setContentType("application/"+ originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length()));		
				response.getOutputStream().write(IOUtils.toByteArray(inputStream));
				response.flushBuffer();
	          
			} catch (IOException e) {			
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}	
		return result;
	
		
	}

	@Override
	public boolean deleteImageDetails(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		
		int count= deploymentTotDetailsModel.getNumDocumentsIds().length;
		//System.out.println("count"+count);
		long[] documentIds= new long[count];
		documentIds=deploymentTotDetailsModel.getNumDocumentsIds();
		boolean deleted =false;
		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=deploymentTotDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		MonthlyProgressDomain monthlyProgressDomain=new MonthlyProgressDomain();
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			monthlyProgressDomain=monthlyProgressDao.getOne(monthlyProgressId);
			
		}
		
		for(int i=0;i<count;i++)
		{
			if(documentIds[i]!=0)
			{
				ProgressReportDocumentsDomain prReportDocumentsDomain=new ProgressReportDocumentsDomain(); 
			    prReportDocumentsDomain=progressReportDocumentsDao.getOne(documentIds[i]);
			    prReportDocumentsDomain.setNumIsValid(0);
			    progressReportDocumentsDao.save(prReportDocumentsDomain);
			    String filePath = "PMS"+"/ProgressReportFiles/"+monthlyProgressDomain.getYear()+"/"+monthlyProgressDomain.getMonth()+"/"+monthlyProgressDomain.getProjectMasterDomain().getNumId()+"/"+numCategoryId+"/"+prReportDocumentsDomain.getStrDocumentName();
			    deleted = fileUploadService.deleteFile(filePath);
			}
			else
				deleted=false;
		}
		return deleted;
	}

	@Override
	public ShowNextPrevBtnModel getPrevNextBtnController(
			String encCategoryId, String encMonthlyProgressId) {
		
		ShowNextPrevBtnModel showNextPrevBtnModel=new ShowNextPrevBtnModel();
		String strDecCategoryId=encryptionService.dcrypt(encCategoryId);
		String strDecMonthlyProgressId=encryptionService.dcrypt(encMonthlyProgressId);
		List<MonthlyProgressDetails> monthlyProgressDetailList=new ArrayList<MonthlyProgressDetails>();
		if(strDecCategoryId!=null && !strDecCategoryId.equals("") && strDecMonthlyProgressId!=null && !strDecMonthlyProgressId.equals(""))
		{
			 long numCategoryId=Long.parseLong(strDecCategoryId);
			 int numDecMonthlyProgressId=Integer.parseInt(strDecMonthlyProgressId);
			 monthlyProgressDetailList=monthlyProgressDetailsDao.getMonthlyProgressDetails(numDecMonthlyProgressId);
			 
			if(monthlyProgressDetailList!=null && monthlyProgressDetailList.size()>1){
			boolean closeLoop = false;
				
				MonthlyProgressDetails monthlyProgressDetails = monthlyProgressDetailList.stream()
						.filter(x -> numCategoryId == x.getNumCateoryId())
						.findAny().orElse(null);
				
				int currentCategoryIndex = monthlyProgressDetailList.indexOf(monthlyProgressDetails);
				//Decrease By 1 to compare with index				
				int totalItemsInList = monthlyProgressDetailList.size()-1;
				
				//int nextIndex=currentCategoryIndex+1;
				int nextIndex = 0;
				if (currentCategoryIndex == totalItemsInList){
					nextIndex =-1;
				}else if(currentCategoryIndex < totalItemsInList){
					nextIndex =currentCategoryIndex+1;
				} 
				
				int prevIndex=0;
				if(currentCategoryIndex ==  0){
					prevIndex = -1;
				}else{
					prevIndex = currentCategoryIndex-1;
				}
						
				
				List<CategoryMaster> prevCategories = null;
				List<CategoryMaster> nextCategories = null;
				
				while(closeLoop == false){
				
				if(prevIndex >= 0 &&  (nextIndex <= totalItemsInList && nextIndex !=-1)){
					//Find Prev and Next Item URL
					MonthlyProgressDetails prevProgressDetails = monthlyProgressDetailList.get(prevIndex);
					MonthlyProgressDetails nextProgressDetails = monthlyProgressDetailList.get(nextIndex);
					
					if(null == prevCategories){
						prevCategories =progressReportDao.getCategoryDetailsById(prevProgressDetails.getNumCateoryId());
					}
					
					if(null == nextCategories){
						nextCategories =progressReportDao.getCategoryDetailsById(nextProgressDetails.getNumCateoryId());
					}
					
					
					if(null != prevCategories && prevCategories.size()>0){
						CategoryMaster prevCategory = prevCategories.get(0);
						if(null != prevCategory.getStrCategoryController() && !prevCategory.getStrCategoryController().equals("")){
							showNextPrevBtnModel.setStrPrevController(prevCategory.getStrCategoryController());
							showNextPrevBtnModel.setEncPrevCategoryId(encryptionService.encrypt(""+prevCategory.getNumCategoryId()));
							
						}else{
							if(prevIndex >0){
								prevIndex -=1;
								prevCategories = null;
								continue;
							}else{
								closeLoop= true;
							}
						}
					}
					
					if(null != nextCategories && nextCategories.size()>0){
						CategoryMaster nextCategory = nextCategories.get(0);
						if(null != nextCategory.getStrCategoryController() && !nextCategory.getStrCategoryController().equals("")){
							showNextPrevBtnModel.setStrNextController(nextCategory.getStrCategoryController());
							showNextPrevBtnModel.setEncNextCategoryId(encryptionService.encrypt(""+nextCategory.getNumCategoryId()));
							closeLoop= true;
						}else{
							if(nextIndex < totalItemsInList){
								nextIndex +=1;
								nextCategories = null;
								continue;
							}else{
								closeLoop= true;
							}
						}
					}
					
				}else if(prevIndex == -1){
					//Find Next Item URL
					MonthlyProgressDetails nextProgressDetails = monthlyProgressDetailList.get(nextIndex);
					nextCategories =progressReportDao.getCategoryDetailsById(nextProgressDetails.getNumCateoryId());
					if(null != nextCategories && nextCategories.size()>0){
						CategoryMaster nextCategory = nextCategories.get(0);
						if(null != nextCategory.getStrCategoryController() && !nextCategory.getStrCategoryController().equals("")){
							showNextPrevBtnModel.setStrNextController(nextCategory.getStrCategoryController());
							showNextPrevBtnModel.setEncNextCategoryId(encryptionService.encrypt(""+nextCategory.getNumCategoryId()));
							closeLoop= true;
						}else{
							if(nextIndex < totalItemsInList){
								nextIndex +=1;
								nextCategories = null;
								continue;
							}else{
								closeLoop= true;
							}
						}
					}
					
				}else if(nextIndex == -1){
					//Find Prev Item URL
					MonthlyProgressDetails prevProgressDetails = monthlyProgressDetailList.get(prevIndex);
					prevCategories =progressReportDao.getCategoryDetailsById(prevProgressDetails.getNumCateoryId());
					if(null != prevCategories && prevCategories.size()>0){
						CategoryMaster prevCategory = prevCategories.get(0);
						if(null != prevCategory.getStrCategoryController() && !prevCategory.getStrCategoryController().equals("")){
							showNextPrevBtnModel.setStrPrevController(prevCategory.getStrCategoryController());
							showNextPrevBtnModel.setEncPrevCategoryId(encryptionService.encrypt(""+prevCategory.getNumCategoryId()));
							if(prevCategory.getStrProjectGroupFlag().equalsIgnoreCase("G")){
								showNextPrevBtnModel.setCategoryType("G");
							}
							else{
								showNextPrevBtnModel.setCategoryType("P");
							}
							closeLoop = true;
							
						}else{
							if(prevIndex >0){
								prevIndex -=1;
								prevCategories = null;
								continue;
							}else{
								closeLoop= true;
							}
						}
					}
				}
				
				}
			}
			if(monthlyProgressDetailList!=null && monthlyProgressDetailList.size()==1){
				boolean closeLoop = false;
					
					MonthlyProgressDetails monthlyProgressDetails = monthlyProgressDetailList.stream()
							.filter(x -> numCategoryId == x.getNumCateoryId())
							.findAny().orElse(null);
					
					int currentCategoryIndex = monthlyProgressDetailList.indexOf(monthlyProgressDetails);
					MonthlyProgressDetails prevProgressDetails = monthlyProgressDetailList.get(currentCategoryIndex);
					List<CategoryMaster> prevCategories =progressReportDao.getCategoryDetailsById(prevProgressDetails.getNumCateoryId());
					if(null != prevCategories && prevCategories.size()>0){
						CategoryMaster prevCategory = prevCategories.get(0);
						if(null != prevCategory.getStrCategoryController() && !prevCategory.getStrCategoryController().equals("")){
							if(prevCategory.getStrProjectGroupFlag().equalsIgnoreCase("G")){
								showNextPrevBtnModel.setCategoryType("G");
							}
							else{
								showNextPrevBtnModel.setCategoryType("P");
							}
							closeLoop = true;
							
						}
						else{
							
								closeLoop= true;
							}
						}
					}
		
			}
		
		
		return showNextPrevBtnModel;
	}
	@Override
	public List<Object[]> getPreviewDataWithHeaderForTOT(long progressDetailsId){
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<DeploymentTotDetailsDomain> domainList = deploymentTotDetailsDao.loadDeploymentDetailsByDetailsId(progressDetailsId);
		if(null != domainList && domainList.size()>0){
			Object[] obj = new Object[9];
			obj[0] = "Service";
			obj[1] = "Date";
			obj[2] = MessageBundleFile.getValueFromKey("deployment.agency.name");
			obj[3] = MessageBundleFile.getValueFromKey("deployment.agency.state");
			obj[4] = MessageBundleFile.getValueFromKey("deployment.agency.city");
			obj[5] = MessageBundleFile.getValueFromKey("deployment.state");
			obj[6] = MessageBundleFile.getValueFromKey("deployment.city");
			obj[7] = MessageBundleFile.getValueFromKey("deployment.service.unit.sold");
			obj[8] = "";
			dataList.add(obj);
		}
		
		for(DeploymentTotDetailsDomain domain : domainList){
			Object[] obj = new Object[9];
			States states=null;
			List<States> state=statesDao.GetStateList();
			String ast=null;
			String dst=null;
			for(int i=0;i<state.size();i++){
				if(state.get(i).getNumStateId()==domain.getNumAgencyStateId()){
					ast=state.get(i).getStrStateName();
				}
			}
			for(int i=0;i<state.size();i++){
				if(state.get(i).getNumStateId()==domain.getNumDeploymentStateId()){
					dst=state.get(i).getStrStateName();
				}
			}
			
			obj[0] =domain.getStrServiceName();
			if(null != domain.getDtDeploymenTotDate()){
				obj[1] =DateUtils.dateToString(domain.getDtDeploymenTotDate());
			}else{
				obj[1] = "";
			}
			obj[2] = domain.getStrAgencyName();
			obj[3] = ast;
			obj[4] = domain.getStrAgencyCity();
			obj[5] = dst;
			obj[6] = domain.getStrDeploymentCity();
			obj[7] = domain.getNumUnitsDeployedOrSold();
			obj[8]= "<a onclick=getTotDocs("+domain.getNumDeploymentId()+")>view Document</a>";
			dataList.add(obj);
		}
		
		return dataList;		
	}
	
}
