package in.pms.master.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;


import in.pms.master.dao.ProjectExpenditureDetailsDao;
import in.pms.master.domain.ProjectExpenditureDetailsDomain;

import in.pms.master.model.ProjectExpenditureDetailsModel;
import in.pms.master.model.TaskDetailsModel;
import in.pms.transaction.service.BudgetHeadMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectExpenditureDetailsServiceImpl implements ProjectExpenditureDetailsService{

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ProjectExpenditureDetailsDao projectExpenditureDetailsDao;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	BudgetHeadMasterService budgetHeadMasterService;
	
	@Autowired
	ExpenditureHeadService expenditureHeadService;
	
	
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_EXPENDITURE_DETAILS')")
	public long saveUpdateProjectExpenditureDetails(ProjectExpenditureDetailsModel projectExpenditureDetailsModel){
		ProjectExpenditureDetailsDomain projectExpenditureDetailsDomain = convertProjectExpenditureDetailsModelToDomain(projectExpenditureDetailsModel);
		return projectExpenditureDetailsDao.saveUpdateProjectExpenditureDetails(projectExpenditureDetailsDomain);
	}
		
	
	
	
	
	@Override
	public ProjectExpenditureDetailsModel getProjectExpenditureDetailsDomainById(long numId){
		return convertEmployeeRoleMasterDomainToModel(projectExpenditureDetailsDao.getProjectExpenditureDetailsById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_EXPENDITURE_DETAILS')")
	public List<ProjectExpenditureDetailsModel> getAllProjectExpenditureDetailsDomain(){
		return convertProjectExpenditureDetailsDomainToModelList(projectExpenditureDetailsDao.getAllProjectExpenditureDetailsDomain());
	}
	
	@Override
	public List<ProjectExpenditureDetailsModel> getAllActiveProjectExpenditureDetailsDomain(){
		return convertProjectExpenditureDetailsDomainToModelList(projectExpenditureDetailsDao.getAllActiveProjectExpenditureDetailsDomain());
	}
	

	
	public ProjectExpenditureDetailsDomain convertProjectExpenditureDetailsModelToDomain(ProjectExpenditureDetailsModel projectExpenditureDetailsModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectExpenditureDetailsDomain projectExpenditureDetailsDomain = new ProjectExpenditureDetailsDomain();
		if(projectExpenditureDetailsModel.getNumId()!=0){				
			projectExpenditureDetailsDomain =  projectExpenditureDetailsDao.getProjectExpenditureDetailsById(projectExpenditureDetailsModel.getNumId());
		}
		
		projectExpenditureDetailsDomain.setDtTrDate(new Date());
		projectExpenditureDetailsDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(projectExpenditureDetailsModel.isValid()){
			projectExpenditureDetailsDomain.setNumIsValid(1);
		}else{
			projectExpenditureDetailsDomain.setNumIsValid(0);
		}
		
		
	
		
		if(null != projectExpenditureDetailsModel.getDtExpenditureDate()){
			try {
				projectExpenditureDetailsDomain.setDtExpenditureDate(DateUtils.dateStrToDate(projectExpenditureDetailsModel.getDtExpenditureDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
			
		projectExpenditureDetailsDomain.setProjectMasterDomain(projectMasterService.getProjectMasterDataById(projectExpenditureDetailsModel.getNumProjectId()));
		//projectExpenditureDetailsDomain.setNumProjectId(projectExpenditureDetailsModel.getNumProjectId());
		
		projectExpenditureDetailsDomain.setNumBudgetHeadId(projectExpenditureDetailsModel.getNumBudgetHeadId());
		projectExpenditureDetailsDomain.setNumExpenditureHeadId(projectExpenditureDetailsModel.getNumExpenditureHeadId());
		projectExpenditureDetailsDomain.setNumExpenditureAmount(projectExpenditureDetailsModel.getNumExpenditureAmount());
		projectExpenditureDetailsDomain.setStrExpenditureDescription(projectExpenditureDetailsModel.getStrExpenditureDescription());
	
		return projectExpenditureDetailsDomain;
	}
	
	public List<ProjectExpenditureDetailsModel> convertProjectExpenditureDetailsDomainToModelList(List<ProjectExpenditureDetailsDomain> projectExpenditureDetailsList){
		List<ProjectExpenditureDetailsModel> list = new ArrayList<ProjectExpenditureDetailsModel>();
			for(int i=0;i<projectExpenditureDetailsList.size();i++){
				ProjectExpenditureDetailsDomain projectExpenditureDetailsDomain = projectExpenditureDetailsList.get(i);
				ProjectExpenditureDetailsModel projectExpenditureDetailsModel = new ProjectExpenditureDetailsModel();
				
				if(projectExpenditureDetailsDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+projectExpenditureDetailsDomain.getNumId());
					projectExpenditureDetailsModel.setEncEmpId(encryptedId);
				}
				projectExpenditureDetailsModel.setNumId(projectExpenditureDetailsDomain.getNumId());
				if(projectExpenditureDetailsDomain.getNumIsValid() ==1){
					projectExpenditureDetailsModel.setValid(true);
				}else{
					projectExpenditureDetailsModel.setValid(false);
				}
			
				//projectExpenditureDetailsModel.setNumProjectId(projectExpenditureDetailsDomain.getNumProjectId());
				projectExpenditureDetailsModel.setNumProjectId(projectExpenditureDetailsDomain.getProjectMasterDomain().getNumId());
			
				projectExpenditureDetailsModel.setNumBudgetHeadId(projectExpenditureDetailsDomain.getNumBudgetHeadId());
				projectExpenditureDetailsModel.setNumExpenditureHeadId(projectExpenditureDetailsDomain.getNumExpenditureHeadId());
				projectExpenditureDetailsModel.setNumExpenditureAmount(projectExpenditureDetailsDomain.getNumExpenditureAmount());
				projectExpenditureDetailsModel.setStrExpenditureDescription(projectExpenditureDetailsDomain.getStrExpenditureDescription());
				
				/*projectExpenditureDetailsModel.setNumProjectId(projectExpenditureDetailsDomain.getNumProjectId());*/
			
				//projectExpenditureDetailsModel.setNumProjectId(projectMasterService.getProjectMasterDomainById(projectExpenditureDetailsDomain.getProjectMasterDomain()));
				
				/*employeeRoleMasterModel.setStrEmpName(employeeMasterService.getEmployeeMasterDomainById(employeeRoleMasterDomain.getNumEmpId()));
				employeeRoleMasterModel.setStrRoleName(roleMasterService.getRoleMasterDomainById(employeeRoleMasterDomain.getRoleMasterDomain()));
				employeeRoleMasterModel.setStrOrganisationName(organisationMasterService.getOrganisationMasterDomainById(employeeRoleMasterDomain.getNumOrganisationId()));
				employeeRoleMasterModel.setStrGroupName(groupMasterService.getGroupMasterDomainById(employeeRoleMasterDomain.getNumGroupId()));
				*/
				list.add(projectExpenditureDetailsModel);
	}
		return list;
	}

	
	
	@Override
	public List<ProjectExpenditureDetailsModel> getProjectExpenditureDetailsByProjectId(long numProjectId) {		
			List<ProjectExpenditureDetailsDomain> projectExpenditureDetailsDomain = projectExpenditureDetailsDao.getProjectExpenditureDetailsByProjectId(numProjectId);
			List<ProjectExpenditureDetailsModel> projectExpenditureDetailsModelList = convertProjectExpenditureDetailsDomainToModelList(projectExpenditureDetailsDomain);
			return projectExpenditureDetailsModelList;
	}
	
	public ProjectExpenditureDetailsModel convertEmployeeRoleMasterDomainToModel(ProjectExpenditureDetailsDomain projectExpenditureDetailsDomain){
		ProjectExpenditureDetailsModel projectExpenditureDetailsModel = new ProjectExpenditureDetailsModel();
	
		if(projectExpenditureDetailsDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+projectExpenditureDetailsDomain.getNumId());
			projectExpenditureDetailsModel.setEncEmpId(encryptedId);
		}
		projectExpenditureDetailsModel.setNumId(projectExpenditureDetailsDomain.getNumId());
		if(projectExpenditureDetailsDomain.getNumIsValid() ==1){
			projectExpenditureDetailsModel.setValid(true);
		}else{
			projectExpenditureDetailsModel.setValid(false);
		}
	
		//projectExpenditureDetailsModel.setNumProjectId(projectExpenditureDetailsDomain.getNumProjectId());
		projectExpenditureDetailsModel.setNumProjectId(projectExpenditureDetailsDomain.getProjectMasterDomain().getNumId());
		projectExpenditureDetailsModel.setNumBudgetHeadId(projectExpenditureDetailsDomain.getNumBudgetHeadId());
		projectExpenditureDetailsModel.setNumExpenditureHeadId(projectExpenditureDetailsDomain.getNumExpenditureHeadId());
		projectExpenditureDetailsModel.setNumExpenditureAmount(projectExpenditureDetailsDomain.getNumExpenditureAmount());
		projectExpenditureDetailsModel.setStrExpenditureDescription(projectExpenditureDetailsDomain.getStrExpenditureDescription());
		
		
		
		return projectExpenditureDetailsModel;
		
	}
	
	@Override
	public List<ProjectExpenditureDetailsModel> getExpenditureDetailsByProjectId(long numProjectId) {	
		return convertProjectExpendituretoModelList(projectExpenditureDetailsDao.getExpenditureDetailsByProjectId(numProjectId));
	}
	
	public List<ProjectExpenditureDetailsModel> convertProjectExpendituretoModelList(List<Object[]>formNoList){
			List<ProjectExpenditureDetailsModel> modelList=new ArrayList<ProjectExpenditureDetailsModel>();
			int totalFormNo=formNoList.size();
		 for(int i=0;i<totalFormNo;i++)
		 {
			 ProjectExpenditureDetailsModel projectExpenditureDetailsModel1 =new ProjectExpenditureDetailsModel();
			 Object[] obj=formNoList.get(i);
			// System.out.println(obj.length);
			 projectExpenditureDetailsModel1.setNumId(Long.parseLong(""+obj[0]));
			 projectExpenditureDetailsModel1.setStrExpenditureDescription(""+obj[1]);
			 projectExpenditureDetailsModel1.setNumExpenditureAmount((Double)obj[2]);
			 projectExpenditureDetailsModel1.setDtExpenditureDate(DateUtils.dateToString((Date) obj[3]));
			 projectExpenditureDetailsModel1.setStrProjectName(""+obj[4]);
			 projectExpenditureDetailsModel1.setStrBudgetHeadName(""+obj[5]);
			 projectExpenditureDetailsModel1.setStrExpenditureHeadName(""+obj[6]);
			 projectExpenditureDetailsModel1.setNumProjectId(Long.parseLong(""+obj[7]));
			 projectExpenditureDetailsModel1.setNumBudgetHeadId(Long.parseLong(""+obj[8]));
			 projectExpenditureDetailsModel1.setNumExpenditureHeadId(Long.parseLong(""+obj[9]));
			 if((Integer)obj[10]==1){
				 projectExpenditureDetailsModel1.setValid(true);
				}else{
					projectExpenditureDetailsModel1.setValid(false);
				}
			 modelList.add(projectExpenditureDetailsModel1);
			 
		 }
		 
		 
		
		return modelList;

	}
	
}



	




