package com.corejava.itemsCart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Discounts {
	public static void main(String[] args) throws ParseException {
		
		float n = (float) 0.10;
		System.out.println(n-(n/10));
		
		String sDate1="2019-08-23";  
	    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);  
	    System.out.println(sDate1+"\t"+date1);
		
		Date todayDate, yesterdayDate, tillNextSevenDays, fromThreeDays, lastDayOfTheMonth;
		
		DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
		Date today = new Date();
		Calendar calender = Calendar.getInstance();
		
		todayDate =  calender.getTime();
				
		Calendar calender1 = Calendar.getInstance();
		calender1.add(Calendar.DATE, -1);
		yesterdayDate =  calender1.getTime();
		
		Calendar calender2 = Calendar.getInstance();
		calender2.add(Calendar.DATE, +7);
		tillNextSevenDays = calender2.getTime();
		
		Calendar calender3 = Calendar.getInstance();
		calender3.add(Calendar.DATE, +3);
		fromThreeDays = calender3.getTime();
		
		Calendar calender4 = Calendar.getInstance();
		calender4.setTime(today);
		calender4.add(Calendar.MONTH, 1);
		calender4.set(Calendar.DAY_OF_MONTH, 1);
		calender4.add(Calendar.DATE, -1);		
		lastDayOfTheMonth = calender4.getTime();		
		
		if(calender2.after(calender3) ) {
			System.out.println("SUCCESS");
		}
		
		System.out.println("todayDate..: "+todayDate);
		System.out.println("yesterdayDate..: "+yesterdayDate);
		System.out.println("tillNextSevenDays..: "+tillNextSevenDays);
		System.out.println("fromThreeDays..: "+fromThreeDays);
		System.out.println("endOfTheMonth..:"+lastDayOfTheMonth);
	}
}
