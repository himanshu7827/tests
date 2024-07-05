package in.pms.master.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.ReportMaster;
import in.pms.master.model.ReportMasterModel;
@Service
public interface ReportMasterService 
{
	
	  @Transactional(propagation=Propagation.REQUIRED)
	  List<ReportMasterModel> getInterfaceDetails();
	  	  	  	  
	  @Transactional(propagation=Propagation.REQUIRED)
	  public List<ReportMasterModel> getReportType() ;
	  
	  @Transactional(propagation=Propagation.REQUIRED)
	  void DeleteReport(ReportMasterModel reportMasterModel, int userId);
	  	  
	  @Transactional(propagation=Propagation.REQUIRED)
	  public Map<String,Object> getQueryData(int id);		             
	  
      @Transactional(propagation=Propagation.REQUIRED)
      void SaveEdittedReport(ReportMasterModel reportMasterModel, int userId);
            
      @Transactional(propagation=Propagation.REQUIRED)
      public List<ReportMaster> getReportData(int reportId);

	  @Transactional(propagation=Propagation.REQUIRED)
	  public List<ReportMasterModel> getReportTypeForInterface(int interfaceId) ;

	  @Transactional(propagation=Propagation.REQUIRED)
	  public boolean isDuplicateReport(String eCode);
	  
	  @Transactional
		public String generateReportForInterface(String query,String fileName,String strSheetNames,HttpServletRequest request, HttpServletResponse response);
	  
	  @Transactional(propagation=Propagation.REQUIRED)
	  public String isReportSchemeSpecificQuery(String eCode);
	  
	  
	  @Transactional(propagation=Propagation.REQUIRED)
	   ReportMaster SaveNewReport(ReportMasterModel reportMasterModel, int userId);
	  
	  @Transactional(propagation=Propagation.REQUIRED)
	  public List<ReportMaster> getReportDetailByEcode(String eCode) ;
	  
		
	  @Transactional
	  public List<ReportMasterModel> getReportsByRoleInterface(int interfaceId);
		 

	  public void readReportAuthorityCheck();

	List<ReportMaster> reportName();

	
}
