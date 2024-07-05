package in.pms.master.service;

import in.pms.master.domain.BusinessTypeMaster;
import in.pms.master.model.BusinessTypeModel;

import java.util.List;

import javax.transaction.Transactional;

public interface BusinessTypeService {

	@Transactional
	public BusinessTypeMaster mergeBusinessType(BusinessTypeMaster businessTypeMaster) ;
	
	public List<BusinessTypeModel> getAllBusinessType();
	
	public List<BusinessTypeModel> getActiveBusinessType();
	
	public BusinessTypeMaster getBusinessTypeById(long l);
	
	public List<String> getdistinctBusinessTypeNames();
	
}
