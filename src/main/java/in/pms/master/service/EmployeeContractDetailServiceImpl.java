package in.pms.master.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeContractDetailDao;
import in.pms.master.domain.EmployeeContractDetailDomain;
import in.pms.master.model.EmployeeContractDetailModel;
@Service

public class EmployeeContractDetailServiceImpl implements EmployeeContractDetailService{
	
	@Autowired
	EmployeeContractDetailDao employeeContractDetailDao;
	
	
	@Override
	public List<EmployeeContractDetailModel> getActiveContractData(){
		return convertEmployeeContractDomainToModelList(employeeContractDetailDao.getActiveContractData());			
	}

	public List<EmployeeContractDetailModel> convertEmployeeContractDomainToModelList(List<EmployeeContractDetailDomain> contractDataList){
		List<EmployeeContractDetailModel> list = new ArrayList<EmployeeContractDetailModel>();
			for(int i=0;i<contractDataList.size();i++){
				EmployeeContractDetailDomain employeeContractDetailDomain = contractDataList.get(i);
				EmployeeContractDetailModel employeeContractDetailModel = new EmployeeContractDetailModel();
				employeeContractDetailModel.setNumId(employeeContractDetailDomain.getNumId());
				employeeContractDetailModel.setContractStartDate(DateUtils.dateToString(employeeContractDetailDomain.getStartDate()));
				employeeContractDetailModel.setContractEndDate(DateUtils.dateToString(employeeContractDetailDomain.getEndDate()));
				if(employeeContractDetailDomain.getNumIsValid()==1){
					employeeContractDetailModel.setValid(true);
				}else{
					employeeContractDetailModel.setValid(false);
				}
				
				list.add(employeeContractDetailModel);
	}
		return list;
	}
	
	
	
	    @Override
        public EmployeeContractDetailDomain saveContractData(EmployeeContractDetailModel employeeContractDetailModel){
	    EmployeeContractDetailDomain employeeContractDetailDomain = convertContractMasterModelToDomain(employeeContractDetailModel);
	    return employeeContractDetailDao.save(employeeContractDetailDomain);
	}
	
	public EmployeeContractDetailDomain convertContractMasterModelToDomain(EmployeeContractDetailModel employeeContractDetailModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeContractDetailDomain employeeContractDetailDomain = new EmployeeContractDetailDomain();
		if(employeeContractDetailModel.getNumId()!=0){		
			employeeContractDetailDomain =  employeeContractDetailDao.getOne(employeeContractDetailModel.getNumId());
		}
		try {
			employeeContractDetailDomain.setStartDate(DateUtils.dateStrToDate(employeeContractDetailModel.getContractStartDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			employeeContractDetailDomain.setEndDate(DateUtils.dateStrToDate(employeeContractDetailModel.getContractEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employeeContractDetailDomain.setDtTrDate(new Date());
		employeeContractDetailDomain.setNumTrUserId(userInfo.getEmployeeId());
		employeeContractDetailDomain.setNumIsValid(1);
					
		return employeeContractDetailDomain;
	}

	/*@Override
	public List<EmployeeContractDetailDomain> getEmpContractMasterDomainById(long numId){
		return employeeContractDetailDao.getContractDataById(numId);
	}*/
}
