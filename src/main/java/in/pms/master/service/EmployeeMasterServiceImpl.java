package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.dao.RoleDao;
import in.pms.login.domain.Role;
import in.pms.login.util.UserInfo;
import in.pms.mail.dao.SendMail;
import in.pms.master.dao.EmployeeContractDetailDao;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProjectPaymentReceivedDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.domain.EmployeeContractDetailDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ForgotPassword;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ThirdPartyMasterDomain;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.EmpTypeMasterModel;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.transaction.dao.EmployeeApprovedJobMappingDao;
import in.pms.transaction.domain.EmployeeApprovedJobMapping;
import in.pms.transaction.model.MiscDataModel;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class EmployeeMasterServiceImpl implements EmployeeMasterService{
	
	@Autowired
	EmployeeMasterDao employeeMasterDao;
	@Autowired
	EmpTypeMasterService empTypeMasterService;
	@Autowired
	GroupMasterService groupMasterService;
	@Autowired
	DesignationMasterService designationMasterService;
	
	@Autowired
	ThirdPartyMasterService thirdPartyMasterService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	GlobalDao globalDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectPaymentReceivedDao projectPaymentReceivedDao;
	
	@Autowired
	EmployeeContractDetailDao employeeContractDetailDao;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	
	@Autowired
	PostTrackerMasterService postTrackerMasterService;
	
	@Autowired
	EmployeeApprovedJobMappingDao employeeApprovedJobMappingDao;
	
	@Autowired
	GroupMasterDao groupMasterDao;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	ProposalMasterDao proposalMasterDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;

	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_EMPLOYEE_MST','USERPROFILE')")
	public long saveUpdateEmployeeMaster(EmployeeMasterModel employeeMasterModel){
		//In case of duplicate Employee
		//throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
		EmployeeMasterDomain employeeMasterDomain=	convertEmployeeMasterModelToDomain(employeeMasterModel);
		return employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
	}
	
	@Override
	public String checkDuplicateCorporateId(EmployeeMasterModel employeeMasterModel){
		return null;
	}
	
	@Override
	public EmployeeMasterModel getEmployeeMasterDomainById(long numId){
		EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeDetails(numId);
		return convertDomainToModel(employeeMasterDomain);
	}
	
	@Override
	@PreAuthorize("hasAnyAuthority('WRITE_EMPLOYEE_MST','USERPROFILE')")
	public EmployeeMasterModel getEmployeeMasterDomainByIdForUpdate(long numId){
		EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeDetails(numId);
		return convertDomainToModel(employeeMasterDomain);
	}
	
	@Override
	public List<EmployeeMasterModel> getAllEmployeeMasterDomain(){
		List<EmployeeMasterDomain> employeeMasterList = employeeMasterDao.getAllEmployeeMasterDomain();		
		return convertDomainListToModelList(employeeMasterList);
	}
	
	@Override
	public List<EmployeeMasterModel> getAllActiveEmployeeMasterDomain(){
		List<EmployeeMasterDomain> list = employeeMasterDao.getAllActiveEmployee();
		return convertDomainListToModelList(list);
	}
	
	@Override
	public EmployeeMasterDomain findByEmail(String email) {
		return employeeMasterDao.findByEmail(email);
	}
	
	@Override
	public EmployeeMasterDomain findAnyByEmail(String email){
		return employeeMasterDao.findAnyByEmail(email);
	}
	
	public List<EmployeeMasterModel> convertEmployeeMasterDomainToModelList(List<EmployeeMasterDomain> employeeMasterDomainList){
		return null;	
	}
	
	public EmployeeMasterModel convertEmployeeMasterDomainToModel(EmployeeMasterDomain employeeMasterDomain){
		EmployeeMasterModel employeeMasterModel= new EmployeeMasterModel();
		employeeMasterModel.setNumId(employeeMasterDomain.getNumId());
		
		return null;
	}
	
	public EmployeeMasterDomain convertEmployeeMasterModelToDomain(EmployeeMasterModel employeeMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		EmployeeMasterDomain employeeMasterDomain = new EmployeeMasterDomain();
		if(employeeMasterModel.getUpdateFlag()==1){
			//Load Obj By Id
			employeeMasterDomain= employeeMasterDao.getEmployeeDetails(employeeMasterModel.getNumId());
		}
		else{
			//String defaultPassword=ResourceBundleFile.getValueFromKey("DEFAULTPASSWORD");
			String str=employeeMasterModel.getDateOfBirth();
			String dtformat = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(dtformat);
			try{
					Date dt = sdf.parse(str);
					SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
					String defaultPassword = sdf2.format(dt);
					employeeMasterDomain.setPassword(passwordEncoder.encode(defaultPassword));
				}catch(Exception e){}
			List<Long> roles=new ArrayList<Long>();
			roles.add((long) 5);//for ROLE_USER
			employeeMasterModel.setRoles(roles);
		}
		if(employeeMasterModel.getEmployeeTypeId() !=35){
		if(employeeMasterDomain.getNumId() ==0 ){
			EmployeeContractDetailDomain employeeContractDetailDomain = convertModelToEmployeeContractDetailDomain(employeeMasterModel);
			employeeContractDetailDomain.setNumTrUserId(userInfo.getEmployeeId());
			employeeContractDetailDomain.setStrRemarks("New Record");
			employeeContractDetailDomain.setEmployeeMasterDomain(employeeMasterDomain);
			
			List<EmployeeContractDetailDomain> list= new ArrayList<EmployeeContractDetailDomain>();
			list.add(employeeContractDetailDomain);
			employeeMasterDomain.setEmployeeContractDetailDomain(list);
		}else{
		 List<EmployeeContractDetailDomain> list= employeeContractDetailDao.getContractDataByEmpId(employeeMasterModel.getNumId());
		if(null != list && list.size()>0){			
			for(int i=0;i<list.size();i++){
				EmployeeContractDetailDomain employeeContractDetailDomain = list.get(i);
				if(employeeContractDetailDomain.getNumIsValid() == 1){
					list.remove(i);
						employeeContractDetailDomain.setNumIsValid(0);
						employeeContractDetailDomain.setStrRemarks("Previous Record");
						employeeContractDetailDomain.setDtTrDate(new Date());
						employeeContractDetailDomain.setNumTrUserId(userInfo.getEmployeeId());
						list.add(i,employeeContractDetailDomain);
					}
				}							
		}
				EmployeeContractDetailDomain employeeContractDetailDomain = convertModelToEmployeeContractDetailDomain(employeeMasterModel);
				employeeContractDetailDomain.setNumTrUserId(userInfo.getEmployeeId());			
				employeeContractDetailDomain.setEmployeeMasterDomain(employeeMasterDomain);
				employeeContractDetailDomain.setStrRemarks("New Record");
				list.add(employeeContractDetailDomain);
				employeeMasterDomain.setEmployeeContractDetailDomain(list);			
		}
		}
		employeeMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		employeeMasterDomain.setAlternateEmail(employeeMasterModel.getAlternateEmail());
		employeeMasterDomain.setCategory(employeeMasterModel.getCategory());
		employeeMasterDomain.setContactNumber(employeeMasterModel.getContactNumber());
		/*employeeMasterDomain.setPassword(passwordEncoder.encode(employeeMasterModel.getPassword()));*/
		if(null !=employeeMasterModel.getDateOfBirth()){
			try {
				employeeMasterDomain.setDateOfBirth(DateUtils.dateStrToDate(employeeMasterModel.getDateOfBirth()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null !=employeeMasterModel.getDateOfJoining()){
			try {
				employeeMasterDomain.setDateOfJoining(DateUtils.dateStrToDate(employeeMasterModel.getDateOfJoining()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
	
		
		if(null != employeeMasterModel.getDateOfRelease() &&  !employeeMasterModel.getDateOfRelease().equals("")){
			try {
				employeeMasterDomain.setDateOfRelease(DateUtils.dateStrToDate(employeeMasterModel.getDateOfRelease()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null != employeeMasterModel.getDateOfResignation() && !employeeMasterModel.getDateOfResignation().equals("")){
			try {
				employeeMasterDomain.setDateOfResignation(DateUtils.dateStrToDate(employeeMasterModel.getDateOfResignation()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		employeeMasterDomain.setDtTrDate(new Date());
		employeeMasterDomain.setEmployeeName(employeeMasterModel.getEmployeeName());
	
		employeeMasterDomain.setEmploymentStatus(employeeMasterModel.getEmploymentStatus());
		
		employeeMasterDomain.setGender(employeeMasterModel.getGender());
			
		employeeMasterDomain.setMobileNumber(employeeMasterModel.getMobileNumber());
		
		if(employeeMasterModel.getNumId() != 0){
			employeeMasterDomain.setNumId(employeeMasterModel.getNumId());
			
			}
		else{
			employeeMasterDomain.setNumId(employeeMasterDao.getSequence());
			
		}
		if (employeeMasterModel.getUpdateFlag()==1 && employeeMasterModel.getNumId()==0 ){
			employeeMasterDomain.setNumId(Long.parseLong(employeeMasterModel.getSearchEmployeeId()));
		}
		
		if(employeeMasterModel.isValid()){
			employeeMasterDomain.setNumIsValid(1);
		}else{
			employeeMasterDomain.setNumIsValid(0);
		}
		employeeMasterDomain.setOrganisationid(employeeMasterModel.getOrganisationId());
		employeeMasterDomain.setOfficeEmail(employeeMasterModel.getOfficeEmail());	
		employeeMasterDomain.setEmpTypeMasterDomain(empTypeMasterService.getEmpTypeMasterDomainById(employeeMasterModel.getEmployeeTypeId()));
		employeeMasterDomain.setGroupMasterDomain(groupMasterService.getGroupMasterDomainById(employeeMasterModel.getGroupId()));
		employeeMasterDomain.setDesignationMaster(designationMasterService.getDesignationMasterDomainById(employeeMasterModel.getDesignation()));
		if(null != employeeMasterModel.getRoles() && employeeMasterModel.getRoles().size()>0){
			employeeMasterDomain.setRoles(roleDao.getAllActiveRoleDomainId(employeeMasterModel.getRoles()));
		}
		employeeMasterDomain.setThirdPartyMasterDomain(thirdPartyMasterService.getThirdPartyDomainById(employeeMasterModel.getThirdPartyId()));
		employeeMasterDomain.setPostTrackerMaster(postTrackerMasterService.getPostTrackerDomainById(employeeMasterModel.getPostId()));
		employeeMasterDomain.setNumDeputedAt(employeeMasterModel.getNumDeputedAt());
		return employeeMasterDomain;
	}
	
	public EmployeeContractDetailDomain convertModelToEmployeeContractDetailDomain(EmployeeMasterModel employeeMasterModel){
		EmployeeContractDetailDomain employeeContractDetailDomain=new EmployeeContractDetailDomain();
		try {
			employeeContractDetailDomain.setStartDate(DateUtils.dateStrToDate(employeeMasterModel.getDtContractStartDate()));
		} catch (ParseException e) {				
			e.printStackTrace();
		}
		try {
			employeeContractDetailDomain.setEndDate(DateUtils.dateStrToDate(employeeMasterModel.getDtContractEndDate()));
		} catch (ParseException e) {				
			e.printStackTrace();
		}
					
		employeeContractDetailDomain.setDtTrDate(new Date());		
		employeeContractDetailDomain.setNumIsValid(1);
		return employeeContractDetailDomain;
	}
	
	public List<EmployeeMasterModel> convertDomainListToModelList(List<EmployeeMasterDomain> list){
		
		List<EmployeeMasterModel> outputList = new ArrayList<EmployeeMasterModel>();
		
		for(int i=0;i<list.size();i++){
			EmployeeMasterModel model = new EmployeeMasterModel();
			EmployeeMasterDomain domain = list.get(i);
			model.setAlternateEmail(domain.getAlternateEmail());
			model.setNumId(domain.getNumId());
			model.setEmployeeName(domain.getEmployeeName());
			model.setDateOfJoining(DateUtils.dateToString(domain.getDateOfJoining()));
			model.setEmployeeTypeName(domain.getEmpTypeMasterDomain().getStrEmpTypeName());
			model.setGroupName(domain.getGroupMasterDomain().getGroupName());
			if(null != domain.getDesignationMaster()){
				model.setStrDesignation(domain.getDesignationMaster().getDesignationName());
				model.setDesignation(domain.getDesignationMaster().getNumId());
			}	
			ThirdPartyMasterDomain thirdPartyMasterDomain = domain.getThirdPartyMasterDomain();
			if(null != thirdPartyMasterDomain &&  thirdPartyMasterDomain.getNumId() !=0 ) {
				model.setThirdPartyName(thirdPartyMasterDomain.getStrAgencyName());
			}
			outputList.add(model);
		}
		
		return outputList;
	}
	
	@Override
	public boolean checkIfValidOldPassword(EmployeeMasterDomain user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}
	
	@Override
	public void changeUserPassword(EmployeeMasterDomain user, String newPassword) {
		 //user.setPassword(passwordEncoder.encode(newPassword));
		EmployeeMasterDomain employee = employeeMasterDao.getEmployeeMasterById(user.getNumId());
		employee.setPassword(passwordEncoder.encode(newPassword));
		 employeeMasterDao.saveUpdateEmployeeMaster(employee);
	}
	
	@Override
	public EmployeeMasterModel getEmployeeDetails(long empId){
		EmployeeMasterDomain employeeMasterDomain =  employeeMasterDao.getEmployeeDetails(empId);
		if(null != employeeMasterDomain){
			return convertDomainToModel(employeeMasterDomain);	
		}
		return null;
	}
	
	@Override
	public EmployeeMasterModel getEmployeeDetailsByEmailId(String empEmailId){
		EmployeeMasterDomain employeeMasterDomain =  employeeMasterDao.getEmployeeByEmail(empEmailId);
		if(null != employeeMasterDomain){
			return convertDomainToModel(employeeMasterDomain);	
		}
		return null;
	}
	
	
	@Override
	public boolean getEmployeeEmail(long empId,String email){
		EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeByEmail(email);
		if(employeeMasterDomain!=null){
			if(employeeMasterDomain.getNumId()==empId){
				return true;
			}
			else if(employeeMasterDomain.getNumId()==0){
				return false;
			}
			else{
				return false;
			}
			
		}
		else{
		return true;
		}
		
	}
	
	public EmployeeMasterModel convertDomainToModel(EmployeeMasterDomain employeeMasterDomain){
		EmployeeMasterModel employeeMasterModel = new EmployeeMasterModel();
				employeeMasterModel.setNumId(employeeMasterDomain.getNumId());
				employeeMasterModel.setEmployeeName(employeeMasterDomain.getEmployeeName());
				employeeMasterModel.setOfficeEmail(employeeMasterDomain.getOfficeEmail());
				employeeMasterModel.setAlternateEmail(employeeMasterDomain.getAlternateEmail());
				employeeMasterModel.setMobileNumber(employeeMasterDomain.getMobileNumber());
				employeeMasterModel.setContactNumber(employeeMasterDomain.getContactNumber());
				employeeMasterModel.setDateOfBirth(DateUtils.dateToString(employeeMasterDomain.getDateOfBirth()));
				employeeMasterModel.setDateOfJoining(DateUtils.dateToString(employeeMasterDomain.getDateOfJoining()));
				employeeMasterModel.setGender(employeeMasterDomain.getGender());
		/*-------------------  Handle the Null Pointer Exception for Designation Field Data [03-08-2023] ------------------------*/
				if (employeeMasterDomain != null && employeeMasterDomain.getDesignationMaster() != null) {
				    long designationid = employeeMasterDomain.getDesignationMaster().getNumId();
				    if (designationid >0) {
				        employeeMasterModel.setDesignation(designationid);
				    }
				} 
				//employeeMasterModel.setDesignation(employeeMasterDomain.getDesignationMaster().getNumId());
		/*------------------------------------------------------------------------------------------------------------------------*/
		/*-------------------  Handle the Null Pointer Exception for Group Field Data [03-08-2023] ------------------------*/
				if (employeeMasterDomain != null && employeeMasterDomain.getGroupMasterDomain() != null) {
				    long groupid = employeeMasterDomain.getGroupMasterDomain().getNumId();
				    if (groupid >0) {
				        employeeMasterModel.setGroupId(groupid);
				    }
				} 
				//employeeMasterModel.setGroupId(employeeMasterDomain.getGroupMasterDomain().getNumId());
		/*------------------------------------------------------------------------------------------------------------------------*/
				if(employeeMasterDomain.getPostTrackerMaster()!=null){
				employeeMasterModel.setPostId(employeeMasterDomain.getPostTrackerMaster().getNumId());		
				}
				List<EmployeeContractDetailDomain> employeeContractDetailDomainList=employeeContractDetailDao.getContractDataByEmpId(employeeMasterModel.getNumId());
				if(null != employeeContractDetailDomainList && employeeContractDetailDomainList.size()>0){				
					EmployeeContractDetailDomain employeeContractDetailDomain=employeeContractDetailDomainList.get(0);
					employeeMasterModel.setDtContractStartDate(DateUtils.dateToString(employeeContractDetailDomain.getStartDate()));
					employeeMasterModel.setDtContractEndDate(DateUtils.dateToString(employeeContractDetailDomain.getEndDate()));				
				}
				employeeMasterModel.setOrganisationId(employeeMasterDomain.getOrganisationid());
				employeeMasterModel.setEmployeeTypeId(employeeMasterDomain.getEmpTypeMasterDomain().getNumId());
				
				employeeMasterModel.setCategory(employeeMasterDomain.getCategory());
				employeeMasterModel.setEmploymentStatus(employeeMasterDomain.getEmploymentStatus());
				if(null != employeeMasterDomain.getDateOfResignation() && !employeeMasterDomain.getDateOfResignation().equals("")){					
						employeeMasterModel.setDateOfResignation(DateUtils.dateToString(employeeMasterDomain.getDateOfResignation()));
				}
				if(null != employeeMasterDomain.getDateOfRelease() && !employeeMasterDomain.getDateOfRelease().equals("")){					
					employeeMasterModel.setDateOfRelease(DateUtils.dateToString(employeeMasterDomain.getDateOfRelease()));
				}
				Collection<Role> employeeRoles= employeeMasterDomain.getRoles();
				final List<Long> collection = new ArrayList<Long>();
				for (final Role role : employeeRoles) {			
					collection.add(role.getId());
				}
				employeeMasterModel.setRoles(collection);
				if(employeeMasterDomain.getNumIsValid() ==1){
					employeeMasterModel.setValid(true);
				}else{
					employeeMasterModel.setValid(false);
				}
				
				if(null != employeeMasterDomain.getDesignationMaster()){
					DesignationMasterDomain designationMasterDomain = employeeMasterDomain.getDesignationMaster();
					employeeMasterModel.setDesignation(designationMasterDomain.getNumId());
					employeeMasterModel.setStrDesignation(designationMasterDomain.getDesignationName());
				}
			employeeMasterModel.setThirdPartyId(employeeMasterDomain.getThirdPartyMasterDomain().getNumId());
			employeeMasterModel.setNumDeputedAt(employeeMasterDomain.getNumDeputedAt());
			
			return employeeMasterModel;
	}
	
	@Override
	public String checkDuplicateEmployeeEmail(EmployeeMasterModel employeeMasterModel){
		String result = "";
		EmployeeMasterDomain employeeMasterDomain = employeeMasterDao.getOfficeEmailByName(employeeMasterModel.getOfficeEmail());
		 if(null == employeeMasterDomain){
			return null; 
		 }else if(employeeMasterModel.getOfficeEmail() != null){
			 if(employeeMasterDomain.getNumId() == employeeMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Office Email with same name already exist with Id "+employeeMasterDomain.getNumId();
			 }
		 }
		 
		return result;
	}
	
	@PreAuthorize("hasAuthority('WRITE_EMP_ROLE_MAP_MST')")
	public long mergeRoleInEmployeeMaster(EmployeeMasterModel employeeMasterModel){
		
		EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeDetails(employeeMasterModel.getEmployeeId());
		//employeeMasterDomain.setNumId(employeeMasterModel.getEmployeeId());
		employeeMasterDomain.setRoles(roleDao.getAllActiveRoleDomainId(employeeMasterModel.getRoles()));
		return employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
	}
	
	@Override
	public JSONArray getEmployeeCountByEmployementType(){
		JSONArray resultArray = new JSONArray();		
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByEmployementType();
	
		JSONArray tempArray = new JSONArray();
		tempArray.put("Employement Type");
		tempArray.put("No of Employees");
		resultArray.put(tempArray);
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONArray innerArray = new JSONArray();	
			innerArray.put(obj[0]);
			innerArray.put(obj[2]);
			resultArray.put(innerArray);
			
		}	
		return resultArray;
	}
	
	//added by devesh on 9/6/23 for deputed graph
	@Override
	public JSONArray getdeputedwiseEmployeeCount(){
		JSONArray resultArray = new JSONArray();		
		List<Object[]> dataList = employeeMasterDao.getdeputedwiseEmployeeCount();
	
		JSONArray tempArray = new JSONArray();
		tempArray.put("Deputed at");
		tempArray.put("Count");
		resultArray.put(tempArray);
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			//System.out.println("print.."+obj[0]);
			//System.out.println("print.."+obj[1]);
			JSONArray innerArray = new JSONArray();
			if(obj[0].equals(1))innerArray.put("CDAC");
			else if(obj[0].equals(2))innerArray.put("Client");
			else innerArray.put("NULL");
			//innerArray.put(obj[0]);
			innerArray.put(obj[1]);
			resultArray.put(innerArray);
			
		}	
		return resultArray;
	}
	//End
	
	@Override
	public  Map<String,List> getEmployeeCountByGroupandDesignationTechnical(){
		List<String> designationNamesTechnical = employeeMasterDao.getDesignationsOfActiveEmployees("T");
		
	
		 Map<String,Map<String,Long>> dataMap = new HashMap<String, Map<String,Long>>();
		 Map<String,List> finalMap = new LinkedHashMap<String, List>();
		 finalMap.put("Technical Group", designationNamesTechnical);
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByGroupandDesignationTechnical();	
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			String groupName = (String) obj[0];
			String designationName= (String) obj[1];
			long count = (long) obj[2];
			
			/*if ("Executive Director".equals(designationName)) {
	            // Skip "Executive Director" and its corresponding values
	            continue;
	        }*///Added to remove Executive Director designation column from table on 2/8/23 by devesh
			
			if(dataMap.containsKey(groupName)){
				Map<String, Long> map = dataMap.get(groupName);
				map.put(designationName, count);
			}else{
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(designationName, count);
				dataMap.put(groupName, map);				
			}
		}
		//Added to remove Executive Director designation column from table on 2/8/23 by devesh
		/*designationNamesTechnical.removeIf(designation -> "Executive Director".equals(designation));*/
// To set 0 count of employee in a group  w.r.t to a designation if employee that designation does not exist in that group
		for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {      
			   if(entry.getValue().size() != designationNamesTechnical.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String designationName : designationNamesTechnical){	 
					  
					   if(!tempMap.containsKey(designationName)){
						   tempMap.put(designationName,  (long) 0);
					   }
					   dataMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		
//to calculate total employee on same designation
		
				Map<String, Long> summap = new HashMap<String, Long>();
				
				for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  { 
					
					   
						 
						  Map<String,Long> tempMap = entry.getValue();
						 
						   for(String designationName : designationNamesTechnical){	 
							  
							   if(!summap.containsKey(designationName)){
								   Long val=tempMap.get(designationName);
								   summap.put(designationName,val);
							   }
							   else
							   {   
								 
							  long value= tempMap.get(designationName)+summap.get(designationName);
							  summap.replace(designationName,value);
								  
							   }
							 }
						   
					   }
				
				dataMap.put("Total", summap);

	// to calculate total employee in a group(department)
		for(Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {
					List<Long> tempList = new ArrayList<Long>();
				   Map<String,Long> tempMap = entry.getValue();
				   
				   for(String designationName : designationNamesTechnical){	
					   Long value = tempMap.get(designationName);
					   if(null != value){
						   tempList.add(tempMap.get(designationName));
						}else{
							   tempList.add((long) 0);				   

					   }
				   }
				   long totalEmployessInGroup = tempList.stream().reduce((long) 0, (a, b) -> a + b);
				   
				   tempList.add(totalEmployessInGroup);
				  finalMap.put(entry.getKey(), tempList);
			}	
		finalMap.get("Technical Group").add("Total");
		//Added by devesh on 20/6/23 
		List totalEntry = finalMap.remove("Total"); // Remove the "Total" entry from the map
		finalMap.put("Total", totalEntry); // Add the "Total" entry at the end of the map
		//End
		return finalMap;
	}
	
	public  Map<String,List> getEmployeeCountByGroupandDesignationSupport(){
		//List<String> designationNames = designationMasterService.getActiveDesignationName();
		
		List<String> designationNamesSupport = employeeMasterDao.getDesignationsOfActiveEmployees("S");
	
		 Map<String,Map<String,Long>> dataMap = new HashMap<String, Map<String,Long>>();
		 Map<String,List> finalMap = new LinkedHashMap<String, List>();
		 finalMap.put("Support Group", designationNamesSupport);
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByGroupandDesignationSupport();	
				
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			String groupName = (String) obj[0];
			String designationName= (String) obj[1];
			long count = (long) obj[2];
			
			/*if ("Executive Director".equals(designationName)) {
	            // Skip "Executive Director" and its corresponding values
	            continue;
	        }*///Added to remove Executive Director designation column from table on 2/8/23 by devesh
			
			if(dataMap.containsKey(groupName)){
				Map<String, Long> map = dataMap.get(groupName);
				
				map.put(designationName, count);
			}else{
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(designationName, count);
				dataMap.put(groupName, map);				
			}
			
		}
		//Added to remove Executive Director designation column from table on 2/8/23 by devesh
		/*designationNamesSupport.removeIf(designation -> "Executive Director".equals(designation));*/
		// To set 0 count of employee in a group  w.r.t to a designation if employee that designation does not exist in that group
		for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {      
			   if(entry.getValue().size() != designationNamesSupport.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String designationName : designationNamesSupport){	 
					  
					   if(!tempMap.containsKey(designationName)){
						   tempMap.put(designationName,  (long) 0);
					   }
					   dataMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		//to calculate total employee on same designation
		
		Map<String, Long> summap = new HashMap<String, Long>();
		
		for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  { 
			
			   
				 
				  Map<String,Long> tempMap = entry.getValue();
				 
				   for(String designationName : designationNamesSupport){	 
					  
					   if(!summap.containsKey(designationName)){
						   Long val=tempMap.get(designationName);
						   summap.put(designationName,val);
					   }
					   else
					   {   
						 
					  long value= tempMap.get(designationName)+summap.get(designationName);
					  summap.replace(designationName,value);
						  
					   }
					 }
				   
			   }
		
		dataMap.put("Total", summap);
  
		// to calculate total employee in a group(department)
		for(Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {
					List<Long> tempList = new ArrayList<Long>();
				   Map<String,Long> tempMap = entry.getValue();
				   
				   for(String designationName : designationNamesSupport){	
					   Long value = tempMap.get(designationName);
					   if(null != value){
						   tempList.add(tempMap.get(designationName));
						}else{
							   tempList.add((long) 0);				   

					   }
				   }
				   long totalEmployessInGroup = tempList.stream().reduce((long) 0, (a, b) -> a + b);
				   
				   tempList.add(totalEmployessInGroup);
				  finalMap.put(entry.getKey(), tempList);
			}	
		finalMap.get("Support Group").add("Total");
		//Added by devesh on 20/6/23 
		List totalEntry = finalMap.remove("Total"); // Remove the "Total" entry from the map
	    finalMap.put("Total", totalEntry); // Add the "Total" entry at the end of the map
	    //End
		return finalMap;
	}
	
	public  Map<String,List> getEmployeeCountByGroupandDesignation(){
		List<String> designationNames = designationMasterService.getActiveDesignationName();
		
		 Map<String,Map<String,Long>> dataMap = new HashMap<String, Map<String,Long>>();
		 Map<String,List> finalMap = new LinkedHashMap<String, List>();
		 finalMap.put("Group", designationNames);
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByGroupandDesignation();
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			String groupName = (String) obj[0];
			String designationName= (String) obj[1];
			long count = (long) obj[2];
			
			if(dataMap.containsKey(groupName)){
				Map<String, Long> map = dataMap.get(groupName);
				map.put(designationName, count);
			}else{
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(designationName, count);
				dataMap.put(groupName, map);				
			}
		}
		
		for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {      
			   if(entry.getValue().size() != designationNames.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String designationName : designationNames){	 
					   
					   if(!tempMap.containsKey(designationName)){
						   tempMap.put(designationName,  (long) 0);
					   }
					   dataMap.put(entry.getKey(), tempMap);
				   }				   
			   }
		   }
		
		for(Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {
					List<Long> tempList = new ArrayList<Long>();
				   Map<String,Long> tempMap = entry.getValue();
				   
				   for(String designationName : designationNames){	
					   Long value = tempMap.get(designationName);
					   if(null != value){
						   tempList.add(tempMap.get(designationName));
						}else{
							   tempList.add((long) 0);				   

					   }
				   }
				   long totalEmployessInGroup = tempList.stream().reduce((long) 0, (a, b) -> a + b);
				   
				   tempList.add(totalEmployessInGroup);
				  finalMap.put(entry.getKey(), tempList);
			}
			
		finalMap.get("Group").add("Total");
		
		//added by devesh on 20/6/23 to remove designation which has count zero for each group
		Map<String, Long> designationSumMap = new HashMap<>();

		for (Map<String, Long> groupDataMap : dataMap.values()) {
		    for (Map.Entry<String, Long> entry : groupDataMap.entrySet()) {
		        String designationName = entry.getKey();
		        Long count = entry.getValue();

		        if (designationSumMap.containsKey(designationName)) {
		            Long sum = designationSumMap.get(designationName) + count;
		            designationSumMap.put(designationName, sum);
		        } else {
		            designationSumMap.put(designationName, count);
		        }
		    }
		}
		//System.out.println("Sum of Employee Counts for each Designation:");
		for (Map.Entry<String, Long> entry : designationSumMap.entrySet()) {
		    String designationName = entry.getKey();
		    Long sum = entry.getValue();
		    //System.out.println(designationName + ": " + sum);

		    if (sum == 0) {
		        List<String> groupCounts1 = finalMap.get("Group");
		        int index = groupCounts1.indexOf(designationName);
		        if (index != -1) {
		            for (List<String> counts : finalMap.values()) {
		                if (counts.size() > index) {
		                    counts.remove(index);
		                }
		            }
		        }
		    }
		}
		finalMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
		// End of removal for designation column with zero count for each group
		
		System.out.println("\nfinalmap.."+finalMap+"\n");//added on 19/6/23
		return finalMap;
	}


	@Override
	public List<EmployeeMasterModel> getThirdPartyById(long employeeTypeId, EmployeeMasterModel employeeMasterModel) {
		List<EmployeeMasterDomain> list = employeeMasterDao.getThirdPartyById(employeeTypeId);
		List<EmployeeMasterModel> empList = convertDomainListToModelListForThirdParty(list);
		return empList;
		
	}
	
	private List<EmployeeMasterModel> convertDomainListToModelListForThirdParty(List<EmployeeMasterDomain> list) {
		
		List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();
				
		for(int i=0;i<list.size();i++){	
	
		EmployeeMasterModel employeeMasterModel =new EmployeeMasterModel();
		EmployeeMasterDomain employeeMasterDomain = list.get(i); 
		employeeMasterModel.setAlternateEmail(employeeMasterDomain.getAlternateEmail());
		employeeMasterModel.setNumId(employeeMasterDomain.getNumId());
		employeeMasterModel.setEmployeeName(employeeMasterDomain.getEmployeeName());
		employeeMasterModel.setOfficeEmail(employeeMasterDomain.getOfficeEmail());
	/*-------------------  Handle the Null Pointer Exception for Designation Field Data [03-08-2023] ------------------------*/
		if (employeeMasterDomain != null && employeeMasterDomain.getDesignationMaster() != null) {
		    String designationName = employeeMasterDomain.getDesignationMaster().getDesignationName();
		    if (designationName != null) {
		        employeeMasterModel.setStrDesignation(designationName);
		    }else{
		    	employeeMasterModel.setStrDesignation("");
		    }
		}else{
	    	employeeMasterModel.setStrDesignation("");
	    }
		//employeeMasterModel.setStrDesignation(employeeMasterDomain.getDesignationMaster().getDesignationName());
	/*----------------------------------------------------------------------------------------------------------------------*/
	/*-------------------  Handle the Null Pointer Exception for Group Field Data [03-08-2023] ----------------------------*/
		if (employeeMasterDomain != null && employeeMasterDomain.getGroupMasterDomain() != null) {
		    String groupName = employeeMasterDomain.getGroupMasterDomain().getGroupName();
		    if (groupName != null) {
		        employeeMasterModel.setGroupName(groupName);
		    }else{
		    	employeeMasterModel.setGroupName("");	
		    }
		}else{
	    	employeeMasterModel.setGroupName("");
	    }
		//employeeMasterModel.setGroupName(employeeMasterDomain.getGroupMasterDomain().getGroupName());
	/*----------------------------------------------------------------------------------------------------------------------*/	
		ThirdPartyMasterDomain thirdPartyMasterDomain = employeeMasterDomain.getThirdPartyMasterDomain();
		if(null != thirdPartyMasterDomain){
			employeeMasterModel.setThirdPartyId(thirdPartyMasterDomain.getNumId());
			employeeMasterModel.setThirdPartyName(thirdPartyMasterDomain.getStrAgencyName());
		}
		
		try{
			if(null != employeeMasterDomain.getDateOfRelease()){
				employeeMasterModel.setDateOfRelease(DateUtils.dateToString(employeeMasterDomain.getDateOfRelease()));
			}		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(null != employeeMasterDomain.getDateOfResignation()){
				employeeMasterModel.setDateOfResignation(DateUtils.dateToString(employeeMasterDomain.getDateOfResignation()));
			}	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
			dataList.add(employeeMasterModel);		
		}
		
		return dataList;

	}


	public long saveUpdateEmployeeMasterProfile(
			EmployeeMasterModel employeeMasterModel) {
		EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeDetails(employeeMasterModel.getNumId());
		employeeMasterDomain.setAlternateEmail(employeeMasterModel.getAlternateEmail());
		employeeMasterDomain.setMobileNumber(employeeMasterModel.getMobileNumber());
		employeeMasterDomain.setContactNumber(employeeMasterModel.getContactNumber());
		return employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);

	}

	@Override
	public List<String> getdistinctEmpGender() {
		return employeeMasterDao.getdistinctEmpGender();
	}

	
	public boolean sendMail(EmployeeMasterModel model) {
		boolean flag=false;
		EmployeeMasterDomain registration=new EmployeeMasterDomain();
		registration.setOfficeEmail(model.getOfficeEmail());;
		try
		{
		 flag=sendMailtoUser(registration);
		
		}
		catch(Exception e)
		{
			//
		}
		 return flag;
	}

	private boolean sendMailtoUser(EmployeeMasterDomain registration) {
		boolean flag = false;
		try {

			final EmployeeMasterDomain user = findByEmail(registration.getOfficeEmail());
			String password="";
			if(user.getDateOfBirth()!=null){
				password=user.getDateOfBirth()+"";
				String dtformat = "yyyy-MM-dd";
				SimpleDateFormat sdf = new SimpleDateFormat(dtformat);
				try{
					Date dt = sdf.parse(password);
					SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
					String defaultPassword = sdf2.format(dt);		

					ForgotPassword forgotPassword=new ForgotPassword();
					forgotPassword.setStrUserName(registration.getOfficeEmail());
					forgotPassword.setPassword(passwordEncoder.encode(defaultPassword));
					Date date=new Date();
					forgotPassword.setDtTrDate(date);
					forgotPassword.setNumIsValid(1);


					String strSubject="PMS Password Reset Information.";
					String strMsgText="Dear User,<br><br> Your password has been reset.<br>Kindly login wth below credentials.<br><br> ";

					strMsgText+="<b>Username:"+registration.getOfficeEmail()+"</b><br>";
					strMsgText+="<b>Password:Date of birth (DDMMYYYY)</b><br>";

					String mailContent=strSubject+strMsgText;
					forgotPassword.setMailContent(mailContent);
					globalDao.mergeForgotPassword(forgotPassword);

					user.setPassword(passwordEncoder.encode(defaultPassword));
					employeeMasterDao.saveUpdateEmployeeMaster(user);
					SendMail mailobj=new SendMail();
					/*-------------------- For Password Reset Mail Send Functionality -------------------------------------------------*/
					SendMail smail = new SendMail();
					beanFactory.autowireBean(smail);
					beanFactory.autowireBean(mailobj);
					smail.TransferToMailServer(registration.getOfficeEmail(), strSubject,strMsgText);
					System.out.println("Reset Email Send ........");
					flag=true;
					/*---------------------------------------------------------------------*/
					//flag = mailobj.TransferToMailServerBoolean(registration.getOfficeEmail(), strSubject, strMsgText);
				}catch(Exception e){}

			}

		}catch(Exception e){

		}
		return flag;
	}

	@Override
	public JSONArray getEmployeeCountByGroup() {
		JSONArray finalArray = new JSONArray();
		List<String> nameColorList = groupMasterService.getdistinctGroupNamesWithColor();
		List<String> groupList = new ArrayList<String>();
		JSONArray groupColorArray = new JSONArray();
				
		for(int i =0;i<nameColorList.size();i++){
			String [] temp = nameColorList.get(i).split("=");		
			groupColorArray.put(temp[0]);
			groupList.add(temp[1]);
		}	
		


		
		finalArray.put(groupColorArray);
		
		JSONArray headerArray = new JSONArray();
		headerArray.put("Group");
		headerArray.put("Count");
		finalArray.put(headerArray);
		
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByGroup();	
		
		for(String group : groupList){
			JSONArray internalArray = new JSONArray();
			Object[] obj = filterDataInList(dataList,group);
			//int index =groupList.indexOf(group);
			if(null != obj){
				String groupName=""+obj[1];
				long count =  (long) obj[0];
				internalArray.put(groupName);
				internalArray.put(count);
			
				
			}else{
				internalArray.put(group);
				internalArray.put(0);
					
			}
					
			finalArray.put(internalArray);
		}	
	
		//System.out.println(finalArray);
		return finalArray;
		
		
	}
	
	
	
	public Object[] filterDataInList(List<Object[]> list,String groupName){
		Object[] obj = list.stream()
	                .filter(p -> {
	                    if (groupName.equals(""+p[1])) {
	                        return true;
	                    }
	                    return false;
	                }).findAny()
	                .orElse(null);
		
		return obj;
	}

	@Override
	public JSONArray getEmployeeCountByCategory() {
		JSONArray resultArray = new JSONArray();		
		List<Object[]> dataList = employeeMasterDao.getEmployeeCountByCategory();
	
		JSONArray tempArray = new JSONArray();
		tempArray.put("Employees Category");
		tempArray.put("No of Employees");
		resultArray.put(tempArray);
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONArray innerArray = new JSONArray();	
			innerArray.put(obj[0]);
			innerArray.put(obj[1]);
			resultArray.put(innerArray);
		}	
		return resultArray;
	}

	@Override
	public JSONArray getYearWiseEmployeeCount() {
		JSONArray finalArray = new JSONArray();
		JSONArray headerArray = new JSONArray();
		headerArray.put("Financial Year");
		headerArray.put("Total New Joining");
		headerArray.put("Total Resignations");
		headerArray.put("Average");
		finalArray.put(headerArray);
		Map<String,List<Long>> finalMap = employeeMasterDao.getYearWiseEmployeeCount();	
		
		for (Map.Entry<String,List<Long>> entry : finalMap.entrySet())  {      
			  JSONArray tempArray = new JSONArray(); 
			 // System.out.println(entry.getKey());
			  tempArray.put(entry.getKey());
			  List<Long> listValue = entry.getValue();
			 // System.out.println(listValue.get(0));
			  long joining = listValue.get(0);
			  long resignation = listValue.get(1);
			 float avg = (joining+resignation)/2;
			  tempArray.put(joining);
			  tempArray.put(resignation);
			  tempArray.put(avg);
			  finalArray.put(tempArray);
	}
		//System.out.println(finalArray);
	return finalArray;
	
}

	@Override
	public List<EmployeeMasterModel> getEmployeesByGroupByEmployementType() {
		List<Object[]> list = employeeMasterDao.getEmployeesByGroupByEmployementType();
		List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
		String employeeName = (String) obj[0];
		String groupName = (String) obj[1];
		String employmentType = (String) obj[2];
		Date date = (Date) obj[3];
		String dateOfJoining = DateUtils.dateToString(date);
		String designation=(String) obj[5];
		int deputed=(int) obj[6];//added by devesh on 14/6/23 for deputed column in hr
		EmployeeMasterModel employeeMasterModel = new EmployeeMasterModel();
		employeeMasterModel.setEmployeeName(employeeName);
		employeeMasterModel.setGroupName(groupName);
		employeeMasterModel.setEmployeeTypeName(employmentType);
		employeeMasterModel.setDateOfJoining(dateOfJoining);
		employeeMasterModel.setStrDesignation(designation);
		employeeMasterModel.setNumDeputedAt(deputed);//added
		dataList.add(employeeMasterModel);
		}
		return dataList;
	}

	@Override
	public JSONObject getEmployeeBasicDetails(String encEmployeeId){
		String strEmployeeId = encryptionService.dcrypt(encEmployeeId);
		long empId = Long.parseLong(strEmployeeId);
		EmployeeMasterDomain employeeDomain =  employeeMasterDao.getEmployeeDetails(empId);
		JSONObject object = new JSONObject();
		if(null != employeeDomain){
			object.append("result", "Success");
			object.append("employeeName", employeeDomain.getEmployeeName());
			object.append("empId", employeeDomain.getNumId());
			object.append("employeeDesignation",employeeDomain.getDesignationMaster().getDesignationName());
			object.append("employeeEmail", employeeDomain.getOfficeEmail());
			object.append("groupName",employeeDomain.getGroupMasterDomain().getGroupName());
			object.append("joiningDate", DateUtils.dateToString(employeeDomain.getDateOfJoining()));
			object.append("mobileNo",employeeDomain.getMobileNumber());
		}else{
			object.append("result", "Fail");
		}		
		return object;
	}

	
	public List<EmployeeMasterModel> getEmpbyDesignationForGroup(String groupName,
			String strDesignation) {
		List<EmployeeMasterDomain> list = employeeMasterDao.getEmpbyDesignationForGroup(groupName,strDesignation);
		List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();
		for (EmployeeMasterDomain domain : list) {
			EmployeeMasterModel model=new  EmployeeMasterModel();
			model.setEmployeeId(domain.getNumId());
			model.setEmployeeName(domain.getEmployeeName());
			dataList.add(model);
		}
		return dataList;
	}

	@Override
	public List<Object[]> getAllActiveEmployeeWithCount() {		
		List<Object[]> list = employeeMasterDao.getAllActiveEmployeeWithCount();
		return list;
	}
	
	@Override
	public long getEmployeeByPostId(long id){
		return employeeMasterDao.getCountByPostId(id);
	}
	
	@Override
	public List<String> saveEmployeeDetails(EmployeeMasterModel employeeMasterModel) {
		List<String> responseList = new ArrayList<String>();
		List<String> empIdList = new ArrayList<String>();
		List<Integer> tpRowId = new ArrayList<Integer>();
		
		if (!employeeMasterModel.getEmployeeDocumentFile().isEmpty()) {
			try {
				InputStream inputStream = employeeMasterModel.getEmployeeDocumentFile().getInputStream();
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

				Sheet sheet = workbook.getSheetAt(0);
				List<EmployeeMasterDomain> list = new ArrayList<EmployeeMasterDomain>();
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserInfo currentUser = (UserInfo) auth.getPrincipal();
				List<EmployeeMasterDomain> allEmployees = employeeMasterDao.getAllEmployeeMasterDomain();
				List<DesignationMasterModel> allDesignation = designationMasterService.getAllActiveDesignationMasterDomain();
				List<GroupMasterModel> allGroup = groupMasterService.getAllActiveGroupMasterDomain();
				List<EmpTypeMasterModel> allEmploymentType = empTypeMasterService.getAllActiveEmpTypeMasterDomain();
				List<ThirdPartyMasterDomain> allThirdPartyAgency = thirdPartyMasterService.getThirdPartyMasterDomain();
				int registerData = employeeMasterModel.getOptionValue();

				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					int j = i + 1;
					String strEmployeeId = "0";
					String deputedAt = "0";
					int numdeputedat = 0;
					
					try {
						Row row = sheet.getRow(i);
						Cell cell = row.getCell(0);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						strEmployeeId = cell.getStringCellValue().trim();
						
						Long empId = (strEmployeeId.equals("")) ? 0L : Long.parseLong(strEmployeeId);
														
						String empName = row.getCell(1).getStringCellValue().trim();

						Cell cell2 = row.getCell(2);
						cell2.setCellType(Cell.CELL_TYPE_STRING);
						String dob =  cell2.getStringCellValue().trim();

						Cell cell3 = row.getCell(3);
						cell3.setCellType(Cell.CELL_TYPE_STRING);
						String mobileNo = cell3.getStringCellValue().trim();

						String email = row.getCell(4).getStringCellValue().trim();

						Cell cell5 = row.getCell(5);
						cell5.setCellType(Cell.CELL_TYPE_STRING);
						String doj = cell5.getStringCellValue().trim();

						String designation = row.getCell(6).getStringCellValue().trim();

						String category = row.getCell(7).getStringCellValue().trim();

						Cell cell1=row.getCell(8, Row.RETURN_BLANK_AS_NULL);					
							cell1.setCellType(Cell.CELL_TYPE_STRING);
							String	group = cell1.getStringCellValue().trim();	
						

						String employmentType = row.getCell(9).getStringCellValue().trim();

						String employmentStatus = row.getCell(10).getStringCellValue().trim();

						String gender = row.getCell(11).getStringCellValue().trim();

						//String contractStartDate = row.getCell(12).getStringCellValue().trim();
						Cell cell6=row.getCell(12);;					
						cell6.setCellType(Cell.CELL_TYPE_STRING);
						String	contractStartDate = cell6.getStringCellValue().trim();	

						//String contractEndDate = row.getCell(13).getStringCellValue().trim();
						Cell cell7=row.getCell(13);;					
						cell7.setCellType(Cell.CELL_TYPE_STRING);
						String contractEndDate = cell7.getStringCellValue().trim();	

						/*----------------------------------------------------*/
						if(registerData!=3){
							Cell cell8 = row.getCell(14);
							cell8.setCellType(Cell.CELL_TYPE_STRING);
							deputedAt = cell8.getStringCellValue().trim();
						}else{
							Cell cell8 = row.getCell(15);
							cell8.setCellType(Cell.CELL_TYPE_STRING);
							deputedAt = cell8.getStringCellValue().trim();
						}
						/*
						if(deputedAt.equalsIgnoreCase("cdac")) {
							numdeputedat = 1;
						}
						if(deputedAt.equalsIgnoreCase("at client")) {
							numdeputedat = 2;
						}
						*/
						int deputedatValue;
						try{
							deputedatValue=Integer.parseInt(deputedAt);
						}catch(Exception e){
							deputedatValue=0;
						}
						if ((deputedatValue>0) && (deputedatValue<3)) {
							numdeputedat = deputedatValue;
						} else {
							tpRowId.add(i);
							empIdList.add(strEmployeeId);
							responseList.add("Record for Row no " + j+ " does not saved because Deputed At value is invalid !");
						}
						/*----------------------------------------------------*/
						EmployeeMasterDomain employeeMaster = allEmployees.stream()
								.filter(x -> empId == x.getNumId() || email.equals(x.getOfficeEmail())).findAny()
								.orElse(null);

						DesignationMasterModel designationMasterModel = allDesignation.stream()
								.filter(x -> designation.equalsIgnoreCase(x.getStrDesignationName())).findAny()
								.orElse(null);

						GroupMasterModel groupMasterModel = allGroup.stream()
								.filter(x -> group.equalsIgnoreCase(x.getGroupName())).findAny().orElse(null);

						EmpTypeMasterModel empTypeMasterModel = allEmploymentType.stream()
								.filter(x -> employmentType.equalsIgnoreCase(x.getStrEmpTypeName())).findAny().orElse(null);

						
						DesignationMasterDomain designationMaster = new DesignationMasterDomain();

						if (null != designationMasterModel) {
							designationMaster.setNumId(designationMasterModel.getNumId());

						} else {
							tpRowId.add(i);
							empIdList.add(strEmployeeId);
							responseList.add("Record for Row no " + j
									+ " does not saved because designation does not exist");
						}
						
						GroupMasterDomain groupMasterDomain = new GroupMasterDomain();

						if (null != groupMasterModel) {
							groupMasterDomain.setNumId(groupMasterModel.getNumId());
							
						} else {
							tpRowId.add(i);
							empIdList.add(strEmployeeId);
							responseList.add("Record for Row no " + j
									+ " does not saved because Group does not exist");
						}
						
						EmpTypeMasterDomain empTypeMasterDomain = new EmpTypeMasterDomain();

						if (null != empTypeMasterModel) {
							empTypeMasterDomain.setNumId(empTypeMasterModel.getNumId());
						} else {
							tpRowId.add(i);
							empIdList.add(strEmployeeId);
							responseList.add("Record for Row no " + i
									+ " does not saved because employment type does not exist");
						}
						
						// Register new employee
						if(registerData != 3){
							/*  ======================================= */
								//CC / GBC/Regular Employee Upload Block
							/*  ======================================= */
							ThirdPartyMasterDomain thirdPartyMasterDomain=new ThirdPartyMasterDomain();
							thirdPartyMasterDomain.setNumId(0);
							
						if (employeeMaster == null &&  registerData==1) {							
							if (!empIdList.contains(strEmployeeId)) {								
								
								EmployeeMasterDomain employeeMasterDomain = new EmployeeMasterDomain();
								employeeMasterDomain.setNumId(empId);								
								employeeMasterDomain.setDesignationMaster(designationMaster);
								employeeMasterDomain.setGroupMasterDomain(groupMasterDomain);								
								employeeMasterDomain.setOrganisationid(groupMasterModel.getOrganisationId());
								employeeMasterDomain.setEmpTypeMasterDomain(empTypeMasterDomain);
								employeeMasterDomain.setEmployeeName(empName);
								employeeMasterDomain.setDateOfBirth(DateUtils.dateStrToDate(dob));
								employeeMasterDomain.setMobileNumber(mobileNo);
								employeeMasterDomain.setOfficeEmail(email);
								employeeMasterDomain.setDateOfJoining(DateUtils.dateStrToDate(doj));
								employeeMasterDomain.setGender(gender);
								employeeMasterDomain.setCategory(category);
								employeeMasterDomain.setEmploymentStatus(employmentStatus);
								

								List<EmployeeContractDetailDomain> employeeContractDetaillist = new ArrayList<EmployeeContractDetailDomain>();
								EmployeeContractDetailDomain employeeContractDetailDomain = new EmployeeContractDetailDomain();
								employeeContractDetailDomain.setStartDate(DateUtils.dateStrToDate(contractStartDate));
								employeeContractDetailDomain.setEndDate(DateUtils.dateStrToDate(contractEndDate));
								employeeContractDetailDomain.setNumTrUserId(currentUser.getEmployeeId());
								employeeContractDetailDomain.setNumIsValid(1);
								employeeContractDetailDomain.setDtTrDate(new Date());
								employeeContractDetailDomain.setStrRemarks("New Record");
								employeeContractDetailDomain.setEmployeeMasterDomain(employeeMasterDomain);
								employeeContractDetaillist.add(employeeContractDetailDomain);

								employeeMasterDomain.setEmployeeContractDetailDomain(employeeContractDetaillist);

								String str = dob;
								String dtformat = "dd/MM/yyyy";
								SimpleDateFormat sdf = new SimpleDateFormat(dtformat);
								Date dt = sdf.parse(str);
								SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
								String defaultPassword = sdf2.format(dt);
								employeeMasterDomain.setPassword(passwordEncoder.encode(defaultPassword));
								employeeMasterDomain.setNumIsValid(1);
								employeeMasterDomain.setDtTrDate(new Date());
								employeeMasterDomain.setNumTrUserId(currentUser.getEmployeeId());
								
								
								employeeMasterDomain.setThirdPartyMasterDomain(thirdPartyMasterDomain);
								employeeMasterDomain.setNumDeputedAt(numdeputedat);
								
								list.add(employeeMasterDomain);
							
							}
						
						}else if (registerData == 2){
							
							if(null == employeeMaster){
								responseList.add("Issue at row no. \t" + j + ".Employee with Id "+strEmployeeId +" not registered");
								empIdList.add(strEmployeeId);
							}else{
								if (!empIdList.contains(strEmployeeId)) {									
										employeeMaster.setDesignationMaster(designationMaster);								
										employeeMaster.setGroupMasterDomain(groupMasterDomain);	
										employeeMaster.setEmpTypeMasterDomain(empTypeMasterDomain);										
										employeeMaster.setOrganisationid(groupMasterModel.getOrganisationId());										
										employeeMaster.setEmployeeName(empName);
										employeeMaster.setDateOfBirth(DateUtils.dateStrToDate(dob));
										employeeMaster.setMobileNumber(mobileNo);
										employeeMaster.setOfficeEmail(email);
										employeeMaster.setDateOfJoining(DateUtils.dateStrToDate(doj));
										employeeMaster.setGender(gender);
										employeeMaster.setCategory(category);
										employeeMaster.setEmploymentStatus(employmentStatus);
									
									
									employeeMaster.setNumIsValid(1);
									employeeMaster.setDtTrDate(new Date());
									employeeMaster.setNumTrUserId(currentUser.getEmployeeId());
									
									
									employeeMaster.setThirdPartyMasterDomain(thirdPartyMasterDomain);
									employeeMaster.setNumDeputedAt(numdeputedat);
								list.add(employeeMaster);
							}
							}
						}else {
							responseList.add("Issue at row no. \t" + j + ". Record not updated for employeeId:"
									+ strEmployeeId + "\t because employee already exit");
							empIdList.add(strEmployeeId);
						}
					}else{
						//TP Employee Upload Block
						String agencyName = row.getCell(14).getStringCellValue().trim();
						ThirdPartyMasterDomain thirdPartyMasterDomain= 	 allThirdPartyAgency.stream()
								.filter(x ->  agencyName.equals(x.getStrAgencyName())).findAny()
								.orElse(null);
						
						if(null == thirdPartyMasterDomain){
							responseList.add("Issue at row no. \t" + j + ". Third Party Agency Named "+agencyName+" does Not exist");
							tpRowId.add(i);
						}
						
						if(!tpRowId.contains(i)){				
						
						
						if(null == employeeMaster){
							employeeMaster = new EmployeeMasterDomain();
							employeeMaster.setNumId(employeeMasterDao.getSequence());
							List<EmployeeContractDetailDomain> employeeContractDetaillist = new ArrayList<EmployeeContractDetailDomain>();
							EmployeeContractDetailDomain employeeContractDetailDomain = new EmployeeContractDetailDomain();
							employeeContractDetailDomain.setStartDate(DateUtils.dateStrToDate(contractStartDate));
							employeeContractDetailDomain.setEndDate(DateUtils.dateStrToDate(contractEndDate));
							employeeContractDetailDomain.setNumTrUserId(currentUser.getEmployeeId());
							employeeContractDetailDomain.setNumIsValid(1);
							employeeContractDetailDomain.setDtTrDate(new Date());
							employeeContractDetailDomain.setStrRemarks("New Record");
							employeeContractDetailDomain.setEmployeeMasterDomain(employeeMaster);
							employeeContractDetaillist.add(employeeContractDetailDomain);
							employeeMaster.setEmployeeContractDetailDomain(employeeContractDetaillist);
							
							String str = dob;
							String dtformat = "dd/MM/yyyy";
							SimpleDateFormat sdf = new SimpleDateFormat(dtformat);
							Date dt = sdf.parse(str);
							SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
							String defaultPassword = sdf2.format(dt);
							employeeMaster.setPassword(passwordEncoder.encode(defaultPassword));
						}
						
														
						employeeMaster.setDesignationMaster(designationMaster);
						employeeMaster.setGroupMasterDomain(groupMasterDomain);								
						employeeMaster.setOrganisationid(groupMasterModel.getOrganisationId());
						employeeMaster.setEmpTypeMasterDomain(empTypeMasterDomain);
						employeeMaster.setEmployeeName(empName);
						employeeMaster.setDateOfBirth(DateUtils.dateStrToDate(dob));
						employeeMaster.setMobileNumber(mobileNo);
						employeeMaster.setOfficeEmail(email);
						employeeMaster.setDateOfJoining(DateUtils.dateStrToDate(doj));
						employeeMaster.setGender(gender);
						employeeMaster.setCategory(category);
						employeeMaster.setEmploymentStatus(employmentStatus);
						
							employeeMaster.setNumIsValid(1);
							employeeMaster.setDtTrDate(new Date());
							employeeMaster.setNumTrUserId(currentUser.getEmployeeId());					
						
							employeeMaster.setThirdPartyMasterDomain(thirdPartyMasterDomain); 
							employeeMaster.setNumDeputedAt(numdeputedat);
							list.add(employeeMaster);
						}
					}

					} catch (Exception e1) {
						
						responseList.add("Record not updated for employeeId:" + strEmployeeId
								+ "\t because issue at Row no. \t" + j + " " + e1.getMessage());
						empIdList.add(strEmployeeId);

					}

				}

				for (int i = 0; i < list.size(); i++) {
					EmployeeMasterDomain employeeMasterDomain = list.get(i);
					employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
					responseList.add("Record saved/updated successfully");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return responseList;

	}

	@Override
	public List<EmployeeMasterModel>  getContractDetails(String date) {
		 List<EmployeeMasterModel> list=new ArrayList<EmployeeMasterModel>();
		List<EmployeeContractDetailDomain> employeeContractDetailDomainList=employeeMasterDao.getContractDetails(date);
		for (int i=0;i<employeeContractDetailDomainList.size();i++){
			EmployeeContractDetailDomain domain=employeeContractDetailDomainList.get(i);
			EmployeeMasterModel employeeMasterModel=new EmployeeMasterModel();
			employeeMasterModel.setEmployeeId(domain.getEmployeeMasterDomain().getNumId());
			employeeMasterModel.setEncEmployeeId(encryptionService.encrypt(""+domain.getEmployeeMasterDomain().getNumId()));
			employeeMasterModel.setEmployeeName(domain.getEmployeeMasterDomain().getEmployeeName());
			employeeMasterModel.setGroupName(domain.getEmployeeMasterDomain().getGroupMasterDomain().getGroupName());
			list.add(employeeMasterModel);
		}
		return list;
	}
	 
	@Override
	@PreAuthorize("hasAuthority('RELEASE_EMPLOYEE')")
	public void releaseEmployeeAuthorityCheck(){
		
	}
	
	@Override
	public EmployeeMasterModel getEmployeeBasicDetail(EmployeeMasterModel employeeMasterModel){
		EmployeeMasterDomain employeeMasterDomain = null;
		String dataLoadedBasedOn = "";
		if(null != employeeMasterModel.getSearchEmployeeId() && !employeeMasterModel.getSearchEmployeeId().equals("0")){
			long employeeId = Long.parseLong(employeeMasterModel.getSearchEmployeeId());
			employeeMasterDomain = employeeMasterDao.getEmployeeDetails(employeeId);
			dataLoadedBasedOn = "Employee Id";
		}
		else if((null != employeeMasterModel.getSearchEmployeeEmail()) && (!employeeMasterModel.getSearchEmployeeEmail().equals("")) && (null == employeeMasterDomain)){
			employeeMasterDomain = employeeMasterDao.getEmployeeByEmail(employeeMasterModel.getSearchEmployeeEmail());
			dataLoadedBasedOn = "Email";
		}
		if(null != employeeMasterDomain && employeeMasterDomain.getNumIsValid() == 1){
			EmployeeMasterModel dataModel = convertDomainToBasicDetail(employeeMasterDomain);
			dataModel.setDataLoadedBasedOn(dataLoadedBasedOn);
			
			return dataModel;
		}
		return null;		
	}
	
	public EmployeeMasterModel convertDomainToBasicDetail(EmployeeMasterDomain employeeMasterDomain){
				EmployeeMasterModel employeeMasterModel = new EmployeeMasterModel();
				employeeMasterModel.setNumId(employeeMasterDomain.getNumId());
				employeeMasterModel.setEmployeeName(employeeMasterDomain.getEmployeeName());
				employeeMasterModel.setOfficeEmail(employeeMasterDomain.getOfficeEmail());
				employeeMasterModel.setAlternateEmail(employeeMasterDomain.getAlternateEmail());
				employeeMasterModel.setMobileNumber(employeeMasterDomain.getMobileNumber());
				employeeMasterModel.setContactNumber(employeeMasterDomain.getContactNumber());
				employeeMasterModel.setDateOfBirth(DateUtils.dateToString(employeeMasterDomain.getDateOfBirth()));
				employeeMasterModel.setDateOfJoining(DateUtils.dateToString(employeeMasterDomain.getDateOfJoining()));
				employeeMasterModel.setDateOfRelease(DateUtils.dateToString(employeeMasterDomain.getDateOfRelease()));
				employeeMasterModel.setDateOfResignation(DateUtils.dateToString(employeeMasterDomain.getDateOfResignation()));
				employeeMasterModel.setGender(employeeMasterDomain.getGender());				
				employeeMasterModel.setGroupName(employeeMasterDomain.getGroupMasterDomain().getGroupName());
				employeeMasterModel.setReleaseRemark(employeeMasterDomain.getReleaseRemark());
				employeeMasterModel.setEmploymentStatus(employeeMasterDomain.getEmploymentStatus());				
			return employeeMasterModel;
}
	
	@Override
	public boolean releaseEmployee(EmployeeMasterModel employeeMasterModel){
		boolean result = false;
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			int employeeId = userInfo.getEmployeeId();
		String teamDetails = employeeMasterModel.getTeamDetails();
		
		Date currentDate = new Date();
		if(null != teamDetails){
			JSONArray jsonArray = new JSONArray(teamDetails);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				//String releaseDate = jsonObject.getString("releaseDate");
                                 String releaseDate=employeeMasterModel.getDateOfRelease();
				String empRoleId = encryptionService.dcrypt(jsonObject.getString("empRoleId"));
				long roleId = Long.parseLong(empRoleId);
				EmployeeRoleMasterDomain roleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterById(roleId);
				if(null != roleMasterDomain){
					roleMasterDomain.setRemarks(employeeMasterModel.getEmploymentStatus());
					if(null != releaseDate && !releaseDate.equals("")){
						roleMasterDomain.setDtEndDate(DateUtils.dateStrToDate(releaseDate));
					}else{
						roleMasterDomain.setDtEndDate(currentDate);
					}
                            // added below setutilization to release employee utilization zero in project team mapping by varun 
                                        roleMasterDomain.setUtilization(0);
					roleMasterDomain.setDtTrDate(currentDate);
					roleMasterDomain.setNumTrUserId(employeeId);
				employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(roleMasterDomain);
				}
			}
		}
		
		
		long releaseEmployeeId = employeeMasterModel.getNumId();
		EmployeeMasterDomain employeeMasterDomain =employeeMasterDao.getEmployeeMasterById(releaseEmployeeId);
		String resignationDate = employeeMasterModel.getDateOfResignation();
		String releaseDate = employeeMasterModel.getDateOfRelease();
		
		if(null != resignationDate && !resignationDate.equals("")){
			employeeMasterDomain.setDateOfResignation(DateUtils.dateStrToDate(resignationDate));
		}else{
			employeeMasterDomain.setDateOfResignation(currentDate);
		}
		if(null != releaseDate && !releaseDate.equals("")){
			employeeMasterDomain.setDateOfRelease(DateUtils.dateStrToDate(releaseDate));
		}else{
			employeeMasterDomain.setDateOfRelease(currentDate);
		}
		employeeMasterDomain.setNumTrUserId(employeeId);
		employeeMasterDomain.setEmploymentStatus(employeeMasterModel.getEmploymentStatus());
		employeeMasterDomain.setReleaseRemark(employeeMasterModel.getReleaseRemark());
		
		employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
		
		List<EmployeeApprovedJobMapping> mappedApprovedPosts = employeeApprovedJobMappingDao.getActiveDataByEmployee(releaseEmployeeId);
		
		for(EmployeeApprovedJobMapping jobMapping: mappedApprovedPosts){
			//jobMapping.setEndDate(currentDate);
			if(null != releaseDate && !releaseDate.equals("")){
				jobMapping.setEndDate(DateUtils.dateStrToDate(releaseDate));
			}else{
				jobMapping.setEndDate(currentDate);
			}
			jobMapping.setNumTrUserId(employeeId);
			//jobMapping.getApprovedJob().setClosedOn(currentDate);
			if(null != releaseDate && !releaseDate.equals("")){
				jobMapping.getApprovedJob().setClosedOn(DateUtils.dateStrToDate(releaseDate));
			}else{
				jobMapping.getApprovedJob().setClosedOn(currentDate);
			}
			jobMapping.getApprovedJob().setStatus("Closed");
			jobMapping.getApprovedJob().setDtTrDate(currentDate);
			jobMapping.getApprovedJob().setNumTrUserId(employeeId);
			jobMapping.setRemark("Employee "+employeeMasterModel.getEmploymentStatus());
			
			employeeApprovedJobMappingDao.save(jobMapping);
		}
		
		result= true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<EmployeeMasterModel> viewEmployeeOnGroupID(Long encGroupId) {
		List<EmployeeMasterDomain> list = employeeMasterDao.getAllEmployeeGroupID(encGroupId);
		return convertDomainListToModelList(list);
	}
	
	
		@Override
		public List<EmployeeMasterModel> loadEmployeeDetails(){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadEmployeeDetails();
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();
	
					model.setEmployeeName(""+obj[1]);
					model.setMobileNumber(""+obj[2]);
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[3]));
					model.setOfficeEmail((""+obj[4]));
					if(obj[0]!=null){
					double inv=new Double(""+obj[0]);
					model.setInvolvment((int) inv);
					model.setPendingInv(100- (int) inv);
					}
					else{
						model.setInvolvment(-1);
						model.setPendingInv(-1);
					}
					/*model.setInvolvment(new Integer(""+obj[4]));*/
					model.setEmployeeId(new Integer(""+obj[5]));
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[5]));
					model.setGroupName(""+obj[6]);
					model.setStrDesignation(""+obj[7]);
					dataList.add(model);
				}
			
			return dataList;
		}
		
	@Override
	public List<EmployeeMasterModel> loadJoinedEmployeeDetails(String startRange, String endRange){
		List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

			List<Object[]> newDataList =  employeeMasterDao.loadJoinedEmployeeDetails(startRange, endRange);
			for(Object[] obj : newDataList){
				EmployeeMasterModel model = new EmployeeMasterModel();

				model.setEmployeeName(""+obj[1]);
				model.setEmployeeId(new Integer(""+obj[0]));
				model.setEmployeeName(""+obj[1]);
				model.setStrDesignation(""+obj[2]);
				model.setGroupName(""+obj[3]);
				model.setEmploymentStatus(""+obj[5]);
				model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
				model.setDateOfJoining(DateUtils.dateToString( (Date)obj[4]));
				dataList.add(model);
			}
		
		return dataList;
	}
		
		@Override
		public List<EmployeeMasterModel> loadResignedEmployeeDetails(String startRange, String endRange){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadResignedEmployeeDetails(startRange, endRange);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeName(""+obj[1]);
					model.setEmployeeId(new Integer(""+obj[0]));
					model.setEmployeeName(""+obj[1]);
					model.setStrDesignation(""+obj[2]);
					model.setGroupName(""+obj[3]);
					model.setEmploymentStatus(""+obj[5]);
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
					model.setDateOfResignation(DateUtils.dateToString( (Date)obj[4]));
					dataList.add(model);
				}
			
			return dataList;
		}
		public List<EmployeeMasterModel> loadResignedEmployeeCustomizedDetails(String startRange, String endRange){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadResignedEmployeeDetailsCustomized(startRange, endRange);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();
                    model.setResignedEmpCount(new Integer(""+obj[2]));
                    model.setGroupName(""+obj[0]);
                    model.setStrDesignation(""+obj[1]);
					dataList.add(model);
				}
			
			return dataList;
		}
         
		@Override
		public List<EmployeeMasterModel> employeeDetailsWithInvolvements(){
			List<EmployeeMasterModel> outputList = new ArrayList<EmployeeMasterModel>();
			List<Object[]> dataList=employeeMasterDao.employeeDetailsWithInvolvements();
			for(Object[] obj : dataList){
				EmployeeMasterModel model = new EmployeeMasterModel();
				model.setGroupName(""+obj[0]);
				model.setEmployeeId((Integer) obj[1]);
				model.setEncEmployeeId(encryptionService.encrypt(""+obj[1]));
				model.setEmployeeName(""+obj[2]);
				model.setStrDesignation(""+obj[3]);
				//Modified by devesh on 9/6/23 for setting deputed value in employee master
				model.setNumDeputedAt((Integer) obj[4]);
				model.setEmployeeTypeName(""+obj[5]);
				model.setOfficeEmail(""+obj[6]);
				if(null != obj[7]){
					model.setTeamDetails(""+obj[7]);
					model.setProjectCount(StringUtils.countMatches(""+obj[7], "<b>"));
				}
				//End
				outputList.add(model);
			}
			return outputList;
		}
		
		@SuppressWarnings("unused")
		@Override
		public Map<String, MiscDataModel> getDetails(String startRange, String endRange) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			String payment =  null;
			EmployeeRoleMasterModel selectedEmployeeRoleMasterModel = userInfo.getSelectedEmployeeRole();
			List<Object[]> submittedProposalList = proposalMasterDao.loadSubmittedProposals(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> closedProposalList = proposalMasterDao.loadProjectClosed(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> loadProjectReceived =   projectMasterDao.loadProjectsReceived(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> joinedEmployee = employeeMasterDao.loadJoinedEmployee(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> resignedEmployee = employeeMasterDao.loadResinedEmployees(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> employees = employeeMasterDao.loadEmployeesCount(selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> income = projectPaymentReceivedDao.loadIncome(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> incomeWithoutInvoice = projectPaymentReceivedDao.loadPaymentReceivedWithoutInvoice(startRange,endRange,selectedEmployeeRoleMasterModel.getNumOrganisationId());
			List<Object[]> projectsAsOnDate = proposalMasterDao.loadProjectsAsOnDate(selectedEmployeeRoleMasterModel.getNumOrganisationId());
			Map<String, MiscDataModel > map = new LinkedHashMap<String, MiscDataModel>();
			List<GroupMasterDomain> allowedGroups = groupMasterDao.getAllActiveGrpMasterDomain(selectedEmployeeRoleMasterModel.getNumOrganisationId());
			
			double totalProposalCostInLakhs = 0,totalProjectCostInLakhs=0,totalIncome=0, totalOutlayOfProject=0;
			BigInteger totalSubmittedProposals= new BigInteger("0");
			BigInteger totalClosedProjets=new BigInteger("0");
			BigInteger totalReceivedProjets=new BigInteger("0");
			BigInteger totalJoinees=new BigInteger("0");
			BigInteger totalResigned=new BigInteger("0");
			BigInteger totalEmployee=new BigInteger("0");
			BigInteger totalProjects=new BigInteger("0");
			for(GroupMasterDomain groupMasterDomain: allowedGroups)	{
				BigInteger groupId = BigInteger. valueOf(groupMasterDomain.getNumId());
				MiscDataModel miscDataModel = new MiscDataModel();
			
				Object[] proposalObj = submittedProposalList.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
				.orElse(null);
		
				if(null != proposalObj){
					miscDataModel.setSubmittedProposalCount((BigInteger)proposalObj[2]);
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)proposalObj[0]));
					totalSubmittedProposals=totalSubmittedProposals.add((BigInteger)proposalObj[2]);
					miscDataModel.setGroupName(""+proposalObj[1]);
				
					if(proposalObj[3] instanceof BigDecimal){
					BigDecimal b=(BigDecimal)proposalObj[3];
					double totalProposalCost=b.doubleValue();
					double dataInLakhs = CurrencyUtils.round((totalProposalCost/100000),2);
					totalProposalCostInLakhs +=dataInLakhs;
					String strProposalTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setStrTotalCost(strProposalTotalCost);
					}
					else if(proposalObj[3] instanceof BigInteger){
						BigInteger b=(BigInteger)proposalObj[3];
						double totalProposalCost=b.doubleValue();
						double dataInLakhs = CurrencyUtils.round((totalProposalCost/100000),2);
						totalProposalCostInLakhs +=dataInLakhs;
						String strProposalTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
						miscDataModel.setStrTotalCost(strProposalTotalCost);
						}
				}
				
				Object[] closedProjectObj = closedProposalList.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != closedProjectObj){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)closedProjectObj[0]));
					miscDataModel.setGroupName(""+closedProjectObj[1]);
					miscDataModel.setClosedProposalCout((BigInteger)closedProjectObj[2]);
					totalClosedProjets=totalClosedProjets.add((BigInteger)closedProjectObj[2]);
					
				}
				
				Object[] receivedProjectList = loadProjectReceived.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != receivedProjectList){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)receivedProjectList[0]));
					miscDataModel.setGroupName(""+receivedProjectList[1]);
					miscDataModel.setProjectReceviedCount((BigInteger)receivedProjectList[2]);
					totalReceivedProjets=totalReceivedProjets.add((BigInteger)receivedProjectList[2]);
					if(receivedProjectList[3] instanceof BigDecimal){
					BigDecimal b=(BigDecimal)receivedProjectList[3];
					miscDataModel.setProjectTotalCost(b.longValue());
					double totalProjectCost=b.doubleValue();
					double dataInLakhs = CurrencyUtils.round((totalProjectCost/100000),2);
					totalProjectCostInLakhs+=dataInLakhs;
					String strTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setStrProjectTotalCost(strTotalCost);
				}
				
				else if(receivedProjectList[3] instanceof BigInteger){
					BigInteger b=(BigInteger)receivedProjectList[3];
					//double totalProposalCost=b.doubleValue();
					double totalProjectCost=b.doubleValue();
					double dataInLakhs = CurrencyUtils.round((totalProjectCost/100000),2);
					totalProjectCostInLakhs+=dataInLakhs;
					String strTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setStrProjectTotalCost(strTotalCost);
					}
				}
					
					
				Object[] joinEmp = joinedEmployee.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != joinEmp){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)joinEmp[0]));
					miscDataModel.setGroupName(""+joinEmp[1]);
					miscDataModel.setJoinEmpCout((BigInteger)joinEmp[2]);
					totalJoinees=totalJoinees.add((BigInteger)joinEmp[2]);
				}
				
				Object[] projAsOndate = projectsAsOnDate.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != projAsOndate){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)projAsOndate[0]));
					miscDataModel.setGroupName(""+projAsOndate[1]);
					miscDataModel.setTotalProjects((BigInteger)projAsOndate[2]);
					totalProjects=totalProjects.add((BigInteger)projAsOndate[2]);
					
					if(projAsOndate[3] instanceof BigDecimal){
						BigDecimal b=(BigDecimal)projAsOndate[3];
						double totalProposalCost=b.doubleValue();
						double dataInLakhs = CurrencyUtils.round((totalProposalCost/100000),2);
						totalOutlayOfProject +=dataInLakhs;
						String strProposalTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
						miscDataModel.setTotalOutlayOfProject(strProposalTotalCost);
						}
						else if(projAsOndate[3] instanceof BigInteger){
							BigInteger b=(BigInteger)projAsOndate[3];
							double totalProposalCost=b.doubleValue();
							double dataInLakhs = CurrencyUtils.round((totalProposalCost/100000),2);
							totalOutlayOfProject +=dataInLakhs;
							String strProposalTotalCost = CurrencyUtils.convertToINR(dataInLakhs);
							miscDataModel.setTotalOutlayOfProject(strProposalTotalCost);
							}
					
				}
				
				Object[] resignEmp = resignedEmployee.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != resignEmp){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)resignEmp[0]));
					miscDataModel.setGroupName(""+resignEmp[1]);
					miscDataModel.setResignEmpCount((BigInteger)resignEmp[2]);
					totalResigned=totalResigned.add((BigInteger)resignEmp[2]);
				}
				
				Object[] employeesCount = employees.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != employeesCount){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)employeesCount[0]));
					miscDataModel.setGroupName(""+employeesCount[1]);
					miscDataModel.setEmployeeCount((BigInteger)employeesCount[2]);
					totalEmployee=	totalEmployee.add((BigInteger)employeesCount[2]);
				}
				
				Object[] incomeDetails = income.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				Object[] incomeDetailsWithoutInvoice = incomeWithoutInvoice.stream().filter(x -> groupId == (BigInteger)x[0]).findAny()
						.orElse(null);
				
				if(null != incomeDetails && null != incomeDetailsWithoutInvoice){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)incomeDetails[0]));
					miscDataModel.setGroupName(""+incomeDetails[1]);
					double actualAmount=(Double)incomeDetails[2];
					actualAmount +=(Double) incomeDetailsWithoutInvoice[2];
					double dataInLakhs = CurrencyUtils.round((actualAmount/100000),2);
					totalIncome+=dataInLakhs;
					payment = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setIncome(payment);
				}else if(null != incomeDetailsWithoutInvoice){
					
					//miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)incomeDetailsWithoutInvoice[0]));
					
					
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)incomeDetailsWithoutInvoice[0]));
					miscDataModel.setGroupName(""+incomeDetailsWithoutInvoice[1]);
					double actualAmount=(Double)incomeDetailsWithoutInvoice[2];
					double dataInLakhs = CurrencyUtils.round((actualAmount/100000),2);
					totalIncome+=dataInLakhs;
					payment = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setIncome(payment);
				}else if(null != incomeDetails){
					miscDataModel.setEncGroupId(encryptionService.encrypt(""+(BigInteger)incomeDetails[0]));
					miscDataModel.setGroupName(""+incomeDetails[1]);
					double actualAmount=(Double)incomeDetails[2];					
					double dataInLakhs = CurrencyUtils.round((actualAmount/100000),2);
					totalIncome+=dataInLakhs;
					payment = CurrencyUtils.convertToINR(dataInLakhs);
					miscDataModel.setIncome(payment);
				}
				
				map.put(groupMasterDomain.getGroupShortName(),miscDataModel);
			}
			MiscDataModel totalDataModel = new MiscDataModel();
			totalDataModel.setSubmittedProposalCount(totalSubmittedProposals);
			totalDataModel.setIncome(CurrencyUtils.convertToINR(totalIncome));
			totalDataModel.setClosedProposalCout(totalClosedProjets);
			totalDataModel.setJoinEmpCout(totalJoinees);
			totalDataModel.setResignEmpCount(totalResigned);
			totalDataModel.setProjectReceviedCount(totalReceivedProjets);
			totalDataModel.setEmployeeCount(totalEmployee);
			totalDataModel.setStrProjectTotalCost(CurrencyUtils.convertToINR(totalProjectCostInLakhs));
			totalDataModel.setStrTotalCost(CurrencyUtils.convertToINR(totalProposalCostInLakhs));
			totalDataModel.setTotalProjects(totalProjects);
			totalDataModel.setTotalOutlayOfProject(CurrencyUtils.convertToINR(totalOutlayOfProject));
			map.put("Total",totalDataModel);
			return map;
		}

		
		@Override
		public List<EmployeeMasterModel> getNewJoinedEmployeeByGroupDetails(String startRange, String endRange,String encGroupId){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();
			//String strEncGroupId=encGroupId.substring( 1, encGroupId.length() - 1 );
			String strGroupId = encryptionService.dcrypt(encGroupId);
			long groupId = Long.parseLong(strGroupId);
				List<Object[]> newDataList =  employeeMasterDao.getNewJoinedEmployeeByGroupDetails(startRange, endRange,groupId);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeName(""+obj[1]);
					model.setEmployeeId(new Integer(""+obj[0]));
					model.setEmployeeName(""+obj[1]);
					model.setStrDesignation(""+obj[2]);
					model.setGroupName(""+obj[3]);
					model.setEmploymentStatus(""+obj[5]);
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[4]));
					dataList.add(model);
				}
			
			return dataList;
		}
		
		@Override
		public List<EmployeeMasterModel> getResignedEmployeeByGroupDetails(String startRange, String endRange,String encGroupId){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();
			//String strEncGroupId=encGroupId.substring( 1, encGroupId.length() - 1 );
			String strGroupId = encryptionService.dcrypt(encGroupId);
			long groupId = Long.parseLong(strGroupId);
				List<Object[]> newDataList =  employeeMasterDao.getResignedEmployeeByGroupDetails(startRange, endRange,groupId);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeName(""+obj[1]);
					model.setEmployeeId(new Integer(""+obj[0]));
					model.setEmployeeName(""+obj[1]);
					model.setStrDesignation(""+obj[2]);
					model.setGroupName(""+obj[3]);
					model.setEmploymentStatus(""+obj[5]);
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
					model.setDateOfResignation(DateUtils.dateToString( (Date)obj[4]));
					dataList.add(model);
				}
			
			return dataList;
		}
		
		@Override
		public JSONArray getEmployeeCountByEmployementTypeNew(){
			JSONArray resultArray = new JSONArray();		
			List<Object[]> dataList = employeeMasterDao.getEmployeeCountByEmployementType();
		
			JSONArray tempArray = new JSONArray();
			tempArray.put("Employement Type");
			tempArray.put("No of Employees");
			resultArray.put(tempArray);
			
			for(int i=0;i<dataList.size();i++){
				Object[] obj = dataList.get(i);
				JSONArray innerArray = new JSONArray();	
				innerArray.put(obj[0]);
				innerArray.put(obj[2]);
				resultArray.put(innerArray);
				
			}	
			return resultArray;
		}
		
		@Override
		public List<EmployeeMasterModel> employeeDetailsWithInvolvements(ProjectMasterModel projectMastermodel){
			List<EmployeeMasterModel> outputList = new ArrayList<EmployeeMasterModel>();
			String strGroupId = encryptionService.dcrypt(projectMastermodel.getEncGroupId());
			long groupId = Long.parseLong(strGroupId);
			List<Object[]> dataList=employeeMasterDao.employeeDetailsWithInvolvements(groupId);
			for(Object[] obj : dataList){
				EmployeeMasterModel model = new EmployeeMasterModel();
				model.setGroupName(""+obj[0]);
				model.setEmployeeId((Integer) obj[1]);
				model.setEmployeeName(""+obj[2]);
				model.setStrDesignation(""+obj[3]);
				model.setEmployeeTypeName(""+obj[4]);
				if(null != obj[5]){
					model.setTeamDetails(""+obj[5]);
				}
				outputList.add(model);
			}
			return outputList;
		}
		
		@Override
		public List<EmployeeMasterModel> searchEmpDataByName(EmployeeMasterModel employeeMasterModel){
			
				
			List<EmployeeMasterDomain> employeeMasterDomain = employeeMasterDao.searchEmpDataByName(employeeMasterModel.getEmployeeName());
				
			
				List<EmployeeMasterModel> dataModel = convertDomainListToModelListForThirdParty(employeeMasterDomain);
				
				
				return dataModel;
			}
				
		@Override
		public EmployeeMasterModel getRelievedEmployeeBasicDetail(EmployeeMasterModel employeeMasterModel){
			EmployeeMasterDomain employeeMasterDomain = null;
			String dataLoadedBasedOn = "";
			if(null != employeeMasterModel.getSearchEmployeeId() && !employeeMasterModel.getSearchEmployeeId().equals("0")){
				long employeeId = Long.parseLong(employeeMasterModel.getSearchEmployeeId());
				employeeMasterDomain = employeeMasterDao.getRelievedEmployeeDetails(employeeId);
				dataLoadedBasedOn = "Employee Id";
			}
			else if((null != employeeMasterModel.getSearchEmployeeEmail()) && (!employeeMasterModel.getSearchEmployeeEmail().equals("")) && (null == employeeMasterDomain)){
				employeeMasterDomain = employeeMasterDao.getRelievedEmployeeByEmail(employeeMasterModel.getSearchEmployeeEmail());
				dataLoadedBasedOn = "Email";
			}
			if(null != employeeMasterDomain && employeeMasterDomain.getNumIsValid() == 1){
				EmployeeMasterModel dataModel = convertDomainToBasicDetail(employeeMasterDomain);
				dataModel.setDataLoadedBasedOn(dataLoadedBasedOn);
				
				return dataModel;
			}
			return null;		
		}
		
		@Override
		public List<EmployeeMasterModel> searchRelievedEmpDataByName(EmployeeMasterModel employeeMasterModel){
			
				
			List<EmployeeMasterDomain> employeeMasterDomain = employeeMasterDao.searchRelievedEmpDataByName(employeeMasterModel.getEmployeeName());
				
			
				List<EmployeeMasterModel> dataModel = convertDomainListToModelListForThirdParty(employeeMasterDomain);
				
				
				return dataModel;
			}
		
		@Override
		public List<EmployeeMasterModel> loadUnderUtilizationEmployees(){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadUnderUtilizationEmployee();
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();
	
					model.setEmployeeName(""+obj[1]);
					model.setMobileNumber(""+obj[2]);
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[3]));
					model.setOfficeEmail((""+obj[4]));
					if(obj[0]!=null){
					double inv=new Double(""+obj[0]);
					model.setInvolvment((int) inv);
					model.setPendingInv(100- (int) inv);
					}
					else{
						model.setInvolvment(-1);
						model.setPendingInv(-1);
					}
					/*model.setInvolvment(new Integer(""+obj[4]));*/
					model.setEmployeeId(new Integer(""+obj[5]));
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[5]));
					model.setGroupName(""+obj[6]);
					model.setStrDesignation(""+obj[7]);
					dataList.add(model);
				}
			
			return dataList;
		}
		
		@Override
		public List<EmployeeMasterModel> getEmployeesByEmploymentType(String employmentTypeName){
			List<EmployeeMasterDomain> dataList = employeeMasterDao.getEmployeesByEmploymentType(employmentTypeName);
			
			if(null != dataList){
				return convertDomainListToModelListForThirdParty(dataList);
			}else{
				return null;
			}	
		}
		
		@Override
		public List<EmployeeMasterModel> employeeDetailsWithInvolvementsGroupWise(String groupId){
			List<EmployeeMasterModel> outputList = new ArrayList<EmployeeMasterModel>();
			List<Object[]> dataList=employeeMasterDao.employeeDetailsWithInvolvementsGroupWise(groupId);
			for(Object[] obj : dataList){
				EmployeeMasterModel model = new EmployeeMasterModel();
				model.setGroupName(""+obj[0]);
				model.setEmployeeId((Integer) obj[1]);
				model.setEncEmployeeId(encryptionService.encrypt(""+obj[1]));
				model.setEmployeeName(""+obj[2]);
				model.setStrDesignation(""+obj[3]);
				model.setEmployeeTypeName(""+obj[4]);
				if(null != obj[5]){
					model.setTeamDetails(""+obj[5]);
					model.setProjectCount(StringUtils.countMatches(""+obj[5], "<b>"));
				}
				outputList.add(model);
			}
			return outputList;
		}
		
		public List<EmployeeMasterModel> employeeDetailsByCategory(String category){
			List<EmployeeMasterDomain> details=employeeMasterDao.getEmployeeCountByCategoryName(category);
			return convertDomainListToModelList(details);
		}
		
		@Override
		public JSONArray getEmployeeCountByGroupwise(String groupName){
			JSONArray resultArray = new JSONArray();		
			List<Object[]> dataList = employeeMasterDao.getEmployeeCountByGroupwise(groupName);
		
			JSONArray tempArray = new JSONArray();
			tempArray.put("Employement Type");
			tempArray.put("No of Employees");
			resultArray.put(tempArray);
			
			for(int i=0;i<dataList.size();i++){
				Object[] obj = dataList.get(i);
				JSONArray innerArray = new JSONArray();	
				innerArray.put(obj[0]);
				innerArray.put(obj[2]);
				resultArray.put(innerArray);
				
			}	
			return resultArray;
		}
		
		@Override
		public  List<DesignationMasterModel> getEmployeeCountBySingleGroupandDesignation(String group){
			List<DesignationMasterModel> details= new ArrayList<DesignationMasterModel>();
			List<Object[]> dataList = employeeMasterDao.getEmployeeCountBySingleGroupandDesignation(group);	
			
			for(int i=0;i<dataList.size();i++){
				DesignationMasterModel designationMasterModel=new DesignationMasterModel();
				Object[] obj = dataList.get(i);
				String designationName= (String) obj[0];
				long count = (long) obj[1];
				designationMasterModel.setStrDesignationName(designationName);
				designationMasterModel.setCount(count);
				details.add(designationMasterModel);
			}	

			return details;
		}
		
		@Override
		public List<EmployeeMasterModel> loadRejoinEmployeeDetails(String startRange, String endRange){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadRejoinEmployeeDetails(startRange, endRange);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeName(""+obj[1]);
					model.setEmployeeId(new Integer(""+obj[0]));
					model.setEmployeeName(""+obj[1]);
					model.setStrDesignation(""+obj[2]);
					model.setGroupName(""+obj[3]);
					model.setEmploymentStatus(""+obj[5]);
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[4]));
					model.setDateOfBirth(DateUtils.dateToString( (Date)obj[6]));
					dataList.add(model);
				}
			
			return dataList;
		}
		
		@Override
		public List<EmployeeMasterModel> getPreviousHistoryOfEmp(String empName, String dob){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.getPreviousHistoryOfEmp(empName, dob);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeId(new Integer(""+obj[4]));
					model.setStrDesignation(""+obj[0]);
					model.setGroupName(""+obj[1]);
	
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[2]));
					model.setDateOfRelease(DateUtils.dateToString( (Date)obj[3]));
					dataList.add(model);
				}
			
			return dataList;
		}
		
		@Override
		public List<EmployeeMasterModel> loadRejoinRelievedEmployeeDetails(String startRange, String endRange,String status){
			List<EmployeeMasterModel> dataList = new ArrayList<EmployeeMasterModel>();

				List<Object[]> newDataList =  employeeMasterDao.loadRejoinRelievedEmployeeDetails(startRange, endRange,status);
				for(Object[] obj : newDataList){
					EmployeeMasterModel model = new EmployeeMasterModel();

					model.setEmployeeName(""+obj[1]);
					model.setEmployeeId(new Integer(""+obj[0]));
					model.setEmployeeName(""+obj[1]);
					model.setStrDesignation(""+obj[2]);
					model.setGroupName(""+obj[3]);
					model.setEmploymentStatus(""+obj[5]);
					model.setEncEmployeeId(encryptionService.encrypt(""+obj[0]));
					model.setDateOfJoining(DateUtils.dateToString( (Date)obj[4]));
					model.setDateOfBirth(DateUtils.dateToString( (Date)obj[6]));
					dataList.add(model);
				}
			
			return dataList;
		}
	
		@Override
		public EmployeeMasterModel getCountRejoinEmployeeDetails(String startRange, String endRange){
			EmployeeMasterModel dataList = new EmployeeMasterModel();
				int relievedCount=0;
				int workingCount=0;
				List<Object[]> newDataList =  employeeMasterDao.getCountRejoinEmployeeDetails(startRange,endRange);
				for(Object[] obj : newDataList){
					String status=""+obj[5];
					if(status.equals("Relieved")){
						relievedCount=relievedCount+1;
						
					}
					else if(status.equals("Working")){
						workingCount=workingCount+1;
					}
				}
				dataList.setRelievedCount(relievedCount);
				dataList.setWorkingCount(workingCount);
				dataList.setTotalRejoinCount(newDataList.size());
			return dataList;
		}
	
}
