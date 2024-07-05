package in.pms.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;


import in.pms.master.dao.ExpenditureHeadDao;
import in.pms.master.domain.ExpenditureHeadDomain;
import in.pms.master.model.ExpenditureHeadModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenditureHeadServiceImpl implements ExpenditureHeadService{

	@Autowired
	EncryptionService encryptionService;
	
	
	@Autowired
	ExpenditureHeadDao expenditureHeadDao;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_EXPENDITURE_HEAD_MST')")
	public long saveUpdateExpenditureHead(ExpenditureHeadModel expenditureHeadModel){
		ExpenditureHeadDomain expenditureHeadDomain = convertExpenditureHeadMasterModelToDomain(expenditureHeadModel);
		return expenditureHeadDao.saveUpdateExpenditureHead(expenditureHeadDomain);
	}
		
	@Override
	public String checkDuplicateExpenditureHeadName(ExpenditureHeadModel expenditureHeadModel){
		String result=	"";
		ExpenditureHeadDomain expenditureHeadDomain = expenditureHeadDao.getExpenditureHeadMasterByName(expenditureHeadModel.getStrExpenditureHeadName());
		
		 if(null == expenditureHeadDomain){
				return null; 
			 }else if(expenditureHeadModel.getNumId() != 0){
				 if(expenditureHeadDomain.getNumId() == expenditureHeadModel.getNumId()){
					 return null; 
				 }else{
					 result = "Expenditure Head with same name already exist with Id "+expenditureHeadDomain.getNumId();
				 }
			 }else{
				if(expenditureHeadDomain.getNumIsValid() == 0){
					result = "Expenditure Head Details already exist with Id "+expenditureHeadDomain.getNumId() +". Please activate same record";
				}else{
					result = "Expenditure Head Details already exist with Id "+expenditureHeadDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public ExpenditureHeadModel getExpenditureHeadMasterDomainById(long numId){
		return convertExpenditureHeadMasterDomainToModel(expenditureHeadDao.getExpenditureHeadMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_EXPENDITURE_HEAD_MST')")
	public List<ExpenditureHeadModel> getAllExpenditureHeadMasterDomain(){
		return convertExpenditureHeadMasterDomainToModelList(expenditureHeadDao.getAllExpenditureHeadMasterDomain());
	}
	
	@Override
	public List<ExpenditureHeadModel> getAllActiveExpenditureHeadMasterDomain(){
		return convertExpenditureHeadMasterDomainToModelList(expenditureHeadDao.getAllActiveExpenditureHeadMasterDomain());
	}
	
	public ExpenditureHeadDomain convertExpenditureHeadMasterModelToDomain(ExpenditureHeadModel expenditureHeadModel){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ExpenditureHeadDomain expenditureHeadDomain = new ExpenditureHeadDomain();
		if(expenditureHeadModel.getNumId()!=0){				
			expenditureHeadDomain =  expenditureHeadDao.getExpenditureHeadMasterById(expenditureHeadModel.getNumId());
		}
		
		expenditureHeadDomain.setDtTrDate(new Date());
		expenditureHeadDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(expenditureHeadModel.isValid()){
			expenditureHeadDomain.setNumIsValid(1);
		}else{
			expenditureHeadDomain.setNumIsValid(0);
		}
		
		expenditureHeadDomain.setStrExpenditureHeadName(expenditureHeadModel.getStrExpenditureHeadName());
		
		
		return expenditureHeadDomain;
	}
	
	public List<ExpenditureHeadModel> convertExpenditureHeadMasterDomainToModelList(List<ExpenditureHeadDomain> expenditureHeadMasterList){
		List<ExpenditureHeadModel> list = new ArrayList<ExpenditureHeadModel>();
			for(int i=0;i<expenditureHeadMasterList.size();i++){
				ExpenditureHeadDomain expenditureHeadDomain = expenditureHeadMasterList.get(i);
				ExpenditureHeadModel expenditureHeadModel = new ExpenditureHeadModel();
				
				if(expenditureHeadDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+expenditureHeadDomain.getNumId());
					expenditureHeadModel.setEncOrganisationId(encryptedId);
				}
				expenditureHeadModel.setNumId(expenditureHeadDomain.getNumId());
				if(expenditureHeadDomain.getNumIsValid() ==1){
					expenditureHeadModel.setValid(true);
				}else{
					expenditureHeadModel.setValid(false);
				}
			
		
				expenditureHeadModel.setStrExpenditureHeadName(expenditureHeadDomain.getStrExpenditureHeadName());
			
			
				
				list.add(expenditureHeadModel);
	}
		return list;
	}

	
	
	public ExpenditureHeadModel convertExpenditureHeadMasterDomainToModel(ExpenditureHeadDomain expenditureHeadDomain){
		ExpenditureHeadModel expenditureHeadModel = new ExpenditureHeadModel();
	
		if(expenditureHeadDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+expenditureHeadDomain.getNumId());
			expenditureHeadModel.setEncOrganisationId(encryptedId);
		}
		expenditureHeadModel.setNumId(expenditureHeadDomain.getNumId());
		if(expenditureHeadDomain.getNumIsValid() ==1){
			expenditureHeadModel.setValid(true);
		}else{
			expenditureHeadModel.setValid(false);
		}
	
		
		expenditureHeadModel.setStrExpenditureHeadName(expenditureHeadDomain.getStrExpenditureHeadName());
		
		
		return expenditureHeadModel;
		
	}
	public long deleteExpenditureHead(ExpenditureHeadModel expenditureHeadModel) 
	{  
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long result =-1;
		List<ExpenditureHeadDomain> expenditureHeadMasterList = expenditureHeadDao.getExpenditureHeadMasterById(expenditureHeadModel.getIdCheck());
		for(int i=0;i<expenditureHeadMasterList.size();i++){
			ExpenditureHeadDomain expenditureHeadDomain = expenditureHeadMasterList.get(i);
			expenditureHeadDomain.setNumIsValid(2);
			expenditureHeadDomain.setDtTrDate(new Date());
			expenditureHeadDomain.setNumTrUserId(userInfo.getEmployeeId());
			result = expenditureHeadDao.saveUpdateExpenditureHead(expenditureHeadDomain);
		}
		return result;
	}	
	
	
	
	
}
