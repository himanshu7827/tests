package in.pms.timesheet.controller;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import in.pms.login.util.UserInfo;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.timesheet.model.MiscOtherActivityMasterModel;
import in.pms.timesheet.model.ParentTransactionModel;
import in.pms.timesheet.model.ProjectTaskWeekEffortTransactionModel;
import in.pms.timesheet.model.ProjectTaskWeekEffortTransactionModelId;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModel;
import in.pms.timesheet.model.ProjectWeekEffortTransactionModelId;
import in.pms.timesheet.model.WeekEffortTransactionModel;
import in.pms.timesheet.model.WeekSubmitModel;
import in.pms.timesheet.service.EmployeeMasterService;
import in.pms.timesheet.service.EmployeeRoleMstService;
import in.pms.timesheet.service.HolidayMasterService;
import in.pms.timesheet.service.MiscActivityMasterService;
import in.pms.timesheet.service.MiscOtherActivityMasterService;
import in.pms.timesheet.service.ParentTransactionService;
import in.pms.timesheet.service.ProjectSubTaskDescMasterService;
import in.pms.timesheet.service.ProjectSubTaskMasterService;
import in.pms.timesheet.service.ProjectTaskHelpMasterService;
import in.pms.timesheet.service.ProjectTaskMasterService;
import in.pms.timesheet.service.ProjectTaskWeekEffortTransactionService;
import in.pms.timesheet.service.ProjectWeekEffortTransactionService;
import in.pms.timesheet.service.TimesheetMailService;
import in.pms.timesheet.service.WeekEffortTransactionService;
import in.pms.timesheet.validator.TimesheetHomeValidator;

import org.threeten.extra.YearWeek;

@Controller
public class HomeController {
	
	@Autowired
	private EmployeeMasterService employeeMasterService; 
	
	@Autowired
	private EmployeeRoleMstService employeeRoleMstService;
	
	@Autowired
	private HolidayMasterService holidayMasterService;
	
	@Autowired
	private MiscActivityMasterService miscActivityMasterService;
	
	@Autowired
	private ProjectTaskMasterService projectTaskMasterService;
	
	@Autowired
	private ProjectSubTaskMasterService projectSubTaskMasterService;
	
	@Autowired
	private ProjectSubTaskDescMasterService projectSubTaskDescMasterService;
	
	@Autowired
	private MiscOtherActivityMasterService miscOtherActivityMasterService;
	
	@Autowired
	private ProjectTaskHelpMasterService projectTaskHelpMasterService;
	
	
	@Autowired 
	private TimesheetMailService mailService;
	 
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ParentTransactionService parentTransactionService;
	
	@Autowired
	ProjectWeekEffortTransactionService projectWeekEffortTransactionService;
	
	@Autowired
	ProjectTaskWeekEffortTransactionService projectTaskWeekEffortTransactionService;
	
	@Autowired
	WeekEffortTransactionService weekEffortTransactionService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	//validation
	@Autowired
	@Qualifier("timesheetHomeValidator")
	Validator validator;
	
	
	@RequestMapping("/timesheethome")
	public String index(Model model) throws MessagingException {
		
		//mailService.sendingTimesheetMail();
		//COMMON THINGHS
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        
		int userId = userInfo.getEmployeeId();
		
		int roleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
		
		long groupIdByUserId = this.employeeMasterService.getGroupId(userId);
		
		LocalDate selectedLocalDate = LocalDate.now();
		
		
		
		String userName = this.employeeMasterService.getEmpName(userId);
		List<String> projectNamesByUserId = new ArrayList<String>();
		List<Integer> projectIdsByUserId = new ArrayList<Integer>();
		List<String> projectTypesByUserId = new ArrayList<String>();
		if(roleId == 5){
			projectNamesByUserId = this.employeeRoleMstService.getProjectNamesByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectNamesByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
			projectIdsByUserId = this.employeeRoleMstService.getProjectIdsByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectIdsByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
			List<String> projectTypesByUserIdTemp = this.employeeRoleMstService.getProjectTimesheetTypesByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectTimesheetTypesByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
	        for (String str : projectTypesByUserIdTemp) {
	            if (Objects.isNull(str)) {
	            	projectTypesByUserId.add("b");
	            } else {
	            	projectTypesByUserId.add(str);
	            }
	        }
		 
		}else{
			projectNamesByUserId = this.employeeRoleMstService.getProjectNames(userId) != null ?
			        this.employeeRoleMstService.getProjectNames(userId) : new ArrayList<>();
			
			projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null ?
			        this.employeeRoleMstService.getProjectIds(userId) : new ArrayList<>();
			
			List<String> projectTypesByUserIdTemp = this.employeeRoleMstService.getProjectTimesheetTypesByUserId(userId) != null ?
			        this.employeeRoleMstService.getProjectTimesheetTypesByUserId(userId) : new ArrayList<>();
			
	        for (String str : projectTypesByUserIdTemp) {
	            if (Objects.isNull(str)) {
	            	projectTypesByUserId.add("b");
	            } else {
	            	projectTypesByUserId.add(str);
	            }
	        }
		}
		
		/*List<String> projectNamesByUserId = this.employeeRoleMstService.getProjectNames(userId) != null ?
		        this.employeeRoleMstService.getProjectNames(userId) : new ArrayList<>();
		
		List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null ?
		        this.employeeRoleMstService.getProjectIds(userId) : new ArrayList<>();*/
		model.addAttribute("empId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("projectNames", projectNamesByUserId);
		model.addAttribute("projectIds", projectIdsByUserId);
		model.addAttribute("projectTypesByUserId", projectTypesByUserId); // Sending Timesheet type for particular project
		model.addAttribute("groupId",groupIdByUserId);
		
		//Color for calendar
		List<String> calendarColor = new ArrayList<String>();/*["#ACDDDE", "#CAF1DE", "#E1F8DC", "#FEF8DD", "#FFE7C7"];*/
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		/*calendarColor.add("#F1B4BB");
		calendarColor.add("#A1EEBD");
		calendarColor.add("#E0AED0");
		calendarColor.add("#F6F7C4");
		calendarColor.add("#DAFFFB");
		calendarColor.add("#D7E5CA");*/
		model.addAttribute("calendarColor", calendarColor);
		
		//code for sending misc acivity at frontend
		List<String> miscActivityNames = this.miscActivityMasterService.getMiscActivityNames() != null ?
				this.miscActivityMasterService.getMiscActivityNames() : new ArrayList<>();
	
		List<Integer> miscActivityIds = this.miscActivityMasterService.getMiscActivityIds() != null ?
				this.miscActivityMasterService.getMiscActivityIds() : new ArrayList<>();
		
		model.addAttribute("miscActivityNames", miscActivityNames);
		model.addAttribute("miscActivityIds", miscActivityIds);
		
		//Calculating active week number i.e. current week number for disabling other week inputs
		// Get the current date
        LocalDate currentDatetoFindActiveWeekNumber = LocalDate.now();

        // Get the week number using ISO week fields
        //int activeWeekNumber = currentDatetoFindActiveWeekNumber.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int activeWeekNumber = getWeekNumber(currentDatetoFindActiveWeekNumber);
        boolean activeWeekNumberFlag = true;
        model.addAttribute("activeWeekNumber", activeWeekNumber);
        model.addAttribute("activeWeekNumberFlag",activeWeekNumberFlag);
        
        //COMMON THINGHS END
        
		// Calculate the start date of the week
		LocalDate startDate = selectedLocalDate.with(DayOfWeek.MONDAY);

		model.addAttribute("todaysDate", selectedLocalDate);
		// Calculate the YearWeek
        YearWeek yearWeek = YearWeek.from(selectedLocalDate);

        // Get the month name, year, and week number as strings
        Year currentYear = Year.now();
        String yearString = String.valueOf(currentYear.getValue());
        //String yearString = String.valueOf(startDate.getYear());
        String weekNumberString = String.valueOf(yearWeek.getWeek());
        
        Integer yearToday = currentYear.getValue();
        //Integer yearToday = startDate.getYear();
        // Get the month name from startDate
        String monthToday = selectedLocalDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        //String monthToday = startDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        List<String> monthNameList = new ArrayList<>();
        
        
        // Loop through each month and add its name to the list
        for (int i = 1; i <= 12; i++) {
            Month month = Month.of(i);
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            if(!monthName.equalsIgnoreCase(monthToday)){
            	monthNameList.add(monthName);
            }
        }
        
        // Loop through to make year list
        LocalDate currentDate = LocalDate.now();
	    int year = currentDate.getYear();
	    
	    List<Integer> yearList = new ArrayList<>();
	   
	    for (int i = 2023; i <= 2030; i++) {
            if(i!=yearToday){
            	yearList.add(i);
            }
            
            //year++;
        } 
	    
	    List<Integer> dayListCalendar = getDayListCalendar(monthToday, yearToday);
        
        Integer firstDayNumberCalendar = getFirstDayNumberCalendar(monthToday, yearToday);
        
	    List<Integer> weekNumbersCalendar = getWeekNumbersCalendar(monthToday, yearToday);
	    /*if("December".equals(monthToday)){
	    	weekNumbersCalendar = weekNumbersCalendar.subList(0, weekNumbersCalendar.size()-1);
	    }*/
	    model.addAttribute("weekNumbers", weekNumbersCalendar);
	    
        model.addAttribute("yearSelectDB", yearString);
        model.addAttribute("weekSelectDB", weekNumberString); //No Use you can remove it
        model.addAttribute("monthToday", monthToday);
        model.addAttribute("yearToday", yearToday);
        model.addAttribute("monthNameList", monthNameList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("calenderDayList", dayListCalendar); //used in frontend
		model.addAttribute("calenderStartDay", firstDayNumberCalendar); //used in frontend
		model.addAttribute("calenderWeekNumbers", weekNumbersCalendar); //used in frontend
		
		//color coding code
		List<Integer> holidayListDaysCompulsary = this.holidayMasterService.getHolidayList(monthToday, yearString,1 ) != null ?
				this.holidayMasterService.getHolidayList(monthToday, yearString, 1) : new ArrayList<>();
		List<Integer> holidayListDaysRestricted = this.holidayMasterService.getHolidayList(monthToday, yearString, 2) != null ?
				this.holidayMasterService.getHolidayList(monthToday, yearString, 2) : new ArrayList<>();
				
		model.addAttribute("holidayListDaysCompulsary", holidayListDaysCompulsary);
		model.addAttribute("holidayListDaysRestricted", holidayListDaysRestricted);
		
		//showing holiday name on hover
		List<String> holidayNamesDaysCompulsary = this.holidayMasterService.getHolidayNames(monthToday, yearString, 1) != null ?
				this.holidayMasterService.getHolidayNames(monthToday, yearString, 1) : new ArrayList<>();
		List<String> holidayNamesDaysRestricted = this.holidayMasterService.getHolidayNames(monthToday, yearString, 2) != null ?
				this.holidayMasterService.getHolidayNames(monthToday, yearString, 2) : new ArrayList<>();
		
		model.addAttribute("holidayNamesDaysCompulsary", holidayNamesDaysCompulsary);
		model.addAttribute("holidayNamesDaysRestricted", holidayNamesDaysRestricted);
		
		//Highlighting current date on calendar
		Boolean currentDayFlag = true;
		int currentDayOfMonth = selectedLocalDate.getDayOfMonth();
		model.addAttribute("currentDayFlag", currentDayFlag);
		model.addAttribute("currentDayOfMonth", currentDayOfMonth);
		
		//warning for more work in a week
		List<Object> holidayCountDaysCompulsary = this.holidayMasterService.getHolidayCountEachWeek(monthToday, yearString, 1) != null ?
				this.holidayMasterService.getHolidayCountEachWeek(monthToday, yearString, 1) : new ArrayList<>();
		List<Object> holidayCountDaysRestricted = this.holidayMasterService.getHolidayCountEachWeek(monthToday, yearString, 2) != null ?
				this.holidayMasterService.getHolidayCountEachWeek(monthToday, yearString, 2) : new ArrayList<>();
		
		
		List<Object> holidayCountWeekKeys = new ArrayList<>();
		List<Object> holidayCount = new ArrayList<>();

		for (Object result : holidayCountDaysCompulsary) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		        	holidayCountWeekKeys.add(row[0]);
		        	holidayCount.add(row[1]);
		        }
		    }
		}
		model.addAttribute("holidayCountWeekKeys", holidayCountWeekKeys);
		model.addAttribute("holidayCount", holidayCount);
		
