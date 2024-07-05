package in.pms.master.controller;

import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.model.FileMeta;
import in.pms.global.service.FileUploadService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.NewsLetterDao;
import in.pms.master.domain.NewsLetterFilterMaster;
import in.pms.master.domain.NewsLetterMaster;
import in.pms.master.domain.NewsletterDocuments;
import in.pms.master.model.NewsLetterFilterForm;
import in.pms.master.model.RoleMasterModel;
import in.pms.master.service.DocumentService;
import in.pms.master.service.GlobalService;
/*import in.cdacnoida.serb.gl.model.UserInfo;
import in.cdacnoida.serb.gl.service.GlobalService;*/
import in.pms.master.service.NewsLetterService;
import in.pms.master.service.RoleMasterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Controller
public class NewsLetterController {

	@Autowired
	public NewsLetterService newsLetterService;
	
	@Autowired
	public GlobalService globalService;
	
	@Autowired
	public RoleMasterService roleMasterService;
	
	@Autowired
	public NewsLetterDao newsletterDao;
	
	@Autowired
	public FileUploadService fileUploadService;
	
	@Autowired
	public DocumentService documentService;
	
	/*
	
	 
	 
   
	
	
	
	
	*/
	
	@RequestMapping(value="/CreateNewsLetterFilter",method=RequestMethod.GET)
	public String newsLetterFilter(@ModelAttribute("NewsLetterFilterForm")NewsLetterFilterForm newsLetterForm,HttpServletRequest request){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
		List<NewsLetterFilterForm> lnlfm = new ArrayList<NewsLetterFilterForm>();
		lnlfm = newsLetterService.getAllSavedFilters();
		request.setAttribute("FilterList", lnlfm);
		return "NewsLetterFilter";
	}
	
	@RequestMapping(value="/saveNewsLetterFilter",method=RequestMethod.POST)
	public String saveNewsLetterFilter(@ModelAttribute("NewsLetterFilterForm")NewsLetterFilterForm newsLetterForm,BindingResult Result,HttpServletRequest request){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
		
		newsLetterForm.setNumUserId(usrInfo.getEmployeeId());
		newsLetterService.saveFilter(newsLetterForm);
		request.setAttribute("Message","Filter Saved Successfully");
		List<NewsLetterFilterForm> lnlfm = new ArrayList<NewsLetterFilterForm>();
		lnlfm = newsLetterService.getAllSavedFilters();
		request.setAttribute("FilterList", lnlfm);
		return "NewsLetterFilter";
	}
	
	@RequestMapping(value="/DeleteNewsLetterFilter",method=RequestMethod.POST)
	public String deleteFilter(@ModelAttribute("NewsLetterFilterForm")NewsLetterFilterForm newsLetterForm,HttpServletRequest request){
		
		List<NewsLetterFilterForm> lnlfm = new ArrayList<NewsLetterFilterForm>();
		if(newsLetterForm.getArrCheckbox()==null || newsLetterForm.getArrCheckbox().length==0){
			lnlfm = newsLetterService.getAllSavedFilters();
			request.setAttribute("FilterList", lnlfm);
	        request.setAttribute("Message","Please select at least one city to delete.");
		}
		else{
		newsLetterService.deleteFilter(newsLetterForm);
		lnlfm = newsLetterService.getAllSavedFilters();
		request.setAttribute("FilterList", lnlfm);
		request.setAttribute("Message","Filter deleted successfully!");
		}
		return "NewsLetterFilter";
	}
	
	@RequestMapping(value="/CreateNewsLetter",method=RequestMethod.GET)
	public String SEDetails(HttpServletRequest request){
	
		
		List<NewsLetterFilterForm> lnlfm = new ArrayList<NewsLetterFilterForm>();
		List<NewsLetterMaster> lnlm = new ArrayList<NewsLetterMaster>();				
		lnlfm = newsLetterService.getAllFilters();
		request.setAttribute("FilterList", lnlfm);
		List<RoleMasterModel> roleList = roleMasterService.getAllRoleMaster();
		request.setAttribute("roleList", roleList);	
		lnlm=newsLetterService.getAllSavedNewsLetter();
		request.setAttribute("NewsLettersList", lnlm);
		return "NewsLetterJSP";
	}
	
	@RequestMapping(value = "/SendNewsLetter", method = RequestMethod.POST)
	public String sendNewsLetter(NewsLetterFilterForm newsLetterFilterForm,HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
		
		newsLetterFilterForm.setNumUserId(usrInfo.getEmployeeId());
		
		try{
			if((newsLetterFilterForm.getScheduledDate()==null || newsLetterFilterForm.getScheduledDate().equals(""))
			&& (newsLetterFilterForm.getPeriodicNewsletterDate()==null || newsLetterFilterForm.getPeriodicNewsletterDate().equals(""))){
			
			newsLetterService.sendAllNewsLetterNow(newsLetterFilterForm);
			}
			else
			{
			
			newsLetterService.saveNewsLetters(newsLetterFilterForm);
			}
			}
		catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/CreateNewsLetter";
	}
	
