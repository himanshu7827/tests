package in.pms.timesheet.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import in.pms.timesheet.model.WeekSubmitModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("timesheetHomeValidator")
public class TimesheetHomeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WeekSubmitModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WeekSubmitModel weekSubmitModel = (WeekSubmitModel) target;

        List<String> weekEffort = weekSubmitModel.getWeekEffort();

        for (int i = 0; i < weekEffort.size(); i++) {
            String effort = weekEffort.get(i);

            // Validate each effort
            validateEachEffort(effort, "weekEffort[" + i + "]", errors);
        }
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
		for (int i = 0; i < seperateWeekEffort.size(); i++) {
            List<String> eachWeekEffort = seperateWeekEffort.get(i);

            // Validate each effort
            validateWeekEffort(eachWeekEffort, "eachWeekEffort[" + i + "]", errors);
        }
    }

    private void validateEachEffort(String effort, String fieldName, Errors errors) {
        /*// Custom validation for each effort
        if (effort == null || effort.trim().isEmpty()) {
            errors.rejectValue(fieldName, "effort.required", "Effort is required");
            return;
        }*/
        // Assuming effort is in "HH:MM" format
    	if(!effort.isEmpty()){
	        String[] parts = effort.split(":");
	        System.out.println(parts);
	        int hours = Integer.parseInt(parts[0]);
	        int minutes = Integer.parseInt(parts[1]);
	        if (hours < 0 || hours > 99 || minutes < 0 || minutes > 59) {
	            errors.rejectValue(fieldName, "effort.invalidRange", "Invalid hours or minutes range");
	            return;
	        }
	        
    	}
    	
    	/*// Validate the format (HH:MM)
        if (!effort.matches("\\d{1,2}:\\d{2}")) {
            errors.rejectValue(fieldName, "effort.invalidFormat", "Invalid effort format (HH:MM)");
            return;
        }*/
    	
        /*String[] parts = effort.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Validate hours and minutes range
        if (hours < 0 || hours > 99 || minutes < 0 || minutes > 59) {
            errors.rejectValue(fieldName, "effort.invalidRange", "Invalid hours or minutes range");
        }*/
    }
    
    private void validateWeekEffort(List<String> weekEffort, String fieldName, Errors errors){
    	int totalMinutes = 0;
    	for (String effort : weekEffort) {
    		if(!effort.isEmpty()){
    			// Assuming effort is in "HH:MM" format
                String[] parts = effort.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);

                // Calculate total minutes
                totalMinutes += hours * 60 + minutes;
                if(totalMinutes>10080){
                	errors.rejectValue("weekEffort", "effort.invalidRange", "Total effort cannot be greater than 168 hours in a week");
    	            return;
                }
    		}
            
        }
    }
}

/*import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.service.DocumentFormatService;
import in.pms.master.validator.ProjectDocumentValidator;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("TimesheetHomeValidator")
public class TimesheetHomeValidator {

	

    private boolean isEffortGreaterThanLimit(String effort) {
        // Assuming effort is in "HH:MM" format
    	if(!effort.isEmpty()){
	        String[] parts = effort.split(":");
	        System.out.println(parts);
	        int hours = Integer.parseInt(parts[0]);
	        int minutes = Integer.parseInt(parts[1]);
	        return hours > 99 || (hours == 99 && minutes > 99);
    	}
    	else{
    		return false;
    	}
    }

	public Boolean isValid(List<String> weekEffort) {
		for (String effort : weekEffort) {
            if (isEffortGreaterThanLimit(effort)) {
                return false;
            }
        }
        return true;
	}
}

*/