		//================start================== (Helping variables to print things on UI for sandwiched weeks)
		//finding previous and next month info
        String[] previousMonthResult = getPreviousMonthAndYear(monthToday, Integer.parseInt(yearString));
        String[] nextMonthResult = getNextMonthAndYear(monthToday, Integer.parseInt(yearString));
        List<Integer> filledMonthWeeknumbers = getWeekNumbersCalendar(monthToday, Integer.parseInt(yearString));
        List<Integer> previousMonthWeeknumbers = getWeekNumbersCalendar(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        List<Integer> nextMonthWeeknumbers = getWeekNumbersCalendar(nextMonthResult[0], Integer.parseInt(nextMonthResult[1]));
        Boolean hasWeekCommon = false;
        if(filledMonthWeeknumbers.get(0).equals(previousMonthWeeknumbers.get(previousMonthWeeknumbers.size() -1))){
        	hasWeekCommon = true;
        }
        Boolean hasWeekCommonLast = false;
        if(nextMonthWeeknumbers.get(0).equals(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size() -1))){
        	hasWeekCommonLast = true;
        }
        Integer previousMonthOddDays = getLastWeekDaysCount(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        Integer currentMonthOddDays = getLastWeekDaysCount(monthToday, Integer.parseInt(yearString));
      //================end==================
        
		//Printing total effort for each project in UI
        ParentTransactionModel parentTransactionModel = new ParentTransactionModel();
        parentTransactionModel.setEmpId(userId);
        parentTransactionModel.setMonthName(monthToday);
        parentTransactionModel.setYearName(yearString);
        List<Integer> transactionId = this.parentTransactionService.getTransactionId(parentTransactionModel);
		/*
		 * DateUseProjectModelId dateUseProjectModelId = new DateUseProjectModelId();
		 * dateUseProjectModelId.setEmpId(userId);
		 * dateUseProjectModelId.setMonthName(monthToday);
		 * dateUseProjectModelId.setYearName(yearString);
		 */
		List<Object> projectEffort = this.projectWeekEffortTransactionService.getProjectEffort(transactionId);
		//List<Object> projectEffort = this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
		List<Object> keys = new ArrayList<>();
		List<Object> values = new ArrayList<>();

		for (Object result : projectEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keys.add(row[0]);
		            values.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each project in UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
			/*
			 * dateUseProjectModelId.setMonthName(previousMonthResult[0]);
			 * dateUseProjectModelId.setYearName(previousMonthResult[1]);
			 * dateUseProjectModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0
			 * )));
			 */
        	parentTransactionModel.setMonthName(previousMonthResult[0]);
        	parentTransactionModel.setYearName(previousMonthResult[1]);
        	parentTransactionModel.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
            List<Integer> transactionId1 = this.parentTransactionService.getTransactionId(parentTransactionModel);
            List<Object> projectEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getProjectEffort(transactionId1);
        	//List<Object> projectEffortPreviousMonthLastWeek = this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
        	for (Object result : projectEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys.add(row[0]);
    		            values.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
			/*
			 * dateUseProjectModelId.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelId.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(
			 * filledMonthWeeknumbers.size()-1)));
			 */
        	parentTransactionModel.setMonthName(nextMonthResult[0]);
        	parentTransactionModel.setYearName(nextMonthResult[1]);
        	parentTransactionModel.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
            List<Integer> transactionId2 = this.parentTransactionService.getTransactionId(parentTransactionModel);
            List<Object> projectEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getProjectEffort(transactionId2);
        	//List<Object> projectEffortNextMonthLastWeek = this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
        	for (Object result : projectEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys.add(row[0]);
    		            values.add(row[1]);
    		        }
    		    }
    		}
        }
        //Making keys i.e project/activity id unique and summing it if there are duplicate keys
        // Create a map to store unique keys and their corresponding summed values
        Map<Object, Double> uniqueKeys = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            Object key = keys.get(i);
            double value = (double) values.get(i); 
            uniqueKeys.put(key, uniqueKeys.getOrDefault(key, 0.0) + value);
        }

        // Update the keys and values lists with unique keys and summed values
        keys.clear();
        values.clear();
        for (Map.Entry<Object, Double> entry : uniqueKeys.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        //================end==================
        
		List<Object> valuesHHMM = convertToHHMM(values);
		model.addAttribute("projectIdKeys", keys);
		model.addAttribute("projectEffortValues", valuesHHMM);
		
		//Printing total effort for each activity on UI
		ParentTransactionModel parentTransactionModelMisc = new ParentTransactionModel();
		parentTransactionModelMisc.setEmpId(userId);
		parentTransactionModelMisc.setMonthName(monthToday);
		parentTransactionModelMisc.setYearName(yearString);
		List<Integer> transactionIdMisc = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
		/*
		 * DateUseProjectModelId dateUseProjectModelIdMisc = new
		 * DateUseProjectModelId(); dateUseProjectModelIdMisc.setEmpId(userId);
		 * dateUseProjectModelIdMisc.setMonthName(monthToday);
		 * dateUseProjectModelIdMisc.setYearName(yearString);
		 */
		List<Object> activityEffort = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc);
		//List<Object> activityEffort = this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
		List<Object> keysMisc = new ArrayList<>();
		List<Object> valuesMisc = new ArrayList<>();

		for (Object result : activityEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keysMisc.add(row[0]);
		            valuesMisc.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each activity in UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
			/*
			 * dateUseProjectModelIdMisc.setMonthName(previousMonthResult[0]);
			 * dateUseProjectModelIdMisc.setYearName(previousMonthResult[1]);
			 * dateUseProjectModelIdMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.
			 * get(0)));
			 */
        	parentTransactionModelMisc.setMonthName(previousMonthResult[0]);
        	parentTransactionModelMisc.setYearName(previousMonthResult[1]);
        	parentTransactionModelMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
        	List<Integer> transactionIdMisc1 = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
        	List<Object> activityEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc1);
        	//List<Object> activityEffortPreviousMonthLastWeek = this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
        	for (Object result : activityEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keysMisc.add(row[0]);
    		            valuesMisc.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
			/*
			 * dateUseProjectModelIdMisc.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelIdMisc.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelIdMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.
			 * get(filledMonthWeeknumbers.size()-1)));
			 */
        	parentTransactionModelMisc.setMonthName(nextMonthResult[0]);
        	parentTransactionModelMisc.setYearName(nextMonthResult[1]);
        	parentTransactionModelMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
        	List<Integer> transactionIdMisc2 = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
        	List<Object> activityEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc2);
        	//List<Object> activityEffortNextMonthLastWeek = this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
        	for (Object result : activityEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keysMisc.add(row[0]);
    		            valuesMisc.add(row[1]);
    		        }
    		    }
    		}
        }
        //Making keys i.e project/activity id unique and summing it if there are duplicate keys
        // Create a map to store unique keys and their corresponding summed values
        Map<Object, Double> uniqueKeysMisc = new HashMap<>();
        for (int i = 0; i < keysMisc.size(); i++) {
            Object keyMisc = keysMisc.get(i);
            double valueMisc = (double) valuesMisc.get(i);
            uniqueKeysMisc.put(keyMisc, uniqueKeysMisc.getOrDefault(keyMisc, 0.0) + valueMisc);
        }

        // Update the keys and values lists with unique keys and summed values
        keysMisc.clear();
        valuesMisc.clear();
        for (Map.Entry<Object, Double> entryMisc : uniqueKeysMisc.entrySet()) {
        	keysMisc.add(entryMisc.getKey());
        	valuesMisc.add(entryMisc.getValue());
        }
        //================end==================
		List<Object> valuesMiscHHMM = convertToHHMM(valuesMisc);
		model.addAttribute("activityIdKeys", keysMisc);
		model.addAttribute("activityEffortValues", valuesMiscHHMM);
				
		//Printing total effort for each week on UI
		/*
		 * DateUseModelId dateUseModelId = new DateUseModelId();
		 * dateUseModelId.setEmpId(userId); dateUseModelId.setMonthName(monthToday);
		 * dateUseModelId.setYearName(String.valueOf(yearString));
		 */
		ParentTransactionModel parentTransactionModelWeek = new ParentTransactionModel();
		parentTransactionModelWeek.setEmpId(userId);
		parentTransactionModelWeek.setMonthName(monthToday);
		parentTransactionModelWeek.setYearName(String.valueOf(yearString));
        List<Integer> transactionIdWeek = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        List<Object> weeksEffort = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek);
		//List<Object> weeksEffort = this.dateUseService.getWeeksEffort(dateUseModelId);
		List<Object> keys1 = new ArrayList<>();
		List<Object> values1 = new ArrayList<>();

		for (Object result : weeksEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keys1.add(row[0]);
		            values1.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each week on UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
			/*
			 * dateUseModelId.setMonthName(previousMonthResult[0]);
			 * dateUseModelId.setYearName(previousMonthResult[1]);
			 * dateUseModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
			 */
        	parentTransactionModelWeek.setMonthName(previousMonthResult[0]);
        	parentTransactionModelWeek.setYearName(previousMonthResult[1]);
        	parentTransactionModelWeek.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
        	List<Integer> transactionIdWeek1 = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        	List<Object> weeksEffortPreviousMonthLastWeek = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek1);
        	//List<Object> weeksEffortPreviousMonthLastWeek = this.dateUseService.getWeeksEffort(dateUseModelId);
        	for (Object result : weeksEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys1.add(row[0]);
    		            values1.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
			/*
			 * dateUseModelId.setMonthName(nextMonthResult[0]);
			 * dateUseModelId.setYearName(nextMonthResult[1]);
			 * dateUseModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(
			 * filledMonthWeeknumbers.size()-1)));
			 */
        	parentTransactionModelWeek.setMonthName(nextMonthResult[0]);
        	parentTransactionModelWeek.setYearName(nextMonthResult[1]);
        	parentTransactionModelWeek.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
        	List<Integer> transactionIdWeek2 = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        	List<Object> weeksEffortNextMonthLastWeek = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek2);
        	//List<Object> weeksEffortNextMonthLastWeek = this.dateUseService.getWeeksEffort(dateUseModelId);
        	for (Object result : weeksEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys1.add(row[0]);
    		            values1.add(row[1]);
    		        }
    		    }
    		}
        }
        //================end==================
		List<Object> values1HHMM = convertToHHMM(values1);
		model.addAttribute("weekKeys", keys1);
		model.addAttribute("values1", values1);
		model.addAttribute("weekEffortValues", values1HHMM);
		
		//Printing each and every effort on UI
		/*
		 * DateUseProjectModelId dateUseProjectModelIdEachAndEveryEffort = new
		 * DateUseProjectModelId();
		 * dateUseProjectModelIdEachAndEveryEffort.setEmpId(userId);
		 * dateUseProjectModelIdEachAndEveryEffort.setMonthName(monthToday);
		 * dateUseProjectModelIdEachAndEveryEffort.setYearName(yearString);
		 */
		ParentTransactionModel parentTransactionModelEachAndEveryEffort = new ParentTransactionModel();
		parentTransactionModelEachAndEveryEffort.setEmpId(userId);
		parentTransactionModelEachAndEveryEffort.setMonthName(monthToday);
		parentTransactionModelEachAndEveryEffort.setYearName(yearString);
		List<Integer> transactionIdEachAndEvery = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);;
				
		List<Object> eachAndEveryEffort = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery);
	
		List<Object> eachAndEveryWeek = new ArrayList<>();
		List<Object> eachAndEveryOnlyEffort = new ArrayList<>();
		List<Object> eachAndEveryProject = new ArrayList<>();
		List<Object> eachAndEveryStatus = new ArrayList<>();

		for (Object result : eachAndEveryEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 4) {
		        	eachAndEveryWeek.add(row[0]);
		        	eachAndEveryOnlyEffort.add(row[1]);
		        	eachAndEveryProject.add(row[2]);
		        	eachAndEveryStatus.add(row[3]);
		        }
		    }
		}
		
		//================start==================(Printing each and every effort on UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
        	/*dateUseProjectModelIdEachAndEveryEffort.setMonthName(previousMonthResult[0]);
    		dateUseProjectModelIdEachAndEveryEffort.setYearName(previousMonthResult[1]);
        	dateUseProjectModelIdEachAndEveryEffort.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));*/
    		parentTransactionModelEachAndEveryEffort.setMonthName(previousMonthResult[0]);
    		parentTransactionModelEachAndEveryEffort.setYearName(previousMonthResult[1]);
    		parentTransactionModelEachAndEveryEffort.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
    		List<Integer> transactionIdEachAndEvery1 = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);
        	List<Object> eachAndEveryEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery1);
        	for (Object result : eachAndEveryEffortPreviousMonthLastWeek) {
    		    if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 4) {
    		        	eachAndEveryWeek.add(row[0]);
    		        	eachAndEveryOnlyEffort.add(row[1]);
    		        	eachAndEveryProject.add(row[2]);
    		        	eachAndEveryStatus.add(row[3]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
			/*
			 * dateUseProjectModelIdEachAndEveryEffort.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelIdEachAndEveryEffort.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelIdEachAndEveryEffort.setWeekName(String.valueOf(
			 * filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
			 */
        	parentTransactionModelEachAndEveryEffort.setMonthName(nextMonthResult[0]);
    		parentTransactionModelEachAndEveryEffort.setYearName(nextMonthResult[1]);
    		parentTransactionModelEachAndEveryEffort.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
    		List<Integer> transactionIdEachAndEvery2 = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);
        	List<Object> eachAndEveryEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery2);
        	for (Object result : eachAndEveryEffortNextMonthLastWeek) {
    		    if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 4) {
    		        	eachAndEveryWeek.add(row[0]);
    		        	eachAndEveryOnlyEffort.add(row[1]);
    		        	eachAndEveryProject.add(row[2]);
    		        	eachAndEveryStatus.add(row[3]);
    		        }
    		    }
    		}
        }
        //================end==================
        List<Object> eachAndEveryOnlyEffortHHMM = convertToHHMM(eachAndEveryOnlyEffort);
        model.addAttribute("eachAndEveryWeek", eachAndEveryWeek);
        model.addAttribute("eachAndEveryOnlyEffort", eachAndEveryOnlyEffortHHMM);
        model.addAttribute("eachAndEveryProject", eachAndEveryProject);
        model.addAttribute("eachAndEveryStatus", eachAndEveryStatus);
        
      
        
        
        //POP UP PROJECT TASK
        //code for sending project task at frontend
  		List<String> projectTaskNames = this.projectTaskMasterService.getProjectTaskNames() != null ?
  				this.projectTaskMasterService.getProjectTaskNames() : new ArrayList<>();
  	
  		List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
		
		List<List<String>> projectSubTaskNames = this.projectSubTaskMasterService.getProjectSubTaskNames(projectTaskIds) != null ?
				this.projectSubTaskMasterService.getProjectSubTaskNames(projectTaskIds) : new ArrayList<>();
  	
		List<List<Long>> projectSubTaskIds = this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) != null ?
				this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) : new ArrayList<>();
  		
		List<List<List<String>>> projectSubTaskDescNames = this.projectSubTaskDescMasterService.getProjectSubTaskDescNames(projectSubTaskIds) != null ?
				this.projectSubTaskDescMasterService.getProjectSubTaskDescNames(projectSubTaskIds) : new ArrayList<>();
  	
		List<List<List<Long>>> projectSubTaskDescIds = this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) != null ?
				this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) : new ArrayList<>();
		
		model.addAttribute("projectTaskNames", projectTaskNames);
		model.addAttribute("projectTaskIds", projectTaskIds);
		
		model.addAttribute("projectSubTaskNames", projectSubTaskNames);
		model.addAttribute("projectSubTaskIds", projectSubTaskIds);
		
		model.addAttribute("projectSubTaskDescNames", projectSubTaskDescNames);
		model.addAttribute("projectSubTaskDescIds", projectSubTaskDescIds);
		
		List<Long> projectSubTaskDescIdsPlain = projectSubTaskDescIds.stream()
			    .flatMap(List::stream)
			    .flatMap(List::stream)
			    .collect(Collectors.toList());
		
		model.addAttribute("projectSubTaskDescIdsPlain", projectSubTaskDescIdsPlain);
		//to disable weeks not present in opened month
		Boolean isCurrentWeekPresent =  weekNumbersCalendar.contains(activeWeekNumber);
	    Boolean isPreviousWeekPresent =  weekNumbersCalendar.contains(activeWeekNumber - 1);
	    model.addAttribute("isCurrentWeekPresent", isCurrentWeekPresent);
	    model.addAttribute("isPreviousWeekPresent", isPreviousWeekPresent);
	    int monthNumber = Month.valueOf(monthToday.toUpperCase()).getValue();
	    int monthMinus1;
	    int monthPlus1;
	    if(monthNumber == 1) {
	    	monthMinus1 = 12;
	    }
	    else {
	    	monthMinus1 = monthNumber - 1;
	    }
	    if(monthNumber == 12) {
	    	monthPlus1 = 1;
	    }
	    else {
	    	monthPlus1 = monthNumber + 1;
	    }
	    model.addAttribute("monthSelectedPopUpMinus1", Month.of(monthMinus1).name());
	    model.addAttribute("monthSelectedPopUpPlus1", Month.of(monthPlus1).name());
	    
		//to show already exixts misc other activity on text area for user to fill already exist misc other activity
		List<String> allMiscActivityDesc = this.miscOtherActivityMasterService.getAllDesc() != null ?
				this.miscOtherActivityMasterService.getAllDesc() : new ArrayList<>();
		List<String> allMiscActivityDescTemp = new ArrayList<String>();
		for(String str: allMiscActivityDesc){
			allMiscActivityDescTemp.add(str+"^");
		}
		model.addAttribute("allMiscActivityDescTemp", allMiscActivityDescTemp);
		
		//Showing Help option on UI which will tell you the project task and what you can consider
		List<Object> projectTaskDescHelp = this.projectTaskHelpMasterService.getProjectTaskHelpDesc() != null ?
				this.projectTaskHelpMasterService.getProjectTaskHelpDesc(): new ArrayList<>();
		// Separate the elements into two lists
        List<String> projectTaskName = new ArrayList<>();
        List<String> projectTaskDesc = new ArrayList<>();

        for (Object obj : projectTaskDescHelp) {
            Object[] arr = (Object[]) obj;
            if (arr.length >= 2) {
                projectTaskName.add(String.valueOf(arr[0]));
                projectTaskDesc.add(String.valueOf(arr[1]));
            }
        }
        
        // Create a HashMap to store task names and their corresponding descriptions
        Map<String, List<String>> taskMap = new HashMap<>();

        // Populate the HashMap
        for (int i = 0; i < projectTaskName.size(); i++) {
            String taskName = projectTaskName.get(i);
            String taskDesc = projectTaskDesc.get(i);

            // If the task name already exists in the map, add the description to its list
            // Otherwise, create a new list for the task name
            if (taskMap.containsKey(taskName)) {
                taskMap.get(taskName).add(taskDesc);
            } else {
                List<String> descList = new ArrayList<>();
                descList.add(taskDesc);
                taskMap.put(taskName, descList);
            }
        }
        model.addAttribute("projectTaskDescMap", taskMap);
		return "timesheethome";
	}
	
	@RequestMapping(path = "/calenderSelectedMonthYear", method = RequestMethod.POST)
	public String calenderSelectedMonth(@RequestParam String calenderSelectedMonth, @RequestParam Integer calenderSelectedYear, @RequestParam Integer selectedDateNumber, @RequestParam Integer userId, Model model){
		//COMMON THINGHS
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		long roleId = userInfo.getSelectedEmployeeRole().getNumRoleId();
		//long roleId = this.employeeRoleMstService.getRoleIdByUserId(userId);
		long groupIdByUserId = this.employeeMasterService.getGroupId(userId);
		String userName = this.employeeMasterService.getEmpName(userId);
		
		
		List<String> projectNamesByUserId = new ArrayList<String>();
		List<Integer> projectIdsByUserId = new ArrayList<Integer>();
		List<String> projectTypesByUserId = new ArrayList<String>();
		if(roleId == 5){
			projectNamesByUserId = this.employeeRoleMstService.getProjectNamesByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectNamesByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
			projectIdsByUserId = this.employeeRoleMstService.getProjectIdsByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectIdsByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
			List<String> projectTypesByUserIdTemp = this.employeeRoleMstService.getProjectTimesheetTypesByGroupId((int)groupIdByUserId) != null ?
					this.employeeRoleMstService.getProjectTimesheetTypesByGroupId((int)groupIdByUserId) : new ArrayList<>();
			
			for (String str : projectTypesByUserIdTemp) {
	            if (Objects.isNull(str)) {
	            	projectTypesByUserId.add("b");
	            } else {
	            	projectTypesByUserId.add(str);
	            }
	        }
			
		}else{
			projectNamesByUserId = this.employeeRoleMstService.getProjectNames(userId) != null ?
			        this.employeeRoleMstService.getProjectNames(userId) : new ArrayList<>();
			
			projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null ?
			        this.employeeRoleMstService.getProjectIds(userId) : new ArrayList<>();
			
			List<String> projectTypesByUserIdTemp = this.employeeRoleMstService.getProjectTimesheetTypesByUserId(userId) != null ? 
			        this.employeeRoleMstService.getProjectTimesheetTypesByUserId(userId) : new ArrayList<>();
			
			for (String str : projectTypesByUserIdTemp) {
	            if (Objects.isNull(str)) {
	            	projectTypesByUserId.add("b");
	            } else {
	            	projectTypesByUserId.add(str);
	            }
	        }
		}
		/*List<String> projectNamesByUserId = this.employeeRoleMstService.getProjectNames(userId) != null ?
		        this.employeeRoleMstService.getProjectNames(userId) : new ArrayList<>();
		List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null ?
		        this.employeeRoleMstService.getProjectIds(userId) : new ArrayList<>();*/
		   
		
		
		model.addAttribute("empId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("projectNames", projectNamesByUserId);
		model.addAttribute("projectIds", projectIdsByUserId);
		model.addAttribute("projectTypesByUserId", projectTypesByUserId); // Sending Timesheet type for particular project
		model.addAttribute("groupId",groupIdByUserId);
		
		//Color for calendar
		List<String> calendarColor = new ArrayList<String>();/*["#ACDDDE", "#CAF1DE", "#E1F8DC", "#FEF8DD", "#FFE7C7"];*/
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		calendarColor.add("#b3ecff");
		calendarColor.add("#66d9ff");
		/*calendarColor.add("#F1B4BB");
		calendarColor.add("#A1EEBD");
		calendarColor.add("#E0AED0");
		calendarColor.add("#F6F7C4");
		calendarColor.add("#DAFFFB");
		calendarColor.add("#D7E5CA");*/
		model.addAttribute("calendarColor", calendarColor);
		
		//code for sending misc acivity at frontend
		List<String> miscActivityNames = this.miscActivityMasterService.getMiscActivityNames() != null ?
				this.miscActivityMasterService.getMiscActivityNames() : new ArrayList<>();
	
		List<Integer> miscActivityIds = this.miscActivityMasterService.getMiscActivityIds() != null ?
				this.miscActivityMasterService.getMiscActivityIds() : new ArrayList<>();
				
		model.addAttribute("miscActivityNames", miscActivityNames);
		model.addAttribute("miscActivityIds", miscActivityIds);
		
		//Calculating active week number i.e. current week number for disabling other week inputs
		// Get the current date
        LocalDate currentDatetoFindActiveWeekNumber = LocalDate.now();

        // Get the week number using ISO week fields
        //int activeWeekNumber = currentDatetoFindActiveWeekNumber.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int activeWeekNumber = getWeekNumber(currentDatetoFindActiveWeekNumber);
        boolean activeWeekNumberFlag = false;
        if(currentDatetoFindActiveWeekNumber.getYear() == calenderSelectedYear){
        	activeWeekNumberFlag = true;
        }
        model.addAttribute("activeWeekNumber", activeWeekNumber);
        model.addAttribute("activeWeekNumberFlag", activeWeekNumberFlag);
        
       //COMMON THINGHS END
		
		int calenderSelectedMonthInt;
		switch (calenderSelectedMonth.toLowerCase()) {
		    case "january":
		    	calenderSelectedMonthInt = 1;
		        break;
		    case "february":
		    	calenderSelectedMonthInt = 2;
		        break;
		    case "march":
		    	calenderSelectedMonthInt = 3;
		        break;
		    case "april":
		    	calenderSelectedMonthInt = 4;
		        break;
		    case "may":
		    	calenderSelectedMonthInt = 5;
		        break;
		    case "june":
		    	calenderSelectedMonthInt = 6;
		        break;
		    case "july":
		    	calenderSelectedMonthInt = 7;
		        break;
		    case "august":
		    	calenderSelectedMonthInt = 8;
		        break;
		    case "september":
		    	calenderSelectedMonthInt = 9;
		        break;
		    case "october":
		    	calenderSelectedMonthInt = 10;
		        break;
		    case "november":
		    	calenderSelectedMonthInt = 11;
		        break;
		    case "december":
		    	calenderSelectedMonthInt = 12;
		        break;
		    default:
		        throw new IllegalArgumentException("Invalid month: " + calenderSelectedMonth);
		}
		
		LocalDate selectedLocalDate = LocalDate.of(calenderSelectedYear, calenderSelectedMonthInt, selectedDateNumber);
		    
		 	// Calculate the start date of the week
		LocalDate startDate = selectedLocalDate.with(DayOfWeek.MONDAY);

		model.addAttribute("selectedDate", selectedLocalDate);
		// Calculate the YearWeek
	    YearWeek yearWeek = YearWeek.from(selectedLocalDate);
	
	    // Get the month name, year, and week number as strings
	    String yearString = String.valueOf(startDate.getYear());
	    String weekNumberString = String.valueOf(yearWeek.getWeek());
	    
	    model.addAttribute("yearSelectDB", yearString);
	    model.addAttribute("weekSelectDB", weekNumberString); //No Use you can remove it
	    
	    List<String> monthNameList = new ArrayList<>();
	    
	    // Loop through each month and add its name to the list
	    for (int i = 1; i <= 12; i++) {
	        Month month1 = Month.of(i);
	        String monthName = month1.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	        if(!monthName.equalsIgnoreCase(calenderSelectedMonth)){
	        	monthNameList.add(monthName);
	        } 
	    }
	    
	    // Loop through to make year list
	    LocalDate currentDate = LocalDate.now();
	    int year = currentDate.getYear();
	    
	    List<Integer> yearList = new ArrayList<>();
	   
	    for (int i = 2023; i <= 2030; i++) {
	        if(i!=calenderSelectedYear){
	        	yearList.add(i);
	        }
	        //year++;
	    }  
	    
	    List<Integer> dayListCalendar = getDayListCalendar(calenderSelectedMonth, calenderSelectedYear);
	    
	    Integer firstDayNumberCalendar = getFirstDayNumberCalendar(calenderSelectedMonth, calenderSelectedYear);
	    
	    List<Integer> weekNumbersCalendar = getWeekNumbersCalendar(calenderSelectedMonth, calenderSelectedYear);
	    
	    /*if("December".equals(calenderSelectedMonth)){
	    	weekNumbersCalendar = weekNumbersCalendar.subList(0, weekNumbersCalendar.size()-1);
	    }*/
	    model.addAttribute("weekNumbers", weekNumbersCalendar);
	    System.out.println("weekNumbersCalendar " +weekNumbersCalendar);
	    System.out.println("Date " +selectedDateNumber);
	    System.out.println("Month " +calenderSelectedMonth);
	    System.out.println("Year " +calenderSelectedYear);
	    
	    
	    model.addAttribute("monthNameList", monthNameList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("calenderSelectedMonth",calenderSelectedMonth); //added to show on frontend
		model.addAttribute("calenderSelectedYear",calenderSelectedYear); //added to show on frontend
		model.addAttribute("calenderDayList",dayListCalendar); //used in frontend
		model.addAttribute("calenderStartDay",firstDayNumberCalendar); //used in frontend
		model.addAttribute("calenderWeekNumbers",weekNumbersCalendar); //used in frontend
		
		//color coding code
		List<Integer> holidayListDaysCompulsary = this.holidayMasterService.getHolidayList(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) != null ?
				this.holidayMasterService.getHolidayList(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) : new ArrayList<>();
		List<Integer> holidayListDaysRestricted = this.holidayMasterService.getHolidayList(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) != null ?
				this.holidayMasterService.getHolidayList(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) : new ArrayList<>();
		
		model.addAttribute("holidayListDaysCompulsary", holidayListDaysCompulsary);
		model.addAttribute("holidayListDaysRestricted", holidayListDaysRestricted);	
		
		//showing holiday name on hover
		List<String> holidayNamesDaysCompulsary = this.holidayMasterService.getHolidayNames(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) != null ?
				this.holidayMasterService.getHolidayNames(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) : new ArrayList<>();
		List<String> holidayNamesDaysRestricted = this.holidayMasterService.getHolidayNames(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) != null ?
				this.holidayMasterService.getHolidayNames(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) : new ArrayList<>();
		
		model.addAttribute("holidayNamesDaysCompulsary", holidayNamesDaysCompulsary);
		model.addAttribute("holidayNamesDaysRestricted", holidayNamesDaysRestricted);
		
		//Highlighting current date on calendar
		Boolean currentDayFlag = false;
        int currentYear = currentDate.getYear();
        String currentMonthName = currentDate.getMonth().name();
        if(currentMonthName.equalsIgnoreCase(calenderSelectedMonth) && (currentYear == calenderSelectedYear) ){
        	currentDayFlag = true;
        }
		int currentDayOfMonth = currentDate.getDayOfMonth();
		model.addAttribute("currentDayFlag", currentDayFlag);
		model.addAttribute("currentDayOfMonth", currentDayOfMonth);
		
		//warning for more work in a week
		List<Object> holidayCountDaysCompulsary = this.holidayMasterService.getHolidayCountEachWeek(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) != null ?
				this.holidayMasterService.getHolidayCountEachWeek(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 1) : new ArrayList<>();
		List<Object> holidayCountDaysRestricted = this.holidayMasterService.getHolidayCountEachWeek(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) != null ?
				this.holidayMasterService.getHolidayCountEachWeek(calenderSelectedMonth, String.valueOf(calenderSelectedYear), 2) : new ArrayList<>();
		
		
		List<Object> holidayCountWeekKeys = new ArrayList<>();
		List<Object> holidayCount = new ArrayList<>();

		for (Object result : holidayCountDaysCompulsary) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		        	holidayCountWeekKeys.add(row[0]);
		        	holidayCount.add(row[1]);
		        }
		    }
		}
		model.addAttribute("holidayCountWeekKeys", holidayCountWeekKeys);
		model.addAttribute("holidayCount", holidayCount);
		
		//================start================== (Helping variables to print things on UI for sandwiched weeks)
		//finding previous and next month info
        String[] previousMonthResult = getPreviousMonthAndYear(calenderSelectedMonth, calenderSelectedYear);
        String[] nextMonthResult = getNextMonthAndYear(calenderSelectedMonth, calenderSelectedYear);
        List<Integer> filledMonthWeeknumbers = getWeekNumbersCalendar(calenderSelectedMonth, calenderSelectedYear);
        List<Integer> previousMonthWeeknumbers = getWeekNumbersCalendar(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        List<Integer> nextMonthWeeknumbers = getWeekNumbersCalendar(nextMonthResult[0], Integer.parseInt(nextMonthResult[1]));
        Boolean hasWeekCommon = false;
        if(filledMonthWeeknumbers.get(0).equals(previousMonthWeeknumbers.get(previousMonthWeeknumbers.size() -1))){
        	hasWeekCommon = true;
        }
        Boolean hasWeekCommonLast = false;
        if(nextMonthWeeknumbers.get(0).equals(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size() -1))){
        	hasWeekCommonLast = true;
        }
        Integer previousMonthOddDays = getLastWeekDaysCount(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        Integer currentMonthOddDays = getLastWeekDaysCount(calenderSelectedMonth, calenderSelectedYear);
        //================end==================
        
		//Printing total effort for each project in UI
        ParentTransactionModel parentTransactionModel = new ParentTransactionModel();
        parentTransactionModel.setEmpId(userId);
        parentTransactionModel.setMonthName(calenderSelectedMonth);
        parentTransactionModel.setYearName(String.valueOf(calenderSelectedYear));
        List<Integer> transactionId = this.parentTransactionService.getTransactionId(parentTransactionModel);
        List<Object> projectEffort = this.projectWeekEffortTransactionService.getProjectEffort(transactionId);
		/*
		 * DateUseProjectModelId dateUseProjectModelId = new DateUseProjectModelId();
		 * dateUseProjectModelId.setEmpId(userId);
		 * dateUseProjectModelId.setMonthName(calenderSelectedMonth);
		 * dateUseProjectModelId.setYearName(String.valueOf(calenderSelectedYear));
		 * List<Object> projectEffort =
		 * this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
		 */
		List<Object> keys = new ArrayList<>();
		List<Object> values = new ArrayList<>();

		for (Object result : projectEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keys.add(row[0]);
		            values.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each project in UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
        	parentTransactionModel.setMonthName(previousMonthResult[0]);
        	parentTransactionModel.setYearName(previousMonthResult[1]);
        	parentTransactionModel.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
            List<Integer> transactionId1 = this.parentTransactionService.getTransactionId(parentTransactionModel);
            List<Object> projectEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getProjectEffort(transactionId1);
			/*
			 * dateUseProjectModelId.setMonthName(previousMonthResult[0]);
			 * dateUseProjectModelId.setYearName(previousMonthResult[1]);
			 * dateUseProjectModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0
			 * ))); List<Object> projectEffortPreviousMonthLastWeek =
			 * this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
			 */
        	for (Object result : projectEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys.add(row[0]);
    		            values.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
			/*
			 * dateUseProjectModelId.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelId.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(
			 * filledMonthWeeknumbers.size()-1))); List<Object>
			 * projectEffortNextMonthLastWeek =
			 * this.dateUseProjectService.getProjectEffort(dateUseProjectModelId);
			 */
        	parentTransactionModel.setMonthName(nextMonthResult[0]);
        	parentTransactionModel.setYearName(nextMonthResult[1]);
        	parentTransactionModel.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
            List<Integer> transactionId2 = this.parentTransactionService.getTransactionId(parentTransactionModel);
            List<Object> projectEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getProjectEffort(transactionId2);
        	for (Object result : projectEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys.add(row[0]);
    		            values.add(row[1]);
    		        }
    		    }
    		}
        }
        
        //Making keys i.e project/activity id unique and summing it if there are duplicate keys
        // Create a map to store unique keys and their corresponding summed values
        Map<Object, Double> uniqueKeys = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            Object key = keys.get(i);
            double value = (double) values.get(i); 
            uniqueKeys.put(key, uniqueKeys.getOrDefault(key, 0.0) + value);
        }

        // Update the keys and values lists with unique keys and summed values
        keys.clear();
        values.clear();
        for (Map.Entry<Object, Double> entry : uniqueKeys.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        //================end==================
		List<Object> valuesHHMM = convertToHHMM(values);
		model.addAttribute("projectIdKeys", keys);
		model.addAttribute("projectEffortValues", valuesHHMM);
		//Printing total effort for each activity in UI
		ParentTransactionModel parentTransactionModelMisc = new ParentTransactionModel();
		parentTransactionModelMisc.setEmpId(userId);
		parentTransactionModelMisc.setMonthName(calenderSelectedMonth);
		parentTransactionModelMisc.setYearName(String.valueOf(calenderSelectedYear));
		List<Integer> transactionIdMisc = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
		/*
		 * DateUseProjectModelId dateUseProjectModelIdMisc = new
		 * DateUseProjectModelId(); dateUseProjectModelIdMisc.setEmpId(userId);
		 * dateUseProjectModelIdMisc.setMonthName(calenderSelectedMonth);
		 * dateUseProjectModelIdMisc.setYearName(String.valueOf(calenderSelectedYear));
		 * List<Object> activityEffort =
		 * this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
		 */
		List<Object> activityEffort = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc);
		List<Object> keysMisc = new ArrayList<>();
		List<Object> valuesMisc = new ArrayList<>();

		for (Object result : activityEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keysMisc.add(row[0]);
		            valuesMisc.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each activity in UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
        	parentTransactionModelMisc.setMonthName(previousMonthResult[0]);
        	parentTransactionModelMisc.setYearName(previousMonthResult[1]);
        	parentTransactionModelMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
        	List<Integer> transactionIdMisc1 = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
        	List<Object> activityEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc1);
			/*
			 * dateUseProjectModelIdMisc.setMonthName(previousMonthResult[0]);
			 * dateUseProjectModelIdMisc.setYearName(previousMonthResult[1]);
			 * dateUseProjectModelIdMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.
			 * get(0))); List<Object> activityEffortPreviousMonthLastWeek =
			 * this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
			 */
        	for (Object result : activityEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keysMisc.add(row[0]);
    		            valuesMisc.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
        	parentTransactionModelMisc.setMonthName(nextMonthResult[0]);
        	parentTransactionModelMisc.setYearName(nextMonthResult[1]);
        	parentTransactionModelMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
        	List<Integer> transactionIdMisc2 = this.parentTransactionService.getTransactionId(parentTransactionModelMisc);
        	List<Object> activityEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getActivityEffort(transactionIdMisc2);
			/*
			 * dateUseProjectModelIdMisc.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelIdMisc.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelIdMisc.setWeekName(String.valueOf(filledMonthWeeknumbers.
			 * get(filledMonthWeeknumbers.size()-1))); List<Object>
			 * activityEffortNextMonthLastWeek =
			 * this.dateUseProjectService.getActivityEffort(dateUseProjectModelIdMisc);
			 */
        	for (Object result : activityEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keysMisc.add(row[0]);
    		            valuesMisc.add(row[1]);
    		        }
    		    }
    		}
        }
        //Making keys i.e project/activity id unique and summing it if there are duplicate keys
        // Create a map to store unique keys and their corresponding summed values
        Map<Object, Double> uniqueKeysMisc = new HashMap<>();
        for (int i = 0; i < keysMisc.size(); i++) {
            Object keyMisc = keysMisc.get(i);
            double valueMisc = (double) valuesMisc.get(i);
            uniqueKeysMisc.put(keyMisc, uniqueKeysMisc.getOrDefault(keyMisc, 0.0) + valueMisc);
        }

        // Update the keys and values lists with unique keys and summed values
        keysMisc.clear();
        valuesMisc.clear();
        for (Map.Entry<Object, Double> entryMisc : uniqueKeysMisc.entrySet()) {
        	keysMisc.add(entryMisc.getKey());
        	valuesMisc.add(entryMisc.getValue());
        }
        //================end==================
		List<Object> valuesMiscHHMM = convertToHHMM(valuesMisc);
		model.addAttribute("activityIdKeys", keysMisc);
		model.addAttribute("activityEffortValues", valuesMiscHHMM);
				
		//Printing total effort for each week in UI
		ParentTransactionModel parentTransactionModelWeek = new ParentTransactionModel();
		parentTransactionModelWeek.setEmpId(userId);
		parentTransactionModelWeek.setMonthName(calenderSelectedMonth);
		parentTransactionModelWeek.setYearName(String.valueOf(calenderSelectedYear));
        List<Integer> transactionIdWeek = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        List<Object> weeksEffort = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek);
		/*
		 * DateUseModelId dateUseModelId = new DateUseModelId();
		 * dateUseModelId.setEmpId(userId);
		 * dateUseModelId.setMonthName(calenderSelectedMonth);
		 * dateUseModelId.setYearName(String.valueOf(calenderSelectedYear));
		 * List<Object> weeksEffort =
		 * this.dateUseService.getWeeksEffort(dateUseModelId);
		 */
		List<Object> keys1 = new ArrayList<>();
		List<Object> values1 = new ArrayList<>();

		for (Object result : weeksEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 2) {
		            keys1.add(row[0]);
		            values1.add(row[1]);
		        }
		    }
		}
		//================start==================(Printing total effort for each week in UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
			/*
			 * dateUseModelId.setMonthName(previousMonthResult[0]);
			 * dateUseModelId.setYearName(previousMonthResult[1]);
			 * dateUseModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
			 * List<Object> weeksEffortPreviousMonthLastWeek =
			 * this.dateUseService.getWeeksEffort(dateUseModelId);
			 */
        	parentTransactionModelWeek.setMonthName(previousMonthResult[0]);
        	parentTransactionModelWeek.setYearName(previousMonthResult[1]);
        	parentTransactionModelWeek.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
        	List<Integer> transactionIdWeek1 = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        	List<Object> weeksEffortPreviousMonthLastWeek = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek1);
        	for (Object result : weeksEffortPreviousMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys1.add(row[0]);
    		            values1.add(row[1]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
        	parentTransactionModelWeek.setMonthName(nextMonthResult[0]);
        	parentTransactionModelWeek.setYearName(nextMonthResult[1]);
        	parentTransactionModelWeek.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
        	List<Integer> transactionIdWeek2 = this.parentTransactionService.getTransactionId(parentTransactionModelWeek);
        	List<Object> weeksEffortNextMonthLastWeek = this.weekEffortTransactionService.getWeeksEffort(transactionIdWeek2);
			/*
			 * dateUseModelId.setMonthName(nextMonthResult[0]);
			 * dateUseModelId.setYearName(nextMonthResult[1]);
			 * dateUseModelId.setWeekName(String.valueOf(filledMonthWeeknumbers.get(
			 * filledMonthWeeknumbers.size()-1))); List<Object> weeksEffortNextMonthLastWeek
			 * = this.dateUseService.getWeeksEffort(dateUseModelId);
			 */
        	for (Object result : weeksEffortNextMonthLastWeek) {
        		if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 2) {
    		            keys1.add(row[0]);
    		            values1.add(row[1]);
    		        }
    		    }
    		}
        }
        //================end==================
		List<Object> values1HHMM = convertToHHMM(values1);
		model.addAttribute("weekKeys", keys1);
		model.addAttribute("values1", values1);
		model.addAttribute("weekEffortValues", values1HHMM);		
		
		//Printing each and every effort on UI
		ParentTransactionModel parentTransactionModelEachAndEveryEffort = new ParentTransactionModel();
		parentTransactionModelEachAndEveryEffort.setEmpId(userId);
		parentTransactionModelEachAndEveryEffort.setMonthName(calenderSelectedMonth);
		parentTransactionModelEachAndEveryEffort.setYearName(String.valueOf(calenderSelectedYear));
		List<Integer> transactionIdEachAndEvery = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);;
		List<Object> eachAndEveryEffort = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery);
		
		/*
		 * DateUseProjectModelId dateUseProjectModelIdEachAndEveryEffort = new
		 * DateUseProjectModelId();
		 * dateUseProjectModelIdEachAndEveryEffort.setEmpId(userId);
		 * dateUseProjectModelIdEachAndEveryEffort.setMonthName(calenderSelectedMonth);
		 * dateUseProjectModelIdEachAndEveryEffort.setYearName(String.valueOf(
		 * calenderSelectedYear)); List<Object> eachAndEveryEffort =
		 * this.dateUseProjectService.getEachAndEveryEffort(
		 * dateUseProjectModelIdEachAndEveryEffort);
		 */
		
		List<Object> eachAndEveryWeek = new ArrayList<>();
		List<Object> eachAndEveryOnlyEffort = new ArrayList<>();
		List<Object> eachAndEveryProject = new ArrayList<>();
		List<Object> eachAndEveryStatus = new ArrayList<>();

		for (Object result : eachAndEveryEffort) {
		    if (result instanceof Object[]) {
		        Object[] row = (Object[]) result;

		        if (row.length >= 4) {
		        	eachAndEveryWeek.add(row[0]);
		        	eachAndEveryOnlyEffort.add(row[1]);
		        	eachAndEveryProject.add(row[2]);
		        	eachAndEveryStatus.add(row[3]);
		        }
		    }
		}
		
		//================start================== (Printing each and every effort on UI for sandwiched weeks)
        if(hasWeekCommon && previousMonthOddDays>2){
        	parentTransactionModelEachAndEveryEffort.setMonthName(previousMonthResult[0]);
    		parentTransactionModelEachAndEveryEffort.setYearName(previousMonthResult[1]);
    		parentTransactionModelEachAndEveryEffort.setWeekName(String.valueOf(filledMonthWeeknumbers.get(0)));
    		List<Integer> transactionIdEachAndEvery1 = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);
        	List<Object> eachAndEveryEffortPreviousMonthLastWeek = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery1);
			/*
			 * dateUseProjectModelIdEachAndEveryEffort.setMonthName(previousMonthResult[0]);
			 * dateUseProjectModelIdEachAndEveryEffort.setYearName(previousMonthResult[1]);
			 * dateUseProjectModelIdEachAndEveryEffort.setWeekName(String.valueOf(
			 * filledMonthWeeknumbers.get(0))); List<Object>
			 * eachAndEveryEffortPreviousMonthLastWeek =
			 * this.dateUseProjectService.getEachAndEveryEffort(
			 * dateUseProjectModelIdEachAndEveryEffort);
			 */
        	for (Object result : eachAndEveryEffortPreviousMonthLastWeek) {
    		    if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 4) {
    		        	eachAndEveryWeek.add(row[0]);
    		        	eachAndEveryOnlyEffort.add(row[1]);
    		        	eachAndEveryProject.add(row[2]);
    		        	eachAndEveryStatus.add(row[3]);
    		        }
    		    }
    		}
        }
        if(hasWeekCommonLast && currentMonthOddDays<=2){
        	parentTransactionModelEachAndEveryEffort.setMonthName(nextMonthResult[0]);
    		parentTransactionModelEachAndEveryEffort.setYearName(nextMonthResult[1]);
    		parentTransactionModelEachAndEveryEffort.setWeekName(String.valueOf(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1)));
    		List<Integer> transactionIdEachAndEvery2 = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffort);
        	List<Object> eachAndEveryEffortNextMonthLastWeek = this.projectWeekEffortTransactionService.getEachAndEveryEffort(transactionIdEachAndEvery2);
			/*
			 * dateUseProjectModelIdEachAndEveryEffort.setMonthName(nextMonthResult[0]);
			 * dateUseProjectModelIdEachAndEveryEffort.setYearName(nextMonthResult[1]);
			 * dateUseProjectModelIdEachAndEveryEffort.setWeekName(String.valueOf(
			 * filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1))); List<Object>
			 * eachAndEveryEffortNextMonthLastWeek =
			 * this.dateUseProjectService.getEachAndEveryEffort(
			 * dateUseProjectModelIdEachAndEveryEffort);
			 */
        	for (Object result : eachAndEveryEffortNextMonthLastWeek) {
    		    if (result instanceof Object[]) {
    		        Object[] row = (Object[]) result;

    		        if (row.length >= 4) {
    		        	eachAndEveryWeek.add(row[0]);
    		        	eachAndEveryOnlyEffort.add(row[1]);
    		        	eachAndEveryProject.add(row[2]);
    		        	eachAndEveryStatus.add(row[3]);
    		        }
    		    }
    		}
        }
        //================end==================
        
		List<Object> eachAndEveryOnlyEffortHHMM = convertToHHMM(eachAndEveryOnlyEffort);
        model.addAttribute("eachAndEveryWeek", eachAndEveryWeek);
        model.addAttribute("eachAndEveryOnlyEffort", eachAndEveryOnlyEffortHHMM);
        model.addAttribute("eachAndEveryProject", eachAndEveryProject);
        model.addAttribute("eachAndEveryStatus", eachAndEveryStatus);
        
      //popup for project tasks
        //code for sending project task at frontend
  		List<String> projectTaskNames = this.projectTaskMasterService.getProjectTaskNames() != null ?
  				this.projectTaskMasterService.getProjectTaskNames() : new ArrayList<>();
  	
  		List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
        
  		List<List<String>> projectSubTaskNames = this.projectSubTaskMasterService.getProjectSubTaskNames(projectTaskIds) != null ?
				this.projectSubTaskMasterService.getProjectSubTaskNames(projectTaskIds) : new ArrayList<>();
  	
		List<List<Long>> projectSubTaskIds = this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) != null ?
				this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) : new ArrayList<>();
  		
		List<List<List<String>>> projectSubTaskDescNames = this.projectSubTaskDescMasterService.getProjectSubTaskDescNames(projectSubTaskIds) != null ?
				this.projectSubTaskDescMasterService.getProjectSubTaskDescNames(projectSubTaskIds) : new ArrayList<>();
  	
		List<List<List<Long>>> projectSubTaskDescIds = this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) != null ?
				this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) : new ArrayList<>();
		
		model.addAttribute("projectTaskNames", projectTaskNames);
		model.addAttribute("projectTaskIds", projectTaskIds);
		
		model.addAttribute("projectSubTaskNames", projectSubTaskNames);
		model.addAttribute("projectSubTaskIds", projectSubTaskIds);
		
		model.addAttribute("projectSubTaskDescNames", projectSubTaskDescNames);
		model.addAttribute("projectSubTaskDescIds", projectSubTaskDescIds);
		
		List<Long> projectSubTaskDescIdsPlain = projectSubTaskDescIds.stream()
			    .flatMap(List::stream)
			    .flatMap(List::stream)
			    .collect(Collectors.toList());
		
		model.addAttribute("projectSubTaskDescIdsPlain", projectSubTaskDescIdsPlain);
		//to disable weeks not present in opened month
		Boolean isCurrentWeekPresent =  weekNumbersCalendar.contains(activeWeekNumber);
	    Boolean isPreviousWeekPresent =  weekNumbersCalendar.contains(activeWeekNumber - 1);
	    model.addAttribute("isCurrentWeekPresent", isCurrentWeekPresent);
	    model.addAttribute("isPreviousWeekPresent", isPreviousWeekPresent);
	    if(isCurrentWeekPresent || isPreviousWeekPresent) {
	    	int monthNumber = Month.valueOf(calenderSelectedMonth.toUpperCase()).getValue();
		    int monthMinus1;
		    int monthPlus1;
		    if(monthNumber == 1) {
		    	monthMinus1 = 12;
		    }
		    else {
		    	monthMinus1 = monthNumber - 1;
		    }
		    if(monthNumber == 12) {
		    	monthPlus1 = 1;
		    }
		    else {
		    	monthPlus1 = monthNumber + 1;
		    }
		    model.addAttribute("monthSelectedPopUpMinus1", Month.of(monthMinus1).name());
		    model.addAttribute("monthSelectedPopUpPlus1", Month.of(monthPlus1).name());
	    }
	    else {
	    	LocalDate todayDate = LocalDate.now();
	    	String monthToday = todayDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	    	Integer yearToday = Year.now().getValue();
	    	List<Integer> monthTodayWeekNumbers = getWeekNumbersCalendar(monthToday, yearToday);
	    	Boolean isCurrentWeekInTodayMonth =  monthTodayWeekNumbers.contains(activeWeekNumber);
		    Boolean isPreviousWeekInTodayMonth =  monthTodayWeekNumbers.contains(activeWeekNumber - 1);
		    int monthNumber = Month.valueOf(monthToday.toUpperCase()).getValue();
		    int monthMinus1;
		    int monthPlus1;
		    if(monthNumber == 1) {
		    	monthMinus1 = 12;
		    }
		    else {
		    	monthMinus1 = monthNumber - 1;
		    }
		    if(monthNumber == 12) {
		    	monthPlus1 = 1;
		    }
		    else {
		    	monthPlus1 = monthNumber + 1;
		    }
		    if(isCurrentWeekInTodayMonth && isPreviousWeekInTodayMonth) {
		    	model.addAttribute("monthSelectedPopUpMinus1", monthToday.toUpperCase());
			    model.addAttribute("monthSelectedPopUpPlus1", monthToday.toUpperCase());
		    }
		    else if(isCurrentWeekInTodayMonth && !isPreviousWeekInTodayMonth){
		    	model.addAttribute("monthSelectedPopUpMinus1", Month.of(monthMinus1).name());
			    model.addAttribute("monthSelectedPopUpPlus1", monthToday.toUpperCase());
		    }
		    
	    }
	    
	    
	    
		//to show already exixts misc other activity on text area for user to fill already exist misc other activity
		List<String> allMiscActivityDesc = this.miscOtherActivityMasterService.getAllDesc() != null ?
				this.miscOtherActivityMasterService.getAllDesc() : new ArrayList<>();
		List<String> allMiscActivityDescTemp = new ArrayList<String>();
		for(String str: allMiscActivityDesc){
			allMiscActivityDescTemp.add(str+"^");
		}
		model.addAttribute("allMiscActivityDescTemp", allMiscActivityDescTemp);
		
		//Showing Help option on UI which will tell you the project task and what you can consider
		List<Object> projectTaskDescHelp = this.projectTaskHelpMasterService.getProjectTaskHelpDesc() != null ?
				this.projectTaskHelpMasterService.getProjectTaskHelpDesc(): new ArrayList<>();
		// Separate the elements into two lists
        List<String> projectTaskName = new ArrayList<>();
        List<String> projectTaskDesc = new ArrayList<>();

        for (Object obj : projectTaskDescHelp) {
            Object[] arr = (Object[]) obj;
            if (arr.length >= 2) {
                projectTaskName.add(String.valueOf(arr[0]));
                projectTaskDesc.add(String.valueOf(arr[1]));
            }
        }
        // Create a HashMap to store task names and their corresponding descriptions
        Map<String, List<String>> taskMap = new HashMap<>();

        // Populate the HashMap
        for (int i = 0; i < projectTaskName.size(); i++) {
            String taskName = projectTaskName.get(i);
            String taskDesc = projectTaskDesc.get(i);

            // If the task name already exists in the map, add the description to its list
            // Otherwise, create a new list for the task name
            if (taskMap.containsKey(taskName)) {
                taskMap.get(taskName).add(taskDesc);
            } else {
                List<String> descList = new ArrayList<>();
                descList.add(taskDesc);
                taskMap.put(taskName, descList);
            }
        }
        model.addAttribute("projectTaskDescMap", taskMap);
		return "timesheethome";
	}
	
	//ajax call for populating data in project popup
	@RequestMapping(value = "/getProjectPopUpData", method = RequestMethod.POST)
 	public @ResponseBody List<HashMap<Long, String>> getProjectPopUpData(
 			@RequestParam("selectedYear") String selectedYear, @RequestParam("activeWeek") String activeWeek, @RequestParam("previousWeek") String previousWeek, 
 			@RequestParam("selectedEmpId") String selectedEmpId, @RequestParam("projectTimesheetType") String projectTimesheetType, @RequestParam("projectId") String projectId) {
		//Printing each and every effort on pop up UI
	    ParentTransactionModel parentTransactionModelEachAndEveryEffortPopUp = new ParentTransactionModel();
	    parentTransactionModelEachAndEveryEffortPopUp.setEmpId(Integer.parseInt(selectedEmpId));
	    parentTransactionModelEachAndEveryEffortPopUp.setYearName(selectedYear);
	    parentTransactionModelEachAndEveryEffortPopUp.setWeekName(previousWeek);
	    List<Integer> transactionIdEachAndEveryPopUpPreviousWeek = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffortPopUp);
	   
	    List<HashMap<Long, String>> eachAndEveryPopUpByProject = new ArrayList<HashMap<Long,String>>();
	    if(projectTimesheetType.equalsIgnoreCase("b")) {
	    	HashMap<Long, String> eachAndEveryEffortTaskPreviousWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdBasic(transactionIdEachAndEveryPopUpPreviousWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortTaskPreviousWeek);
	    	parentTransactionModelEachAndEveryEffortPopUp.setWeekName(activeWeek);
	    	List<Integer> transactionIdEachAndEveryPopUpCurrentWeek = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffortPopUp);
	    	HashMap<Long, String> eachAndEveryEffortTaskCurrentWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdBasic(transactionIdEachAndEveryPopUpCurrentWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortTaskCurrentWeek);
	    }
	    else if(projectTimesheetType.equalsIgnoreCase("d")){
	    	HashMap<Long, String> eachAndEveryEffortSubTaskDescPreviousWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdDetailed(transactionIdEachAndEveryPopUpPreviousWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortSubTaskDescPreviousWeek);
	    	parentTransactionModelEachAndEveryEffortPopUp.setWeekName(activeWeek);
	    	List<Integer> transactionIdEachAndEveryPopUpCurrentWeek = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffortPopUp);
	    	HashMap<Long, String> eachAndEveryEffortSubTaskDescCurrentWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdDetailed(transactionIdEachAndEveryPopUpCurrentWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortSubTaskDescCurrentWeek);
	    }
	    else {
	    	HashMap<Long, String> eachAndEveryEffortTaskPreviousWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdBasic(transactionIdEachAndEveryPopUpPreviousWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortTaskPreviousWeek);
	    	parentTransactionModelEachAndEveryEffortPopUp.setWeekName(activeWeek);
	    	List<Integer> transactionIdEachAndEveryPopUpCurrentWeek = this.parentTransactionService.getTransactionId(parentTransactionModelEachAndEveryEffortPopUp);
	    	HashMap<Long, String> eachAndEveryEffortTaskCurrentWeek = this.projectTaskWeekEffortTransactionService.getTaskAndSubTaskDescEffortByProjectIdBasic(transactionIdEachAndEveryPopUpCurrentWeek, Long.parseLong(projectId));
	    	eachAndEveryPopUpByProject.add(eachAndEveryEffortTaskCurrentWeek);
	    }
	    return eachAndEveryPopUpByProject;
		 
	}
	
	
	@RequestMapping(path = "/weeksubmit", method = RequestMethod.POST)
	public String weekSubmission(/*@RequestParam Integer weekNumberSubmitted, */ @ModelAttribute("weekSubmitModel") WeekSubmitModel weekSubmitModel/*, @RequestParam String yearSelectDB, @RequestParam List<String> weekEffort, @RequestParam String todaysDate, @RequestParam String selectedDate, @RequestParam Integer employeeIdDB, @RequestParam String employeeNameDB, @RequestParam long groupIdDB, @RequestParam String weekNumbers, @RequestParam String projectIds, @RequestParam String miscActivityIds*/, BindingResult bindingResult, RedirectAttributes redirectAttributes ){
		
		
		validator.validate(weekSubmitModel, bindingResult);
		if (bindingResult.hasErrors()) {
            // Handle validation errors
            // You can access validation errors using bindingResult object
			for (ObjectError error : bindingResult.getAllErrors()) {
                redirectAttributes.addFlashAttribute("errorMessage", error.getDefaultMessage());
            }
            //redirectAttributes.addFlashAttribute("errorMessage", "Validation failed");
            return "redirect:/timesheethome";
        }
		
		
		//SAVING DATA IN pms_misc_other_activity_master table
		/*long miscOtherActivityId = 0;
		//finding next miscOtherActivityId by finding maximum id inserted and if no ids are there in table then initializing it with 1. If Id is already there in tablle then using the same Id
		List<Long> allIdsMiscOtherActivityMasterModel = this.miscOtherActivityMasterService.getAllIds();
		List<String> allDescMiscOtherActivityMasterModel = this.miscOtherActivityMasterService.getAllDesc();
		boolean haveMiscActivity = false;
		int m = 0;
		for(m =0; m<allDescMiscOtherActivityMasterModel.size(); m++){
			if(weekSubmitModel.getMiscOtherActivityDesc().equalsIgnoreCase(allDescMiscOtherActivityMasterModel.get(m))){
				haveMiscActivity = true;
				break;
			}
		}
		if(haveMiscActivity){
			miscOtherActivityId = allIdsMiscOtherActivityMasterModel.get(m);
		}else{
			if(allIdsMiscOtherActivityMasterModel.size() == 0 || allIdsMiscOtherActivityMasterModel.isEmpty() || allIdsMiscOtherActivityMasterModel == null){
				miscOtherActivityId = 1;
			}else{
				// Initialize max to the first element
		        long max = allIdsMiscOtherActivityMasterModel.get(0);

		        // Iterate through the list to find the maximum element
		        for (int j = 1; j < allIdsMiscOtherActivityMasterModel.size(); j++) {
		            long current = allIdsMiscOtherActivityMasterModel.get(j);
		            if (current > max) {
		                max = current;
		            }
		        }
		        miscOtherActivityId = max + 1;
			}
			
			if(!weekSubmitModel.getMiscOtherActivityDesc().isEmpty() && (weekSubmitModel.getMiscOtherActivityPreviousWeek()!=null || weekSubmitModel.getMiscOtherActivityActiveWeek()!=null)){
				MiscOtherActivityMasterModel miscOtherActivityMasterModel = new MiscOtherActivityMasterModel();
				miscOtherActivityMasterModel.setMiscOtherActivityId(miscOtherActivityId);
				miscOtherActivityMasterModel.setMiscOtherActivityDesc(weekSubmitModel.getMiscOtherActivityDesc());
				miscOtherActivityMasterModel.setIsValid(1);
				miscOtherActivityMasterModel.setTransactionTime(LocalDate.now());
				miscOtherActivityMasterModel.setTransactionUserId(weekSubmitModel.getEmployeeIdDB());
				miscOtherActivityId = this.miscOtherActivityMasterService.entryInMiscOtherActivityMaster(miscOtherActivityMasterModel);
			}
		}*/
		
		//data collection for SAVING DATA IN pms_timesheet_week_effort_trn & pms_timesheet_project_week_effort_trn table
		String dateSelected = null;
		if(weekSubmitModel.getSelectedDate().isEmpty()) {
			dateSelected = weekSubmitModel.getTodaysDate();
		}else {
			dateSelected = weekSubmitModel.getSelectedDate();
		}
		System.out.println("week Submit date selected "+dateSelected);
		String[] elements = weekSubmitModel.getWeekNumbers().substring(1, weekSubmitModel.getWeekNumbers().length() - 1).split(",");
		//System.out.println(weekSubmitModel.getWeekEffort());
        // Convert to a list of integers
        List<Integer> weekNumbersIntegerList = Arrays.stream(elements)
                .map(String::trim) // Remove leading/trailing spaces
                .map(Integer::parseInt)
                .collect(Collectors.toList());
		int interval = weekNumbersIntegerList.size();
		List<List<String>> seperateWeekEffort = new ArrayList<>();
		for (int startIndex = 0; startIndex < interval; startIndex++) {
            List<String> innerList = new ArrayList<>();
            for (int i = startIndex; i < weekSubmitModel.getWeekEffort().size(); i += interval) {
                innerList.add(weekSubmitModel.getWeekEffort().get(i));
            }
            seperateWeekEffort.add(innerList);
	    }
		//System.out.println(seperateWeekEffort);
		List<Long> projectIdsDB = new ArrayList<Long>();
		//System.out.println(weekSubmitModel.getProjectIds());
		if(!weekSubmitModel.getProjectIds().equalsIgnoreCase("[]")){
			String[] elements1 = weekSubmitModel.getProjectIds().substring(1, weekSubmitModel.getProjectIds().length() - 1).split(",");
	        // Convert to a list of integers
	        List<Integer> intProjectIdsDB = Arrays.stream(elements1)
	                .map(String::trim) // Remove leading/trailing spaces
	                .map(Integer::parseInt)
	                .collect(Collectors.toList());
	        
	       projectIdsDB = intProjectIdsDB.stream()
	                .map(Long::valueOf)
	                .collect(Collectors.toList());
		}
		
		// Get the month name from the first day of the week
        //String monthSelectDB = YearMonth.from(firstDayOfWeek).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String monthSelectDB = LocalDate.parse(dateSelected, DateTimeFormatter.ISO_DATE).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        //Solving sandwiched week
        //finding previous and next month info
        String[] previousMonthResult = getPreviousMonthAndYear(monthSelectDB, Integer.parseInt(weekSubmitModel.getYearSelectDB()));
        String[] nextMonthResult = getNextMonthAndYear(monthSelectDB, Integer.parseInt(weekSubmitModel.getYearSelectDB()));
        List<Integer> filledMonthWeeknumbers = getWeekNumbersCalendar(monthSelectDB, Integer.parseInt(weekSubmitModel.getYearSelectDB()));
        List<Integer> previousMonthWeeknumbers = getWeekNumbersCalendar(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        List<Integer> nextMonthWeeknumbers = getWeekNumbersCalendar(nextMonthResult[0], Integer.parseInt(nextMonthResult[1]));
        Boolean hasWeekCommon = false;
        if(filledMonthWeeknumbers.get(0).equals(previousMonthWeeknumbers.get(previousMonthWeeknumbers.size() -1))){
        	hasWeekCommon = true;
        }
        Boolean hasWeekCommonLast = false;
        if(nextMonthWeeknumbers.get(0).equals(filledMonthWeeknumbers.get(filledMonthWeeknumbers.size() -1))){
        	hasWeekCommonLast = true;
        }
        Integer previousMonthOddDays = getLastWeekDaysCount(previousMonthResult[0], Integer.parseInt(previousMonthResult[1]));
        Integer currentMonthOddDays = getLastWeekDaysCount(monthSelectDB, Integer.parseInt(weekSubmitModel.getYearSelectDB()));
        
        //SAVING DATA IN pms_timesheet_parent_trn, pms_timesheet_week_effort_trn, pms_timesheet_project_week_effort_trn table
		for (int p = 0; p < seperateWeekEffort.size(); p++) {
	        // Get the first day of the specified week
	        /*LocalDate firstDayOfWeek = LocalDate.of(Integer.parseInt(weekSubmitModel.getYearSelectDB()), 1, 1)
	                .with(WeekFields.ISO.weekOfYear(), weekNumbersIntegerList.get(p))
	                .with(WeekFields.ISO.dayOfWeek(), 1);*/
	        String weekSelectDB = Integer.toString(weekNumbersIntegerList.get(p));
	        
	        //SAVING DATA in pms_timesheet_parent_trn
	        ParentTransactionModel parentTransactionModel = new ParentTransactionModel();
	        parentTransactionModel.setEmpId(weekSubmitModel.getEmployeeIdDB());
	        parentTransactionModel.setWeekName(weekSelectDB);
	        if(p==0 && hasWeekCommon && previousMonthOddDays>2){
	        	parentTransactionModel.setMonthName(previousMonthResult[0]);
	        	parentTransactionModel.setYearName(previousMonthResult[1]);
    		}
	        else if(p==seperateWeekEffort.size()-1 && hasWeekCommonLast && currentMonthOddDays<=2){
    			parentTransactionModel.setMonthName(nextMonthResult[0]);
    			parentTransactionModel.setYearName(nextMonthResult[1]);
    		}
	        else{
    			parentTransactionModel.setMonthName(monthSelectDB);
    			parentTransactionModel.setYearName(weekSubmitModel.getYearSelectDB());
    		}
	        int finalTransactionId;
	        List<Integer> transactionIdTemp = this.parentTransactionService.getTransactionId(parentTransactionModel);
	        if(transactionIdTemp.isEmpty()) {
	        	List<Integer> transactionIdTemp1 = this.parentTransactionService.getAllTransactionId();
	        	finalTransactionId = getPrimaryTransactionId(transactionIdTemp1);
	        }
	        else {
	        	finalTransactionId = transactionIdTemp.get(0);
	        }
			parentTransactionModel.setTransactionId(finalTransactionId);
			parentTransactionModel.setIsValid(1);
			parentTransactionModel.setTransactionTime(LocalDate.now());
			 
	        Integer transactionId = this.parentTransactionService.entryInTimesheetParentTrn(parentTransactionModel);
	        
	      //SAVING DATA IN pms_timesheet_week_effort_trn table
	        boolean containsEmptyString = false;

	        for (String str : seperateWeekEffort.get(p)) {
	            if (!str.isEmpty()) {
	                containsEmptyString = true;
	                break; // No need to continue checking once an empty string is found
	            }
	        }
	        
	        if(containsEmptyString){
	        	WeekEffortTransactionModel weekEffortTransactionModel = new WeekEffortTransactionModel();
	        	//weekEffortTransactionModel.setParentTransactionModel(parentTransactionModel);
	        	weekEffortTransactionModel.setTransactionId(finalTransactionId);
	        	int sum = 0;
		        for (String str : seperateWeekEffort.get(p)) {
		            Integer number = convertToMinutes(str);
		            sum += number;
		        }
		        weekEffortTransactionModel.setTotalEffortWeek(String.valueOf(sum));
		        weekEffortTransactionModel.setIsValid(1);
		        weekEffortTransactionModel.setTransactionTime(LocalDate.now());
		        Integer transactionIdWeek = this.weekEffortTransactionService.entryInTimesheetWeekEffortTrn(weekEffortTransactionModel);
	        }else{
	        	WeekEffortTransactionModel weekEffortTransactionModel = new WeekEffortTransactionModel();
	        	//weekEffortTransactionModel.setParentTransactionModel(parentTransactionModel);
	        	weekEffortTransactionModel.setTransactionId(finalTransactionId);
		        weekEffortTransactionModel.setTotalEffortWeek("0");
		        weekEffortTransactionModel.setIsValid(0);
		        weekEffortTransactionModel.setTransactionTime(LocalDate.now());
		        Integer transactionIdWeek = this.weekEffortTransactionService.entryInTimesheetWeekEffortTrn(weekEffortTransactionModel);
	        }
	        
	        //SAVING DATA to pms_timesheet_project_week_effort_trn table - project
	        int i = 0;
	        while (i < projectIdsDB.size()) {
	        	if(seperateWeekEffort.get(p).get(i).isEmpty()==false){
	        		ProjectWeekEffortTransactionModelId projectWeekEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
	        		projectWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
	        		//projectWeekEffortTransactionModelId.setTransactionId(finalTransactionId);
	        		projectWeekEffortTransactionModelId.setProjectId(projectIdsDB.get(i));
	        		projectWeekEffortTransactionModelId.setStatus("p");
	        		ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel = new ProjectWeekEffortTransactionModel();
	        		projectWeekEffortTransactionModel.setId(projectWeekEffortTransactionModelId);
	        		int totalMinutes = convertToMinutes(seperateWeekEffort.get(p).get(i));
	        		projectWeekEffortTransactionModel.setTotalEffortProject(String.valueOf(totalMinutes));
	        		projectWeekEffortTransactionModel.setIsValid(1);
	        		projectWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
	        		Integer transactionIdProject = this.projectWeekEffortTransactionService.entryInTimesheetProjectWeekEffortTrn(projectWeekEffortTransactionModel);
	        		
	        	}else{
	        		ProjectWeekEffortTransactionModelId projectWeekEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
	        		projectWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
	        		//projectWeekEffortTransactionModelId.setTransactionId(finalTransactionId);
	        		projectWeekEffortTransactionModelId.setProjectId(projectIdsDB.get(i));
	        		projectWeekEffortTransactionModelId.setStatus("p");
	        		ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel = new ProjectWeekEffortTransactionModel();
	        		projectWeekEffortTransactionModel.setId(projectWeekEffortTransactionModelId);
	        		
	        		projectWeekEffortTransactionModel.setTotalEffortProject("0");
	        		projectWeekEffortTransactionModel.setIsValid(0);
	        		projectWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
	        		Integer transactionIdProject = this.projectWeekEffortTransactionService.entryInTimesheetProjectWeekEffortTrn(projectWeekEffortTransactionModel);
	        	}
	        	i++;
	            
	        }
	        
	        //saving data to pms_timesheet_project_week_effort_trn table - activity
	        String[] elements2 = weekSubmitModel.getMiscActivityIds().substring(1, weekSubmitModel.getMiscActivityIds().length() - 1).split(",");
	        // Convert to a list of integers
	        List<Integer> intActivityIdsDB = Arrays.stream(elements2)
	                .map(String::trim) // Remove leading/trailing spaces
	                .map(Integer::parseInt)
	                .collect(Collectors.toList());
	        
	        List<Long> activityIdsDB = intActivityIdsDB.stream()
	                .map(Long::valueOf)
	                .collect(Collectors.toList());
	        
	        for (int j = 0; j < activityIdsDB.size(); j++) {
	        	if(seperateWeekEffort.get(p).get(i+j).isEmpty()==false){
	        		ProjectWeekEffortTransactionModelId projectWeekEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
	        		projectWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
	        		//projectWeekEffortTransactionModelId.setTransactionId(finalTransactionId);
	        		
	        		projectWeekEffortTransactionModelId.setProjectId(activityIdsDB.get(j));
	        		projectWeekEffortTransactionModelId.setStatus("m");
	        		ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel = new ProjectWeekEffortTransactionModel();
	        		
	        		projectWeekEffortTransactionModel.setId(projectWeekEffortTransactionModelId);
	        		int totalMinutes = convertToMinutes(seperateWeekEffort.get(p).get(i+j));
	        		projectWeekEffortTransactionModel.setTotalEffortProject(String.valueOf(totalMinutes));
	        		projectWeekEffortTransactionModel.setIsValid(1);
	        		projectWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
	        		//Saving data in last column of pms_timesheet_project_week_effort_trn table i.e. miscOtherActivityId=========start=============
	        		/*List<Long> alreadyPresentMiscOtherActivityIdList = this.projectWeekEffortTransactionService.getMiscOtherActivityId(projectWeekEffortTransactionModel);
	        		if(!alreadyPresentMiscOtherActivityIdList.isEmpty() && activityIdsDB.get(j) == 7 && alreadyPresentMiscOtherActivityIdList.get(0) != null){
	        			long alreadyPresentMiscOtherActivityId = alreadyPresentMiscOtherActivityIdList.get(0);
	        			projectWeekEffortTransactionModel.setMiscOtherActivityId(alreadyPresentMiscOtherActivityId);
	        		}
	        		
	        		if(!weekSubmitModel.getMiscOtherActivityDesc().isEmpty() && activityIdsDB.get(j) == 7 && weekSubmitModel.getMiscOtherActivityPreviousWeek() != null && weekSelectDB.equalsIgnoreCase(weekSubmitModel.getMiscOtherActivityPreviousWeek())){
	        			projectWeekEffortTransactionModel.setMiscOtherActivityId(miscOtherActivityId);
	        		}
	        		if(!weekSubmitModel.getMiscOtherActivityDesc().isEmpty() && activityIdsDB.get(j) == 7 && weekSubmitModel.getMiscOtherActivityActiveWeek() != null && weekSelectDB.equalsIgnoreCase(weekSubmitModel.getMiscOtherActivityActiveWeek())){
	        			projectWeekEffortTransactionModel.setMiscOtherActivityId(miscOtherActivityId);
	        		}*/
	        		//============end===============
	        		Integer transactionIdActivity = this.projectWeekEffortTransactionService.entryInTimesheetProjectWeekEffortTrn(projectWeekEffortTransactionModel);
	        		
	        	}else{
	        		ProjectWeekEffortTransactionModelId projectWeekEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
	        		projectWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
	        		//projectWeekEffortTransactionModelId.setTransactionId(finalTransactionId);
	        		
	        		projectWeekEffortTransactionModelId.setProjectId(activityIdsDB.get(j));
	        		projectWeekEffortTransactionModelId.setStatus("m");
	        		ProjectWeekEffortTransactionModel projectWeekEffortTransactionModel = new ProjectWeekEffortTransactionModel();
	        		projectWeekEffortTransactionModel.setId(projectWeekEffortTransactionModelId);
	        		projectWeekEffortTransactionModel.setTotalEffortProject("0");
	        		projectWeekEffortTransactionModel.setIsValid(0);
	        		projectWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
	        		
	        		Integer transactionIdActivity = this.projectWeekEffortTransactionService.entryInTimesheetProjectWeekEffortTrn(projectWeekEffortTransactionModel);
	        	}
	            
	        }
	        
	        
		}
		
		
		//Fetching project task details so that we can save it in database table - pms_timesheet_project_task__week_effort_trn===========start================
		List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
		
		List<List<Long>> projectSubTaskIds = this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) != null ?
				this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) : new ArrayList<>();
  		
		List<List<List<Long>>> projectSubTaskDescIds = this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) != null ?
				this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) : new ArrayList<>();
		
		List<Long> projectSubTaskDescIdsPlain = projectSubTaskDescIds.stream()
			    .flatMap(List::stream)
			    .flatMap(List::stream)
			    .collect(Collectors.toList());
		
		List<String> taskProjectDetailsString = createListBySplitCommas(weekSubmitModel.getTaskProjectDetails());
		List<String> projectTaskDetailsTemp = createListBySplitCommas(weekSubmitModel.getProjectTaskDetails());
		List<String> taskProjectTimesheetTypeDetails = createListBySplitCommas(weekSubmitModel.getTaskProjectTimesheetTypeDetails());
		List<List<List<String>>> projectTaskDetails = new ArrayList<List<List<String>>>();
		int k = 0;
		for(int i = 0; i<taskProjectDetailsString.size(); i++){
			List<List<String>> list1 = new ArrayList<List<String>>();
			List<Long> listToBeUsed = new ArrayList<Long>();
			if(taskProjectTimesheetTypeDetails.get(i).equalsIgnoreCase("b")) {
				listToBeUsed = new ArrayList<Long>(projectTaskIds);
			}
			else if(taskProjectTimesheetTypeDetails.get(i).equalsIgnoreCase("d")) {
				listToBeUsed = new ArrayList<Long>(projectSubTaskDescIdsPlain);
			}
			for(int j = 0; j<listToBeUsed.size(); j++){
				List<String> list2 = new ArrayList<String>();
				list2.add(projectTaskDetailsTemp.get(k));
				list2.add(projectTaskDetailsTemp.get(k+1));
				list2.add(projectTaskDetailsTemp.get(k+2));
				list1.add(list2);
				k = k+3;
			}
			projectTaskDetails.add(list1);
		}		
	    // Convert to minutes
        List<List<List<Long>>> projectTaskDetailsInMinutes = new ArrayList<>();
        for (List<List<String>> outerList : projectTaskDetails) {
            List<List<Long>> innerList = new ArrayList<>();
            for (List<String> inner : outerList) {
                List<Long> timeInMinutes = new ArrayList<>();
                for (String timeString : inner) {
                    if (timeString.isEmpty()) {
                        timeInMinutes.add(0L); // Handle empty strings
                    } else if (timeString.matches("\\d{2}:\\d{2}")) {
                        timeInMinutes.add((long)convertToMinutes(timeString)); // Convert HH:MM format to minutes
                    } else {
                        timeInMinutes.add(Long.parseLong(timeString)); // Convert other strings to long
                    }
                }
                innerList.add(timeInMinutes);
            }
            projectTaskDetailsInMinutes.add(innerList);
        }
        List<Long> taskProjectDetails = new ArrayList<>();
        for (String str : taskProjectDetailsString) {
        	taskProjectDetails.add(Long.parseLong(str));
        }
        
        System.out.println("taskProjectDetails "+taskProjectDetails);
        System.out.println("projectTaskDetailsInMinutes "+projectTaskDetailsInMinutes);
        
        //Fetching project task details so that we can save it in database===========end================
        
        // Get the week number using ISO week fields
        //int activeWeekNumber = currentDatetoFindActiveWeekNumber.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int activeWeekNumber = Integer.parseInt(weekSubmitModel.getActiveWeekNumber());
        
        boolean havePreviousWeek = false;
        for(int week : filledMonthWeeknumbers){
        	if(week == activeWeekNumber-1){
        		havePreviousWeek = true;
        		break;
        	}
        }
        boolean haveActiveWeek = false;
        for(int week : filledMonthWeeknumbers){
        	if(week == activeWeekNumber){
        		haveActiveWeek = true;
        		break;
        	}
        }
		//SAVING DATA IN pms_timesheet_project_task_week_effort_trn table
		for(int i = 0; i<taskProjectDetails.size(); i++){
			List<Long> listToBeUsed = new ArrayList<Long>();
			boolean basicProject = true;
			if(taskProjectTimesheetTypeDetails.get(i).equalsIgnoreCase("b")) {
				listToBeUsed = new ArrayList<Long>(projectTaskIds);;
			}
			else if(taskProjectTimesheetTypeDetails.get(i).equalsIgnoreCase("d")) {
				listToBeUsed = new ArrayList<Long>(projectSubTaskDescIdsPlain);
				basicProject = false;
			}
			if(havePreviousWeek){
				for(int j =0; j<listToBeUsed.size(); j++){
					ParentTransactionModel parentTransactionModel = new ParentTransactionModel();
					//DateUseProjectTaskModelId dateUseProjectTaskModelId = new DateUseProjectTaskModelId();
					parentTransactionModel.setEmpId(weekSubmitModel.getEmployeeIdDB());
					if(activeWeekNumber-1==filledMonthWeeknumbers.get(0) && hasWeekCommon && previousMonthOddDays>2){
						parentTransactionModel.setMonthName(previousMonthResult[0]);
						parentTransactionModel.setYearName(previousMonthResult[1]);
	        		}else if(activeWeekNumber-1==filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1) && hasWeekCommonLast && currentMonthOddDays<=2){
	        			parentTransactionModel.setMonthName(nextMonthResult[0]);
	        			parentTransactionModel.setYearName(nextMonthResult[1]);
		        	}else{
		        		parentTransactionModel.setMonthName(monthSelectDB);
		        		parentTransactionModel.setYearName(weekSubmitModel.getYearSelectDB());
	        		}
					parentTransactionModel.setWeekName(String.valueOf(activeWeekNumber-1));
					Integer transactionIdTask = this.parentTransactionService.getTransactionId(parentTransactionModel).get(0);
					parentTransactionModel.setTransactionId(transactionIdTask);
					ProjectTaskWeekEffortTransactionModelId projectTaskWeekEffortTransactionModelId = new ProjectTaskWeekEffortTransactionModelId();
					projectTaskWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
					//projectTaskWeekEffortTransactionModelId.setTransactionId(transactionIdTask);
					projectTaskWeekEffortTransactionModelId.setProjectId(taskProjectDetails.get(i));
					if(basicProject) {
						projectTaskWeekEffortTransactionModelId.setProjectTaskId(listToBeUsed.get(j));
						projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(0);
					}
					else {
						projectTaskWeekEffortTransactionModelId.setProjectTaskId(this.projectTaskMasterService.getProjectTaskIdFromProjectSubTaskDescId(listToBeUsed.get(j)));
						projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(listToBeUsed.get(j));
					}
					//projectTaskWeekEffortTransactionModelId.setProjectTaskId(this.projectTaskMasterService.getProjectTaskIdFromProjectSubTaskDescId(projectSubTaskDescIdsPlain.get(j)));
					//projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(projectSubTaskDescIdsPlain.get(j));
					ProjectTaskWeekEffortTransactionModel projectTaskWeekEffortTransactionModel = new ProjectTaskWeekEffortTransactionModel();
					projectTaskWeekEffortTransactionModel.setId(projectTaskWeekEffortTransactionModelId);
					projectTaskWeekEffortTransactionModel.setIsValid(1);
					
					projectTaskWeekEffortTransactionModel.setTotalEffortProjectSubTaskDesc(projectTaskDetailsInMinutes.get(i).get(j).get(1).toString());
					projectTaskWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
					Integer transactionIdTask1 = this.projectTaskWeekEffortTransactionService.entryInTimesheetProjectTaskWeekEffortTrn(projectTaskWeekEffortTransactionModel);
				}
			}
			if(haveActiveWeek){
				for(int j =0; j<listToBeUsed.size(); j++){
					ParentTransactionModel parentTransactionModel = new ParentTransactionModel();
					//DateUseProjectTaskModelId dateUseProjectTaskModelId = new DateUseProjectTaskModelId();
					parentTransactionModel.setEmpId(weekSubmitModel.getEmployeeIdDB());
					if(activeWeekNumber==filledMonthWeeknumbers.get(0) && hasWeekCommon && previousMonthOddDays>2){
						parentTransactionModel.setMonthName(previousMonthResult[0]);
						parentTransactionModel.setYearName(previousMonthResult[1]);
	        		}else if(activeWeekNumber==filledMonthWeeknumbers.get(filledMonthWeeknumbers.size()-1) && hasWeekCommonLast && currentMonthOddDays<=2){
	        			parentTransactionModel.setMonthName(nextMonthResult[0]);
	        			parentTransactionModel.setYearName(nextMonthResult[1]);
		        	}else{
		        		parentTransactionModel.setMonthName(monthSelectDB);
		        		parentTransactionModel.setYearName(weekSubmitModel.getYearSelectDB());
	        		}
					parentTransactionModel.setWeekName(String.valueOf(activeWeekNumber));
					Integer transactionIdTask = this.parentTransactionService.getTransactionId(parentTransactionModel).get(0);
					parentTransactionModel.setTransactionId(transactionIdTask);
					ProjectTaskWeekEffortTransactionModelId projectTaskWeekEffortTransactionModelId = new ProjectTaskWeekEffortTransactionModelId();
					projectTaskWeekEffortTransactionModelId.setParentTransactionModel(parentTransactionModel);
					//projectTaskWeekEffortTransactionModelId.setTransactionId(transactionIdTask);
					projectTaskWeekEffortTransactionModelId.setProjectId(taskProjectDetails.get(i));
					if(basicProject) {
						projectTaskWeekEffortTransactionModelId.setProjectTaskId(listToBeUsed.get(j));
						projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(0);
					}
					else {
						projectTaskWeekEffortTransactionModelId.setProjectTaskId(this.projectTaskMasterService.getProjectTaskIdFromProjectSubTaskDescId(listToBeUsed.get(j)));
						projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(listToBeUsed.get(j));
					}
					//projectTaskWeekEffortTransactionModelId.setProjectTaskId(this.projectTaskMasterService.getProjectTaskIdFromProjectSubTaskDescId(projectSubTaskDescIdsPlain.get(j)));
					//projectTaskWeekEffortTransactionModelId.setProjectSubTaskDescId(projectSubTaskDescIdsPlain.get(j));
					ProjectTaskWeekEffortTransactionModel projectTaskWeekEffortTransactionModel = new ProjectTaskWeekEffortTransactionModel();
					projectTaskWeekEffortTransactionModel.setId(projectTaskWeekEffortTransactionModelId);
					projectTaskWeekEffortTransactionModel.setIsValid(1);
					projectTaskWeekEffortTransactionModel.setTotalEffortProjectSubTaskDesc(projectTaskDetailsInMinutes.get(i).get(j).get(2).toString());
					projectTaskWeekEffortTransactionModel.setTransactionTime(LocalDate.now());
					Integer transactionIdTask1 = this.projectTaskWeekEffortTransactionService.entryInTimesheetProjectTaskWeekEffortTrn(projectTaskWeekEffortTransactionModel);
				}
			}
		}
		return "redirect:/timesheethome";
	}
	//to get primary transaction Id
	 public static int getPrimaryTransactionId(List<Integer> numbers) {
	        if (numbers == null || numbers.isEmpty()) {
	            return 1;
	        }
	        return Collections.max(numbers) + 1;
	  }
	//To find week number of the year from date	
	public static Integer getWeekNumber(LocalDate currentDatetoFindActiveWeekNumber) {
    	// Extract the month name as a string
        String monthName = currentDatetoFindActiveWeekNumber.getMonth().toString();

        // Convert the month name to title case
        monthName = monthName.substring(0, 1) + monthName.substring(1).toLowerCase();
        int monthIndex = getMonthIndex(monthName);
        // Extract the year as an integer
        int year = currentDatetoFindActiveWeekNumber.getYear();
        List<Integer> weekNumbers = getWeekNumbersCalendar(monthName, year);
        LocalDate firstDayOfMonth = LocalDate.of(year, monthIndex, 1);

        // Find the first Monday of the month
        LocalDate firstMondayOfMonth = firstDayOfMonth;
        while (firstMondayOfMonth.getDayOfWeek() != DayOfWeek.MONDAY) {
        	firstMondayOfMonth = firstMondayOfMonth.plusDays(1);
        }

        // Calculate the day number (1 to 31) for the first Monday
        int dayNumber = firstMondayOfMonth.getDayOfMonth();
        System.out.println(dayNumber);
        if(dayNumber == 1) {
        	System.out.println(currentDatetoFindActiveWeekNumber.getDayOfMonth());
        	int weekNumberIndex = (int) Math.ceil((double) (currentDatetoFindActiveWeekNumber.getDayOfMonth()) / 7);
        	System.out.println(weekNumberIndex);
        	return weekNumbers.get(weekNumberIndex - 1);
        }else {
        	System.out.println(currentDatetoFindActiveWeekNumber.getDayOfMonth());
        	int weekNumberIndex = (int) Math.ceil((double) (currentDatetoFindActiveWeekNumber.getDayOfMonth() - dayNumber + 1) / 7);
        	System.out.println(weekNumberIndex);
        	return weekNumbers.get(weekNumberIndex);
        }
    }
	
	public static List<Integer> getDayListCalendar(String calenderSelectedMonth, int calenderSelectedYear) {
		 // Get the month name, year, and week number as strings
	    List<Integer> dayList = new ArrayList<>();

        // Parse the month name to a LocalDate object
        LocalDate firstDayOfMonth = LocalDate.parse("01 " + calenderSelectedMonth + " " + calenderSelectedYear,
                DateTimeFormatter.ofPattern("dd MMMM yyyy"));

        // Determine the number of days in the month
        int daysInMonth = firstDayOfMonth.lengthOfMonth();

        // Populate the list with day numbers
        for (int day = 1; day <= daysInMonth; day++) {
            dayList.add(day);
        }

        return dayList;
    }
	
	public static Integer getFirstDayNumberCalendar(String calenderSelectedMonth, int calenderSelectedYear) {
		//Parse month name to Month enum
        Month month = Month.valueOf(calenderSelectedMonth.toUpperCase());

        // Create a LocalDate for the first day of the month
        LocalDate firstDayOfMonthCalendar = LocalDate.of(calenderSelectedYear, month, 1);

        // Get the DayOfWeek for the first day
        DayOfWeek firstDayOfWeek = firstDayOfMonthCalendar.getDayOfWeek();

        // Get the numeric value of the DayOfWeek (1 for Monday, 2 for Tuesday, and so on)
        int firstDayNumber = firstDayOfWeek.getValue();
        return firstDayNumber;
    }
	
	//To find week numbers list for the given month and year
	public static List<Integer> getWeekNumbersCalendar(String monthName, int year) {
        // Convert month name to month index
        int monthIndex = getMonthIndex(monthName);
        
        List<Integer> weekNumbers = new ArrayList<Integer>();
        if(monthIndex != 1) {
        	int noOfDaysPassed = 0;
            for(int i= 1; i<monthIndex; i++) {
            	noOfDaysPassed = noOfDaysPassed + YearMonth.of(year, i).lengthOfMonth();
            }
            
            
            LocalDate firstDayOfJanuary = LocalDate.of(year, 1, 1);

            // Find the first Monday of the month
            LocalDate firstMondayOfJanuary = firstDayOfJanuary;
            while (firstMondayOfJanuary.getDayOfWeek() != DayOfWeek.MONDAY) {
            	firstMondayOfJanuary = firstMondayOfJanuary.plusDays(1);
            }

            // Calculate the day number (1 to 31) for the first Monday
            int dayNumberJanuary = firstMondayOfJanuary.getDayOfMonth();
            int noOfDaysPassedFrom1stMondayStart = noOfDaysPassed -(dayNumberJanuary-1) + 1;
            int noOfDaysPassedSelectedMonth = YearMonth.of(year, monthIndex).lengthOfMonth();
            int noOfDaysPassedFrom1stMondayEnd = noOfDaysPassedFrom1stMondayStart + noOfDaysPassedSelectedMonth - 1;
            
            int weekNumbersStart = (int) Math.ceil((double) noOfDaysPassedFrom1stMondayStart / 7);
            int weekNumbersEnd = (int) Math.ceil((double) noOfDaysPassedFrom1stMondayEnd / 7);
            
            
             for(int j = weekNumbersStart; j<=weekNumbersEnd; j++) {
            	 weekNumbers.add(j);
             }
            
            return weekNumbers;
        }else {
        	// Create a LocalDate for the first day of January in the given year
            LocalDate firstDayOfJanuary = LocalDate.of(year, 1, 1);

            // Check if the first day of January is a Monday
            Boolean isModayFirstDayOfJanuary =  firstDayOfJanuary.getDayOfWeek() == DayOfWeek.MONDAY;
            
            if(isModayFirstDayOfJanuary) {
            	weekNumbers.add(1);
            	weekNumbers.add(2);
            	weekNumbers.add(3);
            	weekNumbers.add(4);
            	weekNumbers.add(5);
            	return weekNumbers;
            }else {
            	
            	//calculation for december last week Number
            	int noOfDaysPassed = 0;
                for(int i= 1; i<12; i++) {
                	noOfDaysPassed = noOfDaysPassed + YearMonth.of(year - 1, i).lengthOfMonth();
                }
                
                
                LocalDate firstDayOfJanuary1 = LocalDate.of(year - 1, 1, 1);

                // Find the first Monday of the month
                LocalDate firstMondayOfJanuary = firstDayOfJanuary1;
                while (firstMondayOfJanuary.getDayOfWeek() != DayOfWeek.MONDAY) {
                	firstMondayOfJanuary = firstMondayOfJanuary.plusDays(1);
                }

                // Calculate the day number (1 to 31) for the first Monday
                int dayNumberJanuary = firstMondayOfJanuary.getDayOfMonth();
                int noOfDaysPassedFrom1stMondayStart = noOfDaysPassed -(dayNumberJanuary-1) + 1;
                int noOfDaysPassedSelectedMonth = YearMonth.of(year - 1, 12).lengthOfMonth();
                int noOfDaysPassedFrom1stMondayEnd = noOfDaysPassedFrom1stMondayStart + noOfDaysPassedSelectedMonth - 1;
                
                int weekNumbersStart = (int) Math.ceil((double) noOfDaysPassedFrom1stMondayStart / 7);
                int weekNumbersEnd = (int) Math.ceil((double) noOfDaysPassedFrom1stMondayEnd / 7);
                
              //calculation for january last week Number
                LocalDate firstDayOfJanuary2 = LocalDate.of(year, 1, 1);

                // Find the first Monday of the month
                LocalDate firstMondayOfJanuary2 = firstDayOfJanuary2;
                while (firstMondayOfJanuary2.getDayOfWeek() != DayOfWeek.MONDAY) {
                	firstMondayOfJanuary2 = firstMondayOfJanuary2.plusDays(1);
                }

                // Calculate the day number (1 to 31) for the first Monday
                int dayNumberJanuary2 = firstMondayOfJanuary2.getDayOfMonth();
                int noOfDaysFullWeekJanuary = 31 - dayNumberJanuary2 + 1;
                int noOfFullWeekJanuary = (int) Math.ceil((double) noOfDaysFullWeekJanuary / 7);
                weekNumbers.add(weekNumbersEnd);
                for(int k = 1; k<=noOfFullWeekJanuary; k++) {
                	weekNumbers.add(k);
                }
                return weekNumbers;
            }
        }
        
    }
	
	//PREVIOUS CODE FOR GETTING WEEK NUMBERS
	/*public static List<Integer> getWeekNumbersCalendar(String monthName, int year) {
        // Convert month name to month index
        int monthIndex = getMonthIndex(monthName);

        // Create a LocalDate for the first day of the month
        LocalDate firstDayOfMonth = LocalDate.of(year, monthIndex, 1);

        // Get the week fields based on the default locale
        WeekFields weekFields = WeekFields.of(Locale.getDefault()).ISO;

        // Initialize the list to store week numbers
        List<Integer> weekNumbers = new ArrayList<>();

        // Iterate through the days of the month and collect week numbers
        for (int day = 1; day <= YearMonth.of(year, monthIndex).lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(year, monthIndex, day);
            int weekNumber = date.get(weekFields.weekOfWeekBasedYear());

            // Add the week number to the list if it's not already present
            if (!weekNumbers.contains(weekNumber)) {
                weekNumbers.add(weekNumber);
            }
        }

        return weekNumbers;
    }*/
	
	//To get month index from month name
    private static int getMonthIndex(String monthName) {
        // Convert month name to month index
        String[] months = new DateFormatSymbols(Locale.getDefault()).getMonths();
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(monthName)) {
                // Month index is 0-based in DateFormatSymbols, while YearMonth is 1-based
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Invalid month name: " + monthName);
    }
    
    public static int convertToMinutes(String timeString) {
    	if(timeString == "") {
    		return 0;
    	}
        try {
            String[] parts = timeString.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            
            return hours * 60 + minutes;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle the exception based on your requirements
            e.printStackTrace(); // or log the error
            return 0; // Default value or throw an exception
        }
    }
    
    public static List<Object> convertToHHMM(List<Object> minutesList) {
        List<Object> hhmmList = new ArrayList<>();

        for (Object minutesObject : minutesList) {
            try {
                double totalMinutes = Double.parseDouble(minutesObject.toString());
                int hours = (int) totalMinutes / 60;
                int minutes = (int) totalMinutes % 60;
                String hhmmString = String.format("%02d:%02d", hours, minutes);
                hhmmList.add(hhmmString);
            } catch (NumberFormatException e) {
                // Handle the exception based on your requirements
                e.printStackTrace(); // or log the error
                hhmmList.add(""); // Default value or throw an exception
            }
        }

        return hhmmList;
    }
    
    //Finding previous month and year of given month and year
    public static String[] getPreviousMonthAndYear(String monthName, int year) {
        // Parse the month name to get the corresponding Month enum
        Month currentMonth = Month.valueOf(monthName.toUpperCase());

        // Calculate the previous month
        Month previousMonth = currentMonth.minus(1);
        
        // Calculate the year for the previous month
        int previousYear = (previousMonth == Month.DECEMBER) ? year - 1 : year;

        return new String[] { toTitleCase(previousMonth.toString()), String.valueOf(previousYear) };
    }
    
    //Finding next month and year of given month and year
    public static String[] getNextMonthAndYear(String monthName, int year) {
        // Parse the month name to get the corresponding Month enum
        Month currentMonth = Month.valueOf(monthName.toUpperCase());

        // Calculate the next month
        Month nextMonth = currentMonth.plus(1);

        // Calculate the year for the next month
        int nextYear = (nextMonth == Month.JANUARY) ? year + 1 : year;

        return new String[]{toTitleCase(nextMonth.toString()), String.valueOf(nextYear)};
    }
    
    public static String toTitleCase(String input) {
        // Check if the input is not null or empty
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Convert the first letter to uppercase and the rest to lowercase
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    //finding no of odd days in given month
    public static Integer getLastWeekDaysCount(String monthName, int year) {
        int monthIndex = getMonthIndex(monthName);
        int noOfDaysPassed = 0;
        for(int i= 1; i<=monthIndex; i++) {
        	noOfDaysPassed = noOfDaysPassed + YearMonth.of(year, i).lengthOfMonth();
        }
        
        
        LocalDate firstDayOfJanuary = LocalDate.of(year, 1, 1);

        // Find the first Monday of the month
        LocalDate firstMondayOfJanuary = firstDayOfJanuary;
        while (firstMondayOfJanuary.getDayOfWeek() != DayOfWeek.MONDAY) {
        	firstMondayOfJanuary = firstMondayOfJanuary.plusDays(1);
        }

        // Calculate the day number (1 to 31) for the first Monday
        int dayNumberJanuary = firstMondayOfJanuary.getDayOfMonth();
        int noOfDaysPassedFrom1stMondayStart = noOfDaysPassed -(dayNumberJanuary-1);
        return noOfDaysPassedFrom1stMondayStart%7;
    }
    
    //Converting string to List
    public static List<String> createListBySplitCommas(String input) {
    	
    	// If the input string is empty, return an empty list
        if (input.isEmpty()) {
            return new ArrayList<>();
        }
    	// Split the string by commas
        String[] parts = input.split(",");

        // Convert array to list
        List<String> resultList = new ArrayList<>();
        for (String part : parts) {
            resultList.add(part);
        }

        // Add empty string as last element if it's missing
        if (input.endsWith(",,")) {
            resultList.add("");
            resultList.add("");
        }
        else if (input.endsWith(",")) {
            resultList.add("");
        }
        return resultList;
    }
    
    
    // REPORT WORK
 	@RequestMapping("/reports")
 	public String reports(Model model) {

 		// R1 - Finding Employee details under GC/PL
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();

 		// finding group id
 		long groupIdByUserId = this.employeeMasterService.getGroupId(userId);
 		long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
 		
 		// finding employee Names and employee ids using group ids
 		List<String> empNamesUnderLoggedUser = new ArrayList<String>();
 		List<Long> empIdsUnderLoggedUser = new ArrayList<Long>();
 		if (roleIdOfLoggedUser == 5) {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null
 					? this.employeeRoleMstService.getProjectIds(userId)
 					: new ArrayList<>();
 			
 			List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId,
 					roleIdOfLoggedUser);
 			List<String> allEmployeeNamesUnderPL = new ArrayList<String>();
 			for (int i = 0; i < allEmployeeIdsUnderPL.size(); i++) {
 				String allEmployeeNameUnderPL = this.employeeMasterService.getEmpName(allEmployeeIdsUnderPL.get(i));
 				allEmployeeNamesUnderPL.add(allEmployeeNameUnderPL);
 			}
 			empNamesUnderLoggedUser = allEmployeeNamesUnderPL;
 			empIdsUnderLoggedUser = allEmployeeIdsUnderPL;
 		} else if (roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			List<GroupMasterModel> groupOrganisation = groupMasterService.getGroupMasterDomainByOrg(4);
 			model.addAttribute("groupOrganisation", groupOrganisation);
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);

 		} else {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		}
 		
 		model.addAttribute("EmpNames", empNamesUnderLoggedUser);
 		model.addAttribute("EmpIds", empIdsUnderLoggedUser);
 		model.addAttribute("roleIdOfLoggedUser", roleIdOfLoggedUser);
 		// making Range List
 		List<String> rangeList = new ArrayList<>();
 		// rangeList.add("Current Year");
 		rangeList.add("Last 1 Month");
 		rangeList.add("Last 3 Months");
 		rangeList.add("Last 6 Months");
 		rangeList.add("Last 12 Months");
 		rangeList.add("Last 24 Months");
 		model.addAttribute("rangeList", rangeList);
 		// finding current year for reporting
 		LocalDate currentDate = LocalDate.now();
 		int currentYear = currentDate.getYear();
 		model.addAttribute("yearToShow", currentYear);
 		// making year List for user wise
 		List<Integer> comboYear = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != currentYear) {
 				comboYear.add(i);
 			}
 		}
 		model.addAttribute("comboYear", comboYear);

 		// making year List for group wise
 		List<Integer> comboYearGroup = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != currentYear) {
 				comboYearGroup.add(i);
 			}
 		}
 		model.addAttribute("comboYearGroup", comboYearGroup);

 		model.addAttribute("currentYear", currentYear);

 		// R10 - GROUP MONTH WISE REPORT (PROJECT + ACTIVITY)
 		ProjectWeekEffortTransactionModelId projectActivityweekEffortransactionModelId = new ProjectWeekEffortTransactionModelId();
 		// dateUseProjectModelId1.setEmpId((int)selectedEmpId);
 		ParentTransactionModel parenttransactionModel = new ParentTransactionModel();
 		parenttransactionModel.setYearName(String.valueOf(currentYear));
 		projectActivityweekEffortransactionModelId.setParentTransactionModel(parenttransactionModel);
 		projectActivityweekEffortransactionModelId.setStatus("p");

 		List<Object> userProjectsMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectActivityweekEffortransactionModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffortNotGC(projectActivityweekEffortransactionModelId,
 							groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectActivityweekEffortransactionModelId, groupIdByUserId);
 		}
 		// List<Object> userProjectsMonthlyReport =
 		// this.dateUseProjectService.reportGroupProjectActivityMonthEffort(dateUseProjectGroupProjectActivityModelId,groupIdByUserId);
 		projectActivityweekEffortransactionModelId.setStatus("m");

 		List<Object> userActivitysMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectActivityweekEffortransactionModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffortNotGC(projectActivityweekEffortransactionModelId,
 							groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectActivityweekEffortransactionModelId, groupIdByUserId);
 		}
 		

 		List<MonthEffort> projectMonthsEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthsEfforts = new ArrayList<>();

 		for (Object result : userProjectsMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivitysMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValuesProject = new ArrayList<>();
 		List<Object> yValuesProject = new ArrayList<>();
 		List<Object> xValuesActivity = new ArrayList<>();
 		List<Object> yValuesActivity = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthsEfforts) {
 			xValuesProject.add(monthEffort.getMonth());
 			yValuesProject.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthsEfforts) {
 			xValuesActivity.add(monthEffort.getMonth());
 			yValuesActivity.add(monthEffort.getEffort());
 		}

 		System.out.println("xValuesProject " + xValuesProject);
 		System.out.println("yValuesProject " + yValuesProject);
 		System.out.println("xValuesActivity " + xValuesActivity);
 		System.out.println("yValuesActivity " + yValuesActivity);
 		model.addAttribute("xValuesProject", xValuesProject);
 		model.addAttribute("yValuesProject", yValuesProject);
 		model.addAttribute("xValuesActivity", xValuesActivity);
 		model.addAttribute("yValuesActivity", yValuesActivity);
 		// for R11 and R12
 		List<String> monthNameList = new ArrayList<String>();
 		// Loop through each month and add its name to the list
 		for (int i = 1; i <= 12; i++) {
 			Month month = Month.of(i);
 			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
 			monthNameList.add(monthName);
 		}
 		model.addAttribute("monthNameList", monthNameList);
 		return "reports";
 	}

 	// =============================================================================================================================
 	
 	//R9 - LOGGED IN USER PROJECT WISE(Pie) REPORT (Month filter)
 	@RequestMapping(value = "/getMonthWiseProjectPie", method = RequestMethod.POST)
 	public @ResponseBody Map<String, List<Object>> getMonthWiseProjectPie(
 			@RequestParam("selectedYear") String selectedYear, @RequestParam("selectedMonth") String selectedMonth,
 			@RequestParam("selectedEmpIdName") String selectedEmpIdName, HttpServletRequest request) {

 		ProjectWeekEffortTransactionModelId projectWeekEffortModelIdPie = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parenttransactionModel = new ParentTransactionModel();
 		long selectedEmpId = Long.parseLong(selectedEmpIdName.split("\\+")[0]);
 		parenttransactionModel.setEmpId((int) selectedEmpId);
 		parenttransactionModel.setYearName(String.valueOf(selectedYear));
 		parenttransactionModel.setMonthName(String.valueOf(selectedMonth));
 		projectWeekEffortModelIdPie.setParentTransactionModel(parenttransactionModel);
 		List<Object> userMonthProjectWiseReportPie = this.projectWeekEffortTransactionService
 				.reportUserProjectWiseEffortPie(projectWeekEffortModelIdPie);

 		List<Object> xValuesMonth = new ArrayList<>();
 		List<Object> yValuesMonth = new ArrayList<>();

 		for (Object result : userMonthProjectWiseReportPie) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;
 				if (row.length >= 2) {
 					if (row[2] == null) {
 						xValuesMonth.add(row[0]);
 					} else {
 						xValuesMonth.add(row[2] + " - " + row[0]);
 					}
 					yValuesMonth.add(row[1]);
 				}
 			}
 		}

 		Map<String, List<Object>> result = new HashMap<>();
 		// Create a copy of the indices to be removed
 		List<Integer> indicesToRemove = new ArrayList<>();
 		for (int i = 0; i < yValuesMonth.size(); i++) {
 		    if (yValuesMonth.get(i) instanceof Double && ((Double) yValuesMonth.get(i)).equals(0.0)) {
 		        indicesToRemove.add(i);
 		    }
 		}

 		// Remove elements from both lists using the indices
 		for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
 		    int indexToRemove = indicesToRemove.get(i);
 		    yValuesMonth.remove(indexToRemove);
 		    xValuesMonth.remove(indexToRemove);
 		}
 		result.put("xValuesMonth", xValuesMonth);
 		result.put("yValuesMonth", yValuesMonth);

 		return result;
 	}

 	// R4 - LOGGED IN USER ACTIVITY WISE(Pie) REPORT (Month filter)
 	@RequestMapping(value = "/getMonthWiseActivityPie", method = RequestMethod.POST)
 	public @ResponseBody Map<String, List<Object>> getMonthWiseActivityPie(
 			@RequestParam("selectedYear") String selectedYear, @RequestParam("selectedMonth") String selectedMonth,
 			@RequestParam("selectedEmpIdName") String selectedEmpIdName, HttpServletRequest request) {
 		ProjectWeekEffortTransactionModelId projectweekeffortModelIdMisc = new ProjectWeekEffortTransactionModelId();
 		long selectedEmpId = Long.parseLong(selectedEmpIdName.split("\\+")[0]);
 		ParentTransactionModel parenttransactionModel = new ParentTransactionModel();
 		parenttransactionModel.setEmpId((int) selectedEmpId);
 		parenttransactionModel.setYearName(String.valueOf(selectedYear));
 		parenttransactionModel.setMonthName(String.valueOf(selectedMonth));
 		projectweekeffortModelIdMisc.setParentTransactionModel(parenttransactionModel);
 		List<Object> userMonthActivityWiseReportPie = this.projectWeekEffortTransactionService
 				.reportUserActivityWiseEffortPie(projectweekeffortModelIdMisc);
 		List<Object> xValuesMonthsAct = new ArrayList<>();
 		List<Object> yValuesMonthsAct = new ArrayList<>();

 		for (Object result : userMonthActivityWiseReportPie) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					xValuesMonthsAct.add(row[0]);
 					yValuesMonthsAct.add(row[1]);
 				}
 			}
 		}

 		Map<String, List<Object>> result = new HashMap<>();
 		// Create a copy of the indices to be removed
 		List<Integer> indicesToRemove = new ArrayList<>();
 		for (int i = 0; i < yValuesMonthsAct.size(); i++) {
 		    if (yValuesMonthsAct.get(i) instanceof Double && ((Double) yValuesMonthsAct.get(i)).equals(0.0)) {
 		        indicesToRemove.add(i);
 		    }
 		}

 		// Remove elements from both lists using the indices
 		for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
 		    int indexToRemove = indicesToRemove.get(i);
 		    yValuesMonthsAct.remove(indexToRemove);
 		    xValuesMonthsAct.remove(indexToRemove);
 		}
 		result.put("xValuesMonthsAct", xValuesMonthsAct);
 		result.put("yValuesMonthsAct", yValuesMonthsAct);
 		return result;
 	}

 	// =============================================================================================================================

 	@RequestMapping("/employeeSelectReport")
 	public String employeeSelectReport(@RequestParam String selectedEmpIdName, @RequestParam Integer selectedYear, Model model) {

 		// Print or use the selectedEmpId as needed
 		System.out.println("Selected Employee ID: " + selectedEmpIdName);
 		System.out.println("Selected Year: " + selectedYear);
 		

 		// Extract number as long
 		long selectedEmpId = Long.parseLong(selectedEmpIdName.split("\\+")[0]);

 		// Extract text as string
 		String selectedEmpName = selectedEmpIdName.split("\\+")[1];
 		model.addAttribute("selectedEmpId", selectedEmpId);
 		model.addAttribute("selectedEmpName", selectedEmpName);

 		// Finding user id
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();

 		// finding group id
 		long groupIdByUserId = this.employeeMasterService.getGroupId(userId);

 		// R1 - Finding Employee details under GC/PL
 		// finding employee Names and employee ids using group ids
 		long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
 	
 		List<String> empNamesUnderLoggedUser = new ArrayList<String>();
 		List<Long> empIdsUnderLoggedUser = new ArrayList<Long>();
 		if (roleIdOfLoggedUser == 5) {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null
 					? this.employeeRoleMstService.getProjectIds(userId)
 					: new ArrayList<>();
 			System.out.println("projectIdsByUserId----" + projectIdsByUserId);
 			List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId,
 					roleIdOfLoggedUser);

 			List<String> allEmployeeNamesUnderPL = new ArrayList<String>();
 			for (int i = 0; i < allEmployeeIdsUnderPL.size(); i++) {
 				String allEmployeeNameUnderPL = this.employeeMasterService.getEmpName(allEmployeeIdsUnderPL.get(i));
 				allEmployeeNamesUnderPL.add(allEmployeeNameUnderPL);
 			}
 			empNamesUnderLoggedUser = allEmployeeNamesUnderPL;
 			empIdsUnderLoggedUser = allEmployeeIdsUnderPL;
 		} else if (roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			List<GroupMasterModel> groupOrganisation = groupMasterService.getGroupMasterDomainByOrg(4);
 			model.addAttribute("groupOrganisation", groupOrganisation);
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);

 		} else {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		}
 		
 		empNamesUnderLoggedUser.remove(selectedEmpName);
 		empIdsUnderLoggedUser.remove(selectedEmpId);
 		model.addAttribute("EmpNames", empNamesUnderLoggedUser);
 		model.addAttribute("EmpIds", empIdsUnderLoggedUser);
 		model.addAttribute("roleIdOfLoggedUser", roleIdOfLoggedUser);
 		
 		// finding current year for reporting
 		LocalDate currentDate = LocalDate.now();
 		int currentYear = currentDate.getYear();
 		model.addAttribute("yearToShow", selectedYear);
 		// making year List for user wise
 		List<Integer> comboYear = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != selectedYear) {
 				comboYear.add(i);
 			}
 		}
 		model.addAttribute("comboYear", comboYear);

 		// making year List for user wise
 		List<Integer> comboYearGroup = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != selectedYear) {
 				comboYearGroup.add(i);
 			}
 		}
 		model.addAttribute("comboYearGroup", comboYearGroup);
 		// model.addAttribute("currentYear", currentYear);
 		model.addAttribute("selectedYear", selectedYear);

 		// R3 - LOGGED IN USER MONTH WISE REPORT
 		ProjectWeekEffortTransactionModelId projectWeekUserEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parenttransactionuserModel = new ParentTransactionModel();
 		parenttransactionuserModel.setEmpId((int) selectedEmpId);
 		parenttransactionuserModel.setYearName(String.valueOf(selectedYear));
 		projectWeekUserEffortTransactionModelId.setStatus("p");
 		projectWeekUserEffortTransactionModelId.setParentTransactionModel(parenttransactionuserModel);
 		List<Object> userProjectMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectWeekUserEffortTransactionModelId);
 		projectWeekUserEffortTransactionModelId.setStatus("m");
 		List<Object> userActivityMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectWeekUserEffortTransactionModelId);

 		// Create a list of MonthEffort objects
 		List<MonthEffort> projectMonthEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthEfforts = new ArrayList<>();

 		for (Object result : userProjectMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivityMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValues1 = new ArrayList<>();
 		List<Object> yValues1 = new ArrayList<>();
 		List<Object> xValues1_1 = new ArrayList<>();
 		List<Object> yValues1_1 = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthEfforts) {
 			xValues1.add(monthEffort.getMonth());
 			yValues1.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthEfforts) {
 			xValues1_1.add(monthEffort.getMonth());
 			yValues1_1.add(monthEffort.getEffort());
 		}

 		System.out.println("xValues1 " + xValues1);
 		System.out.println("yValues1 " + yValues1);

 		model.addAttribute("xValues1", xValues1);
 		model.addAttribute("yValues1", yValues1);
 		model.addAttribute("xValues1_1", xValues1_1);
 		model.addAttribute("yValues1_1", yValues1_1);
 		
 		/*
 		 * // R8 - GROUP PROJECT WISE REPORT List<Integer> groupProjectWiseReport =
 		 * everyProjectGroupMonthWiseReport.stream() .map(list ->
 		 * list.stream().mapToInt(Integer::intValue).sum()).collect(Collectors.toList())
 		 * ; System.out.println("groupProjectWiseReport" + groupProjectWiseReport);
 		 * model.addAttribute("groupProjectWiseReport", groupProjectWiseReport);
 		 */

 	

 		List<String> monthNameList = new ArrayList<>();
 		// for R9,R4,R11,R12
 		// Loop through each month and add its name to the list
 		for (int i = 1; i <= 12; i++) {
 			Month month = Month.of(i);
 			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
 			monthNameList.add(monthName);
 		}
 		model.addAttribute("monthNameList", monthNameList);

 		// R10 - GROUP MONTH WISE REPORT (PROJECT + ACTIVITY)
 		ProjectWeekEffortTransactionModelId projectGroupProjectActivityModelId = new ProjectWeekEffortTransactionModelId();
 		// dateUseProjectModelId1.setEmpId((int)selectedEmpId);
 		ParentTransactionModel parentransactionsModel = new ParentTransactionModel();
 		parentransactionsModel.setYearName(String.valueOf(selectedYear));
 		projectGroupProjectActivityModelId.setStatus("p");
 		projectGroupProjectActivityModelId.setParentTransactionModel(parentransactionsModel);
 		List<Object> userProjectsMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectGroupProjectActivityModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService.reportGroupProjectActivityMonthEffortNotGC(
 					projectGroupProjectActivityModelId, groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectGroupProjectActivityModelId, groupIdByUserId);
 		}
 		
 		projectGroupProjectActivityModelId.setStatus("m");

 		List<Object> userActivitysMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectGroupProjectActivityModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService.reportGroupProjectActivityMonthEffortNotGC(
 					projectGroupProjectActivityModelId, groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(projectGroupProjectActivityModelId, groupIdByUserId);
 		}
 	

 		List<MonthEffort> projectMonthsEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthsEfforts = new ArrayList<>();

 		for (Object result : userProjectsMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivitysMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValuesProject = new ArrayList<>();
 		List<Object> yValuesProject = new ArrayList<>();
 		List<Object> xValuesActivity = new ArrayList<>();
 		List<Object> yValuesActivity = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthsEfforts) {
 			xValuesProject.add(monthEffort.getMonth());
 			yValuesProject.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthsEfforts) {
 			xValuesActivity.add(monthEffort.getMonth());
 			yValuesActivity.add(monthEffort.getEffort());
 		}

 	
 		model.addAttribute("xValuesProject", xValuesProject);
 		model.addAttribute("yValuesProject", yValuesProject);
 		model.addAttribute("xValuesActivity", xValuesActivity);
 		model.addAttribute("yValuesActivity", yValuesActivity);
 		return "reports";
 	}



 	@RequestMapping("/selectGroupYear")
 	public String selectGroupYear(
 			/* @PathVariable("reportYear") String reportYear, */ @RequestParam Integer selectGroupYear,
 			@RequestParam String groupSelection, Model model) {

 		// R1 - Finding Employee details under GC/PL
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();

 		// finding group id
 		// long groupIdByUserId = this.employeeMasterService.getGroupId(userId);
 		long groupIdByUserId = 0;
 		long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
 		if (groupSelection != null && !groupSelection.isEmpty()) {
 			groupIdByUserId = Long.parseLong(groupSelection);
 		} else {
 			groupIdByUserId = userInfo.getGroupId();
 		}
 		// long roleIdOfLoggedUser =
 		// this.employeeRoleMstService.getRoleIdByUserId(userId);
 		// finding employee Names and employee ids using group ids
 		List<String> empNamesUnderLoggedUser = new ArrayList<String>();
 		List<Long> empIdsUnderLoggedUser = new ArrayList<Long>();
 		if (roleIdOfLoggedUser == 5) {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null
 					? this.employeeRoleMstService.getProjectIds(userId)
 					: new ArrayList<>();
 			System.out.println("projectIdsByUserId----" + projectIdsByUserId);
 			List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId,
 					roleIdOfLoggedUser);

 			List<String> allEmployeeNamesUnderPL = new ArrayList<String>();
 			for (int i = 0; i < allEmployeeIdsUnderPL.size(); i++) {
 				String allEmployeeNameUnderPL = this.employeeMasterService.getEmpName(allEmployeeIdsUnderPL.get(i));
 				allEmployeeNamesUnderPL.add(allEmployeeNameUnderPL);
 			}
 			empNamesUnderLoggedUser = allEmployeeNamesUnderPL;
 			empIdsUnderLoggedUser = allEmployeeIdsUnderPL;
 		} else if (roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			List<GroupMasterModel> groupOrganisation = groupMasterService.getGroupMasterDomainByOrg(4);
 			model.addAttribute("groupOrganisation", groupOrganisation);
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);

 		} else {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupIdByUserId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupIdByUserId);
 		}
 		
 		
 		model.addAttribute("EmpNames", empNamesUnderLoggedUser);
 		model.addAttribute("EmpIds", empIdsUnderLoggedUser);
 		model.addAttribute("roleIdOfLoggedUser", roleIdOfLoggedUser);
 		model.addAttribute("groupSelection", groupSelection);
 		// making Range List
 		List<String> rangeList = new ArrayList<>();
 		// rangeList.add("Current Year");
 		rangeList.add("Last 1 Month");
 		rangeList.add("Last 3 Months");
 		rangeList.add("Last 6 Months");
 		rangeList.add("Last 12 Months");
 		rangeList.add("Last 24 Months");
 		model.addAttribute("rangeList", rangeList);
 		// finding current year for reporting
 		LocalDate currentDate = LocalDate.now();
 		int currentYear = currentDate.getYear();

 		model.addAttribute("yearToShow", selectGroupYear);
 		// making year List for user wise
 		List<Integer> comboYear = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != selectGroupYear) {
 				comboYear.add(i);
 			}
 		}
 		model.addAttribute("comboYear", comboYear);
 		// making year List for group wise
 		List<Integer> comboYearGroup = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != selectGroupYear) {
 				comboYearGroup.add(i);
 			}
 		}
 		model.addAttribute("comboYearGroup", comboYearGroup);
 		// model.addAttribute("currentYear", currentYear);
 		model.addAttribute("selectedYear", selectGroupYear);
 		
 		

 		

 		// Is Submit
 		String isGroupFormSubmit = "true";
 		model.addAttribute("isGroupFormSubmitRe", isGroupFormSubmit);
 		// R10 - GROUP MONTH WISE REPORT (PROJECT + ACTIVITY)
 		ProjectWeekEffortTransactionModelId ProjectWeekgroupsEffortTransactionModelId = new ProjectWeekEffortTransactionModelId();
 		// dateUseProjectModelId1.setEmpId((int)selectedEmpId);
 		
 		ParentTransactionModel parentransactiongroupModel = new ParentTransactionModel();
 		parentransactiongroupModel.setYearName(String.valueOf(selectGroupYear));
 		ProjectWeekgroupsEffortTransactionModelId.setStatus("p");
 		ProjectWeekgroupsEffortTransactionModelId.setParentTransactionModel(parentransactiongroupModel);
 		List<Object> userProjectsMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService.reportGroupProjectActivityMonthEffortNotGC(
 					ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userProjectsMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId);
 		}
 		// List<Object> userProjectsMonthlyReport =
 		// this.dateUseProjectService.reportGroupProjectActivityMonthEffort(dateUseProjectGroupProjectActivityModelId,groupIdByUserId);
 		ProjectWeekgroupsEffortTransactionModelId.setStatus("m");

 		List<Object> userActivitysMonthlyReport = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService.reportGroupProjectActivityMonthEffortNotGC(
 					ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId, empIdsUnderLoggedUser);
 		} else {
 			userActivitysMonthlyReport = this.projectWeekEffortTransactionService
 					.reportGroupProjectActivityMonthEffort(ProjectWeekgroupsEffortTransactionModelId, groupIdByUserId);
 		}
 		// List<Object> userActivitysMonthlyReport =
 		// this.dateUseProjectService.reportGroupProjectActivityMonthEffort(dateUseProjectGroupProjectActivityModelId,groupIdByUserId);

 		List<MonthEffort> projectMonthsEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthsEfforts = new ArrayList<>();

 		for (Object result : userProjectsMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivitysMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthsEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthsEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValuesProject = new ArrayList<>();
 		List<Object> yValuesProject = new ArrayList<>();
 		List<Object> xValuesActivity = new ArrayList<>();
 		List<Object> yValuesActivity = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthsEfforts) {
 			xValuesProject.add(monthEffort.getMonth());
 			yValuesProject.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthsEfforts) {
 			xValuesActivity.add(monthEffort.getMonth());
 			yValuesActivity.add(monthEffort.getEffort());
 		}

 		
 		model.addAttribute("xValuesProject", xValuesProject);
 		model.addAttribute("yValuesProject", yValuesProject);
 		model.addAttribute("xValuesActivity", xValuesActivity);
 		model.addAttribute("yValuesActivity", yValuesActivity);

 		// for R11 and R12
 		List<String> monthNameList = new ArrayList<String>();
 		// Loop through each month and add its name to the list
 		for (int i = 1; i <= 12; i++) {
 			Month month = Month.of(i);
 			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
 			monthNameList.add(monthName);
 		}
 		model.addAttribute("monthNameList", monthNameList);
 		return "reports";
 	}

 	// =====================================================================================================
 	// R11 - GROUP PROJECT-MONTH WISE PIE CHART
 	@RequestMapping(value = "/getMonthWiseProjectGroupPie", method = RequestMethod.POST)
 	public @ResponseBody Map<String, List<Object>> getMonthWiseProjectGroupPie(
 			@RequestParam("selectGroupYear") String selectedYear,
 			@RequestParam("selectedMonthGroup") String selectedMonth,
 			@RequestParam(value = "groupSelection", required = false) String groupSelection,
 			HttpServletRequest request) {
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();
 		long groupId;
 		if (groupSelection != null && !groupSelection.isEmpty()) {
 			groupId = Long.parseLong(groupSelection);
 		} else {
 			groupId = userInfo.getGroupId();
 		}
 		long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
 		// long roleIdOfLoggedUser =
 		// this.employeeRoleMstService.getRoleIdByUserId(userId);
 		List<String> empNamesUnderLoggedUser = new ArrayList<String>();
 		List<Long> empIdsUnderLoggedUser = new ArrayList<Long>();
 		if (roleIdOfLoggedUser == 5 || roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null
 					? this.employeeRoleMstService.getProjectIds(userId)
 					: new ArrayList<>();

 			List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId,
 					roleIdOfLoggedUser);

 			List<String> allEmployeeNamesUnderPL = new ArrayList<String>();
 			for (int i = 0; i < allEmployeeIdsUnderPL.size(); i++) {
 				String allEmployeeNameUnderPL = this.employeeMasterService.getEmpName(allEmployeeIdsUnderPL.get(i));
 				allEmployeeNamesUnderPL.add(allEmployeeNameUnderPL);
 			}
 			empNamesUnderLoggedUser = allEmployeeNamesUnderPL;
 			empIdsUnderLoggedUser = allEmployeeIdsUnderPL;
 		} else {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupId);
 		}

 		ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parengrouptransactiongroupModelpie = new ParentTransactionModel();
 		
 		parengrouptransactiongroupModelpie.setYearName(String.valueOf(selectedYear));
 		parengrouptransactiongroupModelpie.setMonthName(String.valueOf(selectedMonth));
 		projectgroupWeeksEffortTransactionModelpie.setParentTransactionModel(parengrouptransactiongroupModelpie);
 		List<Object> userMonthProjectGroupWiseReportPie = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5 || roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			userMonthProjectGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthProjectGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie, groupId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userMonthProjectGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthProjectGroupWiseEffortNotGC(projectgroupWeeksEffortTransactionModelpie, empIdsUnderLoggedUser);
 		} else {
 			userMonthProjectGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthProjectGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie, groupId);
 		}
 		// List<Object> userMonthProjectGroupWiseReportPie =
 		// this.dateUseProjectService.reportMonthProjectGroupWiseEffort(dateUseProjectGroupModelIdPie,groupId);

 		List<Object> xValuesGroupMonth = new ArrayList<>();
 		List<Object> yValuesGroupMonth = new ArrayList<>();

 		for (Object result : userMonthProjectGroupWiseReportPie) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;
 				if (row.length >= 2) {
 					if (row[2] == null) {
 						xValuesGroupMonth.add(row[0]);
 					} else {
 						xValuesGroupMonth.add(row[2] + " - " + row[0]);
 					}
 					yValuesGroupMonth.add(row[1]);
 				}
 			}
 		}

 		Map<String, List<Object>> result = new HashMap<>();
 		// Create a copy of the indices to be removed
 		List<Integer> indicesToRemove = new ArrayList<>();
 		for (int i = 0; i < yValuesGroupMonth.size(); i++) {
 		    if (yValuesGroupMonth.get(i) instanceof Double && ((Double) yValuesGroupMonth.get(i)).equals(0.0)) {
 		        indicesToRemove.add(i);
 		    }
 		}

 		// Remove elements from both lists using the indices
 		for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
 		    int indexToRemove = indicesToRemove.get(i);
 		    yValuesGroupMonth.remove(indexToRemove);
 		    xValuesGroupMonth.remove(indexToRemove);
 		}
 		result.put("xValuesGroupMonth", xValuesGroupMonth);
 		result.put("yValuesGroupMonth", yValuesGroupMonth);
 		return result;
 	}

 	// R12 - GROUP ACTIVITY-MONTH WISE PIE CHART
 	@RequestMapping(value = "/getMonthWiseActivityGroupPie", method = RequestMethod.POST)
 	public @ResponseBody Map<String, List<Object>> getMonthWiseActivityGroupPie(
 			@RequestParam("selectGroupYear") String selectedYear,
 			@RequestParam("selectedMonthGroup") String selectedMonth,
 			@RequestParam(value = "groupSelection", required = false) String groupSelection,

 			HttpServletRequest request) {
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();
 		long groupId;
 		if (groupSelection != null && !groupSelection.isEmpty()) {
 			groupId = Long.parseLong(groupSelection);
 		} else {
 			groupId = userInfo.getGroupId();
 		}

 		long roleIdOfLoggedUser = userInfo.getSelectedEmployeeRole().getNumRoleId();
 		// long roleIdOfLoggedUser =
 		// this.employeeRoleMstService.getRoleIdByUserId(userId);
 		List<String> empNamesUnderLoggedUser = new ArrayList<String>();
 		List<Long> empIdsUnderLoggedUser = new ArrayList<Long>();
 		if (roleIdOfLoggedUser == 5 || roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			List<Integer> projectIdsByUserId = this.employeeRoleMstService.getProjectIds(userId) != null
 					? this.employeeRoleMstService.getProjectIds(userId)
 					: new ArrayList<>();

 			List<Long> allEmployeeIdsUnderPL = this.employeeRoleMstService.getEmployeeIdByProjectId(projectIdsByUserId,
 					roleIdOfLoggedUser);

 			List<String> allEmployeeNamesUnderPL = new ArrayList<String>();
 			for (int i = 0; i < allEmployeeIdsUnderPL.size(); i++) {
 				String allEmployeeNameUnderPL = this.employeeMasterService.getEmpName(allEmployeeIdsUnderPL.get(i));
 				allEmployeeNamesUnderPL.add(allEmployeeNameUnderPL);
 			}
 			empNamesUnderLoggedUser = allEmployeeNamesUnderPL;
 			empIdsUnderLoggedUser = allEmployeeIdsUnderPL;
 		} else {
 			empNamesUnderLoggedUser = this.employeeMasterService.getEmpNamesByGroupId(groupId);
 			empIdsUnderLoggedUser = this.employeeMasterService.getEmpIdsByGroupId(groupId);
 		}

 		ProjectWeekEffortTransactionModelId projectgroupWeeksEffortTransactionModelpie = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parengrouptransactiongroupModelpie = new ParentTransactionModel();

 		parengrouptransactiongroupModelpie.setYearName(selectedYear);
 		parengrouptransactiongroupModelpie.setMonthName(selectedMonth);
 		
 		projectgroupWeeksEffortTransactionModelpie.setParentTransactionModel(parengrouptransactiongroupModelpie);

 		List<Object> userMonthActivityGroupWiseReportPie = new ArrayList<Object>();
 		if (roleIdOfLoggedUser == 5 || roleIdOfLoggedUser == 9 || roleIdOfLoggedUser == 6) {
 			userMonthActivityGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthActivityGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie, groupId);
 		} else if (roleIdOfLoggedUser == 3 || roleIdOfLoggedUser == 15 || roleIdOfLoggedUser == 4) {
 			userMonthActivityGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthActivityGroupWiseEffortNotGC(projectgroupWeeksEffortTransactionModelpie, empIdsUnderLoggedUser);
 		} else {
 			userMonthActivityGroupWiseReportPie = this.projectWeekEffortTransactionService
 					.reportMonthActivityGroupWiseEffort(projectgroupWeeksEffortTransactionModelpie, groupId);
 		}
 		

 		List<Object> xValuesActivityGroupMonth = new ArrayList<>();
 		List<Object> yValuesActivityGroupMonth = new ArrayList<>();

 		for (Object result : userMonthActivityGroupWiseReportPie) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;
 				if (row.length >= 2) {
 					xValuesActivityGroupMonth.add(row[0]);

 					yValuesActivityGroupMonth.add(row[1]);
 				}
 			}
 		}
 		Map<String, List<Object>> result = new HashMap<>();
 		// Create a copy of the indices to be removed
 		List<Integer> indicesToRemove = new ArrayList<>();
 		for (int i = 0; i < yValuesActivityGroupMonth.size(); i++) {
 		    if (yValuesActivityGroupMonth.get(i) instanceof Double && ((Double) yValuesActivityGroupMonth.get(i)).equals(0.0)) {
 		        indicesToRemove.add(i);
 		    }
 		}

 		// Remove elements from both lists using the indices
 		for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
 		    int indexToRemove = indicesToRemove.get(i);
 		    yValuesActivityGroupMonth.remove(indexToRemove);
 		    xValuesActivityGroupMonth.remove(indexToRemove);
 		}
 		result.put("xValuesActivityGroupMonth", xValuesActivityGroupMonth);
 		result.put("yValuesActivityGroupMonth", yValuesActivityGroupMonth);

 		return result;
 	}

 	// ====================================================================================================================================
 	@RequestMapping("/individualReports")
 	public String individualReports(Model model) {

 		// Finding user id
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();
 		String userName = userInfo.getEmployeeName();
 		model.addAttribute("userId", userId);
 		model.addAttribute("userName", userName);
 		// making Range List
 		List<String> rangeList = new ArrayList<>();
 		// rangeList.add("Current Year");
 		rangeList.add("Last 1 Month");
 		rangeList.add("Last 3 Months");
 		rangeList.add("Last 6 Months");
 		rangeList.add("Last 12 Months");
 		rangeList.add("Last 24 Months");
 		// ("Last 1 Month", "Last 3 Months", "Last 6 Months", "Last 1 Year", "Last 2
 		// Year"));
 		model.addAttribute("rangeList", rangeList);
 		/*
 		 * System.out.println("Filtered Names" +empNamesByGroupId);
 		 * System.out.println("Filtered Ids" +empIdsByGroupId);
 		 */
 		// finding current year for reporting
 		LocalDate currentDate = LocalDate.now();
 		int currentYear = currentDate.getYear();
 		// making year List for user wise
 		List<Integer> comboYear = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != currentYear) {
 				comboYear.add(i);
 			}
 		}
 		model.addAttribute("comboYear", comboYear);
 		model.addAttribute("currentYear", currentYear);
 		model.addAttribute("selectedYear", currentYear);
 		model.addAttribute("yearToShow", currentYear);
 		

 		// R3 - LOGGED IN USER MONTH WISE REPORT
 		ProjectWeekEffortTransactionModelId projectindividualEffortTransactionModel = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parengrouptransactionIndividualModelpie = new ParentTransactionModel();
 		parengrouptransactionIndividualModelpie.setEmpId(userId);
 		parengrouptransactionIndividualModelpie.setYearName(String.valueOf(currentYear));
 		projectindividualEffortTransactionModel.setStatus("p");
 		projectindividualEffortTransactionModel.setParentTransactionModel(parengrouptransactionIndividualModelpie);
 		List<Object> userProjectMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectindividualEffortTransactionModel);
 		projectindividualEffortTransactionModel.setStatus("m");
 		List<Object> userActivityMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectindividualEffortTransactionModel);
 		

 		// Create a list of MonthEffort objects
 		List<MonthEffort> projectMonthEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthEfforts = new ArrayList<>();
 		
 		for (Object result : userProjectMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivityMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValues1 = new ArrayList<>();
 		List<Object> yValues1 = new ArrayList<>();
 		List<Object> xValues1_1 = new ArrayList<>();
 		List<Object> yValues1_1 = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthEfforts) {
 			xValues1.add(monthEffort.getMonth());
 			yValues1.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthEfforts) {
 			xValues1_1.add(monthEffort.getMonth());
 			yValues1_1.add(monthEffort.getEffort());
 		}

 		System.out.println("xValues1 " + xValues1);
 		System.out.println("yValues1 " + yValues1);

 		model.addAttribute("xValues1", xValues1);
 		model.addAttribute("yValues1", yValues1);
 		model.addAttribute("xValues1_1", xValues1_1);
 		model.addAttribute("yValues1_1", yValues1_1);

 		

 		

 		List<String> monthNameList = new ArrayList<>();
 		// for R9 and R4
 		// Loop through each month and add its name to the list
 		for (int i = 1; i <= 12; i++) {
 			Month month = Month.of(i);
 			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
 			monthNameList.add(monthName);
 		}
 		model.addAttribute("monthNameList", monthNameList);
 		return "individualReports";
 	}

 	@RequestMapping("/individualYearReports")
 	public String individualYearReports(@RequestParam Integer selectedYear,
 			Model model) {

 		// Finding user id
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

 		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
 		int userId = userInfo.getEmployeeId();
 		String userName = userInfo.getEmployeeName();
 		model.addAttribute("userId", userId);
 		model.addAttribute("userName", userName);

 		System.out.println("Selected Year: " + selectedYear);
 		// finding current year for reporting
 		LocalDate currentDate = LocalDate.now();
 		int currentYear = currentDate.getYear();
 		// making year List for user wise
 		List<Integer> comboYear = new ArrayList<Integer>();
 		for (int i = 2023; i <= currentYear; i++) {
 			if (i != selectedYear) {
 				comboYear.add(i);
 			}
 		}
 		model.addAttribute("comboYear", comboYear);
 		// model.addAttribute("currentYear", currentYear);
 		model.addAttribute("selectedYear", selectedYear);
 		model.addAttribute("yearToShow", selectedYear);
 		

 		// R3 - LOGGED IN USER MONTH WISE REPORT
 		ProjectWeekEffortTransactionModelId projectindividualEffortTransactionModel = new ProjectWeekEffortTransactionModelId();
 		ParentTransactionModel parengrouptransactionIndividualModelpie = new ParentTransactionModel();
 		parengrouptransactionIndividualModelpie.setEmpId(userId);
 		parengrouptransactionIndividualModelpie.setYearName(String.valueOf(selectedYear));
 		projectindividualEffortTransactionModel.setStatus("p");
 		projectindividualEffortTransactionModel.setParentTransactionModel(parengrouptransactionIndividualModelpie);
 		List<Object> userProjectMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectindividualEffortTransactionModel);
 		projectindividualEffortTransactionModel.setStatus("m");
 		List<Object> userActivityMonthlyReport = this.projectWeekEffortTransactionService
 				.reportUserMonthWiseEffort(projectindividualEffortTransactionModel);
 	

 		// Create a list of MonthEffort objects
 		List<MonthEffort> projectMonthEfforts = new ArrayList<>();
 		List<MonthEffort> activityMonthEfforts = new ArrayList<>();
 		
 		for (Object result : userProjectMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					projectMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		for (Object result : userActivityMonthlyReport) {
 			if (result instanceof Object[]) {
 				Object[] row = (Object[]) result;

 				if (row.length >= 2) {
 					activityMonthEfforts.add(new MonthEffort((String) row[0], String.valueOf(row[1])));
 				}
 			}
 		}
 		// Sort the list based on the order of months
 		projectMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		activityMonthEfforts.sort(Comparator.comparingInt(monthEffort -> {
 			String monthName = monthEffort.getMonth();
 			return Arrays.asList(new DateFormatSymbols().getMonths()).indexOf(monthName);
 		}));

 		// Extract sorted xValues and yValues as strings
 		List<Object> xValues1 = new ArrayList<>();
 		List<Object> yValues1 = new ArrayList<>();
 		List<Object> xValues1_1 = new ArrayList<>();
 		List<Object> yValues1_1 = new ArrayList<>();

 		for (MonthEffort monthEffort : projectMonthEfforts) {
 			xValues1.add(monthEffort.getMonth());
 			yValues1.add(monthEffort.getEffort());
 		}

 		for (MonthEffort monthEffort : activityMonthEfforts) {
 			xValues1_1.add(monthEffort.getMonth());
 			yValues1_1.add(monthEffort.getEffort());
 		}

 		System.out.println("xValues1 " + xValues1);
 		System.out.println("yValues1 " + yValues1);

 		model.addAttribute("xValues1", xValues1);
 		model.addAttribute("yValues1", yValues1);
 		model.addAttribute("xValues1_1", xValues1_1);
 		model.addAttribute("yValues1_1", yValues1_1);


 	

 		List<String> monthNameList = new ArrayList<>();
 		// for R9 and R4
 		// Loop through each month and add its name to the list
 		for (int i = 1; i <= 12; i++) {
 			Month month = Month.of(i);
 			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
 			monthNameList.add(monthName);
 		}
 		model.addAttribute("monthNameList", monthNameList);
 		return "individualReports";
 	}
 }

 //Define a custom class to store month and effort as strings
 class MonthEffort {
 	String month;
 	String effort;

 	public MonthEffort(String month, String effort) {
 		this.month = month;
 		this.effort = effort;
 	}

 	public String getMonth() {
 		return month;
 	}

 	public String getEffort() {
 		return effort;
 	}
 }
   

