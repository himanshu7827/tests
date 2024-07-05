package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmpTypeMasterDao;
import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.model.EmpTypeMasterModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpTypeMasterServiceImpl implements EmpTypeMasterService {

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	EmpTypeMasterDao empTypeMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_EMP_TYPE_MST')")
	public long saveUpdateEmpTypeMaster(EmpTypeMasterModel empTypeMasterModel){
		EmpTypeMasterDomain empTypeMasterDomain = convertEmpTypeMasterModelToDomain(empTypeMasterModel);
		return empTypeMasterDao.saveUpdateEmpTypeMaster(empTypeMasterDomain);
	}
		
	@Override
	public String checkDuplicateEmpTypeName(EmpTypeMasterModel empTypeMasterModel){
		String result=	"";
		EmpTypeMasterDomain empTypeMasterDomain = empTypeMasterDao.getEmpTypeMasterByName(empTypeMasterModel.getStrEmpTypeName());
		
		 if(null == empTypeMasterDomain){
				return null; 
			 }else if(empTypeMasterModel.getNumId() != 0){
				 if(empTypeMasterDomain.getNumId() == empTypeMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Employee Type with same name already exist with Id "+empTypeMasterDomain.getNumId();
				 }
			 }else{
				if(empTypeMasterDomain.getNumIsValid() == 0){
					result = "Employee Type Details already exist with Id "+empTypeMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Employee Type Details already exist with Id "+empTypeMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public EmpTypeMasterDomain getEmpTypeMasterDomainById(long numId){
		return empTypeMasterDao.getEmpTypeMasterById(numId);
	}
	

	
	@Override
	@PreAuthorize("hasAuthority('READ_EMP_TYPE_MST')")
	public List<EmpTypeMasterModel> getAllEmpTypeMasterDomain(){
		return convertEmpTypeMasterDomainToModelList(empTypeMasterDao.getAllEmpTypeMasterDomain());
	}
	
	@Override
	public List<EmpTypeMasterModel> getAllActiveEmpTypeMasterDomain(){
		return convertEmpTypeMasterDomainToModelList(empTypeMasterDao.getAllActiveEmpTypeMasterDomain());
	}
	
	public EmpTypeMasterDomain convertEmpTypeMasterModelToDomain(EmpTypeMasterModel empTypeMasterModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		EmpTypeMasterDomain empTypeMasterDomain = new EmpTypeMasterDomain();
		if(empTypeMasterModel.getNumId()!=0){				
			empTypeMasterDomain =  empTypeMasterDao.getEmpTypeMasterById(empTypeMasterModel.getNumId());
		}
		
		empTypeMasterDomain.setDtTrDate(new Date());
		empTypeMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(empTypeMasterModel.isValid()){
			empTypeMasterDomain.setNumIsValid(1);
		}else{
			empTypeMasterDomain.setNumIsValid(0);
		}
		empTypeMasterDomain.setEmpshortode(empTypeMasterModel.getEmpShortName());
		empTypeMasterDomain.setStrEmpTypeName(empTypeMasterModel.getStrEmpTypeName());
		empTypeMasterDomain.setBgColor(empTypeMasterModel.getBgColor());
        empTypeMasterDomain.setHierarchy(empTypeMasterModel.getHierarchy());	
		return empTypeMasterDomain;
	}
	
	public List<EmpTypeMasterModel> convertEmpTypeMasterDomainToModelList(List<EmpTypeMasterDomain> empTypeMasterList){
		List<EmpTypeMasterModel> list = new ArrayList<EmpTypeMasterModel>();
			for(int i=0;i<empTypeMasterList.size();i++){
				EmpTypeMasterDomain empTypeMasterDomain = empTypeMasterList.get(i);
				EmpTypeMasterModel empTypeMasterModel = new EmpTypeMasterModel();
				
				if(empTypeMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+empTypeMasterDomain.getNumId());
					empTypeMasterModel.setEncOrganisationId(encryptedId);
				}
				empTypeMasterModel.setNumId(empTypeMasterDomain.getNumId());
				if(empTypeMasterDomain.getNumIsValid() ==1){
					empTypeMasterModel.setValid(true);
				}else{
					empTypeMasterModel.setValid(false);
				}
			
		
				empTypeMasterModel.setStrEmpTypeName(empTypeMasterDomain.getStrEmpTypeName());
				empTypeMasterModel.setEmpShortName(empTypeMasterDomain.getEmpshortode());
				empTypeMasterModel.setBgColor(empTypeMasterDomain.getBgColor());
				empTypeMasterModel.setHierarchy(empTypeMasterDomain.getHierarchy());
			
				
				list.add(empTypeMasterModel);
	}
		return list;
	}

	
	
	public EmpTypeMasterModel convertEmpTypeMasterDomainToModel(EmpTypeMasterDomain empTypeMasterDomain){
		EmpTypeMasterModel empTypeMasterModel = new EmpTypeMasterModel();
	
		if(empTypeMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+empTypeMasterDomain.getNumId());
			empTypeMasterModel.setEncOrganisationId(encryptedId);
		}
		empTypeMasterModel.setNumId(empTypeMasterDomain.getNumId());
		if(empTypeMasterDomain.getNumIsValid() ==1){
			empTypeMasterModel.setValid(true);
		}else{
			empTypeMasterModel.setValid(false);
		}
	
		
		empTypeMasterModel.setStrEmpTypeName(empTypeMasterDomain.getStrEmpTypeName());
		empTypeMasterModel.setEmpShortName(empTypeMasterDomain.getEmpshortode());
		
		return empTypeMasterModel;
		
	}
	public long deleteEmpType(EmpTypeMasterModel empTypeMasterModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<EmpTypeMasterDomain> empTypeMasterList = empTypeMasterDao.getEmpTypeMasterById(empTypeMasterModel.getIdCheck());
		for(int i=0;i<empTypeMasterList.size();i++){
			EmpTypeMasterDomain empTypeMasterDomain = empTypeMasterList.get(i);
			empTypeMasterDomain.setNumIsValid(2);
			empTypeMasterDomain.setDtTrDate(new Date());
			empTypeMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = empTypeMasterDao.saveUpdateEmpTypeMaster(empTypeMasterDomain);
		}
		return result;
	}

	@Override
	public List<String> getdistinctEmpTypeShortNames() {
		return empTypeMasterDao.getdistinctEmpTypeNames();
	}	
	
	
	
	
}
