package in.pms.master.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EmployeeMasterModel {

	private long numId;

	private String employeeName;

	private String dateOfBirth;

	private String dateOfResignation;

	private String dateOfRelease;

	private String dateOfJoining;

	private String contactNumber;

	private String mobileNumber;

	private String gender;

	private String officeEmail;

	private String alternateEmail;

	private String category;

	private String employmentStatus;

	private int updateFlag;
 
	private long designation;
	
	private long postId;
	
	private long contractDetailId;
	
	private boolean valid;

	private String searchEmployeeId;

	private long employeeTypeId;
	
	private List<Long> roles;
	private long organisationId;
	private long groupId;
	private long employeeId;
	
	private long thirdPartyId;
	private String thirdPartyName;
	
	private String groupName;
	private String employeeTypeName;
	private int captcha;
	
	private String encEmployeeId;
	
	private String dtContractStartDate;
	
	private String dtContractEndDate;
	private MultipartFile employeeDocumentFile;
	
	private int optionValue;
	
	private String strDesignation;
	
	private String searchEmployeeEmail;
	
	private String dataLoadedBasedOn;
	
	
	List<EmployeeRoleMasterModel> employeeRoleList = new ArrayList<EmployeeRoleMasterModel>();
	
	private String releaseRemark;
	
	private String teamDetails;
	
	private int involvment;
	
	private int pendingInv;
	
	private int projectCount;
	
	private int numDeputedAt;
	List<EmployeeMasterModel> employeeMasterList = new ArrayList<EmployeeMasterModel>();
	List<EmployeeMasterModel> employeeMasterCustomizedList = new ArrayList<EmployeeMasterModel>();
	private int resignedEmpCount;
	private int relievedCount;
	private int workingCount;
	private int totalRejoinCount;
}
