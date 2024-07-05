package in.pms.global.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;



public interface DateUtils {

	public static String dateToString(Date date){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(null != date){
		return dateFormat.format(date);	
		}
		return null;
	}
	
	public static Date dateStrToDate(String dateString) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.parse(dateString);						
	}
	
	  public static String getMonthandDaysBetweenDates(Date startDate, Date endDate) {	   	 
		   	 Interval interval = new Interval(startDate.getTime(), endDate.getTime());
		   		Period period = interval.toPeriod().normalizedStandard(PeriodType.yearMonthDay());

		   		PeriodFormatter formatter = new PeriodFormatterBuilder()
		   		          .appendYears()
		   		            .appendSuffix(" year ", " years ")
		   		            .appendSeparator(" and ")
		   		            .appendMonths()
		   		            .appendSuffix(" month ", " months ")
		   		            .appendSeparator(" and ")
		   		            .appendDays()
		   		            .appendSuffix(" day ", " days ")
		   		            .toFormatter();
		   		return formatter.print(period);		        
	}
	  
	  public static String getLastDateOfMonth(String strDate){
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  String res = "00";
		try {
			Date date = sdf.parse(strDate);
			 Calendar cal = Calendar.getInstance();
			  cal.setTime(date);
			 int temp = cal.getActualMaximum(Calendar.DATE);
			 /* if(temp<10){
				  res="0"+temp;
			  }else{
				  res=""+temp;
			  }*/
			 res=""+temp;
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return res; 
	  }
	  
	  public static int getDurationInYears(Date startDate,Date endDate){
		  Period period = new Period(startDate.getTime(),endDate.getTime());
		  int year = period.getYears();
		  int month = period.getMonths();
		  int days = period.getDays();
		  
		  if(month>0 || days>0){
			  year+=1;
		  }
	
		  return year;
	  }
	  
	  public static int getDurationInMonths(Date startdate,Date enddate){
	  int months = 0;
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	  if(startdate!=null && enddate!=null){
	    String strDate = formatter.format(startdate);  
	    String endDate= formatter.format(enddate); 
	   LocalDate date1 = LocalDate.parse(strDate);
       LocalDate date2 = LocalDate.parse(endDate).plusDays(1);
       /*date2=date2.plusDays(1);*/
       
       
     //Bhavesh (04-10-23) added condition if remaining days grater than 15 then add +1 in diffrenceInMonths 
	 int diffrenceInMonthsValue = Months.monthsBetween(date1,date2).getMonths();
	 
	 int diffrenceInMonths=diffrenceInMonthsValue;
	 
	 int differenceInDays = Days.daysBetween(date1,date2).getDays();
	 
	 double days=(differenceInDays-diffrenceInMonthsValue *30.44);
	
	 if (days>15){
		 diffrenceInMonths+=1; 
		 
	 }
	 
	 return diffrenceInMonths;
	 
	  }else
		  return 0;
  }
  
	  

	  
	  
	  
	 
	
	  
	
	  
	  public static String dateToDateTimeString(Date date){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			return dateFormat.format(date);						
	  }
	  
	  public static int differenceInDates(Date startDate,String strEndDate){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date endDate = null;
			try {
				endDate = dateFormat.parse(strEndDate);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			
			return Days.daysBetween(new LocalDate(startDate), new LocalDate(endDate)).getDays();						
	  }
	  
	  public static Date addDays(Date date, int days){
			 Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        cal.add(Calendar.DATE, days); // minus number would decrement the days
		        return cal.getTime();
		} 
	  
	  public static String formatDateString(String date){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(date);						
		}
}
