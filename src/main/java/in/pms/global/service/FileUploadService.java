package in.pms.global.service;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import in.pms.transaction.domain.MonthlyProgressDomain;

public interface FileUploadService {
	
	public boolean uploadProjectFile(MultipartFile multipartFile,long projectId,long groupId, String fileName);
	
	public boolean downloadProjectFile(String encDocumentId,HttpServletResponse response);
	
	public boolean uploadTaskFile(MultipartFile multipartFile,long projectId, String fileName);
	
	public boolean downloadTaskFile(String encDocumentId,HttpServletResponse response);
	
	public boolean downloadProposalFile(String encDocumentId,HttpServletResponse response);
	
	public boolean uploadProposalFile(MultipartFile multipartFile,long projectId, long groupId,String fileName);

	public boolean uploadSalaryFile(MultipartFile multipartFile,String fileName);

	public boolean uploadProgressRportImages(MultipartFile progressReportQualityImages, long numCategoryId, MonthlyProgressDomain monthlyProgressDomain, String string);

	@Transactional
	public boolean deleteFile(String filePath);
	
	@Transactional(propagation = Propagation.REQUIRED)
	public InputStream getFiles(String remoteFile);
	 
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkFileExist(String remoteFile);
	    
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean downloadFiles(String remoteFile, ServletOutputStream out);
	

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean uploadFile(String path, InputStream inputStream, String directory);
    
    public boolean downloadTempProjectFile(String encDocumentId,HttpServletResponse response);

    public boolean downloadProposalFilebyRevId(String encDocumentId, long numId, HttpServletResponse response);//Added by devesh on 27-09-23 to get file by rev ID

	public boolean downloadRecoSheetFile(String encProjectId,HttpServletResponse response); /*Added by devesh on 29-12-23 to download Reco Sheet from project Id*/
    
    


}