	@RequestMapping(value="/DeleteNewsLetter",method=RequestMethod.POST)
	public @ResponseBody void deleteNewsLetter(HttpServletRequest request){
		
		String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "";

		 String[] Ids=null;
		if(!param.isEmpty())
		{
			
			  if(param.length()>0){
					
					Ids = param.split(",");	
					
					}

		}
		
		newsLetterService.deleteNewsLetter(Ids);
	
	}
	
	@RequestMapping(value="/GetNewsLetterFilter",method = RequestMethod.POST)
	public @ResponseBody List<Integer> GetNewsLetterFilter(HttpServletRequest request){
	 
	List<Integer> filterList=new ArrayList<Integer>();
	String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "0";


	if(Integer.parseInt(param)!=0)
	{
		 filterList = newsLetterService.getNewsLetterFilterMappingDetails(Integer.parseInt(param));
		
	}
	return filterList;
	
	}
	
	 @RequestMapping(value="/GetPeriodicScheduledDate",method = RequestMethod.POST)
		public @ResponseBody String getPeriodicScheduledDisplayDate(HttpServletRequest request){
		
			String scheduledDate="";

		String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "0";
	

		if(Integer.parseInt(param)!=0)
		{
			  scheduledDate = newsLetterService.getPeriodicNewsLetterDisplayDate(Integer.parseInt(param));
						
		}
		return scheduledDate;
		}
	 

	 @RequestMapping(value="/GetScheduledDisplayDate",method = RequestMethod.POST)
		public @ResponseBody String getScheduledDisplayDate(HttpServletRequest request){
		
			String scheduledDate="";

		String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "0";


		if(Integer.parseInt(param)!=0)
		{
			  scheduledDate = newsLetterService.getNewsLetterDisplayDate(Integer.parseInt(param));
						
		}
		return scheduledDate;
		
	 }
	 
	 @RequestMapping(value="/GetDocumentDetails",method = RequestMethod.POST)
		public @ResponseBody HashMap<Integer,String> getDocumentDetails(HttpServletRequest request){

		 
		 HashMap<Integer,String> documentMap=new LinkedHashMap<Integer, String>();
		String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "0";
		

		if(Integer.parseInt(param)!=0)
		{
			documentMap = newsLetterService.getDocumentMapForNewsletter(Integer.parseInt(param));
		}
		
		return documentMap;
		}
	 
	 @RequestMapping(value="/getRolesIds",method = RequestMethod.POST)
		public @ResponseBody NewsLetterFilterForm getRolesIds(HttpServletRequest request){
			String param =  request.getParameter("nlId") !=null && !request.getParameter("nlId").isEmpty() ? request.getParameter("nlId").trim() : "0";
			NewsLetterMaster newsletter = newsletterDao.getNewsLetterObjectFromDB(Integer.parseInt(param));
			int newsLetterId=0;
			if(newsletter!=null){
				 newsLetterId=newsletter.getNumNewsLetterId();
				 
			}
			NewsLetterFilterForm details= newsLetterService.getDetailsOfRole(newsLetterId);
			return details;
		}
	 
	 @RequestMapping(value = "/getNewsLetterDocument/{docId}", method = RequestMethod.GET)
	 public void getNewsLetterDocument(HttpServletResponse response,@PathVariable int docId){
	        
		
		 String defaultPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
		
		String Path = defaultPath;
		
		
		NewsletterDocuments od = newsLetterService.getNewsletterDocuments(docId);
		 String name = docId+ ".pdf";
		 String showName = od.getStrdocname();
		
		 try {			 	
			 	response.setContentType("application/"+ name.substring(name.lastIndexOf(".")+1,name.length()));
			 	response.setHeader("Content-disposition", "attachment; filename=\""+showName+"\"");
			 	
			 	boolean fileExist=fileUploadService.checkFileExist(Path+name);
			 	
			 	if(fileExist)
			 	{
			 		boolean success = fileUploadService.downloadFiles(Path+name, response.getOutputStream());
				 
			 		if (success) 
			 		{
			 			System.out.println("File has been downloaded successfully.");
			 		}
			 		else
			 			System.out.println("Some error occured while downloading file");
			 	}
			 	
		 }catch (IOException e) {
				e.printStackTrace();
		 }
	 }
	 
