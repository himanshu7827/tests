package in.pms.timesheet.dao;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import in.pms.global.dao.DaoHelper;
import in.pms.timesheet.controller.HomeController;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModel;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModelId;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class TSProjectWeekEffortTransactionDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	//Purpose - To save data in pms_date_use_project table
	@Transactional
	public Integer entryInTimesheetProjectWeekEffortTrn(ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel){

		projectWeekEffortTransactionModel = daoHelper.merge(ProjectWeekEffortTransactionModel.class, projectWeekEffortTransactionModel);

        return projectWeekEffortTransactionModel.getId().getParentTransactionModel().getTransactionId();
	}
	
	//Purpose - To get total project Effort for all week in each project for particular month and year to show on UI
	public List<Object> getTotalProjectEffort(List<Integer> transactionId){
		
		/*
		 * Integer empId = dateUseProjectModelId.getEmpId(); String monthName =
		 * dateUseProjectModelId.getMonthName(); String weekName =
		 * dateUseProjectModelId.getWeekName(); //long projectId =
		 * dateUseProjectModelId.getProjectId(); String yearName =
		 * dateUseProjectModelId.getYearName();
		 */
		List<Object> result = new ArrayList<Object>();
		if(transactionId.isEmpty()) {
			return result;
		}
		//for(int i= 0; i<transactionId.size(); i++) {
			String query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.id.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.id.parentTransactionModel.transactionId in (?0) AND e.id.status='p' GROUP BY e.id.projectId" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(transactionId);
			result = (daoHelper.findByQuery(query, paraList));
		//}
		/*
		 * if(weekName == null){ String query =
		 * "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.status='p' GROUP BY e.id.projectId"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); result =
		 * daoHelper.findByQuery(query, paraList); }else{ String query =
		 * "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.status='p' AND e.id.weekName = ?3 GROUP BY e.id.projectId"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); paraList.add(weekName);
		 * result = daoHelper.findByQuery(query, paraList); }
		 */
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//Purpose - To get total activity Effort for all week in each activity for particular month and year to show on UI
	public List<Object> getTotalActivityEffort(List<Integer> transactionId){
		
		/*
		 * Integer empId = dateUseProjectModelId.getEmpId(); String monthName =
		 * dateUseProjectModelId.getMonthName(); String weekName =
		 * dateUseProjectModelId.getWeekName(); //long projectId =
		 * dateUseProjectModelId.getProjectId(); String yearName =
		 * dateUseProjectModelId.getYearName();
		 */
		List<Object> result = new ArrayList<Object>();
		if(transactionId.isEmpty()) {
			return result;
		}
		//for(int i= 0; i<transactionId.size(); i++) {
			String query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.id.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.id.parentTransactionModel.transactionId in (?0) AND e.id.status='m' GROUP BY e.id.projectId" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(transactionId);
			result = (daoHelper.findByQuery(query, paraList));
		//}
		/*
		 * if(weekName == null){ String query =
		 * "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.status='m' GROUP BY e.id.projectId"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); result =
		 * daoHelper.findByQuery(query, paraList); }else{ String query =
		 * "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.status='m' AND e.id.weekName = ?3 GROUP BY e.id.projectId"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); paraList.add(weekName);
		 * result = daoHelper.findByQuery(query, paraList); }
		 */
		
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//Purpose - To get each Effort in each project, week for particular month and year to show on UI
	public List<Object> getEachAndEveryEffort(List<Integer> transactionIdEachAndEvery){
		
		
		/*
		 * Integer empId = dateUseProjectModelId.getEmpId(); String monthName =
		 * dateUseProjectModelId.getMonthName(); String weekName =
		 * dateUseProjectModelId.getWeekName(); //long projectId =
		 * dateUseProjectModelId.getProjectId(); String yearName =
		 * dateUseProjectModelId.getYearName();
		 */
		
		List<Object> result = new ArrayList<Object>();
		if(transactionIdEachAndEvery.isEmpty()) {
			return result;
		}
		//for(int i = 0; i<transactionIdEachAndEvery.size(); i++) {
			String query = "SELECT f.weekName, e.totalEffortProject, e.id.projectId, e.id.status FROM ProjectWeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.id.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.id.parentTransactionModel.transactionId in (?0) ORDER BY CAST(f.weekName AS double) ASC" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(transactionIdEachAndEvery);
			result = (daoHelper.findByQuery(query, paraList));
		//}
		/*
		 * if(weekName == null){ String query =
		 * "SELECT e.id.weekName, e.totalEffortProject, e.id.projectId, e.id.status FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 ORDER BY CAST(e.id.weekName AS double) ASC"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); result =
		 * daoHelper.findByQuery(query, paraList); }else{ String query =
		 * "SELECT e.id.weekName, e.totalEffortProject, e.id.projectId, e.id.status FROM DateUseProjectModel e WHERE e.isValid=1 AND e.id.empId = ?0 AND e.id.monthName = ?1 AND e.id.yearName = ?2 AND e.id.weekName = ?3 ORDER BY CAST(e.id.weekName AS double) ASC"
		 * ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(empId);
		 * paraList.add(monthName); paraList.add(yearName); paraList.add(weekName);
		 * result = daoHelper.findByQuery(query, paraList); }
		 */
		
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//purpose - To get miscOtherActivityId from model's Primary key
	public List<Long> getMiscOtherActivityId(ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel){
		String query = "SELECT e.miscOtherActivityId FROM ProjectWeekEffortTransactionModel e WHERE e.id = ?0  AND e.isValid=?1";
		ArrayList<Object> paraList = new ArrayList<>();
		paraList.add(projectWeekEffortTransactionModel.getId());
		paraList.add(projectWeekEffortTransactionModel.getIsValid());
		List<Long> result = daoHelper.findByQuery(query, paraList);
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//REPORTS
	
	//R3   -    LOGGED IN USER MONTH WISE REPORT
	//Purpose - To fetch month wise effort for project and Activity for reporting
	public List<Object> reportUserMonthWiseEffort(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId){
	Integer empId = projectweekEffortTransactionModelId.getParentTransactionModel().getEmpId();
	String yearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
	String status = projectweekEffortTransactionModelId.getStatus();
	String query = "SELECT e.id.parentTransactionModel.monthName, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e WHERE e.isValid=1 AND e.id.parentTransactionModel.empId = ?0 AND e.id.parentTransactionModel.yearName = ?1  AND e.id.status = ?2 GROUP BY e.id.parentTransactionModel.monthName" ;
	
	ArrayList<Object> paraList = new ArrayList<>();
	paraList.add(empId);
	paraList.add(yearName);
	paraList.add(status);
	
	List<Object> result = daoHelper.findByQuery(query, paraList);
	
	return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	
	
	//R4   -    LOGGED IN USER ACTIVITY WISE(Pie) REPORT (Month filter)
		//  Purpose to fetch data in pie chart Activity month wise 
	public List<Object> reportUserActivityWiseEffortPie(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId){
	
	Integer empId = projectweekEffortTransactionModelId.getParentTransactionModel().getEmpId();
	String yearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
	String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
	String query = "";
	ArrayList<Object> paraList = new ArrayList<>();
	if(monthName.equals("AllMonths")){
	 query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId WHERE e.id.parentTransactionModel.empId = ?0 AND e.id.parentTransactionModel.yearName = ?1 AND e.id.status='m' GROUP BY p.MiscActivityName" ;
	 paraList.add(empId);
		paraList.add(yearName);
	
	}
	else{
		query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId WHERE e.id.parentTransactionModel.empId = ?0 AND e.id.parentTransactionModel.yearName = ?1 AND e.id.parentTransactionModel.monthName = ?2 AND e.id.status='m' GROUP BY p.MiscActivityName,e.id.parentTransactionModel.monthName" ;
	paraList.add(empId);
	paraList.add(yearName);
	paraList.add(monthName);
	}
	List<Object> result = daoHelper.findByQuery(query, paraList);
	
	return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	
	
	/*
	* //R7 - GROUP PROJECT-MONTH WISE REPORT //Purpose - To fetch month wise effort
	* for each project for reporting public List<Integer>
	* reportGroupProjectMonthEffort(ProjectWeekEffortTransactionModelId
	* projectgroupmodelid, long groupID){
	* 
	* String yearName =
	* projectgroupmodelid.getParentTransactionModel().getYearName(); long projectID
	* = projectgroupmodelid.getProjectId(); List<Integer> res = new
	* ArrayList<Integer>(); // Create a List<string> with month names List<String>
	* months = Arrays.asList( "January", "February", "March", "April", "May",
	* "June", "July", "August", "September", "October", "November", "December" );
	* 
	* for(int i=0; i<months.size();i++){ String query =
	* "SELECT e.totalEffortProject FROM DateUseProjectModel e JOIN fetch EmployeeMasterModel p ON p.numId = e.id.empId WHERE p.numGroupId = ?0 AND e.id.yearName = ?1 AND e.id.projectId = ?2 AND e.id.monthName = ?3 AND e.id.status='p'"
	* ; ArrayList<Object> paraList = new ArrayList<>(); paraList.add(groupID);
	* paraList.add(yearName); paraList.add(projectID); paraList.add(months.get(i));
	* List<String> result = daoHelper.findByQuery(query, paraList); int sum = 0;
	* 
	* for (String numericString : result) { try { // Attempt to parse the string as
	* an integer and add to the sum int number = Integer.parseInt(numericString);
	* sum += number; } catch (NumberFormatException e) { // Handle the case where
	* the string is not a valid integer
	* System.err.println("Invalid numeric string: " + numericString); } }
	* res.add(sum); }
	* 
	* 
	* return (res != null && !res.isEmpty()) ? res : new ArrayList<Integer>(); }
	*/
	
	
	public static void generateMonthYearLists(String range, List<String> rangeMonths, List<String> rangeYears) {
	LocalDate currentDate = LocalDate.now();
	DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
	
	int monthsToAdd = parseSelectedMonths(range);
	
	for (long i = 0; i < monthsToAdd; i++) {
	    LocalDate date = currentDate.minusMonths(i+1);
	    rangeMonths.add(date.format(monthFormatter));
	    rangeYears.add(String.valueOf(date.getYear()));
	}
	}
	
	private static int parseSelectedMonths(String range) {
	return Integer.parseInt(range.replaceAll("[^\\d]", ""));
	}
	
	//R9   -    LOGGED IN USER PROJECT(Pie) WISE REPORT (Month filter)
	//Purpose - to fetch data in pie chart Project effort month wise 
	public List<Object> reportUserProjectWiseEffortPie(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId){
		
		Integer empId = projectweekEffortTransactionModelId.getParentTransactionModel().getEmpId();
		String yearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
		String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
		String query = "";
		ArrayList<Object> paraList = new ArrayList<>();
		if(monthName.equals("AllMonths")){
			query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.parentTransactionModel.empId = ?0 AND e.id.parentTransactionModel.yearName = ?1 AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo" ;
			paraList.add(empId);
			paraList.add(yearName);
		}
		else{
			query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.parentTransactionModel.empId = ?0 AND e.id.parentTransactionModel.yearName = ?1 AND e.id.parentTransactionModel.monthName = ?2 AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo,e.id.parentTransactionModel.monthName" ;			
			paraList.add(empId);
			paraList.add(yearName);
			paraList.add(monthName);
		}
		List<Object> result = daoHelper.findByQuery(query, paraList);
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	// R10	-	GROUP MONTH WISE REPORT (PROJECT + ACTIVITY)  
		//Purpose	-	 to fetch data for combo chart for project and activity
	public List<Object> reportGroupProjectActivityMonthEffort(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupID){
		
		
		String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
		String status = projectweekEffortTransactionModelId.getStatus();
		String query = "SELECT e.id.parentTransactionModel.monthName, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN fetch EmployeeMasterModel p ON p.numId = e.id.parentTransactionModel.empId WHERE p.numGroupId = ?0 AND e.isValid=1 AND e.id.parentTransactionModel.yearName = ?1  AND e.id.status = ?2 GROUP BY e.id.parentTransactionModel.monthName" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(groupID);
			paraList.add(YearName);
			paraList.add(status);
			List<Object> result = daoHelper.findByQuery(query, paraList);
			return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//Purpose	-	 to fetch data for combo chart for project and activity
	public List<Object> reportGroupProjectActivityMonthEffortNotGC(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupID, List<Long> empIdsUnderLoggedUser){
		
		String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
		String status = projectweekEffortTransactionModelId.getStatus();
		List<Integer> empIdsUnderLoggedUserInt = empIdsUnderLoggedUser.stream()
	        .map(Long::intValue) // Convert Long to Integer
	        .collect(Collectors.toList());
		
		// Check if empIdsUnderLoggedUserInt is empty
	    if (empIdsUnderLoggedUserInt.isEmpty()) {
	        // Return an empty list as no employee IDs are provided
	        return new ArrayList<>();
	    }
		String query = "SELECT e.id.parentTransactionModel.monthName, SUM(CAST(e.totalEffortProject AS double)) FROM ProjectWeekEffortTransactionModel e JOIN fetch EmployeeMasterModel p ON p.numId = e.id.parentTransactionModel.empId WHERE p.numGroupId = ?0 AND e.isValid=1 AND e.id.parentTransactionModel.yearName = ?1  AND e.id.status = ?2 AND e.id.parentTransactionModel.empId IN ?3 GROUP BY e.id.parentTransactionModel.monthName" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(groupID);
			paraList.add(YearName);
			paraList.add(status);
			paraList.add(empIdsUnderLoggedUserInt);
			List<Object> result = daoHelper.findByQuery(query, paraList);
			return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
		
	//R11 - GROUP PROJECT-MONTH WISE PIE CHART 
	public List<Object> reportMonthProjectGroupWiseEffort(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupId){
	
	//Integer empId = dateUseProjectModelId.getEmpId();
	String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
	String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
	String query = "";
	ArrayList<Object> paraList = new ArrayList<>();
	if(monthName.equals("AllMonths")){
		query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo "+
		"FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.parentTransactionModel.yearName = ?0 AND "+
		"e.id.parentTransactionModel.empId IN (SELECT distinct s.numEmpId FROM EmployeeRoleMstModel s WHERE s.numGroupId= ?1) "+
		"AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo" ;
		paraList.add(YearName);
		paraList.add((int)groupId);
		
		
	}
	else{
		query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo "+
				"FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.parentTransactionModel.yearName = ?0 AND e.id.parentTransactionModel.monthName = ?1 AND "+
				"e.id.parentTransactionModel.empId IN (SELECT distinct s.numEmpId FROM EmployeeRoleMstModel s WHERE s.numGroupId= ?2) "+
				"AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo" ;
		/*query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo FROM DateUseProjectModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId JOIN fetch EmployeeRoleMstModel s ON p.numId = s.numProjectId WHERE s.numGroupId= ?0 AND e.id.yearName = ?1 AND e.id.monthName = ?2 AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo,e.id.monthName" ;*/			
		
		paraList.add(YearName);
		paraList.add(monthName);
		paraList.add((int)groupId);
	
	}
	List<Object> result = daoHelper.findByQuery(query, paraList);
	
	return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	public List<Object> reportMonthProjectGroupWiseEffortNotGC(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, List<Long> empIdsUnderLoggedUser){
		
		//Integer empId = dateUseProjectModelId.getEmpId();
	String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
	String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
		String query = "";
		List<Integer> empIdsUnderLoggedUserInt = empIdsUnderLoggedUser.stream()
	            .map(Long::intValue) // Convert Long to Integer
	            .collect(Collectors.toList());
		ArrayList<Object> paraList = new ArrayList<>();
		// Check if empIdsUnderLoggedUserInt is empty
	    if (empIdsUnderLoggedUserInt.isEmpty()) {
	        // Return an empty list as no employee IDs are provided
	        return new ArrayList<>();
	    }
		if(monthName.equals("AllMonths")){
			query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo "+
			"FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.yearName = ?0 AND "+
			"e.id.empId IN ?1 "+
			"AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo" ;
			paraList.add(YearName);
			paraList.add(empIdsUnderLoggedUserInt);
			
			
		}
		else{
			query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo "+
					"FROM ProjectWeekEffortTransactionModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId WHERE e.id.parentTransactionModel.yearName = ?0 AND e.id.parentTransactionModel.monthName = ?1 AND "+
					"e.id.parentTransactionModel.empId IN ?2 "+
					"AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo" ;
			/*query = "SELECT p.strProjectName, SUM(CAST(e.totalEffortProject AS double)), p.strProjectRefNo FROM DateUseProjectModel e JOIN fetch ProjectMasterModel p ON p.numId = e.id.projectId JOIN fetch EmployeeRoleMstModel s ON p.numId = s.numProjectId WHERE s.numGroupId= ?0 AND e.id.yearName = ?1 AND e.id.monthName = ?2 AND e.id.status='p' GROUP BY p.strProjectName, p.strProjectRefNo,e.id.monthName" ;*/			
			
			paraList.add(YearName);
			paraList.add(monthName);
			paraList.add(empIdsUnderLoggedUserInt);
		
		}
		List<Object> result = daoHelper.findByQuery(query, paraList);
		
		return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	//R12 - GROUP ACTIVITY-MONTH WISE PIE CHART 
	public List<Object> reportMonthActivityGroupWiseEffort(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, long groupId){
	
	//Integer empId = dateUseProjectModelId.getEmpId();
	String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
	String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
	String query = "";
	ArrayList<Object> paraList = new ArrayList<>();
		if(monthName.equals("AllMonths")){
			query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId WHERE e.id.parentTransactionModel.yearName = ?0 "+ 
					 "AND e.id.status='m' AND e.id.parentTransactionModel.empId IN (SELECT distinct s.numEmpId FROM EmployeeRoleMstModel s WHERE s.numGroupId = ?1) GROUP BY p.MiscActivityName";
			/*query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"DateUseProjectModel e JOIN fetch "+ 
					 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 "+ 
					 "AND e.id.status='m' AND s.numGroupId = ?1 GROUP BY e.id.projectId";
			*/
			/*query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM MiscActivityMasterModel p "+
			"JOIN fetch DateUseProjectModel e ON p.MiscActivityId = e.id.projectId JOIN fetch EmployeeRoleMstModel s "+
			"ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.status='m' AND s.numGroupId = ?1 GROUP BY p.MiscActivityName" ;*/
		 //paraList.add((int)groupId);
			paraList.add(YearName);
			paraList.add((int)groupId);
		
		}
		else{
			query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId "+
					"WHERE e.id.parentTransactionModel.yearName = ?0 AND e.id.parentTransactionModel.monthName = ?1 "+ 
					"AND e.id.status='m' AND e.id.parentTransactionModel.empId IN (SELECT distinct s.numEmpId FROM EmployeeRoleMstModel s WHERE s.numGroupId = ?2) GROUP BY p.MiscActivityName";
			/*query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"DateUseProjectModel e JOIN fetch "+ 
					 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.monthName = ?1 "+ 
					 "AND e.id.status='m' AND s.numGroupId = ?2 GROUP BY e.id.projectId";*/
			/*query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM MiscActivityMasterModel p "+
			"JOIN fetch DateUseProjectModel e ON p.MiscActivityId = e.id.projectId JOIN fetch "+ 
			 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.monthName = ?1 "+ 
			 "AND e.id.status='m' AND s.numGroupId = ?2 GROUP BY p.MiscActivityName";*/
		//paraList.add((int)groupId);
		paraList.add(YearName);
		paraList.add(monthName);
		paraList.add((int)groupId);
	
	}
	List<Object> result = daoHelper.findByQuery(query, paraList);
	
	return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	public List<Object> reportMonthActivityGroupWiseEffortNotGC(ProjectWeekEffortTransactionModelId projectweekEffortTransactionModelId, List<Long> empIdsUnderLoggedUser){
	
		//Integer empId = dateUseProjectModelId.getEmpId();
		String YearName = projectweekEffortTransactionModelId.getParentTransactionModel().getYearName();
		String monthName = projectweekEffortTransactionModelId.getParentTransactionModel().getMonthName();
		String query = "";
		List<Integer> empIdsUnderLoggedUserInt = empIdsUnderLoggedUser.stream()
		        .map(Long::intValue) // Convert Long to Integer
		        .collect(Collectors.toList());
		// Check if empIdsUnderLoggedUserInt is empty
	    if (empIdsUnderLoggedUserInt.isEmpty()) {
	        // Return an empty list as no employee IDs are provided
	        return new ArrayList<>();
	    }
		ArrayList<Object> paraList = new ArrayList<>();
		if(monthName.equals("AllMonths")){
			query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId WHERE e.id.parentTransactionModel.yearName = ?0 "+ 
					 "AND e.id.status='m' AND e.id.parentTransactionModel.empId IN ?1 GROUP BY p.MiscActivityName";
			/*query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"DateUseProjectModel e JOIN fetch "+ 
					 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 "+ 
					 "AND e.id.status='m' AND s.numGroupId = ?1 GROUP BY e.id.projectId";
			*/
			/*query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM MiscActivityMasterModel p "+
			"JOIN fetch DateUseProjectModel e ON p.MiscActivityId = e.id.projectId JOIN fetch EmployeeRoleMstModel s "+
			"ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.status='m' AND s.numGroupId = ?1 GROUP BY p.MiscActivityName" ;*/
		 //paraList.add((int)groupId);
			paraList.add(YearName);
			paraList.add(empIdsUnderLoggedUserInt);
		
		}
		else{
			query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"ProjectWeekEffortTransactionModel e JOIN fetch MiscActivityMasterModel p ON p.MiscActivityId = e.id.projectId "+
					"WHERE e.id.parentTransactionModel.yearName = ?0 AND e.id.parentTransactionModel.monthName = ?1 "+ 
					"AND e.id.status='m' AND e.id.parentTransactionModel.empId IN ?2 GROUP BY p.MiscActivityName";
			/*query = "SELECT e.id.projectId, SUM(CAST(e.totalEffortProject AS double)) FROM "+
					"DateUseProjectModel e JOIN fetch "+ 
					 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.monthName = ?1 "+ 
					 "AND e.id.status='m' AND s.numGroupId = ?2 GROUP BY e.id.projectId";*/
			/*query = "SELECT p.MiscActivityName, SUM(CAST(e.totalEffortProject AS double)) FROM MiscActivityMasterModel p "+
			"JOIN fetch DateUseProjectModel e ON p.MiscActivityId = e.id.projectId JOIN fetch "+ 
			 "EmployeeRoleMstModel s ON e.id.empId = s.numEmpId WHERE e.id.yearName = ?0 AND e.id.monthName = ?1 "+ 
			 "AND e.id.status='m' AND s.numGroupId = ?2 GROUP BY p.MiscActivityName";*/
		//paraList.add((int)groupId);
		paraList.add(YearName);
		paraList.add(monthName);
		paraList.add(empIdsUnderLoggedUserInt);
	
	}
	List<Object> result = daoHelper.findByQuery(query, paraList);
	
	return (result != null && !result.isEmpty()) ? result : new ArrayList<>();
	}
	
	//MAILING
		//purpose - to find employee with no effort last week
	public List<Long> getEmployeeWithNoEffortLastWeek(List<Long> empIdsAll){
		
		List<Long> employeeWithNoEffortLastWeek = new ArrayList<Long>();
		// Get the current date
    LocalDate currentDatetoFindActiveWeekNumber = LocalDate.now();
    // Get the current week number using ISO week fields
    //int activeWeekNumber = currentDatetoFindActiveWeekNumber.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    int activeWeekNumber = HomeController.getWeekNumber(currentDatetoFindActiveWeekNumber);
    int lastWeekNumber = activeWeekNumber - 1;
    String lastWeekNumberString = String.valueOf(lastWeekNumber);
    String monthToday = currentDatetoFindActiveWeekNumber.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    Year currentYear = Year.now();
    String yearToday = String.valueOf(currentYear.getValue());
    for(int i =0; i< empIdsAll.size(); i++){
    	ArrayList<Object> paraList = new ArrayList<>();
    	String query = "SELECT e.isValid FROM ProjectWeekEffortTransactionModel e WHERE e.id.parentTransactionModel.weekName = ?0 AND e.id.parentTransactionModel.empId = ?1 AND e.id.parentTransactionModel.monthName = ?2 AND e.id.parentTransactionModel.yearName = ?3";
        /*String query = "SELECT e.id.empId, SUM(CAST(e.totalEffortProject AS double)) FROM DateUseProjectModel e WHERE SUM(CAST(e.totalEffortProject AS double)) = 0 AND e.isValid = 0 AND e.id.weekName = ?0 AND e.id.empId = ?1 GROUP BY e.id.empId";*/
        paraList.add(lastWeekNumberString);
        int empId = empIdsAll.get(i).intValue();
        paraList.add(empId);
        paraList.add(monthToday);
        paraList.add(yearToday);
        List<Integer> result = daoHelper.findByQuery(query, paraList);
        int sum = 0;
        if(result != null){
        	// Calculate the sum
            
            for (Integer str : result) {
                // Convert each string to an integer and add it to the sum
                sum += str;
            }
        }
        if(result == null || sum == 0){
        	employeeWithNoEffortLastWeek.add(empIdsAll.get(i));
        }
        /*for (Object resultTemp : result) {
    		if (resultTemp instanceof Object[]) {
		        Object[] row = (Object[]) resultTemp;

		        if (row.length >= 2) {
		        	employeeWithNoEffortLastWeek.add((int)row[0]);
		            //valuesMisc.add(row[1]);
		        }
		    }
		}*/
    }
        /*if(result != null && !result.isEmpty()){
        	continue;
        }else{
        	employeeWithNoEffortLastWeek.add(result.get(0));
        }*/
    
    
    return (employeeWithNoEffortLastWeek != null && !employeeWithNoEffortLastWeek.isEmpty()) ? employeeWithNoEffortLastWeek : new ArrayList<Long>();
	}
}
