package in.pms.master.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSalaryModel {
	
	private long numId;	
	private long employeeId;
	private double salary;	
	private boolean valid;
	private long[] numIds;
	private String startDate;
	private String endDate;
	private String empName;
    private MultipartFile salaryDocumentFile;
    private String radioButton;

}
