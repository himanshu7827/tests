package in.pms.transaction.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.ProjectCategoryDao;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.transaction.dao.DesignationForClientDao;
import in.pms.transaction.dao.DesignationProjectCategoryMappingDao;
import in.pms.transaction.domain.DesignationForClient;
import in.pms.transaction.domain.DesignationProjectCategoryMapping;
import in.pms.transaction.model.DesignationForClientModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;

@Service
public class DesignationForClientServiceImpl implements
		DesignationForClientService {

	@Autowired
	DesignationForClientDao designationForClientDao;
	
	@Autowired
	DesignationProjectCategoryMappingDao designationProjectCategoryMappingDao;
	
	@Autowired
	ProjectCategoryDao projectCategoryDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public int saveUpdateDesignationMaster(DesignationForClientModel model) {
		return designationForClientDao.save(convertModelToDomain(model)).getNumDesignationId();
	}

	@Override
	public String checkDuplicateDesignationName(DesignationForClientModel model) {
		
		String result=	"";
		List<DesignationForClient> designationList = designationForClientDao.getByName(model.getDesignationName());
		DesignationForClient domain = null;
		if(null != designationList && designationList.size()>0){
			domain = designationList.get(0);
		}
		
		 if(null == domain){
				return null; 
		}else if(model.getNumId() != 0){
				 if(domain.getNumDesignationId() == model.getNumId()){
					 return null; 
				 }else{
					 result = "Designation Name already exist with Id  "+domain.getNumDesignationId();
				 }
		}else{
				if(domain.getNumIsValid() == 0){
					result = "Designation Name already exist with Id "+ domain.getNumDesignationId() +". Please activate same record";
				}else{
					result = "Designation Name already exist with Id "+domain.getNumDesignationId();
				}			
			 }
			return result;		
	}

	@Override
	public DesignationForClient getById(int numId) {		
		return designationForClientDao.getOne(numId);
	}

	@Override
	public List<DesignationForClientModel> getActiveDesignationForClient() {		
		return convertDomainToModelList(designationForClientDao.getActiveDesignationForClient());
	}	

	public DesignationForClient convertModelToDomain(DesignationForClientModel model){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		DesignationForClient domain = new DesignationForClient();
		if(model.getNumId()!=0){				
			domain =  designationForClientDao.getOne(model.getNumId());
		}
		
		domain.setDtTrDate(new Date());
		domain.setNumTrUserId(userInfo.getEmployeeId());
		if(model.isValid()){
			domain.setNumIsValid(1);
		}else{
			domain.setNumIsValid(0);
		}
		domain.setDesignationName(model.getDesignationName());
		domain.setDesignationShortCode(model.getDesignationShortCode());
		domain.setDescription(model.getDesription());		
		return domain;
	}
	
	List<DesignationForClientModel> convertDomainToModelList(List<DesignationForClient> domainList){
		List<DesignationForClientModel> list = new ArrayList<DesignationForClientModel>();
			for(int i=0;i<domainList.size();i++){
				DesignationForClient domain = domainList.get(i);
				DesignationForClientModel model = new DesignationForClientModel();			
				model.setNumId(domain.getNumDesignationId());
				model.setEncNumId(encryptionService.encrypt(""+domain.getNumDesignationId()));
				if(domain.getNumIsValid() ==1){
					model.setValid(true);
				}else{
					model.setValid(false);
				}
			
		
				model.setDesignationName(domain.getDesignationName());
				model.setDesignationShortCode(domain.getDesignationShortCode());
				model.setDesription(domain.getDescription());				
				list.add(model);
	}
		return list;
	}
	
	@Override
	public DesignationForClientModel categorydesignationCost(int projectCategoryId,int designationId,int numDeputedAt){ // Function modified by devesh on 30-04-24 to add deputed value
		DesignationForClientModel model = new DesignationForClientModel();
		List<DesignationProjectCategoryMapping> list = designationProjectCategoryMappingDao.getDesignationProjects(projectCategoryId,designationId,numDeputedAt);
		
		if(null != list && list.size()>0){
			DesignationProjectCategoryMapping domain = list.get(0);
			model.setNumId(domain.getNumId());			
			model.setCost(""+domain.getCost());
			model.setFromDate(DateUtils.dateToString(domain.getFromDate()));
			model.setToDate(DateUtils.dateToString(domain.getToDate()));
		}
		return model;
	}
	
	//Function added by devesh on 06-05-24 to get base cost details
	@Override
	public List<DesignationForClientModel> getBaseCostDetails(int categoryId, int designationId){
		List<DesignationForClientModel> modelList = new ArrayList<>();
		List<Object[]> list = projectCategoryDao.getBaseCostDetails(categoryId, designationId);
		
		for (Object[] objArray : list) {
			DesignationForClientModel model = new DesignationForClientModel();
			model.setNumId((int) objArray[0]);			
			model.setCost(""+(double)objArray[1]);
			if(objArray[2]!=null) model.setFromDate(DateUtils.dateToString((Date) objArray[2]));
			if(objArray[3]!=null) model.setToDate(DateUtils.dateToString((Date) objArray[3]));
			model.setNumDeputedAt((int) objArray[5]);
			model.setDesignationName((String) objArray[7]);
			model.setCategoryName((String) objArray[8]);;
			modelList.add(model);
		}
		return modelList;
	}
	//End of function
	
	//Function added by devesh on 07-05-24 to check Duplicate Project Category Designation Mapping
	@Override
	public boolean checkDuplicateProjectCategorydesignationMapping(DesignationForClientModel model){ // Function modified by devesh on 30-04-24 to add deputed value
		List<DesignationProjectCategoryMapping> list = designationProjectCategoryMappingDao.getDesignationProjects(model.getProjectCategoryId(),model.getDesignationId(),model.getNumDeputedAt());
		
		if(null != list && list.size()>0){
			DesignationProjectCategoryMapping domain = list.get(0);			
			String cost = ""+domain.getCost();
			String fromDate = null;
			String toDate = null;
			if(null != domain.getFromDate() && !domain.getFromDate().equals("")){
				fromDate = DateUtils.dateToString(domain.getFromDate());
			}
			if(null != domain.getToDate() && !domain.getToDate().equals("")){
				toDate = DateUtils.dateToString(domain.getToDate());
			}
			
			if(model.getNumId() == domain.getNumId() &&
		        equals(model.getCost(), cost) &&
		        equals(model.getFromDate(), fromDate) &&
		        equals(model.getToDate(), toDate)) {
				return true;
		    } else {
		        return false;
		    }
		}
		
		return false;
	}
	//End of function
	
	// Utility method for null-safe comparison of two objects
	private boolean equals(Object obj1, Object obj2) {
		if (obj1 == null || "".equals(obj1)) {
	        return obj2 == null || "".equals(obj2);
	    }
	    return obj1.equals(obj2);
	}
	//End of function
	
	public String saveprojectCategorydesignationMapping(DesignationForClientModel model){
		
		String result ="";
		//List<DesignationProjectCategoryMapping> list = designationProjectCategoryMappingDao.getDesignationProjects(model.getProjectCategoryId(),model.getDesignationId());
		// Below function modified by devesh on 01-05-24 to add deputed value
		List<DesignationProjectCategoryMapping> list = designationProjectCategoryMappingDao.getDesignationProjects(model.getProjectCategoryId(),model.getDesignationId(),model.getNumDeputedAt());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		
		if(null != list && list.size()>0){
			result="update";
			DesignationProjectCategoryMapping domain = list.get(0);
			domain.setCost(Double.parseDouble(model.getCost()));
			// FromDate and ToDate set to domain for saving by devesh on 01-05-24
			if(null != model.getFromDate() && !model.getFromDate().equals("")){
				try {
					domain.setFromDate(DateUtils.dateStrToDate(model.getFromDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
		        domain.setFromDate(null);
		    }
			if(null != model.getToDate() && !model.getToDate().equals("")){
				try {
					domain.setToDate(DateUtils.dateStrToDate(model.getToDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else {
		        domain.setToDate(null);
		    }
			/*------------- End of Code -----------------*/
			domain.setDtTrDate(new Date());
			domain.setNumTrUserId(userInfo.getEmployeeId());
			designationProjectCategoryMappingDao.save(domain);
		}else{
			result="save";
			DesignationProjectCategoryMapping domain = new DesignationProjectCategoryMapping();
			domain.setCost(Double.parseDouble(model.getCost()));
			DesignationForClient client = new DesignationForClient();
			client.setNumDesignationId(model.getDesignationId());
			domain.setDesignationForClient(client);
			ProjectCategoryMaster projectCategoryMaster = new ProjectCategoryMaster();
			projectCategoryMaster.setNumId(model.getProjectCategoryId());
			domain.setProjectCategoryMaster(projectCategoryMaster);
			// Deputed At, FromDate and ToDate set to domain for saving by devesh on 01-05-24
			domain.setNumDeputedAt(model.getNumDeputedAt()); 
			if(null != model.getFromDate() && !model.getFromDate().equals("")){
				try {
					domain.setFromDate(DateUtils.dateStrToDate(model.getFromDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(null != model.getToDate() && !model.getToDate().equals("")){
				try {
					domain.setToDate(DateUtils.dateStrToDate(model.getToDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			/*------------- End of Code -----------------*/
			domain.setDtTrDate(new Date());
			domain.setNumIsValid(1);
			domain.setNumTrUserId(userInfo.getEmployeeId());
			designationProjectCategoryMappingDao.save(domain);
		}
		return result;
		
	}
	
	@Override
	 public List<DesignationForClientModel> getDesignationByCategory(int categoryId){
		return convertDomainToModelList(designationProjectCategoryMappingDao.getDataByCategory(categoryId));
	 }
	
	@Override
	@PreAuthorize("hasAuthority('DESIGNATION_FOR_CLIENT')")
	public void designationForClientAuthorityCheck(){}
	
	@Override
	@PreAuthorize("hasAuthority('DESIGNATION_CATEGORY_MAPPING')")
	public void projectCategorydesignationMappingAuthorityCheck(){}
}
