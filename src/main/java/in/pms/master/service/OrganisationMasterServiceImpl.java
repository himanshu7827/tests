package in.pms.master.service;

import in.pms.global.dao.DaoHelper;
import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.OrganisationMasterDao;
import in.pms.master.domain.OrganisationMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.OrganisationMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrganisationMasterServiceImpl implements OrganisationMasterService {
	
	@Autowired
	DaoHelper daoHelper;

	@Autowired
	OrganisationMasterDao organisationMasterDao;
	
	@Autowired
	ThrustAreaMasterService thrustAreaMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ParentOrganisationMasterService parentOrganisationMasterService;
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_ORGANISATION_MST')")
	public long saveUpdateOrganisationMaster(OrganisationMasterModel organisationMasterModel){
		OrganisationMasterDomain organisationMasterDomain = convertOrganisationMasterModelToDomain(organisationMasterModel);
		return organisationMasterDao.saveUpdateOrganisationMaster(organisationMasterDomain);
	}
		
	@Override
	public String checkDuplicateOrganisationName(OrganisationMasterModel organisationMasterModel){
		String result=	"";
		OrganisationMasterDomain organisationMasterDomain = organisationMasterDao.getOrganisationMasterByName(organisationMasterModel.getOrganisationName());
		
		 if(null == organisationMasterDomain){
				return null; 
			 }else if(organisationMasterModel.getNumId() != 0){
				 if(organisationMasterDomain.getNumId() == organisationMasterModel.getNumId()){
					 return null; 
				 }else{
					 result = "Organisation Details already exist with Id "+organisationMasterDomain.getNumId();
				 }
			 }else{
				if(organisationMasterDomain.getNumIsValid() == 0){
					result = "Organisation Details already exist with Id "+organisationMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Organisation Details already exist with Id "+organisationMasterDomain.getNumId();
				}			
			 }
			return result;	
	}
	
	@Override
	public OrganisationMasterModel getOrganisationMasterDomainById(long numId){
		return convertOrganisationMasterDomainToModel(organisationMasterDao.getOrganisationMasterById(numId));
	}
	
	@Override
	@PreAuthorize("hasAuthority('READ_ORGANISATION_MST')")
	public List<OrganisationMasterModel> getAllOrganisationMasterDomain(){
		return convertOrganisationMasterDomainToModelList(organisationMasterDao.getAllOrganisationMasterDomain(),true);
	}
	
	@Override
	public List<OrganisationMasterModel> getAllActiveOrganisationMasterDomain(){
		return convertOrganisationMasterDomainToModelList(organisationMasterDao.getAllActiveOrganisationMasterDomain(),false);
	}
	
	public OrganisationMasterDomain convertOrganisationMasterModelToDomain(OrganisationMasterModel organisationMasterModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		OrganisationMasterDomain organisationMasterDomain = new OrganisationMasterDomain();
		if(organisationMasterModel.getNumId()!=0){				
			organisationMasterDomain =  organisationMasterDao.getOrganisationMasterById(organisationMasterModel.getNumId());
		}
		organisationMasterDomain.setContactNumber(organisationMasterModel.getContactNumber());
		organisationMasterDomain.setDtTrDate(new Date());
		organisationMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		/*if(organisationMasterModel.isValid()){*/
			organisationMasterDomain.setNumIsValid(1);
		/*}else{
			organisationMasterDomain.setNumIsValid(0);
		}*/
		organisationMasterDomain.setOrganisationAddress(organisationMasterModel.getOrganisationAddress());
		organisationMasterDomain.setOrganisationName(organisationMasterModel.getOrganisationName());
		organisationMasterDomain.setOrganisationShortName(organisationMasterModel.getOrganisationShortName());
		organisationMasterDomain.setStrCode(organisationMasterModel.getStrCode());
		
		List<String> list =  organisationMasterModel.getThrustAreaData();
		String thrustAreaIds = StringUtils.join(list, ",");
		organisationMasterDomain.setThrustAreaMaster(thrustAreaMasterService.getThrustAreaData(thrustAreaIds));
	/*
		System.out.println(organisationMasterDomain);*/
		
		organisationMasterDomain.setParentOrganisationMaster(parentOrganisationMasterService.getParentOrganisationById(organisationMasterModel.getParentOrganisationId()));
		
		return organisationMasterDomain;
	}
	
	public List<OrganisationMasterModel> convertOrganisationMasterDomainToModelList(List<OrganisationMasterDomain> organisationMasterList,boolean flag){
		List<OrganisationMasterModel> list = new ArrayList<OrganisationMasterModel>();
			for(int i=0;i<organisationMasterList.size();i++){
				OrganisationMasterDomain organisationMasterDomain = organisationMasterList.get(i);
				OrganisationMasterModel organisationMasterModel = new OrganisationMasterModel();
				
				if(organisationMasterDomain.getNumId() != 0){
					String encryptedId = encryptionService.encrypt(""+organisationMasterDomain.getNumId());
					organisationMasterModel.setEncOrganisationId(encryptedId);
				}
				organisationMasterModel.setNumId(organisationMasterDomain.getNumId());
			/*	if(organisationMasterDomain.getNumIsValid() ==1){
					organisationMasterModel.setValid(true);
				}else{
					organisationMasterModel.setValid(false);
				}*/
			
				organisationMasterModel.setContactNumber(organisationMasterDomain.getContactNumber());
				organisationMasterModel.setStrCode(organisationMasterDomain.getStrCode());
				organisationMasterModel.setOrganisationAddress(organisationMasterDomain.getOrganisationAddress());
				organisationMasterModel.setOrganisationName(organisationMasterDomain.getOrganisationName());
				organisationMasterModel.setOrganisationShortName(organisationMasterDomain.getOrganisationShortName());
				if(flag){
					List<ThrustAreaMasterDomain> thrustAreaMasterList = organisationMasterDomain.getThrustAreaMaster();
				
				String thrustArea ="";
				String thrustAreaIds="";
				for(int j=0;j<thrustAreaMasterList.size();j++){
					ThrustAreaMasterDomain thrustAreaMaster =thrustAreaMasterList.get(j);					
					if(j==0){
						thrustArea = thrustAreaMaster.getStrThrustAreaName();
						thrustAreaIds=""+thrustAreaMaster.getNumId();
					}else if(j <=thrustAreaMasterList.size()-1){
						thrustArea += " | "+thrustAreaMaster.getStrThrustAreaName();
						thrustAreaIds+=","+thrustAreaMaster.getNumId();
					}
					
				}
				organisationMasterModel.setThrustArea(thrustArea);
				organisationMasterModel.setThrustAreaIds(thrustAreaIds);
				}
				organisationMasterModel.setParentOrganisationId(organisationMasterDomain.getParentOrganisationMaster().getNumId());
				organisationMasterModel.setParentOrganisationName(organisationMasterDomain.getParentOrganisationMaster().getOrganisationName());
				list.add(organisationMasterModel);
	}
		return list;
	}

	public OrganisationMasterModel convertOrganisationMasterDomainToModel(OrganisationMasterDomain organisationMasterDomain){
		OrganisationMasterModel organisationMasterModel = new OrganisationMasterModel();
	
		if(organisationMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+organisationMasterDomain.getNumId());
			organisationMasterModel.setEncOrganisationId(encryptedId);
		}
		organisationMasterModel.setNumId(organisationMasterDomain.getNumId());
	/*	if(organisationMasterDomain.getNumIsValid() ==1){
			organisationMasterModel.setValid(true);
		}else{
			organisationMasterModel.setValid(false);
		}*/
	
		organisationMasterModel.setContactNumber(organisationMasterDomain.getContactNumber());
		organisationMasterModel.setOrganisationAddress(organisationMasterDomain.getOrganisationAddress());
		organisationMasterModel.setOrganisationName(organisationMasterDomain.getOrganisationName());
		organisationMasterModel.setOrganisationShortName(organisationMasterDomain.getOrganisationShortName());
		organisationMasterModel.setParentOrganisationId(organisationMasterDomain.getParentOrganisationMaster().getNumId());
		organisationMasterModel.setParentOrganisationName(organisationMasterDomain.getParentOrganisationMaster().getOrganisationName());
		
		return organisationMasterModel;
		
	}
	
	public void deleteOrganisationData(OrganisationMasterModel organisationMasterModel) {
		int count= organisationMasterModel.getNumIds().length;
		long[] orgMaster_ids= new long[count];
		orgMaster_ids= organisationMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			OrganisationMasterDomain organisationMasterDomain =  organisationMasterDao.getOrganisationMasterById(orgMaster_ids[i]);
			organisationMasterDomain.setDtTrDate(new Date());
			organisationMasterDomain.setNumIsValid(2);
			organisationMasterDao.saveUpdateOrganisationMaster(organisationMasterDomain);
			
			
		}		
	}

	@Override
	public String getDistinctOrganisation(String assignedOrganisation) {
		
		return organisationMasterDao.getOrganisationNames(assignedOrganisation);
	}
	
	@Override
	public String getDistinctOrganisationShortName(String assignedOrganisation) {
		
		return organisationMasterDao.getOrganisationShortNames(assignedOrganisation);
	}
	
}
