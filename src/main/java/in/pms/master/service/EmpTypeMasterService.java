package in.pms.master.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.model.EmpTypeMasterModel;

public interface EmpTypeMasterService {

	

		
		@Transactional	
		 public long saveUpdateEmpTypeMaster(EmpTypeMasterModel empTypeMasterModel);		
		
		 public String checkDuplicateEmpTypeName(EmpTypeMasterModel empTypeMasterModel);
		 
		 public EmpTypeMasterDomain getEmpTypeMasterDomainById(long numId);
		
		 public List<EmpTypeMasterModel> getAllEmpTypeMasterDomain();
		
		 public List<EmpTypeMasterModel> getAllActiveEmpTypeMasterDomain();
		// public List<String> getAllActiveEmpTypeMaster();
		 
		 public List<String> getdistinctEmpTypeShortNames();
		 
		 
		 
		@Transactional
		public long deleteEmpType(EmpTypeMasterModel empTypeMasterModel);
		
	}

	

