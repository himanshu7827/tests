package in.pms.global.util;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

import in.pms.global.misc.FTPPropertiesReader;

public class PMSFtp {
	
	 public static boolean loginFTP(FTPClient ftpClient) {
	        boolean login = false;
	      
	        String ftpServer = FTPPropertiesReader.getValueFromKey("FTP_HOST_NAME");
	        String ftpUser = FTPPropertiesReader.getValueFromKey("FTP_USER_NAME");
	        String ftpPassword = FTPPropertiesReader.getValueFromKey("FTP_PASSWORD");
	        try {
	           
	            ftpClient.connect(ftpServer);
	            login = ftpClient.login(ftpUser, ftpPassword);
	            ftpClient.enterLocalPassiveMode();
	        } catch (SocketException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return login;
	    }
	 
	 public static boolean logoutFTP(FTPClient ftpClient) {
		 boolean result = false;
		 if(null != ftpClient){
			 try {
				 result = ftpClient.logout();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		 }		 
		 return result;
	 }

	   public static boolean makeDirectories(FTPClient ftpClient, String dirPath) throws IOException {
	        String[] pathElements = dirPath.split("/");
	        String dir = "";
	        if (pathElements != null && pathElements.length > 0) {
	            for (String singleDir : pathElements) {	 
	                dir = singleDir;
	                if (singleDir.equals(""))
	                    continue;
	                ftpClient.sendSiteCommand("chmod " + "755 " + dir);
	                boolean existed = ftpClient.changeWorkingDirectory(dir);
	                if (!existed) {
	                    boolean created = ftpClient.makeDirectory(dir);
	                    if (created) {
	                        ftpClient.changeWorkingDirectory(dir);
	                    } else {
	                        return false;
	                    }
	                }
	            }
	        }
	        return true;
	    }
}
