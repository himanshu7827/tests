package in.pms.global.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionMethod 
{
	//date to string conversion
	public static String dateToStrDate(Date date)
	{
		if(date != null)
		{
		 SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		 return sdf.format(date);
		}
		else
		{
			return "NA";
		}
	}
	
	//string to date conversion
	public static Date dtDate(String dateformat) throws Exception 
	{
		String dateString = strCurrentDate(dateformat);

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date convertedDate = dateFormat.parse(dateString);
		return convertedDate;
	}
	
	public static String strCurrentDate(String dateformat) {

		Date now = new Date();
		DateFormat df = new SimpleDateFormat(dateformat);
		String currentDate = df.format(now);
		return currentDate;
	}
	
	
}
