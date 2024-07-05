package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.DocumentFormatService;
import in.pms.master.service.DocumentTypeMasterService;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProposalDocumentMasterService;
import in.pms.transaction.service.ApplicationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mst")
public class ProjectDocumentMasterController {
	
	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	DocumentFormatService documentFormatService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired 
	EncryptionService encryptionService;
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	
	@Autowired
	ApplicationService applicationService;
	
	
	
	@Autowired
	@Qualifier("projectDocumentValidator")
	Validator validator;

	@RequestMapping("/uploadProjectDocument")
	public String uploadProjectDocument(HttpServletRequest request, ProjectDocumentMasterModel projectDocumentMasterModel,Model map){	
		boolean isEmptyLayout = false;
		Map md = map.asMap();
		int projectId=0;
		
		
		if(null != request.getParameter("encProjectId")){			
			try{
				String encProjectId = request.getParameter("encProjectId");
				String strProjectId = encryptionService.dcrypt(encProjectId);
				projectId = Integer.parseInt(strProjectId);
				request.setAttribute("showWorkflow", "true");
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		String referrer = request.getHeader("referer");
		if(null != referrer && referrer.contains("projectDetails")){
			request.setAttribute("referrer", referrer);
			isEmptyLayout = true;
			request.setAttribute("showWorkflow", "false");
		}else{
			request.setAttribute("showWorkflow", "true");		
		}
		
		
		
		List<ProjectMasterModel> projectList =new ArrayList<ProjectMasterModel>();
	      if(md.get("projectId")!=null){
	    	  projectId=Integer.parseInt(md.get("projectId").toString());	    	  
	      }
	      
	      if(projectId != 0){
	    	  ProjectMasterModel projectMasterModel=projectMasterService.getProjectMasterModelById(projectId);	    	 
	    	  request.setAttribute("projectMasterModel", projectMasterModel);
	    	    // GET ALL DOCUMENT BY ID 
		      List<ProjectDocumentMasterModel> alldocuments = projectDocumentMasterService.getProjectDocumentByID(projectId);
		      request.setAttribute("alldocuments", alldocuments);
		      //
	      }else{
	    	  projectList = projectMasterService.getAllActiveProjectMasterData();
	      }
			/*List<DocumentTypeMasterModel> documentTypeList =documentTypeMasterService.getAllActiveDocumentTypeMasterDomain();
			request.setAttribute("documentTypeList", documentTypeList);*/
	      	Map<String, List<DocumentTypeMasterModel>> documentTypeList =documentTypeMasterService.retrieveDocumentTypeByClassification();
			request.setAttribute("documentTypeList", documentTypeList);
			request.setAttribute("projectList", projectList);	
			request.setAttribute("projectId", projectId);
			request.setAttribute("encProjectId", encryptionService.encrypt(""+projectId));
			
			if(isEmptyLayout){
				return "uploadProjectDocumentEmpty";
			}
		return "uploadProjectDocument";
	}
	
	@RequestMapping(value="/saveProjectDocument",method = RequestMethod.POST)	
	public String saveProjectDocument(HttpServletRequest request, @ModelAttribute ProjectDocumentMasterModel projectDocumentMasterModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){	
		
		validator.validate(projectDocumentMasterModel, bindingResult);
		
	      if (bindingResult.hasErrors()) {
	    	List<DocumentTypeMasterModel> documentTypeList =documentTypeMasterService.getAllActiveDocumentTypeMasterDomain();
	  		request.setAttribute("documentTypeList", documentTypeList);
	  		List<ProjectMasterModel> projectList = projectMasterService.getAllActiveProjectMasterData();
	  		request.setAttribute("projectList", projectList);	
	  		return "uploadProjectDocument";
	      }
		
	  		request.setAttribute("projectId", projectDocumentMasterModel.getProjectId());	
	  		redirectAttributes.addFlashAttribute("projectId",projectDocumentMasterModel.getProjectId());

		boolean result = projectDocumentMasterService.uploadProjectDocument(projectDocumentMasterModel);
		if(result){
			redirectAttributes.addFlashAttribute("message",  "Document Details saved successfully");	
			redirectAttributes.addFlashAttribute("status", "success");
		}else{
			redirectAttributes.addFlashAttribute("message",  "Something went wrong");	
			redirectAttributes.addFlashAttribute("status", "error");
		}
			
		return "redirect:/mst/uploadProjectDocument";
	}
	
	/*Controller to upload Reco Sheet by devesh on 28-12-23*/
	@RequestMapping(value="/saveFinanceProjectDocument",method = RequestMethod.POST)	
	@ResponseBody
	public boolean saveFinanceProjectDocument(HttpServletRequest request, @ModelAttribute ProjectDocumentMasterModel projectDocumentMasterModel,BindingResult bindingResult,RedirectAttributes redirectAttributes){	
		
		int projectId=0;
		String strProjectId = encryptionService.dcrypt(projectDocumentMasterModel.getEncProjectId());
		projectId = Integer.parseInt(strProjectId);
		projectDocumentMasterModel.setProjectId(projectId);
		projectDocumentMasterModel.setDocumentTypeId(56);
		projectDocumentMasterModel.setDocumentDate(DateUtils.dateToString(new Date()));
		System.out.println(projectDocumentMasterModel);
		/*validator.validate(projectDocumentMasterModel, bindingResult);
		
	      if (bindingResult.hasErrors()) {
	    	  return false;
	      }*/

		return projectDocumentMasterService.uploadProjectDocument(projectDocumentMasterModel);

	}
	/*End of Function*/	
	
	@RequestMapping(value="uploadedProjectDocument",method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectDocumentMasterModel> uploadedProjectDocument(HttpServletRequest request,ProjectDocumentMasterModel projectDocumentMasterModel){				
		return projectDocumentMasterService.uploadedProjectDocument(projectDocumentMasterModel);
	}
	
	@RequestMapping(value="downloadProjectFile",method = RequestMethod.POST)	
	public void downloadProjectFile(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		fileUploadService.downloadProjectFile(model.getEncNumId(),response);
	}
	
	/*Added by devesh on 29-12-23 to download Reco Sheet from project Id*/
	@RequestMapping(value="downloadRecoSheetFile",method = RequestMethod.POST)	
	public void downloadRecoSheetFile(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		fileUploadService.downloadRecoSheetFile(model.getEncProjectId(),response);
	}
	/*End of Function*/
	
	/*Added by devesh on 29-12-23 to validate Reco Sheet from project Id*/
	@RequestMapping(value="validateRecoSheetFile",method = RequestMethod.POST)	
	@ResponseBody
	public boolean validateRecoSheetFile(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){
		String strProjectId = encryptionService.dcrypt(model.getEncProjectId());
		long projectId = Long.parseLong(strProjectId);
		ProjectDocumentMasterDomain projectMasterDetail =projectDocumentMasterService.getProjectDocumentIdFromProjectId(projectId);
		/*String documentId = encryptionService.dcrypt(encDocumentId);*/
		/*System.out.println("\n projectMasterDetail.."+projectMasterDetail);*/
		
		if (null != projectMasterDetail) {
				return true;
			}
		return false;
	}
	/*End of Function*/
	
	@RequestMapping(value="showProjectDocumentRevision/{encDocumentId}",method=RequestMethod.GET)
	public String  showProjectDocumentRevision(HttpServletRequest request ,@PathVariable("encDocumentId") String encDocumentId){	
		//System.out.println(encDocumentId);
		List<ProjectDocumentMasterModel> projectDocument = projectDocumentMasterService.showProjectDocumentRevision(encDocumentId);
		request.setAttribute("projectDocument", projectDocument);				
	return "showProjectDocumentRevision";
	}
	
	@RequestMapping(value="/documentDetails/{encId}")
	public String documentDetails(HttpServletRequest request,@PathVariable("encId") String encId){	
		//String encProjectId = encId;
		String referrer = request.getHeader("referer");
		request.setAttribute("encProjectId", encId);
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);
		Map<String,Map<String,List<ProjectDocumentMasterModel>>> dataMap = projectDocumentMasterService.documentDetailsCategoryWiseByProjectId(projectId);
		
		long proposalId =  applicationService.getProposalIdByProjectId(projectId);
		
		if(proposalId != 0){
			Map<String,Map<String,List<ProjectDocumentMasterModel>>> proposalDocuments = proposalDocumentMasterService.documentDetailsCategoryWiseByProposalId(proposalId);
			if(!proposalDocuments.isEmpty()){
				 for (Map.Entry<String,Map<String,List<ProjectDocumentMasterModel>>> categoryEntry : proposalDocuments.entrySet()){
			         String documentCategory = categoryEntry.getKey();
			         Map<String,List<ProjectDocumentMasterModel>> documentsMap = categoryEntry.getValue();
			         if(dataMap.containsKey(documentCategory)){
			        	 Map<String,List<ProjectDocumentMasterModel>> projectDocumentsMap = dataMap.get(documentCategory);
						 for (Map.Entry<String,List<ProjectDocumentMasterModel>> documentTypeEntry : documentsMap.entrySet()){
							 String documentType = documentTypeEntry.getKey();
							 if(projectDocumentsMap.containsKey(documentType)){
								 List<ProjectDocumentMasterModel> tempList =  projectDocumentsMap.get(documentType);
								 tempList.addAll(documentTypeEntry.getValue());
								 projectDocumentsMap.put(documentType, tempList);
								 dataMap.put(documentCategory, projectDocumentsMap);								 
							 }else{
								 projectDocumentsMap.put(documentType, documentTypeEntry.getValue()); 
								 dataMap.put(documentCategory, projectDocumentsMap);
							 }
						 }
			         }else{
			        	 dataMap.put(documentCategory, documentsMap); 
			         }
			    }
			}
		}
		
		request.setAttribute("categorywiseDocuments", dataMap);
		if(null != referrer){		
			if(referrer.contains("/projectDetails/")){
				request.setAttribute("showWrapper", 0);
				return "documentDetailWithoutHeader";
			}
		}
			
		return "documentDetail";
	}
	

	
	@RequestMapping(value="downloadProposalFile",method = RequestMethod.POST)	
	public void downloadProposalFile(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		fileUploadService.downloadProposalFile(model.getEncNumId(),response);
	}
	
	//Added by devesh on 27-09-23 to get file by rev ID
	@RequestMapping(value="downloadProposalFilebyRevId",method = RequestMethod.POST)	
	public void downloadProposalFilebyRevId(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		fileUploadService.downloadProposalFilebyRevId(model.getEncNumId(), model.getNumId(), response);
	}
	//End of retrieving Document by Rev Id
	
	@RequestMapping(value="deleteProjectDocument",method = RequestMethod.POST)	
	@ResponseBody
	public boolean deleteProjectDocument(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		return projectDocumentMasterService.deleteProposalDocument(model.getEncNumId());
	}
	
	@RequestMapping(value="deleteUploadedProposalDocument",method = RequestMethod.POST)	
	@ResponseBody
	public boolean deleteUploadedProposalDocument(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		return proposalDocumentMasterService.deleteProposalDocument(model.getEncNumId());
	}
	
	@RequestMapping(value = "/uploadProjectFile", method = RequestMethod.POST)
	public final  @ResponseBody String uploadFile(MultipartHttpServletRequest request,@ModelAttribute ProjectDocumentDetailsModel projectDocumentDetailsModel) {
		try{		
		if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
			 
				String filetype = projectDocumentDetailsModel.getProjectDocumentFile().getContentType();					
				String strFormat = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
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
					return "-12"; 
			     }else{
			    	boolean uploadResponse =  projectDocumentMasterService.uploadTempProjectDocument(projectDocumentDetailsModel);
			    	
			    	if(uploadResponse == true){
			    	
			    		return "1";
			    	}else{
			    		return "2";
			    	}
			     }
			
		}else{
			
			return "-10";
		}
		}catch(Exception e){
			
			e.printStackTrace();
			return "-11";
		}
		
	}
	
	
	@RequestMapping(value="projectDate",method = RequestMethod.POST)
	@ResponseBody
	public ProjectMasterModel getProjectDate(HttpServletRequest request,ProjectMasterModel masterModel){
		
		ProjectMasterModel projectMasterModel=projectMasterService.getProjectMasterModelById(masterModel.getNumProjectId());	
		return projectMasterModel;
	}

