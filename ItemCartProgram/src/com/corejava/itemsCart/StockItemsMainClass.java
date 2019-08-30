package com.corejava.itemsCart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;


public class StockItemsMainClass {
	static List<StockItem> itemList = new ArrayList<>();
	static Map<String, Float> itemAndPriceMap = new HashMap<String, Float>();
	
	public static void main(String[] args) throws NumberFormatException, IOException, ParseException {
		readItemsFromCommandLine();
		displayStockItems();
		dates();
		
		//Price a basket containing: 3 tins of soup and 2 loaves of bread, bought today,
		//Expected total cost = 3.15;
		//calculatePrice(noOfSoupTins, noOfBreadLoafs, noOfMilkBottles, noOfApples, Date);
		Calendar calender = Calendar.getInstance();
		System.out.println(calender.getTime());
		calculatePrice(3, 2, 0, 0, purchaseDateWithFormat);
		
		//Price a basket containing: 6 apples and a bottle of milk, bought today,
		//Expected total cost = 1.90;
		calculatePrice(0, 0, 1, 6, purchaseDateWithFormat);
		
		//Price a basket containing: 6 apples and a bottle of milk, bought in 5 days time,
		//Expected total cost = 1.84;
		Calendar calender3 = Calendar.getInstance();
		calender3.add(Calendar.DATE, +5);
		calculatePrice(0, 0, 1, 6, purchaseDateWithFormat); //Bought 5 Days Time
		
		//Price a basket containing: 3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time,
		//Expected total cost = 1.97.
		Calendar calender4 = Calendar.getInstance();
		calender4.add(Calendar.DATE, +5);
		calculatePrice(2, 1, 0, 3, purchaseDateWithFormat);
	}// main
	
	
	
	public static void calculatePrice(int noOfSoupTins, int noOfBreadLoafs, int noOfMilkBottles, int noOfApples, Date date) {
		int offerAvailableForNoOFBreads = noOfSoupTins/2;
		float totalPrice = 0;
		if(noOfSoupTins > 0) {
			for(int i=0; i<noOfSoupTins; i++) {
				totalPrice += itemAndPriceMap.get("soup");
				//System.out.println("itemAndPriceMap.get(\"soup\") " +itemAndPriceMap.get("soup"));
				//System.out.println("Total Price..: "+totalPrice);
			} // for
		}// if - noOfSoupTins
		if(noOfBreadLoafs > 0) {
			for(int i=0; i<noOfBreadLoafs; i++) {
				float breadLoafPrice = itemAndPriceMap.get("bread");
				if(date.after(yesterdayDate) && date.before(tillNextSevenDays)) {
					for(int j=0; j<offerAvailableForNoOFBreads; j++) {
						breadLoafPrice = itemAndPriceMap.get("bread")/2;
						System.out.println("itemAndPriceMap.get(\"bread\")/2 :"+breadLoafPrice);
						offerAvailableForNoOFBreads--;
					}// for
				}// Date Compare				
				totalPrice += breadLoafPrice;
			}// for
		} // if - noOfBreadLoafs
		
		if(noOfMilkBottles>0) {
			float milkCost = itemAndPriceMap.get("milk");
			//System.out.println("milkCost..: "+milkCost);
			for(int i=0; i<noOfMilkBottles; i++) {
				totalPrice+=milkCost;
			}//for
		}// if noOfMilkBottles
		
		if(noOfApples>0) {
			float appleCost = itemAndPriceMap.get("apples");
			if(date.after(fromThreeDays)) {
				appleCost = appleCost-(appleCost/10);
			}//Date Checking
			for(int i=0; i<noOfApples; i++) {
				totalPrice+=appleCost;
				//System.out.println("totalPrice...!");
			}//for
		} //if noOfApples
		
		//System.out.println(float(format(totalPrice,'.2f')));
		System.out.printf("%.2f", totalPrice);
		System.out.println();
		
	} //calculatePrice method...
	
	public static LocalDate validateDateFormat(String date) {
        boolean flag;
        do {
            try {
                 purchaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]"));
                 flag = false;
            } catch (DateTimeParseException e) {
                 System.out.println("Date formate is incorrect please Enter correct formate");
                 flag = true;
            }
        } while (flag);
        return purchaseDate;
	} //validateDateFormat method..

	static LocalDate purchaseDate;
	static Date purchaseDateWithFormat;
	public static void readItemsFromCommandLine() throws NumberFormatException, IOException, ParseException {
		BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(System.in)));
		
		System.out.println("Enter the date of puchasing and date formate is yyyy-MM-dd");		
        String date = bufferedReader.readLine();
        purchaseDate = validateDateFormat(date);
        purchaseDateWithFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        
        System.out.println("Given date is...: " +purchaseDate);
		
		int quantity = Integer.parseInt((bufferedReader.readLine().trim()));
		
		IntStream.range(0, quantity).forEach(i -> {
			try {
				//Reading each Line of input
				String[] strs = bufferedReader.readLine().trim().split("\\s+");
				StockItem pojo = new StockItem();
				pojo.setProduct(strs[0]);
				pojo.setUnit(strs[1]);
				pojo.setCost(Float.parseFloat(strs[2]));
				itemList.add(pojo);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} //try
		});
	}// readItemsFromCommandLine method....
	
	
	public static void displayStockItems() {
		System.out.println("Stock Items...!");
		for( StockItem var:itemList) {
			//System.out.println(var.product+"\t"+var.unit+"\t"+var.cost);
			itemAndPriceMap.put(var.product, var.cost);
		}
	} //displayStockItems method...
	
	static Date todayDate, yesterdayDate, tillNextSevenDays, fromThreeDays, lastDayOfTheMonth;
	public static void dates() {
		
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
	} //dates
	
} //Class
/*
Sample Input

4
soup	tin		0.65
bread	loaf	0.80
milk	bottle	1.30
apples	single	0.10

*/