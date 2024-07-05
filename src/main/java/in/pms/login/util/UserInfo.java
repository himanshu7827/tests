package in.pms.login.util;

import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@ToString
public class UserInfo extends User {
	
	private static final long serialVersionUID = -5056670978156440629L;
	
	private final String employeeName;
	private final String employeeType;
	private final String mobileNumber;
	private final String gender;
	private String officeEmail;
	private String alternateEmail;
	private long designationId;
	private String designation;
	private long groupId;
	private String group;
	private int employeeId;
	private String encEmployeeId;
	List<EmployeeRoleMasterModel> employeeRoleList;
	EmployeeRoleMasterModel selectedEmployeeRole;
	EmployeeRoleMasterModel defaultEmployeeRole;

	private int highestRoleHierarchy;
	private String assignedOrganisation;
	private String assignedGroups;
	private String assignedProjects;
	private String assignedOrganisationName;
	private String assignedGroupName;
	private String lastLogin;
	
	
	public UserInfo(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			String employeeName, String employeeType, String mobileNumber,
			String gender, String officeEmail, String alternateEmail,
			long designationId, String designation, long groupId, String group,int employeeId,String encEmployeeId,List<EmployeeRoleMasterModel> employeeRoleList,
			EmployeeRoleMasterModel selectedEmployeeRole,EmployeeRoleMasterModel defaultEmployeeRole,
			int highestRoleHierarchy,String assignedOrganisation,String assignedGroups,String assignedProjects , String assignedOrganisationName, String assignedGroupName,String lastLogin) {
		super(username, password, authorities);
		this.employeeName = employeeName;
		this.employeeType = employeeType;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.officeEmail = officeEmail;
		this.alternateEmail = alternateEmail;
		this.designationId = designationId;
		this.designation = designation;
		this.groupId = groupId;
		this.group = group;
		this.employeeId=employeeId;
		this.encEmployeeId= encEmployeeId;
		this.employeeRoleList=employeeRoleList;
		this.selectedEmployeeRole=selectedEmployeeRole;
		this.defaultEmployeeRole=defaultEmployeeRole;
		this.highestRoleHierarchy=highestRoleHierarchy;
		this.assignedOrganisation=assignedOrganisation;
		this.assignedGroups=assignedGroups;
		this.assignedProjects= assignedProjects;
		this.assignedOrganisationName = assignedOrganisationName;
		this.assignedGroupName = assignedGroupName;
		this.lastLogin=lastLogin;
		
	}

	@Override
	public boolean equals(Object rhs) {		
		if (rhs instanceof User) {			
			return this.officeEmail.equals(((User) rhs).getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {		
		return officeEmail.hashCode();
	}
}
