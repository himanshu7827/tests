package in.pms.master.service;

import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ManpowerRequirementDao;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectRolesMaster;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ManpowerRequirementModel;
import in.pms.transaction.domain.DesignationForClient;
import in.pms.transaction.service.DesignationForClientService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class ManpowerRequirementServiceImpl implements ManpowerRequirementService{
	
	@Autowired
	ManpowerRequirementDao manpowerRequirementDao;
	
	@Autowired
	RoleMasterService roleMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	DesignationForClientService designationForClientService;
	
	@Autowired
	ProjectMasterDao projectMasterDao;

	    @Override
		@PreAuthorize("hasAuthority('WRITE_MANPOWER_MST')")
        public long saveManpowerRequirementData(ManpowerRequirementModel manpowerRequirementModel){
	    	ManpowerRequirementDomain manpowerRequirementDomain = convertManpowerModelToDomain(manpowerRequirementModel);
		return manpowerRequirementDao.saveManpowerRequirementMaster(manpowerRequirementDomain);
	}
	
	public ManpowerRequirementDomain convertManpowerModelToDomain(ManpowerRequirementModel manpowerRequirementModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ManpowerRequirementDomain manpowerRequirementDomain = new ManpowerRequirementDomain();
		if(manpowerRequirementModel.getNumId()!=0){		
			manpowerRequirementDomain =  manpowerRequirementDao.getManpowerRequirementById(manpowerRequirementModel.getNumId());
		}
		manpowerRequirementDomain.setStrDescription(manpowerRequirementModel.getStrDescription());
		manpowerRequirementDomain.setProjectMasterDomain(projectMasterDao.getProjectMasterDataById(manpowerRequirementModel.getProjectId()));
		//manpowerRequirementDomain.setRoleMasterDomain(roleMasterService.getRoleMasterById(manpowerRequirementModel.getRoleId()));
		manpowerRequirementDomain.setDesignationForClient(designationForClientService.getById(manpowerRequirementModel.getDesignationId()));
		manpowerRequirementDomain.setCount(manpowerRequirementModel.getCount());
		manpowerRequirementDomain.setInvolvement(manpowerRequirementModel.getInvolvement());
		manpowerRequirementDomain.setNumDeputedAt(manpowerRequirementModel.getDeputedAt());
		manpowerRequirementDomain.setNumProjectRoles(manpowerRequirementModel.getNumProjectRoles());
		try{
		manpowerRequirementDomain.setStartDate(DateUtils.dateStrToDate(manpowerRequirementModel.getStartDate()));
		}
		catch (ParseException e) {				
			e.printStackTrace();
		}
		try{
		manpowerRequirementDomain.setEndDate(DateUtils.dateStrToDate(manpowerRequirementModel.getEndDate()));
		}
		catch (ParseException e) {				
			e.printStackTrace();
		}
		manpowerRequirementDomain.setDtTrDate(new Date());
		manpowerRequirementDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(manpowerRequirementModel.isValid()){
			manpowerRequirementDomain.setNumIsValid(1);
			}else{
				manpowerRequirementDomain.setNumIsValid(0);
			}
	
		if(null != manpowerRequirementModel.getRatePerManMonth() && !manpowerRequirementModel.getRatePerManMonth().equals("")){
			manpowerRequirementDomain.setRatePerManMonth(Double.parseDouble(manpowerRequirementModel.getRatePerManMonth()));
		}else{
			manpowerRequirementDomain.setRatePerManMonth(0.0);
		}
		
		if(null != manpowerRequirementModel.getActualRatePerManMonth() && !manpowerRequirementModel.getActualRatePerManMonth().equals("")){
			manpowerRequirementDomain.setActualRatePerManMonth(Double.parseDouble(manpowerRequirementModel.getActualRatePerManMonth()));
		}else{
			manpowerRequirementDomain.setActualRatePerManMonth(0.0);
		}
		
		manpowerRequirementDomain.setPurpose(manpowerRequirementModel.getPurpose());
		if(null != manpowerRequirementModel.getRequiredType()) {
			manpowerRequirementDomain.setNumRequiredType(Integer.parseInt(manpowerRequirementModel.getRequiredType()));
		}
		return manpowerRequirementDomain;
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_MANPOWER_MST')")
	public List<ManpowerRequirementModel> getAllManpowerRequirement(long projectId){
		return convertManpowerRequirementToModelList(manpowerRequirementDao.getAllManpowerRequirment(projectId));
	}
	
	public List<ManpowerRequirementModel> convertManpowerRequirementToModelList(List<ManpowerRequirementDomain> manpowerList){
		List<ManpowerRequirementModel> list = new ArrayList<ManpowerRequirementModel>();
			for(int i=0;i<manpowerList.size();i++){
				ManpowerRequirementDomain manpowerRequirementDomain = manpowerList.get(i);
				ManpowerRequirementModel manpowerRequirementModel = new ManpowerRequirementModel();
				
				manpowerRequirementModel.setNumId(manpowerRequirementDomain.getNumId());
				if(manpowerRequirementDomain.getNumIsValid() ==1){
					manpowerRequirementModel.setValid(true);
				}else{
					manpowerRequirementModel.setValid(false);
				}
				DesignationForClient designationForClient = manpowerRequirementDomain.getDesignationForClient();
				ProjectMasterDomain  projectMasterDomain  = manpowerRequirementDomain.getProjectMasterDomain();
				manpowerRequirementModel.setProjectId(projectMasterDomain.getNumId());
				manpowerRequirementModel.setStrDescription(manpowerRequirementDomain.getStrDescription());
				manpowerRequirementModel.setProjectName(projectMasterDomain.getStrProjectName());
				manpowerRequirementModel.setDesignationId(designationForClient.getNumDesignationId());
				manpowerRequirementModel.setDesignationName(designationForClient.getDesignationName());
				manpowerRequirementModel.setCount(manpowerRequirementDomain.getCount());
				manpowerRequirementModel.setDeputedAt(manpowerRequirementDomain.getNumDeputedAt());
				manpowerRequirementModel.setNumProjectRoles(manpowerRequirementDomain.getNumProjectRoles());
				List<ProjectRolesMaster> data=projectMasterDao.getProjectRolesDetailsById(manpowerRequirementDomain.getNumProjectRoles());
				if(data.size()>0){
				manpowerRequirementModel.setStrRolesName(data.get(0).getRoleName());
				}
				manpowerRequirementModel.setInvolvement(manpowerRequirementDomain.getInvolvement());
				manpowerRequirementModel.setStartDate(DateUtils.dateToString(manpowerRequirementDomain.getStartDate()));
				manpowerRequirementModel.setEndDate(DateUtils.dateToString(manpowerRequirementDomain.getEndDate()));
				manpowerRequirementModel.setPurpose(manpowerRequirementDomain.getPurpose());
				manpowerRequirementModel.setRatePerManMonth(CurrencyUtils.convertToINR(manpowerRequirementDomain.getRatePerManMonth()));
				manpowerRequirementModel.setRequiredType(""+manpowerRequirementDomain.getNumRequiredType());
				manpowerRequirementModel.setActualRatePerManMonth(CurrencyUtils.convertToINR(manpowerRequirementDomain.getActualRatePerManMonth()));				
				list.add(manpowerRequirementModel);
			}
			return list;
			}
	
		@Override
		public Map<String,List<EmployeeRoleMasterModel>> getManpowerRequirementWithAssignedRole(long projectId){
			Map<String,List<EmployeeRoleMasterModel>> dataMap = new LinkedHashMap<String, List<EmployeeRoleMasterModel>>();
			List<ManpowerRequirementDomain> manpowerRequirementList = manpowerRequirementDao.getAllManpowerRequirment(projectId);
			List<EmployeeRoleMasterModel> employeeRoleMasterList = employeeRoleMasterService.getAllTeamDetailsByProject(""+projectId);
			for(ManpowerRequirementDomain requirementDomain : manpowerRequirementList){
				String requirementName = requirementDomain.getDesignationForClient().getDesignationName()+"@#@ [Involvement : "+requirementDomain.getInvolvement()+"%, Number : "+requirementDomain.getCount()+" @#@ [ "+DateUtils.dateToString(requirementDomain.getStartDate())+" - "+DateUtils.dateToString(requirementDomain.getEndDate())+" ] @#@ "+CurrencyUtils.convertToINR(requirementDomain.getRatePerManMonth()) +"@#@"+ requirementDomain.getPurpose() +"@#@"+ requirementDomain.getNumDeputedAt();
				List<EmployeeRoleMasterModel> assignedEmpList = employeeRoleMasterList.stream().filter((p) -> requirementDomain.getNumId() == p.getManpowerRequirementId()).collect(Collectors.toList());
				dataMap.put(requirementName, assignedEmpList);
			}
			return dataMap;
		}
		
		//Added by devesh on 04/09/23 to get team details members including those whose end date has passed
		@Override
		public Map<String,List<EmployeeRoleMasterModel>> getManpowerRequirementWithAllAssignedRole(long projectId){
			Map<String,List<EmployeeRoleMasterModel>> dataMap = new LinkedHashMap<String, List<EmployeeRoleMasterModel>>();
			List<ManpowerRequirementDomain> manpowerRequirementList = manpowerRequirementDao.getAllManpowerRequirment(projectId);
			List<EmployeeRoleMasterModel> employeeRoleMasterList = employeeRoleMasterService.getAllTeamDetailsByProjectForAllEndDate(""+projectId);
			for(ManpowerRequirementDomain requirementDomain : manpowerRequirementList){
				String requirementName = requirementDomain.getDesignationForClient().getDesignationName()+"@#@ [Involvement : "+requirementDomain.getInvolvement()+"%, Number : "+requirementDomain.getCount()+" @#@ [ "+DateUtils.dateToString(requirementDomain.getStartDate())+" - "+DateUtils.dateToString(requirementDomain.getEndDate())+" ] @#@ "+CurrencyUtils.convertToINR(requirementDomain.getRatePerManMonth()) +"@#@"+ requirementDomain.getPurpose() +"@#@"+ requirementDomain.getNumDeputedAt();
				List<EmployeeRoleMasterModel> assignedEmpList = employeeRoleMasterList.stream().filter((p) -> requirementDomain.getNumId() == p.getManpowerRequirementId()).collect(Collectors.toList());
				dataMap.put(requirementName, assignedEmpList);
			}
			return dataMap;
		}
		//End of Service
		
		@Override
		public int deleteManpowerReq(long numId){
			List<EmployeeRoleMasterDomain> domain=manpowerRequirementDao.getManpowerByNumId(numId);
			if(domain!=null && domain.size()>0){
				return 1;
			} else{
				ManpowerRequirementDomain manpowerRequirementDomain=manpowerRequirementDao.getManpowerRequirementById(numId);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				UserInfo userInfo = (UserInfo) authentication.getPrincipal();
				manpowerRequirementDomain.setNumIsValid(2);
				manpowerRequirementDomain.setNumTrUserId(userInfo.getEmployeeId());
				manpowerRequirementDomain.setDtTrDate(new Date());
				manpowerRequirementDao.saveManpowerRequirementMaster(manpowerRequirementDomain);
				return 2;
			}
		}
}	