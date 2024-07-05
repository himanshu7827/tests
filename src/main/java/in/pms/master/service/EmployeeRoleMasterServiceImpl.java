package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.dao.RoleDao;
import in.pms.login.domain.Role;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.GroupMasterDao;
import in.pms.master.dao.ManpowerRequirementDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectRoleAccessRoleMapping;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class EmployeeRoleMasterServiceImpl implements EmployeeRoleMasterService {

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;

	@Autowired
	RoleMasterService roleMasterService;

	@Autowired
	EmployeeMasterService employeeMasterService;

	@Autowired
	GroupMasterDao groupMasterDao;

	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	OrganisationMasterService organisationMasterService;
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
	ManpowerRequirementDao manpowerRequirementDao;
	
	@Autowired
	ProposalMasterDao proposalMasterDao;
	
	@Autowired
	ProjectMasterDao projectMasterDao;

	@Override
	@PreAuthorize("hasAuthority('WRITE_EMPLOYEE_ROLE_MST')")
	public long saveUpdateEmployeeRoleMaster(
			EmployeeRoleMasterModel employeeRoleMasterModel) {
		long id=0;
		EmployeeRoleMasterDomain employeeRoleMasterDomain = convertEmployeeRoleMasterModelToDomain(employeeRoleMasterModel);
		 id= employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
		 if(id>0){
			 List<ProjectRoleAccessRoleMapping> data=employeeRoleMasterDao.getProjectRoleAccessRoleMapping(employeeRoleMasterModel.getNumRoleId());
				EmployeeMasterDomain employeeMasterDomain=employeeMasterDao.getEmployeeDetails(employeeRoleMasterModel.getNumEmpId());
				Collection<Role> coll=employeeMasterDomain.getRoles();
			 for (ProjectRoleAccessRoleMapping projectRoleAccessRoleMapping : data) {
				if(!coll.contains(roleDao.getRoleById(projectRoleAccessRoleMapping.getRoleId())))
					coll.add(roleDao.getRoleById(projectRoleAccessRoleMapping.getRoleId()));	
			}
			 employeeMasterDomain.setRoles(coll);
			 employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
		 }
		 return id;
	}

	@Override
	public EmployeeRoleMasterModel getEmployeeRoleMasterDomainById(long numId) {
		return convertEmployeeRoleMasterDomainToModel(employeeRoleMasterDao.getEmployeeRoleMasterById(numId));
	}
	
	@Override
	public boolean uncheckPrimaryRole(long empId,long projectid,long roleId) {			
			EmployeeRoleMasterDomain employeeRoleMaster=employeeRoleMasterDao.getEmployeeRoleMasterById(roleId);
			employeeRoleMaster.setPrimaryProject(0);
			employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMaster);
			return true;
			
	}

	@Override
	public String getEmployeeRoleMasterDomainByEmpId(long empId,long projectid,long roleId) {
			String result="";	
			List<EmployeeRoleMasterDomain> employeeRoleMasterDomain=employeeRoleMasterDao.getEmployeeRoleMasterByEmpId(empId);
			if(employeeRoleMasterDomain.size()>0){
			long projectId=employeeRoleMasterDomain.get(0).getNumProjectId();
			ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
			String projectName=projectMasterDomain.getStrProjectName();
			List<EmployeeRoleMasterDomain> employeeRoleList = employeeRoleMasterDao.getProjectPL(projectId); 
			if(employeeRoleList.size()>0){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();		
			long PLID=employeeRoleList.get(0).getNumEmpId();
			long currentUserId=userInfo.getEmployeeId();
			
			if(PLID == currentUserId){
				employeeRoleMasterDomain.get(0).setPrimaryProject(0);
				employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain.get(0));
				if(roleId !=0){
				EmployeeRoleMasterDomain employeeRoleMaster=employeeRoleMasterDao.getEmployeeRoleMasterById(roleId);
				employeeRoleMaster.setPrimaryProject(1);
				employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMaster);
				result = "Employee Primary Role is removed from "+projectName+".";
				return result;
				//return true;
				}
				else{
					//return false;
					result="";
				}
			}
					
			}
		}else{ 
			if(roleId !=0){
			EmployeeRoleMasterDomain employeeRoleMaster=employeeRoleMasterDao.getEmployeeRoleMasterById(roleId);
			employeeRoleMaster.setPrimaryProject(1);
			employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(employeeRoleMaster);
			//return true;
			result="Primary Role is assigned";
			}
			else{
				//return false;
				result="";
			}
		}		
		return result;
	}

	
	@Override
	@PreAuthorize("hasAuthority('READ_EMPLOYEE_ROLE_MST')")
	public List<EmployeeRoleMasterModel> getAllEmployeeRoleMasterDomain() {
		return convertEmployeeRoleMasterDomainToModelList(employeeRoleMasterDao
				.getAllEmployeeRoleMasterDomain());
	}

	@Override
	public List<EmployeeRoleMasterModel> getAllActiveEmployeeRoleMaster() {
		return convertEmployeeRoleMasterDomainToModelList(employeeRoleMasterDao
				.getAllActiveEmployeeRoleMasterDomain());
	}

	public EmployeeRoleMasterDomain convertEmployeeRoleMasterModelToDomain(
			EmployeeRoleMasterModel employeeRoleMasterModel) {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();

		EmployeeRoleMasterDomain employeeRoleMasterDomain = new EmployeeRoleMasterDomain();
		ManpowerRequirementDomain manpowerRequirementDomain=new ManpowerRequirementDomain();
		if (employeeRoleMasterModel.getNumId() != 0) {
			employeeRoleMasterDomain = employeeRoleMasterDao
					.getEmployeeRoleMasterById(employeeRoleMasterModel
							.getNumId());
		}

		employeeRoleMasterDomain.setDtTrDate(new Date());
		employeeRoleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
	
		employeeRoleMasterDomain.setNumIsValid(1);
		if(null != employeeRoleMasterModel.getInvolvement() && !employeeRoleMasterModel.getInvolvement().equals("")){
			employeeRoleMasterDomain.setUtilization(Double.parseDouble(employeeRoleMasterModel.getInvolvement()));
		}else{
			employeeRoleMasterDomain.setUtilization(0);
		}
		if (null != employeeRoleMasterModel.getStrEndDate()
				&& !employeeRoleMasterModel.getStrEndDate().equals("")) {
			try {
				employeeRoleMasterDomain
						.setDtEndDate(DateUtils
								.dateStrToDate(employeeRoleMasterModel
										.getStrEndDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (null != employeeRoleMasterModel.getStrStartDate()
				&& !employeeRoleMasterModel.getStrStartDate().equals("")) {
			try {
				employeeRoleMasterDomain.setDtStartDate(DateUtils
						.dateStrToDate(employeeRoleMasterModel
								.getStrStartDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		employeeRoleMasterDomain.setNumEmpId(employeeRoleMasterModel
				.getNumEmpId());

		employeeRoleMasterDomain.setRoleMasterDomain(roleMasterService
				.getRoleDomainById(employeeRoleMasterModel.getNumRoleId()));

		employeeRoleMasterDomain.setNumGroupId(employeeRoleMasterModel
				.getNumGroupId());
		employeeRoleMasterDomain.setNumOrganisationId(employeeRoleMasterModel
				.getNumOrganisationId());
		employeeRoleMasterDomain.setNumProjectId(employeeRoleMasterModel
				.getNumProjectId());

		if(employeeRoleMasterModel.getNumManReqId()!=0)
		{
			manpowerRequirementDomain.setNumId(employeeRoleMasterModel.getNumManReqId());
			employeeRoleMasterDomain.setManpowerRequirementDomain(manpowerRequirementDomain);
			employeeRoleMasterDomain.setStrManReqDetails(employeeRoleMasterModel.getStrManReqDetails());
		}
		// projectPaymentReceivedDomain.setProjectInvoiceMasterDomain(projectInvoiceMasterService.getProjectInvoiceDomainById(projectPaymentReceivedModel.getInvoiceId()));
		return employeeRoleMasterDomain;
	}

	public List<EmployeeRoleMasterModel> convertEmployeeRoleMasterDomainToModelList(
			List<EmployeeRoleMasterDomain> employeeRoleMasterList) {
		List<EmployeeRoleMasterModel> list = new ArrayList<EmployeeRoleMasterModel>();
		for (int i = 0; i < employeeRoleMasterList.size(); i++) {
			EmployeeRoleMasterDomain employeeRoleMasterDomain = employeeRoleMasterList
					.get(i);
			EmployeeRoleMasterModel employeeRoleMasterModel = new EmployeeRoleMasterModel();

			if (employeeRoleMasterDomain.getNumId() != 0) {
				String encryptedId = encryptionService.encrypt(""
						+ employeeRoleMasterDomain.getNumId());
				employeeRoleMasterModel.setEncEmpId(encryptedId);
			}
			employeeRoleMasterModel.setNumId(employeeRoleMasterDomain
					.getNumId());
			if (employeeRoleMasterDomain.getNumIsValid() == 1) {
				employeeRoleMasterModel.setValid(true);
			} else {
				employeeRoleMasterModel.setValid(false);
			}

			employeeRoleMasterModel.setNumEmpId(employeeRoleMasterDomain
					.getNumEmpId());
			employeeRoleMasterModel.setNumGroupId(employeeRoleMasterDomain
					.getNumGroupId());
			employeeRoleMasterModel
					.setNumOrganisationId(employeeRoleMasterDomain
							.getNumOrganisationId());
			employeeRoleMasterModel.setNumProjectId(employeeRoleMasterDomain
					.getNumProjectId());
			employeeRoleMasterModel.setRoleId(employeeRoleMasterDomain.getRoleMasterDomain().getNumId());
			list.add(employeeRoleMasterModel);
		}
		return list;
	}

	/*
	 * @Override public List<EmployeeRoleMasterModel>
	 * getEmployeeRoleMasterByEmpId(long numEmpId) {
	 * List<EmployeeRoleMasterDomain> employeeRoleMasterDomain =
	 * employeeRoleMasterDao.getAllEmployeeRoleByEmpId(numEmpId);
	 * List<EmployeeRoleMasterModel> employeeRoleMasterModelList =
	 * convertEmployeeRoleMasterDomainToModelList(employeeRoleMasterDomain);
	 * return employeeRoleMasterModelList; }
	 */

	public EmployeeRoleMasterModel convertEmployeeRoleMasterDomainToModel(
			EmployeeRoleMasterDomain employeeRoleMasterDomain) {
			
		EmployeeRoleMasterModel employeeRoleMasterModel = new EmployeeRoleMasterModel();

		if (employeeRoleMasterDomain.getNumId() != 0) {
			String encryptedId = encryptionService.encrypt(""
					+ employeeRoleMasterDomain.getNumId());
			employeeRoleMasterModel.setEncEmpId(encryptedId);
		}
		employeeRoleMasterModel.setNumId(employeeRoleMasterDomain.getNumId());
		if (employeeRoleMasterDomain.getNumIsValid() == 1) {
			employeeRoleMasterModel.setValid(true);
		} else {
			employeeRoleMasterModel.setValid(false);
		}

		employeeRoleMasterModel.setNumEmpId(employeeRoleMasterDomain
				.getNumEmpId());
		employeeRoleMasterModel.setNumGroupId(employeeRoleMasterDomain
				.getNumGroupId());
		employeeRoleMasterModel.setNumOrganisationId(employeeRoleMasterDomain
				.getNumOrganisationId());
		employeeRoleMasterModel.setNumProjectId(employeeRoleMasterDomain
				.getNumProjectId());
		
		return employeeRoleMasterModel;

	}

	// }

	@Override
	public List<EmployeeRoleMasterModel> getEmployeeRoleMasterByEmpId(
			long numEmpId) {

		List<Object[]> dataList = employeeRoleMasterDao
				.getAllEmployeeRoleByEmpId(numEmpId);

		return convertObjectsToEmployeeRoles(dataList);
	}

	@Override
	public List<EmployeeRoleMasterModel> getActiveEmployeeRoleByEmpId(
			long employeeId) {
		List<Object[]> dataList = employeeRoleMasterDao
				.getActiveEmployeeRoleByEmpId(employeeId);
		return convertObjectsToEmployeeRoles(dataList);

	}

	public List<EmployeeRoleMasterModel> convertObjectsToEmployeeRoles(
			List<Object[]> dataList) {
		List<EmployeeRoleMasterModel> modelList = new ArrayList<EmployeeRoleMasterModel>();

		for (int i = 0; i < dataList.size(); i++) {
			EmployeeRoleMasterModel employeeRoleMasterModel = new EmployeeRoleMasterModel();
			Object[] obj = dataList.get(i);
			Object obj1 = obj[6];
			// System.out.println(obj.length);

			employeeRoleMasterModel.setNumId(Long.parseLong("" + obj[0]));
			employeeRoleMasterModel.setEncId(encryptionService.encrypt(""+obj[0]));
					
			employeeRoleMasterModel.setStrEmpName("" + obj[1]);
			employeeRoleMasterModel.setStrRoleName("" + obj[2]);
			employeeRoleMasterModel.setStrOrganisationName("" + obj[3]);
			employeeRoleMasterModel.setStrGroupName("" + obj[4]);
			employeeRoleMasterModel.setStrStartDate(DateUtils
					.dateToString((Date) obj[5]));
			if (obj1 != null) {
				employeeRoleMasterModel.setStrEndDate(DateUtils
						.dateToString((Date) obj[6]));
			}
			employeeRoleMasterModel.setNumEmpId(Long.parseLong("" + obj[7]));
			employeeRoleMasterModel.setEncEmployeeId(encryptionService.encrypt(""+obj[7]));
			employeeRoleMasterModel
					.setNumRoleId(Integer.parseInt("" + obj[8]));
			employeeRoleMasterModel.setEncRoleId(encryptionService.encrypt(""+obj[8]));
			employeeRoleMasterModel.setNumOrganisationId((Integer) obj[9]);
			employeeRoleMasterModel.setNumGroupId((Integer) obj[10]);
			employeeRoleMasterModel.setNumProjectId((Integer) obj[11]);
			/*----  Get Project Details by Id and set Project ref number [ 21-05-2024 ] ----------*/
			ProjectMasterModel projectDetails = projectMasterService.getProjectMasterModelById(Long.valueOf((Integer)obj[11]));
			employeeRoleMasterModel.setProjectRefNo(projectDetails.getProjectRefNo());
			/*-----  End of Comment [ 21-05-2024 ] ----------*/
			employeeRoleMasterModel.setEncProjectId(encryptionService.encrypt(""+obj[11]));
			employeeRoleMasterModel.setStrProjectName("" + obj[12]);
			employeeRoleMasterModel.setPrimaryProject(Integer.parseInt(""+obj[13]));
			
			if(obj.length>= 16){			
				if((Integer)obj[14]!=null){
					employeeRoleMasterModel.setNumManReqId((Integer)obj[14]);
				}
				if(obj[15]!=null){
					employeeRoleMasterModel.setStrManReqDetails(""+obj[15]);
				}else{
				employeeRoleMasterModel.setStrManReqDetails("");
				}
				
				if(obj[16] != null){
					employeeRoleMasterModel.setInvolvement(""+obj[16]);
				}
			}
			if(obj.length == 15){
				employeeRoleMasterModel.setInvolvement(""+obj[14]);
			}
			modelList.add(employeeRoleMasterModel);

		}

		return modelList;
	}
	
	@Override
	public int getEmployeeHighestRoleHierarchy(long employeeId){
		return employeeRoleMasterDao.getEmployeeHighestRoleHierarchy(employeeId);
	}
	
	@Override
	public String getDistinctOrganisationsForEmployee(long employeeId){
		return employeeRoleMasterDao.getDistinctOrganisationsForEmployee(employeeId);
	}
	
	@Override
	public String getDistinctGroupsForEmployee(long employeeId){
		return employeeRoleMasterDao.getDistinctGroupsForEmployee(employeeId);
	}
	@Override
	public String getDistinctProjectsForEmployee(long employeeId){
		return employeeRoleMasterDao.getDistinctProjectsForEmployee(employeeId);
	}
	
	@Override
	public @ResponseBody JSONObject getProjectTeamDetails(String encProjectId){
		String strProjectId = encryptionService.dcrypt(encProjectId);
		List<Object[]> dataList = employeeRoleMasterDao.getProjectTeamDetails(strProjectId);
		Map<String,List<JSONObject>> map = new HashMap<String,List<JSONObject>>();
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONObject json = new JSONObject();
			json.put("name", ""+obj[1]);
			json.put("title", ""+obj[2]);
			
			if(map.containsKey(""+obj[0])){
				List<JSONObject> list = map.get(""+obj[0]);
				list.add(json);
				map.put(""+obj[0],list);
			}else{
				List<JSONObject> list =new ArrayList<JSONObject>();
				list.add(json);
				map.put(""+obj[0],list);
			}
		}

		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		JSONObject finalData = new JSONObject();	 
		
		for(int i=0;i<keys.size();i++){
			
			List<JSONObject> list = map.get(keys.get(i));
			
			if(i == 0){
				finalData = list.get(0);
			}else{
				JSONArray array = new JSONArray();
				for(int j = 0;j<list.size();j++){
					array = array.put(list.get(j));
				}				
				if(finalData.has("children")){
					JSONArray  jsonArray = finalData.getJSONArray("children");					
					JSONObject jsonObject =  jsonArray.getJSONObject(0);
					if(jsonObject.has("children")){
						JSONArray tempArray =  jsonObject.getJSONArray("children");
						JSONObject tempObject = tempArray.getJSONObject(0);
						if(tempObject.has("children")){
							//JSONArray tempArrayFinal =  jsonObject.getJSONArray("children");
							//JSONObject tempObjectFinal = tempArrayFinal.getJSONObject(0);
							//tempObjectFinal.put("children", array);
							/*---------------------------  For More Levels --------------------------------------------*/
				            JSONArray tempArrayFinal = tempObject.getJSONArray("children");
				            for (int k = 0; k < tempArrayFinal.length(); k++) {
				                JSONObject tempObjectFinal = tempArrayFinal.getJSONObject(k);
				                if (tempObjectFinal.has("children")) {
				                    tempObjectFinal.put("children", array);
				                } else {
				                    tempObjectFinal.put("children", array);
				                }
				            }
							/*---------------------------------------------------------------------------*/
						}else{
							tempObject = tempObject.put("children", array);
						}
					}else{
						jsonObject = jsonObject.put("children", array);
					}
				}else{
					finalData = finalData.put("children", array);
				}
			}			
		}		
		return finalData;
	}
	
	@Override
	public List<Object[]> getEmployeeFromProject(int month,int year){
		//Getting Last Date of month
		String startDate= "01";
		if(month<10){
			startDate+="/0"+month;
		}else{
			startDate+="/"+month;
		}		
		startDate+="/"+year;
		
		String endDate = DateUtils.getLastDateOfMonth(startDate);
		
		if(month<10){
			endDate+="/0"+month;
		}else{
			endDate+="/"+month;
		}
		endDate+="/"+year;
		
		return employeeRoleMasterDao.getEmployeeFromProject(startDate, endDate);		 
	}

	
	public EmployeeRoleMasterModel getDefaultEmployeeRoleByEmpId(long employeeId) {
		
		List<Object[]> defaultRole = employeeRoleMasterDao
				.getDefaultEmployeeRoleByEmpId(employeeId);
		if(defaultRole.size()>0)
		return convertSingleObjectToEmployeeRole(defaultRole);
		else return null;
	}

	private EmployeeRoleMasterModel convertSingleObjectToEmployeeRole(
			List<Object[]> defaultRole) {
		
		EmployeeRoleMasterModel EmployeeRoleMasterModel1 = new EmployeeRoleMasterModel();
		Object[] obj = defaultRole.get(0);

		EmployeeRoleMasterModel1.setNumId(Long.parseLong("" + obj[0]));
		EmployeeRoleMasterModel1.setStrEmpName("" + obj[1]);
		EmployeeRoleMasterModel1.setStrRoleName("" + obj[2]);
		EmployeeRoleMasterModel1.setStrOrganisationName("" + obj[3]);
		EmployeeRoleMasterModel1.setStrGroupName("" + obj[4]);
		if(obj[5]!=null)
		EmployeeRoleMasterModel1.setStrStartDate(DateUtils
				.dateToString((Date) obj[5]));
		if (obj[6] != null) {
			EmployeeRoleMasterModel1.setStrEndDate(DateUtils
					.dateToString((Date) obj[6]));
		}
		EmployeeRoleMasterModel1.setNumEmpId(Long.parseLong("" + obj[7]));
		EmployeeRoleMasterModel1
				.setNumRoleId(Integer.parseInt("" + obj[8]));
		EmployeeRoleMasterModel1.setNumOrganisationId((Integer) obj[9]);
		EmployeeRoleMasterModel1.setNumGroupId((Integer) obj[10]);
		EmployeeRoleMasterModel1.setNumProjectId((Integer) obj[11]);
		EmployeeRoleMasterModel1.setStrProjectName("" + obj[12]);

	

	return EmployeeRoleMasterModel1;
	}


	
	public List<EmployeeRoleMasterModel> getEmployeeRoleMasterDomain(long empId) {
		return convertEmployeeRoleMasterDomainToModelList(employeeRoleMasterDao.getEmployeeRoleMasterDomain(empId));
			
	}

	
	public String checkDuplicateEmpRoleMstDetailsData(EmployeeRoleMasterModel employeeRoleMasterModel) {
		String result=null;
			List<Object[]> list=employeeRoleMasterDao.getAllEmpRole(employeeRoleMasterModel);
			if(list.size()>0){
				double totalInvolvement = 0;
				String strEndDate= null;
				String strStartDate = employeeRoleMasterModel.getStrStartDate();
				if(employeeRoleMasterModel.getStrEndDate().equals("")){
					ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(employeeRoleMasterModel.getNumProjectId());
					strEndDate = projectMasterForm.getEndDate();
				}else{
					strEndDate = employeeRoleMasterModel.getStrEndDate();
				}
				if(strEndDate.equals("")){
					strEndDate = null;
				}
			
					for (Object[] obj : list) {
						String roleStartDate = DateUtils.dateToString((Date)obj[0]);
						String roleEndDate="";
						if(null != obj[1]){
							roleEndDate=DateUtils.dateToString((Date)obj[1]);
						}else{
							roleEndDate=DateUtils.dateToString((Date)obj[7]);
						}		
						/*---------------- [09-10-2023]-------------------------------------*/
						try{
							if(true == employeeRoleMasterDao.checkforDateOverlap(strStartDate,strEndDate,roleStartDate,roleEndDate)){
								totalInvolvement += Double.parseDouble(""+obj[4]);
							}	
						}catch(Exception e){
							
						}
						/*----------------------------------------------------*/
					}
				
				
				if((totalInvolvement + Double.parseDouble(employeeRoleMasterModel.getInvolvement())) >100){
					result = "Involvement will exceed max  allowed Range of 100%. Employee is booked for "+totalInvolvement +"% already";
				}				 
			}
			if(null == result){
				//Check against Requirement
				if(employeeRoleMasterModel.getNumManReqId()>0){
					double involvement = Double.parseDouble(employeeRoleMasterModel.getInvolvement());
						
						if(involvement > 100 || involvement <0){
							result ="Allowed Requested involvement can not be more than 100 and less than 0";
						}else{						
						long requirementId = employeeRoleMasterModel.getNumManReqId();
						ManpowerRequirementDomain manpowerRequirementDomain = manpowerRequirementDao.getManpowerRequirementById(requirementId);
						if(null != manpowerRequirementDomain && manpowerRequirementDomain.getNumIsValid() ==1){
							long totalAllowedInvolvement = manpowerRequirementDomain.getCount()* manpowerRequirementDomain.getInvolvement();
							List<EmployeeRoleMasterDomain> employeeRoles = employeeRoleMasterDao.getAllocatedInvolvement(employeeRoleMasterModel);					
							
							if(null != employeeRoles && employeeRoles.size()>0){
								ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(employeeRoleMasterModel.getNumProjectId());
								double totalInvolvement = 0;
								String strEndDate= null;
								String strStartDate = employeeRoleMasterModel.getStrStartDate();
								if(employeeRoleMasterModel.getStrEndDate().equals("")){
									
									strEndDate = projectMasterForm.getEndDate();
								}else{
									strEndDate = employeeRoleMasterModel.getStrEndDate();
								}
								if(strEndDate.equals("")){
									strEndDate = null;
								}
							
									for (EmployeeRoleMasterDomain empRole : employeeRoles) {
										String roleStartDate = DateUtils.dateToString(empRole.getDtStartDate());
										String roleEndDate="";
										if(null != empRole.getDtEndDate()){
											roleEndDate=DateUtils.dateToString(empRole.getDtEndDate());
										}else{
											roleEndDate= projectMasterForm.getEndDate();
										}				
										
										if(true == employeeRoleMasterDao.checkforDateOverlap(strStartDate,strEndDate,roleStartDate,roleEndDate)){
											totalInvolvement += empRole.getUtilization();
										}						
									}
									double availableInvolvement = totalAllowedInvolvement-totalInvolvement;
									if(availableInvolvement < involvement){
										result = "Involement can not be more than "+availableInvolvement +" for selected Requirement";
									}
								}else{
									if(involvement>totalAllowedInvolvement){
										result ="Involement can not be more than "+totalAllowedInvolvement +" for selected Requirement";
								}
									
									
								}
						}else{
							result = "Selected Requirement does not exist";
						}
						}				
				}
			}
		return result;
	}
	

	@Override
	public JSONArray projectTeamWiseEmployees(long projectId,long roleId){
		List<Object[]> allocatedEmployees = employeeRoleMasterDao.projectTeamWiseEmployees(projectId,roleId);
		ProjectMasterDomain projectMasterDomain = projectMasterService.getProjectMasterDataById(projectId);
		Date projectStartDate = projectMasterDomain.getDtProjectStartDate();
		Date projectEndDate = projectMasterDomain.getDtProjectEndDate();
		JSONObject projectDetails = new JSONObject();
		projectDetails.put("projectStartDate", DateUtils.dateToString(projectStartDate));
		projectDetails.put("projectEndDate", DateUtils.dateToString(projectEndDate));
		
		List<Long> allocatedEmployeeList = new ArrayList<Long>();
		JSONArray finalArray = new JSONArray();
		JSONArray manpowerReqArray = new JSONArray();
		finalArray.put(projectDetails);
		List<ManpowerRequirementDomain> projectDetailsList=manpowerRequirementDao.getAllManpowerRequirment(projectId);
		if(null != projectDetailsList){
			for(int i=0;i<projectDetailsList.size();i++){
				JSONObject object = new JSONObject();
				ManpowerRequirementDomain manReq=projectDetailsList.get(i);
				object.put("type",manReq.getNumRequiredType());
				object.put("reqNumId", manReq.getNumId());
				/*---------- Get Project Based Role _[26-07-2023 added by Anuj] ------------*/
				String pbr ="";
				List<ProjectRolesMaster> data=projectMasterDao.getProjectRolesDetailsById(manReq.getNumProjectRoles());
				if(data.size()>0){
					pbr = data.get(0).getRoleName();
				}
				object.put("reqDetails",manReq.getDesignationForClient().getDesignationName()+"("+pbr+")@"+manReq.getInvolvement()+"%["+manReq.getStartDate()+" to "+manReq.getEndDate()+"]"+"No.:"+manReq.getCount());
				manpowerReqArray.put(object);
				
			}
		}
		JSONObject objectNew = new JSONObject();
		objectNew.put("arr", manpowerReqArray);
		finalArray.put(objectNew);
		if(null != allocatedEmployees){
			for(int i=0;i<allocatedEmployees.size();i++){
				Object[] obj = allocatedEmployees.get(i);
				JSONObject object = new JSONObject();
				object.put("numId", obj[0]);
				object.put("employeeId", obj[4]);
				object.put("encEmployeeId", encryptionService.encrypt(""+obj[4]));
				allocatedEmployeeList.add(Long.parseLong(""+obj[4]));
				object.put("employeeName", obj[1]);
				object.put("assignmentDate", DateUtils.dateToString((Date)obj[2]));
				String dtJioning=new String(""+obj[13]);
				int difference = DateUtils.differenceInDates(projectStartDate,dtJioning);
				if(difference>0){
					object.put("startDate", obj[13]);
				}else{
					object.put("startDate", DateUtils.dateToString(projectStartDate));
				}
				object.put("roleId", obj[14]);
				object.put("endDate", obj[3]);
				object.put("primaryProject", obj[5]);
				object.put("designationName", obj[7]);
				object.put("roleName", obj[8]);
				object.put("projectName", obj[9]);
				object.put("countProjectMapping", obj[10]);
				object.put("reqId", obj[11]);
				object.put("numInvolvment", obj[12]);
				//Bhavesh(06-7-23)(added num_deputed_at )
				object.put("num_deputed_at", obj[15]);

				finalArray.put(object);
			}
		}
		
			List<Object[]> employeeList = employeeMasterService.getAllActiveEmployeeWithCount();
		
			for(int i=0;i<employeeList.size();i++){
				Object[] obj = employeeList.get(i);
				BigInteger b=new BigInteger(""+obj[0]);
				long employeeId = b.longValue();
				String dtJioning=new String(""+obj[2]);
				//String dtJioning=c.toString();
				if(!allocatedEmployeeList.contains(employeeId)){
					JSONObject object = new JSONObject();
					object.put("numId", 0);
					object.put("employeeId", employeeId);
					object.put("encEmployeeId", encryptionService.encrypt(""+employeeId));
					object.put("employeeName", obj[1]);
					int difference = DateUtils.differenceInDates(projectStartDate,dtJioning);
					if(difference>0){
						object.put("startDate", obj[2]);
					}else{
						object.put("startDate", DateUtils.dateToString(projectStartDate));
					}
					object.put("endDate", "");
					object.put("primaryProject", 0);
					object.put("countProjectMapping", obj[3]);
					object.put("Designation", obj[4]);
					object.put("reqId", 0);
					object.put("numInvolvment",0);
					//Bhavesh(06-7-23)(added num_deputed_at )
					object.put("num_deputed_at", obj[5]);

					finalArray.put(object);
				}			
			}
			
			return finalArray;
	}
				
	
	@Override
	public long saveprojectTeamMapping(EmployeeRoleMasterModel employeeRoleMasterModel){
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		 ProjectMasterDomain projectMasterDomain = projectMasterService.getProjectMasterDataById(employeeRoleMasterModel.getNumProjectId());
		 int groupId = (int) projectMasterDomain.getApplication().getGroupMaster().getNumId();
		 int organisationId = (int) projectMasterDomain.getApplication().getGroupMaster().getOrganisationMasterDomain().getNumId();
		 
		 RoleMasterDomain roleMasterDomain = roleMasterService.getRoleDomainById(employeeRoleMasterModel.getNumRoleId());
		 
		long result =0;
		EmployeeRoleMasterDomain employeeRoleMasterDomain = new EmployeeRoleMasterDomain();
		 if(employeeRoleMasterModel.getNumId()!=0){
			 employeeRoleMasterDomain= employeeRoleMasterDao.getEmployeeRoleMasterById(employeeRoleMasterModel.getNumId());
		 }
		 employeeRoleMasterDomain.setDtTrDate(new Date());
		 employeeRoleMasterDomain.setNumIsValid(1);
		employeeRoleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		employeeRoleMasterDomain.setNumGroupId(groupId);
		employeeRoleMasterDomain.setNumOrganisationId(organisationId);
		employeeRoleMasterDomain.setNumProjectId(employeeRoleMasterModel.getNumProjectId());
		employeeRoleMasterDomain.setRoleMasterDomain(roleMasterDomain);
		if(employeeRoleMasterModel.getStrStartDate()!=null)
		{
			try {
			employeeRoleMasterDomain.setDtStartDate(DateUtils.dateStrToDate(employeeRoleMasterModel.getStrStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		if(employeeRoleMasterModel.getStrEndDate()!=null ||employeeRoleMasterModel.getStrEndDate()!=""){
		try {
			employeeRoleMasterDomain.setDtEndDate(DateUtils.dateStrToDate(employeeRoleMasterModel.getStrEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		long requirementId = employeeRoleMasterModel.getNumManReqId();
		ManpowerRequirementDomain manpowerRequirementDomain = manpowerRequirementDao.getManpowerRequirementById(requirementId);
		employeeRoleMasterDomain.setManpowerRequirementDomain(manpowerRequirementDomain);
		employeeRoleMasterDomain.setUtilization(Double.parseDouble(employeeRoleMasterModel.getInvolvement()));
		employeeRoleMasterDomain.setStrManReqDetails(employeeRoleMasterModel.getStrManReqDetails());
		employeeRoleMasterDomain.setNumEmpId(employeeRoleMasterModel.getNumEmpId());
		
				
		long numId= employeeRoleMasterDao
					.saveUpdateEmployeeRoleMaster(employeeRoleMasterDomain);
				
				if(numId>0){
					result++;
				}
		return result;
	}
	
	public EmployeeRoleMasterDomain convertJSONObjectToDomain(JSONObject jsonObject) {	

		EmployeeRoleMasterDomain employeeRoleMasterDomain = new EmployeeRoleMasterDomain();
		ManpowerRequirementDomain manpowerRequirementDomain=new ManpowerRequirementDomain();
		if (jsonObject.getLong("numId") != 0) {
			employeeRoleMasterDomain = employeeRoleMasterDao.getEmployeeRoleMasterById(jsonObject.getLong("numId"));
		}

		employeeRoleMasterDomain.setDtTrDate(new Date());
		employeeRoleMasterDomain.setNumIsValid(1);

		if (null != jsonObject.getString("startDate") 
				&& !jsonObject.getString("startDate") .equals("")) {
			try {
				employeeRoleMasterDomain.setDtStartDate(DateUtils
						.dateStrToDate(jsonObject.getString("startDate")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
				
        }

        if(jsonObject.getString("numInvolvment")!=null && !jsonObject.getString("numInvolvment").equals("") ){

                employeeRoleMasterDomain.setUtilization(jsonObject.getDouble("numInvolvment"));

        }else{
	
                employeeRoleMasterDomain.setUtilization(0);
		}
		employeeRoleMasterDomain.setPrimaryProject(jsonObject.getInt("primaryRole"));
		employeeRoleMasterDomain.setNumEmpId(jsonObject.getLong("employeeId"));
		employeeRoleMasterDomain.setNumProjectId(jsonObject.getInt("numProjectId"));
		
		if (jsonObject.getString("reqNumId")!=null && !jsonObject.getString("reqNumId").equals("")) 
		{
			manpowerRequirementDomain.setNumId(Integer.parseInt(jsonObject.getString("reqNumId")));
			employeeRoleMasterDomain.setManpowerRequirementDomain(manpowerRequirementDomain);
		}
		if (jsonObject.getString("reqDetails")!=null && !jsonObject.getString("reqDetails").equals(""))
		employeeRoleMasterDomain.setStrManReqDetails(jsonObject.getString("reqDetails"));
		else
			employeeRoleMasterDomain.setStrManReqDetails("");	
			
		return employeeRoleMasterDomain;
	}

	@Override
	public List<EmployeeRoleMasterModel> getAllTeamDetailsByProject(
			String strProjectId) {
		List<Object[]> dataList = employeeRoleMasterDao.getAllTeamDetailsByProject(strProjectId);
		List<EmployeeRoleMasterModel> outputList = null;
		if(null != dataList){
			return convertEmployeeRoleObjectToModelList(dataList);
		}
		return outputList;
	}
	
	//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
	@Override
	public List<EmployeeRoleMasterModel> getAllTeamDetailsByProjectForAllEndDate(
			String strProjectId) {
		List<Object[]> dataList = employeeRoleMasterDao.getAllTeamDetailsByProjectForAllEndDate(strProjectId);
		List<EmployeeRoleMasterModel> outputList = null;
		if(null != dataList){
			return convertEmployeeRoleObjectToModelList(dataList);
		}
		return outputList;
	}
	//End of Service
	
	public List<EmployeeRoleMasterModel> convertEmployeeRoleObjectToModelList(
			List<Object[]> employeeRoles) {
		List<EmployeeRoleMasterModel> list = new ArrayList<EmployeeRoleMasterModel>();
		for (int i = 0; i < employeeRoles.size(); i++) {
			Object[] obj = employeeRoles.get(i);
			EmployeeRoleMasterModel employeeRoleMasterModel = new EmployeeRoleMasterModel();

			
			if ((""+obj[5]).equals("1")) {
				employeeRoleMasterModel.setValid(true);
			} else {
				
				employeeRoleMasterModel.setValid(false);
			}			
			
			employeeRoleMasterModel.setStrEmpName(""+obj[1]);
			employeeRoleMasterModel.setStrRoleName(""+obj[2]);
			
			if(StringUtils.isNotBlank(""+obj[3])){
				Date startDate = (Date) obj[3];
				employeeRoleMasterModel.setStrStartDate(DateUtils.dateToString(startDate));
			}		
			
			if(StringUtils.isNotBlank(""+obj[4])){
				Date endDate = (Date) obj[4];
				employeeRoleMasterModel.setStrEndDate(DateUtils.dateToString(endDate));
			}
			
			
			if(StringUtils.isNotBlank(""+obj[3]) && StringUtils.isNotBlank(""+obj[4])){
				Date startDate = (Date) obj[3];
				Date endDate = (Date) obj[4];
				if(null != startDate && null != endDate){
				employeeRoleMasterModel.setDuration(DateUtils.getMonthandDaysBetweenDates(startDate, endDate));
				}
			}
			
			employeeRoleMasterModel.setEncRoleId(encryptionService.encrypt(""+obj[7]));
			employeeRoleMasterModel.setEncEmployeeId(encryptionService.encrypt(""+obj[6]));
			employeeRoleMasterModel.setEncId(encryptionService.encrypt(""+obj[8]));
			if(null != obj[9]){
				employeeRoleMasterModel.setManpowerRequirementId((Integer) obj[9]);
			}else{
				employeeRoleMasterModel.setManpowerRequirementId(-1);
			}
			employeeRoleMasterModel.setDesignation(""+obj[10]);
			employeeRoleMasterModel.setStrEmploymentStatus(""+obj[12]);
			employeeRoleMasterModel.setNumDeputedAt((int)obj[13]);
			employeeRoleMasterModel.setInvolvement(""+obj[14]); // Set Involvment Variable
			list.add(employeeRoleMasterModel);
		}
		return list;
	}

	@Override
	public JSONArray getManpowerRequiermentDetails(long numProjectId) {
		List<ManpowerRequirementDomain> projectDetailsList=manpowerRequirementDao.getAllManpowerRequirment(numProjectId);
		JSONArray finalArray = new JSONArray();
		
		
		if(null != projectDetailsList){
			for(int i=0;i<projectDetailsList.size();i++){
				JSONObject object = new JSONObject();
				ManpowerRequirementDomain manReq=projectDetailsList.get(i);
				String pbr ="";
				List<ProjectRolesMaster> data=projectMasterDao.getProjectRolesDetailsById(manReq.getNumProjectRoles());
				if(data.size()>0){
					pbr = data.get(0).getRoleName();
				}
				object.put("reqNumId", manReq.getNumId());
				object.put("reqDetails", manReq.getDesignationForClient().getDesignationName()+"("+pbr+")@"+manReq.getInvolvement()+"%["+manReq.getStartDate()+" to "+manReq.getEndDate()+"]"+"No.:"+manReq.getCount());
				object.put("reqType", manReq.getNumRequiredType());
				object.put("reqInvolvement", manReq.getInvolvement());
				object.put("reqStartDate", manReq.getStartDate());
				object.put("reqEndDate", manReq.getEndDate());
				object.put("reqRole", manReq.getDesignationForClient().getNumDesignationId());
				finalArray.put(object);
				
			}
		}
		
		return finalArray;
		
	}

	@Override
	public List<EmployeeRoleMasterModel> getAllTeamMembers(){
		List<Object[]> dataList = employeeRoleMasterDao.getAllTeamMembers();
		List<EmployeeRoleMasterModel> finalList = new ArrayList<EmployeeRoleMasterModel>();
		
		for(Object[] obj :dataList){
			EmployeeRoleMasterModel model = new EmployeeRoleMasterModel();
			model.setStrEmpName(""+obj[0]);
			model.setDesignation(""+obj[1]);
			model.setStrProjectName(""+obj[2]);
			finalList.add(model);
		}
		return finalList;
	}
	
	//Added by devesh on 30-11-23 to get team members according to projects
	@Override
	public List<EmployeeRoleMasterModel> getAllTeamMembersProjectWise(){
		List<Object[]> dataList = employeeRoleMasterDao.getAllTeamMembersProjectWise();
		List<EmployeeRoleMasterModel> finalList = new ArrayList<EmployeeRoleMasterModel>();
		
		for(Object[] obj :dataList){
			EmployeeRoleMasterModel model = new EmployeeRoleMasterModel();
			model.setStrProjectName(""+obj[0]);
			model.setDesignation(""+obj[1]);
			model.setStrEmpName(""+obj[2]);
			finalList.add(model);
		}
		return finalList;
	}
	//End of Function
	
	@Override
	public List<Object[]> getTeamMIdsName(){ 
		List<Object[]> empList =employeeRoleMasterDao.getTeamMIdName();
		
		List<EmployeeRoleMasterModel> list = new ArrayList<EmployeeRoleMasterModel>();
		
		for(int i=0; i<list.size();i++)
		{
			Object[] obj = empList.get(i);
			
			
			
		}
		
		 return empList;
	}
	
		
	@Override
	public List<EmployeeRoleMasterModel> getActiveEmployeeRoleByEmpIdNew(
			long employeeId) {
		List<Object[]> dataList = employeeRoleMasterDao
				.getActiveEmployeeRoleByEmpIdWithoutEndDate(employeeId);
		return convertObjectsToEmployeeRolesNew(dataList);

	}

	public List<EmployeeRoleMasterModel> convertObjectsToEmployeeRolesNew(
			List<Object[]> dataList) {
		List<EmployeeRoleMasterModel> modelList = new ArrayList<EmployeeRoleMasterModel>();

		for (int i = 0; i < dataList.size(); i++) {
			EmployeeRoleMasterModel employeeRoleMasterModel = new EmployeeRoleMasterModel();
			Object[] obj = dataList.get(i);
			Object obj1 = obj[6];
			// System.out.println(obj.length);

			employeeRoleMasterModel.setNumId(Long.parseLong("" + obj[0]));
			employeeRoleMasterModel.setEncId(encryptionService.encrypt(""+obj[0]));
					
			employeeRoleMasterModel.setStrEmpName("" + obj[1]);
			employeeRoleMasterModel.setStrRoleName("" + obj[2]);
			/*employeeRoleMasterModel.setStrOrganisationName("" + obj[3]);
			employeeRoleMasterModel.setStrGroupName("" + obj[4]);*/
			employeeRoleMasterModel.setStrStartDate(DateUtils
					.dateToString((Date) obj[3]));
			if (obj1 != null) {
				employeeRoleMasterModel.setStrEndDate(DateUtils
						.dateToString((Date) obj[4]));
			}
			employeeRoleMasterModel.setNumEmpId(Long.parseLong("" + obj[5]));
			employeeRoleMasterModel.setEncEmployeeId(encryptionService.encrypt(""+obj[5]));
			employeeRoleMasterModel
					.setNumRoleId(Integer.parseInt("" + obj[6]));
			employeeRoleMasterModel.setEncRoleId(encryptionService.encrypt(""+obj[6]));
			employeeRoleMasterModel.setNumOrganisationId((Integer) obj[7]);
			employeeRoleMasterModel.setNumGroupId((Integer) obj[8]);
			employeeRoleMasterModel.setNumProjectId((Integer) obj[9]);
			employeeRoleMasterModel.setEncProjectId(encryptionService.encrypt(""+obj[9]));
			employeeRoleMasterModel.setStrProjectName("" + obj[10]);
			employeeRoleMasterModel.setPrimaryProject(Integer.parseInt(""+obj[11]));
			
			if(obj.length>= 14){			
				if((Integer)obj[12]!=null){
					employeeRoleMasterModel.setNumManReqId((Integer)obj[12]);
				}
				if(obj[13]!=null){
					employeeRoleMasterModel.setStrManReqDetails(""+obj[13]);
				}else{
				employeeRoleMasterModel.setStrManReqDetails("");
				}
				
				if(obj[14] != null){
					employeeRoleMasterModel.setInvolvement(""+obj[14]);
				}
			}
			if(obj.length == 13){
				employeeRoleMasterModel.setInvolvement(""+obj[12]);
			}
			modelList.add(employeeRoleMasterModel);

		}

		return modelList;
	}
	
	public List<EmployeeRoleMasterModel> getClosedProjectEmpListService(long projectId)
	{	 
		
if(projectId!=0)
    {	
			
		
			List<Object[]> employeelist = employeeRoleMasterDao.getClosedProjectEmpListDao(projectId);
			List<EmployeeRoleMasterModel> list = new ArrayList<EmployeeRoleMasterModel>();
			if(employeelist!=null)
			{
			
				for(Object[] obj :employeelist){
					Date startdate=null;
					Date enddate=null;
					EmployeeRoleMasterModel model = new EmployeeRoleMasterModel();
					model.setStrEmpName(""+obj[0]);
					model.setStrEmp(""+obj[1]);
					model.setDesignation(""+obj[2]);
					
					if (obj[3] instanceof Date) {
				        startdate = (Date) obj[3];
					model.setStrStartDate(DateUtils.dateToString(startdate));
					}
					if (obj[4] instanceof Date) {
				         enddate = (Date) obj[4];
					model.setStrEndDate(DateUtils.dateToString(enddate));
					}
					if(startdate!=null && enddate!=null )
						model.setDuration(DateUtils.getMonthandDaysBetweenDates(startdate,enddate));
					//model.setStrEndDate(""+obj[4]);
					model.setInvolvement(""+obj[5]);
					
					//Added by devesh on 26-10-23 to set release date and technical/financial release bifurcation
					if (obj[6] instanceof Date) {
				        startdate = (Date) obj[6];
				        model.setStrApplicationName(DateUtils.dateToString(startdate));
					}
					if (obj[7] instanceof Date) {
				        startdate = (Date) obj[7];
				        model.setStrOrganisationName(DateUtils.dateToString(startdate));
					}
					model.setIdCheck(""+obj[8]);
					//End of setting values in modal
					
					//model.setStrProjectName(""+obj[2]);
					list.add(model);
				}
			
			return list;
			}
	      
			
		}
		
		return null;
	}
	
	@Override
	public List<EmployeeRoleMasterModel> getAllTempTeamDetailsByProject(
			String strProjectId) {
		List<Object[]> dataList = employeeRoleMasterDao.getAllTempTeamDetailsByProject(strProjectId);
		List<EmployeeRoleMasterModel> outputList = null;
		if(null != dataList){
			return convertEmployeeRoleObjectToModelList(dataList);
		}
		return outputList;
	}
	
}