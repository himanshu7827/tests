package in.pms.timesheet.dao;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Comparator;
import in.pms.mail.dao.SendMail;

import in.pms.timesheet.service.EmployeeMasterService;
import in.pms.timesheet.service.EmployeeRoleMstService;
import in.pms.timesheet.service.ProjectWeekEffortTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSMailDao {
	
	@Autowired
	private EmployeeMasterService employeeMasterService;
	
	@Autowired
	private ProjectWeekEffortTransactionService projectWeekEffortTransactionService;
	
	@Autowired
	private EmployeeRoleMstService employeeRoleMstService;
	
	
	
	public void sendingTimesheetMail() throws MessagingException{
		List<Long> empIdsAll = employeeMasterService.getEmpIdsAll();
		List<Long> employeeWithNoEffortLastWeek = projectWeekEffortTransactionService.getEmployeeWithNoEffortLastWeek(empIdsAll);
		System.out.println("Employee with no effort - "+employeeWithNoEffortLastWeek);
		List<Long> GCs = employeeRoleMstService.getEmployeeIdsByRoleId(5); //Now GC emp ids
		
		List<Integer> allGroupIds = new ArrayList<Integer>();
		for(Long empId : GCs){
			Integer groupId = (int)employeeMasterService.getGroupId(empId);
			allGroupIds.add(groupId);
		}
		List<List<Long>> HODs = new ArrayList<List<Long>>();
		for(Integer x : allGroupIds){
			List<Long> HODsPerGroup = employeeRoleMstService.getEmployeeIdsByRoleIdAndGroupID(15, x);
			HODs.add(HODsPerGroup);
		}
		List<List<Long>> PIs = new ArrayList<List<Long>>();
		for(Integer x : allGroupIds){
			List<Long> PIsPerGroup = employeeRoleMstService.getEmployeeIdsByRoleIdAndGroupID(4, x);
			PIs.add(PIsPerGroup);
		}
		List<List<Long>> PLs = new ArrayList<List<Long>>();
		for(Integer x : allGroupIds){
			List<Long> PLsPerGroup = employeeRoleMstService.getEmployeeIdsByRoleIdAndGroupID(3, x);
			PLs.add(PLsPerGroup);
		}
		List<List<Long>> TMsMLsWith0Effort = new ArrayList<List<Long>>();
		for(Integer x : allGroupIds){
			List<Long> TMsMLsPerGroup = employeeRoleMstService.getEmployeeIdsOfTMML(x);
			List<Long> intersectionPL = new ArrayList<>(TMsMLsPerGroup);
            intersectionPL.retainAll(employeeWithNoEffortLastWeek);
            TMsMLsWith0Effort.add(intersectionPL);
		}
		
		System.out.println("======================");
		System.out.println("All Group IDs - "+allGroupIds);
		System.out.println("GCs - "+GCs);
		System.out.println("HODs - "+HODs);
		System.out.println("PIs - "+PIs);
		System.out.println("PLs - "+PLs);
		System.out.println("TMsMLsWith0Effort - "+TMsMLsWith0Effort);
		System.out.println("======================");
		//CODE FOR GC
		long roleId = 5;
		List<Long> employeeIdsOfGC = employeeRoleMstService.getEmployeeIdsByRoleId(roleId); //Now GC emp ids
		System.out.println("GCs - "+employeeIdsOfGC);
		List<Long> groupIdsOfGC = new ArrayList<Long>();
		for(Long empId : employeeIdsOfGC){
			Long groupId = employeeMasterService.getGroupId(empId);
			groupIdsOfGC.add(groupId);
		}
		System.out.println("GCs GroupID - "+groupIdsOfGC);
		
		// HashMap to store mappings
        HashMap<Long, List<Long>> GCEmployeeMapping = new HashMap<>();

        // Iterate over senior employee IDs
        for (int i = 0; i < employeeIdsOfGC.size(); i++) {
            Long seniorEmployeeId = employeeIdsOfGC.get(i);
            Long seniorEmployeeGroupId = groupIdsOfGC.get(i);

            // List to store employee IDs falling under the same group as the senior employee
            List<Long> employeesInSameGroup = new ArrayList<>();

            // Iterate over all employees to check their group IDs
            for (Long employeeId : employeeWithNoEffortLastWeek) {
                Long employeeGroupId = employeeMasterService.getGroupId(employeeId); // Assuming getGroupId() returns the group ID for an employee
                if (employeeGroupId.equals(seniorEmployeeGroupId) && !seniorEmployeeId.equals(employeeId)) {
                    employeesInSameGroup.add(employeeId);
                }
            }

            // Add the mapping to the result HashMap
            GCEmployeeMapping.put(seniorEmployeeId, employeesInSameGroup);
        }
        // Print the result HashMap for GC
        System.out.println("========================GC========================");
        for (Long key : GCEmployeeMapping.keySet()) {
        	System.out.println("GC ID: " + key + ", GC's Employee's IDs: " + GCEmployeeMapping.get(key));
        }
        
        //CODE FOR PL
        roleId = 3;
        List<Long> employeeIdsOfPL = employeeRoleMstService.getEmployeeIdsByRoleId(roleId);
        HashMap<Long, List<Long>> PLEmployeeMapping = new HashMap<>();
        for(long num: employeeIdsOfPL){
        	List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(num) != null ?
    		        this.employeeRoleMstService.getProjectIds(num) : new ArrayList<>();
    		List<Long> allEmployeeIdsUnderPL = new ArrayList<Long>();
    		if(!projectIdsByUserId.isEmpty()){
    			allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		}
    		//List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		// Find the intersection
            List<Long> intersectionPL = new ArrayList<>(allEmployeeIdsUnderPL);
            intersectionPL.retainAll(employeeWithNoEffortLastWeek);
    		PLEmployeeMapping.put(num, intersectionPL);
        }
        // Print the result HashMap for PL
        System.out.println("========================PL========================");
        for (Long key : PLEmployeeMapping.keySet()) {
        	System.out.println("PL ID: " + key + ", PL's Employee's IDs: " + PLEmployeeMapping.get(key));
        }
        
        //CODE FOR HOD
        roleId = 15;
        List<Long> employeeIdsOfHOD = employeeRoleMstService.getEmployeeIdsByRoleId(roleId);
        HashMap<Long, List<Long>> HODEmployeeMapping = new HashMap<>();
        for(long num: employeeIdsOfHOD){
        	List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(num) != null ?
    		        this.employeeRoleMstService.getProjectIds(num) : new ArrayList<>();
	        List<Long> allEmployeeIdsUnderHOD = new ArrayList<Long>();
	        if(!projectIdsByUserId.isEmpty()){
	        	allEmployeeIdsUnderHOD = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		}
    		//List<Long> allEmployeeIdsUnderHOD = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		// Find the intersection
            List<Long> intersectionHOD = new ArrayList<>(allEmployeeIdsUnderHOD);
            intersectionHOD.retainAll(employeeWithNoEffortLastWeek);
    		HODEmployeeMapping.put(num, intersectionHOD);
        }
        // Print the result HashMap for HOD
        System.out.println("========================HOD========================");
        for (Long key : HODEmployeeMapping.keySet()) {
        	System.out.println("HOD ID: " + key + ", HOD's Employee's IDs: " + HODEmployeeMapping.get(key));
        }
        
        //CODE FOR PI
        roleId = 4;
        List<Long> employeeIdsOfPI = employeeRoleMstService.getEmployeeIdsByRoleId(roleId);
        HashMap<Long, List<Long>> PIEmployeeMapping = new HashMap<>();
        for(long num: employeeIdsOfPI){
        	List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(num) != null ?
    		        this.employeeRoleMstService.getProjectIds(num) : new ArrayList<>();
	        List<Long> allEmployeeIdsUnderPI = new ArrayList<Long>();
	        if(!projectIdsByUserId.isEmpty()){
	        	allEmployeeIdsUnderPI = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		}
    		//List<Long> allEmployeeIdsUnderPI = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId, roleId);
    		// Find the intersection
            List<Long> intersectionPI = new ArrayList<>(allEmployeeIdsUnderPI);
            intersectionPI.retainAll(employeeWithNoEffortLastWeek);
    		PIEmployeeMapping.put(num, intersectionPI);
        }
        // Print the result HashMap for PI
        System.out.println("========================PI========================");
        for (Long key : PIEmployeeMapping.keySet()) {
        	System.out.println("PI ID: " + key + ", PI's Employee's IDs: " + PIEmployeeMapping.get(key));
        }
        
        //SORTING AND ADDING ROLE IN HOD MAPPING
        HashMap<Long, List<String>> HODEmployeeMappingWithRole = new HashMap<>();
        for(long key : HODEmployeeMapping.keySet()){
        	List<String> empIdWithRole = new ArrayList<String>();
        	if(HODEmployeeMapping.get(key).size()!=0){
        		for(long emp : HODEmployeeMapping.get(key)){
            		long roleID = employeeRoleMstService.getRoleIdByUserIdForMail((int)emp);
            		String roleName = getRoleNameFromRoleId(roleID);
            		if(!roleName.equals("")){
            			empIdWithRole.add(emp +" "+ roleName);
            		}else{
            			empIdWithRole.add(String.valueOf(emp));
            		}
            		
            	}
        		sortEmployeeListByRole(empIdWithRole);
        	}
        	HODEmployeeMappingWithRole.put(key, empIdWithRole);
        }
        System.out.println("========================HOD with ROLE========================");
        for (Long key : HODEmployeeMappingWithRole.keySet()) {
        	System.out.println("HOD ID: " + key + ", HOD's Employee's IDs: " + HODEmployeeMappingWithRole.get(key));
        }
        
        // FINDING DATES FOR LAST WEEK
        LocalDate today = LocalDate.now();
        
        // Find the previous Monday
        LocalDate firstDayOfLastWeek = today.minusDays(7).with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        
        // Add 7 days to the lastMonday
        LocalDate lastDayOfLastWeek = firstDayOfLastWeek.plusDays(6);
        //SEND MAIL
        SendMail sendMail = new SendMail();
        for(int i = 0; i < allGroupIds.size(); i++){
        	if(allGroupIds.get(i)==8){  //FILTER FOR pmo GROUP
        		
        		//Mail to Individual TMs and MLs
        		for(long TMML: TMsMLsWith0Effort.get(i)){
        			if(TMML != 240690){ //FILTER FOR amrita MAAM
        				String txtMsg = "Dear "+"Sir/Ma'am"/*employeeMasterService.getEmpName(key)*/+","+"<br><br>You haven't filled your timesheet from "+firstDayOfLastWeek+" to "+lastDayOfLastWeek+". Kindly fill it ASAP<br><br>Regards,<br>PMO & SQA";
            			sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(TMML), "Timesheet Alert", txtMsg, "");
        			}
        			
        		}
        		
        		//Mail to PL with List and HOD and PI in cc
        		if(PLs.get(i).size()!=0){
            		for(long PL : PLs.get(i)){
            			for (Long key : PLEmployeeMapping.keySet()) {
            				if(key == PL && PLEmployeeMapping.get(key).size()!=0){
            					long HOD = employeeRoleMstService.getHODsByEmployeeId(PL);
            					long PI = employeeRoleMstService.getPIsByEmployeeId(PL);
            					String cc = new String();
            					if(HOD == 0 && PI != 0){
            						cc = employeeMasterService.getEmpMailById(PI);
            					}else if(HOD != 0 && PI == 0){
            						cc = employeeMasterService.getEmpMailById(HOD);
            					}else if(HOD != 0 && PI != 0){
            						cc = employeeMasterService.getEmpMailById(HOD) + "," + employeeMasterService.getEmpMailById(PI);
            					}else{
            						cc = employeeMasterService.getEmpMailById(GCs.get(i));
            					}
            					String txtMsg = "Dear "+"Sir/Ma'am"/*employeeMasterService.getEmpName(key)*/+","+"<br><br>Employees who didn't filled their timesheet from "+firstDayOfLastWeek+" to "+lastDayOfLastWeek+" are :- <br><br>";
            	                for(int j = 0; j<PLEmployeeMapping.get(key).size(); j++){
            	                	txtMsg = txtMsg + String.valueOf(j+1) + ". " + PLEmployeeMapping.get(key).get(j) + " - " + employeeMasterService.getEmpName(PLEmployeeMapping.get(key).get(j)) + "<br>";
            	                }
            	                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
            	                
            	                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(key), "Timesheet Alert", txtMsg, cc);
            				}
            			}
            		}
            	}
        		
        		//Mail to HOD with List and GC in cc
        		if(HODs.get(i).size()!=0){
            		for(long HOD : HODs.get(i)){
            			for (Long key : HODEmployeeMappingWithRole.keySet()) {
            				if(key == HOD && HODEmployeeMappingWithRole.get(key).size()!=0){
            					
            					String txtMsg = "Dear "+"Sir/Ma'am"/*employeeMasterService.getEmpName(key)*/+","+"<br><br>Employees who didn't filled their timesheet from "+firstDayOfLastWeek+" to "+lastDayOfLastWeek+" are :- <br><br>";
            	                for(int j = 0; j<HODEmployeeMappingWithRole.get(key).size(); j++){
            	                	txtMsg = txtMsg + String.valueOf(j+1) + ". " + HODEmployeeMappingWithRole.get(key).get(j).split(" ")[0] + " - " + employeeMasterService.getEmpName(Long.parseLong(HODEmployeeMappingWithRole.get(key).get(j).split(" ")[0])) + " ("+HODEmployeeMappingWithRole.get(key).get(j).split(" ")[1]+")"+"<br>";
            	                }
            	                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
            	                
            	                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(key), "Timesheet Alert", txtMsg, "malpotravarun01@gmail.com"); //GC hardcoded to varun
            				}
            			}
            		}
            	}
        	}
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /*//Sending mail to GC
        int count = 0;//REMOVE
        for (Long key : GCEmployeeMapping.keySet()) {
        	if(GCEmployeeMapping.get(key).size() != 0){
        		String txtMsg = "Dear "+"Sir/Ma'am"+","+"<br><br>Users who didn't filled their timesheet last week are :- <br><br>";
                for(int i = 0; i<GCEmployeeMapping.get(key).size(); i++){
                	txtMsg = txtMsg + String.valueOf(i+1) + ". " + GCEmployeeMapping.get(key).get(i) + " - " + employeeMasterService.getEmpName(GCEmployeeMapping.get(key).get(i)) + "<br>";
                }
                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
                
                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(220790), "Timesheet Alert", txtMsg, "jaykumar@cdac.in");//CHANGE
                count = count + 1;//REMOVE
                if(count == 3){//REMOVE
                	break;//REMOVE
                }//REMOVE
        	}
        	
        }
        
        //Sending mail to PL
        int count1 = 0;//REMOVE
        for (Long key : PLEmployeeMapping.keySet()) {
        	if(PLEmployeeMapping.get(key).size() != 0){
        		String txtMsg = "Dear "+"Sir/Ma'am"+","+"<br><br>Users who didn't filled their timesheet last week are :- <br><br>";
                for(int i = 0; i<PLEmployeeMapping.get(key).size(); i++){
                	txtMsg = txtMsg + String.valueOf(i+1) + ". " + PLEmployeeMapping.get(key).get(i) + " - " + employeeMasterService.getEmpName(PLEmployeeMapping.get(key).get(i)) + "<br>";
                }
                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
                
                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(345145), "Timesheet Alert", txtMsg, "jaykumar@cdac.in");//CHANGE
                count1 = count1 + 1;//REMOVE
                if(count1 == 3){//REMOVE
                	break;//REMOVE
                }//REMOVE
        	}
        	
        }
        
        //Sending mail to HOD
        int count2 = 0;//REMOVE
        for (Long key : HODEmployeeMapping.keySet()) {
        	if(HODEmployeeMapping.get(key).size() != 0){
        		String txtMsg = "Dear "+"Sir/Ma'am"+","+"<br><br>Users who didn't filled their timesheet last week are :- <br><br>";
                for(int i = 0; i<HODEmployeeMapping.get(key).size(); i++){
                	txtMsg = txtMsg + String.valueOf(i+1) + ". " + HODEmployeeMapping.get(key).get(i) + " - " + employeeMasterService.getEmpName(HODEmployeeMapping.get(key).get(i)) + "<br>";
                }
                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
                
                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(344677), "Timesheet Alert", txtMsg, "jaykumar@cdac.in");//CHANGE
                count2 = count2 + 1;//REMOVE
                if(count2 == 3){//REMOVE
                	break;//REMOVE
                }//REMOVE
        	}
        	
        }
        
        //Sending mail to PI
        int count3 = 0;//REMOVE
        for (Long key : PIEmployeeMapping.keySet()) {
        	if(PIEmployeeMapping.get(key).size() != 0){
        		String txtMsg = "Dear "+"Sir/Ma'am"+","+"<br><br>Users who didn't filled their timesheet last week are :- <br><br>";
                for(int i = 0; i<PIEmployeeMapping.get(key).size(); i++){
                	txtMsg = txtMsg + String.valueOf(i+1) + ". " + PIEmployeeMapping.get(key).get(i) + " - " + employeeMasterService.getEmpName(PIEmployeeMapping.get(key).get(i)) + "<br>";
                }
                txtMsg = txtMsg + "<br><br>Regards,<br>PMO & SQA";
                
                sendMail.timesheetPostMailBulkDB(employeeMasterService.getEmpMailById(347205), "Timesheet Alert", txtMsg, "jaykumar@cdac.in");//CHANGE
                count3 = count3 + 1;//REMOVE
                if(count3 == 3){//REMOVE
                	break;//REMOVE
                }//REMOVE
        	}
        	
        }*/
	}
	// Function to get role name based on role ID
    public static String getRoleNameFromRoleId(long roleId) {
        String roleName;
        switch ((int) roleId) {
            case 2:
                roleName = "ML";
                break;
            case 1:
                roleName = "TM";
                break;
            case 5:
                roleName = "GC";
                break;
            case 3:
                roleName = "PL";
                break;
            case 15:
                roleName = "HOD";
                break;
            case 4:
                roleName = "PI";
                break;
            default:
                roleName = "";
        }
        return roleName;
    }
    // Method to sort the list of employees by their roles
    private static void sortEmployeeListByRole(List<String> employeeList) {
        Collections.sort(employeeList, new Comparator<String>() {
            @Override
            public int compare(String emp1, String emp2) {
                String[] parts1 = emp1.split(" ");
                String[] parts2 = emp2.split(" ");
                String role1 = parts1[1];
                String role2 = parts2[1];
                return getRolePriority(role1) - getRolePriority(role2);
            }
        });
    }

    // Method to get the priority of a role
    private static int getRolePriority(String role) {
        switch (role) {
            case "PI":
                return 1;
            case "PL":
                return 2;
            case "ML":
                return 3;
            case "TM":
                return 4;
            default:
                return Integer.MAX_VALUE; // For any other role
        }
    }
}
