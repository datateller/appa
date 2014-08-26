package net.ywb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String getCurrentDay(){
		Calendar calendar=Calendar.getInstance();
		String year=String.valueOf(calendar.get(Calendar.YEAR));
		String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
		String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		return year+"��"+month+"��"+day+"��";
	}
	
	public static String getStandardCurrentDay() {
		// TODO Auto-generated method stub
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	
	public static String CheckBabyBirthday(String year,String month,String day){
		int intyear=Integer.valueOf(year);
		int intmonth=Integer.valueOf(month);
		int intday=Integer.valueOf(day);
		
		if(intyear>2100&&intyear<1900) 
			return "����Ƿ������";
		if(intmonth>12||intmonth<0)
			return "����Ƿ����·�";
		if(intday>31||intday<0)
			return "����Ƿ�����";
		return "true";
		
	}
    
	public static int getCurrentYear(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}
	
    public static int getCurrentDayOfMonth(){
    	Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
    }
	
}
