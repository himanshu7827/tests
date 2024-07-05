package in.pms.master.service;

import in.pms.global.service.EncryptionService;
import in.pms.master.dao.BusinessTypeDao;
import in.pms.master.domain.BusinessTypeMaster;
import in.pms.master.model.BusinessTypeModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {
	
	@Autowired
	BusinessTypeDao businessTypeDao;
	
	@Autowired
	EncryptionService encryptionService;

	@Override
	public BusinessTypeMaster mergeBusinessType(
			BusinessTypeMaster businessTypeMaster) {
		return businessTypeDao.mergeBusinessType(businessTypeMaster);
	}

	@Override
	public List<BusinessTypeModel> getAllBusinessType() {		
		return convertBusinessTypeToModelList(businessTypeDao.getActiveBusinessType());
	}

	@Override
	public List<BusinessTypeModel> getActiveBusinessType() {
		return convertBusinessTypeToModelList(businessTypeDao.getActiveBusinessType());
	}

	@Override
	public BusinessTypeMaster getBusinessTypeById(long id) {	
		return businessTypeDao.getBusinessTypeById(id);
	}

	public BusinessTypeModel convertBusinessTypeMasterToModel(BusinessTypeMaster domain){
		BusinessTypeModel model = new BusinessTypeModel();
		model.setNumId(domain.getNumId());
		model.setBusinessTypeName(domain.getBusinessTypeName());
		model.setShortCode(domain.getShortCode());
		if(domain.getNumIsValid()==1){
			model.setValid(true);
		}else{
			model.setValid(false);
		}
		model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
		
		return model;
	}
	
	public List<BusinessTypeModel> convertBusinessTypeToModelList(List<BusinessTypeMaster> domains){
		List<BusinessTypeModel> modelList = new ArrayList<BusinessTypeModel>();
		for(int i=0;i<domains.size();i++){
			BusinessTypeMaster domain = domains.get(i);
			BusinessTypeModel model = new BusinessTypeModel();
			model.setNumId(domain.getNumId());
			model.setBusinessTypeName(domain.getBusinessTypeName());
			model.setShortCode(domain.getShortCode());
			if(domain.getNumIsValid()==1){
				model.setValid(true);
			}else{
				model.setValid(false);
			}
			model.setEncNumId(encryptionService.encrypt(""+domain.getNumId()));
			modelList.add(model);
		}
		
		
		return modelList;
	}
	
	@Override
	public List<String> getdistinctBusinessTypeNames(){
		return businessTypeDao.getdistinctBusinessTypeNames();
	}

}
