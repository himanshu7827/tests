package in.pms.transaction.controller;

import in.pms.global.dao.TransactionDao;
import in.pms.global.model.ProceedingModel;
import in.pms.global.model.WorkflowModel;
import in.pms.global.service.EncryptionService;
import in.pms.global.service.WorkflowService;
import in.pms.login.service.TransactionActivityService;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.domain.States;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.AwardWonService;
import in.pms.master.service.DocumentFormatService;
import in.pms.master.service.MediaService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.SeminarEventService;
import in.pms.transaction.model.CategoryMasterModel;
import in.pms.transaction.model.DeploymentTotDetailsModel;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ProductServiceDetailsModel;
import in.pms.transaction.model.ShowNextPrevBtnModel;
import in.pms.transaction.service.AdditionalQualificationService;
import in.pms.transaction.service.AppreciationLetterService;
import in.pms.transaction.service.ApprovedJobService;
import in.pms.transaction.service.CopyrightService;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.MouCollaborationService;
import in.pms.transaction.service.OthersService;
import in.pms.transaction.service.PatentDetailsService;
import in.pms.transaction.service.ProductServiceDetailsService;
import in.pms.transaction.service.ProductsDevelopedService;
import in.pms.transaction.service.ProgressReportService;
import in.pms.transaction.service.ProjectHighlightsService;
import in.pms.transaction.service.ProjectInnovationsService;
import in.pms.transaction.service.ProjectOthersService;
import in.pms.transaction.service.ProjectPublicationDetailsService;
import in.pms.transaction.service.TalksService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProgressReportController {

	@Autowired
	ProgressReportService progressReportService;
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
	DocumentFormatService documentFormatService;
	@Autowired
	SeminarEventService seminarEventService;
	@Autowired
	AwardWonService awardWonService;
	@Autowired
	MediaService mediaService;
	@Autowired
	OthersService othersService;
	@Autowired
	CopyrightService copyrightService;
	@Autowired 
	AppreciationLetterService AppreciationLetterService;
	@Autowired 
	ApprovedJobService approvedJobService;
	@Autowired
	TalksService talksService;
	@Autowired
	PatentDetailsService patentDetailsService;
	@Autowired
	ProjectPublicationDetailsService projectPublicationDetailsService;
	@Autowired
	ProductsDevelopedService productsDevelopedService;
	@Autowired
	AdditionalQualificationService additionalQualificationService;
	@Autowired
	ProductServiceDetailsService productServiceDetailsService;
	@Autowired
	EncryptionService encryptionService;
	@Autowired
	MonthlyProgressService monthlyProgressService;
	@Autowired 
	ProjectInnovationsService projectInnovationsService;
	@Autowired 
	ProjectOthersService projectOthersService;
	@Autowired
	AppreciationLetterService appreciationLetterService;
	@Autowired
	MouCollaborationService mouCollaborationService;
	@Autowired 
	ProjectHighlightsService highlightService;
	@Autowired 
	WorkflowService workFlowService;
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	TransactionActivityService transactionActivityService;
	
	
	
	@RequestMapping(value="progressReport",method=RequestMethod.GET)
	public String progressReportMaster(HttpServletRequest request,CategoryMasterModel categoryMasterModel){
		
		
		List<CategoryMasterModel> categoryMasterList=new ArrayList<CategoryMasterModel>();
		List<ProjectMasterModel> projectsList=new ArrayList<ProjectMasterModel>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String roleCheck="";
		if(userInfo.getSelectedEmployeeRole()!=null && (userInfo.getSelectedEmployeeRole().getNumRoleId()==3 || userInfo.getSelectedEmployeeRole().getNumRoleId()==5))
		{
			if(userInfo.getSelectedEmployeeRole().getNumRoleId()==3)
			{
				categoryMasterList=progressReportService.getAllCategoryList("P");
				
			}else if(userInfo.getSelectedEmployeeRole().getNumRoleId()==5)
			{
				categoryMasterList=progressReportService.getAllCategoryList("G");
				
			}
			projectsList =projectMasterService.getAllActiveProjectMasterData();
			
		
		}		
		
	    
		request.setAttribute("categoryList", categoryMasterList);
		request.setAttribute("projectsList", projectsList);
		request.setAttribute("roleCheck", roleCheck);
		return "progressReport";			
	
	}
	
	
	@RequestMapping(value="/getSubCategory",method=RequestMethod.POST)
	public @ResponseBody List<CategoryMasterModel> progressReportMaster(CategoryMasterModel categoryMasterModel,HttpServletRequest request){
		Long categoryId=null;
		
		if(categoryMasterModel.getNumCategoryId()!=0){
			categoryId=categoryMasterModel.getNumCategoryId();	
		}
		else{
			categoryId=Long.parseLong(encryptionService.dcrypt(categoryMasterModel.getEncCategoryId()));
			
		}
		List<CategoryMasterModel> categoryMasterList=progressReportService.getSubCategoryList(categoryId);
		
		return categoryMasterList;			
	
	}
	@RequestMapping(value="deploymentTotDetails",method=RequestMethod.GET)
	public String deploymentTotDetails(HttpServletRequest request,DeploymentTotDetailsModel deploymentTotDetailsModel){
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		String encCategoryId=deploymentTotDetailsModel.getEncCategoryId();
		int monthlyProgressId=0;
		long numCategoryId=0l;
		if(null != encMonthlyProgressId){
			monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			//Load Group, Project Id based on encMonthlyProgressId
			numCategoryId=Long.parseLong(encryptionService.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("encCategoryId", deploymentTotDetailsModel.getEncCategoryId());
			
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		List<States> listState = new ArrayList<States>();		
		
		List<DeploymentTotDetailsModel> deploymentTotDetailsList=progressReportService.getDeploymentDetails(monthlyProgressId,numCategoryId);
		listState = progressReportService.getStateList();
		//System.out.println("after");
		request.setAttribute("StateList", listState);
		request.setAttribute("deploymentList", deploymentTotDetailsList);
		return "deploymentTotDetails";
	}
	@RequestMapping(value="saveUpdateDeploymentDetails",method=RequestMethod.POST)
	public String saveUpdateDeploymentDetails(HttpServletRequest request,DeploymentTotDetailsModel deploymentTotDetailsModel,RedirectAttributes redirectAttributes){
	
		boolean flag=progressReportService.saveUpdateDeploymentDetails(request,deploymentTotDetailsModel);
		if(flag && deploymentTotDetailsModel.getNumDeploymentId()!=0)
		{
			redirectAttributes.addFlashAttribute("message",  "Deployment details updated Successfully");
			redirectAttributes.addFlashAttribute("status",  "success");
		
		}else if(flag && deploymentTotDetailsModel.getNumDeploymentId()==0)
		{
			redirectAttributes.addFlashAttribute("message",  "Deployment details saved Successfully");
			redirectAttributes.addFlashAttribute("status",  "success");
		}
		else
		{
			redirectAttributes.addFlashAttribute("message",  "There is some problem in data save");	
			redirectAttributes.addFlashAttribute("status",  "error");
		}
		/*redirectAttributes.addFlashAttribute("encCategoryId",deploymentTotDetailsModel.getEncCategoryId());
		redirectAttributes.addFlashAttribute("encMonthlyProgressId",deploymentTotDetailsModel.getEncMonthlyProgressId());
		*/
		return "redirect:/deploymentTotDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
	}
	
	@RequestMapping(value="/deleteDeployementDetails",method=RequestMethod.POST)
	public String deleteDeployementDetails(DeploymentTotDetailsModel deploymentTotDetailsModel, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes)
		{
			 
		if(deploymentTotDetailsModel.getNumDeploymentIds()==null || deploymentTotDetailsModel.getNumDeploymentIds().length==0){

			
			redirectAttributes.addFlashAttribute("message",  "There is some problem in delete");
			redirectAttributes.addFlashAttribute("status",  "error");
			return "redirect:/deploymentTotDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
		}
		else{
			    	try
			    	{ 
					
			    		progressReportService.deleteDeployementDetails(deploymentTotDetailsModel);
			    		redirectAttributes.addFlashAttribute("message",  "Deployment details deleted Successfully");	
			    		redirectAttributes.addFlashAttribute("status",  "success");
			    		
	                    
	                    
			    	}
			    	catch(Exception e)
			    	{
		          	System.out.println(e);

			    	}
			    	return "redirect:/deploymentTotDetails?encMonthlyProgressId="+deploymentTotDetailsModel.getEncMonthlyProgressId()+"&encCategoryId="+deploymentTotDetailsModel.getEncCategoryId();
		}
			    }
	
	@RequestMapping(value = "/uploadProgressRportImages", method = RequestMethod.POST)
	public final  @ResponseBody long uploadFile(MultipartHttpServletRequest request,@ModelAttribute DeploymentTotDetailsModel deploymentTotDetailsModel) {
		try{		
		if(!deploymentTotDetailsModel.getProgressReportQualityImages().getOriginalFilename().equals("") && null !=deploymentTotDetailsModel.getProgressReportQualityImages().getOriginalFilename()){
				
				String filetype = deploymentTotDetailsModel.getProgressReportQualityImages().getContentType();					
				String strFormat = "6";
				int documentFormatId = Integer.parseInt(strFormat);
			    DocumentFormatMaster documentFormatMaster = documentFormatService.getDocumentFormatById(documentFormatId);
			     String[] allowedMimeTypes = documentFormatMaster.getMimeTypes().split(",");
			     boolean mimeMatched= false;
			     for(int j=0;j<allowedMimeTypes.length;j++){
			    	if(allowedMimeTypes[j].equalsIgnoreCase(filetype)){
			    		mimeMatched=true;
			    		break;
			    	}
			     }
			     if(!mimeMatched){
					return -12; 
			     }else{
			    	long uploadResponse =  progressReportService.uploadProgressRportImages(deploymentTotDetailsModel);
			    	if(uploadResponse !=0 ){
			    		return uploadResponse;
			    	}else{
			    		return -2;
			    	}
			     }
			
		}else{
			return -10;
		}
		}catch(Exception e){
			e.printStackTrace();
			return -11;
		}
		
	}
	@RequestMapping(value = "/getUplodedImages", method = RequestMethod.POST)
	public @ResponseBody List<DeploymentTotDetailsModel> getUplodedImages(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		
		List<DeploymentTotDetailsModel> deploymentTotDetailsList=progressReportService.getUplodedImages(deploymentTotDetailsModel.getStrDocumentIds());
		return deploymentTotDetailsList;
		
		
	}
	
	
	@RequestMapping(value="downloadDeploymentImages",method = RequestMethod.POST)	
	public void downloadDeploymentImages(DeploymentTotDetailsModel deploymentTotDetailsModel,HttpServletRequest request,HttpServletResponse response){				
		progressReportService.downloadDeploymentImages(deploymentTotDetailsModel,response);
	}

	@RequestMapping(value = "/deleteImageDetails", method = RequestMethod.POST)
	public @ResponseBody boolean deleteImageDetails(DeploymentTotDetailsModel deploymentTotDetailsModel) {
		
		boolean flag=progressReportService.deleteImageDetails(deploymentTotDetailsModel);
		return flag;
		
		
	}
	@RequestMapping(value = "/getPrevNextBtnController", method = RequestMethod.POST)
	public @ResponseBody ShowNextPrevBtnModel getPrevNextBtnController(@RequestParam("encCategoryId") String encCategoryId,@RequestParam("encMonthlyProgressId") String encMonthlyProgressId,HttpServletRequest request) {
		ShowNextPrevBtnModel showNextPrevBtnModel=new ShowNextPrevBtnModel();
		showNextPrevBtnModel=progressReportService.getPrevNextBtnController(encCategoryId,encMonthlyProgressId);
		return showNextPrevBtnModel;
	}
	
	
	
	@RequestMapping(value = "/getPreviewDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String getPreviewDetails(HttpServletRequest request,MonthlyProgressModel monthlyProgressModel,@RequestParam("encMonthlyProgressId") String encMonthlyProgressId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		monthlyProgressService.writeProgressReportAuthorityCheck();
		if(null !=encMonthlyProgressId && !encMonthlyProgressId.equals("")){
			int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
			String encPageId = encryptionService.encrypt("2");
			String encWorkflowId=encryptionService.encrypt("1");
			List<MonthlyProgressModel> progressDetails = monthlyProgressService.getMonthlyProgressDetailsByPId(monthlyProgressId);
			MonthlyProgressModel monthlyModel= monthlyProgressService.getById(monthlyProgressId);
			//System.out.println(monthlyModel.getStrMonth());
			request.setAttribute("project","project");
			/*if(monthlyProgressModel.getPreviewFlag()==1){*/   // remove the condition to update monthly progress [21-05-2024] --*/
				request.setAttribute("diffView", 1);
			/*}*/
			
			List<ProceedingModel> proceedingList=transactionActivityService.getProceedings(monthlyProgressId);
			request.setAttribute("transactionList", proceedingList);
			request.setAttribute("monthlyModel", monthlyModel);
			request.setAttribute("progressDetails", progressDetails);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("encPageId", encPageId);
			request.setAttribute("encWorkflowId", encWorkflowId);
			List<CategoryMasterModel> categoryMasterList=new ArrayList<CategoryMasterModel>();
			categoryMasterList=progressReportService.getAllCategoryList("P");
			request.setAttribute("categoryList", categoryMasterList);
			WorkflowModel workflowModel=workFlowService.getTransactionDetails(monthlyProgressId);
			
			if((userInfo.getSelectedEmployeeRole().getNumRoleId()==3 ||userInfo.getSelectedEmployeeRole().getNumRoleId()==5)&&(workflowModel==null) ){
				request.setAttribute("allowEdit", 1);	
			}
			else if(workflowModel!=null){
				long actionId=workflowModel.getNumLastActionId();
				long roleId=workflowModel.getNumLastRoleId();
				long workflowId=workflowModel.getNumWorkflowId();
				boolean allowed=workFlowService.allowPreviewEdit(workflowId, roleId, actionId);
				if(allowed){
					request.setAttribute("allowEdit", 1);
				}
			}
			
		}
		
		return "progressPreview"; 
		
	}
	
	@RequestMapping(value = "/getPreviewData", method = RequestMethod.POST)
	public @ResponseBody List<Object[]> getPreviewData(HttpServletRequest request,MonthlyProgressModel monthlyProgressModel) {
		long progressDetailsId = Long.parseLong(encryptionService.dcrypt(monthlyProgressModel.getEncProgressDetailsId()));
		int categoryId = Integer.parseInt(encryptionService.dcrypt(monthlyProgressModel.getEncCategoryId()));
		
		List<Object[]> dataList = null;
		
		switch(categoryId) 
        { 
        case 1: 
    		dataList= highlightService.getPreviewDataWithHeader(progressDetailsId);
        break;
        case 2: 
    		dataList= productsDevelopedService.getPreviewDataWithHeader(progressDetailsId);
        break;
         case 3: 
		dataList= progressReportService.getPreviewDataWithHeaderForTOT(progressDetailsId);
    break;
        case 4: 
    		dataList= seminarEventService.getPreviewDataWithHeader(progressDetailsId);
        break;
        case 5: 
        		dataList= patentDetailsService.getPreviewDataWithHeader(progressDetailsId);
        break;
        case 6: 
            dataList= projectPublicationDetailsService.getPreviewDataWithHeader(progressDetailsId);
        break;
        case 7: 
    		dataList=copyrightService.getPreviewDataWithHeader(progressDetailsId);
        break;   
        case 8: 
        	dataList=  projectInnovationsService.getPreviewDataWithHeader(progressDetailsId); 
        break;
        case 10: 
           dataList=  awardWonService.getPreviewDataWithHeader(progressDetailsId); 
         break;
        case 11: 
            dataList=  appreciationLetterService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 12: 
            dataList=  mediaService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 13: 
            dataList=  othersService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 14: 
            dataList=  talksService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 15: 
            dataList=  mouCollaborationService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 16: 
            dataList=  productServiceDetailsService.getPreviewDataWithHeader(progressDetailsId); 
          break;
       case 17: 
            dataList=  additionalQualificationService.getPreviewDataWithHeader(progressDetailsId); 
          break;
        case 18: 
               dataList=  projectOthersService.getPreviewDataWithHeader(progressDetailsId); 
                    break;
             
        }				
		return dataList;		
	}
	@RequestMapping(value="ViewTotDocs",method=RequestMethod.GET)
	public String viewTotDocs(HttpServletRequest request,DeploymentTotDetailsModel deploymentTotDetailsModel){
	
		String encMonthlyProgressId = deploymentTotDetailsModel.getEncMonthlyProgressId();
		int monthlyProgressId=0;
		Long numCategoryId=deploymentTotDetailsModel.getNumCategoryId();
		String encCategoryId=encryptionService.encrypt(numCategoryId.toString());
		//System.out.println(encMonthlyProgressId);
		monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		request.setAttribute("encMonthlyProgressId", deploymentTotDetailsModel.getEncMonthlyProgressId());
		request.setAttribute("numCategoryId", deploymentTotDetailsModel.getNumCategoryId());
		request.setAttribute("numId", deploymentTotDetailsModel.getNumDeploymentId());
		request.setAttribute("encCategoryId", encCategoryId);
		
		List<Long> docList=new ArrayList<Long>();
		if(deploymentTotDetailsModel.getNumCategoryId()==3){	
		List<DeploymentTotDetailsModel> deploymentTotDetailsList=progressReportService.getDeploymentDetails(monthlyProgressId,numCategoryId);
		for(int k=0;k<deploymentTotDetailsList.size();k++){
			DeploymentTotDetailsModel model=deploymentTotDetailsList.get(k);
			if(deploymentTotDetailsModel.getNumDeploymentId()==model.getNumDeploymentId()){
		
			String docId=model.getStrDocumentIds();
			//System.out.println(docId);
			if(docId!=null){
				String[] id=docId.split(",");
				for(int i=0;i<id.length;i++){
				Long val=Long.valueOf(id[i]);
				//System.out.println(val);
				docList.add(val);
				}
			}
			request.setAttribute("docIds", docId);
		}
	}
		}
		else{
			List<ProductServiceDetailsModel> productServiceDetailsList=productServiceDetailsService.getProductServiceDetails(monthlyProgressId,numCategoryId);
			for(int k=0;k<productServiceDetailsList.size();k++){
				ProductServiceDetailsModel model=productServiceDetailsList.get(k);
				if(deploymentTotDetailsModel.getNumDeploymentId()==model.getNumProductServiceDetailsId()){
			
				String docId=model.getStrDocumentIds();
				//System.out.println(docId);
				if(docId!=null){
					String[] id=docId.split(",");
					for(int i=0;i<id.length;i++){
					Long val=Long.valueOf(id[i]);
					//System.out.println(val);
					docList.add(val);
					}
				}
				request.setAttribute("docIds", docId);
			}
		}
			
		}
			
		return "DocumentImages";
	}


	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public void viewImage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	String encMonthlyProgressId =request.getParameter("encMonthlyProgressId");
	Long numCategoryId =Long.parseLong(request.getParameter("numCategoryId"));
	int numId = Integer.parseInt(request.getParameter("numId"));
	Long docid=Long.parseLong(request.getParameter("docId"));
	
	System.out.println("controler"+docid);
	try
	{
		DeploymentTotDetailsModel deploymentTotDetailsModel=new DeploymentTotDetailsModel();
		deploymentTotDetailsModel.setEncMonthlyProgressId(encMonthlyProgressId);
		deploymentTotDetailsModel.setEncCategoryId(encryptionService.encrypt(numCategoryId.toString()));
		deploymentTotDetailsModel.setNumDocumentId(docid);
		HttpSession session = request.getSession(false);
		if (session != null) {    
    	  ServletOutputStream out = response.getOutputStream();
    	  boolean success= progressReportService.downloadDeploymentImages(deploymentTotDetailsModel,response);
    	  
    	  response.setContentType("image/jpeg");
				if (success) 
				{
					System.out.println("File has been downloaded successfully.");
				}
				else{
					System.out.println("Some error occured while downloading file");		
				}			

      }
 
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	@RequestMapping(value = "/getGroupPreviewDetails", method = RequestMethod.POST)
	public String getGroupPreviewDetails(HttpServletRequest request,MonthlyProgressModel monthlyProgressModel,@RequestParam("encMonthlyProgressId") String encMonthlyProgressId) {
		int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
		List<MonthlyProgressModel> progressDetails = monthlyProgressService.getMonthlyProgressDetailsByPId(monthlyProgressId);
		MonthlyProgressModel monthlyModel= monthlyProgressService.getById(monthlyProgressId);
		String encPageId = encryptionService.encrypt("2");
		String encWorkflowId=encryptionService.encrypt("2");
		request.setAttribute("monthlyModel", monthlyModel);
		request.setAttribute("progressDetails", progressDetails);
		request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
		request.setAttribute("encPageId", encPageId);
		request.setAttribute("encWorkflowId", encWorkflowId);
		request.setAttribute("group", "group");
		List<ProceedingModel> proceedingList=transactionActivityService.getProceedings(monthlyProgressId);
		request.setAttribute("transactionList", proceedingList);
		return "groupProgressPreview"; 
		
	}
	
	@RequestMapping(value="/projectProceedings",method = RequestMethod.POST)
	public String projectProceedings(ProceedingModel proceedingModel,@RequestParam("encMonthlyProgressId") String encMonthlyProgressId,@RequestParam("Type") String Type,HttpServletRequest request){	
		String monthlyProgressID= encryptionService.dcrypt(encMonthlyProgressId);
		int monProgID= Integer.parseInt(monthlyProgressID);
		if(monProgID==0){
			return "redirect:/GroupMonthlyReport";
		}
		List<ProceedingModel> proceedingList=transactionActivityService.getProceedings(monProgID);
		request.setAttribute("data", proceedingList);	
		MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monProgID);
		request.setAttribute("monthlyProgressModel", monthlyProgressModel);
		
		request.setAttribute("Type", Type);
	
		return "proceedingDetails";
	}
	
}
