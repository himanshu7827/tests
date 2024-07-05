package in.pms.master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.master.dao.ThirdPartyMasterDao;
import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.master.domain.ThirdPartyMasterDomain;
import in.pms.master.model.ThirdPartyMasterModel;

@Service
public class ThirdPartyMasterServiceImpl implements ThirdPartyMasterService{
	
	@Autowired
	ThirdPartyMasterDao thirdPartyMasterDao;
	
	@Autowired
	EncryptionService encryptionService;

	@Override
	@PreAuthorize("hasAuthority('WRITE_THIRD_PARTY_MST')")
	public long saveUpdateThirdPartyMaster(ThirdPartyMasterModel thirdPartyMasterModel) {
		ThirdPartyMasterDomain thirdPartyMasterDomain = convertThirdPartyMasterModelToDomain(thirdPartyMasterModel);
		return thirdPartyMasterDao.saveUpdateThirdPartyMaster(thirdPartyMasterDomain);
	}

	
	@SuppressWarnings("null")
	@Override
	public String checkDuplicateThirdPartyName(ThirdPartyMasterModel thirdPartyMasterModel) {
		String result = "";
		ThirdPartyMasterDomain thirdPartyMasterDomain = thirdPartyMasterDao.getThirdPartyMasterByName(thirdPartyMasterModel.getAgencyName());
	
		 if(null == thirdPartyMasterDomain){
			return null; 
		 }else if(thirdPartyMasterModel.getNumId() != 0){
			 if(thirdPartyMasterDomain.getNumId() == thirdPartyMasterModel.getNumId()){
				 return null; 
			 }else{
				 result = "Agency Name with same name already exist with Id "+thirdPartyMasterDomain.getNumId();
			 }
		 }else{
			if(thirdPartyMasterDomain.getNumIsValid() == 0){
				result = "Agency Name Already Registered with Id "+thirdPartyMasterDomain.getNumId() +". Please activate same record";
			}else{
				result = "Agency Name Already Registered with Id "+thirdPartyMasterDomain.getNumId();
			}			
		 }
		return result;
	}

	@Override
	public ThirdPartyMasterModel getThirdPartyMasterDomainById(long numId) {
		return convertThirdPartyMasterDomainToModel(thirdPartyMasterDao.getThirdPartyMasterDomainById(numId));
	}
	@PreAuthorize("hasAuthority('READ_THIRD_PARTY_MST')")
	@Override
	public List<ThirdPartyMasterModel> getAllThirdPartyMasterDomain() {
		return convertThirdPartyMasterDomainToModelList(thirdPartyMasterDao.getAllThirdPartyMasterDomain());
	}

	@Override
	public List<ThirdPartyMasterModel> getAllActiveThirdPartyMasterDomain() {
		return convertThirdPartyMasterDomainToModelList(thirdPartyMasterDao.getAllActiveThirdPartyMasterDomain());
	}
	
