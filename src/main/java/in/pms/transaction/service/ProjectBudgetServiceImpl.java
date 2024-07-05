package in.pms.transaction.service;

import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectBudgetDao;
import in.pms.master.domain.ProjectBudgetDomain;
import in.pms.master.domain.ProjectBudgetDomainTemp;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.model.BudgetHeadMasterForm;
import in.pms.transaction.model.ProjectBudgetForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class ProjectBudgetServiceImpl implements ProjectBudgetService{
	
	@Autowired
	ProjectBudgetDao projectbudgetDao;	
	@Autowired
	ProjectMasterService projectMasterService;
	@Autowired
	BudgetHeadMasterService budgetHeadMasterService;
	

	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_BUDGET_MST')")
	public long saveProjectDetailsData(ProjectBudgetForm projectMasterForm){
		long result=0;
		ProjectBudgetDomainTemp projectBudgetDomainTemp = convertProjectBudgetFormToTempDomain(projectMasterForm);
		if(null != projectBudgetDomainTemp){
			 result = 	projectbudgetDao.saveTempProjectDetails(projectBudgetDomainTemp);
		}
				
		 return result;
	}
	
	public ProjectBudgetDomainTemp convertProjectBudgetFormToTempDomain(ProjectBudgetForm projectBudgetForm){
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectBudgetDomainTemp projectBudgetDomainTemp = new ProjectBudgetDomainTemp();
		
		long numId = 0;
		int budgetHeadId=0;
		int year =0;
		
		String key = projectBudgetForm.getKey();
		String[] inputData = key.split("_");
		if(inputData.length == 2){
			budgetHeadId = Integer.parseInt(inputData[0]);
			year = Integer.parseInt(inputData[1]);
		}else if (inputData.length == 3){
			budgetHeadId = Integer.parseInt(inputData[0]);
			year = Integer.parseInt(inputData[1]);
			numId= Long.parseLong(inputData[2]);
		}
		
			if(numId!=0){
				projectBudgetDomainTemp =projectbudgetDao.getTempBudgetDataById(numId);
			}else{
				projectBudgetDomainTemp = projectbudgetDao.getTempProjectBudgetByBudgetHeadIdYear(budgetHeadId,year);
			}
			
			if(projectBudgetDomainTemp.getNumId()==0 && projectBudgetForm.getAmount()==0){
				return null;
			}
			
			projectBudgetDomainTemp.setNumIsValid(1);			
			projectBudgetDomainTemp.setProjectId(projectBudgetForm.getNumProjectId());
			projectBudgetDomainTemp.setNumYear(year);
			projectBudgetDomainTemp.setNumAmount(projectBudgetForm.getAmount());
			projectBudgetDomainTemp.setNumBudgetHeadId(budgetHeadId);
			projectBudgetDomainTemp.setDtTrDate(new Date());
			projectBudgetDomainTemp.setNumTrUserId(userInfo.getEmployeeId());		
		return projectBudgetDomainTemp;
	}

	public List<ProjectBudgetForm> convertBudgetMasterDomainToModelList(List<ProjectBudgetDomain> budgetDataList){		
		List<ProjectBudgetForm> list = new ArrayList<ProjectBudgetForm>();
			for(int i=0;i<budgetDataList.size();i++){
				ProjectBudgetDomain projectBudgetDomain = budgetDataList.get(i);
				ProjectMasterDomain  projectMasterDomain  = projectBudgetDomain.getProjectMasterDomain();
				ProjectBudgetForm budgetHeadMasterForm = new ProjectBudgetForm();
				budgetHeadMasterForm.setNumId(projectBudgetDomain.getNumId());
				budgetHeadMasterForm.setNumProjectId(projectMasterDomain.getNumId());
				budgetHeadMasterForm.setProjectName(projectMasterDomain.getStrProjectName());
				budgetHeadMasterForm.setYear(projectBudgetDomain.getNumYear());
				budgetHeadMasterForm.setBudgetId(projectBudgetDomain.getNumBudgetHeadId());
				//budgetHeadMasterForm.setBudgetName(projectBudgetDomain.getStrBudgetHeadName());				
				budgetHeadMasterForm.setAmount(projectBudgetDomain.getNumAmount());
				list.add(budgetHeadMasterForm);
	}
		return list;
	}
	
	@Override
	public List<ProjectBudgetForm> getBudgetDetailByProjectIdYear(long projectId,int year){
		return convertBudgetMasterDomainToModelList(projectbudgetDao.getBudgetDetailByProjectIdYear(projectId,year));			
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_BUDGET_MST')")
	public Map<String,Map<String,Float>> getProjectBudget(long projectId){
		
  	  ProjectMasterDomain projectMasterDomain=projectMasterService.getProjectMasterDataById(projectId);
	  int years = DateUtils.getDurationInYears(projectMasterDomain.getDtProjectStartDate(),projectMasterDomain.getDtProjectEndDate());

	  List<BudgetHeadMasterForm> budgetHeadList = budgetHeadMasterService.getAllActiveBudgetMasterDomain();
	  
		Map<String,Map<String,Float>> dataMap = new LinkedHashMap<String, Map<String,Float>>();		
		
		List<ProjectBudgetDomainTemp> budgetList = projectbudgetDao.getTempBudgetDetailByProjectId(projectId);
  	  	
  	  Map<String,Float> headingMap = new LinkedHashMap<String, Float>();
		
		//For Creation of Heading
		for(int i=1;i<=years;i++){
			headingMap.put("Year "+i, 0.0f);
		}
  	  	
		dataMap.put("Budget Head", headingMap);
		
		if(budgetList.size()>0){
			//Previous Details Available
			for(BudgetHeadMasterForm budgetHeadMasterForm :budgetHeadList){
				Map<String,Float> map = new LinkedHashMap<String, Float>();
				for(int i=1;i<=years;i++){
					 ProjectBudgetDomainTemp projectBudgetDomainTemp = filterTempProjectBudget( budgetList,i,budgetHeadMasterForm.getNumId());
					 if(null != projectBudgetDomainTemp){
						 String key = budgetHeadMasterForm.getNumId()+"_"+i+"_"+projectBudgetDomainTemp.getNumId();
							map.put(key, projectBudgetDomainTemp.getNumAmount());
						 
					 }else{
						 String key = budgetHeadMasterForm.getNumId()+"_"+i;
							map.put(key, 0.0f);
					 }
				}
				dataMap.put(budgetHeadMasterForm.getStrBudgetHeadName(),map);
			}
			
		}else{
			
			for(BudgetHeadMasterForm budgetHeadMasterForm :budgetHeadList){
				Map<String,Float> map = new LinkedHashMap<String, Float>();
				for(int i=1;i<=years;i++){
					String key = budgetHeadMasterForm.getNumId()+"_"+i;
					map.put(key, 0.0f);
				}
				dataMap.put(budgetHeadMasterForm.getStrBudgetHeadName(),map);
			}
			
		}
		
		return dataMap;			
	}
	
	public ProjectBudgetDomainTemp filterTempProjectBudget(List<ProjectBudgetDomainTemp> budgetList,int year,long budgetHeadId){

		ProjectBudgetDomainTemp projectBudgetDomainTemp = budgetList.stream()
	                .filter(p -> {
	                    if (year == p.getNumYear() && budgetHeadId == p.getNumBudgetHeadId()) {
	                        return true;
	                    }
	                    return false;
	                }).findAny()
	                .orElse(null);

		
		return projectBudgetDomainTemp;
	}
	
	@Override
	public boolean saveProjectBudgetDetails(ProjectBudgetForm projectBudgetForm){
		List<ProjectBudgetDomain> previousDataList = projectbudgetDao.getBudgetDetailByProjectId(projectBudgetForm.getNumProjectId());
		List<ProjectBudgetDomainTemp> newDataList = projectbudgetDao.getTempBudgetDetailByProjectId(projectBudgetForm.getNumProjectId());
		ProjectMasterDomain projectMaster = projectMasterService.getProjectMasterDataById(projectBudgetForm.getNumProjectId());
		boolean flag= false;
		try{
		for(ProjectBudgetDomainTemp projectBudgetTemp: newDataList){
			ProjectBudgetDomain projectBudgetDomain = filterProjectBudget(previousDataList,projectBudgetTemp.getNumYear(),projectBudgetTemp.getNumBudgetHeadId());
			if(null!=projectBudgetDomain){
				projectBudgetDomain = convertProjectBudgetTempToDomain(projectBudgetDomain,projectBudgetTemp);
				projectBudgetDomain.setProjectMasterDomain(projectMaster);
				projectbudgetDao.saveProjectDetailsMaster(projectBudgetDomain);
			}else{
				ProjectBudgetDomain projectBudget = new ProjectBudgetDomain();
				projectBudget = convertProjectBudgetTempToDomain(projectBudget,projectBudgetTemp);
				projectBudget.setProjectMasterDomain(projectMaster);
				projectbudgetDao.saveProjectDetailsMaster(projectBudget);
			}
		}
		flag=true;
		}catch(Exception e){
			flag=false;
		}
		return flag;	
	}
	
	public ProjectBudgetDomain filterProjectBudget(List<ProjectBudgetDomain> budgetList,int year,long budgetHeadId){

		ProjectBudgetDomain projectBudgetDomain = budgetList.stream()
	                .filter(p -> {
	                    if (year == p.getNumYear() && budgetHeadId == p.getNumBudgetHeadId()) {
	                        return true;
	                    }
	                    return false;
	                }).findAny()
	                .orElse(null);

		
		return projectBudgetDomain;
	}
	
	public ProjectBudgetDomain convertProjectBudgetTempToDomain(ProjectBudgetDomain projectBudgetDomain,ProjectBudgetDomainTemp projectBudgetDomaintemp){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		projectBudgetDomain.setDtTrDate(new Date());
		projectBudgetDomain.setNumAmount(projectBudgetDomaintemp.getNumAmount());
		projectBudgetDomain.setNumBudgetHeadId(projectBudgetDomaintemp.getNumBudgetHeadId());
		if(projectBudgetDomaintemp.getNumAmount()  == 0){
			projectBudgetDomain.setNumIsValid(0);
		}else{
			projectBudgetDomain.setNumIsValid(1);
		}
		projectBudgetDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectBudgetDomain.setNumYear(projectBudgetDomaintemp.getNumYear());	
		return projectBudgetDomain;
	}
}

