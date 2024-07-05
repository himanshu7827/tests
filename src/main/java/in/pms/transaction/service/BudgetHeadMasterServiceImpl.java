package in.pms.transaction.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.BudgetHeadMasterDao;
import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.model.BudgetHeadMasterForm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class BudgetHeadMasterServiceImpl implements BudgetHeadMasterService{
	
	@Autowired
	BudgetHeadMasterDao budgetHeadMasterDao;

	@Autowired
	ProjectMasterService projectMasterService;

	@Autowired
	EncryptionService encryptionService;
	
	@Override
	@PreAuthorize("hasAuthority('READ_BUDGET_HEAD_MST')")
	public List<BudgetHeadMasterForm> getAllBugdetHeadData(){
		return convertBudgetMasterDomainToModelList(budgetHeadMasterDao.getAllBudgetData());			
	}

	public List<BudgetHeadMasterForm> convertBudgetMasterDomainToModelList(List<BudgetHeadMasterDomain> budgetDataList){
		List<BudgetHeadMasterForm> list = new ArrayList<BudgetHeadMasterForm>();
			for(int i=0;i<budgetDataList.size();i++){
				BudgetHeadMasterDomain budgetHeadMasterDomain = budgetDataList.get(i);
				BudgetHeadMasterForm budgetHeadMasterForm = new BudgetHeadMasterForm();
				budgetHeadMasterForm.setStrBudgetHeadName(budgetHeadMasterDomain.getStrBudgetHeadName());
				budgetHeadMasterForm.setStrDescription(budgetHeadMasterDomain.getStrDescription());
				budgetHeadMasterForm.setNumId(budgetHeadMasterDomain.getNumId());
				if(budgetHeadMasterDomain.getNumIsValid()==1){
					budgetHeadMasterForm.setValid(true);
				}else{
					budgetHeadMasterForm.setValid(false);
				}
				/*if(budgetHeadMasterDomain.getNumIsValid()==1){
				budgetHeadMasterForm.setShortCode("Active");		
				}
				else
					budgetHeadMasterForm.setShortCode("Inactive");	*/
				list.add(budgetHeadMasterForm);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateBugdetHeadData(BudgetHeadMasterForm budgetHeadMasterForm){
		String result = "";
		BudgetHeadMasterDomain budgetHeadMasterDomain = budgetHeadMasterDao.getBudgetHeadByName(budgetHeadMasterForm.getStrBudgetHeadName());
	
		 if(null == budgetHeadMasterDomain){
			return null; 
		 }else if(budgetHeadMasterForm.getNumId() != 0){
			 if(budgetHeadMasterDomain.getNumId() == budgetHeadMasterForm.getNumId()){
				 return null; 
			 }else{
				 result = "Budget with same name already exist with Id "+budgetHeadMasterDomain.getNumId();
			 }
		 }
		 else{
				if(budgetHeadMasterDomain.getNumIsValid() == 0){
					result = "Budget Already Registered with Id "+budgetHeadMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Budget Already Registered with Id "+budgetHeadMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	@Override
	@PreAuthorize("hasAuthority('WRITE_BUDGET_HEAD_MST')")
	public long saveBudgetHeadData(BudgetHeadMasterForm budgetHeadMasterForm){
		BudgetHeadMasterDomain budgetHeadMasterDomain = convertBudgetMasterModelToDomain(budgetHeadMasterForm);
		return 	budgetHeadMasterDao.save(budgetHeadMasterDomain).getNumId();

	}
	
	public BudgetHeadMasterDomain convertBudgetMasterModelToDomain(BudgetHeadMasterForm budgetHeadMasterForm){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfo currentUser = (UserInfo)auth.getPrincipal();
		BudgetHeadMasterDomain budgetHeadMasterDomain = new BudgetHeadMasterDomain();
		if(budgetHeadMasterForm.getNumId()!=0){		
			budgetHeadMasterDomain =  budgetHeadMasterDao.getOne(budgetHeadMasterForm.getNumId());
		}
		budgetHeadMasterDomain.setStrBudgetHeadName(budgetHeadMasterForm.getStrBudgetHeadName());
		budgetHeadMasterDomain.setStrDescription(budgetHeadMasterForm.getStrDescription());
		
		budgetHeadMasterDomain.setDtTrDate(new Date());
		if(budgetHeadMasterForm.isValid()){
			budgetHeadMasterDomain.setNumIsValid(1);
			}else{
				budgetHeadMasterDomain.setNumIsValid(0);

			}
		budgetHeadMasterDomain.setNumTrUserId(currentUser.getEmployeeId());

		
		
		
		
		return budgetHeadMasterDomain;
	}

	public void deleteBudgetHeadData(BudgetHeadMasterForm budgetHeadMasterForm) {
		int count= budgetHeadMasterForm.getNumIds().length;
		long[] budgetMaster_ids= new long[count];
		budgetMaster_ids= budgetHeadMasterForm.getNumIds();		
		for(int i=0;i<count;i++){
			BudgetHeadMasterDomain budgetHeadMasterDomain= new BudgetHeadMasterDomain();
			budgetHeadMasterDomain.setNumId(budgetMaster_ids[i]);			
			Date date= new Date();
			budgetHeadMasterDomain.setDtTrDate(date);
			budgetHeadMasterDao.delete(budgetHeadMasterDomain);			
		}		
	}

	@Override
	public List<BudgetHeadMasterForm> getAllActiveBudgetMasterDomain() {
		return convertBudgetMasterDomainToModelList(budgetHeadMasterDao.getAllActiveBudgetMasterDomain());		
	}
	
	@Override
	public List<BudgetHeadMasterDomain> getBudgetHeadDataById(List<Integer> Ids){		
		return budgetHeadMasterDao.getBudgetHeadDataById(Ids);
	}

	@Override
	public JSONArray getAmountAgainstBudgetHeadByProjectId(long projectId) {
		JSONArray resultArray = new JSONArray();
		Map<String,Map<Integer,Float>> finalMap = new HashMap<String,Map<Integer,Float>>();	
		
		List<Object[]> List = budgetHeadMasterDao.getAmountAgainstBudgetHeadByProjectId(projectId) ;
		ProjectMasterDomain projectMasterDomain = projectMasterService.getProjectMasterDataById(projectId);
		int duration = DateUtils.getDurationInYears(projectMasterDomain.getDtProjectStartDate(),projectMasterDomain.getDtProjectEndDate());
		
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);

			
			Float sum = (float) 0;
			//String strSum = null;

			String strBudgetHeadName = (String) obj[0];

			Integer numYear = (Integer) obj[2]; 
			BigInteger projectId1 =  (BigInteger) obj[3];
			projectId1.longValue();
			//Float numBudgetAmount = (Float) obj[1];
			//sum = sum + numBudgetAmount;
			BigDecimal  numBudgetAmount =  (BigDecimal) obj[1];
		 sum = sum + numBudgetAmount.floatValue();

			
			if(finalMap.containsKey(strBudgetHeadName)){
				Map<Integer,Float> tempMap = finalMap.get(strBudgetHeadName);
				tempMap.put(numYear, numBudgetAmount.floatValue());
				finalMap.put(strBudgetHeadName,tempMap);
			}else{
				Map<Integer,Float> tempMap = new HashMap<Integer,Float>();
				tempMap.put(numYear, numBudgetAmount.floatValue());
				finalMap.put(strBudgetHeadName,tempMap);
			}
			
		}
		
		JSONArray headerArray = new JSONArray();
			headerArray.put("Budget Head");
		for(int i=1;i<=duration;i++){
			headerArray.put("Year "+i);
		}

		headerArray.put("Total");

		resultArray.put(headerArray);
		
		
		
			   for (Map.Entry<String,Map<Integer,Float>> entry : finalMap.entrySet())  { 
				   JSONArray dataArray = new JSONArray();
				  	  Map<Integer,Float> tempMap = entry.getValue();
					 dataArray.put(entry.getKey());
					 double totalForBudgetHead =0;
					//double totalBudget = 0;
					 for(int i=1;i<=duration;i++){					
						 if(tempMap.containsKey(i)){
							 dataArray.put(CurrencyUtils.convertToINR(tempMap.get(i)));
							 totalForBudgetHead+=tempMap.get(i);					
							
						 }else{
							 dataArray.put("");
						
						 }
					 }

				
					 dataArray.put(CurrencyUtils.convertToINR(totalForBudgetHead));
					

		

				  	resultArray.put(dataArray);
				   }

		return resultArray;
	}
	
}
