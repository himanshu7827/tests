package in.pms.global.model;


public class FileUploadFlag {
    
	String fileName;
	
	int successFlag;
	
	int fileSize;
	
	String eNSuccessFlag;
	
	
	public String geteNSuccessFlag() {
		return eNSuccessFlag;
	}
	public void seteNSuccessFlag(String eNSuccessFlag) {
		this.eNSuccessFlag = eNSuccessFlag;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(int successFlag) {
		this.successFlag = successFlag;
	}
	
	
}