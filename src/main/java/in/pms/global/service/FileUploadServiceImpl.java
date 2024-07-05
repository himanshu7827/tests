package in.pms.global.service;

import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.util.PMSFtp;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProposalDocumentDetailsDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.domain.TaskDocumentDomain;
import in.pms.master.domain.TempProjectDocumentDetailsDomain;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProposalDocumentMasterService;
import in.pms.master.service.ProposalMasterService;
import in.pms.master.service.TaskDetailsService;
import in.pms.transaction.domain.MonthlyProgressDomain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.SocketException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileUploadServiceImpl implements FileUploadService {

	public static final String rootPath =FTPPropertiesReader.getValueFromKey("FTP_ROOT");

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	@Autowired
	TaskDetailsService taskDetailsService;
	@Autowired
	ProposalDocumentMasterService proposalDocumentMasterService;
	@Autowired
	ProposalMasterService proposalMasterService;
	@Autowired
	ProjectMasterService projectMasterService;
	
	
	@Override
	public boolean uploadProjectFile(MultipartFile multipartFile,
			long projectId,long groupId, String fileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
		String filePath = "PMS"+"/"+groupId+"/"+"Project"+"/"+projectId;			
		if(login){
			 try {
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				PMSFtp.makeDirectories(ftpClient, filePath); 
				ftpClient.changeWorkingDirectory(rootPath+File.separator+filePath);
				result =ftpClient.storeFile(fileName, multipartFile.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('DOWNLOAD_PROJECT_DOCUMENT')")
	public boolean downloadTempProjectFile(String encDocumentId,HttpServletResponse response){
		boolean result = false;
		String documentId = encryptionService.dcrypt(encDocumentId);
		
		long uploadId = Long.parseLong(documentId);		
		TempProjectDocumentDetailsDomain projectDocumentDetail =projectDocumentMasterService.getTempProjectDocumentDetailsById(uploadId);
		
		long projectId = projectDocumentDetail.getProjectDocumentMasterDomain().getProjectId();
		ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
		long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();
		String fileName= projectDocumentDetail.getDocumentName();
		String originalFileName= projectDocumentDetail.getOriginalDocumentName();
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/"+groupId+"/"+"Project"+"/"+projectId;			
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
	public boolean downloadProposalFile(String encDocumentId,HttpServletResponse response){
		boolean result = false;
		String documentId = encryptionService.dcrypt(encDocumentId);
		
		long uploadId = Long.parseLong(documentId);		
		ProposalDocumentDetailsDomain proposalDocumentDetailsDomain =proposalDocumentMasterService.getProposalDocumentDetail(uploadId);
		
		long proposalId = proposalDocumentDetailsDomain.getProposalDocumentMasterDomain().getProposalId();
		ProposalMasterDomain proposalMasterDomain=proposalMasterService.getProposalDomainById(proposalId);
		long groupId=proposalMasterDomain.getApplication().getGroupMaster().getNumId();
		String fileName= proposalDocumentDetailsDomain.getDocumentName();
		String originalFileName= proposalDocumentDetailsDomain.getOriginalDocumentName();
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/"+groupId+"/"+"Proposal"+"/"+proposalId;	
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
	
	//Added by devesh on 27-09-23 to get file by rev ID
	@Override
	public boolean downloadProposalFilebyRevId(String encDocumentId, long numId, HttpServletResponse response){
		boolean result = false;
		String documentId = encryptionService.dcrypt(encDocumentId);
		
		long uploadId = Long.parseLong(documentId);		
		List<Object[]> proposalDocumentDetailsDomain =proposalDocumentMasterService.getProposalDocumentDetailbyRevId(uploadId, numId);
		
		String fileName = "";
	    String originalFileName = "";
	    long proposalId = 0;
	    long groupId = 0;
	    
		for (Object[] row : proposalDocumentDetailsDomain) {
		    fileName = (String) row[0]; // Index 0 corresponds to str_document_name
		    originalFileName = (String) row[1]; // Index 1 corresponds to str_original_doc_name
		    // Handle proposalId (bigint) as long
		    BigInteger a = (BigInteger) row[2];
		    proposalId = a != null ? a.longValue() : 0L;
		    // Handle groupId (bigint) as long
		    BigInteger b = (BigInteger) row[3];
		    groupId = b != null ? b.longValue() : 0L;
		}
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/"+groupId+"/"+"Proposal"+"/"+proposalId;	
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
	//End of retrieving Document by Rev Id
	
	@Override
	public boolean uploadProposalFile(MultipartFile multipartFile,
			long proposalId,long groupId, String fileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
		String filePath = "PMS"+"/"+groupId+"/"+"Proposal"+"/"+proposalId;		
		if(login){
			 try {
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				PMSFtp.makeDirectories(ftpClient, filePath); 
				ftpClient.changeWorkingDirectory(rootPath+File.separator+filePath);
				result =ftpClient.storeFile(fileName, multipartFile.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('UPLOAD_TASK_DOCUMENT')")
	public boolean uploadTaskFile(MultipartFile multipartFile,
			long projectId, String fileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
		String filePath = "PMS/"+projectId+"/"+"TaskDoc";		
		if(login){
			 try {
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				PMSFtp.makeDirectories(ftpClient, filePath); 
				ftpClient.changeWorkingDirectory(rootPath+File.separator+filePath);
				result =ftpClient.storeFile(fileName, multipartFile.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return result;
	}
	
	@Override
	public boolean uploadSalaryFile(MultipartFile multipartFile,String fileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
		String filePath = "PMS/"+"SalaryDoc";		
		if(login){
			 try {
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				PMSFtp.makeDirectories(ftpClient, filePath); 
				ftpClient.changeWorkingDirectory(rootPath+File.separator+filePath);
				result =ftpClient.storeFile(fileName, multipartFile.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('DOWNLOAD_TASK_DOCUMENT')")
	public boolean downloadTaskFile(String encDocumentId,HttpServletResponse response){
		boolean result = false;
		String documentId = encryptionService.dcrypt(encDocumentId);
		
		long uploadId = Long.parseLong(documentId);		
		TaskDocumentDomain taskDocumentDomain =taskDetailsService.getTaskDocumentDetail(uploadId);
		long projectId = taskDocumentDomain.getProjectId();

		String fileName= taskDocumentDomain.getStrDocumentName();
		String originalFileName= taskDocumentDomain.getOriginalDocumentName();
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS/"+projectId+"/"+"TaskDoc";		
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
	public boolean uploadProgressRportImages(MultipartFile progressReportQualityImages, long numCategoryId,MonthlyProgressDomain monthlyProgressDomain,
			String fileName) {

		boolean result = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
		String filePath = "PMS"+"/ProgressReportFiles/"+monthlyProgressDomain.getYear()+"/"+monthlyProgressDomain.getMonth()+"/"+monthlyProgressDomain.getProjectMasterDomain().getNumId()+"/"+numCategoryId;			
		if(login){
			 try {
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				PMSFtp.makeDirectories(ftpClient, filePath); 
				ftpClient.changeWorkingDirectory(rootPath+File.separator+filePath);
				result =ftpClient.storeFile(fileName, progressReportQualityImages.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return result;
	
		
	}

	@Override
	public boolean deleteFile(String filePath) {
		boolean deleted = false;
		FTPClient ftpClient = new FTPClient();		
		boolean login = PMSFtp.loginFTP(ftpClient);
					
		if(login){
			 try {
				 if (checkFileExist(filePath)) {
					 deleted =ftpClient.deleteFile(filePath);
	                    if (deleted) {
	                        System.out.println("The file was deleted successfully.");
	                    } else {
	                        deleted=false;
	                    }
	                }
				
			} catch (IOException e) {				
				e.printStackTrace();
			}finally{
				PMSFtp.logoutFTP(ftpClient);
			}
		}
		return deleted;
	
		
	}

	public boolean checkFileExist(String filePath) {

        FTPClient ftpClient = new FTPClient();
        boolean success = false;
        try {

        	boolean login = PMSFtp.loginFTP(ftpClient);

            if (login) {
                System.out.println("Connection established..."+filePath);

                InputStream inputStream = ftpClient.retrieveFileStream(filePath);
                int returnCode = ftpClient.getReplyCode();
                if (inputStream == null || returnCode == 550) {
                    System.out.println("File doesnot exist");
                } else {
                    success = true;
                    inputStream.close();
                }
                boolean logout = PMSFtp.logoutFTP(ftpClient);
                if (logout) {
                    System.out.println("Connection close...");
                }
            } else {
                System.out.println("Connection fail...");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	PMSFtp.logoutFTP(ftpClient);
        }
        return success;
    
	}	
	
	
	  /*
     * Getting Files from server for PDF
     */
    public InputStream getFiles(String remoteFile) {
        FTPClient ftpClient = new FTPClient();
        boolean success = false;
        InputStream is = null;
        remoteFile = parseFilePath(remoteFile);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {

            boolean login = ftpConnection(ftpClient);
            if (login) {
            
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                success = ftpClient.retrieveFile(remoteFile, outputStream);

                if (success) {
                    System.out.println("File Retrieved");
                    is = new ByteArrayInputStream(outputStream.toByteArray());
                } else
                    System.out.println("File Not Retrieved");

                outputStream.close();
                is.close();

                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection close...");
                }
            } else {
                System.out.println("Connection fail...");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return is;
    }
	
    
 public String parseFilePath(String dirPath) {
	
        if (dirPath.contains(FTPPropertiesReader.getValueFromKey("PROPOSAL_DOC_UPLOAD_PATH"))) {

            String lastElement = dirPath.substring(dirPath.length() - 1);
            String[] elements = dirPath.split("/");
            String dirPathNew = "";
            int i = 0;
            String prevElement = "";
            for (String element : elements) {
                i++;
                           
                if ((StringUtils.isNumeric(element)) && element.length() == 12 && !element.equalsIgnoreCase("100000000000") && !(prevElement.length() == 4) && (element.indexOf(".")==-1)) {
                    String year = element.substring(2, 6);
                    dirPathNew += year + "/";
                }
                
                if (element != null && !element.isEmpty()) {
                    if (i == elements.length) {
                        if (lastElement.equalsIgnoreCase("/"))
                            dirPathNew += element + "/";
                        else
                            dirPathNew += element;
                    } else {
                        dirPathNew += element + "/";
                    }
                }
                prevElement = element;
            }
           

            return dirPathNew;
        }
        return dirPath;
    }
 
 public static boolean ftpConnection(FTPClient ftpClient) {
     boolean login = false;
    /* ResourceBundle bundle2 = ResourceBundle.getBundle("in.cdacnoida.serb.gl.misc.FTP_Server_details");
     String ftpServer = bundle2.getString("FTP_HOST_NAME");
     String ftpUser = bundle2.getString("FTP_USER_NAME");
     String ftpPassword = bundle2.getString("FTP_PASSWORD");*/
     String ftpServer = FTPPropertiesReader.getValueFromKey("FTP_HOST_NAME");
     String ftpUser = FTPPropertiesReader.getValueFromKey("FTP_USER_NAME");
     String ftpPassword = FTPPropertiesReader.getValueFromKey("FTP_PASSWORD");
     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
     try {
         // pass directory path on server to connect
         ftpClient.connect(ftpServer);

         // pass username and password, returned true if authentication is
         // successful
         login = ftpClient.login(ftpUser, ftpPassword);
         ftpClient.enterLocalPassiveMode();
     } catch (SocketException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
     return login;
 }
 
 public boolean downloadFiles(String remoteFile, ServletOutputStream out) {
	    FTPClient ftpClient = new FTPClient();
	    FileOutputStream fos = null;
	    String path = parseFilePath(remoteFile);
	    boolean success = false;
	    try {

	        boolean login = ftpConnection(ftpClient);

	        if (login) {
	            System.out.println("Connection established...");

	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

	            success = ftpClient.retrieveFile(path, out);

	            out.close();
	            if (success) {
	                System.out.println("File #1 has been downloaded successfully.");
	            }

	            // logout the user, returned true if logout successfully
	            boolean logout = ftpClient.logout();
	            if (logout) {
	                System.out.println("Connection close...");
	            }
	        } else {
	            System.out.println("Connection fail...");
	        }

	    } catch (SocketException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            ftpClient.disconnect();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return success;
	}
 
 public boolean uploadFile(String path, InputStream inputStream, String directory) {
     FTPClient ftpClient = new FTPClient();
     System.out.println("Dir"+directory);
     path = parseFilePath(path);
     directory = parseFilePath(directory);
     boolean uploaded = false;
     try {
         boolean login = ftpConnection(ftpClient);
        /* ResourceBundle bundle2 = ResourceBundle.getBundle("in.cdacnoida.serb.gl.misc.FTP_Server_details");
         String ftpRoot = bundle2.getString("FTP_ROOT");*/
         if (login) {
             ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
             // ftpClient.enterLocalPassiveMode();
             System.out.println("Connection established...");

             System.out.println("File Path...." + path);
             System.out.println("Directory Path...." + directory);

             boolean success = makeDirectories(ftpClient, directory);

             System.out.println(ftpClient.getReplyString());

             if (success) {
                 System.out.println("Directory Created");
             } else {
                 System.out.println("Directory Not Created");
             }
             ftpClient.changeWorkingDirectory(rootPath);
             // inputStream = new FileInputStream(mpf.getOriginalFilename());

             uploaded = ftpClient.storeFile(path, inputStream);
             System.out.println(ftpClient.getReplyString());

             if (uploaded) {
                 System.out.println("File uploaded successfully !");
             } else {
                 System.out.println("Error in uploading file !");
             }

             // logout the user, returned true if logout successfully
             boolean logout = ftpClient.logout();
             if (logout) {
                 System.out.println("Connection close...");
             }
         } else {
             System.out.println("Connection fail...");
         }

     } catch (SocketException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } finally {
         try {
             ftpClient.disconnect();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     return uploaded;

 }
 
 public boolean makeDirectories(FTPClient ftpClient, String dirPath) throws IOException {

     String[] pathElements = dirPath.split("/");
     String dir = "";
     if (pathElements != null && pathElements.length > 0) {
         for (String singleDir : pathElements) {
             // if(!singleDir.equals(""))
             dir = singleDir;

             if (singleDir.equals(""))
                 continue;
             ftpClient.sendSiteCommand("chmod " + "755 " + dir);
             boolean existed = ftpClient.changeWorkingDirectory(dir);
             System.out.println(dir + ":" + existed);
             if (!existed) {
                 boolean created = ftpClient.makeDirectory(dir);
                 if (created) {
                     System.out.println("CREATED directory: " + dir);
                     ftpClient.changeWorkingDirectory(dir);
                 } else {
                     System.out.println("COULD NOT create directory: " + dir);
                     return false;
                 }
             }
         }
     }
     return true;
 }
 
 @Override
	@PreAuthorize("hasAuthority('DOWNLOAD_PROJECT_DOCUMENT')")
	public boolean downloadProjectFile(String encDocumentId,HttpServletResponse response){
		boolean result = false;
		String documentId = encryptionService.dcrypt(encDocumentId);
		
		long uploadId = Long.parseLong(documentId);		
		ProjectDocumentDetailsDomain projectDocumentDetail =projectDocumentMasterService.getProjectDocumentDetail(uploadId);
		
		long projectId = projectDocumentDetail.getProjectDocumentMasterDomain().getProjectId();
		ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
		long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();
		String fileName= projectDocumentDetail.getDocumentName();
		String originalFileName= projectDocumentDetail.getOriginalDocumentName();
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/"+groupId+"/"+"Project"+"/"+projectId;			
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
 
 /*Added by devesh on 29-12-23 to download Reco Sheet from project Id*/
 @Override
	@PreAuthorize("hasAuthority('DOWNLOAD_PROJECT_DOCUMENT')")
	public boolean downloadRecoSheetFile(String encProjectId,HttpServletResponse response){
		boolean result = false;
		String strProjectId = encryptionService.dcrypt(encProjectId);
		long projectId = Long.parseLong(strProjectId);	
		System.out.println("\n projectId.."+projectId);
		ProjectDocumentMasterDomain projectMasterDetail =projectDocumentMasterService.getProjectDocumentIdFromProjectId(projectId);
		/*String documentId = encryptionService.dcrypt(encDocumentId);*/
		/*System.out.println("\n projectMasterDetail.."+projectMasterDetail);*/
		
		long uploadId = projectMasterDetail.getProjectDocumentDetailsDomainList().get(0).getNumId();	
		ProjectDocumentDetailsDomain projectDocumentDetail =projectDocumentMasterService.getProjectDocumentDetail(uploadId);
		
		/*long projectId = projectDocumentDetail.getProjectDocumentMasterDomain().getProjectId();*/
		ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
		long groupId=projectMasterDomain.getApplication().getGroupMaster().getNumId();
		String fileName= projectDocumentDetail.getDocumentName();
		String originalFileName= projectDocumentDetail.getOriginalDocumentName();
		
		FTPClient ftpClient = new FTPClient();
		boolean login = PMSFtp.loginFTP(ftpClient);
		if(login){
			String filePath = "PMS"+"/"+groupId+"/"+"Project"+"/"+projectId;		
			System.out.println("\n filePath.."+filePath);
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
 /*End of Function*/
}
