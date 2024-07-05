package in.pms.transaction.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ManpowerUtilizationModel {	
	private long numId;	
	private long employeeId;
	private int month;
	private int year;	
	private double salaryBySystem;
	private double salaryByAuthority;	
	private int totalUtilization;
	private String submissionStatus;
	private String utilizationError;
	
	//Added by harshita for manpower utilization for project Details
	private double salaryCostProject;
	private String employeeName;
	private String encProjectId;
	private long projectId;
	private float utilization;
	private String strSalaryCostProject;
	private  String strSalaryByAuthority;
	private String strSalaryBySystem;
	List<ManpowerUtilizationDetailsModel> utilizationDetails;
    private MultipartFile utilizationDocumentFile;

}
