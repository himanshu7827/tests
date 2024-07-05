package in.pms.transaction.service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.master.dao.ProjectExpenditureDetailsDao;
import in.pms.master.dao.ProjectPaymentReceivedDao;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.service.BusinessTypeService;
import in.pms.master.service.EmpTypeMasterService;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.transaction.dao.DashboardDao;
import in.pms.transaction.dao.ManpowerUtilizationDao;
import in.pms.transaction.model.DashboardModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	
	
	@Autowired
	DashboardDao dashboardDao;
	
	@Autowired
	BusinessTypeService businessTypeService;
	
	@Autowired
	EmpTypeMasterService empTypeMasterService;
	
	@Autowired
	ApplicationService applicationService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ProjectPaymentReceivedDao projectPaymentReceivedDao;
	
	@Autowired
	ManpowerUtilizationDao manpowerUtilizationDao;
	
	@Autowired
	ProjectExpenditureDetailsDao projectExpenditureDetailsDao;
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Override
	public JSONArray getgroupBusinessTypeProjectCount(){
		JSONArray finalArray = new JSONArray(); 
		List<String> businessTypeList = businessTypeService.getdistinctBusinessTypeNames();
		JSONArray array = new JSONArray();
		array.put("Group");
		for(int i =0;i<businessTypeList.size();i++){
			array.put(businessTypeList.get(i));		
		}				
		finalArray.put(array);
		
		Map<String,Map<String,Long>> finalMap = new HashMap<String,Map<String,Long>>();		
		
		List<Object[]> dataList = dashboardDao.getgroupBusinessTypeProjectCount();		
		
		
		for(Object[] obj : dataList){	
			if(finalMap.containsKey(""+obj[3])){
				Map<String,Long> dataMap =  finalMap.get(""+obj[3]);
				dataMap.put(""+obj[1], (Long) obj[4]);
			}else{
				Map<String,Long> dataMap = new HashMap<String,Long>();
				dataMap.put(""+obj[1], (Long) obj[4]);
				finalMap.put(""+obj[3], dataMap);
			}			
		}
		
		   for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {      
			   if(entry.getValue().size() != businessTypeList.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String businessTypeName : businessTypeList){	 
					  
					   if(!tempMap.containsKey(businessTypeName)){
						   tempMap.put(businessTypeName, (long) 0);
					   }
					   finalMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		
		   for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {
			   JSONArray dataArray = new JSONArray();
			   dataArray.put(entry.getKey());
			   Map<String,Long> businessTypeWiseCountMap =  entry.getValue();
			   
			   for(int i =0;i<businessTypeList.size();i++){
				   dataArray.put(businessTypeWiseCountMap.get(businessTypeList.get(i)));
				}			   
			   finalArray.put(dataArray);
		   }		
		return finalArray;		
	}
	
	
	@Override
	public JSONArray getgroupEmployeeTypeEmployeeCount() {
		JSONArray finalArray = new JSONArray();
		List<String> empTypeList = empTypeMasterService.getdistinctEmpTypeShortNames();
		JSONArray array = new JSONArray();
		array.put("Group");
		for(int i =0;i<empTypeList.size();i++){
			array.put(empTypeList.get(i));		
		}				
		finalArray.put(array);
		
		Map<String,Map<String,Long>> finalMap = new LinkedHashMap<String,Map<String,Long>>();		
		List<Object[]> dataList = dashboardDao.getgroupEmployeeTypeEmployeeCount();
		
		for(Object[] obj : dataList){	
			if(finalMap.containsKey(""+obj[3])){
				Map<String,Long> dataMap =  finalMap.get(""+obj[3]);
				dataMap.put(""+obj[1], (Long) obj[4]);
			}else{
				Map<String,Long> dataMap = new HashMap<String,Long>();
				dataMap.put(""+obj[1], (Long) obj[4]);
				finalMap.put(""+obj[3], dataMap);
			}			
		}
		
		   for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {      
			   if(entry.getValue().size() != empTypeList.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String empTypeName : empTypeList){	 
					  
					   if(!tempMap.containsKey(empTypeName)){
						   tempMap.put(empTypeName, (long) 0);
					   }
					   finalMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		
		
		   for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {
			   JSONArray dataArray = new JSONArray();
			   dataArray.put(entry.getKey());
			   Map<String,Long> empTypeWiseCountMap =  entry.getValue();
			   
			   for(int i =0;i<empTypeList.size();i++){
				   dataArray.put(empTypeWiseCountMap.get(empTypeList.get(i)));
				}			   
			   finalArray.put(dataArray);
		   }		
		return finalArray;	
	}
	
	//Code for year-wise project count for each department graph
	@Override
	public JSONArray getgroupProjectCount() {
		JSONArray finalArray = new JSONArray();
		List<String> nameColorList = groupMasterService.getdistinctGroupNamesWithColor();
		List<String> groupList = new ArrayList<String>();
		
		JSONArray groupNameArray = new JSONArray();
		JSONArray groupColorArray = new JSONArray();
		groupNameArray.put("Group");		
		for(int i =0;i<nameColorList.size();i++){
			String [] temp = nameColorList.get(i).split("=");			
			groupNameArray.put(temp[1]);
			groupColorArray.put(temp[0]);
			groupList.add(temp[1]);
		}				
		finalArray.put(groupNameArray);
		finalArray.put(groupColorArray);
		
		
		
		Map<String,Map<String,Long>> finalMap = new LinkedHashMap<String,Map<String,Long>>();		
		List<Object[]> dataList = dashboardDao.getgroupProjectCount();
		/*for(int i=0;i<dataList.size();i++){
			System.out.println(dataList.get(i)[0] +"\t"+dataList.get(i)[1]+"\t"+dataList.get(i)[2]+"\t"+ dataList.get(i)[3]);
		}*/
		for(Object[] obj : dataList){	
			if(finalMap.containsKey(""+obj[2])){
				Map<String,Long> dataMap =  finalMap.get(""+obj[2]);
				dataMap.put(""+obj[1], (Long) obj[3]);
			}else{
				Map<String,Long> dataMap = new LinkedHashMap<String,Long>();
				dataMap.put(""+obj[1], (Long) obj[3]);
				finalMap.put(""+obj[2], dataMap);
			}			
		}
		
	for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {      
			   if(entry.getValue().size() != groupList.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String projectName : groupList){	 
					  
					   if(!tempMap.containsKey(projectName)){
						   tempMap.put(projectName, (long) 0);
					   }
					   finalMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		
		 for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {
			   JSONArray dataArray = new JSONArray();
			   dataArray.put(entry.getKey());
			   Map<String,Long> groupWiseProjectCountMap =  entry.getValue();
			   
			   for(int i =0;i<groupList.size();i++){
				   dataArray.put(groupWiseProjectCountMap.get(groupList.get(i)));
				}			   
			   finalArray.put(dataArray);
		   }		
		return finalArray;	
	}
	
	
	

	@Override
	public long getTotalActiveEmployees() {
		long activeEmployeeCount = dashboardDao.getTotalActiveEmployees();	
		return activeEmployeeCount;
	}

	@Override
	public List<DashboardModel> getAllActiveProjectName() {
		List<Object[]> projectList = dashboardDao.getAllActiveProjectName();	
		List<DashboardModel> dataList = new ArrayList<DashboardModel>();
		
		for(int i=0;i<projectList.size();i++){
			Object[] obj = projectList.get(i);
			ProjectMasterDomain projectMaster = (ProjectMasterDomain) obj[0];
			GroupMasterDomain groupMaster = (GroupMasterDomain) obj[1];
			
				DashboardModel dashboardModel = new DashboardModel();
				dashboardModel.setProjectName(projectMaster.getStrProjectName());
				dashboardModel.setGroupName(groupMaster.getGroupName());
				//dashboardModel.setOrganisationName(groupMaster.getOrganisationMasterDomain());
				dataList.add(dashboardModel);
			
			
		}
				
		return dataList;
}

	
	public JSONArray getProjectCountByGroupandBusinessType(){
		JSONArray finalArray = new JSONArray();
		List<String> nameColorList = groupMasterService.getdistinctGroupNamesWithColor();
		List<String> groupList = new ArrayList<String>();
		JSONArray groupNameArray = new JSONArray();
		JSONArray groupColorArray = new JSONArray();
		groupNameArray.put("Group");		
		for(int i =0;i<nameColorList.size();i++){
			String [] temp = nameColorList.get(i).split("=");			
			groupNameArray.put(temp[1]);
			groupColorArray.put(temp[0]);
			groupList.add(temp[1]);
		}				
		finalArray.put(groupNameArray);
		finalArray.put(groupColorArray);
		
		
		Map<String,Map<String,Long>> dataMap = new HashMap<String, Map<String,Long>>();
		
		List<Object[]> list = dashboardDao.getProjectCountByGroupandBusinessType();
		for(int i=0;i<list.size();i++){
			Object[] obj = list.get(i);
			long count = (long) obj[0];
			String groupName=  (String) obj[1];
			String businessTypeName =  (String) obj[2];
			 
			if(dataMap.containsKey(businessTypeName)){
				Map<String, Long> map = dataMap.get(businessTypeName);
				map.put(groupName, count);
			}else{
				Map<String, Long> map = new HashMap<String, Long>();
				map.put(groupName, count);
				dataMap.put(businessTypeName, map);
				
			}
			
		}
		
		for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {      
			   if(entry.getValue().size() != groupList.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String groupName : groupList){	 
					  
					   if(!tempMap.containsKey(groupName)){
						   tempMap.put(groupName,  (long) 0);
					   }
					   dataMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
			 for (Map.Entry<String,Map<String,Long>> entry : dataMap.entrySet())  {
			   JSONArray dataArray = new JSONArray();
			  // dataArray.put(entry.getKey());
			   Map<String,Long> groupWiseProjectCountMap =  entry.getValue();
			   JSONArray defaultArray = new JSONArray();
			   defaultArray.put("Groups");
			   defaultArray.put(entry.getKey());
			   dataArray.put(defaultArray);
			   
			   for(int i =0;i<groupList.size();i++){
				   JSONArray internalArray = new JSONArray();			
					   internalArray.put(groupList.get(i));
					   internalArray.put(groupWiseProjectCountMap.get(groupList.get(i)));				  
				   dataArray.put(internalArray);
				   
				}			   
			   finalArray.put(dataArray);
		   }	 
		return finalArray;
	}



	@Override
	public JSONArray getProjectCountByBusinessType() {
		JSONArray finalArray = new JSONArray(); 
		JSONArray tempArray = new JSONArray(); 
			tempArray.put("Business Type");
			tempArray.put("Count");
		finalArray.put(tempArray);
			
		
		List<Object[]> dataList = dashboardDao.getProjectCountByBusinessType();		
		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONArray array = new JSONArray();
			array.put(obj[1]);
			array.put(obj[0]);
			finalArray.put(array);
		}
		
		return finalArray;		
	}


	@Override
	public JSONArray getProjectCostByBusinessType() {
		JSONArray finalArray = new JSONArray(); 
		JSONArray tempArray = new JSONArray(); 
			tempArray.put("Business Type");
			tempArray.put("Cost");
		finalArray.put(tempArray);
		
		List<Object[]> dataList = dashboardDao.getProjectCostByBusinessType();		
		for(int i=0;i<dataList.size();i++){
			Object[] obj = dataList.get(i);
			JSONArray array = new JSONArray();
			array.put(obj[1]);
			array.put(obj[0]);			
			finalArray.put(array);
		}	
		
		return finalArray;
	}

////Count to find number of ongoing projects based on organization
	@Override
	public long getAllOngoingProjectCount() {
		long ongoingProjectCount = dashboardDao.getOngoingProjectsCountByOrganisation();
		return ongoingProjectCount;
	}

////Count to find total cost of ongoing projects based on organization
	@Override
	public double getTotalOngoingProjectsCost() {
		double ongoingProjectCost = dashboardDao.getOngoingProjectsCostByOrganisation();
		//Division by 1 Lakh to show amount in Lakhs and round is used for showing up to 2 decimal places
			return CurrencyUtils.round((ongoingProjectCost/100000),2);
	}
	
// Get Total outlay share on 22/05/2023
	public double getCDACoutlay() {
		double ongoingProjectCost = Double.parseDouble(dashboardDao.getCDACoutlay());
			return CurrencyUtils.round((ongoingProjectCost/100000),2);
	}


////Count total ongoing projects and their cost based on group and organization
	@Override
	public List<DashboardModel> getProjectCountandCostByGroup() {
		List<Object[]> List = dashboardDao.getProjectCountandCostByGroup();
		List<DashboardModel> dataList = new ArrayList<DashboardModel>();
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			//ProjectMasterDomain projectMaster = (ProjectMasterDomain) obj[0];
			String groupName =  (String) obj[2];
			long projectCount = (long) obj[0];
			double projectCost = Double.parseDouble(""+obj[1]);
			// Total Outlay Display -->
			double totalCost = Double.parseDouble(""+obj[3]);
			
				DashboardModel dashboardModel = new DashboardModel();
				
				dashboardModel.setGroupName(groupName);
				dashboardModel.setProjectCount(projectCount);
				dashboardModel.setProjectCost(projectCost);
				dashboardModel.setTotalCost(totalCost);
				dataList.add(dashboardModel);
			
			
		}
		return dataList;
	}


	@Override
	public String getExpenditure(Date startDate) {
		//System.out.println("startDate at Dashboard Service Impl"+startDate);
		double expenditure1 =  0;
		double expenditure2 =  0;
		double totalExpenditure = 0;
		String total = null;

		List<Double> manpowerExpenditure = manpowerUtilizationDao.getExpenditureFromManpower(startDate);
		List<Double> projectExpenditure = projectExpenditureDetailsDao.getExpenditureFromProject(startDate);
		
		if(manpowerExpenditure.size()>0){
			if(null != manpowerExpenditure.get(0)){
				 expenditure1 = manpowerExpenditure.get(0);
			}	
			else{
				expenditure1 = 0;
			}
		}	
		
		if(projectExpenditure.size()>0){
			if(null != projectExpenditure.get(0)){
				 expenditure2 = projectExpenditure.get(0);
			}		
			else{
				expenditure2 = 0;
			}
		}	
		//System.out.println();
		totalExpenditure = expenditure1 + expenditure2;
		//System.out.println(totalExpenditure);
		total = CurrencyUtils.convertToINR(totalExpenditure);
		//System.out.println(total);
		return total;
	}


	@Override
	public JSONArray getExpenditureByGroup(Date startDate) {
		//System.out.println(startDate);
		JSONArray finalArray = new JSONArray(); 
		JSONArray headerArray = new JSONArray();
		
		headerArray.put("Group");
		headerArray.put("Expenditure");
		finalArray.put(headerArray);
		JSONArray colorArray = new JSONArray();
	
		
		Map<String,Double> finalMap = new LinkedHashMap<String,Double>();		
		
		List<Object[]> dataListManpower = manpowerUtilizationDao.getManpowerExpenditureByGroup(startDate);		
		List<Object[]> dataListProjExp = projectExpenditureDetailsDao.getProjectExpenditureByGroup(startDate);

		for(Object[] obj : dataListManpower){			
				colorArray.put(obj[3]);
				finalMap.put(""+obj[2], (Double) obj[0]);					
		}
			
		
	for(Object[] obj : dataListProjExp){	
			if(finalMap.containsKey(""+obj[2])){
				Double manpowerExpenditure =  finalMap.get(""+obj[2]);
				manpowerExpenditure = manpowerExpenditure+(Double)obj[0];
				finalMap.put(""+obj[2],  manpowerExpenditure);
			}else{				
				colorArray.put(obj[3]);
				finalMap.put(""+obj[2], (Double) obj[0]);
			}			
		}
		
	finalArray.put(colorArray);
	
	  for (Map.Entry<String,Double> entry : finalMap.entrySet())  {      
		  JSONArray tempArray = new JSONArray(); 
		  tempArray.put(entry.getKey());
		  tempArray.put(entry.getValue());
		  //tempArray.put(CurrencyUtils.convertToINR(entry.getValue()).substring(2));			   
		  finalArray.put(tempArray);	  	 
	   }
	
		return finalArray;		
		
	
	}


	@Override
	public List<DashboardModel> getExpenditureGroupwiseProjectwise() {
		List<Object[]> List = dashboardDao.getProjectWiseExpenditure();
		List<DashboardModel> dataList = new ArrayList<DashboardModel>();
		Double sum = (double) 0;
		Double numExpenditureAmount =0.0;
		Double salaryInProject =0.0;
		Double roundOfExpenditure = 0.0;
		String strTotalExpenditure = null;
		for(int i=0;i<List.size();i++){
			Object[] obj = List.get(i);
			String groupName = (String) obj[0];
			String projectName = (String) obj[1];
			//System.out.println("groupName"+ groupName);
			//System.out.println("projectName"+projectName );
		
			Double tempExpenditure=  (Double) obj[3]; 
			if(null != tempExpenditure){
				 numExpenditureAmount =tempExpenditure;
			}
			Double tempSalary = (Double) obj[4];
			if(null != tempSalary){
				salaryInProject = tempSalary;
			}
			sum = (Double)(numExpenditureAmount + salaryInProject);
			roundOfExpenditure = CurrencyUtils.round((sum/100000), 2);
			strTotalExpenditure = CurrencyUtils.convertToINR(roundOfExpenditure);
			BigInteger b=new BigInteger(""+obj[2]);
			DashboardModel dashboardModel =  new DashboardModel();
			dashboardModel.setGroupName(groupName);
			dashboardModel.setProjectName(projectName);
			dashboardModel.setStrTotalExpenditure(strTotalExpenditure+" Lakhs");
			dashboardModel.setProjectId(b.longValue());
			dashboardModel.setEncProjectId(encryptionService.encrypt(""+b.longValue()));
			dataList.add(dashboardModel);
		}
		return dataList;
	}


	@Override
	public JSONArray getGenderWiseEmployeeCount() {
		
		JSONArray finalArray = new JSONArray();
		List<String> empGenderList = employeeMasterService.getdistinctEmpGender();
		JSONArray array = new JSONArray();
		array.put("Gender");
		array.put("Count");
					
		finalArray.put(array);
	
		List<Object[]> dataList = dashboardDao.getGenderWiseEmployeeCount();
		
		for(Object[] obj : dataList){	
			JSONArray dataArray = new JSONArray();
			dataArray.put(obj[0]);
			dataArray.put(obj[1]);
			finalArray.put(dataArray);
		}		
		return finalArray;	
	}




//////For GC Dashboard graph 
	@Override
	public JSONArray getProjectDetailGraph() {
		/*JSONArray finalArray = new JSONArray();
		List<String> nameColorList = groupMasterService.getdistinctGroupNamesWithColor();
		List<String> groupList = new ArrayList<String>();
		
		JSONArray groupNameArray = new JSONArray();
		JSONArray groupColorArray = new JSONArray();
		groupNameArray.put("Group");		
		for(int i =0;i<nameColorList.size();i++){
			String [] temp = nameColorList.get(i).split("=");			
			groupNameArray.put(temp[1]);
			groupColorArray.put(temp[0]);
			groupList.add(temp[1]);
		}				
		finalArray.put(groupNameArray);
		finalArray.put(groupColorArray);
		
		
		
		Map<String,Map<String,Long>> finalMap = new LinkedHashMap<String,Map<String,Long>>();		
		List<Object[]> dataList = dashboardDao.getgroupProjectCount();
		for(int i=0;i<dataList.size();i++){
			System.out.println(dataList.get(i)[0] +"\t"+dataList.get(i)[1]+"\t"+dataList.get(i)[2]+"\t"+ dataList.get(i)[3]);
		}
		for(Object[] obj : dataList){	
			if(finalMap.containsKey(""+obj[2])){
				Map<String,Long> dataMap =  finalMap.get(""+obj[2]);
				dataMap.put(""+obj[1], (Long) obj[3]);
			}else{
				Map<String,Long> dataMap = new LinkedHashMap<String,Long>();
				dataMap.put(""+obj[1], (Long) obj[3]);
				finalMap.put(""+obj[2], dataMap);
			}			
		}
		
	for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {      
			   if(entry.getValue().size() != groupList.size()){
				 
				  Map<String,Long> tempMap = entry.getValue();
				   for(String projectName : groupList){	 
					  
					   if(!tempMap.containsKey(projectName)){
						   tempMap.put(projectName, (long) 0);
					   }
					   finalMap.put(entry.getKey(), tempMap); 
				   }				   
			   }
		   }
		
		 for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {
			   JSONArray dataArray = new JSONArray();
			   dataArray.put(entry.getKey());
			   Map<String,Long> groupWiseProjectCountMap =  entry.getValue();
			   
			   for(int i =0;i<groupList.size();i++){
				   dataArray.put(groupWiseProjectCountMap.get(groupList.get(i)));
				}			   
			   finalArray.put(dataArray);
		   }		
		return finalArray;	*/
		return null;
	}


/*
 private static Object[] checkUtilizationForProject(List<Object[]> expenditureDetails, Object groupId) {
		  Object[] utilization = expenditureDetails.stream()
	                .filter(x -> groupId == x[1])
	                .findAny()
	                .orElse(null);	  
		  return utilization;	               
	    }*/
	
	//Bar Graph: Group-wise Proposals submitted in last 3 years
		@Override
		public JSONArray getgroupProposalCount() {
			JSONArray finalArray = new JSONArray();
			List<String> nameColorList = groupMasterService.getdistinctGroupNamesWithColor();
			List<String> groupList = new ArrayList<String>();
			
			JSONArray groupNameArray = new JSONArray();
			JSONArray groupColorArray = new JSONArray();
			groupNameArray.put("Group");		
			for(int i =0;i<nameColorList.size();i++){
				String [] temp = nameColorList.get(i).split("=");			
				groupNameArray.put(temp[1]);
				groupColorArray.put(temp[0]);
				groupList.add(temp[1]);
			}				
			finalArray.put(groupNameArray);
			finalArray.put(groupColorArray);
			
			
			
			Map<String,Map<String,Long>> finalMap = new LinkedHashMap<String,Map<String,Long>>();		
			List<Object[]> dataList = dashboardDao.getgroupProposalCount();
			
			for(Object[] obj : dataList){	
				if(finalMap.containsKey(""+obj[2])){
					Map<String,Long> dataMap =  finalMap.get(""+obj[2]);
					dataMap.put(""+obj[1], (Long) obj[3]);
				}else{
					Map<String,Long> dataMap = new LinkedHashMap<String,Long>();
					dataMap.put(""+obj[1], (Long) obj[3]);
					finalMap.put(""+obj[2], dataMap);
				}			
			}
			
		for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {      
				   if(entry.getValue().size() != groupList.size()){
					 
					  Map<String,Long> tempMap = entry.getValue();
					   for(String projectName : groupList){	 
						  
						   if(!tempMap.containsKey(projectName)){
							   tempMap.put(projectName, (long) 0);
						   }
						   finalMap.put(entry.getKey(), tempMap); 
					   }				   
				   }
			   }
			
			 for (Map.Entry<String,Map<String,Long>> entry : finalMap.entrySet())  {
				   JSONArray dataArray = new JSONArray();
				   dataArray.put(entry.getKey());
				   Map<String,Long> groupWiseProjectCountMap =  entry.getValue();
				   
				   for(int i =0;i<groupList.size();i++){
					   dataArray.put(groupWiseProjectCountMap.get(groupList.get(i)));
					}			   
				   finalArray.put(dataArray);
			   }		
			 
			return finalArray;	
			
		}

	
		@Override
		public List<DashboardModel> getOngoinProjectlist(ProjectMasterModel projectMastermodel) {
	
			String strGroupId = encryptionService.dcrypt(projectMastermodel.getEncGroupId());
			long groupId = Long.parseLong(strGroupId);
			List<Object[]> List = dashboardDao.getOngoinProjectlist(groupId);
			List<DashboardModel> dataList = new ArrayList<DashboardModel>();
			for(int i=0;i<List.size();i++){
				Object[] obj = List.get(i);
				//ProjectMasterDomain projectMaster = (ProjectMasterDomain) obj[0];
				String groupName =  (String) obj[2];
				long projectCount = (long) obj[0];
				double projectCost = Double.parseDouble(""+obj[1]);
				
					DashboardModel dashboardModel = new DashboardModel();
					
					dashboardModel.setGroupName(groupName);
					dashboardModel.setProjectCount(projectCount);
					dashboardModel.setProjectCost(projectCost);
				
					dataList.add(dashboardModel);
				
				
			}
			
			return dataList;
		}
		
		@Override
		public long getActiveEmployeesAtCDAC() {
			long activeEmployeeCount = dashboardDao.getActiveEmployeesAtCDAC();	
			return activeEmployeeCount;
		}
		//Added by devesh on 14/6/23 for employee deputed at client count
		@Override
		public long getActiveEmployeesAtCLIENT() {
			long activeEmployeeCount = dashboardDao.getActiveEmployeesAtCLIENT();	
			return activeEmployeeCount;
		}
		//End of employee deputed at client count
	

@Override
public JSONArray getGroupGenderWiseEmployeeCount(String groupName) {
	
	JSONArray finalArray = new JSONArray();
	List<String> empGenderList = employeeMasterService.getdistinctEmpGender();
	JSONArray array = new JSONArray();
	array.put("Gender");
	array.put("Count");
				
	finalArray.put(array);

	List<Object[]> dataList = dashboardDao.getGroupGenderWiseEmployeeCount(groupName);
	
	for(Object[] obj : dataList){	
		JSONArray dataArray = new JSONArray();
		dataArray.put(obj[0]);
		dataArray.put(obj[1]);
		finalArray.put(dataArray);
	}		
	return finalArray;	
}

//Added by devesh on 13-10-23 to get last Updated Date of Employees
public List<Object[]> getLastUpdatedAtDateForEmployees() {
	List<Object[]> obj = dashboardDao.getLastUpdatedAtDateForEmployees();
	// Convert the date to a string using DateUtils.dateToString
    List<Object[]> resultObjList = new ArrayList<>();
    for (Object[] row : obj) {
        if (row[0] instanceof Date) {
            Date date = (Date) row[0];
            String dateString = DateUtils.dateToString(date); // Use your DateUtils method

            Object[] newRow = { dateString };
            resultObjList.add(newRow);
        }
    }

    return resultObjList;

}
//End of Last Updated

}