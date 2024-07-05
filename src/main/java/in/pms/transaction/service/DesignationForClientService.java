package in.pms.transaction.service;

import in.pms.transaction.domain.DesignationForClient;
import in.pms.transaction.model.DesignationForClientModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface DesignationForClientService {

	@Transactional	
	 public int saveUpdateDesignationMaster(DesignationForClientModel designationForClientModel);		
	
	 public String checkDuplicateDesignationName(DesignationForClientModel designationForClientModel);
	 
	 public DesignationForClient getById(int numId);
	
	 public List<DesignationForClientModel> getActiveDesignationForClient(); 

	 public DesignationForClientModel categorydesignationCost(int projectCategoryId,int designationId, int numDeputedAt); // Function modified by devesh on 30-04-24 to add deputed value
	 
	 @Transactional
	 public String saveprojectCategorydesignationMapping(DesignationForClientModel model);
	 
	 public List<DesignationForClientModel> getDesignationByCategory(int categoryId);
	 
	 public void designationForClientAuthorityCheck();
	 
	 public void projectCategorydesignationMappingAuthorityCheck();

	 public List<DesignationForClientModel> getBaseCostDetails(int categoryId, int designationId); // Function modified by devesh on 30-04-24 to add deputed value

	 public boolean checkDuplicateProjectCategorydesignationMapping(DesignationForClientModel model); //Function added by devesh on 07-05-24 to check Duplicate entry
}