	@RequestMapping(value="downloadTempProjectFile",method = RequestMethod.POST)	
	public void downloadTempProjectFile(ProjectDocumentDetailsModel model,HttpServletRequest request,HttpServletResponse response){				
		System.out.println("here to download the project closure request");
		fileUploadService.downloadTempProjectFile(model.getEncNumId(),response);
	}
	
// Controller for Validate the project file type
	@RequestMapping(value = "/checkUploadProjectFile", method = RequestMethod.POST)
	public final  @ResponseBody String checkUploadProjectFile(MultipartHttpServletRequest request,@ModelAttribute ProjectDocumentDetailsModel projectDocumentDetailsModel) {
		try{		
		if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
			 
				String filetype = projectDocumentDetailsModel.getProjectDocumentFile().getContentType();					
				String strFormat = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
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
					return "-12"; 
			     }else{
			    	 return "1";
			     }
		}else{
			
			return "-10";
		}
		}catch(Exception e){
			
			e.printStackTrace();
			return "-11";
		}
	}
// End For Controller
	
	@RequestMapping(value = "/updateUploadProjectFile", method = RequestMethod.POST)
	public final  @ResponseBody String updateUploadProjectFile(MultipartHttpServletRequest request,@ModelAttribute ProjectDocumentDetailsModel projectDocumentDetailsModel) {
		try{		
		if(!projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename().equals("") && null !=projectDocumentDetailsModel.getProjectDocumentFile().getOriginalFilename()){
			 
				String filetype = projectDocumentDetailsModel.getProjectDocumentFile().getContentType();					
				String strFormat = encryptionService.dcrypt(projectDocumentDetailsModel.getEncDocumentFormatId());
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
					return "-12"; 
			     }else{
			    	boolean uploadResponse =  projectDocumentMasterService.updateUploadTempProjectDocument(projectDocumentDetailsModel);
			    	
			    	if(uploadResponse == true){
			    	
			    		return "1";
			    	}else{
			    		return "2";
			    	}
			     }
			
		}else{
			
			return "-10";
		}
		}catch(Exception e){
			
			e.printStackTrace();
			return "-11";
		}
		
	}
	
	@RequestMapping(value = "/isFileUploaded", method = RequestMethod.POST)
	public final  @ResponseBody String isFileUploaded(ProjectDocumentDetailsModel projectDocumentDetailsModel,HttpServletRequest request,HttpServletResponse response) {
		String flag="";
		try{		
					
			
				String strDocTypeId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncStrId());
				int docTypeId = Integer.parseInt(strDocTypeId);
				
				String strProjectId = encryptionService.dcrypt(projectDocumentDetailsModel.getEncProjectId());
				long projId = Long.parseLong(strProjectId);
				
				List<TempProjectDocumentMasterDomain> details = projectDocumentMasterService.getTempProjectDocumentMasterDetails(projId,docTypeId);
			  
				int size=details.size();
				if(size>0){
				  return "uploaded";
			  }
				else{
					return "notUploaded";
				}
			
	
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		return flag;
	}
	
}
