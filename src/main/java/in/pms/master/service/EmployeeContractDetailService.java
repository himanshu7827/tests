package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import in.pms.master.domain.EmployeeContractDetailDomain;
import in.pms.master.model.EmployeeContractDetailModel;




public interface EmployeeContractDetailService {
	
	
	public List<EmployeeContractDetailModel> getActiveContractData();
	@Transactional
	public EmployeeContractDetailDomain saveContractData(EmployeeContractDetailModel employeeContractDetailModel);
	
	//public List<EmployeeContractDetailDomain> getEmpContractMasterDomainById(long numId);

 	
}
