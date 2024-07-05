package in.pms.login.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.domain.LoginHistoryDomain;
import in.pms.login.domain.Privilege;
import in.pms.login.domain.Role;
import in.pms.login.util.CustomPasswordEncoder;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.OrganisationMasterDomain;
import in.pms.master.domain.OtpInfo;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.OrganisationMasterService;
import in.pms.master.service.OtpInfoService;
//sun.misc.BASE64Decoder() deprecated for upgraded Java versions (jdk 11+)
//import sun.misc.BASE64Decoder;
import java.util.Base64;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private EmployeeMasterService employeeMasterService;

	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;

	@Autowired
	EncryptionService encryptionService;
	
	@Autowired 
	GroupMasterService groupMasterService;

	@Autowired 
	OrganisationMasterService organisationMasterService;
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	@Autowired
	OtpInfoService otpInfoService;

	@Autowired
	CustomPasswordEncoder customPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		 ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		 HttpServletRequest request = attributes.getRequest();
		//HttpSession session = request.getSession();
		
		System.out.println("sysout"+request.getParameter("otpenter"));
		String user = request.getParameter("otpenter");
		System.out.println("request object"+user);
		System.out.println("User Found");
		boolean otpInfoDomain = false;
		if(user != null)
		{
			otpInfoDomain = otpInfoService.findByEmailOTP(userName);
			System.out.println("-----"+otpInfoDomain);
		}
	    
		
		EmployeeMasterDomain employee = employeeMasterService.findByEmail(userName);
		
		
		
		
		
		
		if(otpInfoDomain == true)
		{
			 
			
			 String decodedOTP="";
			try{
				 //byte[] decodedPass = new BASE64Decoder().decodeBuffer((user.toString()));
				 byte[] decodedPass = Base64.getDecoder().decode(user.toString());
				 //System.out.println("----rawPassword-----"+rawPassword+"encodedPassword==="+encodedPassword);
					String value =new String(decodedPass);
					String[] temp = value.split(" ### ");
					
						String random1 = temp[0];
						String random2 = temp[2];
						
						//RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
					    //ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
					    //HttpServletRequest request = attributes.getRequest();
					    //HttpSession httpSession = request.getSession(true);
					    //String sessionRandom1 = (String) httpSession.getAttribute("prefixRandom");
					    //String sessionRandom2 = (String) httpSession.getAttribute("suffixRandom");
						
					    System.out.println("----decoded-----"+temp[1]);
					    decodedOTP=temp[1];
					    //if(!sessionRandom1.equals(random1) || !sessionRandom2.equals(random2)) {
					    	//return false;
					    //}
					    
						//inputPassword = temp[1];
						
					//}else{
						//inputPassword = rawPassword.toString();
						//System.out.println("----inputPassword-----"+inputPassword);
					//}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			System.out.println(decodedOTP);
			OtpInfo otpInfo=otpInfoService.findOTP(userName,decodedOTP);
			
		System.out.println(otpInfo);
			
    	System.out.println(otpInfo.getOneTimePassword());
			
		String strOTP=otpInfo.getOneTimePassword();
		employee.setPassword(customPasswordEncoder.encode(strOTP));
		//OtpInfo otpInfoDomain = otpInfoService.checkSameOtp();
		System.out.println(employee.getPassword());
	}
		System.out.println("-----"+otpInfoDomain);
		
		
		//OtpInfo otpInfo = new OtpInfo();
		//EmployeeMasterDomain employee = new EmployeeMasterDomain();
		
		if(null == employee){
			EmployeeMasterDomain anyEmployee = employeeMasterService.findAnyByEmail(userName);
			
				if(null != anyEmployee){
					throw  new UsernameNotFoundException("Inactive User");
				}
		throw  new UsernameNotFoundException("User Not Found");
		}
		
		List<EmployeeRoleMasterModel> employeeRoles = employeeRoleMasterService.getActiveEmployeeRoleByEmpId(employee.getNumId());
		EmployeeRoleMasterModel selectedEmpRole = null;
		EmployeeRoleMasterModel defaultEmpRole = employeeRoleMasterService.getDefaultEmployeeRoleByEmpId(employee.getNumId());
		if(null != defaultEmpRole){
			selectedEmpRole=defaultEmpRole;
		}else{
			if(null != employeeRoles && employeeRoles.size()>0){
				selectedEmpRole=employeeRoles.get(0);				
			}else{
				selectedEmpRole = new EmployeeRoleMasterModel();
				GroupMasterDomain groupMaster = employee.getGroupMasterDomain();
				if(null != groupMaster){
					selectedEmpRole.setNumGroupId((int)groupMaster.getNumId());
					OrganisationMasterDomain organisation = groupMaster.getOrganisationMasterDomain();
					if(null != organisation){
						selectedEmpRole.setNumOrganisationId((int)organisation.getNumId());
					}
					
				}
				
				
			}
	}
	
		int highestRoleHierarchy = employeeRoleMasterService.getEmployeeHighestRoleHierarchy(employee.getNumId());
		String assignedOrganisation = employeeRoleMasterService.getDistinctOrganisationsForEmployee(employee.getNumId());
		String assignedGroups = employeeRoleMasterService.getDistinctGroupsForEmployee(employee.getNumId());
		String assignedProjects = employeeRoleMasterService.getDistinctProjectsForEmployee(employee.getNumId());
		String assignedOrganisationName = "";
		String assignedGroupName = "";
		
		if (null != assignedOrganisation) {
			assignedOrganisationName = organisationMasterService.getDistinctOrganisationShortName(assignedOrganisation);
			if (null == assignedOrganisationName )
			{
			assignedOrganisationName = organisationMasterService.getDistinctOrganisation(assignedOrganisation);
		}
	}
		if(null != assignedGroups && !assignedGroups.equals("0")){
			assignedGroupName = groupMasterService.getDistinctGroupShortNamesForOrganisation(assignedGroups);
			if (null == assignedGroupName )
			{
				assignedGroupName = groupMasterService.getDistinctGroupsForOrganisation(assignedGroups);
			}
			
		}
		
		int employeeId = (int) employee.getNumId();
		String employeeType = employee.getEmpTypeMasterDomain().getStrEmpTypeName();
		DesignationMasterDomain designationMaster = employee.getDesignationMaster();
		GroupMasterDomain groupMasterDomain = employee.getGroupMasterDomain();
		long groupId = employee.getGroupMasterDomain().getNumId();
		long designationId = employee.getDesignationMaster().getNumId();
		LoginHistoryDomain loginHistoryDomain =loginHistoryService.getLastLoginDetails(employee.getNumId());
		String lastLogin="";
		if(null != loginHistoryDomain){
			lastLogin=DateUtils.dateToDateTimeString(loginHistoryDomain.getLoginDate());
		}
		
		
		
		return new UserInfo(employee.getOfficeEmail(), employee.getPassword(), getAuthorities(employee.getRoles()),
				employee.getEmployeeName(), employeeType, employee.getMobileNumber(), employee.getGender(),
				employee.getOfficeEmail(), employee.getAlternateEmail(), designationId,
				designationMaster.getDesignationName(), groupId, groupMasterDomain.getGroupName(), employeeId,
				encryptionService.encrypt("" + employeeId), employeeRoles, selectedEmpRole,defaultEmpRole,highestRoleHierarchy, assignedOrganisation,
				assignedGroups, assignedProjects,assignedOrganisationName,assignedGroupName,lastLogin);
		
		
	}

	private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private final List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		final List<Privilege> collection = new ArrayList<Privilege>();
		for (final Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (final Privilege item : collection) {
			privileges.add(item.getName());
		}

		return privileges;
	}

	private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
