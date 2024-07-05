package in.pms.master.validator;


import in.pms.global.misc.RegexValidationFile;
import in.pms.global.util.DateUtils;
import in.pms.master.model.MilestoneReviewModel;
import in.pms.master.model.ProjectMilestoneForm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class MilestoneReviewValidator implements Validator {
	
	private Pattern namePattern;
	private Matcher nameMatcher;	

	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return MilestoneReviewModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		MilestoneReviewModel milestoneReviewModel=(MilestoneReviewModel) obj;
		Calendar cal = GregorianCalendar.getInstance();
		/*if((DateUtils.dateStrToDate(milestoneReviewModel.getCompletionDate())).compareTo(date) >0){
			
		}*/
		String day = "";
		String month = "";
		int tempMonth=cal.get(Calendar.MONTH)+1;
		if(cal.get(Calendar.DATE)<10){
			day ="0"+cal.get(Calendar.DATE);
		}else{
			day =""+cal.get(Calendar.DATE);
		}
		
		if(tempMonth<10){
			month ="0"+tempMonth;
		}else{
			month =""+tempMonth;
		}
		
		String strEndDate= day+"/"+month+"/"+cal.get(Calendar.YEAR);
		
		//System.out.println(strEndDate);
		
		if(milestoneReviewModel.isStatus()){
		try {
			Date completeionDate  = DateUtils.dateStrToDate(milestoneReviewModel.getCompletionDate());
			//System.out.println(DateUtils.differenceInDates(completeionDate, strEndDate));
			if(DateUtils.differenceInDates(completeionDate, strEndDate) < 0){
		          errors.rejectValue("completionDate", "completion.date.message");

			}
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		}else{
			/*------ Server-Side Error For Review Remarks only completed status [05-04-2024]-------*/
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "strRemarks", "milestone.review.remarks");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reviewDate", "milestone.review.reviewDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "completionDate", "milestone.review.completionDate");

	
	}
	
}
