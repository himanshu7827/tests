package in.pms.master.service;

import in.pms.master.model.ExpenditureHeadModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ExpenditureHeadService {
		
		@Transactional	
		 public long saveUpdateExpenditureHead(ExpenditureHeadModel expenditureHeadModel);		
		
		 public String checkDuplicateExpenditureHeadName(ExpenditureHeadModel expenditureHeadModel);
		 
		 public ExpenditureHeadModel getExpenditureHeadMasterDomainById(long numId);
		
		 public List<ExpenditureHeadModel> getAllExpenditureHeadMasterDomain();
		
		 public List<ExpenditureHeadModel> getAllActiveExpenditureHeadMasterDomain();
		
		@Transactional
		public long deleteExpenditureHead(ExpenditureHeadModel expenditureHeadModel);
		
	}

	