	@Override
	public List<ThirdPartyMasterDomain> getThirdPartyMasterDomain(){
		return thirdPartyMasterDao.getAllActiveThirdPartyMasterDomain();
	}
	
	
	private ThirdPartyMasterDomain convertThirdPartyMasterModelToDomain(ThirdPartyMasterModel thirdPartyMasterModel) {
		ThirdPartyMasterDomain thirdPartyMasterDomain =  new ThirdPartyMasterDomain();
		if(thirdPartyMasterModel.getNumId()!=0){
				
			thirdPartyMasterDomain =  thirdPartyMasterDao.getThirdPartyMasterDomainById(thirdPartyMasterModel.getNumId());
		}
		thirdPartyMasterDomain.setStrAgencyName(thirdPartyMasterModel.getAgencyName());
		thirdPartyMasterDomain.setStrAgencyAddress(thirdPartyMasterModel.getAgencyAddress());
		thirdPartyMasterDomain.setStrContactPerson(thirdPartyMasterModel.getContactPerson());
		thirdPartyMasterDomain.setStrMobileNo(thirdPartyMasterModel.getMobileNumber());
		thirdPartyMasterDomain.setStrContactNo(thirdPartyMasterModel.getContactNumber());
		//thirdPartyMasterDomain.setDtStartDate(thirdPartyMasterModel.getStartDate());
		//thirdPartyMasterDomain.setDtEndDate(thirdPartyMasterModel.getEndDate());
		
		if(null !=thirdPartyMasterModel.getStartDate()){
			try {
				thirdPartyMasterDomain.setDtStartDate(DateUtils.dateStrToDate(thirdPartyMasterModel.getStartDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null !=thirdPartyMasterModel.getEndDate()){
			try {
				thirdPartyMasterDomain.setDtEndDate(DateUtils.dateStrToDate(thirdPartyMasterModel.getEndDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
	
		
		if(null != thirdPartyMasterModel.getStartDate() &&  !thirdPartyMasterModel.getStartDate().equals("")){
			try {
				thirdPartyMasterDomain.setDtStartDate(DateUtils.dateStrToDate(thirdPartyMasterModel.getStartDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		if(null != thirdPartyMasterModel.getEndDate() && !thirdPartyMasterModel.getEndDate().equals("")){
			try {
				thirdPartyMasterDomain.setDtEndDate(DateUtils.dateStrToDate(thirdPartyMasterModel.getEndDate()));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		thirdPartyMasterDomain.setDtTrDate(new Date());
		
		if(thirdPartyMasterModel.isValid()){
			thirdPartyMasterDomain.setNumIsValid(1);
		}else{
			thirdPartyMasterDomain.setNumIsValid(0);
		}
		return thirdPartyMasterDomain;
	}

	
	private ThirdPartyMasterModel convertThirdPartyMasterDomainToModel(ThirdPartyMasterDomain thirdPartyMasterDomain) {
		ThirdPartyMasterModel thirdPartyMasterModel = new ThirdPartyMasterModel();
		thirdPartyMasterModel.setAgencyName(thirdPartyMasterDomain.getStrAgencyName());
		thirdPartyMasterModel.setAgencyAddress(thirdPartyMasterDomain.getStrAgencyAddress());
		thirdPartyMasterModel.setContactPerson(thirdPartyMasterDomain.getStrContactPerson());
		thirdPartyMasterModel.setMobileNumber(thirdPartyMasterDomain.getStrMobileNo());
		thirdPartyMasterModel.setContactNumber(thirdPartyMasterDomain.getStrContactNo());
		if(thirdPartyMasterDomain.getNumId() != 0){
			String encryptedId = encryptionService.encrypt(""+thirdPartyMasterDomain.getNumId());
			thirdPartyMasterModel.setEncStrId(encryptedId);
		}
		
		if(null != thirdPartyMasterDomain.getDtStartDate() && !thirdPartyMasterDomain.getDtStartDate().equals("")){
			
			thirdPartyMasterModel.setStartDate(DateUtils.dateToString(thirdPartyMasterDomain.getDtStartDate()));
		}
		if(null != thirdPartyMasterDomain.getDtEndDate() && !thirdPartyMasterDomain.getDtEndDate().equals("")){
		
		thirdPartyMasterModel.setEndDate(DateUtils.dateToString(thirdPartyMasterDomain.getDtEndDate()));
		}
		thirdPartyMasterModel.setNumId(thirdPartyMasterDomain.getNumId());
		if(thirdPartyMasterDomain.getNumIsValid()==1){
			thirdPartyMasterModel.setValid(true);
		}else{
			thirdPartyMasterModel.setValid(false);
		}
		
		return thirdPartyMasterModel;
	}
	
	private List<ThirdPartyMasterModel> convertThirdPartyMasterDomainToModelList(List<ThirdPartyMasterDomain> thirdPartyMasterDomainList) {
		List<ThirdPartyMasterModel> list = new  ArrayList<ThirdPartyMasterModel>();
		for(int i=0;i<thirdPartyMasterDomainList.size();i++){
			ThirdPartyMasterModel thirdPartyMasterModel = new ThirdPartyMasterModel();
			ThirdPartyMasterDomain thirdPartyMasterDomain = thirdPartyMasterDomainList.get(i);
			thirdPartyMasterModel.setAgencyName(thirdPartyMasterDomain.getStrAgencyName());
			thirdPartyMasterModel.setAgencyAddress(thirdPartyMasterDomain.getStrAgencyAddress());
			thirdPartyMasterModel.setContactPerson(thirdPartyMasterDomain.getStrContactPerson());
			thirdPartyMasterModel.setMobileNumber(thirdPartyMasterDomain.getStrMobileNo());
			thirdPartyMasterModel.setContactNumber(thirdPartyMasterDomain.getStrContactNo());
			if(thirdPartyMasterDomain.getNumId() != 0){
				String encryptedId = encryptionService.encrypt(""+thirdPartyMasterDomain.getNumId());
				thirdPartyMasterModel.setEncStrId(encryptedId);
			}
			
			if(null != thirdPartyMasterDomain.getDtStartDate() && !thirdPartyMasterDomain.getDtStartDate().equals("")){
				
				thirdPartyMasterModel.setStartDate(DateUtils.dateToString(thirdPartyMasterDomain.getDtStartDate()));
			}
			if(null != thirdPartyMasterDomain.getDtEndDate() && !thirdPartyMasterDomain.getDtEndDate().equals("")){
			
			thirdPartyMasterModel.setEndDate(DateUtils.dateToString(thirdPartyMasterDomain.getDtEndDate()));
			}
			thirdPartyMasterModel.setNumId(thirdPartyMasterDomain.getNumId());
			if(thirdPartyMasterDomain.getNumIsValid()==1){
				thirdPartyMasterModel.setValid(true);
			}else{
				thirdPartyMasterModel.setValid(false);
			}
			list.add(thirdPartyMasterModel);
		}
		return list;
	}


	@Override
	public void deleteThirdPartyMaster(ThirdPartyMasterModel thirdPartyMasterModel) {
		int count= thirdPartyMasterModel.getNumIds().length;
		long[] thirdParty_ids= new long[count];
		thirdParty_ids= thirdPartyMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ThirdPartyMasterDomain thirdPartyMasterDomain= new ThirdPartyMasterDomain();
			thirdPartyMasterDomain.setNumId(thirdParty_ids[i]);
			
			Date date= new Date();
			thirdPartyMasterDomain.setDtTrDate(date);

			thirdPartyMasterDao.deleteThirdPartyMaster(thirdPartyMasterDomain);
			
			
		}		
		
	}


	@Override
	public ThirdPartyMasterDomain getThirdPartyDomainById(long numId) {
		return thirdPartyMasterDao.getThirdPartyMasterDomainById(numId);
	}

}