	 @RequestMapping(value="/uploadNewsletterDocument", method = RequestMethod.POST)
		public @ResponseBody LinkedList<FileMeta> uploadNewsLetterDocs(MultipartHttpServletRequest request, HttpServletResponse response) {
			List<Integer> returnList = new ArrayList<Integer>();
			returnList.clear();
			returnList.add(0);
			
			LinkedList<FileMeta> lpfm = new LinkedList<FileMeta>();
			
			String defaultPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
			
			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf = null;
			
			FileMeta proposalFileMeta = new FileMeta();
		 

			 while(itr.hasNext()){
				 mpf = request.getFile(itr.next()); 
				 proposalFileMeta = new FileMeta();

				 	NewsletterDocuments nd = new NewsletterDocuments();
				 	nd.setNumIsValid(1);
				 	nd.setDtTrDate(new Date());
				 	nd.setIsDraft(1);
				 	nd.setNumTrUserId(0);
				 	nd.setStrdocname(mpf.getOriginalFilename());
					
					int documentId = documentService.insertNewsLetterDocuments(nd);		
					
					//Meta Information for the file
					proposalFileMeta.setFileName(mpf.getOriginalFilename());
					proposalFileMeta.setFileSize(mpf.getSize()/1024+" Kb");
					proposalFileMeta.setFileType(mpf.getContentType());
					String fname= mpf.getOriginalFilename();
					
					//Check for PDF
					
					
					 if(proposalFileMeta.getFileType().equalsIgnoreCase("application/pdf") || proposalFileMeta.getFileType().equalsIgnoreCase("application/download")||proposalFileMeta.getFileType().equalsIgnoreCase("application/octet-stream")||proposalFileMeta.getFileType().equalsIgnoreCase("application/x-real")||proposalFileMeta.getFileType().equalsIgnoreCase("application/vnd.adobe.xfdf")||proposalFileMeta.getFileType().equalsIgnoreCase("application/vnd.fdf")||proposalFileMeta.getFileType().equalsIgnoreCase("octet/stream"))
						{
							
							//Check for File size < 10 MB
							if(Integer.parseInt(proposalFileMeta.getFileSize().split(" ")[0]) < 10*1024)
							{
								

								proposalFileMeta.setDocumentId(documentId);
								lpfm.add(proposalFileMeta);
								
								String Path = defaultPath;
								String finalName = Path + documentId + "."+(fname.substring(fname.lastIndexOf(".")+1,fname.length())).toLowerCase();
								System.out.println("finalName = "+finalName);
								boolean upload = false;
								 try{
									 upload = fileUploadService.uploadFile(finalName,mpf.getInputStream(),Path);
								 }
								 catch(Exception e)
								 {
									 e.printStackTrace();
								 }
		
							}
							else{
								returnList.clear();
								returnList.add(2);
							}
						}
						else{
							System.out.println("this is not pdf");
							
							returnList.clear();
							returnList.add(1);
						}
					}
			 
			return lpfm;
		}
	 
	 @RequestMapping(value="/removeNewsLetterDoc/{docName}", method = RequestMethod.POST)
		public @ResponseBody String OTDRemoveTemporary(@PathVariable int docName, HttpServletResponse response, HttpServletRequest request) {
		       
				
				String defaultPath = FTPPropertiesReader.getValueFromKey("NEWSLETTER_DOCUMENTS");
				
				String Path = defaultPath;
				
				 String name = docName+ ".pdf";
				
				 
			String currentFilePath = Path+name;
			boolean od = documentService.deleteNewsletterDocuments(docName);
			boolean deleted = false;
			if(od){
				deleted = fileUploadService.deleteFile(currentFilePath);
			}
			if (deleted) 
			{
				 System.out.println("The file was deleted successfully.");
			} 
			else 
			{
				 System.out.println("Could not delete the file.");
			}
			
			return "Done";
		}
	 
	 @RequestMapping(value = "/UpdateNewsLetter", method = RequestMethod.POST)
		public String updateNewsLetter(NewsLetterFilterForm newsLetterFilterForm,
				HttpServletRequest request) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
			
			newsLetterFilterForm.setNumUserId(usrInfo.getEmployeeId());
			try{
				//newsLetterService.saveNewsLetters(newsLetterFilterForm);
				
				newsLetterService.updateNewsLetter(newsLetterFilterForm);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return "redirect:/CreateNewsLetter";
		}

	/* 
	 
    
	 
	 
	 
	 

	 
	 


	 
	 
	
	
	
	
	@RequestMapping(value="/getAllFiltersByFilterType",method=RequestMethod.POST)
	public @ResponseBody List<NewsLetterFilterMaster> getAllFiltersByFilterType(HttpServletRequest request){		
		String filterType =  request.getParameter("filterType");		
		return newsLetterService.getAllFiltersByFilterType(filterType);	
	} */
	
}
