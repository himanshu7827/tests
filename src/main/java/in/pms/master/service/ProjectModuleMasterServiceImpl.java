package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectModuleMasterDao;
import in.pms.master.domain.ProjectModuleMasterDomain;
import in.pms.master.model.ProjectModuleMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleMasterServiceImpl implements ProjectModuleMasterService{

	
	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ProjectModuleMasterDao projectModuleMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_PROJECT_MODULE_MST')")
	public long saveUpdateProjectModuleMaster(ProjectModuleMasterModel projectModuleMasterModel){
		ProjectModuleMasterDomain projectModuleMasterDomain = convertProjectModuleMasterModelToDomain(projectModuleMasterModel);
		return projectModuleMasterDao.saveUpdateProjectModuleMaster(projectModuleMasterDomain);
	}
		
	@Override
	public String checkDuplicateProjectModuleName(ProjectModuleMasterModel projectModuleMasterModel){
		String result=	"";
		ProjectModuleMasterDomain projectModuleMasterDomain = projectModuleMasterDao.getProjectModuleMasterByName(projectModuleMasterModel.getStrModuleName(),projectModuleMasterModel.getProjectId());
		
		 if(null == projectModuleMasterDomain){
				return null; 
			 }else if(projectModuleMasterModel.getNumId() != 0){
				 if(projectModuleMasterDomain.getNumId() == projectModuleMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Project Module with same name already exist with Id "+projectModuleMasterDomain.getNumId();
				 }
			 }else{
				if(projectModuleMasterDomain.getNumIsValid() == 0){
					result = "Project Module Details already exist with Id "+projectModuleMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Project Module Details already exist with Id "+projectModuleMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ProjectModuleMasterModel getProjectModuleMasterDomainById(long numId){
		return convertProjectModuleMasterDomainToModel(projectModuleMasterDao.getProjectModuleMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_PROJECT_MODULE_MST')")
	public List<ProjectModuleMasterModel> getAllProjectModuleMasterDomain(){
		return convertProjectModuleMasterDomainToModelList(projectModuleMasterDao.getAllActiveProjectModuleMasterDomain());
	}
	
	@Override
	public List<ProjectModuleMasterModel> getAllActiveProjectModuleMasterDomain(){
		return convertProjectModuleMasterDomainToModelList(projectModuleMasterDao.getAllActiveProjectModuleMasterDomain());
	}
	
	/*@Override
	public List<ProjectModuleMasterModel> getAllActiveProjectMasterDomain(){
		return convertOrganisationMasterDomainToModelList(ProjectModuleMasterDao.getAllActiveProjectMasterDomain());
	}*/
	
	public ProjectModuleMasterDomain convertProjectModuleMasterModelToDomain(ProjectModuleMasterModel projectModuleMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ProjectModuleMasterDomain projectModuleMasterDomain = new ProjectModuleMasterDomain();
		if(projectModuleMasterModel.getNumId()!=0){				
			projectModuleMasterDomain =  projectModuleMasterDao.getProjectModuleMasterById(projectModuleMasterModel.getNumId());
		}
		
		projectModuleMasterDomain.setDtTrDate(new Date());
		projectModuleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		projectModuleMasterDomain.setNumIsValid(1);
		/*if(projectModuleMasterModel.isValid()){
			projectModuleMasterDomain.setNumIsValid(1);
		}else{
			projectModuleMasterDomain.setNumIsValid(0);
		}*/
		projectModuleMasterDomain.setStrModuleDescription(projectModuleMasterModel.getStrModuleDescription());
		projectModuleMasterDomain.setStrModuleName(projectModuleMasterModel.getStrModuleName());
		
		projectModuleMasterDomain.setProjectId(projectModuleMasterModel.getProjectId());
		
		return projectModuleMasterDomain;
	}
	
	public List<ProjectModuleMasterModel> convertProjectModuleMasterDomainToModelList(List<ProjectModuleMasterDomain> projectModuleMasterList){
		List<ProjectModuleMasterModel> list = new ArrayList<ProjectModuleMasterModel>();
			for(int i=0;i<projectModuleMasterList.size();i++){
				ProjectModuleMasterDomain projectModuleMasterDomain = projectModuleMasterList.get(i);
				ProjectModuleMasterModel projectModuleMasterModel = new ProjectModuleMasterModel();
				
				if(projectModuleMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+projectModuleMasterDomain.getNumId());
					projectModuleMasterModel.setEncProjectId(encryptedId);
				}
				projectModuleMasterModel.setNumId(projectModuleMasterDomain.getNumId());
				if(projectModuleMasterDomain.getNumIsValid() ==1){
					projectModuleMasterModel.setValid(true);
				}else{
					projectModuleMasterModel.setValid(false);
				}
			
		
				projectModuleMasterModel.setStrModuleDescription(projectModuleMasterDomain.getStrModuleDescription());
				projectModuleMasterModel.setStrModuleName(projectModuleMasterDomain.getStrModuleName());
				
			
				
				list.add(projectModuleMasterModel);
	}
		return list;
	}

	
	
	@Override
	public List<ProjectModuleMasterModel> getProjectModuleByProjectId(long projectId) {		
			List<ProjectModuleMasterDomain> projectModuleMasterDomain = projectModuleMasterDao.getAllProjectModuleByProjectID(projectId);
			List<ProjectModuleMasterModel> projectModuleMasterModelList = convertProjectModuleMasterDomainToModelList(projectModuleMasterDomain);
			return projectModuleMasterModelList;
	}
	
	public ProjectModuleMasterModel convertProjectModuleMasterDomainToModel(ProjectModuleMasterDomain projectModuleMasterDomain){
		ProjectModuleMasterModel projectModuleMasterModel = new ProjectModuleMasterModel();
	
		if(projectModuleMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+projectModuleMasterDomain.getNumId());
			projectModuleMasterModel.setEncProjectId(encryptedId);
		}
		projectModuleMasterModel.setNumId(projectModuleMasterDomain.getNumId());
		if(projectModuleMasterDomain.getNumIsValid() ==1){
			projectModuleMasterModel.setValid(true);
		}else{
			projectModuleMasterModel.setValid(false);
		}
	
		
		projectModuleMasterModel.setStrModuleDescription(projectModuleMasterDomain.getStrModuleDescription());
		projectModuleMasterModel.setStrModuleName(projectModuleMasterDomain.getStrModuleName());
		
		
		return projectModuleMasterModel;
		
	}
	public long deleteProjectModule(ProjectModuleMasterModel projectModuleMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ProjectModuleMasterDomain> projectModuleMasterList = projectModuleMasterDao.getProjectModuleMasterById(projectModuleMasterModel.getIdCheck());
		for(int i=0;i<projectModuleMasterList.size();i++){
			ProjectModuleMasterDomain projectModuleMasterDomain = projectModuleMasterList.get(i);
			projectModuleMasterDomain.setNumIsValid(2);
			projectModuleMasterDomain.setDtTrDate(new Date());
			projectModuleMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = projectModuleMasterDao.saveUpdateProjectModuleMaster(projectModuleMasterDomain);
		}
		return result;
	}	
	
	
	
}
